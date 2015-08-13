package vue.windows;
 
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JTextField;
import java.beans.*; //property change stuff
import java.awt.*;
import java.awt.event.*;
import resources.Resource;
 
/* 1.4 example used by DialogDemo.java. */
class CustomDialog extends JDialog
                   implements ActionListener,
                              PropertyChangeListener {
    private String typedText = null;
    private JTextField textField;
    private ModeSelector dd;
 
    private String magicWord;
    private JOptionPane optionPane;
 
    private String btnString1 = "Enter";
    private String btnString2 = "Cancel";
 
    /**
     * Returns null if the typed string was invalid;
     * otherwise, returns the string as the user entered it.
     */
    public String getValidatedText() {
        return typedText;
    }
 
    /** Creates the reusable dialog. */
    public CustomDialog(Frame aFrame, String aWord, ModeSelector parent) {
        super(aFrame, true);
        dd = parent;
 
        magicWord = aWord.toUpperCase();
        setTitle("Resources Downloader");
 
        textField = new JTextField(10);
 
        //Create an array of the text and components to be displayed.
        String msgString1 = "Enter the number you were given on the website";
        Object[] array = {msgString1, textField};
 
        //Create an array specifying the number of dialog buttons
        //and their text.
        Object[] options = {btnString1, btnString2};
 
        //Create the JOptionPane.
        optionPane = new JOptionPane(array,
                                    JOptionPane.QUESTION_MESSAGE,
                                    JOptionPane.YES_NO_OPTION,
                                    null,
                                    options,
                                    options[0]);
 
        //Make this dialog display it.
        setContentPane(optionPane);
 
        //Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                /*
                 * Instead of directly closing the window,
                 * we're going to change the JOptionPane's
                 * value property.
                 */
                    optionPane.setValue(new Integer(
                                        JOptionPane.CLOSED_OPTION));
            }
        });
 
        //Ensure the text field always gets the first focus.
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent ce) {
                textField.requestFocusInWindow();
            }
        });
 
        //Register an event handler that puts the text into the option pane.
        textField.addActionListener(this);
 
        //Register an event handler that reacts to option pane state changes.
        optionPane.addPropertyChangeListener(this);
    }
 
    /** This method handles events for the text field. */
    public void actionPerformed(ActionEvent e) {
        optionPane.setValue(btnString1);
    }
 
    /** This method reacts to state changes in the option pane. */
    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();
 
        if (isVisible()
         && (e.getSource() == optionPane)
         && (JOptionPane.VALUE_PROPERTY.equals(prop) ||
             JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
            Object value = optionPane.getValue();
 
            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                //ignore reset
                return;
            }
            typedText = textField.getText();
            Resource.numberPath=typedText;           
            textField.requestFocusInWindow();
            clearAndHide();           
        }
    }
 
    /** This method clears the dialog and hides it. */
    public void clearAndHide() {
        textField.setText(null);
        setVisible(false);
    }
}