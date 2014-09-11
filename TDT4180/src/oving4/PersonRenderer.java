package oving4;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class PersonRenderer implements ListCellRenderer {
	DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		
		Person person = (Person) value;
		String text = "Unknown";
		if (person.getName() != null && !person.getName().equals(""))
		{
			text = person.getName();
		}
		if (person.getEmail() != null && !person.getEmail().equals(""))
		{
			text += ", " + person.getEmail();
		}
		
		label.setText(text);
		if (person.getGender() == Gender.male)
			label.setIcon(new ImageIcon("res/male.png"));
		else if (person.getGender() == Gender.female)
			label.setIcon(new ImageIcon("res/female.png"));
		return label;
	}
}
