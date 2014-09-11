package oving9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class ObservableList  {
	
	private List<ListListener> list = new ArrayList<ListListener>();
	
	public void addListListener(ListListener listener) {
		if (!list.contains(listener)) {
			list.add(listener);
		}
		
	}
	
	public void removeListListener(ListListener listener) {
		list.remove(listener);
	}
	
	protected void fireListChanged(int index1, int index2) {
		for (ListListener i : list) {
			i.listChanged(this, index1, index2);
		}
	}
	
	protected abstract List getList();
	
	public int size() {
		return getList().size();
	}
	
	protected void addElement(int index, int element) {
		int elementsChanged = 0;
		if (getList().size() > index && getList().get(index) != null) {
			elementsChanged++;
			while (index + elementsChanged < getList().size() && getList().get(index + elementsChanged) != null  ) {
				elementsChanged++;
			}
		}
		getList().add(index, element);
		fireListChanged(index, index+ elementsChanged);
	}
	
	protected void removeElement(int index) {
		getList().remove(index);
		if (index == getList().size()) {
			fireListChanged(index, index);
		} else {
			fireListChanged(Math.min(index, getList().size()), getList().size());
		}
	}
	
}
