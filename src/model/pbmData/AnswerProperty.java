package model.pbmData;


public class AnswerProperty {
	/**
	 */
	private String id;
	/**
	 */
	private String name="";
	
	public AnswerProperty(String idgiven){
		id=idgiven;
	}
	public AnswerProperty(String idgiven, String namegiven){
		id=idgiven;
		name=namegiven;
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
}
