package vue.windows;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import resources.Resource;
import resources.ResourceDownloader;

import vue.buttons.CreateDeleteRule;
import vue.buttons.LoadModel;
import vue.buttons.LoadModelUser;
import vue.buttons.OpenAction;
import vue.buttons.OpenModel;
import vue.buttons.RunAction;
import vue.buttons.SaveAction;
import vue.buttons.SelectRuleAction;
import controller.DataManager;
import controller.ModelManager;
import controller.RuleLinesManager;
import controller.RunManager;

public class MainWindow extends JFrame implements ActionListener{
	private JPanel Datacontainer = new JPanel();
	private JPanel Rulescontainer= new JPanel();


	private JPanel Modelcontainer = new JPanel();
	private JPanel Runcontainer = new JPanel();
	private ArrayList<ArrayList<Component>> dualPanelList=null;
	private JTabbedPane Onglets = null;
	private JScrollPane scroll;
	
	private DataManager datas ;
	
	private JLabel messageWindowRules=new JLabel("");
	private String messageRules;
	
	private String messageData;
	public JLabel messageWindowData=new JLabel("");
	
	private String messageModel;
	public JLabel messageWindowModel=new JLabel("");
	private JScrollPane answerPropertiesPane;
	private ArrayList<String> currentRulesList=new ArrayList<String>();

	private JFormattedTextField newrulename;
	private RuleLinesManager linesmanager;
	private JComboBox selectrulename;
	private ModelManager modelmanager;
	private RunManager runmanager;
	private JLabel messageWindowRun=new JLabel("");
	private JTextArea messagesSelectionLabel=new JTextArea("");
	
	public boolean ruleSaved=true;
	public boolean modelSaved=true;
	public boolean modelUpdated=false;
	public boolean modelEdited=false;
	
	



	public JFormattedTextField getNewrulename() {
		return newrulename;
	}
	public static  JTabbedPane tabbedPane = new JTabbedPane();

	public MainWindow() throws IOException{
		super();
		
		build();//On initialise notre fenêtre
	}
	
	private void build() throws IOException{
		
		ModeSelector newContentPane = new ModeSelector(this);
		//ModeSelector.isclosed()
		newContentPane.setSize(350, 250);
		newContentPane.setVisible(true);
		
		
		setTitle(Resource.messages.getString("title"));
		setSize(850,600); 
		setLocationRelativeTo(null); 
		setResizable(true); 
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		
		setLayout(new GridBagLayout());//the windows is managed by a gridbag layout, with a tabbedPane in last instance		
		//System.out.println(Resource.runningMode);
		loadContent();
		BuildTabPane();
		
		

        //newContentPane.setOpaque(true); 
        //this.setContentPane(newContentPane);
        //this.pack();
        
     //************menu creation
		
	
	}


	public void loadContent() {
		

		//DeletePane()
		if(Resource.runningMode.equals("Online")){loadOnlineResources(Resource.numberPath);
		}
		System.out.println(Resource.path);
		datas=null;
		linesmanager=null;
		datas= new DataManager();//T48
		linesmanager= new RuleLinesManager(
				datas.getRuleDatas().getAllRulelines(),
				datas.getpbmPropList(),
				datas.getRuleDatas().getIndexPropertyAnswer(),
				datas.getRuleDatas().getIndexPropertyPbm());
		modelmanager=new ModelManager();
		runmanager=new RunManager(
				modelmanager.getModelDatas(), 
				datas.getRuleDatas(),
				datas.getProblemDatas()
				);
		messageWindowModel.setText(messageModel=modelmanager.getModelMessage());//
		messageWindowData.setText(messageData=datas.getProblemmessage());
		messageWindowRules.setText(messageRules=datas.getRulemessage());//
		currentRulesList=datas.getRulesList();
		//loadingRules();

		//
		
	}



	private void loadOnlineResources(String numberPath) {
		try{
	new ResourceDownloader(numberPath);}
		catch (Exception e) {
			handleException("an error ocur, the FTP did not answer. Possible causes : no internet connection, wrong code. If not contact your web administrator");
			e.printStackTrace();
			System.exit(0);
		}
		
	}

	public RuleLinesManager getLinesmanager() {
		return linesmanager;
	}

	public DataManager getdatas() {
		
		return datas;
	}

