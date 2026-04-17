package com.app.rickemorty;

import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.app.rickemorty.service.CharacterServiceImpl;

@SpringBootApplication
public class RickemortyApplication {

	public static void main(String[] args) {
//		Versioni nuove
//		SpringApplication.run(RickemortyApplication.class, args);
		System.out.println("STEP 1: CARICAMENTO CONTESTO (viene letto il file beans.xml)");
		ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/beans.xml");
		
		System.out.println("CONTESTO CARICATO CORRETTAMENTE");
		System.out.println("Step 7: il main prende il controllo e svolte le operazioni utilizzando i beans");
		CharacterServiceImpl service = (CharacterServiceImpl) context.getBean("internalService"); //vado a richiamare il bean con proprietà name=characterService
		
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			System.out.println("\n--- GESTIONALE Rick & Morty---\n");
			System.out.println("1. Importa personaggio da API (inserisci ID)");
			System.out.println("2. Cerca personaggio nel DB locale");
			System.out.println("3. Elenco completo nel DB");
			System.out.println("0. Esci");
			
			int scelta = scanner.nextInt();
			if(scelta == 0) {
				System.out.println("Uscita in corso...");
				break;
				
			}
			switch(scelta) {
				case 1:
					System.out.println("ID da scaricare: ");
					service.importCharacterFromRickeAndMorty(scanner.nextLong());
					break;
				case 2:
					System.out.println("Nome da cercare:");
					service.searchLocal(scanner.next());
					break;
				case 3:
					System.out.println("Elenco completo dei personaggi salvati in locale");
					service.printLocals();
			}
			
			service.printLocals();
		}
		
		System.out.println("Arrivederci");
		
	}

}
