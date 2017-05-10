package it.polito.tdp.metrodeparis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.dao.MetroDAO;
import org.jgrapht.alg.DijkstraShortestPath;

public class Model {

private WeightedGraph<Fermata,DefaultWeightedEdge> graph;
private List<Fermata> fermate;
private Map<Integer,Fermata> mappaFermate;
private Map<Integer,Linea> linee;
private DijkstraShortestPath<Fermata,DefaultWeightedEdge> dijkstra;

	public Model(){
	}
	
	public List<Fermata> getFermate(){
		if(fermate==null){
			MetroDAO dao=new MetroDAO();
			fermate=dao.getAllFermate();
			mappaFermate=dao.getMappaFermate();
		}
		return fermate;
	}
	
	
	
	
	public void creaGrafo(){
	
			this.graph=new WeightedMultigraph<Fermata,DefaultWeightedEdge>(DefaultWeightedEdge.class);
			MetroDAO dao=new MetroDAO();
			//popola mappa linee
			linee=dao.getAllLinee();
			
			Graphs.addAllVertices(graph, this.getFermate());
			
			for(FermataPairs fp: dao.getFermateCollegate()){
				Fermata stazP= mappaFermate.get(fp.getIdStazP());
				Fermata stazA=mappaFermate.get(fp.getIdStazA());
				Linea l=linee.get(fp.getIdLinea());
				
				double distanza= LatLngTool.distance(stazP.getCoords(), stazA.getCoords(), LengthUnit.KILOMETER);
				double peso=distanza/l.getVelocita();
				
				DefaultWeightedEdge e = graph.addEdge(stazA,stazP);
				graph.setEdgeWeight(e, peso);
				
				//jvbjv
			}
			
	}
	
	
	public double pesoCamminoMinimo(Fermata partenza, Fermata destinazione){
		dijkstra=new DijkstraShortestPath<Fermata,DefaultWeightedEdge>(graph, partenza, destinazione);
		
		return dijkstra.getPathLength()*3600+(dijkstra.getPathEdgeList().size())*30;
	}
	
	public List<Fermata> camminoMinimo(Fermata partenza, Fermata destinazione){
		dijkstra=new DijkstraShortestPath<Fermata,DefaultWeightedEdge>(graph, partenza, destinazione);
		
		List<Fermata> ltemp = new ArrayList<Fermata>();
		
		for(DefaultWeightedEdge e :dijkstra.getPathEdgeList()){
			Fermata f1=graph.getEdgeSource(e);
			Fermata f2=graph.getEdgeTarget(e);
			if(!ltemp.contains(f1)){
				ltemp.add(f1);
			}
			else
				ltemp.add(f2);
			
		}
		
		return ltemp;
	}
	
	
			
		
	}



