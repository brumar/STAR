package model.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class DoubleHashMap {
	public  HashMap<Integer, String> mapInt_to_String = new HashMap<Integer, String>();
	public  HashMap<String, Integer> mapString_to_Int = new HashMap<String, Integer>();
	
		
	public int find(String string){
		return mapString_to_Int.get(string);
		
	}
	public String find(Integer i){
		return mapInt_to_String.get(i);
	}
	public void put(String s, int i){
		this.mapInt_to_String.put(i, s);
		mapString_to_Int.put(s, i);
	}
	public void put(int i,String s){
		mapInt_to_String.put(i, s);
		mapString_to_Int.put(s, i);
	}
	public void putlist(List<String> List) {
		int l=List.size();
		for (int i=0;i<l;i++){
			put(i,List.get(i)); 
		}
	}
	public void putset(Set<String> set) {
		ArrayList<String> List=new ArrayList<String>(set);
		putlist(List);
	}
	public List<String> list_me(){
		Set<String> da= mapString_to_Int.keySet();
		List<String> list = new ArrayList<String>(da);
		return list;
	}
	int size(){
		return mapInt_to_String.size();
	}
	public void clear() {
		mapInt_to_String = new HashMap<Integer, String>();
		mapString_to_Int = new HashMap<String, Integer>();
		
	}
	public void LoadProperties(String file) throws IOException {//DoubleHashMap dmap
		Properties HashMapProPbm = new Properties();
		FileInputStream input = new FileInputStream(file);
		HashMapProPbm.load(input);
		HashMap<String, String> map = new HashMap<String, String>((Map) HashMapProPbm);
		Iterator<String> iter = map.keySet().iterator();
		while(iter.hasNext()){
			String key=iter.next();
			String value=(String) HashMapProPbm.get(key);
			Integer keyInteger=Integer.parseInt(key);
			this.put(keyInteger, value);
		}
		input.close();
	}

	public void saveMaps(String file) {
		File savedHashMaps = new File(file);
	    try {
	    	Properties props = new Properties();
	        ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream(savedHashMaps));	      
			Iterator<Integer> iter = mapInt_to_String.keySet().iterator();
			while(iter.hasNext()){
				Integer key=iter.next();
				String value=mapInt_to_String.get(key);
				props.setProperty(key.toString(), value);
			}
			props.store(oos,"comment");
	    } catch (Exception e) {
	        // Catch exceptions
	    }
	}


}



