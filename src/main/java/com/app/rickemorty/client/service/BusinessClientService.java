package com.app.rickemorty.client.service;

import com.app.rickemorty.interfaces.CharacterProvider;
import com.app.rickemorty.model.Character;

public class BusinessClientService {
	private CharacterProvider provider;
	
	public void setProvider(CharacterProvider provider) {
		this.provider = provider;
	}
	
	public void getData(Long id) {
		System.out.println("Client: Sto chiedendo i dati per l'ID "+id);
		Character c = provider.getCharacter(id);
		System.out.println("Client: ho ricevuto "+c.getName()+". Procedo con l'elaborazione aziendale");
	}
}
