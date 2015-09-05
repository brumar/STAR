package model.runModel;

import java.util.ArrayList;
import java.util.Iterator;

import model.pbmData.Protocol;
import model.tools.DoubleHashMap;

import resources.Resource;

public class EvaluationOutput {

	private ArrayList<Integer> possList;
	private Protocol pro;
	private double modeldescriptionLength;
	private double withoutModeldescriptionLength;
	private DoubleHashMap indexRule;
	private String defaultMessage;
	private double pvalue;

	
	public EvaluationOutput(ArrayList<Integer> possList, Protocol pro, double modeldescriptionLength,
			double withoutModeldescriptionLength, DoubleHashMap indexRule) {
		this.possList=possList;
		this.pro=pro;
		this.modeldescriptionLength=modeldescriptionLength;
		this.withoutModeldescriptionLength=withoutModeldescriptionLength;
		this.indexRule=indexRule;
		this.defaultMessage="ok";

		/*
		 * 
		 * normalizeEvaluation=yes
coefficientNormalisation=2
		 */
		// TODO Auto-generated constructor stub
	}
	
	public EvaluationOutput(String string) {
		this.defaultMessage = string;
	}

	public String writeMessage() {
		if(this.defaultMessage.equals("error")){
			return "error";
		}
		String mess="\n The subject ";
		mess+=pro.getIdSubjectSession();
		mess+=" has been best simulated  by ";
		Iterator<Integer> it = possList.iterator();
		while(it.hasNext()){
			mess+=indexRule.find(it.next()+1)+" , ";
		}
		mess+="\nwith a description length of : "+(Math.floor(modeldescriptionLength*100)/100)+" bits";
		mess+="\n( description length without any model : "+(Math.floor(withoutModeldescriptionLength*100)/100)+"  bits)";
		mess+="\n+pvalue : "+pvalue;
		return mess;
	}

	public Double getCompression() {
		// TODO Auto-generated method stub
		return (modeldescriptionLength-withoutModeldescriptionLength);
	}

	public void setPermutationPvalue(double p) {
		this.pvalue=p;
		
	}

}
