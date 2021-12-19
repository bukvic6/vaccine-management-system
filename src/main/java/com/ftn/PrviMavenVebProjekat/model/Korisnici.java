package com.ftn.PrviMavenVebProjekat.model;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Korisnici {
	
	private Map<Long, Korisnik> korisnici = new HashMap<>();
	private long nextId = 1L;

	
	public Korisnici() {
		try {
			Path path = Paths.get(getClass().getClassLoader().getResource("korisnici.txt").toURI());
			System.out.println(path.toFile().getAbsolutePath());
			List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));

			for (String line : lines) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				String[] tokens = line.split(";");
				Long id = Long.parseLong(tokens[0]);
				String jmbg = tokens[1];
				String ime = tokens[2];
				String prezime = tokens[3];
				String lozinka = tokens[4];
				TipKorisnika tipKorisnika =TipKorisnika.valueOf(tokens[5]);

				korisnici.put(Long.parseLong(tokens[0]), new Korisnik(id, jmbg, ime, prezime, lozinka, tipKorisnika));
				if(nextId<id)
					nextId=id;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** vraca termin u odnosu na zadati jmbg */
	public Korisnik findOne(Long id) {
		return korisnici.get(id);
	}

	public List<Korisnik> findAll() {
		return new ArrayList<Korisnik>(korisnici.values());
	}
	public Korisnik nadjiKorisnikaPoJMBG(String jmbg) {
		Korisnik found = null;
		for (Korisnik k : findAll()) {
			if(k.getJmbg().equals(jmbg)) {
				found = k;
				break;
			}
		}
		return found;
		
	}
}
