package model.rulesData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.tools.Readers;

public class Rule {

	private String id;
	private LogicalCondition condition;

	public Rule(String idgiven){
		this.id=idgiven;
	}
	public Rule(String idgiven, boolean[][] matrix){
		this.id=idgiven;
		this.condition=new LogicalCondition(idgiven,matrix);//in this case the rule and the condition share the same id
	}
	public Rule(String idgiven, String csvfile) throws FileNotFoundException, IOException {
		boolean[][] b=Readers.buildBooleanMatrix(csvfile);
		this.id=idgiven;
		this.condition=new LogicalCondition(idgiven,b);
	}
	public String getId() {
		return id;
	}
	public LogicalCondition getCondition() {
		return condition;
	}
}