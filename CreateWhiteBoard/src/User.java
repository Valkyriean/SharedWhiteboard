// written by Jiachen Li, 1068299

public class User {
	public final String username, ip;
	public final int port;
	public User(String username, String ip, String port) {
		this.username = username;
		this.ip = ip;
		this.port = Integer.parseInt(port);
	}

}
