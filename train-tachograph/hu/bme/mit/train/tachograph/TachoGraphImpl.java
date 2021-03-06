package hu.bme.mit.train.tachograph;


import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class TachoGraphImpl {

	private Table<Integer, Integer, Integer> records  = HashBasedTable.create(); 
	
	public TachoGraphImpl(){}

	public Table<Integer, Integer, Integer> getRecords() {
		return records;
	}

	public void setRecords(Integer time, Integer position, Integer referencespeed) {
		records.put(time, position, referencespeed);
	}
}
