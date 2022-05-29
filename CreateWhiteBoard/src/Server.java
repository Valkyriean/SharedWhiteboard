// written by Jiachen Li, 1068299

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.util.logging.Formatter;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class Server {
	private static String username;
	private static final Logger logger = Logger.getLogger(Server.class.getName());
	private static ArrayList<User> users = new ArrayList<User>();
	private static GUI gui = null;
	private static boolean running = true;
	
	
	public static void main(String[] args) {
		String serverIp;
		int serverPort;
		ServerSocket listeningSocket=null;
		createLogger();
		// Arguments reading
		if (args.length != 3) 
		{
			System.out.println("Wrong number of arguement");
	    	System.out.println("Usage: java CreateWhiteBoard <serverIPAddress> <serverPort> <username>");
	    	logger.log(Level.SEVERE, "Wrong number of arguement");
			return;
		}
		serverIp = args[0];
		username=args[2];
		try
		{
			serverPort = Integer.parseInt(args[1]);
	    }
	    catch (NumberFormatException e){
	    	System.out.println("Invalid port");
	    	System.out.println("Usage: java CreateWhiteBoard <serverIPAddress> <serverPort> <username>");
	    	logger.log(Level.SEVERE, "Invalid port", e);
	    	return;
	    }

		gui = new GUI();
		// Listener
		try {
			listeningSocket = new ServerSocket(serverPort);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to create listenting socket", e);
			System.exit(1);;
		}

		while(running) {
			try {
				Socket client = listeningSocket.accept();
				listen(client);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Listenting main loop failed", e);
			}
		}
    }
	
	// Log set up based on SampleLoggingDemo.java from tutorial demo
	private static void createLogger() {
		Handler handler = null;
		Formatter simpleFormatter = null;
		try{
		    // Creating Handler
			handler = new FileHandler("./server.log");
		    // Creating SimpleFormatter
		    simpleFormatter = new SimpleFormatter();
		    // Assigning handler to logger
		    logger.addHandler(handler);
		    logger.info("Message: Logger with DEFAULT FORMATTER");
		    // Setting formatter to the handler
		    handler.setFormatter(simpleFormatter);
		    // Setting Level to ALL
		    handler.setLevel(Level.FINEST);
		    logger.setLevel(Level.FINEST);
		    // will not be printed as level is info
		    logger.info("server starting ");
		}catch(Exception e){
		    logger.log(Level.SEVERE, "Error occur in create logger", e);
		}
	}
	
	
	private static void listen(Socket client) throws IOException {
		DataInputStream in = new DataInputStream(client.getInputStream());
		DataOutputStream out = new DataOutputStream(client.getOutputStream());
		String request = in.readUTF();
		logger.info("Request received "+request);
		// limit to length 2 for safety
		String[] r = request.split("<br>", 2);
		String type = r[0];
		String username;
		switch(type) {
		case "joinRequest":
			r = request.split("<br>", 3);
			username= r[1];
			boolean duplicated = false;
			for(User user: users) {
				if(user.username.equals(username)) {
					logger.info("Duplicated username caused by "+username);
					out.writeUTF("duplicateUsername");
					out.flush();
					duplicated = true;
					break;
				}
			}
			if(duplicated) {
				break;
			}
			else if(JOptionPane.showConfirmDialog(null, username+" want to join your whiteboard, accept?") == 0) {
				logger.info(username + " has joined");
				out.writeUTF("accepted");
				out.flush();
				String clientIp = client.getRemoteSocketAddress().toString().split(":")[0].substring(1);
				User user = new User(username, clientIp, r[2]);
				sendAllDraw(user);
				users.add(user);
				gui.updateKickList(users);
				broadcastUserList();
				break;
			}
			else {
				logger.info("Manager rejected "+username);
				out.writeUTF("rejected");
				out.flush();
				break;
			}
		case "exit":
			logger.info(r[1]+" has left");
			User user=null;
			for(User u: users) {
				if(u.username.equals(r[1])) {
					user = u;
				}
			}
			users.remove(user);
			gui.updateKickList(users);
			broadcastUserList();
			break;
		case "draw":
			r = request.split("<br>", 3);
			logger.info(r[1]+" has drawn a "+r[2]);
			username = r[1];
			getDraw(r[2]);
			
			broadcastOthers(r[2], username);
			break;
		case "chat":
			logger.info("Chat received "+r[1]);
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
        	gui.getShapes().add(new Line(s));
        	break;
        case "Circle":
        	gui.getShapes().add(new Circle(s));
        	break;
        case "Triangle":
        	gui.getShapes().add(new Triangle(s));
        	break;
        case "Rectangle":
        	gui.getShapes().add(new Rectangle(s));
        	break;
        case "Text":
        	gui.getShapes().add(new Text(data.split(",",5)));
        	break;
        }
        gui.repaint();
	}
	
	private static void sendDraw(String drawable, User user) {
		try {
			logger.info("Send drawable "+drawable+" to "+user.username);
			Socket client = new Socket(user.ip,user.port);
			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			out.writeUTF("draw<br>"+drawable);
			out.flush();
			client.close();
		}catch(Exception e) {
			//Disconnected user removal
//			users.remove(user);
//			gui.updateKickList(users);
//			broadcastUserList();
			logger.log(Level.SEVERE, "Send drawable "+drawable+" to "+user+"failed", e);
		}
	}
	
	public static void broadcastDraw(String drawable) {
		logger.info("Send drawable "+drawable+" to all users");
		for(User u: users) {
			sendDraw(drawable, u);
		}
	}
	
	private static void sendAllDraw(User u) {
		logger.info("Send all drawable to "+u.username);
		for(Drawable d:gui.getShapes()) {
			sendDraw(d.toString(), u);
		}
	}
	private static void broadcastOthers(String drawable, String username) {
		logger.info("Send all drawable to all users except "+username);
		for(User u: users) {
			if(u.username.equals(username)) {
				continue;
			}
			sendDraw(drawable, u);
		}
	}
	
	public static void broadcastNewFile() {
		logger.info("Broadcast new file to all users");
		for(User user: users) {
			try {
				Socket client = new Socket(user.ip,user.port);
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
				out.writeUTF("clear");
				out.flush();
				client.close();
				sendAllDraw(user);
			}
			catch(Exception e){
				logger.log(Level.SEVERE, "Broad cast new file failed for user " + user.username, e);
			}
		}
	}
	
	public static void kickUser(User user) {
		logger.info("Kick user "+user.username);
		try {
			Socket client = new Socket(user.ip,user.port);
			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			out.writeUTF("kick");
			out.flush();
			client.close();
		}catch(Exception e) {
			logger.log(Level.SEVERE,"Failed to kick user "+user.username, e);
		}
		users.remove(user);
		gui.updateKickList(users);
		broadcastUserList();
	}
	
	public static void exit() {
		logger.info("Client exit");
		broadcastHostClose();
		gui.dispose();
		System.exit(0);
	}
	
	private static void broadcastUserList() {
		logger.info("Broadcast userlist");
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
			}
			catch(Exception e){
				logger.log(Level.SEVERE,"Failed to Broadcast userlist to "+user.username, e);
			}
		}
	}
	
	private static void broadcastHostClose() {
		logger.info("Broadcast host close");
		for(User user: users) {
			try {
				Socket client = new Socket(user.ip,user.port);
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
				out.writeUTF("hostClose");
				out.flush();
				client.close();
			}
			catch(Exception e){
				logger.log(Level.SEVERE,"Failed to Broadcast host close "+user.username, e);
			}
		}
	}
	
	public static void broadcastChat(String chat) {
		logger.info("Broadcast chat "+chat);
		for(User user: users) {
			try {
				Socket client = new Socket(user.ip,user.port);
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
				out.writeUTF("chat<br>"+chat);
				out.flush();
				client.close();
			}
			catch(Exception e){
				logger.log(Level.SEVERE,"Failed to Broadcast chat to "+user.username+
						" about "+chat, e);
			}
		}
	}
	
	public static String getUsername() {
		return username;
	}
	
	public static Logger getLogger() {
		return logger;
	}
}
