package model.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public  class Filesmethods {
	public static void copyFile(File src, File dest) throws IOException{   
		if(src.canRead()){
		FileInputStream fis = new FileInputStream(src);
	     FileOutputStream fos = new FileOutputStream(dest);

	     java.nio.channels.FileChannel channelSrc   = fis.getChannel();
	     java.nio.channels.FileChannel channelDest = fos.getChannel();

	     channelSrc.transferTo(0, channelSrc.size() , channelDest);

	     fis.close();
	     fos.close();
		}
	}
		public static void copyFile (String src2, String dest2) throws IOException {
			copyFile(new File(src2), new File (dest2));
			}
		
		public static void copyFolder (File src, File dest) throws IOException {
			String[] s = src.list();
			List<String> ls = Arrays.asList(s);
			Iterator<String> it = ls.iterator();
			while(it.hasNext()){
				String f = it.next();
				File file = new File(f);
				copyFile(src.getPath().replace("\\", "/")+"/"+f, dest.getPath().replace("\\", "/")+"/"+file.getName());
			}
		}
		
		public static void deleteFile (File src,String filter) throws IOException {
			if(src.getPath().contains(filter)){
				src.delete();
				}
			}
		public static void deleteFolder (File src,String filter) throws IOException {
			String[] s = src.list();
			List<String> ls = Arrays.asList(s);
			Iterator<String> it = ls.iterator();
			while(it.hasNext()){
				String f = it.next();
				File file = new File(src.getPath().replace("\\", "/")+"/"+f);
				deleteFile(file,filter);
			}
				
			
			}
		public static void deleteFolder(String saveFolder, String filter) throws IOException {
			// TODO Auto-generated method stub
			deleteFolder(new File(saveFolder),filter);
		}
	
	}

