import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ServerSocketFactory;
import javax.swing.JOptionPane;

public class Client {
//	private static DataInputStream input = null;
//	private static DataOutputStream output = null;
	public static GUI gui = null;
	private static boolean running = true;
	private static String serverIp;
	private static int serverPort;
	public static String username;
	
	public static void main(String[] args) {
		
		int listeningPort = 0;
		ServerSocket listeningSocket;
		// Arguments reading
		if (args.length != 3) 
		{
			System.out.println("Wrong number of arguement");
	    	System.out.println("Usage: java JoinWhiteBoard <serverIPAddress> <serverPort> <username>");
			return;
		}
		serverIp = args[0];
		username=args[2];
		try
		{
			serverPort = Integer.parseInt(args[1]);
	    }
	    catch (NumberFormatException ex){
	    	System.out.println("Invalid port");
	    	System.out.println("Usage: java JoinWhiteBoard <serverIPAddress> <serverPort> <username>");
	    	return;
	    }
		

		
		try {
			listeningSocket = new ServerSocket(0);
			listeningPort = listeningSocket.getLocalPort();
			System.out.println(listeningPort);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		if(!requestJoin(username, listeningPort)) {
			return;
		}
		gui = new GUI();
		
		// listener
		while(running) {
			try {
				Socket client = listeningSocket.accept();
				listen(client);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
    }
	
	public static boolean requestJoin(String username, int listeningPort) {
		try{
			Socket socket = new Socket(serverIp,serverPort);
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			output.writeUTF("joinRequest<br>"+username+"<br>"+listeningPort);
			output.flush();
			System.out.println("Join request sent, waiting for server reply");
			String reply = input.readUTF();
			socket.close();
			switch (reply) {
			case "duplicateUsername":
				System.out.println("Username duplicated, please try a new one");
				return false;
			case "rejected":
				System.out.println("You are rejected by the server, please try again");
				return false;
			case "accepted":
				System.out.println("Joined");
				return true;
			default:
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void listen(Socket client) {
		try{
			DataInputStream input = new DataInputStream(client.getInputStream());
			String request = input.readUTF();
			System.out.print(request);
			String[] r = request.split("<br>",2);
			String type = r[0];
			switch(type) {
			case "kick":
				JOptionPane.showMessageDialog(null,"You have been kicked by manager");
				running=false;
				gui.dispose(); 
				System.exit(0);
			case "draw":
				getDraw(r[1]);
				break;
			case "clear":
				gui.shapes.clear();
				break;
			case "userList":
				String userList = r[1];
				gui.updateUserList(userList);
				break;
			case "hostClose":
				JOptionPane.showMessageDialog(null,"Host has closed the server");
				running=false;
				gui.dispose();
				System.exit(0);
				return;
			case "chat":
				gui.updateChat(r[1]);
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void postDraw(String s) {
		try {
			Socket socket = new Socket(serverIp,serverPort);
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			output.writeUTF("draw<br>"+username+"<br>"+s);
			output.flush();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void exit() {
		try {
			Socket socket = new Socket(serverIp,serverPort);
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			output.writeUTF("exit<br>"+username);
			output.flush();
			socket.close();
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void getDraw(String data) {
		String[] s = data.split(",");
        String type = s[0];
        switch(type){
        case "Line":
        	gui.shapes.add(new Line(s));
        	break;
        case "Circle":
        	gui.shapes.add(new Circle(s));
        	break;
        case "Triangle":
        	gui.shapes.add(new Triangle(s));
        	break;
        case "Rectangle":
        	gui.shapes.add(new Rectangle(s));
        	break;
        case "Text":
        	gui.shapes.add(new Text(data.split(",",5)));
        	break;
        }
        gui.repaint();
	}
	
	public static void sentChat(String chat) {
		try {
			Socket socket = new Socket(serverIp,serverPort);
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			output.writeUTF("chat<br>"+chat);
			output.flush();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
