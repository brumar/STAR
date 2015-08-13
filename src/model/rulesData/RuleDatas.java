package model.rulesData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import resources.Resource;

import model.pbmData.Answer;
import model.pbmData.AnswerProperty;
import model.pbmData.Problem;
import model.pbmData.ProblemDatas;
import model.pbmData.ProblemProperty;
import model.rulesData.Line;
import model.tools.DoubleHashMap;


public class RuleDatas {

	private HashMap<String,Rule> rulesMap=new HashMap<String,Rule>() ;//binding the rules to their ids
	private HashMap<String,LogicalCondition> conditionsMap=new HashMap<String,LogicalCondition>() ;//binding the conditions to their id's
	private DoubleHashMap indexPropertyPbm=new model.tools.DoubleHashMap();//give the position of the propertyPbm inside the logical condition  matrix
	private DoubleHashMap indexPropertyAnswer=new model.tools.DoubleHashMap();//give the position of the propertyAnswer inside the logical condition  matrix
	private boolean error;
	private ArrayList<String> getpbmPropList=new ArrayList<String>();
	private ArrayList<String> rulesList=new ArrayList<String>();
	
	public RuleDatas(ProblemDatas d){//(model.pbmData.ProblemDatas d){
		//LogicalCondition.Datadimensions=d.GiveDataDimensions();
		//BuildIndexPbmMap(d.getProblemMap());
		//BuildIndexAnswerMap(d.getAnswersMap());
		
		try{
			rulesMap=buildRulesMap(Resource.path+'/'+Resource.messages.getString("dataRulesPath"));//Resource.path+'/'+Resource.messages.getString("dataRulesPath")
			String path1=Resource.path+'/'+Resource.messages.getString("dataRulesPath")+'/'+Resource.messages.getString("hmapPP");
			indexPropertyPbm.LoadProperties(path1);//Resource.path+'/'+Resource.messages.getString("hmapPP")
			indexPropertyAnswer.LoadProperties(Resource.path+'/'+Resource.messages.getString("dataRulesPath")+'/'+Resource.messages.getString("hmapPA"));//Resource.path+'/'+Resource.messages.getString("hmapPA")
			getpbmPropList=turnKeysToList(indexPropertyPbm);
			rulesList=turnKeysToList(rulesMap);
		} 
		catch (Exception e) {
			if(!d.isError()){
				try{
					buildFromDatas(d);
				/*
				
								datas.getRuleDatas().getAllRulelines(),
				datas.getpbmPropList(),
				datas.getRuleDatas().getIndexPropertyAnswer(),
				datas.getRuleDatas().getIndexPropertyPbm());
				*/
				}
				catch (Exception e2) {
					error=true;
				}
			}
			error=true;
		} 
	}

	private void buildFromDatas(ProblemDatas d) {
		getpbmPropList=turnKToList(d.getPropertiesProblemMap());//ugly beacause overcharge was not working
		ArrayList<String> ansPropList = turnK2ToList(d.getPropertiesAnswersMap());
		indexPropertyPbm.putlist(getpbmPropList);
		indexPropertyAnswer.putlist(ansPropList);
/*
 * 	public void putlist(List<String> List) {
		int l=List.size();
		for (int i=0;i<l;i++){
			put(i,List.get(i)); 
		}
	}
 */
		
	}

	private ArrayList<String> turnK2ToList(HashMap<String, AnswerProperty> propertiesAnswersMap) {
		ArrayList<String> l = new ArrayList<String>(propertiesAnswersMap.keySet());
		return l;
	}

	private ArrayList<String> turnKToList(HashMap<String, ProblemProperty> propertiesProblemMap) {
		ArrayList<String> l = new ArrayList<String>(propertiesProblemMap.keySet());
		return l;
	}

	private ArrayList<String> turnKeysToList(HashMap<String, Rule> rulesMap2) {
		ArrayList<String> l = new ArrayList<String>(rulesMap2.keySet());
		return l;
	}

