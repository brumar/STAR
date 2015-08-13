package model.modelData;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import resources.Resource;

import model.tools.DoubleHashMap;
import model.tools.Readers;
import model.tools.Writers;


public class ModelDatas {
	private DoubleHashMap indexRule=new DoubleHashMap();//give the position of the Rule inside the  Model (boolean) Matrix



	private Model model;
	boolean error=false;
	
	public ModelDatas(){
		try{
			BuildStaticModel();
		} 
		catch (Exception e) {
			error=true;
			boolean[][] b = new boolean[5][5];
			model=new Model(b);
		} 
	}
	
	
	
	/**
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void BuildStaticModel() throws FileNotFoundException, IOException {
		
		boolean[][] b=Readers.buildBooleanMatrix(Resource.path+"/"+Resource.messages.getString("dataModelPath")+"/"+Resource.messages.getString("model"));
		model=new Model(b);
		indexRule.LoadProperties(Resource.path+"/"+Resource.messages.getString("dataModelPath")+"/"+Resource.messages.getString("hindexRule"));
		
	}
	
	public void createModelMatrix(boolean b, Set<String> rules) throws IOException {
		
		boolean[][] mat = createBooleanMatrix( b,rules.size());
		String[][] matS=createStringMatrix (mat,rules);
		Writers.writeStringMatrix(Resource.clientpath+"/"+Resource.messages.getString("pathToUserModelMatrix"),  matS);
		
		
	}



	private String[][] createStringMatrix(boolean[][] mat, Set<String> rules) {
		Integer size=rules.size();
		ArrayList<String> Rl=new ArrayList<String>(rules);
		String[][] matS = new String[size+1][size+1];
		matS[0][0]="";
		for (int i=0; i<size+1; i++ ){
			for (int j=0; j<size+1; j++ ){
				if(!((i==0)&&(j==0))){
					if(i==0){matS[i][j]=Rl.get(j-1);}
					else if(j==0){matS[i][j]=Rl.get(i-1);}
					else if(mat[i-1][j-1]){matS[i][j]="1";}
					else{matS[i][j]="0";}
					System.out.println(matS[i][j]);
				}
			}
			
		}
		return matS;
	}



	private boolean[][] createBooleanMatrix(boolean b, int size) {
		boolean[][] mat = new boolean[size][size];
		for (int i=0; i<size; i++ ){
			for (int j=0; j<size; j++ ){
				if(i==j){mat[i][j]=true;}else{mat[i][j]=b;}
			}
			
		}
		return mat;
	}



	public boolean isError() {
		// TODO Auto-generated method stub
		return error;
	}



	public boolean[][] getMatrix() {
		// TODO Auto-generated method stub
		return model.getMatrix();
	}
	public DoubleHashMap getIndexRule() {
		return indexRule;
	}



	public void loadMatrix(String string) throws IOException {
		ArrayList<ArrayList<String>> values=Readers.read(string);
		WritePropertyFile(values);
		boolean[][]mat=extractBooleanMatrix(values);
		Writers.writeBooleanMatrix(Resource.path+"/"+Resource.messages.getString("dataModelPath")+"/"+Resource.messages.getString("model"), mat);
		//HashMap<>
		
	}



	private boolean[][] extractBooleanMatrix(ArrayList<ArrayList<String>> values) {
		
		int size=values.size();
		boolean[][] mat = new boolean[size][size];
		Iterator<ArrayList<String>> it = values.iterator();
		int i=0;
		int j;
		while(it.hasNext()){
			ArrayList<String> lval=it.next();
			lval.remove(0);//remove the lateral header
			Iterator<String> it2 = lval.iterator();
			j=0;
			while(it2.hasNext()){
				String val=it2.next();
				boolean b=(val.equals("1"));
				mat[i][j]=b;
				j++;
			}
			i++;
		}
		
		
		return mat;
	}



	private void WritePropertyFile(ArrayList<ArrayList<String>> values) throws IOException {
		//ArrayList<String> listRules = values.get(0);
		Properties defaultProps = new Properties();
		Iterator<ArrayList<String>> it = values.iterator();
		int index=1;
		while(it.hasNext()){
			defaultProps.put(""+index, it.next().get(0));
			index++;
		}
		FileOutputStream out = new FileOutputStream(Resource.path+"/"
														+Resource.messages.getString("dataModelPath")+
														"/"+Resource.messages.getString("hindexRule"));//hindexRule
		defaultProps.store(out, "---No Comment---");
		
		
	}

	
}
