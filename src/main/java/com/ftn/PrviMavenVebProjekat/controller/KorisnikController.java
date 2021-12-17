package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftn.PrviMavenVebProjekat.bean.SecondConfiguration.ApplicationMemory;
import com.ftn.PrviMavenVebProjekat.model.Korisnici;
import com.ftn.PrviMavenVebProjekat.model.Korisnik;

@Controller
@RequestMapping(value = "/korisnici")
public class KorisnikController {
	private String bURL;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private ApplicationMemory applicationMemory;
	
//	private String ULOGOVANI_KORISNIK_KEY = "ulogovani_korisnik";
	public static final String ULOGOVANI_KORISNIK_KEY = "ulogovani_korisnik";
	
	@PostConstruct
	public void init() {
		bURL = servletContext.getContextPath() + "/";
//		Korisnici korisnici = new Korisnici();
//		applicationMemory.put(KorisnikController.KORISNICI_KEY, korisnici);
	} 
//	Izvuce korisnike iz memorije, ako je kolekcija prazna inicijalizovati 
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/login")
	public void login(@RequestParam(required = true) String jmbg,
			@RequestParam(required = true) String password, HttpServletResponse response, HttpSession session) throws IOException {
		
		Korisnici korisnici = (Korisnici) applicationMemory.get(ULOGOVANI_KORISNIK_KEY);
		if (korisnici != null) {
			if (korisnici.findAll().size() == 0) {
				korisnici = new Korisnici();
				applicationMemory.put(ULOGOVANI_KORISNIK_KEY, korisnici);
				
			}
			
		}else {
			korisnici = new Korisnici();
			applicationMemory.put(ULOGOVANI_KORISNIK_KEY, korisnici);
			
		}
		String greska = "";
		Korisnik korisnik = korisnici.nadjiKorisnikaPoJMBG(jmbg);
		if (korisnik == null) {
			greska = "korisnik nije pronadjen <br/>";
		}else if (!korisnik.getLozinka().equals(password)) {
			greska = "korisnik nije pronadjen <br/>";
			
		}
//		proverava da li je neko vec ulogovan na sesiji
		if(session.getAttribute(ULOGOVANI_KORISNIK_KEY)!= null) {
			greska = "Potrebno je da se prvo odjavite. <br/>";
		}
		if (!greska.equals("")) {
			response.setContentType("text/html; charset = UTF-8");
			PrintWriter out = response.getWriter();
			StringBuilder retVal = new StringBuilder();
			
			retVal.append("<!DOCTYPE html>\n" + 
					"<html>\n" + 
					"<head>\n" + 
					"	<meta charset=\"UTF-8\" />\n" + 
					"	<base href=\"b/PrviMavenVebProjekat/\">\n" + 
					"	<title>Osnovna stranica projekta</title>\n" + 
					"</head>\n" + 
					"<body>\n" + 
					"	<h1>Osnovna stranica projekta</h1>\n" + 
					"	\n" + 
					"	\n" + 
					" \"<div>\" + "
					+ greska + "</div><br/>\")\n" + 
				
					"		<form method=\"post\" action=\"korisnici/login\">\n" + 
					"		<table>\n" + 
					"			<caption>Prijava korisnika na sistem</caption>\n" + 
					"			<tr><th>jmbg::</th><td><input type=\"text\" value=\"\" name=\"jmbg\" required/></td></tr>\n" + 
					"			<tr><th>Šifra:</th><td><input type=\"password\" value=\"\" name=\"password\" required/></td></tr>\n" + 
					"			<tr><th></th><td><input type=\"submit\" value=\"Prijavi se\" /></td>\n" + 
					"		</table>\n" + 
					"	</form>\n" + 
					"</body>\n" + 
					"</html>");
			out.write(retVal.toString());
			return;
			        
			
		}
		session.setAttribute(ULOGOVANI_KORISNIK_KEY,korisnik);
		response.sendRedirect(bURL + "termini");
	}
	

}
