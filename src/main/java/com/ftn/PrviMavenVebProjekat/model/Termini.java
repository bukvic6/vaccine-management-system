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
				String jmbg = tokens[1];
				String vreme = tokens[2];
				String vakcina = tokens[3];
				

				termini.put(Long.parseLong(tokens[0]), new Termin(jmbg, vreme, vakcina));
				if(nextId<id)
					nextId=id;

			} 
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<Termin> findOne(String jmbg) {
		ArrayList<Termin> ret = new ArrayList<>();
		for (Termin t: termini.values()) {
			if(t.getJmbg().equals(jmbg)){
				ret.add(t);
			}
		}
		return ret;
		}
	
	public Termin findOned(String jmbg) {
		return termini.get(jmbg);
	}

	/** VRACA TERMINE */
	public List<Termin> findAll() {
		return new ArrayList<Termin>(termini.values());

	}
//	public List<Termin> findAll(String jmbg) {
//		List<Termin> matching = new ArrayList<>();
//		for(Long id: jmbgs) {
//			matching.add(extitiesMap.get(id));
//
//		}
//		
//		
//		return new ArrayList<Termin>(termini.values());
//
//	}
	
//	public Termin save(Termin termin) {
//		if (termin.getId() == null) {
//			termin.setId(++nextId);
//		}
//		termini.put(termin.getId(), termin);
//		return termin;
	
//	public Termin delete(Long idtermin) {
//		if (!termini.containsKey(idtermin)) {
//			throw new IllegalArgumentException("tried to remove non existing book");
//		}
//		Termin termin = termini.get(idtermin);
//		if (termin != null) {
//			termin.remove(idtermin);
//		}
//		return termin;
//		
//	}

	public Termin save(Termin termin) {
		// TODO Auto-generated method stub
		return null;
	}
	

	
}
		


