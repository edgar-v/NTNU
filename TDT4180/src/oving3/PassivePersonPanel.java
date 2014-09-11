package oving3;

import javax.swing.JTextField;

import oving4.PersonPanel;


public class PassivePersonPanel extends PersonPanel {
	
	public PassivePersonPanel()
	{
		super();
		NamePropertyComponent.setEditable(false);
		EmailPropertyComponent.setEditable(false);
		DateOfBirthPropertyComponent.setEditable(false);
		GenderPropertyComponent.setEnabled(false);
		HeightPropertyComponent.setEnabled(false);
	}
}
