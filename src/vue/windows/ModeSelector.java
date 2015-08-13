package vue.windows;
 
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.beans.*; //Property change stuff
import java.awt.*;
import java.awt.event.*;

import resources.Resource;
 
/*
 * DialogDemo.java requires these files:
 *   CustomDialog.java
 *   images/middle.gif
 */
public class ModeSelector extends JDialog {
    JLabel label;
   // ImageIcon icon = createImageIcon("../icons/other_lock.png");
    JFrame frame;
    String simpleDialogDesc = "Are you using the offline or the online version ?";
    String iconDesc = "A JOptionPane has its choice of icons";
    String moreDialogDesc = "Some more dialogs";
    CustomDialog customDialog;
 
    /** Creates the GUI shown inside the frame's content pane. */
    public ModeSelector(JFrame frame) {
    	//EXIT_ON_CLOSE
    	super(frame, true);
    	this.addWindowListener(exitListener);
        this.frame = frame;
        customDialog = new CustomDialog(frame, "geisel", this);
        customDialog.pack();
 
        //Create the components.
        JPanel frequentPanel = EnterYourCodeBox();
        label = new JLabel("Click offline if you are using a desktop application",
                           JLabel.CENTER);
 
        //Lay them out.
        Border padding = BorderFactory.createEmptyBorder(20,20,5,20);
        frequentPanel.setBorder(padding);
 
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Options", null,
                          frequentPanel,
                          simpleDialogDesc); //tooltip text
        add(tabbedPane, BorderLayout.CENTER);
        add(label, BorderLayout.PAGE_END);
        label.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    }
    
	public void handleException(String string) {
		if(string==null){string="An error occured, please contact the developper";}
		JOptionPane.showMessageDialog(this, string);
	
	}
 
    /** Sets the text displayed at the bottom of the frame. */
    void setLabel(String newText) {
        label.setText(newText);
    }
 

    private JPanel EnterYourCodeBox() {
       // final int numButtons = 4;
        final int numButtons = 2;
        JRadioButton[] radioButtons = new JRadioButton[numButtons];
        final ButtonGroup group = new ButtonGroup();
 
        JButton showItButton = null;
 
        final String Offline = "Offline";
        final String Online = "Online";
       
 
        radioButtons[0] = new JRadioButton("Online");
        radioButtons[0].setActionCommand(Online);
 
        radioButtons[1] = new JRadioButton("Offline");
        radioButtons[1].setActionCommand(Offline);

        for (int i = 0; i < numButtons; i++) {
            group.add(radioButtons[i]);
        }
        radioButtons[0].setSelected(true);
 
        showItButton = new JButton("Continue");
        showItButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String command = group.getSelection().getActionCommand();
                if (command == Offline) {
                  Resource.runningMode="Offline";
                } else if (command == Online) {
                	Resource.runningMode="Online";
                	printDialog();          
                }
                close();
                return;
            }
        });
 
        return createPane(simpleDialogDesc + ":",
                          radioButtons,
                          showItButton);
    }
 
    protected void close() {
		setVisible(false);
	}

	protected void printDialog() {
    	CustomDialog c = new CustomDialog(frame, "test", this);
    	c.setSize(300,150);
    	c.setVisible(true);
		
	}

    private JPanel createPane(String description,
                              JRadioButton[] radioButtons,
                              JButton showButton) {
 
        int numChoices = radioButtons.length;
        JPanel box = new JPanel();
        JLabel label = new JLabel(description);
 
        box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
        box.add(label);
 
        for (int i = 0; i < numChoices; i++) {
            box.add(radioButtons[i]);
        }
 
        JPanel pane = new JPanel(new BorderLayout());
        pane.add(box, BorderLayout.PAGE_START);
        pane.add(showButton, BorderLayout.PAGE_END);
        return pane;
    }
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("CodeSelector");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        ModeSelector newContentPane = new ModeSelector(frame);
       // newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    private WindowListener exitListener = new WindowAdapter() {

        @Override
        public void windowClosing(WindowEvent e) {
            int confirm = JOptionPane.showOptionDialog(null, "Are You Sure to Close Application?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (confirm == 0) {
               System.exit(0);
            }
        }
    };
   
 
}