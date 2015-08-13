package vue.buttons;


import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

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
				 Iterator<String> it = messages.iterator();
				 String selectionmessage="";
				 while(it.hasNext()){
					 selectionmessage+=it.next()+"\n";
				 }
				 
				 window.getMessagesSelectionArea().setText(selectionmessage);	
			}
			 window.getmessageWindowRunLabel().setText(runmessage);
			 
			 window.revalidate();
	 	     window.repaint();
			}
		 }
	
}
