
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

public class GUI extends JFrame{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        new GUI();

    }
    private int state;
    private int tempx,tempy;
    private ArrayList<Shape> shapes = new ArrayList<Shape>();
    
    private void click(int x, int y) {
    	System.out.println(x);
    	System.out.println(y);
    	if(state==0) {
    		this.tempx=x;
    		this.tempy=y;
    		state = 1;
    	}else if(state ==1) {
    		shapes.add(new Line2D.Float(tempx, tempy, x, y));
    		state=0;
    	}

    }
    
    public GUI(){
    	this.state=0;
        this.setSize(800,600);
        this.setPreferredSize(new Dimension(800,600));
        this.setTitle("Drawing tings");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JButton btnNewButton = new JButton("New button");
        
        JButton btnNewButton_1 = new JButton("New button");
        
        JButton btnNewButton_3 = new JButton("New button");
        
        JComponent panel = new GraphicsPanel();
        panel.setBackground(Color.WHITE);
        int x = 1;
        panel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		click(e.getX(),e.getY());
        	}
        });
        
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(btnNewButton_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
        				.addComponent(btnNewButton_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
        				.addComponent(btnNewButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        			.addGap(53)
        			.addComponent(panel, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
        			.addGap(124))
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(groupLayout.createSequentialGroup()
        					.addGap(17)
        					.addComponent(btnNewButton)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(btnNewButton_3)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(btnNewButton_1))
        				.addGroup(groupLayout.createSequentialGroup()
        					.addGap(37)
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

    }

    public class GraphicsPanel extends JPanel {

        public GraphicsPanel(){
//            setLayout(new BorderLayout());
//            this.setPreferredSize(new Dimension(1000,1000));
//            this.add(new DrawStuff(), BorderLayout.CENTER);
//            revalidate();
//            repaint();
            this.setVisible(true); //probably not necessary

        }
        
        protected void paintComponent(Graphics g){
            super.paintComponent(g);

            Graphics2D graph2 = (Graphics2D) g;

            graph2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//            for(Shape shape: shapes) {
//            	
//            }
            
            Shape rootRect = new Rectangle2D.Float(10, 10, 500, 400);
            Shape line = new Line2D.Float(10, 10, 500, 400);
            graph2.setColor(Color.BLACK);
            graph2.draw(rootRect);
            graph2.setColor(Color.GREEN);
            graph2.draw(line);
            
        }

//        private class DrawStuff extends JComponent{
//
//            @Override
//            protected void paintComponent(Graphics g){
//                super.paintComponent(g);
//
//                Graphics2D graph2 = (Graphics2D) g;
//
//                graph2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
////                for(Shape shape: shapes) {
////                	
////                }
//                
////                Shape rootRect = new Rectangle2D.Float(10, 10, 500, 400);
////                Shape line = new Line2D.Float(10, 10, 500, 400);
////                graph2.setColor(Color.BLACK);
////                graph2.draw(rootRect);
////                graph2.setColor(Color.GREEN);
////                graph2.draw(line);
//                
//            }
//        }
    }
}
