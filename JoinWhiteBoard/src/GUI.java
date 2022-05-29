
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.Font;

public class GUI extends JFrame{

    public static void main(String[] args) {
        new GUI();

    }
    
    private String state, filePath;
    private int preX,preY,preX1,preY1;
    private ArrayList<Drawable> shapes = new ArrayList<Drawable>();
    private Color currentColor;
    

    
    private JLabel status = new JLabel();
    private JTextField textInput;
    private JTextField chatInput;
    
    public GUI(){
    	this.state="null";
    	this.filePath=null;
        this.setSize(800,600);
        this.setPreferredSize(new Dimension(800,600));
        this.setTitle("Distributed Shared White Board Manager");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.currentColor = Color.black;
        JButton colorButton = new JButton("Color");
        colorButton.setToolTipText("Click to select color, displying selected color");
        colorButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	currentColor = JColorChooser.showDialog(null, "Select color", Color.white);
            	colorButton.setForeground(currentColor);
        	}
        });
        
        
        
        
        
        
        
        
        
        JComponent panel = new GraphicsPanel();
//        JComponent panel = new JPanel();
        panel.setBackground(Color.WHITE);
        
        
        status.setText("Plase select the color and shape on left");
        
        JButton line = new JButton("Line");
        line.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "line_1";
        		status.setText("Line selected, click on canves at where you want line start from.");
        	}
        });
        JButton circle = new JButton("Circle");
        circle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "circle_1";
        		status.setText("Circle selected, click on canves at where you want center of the circle be.");
        	}
        });
        JButton triangle = new JButton("Triangle");
        triangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "triangle_1";
        		status.setText("Triangle selected, click on canves at where you want first point be.");
        	}
        });
        JButton rectangle = new JButton("Rectangle");
        rectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "rectangle_1";
        		status.setText("Rectangle selected, click on canves at where you want top left point be.");
        	}
        });
        JButton text = new JButton("Text");
        rectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "rectangle_1";
        		status.setText("Rectangle selected, click on canves at where you want top left point be.");
        	}
        });
        
        // Text button
        textInput = new JTextField();
        textInput.setText("Put text here");
        textInput.setColumns(100);
        text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "text_1";
        		status.setText("Text selected, put in text abrove and click on canves at where text to be.");
        	}
        });
        
        JButton cancel = new JButton("Cancel");
        cancel.setToolTipText("Abort action and reselect shape");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "null";
        		status.setText("Plase select the color above and shape on left");
        	}
        });
        
        JTextPane chatPane = new JTextPane();
        chatPane.setToolTipText("Chat Box");
        chatPane.setEditable(false);
        
        chatInput = new JTextField();
        chatInput.setToolTipText("Input chat here");
        chatInput.setColumns(20);
        
        JButton chatSendButton = new JButton("Send");
        
        JLabel chatLabel = new JLabel("Chat");
        
        
        

        
        
        
        
        
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(rectangle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(triangle, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        				.addComponent(circle, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        				.addComponent(line, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        				.addComponent(text, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        				.addComponent(cancel, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        				.addComponent(colorButton, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        				.addComponent(textInput, 0, 0, Short.MAX_VALUE))
        			.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
        			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(chatInput, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
        				.addComponent(chatSendButton)
        				.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
        				.addComponent(status, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(chatLabel)
        				.addComponent(chatPane, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE))
        			.addGap(14))
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGap(9)
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(groupLayout.createSequentialGroup()
        					.addComponent(status)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(colorButton)
        					.addGap(9)
        					.addComponent(line)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(circle)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(triangle)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(rectangle)
        					.addGap(18)
        					.addComponent(textInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addGap(15)
        					.addComponent(text)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(cancel))
        				.addGroup(groupLayout.createSequentialGroup()
        					.addComponent(chatLabel)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(chatPane)
        						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE))
        					.addGap(26)
        					.addComponent(chatInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addGap(27)
        					.addComponent(chatSendButton)))
        			.addContainerGap(20, Short.MAX_VALUE))
        );
        getContentPane().setLayout(groupLayout);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        
        JMenuItem NewMI = new JMenuItem(new AbstractAction("New") {
            public void actionPerformed(ActionEvent e) {
            	shapes = new ArrayList<Drawable>();	            	
            	repaint();
            }
        });
        fileMenu.add(NewMI);
        
        JMenuItem openMi = new JMenuItem(new AbstractAction("Open") {
            public void actionPerformed(ActionEvent e) {
            	open();
            	repaint();
            }
        });
        fileMenu.add(openMi);
        
        JMenuItem saveMI = new JMenuItem(new AbstractAction("Save") {
            public void actionPerformed(ActionEvent e) {
            	save();
            }
        });
        fileMenu.add(saveMI);
        
        JMenuItem saveAsMI = new JMenuItem(new AbstractAction("SaveAs") {
            public void actionPerformed(ActionEvent e) {
            	saveAs();
            }
        });
        fileMenu.add(saveAsMI);
        
        JMenuItem closeMI = new JMenuItem("Close");
        
        fileMenu.add(closeMI);
        
        
        JMenu kickUserMenu = new JMenu("Kick User");
        menuBar.add(kickUserMenu);
      
        
        this.setVisible(true);


    }

    
    public class GraphicsPanel extends JPanel {

        public GraphicsPanel(){
            this.addMouseListener(new MouseAdapter() {
            	@Override
            	public void mouseClicked(MouseEvent e) {
            		click(e.getX(),e.getY());
            		repaint();
            	}
            });
            this.setVisible(true); //probably not necessary

        }

        
        public void paint(Graphics g){
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for(Drawable shape: shapes) {
            	shape.draw(g2d);
            }
        }
    }
    
    
    private void click(int x, int y) {
    	System.out.println(x);
    	System.out.println(y);
    	System.out.println(state);
    	System.out.println(shapes);
    	switch (state) {
    	case "line_1":
    		this.preX=x;
    		this.preY=y;
    		state = "line_2";
    		status.setText("Now, click on canvas at where you want line end.");
    		break;
    	case "line_2":
    		shapes.add(new Line(preX, preY, x, y, currentColor));
    		state="null";
    		status.setText("Plase select the shape you want at left");
    		break;
    	case "circle_1":
    		this.preX=x;
    		this.preY=y;
    		state = "circle_2";
    		status.setText("Now, click on canvas at where you want circle go through.");
    		break;
    	case "circle_2":
    		shapes.add(new Circle(preX, preY, x, y, currentColor));
    		state="null";
    		status.setText("Plase select the shape you want at left");
    		break;
    	case "triangle_1":
    		System.out.println("ta1");
    		this.preX=x;
    		this.preY=y;
    		state = "triangle_2";
    		status.setText("Now, click on canvas at where you want second point be.");
    		break;
    	case "triangle_2":
    		this.preX1=x;
    		this.preY1=y;
    		state = "triangle_3";
    		status.setText("Now, click on canvas at where you want third point be.");
    		break;
    	case "triangle_3":
    		shapes.add(new Triangle(preX, preY, preX1, preY1, x, y, currentColor));
    		state="null";
    		status.setText("Plase select the shape you want at left");
    		break;
    	case "rectangle_1":
    		this.preX=x;
    		this.preY=y;
    		state = "rectangle_2";
    		status.setText("Now, click on canvas at where you want bottom right be.");
    		break;
    	case "rectangle_2":
    		shapes.add(new Rectangle(preX, preY, x, y, currentColor));
    		state="null";
    		status.setText("Plase select the shape you want at left");
    		break;
    	case "text_1":
    		shapes.add(new Text(x, y,textInput.getText() ,currentColor));
    		state="null";
    		status.setText("Plase select the shape you want at left");
    		break;
    	
    	}
    }
    
    private void save() {
    	if (filePath == null) {
    		saveAs();
    	}else {
    		FileManager.save(filePath, shapes);
    	}
    }
    
    private void saveAs() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");   
		int userSelection = fileChooser.showSaveDialog(this); 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = fileChooser.getSelectedFile();
		    FileManager.save(fileToSave.getAbsolutePath(), shapes);
		    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
		}
    }
    
    private void open() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to open");   
		int userSelection = fileChooser.showOpenDialog(this); 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToOpen = fileChooser.getSelectedFile();
		    this.shapes = FileManager.open(fileToOpen);
		    this.filePath = fileToOpen.getAbsolutePath();	
		    System.out.println("Open as file: " + fileToOpen.getAbsolutePath());
		}
    }
}
