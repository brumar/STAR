package vue.buttons;


import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import model.tools.Writers;

import resources.Resource;


import vue.windows.MainWindow;

public class RunAction extends AbstractAction {
	private MainWindow window;
	
	public RunAction(MainWindow window, String texte){
		super(texte);
		this.window = window;
	}
	
	public void actionPerformed(ActionEvent e) { 
		
		if(!window.isRunnable()){
			window.handleException(window.getRunStatus());
		}
		else{		
			window.loadContent();//reload the content 
			 try {
				window.getRunmanager().run();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				window.handleException(e1);
			}
			 String runmessage=window.getRunmanager().getRunmessage();
			 if(!runmessage.equals("error")){
				 ArrayList<String> messages=window.getRunmanager().getSelectionMessages();
				 String outputFile = Resource.messages.getString("outputFile");			 
				 Writers.createTextFileWithLines(messages,outputFile);			 
				 window.getMessagesSelectionArea().setText("done, check "+outputFile);	
			}
			 window.getmessageWindowRunLabel().setText(runmessage);
			 
			 window.revalidate();
	 	     window.repaint();
			}
		 }
	
}
