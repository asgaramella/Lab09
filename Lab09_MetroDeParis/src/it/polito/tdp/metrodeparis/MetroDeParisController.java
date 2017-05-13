package it.polito.tdp.metrodeparis;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MetroDeParisController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Fermata> comboPartenza;

    @FXML
    private ComboBox<Fermata> comboArrivo;

    @FXML
    private Button btnPercorso;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	Fermata partenza=comboPartenza.getValue();
    	Fermata destinazione=comboArrivo.getValue();
    	if(partenza!=null && destinazione!=null){
    		if(!partenza.equals(destinazione)){
    			model.creaGrafo();
    	
    			double tempo=model.pesoCamminoMinimo(partenza, destinazione)/60;
    	
    			txtResult.appendText(model.camminoMinimo());
    	
    	
    			txtResult.appendText("Tempo di percorrenza stimato in minuti: "+Double.toString(tempo)+"\n" );
    			}
    		else
    			txtResult.appendText("Inserire stazione di partenza diversa da quella di destinazione!");
    	}
    	else
    		txtResult.appendText("Inserire una stazione di partenza e di destinazione! ");
    	
    }

    @FXML
    void initialize() {
        assert comboPartenza != null : "fx:id=\"comboPartenza\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert comboArrivo != null : "fx:id=\"comboArrivo\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MetroDeParis.fxml'.";

    }

	public void setModel(Model model) {
		
		this.model=model;
		comboArrivo.getItems().addAll(model.getFermate());
		comboPartenza.getItems().addAll(model.getFermate());
	}
}
