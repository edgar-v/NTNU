import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class Person {
	public final static String NAME_PROPERTY = "Beate";

	private PropertyChangeSupport pcs;
	private String name = null;

	public Person() {
		pcs = new PropertyChangeSupport(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldValue = this.name;
		this.name = name;
		pcs.firePropertyChange(NAME_PROPERTY, oldValue, name);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

}
