public class Client {

	public static void main(String[] args) {
		String ip, username;
		int port;
		GUI gui;
		// Arguments reading
		if (args.length != 3) 
		{
			System.out.println("Wrong number of arguement");
	    	System.out.println("Usage: java CreateWhiteBoard <serverIPAddress> <serverPort> <username>");
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
	    	System.out.println("Usage: java CreateWhiteBoard <serverIPAddress> <serverPort> <username>");
	    	return;
	    }
		
		
		
		// Initialize GUI
		try 
		{
			gui = new GUI();
			gui.setVisible(true);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		

    }

}
