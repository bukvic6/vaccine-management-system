package com.ftn.PrviMavenVebProjekat.model;

public class Korisnik {
	
	private Long id;
	private String ime;
	private String prezime;
	private String lozinka;
	private String jmbg;
	
	public Korisnik() {}

	public Korisnik(String ime, String prezime, String lozinka, String jmbg) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.lozinka = lozinka;
		this.jmbg = jmbg;
	}
	
	public Korisnik(Long id, String ime, String prezime, String lozinka, String jmbg) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.lozinka = lozinka;
		this.jmbg = jmbg;
	}
	
	public Long getId() { return id; }

	public void setId(Long id) { this.id = id; }

	public String getIme() { return ime; }

	public void setIme(String ime) { this.ime = ime; }

	public String getPrezime() { return prezime; }

	public void setPrezime(String prezime) { this.prezime = prezime; }

	public String getLozinka() { return lozinka; }

	public void setLozinka(String lozinka) { this.lozinka = lozinka; }
	
	

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	@Override
	public String toString() {
		return this.ime + " " + this.prezime + " (" + this.lozinka + ")";
	}

	
	
}