	private void BuildTabPane() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weightx=1;
		c.weighty=1;
	    c.gridx = 0;
	    c.gridy = 0;
		add(TabbedPaneDemo(),c);
		
	}

	JTabbedPane TabbedPaneDemo() {
		 
	      
	        tabbedPane.addTab("datas", null, loadingDatas(),"loading datas");
	        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
	        tabbedPane.addTab("rules", null, loadingRules(),"loading rules");
	        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
	        tabbedPane.addTab("model", null, loadingModel(),"loading model");
	        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
	        tabbedPane.addTab("evaluation", null, loadingRun(),"run model");
	        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
	        tabbedPane.addChangeListener(new ChangeListener() {
				// This method is called whenever the selected tab changes
	            public void stateChanged(ChangeEvent evt) {          	
	                }
	            });	        	        
	        return tabbedPane;
	}

	private Component loadingRun() {
		GridBagConstraints c = new GridBagConstraints();
	    c.gridheight=1;
	    c.gridwidth=2;
	    c.weightx=1;
	    c.weighty=0.22;
	    c.gridx = 0;
	    c.gridy = 0;
	    c.insets = new Insets(2, 2, 2, 2); 
		c.anchor=GridBagConstraints.FIRST_LINE_START;
		c.fill=GridBagConstraints.NONE;
		//c.fill=GridBagConstraints.HORIZONTAL;
		
		Runcontainer.setLayout(new GridBagLayout());
		JPanel datas = new JPanel();
        JButton runbutton = new JButton(new RunAction(this,""));//(new OpenAnkiAction(this,"")
        Icon iconOpen = new ImageIcon("src/icons/run24.png");
        runbutton.setIcon(iconOpen);          		
        Runcontainer.add(runbutton,c);
        c.gridy++;
        Runcontainer.add(messageWindowRun,c);
        c.gridy++;
        //messagesSelectionLabel.setMinimumSize(new Dimension(500,100));
		Runcontainer.add(messagesSelectionLabel,c);
		c.gridy++;
		c.weighty=5;
		Runcontainer.add(new JLabel() ,c);
		
		return Runcontainer;
	}

	private Component loadingModel() {
		GridBagConstraints c = new GridBagConstraints();
	    c.gridheight=1;
	    c.gridwidth=1;
	    c.weightx=1;
	    c.weighty=0.22;
	    c.gridx = 0;
	    c.gridy = 0;
	    c.insets = new Insets(5, 5, 5, 5); 
		c.anchor=GridBagConstraints.FIRST_LINE_START;	
		c.fill=GridBagConstraints.NONE;
		
		
		//init the grid bag -end
		Modelcontainer=addMainButtons("model",Modelcontainer);
		c.gridy++;
		Modelcontainer.add(messageWindowModel,c);
		c.gridy++;
		Modelcontainer.add(new JLabel("<html><u><bold><b1>Load a Default Model</u></bold></b1></html>") ,c);
		c.gridy++;
		
		
		JButton LoadBestbutton = new JButton(new LoadModel(this,""));//new LoadRule(this,""));
		LoadBestbutton.setText("BestRule");
		LoadBestbutton.setName("BestRule");
		//LoadBestbutton.setSize(new Dimension(20,50));
		JPanel jp1 = new JPanel();
		jp1.add(LoadBestbutton);
		jp1.add(new JLabel("no more than one single rule can be true at the same time"));
		Modelcontainer.add(jp1,c);
		
		c.weightx=1;
		c.gridwidth=1;
		c.gridx=0;
		c.gridy++;
		JButton LoadCombinaisonbutton = new JButton(new LoadModel(this,""));//new LoadRule(this,""));
		LoadCombinaisonbutton.setText("BestCombinaison");
		LoadCombinaisonbutton.setName("BestCombinaison");
		JPanel jp2 = new JPanel();
		jp2.add(LoadCombinaisonbutton);
		jp2.add(new JLabel("many rules can be true at the same time"));
		Modelcontainer.add(jp2,c);
		
		c.gridy++;
		Modelcontainer.add(new JLabel("<html>OR <br><u><bold><b1>Load a complex Model</u></bold></b1></html>") ,c);
		c.gridy++;
		c.weightx=0.1;
		
		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridheight=1;
		c2.gridwidth=1;
		c2.weightx=1;
		c2.weighty=0.22;
		c2.gridx = 0;
		c2.gridy = 0;
	    c2.insets = new Insets(10, 10,0, 10); 
	    c2.anchor=GridBagConstraints.FIRST_LINE_START;	
	    c2.fill=GridBagConstraints.NONE;
	    JPanel jp3 = new JPanel();
		
		jp3.add(new JLabel("<html><u>step1</u></html>"),c2);
		jp3.setLayout( new GridBagLayout());
		c2.gridx++;
		jp3.add(new JLabel("Edit the matrix with your favorite application : "),c2);
		c2.gridx++;
		jp3.add(new JLabel("message1"),c2);//message 1
		c2.gridy++;
		c2.gridx=1;
		jp3.add(new JLabel("path : "),c2);//path
		c2.gridx++;
		//jp3.add(new JLabel("message2"));//message 1
		JButton EditMatrixModel = new JButton(new OpenModel(this,""));//new LoadRule(this,""));
		EditMatrixModel.setText("open");
		EditMatrixModel.setName("open");
	//	c2.gridy++;
		jp3.add(EditMatrixModel,c2);	
		Modelcontainer.add(jp3,c);	 
		
		c.gridy++;
		
		JPanel jp4 = new JPanel();
		jp4.add(new JLabel("<html><u>step2</u></html>"),c2);
		jp4.setLayout( new GridBagLayout());
		c2.gridx--;
		jp4.add(new JLabel("Save and close your file before reloading "),c2);
		c2.gridx++;
		jp4.add(new JLabel("message1"),c2);//message 1
		c2.gridy++;
		c2.gridx=1;
		jp4.add(new JLabel("path : "),c2);//path
		c2.gridx++;
		//jp3.add(new JLabel("message2"));//message 1
		JButton LoadMatrixModel = new JButton(new LoadModelUser(this,""));//new LoadRule(this,""));
		LoadMatrixModel.setText("reload");
		LoadMatrixModel.setName("reload");
	//	c2.gridy++;
		jp4.add(LoadMatrixModel,c2);	
		Modelcontainer.add(jp4,c);
	
		c.weighty=5;
		Modelcontainer.add(new JLabel() ,c);
		
		return Modelcontainer;
	}

	private Component loadingRules() {
		Rulescontainer=null;
		Rulescontainer=new JPanel();
		//Rulescontainer= new JPanel();//init the grid bag -start
		double w=0.22;
		GridBagConstraints c = new GridBagConstraints();
	    c.gridheight=1;
	    c.gridwidth=2;
	    c.weightx=1;
	    c.weighty=w;
	    c.gridx = 0;
	    c.gridy = 0;
	    c.insets = new Insets(2, 2, 2, 2); 
		c.anchor=GridBagConstraints.FIRST_LINE_START;	
		c.fill=GridBagConstraints.HORIZONTAL;
		//init the grid bag -end
		Rulescontainer=addMainButtons("rules",Rulescontainer);
		
		c.gridy++;
			Rulescontainer.add(messageWindowRules,c);
		c.gridy++;		
	        JLabel newrulelabel= new JLabel("add a rule");
	        newrulename = new JFormattedTextField();
	        newrulename.setName("newrule");
	        newrulename.setPreferredSize(new Dimension(200,30));
	        JButton newrulebutton = new JButton(new CreateDeleteRule(this,""));
	        newrulebutton.setText("new");
	        newrulebutton.setName("new");
	        JPanel newrule=new JPanel();
	        newrule.add(newrulelabel);newrule.add(newrulename);newrule.add(newrulebutton);
	        Rulescontainer.add(newrule,c); 
		c.gridy++;
		JLabel selectrulelabel= new JLabel("select a rule");////T48
	        selectrulename = new JComboBox();
	        //selectrulename.addItem("");
	        Iterator<String> iter = currentRulesList.iterator();//T48
	       while(iter.hasNext()){
	        	selectrulename.addItem(iter.next());
	        }       
	        selectrulename.setAction(new SelectRuleAction(this,""));  
	        selectrulename.setPreferredSize(new Dimension(184,30));
	        JButton selectrulebutton = new JButton(new CreateDeleteRule(this,""));
	        selectrulebutton.setText("delete");
	        selectrulebutton.setName("delete");
	        JPanel selectrule=new JPanel();
	        selectrule.add(selectrulelabel);
	        selectrule.add(selectrulename);
	        selectrule.add(selectrulebutton);//T48
	        Rulescontainer.add(selectrule,c);//T48
	     c.gridy++;
	     c.gridwidth=2;
	     c.weighty=5;
	     c.fill=GridBagConstraints.BOTH;
	        JScrollPane mypane = new JScrollPane(new JLabel(""));
	        JScrollPane mypane2 = new JScrollPane(new JLabel(""));
	        Rulescontainer.add(mypane,c);
	     c.gridx++;	     
	     	Rulescontainer.add(mypane2,c);

	       // tabbedPane.get
	        
		return Rulescontainer;
		
		
	}

	public JComboBox getSelectrulename() {
		return selectrulename;
	}

	private Component loadingDatas() {
		
		Datacontainer=addMainButtons("datas", Datacontainer);
		GridBagConstraints c = new GridBagConstraints();
	    c.gridheight=1;
	    c.weightx=1;
	    c.weighty=5;
	    c.gridx = 0;
	    c.gridy = 1;
	    c.insets = new Insets(2, 2, 2, 2); 
		c.anchor=GridBagConstraints.FIRST_LINE_START;		
        Datacontainer.add(messageWindowData,c); 
		return Datacontainer;
	}

	

	private JPanel addMainButtons(String string, JPanel container) {
		container.setLayout(new GridBagLayout());
		JPanel datas = new JPanel();
        JButton opendatasdir = new JButton(new OpenAction(this,""));//(new OpenAnkiAction(this,"")
        opendatasdir.setText("open "+string);
        opendatasdir.setName("open"+string);
        Icon iconOpen = new ImageIcon(Resource.messages.getString("icopath")+"/"+Resource.messages.getString("openIco"));
        opendatasdir.setToolTipText("Open "+string);
        opendatasdir.setIcon(iconOpen);   
        
        JButton savedatasdir = new JButton(new SaveAction(this,""));//(new SaveAction(this,"")
        Icon iconSave = new ImageIcon(Resource.messages.getString("icopath")+"/"+Resource.messages.getString("saveIco"));
        savedatasdir.setToolTipText("save "+string);
        savedatasdir.setText("save "+string);
        savedatasdir.setName("save"+string);      
        savedatasdir.setIcon(iconSave);
        
        
        datas.add(opendatasdir); 
        datas.add(savedatasdir);
        GridBagConstraints c = new GridBagConstraints();
        c.gridheight=1;
        c.weightx=1;
        c.weighty=0.2;
		c.gridx = 0;
	    c.gridy = 0;
	    //c.gridheight=5;
		c.insets = new Insets(2, 2, 2, 2); 
		c.anchor=GridBagConstraints.FIRST_LINE_START;
        container.add(datas,c);  
		return container;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public JLabel getmessageWindowRunLabel() {
		// TODO Auto-generated method stub
		return messageWindowRun;
	}
	
	public JLabel getmessageWindowDataLabel() {
		// TODO Auto-generated method stub
		return messageWindowData;
	}

	public JLabel getmessageWindowModelLabel() {
		
		// TODO Auto-generated method stub
		return messageWindowModel;
	}
	
	public JLabel getmessageWindowRulesLabel() {
		// TODO Auto-generated method stub
		return messageWindowRules;
	}

	public ModelManager getModelManager() {
		// TODO Auto-generated method stub
		return modelmanager;
	}
	
	public RunManager getRunmanager(){
		return runmanager;
	}

	public void setdualPanelList(ArrayList<ArrayList<Component>> dualPanelList) {
		this.dualPanelList=dualPanelList;	
		//this.loadingRules();//refresh is needed
	}
	
	public JScrollPane getAnswerPropertiesPane() {
		return answerPropertiesPane;
	}

	public void setAnswerPropertiesPane(JScrollPane answerPropertiesPane) {
		this.answerPropertiesPane = answerPropertiesPane;
	}
	public JPanel getRulescontainer() {
		return Rulescontainer;
	}

	public ArrayList<String> getCurrentRulesList() {
		return currentRulesList;
	}

	public void setSelectrulename(Object object) {
		// TODO Auto-generated method stub
		this.selectrulename=(JComboBox) object;
	}
	public JTextArea getMessagesSelectionArea() {
		return messagesSelectionLabel;
	}

	public boolean isRunnable() {
		// TODO Auto-generated method stub
		return ruleSaved&&modelUpdated&&modelSaved;
	}

	public void setRuleSaved(boolean b) {
		// TODO Auto-generated method stub
		ruleSaved=b;
	}

	public void setmodelSaved(boolean b) {
		// TODO Auto-generated method stub
		modelSaved=b;
	}

	public void handleException(String string) {
		if(string==null){string="An error occured, please contact the developper";}
		JOptionPane.showMessageDialog(this, string);
	
	}
	public void handleException(Exception e) {
		handleException(e.getMessage());
	}

	public String getRunStatus() {
		/*
		 * 	
	public boolean ruleSaved=true;
	public boolean modelSaved=true;
	
	public boolean modelUpdated=true;
	public boolean modelEdited=true;
	public boolean startMode=true;
		 */
		if((!ruleSaved)||(!ruleSaved)){return Resource.messages.getString("SaveMessage");}
		if(!modelUpdated){
			if(!modelEdited){
			return Resource.messages.getString("UpdateModelMessage");}
			else{
				Resource.messages.getString("EditUpdateModelMessage");
			}
		}
		return "";
	}

	public void setModelUpdated(boolean b) {
		modelUpdated=b;		
	}
	public void setModelEdited(boolean b) {
		modelEdited=b;		
	}

	public boolean isModelEdited() {
		// TODO Auto-generated method stub
		return modelEdited;
	}
    
    
}  
	

	
