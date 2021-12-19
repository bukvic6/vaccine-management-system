package com.ftn.PrviMavenVebProjekat.model;

public class Korisnik {
	private Long id;
	private String jmbg;
	private String ime;
	private String prezime;
	private String lozinka;
	private TipKorisnika tipKorisnika;

	
	public Korisnik() {}
	public Korisnik(Long id, String jmbg, String ime, String prezime, String lozinka, TipKorisnika tipKorisnika) {
		super();
		this.id = id;
		this.jmbg = jmbg;
		this.ime = ime;
		this.prezime = prezime;
		this.lozinka = lozinka;
		this.tipKorisnika = tipKorisnika;

	}

	public Korisnik( String jmbg, String ime, String prezime, String lozinka) {
		super();
		this.jmbg = jmbg;
		this.ime = ime;
		this.prezime = prezime;
		this.lozinka = lozinka;

	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
		return this.id + ";" +this.jmbg+ ";" +  this.ime+ ";" + this.prezime + ";" + this.lozinka;
	}
	public TipKorisnika getTipKorisnika() {
		return tipKorisnika;
	}
	public void setTipKorisnika(TipKorisnika tipKorisnika) {
		this.tipKorisnika = tipKorisnika;
	}



	
	
}
