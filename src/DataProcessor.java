import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class DataProcessor implements Runnable {
	private boolean running;
	private CircularObjectBuffer<Byte> buffer;
	private ArrayList<Reading> readings;
	int counter;
	long time;
	long starttime;
	int t=0;
	FileWriter outFile;
	PrintWriter out;

	private static final int DATALENGTH = 2 * 2 + 1;
	private static final int INTERVAL = 1000;
	private static final String FILEPATH = "C:\\Users\\Louis\\Desktop\\Engineering project_Internship";
	
	
	public DataProcessor(SerialTest serial) {
		buffer = serial.buffer;
		running = true;
		readings = new ArrayList<Reading>(1000);
	}

	public void run() {
		counter = 0;
		time = -1;
		readings.clear();
		running = true;
		String filename = FILEPATH + "\\" + System.currentTimeMillis() + "_QL6_480kbps_592x336_25fps_test_3.csv";
		try {
			outFile = new FileWriter(filename);
			out = new PrintWriter(outFile);
		} catch (IOException e1) {
			System.err.println(e1.getMessage());
			return;
		}
		System.out.println("processor start - " + filename);
		try {
			loop();
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
		out.close();
		System.out.println("processor end - " + filename);
		System.out.println("processor end - " + filename);
	}
	
	public void stop() {
		running = false;
	}
	
	private void loop() throws InterruptedException {
		Byte b0, b1;
		int resistor, voltage;
		long readingtime;
		while(running || buffer.getAvailable() >= DATALENGTH) {
			if(time >= 0 && System.currentTimeMillis() >= time + INTERVAL) {
				if(counter > 0) {
					counter = 0;
					processReadings(time);
				}
				time += INTERVAL;
			}
			if(buffer.getAvailable() >= DATALENGTH) {
				if(buffer.read() != (byte)255)
					continue;
				readingtime = System.currentTimeMillis();
				b0 = buffer.read();
				b1 = buffer.read();
				resistor = ((b0 & 0xFF) << 8) + (b1 & 0xFF);
				b0 = buffer.read();
				b1 = buffer.read();
				voltage = ((b0 & 0xFF) << 8) + (b1 & 0xFF);
				readings.add(new Reading(readingtime, resistor,voltage));
				counter++;
			} else Thread.sleep(100);
			if(time == -1 && counter == 1) {
				time = System.currentTimeMillis();
				starttime = time;
			}
		}
	}
	
	private void processReadings(long time) {
		if(readings.size() == 0)
			return;
		long tempResistor = 0, tempBattery = 0;
		double voltage,current,Rres,resistor,power = 0;
		
		for(Reading r : readings) {
			tempResistor += r.resistor;
			tempBattery += r.voltage;
		}
		resistor = tempResistor * (5000.0 / 1024.0) / readings.size() / 0.22;
		voltage = tempBattery * (5.0 / 1024.0) / readings.size();
		Rres=GUI.get_res();
		power = (resistor*voltage)/Rres;
		current = power/voltage;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd, HH:mm:ss");
		Date resultdate=new Date(time);
		out.println(sdf.format(resultdate)+","+t + "," + Rres + "," + voltage + "," + power);
		t++;
		System.out.println(("TIME= "+sdf.format(resultdate)+", "+readings.size() / (INTERVAL/1000))+ " mps. t = " + t + ", V = " + voltage + " , P = " + power + " mW"+ " , I = " + current+ " A ");
		GUI.setoutput(power);
		GUI.setoutput1(voltage);
		GUI.setoutput2(current);
		GUI.datachart.refreshData(power);
		readings.clear();
	}


}
