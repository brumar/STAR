package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import resources.Resource;

import model.pbmData.ProblemDatas;
import model.rulesData.RuleDatas;
import model.rulesData.Line;
import model.tools.Filesmethods;
import model.tools.MyZip;

public class DataManager {
	
	private ProblemDatas problemDatas;
	private RuleDatas ruleDatas;
	private ArrayList<String> pbmPropList=new ArrayList<String>();
	private ArrayList<String> rulesList=new ArrayList<String>();
	
	//private ModelDatas modelDatas;
	
	public RuleDatas getRuleDatas() {
		return ruleDatas;
		
	}

	private String problemMessage;
	private String ModelMessage;

	public String getModelMessage() {
		return ModelMessage;
	}

	public void setModelMessage(String modelMessage) {
		ModelMessage = modelMessage;
	}

	public HashMap<String, ArrayList<Line>> getRuleslines() {
		return ruleslines;
	}

	private String RulesMessage;
	private String ruleMessage;
	private HashMap<String, ArrayList<Line>> ruleslines;



	public  DataManager(){

		model.pbmData.ProblemDatas d=new model.pbmData.ProblemDatas();//load the csv present in the folder
		if (d.isError()){
			//problemDatas=null;
			problemMessage=Resource.messages.getString("dataPloadFail");
		}
		else {
			problemMessage=Resource.messages.getString("dataPloadSuccess");
		}
		System.out.println(problemMessage);
		
		RuleDatas r=new RuleDatas(d);
		if (r.isError()){
			//ruleDatas=null;
			ruleMessage=Resource.messages.getString("dataRloadFail");
		}
		else {
			ruleMessage=Resource.messages.getString("dataRloadSuccess");
		}
		System.out.println(ruleMessage);
		this.ruleDatas=r;
		this.pbmPropList=r.getpbmPropList();
		this.rulesList=r.getrulesList();
		this.ruleslines=r.getAllRulelines();
		this.problemDatas=d;
	}

	public ArrayList<String> getRulesList() {
		return rulesList;
	}

	public String getProblemmessage() {
		// TODO Auto-generated method stub
		return problemMessage;
	}
	
	public String getRulemessage() {
		// TODO Auto-generated method stub
		return ruleMessage;
	}

	public void dataPloading(String f, boolean completeProperties) {
		boolean failUnzip=false;
		try {
			MyZip.unzip(new File(f), new File(Resource.path+'/'+Resource.messages.getString("dataPbmPath")));
			}
		catch (Exception e) 
			{
			failUnzip=true;
			}
		model.pbmData.ProblemDatas d=new model.pbmData.ProblemDatas();
		if(completeProperties){
			d.completePropertiesByNegation();
		}
		if (d.isError()||failUnzip){
			problemMessage=Resource.messages.getString("dataPloadFail");
		}
		else {
			problemMessage=Resource.messages.getString("dataPloadSuccess");
		}
		problemDatas=d;
		
	}

	public void dataSaving(String save, String folder) {
		problemMessage=Resource.messages.getString("dataPsaveSuccess");
		try {
			MyZip.zipAll( new File(folder),new File(save));
		}
		catch (Exception e) 
			{
			problemMessage=Resource.messages.getString("dataPsaveFail");
			}	
	}

	public void dataMloading(String f) {
		boolean failUnzip=false;
		try {
			MyZip.unzip(new File(f), new File(Resource.path+'/'+Resource.messages.getString("dataModelPath")));
			}
		catch (Exception e) 
			{
			failUnzip=true;
			}
		//model.pbmData.ProblemDatas d=new model.pbmData.ModelDatas();
		if (failUnzip){
			//ModelDatas=null;
			ModelMessage=Resource.messages.getString("dataMloadFail");
		}
		else {
			ModelMessage=Resource.messages.getString("dataMloadSuccess");
			//problemDatas=d;
		}
		
	}

	public void dataRloading(String f) {
		boolean failUnzip=false;
		try {
			MyZip.unzip(new File(f),  new File(Resource.path+'/'+Resource.messages.getString("dataRulesPath")));
			}
		catch (Exception e) 
			{
			failUnzip=true;
			}
		//model.pbmData.ProblemDatas d=new model.pbmData.ModelDatas();
		if (failUnzip){
			//ModelDatas=null;
			RulesMessage=Resource.messages.getString("dataRloadFail");
		}
		else {
			RulesMessage=Resource.messages.getString("dataRloadSuccess");
			//problemDatas=d;
		}
		
		
	}

	
	public ArrayList<String> getpbmPropList() {
		// TODO Auto-generated method stub
		return pbmPropList;
	}

	public void copyDatas() throws IOException {
		Filesmethods.copyFolder(new File(Resource.path+'/'+Resource.messages.getString("dataPbmPath")),
				new File(Resource.temppath+'/'+Resource.messages.getString("dataPbmPath")));		
	}
	public ProblemDatas getProblemDatas() {
		return problemDatas;
	}





}
