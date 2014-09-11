package oving3;

import java.util.ArrayList;

public class Person {
	String name;
	Person mother;
	Person father;
	ArrayList<Person> children = new ArrayList<Person>();
	
	public Boolean isMotherOf(Person child) {
		if (children.contains(child) && this == child.mother) {
			return true;
		}
		return false;
	}
	
	public Boolean isFatherOf(Person child) {
		if (children.contains(child) && this == child.father) {
			return true;
		}
		return false;
	}
	
	public Boolean isSiblingOf(Person sibling) {
		if (sibling != this && mother.children.contains(sibling) && father.children.contains(sibling)) {
			return true;
		}
		return false;
	}
	
	public void addParents(Person father, Person mother) {
		this.father = father;
		this.mother = mother;
		father.children.add(this);
		mother.children.add(this);
	}
	
	public String toString() {
		String info;
		if (mother != null && father != null)
		{
			info = "name: " + name + ". mother: " + mother.name + ". father: " + father.name + "\nchildren:\n";
		} else {
			info = "name: " + name + ". mother: none. father: none\nchildren:\n";
		}

		for ( Person child : children) {
			info += child.name + "\n";
		}
		return info;
	}
}
