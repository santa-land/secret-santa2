package com.secretsanta.service;

import com.secretsanta.model.Santa;

public interface SantaService {
	String lastSanta();
	
	int santaSize();
	
	public boolean isSantaExist(Santa santa);
	
	void saveSanta(Santa santa);
	
	Santa findMyMatch(String santaName);
	
	void createMatch(String user, String password);
	
	void deleteAllSantas(String user, String password);
	
	Santa findByName(String name);
	
	void shuffle();

}
