
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
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

public class GUI extends JFrame{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        new GUI();

    }
    private String state;
    private int tempx,tempy;
    private ArrayList<Shape> shapes = new ArrayList<Shape>();
    
    private void click(int x, int y) {
    	System.out.println(x);
    	System.out.println(y);
    	System.out.println(state);
    	System.out.println(shapes);
    	if(state=="line_start") {
    		this.tempx=x;
    		this.tempy=y;
    		state = "line_end";
    		status.setText("Now, click on canvas at where you want line end.");
    	}else if(state =="line_end") {
    		shapes.add(new Line2D.Double(tempx, tempy, x, y));
    		state="null";
    		status.setText("Plase select the shape you want at left");
    	}
    	else if(state=="rec_start") {
    		this.tempx=x;
    		this.tempy=y;
    		state = "rec_end";
    		status.setText("Now, click on canvas at where you want bottom right be.");

    	}else if(state =="rec_end") {
    		shapes.add(new Rectangle(tempx, tempy, x-tempx, y-tempy));
    		state="null";
    		status.setText("Plase select the shape you want at left");
    	}
    	else if(state=="circle_start") {
    		this.tempx=x;
    		this.tempy=y;
    		state = "rec_end";
    		status.setText("Now, click on canvas at where you want bottom right be.");

    	}else if(state =="rec_end") {
    		shapes.add(new Rectangle(tempx, tempy, x-tempx, y-tempy));
    		state="null";
    		status.setText("Plase select the shape you want at left");
    	}

    }
    
    JTextArea status = new JTextArea();
    
    public GUI(){
    	this.state="null";
        this.setSize(800,600);
        this.setPreferredSize(new Dimension(800,600));
        this.setTitle("Drawing tings");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JButton line = new JButton("Line");
        JButton triangle = new JButton("Triangle");
        JButton circle = new JButton("Circle");
        JButton rectangle = new JButton("Rectangle");

        
        JComponent panel = new GraphicsPanel();
//        JComponent panel = new JPanel();
        panel.setBackground(Color.WHITE);
        
        
        status.setText("Plase select the shape you want at left");
        
        line.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		state = "line_start";
        		status.setText("Line selected, click on canves at where you want line start from.");
        	}
        });
        
        rectangle.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		state = "rec_start";
        		status.setText("Rectangle selected, click on canves at where you want top left point be.");
        	}
        });
        


        
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(rectangle, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
        				.addComponent(triangle, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
        				.addComponent(circle, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
        				.addComponent(line, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
        			.addGap(53)
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(status)
        				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
        			.addGap(136))
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
        					.addComponent(rectangle))
        				.addGroup(groupLayout.createSequentialGroup()
        					.addGap(9)
        					.addComponent(status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 365, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap(148, Short.MAX_VALUE))
        );
        getContentPane().setLayout(groupLayout);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu mnNewMenu = new JMenu("New menu");
        menuBar.add(mnNewMenu);
        
        JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
        mnNewMenu.add(mntmNewMenuItem);
        
        JMenuItem mntmNewMenuItem_1 = new JMenuItem("New menu item");
        mnNewMenu.add(mntmNewMenuItem_1);
        
        JMenuItem mntmNewMenuItem_2 = new JMenuItem("New menu item");
        mnNewMenu.add(mntmNewMenuItem_2);
        
        JMenuItem mntmNewMenuItem_3 = new JMenuItem("New menu item");
        mnNewMenu.add(mntmNewMenuItem_3);
      
        
        this.setVisible(true);
        
        Shape rootRect = new Rectangle(30, 50, 420, 120);
        Shape rootline = new Line2D.Float(10, 10, 500, 400);
        shapes.add(rootRect);
        shapes.add(rootline);

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

            Graphics2D graph2 = (Graphics2D) g;

            graph2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            for(Shape shape: shapes) {
            	graph2.setColor(Color.BLACK);
            	graph2.draw(shape);
            }
            

    }
}
}
