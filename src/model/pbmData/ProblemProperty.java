package model.pbmData;


public class ProblemProperty {
	/**
	 */
	private String id;
	/**
	 */
	private String name="";
	
	public  ProblemProperty(String idgiven){
		id=idgiven;
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
