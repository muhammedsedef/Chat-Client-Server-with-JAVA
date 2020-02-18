import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	static Socket clientSocket = null;
	static ServerSocket serverSocket = null;
	static clientThread t[] = new clientThread[10]; // I limited the server to a maximum of 10 users.I keep all clients into a Array.
	
	public static void main(String[] args) {
		int port_number = 1234;
		try {
			serverSocket = new ServerSocket(port_number); // Listening to Client from Server 1234 port.
		} catch (IOException e) {
			System.out.println(e);
		}
		while(true) {
			try {
				
				clientSocket = serverSocket.accept(); // connection is directed to a socket.Data communication on server opened
				
				for (int i = 0; i <= 9; i++) {
					if (t[i] == null) {
						(t[i] = new clientThread(clientSocket,t)).start();/*
						 * I kept an object reference (pointer) for each connected client.
						 * This provide us to manage client-to-client communication.
						 * After a client connects to the server,the thread created for that client now communicates with other thread while chatting.
						 * And our network chat program actually turns into inter-thread chat.
						 */  

						break;
					}
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}
