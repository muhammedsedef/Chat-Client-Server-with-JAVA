import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {
	static Socket clientSocket = null;
	static PrintStream os = null; // Used to send data 
	static DataInputStream is = null;
	static BufferedReader inputLine = null; // Used to read data 
	static boolean closed = false;
	
		public static void main (String [] args) {
			int port_number = 1234; // port number.
			String host = "127.0.0.1"; // local host adress.
			
			try {
				/*
				 * Initialize the variables.
				 */
				clientSocket = new Socket(host,port_number);
				inputLine = new BufferedReader(new InputStreamReader(System.in));
				os = new PrintStream(clientSocket.getOutputStream());
				is = new DataInputStream(clientSocket.getInputStream());
			} catch (UnknownHostException e) {
				System.err.println("Server discovery error");
			} catch (IOException e) {
				System.err.println("Server connettion error");
			}
		 if (clientSocket != null && os != null && is !=null) {
			 try {
				new Thread(new Client()).start(); // Creating a new Thread(Client)
				while(!closed) {
					os.println(inputLine.readLine()); // Read client's message and print.
				}
				os.close();
				is.close();
				clientSocket.close();
			} catch (IOException e) {
				System.err.println("Communication error");
			}
		}
		}
		public void run() {
			
			String responseLine;
			try {
				while((responseLine = is.readLine()) !=null) {
					
					System.out.println(responseLine); // Print client's message.
					if (responseLine.indexOf("*** Bye") !=-1) {
						break; // When the client write /exit then broke the infinite loop and closed became true on the line 58
					}
				}
					closed = true; // broken the infinite loop on the line 36.
				
			} catch (IOException e) {
				System.err.println("Communication error");
			}
		}
	
}
