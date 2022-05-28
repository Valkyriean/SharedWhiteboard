import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {

	public static void save(String file, ArrayList<Drawable> shapes) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			for(Drawable d:shapes) {
				out.println(d.toString());
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	    
		
		
		
	}
	
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
			System.out.println("An error occurred.");
		    e.printStackTrace();
		}
		
		return shapes;
	}
	
	public static void main(String[] args) throws IOException {
		open(new File("123.txt"));
	}

}
