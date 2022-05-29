// written by Jiachen Li, 1068299

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.Font;

public class GUI extends JFrame{
	private int preX,preY,preX1,preY1;
    private String state = null, filePath=null, tempText = null;
    private ArrayList<Drawable> shapes = new ArrayList<Drawable>();
    private Color currentColor;
    private JLabel status;
    private JTextField chatInput;
    private JMenu userListMenu;
    private JTextArea chatArea;
    private Logger logger;
    public GUI(){
    	this.logger = Client.getLogger();
    	this.setSize(800,600);
        this.setPreferredSize(new Dimension(800,600));
        this.setTitle("Distributed Shared White Board Client: "+Client.username);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JComponent panel = new GraphicsPanel();
//        JComponent panel = new JPanel();
        panel.setToolTipText("Draw area");
        panel.setBackground(Color.WHITE);       
        status = new JLabel();
        status.setText("Plase select the color and shape on left");
        // Drawing tool box
        // Color
        JButton colorButton = new JButton("Color");
        this.currentColor = Color.black;
        colorButton.setToolTipText("Click to select color, displying selected color");
        colorButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	currentColor = JColorChooser.showDialog(null, "Select color", currentColor);
            	colorButton.setForeground(currentColor);
        	}
        });
        // Line
        JButton line = new JButton("Line");
        line.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "line_1";
        		status.setText("Line selected, click on canves at where you want line start from.");
        	}
        });
        // Circle
        JButton circle = new JButton("Circle");
        circle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "circle_1";
        		status.setText("Circle selected, click on canves at where you want center of the circle be.");
        	}
        });
        // Triangle
        JButton triangle = new JButton("Triangle");
        triangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "triangle_1";
        		status.setText("Triangle selected, click on canves at where you want first point be.");
        	}
        });
        // Rectangle
        JButton rectangle = new JButton("Rectangle");
        rectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "rectangle_1";
        		status.setText("Rectangle selected, click on canves at where you want top left point be.");
        	}
        });
        // Text
        JButton text = new JButton("Text");
        text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		tempText = JOptionPane.showInputDialog("Put text you want below");
        		if (tempText != null) {
        			state = "text_1";
            		status.setText("Text selected, click on canves at where text to be.");
        		}
        	}
        });
        // Cancel
        JButton cancel = new JButton("Cancel");
        cancel.setToolTipText("Abort action and reselect shape");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "null";
        		status.setText("Plase select the color above and shape on left");
        	}
        });
        // Chat
        JLabel chatLabel = new JLabel("Chat");
        chatInput = new JTextField();
        chatInput.setToolTipText("Input chat here");
        chatInput.setColumns(20);
        chatArea = new JTextArea();
        chatArea.setToolTipText("Chat Box");
        chatArea.setWrapStyleWord(true);
        chatArea.setEditable(false);
        JButton chatSendButton = new JButton("Send");
        chatSendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String chat = Client.username+":"+chatInput.getText();
            	System.out.println(chat);
            	if(chat!= null && chat.length()>0) {
            		int max = 19;
            		while (chat.length()>max) {
            			chat = chat.substring(0, max) + "\n" + chat.substring(max);
            			max+=19;
            		}
            		chatInput.setText("");
            		Client.sentChat(chat);
            	}
        	}
        });
        //Layout
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(groupLayout.createSequentialGroup()
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
        							.addComponent(circle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(line, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(cancel, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        							.addComponent(colorButton, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
        						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
        							.addComponent(text, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(rectangle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(triangle, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)))
        					.addGap(21)
        					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
        						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
        						.addComponent(status, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE))
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
        						.addGroup(groupLayout.createSequentialGroup()
        							.addGap(6)
        							.addComponent(chatInput, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
        						.addGroup(groupLayout.createSequentialGroup()
        							.addGap(12)
        							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        								.addComponent(chatLabel)
        								.addComponent(chatArea))))
        					.addGap(15))
        				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
        					.addComponent(chatSendButton)
        					.addGap(23))))
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGap(9)
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(groupLayout.createSequentialGroup()
        					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(status)
        						.addComponent(chatLabel))
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(colorButton)
        					.addGap(3)
        					.addComponent(line)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(circle)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(triangle)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(rectangle)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(text)
        					.addGap(83)
        					.addComponent(cancel))
        				.addGroup(groupLayout.createSequentialGroup()
        					.addGap(28)
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        						.addComponent(chatArea)
        						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE))))
        			.addGap(18)
        			.addComponent(chatInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(chatSendButton)
        			.addGap(37))
        );
        getContentPane().setLayout(groupLayout);
        // Menu
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
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
        JMenuItem closeMI = new JMenuItem(new AbstractAction("Close") {
            public void actionPerformed(ActionEvent e) {
            	close();
            }
        });
        fileMenu.add(closeMI);
        // User List
        userListMenu = new JMenu("User List");
        menuBar.add(userListMenu);
        //initializing complete
        this.setVisible(true);
    }

    // Panel for drawing
    public class GraphicsPanel extends JPanel {
        public GraphicsPanel(){
            this.addMouseListener(new MouseAdapter() {
            	@Override
            	public void mouseClicked(MouseEvent e) {
            		click(e.getX(),e.getY());
            		repaint();
            	}
            });
        }
        // Draw shapes
        public void paint(Graphics g){
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for(Drawable shape: shapes) {
            	shape.draw(g2d);
            }
        }
    }  
    // Action on click based on state machine
    private void click(int x, int y) {
    	switch (state) {
    	case "line_1":
    		this.preX=x;
    		this.preY=y;
    		state = "line_2";
    		status.setText("Now, click on canvas at where you want line end.");
    		break;
    	case "line_2":
    		Drawable l = new Line(preX, preY, x, y, currentColor);
    		shapes.add(l);
    		Client.postDraw(l.toString());
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
    		Drawable c = new Circle(preX, preY, x, y, currentColor);
    		shapes.add(c);
    		Client.postDraw(c.toString());
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
    		Drawable t = new Triangle(preX, preY, preX1, preY1, x, y, currentColor);
    		shapes.add(t);
    		Client.postDraw(t.toString());
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
    		Drawable r = new Rectangle(preX, preY, x, y, currentColor);
    		shapes.add(r);
    		Client.postDraw(r.toString());
    		state="null";
    		status.setText("Plase select the shape you want at left");
    		break;
    	case "text_1":
    		Drawable te = new Text(x, y,tempText,currentColor);
    		shapes.add(te);
    		Client.postDraw(te.toString());
    		state="null";
    		status.setText("Plase select the shape you want at left");
    		break;
    	}
    }
    
    // Save canvas current file
    private void save() {
    	logger.info("Save to current file");
    	if (filePath == null) {
    		saveAs();
    	}else {
    		FileManager.save(filePath, shapes);
    	}
    }
    
    // Save canvas to new file
    private void saveAs() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");   
		int userSelection = fileChooser.showSaveDialog(this); 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = fileChooser.getSelectedFile();
		    logger.info("Save as file: " + fileToSave.getAbsolutePath());
		    FileManager.save(fileToSave.getAbsolutePath(), shapes);
		}
    }
    
    private void close() {
    	logger.info("Closing");
    	int answer = JOptionPane.showConfirmDialog(null, "Do you want to save before close?");
    	if (answer == 2) {
    		return;
    	} 
    	else if(answer == 0) {
    		save();
    	}
    	Client.exit();
    }

    public void updateUserList(String userList) {
    	userListMenu.removeAll();
    	String[] users = userList.split("<usrbr>");
    	for(String user: users) {
    		JMenuItem userMI = new JMenuItem(user);
    		userListMenu.add(userMI);
    	}
    }
    
    public void updateChat(String chat) {
    	chatArea.setText(chatArea.getText()+chat+"\n");
    }

	public ArrayList<Drawable> getShapes() {
		return shapes;
	}

}
