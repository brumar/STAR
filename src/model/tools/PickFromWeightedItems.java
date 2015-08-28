package model.tools;

import java.awt.List;
import java.util.ArrayList;
import java.util.Iterator;

import model.tools.WeightedItem;


	public class   PickFromWeightedItems
	{
		    private  String value;
		    private ArrayList<ArrayList<WeightedItem<String>>> weightMatrice;
		    public    PickFromWeightedItems(ArrayList<ArrayList<WeightedItem<String>>> weightMatrice){
		    	this.weightMatrice=weightMatrice;
		    }
		    
		public ArrayList<String> PickFromWeightedMatrice(){
			Iterator<ArrayList<WeightedItem<String>>> it = this.weightMatrice.iterator();
			ArrayList<String> selectionVector = new ArrayList<String>();
			while(it.hasNext()){
				ArrayList<WeightedItem<String>> vector = it.next();
				String s = PickFromWeightedVector(vector);
				selectionVector.add(s);
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
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("test");
		WeightedItem<String> wi = new WeightedItem<String>(5, "un");
		WeightedItem<String> w2 = new WeightedItem<String>(1, "deux");
		WeightedItem<String> w3 = new WeightedItem<String>(5, "trois");
		
		ArrayList<WeightedItem<String>> listItems = new ArrayList<WeightedItem<String>>();
		listItems.add(wi);
		listItems.add(w2);
		listItems.add(w3);
		ArrayList<WeightedItem<String>> l2 = new ArrayList<WeightedItem<String>>(listItems);
		ArrayList<WeightedItem<String>> l3 = new ArrayList<WeightedItem<String>>(listItems);
		ArrayList<ArrayList<WeightedItem<String>>> matrice = new ArrayList<ArrayList<WeightedItem<String>>>();
		matrice.add(listItems);
		matrice.add(l2);
		matrice.add(l3);
		PickFromWeightedItems selector = new PickFromWeightedItems(matrice);
		ArrayList<String> selection = selector.PickFromWeightedMatrice();
		System.out.println(selection);
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}