	private ArrayList<String> turnKeysToList(DoubleHashMap indexProperty) {
		List<String> ltemp = indexProperty.list_me();
		ArrayList<String> l = new ArrayList<String>(ltemp);
		return l;
	}

	private HashMap<String, Rule> buildRulesMap(String f) throws FileNotFoundException, IOException {
		HashMap<String, Rule> map=new HashMap<String, Rule>();
		File folder=new File(f);
		String[] dirlist=folder.list();
		int l=dirlist.length;
		for(int i=0;i<l;i++){
			String fname=dirlist[i];
			if(fname.substring(fname.lastIndexOf('.')+1).equals("csv")){
				
				String id=fname.substring(0,fname.lastIndexOf('.'));
				String csvfile=folder+"/"+dirlist[i];
				Rule r=new Rule(id,csvfile);
				map.put(id, r);
				
			}
			
		}
		return map;
	}

	//private void BuildRulesMap() {
		// TODO Auto-generated method stub
		
	//}


	public ArrayList<Line> getLines(String ruleid){
		
		Rule r = rulesMap.get(ruleid);
		ArrayList<Line> Lines=new ArrayList<Line>();
		
		List<String> l=indexPropertyAnswer.list_me();
		Iterator<String> it = l.iterator();
		while(it.hasNext()){
			boolean full=true;//this boolean is usefull to determine if the entire raw is full of 1
			boolean none=true;//this boolean is usefull to determine if the entire raw is full of 0
			String s = it.next();
			int i = indexPropertyAnswer.find(s);
			
			List<String> l2=indexPropertyPbm.list_me();
			Iterator<String> it2 = l2.iterator();
			ArrayList<String> line =new ArrayList<String>();
			while(it2.hasNext()){
				String s2 = it2.next();
				int j = indexPropertyPbm.find(s2);
				boolean b = r.getCondition().getMatrix()[i][j];
				if(b){
					none=false;
					line.add(s2);}
				else{
					full=false;}				
													
			}
			Lines.add(new Line(s,line,full,none));
		}
		return Lines;
	}
	
	public void print(){			
			if(error){
				System.out.println("error");
			}
			else{				
				Set<String> s=rulesMap.keySet();
				Iterator<String> iter = s.iterator();
				while (iter.hasNext()){
					String value = iter.next();
					Rule r=rulesMap.get(value);
					
					System.out.println("rule : "+r.getId());
					System.out.println("matrix : "+r.getCondition().getMatrix());
					//System.out.println("properties : "+r.getProblemPropertiesID());
				}	
				List<String> s2=indexPropertyAnswer.list_me();
				Iterator<String> iter2 = s2.iterator();
				while (iter2.hasNext()){
					String c= iter2.next();
					System.out.println(c);
				}
				
		
		}	
			
	}
	public boolean isError() {
		// TODO Auto-generated method stub
		return error;
	}

	public ArrayList<String> getpbmPropList() {
		// TODO Auto-generated method stub
		return getpbmPropList;
	}

	public ArrayList<String> getrulesList() {
		// TODO Auto-generated method stub
		return rulesList;
	}

	public HashMap<String, ArrayList<Line>> getAllRulelines() {
		HashMap<String, ArrayList<Line>> h = new HashMap<String,ArrayList<Line>>();
		Iterator<String> iter = rulesList.iterator();
		while(iter.hasNext()){
			String s=iter.next();
			ArrayList<Line> l = getLines(s);
			h.put(s, l);
		}
		return h;
	}
	public DoubleHashMap getIndexPropertyPbm() {
		return indexPropertyPbm;
	}

	public DoubleHashMap getIndexPropertyAnswer() {
		return indexPropertyAnswer;
	}
	public HashMap<String, LogicalCondition> getConditionsMap() {
		return conditionsMap;
	}
	public HashMap<String, Rule> getRulesMap() {
		return rulesMap;
	}
	
}
