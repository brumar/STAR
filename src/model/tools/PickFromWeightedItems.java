package model.tools;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import model.tools.WeightedItem;


	public class   PickFromWeightedItems
	{
		    private  String value;
		    private HashMap<String,ArrayList<WeightedItem<String>>> weightMatrice;
		    public    PickFromWeightedItems(HashMap<String, HashMap<String, Integer>> occurMatrice){
		    	HashMap<String,ArrayList<WeightedItem<String>>> finalMatrix=new HashMap<String,ArrayList<WeightedItem<String>>>();
		    	for(String pbm:occurMatrice.keySet()){
		    		ArrayList<WeightedItem<String>> pbmList=new ArrayList<WeightedItem<String>>();
		    		for(String ans:occurMatrice.get(pbm).keySet()){
		    			Integer val = occurMatrice.get(pbm).get(ans);
		    			WeightedItem<String> current = new WeightedItem<String>(val, ans);
		    			pbmList.add(current);
		    		}
		    		finalMatrix.put(pbm,pbmList);
		    	} 
		    	this.weightMatrice=finalMatrix;
		    }
		    
		public HashMap<String,String> PickFromWeightedMatrice(){
			HashMap<String,String> selectionVector = new HashMap<String,String>();
			for(String pbm:this.weightMatrice.keySet()){
				ArrayList<WeightedItem<String>> vector = this.weightMatrice.get(pbm);
				String s = PickFromWeightedVector(vector);
				selectionVector.put(pbm,s);
			}
			return selectionVector;
		}
		
		private String PickFromWeightedVector(ArrayList<WeightedItem<String>> listItems){	    	
			double totalWeight = 0.0d;
			for (WeightedItem<String> i : listItems)
			{
			    totalWeight += i.getWeight();
			}
			// Now choose a random item
			int randomIndex = -1;
			double random = Math.random() * totalWeight;
			Integer index=0;
			for (WeightedItem<String> i : listItems)
			{
				
			    random -= i.getWeight();
			    if (random <= 0.0d)
			    {
			        randomIndex = index;
			        break;
			    }
			    index+=1;
			}
			String myRandomItem = listItems.get(randomIndex).getItem();
			return myRandomItem;
		}
		
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}