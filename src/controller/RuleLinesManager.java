package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import resources.Resource;

import vue.buttons.SelectRuleAction;

import model.rulesData.Line;
import model.tools.DoubleHashMap;
import model.tools.Filesmethods;
import model.tools.Writers;

public class RuleLinesManager {

	private HashMap<String, ArrayList<Line>> ruleslines;
	private ArrayList<String> propPbmList;
	private DoubleHashMap indexPropertyAnswer;
	private DoubleHashMap indexPropertyPbm;
	private int limitePropPbm;

	public RuleLinesManager(HashMap<String,ArrayList<Line>> hashMap, ArrayList<String> propPbmList, DoubleHashMap doubleHashMapA, DoubleHashMap doubleHashMapP){
		this.ruleslines=hashMap;
		this.propPbmList=propPbmList;
		this.indexPropertyPbm=doubleHashMapP;
		this.indexPropertyAnswer=doubleHashMapA;
		this.limitePropPbm=indexPropertyPbm.list_me().size();
	}
	
	public Set<String> getActiveRules(){
		ruleslines.keySet();
		return ruleslines.keySet();
	}

	public void AddLine(String id, ArrayList<Line> lines) {
		ruleslines.put(id, lines);	
	}

	public void deleteLine(String id) {
		ruleslines.remove(id);	
	}

	public ArrayList<Line> getLines(String selectedValue) {
		// TODO Auto-generated method stub
		return ruleslines.get(selectedValue);
		
	}

	public boolean changeLine(String selectedRule, String legend, String line,
		String panelLegend,String property) {
		ArrayList<Line> lines = ruleslines.get(selectedRule);
		boolean newl=false;
		if(panelLegend.equals("proPbmPane")){
			int index=Integer.valueOf(line)-1;
			Line oldline = lines.get(index);	
			Line newline = null;		
			if (legend.equals("pass")){
				newline = new Line(property,propPbmList,true,false);
				newl=true;
			}
			else if(legend.equals("block")){
				newline = new Line(property,new ArrayList<String>(),false,true);
				newl=true;
			}
			if(newl){
				lines.remove(oldline);
				lines.add(index, newline);
				ruleslines.remove(selectedRule);
				ruleslines.put(selectedRule, lines);
			}
		}
		
		if(panelLegend.contains("dependancies_")){
			int beginIndex="dependancies_".length();
			String s=panelLegend.substring(beginIndex);
			int index=Integer.valueOf(s)-1;
			
			Line oldline = lines.get(index);
			
			ArrayList<String> oldpbm = (ArrayList<String>) oldline.getListPropbm().clone();
			Line newline = null;
			if (legend.equals("pass")){			
				if(!oldpbm.contains(property)){
					oldpbm.add(property);
					newl=true;
				}
			}
			else if(legend.equals("block")){
				if(oldpbm.contains(property)){
					oldpbm.remove(property);
					newl=true;		
				}
			}
			if(newl){
				boolean isnone=(oldpbm.isEmpty());
				boolean isfull=(oldpbm.size()==limitePropPbm);
				
				//System.out.println(oldline.getIdProanswer());
				newline = new Line(oldline.getIdProanswer(),oldpbm,isfull,isnone);
				lines.remove(oldline);
				lines.add(index, newline);
				ruleslines.remove(selectedRule);
				ruleslines.put(selectedRule, lines);
				
				//System.out.println("remplacement de la ligne "+ index+ " portant sur " + oldline.getIdProanswer() +"");
			}
		}
		
		/*
		 * 
		 * 		private Line(String id,ArrayList<String> lis,boolean f,boolean n){
			this.idProanswer=id;
			this.listPropbm=lis;
			this.full=f;
			this.none=n;
		}
		 */

		return newl;
	}
	public ArrayList<Line> NewFullLines(){
		ArrayList<Line> Lines=new ArrayList<Line>();		
		List<String> l=indexPropertyAnswer.list_me();
		Iterator<String> it = l.iterator();
		while(it.hasNext()){
			String s=it.next();		
			List<String> l2=indexPropertyPbm.list_me();
			Iterator<String> it2 = l2.iterator();
			ArrayList<String> line =new ArrayList<String>();
			while(it2.hasNext()){
				String s2 = it2.next();
				line.add(s2);
				int j = indexPropertyPbm.find(s2);																
			}
			Lines.add(new Line(s,line,true,false));
		}
		return Lines;
	}
	
	public boolean[][] createMatrix(String idRule) {
		ArrayList<Line> lines = ruleslines.get(idRule);
		int t1 = indexPropertyAnswer.list_me().size();
		int t2 = indexPropertyPbm.list_me().size();
		boolean[][] mat = new boolean[t1][t2];
		Iterator<Line> it = lines.iterator();
		while(it.hasNext()){
			Line line = it.next();
			int index1 = indexPropertyAnswer.find(line.getIdProanswer());
			Iterator<String> it2 = propPbmList.iterator();
			while(it2.hasNext()){
				String pro = it2.next();
				int index2=indexPropertyPbm.find(pro);
				mat[index1][index2]=(line.getListPropbm().contains(pro));
				//System.out.println(mat[index1][index2]);
			}
		}
		/*try{
		Writers.writeBooleanMatrix("test.csv",mat);}
		catch(Exception e){
			
		}*/
		return mat;
		
	}

	public void rewriteRules() {
		try{
			Set<String> k = ruleslines.keySet();
			Iterator<String> it = k.iterator();
			Filesmethods.copyFile(Resource.path+'/'+Resource.messages.getString("dataRulesPath")+'/'+Resource.messages.getString("hmapPA"),
					Resource.temppath+'/'+Resource.messages.getString("dataRulesPath")+'/'+Resource.messages.getString("hmapPA"));
			Filesmethods.copyFile(Resource.path+'/'+Resource.messages.getString("dataRulesPath")+'/'+Resource.messages.getString("hmapPP"),
					Resource.temppath+'/'+Resource.messages.getString("dataRulesPath")+'/'+Resource.messages.getString("hmapPP"));
			while(it.hasNext()){
				String s = it.next();
				boolean[][] b = createMatrix(s);
				String file = Resource.temppath+'/'+Resource.messages.getString("dataRulesPath")+'/'+s+".csv";
				Writers.writeBooleanMatrix(file,b);
				}	
			}
			catch(Exception e){
			e.printStackTrace();
			}
	}


}
