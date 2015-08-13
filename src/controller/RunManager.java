package controller;

import java.util.ArrayList;
import java.util.HashMap;

import resources.Resource;

import model.modelData.ModelDatas;
import model.pbmData.ProblemDatas;
import model.pbmData.Protocol;
import model.rulesData.LogicalCondition;
import model.rulesData.Rule;
import model.rulesData.RuleDatas;
import model.runModel.ModelFinder;
import model.tools.DoubleHashMap;

public class RunManager {
	private ArrayList<String> SelectionMessages;
	private String runmessage;
	private boolean[][] matrix;
	private HashMap<String, Rule> ruleMap;
	private ArrayList<Protocol> protocols;
	private DoubleHashMap indexRule;
	private ProblemDatas problemDatas;
	private RuleDatas ruleDatas;
	
	/*public RunManager(boolean[][] m, HashMap<String, Rule> hashMap, ArrayList<Protocol> arrayList, DoubleHashMap iRule){
		matrix=m;
		ruleMap=hashMap;
		protocols=arrayList;
		indexRule=iRule;
	}*/
	
	public RunManager(ModelDatas modelDatas, RuleDatas rDatas,ProblemDatas pbmDatas) {
		problemDatas=pbmDatas;	
		matrix=modelDatas.getMatrix(); 
		ruleDatas=rDatas;
		protocols=pbmDatas.getProtocols();
		indexRule=modelDatas.getIndexRule();
	}

	public void run() throws Exception {
			ModelFinder mf = new ModelFinder(matrix,problemDatas,ruleDatas,indexRule);
			mf.run();
			if(mf.isError()){
				runmessage=Resource.messages.getString("RunFail");
			}
			else{
				runmessage=Resource.messages.getString("RunSucess");
				SelectionMessages=mf.getMessages();
			}

		}
	public ArrayList<String> getSelectionMessages() {
		return SelectionMessages;
	}

	public String getRunmessage() {
		return runmessage;
	}
		
	}


