package oving9;

import java.util.Iterator;
import java.util.List;

public class HighscoreList extends ObservableList implements Comparable<HighscoreList> {

	int size;
	public HighscoreList(int maxSize) {
		size = maxSize;
	}
	
	public void addResult(SokobanResult result) {
		
	}
	
	@Override
	public int compareTo(HighscoreList o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected List getList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public Iterator<Comparable> iterator () {
		
	}
	
}
