import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class clientThread extends Thread {
	
	DataInputStream is = null;
	PrintStream os = null;
	Socket clientSocket = null;
	clientThread t[];
	
	public clientThread (Socket clienSocket, clientThread[] t) {
	/*
	 * Thread information and socket information from the main server are passed to this class.
	 * Thus, it can receive messages from other threads by messaging between threads, or forward a new message to other threads.
	 */
		this.clientSocket = clienSocket;
		this.t = t;
	}
	
	public void run() {
		String line;
		String name;
			try {  
			/*
			 * On line 33 of the code,a new name is requested from the client.
			 * On line 34 of the code,reading the name.
			 * On line between 36 to 40 this connection is notified to the other threads.
			 */
				is = new DataInputStream(clientSocket.getInputStream());
				os = new PrintStream(clientSocket.getOutputStream());
				os.println("What is your name?"); //requesting a new name from the client 
				name = is.readLine(); // Reading the name
				os.println("Type /exit to exit the chat");// This is the information message to clients."/exit" command is intended to disconnect the user.
				for (int i = 0; i <= 9; i++) { // connection is notified to the other threads(Clients).
					if (t[i] != null && t[i] != this) {
						t[i].os.println("Warning:"+name+" has joined the chat"); // announcing a new connected user
					}
				}
					while(true) {
						line = is.readLine(); // Reading clients message 
								if (line.startsWith("/exit")) { // Checking if the client wants to quit. 
									break; // The infinite loop is broken and the lines between 52 to 54  are notified to the other clients that the client has left the conversation.
								}
						for (int i = 0; i <= 9; i++) { // Transfer client message to other clients's screen.
								if (t[i] != null) {
									t[i].os.println("<"+name+"> "+line);	// Show the message 
								}
						}
					}
					for (int i = 0; i <= 9; i++) { // Announcing the lefted Client to other Clients(Thread)
						if (t[i] != null && t[i] !=this) {
							t[i].os.println("Warning:"+name+" has left chat"); 
						}
					}
					for (int i = 0; i <= 9; i++) {
						if (t[i] == this) {
							t[i]=null;
						}
					}
						is.close();
						os.close();
						clientSocket.close();
			}
			catch (IOException e) {};
	}
						
}
