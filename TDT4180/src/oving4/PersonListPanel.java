package oving4;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PersonListPanel extends JPanel {
	private JList PersonList;
	private PersonPanel personPanel;
	private JButton NewPersonButton;
	private JButton DeletePersonButton;

	public PersonListPanel() {
		super();
		createGUI();
	}
	
	private void createGUI()
	{
		GridBagLayout gbLayout = new GridBagLayout();
		gbLayout.columnWidths = new int[] {50, 50, 7};
		gbLayout.rowHeights = new int[] {7, 35};
		gbLayout.columnWeights = new double[] {0.0, 0.0, 0.1};
		gbLayout.rowWeights = new double[] {1.0, 0.0};
		this.setLayout(gbLayout);
		this.setPreferredSize(new java.awt.Dimension(600,240));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		PersonList = new JList();
		PersonList.setName("PersonList");
		add(PersonList, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		PersonList.setCellRenderer(new PersonRenderer());
		PersonList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		PersonList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				if (PersonList.getSelectedIndex() >= 0) 
				{
					personPanel.setModel((Person) PersonList.getSelectedValue());
					System.out.println(((Person)PersonList.getSelectedValue()).getName());
					System.out.println(personPanel.model.getName());
				}
				else
					personPanel.setModel(new Person());
			}
		});
		
		personPanel = new PersonPanel();
		personPanel.setName("PersonPanel");
		add(personPanel, new GridBagConstraints(2, 0, 1, 2, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		
		NewPersonButton = new JButton();
		NewPersonButton.setName("NewPersonButton");
		add(NewPersonButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		NewPersonButton.setText("New");
		NewPersonButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Person person = new Person();
				getModel().addElement(person);
				PersonList.setSelectedValue(person, true);
			}
		});
		
		DeletePersonButton = new JButton();
		DeletePersonButton.setName("DeletePersonButton");
		add(DeletePersonButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		DeletePersonButton.setText("Delete");
		DeletePersonButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				getModel().removeElement(PersonList.getSelectedValue());
			}
		});
	}
	
	private void setModel(DefaultListModel model)
	{
		PersonList.setModel(model);
	}
	
	private DefaultListModel getModel()
	{
		return (DefaultListModel) PersonList.getModel();
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame("...");
		PersonListPanel personListPanel = new PersonListPanel();
		frame.getContentPane().add(personListPanel);
		frame.pack();
		frame.setVisible(true);
		
		DefaultListModel personListModel = new DefaultListModel();
		Person person = new Person();
		System.out.println(person.getName());
		personListModel.addElement(new Person());
		personListModel.addElement(new Person());
		personListModel.addElement(new Person());
		personListPanel.setModel(personListModel);
	}
}
