package oving1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class ButtonsNText extends JPanel {
	
	public JPanel panel;
	public  ButtonGroup buttonGroup;
	public   JTextField TextLine;
	public JToggleButton UpperCaseButton;
	public JToggleButton LowerCaseButton;
	public JCheckBox ContinousButton;
	
	public ButtonsNText()
	{
		panel = new JPanel();
		add(panel);
		
		TextLine = new JTextField();
		TextLine.setColumns(25);
		TextLine.setName("TextLine");
		TextLine.addKeyListener(new EnterPressAction());
		panel.add(TextLine);
		
		buttonGroup = new ButtonGroup();
		
		UpperCaseButton = new JToggleButton();
		UpperCaseButton.setText("Upper case");
		UpperCaseButton.addActionListener(new UpperCaseAction());
		buttonGroup.add(UpperCaseButton);
				
		LowerCaseButton = new JToggleButton();
		LowerCaseButton.setText("Lower case");
		LowerCaseButton.addActionListener(new LowerCaseAction());
		buttonGroup.add(LowerCaseButton);
		
		panel.add(UpperCaseButton);
		panel.add(LowerCaseButton);
		
		ContinousButton = new JCheckBox();
		ContinousButton.setText("Continous?");
		panel.add(ContinousButton);
		
	}
	
	public void ChangeCase()
	{
		int position = TextLine.getCaretPosition();
		if (UpperCaseButton.isSelected()) {
			TextLine.setText(TextLine.getText().toUpperCase());
		}
		else {
			TextLine.setText(TextLine.getText().toLowerCase());
		}
		TextLine.setCaretPosition(position);
	}
	
	public class EnterPressAction implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == e.VK_ENTER) {
				ChangeCase();
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			if (ContinousButton.isSelected()){
				ChangeCase();
			}
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class UpperCaseAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			TextLine.setText(TextLine.getText().toUpperCase());
		}
	}
	
	public class LowerCaseAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			TextLine.setText(TextLine.getText().toLowerCase());
		}
	}
	
	public static void main(String args[]) 
	{
		JFrame frame = new JFrame("Oving1");
		frame.getContentPane().add(new ButtonsNText());
		frame.pack();
		frame.setVisible(true);
	}
}