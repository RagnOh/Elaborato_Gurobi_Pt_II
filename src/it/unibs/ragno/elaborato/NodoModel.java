package it.unibs.ragno.elaborato;

import java.util.ArrayList;

import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;
import gurobi.GRB.DoubleAttr;
import gurobi.GRB.StringAttr;
import it.unibs.ragno.File.ReadData;

public class NodoModel {

	private String file; 
	private ReadData read;
	private GRBEnv env;
	private GRBModel model;
	private GRBVar [] [] xvars;
	
	private GRBLinExpr expr;
	
	private ArrayList<Integer>colLenght= new ArrayList<Integer>();
	private ArrayList<Integer>colLenght2= new ArrayList<Integer>();
	
	private double solOttima;
	private double solOttima2;
	private double tempo;
	
	private int [][]array2;
	
	public NodoModel(String file) {
		
		this.file=file;
	
		read=new ReadData(file);
		read.leggiFile();
		
		xvars=new GRBVar[100][100];
		array2=new int[100][100];
	}
	
	public NodoModel(GRBVar[][] vars) {
		
		xvars= vars;
	}
	
	
     public void buildModel() {
		
		try
		{
			
			env= new GRBEnv();
			env.set(GRB.IntParam.Threads,8);
			env.set(GRB.IntParam.Presolve,0);
			//env.set(IntParam.Cuts,0);
			
			
			model = new GRBModel(env);
			
			addVariables();
			//System.out.println("lunghezza= "+xvars.length+" "+xvars[1].length);
			model.set(GRB.IntAttr.ModelSense,GRB.MINIMIZE);
			addConstraint();
			addConstraint2();
			
			
				
			
			
		}catch(GRBException e) {
			
			e.printStackTrace();
		}
		
	}
	
     //-------------------------------------------------------------------------------------------------------------------------------------------------------
     
     //Creo un array multidimensionale esteso in cui metto i vertici entranti e  quelli uscenti
     private void addVariables() throws GRBException{
    	 
    	 
    	 for (int j=0;j<47;j++) {
    	 
    		 int n=0;
    		 for( int i=0;i<read.nodi.size();i++) { //cerco tra tutt
    		 
    		 //vertici uscenti
    		if(read.nodi.get(i).getPrev()==j) {
    		 xvars[j][n]=model.addVar(0, 1, read.nodi.get(i).getCosto(), GRB.BINARY, "x_"+read.nodi.get(i).getPrev()+"_"+read.nodi.get(i).getNext());
    		
    		 array2[j][read.nodi.get(i).getNext()]= read.nodi.get(i).getCosto(); //array di appoggio per i sottocicli
    	      n++;
    		}
    	 }
    		 //System.out.println(n);
    		 colLenght.add(n);
    	 }
    	 
    	 
    	 for (int j=0;j<47;j++) {
        	 
    		 int n=0;
    		 for( int i=0;i<read.nodi.size();i++) {
    		 
    		 //vertici entranti
    		if(read.nodi.get(i).getPrev()==j) {
    		 xvars[j+47][n]=model.addVar(0, 1, read.nodi.get(i).getCosto(), GRB.BINARY, "x_"+read.nodi.get(i).getNext()+"_"+read.nodi.get(i).getPrev());
    		
    	      n++;
    		}
    	 }
    		 //System.out.println(n);
    		 colLenght2.add(n);
    	 }
    	 
    	 
    	 
    	 model.update();
    	 
     }
	

     //---------------------------------------------------------------------------------------------------------------------------------
     
   //vincolo in cui faccio la sommatoria di tutti gli Xij con i fisso;
     private void addConstraint() throws GRBException{
    	 
    	 for(int j=0;j<47;j++) {                   //aggiungo archi in uscita
    	 
    		 expr=new GRBLinExpr();
    		 
    		 for(int i=0;i<colLenght.get(j);i++){
    		 
    		 expr.addTerm(1, xvars[j][i]);
    		 
    	 }
    		
    		 
    		 for(int d=47;d<94;d++) {     //aggiungo archi in ingresso
    			 
    			 
    				 for(int g=0;g<colLenght2.get(d-47);g++)
    				 {
    				
    					//Analizzo il nome della variabile per poter trovare tutte le variabili con j uguale
    					      String nome=xvars[d][g].get(StringAttr.VarName);
    					      nome=nome.substring(2, 5);
    					      
    					      if(nome.substring(2, 3).equalsIgnoreCase("_")) {
    					    	  
    					    	  int jk=Integer.parseInt(nome.substring(0, 2));
    					    	  if( xvars[d][g]!=null && jk==j ) {
    		    						 expr.addTerm(1, xvars[d][g]);
    		    				//	 System.out.println("trovato "+d+" "+ g+" per= "+j);
    		    						 }
    					    	  
    					    	  continue;
    					      }
    					      
    					      if(nome.substring(1, 2).equalsIgnoreCase("_")) {
    					      int t=Integer.parseInt(nome.substring(0, 1));
    					 
    						 if( xvars[d][g]!=null && t==j ) {
    						 expr.addTerm(1, xvars[d][g]);
    					// System.out.println("trovato "+d+" "+ g+" per= "+j);
    						 }
    					 
    				 }
    				 }
    				 
    			 
    			 
    		 }
    		 
    		 model.addConstr(expr, GRB.EQUAL, 1, "c_"+j);
    	 
    	 }
     }
     
