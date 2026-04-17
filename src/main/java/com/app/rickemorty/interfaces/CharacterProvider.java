package com.app.rickemorty.interfaces;
import com.app.rickemorty.model.Character;

public interface CharacterProvider {
	Character getCharacter(Long id);
}
