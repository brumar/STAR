package model.runModel;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class ModelLister {
	private Integer listlength;
	private ArrayList<HashSet<Integer>> compatiblesRulesSets;
	private boolean[][] matrix;
	
	public ModelLister(boolean[][] matrixGrid) {
		matrix=matrixGrid;
		compatiblesRulesSets=new ArrayList<HashSet<Integer>>();
		Integer matrixlength=matrixGrid.length;
		listlength=matrixlength;
		
	}

	public ArrayList<HashSet<Integer>> listPossibilities() {
		
		boolean[] b=InitChoiceVectors();//take the rules which pre-selected for the simulation
		RecurciveFinder(new HashSet<Integer>(),b);///pause
		HashSet<Integer> voidlist = compatiblesRulesSets.get(0);
		compatiblesRulesSets.remove(0);		//dirty code to avoid that the first item is empty, 
		compatiblesRulesSets.add(voidlist);	//and also instanciate early some parameters in model builder (height and width)
		
		//compatiblesRulesSets=completeWithSingleRules(compatiblesRulesSets);//When a rule is not involved in a ExclusionPairs,
		System.out.println(compatiblesRulesSets);														// it's excluded from being processed in the previous recurcive algorithm		
		return compatiblesRulesSets;
	}
	private boolean[] InitChoiceVectors() {
		// TODO Auto-generated method stub
		boolean[] b = new boolean[listlength];
		for(int i=0;i<listlength;i++){
			b[i]=matrix[i][i];//the rules pre-selected have a true in the matrix diagonale 
		}
		return b;
	}

	public void RecurciveFinder(HashSet<Integer> l,boolean[] b) {
		//printbitset(b);
		
		if(!compatiblesRulesSets.contains(l)){
			compatiblesRulesSets.add(new HashSet<Integer>(l));
		}
		//HashSet<Integer> l = (HashSet<Integer>) l2.clone();
		for(int i=0;i<listlength;i++){
			if((!l.contains(i)&&(b[i]))){
				boolean[] b2 = matrix[i];
				boolean[] b3=BoolArrayAnd(b,b2);
				l.add(i);
				RecurciveFinder(new HashSet<Integer>(l),b3);
				l.remove(i);
			}
			
		}

	
	}
	private boolean[] BoolArrayAnd(boolean[] b, boolean[] b2) {
		int l = b.length;
		boolean[] b3=new boolean[l];
		for(int i=0;i<l;i++){
			b3[i]=b[i]&&b2[i];
		}
		
		return b3;
	}


	public double ParamdescriptionLength() {
		// TODO Auto-generated method stub
		return Math.log(compatiblesRulesSets.size());
	}

}

