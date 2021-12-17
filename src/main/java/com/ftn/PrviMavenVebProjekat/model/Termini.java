package com.ftn.PrviMavenVebProjekat.model;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Termini {
	private Map<Long, Termin> termini = new HashMap<>();
	
	private long nextId = 1L;
	
	public Termini() {

		try {
			Path path = Paths.get(getClass().getClassLoader().getResource("termini.txt").toURI());
			System.out.println(path.toFile().getAbsolutePath());
			List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));
			
			

			for (String line : lines) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				String[] tokens = line.split(";");
				Long id = Long.parseLong(tokens[0]);
				String vreme = tokens[1];
				String vakcina = tokens[2];
				

				termini.put(Long.parseLong(tokens[0]), new Termin(id, vreme, vakcina));
				if(nextId<id)
					nextId = id;
			} 
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<Termin> findOne(Long id) {
		List<Termin> ret = new ArrayList<>();
		for (Termin t: termini.values()) {
			if(t.getId().equals(id)){
				ret.add(t);
			}
		}
		return ret;
		}

    
	/** VRACA TERMINE */
	public List<Termin> findAll() {
		return new ArrayList<Termin>(termini.values());

	}
	
	public Termin save(Termin termin) {
		if (termin.getId() == null) {
			termin.setId(++nextId);
		}
		termini.put(termin.getId(), termin);
		return termin;
	}
	

	
}
		

