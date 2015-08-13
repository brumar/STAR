package model.pbmData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import resources.Resource;


public class ProblemDatas {

static boolean createOppositeProperties=false;
private  HashMap<String,Problem> ProblemMap=new HashMap<String,Problem>();
private  HashMap<String,Answer> AnswersMap=new HashMap<String,Answer>();
private  HashMap<String,AnswerProperty> PropertiesAnswersMap=new HashMap<String,AnswerProperty>();
private  HashMap<String,ProblemProperty> PropertiesProblemMap=new HashMap<String,ProblemProperty>();
private  ArrayList<Protocol> protocols= new ArrayList<Protocol>();
private boolean error=false;

	public ProblemDatas(){
		try{
			BuildProblemAndAnswerMap(Resource.path+"/"+Resource.messages.getString("dataPbmPath")+"/"+Resource.messages.getString("APSheet"));
			BuildPropertiesProblemMap(Resource.path+"/"+Resource.messages.getString("dataPbmPath")+"/"+Resource.messages.getString("PPSheet"));//"datas/PbmDatas/PropertiesProblem.csv");
			BuildPropertiesAnswersMap(Resource.path+'/'+Resource.messages.getString("dataPbmPath")+'/'+Resource.messages.getString("PASheet"));
			BuildProtocols(Resource.path+'/'+Resource.messages.getString("dataPbmPath")+'/'+Resource.messages.getString("ProtocoSheet"));
			if(createOppositeProperties){
				completePropertiesByNegation();
			}
		} 
		catch (Exception e) {
			error=true;		
		} 		
	}
	public int[] GiveDataDimensions(){
		Integer propAnsDim=PropertiesAnswersMap.size();
		Integer propPbmDim=PropertiesProblemMap.size();
		return new int[] {propAnsDim, propPbmDim};
	}


	private void BuildProblemAndAnswerMap(String path)throws FileNotFoundException, IOException {
		ArrayList<String> headers=new ArrayList<String>();
		headers.add("idAnswers");//headers.add(Resource.messages.getString("dataMloadSuccess"));
		headers.add("idPbm");
		/*
    	ClassLoader cl = this.getClass().getClassLoader();
    	InputStream is = cl.getResourceAsStream("datas/PbmDatas/AnswersPbm.csv");
    	Reader rd = new InputStreamReader(is);
    	CsvReader reader = new CsvReader(rd,";".charAt(0));*/
    	//reader.getHeaders();
    	ArrayList<ArrayList<String>> values = model.tools.Readers.read(headers, path);
    	//System.out.println(values);
    	ProblemAndAnswerMapping(values);
	}
	
	private void BuildPropertiesProblemMap(String path)throws FileNotFoundException, IOException {
		
		ArrayList<String> headers=new ArrayList<String>();
		headers.add("idPropertiesProblem");
		headers.add("idProblem");
		ArrayList<ArrayList<String>> values = model.tools.Readers.read(headers, path);
		PropertiesProblemMapping(values);
	}

	



	private void PropertiesProblemMapping(ArrayList<ArrayList<String>> values) {
		HashMap<String, Problem> Pmap=ProblemMap;
		HashMap<String, ProblemProperty> ProPmap=PropertiesProblemMap;
		Iterator<ArrayList<String>> it=values.iterator();
		while(it.hasNext()){
			ArrayList<String> l = it.next();
			String pbm=l.get(1);
			String Pproperty=l.get(0);
			if(!Pmap.containsKey(pbm)){//if the problem is not in the problem map
				Problem p = new Problem(pbm);
				ProblemProperty proP= new ProblemProperty(Pproperty);
				p.addProperty(Pproperty);
				Pmap.put(pbm, p);//update problem map
				ProPmap.put(Pproperty,proP);//update property map
			}
			else{
				Problem p=Pmap.get(pbm);//get the pbm inside the problem map
				p.addProperty(Pproperty);
				ProblemProperty proP= new ProblemProperty(Pproperty);
				ProPmap.put(Pproperty,proP);//update property map
			}				
		}		
		
	}


