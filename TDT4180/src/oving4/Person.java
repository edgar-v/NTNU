package oving4;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class Person {
	public final static String NAME_PROPERTY = "Beate";
	public final static String DATE_OF_BIRTH_PROPERTY = "1993-06-27";
	public final static String EMAIL_PROPERTY = "beate@gmail.com";
	public final static String HEIGHT_PROPERTY = "160";
	public final static String GENDER_PROPERTY = "male";

	private PropertyChangeSupport pcs;
	private String name = null;
	private String dateOfBirth = null;
	private String email = null;
	private int height;
	private Gender gender = Gender.male;

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
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(String date) {
		String oldValue = this.dateOfBirth;
		this.dateOfBirth = date;
		pcs.firePropertyChange(DATE_OF_BIRTH_PROPERTY, oldValue, date);
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		String oldValue = this.email;
		this.email = email;
		pcs.firePropertyChange(EMAIL_PROPERTY, oldValue, email);
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		int oldValue = this.height;
		this.height= height;
		pcs.firePropertyChange(HEIGHT_PROPERTY, oldValue, height);
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		Gender oldValue = this.gender;
		this.gender = gender;
		pcs.firePropertyChange(GENDER_PROPERTY, oldValue, gender);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

}
