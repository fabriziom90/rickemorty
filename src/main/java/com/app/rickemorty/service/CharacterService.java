package com.app.rickemorty.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.app.rickemorty.dao.CharacterDAO;
import com.app.rickemorty.model.Character;

public class CharacterService {
	private CharacterDAO characterDao;
	private RestTemplate restTemplate;
	
//	Creazione del costruttore base giusto per vedere lo step
	public CharacterService() {
		System.out.println("Step 5: Spring instanzia il CharacterService");
	}
	
	public void setCharacterDAO(CharacterDAO characterDao) {
		this.characterDao = characterDao;
		System.out.println("Step 6: Spring collega il DAO al Service (Injection)");
	}
	
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		System.out.println("Step 6: Spring ha collegato il RestTemplate al Service (injection)");
	}
	
	public void importCharacterFromRickeAndMorty(Long id) {
		String url = "https://rickandmortyapi.com/api/character/" + id;
		
		Map<String, Object> response = restTemplate.getForObject(url, Map.class);
		
		if(response != null) {
			//Il cast è necessario: response.get restituisce un Object generico.
			System.out.println("RESPONSE: "+response.get("name"));
			String name = (String) response.get("name");
			String species = (String) response.get("species");
			
			Character c = new Character(id, name, species);
			characterDao.save(c);
			System.out.println("Importato con successo");
		}
	}
	
	public void searchLocal(String name) {
		System.out.println("Ricerca in corso per: "+name+"...");
		List<Character> results = characterDao.findByName(name);
		
		if(results.isEmpty()) {
			System.out.println("Nessun risultato trovato");
		}
		else {
			System.out.println("Risultati trovati");
			results.forEach(c -> System.out.println(c.toString()));
		}
	}
	
	public void printLocals() {
		System.out.println("Elenco del DB");
		characterDao.findAll().forEach(System.out::println);
	}
}
