package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;
import java.util.Map;

import com.ftn.PrviMavenVebProjekat.model.Termin;


public interface TerminService {
	List<Termin> findAll();
	List<Termin> terminiPoJmbg(String jmbg);
	Termin delete(Long id);
	Map<Long, Termin> removeAll(String element);
	Map<Long, Termin> save(Termin termin);
	

}
