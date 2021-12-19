package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ftn.PrviMavenVebProjekat.bean.SecondConfiguration.ApplicationMemory;
import com.ftn.PrviMavenVebProjekat.model.Korisnici;
import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.TipKorisnika;

@Controller
@RequestMapping(value = "/korisnici")
public class KorisnikController {
	private String bURL;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private ApplicationMemory memorijaAplikacije;
	
//	private String ULOGOVANI_KORISNIK_KEY = "ulogovani_korisnik";
	public static final String KORISNIK_KEY = "korisnik";
	public static final String ULOGOVANI_KORISNIK_KEY = "ulogovani_korisnik";
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		bURL = servletContext.getContextPath() + "/";
		Korisnici korisnici = new Korisnici();
		memorijaAplikacije.put(KORISNIK_KEY, korisnici);
	} 

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/login")
	public void login(@RequestParam(required = true) String jmbg,
			@RequestParam(required = true) String lozinka, HttpServletResponse response, HttpSession session) throws IOException {
		
		Korisnici korisnici = (Korisnici) memorijaAplikacije.get(KORISNIK_KEY);
		if (korisnici != null) {
			if (korisnici.findAll().size() == 0) {
				korisnici = new Korisnici();
				memorijaAplikacije.put(KORISNIK_KEY, korisnici);
				
			}
		}else {
			korisnici = new Korisnici();
			memorijaAplikacije.put(ULOGOVANI_KORISNIK_KEY, korisnici);
			
		}
		String greska = "";
		Korisnik korisnik = korisnici.nadjiKorisnikaPoJMBG(jmbg);
		if (korisnik == null) {
			greska = "korisnik nije pronadjen <br/>";
		}else if (!korisnik.getLozinka().equals(lozinka)) {
			greska = "korisnik nije pronadjen <br/>";
			
		}
		if(!greska.equals("")) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out = response.getWriter();

			StringBuilder retVal = new StringBuilder();
			retVal.append("<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "	<meta charset=\"UTF-8\">\r\n"
					+ "	<base href=\"/PrviMavenVebProjekat/\">	\r\n" + "	<title>Prijava korisnika</title>\r\n"
					+ "	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviForma.css\"/>\r\n"
					+ "	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n"
					+ "</head>\r\n" + "<body>\r\n" + "	<ul>\r\n"
					+ "		<li><a href=\"registracija.html\">Registruj se</a></li>\r\n" + "	</ul>\r\n");
			if (!greska.equals(""))
				retVal.append("	<div>" + greska + "</div>\r\n");
			retVal.append("	<form method=\"post\" action=\"korisnici/login\">\r\n" + "		<table>\r\n"
					+ "			<caption>Prijava korisnika na sistem</caption>\r\n"
					+ "			<tr><th>Email:</th><td><input type=\"text\" value=\"\" name=\"email\" required/></td></tr>\r\n"
					+ "			<tr><th>Šifra:</th><td><input type=\"password\" value=\"\" name=\"sifra\" required/></td></tr>\r\n"
					+ "			<tr><th></th><td><input type=\"submit\" value=\"Prijavi se\" /></td>\r\n"
					+ "		</table>\r\n" + "	</form>\r\n" + "	<br/>\r\n" + "</body>\r\n" + "</html>");

			out.write(retVal.toString());
			return;
		}
			
//		proverava da li je neko vec ulogovan na sesiji
		if(session.getAttribute(KORISNIK_KEY)!= null) {
			greska = "Potrebno je da se prvo odjavite. <br/>";
		}
		if(korisnik.getTipKorisnika().equals(TipKorisnika.MEDICINAR)) {
			session.setAttribute(ULOGOVANI_KORISNIK_KEY, korisnik);
			response.sendRedirect(bURL + "medicinar");
		}else {
			session.setAttribute(ULOGOVANI_KORISNIK_KEY, korisnik);
			response.sendRedirect(bURL + "termini");
			
		}
		
	}
	

}