     //---------------------------------------------------------------------------------------------------------------------------------------------
     
     //vincolo in cui faccio la sommatoria di tutti gli Xij con j fisso;
      private void addConstraint2() throws GRBException{
    	 
    	 for(int j=47;j<94;j++) {                         //aggiungo archi in uscita
    	 
    		 expr=new GRBLinExpr();
    		 
    		 for(int i=0;i<colLenght2.get(j-47);i++){
    		 
    			 if(xvars[j][i]!=null)
    		 expr.addTerm(1, xvars[j][i]);
    		 
    	 }
    		 
    		 for(int d=47;d<94;d++) {
    			 
    			 
				 for(int g=0;g<colLenght2.get(d-47);g++)  //aggiungo archi in ingresso
				 {
				
					 //Analizzo il nome della variabile per poter trovare tutte le variabili con j uguale
					      String nome=xvars[d][g].get(StringAttr.VarName);
					      nome=nome.substring(2, 5);
					      
					      if(nome.substring(2, 3).equalsIgnoreCase("_")) {
					    	  
					    	  int jk=Integer.parseInt(nome.substring(0, 2));
					    	  if( xvars[d][g]!=null && jk==(j-47) ) {
		    						 expr.addTerm(1, xvars[d][g]);
		    					// System.out.println("2_trovato "+d+" "+ g+" per= "+(j-47));
		    						 }
					    	  
					    	  continue;
					      }
					      
					      if(nome.substring(1, 2).equalsIgnoreCase("_")) {
					      int t=Integer.parseInt(nome.substring(0, 1));
					 
						 if( xvars[d][g]!=null && t==(j-47) ) {
						 expr.addTerm(1, xvars[d-47][g]);
					 //System.out.println("2_trovato "+(d-47)+" "+ g+" per= "+(j-47));
						 }
					 
				 }
				 }
				 
			 
			 
		 }
    		 model.addConstr(expr, GRB.EQUAL, 1, "z_"+j);
    	 
    	 }
     }
	
      //----------------------------------------------------------------------------------------------------------------------------------------------
      
	public void solve()
	{
		try {
			//model.setCallback(new NodoModel(xvars));
			//trovaSottoCiclo(array2, 0);
			model.optimize();
	
			solOttima=model.get(DoubleAttr.ObjVal);
		}catch(GRBException e) {
			e.printStackTrace();
		}
		}
	

//---------------------------------------------------------------------------------------------------------------------------------------------------------
	
	//Risolvo il terzo quesito
	public void quesito3() {
		
		try {
		GRBVar[] u= new GRBVar[48];
		
		for(int i=0;i<48;i++) { //aggiungo variabili riguardo il terzo quesito
			
			u[i] = model.addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS, "u"+i);
		}
		
		expr = new GRBLinExpr();
		expr.addTerm(1.0, u[0]);
		model.addConstr(expr, GRB.EQUAL , 1.0, "PrimoVincoloMTZ");
		
		for(int i=1;i<48;i++) { //aggiungo i vincoli del terzo quesito
			
			expr = new GRBLinExpr();
			expr.addTerm(1.0, u[i]);
			model.addConstr(expr, GRB.GREATER_EQUAL, 2.0, "vincolo2.1_"+i);
			model.addConstr(expr, GRB.LESS_EQUAL, 48.0, "vincolo2.2_"+i);
			
		}
		
		
		for(int i=1; i<48;i++) {
			for(int j=1;j<48;j++) {
				
				if(i!=j && xvars[i][j]!=null && xvars[j][i]!=null) {
					
					expr.addTerm(1.0, u[j]);
					expr.addTerm(-1.0, u[i]);
					expr.addTerm(-47.0, xvars[i][j]);
					model.addConstr(expr, GRB.GREATER_EQUAL, -46.0, "Vincolo3_"+i+" "+j);
				}
			}
			
		}
		
		model.optimize();
		
		solOttima2=model.get(DoubleAttr.ObjVal);
	    tempo = model.get(DoubleAttr.Runtime);
	   
		model.dispose();
		env.dispose();
		
		}catch(GRBException e) {
			
			e.printStackTrace();
		}
		
	}



	//funzione per trovare i sottocicli nel secondo quesito ma non funzionante
	
	public ArrayList<Integer> trovaSottoCiclo(int [][] grafo, int primoNodo)throws GRBException{
		
		ArrayList<Integer>percorso = new ArrayList<>();
		
		int iterazioni=0;
		int i,j = primoNodo;
		percorso.add(primoNodo);
		int count=0;
		int[] source = new int[2];
		
		do {
			i=j;
			j=0;
			
			while(grafo[i][j] == 0 && j<grafo[0].length-1) {
				
				j++;
			}
			
			percorso.add(j);
			count++;
			if(count ==1 ) {
				
				source[0] = i;
				source[1] = j;
			}
			iterazioni= iterazioni +1;
		}while ((i!= source[0] && j!=source[1])|| count <=1 ||  iterazioni>48);
		
		
		if(!percorso.isEmpty()) {
			
			System.out.println("ciclo:");
			for(int k=0;k<percorso.size();k++) {
				
				System.out.println(percorso.get(k));
			}
		}
		
		return percorso;
	}
	
	
	public double getSolOttima() {
		
		return solOttima;
	}
	
    public double getSolOttima2() {
		
		return solOttima2;
	}
	
    public double getTempo() {
    	
    	return tempo;
    }
}
