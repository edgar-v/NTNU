import javax.swing.JPanel;
import javax.swing.JFrame;

public class PersonPanelExample_Simple extends JPanel {
	private PersonPanel personPanelA, personPanelB;

	private Person model;

	public PersonPanelExample_Simple() {
		model = new Person();
		personPanelA = new PersonPanel();
		personPanelA.setModel(model);
		add(personPanelA);
		personPanelB = new PersonPanel();
		personPanelB.setModel(model);
		add(personPanelB);
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame("...");
		frame.getContentPane().add(new PersonPanelExample_Simple());
		frame.pack();
		frame.setVisible(true);
	}
}
