package aletrainsystem.colorsensorthreaded;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import aletrainsystem.enums.SleeperColor;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import no.ntnu.item.arctis.runtime.Block;

public class ColorSensorThreaded extends Block {
	public final int CONSECUTIVE_READINGS_REQUIREMENT = 2;
	public final int SAMPLESIZE = 20;
	public final int BACKGROUND_COLOR_ID = Color.BROWN;
	public final int HIGHEST_SIGNAL_COLOR_ID = 5;
	public EV3ColorSensor colorSensor;
	public Port s1 = LocalEV3.get().getPort("S1");
	public int lastDetectedColorId = -1;
	public int lastRegisteredColorId = BACKGROUND_COLOR_ID;
	public int readings;
	public boolean stopped = false;
	public java.lang.Thread sensorThread;
	private ArrayList<String> log = new ArrayList<String>();
	private long previousLogging;
	public void init() {
		Button.LEDPattern(2);
		colorSensor = new EV3ColorSensor(s1);
		colorSensor.setFloodlight(Color.WHITE);
		log = new ArrayList<>();
		previousLogging = System.currentTimeMillis();
		logger.info("Initialized");
		
		Runnable r = new Runnable() {
			public void run() {
				while (!stopped) {
					int detectedColorId = colorSensor.getColorID();
					
					long now = System.currentTimeMillis();
					log.add(String.valueOf(now - previousLogging));
					previousLogging = now;

					if (detectedColorId == lastRegisteredColorId) {	
						sleep();
						continue;
					}

					if (detectedColorId != lastDetectedColorId) {
						lastDetectedColorId = detectedColorId;
						readings = 1;
						sleep();
						continue;
					}

					readings++;
					if (detectedColorId < HIGHEST_SIGNAL_COLOR_ID || readings == CONSECUTIVE_READINGS_REQUIREMENT) {
						lastRegisteredColorId = detectedColorId;
						if (detectedColorId != BACKGROUND_COLOR_ID) {
							sendToBlock("SLEEPER", SleeperColor.convertFromLejosColor(detectedColorId));
						}
					}

					sleep();
				}
			}
		};
		
		runAsync(r);
	}

	public void stop() {
		stopped = true;
		writeLogToFile();
	}
	
	public void sleep(){
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void writeLogToFile() {
		try {
			PrintWriter writer = new PrintWriter("readerlog.txt", "UTF-8");
			writer.println("Battery voltage: " + lejos.hardware.Battery.getVoltage());
			for (String string : log) {
				writer.println(string);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
