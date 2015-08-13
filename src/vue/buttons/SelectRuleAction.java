package vue.buttons;


import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import model.rulesData.RuleDatas;
import model.rulesData.Line;

import vue.windows.MainWindow;

public class SelectRuleAction extends AbstractAction {
	private MainWindow window;
	private RuleDatas rules;
	private ArrayList<String> PropertiesPbm=new ArrayList<String>();
	

	
	public SelectRuleAction(MainWindow window, String texte){
		super(texte);
		this.window = window;
		PropertiesPbm=window.getdatas().getpbmPropList();//T48
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox jc=(JComboBox) e.getSource();	
		if(jc.getItemCount()!=0){
			String selectedRule = jc.getSelectedItem().toString();
			if(!selectedRule.equals("")){		
				ArrayList<Line> lines = window.getLinesmanager().getLines(selectedRule);
				updateWindow(lines,selectedRule);			
			}
		}
	}
	public void updateWindow(ArrayList<Line> lines,String selectedValue) {
		Iterator<Line> it = lines.iterator();
		ArrayList<ArrayList<Component>> dualPanelList=new ArrayList<ArrayList<Component>>();
		int index=0;
		while(it.hasNext()){
			index++;
			Line line = it.next();
			//System.out.println(" full : "+line.isFull()+" none : "+line.isNone()+" list : "+line.getListPropbm()+" id : "+line.getIdProanswer());
			JPanel myanswerline;
			if(line.isFull()){
				myanswerline=createLine(line.getIdProanswer(),1,index,true);	
			}
			else if(line.isNone()){
				myanswerline=createLine(line.getIdProanswer(),0,index,true);
			}
			else{
				myanswerline=createLine(line.getIdProanswer(),2,index,true);	
			}
			JScrollPane problemsPanel=createProblemsPanel(PropertiesPbm,line.getListPropbm(),index);
			ArrayList<Component> dualPanel=new ArrayList<Component>();
			dualPanel.add(myanswerline);
			dualPanel.add(problemsPanel);
			dualPanelList.add(dualPanel);
		}
		//System.out.println(javax.swing.SwingUtilities.isEventDispatchThread());
		UpdateMainWindow(dualPanelList);
		
	}

	private void UpdateMainWindow(ArrayList<ArrayList<Component>> dualPanelList) {
	//	window.setdualPanelList(dualPanelList);
		removePreviousScrollpanes();
		
		GridBagConstraints c = new GridBagConstraints();
	    c.gridheight=1;
	    c.gridwidth=2;
	    c.weightx=1;
	    c.weighty=4;
	    c.gridx = 0;
	    c.gridy = 4;
	    c.insets = new Insets(2, 2, 2, 2); 
		c.anchor=GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		
		JPanel mypanel = new JPanel();
		JScrollPane mypane = new JScrollPane();
		mypanel.setLayout(new GridBagLayout());

		int temp=4;
		Iterator<ArrayList<Component>> iter = dualPanelList.iterator();
		
		while(iter.hasNext()){
			c.gridx=0;	
			ArrayList<Component> compons = iter.next();
			mypanel.add(compons.get(0),c);			
			c.gridx=1;			
			c.gridy=5;
			window.getRulescontainer().add(compons.get(1),c);
			temp++;
			c.gridy=temp;
		}
		c.gridx=0;
		c.gridy = 5;
		c.gridheight=3;
		
		mypanel.setName("proPbmPane");
		mypane.getViewport().add(mypanel);
		mypane.setName("ScrollproPbmPane");//temp
		//mypane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//mypane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		window.getRulescontainer().add(mypane,c);
		
		c.gridx=1;
		c.gridy = 5;
		c.weightx=2;
		JScrollPane emptypane=new JScrollPane(new JLabel(""));
		emptypane.setName("Scrolldependancies_empty");
		emptypane.setVisible(false);
		window.getRulescontainer().add(emptypane,c);

		
		
		window.revalidate();
		window.repaint();
		
	}
	

	private void removePreviousScrollpanes() {
		Component[] compotab = window.getRulescontainer().getComponents();
		List<Component> compolist = Arrays.asList(compotab);
		Iterator<Component> iter = compolist.iterator();
		while(iter.hasNext()){
			Component c = iter.next();
			if (c.getClass()==new JScrollPane().getClass()){
				window.getRulescontainer().remove(c);
			}
		}
		
	}

	public JPanel createLine(String legend,Integer value, Integer index ,boolean answerProp){
		JPanel p=new JPanel();
		JLabel l= new JLabel(legend);
		ButtonGroup group = new ButtonGroup();
        JRadioButton jr1 = new JRadioButton("pass");
        jr1.setAction(new RadioAction(window,""));
        jr1.setText("pass");
        JRadioButton jr2 = new JRadioButton("block");
        jr2.setAction(new RadioAction(window,""));
        jr2.setText("block");
       
        
        if(value==0){
        	jr2.setSelected(true);
        	}
        else if(value==1){
        	jr1.setSelected(true);
        	}
        group.add(jr1);
        group.add(jr2);
        p.add(l);
        p.add(jr1);
        p.add(jr2);
        
        if(answerProp){
        JRadioButton jr3 = new JRadioButton("it depends");       
	        if(value==2){
	        	jr3.setSelected(true);
	        	}
	        jr3.setAction(new RadioAction(window,""));
	        jr3.setText("it depends (click to edit dependances)");
	        group.add(jr3);
	        p.add(jr3);
        } 
        p.setName(""+index);
        return p;
		
	}
	public JScrollPane createProblemsPanel(ArrayList<String> pbms,ArrayList<String> pbmselected, Integer index){
		GridBagConstraints c = new GridBagConstraints();
	    c.gridheight=1;
	    c.gridwidth=3;
	    c.gridx = 0;
	    c.gridy = 0;
	    c.insets = new Insets(2, 2, 2, 2); 
		c.anchor=GridBagConstraints.FIRST_LINE_START;	
		JPanel p=new JPanel();
		p.setLayout(new GridBagLayout());
		Iterator<String> it = pbms.iterator();
		while(it.hasNext()){
			 c.gridy++;
			JPanel p1;
			String pbm = it.next();
			if(pbmselected.contains(pbm)){
				p1 = createLine(pbm,1,c.gridy,false);
			}
			else{
				p1 = createLine(pbm,0,c.gridy,false);
			}
			p.add(p1,c);
		}
		 JScrollPane thePane = new JScrollPane(p);
		 p.setName("dependancies_"+index);
		 thePane.setName("Scrolldependancies_"+index);
		 thePane.setVisible(false);
		 //thePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		 //thePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		 
		
		return thePane;
		
	}

}