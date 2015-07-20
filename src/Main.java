import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class Main implements WindowListener {
	private SerialTest serial;
	private GUI gui;
	private DataProcessor processor;
	private Thread dataThread;
	
	public Main() {
		serial = new SerialTest();
		gui = new GUI(this);
		gui.addWindowListener(this);
		processor = new DataProcessor(serial);
		serial.initialize();
		dataThread = new Thread(processor);
		
	}
	
	public void start() {
		processor = new DataProcessor(serial);
		if(dataThread.isAlive()) {
			processor.stop();
			try {
				dataThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		dataThread = new Thread(processor);
		dataThread.start();
		serial.start();
	}
	
	public void stop() {
		serial.stop();
		processor.stop();
	}
	public void pause() {
		serial.pause();
	}
	public void init() {
		serial.initialize();
	}
	
	public void close() {
		serial.close();
		processor.stop();
	}
	public void resume() {
		serial.resume();
		
	}
	public static void main(String[] args) {
		new Main();
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		System.out.println("shutting down");
		serial.close();
		processor.stop();
		try {
			dataThread.join(); 
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}	
		System.out.println("EXIT");
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}

	public void setVisible(boolean b) {
	}

	

}
