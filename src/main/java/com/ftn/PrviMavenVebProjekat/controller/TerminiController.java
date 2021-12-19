package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ftn.PrviMavenVebProjekat.bean.SecondConfiguration.ApplicationMemory;
import com.ftn.PrviMavenVebProjekat.model.Korisnici;
import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.Termin;
import com.ftn.PrviMavenVebProjekat.model.Termini;

@Controller
@RequestMapping(value="/termini")

public class TerminiController  implements ApplicationContextAware{
	
	public static final String TERMINI_KEY = "termini";
	
	@Autowired
	private ServletContext servletContext;
	private String bURL;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private ApplicationMemory memorijaAplikacije;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	
//	instanciranje applicationConteksta
	@PostConstruct
	public void init() {
		bURL = servletContext.getContextPath() + "/";
		memorijaAplikacije = applicationContext.getBean(ApplicationMemory.class);
		
		Termini termini = new Termini();
		
//		postavljanje termina u memoriju aplikacije
		
		memorijaAplikacije.put(TerminiController.TERMINI_KEY, termini);
		
//		servletContext.setAttribute(TerminiController.TERMINI_KEY, termini);	

	}
//	preuzmemo iz memorije app termine koje smo ucitali  i onda napravimo html stranicu koja vraca povratnu vrednost
//	koristimo klasu termini koja sadrzi kolekciju termina 
//	preuzeti je iz memorije aplikacije
//	prosledimo kljuc pod kojim smo sacuvali termine
//	moramo kastovati jer vraca objekat
	@GetMapping
	@ResponseBody
	public String index(HttpSession session) {

		
		Korisnik ulogovani = (Korisnik) session.getAttribute(KorisnikController.ULOGOVANI_KORISNIK_KEY);
		Termini termini = (Termini) memorijaAplikacije.get(TerminiController.TERMINI_KEY);
		
		
		
		StringBuilder retVal = new StringBuilder();

		
		retVal.append("<!DOCTYPE html>\n" + 
				"<html>\n" + 
				"<head>\n" + 
				"	<meta charset=\"UTF-8\" />\n" + 
				"	<base href=\""+ bURL + "\">\n" + 
				"	<title>Prikaz Termina</title>\n" + 
				"</head>\n" + 
				"<body>\n" +
				"<table>" +
				       "<tr>" + 
				            "<th></th>" +
				            "<th>Prezime</th>" +
				            "<th>JMBG</th>" +
				            "<th>Vreme</th>" +
				            "<th>Vakcina</th>" +
				"</tr>");
		String jmbgg = ulogovani.getJmbg();
	
		
		List<Termin> listaTermina = termini.findOne(jmbgg);
		
		

		
		for(int i = 0; i< listaTermina.size(); i++) {
			retVal.append("<tr>"
					+ "<td>" + ulogovani.getIme()+ "</td>"
					+ "<td>" + ulogovani.getPrezime() +"</td>"
							+ "<td>" + ulogovani.getJmbg()+ "</td>"
					+ "<td>" + listaTermina.get(i).getVreme()+ "</td>"
							+ "<td>" + listaTermina.get(i).getVakcina()+ "</td>" +
							"				<td>" + 
							"					<form method=\"post\" action=\"termini/ukloni\">\r\n" + 
							"						<input type=\"hidden\" name=\"id\" value=\""+listaTermina.get(i).getId()+ "\">\r\n" + 
							"						<input type=\"submit\" value=\"ukloni\"></td>\r\n" + 
							"					</form>\r\n" +
							"				</td>" +
							"			</tr>\r\n");
		}

		retVal.append(
				"		</table>\r\n");
		retVal.append(
				"	<ul>\r\n" + 
				"		<li><a href=\"termini/add\">Dodaj termin za vakcinu</a></li>\r\n" + 
				"	</ul>\r\n");
		
		retVal.append(
				"</body>\r\n"+
				"</html>\r\n");		
		return retVal.toString();
	}
	
	/** pribavnjanje HTML stanice za unos novog entiteta, get zahtev */
	@GetMapping(value="/add")
	@ResponseBody
	public String create() {
		//preuzimanje vrednosti iz konteksta

		StringBuilder retVal = new StringBuilder();
		retVal.append(
				"<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"	<meta charset=\"UTF-8\">\r\n" + 
	    		"	<base href=\""+bURL+"\">" + 
				"	<title>Dodaj knjigu</title>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviForma.css\"/>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n"+
				"</head>\r\n" + 
				"<body>\r\n" + 
				"	<form method=\"post\" action=\"termini/add\">\r\n" + 
				"		<table>\r\n" + 
				"			<caption>Termin</caption>\r\n" + 
				"            <tr><th>   "
				+ "    <select name=\"vakcina\">\n" + 
				"      <option value=\"Pfizer\">Pfizer</option>\n" + 
				"      <option value=\"Sputnik\">Sputnik</option>\n" + 
				"      <option value=\"Sinopharm\">Sinopharm</option>\n" + 
				"       <option value=\"AstraZrnrca\">AstraZrnrca</option>\n" + 
				"      <tr><th></th><td><input type=\"submit\" value=\"Dodaj\" /></td>\r\n" + 
				"	</form>\r\n" +
				"	<br/>\r\n");
		retVal.append(
				"</body>\r\n"+
				"</html>\r\n");		
		return retVal.toString();
	}
	@PostMapping(value="/ukloni")
	
	public void ukloniTermin(@RequestParam Long id, HttpServletResponse response) throws IOException {
        
		Termini termini = (Termini) memorijaAplikacije.get(TerminiController.TERMINI_KEY);
		Termin deleted = termini.delete(id);
		response.sendRedirect(bURL + "termini");
       
	}

	// POST: termini/add
	@PostMapping(value="/add")
	
	public void create(@RequestParam String vakcina, HttpServletResponse response,HttpSession session) throws IOException {		
		//preuzimanje vrednosti iz konteksta
		Korisnik ulogovani = (Korisnik) session.getAttribute(KorisnikController.ULOGOVANI_KORISNIK_KEY);

		Termini termini = (Termini) memorijaAplikacije.get(TerminiController.TERMINI_KEY);
		
		Termin termin = new Termin();
		if(vakcina.equals("Pfizer")) {
			termin.setVakcina("Fizer");
		}else if(vakcina.equals("Sputnik")) {
			termin.setVakcina("Sputnik");
		}else if(vakcina.equals("Sinopharm")) {
			termin.setVakcina("Sinopharm");
		}else {
			termin.setVakcina("AstraZeneka");
		}
		termin.setJmbg(ulogovani.getJmbg());
		
			
		Termin saved = termini.save(termin);
		response.sendRedirect(bURL+"termini");
	}
}


