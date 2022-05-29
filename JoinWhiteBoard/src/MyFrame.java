import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.Canvas;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.DropMode;
import javax.swing.JTextArea;


public class MyFrame extends JFrame{
    public static void main(String[] args) {
        new MyFrame();

    }
	
	
	protected String state;
	MyFrame() {
		setAlwaysOnTop(true);
		this.state = "line";
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,600);
		this.setTitle("Demo");
//		this.setLocationRelativeTo(null);
//		JFileChooser fileChooser = new JFileChooser();
//		fileChooser.setDialogTitle("Specify a file to save");   
//		int userSelection = fileChooser.showSaveDialog(this);	 
//		if (userSelection == JFileChooser.APPROVE_OPTION) {
//		    File fileToSave = fileChooser.getSelectedFile();
//		    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
//		}
		
		final JButton button = new JButton("Pick to Change Background");

	
			
		JMenuBar menuBar = new JMenuBar();
		menuBar.setToolTipText("tip");

		
		JMenu mnNewMenu = new JMenu("New menu");

		
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
		mntmNewMenuItem.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
			        Color initialBackground = button.getBackground();
			        Color background = JColorChooser.showDialog(null, "Change Button Background",
			            initialBackground);
			        if (background != null) {
			          button.setBackground(background);
			        }
			      }
			    });
		mnNewMenu.add(mntmNewMenuItem);
		
		JTextPane a1 = new JTextPane();
		a1.setText("saljksjfalksdfjalksdfja\n;skdlfjas;kldfjas;kldfjas;dklfjaslk;dfja;sdlkfjaslk;dfjasd;lkfjasdfjaklsdfj");
		a1.setEditable(false);
		
		JTextArea b1 = new JTextArea();
		b1.setColumns(10);
		b1.setText("123\n123");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(menuBar, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(173)
					.addComponent(a1, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(155)
					.addComponent(b1, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(menuBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(126)
					.addComponent(a1, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
					.addGap(56)
					.addComponent(b1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addGap(167))
		);
		getContentPane().setLayout(groupLayout);
		
		this.setVisible(true);
		// TODO Auto-generated constructor stub
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawLine(0, 0, 500, 500);
		if(this.state == "line1") {
			g2d.drawString("123144", 150,150);
		}
	}
	private int xStart ,yStart;
	public void click(int x, int y) {
		if(this.state == "line") {
			this.xStart = x;
			this.yStart = y;
			this.state = "line1";
		}else if(this.state == "line1");
	}
}