	private void ProblemAndAnswerMapping( ArrayList<ArrayList<String>> values) {
		HashMap<String, Problem> Pmap=ProblemMap;
		HashMap<String, Answer> Amap=AnswersMap;
		Iterator<ArrayList<String>> it=values.iterator();
		while(it.hasNext()){
			ArrayList<String> l = it.next();
			String pbm=l.get(1);
			String answer=l.get(0);
			if(!Pmap.containsKey(pbm)){//if the problem is not in the problem map
				Problem p = new Problem(pbm);
				Answer a= new Answer(answer);
				p.addIdAnswer(answer);
				Pmap.put(pbm, p);//update problem map
				Amap.put(answer,a);//update answer map
			}
			else{
				Problem p=Pmap.get(pbm);//get the pbm inside the problem map
				p.addIdAnswer(answer);//add the answer
				Answer a= new Answer(answer);//create an answer
				Amap.put(answer,a);//put it in the answer map
			}				
		}		
	}




	private  void BuildPropertiesAnswersMap(String path)throws FileNotFoundException, IOException {
		ArrayList<String> headers=new ArrayList<String>();
		headers.add("idPropertiesAnswer");
		headers.add("idAnswer");
		ArrayList<ArrayList<String>> values = model.tools.Readers.read(headers, path);
		PropertiesAnswerMapping(values);
	}

	private void BuildProtocols(String path) throws FileNotFoundException, IOException {	
		ArrayList<String> headers=new ArrayList<String>();//idAnswerSubject	idSession	idPbm	idAnswer
		headers.add("idSession");
		headers.add("idPbm");
		headers.add("idAnswer");
		ArrayList<ArrayList<String>> values = model.tools.Readers.read(headers, path);
		ProtocolsMapping(values);
	}


	private void ProtocolsMapping(ArrayList<ArrayList<String>> values) {
		// TODO Auto-generated method stub
		HashMap <Integer,HashMap<String,String>> hmapsProtocols = new HashMap<Integer,HashMap<String,String>>();
		
		Iterator<ArrayList<String>> it=values.iterator();
		while(it.hasNext()){
			ArrayList<String> l = it.next();
			Integer id=Integer.valueOf(l.get(0));
			if(hmapsProtocols.containsKey(id)){
				hmapsProtocols.get(id).put(l.get(1), l.get(2));
			}
			else{
				HashMap<String, String> hmapProtocolTemp = new HashMap<String,String>();
				hmapProtocolTemp.put(l.get(1), l.get(2));
				hmapsProtocols.put(id,hmapProtocolTemp);
			}
		}		
		Set<Integer> k = hmapsProtocols.keySet();
		Iterator<Integer> it2 = k.iterator();
		while(it2.hasNext()){
			Integer idp=it2.next();
			protocols.add(new Protocol(idp,hmapsProtocols.get(idp)));			
		}
	}





	private void PropertiesAnswerMapping(ArrayList<ArrayList<String>> values) {

		HashMap<String, Answer> Amap=AnswersMap;
		HashMap<String, AnswerProperty> ProAmap=PropertiesAnswersMap;
		
		Iterator<ArrayList<String>> it=values.iterator();
		while(it.hasNext()){
			ArrayList<String> l = it.next();
			String answer=l.get(1);
			String IDpropAnswer=l.get(0);
			if(!Amap.containsKey(answer)){//if the answer is not in the answer map
				Answer a= new Answer(answer);
				AnswerProperty Ap=new AnswerProperty(IDpropAnswer);
				a.addIdAnswerProperty(IDpropAnswer);
				Amap.put(answer,a);//update answer map
				ProAmap.put(IDpropAnswer, Ap);
				
			}
			else{
				Answer a=Amap.get(answer);
				AnswerProperty Ap=new AnswerProperty(IDpropAnswer);
				a.addIdAnswerProperty(IDpropAnswer);
				ProAmap.put(IDpropAnswer, Ap);
			}				
		}
	}


