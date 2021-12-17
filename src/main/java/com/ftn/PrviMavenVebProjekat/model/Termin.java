package com.ftn.PrviMavenVebProjekat.model;

import java.time.LocalDateTime;
import java.util.Date;

public class Termin {
	private Long id;
	private LocalDateTime vreme;
	private String vakcina;
	
	public Termin() {}
	
	public Termin(Long id, String vreme, String vakcina) {
		super();
		this.id = id;
		this.vreme = LocalDateTime.parse(vreme);
		this.vakcina = vakcina;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return this.id + " " + this.vreme + " (" + this.vakcina+ ")";
	}

	public void remove(Long id2) {
		// TODO Auto-generated method stub
		
	}

	
	

}
