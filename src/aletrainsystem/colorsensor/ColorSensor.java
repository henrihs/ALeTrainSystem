package aletrainsystem.colorsensor;

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

public class ColorSensor extends Block {

	private final int CONSECUTIVE_READINGS_REQUIREMENT = 2;
	private final int SAMPLESIZE = 20;
	private final int BACKGROUND_COLOR_ID = Color.BROWN;
	private final int HIGHEST_SIGNAL_COLOR_ID = 5;

	private EV3ColorSensor colorSensor;
	private Port s1 = LocalEV3.get().getPort("S1");
	private int lastDetectedColorId = -1;
	private int lastRegisteredColorId = BACKGROUND_COLOR_ID;
	private int readings;
	private ArrayList<String> log = new ArrayList<String>();
	private long previousLogging;

	public SleeperColor readColor() {
//		float[] f = new float[SAMPLESIZE];
//		for (int i = 0; i < f.length; i++) {
//			colorSensor.fetchSample(f, i);
//			if (i > 0 && f[i] != f[i-1]) return null;
//		}
//
//		int detectedColorId = (int) f[0];
		int detectedColorId = colorSensor.getColorID();
		
		long now = System.currentTimeMillis();
		log.add(String.valueOf(now - previousLogging));
		previousLogging = now;

		if (detectedColorId == lastRegisteredColorId) {	
			return null;
		}

		if (detectedColorId != lastDetectedColorId) {
			lastDetectedColorId = detectedColorId;
			readings = 1;
			return null;
		}

		readings++;
		if (detectedColorId < HIGHEST_SIGNAL_COLOR_ID || readings == CONSECUTIVE_READINGS_REQUIREMENT) {
			lastRegisteredColorId = detectedColorId;
			if (detectedColorId != BACKGROUND_COLOR_ID) {
				return SleeperColor.convertFromLejosColor(detectedColorId);
			}
		}
		return null;
	}

	public void init() {
		log = new ArrayList<>();
		previousLogging = System.currentTimeMillis();
		Button.LEDPattern(2);
		colorSensor = new EV3ColorSensor(s1);
		colorSensor.setFloodlight(Color.WHITE);
		logger.info("Initialized");
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
