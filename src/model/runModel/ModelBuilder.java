package model.runModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;

import model.pbmData.Answer;
import model.pbmData.AnswerProperty;
import model.pbmData.Problem;
import model.pbmData.ProblemDatas;
import model.pbmData.ProblemProperty;
import model.pbmData.Protocol;
import model.rulesData.LogicalCondition;
import model.rulesData.Rule;
import model.rulesData.RuleDatas;
import model.tools.DoubleHashMap;

public class ModelBuilder {
	private ArrayList<Integer> possibility;
	private HashMap<String, Rule> ruleMap;
	private DoubleHashMap indexRule;
	private boolean[][] matrixPropertiesModel;
	ArrayList<boolean[]> listAnswersToPbmVector; //represents the answers accepted by the Pbm
	private HashMap<String, Integer> problemIdToIndex=new HashMap<String, Integer>();


	static int width;
	static int height;

	public ModelBuilder(ArrayList<Integer>poss , HashMap<String, Rule> ruleMap2, DoubleHashMap iRule) {
		possibility=poss;
		ruleMap=ruleMap2;
		indexRule=iRule;
		//pro.getProbAnswerMap().keySet().iterator();
		listAnswersToPbmVector=new ArrayList<boolean[]>();// TODO Auto-generated constructor stub
		
	}

	public void BuildMatrixModel() {
		
		Iterator<Integer> iter = possibility.iterator();
		ArrayList<boolean[][]> l =new ArrayList<boolean[][]>();
		while(iter.hasNext()){
			Integer i = iter.next();		
			l.add(buildPropertiesBehaviorMatrix(i));
		}

		PerformIntegration("and",l);
	}
	
	private void PerformIntegration(String operator, ArrayList<boolean[][]> l) {
		// each rule has its own matrix, this function merge them together
		if(l.size()==0){
			boolean[][] b = new boolean[height][width];
			for(int i=0;i<height;i++){
				for(int j=0;j<width;j++){
					b[i][j]=operator.equals("and");
				}
			}
			l.add(b);
		}
		Iterator<boolean[][]> it = l.iterator();
		boolean[][] matrix=it.next();
		while(it.hasNext()){
			boolean[][] matrixTobeIntegrated = it.next();
			for(int i=0;i<height;i++){
				for(int j=0;j< width;j++){
					if (operator.equals("and")){matrix[i][j]=matrix[i][j] && matrixTobeIntegrated[i][j];}
					if (operator.equals("or")){matrix[i][j]=matrix[i][j] || matrixTobeIntegrated[i][j];}
				}
			}
		}
		matrixPropertiesModel=matrix;
	}

	public boolean[][] buildPropertiesBehaviorMatrix(Integer i) {
		
		String id=indexRule.find(i+1);
		boolean[][] matrix=copyMatrix(ruleMap.get(id).getCondition().getMatrix());
		height=matrix.length;
		width=matrix[0].length;
		//printUniqueRuleMatrix(matrix,i);
		return matrix;
	}


	private boolean[][] copyMatrix(boolean[][] matrix) {
		int h = matrix.length;
		int w = matrix[0].length;
		boolean[][] m = new boolean[h][w];
		for(int i=0;i<h;i++){
			for(int j=0;j< w;j++){
				m[i][j]=matrix[i][j];
			}
		}
		return m;
	}

	public boolean[][] getMatrixPropertiesModel() {
		return matrixPropertiesModel;
	}

