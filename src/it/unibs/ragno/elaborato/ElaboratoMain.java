package it.unibs.ragno.elaborato;

import it.unibs.ragno.File.PrintData;
import it.unibs.ragno.File.ReadData;

public class ElaboratoMain {

	
	private static PrintData p1; //Classe per stampare in modo più veloce il file .txt
	
	
	//-------------------------------------------------------------------------
	
	public static void main(String[] args) {
		
	 p1= new PrintData(31, "Trovato Eleonora", "Ragnoli Michele");	
	 NodoModel nModel= new NodoModel("tsp.txt");
	// MMKPModel model = new MMKPModel("mmkp.lp",10);
	 
	 //model.buildModel();
	// model.solve();
		nModel.buildModel();
		nModel.solve();
		
		
	}
		
		
	
}
