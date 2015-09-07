package model.runModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import model.pbmData.ProblemDatas;
import model.pbmData.Protocol;
import resources.Resource;

public class ModelEvaluator {
	private ArrayList<boolean[]> ListofVector;
	private Integer[] CompatibilitiesNumber ;
	private String[] SpecialNotification;
	private int length;
	private ProblemDatas problemDatas;
	private Integer[] CompatibilitiesMaxNumber;
	private HashMap<String, Integer> problemIdToIndex;
	private boolean normalize;
	private double normalizeCoeff;
	
	public ModelEvaluator(ProblemDatas pbmDatas,ArrayList<boolean[]> arrayList, HashMap<String, Integer> pbmIdToIndex) {
		ListofVector=arrayList;
		problemIdToIndex=pbmIdToIndex;
		length=arrayList.size();
		CompatibilitiesNumber=new Integer[length];
		CompatibilitiesMaxNumber=new Integer[length];
		SpecialNotification=new String[length];
		countCompabilities(ListofVector);
		problemDatas=pbmDatas;
		this.normalize=Resource.messages.getString("normalizeEvaluation").equals("yes");
		this.normalizeCoeff=Double.parseDouble(Resource.messages.getString("coefficientNormalisation"));
	}

	private void countCompabilities(ArrayList<boolean[]> listofVector2) {		
		for(int i=0;i<length;i++){
			int compter=0;
			boolean[] bs = listofVector2.get(i);
			for(int j=0;j<bs.length;j++){
				if(bs[j])
				{
					compter++;
				}	
			}
			if(compter==bs.length){
				SpecialNotification[i]="FULL"; 
				// these special notifications avoid the 1-bit cost to indicate if the model is right or wrong
			}
			else if(compter==0){
				SpecialNotification[i]="VOID";
			}
			else{
				SpecialNotification[i]="VALID";
			}
			CompatibilitiesNumber[i]=compter;
			CompatibilitiesMaxNumber[i]=bs.length;
		}
	}

	public double evaluateProtocol(Protocol pro) {
		//printSumCompat();
		Iterator<String> it = pro.getProbAnswerMap().keySet().iterator();
		double descriptionLength=0;
		while(it.hasNext()){
			String ProblemId = it.next();
			int indexPbm=problemIdToIndex.get(ProblemId);
			ArrayList<String> answers = problemDatas.getProblemMap().get(ProblemId).getAnswersID();
			int indexAnswer = answers.indexOf(pro.getProbAnswerMap().get(ProblemId));
			descriptionLength+=evaluate(indexPbm,indexAnswer); // WHAAAAAAAAAT
		}
		return descriptionLength;
	}

	private void printSumCompat() {
		int sum=0;
		for (Integer c:CompatibilitiesNumber){
			sum+=c;
		}
		System.out.println(sum);
	}

	private double evaluate(int indexPbm,int indexAnswer) {
		double localdescriptionLength=0;
		double maxComp = (double)CompatibilitiesMaxNumber[indexPbm];
		if(SpecialNotification[indexPbm].equals("FULL")||SpecialNotification[indexPbm].equals("VOID")){
			localdescriptionLength = Math.log(maxComp);
		}
		else{
			double comp = (double)CompatibilitiesNumber[indexPbm];
			if(ListofVector.get(indexPbm)[indexAnswer]){
				localdescriptionLength=costProfileIndication(comp,maxComp,true)+Math.log(comp);
			}
			else{
				localdescriptionLength=costProfileIndication(comp,maxComp,false)+Math.log(maxComp-comp);
			}
		}
	
		return localdescriptionLength;
	}

	private double costProfileIndication(double comp, double maxComp, boolean success) {
		if(!normalize){
			return 1;
		}
		double pfail = (maxComp-comp)/(maxComp*(normalizeCoeff));
		double psuccess = 1-pfail;
		if(success){
			return(Math.log(1/psuccess));
		}
		else{
			return(Math.log(1/pfail));
		}
	}

	public double descriptionLengthNoModel(Protocol pro) {
		Iterator<String> it = pro.getProbAnswerMap().keySet().iterator();
		double description=0;
		while(it.hasNext()){
			String ProblemId = it.next();
			int indexPbm=problemIdToIndex.get(ProblemId);
			description+=Math.log((double)CompatibilitiesMaxNumber[indexPbm]);
		}
		return description;
	}

}
