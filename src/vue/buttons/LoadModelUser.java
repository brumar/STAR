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




public class LoadModelUser extends AbstractAction {
	private MainWindow window;
	private JTextField filename = new JTextField(); 
	private JTextField	dir = new JTextField();
	public LoadModelUser(MainWindow window, String txt){
		super(txt);
		this.window =window;		
	}
	
	public void actionPerformed(ActionEvent e) { 
		if(window.isModelEdited()){window.setModelUpdated(true);}
		try {
			window.getModelManager().loadUserMatrix(Resource.clientpath+"/"+Resource.messages.getString("pathToUserModelMatrix"));
			
		} catch (Exception e1) {
			if(e1 instanceof FileNotFoundException){ 
				window.handleException(Resource.messages.getString("notFound"));}
			else {window.handleException(e1);}
		}
		window.loadContent();
		window.revalidate();
	     window.repaint();

		}      

}
