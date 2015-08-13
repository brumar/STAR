package controller;

import java.io.File;
import java.io.IOException;

import resources.Resource;

import model.modelData.ModelDatas;
import model.tools.Filesmethods;

public class ModelManager {
	private String modelMessage;

	private ModelDatas modelDatas;
	public ModelManager(){
		ModelDatas m = new ModelDatas();//load the csv present in the folder
		if (m.isError())
		{
			modelMessage=Resource.messages.getString("dataMloadFail");
		}
		else {
		modelMessage=Resource.messages.getString("dataMloadSuccess");
	}
		this.modelDatas=m;
}
	public String getModelMessage() {
		return modelMessage;
	}
	public void copyDatas() throws IOException {
		Filesmethods.copyFolder(new File(Resource.path+'/'+Resource.messages.getString("dataModelPath")),
				new File(Resource.temppath+'/'+Resource.messages.getString("dataModelPath")));	
		
	}
	public ModelDatas getModelDatas() {
		return modelDatas;
	}
	public void loadUserMatrix(String string) throws IOException {
		//Filesmethods.deleteFolder(Resource.path+'/'+Resource.messages.getString("dataModelPath"), "");
		modelDatas.loadMatrix(string);
		
	}

}
