package model.rulesData;

public class LogicalCondition {
	static int[] dimensions;
	public static int[] Datadimensions;
	boolean[][] matrix;
	String id;
	
	LogicalCondition(String idgiven){
		this.id=idgiven;
		this.matrix=new boolean[dimensions[0]][dimensions[1]];
	}
	LogicalCondition(String idgiven, boolean[][] b){
		this.id=idgiven;
		this.matrix=b;
	}
	public static int[] getDimensions() {
		return dimensions;
	}
	public static int[] getDatadimensions() {
		return Datadimensions;
	}
	public boolean[][] getMatrix() {
		return matrix;
	}
	public String getId() {
		return id;
	}
}
