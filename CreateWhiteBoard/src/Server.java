import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.net.ServerSocketFactory;
import javax.swing.JOptionPane;

public class Server {
	private static ArrayList<String> usernames = new ArrayList<String>();
	public static void main(String[] args) {
		
		String ip, username;
		int port;
		GUI gui = null;
//		ServerSocket listentSocket=null;
//		Socket clientSocket=null;
		ServerSocketFactory factory = ServerSocketFactory.getDefault();
		
		
		// Arguments reading
		if (args.length != 3) 
		{
			System.out.println("Wrong number of arguement");
	    	System.out.println("Usage: java CreateWhiteBoard <serverIPAddress> <serverPort> <username>");
			return;
		}
		ip = args[0];
		username=args[2];
		usernames.add(username);
		try
		{
			port = Integer.parseInt(args[1]);
	    }
	    catch (NumberFormatException ex){
	    	System.out.println("Invalid port");
	    	System.out.println("Usage: java CreateWhiteBoard <serverIPAddress> <serverPort> <username>");
	    	return;
	    }
		
		try 
		{
			gui = new GUI();
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		gui.setVisible(true);
		
		
		try(ServerSocket server = factory.createServerSocket(port)){
			System.out.println("Waiting for client connection");
			while(true) {
				Socket client = server.accept();
				Thread t = new Thread(()->serveClient(client));
				t.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// Initialize GUI
		

    }
	
	private static void serveClient(Socket client) {
		try(Socket clientSocket = client){
			DataInputStream input = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
			String request = input.readUTF();
			String type = request.split("<br>")[0];
			switch(type) {
			case "joinRequest":
				String[] r = request.split("<br>");
				String username = r[1];
				if(usernames.contains(username)) {
					output.writeUTF("duplicateUsername");
					output.flush();
					return;
				} else {
					if(JOptionPane.showConfirmDialog(null, username+" want to join your whiteboard, accept?") == 0) {
						output.writeUTF("accepted");
						output.flush();
						usernames.add(username);
						System.out.print("welcome");
						// TODO
						
					}else {
						output.writeUTF("rejected");
						output.flush();
						return;
					}
					
				}
			}
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
