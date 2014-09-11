package oving3;

import javax.swing.JPanel;
import javax.swing.JFrame;

import oving4.Person;
import oving4.PersonPanel;

public class MainPersonPanel extends JPanel {
	private PersonPanel personPanelA;
	private PassivePersonPanel personPanelB;

	private Person model;

	public MainPersonPanel() {
		model = new Person();
		personPanelA = new PersonPanel();
		personPanelB = new PassivePersonPanel();
		personPanelA.setModel(model);
		personPanelB.setModel(model);
		add(personPanelA);
		add(personPanelB);
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame("...");
		frame.getContentPane().add(new MainPersonPanel());
		frame.pack();
		frame.setVisible(true);
	}
}
