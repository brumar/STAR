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
import model.tools.PickFromWeightedItems;
import resources.Resource;
import model.tools.Writers;

import java.util.Collections;

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
	private PickFromWeightedItems randomSelector;
	private ArrayList<Double> permutationValues= new ArrayList<Double>();
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
		preparePermutationTest();
		evaluation(protocols);}



	private void precomputation() {
		modellist=new ModelLister(matrixGrid);
		ListofPoss=modellist.listPossibilities();
		ParametersdescriptionLength=modellist.ParamdescriptionLength();
		//System.out.println("parameters : "+ParametersdescriptionLength);
		}

	private void evaluation(ArrayList<Protocol> protocols) {
		Iterator<Protocol> it2 = protocols.iterator();
		ArrayList<EvaluationOutput> outputs=new ArrayList<EvaluationOutput>();
		int index=0;
		while(it2.hasNext()){
			index++;
			try{
			Protocol pro = it2.next();		
			outputs.add( singleEvaluation(pro,"compareWithPermutationValues"));
			}
			 catch (Exception e1) {
				System.out.println( e1.getMessage());
				System.out.println("fail with protocol"+index);
			}
		}
		for (EvaluationOutput ev:outputs){
			messages.add(ev.writeMessage());
		}
	}

	private EvaluationOutput singleEvaluation(Protocol pro, String action) {
		Iterator<HashSet<Integer>> it = ListofPoss.iterator();
		double minimum=100;
		EvaluationOutput output = new EvaluationOutput("error");
		double ModeldescriptionLength=0;
		double WithoutModeldescriptionLength=0;
		
		while(it.hasNext()){
			HashSet<Integer> poss = it.next();
			ArrayList<Integer> possList = new ArrayList<Integer>(poss);
			model=new ModelBuilder(possList,ruleMap,indexRule);
			model.BuildMatrixModel();
			model.BuilBehavioralVectors(problemDatas,ruleDatas,"interdiction");
			modelEvaluation=new ModelEvaluator(problemDatas,model.getListAnswersToPbmVector(),model.getProblemIdToIndex());
			 WithoutModeldescriptionLength=modelEvaluation.descriptionLengthNoModel(pro);			
			double WithModelLocaldescriptionLength=modelEvaluation.evaluateProtocol(pro);
		//	System.out.println("local description : "+WithModelLocaldescriptionLength);
			 ModeldescriptionLength=WithModelLocaldescriptionLength+ParametersdescriptionLength;
			//System.out.println("avec modele : "+ModeldescriptionLength+" sans modele : "+WithoutModeldescriptionLength);
			
			if(ModeldescriptionLength<minimum){
				minimum=ModeldescriptionLength;
				output = new EvaluationOutput(possList,pro,ModeldescriptionLength,WithoutModeldescriptionLength,indexRule);
			}
		}
		if(action=="compareWithPermutationValues"){
			double p = this.getPvalue(permutationValues, output.getCompression(), 0, permutationValues.size());
			output.setPermutationPvalue(p);
		}
		return output;
	}

	public ArrayList<String> getMessages() {
		return messages;
	}

	public boolean isError() {
		return error;
	}

	private void preparePermutationTest() {
		randomSelector=new PickFromWeightedItems(problemDatas.getOccurenceMatrix());
		int nperm = Integer.parseInt(Resource.messages.getString("Npermutations"));
		for (int i = 0; i < nperm; i++){
			HashMap<String, String> selection = randomSelector.PickFromWeightedMatrice();
			Protocol p=new Protocol(i,selection);
			EvaluationOutput output = singleEvaluation(p,"storePermutationValues");
			if(!output.writeMessage().equals("error")){
				this.permutationValues.add(output.getCompression());
			}
			
		}
		Collections.sort(permutationValues);
		if(Resource.messages.getString("storePermutationValues").equals("yes")){
			String fileDest = Resource.messages.getString("outputPermutationValues");
			Writers.createTextFileWithNumbers(permutationValues, fileDest);
		}
	}
	
	public double getPvalue( ArrayList<Double> permutationValues,double i, Integer low,Integer high){
	    if (high-low<2){
	        return (double)high/(double)permutationValues.size();
	    }
	    int mid = low + ((high - low) >> 1);
	    if (permutationValues.get(mid) > i)
	        return getPvalue(permutationValues, i, low, mid - 1);
	    else
	        return getPvalue(permutationValues, i, mid + 1, high);
	}


}
