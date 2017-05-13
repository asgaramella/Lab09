package it.polito.tdp.metrodeparis.model;

import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

public class Fermata {

	private int idFermata;
	private String nome;
	private LatLng coords;
	private List<FermataConLinea> figlie;

	public Fermata(int idFermata, String nome, LatLng coords) {
		super();
		this.idFermata = idFermata;
		this.nome = nome;
		this.coords = coords;
		figlie=new ArrayList<FermataConLinea>();
	}

	public Fermata(int idFermata) {
		this.idFermata = idFermata;
	}

	public int getIdFermata() {
		return idFermata;
	}

	public void setIdFermata(int idFermata) {
		this.idFermata = idFermata;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LatLng getCoords() {
		return coords;
	}

	public void setCoords(LatLng coords) {
		this.coords = coords;
	}

	public void setFigliaIn(FermataConLinea f){
		this.figlie.add(f);
	}
	
	public List<FermataConLinea> getFiglie(){
		return this.figlie;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idFermata;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fermata other = (Fermata) obj;
		if (idFermata != other.idFermata)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}
}
