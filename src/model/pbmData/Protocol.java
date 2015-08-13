package model.pbmData;

import java.util.HashMap;

public class Protocol {
	

	String id;
	Integer idSubjectSession;
	HashMap<String,String> ProbAnswerMap;
	
	public Protocol(Integer idp, HashMap<String, String> hashMap) {
		idSubjectSession=idp;
		ProbAnswerMap=hashMap;
		
	}
	public Integer getIdSubjectSession() {
		return idSubjectSession;
	}

	public HashMap<String, String> getProbAnswerMap() {
		return ProbAnswerMap;
	}
	
}
