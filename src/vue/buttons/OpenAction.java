package vue.buttons;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.SwingUtilities;

import resources.Resource;

import model.tools.Filesmethods;

import controller.DataManager;

import vue.windows.MainWindow;




public class OpenAction extends AbstractAction {
	private MainWindow window;
	private JTextField filename = new JTextField(); 
	private JTextField	dir = new JTextField();
	public OpenAction(MainWindow window, String txt){
		super(txt);
		this.window =window;		
	}
	
	public void actionPerformed(ActionEvent e) { 
		//e.getActionCommand())
			JButton button=(JButton) e.getSource();
			String source=button.getName();
			String filterDescription="";
			String filterValue="";
			
			if(source.equals("opendatas")){
				filterDescription="Problems database (.dataP)";
				filterValue="dataP";
			}
			else if(source.equals("openmodel")){
				filterDescription="model database (.dataM)";
				filterValue="dataM";
			}
			else if(source.equals("openrules")){
				filterDescription="rules database (.rulesR)";
				filterValue="dataR";
			}
			
			JFileChooser c = new JFileChooser("./");
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter(filterDescription, filterValue);
			c.addChoosableFileFilter(filter);
			c.setFileFilter(filter);		
			int rVal = c.showOpenDialog(null);
			if (rVal == JFileChooser.APPROVE_OPTION) {
		    	String fname=c.getSelectedFile().getName();
		        filename.setText(fname);
		        String dirString=(c.getCurrentDirectory().toString()).replace('\\','/');
		        dir.setText(dirString);   
	      if(fname.substring(fname.lastIndexOf('.')+1).equals(filterValue)){
	    	  
	    	  if(source.equals("opendatas")){    		  
	    		  window.getdatas().dataPloading(dirString+'/'+fname);
	    		  String mess=window.getdatas().getProblemmessage();
	    		  window.getmessageWindowDataLabel().setText(fname+ mess);
	    	  }
	    	  
	    	  if(source.equals("openmodel")){
	  			window.setModelEdited(false);
				window.setModelUpdated(false);
	    		  
	    		  window.getdatas().dataMloading(dirString+'/'+fname);
	    		  String mess=window.getdatas().getProblemmessage();
	    		  window.getmessageWindowModelLabel().setText(fname+ mess);
	    		  }
	    	  if(source.equals("openrules")){ 
	    		  try 
	    		  {
					Filesmethods.deleteFolder(Resource.path+'/'+Resource.messages.getString("dataRulesPath")+'/',"");//Resource.path+'/'+Resource.messages.getString("dataRulesPath")+'/'
	    		  } catch (IOException e1) 
	    		  {
	    			 window.handleException(e1);
					e1.printStackTrace();
	    		  }
	    		  window.getdatas().dataRloading(dirString+'/'+fname);//unzip the .dataR inside the regular directory
	    		  String mess=window.getdatas().getRulemessage();
	    		  window.getmessageWindowRulesLabel().setText(fname+ mess);
	    	      window.loadContent();//reload all the datas
	    	      
	    	      //refresh pannel - start
	    	      Component[] oldcompos = window.getSelectrulename().getComponents();
	    	      Iterator<String> iter = window.getCurrentRulesList().iterator();
	    	       window.getSelectrulename().removeAllItems();
	    	       while(iter.hasNext()){
	    	        	window.getSelectrulename().addItem(iter.next());
	    	       }
	    	       List<Component> listcompos = Arrays.asList(oldcompos);
	    	       //refresh pannel - end 
	    	        
	    	      window.revalidate();
	    	      window.repaint();
	    	  
	    	  
	    	  }
	    	     	  
	      }

	    }
	}      

}
