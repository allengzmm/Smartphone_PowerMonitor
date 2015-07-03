import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 

import java.util.Enumeration;

public class SerialTest implements SerialPortEventListener {
	SerialPort serialPort;
        /** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { 
			"/dev/tty.usbserial-A9007UX1", // Mac OS X
			"/dev/ttyUSB0", // Linux
			"COM3", // Windows
			};
	/** Buffered input stream from the port */
	private InputStream input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;
	
	public CircularObjectBuffer<Byte> buffer;
	
	public SerialTest() {
		buffer = new CircularObjectBuffer<Byte>(10000);
	}

	public void initialize() {
		System.out.println("initializing serial port");
		buffer.clear();
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// iterate through, looking for the port
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}

		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = serialPort.getInputStream();
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}
	
	public synchronized void start() {
		System.out.println("starting");
		if (serialPort != null) {
			try {
				output.write(1);
				output.write(1);
			} catch (IOException e) {
				System.err.println(e.toString());
			}
		}
	}
	
	public synchronized void stop() {
		System.out.println("stopping");
		if (serialPort != null) {
			try {
				output.write(0);
			} catch (IOException e) {
				System.err.println(e.toString());
			}
		}
	}
	
	public synchronized void close() {
		System.out.println("closing serial port");
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}
	public void pause() {
		if (serialPort != null) {
			try {
				output.write(0);
			}catch (IOException e) {
			System.err.println(e.toString());
			}
		}
	}
		
	public void resume() {
		System.out.println("resume");
		if(serialPort !=null){
			try{
				output.write(1);
				output.write(1);
			}catch (IOException e) {
			System.err.println(e.toString());
			}
		}
	}


	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				int available = input.available();
				byte[] chunk = new byte[available];
				input.read(chunk, 0, available);
				for(int i = 0; i < available; i++)
					buffer.write(chunk[i]);
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}
}
