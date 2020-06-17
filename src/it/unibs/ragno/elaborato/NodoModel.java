package it.unibs.ragno.elaborato;

import java.util.ArrayList;

import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;
import gurobi.GRB.IntParam;
import gurobi.GRB.StringAttr;
import it.unibs.ragno.File.ReadData;
import it.unibs.ragno.Utils.Nodo;

public class NodoModel {

	private String file; 
	private ReadData read;
	private GRBEnv env;
	private GRBModel model;
	private GRBVar [] [] xvars;
	
	private GRBLinExpr expr;
	
	private ArrayList<Integer>colLenght= new ArrayList<Integer>();
	private ArrayList<Integer>colLenght2= new ArrayList<Integer>();
	
	public NodoModel(String file) {
		
		this.file=file;
	
		read=new ReadData(file);
		read.leggiFile();
		
		xvars=new GRBVar[100][100];
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
	
     
     //Creo un array multidimensionale esteso in cui metto i vertici entranti e  quelli uscenti
     private void addVariables() throws GRBException{
    	 
    	 
    	 for (int j=0;j<47;j++) {
    	 
    		 int n=0;
    		 for( int i=0;i<read.nodi.size();i++) { //cerco tra tutt
    		 
    		 //vertici uscenti
    		if(read.nodi.get(i).getPrev()==j) {
    		 xvars[j][n]=model.addVar(0, 1, read.nodi.get(i).getCosto(), GRB.BINARY, "x_"+read.nodi.get(i).getPrev()+"_"+read.nodi.get(i).getNext());
    		
    	      n++;
    		}
    	 }
    		 System.out.println(n);
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
	

     
   //vincolo in cui faccio la sommatoria di tutti gli Xij con i fisso;
     private void addConstraint() throws GRBException{
    	 
    	 for(int j=0;j<47;j++) {
    	 
    		 expr=new GRBLinExpr();
    		 
    		 for(int i=0;i<colLenght.get(j);i++){
    		 
    		 expr.addTerm(1, xvars[j][i]);
    		 
    	 }
    		//Analizzo il nome della variabile per poter trovare tutte le variabili con j uguale
    		 
    		 for(int d=47;d<94;d++) {
    			 
    			 
    				 for(int g=0;g<colLenght2.get(d-47);g++)
    				 {
    				
    					 
    					      String nome=xvars[d][g].get(StringAttr.VarName);
    					      nome=nome.substring(2, 5);
    					      
    					      if(nome.substring(2, 3).equalsIgnoreCase("_")) {
    					    	  
    					    	  int jk=Integer.parseInt(nome.substring(0, 2));
    					    	  if( xvars[d][g]!=null && jk==j ) {
    		    						 expr.addTerm(1, xvars[d][g]);
    		    					 System.out.println("trovato "+d+" "+ g+" per= "+j);
    		    						 }
    					    	  
    					    	  continue;
    					      }
    					      
    					      if(nome.substring(1, 2).equalsIgnoreCase("_")) {
    					      int t=Integer.parseInt(nome.substring(0, 1));
    					 
    						 if( xvars[d][g]!=null && t==j ) {
    						 expr.addTerm(1, xvars[d][g]);
    					 System.out.println("trovato "+d+" "+ g+" per= "+j);
    						 }
    					 
    				 }
    				 }
    				 
    			 
    			 
    		 }
    		 
    		 model.addConstr(expr, GRB.EQUAL, 1, "c_"+j);
    	 
    	 }
     }
     
     
     //vincolo in cui faccio la sommatoria di tutti gli Xij con j fisso;
      private void addConstraint2() throws GRBException{
    	 
    	 for(int j=47;j<94;j++) {
    	 
    		 expr=new GRBLinExpr();
    		 
    		 for(int i=0;i<colLenght2.get(j-47);i++){
    		 
    			 if(xvars[j][i]!=null)
    		 expr.addTerm(1, xvars[j][i]);
    		 
    	 }
    		 
    		 for(int d=47;d<94;d++) {
    			 
    			 
				 for(int g=0;g<colLenght2.get(d-47);g++)
				 {
				
					 //Analizzo il nome della variabile per poter trovare tutte le variabili con j uguale
					      String nome=xvars[d][g].get(StringAttr.VarName);
					      nome=nome.substring(2, 5);
					      
					      if(nome.substring(2, 3).equalsIgnoreCase("_")) {
					    	  
					    	  int jk=Integer.parseInt(nome.substring(0, 2));
					    	  if( xvars[d][g]!=null && jk==(j-47) ) {
		    						 expr.addTerm(1, xvars[d][g]);
		    					 System.out.println("2_trovato "+d+" "+ g+" per= "+(j-47));
		    						 }
					    	  
					    	  continue;
					      }
					      
					      if(nome.substring(1, 2).equalsIgnoreCase("_")) {
					      int t=Integer.parseInt(nome.substring(0, 1));
					 
						 if( xvars[d][g]!=null && t==(j-47) ) {
						 expr.addTerm(1, xvars[d][g]);
					 System.out.println("2_trovato "+d+" "+ g+" per= "+(j-47));
						 }
					 
				 }
				 }
				 
			 
			 
		 }
    		 model.addConstr(expr, GRB.EQUAL, 1, "z_"+j);
    	 
    	 }
     }
	
	public void solve()
	{
		try {
			
			model.optimize();
		}catch(GRBException e) {
			e.printStackTrace();
		}
	}
	
	
}
