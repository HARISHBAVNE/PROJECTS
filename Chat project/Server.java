//////////////////////////////////////////////////////////////////////
//	
//	Application Name : Server(socket programming)
//	Description : Server side application which provide facility to 
//				  chat with Client.
//  Date   : 20/10/2021
//	Author : Harish Vijay Bavne.
//
/////////////////////////////////////////////////////////////////////

// Required Imports
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

// class for server connection
class ser extends Frame implements ActionListener
{
	private ServerSocket server;		// serverscoket variable
	private Socket socket;				// for socket
	private BufferedReader br1;			// for reading input from client
	private BufferedReader br2;			// for reaading input from input device(Key board)
	private PrintWriter out;			// for sending a message to client 
	private String str1;				// for storing message from client
	private String str2;				// for soring message from keyboard

	private Frame MainFrame;			// GUI frame object
	private Label header;				// Label for header(Server)
	private Label Received;				// Label for TextArea STR1
	private Label Send;					// Label for TextArea STR2
	private TextArea STR1;				// TextArea
	private TextArea STR2;				// TextArea
	private Button SEND;				// Button
	private Button CLOSE;				// Button
	
	// Constructor
	public ser()	
	{
		try
		{
			server = new ServerSocket(4545);		// Server creation with port number
			MainFrame  = new Frame("Server");		// frame creation
			MainFrame.setSize(500,500);				// set size of frame
			MainFrame.setBackground(Color.LIGHT_GRAY);// Set bakground color to frame

			// Method to close frame
			MainFrame.addWindowListener(new WindowAdapter()	
			{
				public void windowClosing(WindowEvent obj)
				{
					System.exit(0);
				}
			});
			MainFrame.setVisible(true);				// frame visible
			connection();							// calling connection method	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	// Method to establish connection with client
	private void connection()
	{
		try
		{
			System.out.println("Server is running...");
			System.out.println("Waiting for clinet...");
			socket = server.accept();					// excecute when client access server with port number
			System.out.println("Connection done..");

			br1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			br2 = new BufferedReader(new InputStreamReader(System.in));

			out = new PrintWriter(socket.getOutputStream());
			createGUI();								// calling createGUI method
			startReading();								// Thread for reading message from Client
			//startWritting();							// Thread for writting message 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	 // Method to create GUI window
	private void createGUI()
	{
		MainFrame.setLayout(null);					// set layout null for customization
		header = new Label("Server");				// set header label
		header.setBounds(200,20,50,50);				// set size and place
		header.setAlignment(Label.CENTER);			// set header label at center postion
		MainFrame.add(header);						// add header label on frame

		Received = new Label("Receive");			// set Received label for TextArea(STR1)
		Received.setBounds(20,180,70,40);			// set size and place
		MainFrame.add(Received);					// add label on frame

		STR1 = new TextArea();						// set TextArea for receive message 
		STR1.setBounds(90,80,370,260);				// set size and place
		MainFrame.add(STR1);						// add TextArea on frame

		Send = new Label("Send");					// set Send label for TextArea(STR2)
		Send.setBounds(20,350,70,40);				// set size and place
		MainFrame.add(Send);						// add label on frame 

		STR2 = new TextArea();						// set TextArea for send message
		STR2.setBounds(90,350,370,50);				// set Size and place
		MainFrame.add(STR2);						// add on frame

		SEND = new Button("SEND");					// set button to send message
		SEND.setActionCommand("SEND");				// add actionCommand
		SEND.setBounds(130,430,70,40);				// set Size and place
		SEND.addActionListener(this);				// add action listener
		MainFrame.add(SEND);						// add on frame

		CLOSE = new Button("CLOSE");				// set button to close chat
		CLOSE.setActionCommand("CLOSE");			// add actioncommand
		CLOSE.setBounds(300,430,70,40);				// set size and display
		CLOSE.addActionListener(this);				// add actionListener
		MainFrame.add(CLOSE);						// add on frame
		MainFrame.setVisible(true);					// set frame to visible
	}

	// call back method (which handel event)
	public void actionPerformed(ActionEvent obj)
	{
		String command = obj.getActionCommand();	// get action command while clicking button
		if(command == "SEND")
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

	// method for reading message
	private void startReading()
	{
		// Thread-> is reading the message
		Runnable r1=()->{
			System.out.println("Reader started..");
			try
			{
				while(true)
				{
					str1 = br1.readLine();
					if(str1==null)				// If message received from client is null then socket closed chat terminated.
					{
						System.out.println("Client terminated the chat");
						socket.close();
						System.exit(0);
						break;
					}
					else
					{
						STR1.append(str1+"\n");		// append message on received text area(client message)
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

	// Method for writting message
	private void startWritting()
	{
		// Thread-> is writting a message
		Runnable r2=()->{
			System.out.println("Writer started");
			try
			{
				while(true)
				{
					str2 = br2.readLine();
					if(str2.equals("exit"))			// If server type exit then chat terminated and socket closed.
					{
						socket.close();				// socket connection close.
						System.exit(0);
						break;
					}
					out.println(str2);				// send message through output stream(socket output stream)
					out.flush();					// flush output stream.
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Connection closed");
			}
		};
		// Thread started
		new Thread(r2).start();
	}
}

// class which contains main method(Entry point function)
class Server
{
	public static void main(String[] args) 
	{
		ser obj = new ser();		// Object of ser class
	}
}