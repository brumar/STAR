package resources;

import java.io.File;
import java.util.ResourceBundle;

public class Resource {

	public static ResourceBundle messages;
	public static String path;
	public static String temppath;
	public static String clientpath;
	public static String runningMode;
	public static String numberPath="";
	
	public static void loadMessages(){
		messages=ResourceBundle.getBundle("resources.bundle");
		path=messages.getString("filepath");
		temppath=messages.getString("temppath");
		clientpath=System.getProperty("user.home")+"/"+messages.getString("clientDirAppName");
		clientpath=clientpath.replace("\\", "/");
		runningMode=messages.getString("runningMode");
		
	}
}
