package net.infobosccoma.mp08.programame.model;

import android.graphics.Bitmap;

public class Usuari {
	
	private String nom, cognoms;
	private Bitmap fotografia;
	
	public Usuari(String nom, String cognoms, Bitmap fotografia) {
		super();
		this.nom = nom;
		this.cognoms = cognoms;
		this.fotografia = fotografia;
	}
	
	public Usuari(){
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCognoms() {
		return cognoms;
	}

	public void setCognoms(String cognoms) {
		this.cognoms = cognoms;
	}

	public Bitmap getFotografia() {
		return fotografia;
	}

	public void setFotografia(Bitmap fotografia) {
		this.fotografia = fotografia;
	}
	
	
	
}
