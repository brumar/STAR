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
import java.util.Set;

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




public class LoadModel extends AbstractAction {
	private MainWindow window;
	private JTextField filename = new JTextField(); 
	private JTextField	dir = new JTextField();
	public LoadModel(MainWindow window, String txt){
		super(txt);
		this.window =window;		
	}
	
	public void actionPerformed(ActionEvent e) { 
		//e.getActionCommand())
			window.setModelEdited(true);
			window.setModelUpdated(true);
			
			JButton button=(JButton) e.getSource();
			String source=button.getName();
			Set<String> rules = window.getLinesmanager().getActiveRules();
			boolean b=false;
			if(source.equals("BestCombinaison")){
				b=true;
			}
			
			try {
				window.getModelManager().getModelDatas().createModelMatrix(b, rules);
				window.getModelManager().loadUserMatrix(Resource.clientpath+"/"+Resource.messages.getString("pathToUserModelMatrix"));
				
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				if(e1 instanceof FileNotFoundException){ 
					window.handleException(Resource.messages.getString("notFound"));}
				else {window.handleException(e1);}
			}
			window.loadContent();
			window.revalidate();
   	      	window.repaint();
			

		}      

}
