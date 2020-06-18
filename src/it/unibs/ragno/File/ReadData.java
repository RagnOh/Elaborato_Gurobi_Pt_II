package it.unibs.ragno.File;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import it.unibs.ragno.Utils.Nodo;

public class ReadData {

	//Classe per leggere il file .txt con i nodi
	public  ArrayList<Nodo>nodi= new ArrayList<Nodo>();
	
	private int a;
	private int b;
	private int c;
	
	private String file;
	
	public ReadData(String file) {


		this.file=file;
	}
	
	public void leggiFile() {
		
		
		try {
	  
			File file = new File("tsp.txt"); 
	  
	        BufferedReader br = new BufferedReader(new FileReader(file)); 
	  
	        String st=" ";
	  
	       
	        
	      
	        
	        System.out.println(" ");
	        while ((st = br.readLine()) != null) {
		  
	        	if(st.substring(0).equals("Nodi 48")) continue;
	        	
	        	//System.out.println(st); 
	        	
	        	analizzaStringa(st);
	        	
	        	nodi.add(new Nodo(a,b,c));
	      
	        }
	  
	  
		}catch(Exception e) {
			
			e.printStackTrace();
		}
	  
	}
	
	
	
	private void analizzaStringa(String s) {
		
		String d=" ";
		String f=" ";
		String g=" ";
		
		d=s.substring(0, 1);
		
		
		
		
		s=s.substring(1);
		for(int i=0;i<s.length();i=i+1) {
			
			
			if(s.substring(i, i+1).equals(" "))
			{
				s=s.substring(i+1);
				break;
			}
			
			else {
				
				d=d+s.substring(i, i+1);
			}
		}
		
		
		
		for(int i=0;i<s.length();i++) {
			
		if(s.substring(i, i+1).equals(" "))
		{
			s=s.substring(i);
			break;
		}
			
			f=f+s.substring(i, i+1);
			
		}
		
		g=s.trim();
		f=f.trim();
		
		
		a=	Integer.parseInt(d);
		b=	Integer.parseInt(f);
		c=  Integer.parseInt(g);
		
		//System.out.println("a="+a);
		//System.out.println("b="+b);
		//System.out.println("c="+c);
		
		//System.out.println(" ");
	}
}
