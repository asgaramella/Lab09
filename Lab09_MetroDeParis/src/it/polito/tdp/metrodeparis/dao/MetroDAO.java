package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.FermataConLinea;
import it.polito.tdp.metrodeparis.model.FermataPairs;
import it.polito.tdp.metrodeparis.model.Linea;

public class MetroDAO {
	private Map<Integer,Fermata> mappaFermate=new HashMap<Integer,Fermata>();
	private Map<String, FermataConLinea> mappaFermatine=new HashMap<String,FermataConLinea>();
	
	public Map<Integer,Fermata> getMappaFermate(){
		return mappaFermate;
	}
	
	public Map<String,FermataConLinea> getMappaFermatine(){
		return mappaFermatine;
	}
	
	

	public List<Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
				mappaFermate.put(f.getIdFermata(), f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}

	public List<FermataConLinea> getAllFermateconLinee() {

		final String sql = "SELECT DISTINCT fermata.id_fermata, fermata.nome, fermata.coordX, fermata.coordY, connessione.id_linea "+
							"FROM fermata, connessione "+
							"WHERE fermata.id_fermata=connessione.id_stazP";
		List<FermataConLinea> fermate = new ArrayList<FermataConLinea>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				FermataConLinea f = new FermataConLinea(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")), rs.getInt("id_linea"));
				fermate.add(f);
				String key=Integer.toString(f.getIdFermata())+ Integer.toString(f.getIdLinea());
				mappaFermatine.put(key, f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}

	
	
	public Map<Integer,Linea> getAllLinee() {
		final String sql = "SELECT id_linea, nome, velocita, intervallo, colore FROM linea";
		Map<Integer,Linea> linee=new HashMap<Integer,Linea>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Linea l= new Linea(rs.getInt("id_linea"), rs.getString("nome"), rs.getDouble("velocita"), rs.getDouble("intervallo"), rs.getString("colore"));
				linee.put(l.getIdLinea(), l);
				
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return linee;
		
		
	}
	

	public List<FermataPairs> getFermateCollegate(){
		final String sql = "SELECT id_linea, id_stazP, id_stazA FROM connessione";
		List<FermataPairs> collegate = new ArrayList<FermataPairs>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			
				while (rs.next()) {
				FermataPairs fp= new FermataPairs(rs.getInt("id_linea"), rs.getInt("id_stazP"), rs.getInt("id_stazA"));
				collegate.add(fp);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return collegate;
		
		
	}
}
