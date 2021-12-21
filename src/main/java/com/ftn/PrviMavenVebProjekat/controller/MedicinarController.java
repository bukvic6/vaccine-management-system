package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.context.ServletContextAware;

import com.ftn.PrviMavenVebProjekat.bean.SecondConfiguration.ApplicationMemory;
import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.Termin;
import com.ftn.PrviMavenVebProjekat.service.KorisnikService;
import com.ftn.PrviMavenVebProjekat.service.TerminService;

@Controller
@RequestMapping(value="/medicinar")
public class MedicinarController implements ServletContextAware{
	
//	klasa je radila sa memorijom aplikacije i apl Contextom.implementiram servletContext zbog rada u fajlovima
	
	@Autowired
	private ServletContext servletContext;
	private String bURL;
	
	
	
//	preko dependency injection(potrazi u literaturi) ubacujem servise  
//	dependency injection u runtime-u injektuje implementacije tog servisa
	@Autowired
	private TerminService terminService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	
	
//    metode za setovanje servletContexsta, moramo ga inicializovati
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		
	}
//	izbrisem sve sto se tice memorijeAplikacije 
		
	@PostConstruct
	public void init() {
		bURL = servletContext.getContextPath() + "/";		
	}
	@GetMapping		
	@ResponseBody
	public String medicinski(HttpSession session) {
		List<Termin> termini = terminService.findAll();
	
		
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
		
		


		

		
		for(int i = 0; i< termini.size(); i++) {
			Termin trenutniTermin = termini.get(i);
			String jmbgg = trenutniTermin.getJmbg();
			
			Korisnik korisnikJmbg = korisnikService.nadjiKorisnikaPoJMBG(jmbgg);
//			for(int j = 0; j< korisnici.size(); j++){
//				
			
			retVal.append("<tr>"
			        + "<td>" + korisnikJmbg.getIme()+ "</td>" 
			        + "<td>" + korisnikJmbg.getPrezime()+ "</td>" 
					        + "<td>" + termini.get(i).getJmbg()+ "</td>" 
					        + "<td>" + termini.get(i).getVreme().withSecond(0).withNano(0).withMinute(0)+ "</td>"
							+ "<td>" + termini.get(i).getVakcina()+ "</td>" +
							"				<td>" + 
							"					<form method=\"post\" action=\"medicinar/ukloni\">\r\n" + 
							"						<input type=\"hidden\" name=\"jmbg\" value=\""+termini.get(i).getJmbg()+ "\">\r\n" + 
							"						<input type=\"submit\" value=\"Daj vakcinu\"></td>\r\n" + 
							"					</form>\r\n" +
							"				</td>" +
							"			</tr>\r\n");
		}

		retVal.append(
				"		</table>\r\n");
		retVal.append(
				"	<ul>\r\n" + 
				"		<li><a href=\"medicinar/logout\">logout</a></li>\r\n" + 
				"	</ul>\r\n");
		
		retVal.append(
				"		</table>\r\n");
		retVal.append(
				"</body>\r\n"+
				"</html>\r\n");		
		return retVal.toString();
		
		
		
	}
	@PostMapping(value="/ukloni")
	public void ukloniTermin(@RequestParam String jmbg, HttpServletResponse response) throws IOException {
		Map<Long, Termin> deleted = terminService.removeAll(jmbg);
		response.sendRedirect(bURL + "medicinar");
       
	}
	
	@GetMapping(value="/logout")
	@ResponseBody
	public void logout(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {	
		request.getSession().removeAttribute(KorisnikController.ULOGOVANI_KORISNIK_KEY);
		request.getSession().invalidate();
		response.sendRedirect(bURL);
	}

	
}
