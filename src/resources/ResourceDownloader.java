package resources;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;

public class ResourceDownloader {
	FTPClient client;
	
	public ResourceDownloader(String local) throws IllegalStateException, IOException, FTPIllegalReplyException, FTPException, FTPDataTransferException, FTPAbortedException, FTPListParseException{
	
	buildDirectories();	
		
	client = new FTPClient();
	
	//try {
		client.connect(Resource.messages.getString("ftp"));
		client.login(Resource.messages.getString("login"), Resource.messages.getString("pass"));
		client.changeDirectory(Resource.messages.getString("OnlineResourceDirectory")+"/"+local);
		//client.
		ArrayList<String> l = BuildList();
		Iterator<String> it = l.iterator();
		while(it.hasNext()){
			String s=it.next();
			client.download(s, new File(Resource.clientpath+"/"+s));
		}
		Resource.path=Resource.clientpath;
		//client.download("dlMeIfYouCanSauron.txt", new File(Resource.clientpath+"/"+"localFile.txt"));
		
	/*} catch (Exception e) {
		System.out.println("an error ocur, the FTP did not answer. Cause possibles : no internet connection, wrong code. If not contact your web administrator");
		e.printStackTrace();
	}*/

}
	private void buildDirectories() {	
		new File(Resource.clientpath+"/"+Resource.messages.getString("dataRulesPath")).mkdirs();
		new File(Resource.clientpath+"/"+Resource.messages.getString("dataPbmPath")).mkdirs();
		new File(Resource.clientpath+"/"+Resource.messages.getString("dataModelPath")).mkdirs();		
	}

	private ArrayList<String> BuildList() throws IllegalStateException, IOException, FTPIllegalReplyException, FTPException, FTPDataTransferException, FTPAbortedException, FTPListParseException {
		ArrayList<String> dlList=new ArrayList<String>();
		dlList.addAll(ListNameFromDir(Resource.messages.getString("dataRulesPath")));
		dlList.addAll(ListNameFromDir(Resource.messages.getString("dataPbmPath")));
		dlList.addAll(ListNameFromDir(Resource.messages.getString("dataModelPath")));		
		return dlList;
		
	}

	private ArrayList<String> ListNameFromDir(String dirpath) throws IllegalStateException, IOException, FTPIllegalReplyException, FTPException, FTPDataTransferException, FTPAbortedException, FTPListParseException {
		String dirinit = client.currentDirectory();
		ArrayList<String> dlList=new ArrayList<String>();
		client.changeDirectory(dirpath);
		//ArrayList<String>();
		FTPFile[] list = client.list();
		List<FTPFile> arraylist = Arrays.asList(list);
		Iterator<FTPFile> it = arraylist.iterator();
		while(it.hasNext()){	
			dlList.add(dirpath+'/'+it.next().getName());
		}
		client.changeDirectory(dirinit);
		return dlList;
	}
}
