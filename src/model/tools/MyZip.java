package model.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class MyZip{

    /**
     * d�compresse le fichier zip dans le r�pertoire donn�
     * @param folder le r�pertoire o� les fichiers seront extraits
     * @param zipfile le fichier zip � d�compresser
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void unzip(File zipfile, File folder) throws FileNotFoundException, IOException{

        ZipInputStream zis = new ZipInputStream(
                new BufferedInputStream(
                        new FileInputStream(zipfile.getCanonicalFile())));
        ZipEntry ze = null;
        try {
            while((ze = zis.getNextEntry()) != null){
                File f = new File(folder.getCanonicalPath(), ze.getName());
                if (ze.isDirectory()) {
                    f.mkdirs();
                    continue;
                }
                f.getParentFile().mkdirs();
                OutputStream fos = new BufferedOutputStream(
                        new FileOutputStream(f));
                try {
                    try {
                        final byte[] buf = new byte[8192];
                        int bytesRead;
                        while (-1 != (bytesRead = zis.read(buf)))
                            fos.write(buf, 0, bytesRead);
                    }
                    finally {
                        fos.close();
                    }
                }
                catch (final IOException ioe) {
                    f.delete();
                    throw ioe;
                }
            }
        }
        finally {
            // fermeture de la ZipInputStream
            zis.close();
        }
    }
    
    public static void zipAll(File folder,File zipfile) throws FileNotFoundException, IOException{
    	try{
    		
    		String[] dirlist=folder.list();
    
    		ZipOutputStream z=new ZipOutputStream(new FileOutputStream(zipfile));
    		for(int i=0;i<dirlist.length;i++)
    		  zip(new File(folder+"\\"+dirlist[i]),"",z);//attention, it's not a good way to proceed
    		z.close();
    		}catch(Exception e){System.err.println("Error in zipAll-Method!!"+e);}
    	}
   				
    public static void zip(File x,String Dir,ZipOutputStream z){
    	try{
    	if(!x.exists())
    	  System.err.println("file not found");
    	if(!x.isDirectory()){
    	 z.putNextEntry(new ZipEntry((Dir+x.getName()).replace('\\','/')));
    	 FileInputStream y=new FileInputStream(x);
    	 byte[] a=new byte[(int)x.length()];
    	 int did=y.read(a);
    	 if(did!=x.length())
    	   System.err.println("DID NOT GET WHOLE FILE "+Dir+x.getName()+" ; only "+ did+ " of "+x.length());
    	 z.write(a,0,a.length);
    	 z.closeEntry();
    	 y.close();
    	 x=null;
    	 }
    	else  //recurse
    	 {
    	 String nnn=Dir+"/"+x.getName();
    	 x=null;
    	 z.putNextEntry(new ZipEntry(nnn.replace('\\','/')));
    	 z.closeEntry();
    	 String[] dirlist=(new File("../"+nnn+"/")).list();
    	 for(int i=0;i<dirlist.length;i++){
    		 zip(new File("../"+nnn+"/"+dirlist[i]),"../"+nnn+"/",z);
    	   }
    	 }
    	}catch(Exception e){System.err.println("Error in zip-Method!!"+e);}
    	}
public static void main(String[] args) throws Exception {
    File zipfile = new File("../test2.zip");
    File folder = new File("../Xmind_temp");
    MyZip.unzip(zipfile, folder);
    File zipfile2 = new File("../test2.xmind");
    zipAll(folder,zipfile2);
}

}
