package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Korisnik;

public interface KorisnikService {

	List<Korisnik> findAll();

	Korisnik nadjiKorisnikaPoJMBG(String jmbg);

	Korisnik findOne(String jmbg, String lozinka);

}
