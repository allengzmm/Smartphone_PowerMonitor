import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.text.DefaultCaret;

public class GUI extends JFrame implements ActionListener, Runnable {
	
	private static final long serialVersionUID = 1L;
	static boolean state[]={false,false,false};
	
	private static JButton init;
	private static JButton close;
	private static JButton start;
	private static JButton stop;
	private static JButton pause;
	private static JButton ref;
	private JButton resume;
	private JButton register;
	private static JButton tasks;
	private static JButton delete;
	private JPanel panel1;    
    public static JPanel panel2;
	public static JPanel panel3;
	private JPanel panel4;
	private JPanel panel5;
	private JPanel panel6;
	private Main main;
	static JTextField textZone1;
	static JTextField textZone2;
	static JTextField textZone3;
	static JTextField textZone4;
	static JTextField textZone5;
	static JTextArea textZone6;
	static JTextField textZone7;
	String monFichier = "monFichier.txt";
	private Thread reader;
	private Thread reader2;
	private boolean quit;
	private final PipedInputStream pin=new PipedInputStream();
	private final PipedInputStream pin2=new PipedInputStream();
	Thread errorThrower; // just for testing (Throws an Exception at this Console
	private JScrollPane scroll;
	static JComboBox<String> Box1;
	static JComboBox<String> Box2;
	static JComboBox<String> Box3;
	static JComboBox<String> Box4;
	static JComboBox [] Box5;
	static JComboBox [] Box6;
	static JComboBox [] Box7;
	static JComboBox<String> Box8;
	static DynamicDataDemo datachart;
	static JList<String> jltt;
	static DefaultListModel<String> modlist;
	public static OutputStream JTextAreaOutputStream;
	static JList List1;
	
