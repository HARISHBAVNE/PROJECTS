//////////////////////////////////////////////////////////////////////
//	
//	Application Name : Calculator.
//	Description : This application provides GUI to perform arithmatic
//				  calculations.
//				  (Addition,Substraction,Multiplication and Division)
//  Input  : Two Number values.
//	Output : Showing result of particular operation.
//  Date   : 20/10/2021
//	Author : Harish Vijay Bavne.
//
/////////////////////////////////////////////////////////////////////

// Required Packages
import java.awt.*;
import java.awt.event.*;


// Class for GUI
class AWT_FRAME implements ActionListener
{
	// Required components
	Frame mainframe;
	TextField value1;
	TextField value2;
	
	Label Value1;
	Label Value2;

	Label header;
	Label result;
	Button ADD;
	Button SUB;
	Button MULT;
	Button DIV;

	// Constructor
	AWT_FRAME()
	{
		// Main frame creation
		mainframe = new Frame("Calculator");
		mainframe.setSize(500,300);
		mainframe.setBackground(Color.CYAN);

		// Window listener for closing window
		mainframe.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent obj)
			{
				System.exit(0);
			}
		});

		// Calling method to add components on frame.
		this.ComponentAdd();

		mainframe.setVisible(true);

	}

	// method for Mount components on frame 
	private void ComponentAdd()
	{
		mainframe.setLayout(null);				// Frame layout set to null.

		// header label
		header = new Label("Calculator");
		header.setAlignment(Label.CENTER);
		header.setBounds(200,20,100,100);		// Setting a visual box
		
		// result label to show result
		result = new Label();
		result.setBounds(200,210,200,100);		// Setting a visual box

		// create value1 textbox and its label
		value1 = new TextField("0");
		value1.setBounds(145,100,50,50);		// Setting a visual box
		Value1 = new Label("Value1");
		Value1.setBounds(95,100,50,50);			// Setting a visual box
		
		// create value2 textbox and its label
		value2 = new TextField("0");
		value2.setBounds(300,100,50,50);		// Setting a visual box
		Value2 = new Label("Value2");
		Value2.setBounds(250,100,50,50);		// Setting a visual box

		// Button for addition
		ADD = new Button("+");
		ADD.setBounds(110,180,50,50);			// Setting a visual box
		ADD.setActionCommand("+");				// to get notificaton Button pressed
		ADD.addActionListener(this);			// Action listener to listen Event

		// Button for substraction
		SUB = new Button("-");
		SUB.setBounds(180,180,50,50);			// Setting a visual box
		SUB.setActionCommand("-");				// to get notificaton Button pressed
		SUB.addActionListener(this);			// Action listener to listen Event

		// Button for multiplication
		MULT = new Button("*");
		MULT.setBounds(240,180,50,50);			// Setting a visual box
		MULT.setActionCommand("*");				// to get notificaton Button pressed
		MULT.addActionListener(this);			// Action listener to listen Event

		// Button for division					
		DIV = new Button("/");
		DIV.setBounds(310,180,50,50);			// Setting a visual box
		DIV.setActionCommand("/");				// to get notificaton Button pressed
		DIV.addActionListener(this);			// Action listener to listen Event 


		mainframe.add(header);				// Add header label on frame.
		mainframe.add(value1);				// Add textbox value1 on frame.
		mainframe.add(Value1);				// Add label for textbox value1 on frame.
		mainframe.add(Value2);				// Add label for textbox value2 on frame.
		mainframe.add(value2);				// Add textbox value2 on frame on frame.
		mainframe.add(ADD);
		mainframe.add(SUB);
		mainframe.add(MULT);
		mainframe.add(DIV);
		mainframe.add(result);				// Add result label on frame.
		mainframe.setVisible(true);
	}

	// call back method(called by JVM whenever button get clicked)
	public void actionPerformed(ActionEvent obj)
	{
		String command = obj.getActionCommand();
		if(command == "+")
		{
			double iret = (Double.parseDouble(value1.getText())) + (Double.parseDouble(value2.getText()));
			result.setText("Additon is:"+iret);
		}
		else if (command == "-")
		{
			double iret = (Double.parseDouble(value1.getText())) - (Double.parseDouble(value2.getText()));
			result.setText("Substraction is:"+iret);
		}
		else if (command == "*")
		{
			double iret = (Double.parseDouble(value1.getText())) * (Double.parseDouble(value2.getText()));
			result.setText("Multiplication is:"+iret);
		}
		else if (command == "/")
		{
			if((Double.parseDouble(value2.getText()) == 0.0))
			{
				result.setText("Can't divide by 0");	
			}
			else
			{
				double iret = (Double.parseDouble(value1.getText())) / (Double.parseDouble(value2.getText()));
			result.setText("Multiplication is:"+iret);
			}
			
		}
	}

}

// Entry point class (Main method)
class Calculator
{
	public static void main(String arg[])
	{
		AWT_FRAME f = new AWT_FRAME();
	}
}