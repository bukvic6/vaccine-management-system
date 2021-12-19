package com.ftn.PrviMavenVebProjekat.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ftn.PrviMavenVebProjekat.bean.SecondConfiguration.ApplicationMemory;
import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.Termin;
import com.ftn.PrviMavenVebProjekat.model.Termini;

@Controller
@RequestMapping(value="/medicinar")
public class MedicinarController implements ApplicationContextAware{
	
	public static final String TERMINI_KEY = "medicinar";
	

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
	@PostConstruct
	public void init() {
		bURL = servletContext.getContextPath() + "/";
		memorijaAplikacije = applicationContext.getBean(ApplicationMemory.class);
		
		Termini termini = new Termini();
		
		memorijaAplikacije.put(TerminiController.TERMINI_KEY, termini);
	}
	@GetMapping		
	@ResponseBody
	public String medicinski() {
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
		
		
		List<Termin> listaTermina = termini.findAll();
		
		for(int i = 0; i< listaTermina.size(); i++) {
			retVal.append("<tr>"
									+ "<td>" + listaTermina.get(i).getId()+ "</td>" 
					+ "<td>" + listaTermina.get(i).getVreme()+ "</td>"
							+ "<td>" + listaTermina.get(i).getVakcina()+ "</td>" +
							"				<td>" + 
							"					<form method=\"post\" action=\"termini/ukloni\">\r\n" + 
							"						<input type=\"hidden\" name=\"id\" value=\""+listaTermina.get(i).getId()+ "\">\r\n" + 
							"						<input type=\"submit\" value=\"Daj vakcinu\"></td>\r\n" + 
							"					</form>\r\n" +
							"				</td>" +
							"			</tr>\r\n");
		}
		retVal.append(
				"		</table>\r\n");
		retVal.append(
				"</body>\r\n"+
				"</html>\r\n");		
		return retVal.toString();
		
		
		
	}
	
	
}
