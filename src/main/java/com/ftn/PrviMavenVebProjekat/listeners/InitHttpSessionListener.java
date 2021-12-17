package com.ftn.PrviMavenVebProjekat.listeners;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;


@Component
public class InitHttpSessionListener implements HttpSessionListener {

	/** kod koji se izvrsava po kreiranju sesije */
	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("Inicijalizacija sesisje HttpSessionListener...");
//		
//		//pri kreiranju sesije inicijalizujemo je ili radimo neke dodatne aktivnosti
//		
		System.out.println("Uspeh HttpSessionListener!");
	}
	
	/** kod koji se izvrsava po brisanju sesije */
	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("Brisanje sesisje HttpSessionListener...");
		
		System.out.println("Uspeh HttpSessionListener!");
	}

}


