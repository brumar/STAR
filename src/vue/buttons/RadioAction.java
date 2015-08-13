package vue.buttons;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


import javax.swing.AbstractAction;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


import vue.windows.MainWindow;




@SuppressWarnings("serial")
public class RadioAction extends AbstractAction {
	private MainWindow window;
	public RadioAction(MainWindow window, String txt){
		super(txt);
		this.window =window;		
	}
	
	public void actionPerformed(ActionEvent e) {
		window.setRuleSaved(false);
		
		JRadioButton j=(JRadioButton) e.getSource();
		String legend=j.getText();
		String line=j.getParent().getName();
		String property=((JLabel)j.getParent().getComponent(0)).getText();
		String panelLegend=j.getParent().getParent().getName();
		System.out.println(panelLegend);
		String selectedRule = (String) window.getSelectrulename().getSelectedItem();
		

		boolean change=window.getLinesmanager().changeLine(selectedRule,legend,line,panelLegend,property);
		if(change){new SelectRuleAction(window,"").updateWindow(window.getLinesmanager().getLines(selectedRule),selectedRule);}
		
		if(legend.equals("it depends (click to edit dependances)")){//visibility is changed only when proPbmPane is clicked
			changeVisibility(line);
			//System.out.println("affichage de la ligne " +line);
		}
		else if(panelLegend.contains("dependancies")){
			changeVisibility(panelLegend.substring("dependancies_".length()));
		}else{
			changeVisibility("empty");
		}
		
		window.revalidate();
		window.repaint();
	}

	private void changeVisibility(String line) {
		boolean visible=false;
		List<Component> compolist = Arrays.asList(window.getRulescontainer().getComponents());
		Iterator<Component> iter = compolist.iterator();
		while(iter.hasNext()){
			Component c = iter.next();
			if (c.getClass()==new JScrollPane().getClass()){
				if(c.getName().contains("Scrolldependancies_")){c.setVisible(false);}//
				//System.out.println((c.getName()));
				if(c.getName().equals("Scrolldependancies_"+line)){
					c.setVisible(true);
				}
			}
		}
		
	}
}
	