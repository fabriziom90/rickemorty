package com.app.rickemorty.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.app.rickemorty.model.Character;

public class CharacterDAO {
	private JdbcTemplate jdbcTemplate;
	
//	creazione del costruttore base giusto per mostrare un messaggio
	public CharacterDAO() {
		System.out.println("Step 2: Spring sta instanziando il CharacterDAO tramite il costruttore");
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		System.out.println("Step 3: Setter Injection del jdbcTemplate nel DAO (Dipendency Injection)");
	}
	
	public void setupTable() {
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS characters (id BIGINT PRIMARY KEY, name VARCHAR(50), species VARCHAR(50))");
		System.out.println("Step 4: Creazione tabella personaggi creata/verificata");
	}
	
	public List<Character> findByName(String name){
		return jdbcTemplate.query("SELECT * FROM characters WHERE name LIKE ?", 
			new Object[] {"%"+name+"%"}, new RowMapper<Character>(){
				@Override
				public Character mapRow(ResultSet rs, int numRow) throws SQLException{
					Character c = new Character();
					c.setId(rs.getLong("id"));
					c.setName(rs.getString("name"));
					c.setSpecies(rs.getString("species"));
					
					return c;
				}
		});
	}
	
	public List<Character> findAll(){
//		Versioni più recenti
//		List<Character> characters = jdbcTemplate.query("SELECT * FROM characters", (resultSet, rowNum) -> {
//			new Character(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("species"));
//		});
		
		String sql = "SELECT * FROM characters";
		
		return jdbcTemplate.query(sql, new RowMapper<Character>() {
			@Override
			public Character mapRow(ResultSet rs, int rowNum) throws SQLException{
				Character character = new Character();
				character.setId(rs.getLong("id"));
				character.setName(rs.getString("name"));
				character.setSpecies(rs.getString("species"));
				
				return character;
			}
		});
	}
	
	public Character findById(Long id) {
		String sql = "SELECT * FROM characters WHERE id = ?";
		
		return jdbcTemplate.queryForObject(sql, new Object[] {id}, new RowMapper<Character>() {
			@Override
			public Character mapRow(ResultSet rs, int rowNum) throws SQLException{
				Character c = new Character();
				c.setId(rs.getLong("id"));
				c.setName(rs.getString("name"));
				c.setSpecies(rs.getString("species"));
				
				return c;
				
			}
		});
	}
	
	public void save(Character c) {
		try {
			String sql = "INSERT INTO characters (id, name, species) VALUES (?, ?, ?)";
			
//		Versioni più vecchie
			Object[] params = new Object[] {c.getId(), c.getName(), c.getSpecies()};
			jdbcTemplate.update(sql, params);
			
//		Versioni più recenti
//		jdbcTemplate.update(sql, c.getId(), c.getName(), c.getSpecies());
			
		}
		catch(DuplicateKeyException e) {
			throw new IllegalArgumentException("ID Duplicato");
		}
	}
	
	public void update(Long id, Character c) {
		String sql ="UPDATE characters SET name = ?, species = ? WHERE id = ?";
		
//		Versioni più vecchie
		Object[] params = new Object[] {c.getId(), c.getName(), c.getSpecies()};
		jdbcTemplate.update(sql, params);
		
//		Versioni più recenti
//		jdbcTemplate.update(sql, c.getId(), c.getName(), c.getSpecies());
	}
	
	public void delete(Long id) {
		jdbcTemplate.update("DELETE FROM characters WHERE id = ?", id);
	}
	
}