	GUI(Main main){
		this.main = main;
		//setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		GridBagLayout g=new GridBagLayout();
		setLayout(g);
		GridBagConstraints c=new GridBagConstraints();
		c.fill=GridBagConstraints.BOTH;
		
		Timers.init_timers();
		//panel1
        c.gridx=0;
	    c.gridy=0;
		c.weightx=1;
	    c.weighty=1;
	    c.gridwidth=6;
	    c.gridheight=1;
	    panel1=new JPanel();
	    panel1.setPreferredSize( new java.awt.Dimension( 950 , 100 ) );
	    g.setConstraints(panel1,c);
	    panel1.setBorder(BorderFactory.createLineBorder(Color.black));
		getContentPane().add(panel1);
		
		panel1.setLayout(g);
		c.gridx=0;
		c.gridy=0;
		c.weightx=1;
		c.weighty=1;
		c.gridwidth=1;
		c.gridheight=1;
		JLabel label4 = new JLabel("Starting Time");
		g.setConstraints(label4,c);
		panel1.add(label4);
		
	  
		c.gridx=1;
		c.gridy=0;
		c.weightx=1;
		c.weighty=1;
		c.gridwidth=1;
		c.gridheight=1;
		Box5=new JComboBox[2];
		Box5[0]=new JComboBox<String>();
		g.setConstraints(Box5[0],c);
		panel1.add(Box5[0]);
	
        c.gridx=2;
		c.gridy=0;
		c.weightx=1;
		c.weighty=1;
		c.gridwidth=1;
		c.gridheight=1;
		Box6=new JComboBox[2];
		Box6[0]=new JComboBox<String>();
		g.setConstraints(Box6[0],c);
		panel1.add(Box6[0]);
		
		c.gridx=3;
        c.gridy=0;
        c.weightx=0;
        c.weighty=1;
        c.gridwidth=1;
        c.gridheight=1;
        //c.fill=GridBagConstraints.NONE;
        JLabel label6 = new JLabel("Time Duration");
        g.setConstraints(label6,c);
        panel1.add(label6);
		
        c.gridx=4;
	    c.gridy=0;
	    c.weightx=1;
	    c.weighty=1;
	    c.gridwidth=1;
	    c.gridheight=1;
	    Box5[1]=new JComboBox<String>();
	    g.setConstraints(Box5[1],c);
	    panel1.add(Box5[1]);
	    
	    c.gridx=5;
	    c.gridy=0;
	    c.weightx=1;
	    c.weighty=1;
	    c.gridwidth=1;
	    c.gridheight=1;
	    Box6[1]=new JComboBox<String>();
	    g.setConstraints(Box6[1],c);
	    panel1.add(Box6[1]);
	    
	    c.gridx=6;
		c.gridy=0;
		c.weightx=1;
		c.weighty=1;
		c.gridwidth=1;
		c.gridheight=1;
		Box7=new JComboBox[1];
		Box7[0]=new JComboBox<String>();
		g.setConstraints(Box7[0],c);
		panel1.add(Box7[0]);
	    
		int i,j;
		for(j=0;j<2;j++){
			for(i=0;i<24;i++){
				Box5[j].addItem(Integer.toString(i));
			}
		}
		
		for(j=0;j<2;j++){
			for(i=0;i<60;i++){
				Box6[j].addItem(Integer.toString(i));
			}
		}
		
		for(j=0;j<1;j++){
			for(i=0;i<60;i++){
				Box7[j].addItem(Integer.toString(i));
			}
		}
		
		c.gridx=0;
		c.gridy=1;
		c.weightx=1;
		c.weighty=1;
		c.gridwidth=1;
		c.gridheight=1;
		JLabel label5 = new JLabel("resistor value");
		g.setConstraints(label5,c);
		panel1.add(label5);
		
		c.gridx=1;
	    c.gridy=1;
	    c.weightx=1;
	    c.weighty=1;
	    c.gridwidth=1;
	    c.gridheight=1;
	    textZone7 = new JTextField("0.22000000");
	    g.setConstraints(textZone7,c);
	    panel1.add(textZone7);
	        
		c.gridx=2;
		c.gridy=1;
		c.weightx=1;
		c.weighty=1;
		c.gridwidth=1;
		c.gridheight=1;
		Box2=new JComboBox<String>();
		Box2.addItem("mohm");
		Box2.addItem("ohm");
		Box2.addItem("Kohm");
		Box2.addItem("Mohm");
		Box2.addItem("Gohm");
		g.setConstraints(Box2,c);
		panel1.add(Box2);
		
		c.gridx=3;
		c.gridy=1;
		c.weightx=1;
		c.weighty=1;
		c.gridwidth=1;
		c.gridheight=1;
		JLabel label7 = new JLabel("Actions Tasks");
		g.setConstraints(label7,c);
		panel1.add(label7);
		
		c.gridx=4;
		c.gridy=1;
		c.weightx=1;
		c.weighty=1;
		c.gridwidth=1;
		c.gridheight=1;
		Box4=new JComboBox<String>();
		Box4.addItem("start");
		Box4.addItem("Pause/Resume");
		Box4.addItem("Stop");
		g.setConstraints(Box4,c);
		panel1.add(Box4);
		
		c.gridx=5;
		c.gridy=1;
		c.weightx=1;
		c.weighty=1;
		c.gridwidth=1;
		c.gridheight=1;
		JLabel label8 = new JLabel("Axis Scale");
		g.setConstraints(label8,c);
		panel1.add(label8);
		
		c.gridx=6;
	    c.gridy=1;
	    c.weightx=1;
	    c.weighty=1;
	    c.gridwidth=1;
	    c.gridheight=1;
	    textZone5 = new JTextField("0.22000000");
	    g.setConstraints(textZone5,c);
	    panel1.add(textZone5);
	    
		c.gridx=7;
		c.gridy=1;
		c.weightx=1;
		c.weighty=1;
		c.gridwidth=1;
		c.gridheight=1;
		Box8=new JComboBox<String>();
		Box8.addItem("mohm");
		Box8.addItem("ohm");
		Box8.addItem("Kohm");
		Box8.addItem("Mohm");
		Box8.addItem("Gohm");
		g.setConstraints(Box8,c);
		panel1.add(Box8);
		
		//Panel2
		c.gridx=0;
	    c.gridy=1;
		c.weightx=0;
	    c.weighty=0;
	    c.gridwidth=7;
	    c.gridheight=1;
	    panel2=new JPanel();
	    panel2.setPreferredSize( new java.awt.Dimension( 950 , 25 ) );
	    g.setConstraints(panel2,c);
	    panel2.setBorder(BorderFactory.createLineBorder(Color.black));
		getContentPane().add(panel2);
		
		
		//Button
		panel2.setLayout(g);
		c.gridx=0;
	    c.gridy=0;
		c.weightx=1;
	    c.weighty=1;
	    c.gridwidth=1;
	    c.gridheight=1;
        init=new JButton("Init");
        init.addActionListener(this);
        g.setConstraints(init,c);
        panel2.add(init);
        
        c.gridx=1;
	    c.gridy=0;
		c.weightx=1;
	    c.weighty=1;
	    c.gridwidth=1;
	    c.gridheight=1;
        start = new JButton("Start");
        start.addActionListener(this);
        g.setConstraints(start,c);  
        panel2.add(start);
        
        c.gridx=2;
	    c.gridy=0;
		c.weightx=1;
	    c.weighty=1;
	    c.gridwidth=1;
	    c.gridheight=1;
        stop = new JButton("Stop");
        stop.addActionListener(this);
        g.setConstraints(stop,c);
        panel2.add(stop);
        
        c.gridx=3;
	    c.gridy=0;
		c.weightx=1;
	    c.weighty=1;
	    c.gridwidth=1;
	    c.gridheight=1;
        close = new JButton("Close");
        close.addActionListener(this);
        g.setConstraints(close,c);
        panel2.add(close);
        
        c.gridx=4;
	    c.gridy=0;
		c.weightx=1;
	    c.weighty=1;
	    c.gridwidth=1;
	    c.gridheight=1;
        tasks = new JButton("Create task");
        tasks.addActionListener(this);
        g.setConstraints(tasks,c);
        panel2.add(tasks);
        
        c.gridx=5;
	    c.gridy=0;
		c.weightx=1;
	    c.weighty=1;
	    c.gridwidth=1;
	    c.gridheight=1;
        ref = new JButton("Refresh");
        ref.addActionListener(this);
        g.setConstraints(ref,c);
        panel2.add(ref);
		
        c.gridx=6;
	    c.gridy=0;
		c.weightx=1;
	    c.weighty=1;
	    c.gridwidth=1;
	    c.gridheight=1;
        delete = new JButton("Delete");
        delete.addActionListener(this);
        g.setConstraints(delete,c);
        panel2.add(delete);
        
        c.gridx=7;
	    c.gridy=0;
		c.weightx=1;
	    c.weighty=1;
	    c.gridwidth=1;
	    c.gridheight=1;
        pause = new JButton("pause");
        pause.addActionListener(this);
        g.setConstraints(pause,c);
        panel2.add(pause);
        
        c.gridx=8;
	    c.gridy=0;
		c.weightx=1;
	    c.weighty=1;
	    c.gridwidth=1;
	    c.gridheight=1;
        resume = new JButton("resume");
        resume.addActionListener(this);
        g.setConstraints(resume,c);
        panel2.add(resume);
       
        
		
        //Panel3
        c.gridx=0;
        c.gridy=3;
        c.weightx=0;
        c.weighty=0;
        c.gridwidth=3;
        c.gridheight=3;
        panel3=new JPanel();
        panel3.setPreferredSize( new java.awt.Dimension( 475 , 250 ) ); 
        g.setConstraints(panel3,c);
        getContentPane().add(panel3);
        panel3.setBorder(BorderFactory.createLineBorder(Color.black));
        datachart = new DynamicDataDemo("Power-To-Time");
		
        
        
        //Panel4
        c.gridx=3;
        c.gridy=3;
        c.weightx=1;
        c.weighty=1;
        c.gridwidth=3;
        c.gridheight=3;
        panel4=new JPanel();
        panel3.setPreferredSize( new java.awt.Dimension( 475 , 250 ) );
        g.setConstraints(panel4,c);
        panel4.setBorder(BorderFactory.createLineBorder(Color.black));
        getContentPane().add(panel4);
       
        panel4.setLayout(g);
        c.gridx=0;
        c.gridy=0;
        c.weightx=0;
        c.weighty=1;
        c.gridwidth=1;
        c.gridheight=1;
        JLabel label1 = new JLabel("voltage(V)");
        g.setConstraints(label1,c);
        panel4.add(label1);
        
        c.gridx=0;
        c.gridy=1;
        c.weightx=0;
        c.weighty=1;
        c.gridwidth=1;
        c.gridheight=1;
        JLabel label2 = new JLabel("current(A)");
        g.setConstraints(label2,c);
        panel4.add(label2);
        
        c.gridx=0;
        c.gridy=2;
        c.weightx=1;
        c.weighty=1;
        c.gridwidth=1;
        c.gridheight=1;
        JLabel label3 = new JLabel("power(mW)");
        g.setConstraints(label3,c);
        panel4.add(label3);
        
        c.gridx=1;
        c.gridy=0;
        c.weightx=1;
        c.weighty=1;
        c.gridwidth=2;
        c.gridheight=1;
        textZone1 = new JTextField();
        g.setConstraints(textZone1,c);
        panel4.add(textZone1);
       
        
        c.gridx=1;
        c.gridy=1;
        c.weightx=1;
        c.weighty=1;
        c.gridwidth=2;
        c.gridheight=1;
        textZone2 = new JTextField();
        g.setConstraints(textZone2,c);
        panel4.add(textZone2);
        
        
        c.gridx=1;
        c.gridy=2;
        c.weightx=1;
        c.weighty=1;
        c.gridwidth=2;
        c.gridheight=1;
        textZone3 = new JTextField();
        g.setConstraints(textZone3,c);
        panel4.add(textZone3);
        
       
        //panel5
        c.gridx=0;
	    c.gridy=6;
		c.weightx=1;
	    c.weighty=1;
	    c.gridwidth=6;
	    c.gridheight=5;
	    panel5=new JPanel();
	    g.setConstraints(panel5,c);
	    panel5.setBorder(BorderFactory.createLineBorder(Color.black));
		getContentPane().add(panel5);
		
		panel5.setLayout(g);
		c.gridx=0;
	    c.gridy=0;
		c.weightx=1;
	    c.weighty=1;
	    c.gridwidth=6;
	    c.gridheight=5;
		List1=new JList<Object>();
		modlist=new DefaultListModel<String>();
		List1.setPreferredSize( new java.awt.Dimension( 950 ,150 ) );
		g.setConstraints(List1, c);
		panel5.add(List1);
		
		//panel6
		c.gridx=0;
	    c.gridy=11;
		c.weightx=1;
	    c.weighty=1;
	    c.gridwidth=6;
	    c.gridheight=5;
	    panel6=new JPanel();
	    g.setConstraints(panel6,c);
	    panel6.setBorder(BorderFactory.createLineBorder(Color.black));
		getContentPane().add(panel6);
		
		panel6.setLayout(g);
		c.gridx=0;
	    c.gridy=4;
	    c.weightx=0;
	    c.weighty=0;
	    c.gridwidth=1;
	    c.gridheight=1;
	    register = new JButton("register");
	    g.setConstraints(register,c);
	    register.addActionListener(this);
	    register.setPreferredSize( new java.awt.Dimension( 125, 25) );
	    panel6.add(register);
	    
        c.gridx=0;
        c.gridy=0;
        c.weightx=1;
        c.weighty=1;
        c.gridwidth=6;
        c.gridheight=4;
        textZone6 = new JTextArea();
        textZone6.setEditable(false);  
        scroll = new JScrollPane( textZone6);
        g.setConstraints(scroll,c);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        DefaultCaret caret = (DefaultCaret)textZone6.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        scroll.setPreferredSize( new java.awt.Dimension( 950 , 250 ) );
        panel6.add(scroll);
        
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		try
			{
				PipedOutputStream pout=new PipedOutputStream(this.pin);
				System.setOut(new PrintStream(pout,true));
				}
			catch (java.io.IOException io)
				{
				textZone6.append("Couldn't redirect STDOUT to this console\n"+io.getMessage());
				}
			catch (SecurityException se)
				{
				textZone6.append("Couldn't redirect STDOUT to this console\n"+se.getMessage());
				}
			try
				{
				PipedOutputStream pout2=new PipedOutputStream(this.pin2);
				System.setErr(new PrintStream(pout2,true));
				}
			catch (java.io.IOException io)
				{
				textZone6.append("Couldn't redirect STDERR to this console\n"+io.getMessage());
				}
			catch (SecurityException se)
				{
				textZone6.append("Couldn't redirect STDERR to this console\n"+se.getMessage());
				}
		
			quit=false; // signals the Threads that they should exit
		
			// Starting two separate threads to read from the PipedInputStreams
			//
			reader=new Thread(this);
			reader.setDaemon(true);
			reader.start();
			//
			reader2=new Thread(this);
			reader2.setDaemon(true);
			reader2.start();
			
			errorThrower=new Thread(this);
			errorThrower.setDaemon(true);
			errorThrower.start();        
	}
	public synchronized void windowClosed(WindowEvent evt)
		{
		quit=true;
		this.notifyAll(); // stop all threads
		try { reader.join(1000);pin.close();   } catch (Exception e){}
		try { reader2.join(1000);pin2.close(); } catch (Exception e){}
		System.exit(0);
		}
		public synchronized void windowClosing(WindowEvent evt)
		{
		this.setVisible(false); // default behaviour of JFrame
		this.dispose();
		}
	
