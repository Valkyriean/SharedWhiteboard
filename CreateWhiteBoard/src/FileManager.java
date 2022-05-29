// written by Jiachen Li, 1068299

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;

public class FileManager {
	// Save canvas to file
	public static void save(String file, ArrayList<Drawable> shapes) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			for(Drawable d:shapes) {
				out.println(d.toString());
			}
			out.close();
		} catch(Exception e) {
			Server.getLogger().log(Level.SEVERE, "Error occur in save", e);
		}
	}
	// Open canvas from file
	public static ArrayList<Drawable> open(File file){
		ArrayList<Drawable> shapes = new ArrayList<Drawable>();
		try {
			Scanner myReader = new Scanner(file);
		    while (myReader.hasNextLine()) {
		    	String data = myReader.nextLine();
		        System.out.println(data);
		        String[] s = data.split(",");
		        String type = s[0];
		        switch(type){
		        case "Line":
		        	shapes.add(new Line(s));
		        	break;
		        case "Circle":
		        	shapes.add(new Circle(s));
		        	break;
		        case "Triangle":
		        	shapes.add(new Triangle(s));
		        	break;
		        case "Rectangle":
		        	shapes.add(new Rectangle(s));
		        	break;
		        case "Text":
		        	shapes.add(new Text(data.split(",",5)));
		        	break;
		        }
		    }
		    myReader.close();
		} 
		catch (FileNotFoundException e) {
			Server.getLogger().log(Level.SEVERE, "Error occur in open", e);
		}	
		return shapes;
	}
}
