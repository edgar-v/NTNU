package oving5.person;

import java.util.ArrayList;

public class Person {
	
	private String name;
	private char gender;
	private ArrayList<Person> children;
	private Person father;
	private Person mother;
	
	public Person(char gender) {
		if (gender == 'm' || gender == 'f') {
				this.gender = gender;
		}
		children = new ArrayList<Person>();
	}
	
	public Person getFather() {
		return father;
	}
	
	public void setFather(Person person) {
		if (person == null) {
			if (father != null) {
				father.removeChild(this);
				father = null;
			}
		} else if (person.isMale()) {
			if (father != null && father != person) {
				getFather().removeChild(this);
			}
			father = person;
			if (!getFather().containsChild(this)) {
				getFather().addChild(this);
			}
		}
	}
	
	public Person getMother() {
		return mother;
	}
	
	public void setMother(Person person) {
		if (person == null) {
			if (mother != null) {
				mother.removeChild(this);
				mother =null;
			}
		} else if (person.isFemale()) {
			if (mother != null  && mother != person) {
				mother.removeChild(this);
			}
			mother = person;
			if (!getMother().containsChild(this)) {
				getMother().addChild(this);
			}
		}
	}
	
	public int indexOfChild(Person child) {
		if (containsChild(child)) {
			return children.indexOf(child);
		} else {
			return -1;
		}
	}
	
	
	public int getChildCount() {
		return children.size();
	}
	
	public Person getChild(int index) {
		if (children.size() > index) {
			return children.get(index);
		} else {
			return null;
		}
	}
	
	public void addChild(Person child) {
		if (!children.contains(child)) {
			children.add(child);
			if (isMale()) {
				if (child.getFather() == null) {
					child.setFather(this);
				} else if (child.getFather() != this) {
					child.getFather().removeChild(child);
					child.setFather(this);
				}
			} else if (isFemale()) {
				if (child.getMother() == null) {
					child.setMother(this);
				} else if (child.getMother() != this) {
					child.getMother().removeChild(child);
					child.setMother(this);
				}
			}
		}
	}
	
	public void removeChild(Person child) {
		if (containsChild(child)) {
			children.remove(child);
			if (child.getFather() == this) {
				child.setFather(null);
			}
			if (child.getMother() == this) {
				child.setMother(null);
			}
			
		}
	}
	
	public boolean containsChild(Person child) {
		if (children.contains(child)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isAncestorOf(Person person) {
		if (person.getFather() != null) {
			if (this == person.getFather() || isAncestorOf(person.getFather())) {
				return true;
			}
		}
		if (person.getMother() != null) {
			if (this == person.getMother() || isAncestorOf(person.getMother())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isMale() {
		return gender == 'm';
	}
	
	public boolean isFemale() {
		return gender == 'f';
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		for (int i = 0; i < name.length(); i++) {
			if (!Character.isLetter(name.charAt(i)) && name.charAt(i) != ' ') {
				return;
			}
		}
		this.name = name;
	}

}
