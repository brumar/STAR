package model.runModel;

import java.util.ArrayList;
import java.util.Iterator;

import model.pbmData.ProblemDatas;
import model.pbmData.Protocol;

public class ModelEvaluator {
	private ArrayList<boolean[]> ListofVector;
	private Integer[] CompatibilitiesNumber ;
	private String[] SpecialNotification;
	private int length;
	private ProblemDatas problemDatas;
	private Integer[] CompatibilitiesMaxNumber;
	
	public ModelEvaluator(ProblemDatas pbmDatas,ArrayList<boolean[]> arrayList) {
		ListofVector=arrayList;
		length=arrayList.size();
		CompatibilitiesNumber=new Integer[length];
		CompatibilitiesMaxNumber=new Integer[length];
		SpecialNotification=new String[length];
		countCompabilities(ListofVector);
		problemDatas=pbmDatas;
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
		Iterator<String> it = pro.getProbAnswerMap().keySet().iterator();
		int indexPbm = 0;
		double descriptionLength=0;
		while(it.hasNext()){
			String ProblemId = it.next();
			ArrayList<String> answers = problemDatas.getProblemMap().get(ProblemId).getAnswersID();
			int indexAnswer = answers.indexOf(pro.getProbAnswerMap().get(ProblemId));
			descriptionLength+=evaluate(indexPbm,indexAnswer);
			indexPbm++;
		}
		return descriptionLength;
	}

	private double evaluate(int indexPbm,int indexAnswer) {
		double localdescriptionLength=0;
		if(SpecialNotification[indexPbm].equals("FULL")||SpecialNotification[indexPbm].equals("VOID")){
			localdescriptionLength = Math.log((double)CompatibilitiesMaxNumber[indexPbm]);
		}
		else{
			if(ListofVector.get(indexPbm)[indexAnswer]){
				localdescriptionLength=1+Math.log((double)CompatibilitiesNumber[indexPbm]);
			}
			else{
				localdescriptionLength=1+Math.log(
						(double)CompatibilitiesMaxNumber[indexPbm]-(double)CompatibilitiesNumber[indexPbm]);
			}
		
		}
	
		return localdescriptionLength;
	}

	public double descriptionLengthNoModel() {
		double description=0;
		for(int i=0;i<length;i++){
			description+=Math.log(CompatibilitiesMaxNumber[i]);
		}	
		return description;
	}

}
