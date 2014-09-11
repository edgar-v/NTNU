package oving2;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class PersonPanel extends JPanel {

	private JPanel panel;
	private Person model;
	
	private JLabel nameLabel;
	private JTextField NamePropertyComponent;
	private JLabel emailLabel;
	private JTextField EmailPropertyComponent;
	private JLabel birthdayLabel;
	private JTextField DateOfBirthPropertyComponent;
	private JLabel genderLabel;
	private JComboBox GenderPropertyComponent;
	private JLabel heightLabel;
	private JSlider HeightPropertyComponent;
	
	
	public PersonPanel() 
	{
		super();
		model = null;
		createGUI();
		Person person = createTestPerson();
		setModel(person);
	}
	
	public Person createTestPerson() {
		Person person = new Person();
		
		person.setName("Ola Nordmann");
		person.setEmail("ola.nordmann@gmail.com");
		person.setDateOfBirth("1993-06-27");
		person.setGender(Gender.male);
		person.setHeight(160);
		
		return person;
	}
	
	private void setModel(Person person)
	{
		model = person;
		NamePropertyComponent.setText(person.getName());
		EmailPropertyComponent.setText(person.getEmail());
		DateOfBirthPropertyComponent.setText(person.getDateOfBirth());
		GenderPropertyComponent.setSelectedItem(person.getGender());
		HeightPropertyComponent.setValue(person.getHeight());
	}
	
	private Person getModel()
	{
		return model;
	}
	
	private void createGUI() {
		GridBagLayout thisLayout = new GridBagLayout();
		thisLayout.rowWeights = new double[] {1, 1, 1, 1, 2};
		thisLayout.rowHeights = new int[] {7, 7, 7, 7, 7};
		thisLayout.columnWeights = new double[] {0, 1};
		thisLayout.columnWidths = new int[] { 10, 400};
		this.setLayout(thisLayout);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
		
		
		
		nameLabel = new JLabel();
		nameLabel.setText("Name:");
		this.add(nameLabel, c);
		
		emailLabel = new JLabel();
		emailLabel.setText("Email:");
		c.gridy = 1;
		this.add(emailLabel, c);
		
		birthdayLabel = new JLabel();
		birthdayLabel.setText("Birthday:");
		c.gridy = 2;
		this.add(birthdayLabel, c);
		
		genderLabel = new JLabel();
		genderLabel.setText("Gender:");
		c.gridy = 3;
		this.add(genderLabel, c);
		
		heightLabel = new JLabel();
		heightLabel.setText("Height:");
		c.gridy = 4;
		this.add(heightLabel, c);
		

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		
		NamePropertyComponent = new JTextField();
		NamePropertyComponent.addCaretListener(new caretListener());
		c.gridy = 0;
		this.add(NamePropertyComponent, c);
		
		EmailPropertyComponent = new JTextField();
		EmailPropertyComponent.addCaretListener(new caretListener());
		c.gridy = 1;
		this.add(EmailPropertyComponent, c);
		
		
		DateOfBirthPropertyComponent = new JTextField();
		DateOfBirthPropertyComponent.addCaretListener(new caretListener());
		c.gridy = 2;
		this.add(DateOfBirthPropertyComponent, c);
		
		Gender genders = null;
		GenderPropertyComponent = new JComboBox(genders.values());
		GenderPropertyComponent.addItemListener(new itemListener());
		c.fill = GridBagConstraints.NONE;
		c.gridy = 3;
		this.add(GenderPropertyComponent, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		
		HeightPropertyComponent = new JSlider(120, 220, 175);
		HeightPropertyComponent.addChangeListener(new changeListener());
		HeightPropertyComponent.setPaintTicks(true);
		HeightPropertyComponent.setMinorTickSpacing(5);
		HeightPropertyComponent.setMajorTickSpacing(10);
		HeightPropertyComponent.setPaintLabels(true);
		
		c.gridx = 1;
		c.gridy = 4;
		this.add(HeightPropertyComponent, c);
	}
	
	public class caretListener implements CaretListener {

		@Override
		public void caretUpdate(CaretEvent e) {
			if (model != null) {
				if (e.getSource() == NamePropertyComponent)
					model.setName(NamePropertyComponent.getText());
				if (e.getSource() == EmailPropertyComponent)
					model.setEmail(EmailPropertyComponent.getText());
				if (e.getSource() == DateOfBirthPropertyComponent.getText())
					model.setDateOfBirth(DateOfBirthPropertyComponent.getText());
			}
		}
	}
	
	public class itemListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			
			if (e.getSource() == GenderPropertyComponent && model != null)
				model.setGender((Gender)GenderPropertyComponent.getSelectedItem());
			
		}
	}
	
	public class changeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() == HeightPropertyComponent && model != null)
				model.setHeight(HeightPropertyComponent.getValue());
			
		}
		
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame("oving2");
		frame.getContentPane().add(new PersonPanel());
		frame.pack();
		frame.setVisible(true);
	}
}
