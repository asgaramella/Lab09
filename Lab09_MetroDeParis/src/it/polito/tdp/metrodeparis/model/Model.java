package it.polito.tdp.metrodeparis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.graph.WeightedMultigraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.dao.MetroDAO;
import org.jgrapht.alg.DijkstraShortestPath;

public class Model {

private WeightedGraph<FermataConLinea,DefaultWeightedEdge> graph;
private List<Fermata> fermate;
private List<FermataConLinea> fermatine;
private Map<Integer,Fermata> mappaFermate;
private Map<String,FermataConLinea> mappaFermatine;
private Map<Integer,Linea> linee;
private DijkstraShortestPath<FermataConLinea, DefaultWeightedEdge> dijkstra;
private FermataConLinea p=null;
private FermataConLinea d=null;

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
	
	public List<FermataConLinea> getFermateConLinea(){
		if(fermatine==null){
			MetroDAO dao=new MetroDAO();
			fermatine=dao.getAllFermateconLinee(fermate);
			mappaFermatine=dao.getMappaFermatine();

		}
		return fermatine;
	}
	
	

	
	public void creaGrafo(){
	
			this.graph=new DirectedWeightedMultigraph<FermataConLinea,DefaultWeightedEdge>(DefaultWeightedEdge.class);
			MetroDAO dao=new MetroDAO();
			//popola mappa linee
			linee=dao.getAllLinee();
		
			
			Graphs.addAllVertices(graph, this.getFermateConLinea());
			
			for(FermataPairs fp: dao.getFermateCollegate()){
				String keyP=Integer.toString(fp.getIdStazP())+Integer.toString(fp.getIdLinea());
				String keyA=Integer.toString(fp.getIdStazA())+Integer.toString(fp.getIdLinea());
				FermataConLinea stazP= mappaFermatine.get(keyP);
				FermataConLinea stazA=mappaFermatine.get(keyA);
				Linea l=linee.get(fp.getIdLinea());
				
				double distanza= LatLngTool.distance(stazP.getCoords(), stazA.getCoords(), LengthUnit.KILOMETER);
				double peso=distanza/l.getVelocita();
				
				DefaultWeightedEdge e = graph.addEdge(stazA,stazP);
				DefaultWeightedEdge e2 = graph.addEdge(stazP,stazA);
				graph.setEdgeWeight(e, peso);
				graph.setEdgeWeight(e2, peso);
			}
			
				//aggiungo collegamenti tra la stessa stazione su linee diverse 
				for(Fermata f: fermate){
					for(FermataConLinea fclP: f.getFiglie()){
						for(FermataConLinea fclA: f.getFiglie()){
							if(!fclP.equals(fclA)){
								DefaultWeightedEdge a = graph.addEdge(fclP,fclA);
								graph.setEdgeWeight(a, (linee.get(fclA.getIdLinea()).getIntervallo())/60);
							}
								
						}
						
					}
							
						}
					}
			
			
	
	
	
	
	public double pesoCamminoMinimo(Fermata partenza, Fermata destinazione){
		double peso=Double.MAX_VALUE;
		for(FermataConLinea fclP: partenza.getFiglie()){
			for(FermataConLinea fclD:destinazione.getFiglie()){
				dijkstra=new DijkstraShortestPath<FermataConLinea,DefaultWeightedEdge>(graph, fclP, fclD);
				if(dijkstra.getPathLength()*3600+(dijkstra.getPathEdgeList().size())*30<peso){
					peso=dijkstra.getPathLength()*3600+(dijkstra.getPathEdgeList().size()-1)*30;
					this.p=fclP;
					this.d=fclD;
				}
				
			}
		}
		
		
		return peso;
	}
	
	public String camminoMinimo(){
		dijkstra=new DijkstraShortestPath<FermataConLinea,DefaultWeightedEdge>(graph, p, d);
		 String stemp="Prendo Linea "+linee.get(p.getIdLinea()).getNome()+"\n"+ p.toString()+"\n";
		
		for(DefaultWeightedEdge e :dijkstra.getPathEdgeList()){
			if(graph.getEdgeSource(e).getIdFermata()==graph.getEdgeTarget(e).getIdFermata()){
				stemp+="Cambio Linea "+linee.get(graph.getEdgeTarget(e).getIdLinea()).getNome()+"\n";
			}
			stemp+=graph.getEdgeTarget(e).toString()+"\n";
			
		}
		
		return stemp ;
	}
	
	
			
		
	}



