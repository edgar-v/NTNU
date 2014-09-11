package oving4;

import java.beans.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class PersonPanel extends JPanel implements PropertyChangeListener {
	
	public Person model;
	
	protected JLabel nameLabel;
	protected JTextField NamePropertyComponent;
	protected JLabel emailLabel;
	protected JTextField EmailPropertyComponent;
	protected JLabel birthdayLabel;
	protected JTextField DateOfBirthPropertyComponent;
	protected JLabel genderLabel;
	public JComboBox GenderPropertyComponent;
	protected JLabel heightLabel;
	protected JSlider HeightPropertyComponent;
	
	protected GridBagConstraints c;

	public PersonPanel() {
		GridBagLayout thisLayout = new GridBagLayout();
		thisLayout.rowWeights = new double[] {1, 1, 1, 1, 2};
		thisLayout.rowHeights = new int[] {7, 7, 7, 7, 7};
		thisLayout.columnWeights = new double[] {0, 1};
		thisLayout.columnWidths = new int[] { 10, 400};
		this.setLayout(thisLayout);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		c = new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
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
		NamePropertyComponent.addActionListener(new actionListener());
		c.gridy = 0;
		this.add(NamePropertyComponent, c);
		
		EmailPropertyComponent = new JTextField();
		EmailPropertyComponent.addActionListener(new actionListener());
		c.gridy = 1;
		this.add(EmailPropertyComponent, c);
		
		
		DateOfBirthPropertyComponent = new JTextField();
		DateOfBirthPropertyComponent.addActionListener(new actionListener());
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

	private class actionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.out.println("model: " + model);
			
			if (model != null) {
				if (e.getSource() == NamePropertyComponent)
					System.out.println("lol");
					model.setName(NamePropertyComponent.getText());
					System.out.println(model.getName());
				if (e.getSource() == EmailPropertyComponent)
					model.setEmail(EmailPropertyComponent.getText());
				if (e.getSource() == DateOfBirthPropertyComponent)
					model.setDateOfBirth(DateOfBirthPropertyComponent.getText());
			}
		}
	}
	
	protected class itemListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			System.out.println("Lololol");
			if (e.getSource() == GenderPropertyComponent && model != null)
				model.setGender((Gender)GenderPropertyComponent.getSelectedItem());
			
		}
	}
	
	protected class changeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() == HeightPropertyComponent && model != null)
				model.setHeight(HeightPropertyComponent.getValue());
			
		}
		
	}
	
	// Set model
	public void setModel(Person person) {
		model = person;
		if (person != null)
		{
			NamePropertyComponent.setText(person.getName());
			EmailPropertyComponent.setText(person.getEmail());
			DateOfBirthPropertyComponent.setText(person.getDateOfBirth());
			HeightPropertyComponent.setValue(person.getHeight());
			GenderPropertyComponent.setSelectedItem(person.getGender());
			model.addPropertyChangeListener(this);
		}
	}
	
	public Person getModel() {
		return model;
	}
	
	// PropertyChangeListener
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == Person.NAME_PROPERTY) {
			NamePropertyComponent.setText(model.getName());
		}
		else if (evt.getPropertyName() == Person.EMAIL_PROPERTY) {
			EmailPropertyComponent.setText(model.getEmail());
		}
		else if (evt.getPropertyName() == Person.DATE_OF_BIRTH_PROPERTY) {
			DateOfBirthPropertyComponent.setText(model.getDateOfBirth());
		}
		else if (evt.getPropertyName() == Person.HEIGHT_PROPERTY) {
			HeightPropertyComponent.setValue(model.getHeight());
		}
		else if (evt.getPropertyName() == Person.GENDER_PROPERTY) {
			GenderPropertyComponent.setSelectedItem(model.getGender());
		}
	}
}
