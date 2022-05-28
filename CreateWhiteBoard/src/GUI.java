
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
import javax.swing.AbstractListModel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;

public class GUI extends JFrame{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        new GUI();

    }
    private String state;
    private int preX,preY,preX1,preY1;
    private ArrayList<Drawable> shapes = new ArrayList<Drawable>();
    private Color currentColor;
    
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
    	}
    	
    }
    
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
        
//        JComponent panel = new GraphicsPanel();
        JComponent panel = new JPanel();
        panel.setBackground(Color.WHITE);
        
        
        status.setText("Plase select the shape you want at left");
        
        line.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		state = "line_1";
        		status.setText("Line selected, click on canves at where you want line start from.");
        	}
        });
        circle.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		state = "circle_1";
        		status.setText("Circle selected, click on canves at where you want center of the circle be.");
        	}
        });
        
        triangle.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		state = "triangle_1";
        		status.setText("Triangle selected, click on canves at where you want first point be.");
        	}
        });
        
        rectangle.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		state = "rectangle_1";
        		status.setText("Rectangle selected, click on canves at where you want top left point be.");
        	}
        });
        
        rectangle.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		state = "rectangle_1";
        		status.setText("Rectangle selected, click on canves at where you want top left point be.");
        	}
        });
        
        textInput = new JTextField();
        textInput.setText("Put text here");
        textInput.setColumns(10);
        
        JTextPane chatPane = new JTextPane();
        
        chatInput = new JTextField();
        chatInput.setText("Input chat here");
        chatInput.setColumns(10);
        
        JButton chatSendButton = new JButton("Send");
        
        

        
        

        
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        					.addGroup(groupLayout.createSequentialGroup()
        						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        							.addComponent(rectangle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(triangle, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        							.addComponent(circle, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        							.addComponent(line, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
        						.addGap(53))
        					.addGroup(groupLayout.createSequentialGroup()
        						.addComponent(text, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        						.addPreferredGap(ComponentPlacement.RELATED)))
        				.addGroup(groupLayout.createSequentialGroup()
        					.addComponent(textInput, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)))
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
        				.addGroup(groupLayout.createSequentialGroup()
        					.addGap(18)
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        						.addComponent(chatPane, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
        						.addComponent(chatInput, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
        					.addGap(14))
        				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(chatSendButton)
        					.addGap(27))))
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(groupLayout.createSequentialGroup()
        					.addGap(17)
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
        					.addComponent(text))
        				.addGroup(groupLayout.createSequentialGroup()
        					.addGap(9)
        					.addComponent(status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
        						.addGroup(groupLayout.createSequentialGroup()
        							.addComponent(chatPane, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addComponent(chatInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(chatSendButton))
        						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 365, GroupLayout.PREFERRED_SIZE))))
        			.addContainerGap(148, Short.MAX_VALUE))
        );
        getContentPane().setLayout(groupLayout);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu FileMenu = new JMenu("File");
        menuBar.add(FileMenu);
        
        JMenuItem NewMI = new JMenuItem("New");
        FileMenu.add(NewMI);
        
        JMenuItem mntmNewMenuItem_1 = new JMenuItem("New menu item");
        FileMenu.add(mntmNewMenuItem_1);
        
        JMenuItem mntmNewMenuItem_2 = new JMenuItem("New menu item");
        FileMenu.add(mntmNewMenuItem_2);
        
        JMenuItem mntmNewMenuItem_3 = new JMenuItem("New menu item");
        FileMenu.add(mntmNewMenuItem_3);
        
        JMenu mnNewMenu = new JMenu("Color");
        menuBar.add(mnNewMenu);
        
        JMenu mnNewMenu_1 = new JMenu("Kick User");
        menuBar.add(mnNewMenu_1);
      
        
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
}
