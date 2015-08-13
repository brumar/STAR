package model.runModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import model.pbmData.ProblemDatas;
import model.pbmData.Protocol;
import model.rulesData.LogicalCondition;
import model.rulesData.Rule;
import model.rulesData.RuleDatas;
import model.tools.DoubleHashMap;

public class ModelFinder {

	private ModelLister modellist;
	private ModelBuilder model;
	private ModelEvaluator modelEvaluation;	
	private ArrayList<HashSet<Integer>> ListofPoss;
	private boolean[][] matrixGrid;
	private boolean[][] matrixModel;
	private ArrayList<Protocol> protocols;
	private HashMap<String, Rule> ruleMap;
	private DoubleHashMap indexRule;
	private ProblemDatas problemDatas;
	private RuleDatas ruleDatas;
	double ParametersdescriptionLength;
	private ArrayList<String> messages=new ArrayList<String>() ;
	boolean error=false;



	public ModelFinder(boolean[][] m, ProblemDatas pbmDatas, RuleDatas ruleDs,DoubleHashMap iRule){
		matrixGrid=m;
		protocols=pbmDatas.getProtocols();
		ruleDatas=ruleDs;		
		indexRule=iRule;
		problemDatas=pbmDatas;
		ruleMap=ruleDs.getRulesMap();
	}

	public void run() throws Exception {

		precomputation();
		evaluation(protocols);}



	private void precomputation() {
		modellist=new ModelLister(matrixGrid);
		ListofPoss=modellist.listPossibilities();
		ParametersdescriptionLength=modellist.ParamdescriptionLength();
		//System.out.println("parameters : "+ParametersdescriptionLength);
		}

	private void evaluation(ArrayList<Protocol> protocols) {
		Iterator<Protocol> it2 = protocols.iterator();
		while(it2.hasNext()){
			Protocol pro = it2.next();
			Iterator<HashSet<Integer>> it = ListofPoss.iterator();
			double minimum=100;
			String message="";
			while(it.hasNext()){
				HashSet<Integer> poss = it.next();
				ArrayList<Integer> possList = new ArrayList<Integer>(poss);
				model=new ModelBuilder(possList,ruleMap,indexRule);
				model.BuildMatrixModel();
				model.BuilBehavioralVectors(problemDatas,ruleDatas,"interdiction");
				modelEvaluation=new ModelEvaluator(problemDatas,model.getListAnswersToPbmVector());
				double WithoutModeldescriptionLength=modelEvaluation.descriptionLengthNoModel();			
				double WithModelLocaldescriptionLength=modelEvaluation.evaluateProtocol(pro);
			//	System.out.println("local description : "+WithModelLocaldescriptionLength);
				double ModeldescriptionLength=WithModelLocaldescriptionLength+ParametersdescriptionLength;
				//System.out.println("avec modele : "+ModeldescriptionLength+" sans modele : "+WithoutModeldescriptionLength);
				
				if(ModeldescriptionLength<minimum){
					minimum=ModeldescriptionLength;
					message=writeMessage(possList,pro,ModeldescriptionLength,WithoutModeldescriptionLength);
				}
			}
			System.out.println(message);
			messages.add(message);
		}	
	}
	private String writeMessage(ArrayList<Integer> poss, Protocol pro, double modeldescriptionLength, double WithoutModeldescriptionLength) {
		String mess="\n The subject ";
		mess+=pro.getIdSubjectSession();
		mess+=" has been best simulated  by ";
		Iterator<Integer> it = poss.iterator();
		while(it.hasNext()){
			mess+=indexRule.find(it.next()+1)+" , ";
		}
		mess+="\nwith a description length of : "+(Math.floor(modeldescriptionLength*100)/100)+" bits";
		mess+="\n( description length without any model : "+(Math.floor(WithoutModeldescriptionLength*100)/100)+"  bits)";
		return mess;
	}

	private void evaluation() {
		evaluation(protocols);	
	}

	public ArrayList<String> getMessages() {
		return messages;
	}

	public boolean isError() {
		return error;
	}


}
