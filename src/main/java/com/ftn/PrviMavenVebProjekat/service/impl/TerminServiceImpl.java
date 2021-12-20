package com.ftn.PrviMavenVebProjekat.service.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.model.Termin;
import com.ftn.PrviMavenVebProjekat.service.TerminService;

@Service
@Qualifier("fajloviTermin")
public class TerminServiceImpl implements TerminService{
	
	@Value("${termini.pathToFile}")
	private String pathToFile;
	
	private Map<Long, Termin> citajIzFajla(){
		Map<Long, Termin> termini = new HashMap<>();
		Long nextId = 1L;
		try {
			Path path = Paths.get(pathToFile);
			System.out.println(path.toFile().getAbsolutePath());
			List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));
			

			for (String line : lines) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				String[] tokens = line.split(";");
				Long id = Long.parseLong(tokens[0]);
				String jmbg = tokens[1];
				String vreme = tokens[2];
				String vakcina = tokens[3];
				

				termini.put(id, new Termin(id,jmbg, vreme, vakcina));
				if(nextId<id)
					nextId=id;

			} 
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return termini;
	}
	private Map<Long, Termin> snimiUFajl(Map<Long, Termin> termini){
		Map<Long, Termin> povratna = new HashMap<>();
		try {
			Path path = Paths.get(pathToFile);
			System.out.println(path.toFile().getAbsolutePath());
			List<String> lines = new ArrayList<>();
			
			for(Termin termin : termini.values()) {
				lines.add(termin.toString());
				povratna.put(termin.getId(), termin);
			}
			Files.write(path, lines, Charset.forName("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	return povratna;
	}
	
	
	@Override
	public List<Termin> findAll() {
		Map<Long,Termin>termini = citajIzFajla();
		return new ArrayList<>(termini.values());

	}
	
	@Override
	public List<Termin> terminiPoJmbg(String jmbg) {
		Map<Long, Termin> termini = citajIzFajla();
		ArrayList<Termin> ret = new ArrayList<>();
		for (Termin t: termini.values()) {
			if(t.getJmbg().equals(jmbg)){
				ret.add(t);
			}
		}
		return ret;
	}
	

    private Long nextId(Map<Long, Termin> termini) {
    	Long nextId = 1L;
    	for (Long id : termini.keySet()) {
    		if(nextId<id)
				nextId=id;
		}
    	return ++nextId;
    }
	
    
    @Override
	public Termin save(Termin termin){
		Map<Long, Termin>termini = citajIzFajla();
		Long nextId = nextId(termini);
		if (termin.getId() == null) {
			termin.setId(nextId++);
		}
		termin.setVreme(LocalDateTime.now());
		termini.put(termin.getId(), termin);
		snimiUFajl(termini);

		return termin;
	}
	
    @Override
	public Termin delete(Long id) {
		Map<Long, Termin> termini = citajIzFajla();
		if (!termini.containsKey(id)) {
			throw new IllegalArgumentException("Pokusavate da izbrisete termin koji ne postoji");
		}
		Termin termin = termini.get(id);
		if (termin != null) {
			termini.remove(id);
		}
		snimiUFajl(termini);
		return termin;
		
	}
		
	
	
	
	

}
