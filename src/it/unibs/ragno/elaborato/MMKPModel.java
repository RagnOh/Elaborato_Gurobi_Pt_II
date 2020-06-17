package it.unibs.ragno.elaborato;

import gurobi.GRB;
import gurobi.GRB.IntParam;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

public class MMKPModel {

	private int timeLimit;
	private GRBEnv env;
	private GRBModel model;
	private String percorso;
	
	public MMKPModel(String percorso, int timeLimit) {
		
		
		this.timeLimit = timeLimit;
		this.percorso = percorso;
		
	}
	
	public void buildModel() {
		
		try
		{
			
			env= new GRBEnv();
			env.set(GRB.IntParam.Threads,8);
			env.set(GRB.IntParam.Presolve,2);
			env.set(IntParam.Cuts,0);
			
			env.set(GRB.DoubleParam.TimeLimit, timeLimit);
			model = new GRBModel(env,percorso);
			
			model.set(GRB.IntAttr.ModelSense,GRB.MAXIMIZE);
			
			
		}catch(GRBException e) {
			
			e.printStackTrace();
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
