package model.tools;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.csvreader.CsvWriter;

public class Writers {
	static String ch=";";
	static char c=ch.charAt(0);

	public static void writeBooleanMatrix(String filename, boolean[][] matrix) throws IOException{
		
		//attention : the matrix will contain a header (header1, header2...) as first lines)
		
		CsvWriter csvwriter = new CsvWriter(filename);
		csvwriter.setDelimiter(c);
		int lengthVert=matrix.length;
		int lengthHoriz=matrix[0].length;
		
		for(int v=-1;v<lengthVert;v++){
			for(int h=0;h<lengthHoriz;h++){
				String s="";
				if(v!=-1){
					s=(matrix[v][h])?"1":"0";
				}
				else{
					s="header"+(h+1);//the file must contain header to be read properly
				}
				csvwriter.write(s);
			}
			csvwriter.endRecord();
		}
		csvwriter.close();
	}
	public static void writeStringMatrix(String filename, String[][] matrix) throws IOException{
		CsvWriter csvwriter = new CsvWriter(filename);
		csvwriter.setDelimiter(c);
		int lengthVert=matrix.length;
		int lengthHoriz=matrix[0].length;
		
		for(int v=0;v<lengthVert;v++){
			for(int h=0;h<lengthHoriz;h++){
				csvwriter.write(matrix[v][h]);
			}
			csvwriter.endRecord();
		}
		csvwriter.close();
	}
}
