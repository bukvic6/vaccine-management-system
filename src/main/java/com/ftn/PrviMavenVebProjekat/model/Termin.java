package com.ftn.PrviMavenVebProjekat.model;

import java.time.LocalDateTime;
import java.util.Date;

public class Termin {
	private String jmbg;
	private LocalDateTime vreme;
	private String vakcina;
	
	public Termin() {}
	
	public Termin(String jmbg, String vreme, String vakcina) {
		super();
		this.jmbg = jmbg;
		this.vreme = LocalDateTime.parse(vreme);
		this.vakcina = vakcina;
	}

	public Termin(String vreme, String vakcina) {
		super();
		this.vreme = LocalDateTime.parse(vreme);
		this.vakcina = vakcina;

	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public LocalDateTime getVreme() {
		return vreme;
	}

	public void setVreme(LocalDateTime vreme) {
		this.vreme = vreme;
	}

	public String getVakcina() {
		return vakcina;
	}

	public void setVakcina(String vakcina) {
		this.vakcina = vakcina;
	}
	
	@Override
	public String toString() {
		return this.jmbg + " " + this.vreme + " (" + this.vakcina+ ")";
	}

	
	

}
