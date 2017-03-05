package com.cybernetica.bj.server.models;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;


@SuppressWarnings("serial")
@Entity(name="GAMES")
public class Game  implements Serializable{

	
	@Id
	@GenericGenerator(name="seq_games" , strategy="increment")
	@GeneratedValue(generator="seq_games")
	@Column(name="ID")
	private Long id;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
	private Set<User> players;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<User> getPlayers() {
		return players;
	}

	public void setPlayers(Set<User> players) {
		this.players = players;
	}
	
}
