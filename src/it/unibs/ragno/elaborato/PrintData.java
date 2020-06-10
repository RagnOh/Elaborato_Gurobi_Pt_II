package it.unibs.ragno.elaborato;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class PrintData {

	
	private BufferedWriter bufWriter;
	
	//---------------------------------------------------------------------------------------------------
	private int numGruppo;
	
	//---------------------------------------------------------------------------------------------------
	private String nome1;
	private String nome2;
	
	//---------------------------------------------------------------------------------------------------
	private int valFObbiettivo;
	
	//------------------------------------------------
	private int valFObbClean;
	
	//-----------------------------------------------------------
	private ArrayList<Integer>nodi=new ArrayList<Integer>();
	
	//----------------------------------------------------------------
	private int valFObbMTZ;
	
	//--------------------------------------------------------------
	private ArrayList<Integer>cicloOttimo=new ArrayList<Integer>();
	
	//---------------------------------------------------------------------------------------------------
	
	public PrintData(int numGruppo, String nome1, String nome2) {
		
		this.numGruppo=numGruppo;
		
		this.nome1=nome1;
		this.nome2=nome2;
		
		
	}
	
	//----------------------------------------------------------------------------------------------------
	
	public void getValFObb(int val) {
		
		this.valFObbiettivo=val;
	}
	
	//----------------------------------------------------------------------------------------------------
	
    public void getValFObbClean(int val) {
		
		this.valFObbClean=val;
	}
	
    //-------------------------------------------------------------------------
    
    public void addNodo(int n) {
    	
    	nodi.add(n);
    }
    
    //------------------------------------------------------------------------
    
    public void getValFObbMTZ(int val) {
    	
    	this.valFObbMTZ=val;
    }
    
    //-------------------------------------------------------------------------
    
    public void addCiclo(int n) {
    	
    	cicloOttimo.add(n);
    }
    
    //--------------------------------------------------------------------
    
	public void stampaDati() {
		
		
		try {
			
			OutputStreamWriter writer = new OutputStreamWriter(
            new FileOutputStream("risposte_gruppo"+numGruppo+".txt"), "UTF-8");
		
			bufWriter = new BufferedWriter(writer);
		
			bufWriter.write("GRUPPO "+numGruppo);
            bufWriter.newLine();
            bufWriter.write("Componenti: "+ nome1 + ",  " + nome2);
            
            doppioSpazio();
            bufWriter.newLine();
            
            quesito1();
            
            doppioSpazio();
            
            quesito2();
            
            doppioSpazio();
            
            quesito3();
 
            bufWriter.close();
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	//------------------------------------------------------------------------------------------------------
	
     private void doppioSpazio() {
		
		try {
			
		    bufWriter.newLine();
		    bufWriter.newLine();
		    
		}   
		catch(IOException e)
		    {
			e.printStackTrace();
		    }
	}
	
     //------------------------------------------------------------------------------------------------------
     
     private void quesito1() {
    	 
    	 try {
    		 
    		 bufWriter.write("QUESITO I:");
 			 bufWriter.newLine();
 			 
    		 bufWriter.write("funzione obbiettivo = " + valFObbiettivo);
 			 bufWriter.newLine();
    		 
 			 
    	 }catch(Exception e) {
    		 
    		 e.printStackTrace();
    	 }
     }
     
     //-----------------------------------------------------------------------
     
     private void quesito2() {
    	 
    	  int i=0;
          try {
    		 
        	 bufWriter.write("QUESITO II:");
  			 bufWriter.newLine(); 
        	  
    		 bufWriter.write("funzione obbiettivo senza SEC, CC o MTZ = " + valFObbClean);
 			 bufWriter.newLine();
    		 
 			for(i=0;i<nodi.size();i++) {
				 
 				bufWriter.write(nodi.get(i)+", ");
 				 
 			 }
 			 
    	 }catch(Exception e) {
    		 
    		 e.printStackTrace();
    	 }
    	 
     }
	
     //-------------------------------------------------------------------
     
     private void quesito3() {
    	 
   	  int i=0;
         try {
   		 
        	bufWriter.write("QUESITO III:");
 			bufWriter.newLine(); 
        	 
   		    bufWriter.write("funzione obbiettivo con MTZ = " + valFObbMTZ);
			bufWriter.newLine();
   		 
			for(i=0;i<cicloOttimo.size();i++) {
				 
				bufWriter.write(cicloOttimo.get(i)+", ");
				 
			 }
			 
   	 }catch(Exception e) {
   		 
   		 e.printStackTrace();
   	 }
   	 
    }
	
}
