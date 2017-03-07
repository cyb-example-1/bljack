package com.cybernetica.bj.common;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.cybernetica.bj.common.dto.CardDTO;
import com.cybernetica.bj.common.enums.CardRank;
import com.cybernetica.bj.common.enums.CardSuit;

public class CardSetUtils {

	/**
	 * generates card  outside existing set
	 * @param existing
	 * @return
	 */
	public static long generateCard(long existing){
		BitSet bitset = toBitset(existing);
		Random r = new Random();
		int nextInt = r.nextInt(52-bitset.cardinality());
		for(int i=0;i<52;i++){
			if(bitset.get(i))
				continue;
			if(nextInt==0){
				bitset.set(i);
				break;
			}
			nextInt--;
		}
		return fromBitset(bitset);
	}
	
	/**
	 * adds one card to existing set
	 * @param existing
	 * @return
	 */
	public static long generateSet(long existing){
		long card = generateCard(existing);
		return card|existing;
	}
	
	public static BitSet toBitset(long l){
		return BitSet.valueOf(new long[]{l});
	}
	public static long fromBitset(BitSet bs)
	{
		return bs.toLongArray()[0];
	}
	
	public static List<CardDTO> toList(long set){
		List<CardDTO> ret = new ArrayList<>();
		for(int i=0;i<4;i++) {
			long rankset = set &0x1fff;
			ret.addAll(toList(rankset, CardSuit.valueOf(i)));
			set= set>>13;
		}
		return ret;
	}
	
	public static List<CardDTO> toList(long set,CardSuit suit){
		List<CardDTO> ret = new ArrayList<>();
		BitSet bs=toBitset(set);
		for(int i=0;i<13;i++) {
			if(bs.get(i)) {
				ret.add(new CardDTO(suit, CardRank.valueOf(i)));
			}
		}
		return ret;
	}

	public static int getSetScore(Long cardSet) {
		List<CardDTO> cards = toList(cardSet);
		int aceCount=0;
		int score=0;
		for(CardDTO card:cards) {
			if(card.getRank()==CardRank.ACE) 
				aceCount++;
			else {
					score+=card.getRank().getScore();	
				}				
		}
		
		if(aceCount==0)
			return score;
		List<Integer> variants= new ArrayList<>();
		variants.add(score);
		// make all variants
		for(int i=0;i<aceCount;i++) {
			variants=getAceVariants(variants);
		}
		
		List<Integer> goodVariants = variants.stream().filter(i-> i<=21).collect(Collectors.toList());
		if(goodVariants.size()>0) {
			goodVariants.sort((Integer v1,Integer v2)->{return v2-v1;});
			return goodVariants.get(0);
		}
		//return smallest
		variants.sort((Integer v1,Integer v2)->{return v1-v2;});
		return variants.get(0);
	}
	
	private static List<Integer> getAceVariants(List<Integer> existing){
		List<Integer> ret = new ArrayList<>();
		for(Integer v:existing)
			ret.add(v+1);
		for(Integer v:existing)
			ret.add(v+11);		
		return ret;
	}
	

}
