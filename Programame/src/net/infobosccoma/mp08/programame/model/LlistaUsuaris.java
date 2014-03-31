package net.infobosccoma.mp08.programame.model;

import java.util.ArrayList;

public class LlistaUsuaris {
	
	private static ArrayList<Usuari> usuaris;

	public static ArrayList<Usuari> getUsuaris() {
		if(usuaris == null){
			usuaris = new ArrayList<Usuari>();
		}
		return usuaris;
	}

	public static void setUsuaris(ArrayList<Usuari> usuaris) {
		LlistaUsuaris.usuaris = usuaris;
	}

}
