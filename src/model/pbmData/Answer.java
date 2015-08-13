package model.pbmData;

import java.util.ArrayList;


public class Answer {
	/**
	 */
	private String id;
	/**
	 */
	private String name="";
	/**
	 */
	private ArrayList<String> AnswerPropertiesID=new ArrayList<String>();
	
	public Answer(String idgiven){
		id=idgiven;
	}
	public Answer(String idgiven, String namegiven){
		id=idgiven;
		name=namegiven;
	}
	public Answer(String idgiven, ArrayList<String> properties){
		id=idgiven;
		AnswerPropertiesID=properties;
	}
	

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

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	public void addIdAnswerProperty(String iDpropAnswer) {
		if (!this.hasProperty(iDpropAnswer)){
			this.AnswerPropertiesID.add(iDpropAnswer);
			}	
	}

	private boolean hasProperty(String iDpropAnswer) {
		return AnswerPropertiesID.contains(iDpropAnswer);
	}

	public ArrayList<String> getAnswerPropertiesID() {
		// TODO Auto-generated method stub
		return AnswerPropertiesID;
	}
}