	public void BuilBehavioralVectors(ProblemDatas problemDatas, RuleDatas ruleDatas, String operator) {
		// TODO Auto-generated method stub
		/*
		private  HashMap<String,Problem> ProblemMap=new HashMap<String,Problem>();
		private  HashMap<String,Answer> AnswersMap=new HashMap<String,Answer>();
		private  HashMap<String,AnswerProperty> PropertiesAnswersMap=new HashMap<String,AnswerProperty>();
		private  HashMap<String,ProblemProperty> PropertiesProblemMap=new HashMap<String,ProblemProperty>();
		private  ArrayList<Protocol> protocols= new ArrayList<Protocol>();
		ruleDatas.getRulesMap();
		private DoubleHashMap indexPropertyPbm=//give the position of the propertyPbm inside the logical condition  matrix
		private DoubleHashMap indexPropertyAnswer
		*/
		Iterator<String> it = problemDatas.getProblemMap().keySet().iterator();	
		int indexPbm=0;
		while (it.hasNext()){		
			String pbm = it.next();
			problemIdToIndex.put(pbm,indexPbm);
			indexPbm++;
			ArrayList<String> problemProperties = problemDatas.getProblemMap().get(pbm).getProblemPropertiesID();
			ArrayList<String> answers = problemDatas.getProblemMap().get(pbm).getAnswersID();
			boolean[] answersVector=new boolean[answers.size()];
			Iterator<String> it2 = answers.iterator();
			int indexAnswer = 0;
			while(it2.hasNext()){
				ArrayList<String> answerProperties = problemDatas.getAnswersMap().get(it2.next()).getAnswerPropertiesID();
				Iterator<String> it3 = problemProperties.iterator();
				ArrayList<Boolean> listBool=new ArrayList<Boolean>();
				while(it3.hasNext()){
					String propertyPbm = it3.next();
					int j=ruleDatas.getIndexPropertyPbm().find(propertyPbm);
					Iterator<String> it4 = answerProperties.iterator();
					while(it4.hasNext()){					
						String propertyAnswer = it4.next();
						int i=ruleDatas.getIndexPropertyAnswer().find(propertyAnswer);
						//System.out.println(" i : "+ i + " j : "+j);
						boolean autorisation = matrixPropertiesModel[i][j];
						listBool.add(autorisation);
						/*
						if(!autorisation){//debug purpose
							String message = "answerProp ("+propertyAnswer+") forbidden on problemProp ("+propertyPbm+")";
							message+=" current Rules : ";
							for(Integer p:possibility){
								message+= indexRule.find(p+1)+", ";
							}
							System.out.println(message);
						}*/
					}
				}
				Iterator<Boolean> it5 = listBool.iterator();
				boolean value=false;
				if(operator.equals("interdiction")){value=true;}
				if(operator.equals("autorisation")){value=false;}
				while(it5.hasNext()){
					Boolean valueToCompare = it5.next();
					if(operator.equals("interdiction")){value=value&&valueToCompare;}
					if(operator.equals("autorisation")){value=value||valueToCompare;}
				}
				answersVector[indexAnswer]=value;
				indexAnswer++;
			}			
			listAnswersToPbmVector.add(answersVector);
		}
		//printStuff();
	}

	private void printStuff() {
		int sumVec = 0;
		int total=0;
		int sumMat = 0;
		int totalMat=0;
		for( boolean[] vector:listAnswersToPbmVector){
			for(boolean b:vector){
				if(b){
					sumVec+=1;
				}
				total+=1;
			}
		}
		for( boolean[] row:matrixPropertiesModel){
			for(boolean b2:row){
				if(b2){
					sumMat+=1;
				}
				totalMat+=1;
			}
		}
		double proportionVec = (double)(sumVec)/(double)(total);
		double proportionMat = (double)(sumMat)/(double)(totalMat);
		System.out.println(possibility);		
		System.out.println("proportion of true in answer vector "+proportionVec);
		System.out.println("proportion of true in matrice "+proportionMat);		
	}
	
	private void printUniqueRuleMatrix(boolean[][] matrix, Integer i) {
		int sumMat = 0;
		int totalMat=0;
		for( boolean[] row:matrix){
			for(boolean b2:row){
				if(b2){
					sumMat+=1;
				}
				totalMat+=1;
			}
		}
		double proportionMat = (double)(sumMat)/(double)(totalMat);
		System.out.println(possibility);		
		System.out.println("proportion of true in Rule matrice "+i+" is "+proportionMat);	
		
	}

	public ArrayList<boolean[]> getListAnswersToPbmVector() {
		return listAnswersToPbmVector;
	}
	public HashMap<String, Integer> getProblemIdToIndex() {
		return problemIdToIndex;
	}
}