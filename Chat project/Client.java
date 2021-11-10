//////////////////////////////////////////////////////////////////////
//	
//	Application Name : Client(socket programming)
//	Description : Client side application which provide facility to 
//				  chat with server.
//  Date   : 20/10/2021
//	Author : Harish Vijay Bavne.
//
/////////////////////////////////////////////////////////////////////

// Required imports
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

// class for client connection
class cli extends Frame implements ActionListener
{
	private Socket socket;			// for socket.
	private BufferedReader br1;		// for reading message from server.
	private BufferedReader br2;		// for readding input from standard input device(Keyboard).
	private PrintWriter out;		// for sending message to server.
	private String str1;			// for storing meassage from server.
	private String str2;			// for storing message from keyboard.

	private Frame MainFrame;		// GUI Frame object
	private Label header;			// Label for header(Server)
	private Label Received;			// Label for TextArea STR1
	private Label Send;				// Label for TextArea STR2
	private TextArea STR1;			// TextArea
	private TextArea STR2;			// TextArea
	private Button SEND;			// Button
	private Button CLOSE;			// Button

	// Constructor
	public cli()
	{

		try
		{
			System.out.println("Client is running..");
			socket = new Socket("127.0.0.1",4545);  // Connection with server with server port number
			System.out.println("Connection done..");
			MainFrame  = new Frame("Client");		// GUI frame object creation
			MainFrame.setSize(500,500);				// Set size of Frame
			MainFrame.setBackground(Color.LIGHT_GRAY);

			// For closing GUI window using cross button
			MainFrame.addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent obj)
				{
					System.exit(0);
				}
			});
		
			MainFrame.setVisible(true);
			connection();							// calling connection method
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	// Method to establish connection with server.
	private void connection()
	{
		try
		{
			br1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			br2 = new BufferedReader(new InputStreamReader(System.in));

			out = new PrintWriter(socket.getOutputStream());

			createGUI();     // Calling to createGUI method
			startReading();		// Calling startReading method
			//startWritting();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	// Method for setup of GUI components
	private void createGUI()
	{
		MainFrame.setLayout(null);			// frame layout
		header = new Label("Client");		// label
		header.setBounds(200,20,50,50);		// set label
		header.setAlignment(Label.CENTER);	// label set at center
		MainFrame.add(header);				// add label on frame

		Received = new Label("Receive");	// Label
		Received.setBounds(20,180,70,40);	// set label
		MainFrame.add(Received);			// add label on frame

		STR1 = new TextArea();				// Text area to display message
		STR1.setBounds(90,80,370,260);		// set text area
		MainFrame.add(STR1);				// addd on frame

		Send = new Label("Send");			// Label
		Send.setBounds(20,350,70,40);		// set label
		MainFrame.add(Send);				// add on frame

		STR2 = new TextArea();				// Teat area to send message
		STR2.setBounds(90,350,370,50);		// set text area
		MainFrame.add(STR2);				// add on frame

		SEND = new Button("SEND");			// button
		SEND.setActionCommand("SEND");		// set acction command
		SEND.setBounds(130,430,70,40);		// set button
		SEND.addActionListener(this);		// add acton listner to button to catch event
		MainFrame.add(SEND);				//add on frame

		CLOSE = new Button("CLOSE");				// set button to close chat
		CLOSE.setActionCommand("CLOSE");			// add actioncommand
		CLOSE.setBounds(300,430,70,40);				// set size and display
		CLOSE.addActionListener(this);				// add actionListener
		MainFrame.add(CLOSE);						// add on frame
		MainFrame.setVisible(true);					// set frame to visible

	}

	// Call back method for event handling
	public void actionPerformed(ActionEvent obj)
	{
		String command = obj.getActionCommand();	// get action command while clicking button
		if (command == "SEND")
		{
			str2 = STR2.getText();					// get input from text area
			out.println(str2);						// send to client
			out.flush();
			STR2.setText("");
		}
		else if(command == "CLOSE")
		{
			System.exit(0);
		}
	}

	// method for receiving message
	private void startReading()
	{
		// Thread for reading message
		Runnable r1=()->{
			System.out.println("Reader Started.");
			try
			{
				while(true)
				{
					str1 = br1.readLine();
					if(str1 == null)		//If message received from client is null then socket closed chat terminated.
					{
						System.out.println("Server terminated the chat.");
						socket.close();
						System.exit(0);
						break;
					}
					else
					{
						STR1.append(str1+"\n");	// append message on received text area(Server message)
					}
				}

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		};
		// Thread started
		new Thread(r1).start();
	}

	// method for sending message
	private void startWritting()
	{
		// Thread for writing message
		Runnable r2=()->{
			System.out.println("Writter started.");
			try
			{
				while(true)
				{
					str2 = br2.readLine();	// Read input from standarad input device(keyboard)
					if(str2.equals("exit"))	// 
					{
						socket.close();		// socket connection close
						System.exit(0);
						break;
					}
					else
					{
						out.println(str2);	// send message through output stream(socket output stream)
						out.flush();		// flush output stream
					}
				}

			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Connection closed");
			}

		};
		// Thread Started
		new Thread(r2).start();
	}
}

// class which contains main method(Entry point function)
class Client
{
	public static void main(String args[]) 
	{
		cli obj = new cli();		// object of cli class. 
		
	}
}