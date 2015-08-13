package vue.windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.File;


import javax.swing.JFrame;
import javax.swing.JLabel;

public class HelloWorldWindow extends JFrame implements ActionListener {
	  String path=System.getProperty("user.home")+"/folderofjnlp";
	  
	 public HelloWorldWindow() throws FileNotFoundException, IOException {
	        //Make sure we have nice window decorations.
	        JFrame.setDefaultLookAndFeelDecorated(true);
	        

	        //Create and set up the window.
	        JFrame frame = new JFrame("HelloWorldSwing");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        //Add the ubiquitous "Hello World" label.
	        JLabel label = new JLabel("Helloo World");
	        frame.getContentPane().add(label);
	        
	        //String message=loadFile();
	      // JLabel label2 = new JLabel( message);
	        //frame.getContentPane().add(label2);
	       downDatas();
	       JLabel label3 = new JLabel(loadDDatas());
	        frame.getContentPane().add(label3);
	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	    }

	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	private String loadFile() throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		ArrayList<String> l = new ArrayList<String>();
		l.add("h1");
		l.add("h2");
		String message=(model.tools.Readers.read(l,"mattest.csv")).toString();
		return message;
	}
	public void downDatas() throws IOException {
		URL whatismyip = new URL("http://www.google.fr");
	    URLConnection connection = whatismyip.openConnection();
	    connection.setConnectTimeout(20000);
	    connection.addRequestProperty("Protocol", "Http/1.1");
	    connection.addRequestProperty("Connection", "keep-alive");
	    connection.addRequestProperty("Keep-Alive", "1000");
	    connection.addRequestProperty("User-Agent", "Web-Agent");

	    BufferedReader in = 
	        new BufferedReader(new InputStreamReader(connection.getInputStream()));

	    String ip = in.readLine();
	    System.out.println(ip);

	    ReadableByteChannel rbc = Channels.newChannel(whatismyip.openStream());
	    System.out.println("User Home Path: "+
	    		System.getProperty("user.home"));
	    new File(path).mkdirs();
	  
	    FileOutputStream fos = new FileOutputStream(path+"/information.txt");
	    fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		
	}

	public String loadDDatas() throws FileNotFoundException {
		
		File f=new File(path+"/information.txt");
		String s=(f.canRead())?"yes":"no";
		return s;
	}
	
}
