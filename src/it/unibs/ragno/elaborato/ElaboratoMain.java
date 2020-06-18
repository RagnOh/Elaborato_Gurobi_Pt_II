package it.unibs.ragno.elaborato;

import gurobi.GRB.DoubleAttr;
import it.unibs.ragno.File.PrintData;
import it.unibs.ragno.File.ReadData;

public class ElaboratoMain {

	
	private static PrintData p1; //Classe per stampare in modo più veloce il file .txt
	
	
	//-------------------------------------------------------------------------
	
	public static void main(String[] args) {
		
	 p1= new PrintData(31, "Trovato Eleonora", "Ragnoli Michele");	
	 
	 //Primo Quesito
	MMKPModel model = new MMKPModel("mmkp.lp",120);
 
	 model.buildModel();
	 model.solve();
	 p1.setValFObb(model.getSol());
	
	 
	  //Secondo quesito
	  NodoModel nModel= new NodoModel("tsp.txt");
	  nModel.buildModel();
	  nModel.solve();
		
	  p1.setValFObbClean(nModel.getSolOttima());
		
		
	  System.out.println(" ");
		
	  //Terzo quesito
	  nModel.quesito3();
			
	  p1.setValFObbMTZ(nModel.getSolOttima2());
	  
	  
	  
	  p1.setTempo((Math.floor(10000*nModel.getTempo()))/10000);
		
	  p1.stampaDati();
		
		
	}
		
		
	
}
