package model.runModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import model.pbmData.ProblemDatas;
import model.pbmData.Protocol;
import model.rulesData.Rule;
import model.tools.PickFromWeightedItems;
import model.tools.Writers;
import resources.Resource;

public class ProtocolsPermutations {

	private ProblemDatas problemDatas;
	private PickFromWeightedItems randomSelector;
	private ArrayList<Protocol> randomProtocols=new ArrayList<Protocol>();

	private Random randomGenerator;
	private ArrayList<Double> fullPermutationValues;
	
	private HashMap<Integer, Set<String>> uncompleteProtocols=new HashMap<Integer, Set<String>>(); // string => id
	private HashMap<Integer,  ArrayList<Double>> PermutationValuesOfUncompleteProtocols=new HashMap<Integer,  ArrayList<Double>>();
	private HashMap<Integer,  String> stringRepresentationsPermutSet=new HashMap<Integer,  String>();
	private Integer numberOfDifferentProtocolCount=0;
	private int nperm;;
	

	public ProtocolsPermutations(ProblemDatas pDatas) {
		nperm = Integer.parseInt(Resource.messages.getString("Npermutations"));
		problemDatas=pDatas;
		randomSelector=new PickFromWeightedItems(problemDatas.getOccurenceMatrix());
		randomGenerator = new Random();
		preparePermutationTest();
	}

	public void preparePermutationTest() {


		for (int i = 0; i < nperm; i++){
			HashMap<String, String> selection = randomSelector.PickFromWeightedMatrice();
			Protocol p=new Protocol(i,selection);
			randomProtocols.add(p);		
		}
		/*
		 * 			EvaluationOutput output = singleEvaluation(p,"storePermutationValues");
			if(!output.writeMessage().equals("error")){
				this.permutationValues.add(output.getCompression());
			}
		
		Collections.sort(permutationValues);
		if(Resource.messages.getString("storePermutationValues").equals("yes")){
			String fileDest = Resource.messages.getString("outputPermutationValues");
			Writers.createTextFileWithNumbers(permutationValues, fileDest);
		} */
	}
	public Protocol pickProtocol() {
		int index = randomGenerator.nextInt(randomProtocols.size());
		Protocol pSelected = randomProtocols.get(index);
		return pSelected;
	}

	public ArrayList<Protocol> getRandomProtocols(Protocol p) {
		ArrayList<Protocol> ps = new ArrayList<Protocol>();
		for (int i = 0; i < nperm; i++){
			Protocol arandomProtocol = pickProtocol();
			Set<String> allTheProblemsToSelect = p.getProbAnswerMap().keySet();
			Set<String> allTheProblems=arandomProtocol.getProbAnswerMap().keySet();
			allTheProblems.retainAll(allTheProblemsToSelect);
			ps.add(arandomProtocol);
		}
		return ps;	
	}	

	public void storeFullPermValues(Protocol p, ArrayList<Double> permutationValues){
		numberOfDifferentProtocolCount+=1;
		Collections.sort(permutationValues);
		Set<String> pbmSet = p.getProbAnswerMap().keySet();	
		uncompleteProtocols.put(numberOfDifferentProtocolCount, pbmSet);
		PermutationValuesOfUncompleteProtocols.put(numberOfDifferentProtocolCount, permutationValues);
		String shortKeysetRepresentation = shortKeysetRepresentation(pbmSet);
		stringRepresentationsPermutSet.put(numberOfDifferentProtocolCount, shortKeysetRepresentation);
		
		
		if(Resource.messages.getString("storePermutationValues").equals("yes")){
			String fileDest = Resource.messages.getString("outputPermutationValues")+numberOfDifferentProtocolCount;
			Writers.createTextFileWithNumbers(permutationValues,shortKeysetRepresentation, fileDest);
		}
	}
	
	public String shortKeysetRepresentation(Set<String> k){
		String message="";
		for(String a:k){
			message+=a+"_";
		}
		return message;
	}
	
	public double getPvalueForFullProtocol(ArrayList<Double> values, Double i,  Integer low,Integer high){
		// Binary search
	    if (high-low<2){
	        return (double)high/(double)values.size();
	    }
	    int mid = low + ((high - low) >> 1);
	    if (values.get(mid) > i)
	        return getPvalueForFullProtocol(values,i, low, mid - 1);
	    else
	        return getPvalueForFullProtocol(values, i, mid + 1, high);
	}	
	//0, permutationValues.size()
	public ArrayList<Double> getFullPermutationValues() {
		return fullPermutationValues;
	}

	public double getPvalue(Protocol pro, Double compression) {
		
		ArrayList<Double> values;
		values=getPermutationsForThisProtocol(pro);	
		return getPvalueForFullProtocol(values,compression,0,values.size());
		}

	private ArrayList<Double> getPermutationsForThisProtocol(Protocol pro) {
		Set<String> set = pro.getProbAnswerMap().keySet();
		Set<Integer> us = uncompleteProtocols.keySet();
		for(Integer u:us){
			if(set.equals(uncompleteProtocols.get(u))){
				return PermutationValuesOfUncompleteProtocols.get(u);
			}
		}
		System.out.println("houston we have a problem");
		return new ArrayList<Double>();//in case of trouble, but trouble are not avoided
	}
	
	public String getStringRepresentationProtocol(Protocol pro) {
		Set<String> set = pro.getProbAnswerMap().keySet();
		Set<Integer> us = uncompleteProtocols.keySet();
		for(Integer u:us){
			if(set.equals(uncompleteProtocols.get(u))){
				return stringRepresentationsPermutSet.get(u);
			}
		}
		return"";
	}

	public boolean alreadyStoredThisKindOfProtocol(Protocol p) {
		Set<String> set = p.getProbAnswerMap().keySet();
		Set<Integer> us = uncompleteProtocols.keySet();
		for(Integer u:us){
			if(set.equals(uncompleteProtocols.get(u))){
				return true;
			}
		}
		return false;//in case of trouble, but trouble are not avoided
	}
}
