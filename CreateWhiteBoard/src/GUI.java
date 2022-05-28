
import java.awt.BorderLayout;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuKeyEvent;

public class GUI extends JFrame{

    public static void main(String[] args) {
        new GUI();

    }
    
    private String state;
    private int preX,preY,preX1,preY1;
    private ArrayList<Drawable> shapes = new ArrayList<Drawable>();
    private Color currentColor;
    

    
    JTextArea status = new JTextArea();
    private JTextField textInput;
    private JTextField chatInput;
    
    public GUI(){
    	this.state="null";
        this.setSize(800,600);
        this.setPreferredSize(new Dimension(800,600));
        this.setTitle("Distributed Shared White Board Manager");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.currentColor = Color.black;
        
        JButton line = new JButton("Line");
        JButton circle = new JButton("Circle");
        JButton triangle = new JButton("Triangle");
        JButton rectangle = new JButton("Rectangle");
        JButton text = new JButton("Text");
        JButton cancel = new JButton("Cancel");
        
        JComponent panel = new GraphicsPanel();
//        JComponent panel = new JPanel();
        panel.setBackground(Color.WHITE);
        
        
        status.setText("Plase select the color above and shape on left");
                
        line.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "line_1";
        		status.setText("Line selected, click on canves at where you want line start from.");
        	}
        });
        circle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "circle_1";
        		status.setText("Circle selected, click on canves at where you want center of the circle be.");
        	}
        });
        
        triangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "triangle_1";
        		status.setText("Triangle selected, click on canves at where you want first point be.");
        	}
        });
        
        rectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "rectangle_1";
        		status.setText("Rectangle selected, click on canves at where you want top left point be.");
        	}
        });
        
        rectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "rectangle_1";
        		status.setText("Rectangle selected, click on canves at where you want top left point be.");
        	}
        });
        
        textInput = new JTextField();
        textInput.setText("Put text here");
        textInput.setColumns(10);
        
        text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "text_1";
        		status.setText("Text selected, put in text abrove and click on canves at where text to be.");
        	}
        });
        
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "null";
        		status.setText("Plase select the color above and shape on left");
        	}
        });
        
        JTextPane chatPane = new JTextPane();
        
        chatInput = new JTextField();
        chatInput.setText("Input chat here");
        chatInput.setColumns(10);
        
        JButton chatSendButton = new JButton("Send");
        JLabel currentColorLabel = new JLabel("CurrentColor: Black");
        
        

        
        
        
        
        
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
        					.addGap(1)
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(rectangle)
        						.addComponent(triangle, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        						.addComponent(circle, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        						.addComponent(line, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        						.addComponent(text, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        						.addComponent(textInput, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cancel, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)))
        				.addComponent(currentColorLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(status, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
        			.addGap(18)
        			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
        				.addGroup(groupLayout.createSequentialGroup()
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        						.addComponent(chatPane, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
        						.addComponent(chatInput, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
        					.addGap(14))
        				.addGroup(groupLayout.createSequentialGroup()
        					.addComponent(chatSendButton)
        					.addGap(27))))
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGap(9)
        			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(currentColorLabel))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(groupLayout.createSequentialGroup()
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
        				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        					.addGroup(groupLayout.createSequentialGroup()
        						.addComponent(chatPane, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE)
        						.addPreferredGap(ComponentPlacement.UNRELATED)
        						.addComponent(chatInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addPreferredGap(ComponentPlacement.RELATED)
        						.addComponent(chatSendButton)
        						.addGap(0, 0, Short.MAX_VALUE))
        					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)))
        			.addGap(148))
        );
        getContentPane().setLayout(groupLayout);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        
        JMenuItem NewMI = new JMenuItem("New");
        fileMenu.add(NewMI);
        
        JMenuItem mntmNewMenuItem_1 = new JMenuItem("New menu item");
        fileMenu.add(mntmNewMenuItem_1);
        
        JMenuItem mntmNewMenuItem_2 = new JMenuItem("New menu item");
        fileMenu.add(mntmNewMenuItem_2);
        
        JMenuItem mntmNewMenuItem_3 = new JMenuItem("New menu item");
        fileMenu.add(mntmNewMenuItem_3);
        
        
        

        
        colorMenu(menuBar, currentColorLabel);
        
        
        JMenu kickUserMenu = new JMenu("Kick User");
        menuBar.add(kickUserMenu);
      
        
        this.setVisible(true);


    }

    
    private void colorMenu(JMenuBar menuBar, JLabel currentColorLabel) {
    	// Color menu
    	
        JMenu colorMenu = new JMenu("Color");
        menuBar.add(colorMenu);
        // Black
        JMenuItem blackColorMI = new JMenuItem(new AbstractAction("Black") {
            public void actionPerformed(ActionEvent e) {
            	currentColor = Color.black;
        		currentColorLabel.setText("CurrentColor: Black");
            }
        });
        // Blue
        colorMenu.add(blackColorMI);        
        JMenuItem blueColorMI = new JMenuItem(new AbstractAction("Blue") {
            public void actionPerformed(ActionEvent e) {
            	currentColor = Color.blue;
        		currentColorLabel.setText("CurrentColor: Blue");
            }
        });
        colorMenu.add(blueColorMI);
        // Cyan
        JMenuItem cyanColorMI = new JMenuItem(new AbstractAction("Cyan") {
            public void actionPerformed(ActionEvent e) {
            	currentColor = Color.cyan;
        		currentColorLabel.setText("CurrentColor: Cyan");
            }
        });
        colorMenu.add(cyanColorMI);
        // Dark Gray
        JMenuItem darkGrayColorMI = new JMenuItem(new AbstractAction("Dark Gray") {
            public void actionPerformed(ActionEvent e) {
            	currentColor = Color.darkGray;
        		currentColorLabel.setText("CurrentColor: Dark Gray");
            }
        });
        colorMenu.add(darkGrayColorMI);
        // Gray
        JMenuItem grayColorMI = new JMenuItem(new AbstractAction("Gray") {
            public void actionPerformed(ActionEvent e) {
            	currentColor = Color.gray;
        		currentColorLabel.setText("CurrentColor: Gray");
            }
        });
        colorMenu.add(grayColorMI);
        // Green
        JMenuItem greenColorMI = new JMenuItem(new AbstractAction("Green") {
            public void actionPerformed(ActionEvent e) {
            	currentColor = Color.green;
        		currentColorLabel.setText("CurrentColor: Green");
            }
        });
        colorMenu.add(greenColorMI);
        // Indigo
        JMenuItem indigoColorMI = new JMenuItem(new AbstractAction("Indigo") {
            public void actionPerformed(ActionEvent e) {
            	currentColor = new Color(75,0,130);
        		currentColorLabel.setText("CurrentColor: Indigo");
            }
        });
        colorMenu.add(indigoColorMI);
        // Light Gray
        JMenuItem lightGrayColorMI = new JMenuItem(new AbstractAction("Light Gray") {
            public void actionPerformed(ActionEvent e) {
            	currentColor = Color.lightGray;
        		currentColorLabel.setText("CurrentColor: Light Gray");
            }
        });
        colorMenu.add(lightGrayColorMI);
        // Magenta
        JMenuItem magentaColorMI = new JMenuItem(new AbstractAction("Magenta") {
            public void actionPerformed(ActionEvent e) {
            	currentColor = Color.magenta;
        		currentColorLabel.setText("CurrentColor: Magenta");
            }
        });
        colorMenu.add(magentaColorMI);
        // Navy
        JMenuItem navyColorMI = new JMenuItem(new AbstractAction("Navy") {
            public void actionPerformed(ActionEvent e) {
            	currentColor = new Color(0,0,128);
        		currentColorLabel.setText("CurrentColor: Navy");
            }
        });
        colorMenu.add(navyColorMI);
        // Orange
        JMenuItem orangeColorMI = new JMenuItem(new AbstractAction("Orange") {
            public void actionPerformed(ActionEvent e) {
            	currentColor = Color.orange;
        		currentColorLabel.setText("CurrentColor: Orange");
            }
        });
        colorMenu.add(orangeColorMI);
        // Pink
        JMenuItem pinkColorMI = new JMenuItem(new AbstractAction("Pink") {
            public void actionPerformed(ActionEvent e) {
            	currentColor = Color.pink;
        		currentColorLabel.setText("CurrentColor: Pink");
            }
        });
        colorMenu.add(pinkColorMI);
        // Red
        JMenuItem redColorMI = new JMenuItem(new AbstractAction("Red") {
            public void actionPerformed(ActionEvent e) {
            	currentColor = Color.red;
        		currentColorLabel.setText("CurrentColor: Red");
            }
        });
        colorMenu.add(redColorMI);
        // Teal
        JMenuItem tealColorMI = new JMenuItem(new AbstractAction("Teal") {
            public void actionPerformed(ActionEvent e) {
            	currentColor = new Color(0,128,128);
        		currentColorLabel.setText("CurrentColor: Teal");
            }
        });
        colorMenu.add(tealColorMI);
        // White
        JMenuItem whiteColorMI = new JMenuItem(new AbstractAction("White") {
            public void actionPerformed(ActionEvent e) {
            	currentColor = Color.white;
        		currentColorLabel.setText("CurrentColor: White");
            }
        });
        colorMenu.add(whiteColorMI);
        // Yellow
        JMenuItem yellowColorMI = new JMenuItem(new AbstractAction("Yellow") {
            public void actionPerformed(ActionEvent e) {
            	currentColor = Color.yellow;
        		currentColorLabel.setText("CurrentColor: Yellow");
            }
        });
        colorMenu.add(yellowColorMI);
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
}