		public synchronized void actionPerformed1(ActionEvent evt)
		{
		textZone6.setText("");
		}
	
		public synchronized void run()
		{
		try
		{
		while (Thread.currentThread()==reader)
		{
		try { this.wait(100);}catch(InterruptedException ie) {}
				if (pin.available()!=0)
				{
				String input=this.readLine(pin);
				textZone6.append(input);
				}
				if (quit) return;
				}
		while (Thread.currentThread()==reader2)
				{
		try 	{ 
				this.wait(100);}catch(InterruptedException ie) {}
				if (pin2.available()!=0)
				{
				String input=this.readLine(pin2);
				textZone6.append(input);
				}
				if (quit) return;
				}
				} catch (Exception e)
				{
				textZone6.append("\nConsole reports an Internal error.");
				textZone6.append("The error is: "+e);
				}
				if (Thread.currentThread()==errorThrower)
				{
				try { this.wait(1000); }catch(InterruptedException ie){}
				}
			}
			public synchronized String readLine(PipedInputStream in) throws IOException	{
				String input="";
				do
				{
				int available=in.available();
				if (available==0) break;
				byte b[]=new byte[available];
				in.read(b);
				input=input+new String(b,0,b.length);
			}while( !input.endsWith("\n") &&  !input.endsWith("\r\n") && !quit);
			return input;
		}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(start)) {
			main.start();
		}
		else if(e.getSource().equals(stop)) {
			main.stop();
		}
		else if(e.getSource().equals(init)) {
			main.init();
		}
		else if(e.getSource().equals(close)) {
			main.close();
		}
		else if(e.getSource().equals(pause)) {
			main.pause();
		}
		else if(e.getSource().equals(resume)) {
			main.resume();
		}
		else if(e.getSource().equals(register)){
			 JFileChooser filechoose = new JFileChooser();
		        filechoose.setCurrentDirectory(new
		        File("C:\\Users\\Louis\\Desktop\\Engineering project_Internship\\Internship")); 
		           String approve = new String("registrer");
		           int resultatEnregistrer = filechoose.showDialog(filechoose, approve); 
		          if (resultatEnregistrer == JFileChooser.APPROVE_OPTION) 
		           { String monFichier= new String(filechoose.getSelectedFile().toString());
		              if(monFichier.endsWith(".txt") || monFichier.endsWith(".TXT")) {;}
		              else if(monFichier == monFichier+ ".txt" );
		              { 
		            	  try
		            	  { FileWriter lu = new FileWriter(monFichier);
		            	     BufferedWriter out = new BufferedWriter(lu);
		            	     out.write(textZone6.getText()); 
		            	   out.close();
		              } catch (IOException er) {;} 
		          }
		     }
		}
		else if(e.getSource().equals(tasks)){
				switch(Box4.getSelectedIndex()){
					case 3: Timers ti=new Timers();
						Timers tim=new Timers();
						if(ti.getCountDown()>=-3600000L){
							Timers.add_timer(ti);
							Timers.add_timer(tim);
							ti.setTriggerAct(0);
							tim.setTriggerAct(2);
							tim.setPos(ti.getPos()+1);
							ti.setGroup(true);
							tim.setGroup(true);
							tim.setTrigTime();
							ti.start();
							tim.start();
						}
						else{System.out.println("Oups, Too late");}
						ti=null;
						tim=null;
						refresh_tasks();
						break;
					default: Timers t=new Timers();
						if(t.getCountDown()>=-3600000L){
							Timers.add_timer(t);
							t.setTriggerAct(Box4.getSelectedIndex());
							t.setGroup(false);
							t.start();
						}
						else{System.out.println("Oups, Too late");}
						t=null;
						refresh_tasks();
						break;
				}
		}
		else if(e.getSource().equals(ref)){
			Timers.refreshList();
			refresh_tasks();
		}
		else if(e.getSource().equals(delete)){
			if	(List1.getSelectedIndex()!=-1){
			int i,j=0;
			i=Timers.getTimer(List1.getSelectedIndex()).getGroupPos();
			Timers.remove_timer(List1.getSelectedIndex());
			if(i!=0){
				while(j<Timers.getNumberOfTimers()){
					if(i==Timers.getTimer(j).getGroupPos()){
						Timers.remove_timer(j);
					}
					j++;
				}
				for(j=0;j<Timers.getNumberOfTimers();j++){
					if(i<Timers.getTimer(j).getGroupPos()){Timers.getTimer(j).updateGroupPos();;}
				}
			}
			refresh_tasks();
		}
		}
	}
	static double get_resaxis(){
		double raxis=Double.parseDouble(textZone5.getText());
		switch(Box8.getSelectedIndex()){
		case 0:raxis=raxis/1000;
		case 1:break;
		case 2:raxis*= 1000; break;
		case 3:raxis*=1000*1000;break;
		case 4:raxis*=1000*1000*1000;break;
		}
	return raxis;	
	}
	static double get_res(){
		double r=Double.parseDouble(textZone7.getText());
		switch(Box2.getSelectedIndex()){
		case 0:r=r/1000;
		case 1:break;
		case 2:r*= 1000; break;
		case 3:r*=1000*1000;break;
		case 4:r*=1000*1000*1000;break;
		}
	return r;	
	}
	
	static void tStart(){
		init.doClick();
		start.doClick();
	}
		
	static void tStop(){
		stop.doClick();
		close.doClick();
	}
		
	static void tPauseResume(){
		pause.doClick();
	}
	static void tRefresh(){
		ref.doClick();
	}
	static void refresh_tasks(){
		int i;
		modlist.removeAllElements();
		List1.setModel(modlist);
		for(i=0;i<Timers.getNumberOfTimers();i++){
			modlist.addElement("Task ID : "+Timers.getTimer(i).getPos()+", Grouppos : "+Timers.getTimer(i).getGroupPos()+", Task nature : "+Timers.getTimer(i).getNature()+", Trigger enabled : "+Timers.getTimer(i).getStatus()+", Trigger time : "+Timers.getTimer(i).getTriggerTime()+"\n");
		}
		List1.setModel(modlist);
	}
	static int getTriggaction(){
		return Box4.getSelectedIndex();
	}
	public static void setoutput(double power) {
		textZone3.setText(Double.toString(power));
	}



	public static void setoutput1(double voltage) {
		textZone1.setText(Double.toString(voltage));
		
	}



	public static void setoutput2(double current) {
		textZone2.setText(Double.toString(current));
		
	}
	static String getTriggtime(boolean tf){
		String stt;
		if(tf){
			stt=Integer.toString(Box5[1].getSelectedIndex()+Box5[0].getSelectedIndex())+":"+Integer.toString(Box6[1].getSelectedIndex()+Box6[0].getSelectedIndex())+":"+Integer.toString(Box7[0].getSelectedIndex());
		}else{
			stt=Integer.toString(Box5[0].getSelectedIndex())+":"+Integer.toString(Box6[0].getSelectedIndex())+":"+Integer.toString(Box7[0].getSelectedIndex());
		}
		return stt;
	}
	
}
