package it.unibs.ragno.elaborato;

import gurobi.GRB;
import gurobi.GRB.DoubleAttr;
import gurobi.GRB.IntParam;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

public class MMKPModel {

	//Classe per il primo quesito
	private int timeLimit;
	private GRBEnv env;
	private GRBModel model;
	private String percorso;
	private static double relax ;
	private static final double valDato= 81900.0;
	private double soluzione;
	
	public MMKPModel(String percorso, int timeLimit) {
		
		
		this.timeLimit = timeLimit;
		this.percorso = percorso;
		
	}
	
	public void buildModel() {
		
		try
		{
			
			env = new GRBEnv();
			env.set(GRB.IntParam.Threads,8);
			env.set(GRB.IntParam.Presolve,2);
			env.set(IntParam.Cuts,0);
			env.set(GRB.DoubleParam.ImproveStartGap,(double)(9.0/81909.0)*100.0); // 9 dato come differenza tra valore rilassato precedentemente calcolato e valore dato
			env.set(GRB.DoubleParam.TimeLimit, timeLimit);
			model = new GRBModel(env, "mmkp.lp");
			
			
			
			
		}catch(GRBException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	
	public void solve()
	{
		try {
			
			model.optimize();
			soluzione=model.get(DoubleAttr.ObjVal);
			System.out.println(soluzione/10000);
			
			model.dispose();
			env.dispose();
		}catch(GRBException e) {
			e.printStackTrace();
		}
	}
	
	public double getSol() {
		
		return soluzione;
	}
}
