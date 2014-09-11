import java.beans.*;
import java.awt.event.*;
import javax.swing.*;


public class PersonPanel extends JPanel implements PropertyChangeListener {
	
	private Person model;
	private JTextField nameField;
	private JButton clearButton;

	public PersonPanel() {
		nameField = new JTextField();
		nameField.setColumns(20);
		nameField.addActionListener(new myTextAction());
		add(nameField);
		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ClearButtonAction());
		add(clearButton);
	}

	/** Action for text field * */
	class myTextAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			model.setName(nameField.getText());
		}
	}

	/** Action for clearButton * */
	class ClearButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			model.setName("");
		}
	}
	
	// Set model
	public void setModel(Person theModel) {
		model = theModel;
		model.addPropertyChangeListener(this);
	}
	
	// PropertyChangeListener
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == Person.NAME_PROPERTY) {
			nameField.setText(model.getName());
		}
	}
}
