package vue.buttons;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.SwingUtilities;

import model.rulesData.Line;

import controller.DataManager;

import vue.windows.MainWindow;




public class CreateDeleteRule extends AbstractAction {
	private MainWindow window;
	private JTextField filename = new JTextField(); 
	private JTextField	dir = new JTextField();
	public  CreateDeleteRule(MainWindow window, String txt){
		super(txt);
		this.window =window;		
	}
	
	public void actionPerformed(ActionEvent e) { 
		window.setRuleSaved(false);
		window.setModelUpdated(false);
		Component s = (Component) e.getSource();
		if(s.getName().equals("new")){
			String id=(window.getNewrulename().getText());
			ArrayList<Line> lines = window.getLinesmanager().NewFullLines();
			window.getLinesmanager().AddLine(id,lines);
			new SelectRuleAction(window,"").updateWindow(window.getLinesmanager().getLines(id),id);
			window.getSelectrulename().addItem(id);
			window.getSelectrulename().setSelectedItem(id);
		}
		if(s.getName().equals("delete")){
			String id=window.getSelectrulename().getSelectedItem().toString();
			//ArrayList<Line> lines = window.getLinesmanager().NewFullLines();
			window.getLinesmanager().deleteLine(id);
			//new SelectRuleAction(window,"").updateWindow(window.getLinesmanager().getLines(id),id);
			window.getSelectrulename().removeItem(id);
		
		}
		window.revalidate();
		window.repaint();
		
		
		
		
	}
	
}