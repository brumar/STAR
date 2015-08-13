package model.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.csvreader.CsvReader;

public class Readers {
	static String ch=";";
	static char c=ch.charAt(0);

    public static ArrayList<ArrayList<String>> read (List<String> headerName, String filename) throws FileNotFoundException, IOException {
    	/*
    	 * getClass().getClassLoader();
            		// Create icons
            		Icon saveIcon  = new ImageIcon(cl.getResource("images/save.gif"));
            		Icon cutIcon   = new ImageIcon(cl.getResource("images/cut.gif"));
    	 */
    	
            CsvReader reader = new CsvReader(filename,c);
            return read(reader,headerName);
    }

	public static boolean[][] buildBooleanMatrix(String csvfile) throws FileNotFoundException, IOException {
        CsvReader reader = new CsvReader(csvfile,c);
        reader.readHeaders(); 
        String[] headers = reader.getHeaders();
        int Cnumber=reader.getHeaderCount();
        ArrayList<boolean[]> Lbool= new ArrayList<boolean[]>();  
        int l=0;
        while (reader.readRecord())
        { 
        	boolean[] mbool=new boolean[Cnumber];
        	for(int c=0;c<Cnumber;c++){
        		String value =reader.get(headers[c]);
        		boolean b=(value.equals("1")) ? true : false; 
        		mbool[c]=b;
        	}
        	Lbool.add(mbool);
        		l++;
        }
        reader.close();
        
        boolean[][] matrix = new boolean[l][Cnumber];
        Iterator<boolean[]> it = Lbool.iterator();
        int index=0;
        while(it.hasNext()){
        	matrix[index]=it.next();        	
        	index++;
        }
        	
   
    return matrix;
	
	}

	public static ArrayList<ArrayList<String>> read(CsvReader reader,List<String> headerName) throws IOException {
		
    	ArrayList<ArrayList<String>> table=new ArrayList<ArrayList<String>>();


            reader.readHeaders(); 
            while (reader.readRecord())
            {
            	ArrayList<String> line=new ArrayList<String>();
            	Iterator<String> iter=headerName.iterator();
            	while(iter.hasNext()){
            		String head=iter.next();
            		String value = reader.get(head);
            		line.add(value);
            		//System.out.println(value);
            	}
            	table.add(line);
            }
            reader.close();
       
        return table;
	}

	public static ArrayList<ArrayList<String>> read(String string) throws IOException {
		  CsvReader reader1 = new CsvReader(string,c);
		  reader1.readHeaders();
	        String[] headers = reader1.getHeaders();
	        ArrayList<String> headerlist = new ArrayList(Arrays.asList(headers));
	       // headerlist.remove(0);
	        
	       return read(new CsvReader(string,c),headerlist);//the reader1 has already been read the headers, it mustnt be the case for the reader un argument
	
	}

   

}