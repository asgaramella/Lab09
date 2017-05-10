package it.polito.tdp.metrodeparis.model;

public class FermataPairs {
	
	private int idLinea;
	private int idStazP;
	private int idStazA;
	
	public FermataPairs(int idLinea, int idStazP, int idStazA) {
		super();
		this.idLinea = idLinea;
		this.idStazP = idStazP;
		this.idStazA = idStazA;
	}

	public int getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(int idLinea) {
		this.idLinea = idLinea;
	}

	public int getIdStazP() {
		return idStazP;
	}

	public void setIdStazP(int idStazP) {
		this.idStazP = idStazP;
	}

	public int getIdStazA() {
		return idStazA;
	}

	public void setIdStazA(int idStazA) {
		this.idStazA = idStazA;
	}
	
	


}
