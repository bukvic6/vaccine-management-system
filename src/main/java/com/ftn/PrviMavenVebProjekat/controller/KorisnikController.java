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
import org.springframework.web.context.ServletContextAware;

import com.ftn.PrviMavenVebProjekat.bean.SecondConfiguration.ApplicationMemory;
import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.TipKorisnika;
import com.ftn.PrviMavenVebProjekat.service.KorisnikService;

@Controller
@RequestMapping(value = "/korisnici")
public class KorisnikController implements ServletContextAware{
	private String bURL;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private KorisnikService korisnikService;
	

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;		
	}
	
	public static final String ULOGOVANI_KORISNIK_KEY = "ulogovani_korisnik";
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		bURL = servletContext.getContextPath() + "/";
//		Korisnici korisnici = new Korisnici();
//		memorijaAplikacije.put(KORISNIK_KEY, korisnici);
	} 

	@GetMapping(value = "/login")
	public void getLogin(@RequestParam(required = false) String email, @RequestParam(required = false) String lozinka,
			HttpSession session, HttpServletResponse response) throws IOException {
		postLogin(email, lozinka, session, response);
	}

	@PostMapping(value = "/login")
	@ResponseBody
	public void postLogin(@RequestParam(required = false) String jmbg, @RequestParam(required = false) String lozinka,
			HttpSession session, HttpServletResponse response) throws IOException {
		
		Korisnik korisnik = korisnikService.findOne(jmbg, lozinka);
		String greska = "";
		if (korisnik == null)
			greska = "neispravni kredencijali<br/>";

		if (!greska.equals("")) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out;
			out = response.getWriter();

			StringBuilder retVal = new StringBuilder();
			retVal.append("<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "	<meta charset=\"UTF-8\">\r\n"
					+ "	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviForma.css\"/>\r\n"
					+ "	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n"
					+ "	<base href=\"/PrviMavenVebProjekat/\">	\r\n" + 
					"<title>Prijava korisnika</title>\r\n"
				   
					+ "</head>\r\n" + "<body>\r\n" + "	<ul>\r\n");
			if (!greska.equals(""))
				retVal.append("	<div>" + greska + "</div>\r\n");
			retVal.append("	<form method=\"post\" action=\"korisnici/login\">\r\n" + "		<table>\r\n"
					+ "			<caption>Prijava korisnika na sistem</caption>\r\n"
					+ "			<tr><th>Jmbg:</th><td><input type=\"text\" value=\"\" name=\"jmbg\" required/></td></tr>\r\n"
					+ "			<tr><th>Lozinka:</th><td><input type=\"password\" value=\"\" name=\"lozinka\" required/></td></tr>\r\n"
					+ "			<tr><th></th><td><input type=\"submit\" value=\"Prijavi se\" /></td>\r\n"
					+ "		</table>\r\n" + "	</form>\r\n" + "	<br/>\r\n" + "</body>\r\n" + "</html>");

			out.write(retVal.toString());
			return;
		}

		if (session.getAttribute(ULOGOVANI_KORISNIK_KEY) != null)
			greska = "korisnik je već prijavljen na sistem morate se prethodno odjaviti<br/>";

		if (!greska.equals("")) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out;
			out = response.getWriter();

			StringBuilder retVal = new StringBuilder();
			retVal.append("<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "	<meta charset=\"UTF-8\">\r\n"
					+ "	<base href=\"/PrviiMavenVebProjekat/\">	\r\n" + "	<title>Prijava korisnika</title>\r\n"
					+ "</head>\r\n" + "<body>\r\n" + "	<ul>\r\n");
			if (!greska.equals(""))
				retVal.append("	<div>" + greska + "</div>\r\n");
			retVal.append("	<a href=\"index.html\">Povratak</a>\r\n" + "	<br/>\r\n" + "</body>\r\n" + "</html>");

			out.write(retVal.toString());
			return;
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
