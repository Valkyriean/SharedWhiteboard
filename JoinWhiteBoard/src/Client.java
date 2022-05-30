// written by Jiachen Li, 1068299

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JOptionPane;

public class Client {
	private static GUI gui = null;
	private static boolean running = true;
	private static String serverIp;
	private static int serverPort;
	private static String username;
	private static final Logger logger = Logger.getLogger(Client.class.getName());
	public static void main(String[] args) {
		int listeningPort = 0;
		ServerSocket listeningSocket;
		createLogger();
		// Arguments reading
		if (args.length != 3) 
		{
			System.out.println("Wrong number of arguement");
	    	System.out.println("Usage: java JoinWhiteBoard <serverIPAddress> <serverPort> <username>");
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
	    	System.out.println("Usage: java JoinWhiteBoard <serverIPAddress> <serverPort> <username>");
	    	logger.log(Level.SEVERE, "Invalid port", e);
	    	return;
	    }
		// Listening socket setup
		try {
			listeningSocket = new ServerSocket(0);
			listeningPort = listeningSocket.getLocalPort();
			logger.info("Listening to port "+listeningPort);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to setup Listening port", e);
			return;
		}
		// Request to join white board
		if(!requestJoin(username, listeningPort)) {
			System.exit(0);;
		}
		// Open window if joined
		gui = new GUI();
		// listener
		while(running) {
			try {
				Socket client = listeningSocket.accept();
				listen(client);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Error occur in listening port", e);
			}
		}
		
    }
	
	// Log set up based on SampleLoggingDemo.java from tutorial demo
	private static void createLogger() {
		Handler handler = null;
		Formatter simpleFormatter = null;
		try{
		    // Creating Handler
			handler = new FileHandler("./client.log");
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
		    logger.info("client starting ");
		}catch(Exception e){
		    logger.log(Level.SEVERE, "Error occur in creating logger", e);
		}
	}
	
	// Request to join shared white board
	public static boolean requestJoin(String username, int listeningPort) {
		try{
			Socket socket = new Socket(serverIp,serverPort);
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			output.writeUTF("joinRequest<br>"+username+"<br>"+listeningPort);
			output.flush();
			logger.info("Join request sent, waiting for server reply");
			String reply = input.readUTF();
			socket.close();
			switch (reply) {
			case "duplicateUsername":
				logger.info("Duplicate sername");
				JOptionPane.showMessageDialog(null,"Username duplicated, please try a new one");
				return false;
			case "rejected":
				logger.info("Join request rejected by manager");
				JOptionPane.showMessageDialog(null,"You are rejected by the server, please try again");
				return false;
			case "accepted":
				logger.info("Joined");
				return true;
			default:
				return false;
			}
		}catch (Exception e) {
			logger.log(Level.SEVERE, "Error occur in request join", e);
			return false;
		}
	}
	
	// Listen request from server
	public static void listen(Socket client) {
		try{
			DataInputStream input = new DataInputStream(client.getInputStream());
			String request = input.readUTF();
			logger.info("From server get request "+request);
			String[] r = request.split("<br>",2);
			String type = r[0];
			switch(type) {
			case "kick":
				logger.info("Kicked by manager");
				JOptionPane.showMessageDialog(null,"You have been kicked by manager");
				running=false;
				gui.dispose(); 
				System.exit(0);
			case "draw":
				logger.info("Request to draw "+r[1]);
				getDraw(r[1]);
				break;
			case "clear":
				logger.info("Request to clear canvas");
				gui.getShapes().clear();
				break;
			case "userList":
				logger.info("Request to update userlist "+r[1]);
				String userList = r[1];
				gui.updateUserList(userList);
				break;
			case "hostClose":
				logger.info("Request to close ");
				JOptionPane.showMessageDialog(null,"Host has closed the server");
				running=false;
				gui.dispose();
				System.exit(0);
				return;
			case "chat":
				logger.info("Request to update chat "+r[1]);
				gui.addChat(r[1]);
			}			
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error occur in processing request from server", e);
		}
	}
	
	// post shape drawn to server
	public static void postDraw(String drawable) {
		try {
			logger.info("Post draw to server: "+drawable);
			Socket socket = new Socket(serverIp,serverPort);
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			output.writeUTF("draw<br>"+username+"<br>"+drawable);
			output.flush();
			socket.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error occur in post draw " + drawable, e);
			// Server lost
			JOptionPane.showMessageDialog(null,"Connection Lost, client will close");
			exit();
		}
	}
	
	// Request exit to server
	public static void exit() {
		try {
			Socket socket = new Socket(serverIp,serverPort);
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			output.writeUTF("exit<br>"+username);
			output.flush();
			socket.close();
			System.exit(0);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error occur in request exit ", e);
			System.exit(0);
		}
	}
	
	// Add shape from server to canvas
	public static void getDraw(String data) {
		logger.info("Add shape from server to canvas");
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
	
	// Send chat to server
	public static void sentChat(String chat) {
		try {
			logger.info("Send chat to server: "+chat);
			Socket socket = new Socket(serverIp,serverPort);
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			output.writeUTF("chat<br>"+chat);
			output.flush();
			socket.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error occur in send chat to server: "+chat, e);
			JOptionPane.showMessageDialog(null,"Connection Lost, client will close");
			exit();
		}
	}
	
	public static Logger getLogger() {
		return logger;
	}
	
	public static String getUsername() {
		return username;
	}
}
