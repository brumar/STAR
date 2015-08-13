package execution;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;

import resources.Resource;
import resources.ResourceDownloader;


import vue.windows.ModeSelector;
import vue.windows.HelloWorldWindow;
import vue.windows.MainWindow;



public class Window {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			NimRODTheme nt = new NimRODTheme("resources/Night.theme");
			NimRODLookAndFeel nf = new NimRODLookAndFeel();
			nf.setCurrentTheme(nt);
			UIManager.setLookAndFeel(nf);
			
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				Resource.loadMessages();
				//new SauronTester();
				
			
		        
		        
				/*
				
				//Ressources_fr.getString("test");
				
				HelloWorldWindow f = null;
				try {
					f = new HelloWorldWindow();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				f.setVisible(true);*/
				MainWindow fenetre = null;
				try {
					
					fenetre = new MainWindow();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				fenetre.setVisible(true);//*/
			}
		});
	}

}
