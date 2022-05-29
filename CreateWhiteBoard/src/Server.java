import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.net.ServerSocketFactory;
import javax.swing.JOptionPane;

public class Server {
	private static ArrayList<User> users = new ArrayList<User>();
	private static GUI gui = null;
	private static boolean running = true;
	public static String username;
	public static void main(String[] args) {
		String serverIp;
		int serverPort;
		ServerSocket listeningSocket=null;
				
		// Arguments reading
		if (args.length != 3) 
		{
			System.out.println("Wrong number of arguement");
	    	System.out.println("Usage: java CreateWhiteBoard <serverIPAddress> <serverPort> <username>");
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
	    	System.out.println("Usage: java CreateWhiteBoard <serverIPAddress> <serverPort> <username>");
	    	return;
	    }

		gui = new GUI();
		// Listener
		try {
			listeningSocket = new ServerSocket(serverPort);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);;
		}

		while(running) {
			try {
				Socket client = listeningSocket.accept();
				listen(client);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
	
	
	
	private static void listen(Socket client) throws IOException {
		DataInputStream in = new DataInputStream(client.getInputStream());
		DataOutputStream out = new DataOutputStream(client.getOutputStream());
		String request = in.readUTF();
		System.out.println(request);
		String[] r = request.split("<br>");
		String type = r[0];
		String username;
		switch(type) {
		case "joinRequest":
			username= r[1];
			for(User user: users) {
				if(user.username.equals(username)) {
					System.out.print(username+" wants to joined, but username duplicated");
					out.writeUTF("duplicateUsername");
					out.flush();
					return;
				}
			}
			if(JOptionPane.showConfirmDialog(null, username+" want to join your whiteboard, accept?") == 0) {
				out.writeUTF("accepted");
				out.flush();
				String clientIp = client.getRemoteSocketAddress().toString().split(":")[0].substring(1);
				User user = new User(username, clientIp, r[2]);
				System.out.println(username + " has joined");
				sendAll(user);
				users.add(user);
				gui.updateKickList(users);
				broadcastUserList();
				break;
			}
			else {
				out.writeUTF("rejected");
				out.flush();
				return;
			}
		case "exit":
			User user=null;
			for(User u: users) {
				if(u.username.equals(r[1])) {
					user = u;
				}
			}
			users.remove(user);
			gui.updateKickList(users);
			broadcastUserList();
			System.out.println(r[1]+" has left");
			return;
		case "draw":
			r = request.split("<br>", 3);
			username = r[1];
			getDraw(r[2]);
			broadcastOthers(r[2], username);
			break;
		case "chat":
			gui.addChat(r[1]);
			broadcastChat(r[1]);
			break;
		}
		client.close();
	}
	
	
	private static void getDraw(String data) {
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
	
	private static void sendDraw(String drawable, User user) {
		try {
			Socket client = new Socket(user.ip,user.port);
			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			out.writeUTF("draw<br>"+drawable);
			out.flush();
			client.close();
		}catch(Exception e) {
			//User dropped
//			users.remove(user);
//			gui.updateKickList(users);
//			broadcastUserList();
			e.printStackTrace();
		}
	}
	
	public static void broadcastDraw(String drawable) {
		for(User u: users) {
			sendDraw(drawable, u);
		}
	}
	
	private static void sendAll(User u) {
		for(Drawable d:gui.shapes) {
			sendDraw(d.toString(), u);
		}
	}
	private static void broadcastOthers(String drawable, String username) {
		for(User u: users) {
			if(u.username.equals(username)) {
				continue;
			}
			sendDraw(drawable, u);
		}
	}
	
	public static void broadcastNewFile() {
		for(User user: users) {
			try {
				Socket client = new Socket(user.ip,user.port);
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
				out.writeUTF("clear");
				out.flush();
				client.close();
				sendAll(user);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			
		}
	}
	
	public static void kickUser(User user) {
		try {
			Socket client = new Socket(user.ip,user.port);
			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			out.writeUTF("kick");
			out.flush();
			client.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		users.remove(user);
		gui.updateKickList(users);
		broadcastUserList();
	}
	
	public static void kickAll() {
		for(User user: users) {
			kickUser(user);
		}
	}
	
	public static void exit() {
		broadcastHostClose();
		gui.dispose();
		System.exit(0);
	}
	
	private static void broadcastUserList() {
		String[] userList = new String[1+users.size()];
		userList[0] = username;
		int i = 1;
		for(User user: users) {
			userList[i] = user.username;
			i++;
		}
		String msg = String.join("<usrbr>", userList);
		for(User user: users) {
			try {
				Socket client = new Socket(user.ip,user.port);
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
				out.writeUTF("userList<br>"+msg);
				out.flush();
				client.close();
				sendAll(user);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private static void broadcastHostClose() {
		for(User user: users) {
			try {
				Socket client = new Socket(user.ip,user.port);
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
				out.writeUTF("hostClose");
				out.flush();
				client.close();
				sendAll(user);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void broadcastChat(String chat) {
		for(User user: users) {
			try {
				Socket client = new Socket(user.ip,user.port);
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
				out.writeUTF("chat<br>"+chat);
				out.flush();
				client.close();
				sendAll(user);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}
