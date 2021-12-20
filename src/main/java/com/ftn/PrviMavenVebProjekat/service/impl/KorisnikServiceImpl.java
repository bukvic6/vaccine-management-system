package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.TipKorisnika;
import com.ftn.PrviMavenVebProjekat.service.KorisnikService;

@Service
@Qualifier("fajloviKorisnik")
public class KorisnikServiceImpl implements KorisnikService {
	

	@Value("${korisnici.pathToFile}")
	private String pathToFile;
	
	private Map<Long, Korisnik> citajIzFajla(){
	Map<Long, Korisnik> korisnici = new HashMap<>();
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
			String ime = tokens[2];
			String prezime = tokens[3];
			String lozinka = tokens[4];
			TipKorisnika tipKorisnika =TipKorisnika.valueOf(tokens[5]);

			korisnici.put(Long.parseLong(tokens[0]), new Korisnik(id, jmbg, ime, prezime, lozinka, tipKorisnika));
			if(nextId<id)
				nextId=id;
			Files.write(path, lines, Charset.forName("UTF-8"));
		} 
	}catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return korisnici;
    }
	
	@Override
	public List<Korisnik> findAll() {
		Map<Long, Korisnik> korisnici = citajIzFajla();
		return new ArrayList<Korisnik>(korisnici.values());
	}
	@Override
	public Korisnik findOne(String jmbg, String sifra) {
		Map<Long, Korisnik> korisnici = citajIzFajla();
		Korisnik found = null;
		for (Korisnik korisnik : korisnici.values()) {
			if (korisnik.getJmbg().equals(jmbg) && korisnik.getLozinka().equals(sifra)) {
				found = korisnik;
				break;
			}
		}
		
		return found;
	}
	@Override
	public Korisnik nadjiKorisnikaPoJMBG(String jmbg) {
		Map<Long, Korisnik> korisnici = citajIzFajla();
		Korisnik found = null;
		for (Korisnik k : korisnici.values()) {
			if(k.getJmbg().equals(jmbg)) {
				found = k;
				break;
			}
		}
		return found;
		
	}
	public Korisnik findOne(Long id) {
		Map<Long, Korisnik> korisnici = citajIzFajla();
		return korisnici.get(id);
	}
	
}
	
