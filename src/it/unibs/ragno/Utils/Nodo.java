package it.unibs.ragno.Utils;

public class Nodo {

	private int prevNodo;
	private int nextNodo;
	private int costo;
	
	//------------------------------------------------------------------------------------------
	
	public Nodo (int prevNodo, int nextNodo, int costo) {
		
		this.prevNodo=prevNodo;
		this.nextNodo=nextNodo;
		this.costo=costo;
	}
	
	//---------------------------------------------------------------------------------------------
	
	public int getPrev() {
		
		return prevNodo;
	}
	
	//----------------------------------------------------------------------------------------------
	
	public int getNext() {
		
		return nextNodo;
	}
	
	//----------------------------------------------------------------------------------------------
	
	public int getCosto() {
		
		return costo;
	}
}
