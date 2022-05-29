import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) {
		String ip, username;
		int port;
		GUI gui = null;
//		Socket socket=null;
		// Arguments reading
		if (args.length != 3) 
		{
			System.out.println("Wrong number of arguement");
	    	System.out.println("Usage: java JoinWhiteBoard <serverIPAddress> <serverPort> <username>");
			return;
		}
		ip = args[0];
		username=args[2];
		try
		{
			port = Integer.parseInt(args[1]);
	    }
	    catch (NumberFormatException ex){
	    	System.out.println("Invalid port");
	    	System.out.println("Usage: java JoinWhiteBoard <serverIPAddress> <serverPort> <username>");
	    	return;
	    }
		
		try (Socket socket = new Socket(ip,port)){
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			output.writeUTF("joinRequest<br>"+username);
			output.flush();
			System.out.println("Join request sent, waiting for server reply");
			String reply = input.readUTF();
			switch (reply) {
			case "duplicateUsername":
				System.out.println("Username duplicated, please try a new one");
				return;
			case "rejected":
				System.out.println("You are rejected by the server, please try again");
				return;
			case "accepted":
				System.out.println("Welcome");
				gui = new GUI();
				break;
			default:
				return;
			}
				
			
		}catch (UnknownHostException e) {
			e.printStackTrace();
			return;

		}catch (IOException e) {
			e.printStackTrace();
			return;
		}

    }

}
