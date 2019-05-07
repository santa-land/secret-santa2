package com.secretsanta.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.secretsanta.model.Santa;
import org.springframework.stereotype.Service;

@Service("santaService")
public class SantaServiceImpl implements SantaService {

	private static final AtomicLong counter = new AtomicLong();
	private static List<Santa> santas;
	private static List<Santa> matches;

	static {
		santas = new ArrayList<Santa>();
		matches = new ArrayList<Santa>();
	}

	@Override
	public String lastSanta() {
		System.out.println("inside last santa ...");
		if (santas.size() >= 1) {
			return santas.get(santas.size() - 1).toString();
		}
		return null;
	}

	@Override
	public int santaSize() {
		return santas.size();
	}

	@Override
	public boolean isSantaExist(Santa santa) {
		return findByName(santa.getSantaname()) != null;
	}

	@Override
	public void saveSanta(Santa santa) {
		santa.setId(counter.incrementAndGet());
		santas.add(santa);
	}

	@Override
	public Santa findMyMatch(String santaName) {
		Santa santa = findSanta(santaName);
		return matches.get(santas.indexOf(santa));
	}

	@Override
	public void createMatch(String user, String password) {
		if (user.equals("admin@smith.com") && user.equals("123")) {
			// using a shuffle method
			shuffle();
		}
	}

	@Override
	public void deleteAllSantas(String user, String password) {
		if (user.equals("admin@smith.com") && user.equals("123")) {
			santas.clear();
		}
	}

	@Override
	public Santa findByName(String name) {
		for (Santa santa : santas) {
			if (santa.getSantaname().equalsIgnoreCase(name)) {
				return santa;
			}
		}
		return null;
	}

	@Override
	public void shuffle() {

		int counter = santas.size();

		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= counter; i++) {
			list.add(new Integer(i));
		}
		Collections.shuffle(list);
		for (int i = 0; i < counter; i++) {
			matches.add(santas.get(list.get(counter)));
		}
	}

	public Santa findSanta(String santaName) {
		for (Santa santa : santas) {
			if (santa.getSantaname().equalsIgnoreCase(santaName)) {
				return santa;
			}
		}
		return null;
	}


}
