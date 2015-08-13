package model.rulesData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;



public class Line{
		String idProanswer;
		public String getIdProanswer() {
			return idProanswer;
		}

		public ArrayList<String> getListPropbm() {
			return listPropbm;
		}

		public boolean isFull() {
			return full;
		}

		public boolean isNone() {
			return none;
		}

		ArrayList<String> listPropbm;
		boolean full;
		boolean none;
		
		public Line(String id,ArrayList<String> lis,boolean f,boolean n){
			this.idProanswer=id;
			this.listPropbm=lis;
			this.full=f;
			this.none=n;
		}
	}
	