	public void print() {
		
		if(error){
			System.out.println("error");
		}
			else{
			
			Set<String> s=ProblemMap.keySet();
			Iterator<String> iter = s.iterator();
			while (iter.hasNext()){
				String value = iter.next();
				Problem p=ProblemMap.get(value);
				System.out.println("problem"+p.getId());
				System.out.println("answers : "+p.getAnswersID());
				System.out.println("properties : "+p.getProblemPropertiesID());
			}	
			Set<String> s2=AnswersMap.keySet();
			Iterator<String> iter2 = s2.iterator();
			while (iter2.hasNext()){
				String c= iter2.next();
				Answer a=AnswersMap.get(c);
				System.out.println("answer"+a.getId());
				System.out.println("properties : "+a.getAnswerPropertiesID());
			}
			
			System.out.println("list property answers");
			Set<String> s3=PropertiesAnswersMap.keySet();
			Iterator<String> iter3 = s3.iterator();
			while (iter3.hasNext()){
				String c= iter3.next();
				AnswerProperty a=PropertiesAnswersMap.get(c);
				System.out.println("answer"+a.getId());
			}	
			System.out.println("list property problems");
			Set<String> s4=PropertiesProblemMap.keySet();
			Iterator<String> iter4 = s4.iterator();
			while (iter4.hasNext()){
				String c= iter4.next();
				ProblemProperty a=PropertiesProblemMap.get(c);
				System.out.println("problem"+a.getId());
			}	
		}
	}
	public HashMap<String, Problem> getProblemMap() {
		return ProblemMap;
	}

	public HashMap<String, Answer> getAnswersMap() {
		return AnswersMap;
	}

	public HashMap<String, AnswerProperty> getPropertiesAnswersMap() {
		return PropertiesAnswersMap;
	}

	public HashMap<String, ProblemProperty> getPropertiesProblemMap() {
		return PropertiesProblemMap;
	}
	public boolean isError() {
		return error;
	}
	public ArrayList<Protocol> getProtocols() {
		return protocols;
	}
	public void completePropertiesByNegation() {/*
		private  HashMap<String,AnswerProperty> PropertiesAnswersMap=new HashMap<String,AnswerProperty>();
		private  HashMap<String,ProblemProperty> PropertiesProblemMap=new HashMap<String,ProblemProperty>();
		private  ArrayList<Protocol> protocols= new ArrayList<Protocol>();*/
		
		Set<String> propAnsKSet = PropertiesAnswersMap.keySet();//ProblemMap	
		Set<String> propPbmKSet = PropertiesProblemMap.keySet();//ProblemMap
		
		Set<String> pbmsId = ProblemMap.keySet();
		Iterator<String> itp = pbmsId.iterator();
		while(itp.hasNext()){
			Problem problem = ProblemMap.get(itp.next());
			Iterator<String> idppbm = propPbmKSet.iterator();
			while(idppbm.hasNext()){
				String p = idppbm.next();
				if(!problem.hasProperty(p)){
					problem.addProperty("ABS_OF_"+p);
				}
			}			
		}
		
		Set<String> ansId = AnswersMap.keySet();
		Iterator<String> ita = ansId.iterator();
		while(ita.hasNext()){
			Answer answer = AnswersMap.get(ita.next());
			Iterator<String> idpans = propAnsKSet.iterator();
			while(idpans.hasNext()){
				String a = idpans.next();
				if(!answer.hasProperty(a)){
					answer.addIdAnswerProperty("ABS_OF_"+a);
				}
			}			
		}
		
		Iterator<String> idpans2 = propAnsKSet.iterator();
		HashMap<String, AnswerProperty> PropertiesAnswersMapTemp = new HashMap<String,AnswerProperty>();
		HashMap<String, ProblemProperty> PropertiesProblemMapTemp = new HashMap<String,ProblemProperty>();
		
		while(idpans2.hasNext()){
			String ta = idpans2.next();
			String a = "ABS_OF_"+ta;
			AnswerProperty Ap=new AnswerProperty(a);
			PropertiesAnswersMapTemp.put(a, Ap);
		}
		PropertiesAnswersMap.putAll(PropertiesAnswersMapTemp);
		
		Iterator<String> idppbm2 = propPbmKSet.iterator();
		while(idppbm2.hasNext()){
			String tp = idppbm2.next();
			String p = "ABS_OF_"+tp;
			ProblemProperty Pp=new ProblemProperty(p);
			PropertiesProblemMapTemp.put(p, Pp);
		}
		PropertiesProblemMap.putAll(PropertiesProblemMapTemp);
		//print();
	}
	public static boolean isCreateOppositeProperties() {
		return createOppositeProperties;
	}
	public static void setCreateOppositeProperties(boolean createOppositeProperties) {
		ProblemDatas.createOppositeProperties = createOppositeProperties;
	}
}
