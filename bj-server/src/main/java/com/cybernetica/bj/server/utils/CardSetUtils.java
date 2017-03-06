package com.cybernetica.bj.server.utils;

import java.util.BitSet;
import java.util.Random;

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

}
