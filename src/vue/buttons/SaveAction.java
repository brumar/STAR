package vue.buttons;


import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import resources.Resource;

import model.tools.Filesmethods;

import vue.windows.MainWindow;

public class SaveAction extends AbstractAction {
	private MainWindow window;
	
	
	public SaveAction(MainWindow window, String texte){
		super(texte);
		this.window = window;
	}
	
	public void actionPerformed(ActionEvent e) { 
		JButton button=(JButton) e.getSource();
		String source=button.getName();
		String filterDescription="";
		String filterValue="";
		String SaveFolder="";
		String RegularFolder="";
		
		if(source.equals("savedatas")){
			filterDescription="Problems database (.dataP)";
			filterValue="dataP";
			SaveFolder=Resource.temppath+'/'+Resource.messages.getString("dataPbmPath")+'/';
			RegularFolder=Resource.path+'/'+Resource.messages.getString("dataPbmPath")+'/';
		}
		else if(source.equals("savemodel")){
			filterDescription="model database (.dataM)";
			filterValue="dataM";
			SaveFolder=Resource.temppath+'/'+Resource.messages.getString("dataModelPath")+'/';
			RegularFolder=Resource.path+'/'+Resource.messages.getString("dataModelPath")+'/';
		}
		else if(source.equals("saverules")){
			filterDescription="rules database (.dataR)";
			filterValue="dataR";
			//SaveFolder="datas/RulesDatas/";
			SaveFolder=Resource.temppath+'/'+Resource.messages.getString("dataRulesPath")+'/';
			RegularFolder=Resource.path+'/'+Resource.messages.getString("dataRulesPath")+'/';
		}
		
		JFileChooser c = new JFileChooser("./");
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter(filterDescription, filterValue);
		c.addChoosableFileFilter(filter);
		c.setFileFilter(filter);
		 int rVal = c.showSaveDialog(null);
	      
	      if (rVal == JFileChooser.APPROVE_OPTION) {
	    	  //prepare the temp folder
	    	  
		    	try {
					Filesmethods.deleteFolder(SaveFolder,"");//delete the previous content present in the temp folder
					
				//write into the temp folder - start 
					
		    	if(filterValue.equals("dataR")){
		    		window.getLinesmanager().rewriteRules();
					window.getdatas().getRuleDatas().getIndexPropertyAnswer().saveMaps(RegularFolder+"/"+Resource.messages.getString("hmapPA"));
					window.getdatas().getRuleDatas().getIndexPropertyPbm().saveMaps(RegularFolder+Resource.messages.getString("hmapPP"));
			
		    		window.setRuleSaved(true);
		    	}
		    	if(filterValue.equals("dataP")){
		    		window.getdatas().copyDatas();
		    	}
		     	if(filterValue.equals("dataM")){
		     		window.getModelManager().copyDatas();
		     		window.setmodelSaved(true);
		    	}
		    	} catch (IOException e1) {
					e1.printStackTrace();
				}//
		    	
		    	//write into the temp folder - end
		    	
		        String fname=c.getSelectedFile().getName();
		        if(!fname.contains("."+filterValue)){
		        	fname+="."+filterValue;}
			       
		        window.getdatas().dataSaving(fname,SaveFolder);//ZIP the temp folder into a .dataX file
		    
			    String mess=window.getdatas().getProblemmessage();
			    window.getmessageWindowDataLabel().setText(fname+ mess);
			    
			    rewriteRegularDirectory(SaveFolder,RegularFolder);
			    window.loadContent();		      
		      } 
		 }

	private void rewriteRegularDirectory(String newContent, String oldDirectory) {
		try {
			Filesmethods.deleteFolder(oldDirectory,"");
			Filesmethods.copyFolder(new File(newContent), new File(oldDirectory));
	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
