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
import javax.swing.JButton;
import javax.swing.JColorChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
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
import javax.swing.JTextArea;
import javax.swing.DropMode;

public class GUI extends JFrame{
	private ArrayList<Drawable> shapes = new ArrayList<Drawable>();
	private String state = "null", filePath=null, tempText = null;
    private int preX,preY,preX1,preY1;
    private Color currentColor;
    private JLabel status; 
    private JTextArea chatArea;
    private JTextField chatInput;
    private JMenu kickUserMenu;
    private Logger logger;
    public GUI(){
    	this.logger = Server.getLogger();
        this.setSize(800,600);
        this.setPreferredSize(new Dimension(800,600));
        this.setTitle("Distributed Shared White Board Manager: "+Server.getUsername());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JComponent panel = new GraphicsPanel();
//        JComponent panel = new JPanel();
        panel.setBounds(132, 25, 466, 397);
        panel.setToolTipText("Draw area");
        panel.setBackground(Color.WHITE);
        status = new JLabel();
        status.setBounds(132, 9, 466, 16);
        status.setText("Plase select the color and shape on left");
        // Drawing tool box
        // Color
        JButton colorButton = new JButton("Color");
        colorButton.setBounds(5, 25, 105, 30);
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
        line.setBounds(5, 75, 105, 30);
        line.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "line_1";
        		status.setText("Line selected, click on canves at where you want line start from.");
        	}
        });
        // Circle
        JButton circle = new JButton("Circle");
        circle.setBounds(5, 125, 105, 30);
        circle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "circle_1";
        		status.setText("Circle selected, click on canves at where you want center of the circle be.");
        	}
        });
        // Triangle
        JButton triangle = new JButton("Triangle");
        triangle.setBounds(5, 175, 105, 30);
        triangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "triangle_1";
        		status.setText("Triangle selected, click on canves at where you want first point be.");
        	}
        });
        // Rectangle
        JButton rectangle = new JButton("Rectangle");
        rectangle.setBounds(5, 225, 105, 30);
        rectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		state = "rectangle_1";
        		status.setText("Rectangle selected, click on canves at where you want first corner be.");
        	}
        });
        // Text
        JButton text = new JButton("Text");
        text.setBounds(5, 275, 105, 30);
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
        cancel.setBounds(5, 375, 105, 30);
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
        chatLabel.setBounds(610, 9, 29, 16);
        chatInput = new JTextField();
        chatInput.setBounds(610, 440, 160, 26);
        chatInput.setToolTipText("Input chat here");
        chatInput.setColumns(20);
        chatArea = new JTextArea();
        chatArea.setDropMode(DropMode.INSERT);
        chatArea.setBounds(610, 25, 160, 400);
        chatArea.setLineWrap(true);
        chatArea.setToolTipText("Chat Box");
        chatArea.setWrapStyleWord(true);
        chatArea.setEditable(false);
        JButton chatSendButton = new JButton("Send");
        chatSendButton.setBounds(610, 485, 160, 30);
        chatSendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String chat = chatInput.getText();
            	logger.info("Said "+chat);
            	if(chat!= null && chat.length()>0) {
            		chat = Server.getUsername()+":"+chat;
            		int max = 19;
            		while (chat.length()>max) {
            			logger.info("Chat too long, making new line");
            			chat = chat.substring(0, max) + "\n" + chat.substring(max);
            			max+=19;
            		}
            		addChat(chat);
            		chatInput.setText("");
            		Server.broadcastChat(chat);
            	}
        	}
        });
        getContentPane().setLayout(null);
        getContentPane().add(circle);
        getContentPane().add(line);
        getContentPane().add(cancel);
        getContentPane().add(colorButton);
        getContentPane().add(text);
        getContentPane().add(rectangle);
        getContentPane().add(triangle);
        getContentPane().add(panel);
        getContentPane().add(status);
        getContentPane().add(chatInput);
        getContentPane().add(chatLabel);
        getContentPane().add(chatArea);
        getContentPane().add(chatSendButton);
        // Menu
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        // File Menu
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem NewMI = new JMenuItem(new AbstractAction("New") {
            public void actionPerformed(ActionEvent e) {
            	logger.info("New canvas");
            	shapes.clear();	            	
            	repaint();
            }
        });
        fileMenu.add(NewMI);
        
        JMenuItem openMi = new JMenuItem(new AbstractAction("Open") {
            public void actionPerformed(ActionEvent e) {
            	open();
            	
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
        
        JMenuItem closeMI = new JMenuItem(new AbstractAction("Close") {
            public void actionPerformed(ActionEvent e) {
            	close();
            }
        });
        fileMenu.add(closeMI);  
        // Kick User Menu
        kickUserMenu = new JMenu("Kick User");
        menuBar.add(kickUserMenu);
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
    		Server.broadcastDraw(l.toString());
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
    		Server.broadcastDraw(c.toString());
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
    		Server.broadcastDraw(t.toString());
    		state="null";
    		status.setText("Plase select the shape you want at left");
    		break;
    	case "rectangle_1":
    		this.preX=x;
    		this.preY=y;
    		state = "rectangle_2";
    		status.setText("Now, click on canvas at where you want oppsite corner be.");
    		break;
    	case "rectangle_2":
    		Drawable r = new Rectangle(preX, preY, x, y, currentColor);
    		shapes.add(r);
    		Server.broadcastDraw(r.toString());
    		state="null";
    		status.setText("Plase select the shape you want at left");
    		break;
    	case "text_1":
    		Drawable te = new Text(x, y,tempText,currentColor);
    		shapes.add(te);
    		Server.broadcastDraw(te.toString());
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
		    this.filePath = fileToSave.getAbsolutePath();
		    FileManager.save(fileToSave.getAbsolutePath(), shapes);
		}
    }
    
    // Load canvas from file
    private void open() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to open");   
		int userSelection = fileChooser.showOpenDialog(this); 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToOpen = fileChooser.getSelectedFile();
		    logger.info("Open file: " + fileToOpen.getAbsolutePath());
		    this.filePath = fileToOpen.getAbsolutePath();
		    this.shapes = FileManager.open(fileToOpen);
		    repaint();
		    Server.broadcastNewFile();
		}
    }
    
    public void updateKickList(ArrayList<User> users) {
    	kickUserMenu.removeAll();
    	for(User user: users) {
    		JMenuItem kickUserMI = new JMenuItem(new AbstractAction(user.username) {
                public void actionPerformed(ActionEvent e) {
                	Server.kickUser(user);
                }
            });
    		kickUserMenu.add(kickUserMI);
    	}
    }
    
    private void close() {
    	logger.info("Closing");
    	if (shapes.size()>0) {
    		int answer = JOptionPane.showConfirmDialog(null, "Do you want to save before close?");
        	if (answer == 2) {
        		return;
        	} 
        	else if(answer == 0) {
        		save();
        	}
    	}
    	Server.exit();
    }
    
    public void addChat(String chat) {
    	String newChat = chatArea.getText()+chat+"\n";
    	while((newChat.split("\n")).length > 25) {
    		newChat = newChat.substring(newChat.indexOf('\n')+1);
    		logger.info("Chat too tall, remove first line");
    	}
    	chatArea.setText(newChat);
    }
    
    public ArrayList<Drawable> getShapes() {
		return shapes;
	}

	public JMenu getKickUserMenu() {
		return kickUserMenu;
	}
}
