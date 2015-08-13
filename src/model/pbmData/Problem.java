package model.pbmData;

import java.util.ArrayList;


public class Problem {
	

	private String id;
	private String name="";
	private ArrayList<String> AnswersID=new ArrayList<String>();
	private ArrayList<String> ProblemPropertiesID=new ArrayList<String>();
	
	
	public  Problem(String idgiven){
		id=idgiven;
	}
	
	public boolean hasAnswer(String id){
		return AnswersID.contains(id);
	}
	
	public boolean hasProperty(String idgiven){
		return ProblemPropertiesID.contains(idgiven);
	}
	
	
	//setters and getters

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getAnswersID() {
		return AnswersID;
	}

	public void setAnswersID(ArrayList<String> answersID) {
		AnswersID = answersID;
	}

	public ArrayList<String> getProblemPropertiesID() {
		return ProblemPropertiesID;
	}

	public void setProblemPropertiesID(ArrayList<String> problemPropertiesID) {
		ProblemPropertiesID = problemPropertiesID;
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	public void addIdAnswer(String Idanswer) {
		
		if (!this.hasAnswer(Idanswer)){
			this.AnswersID.add(Idanswer);
			}
		}

	public void addProperty(String IdproP) {
		if (!this.hasProperty(IdproP)){
			this.ProblemPropertiesID.add(IdproP);
			}
		}

}
