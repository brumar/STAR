package vue.buttons;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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




public class OpenModel extends AbstractAction {
	private MainWindow window;
	private JTextField filename = new JTextField(); 
	private JTextField	dir = new JTextField();
	public OpenModel(MainWindow window, String txt){
		super(txt);
		this.window =window;		
	}
	
	public void actionPerformed(ActionEvent e) { 
		//e.getActionCommand())
		File f=new File(Resource.clientpath+"/"+Resource.messages.getString("pathToUserModelMatrix"));
		try {
			Desktop.getDesktop().edit(f);//trial 1 : edit
		} catch (IOException e1) {
						
			try{
				Desktop.getDesktop().open(f);//trial 2 : open
			}
				 catch (IOException e2)
				 {
				 URI url;
				 try {
					url = new URI(Resource.clientpath);
					Desktop.getDesktop().browse(url); //trial 3 : browse
				 } catch (URISyntaxException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
						window.handleException(e3);
					 } catch (IOException e3) {
						 e3.printStackTrace();
						window.handleException(e3);
					}
					
				}
		 }
			

		}      

}
