package aletrainsystem.colorsensor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.HashMap;

import aletrainsystem.enums.SleeperColor;
import aletrainsystem.models.Sleeper;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import no.ntnu.item.arctis.runtime.Block;

public class ColorSensor extends Block {

	private final int CONSECUTIVE_READINGS_REQUIREMENT = 3;
	private final int SAMPLESIZE = 20;
//	public final int CONSECUTIVE_COLOR_REQUIREMENT = 8; 
	private final double SLEEPER_DISTANCE = 32.0;
	private final int BACKGROUND_COLOR_ID = Color.BROWN;
	private final int REGULAR_SLEEPER_ID = Color.BLACK;
	private final int HIGHEST_SIGNAL_COLOR_ID = 5;

	private EV3ColorSensor colorSensor;
	private Port s1 = LocalEV3.get().getPort("S1");
	private int lastDetectedColorId = -1;
	private int lastRegisteredColorId = BACKGROUND_COLOR_ID;
	private double speed;
	private long registeredTime = System.currentTimeMillis();
	private long lastRegisteredTime = System.currentTimeMillis();
	private double timeDifference;
	private boolean stopping = false;
	private boolean standby = true;
	private int readings;
//	private HashMap<Integer, Integer> colorCount;
	private ArrayList<String> log;
	
	public SleeperColor readColor() {
//		int detectedColorID = colorSensor.getColorID();
		float[] f = new float[SAMPLESIZE];
		for (int i = 0; i < f.length; i++) {
			colorSensor.fetchSample(f, i);
			if (i > 0 && f[i] != f[i-1]) return null;
		}
		
		int detectedColorId = (int) f[0];
//		log.add(System.currentTimeMillis() + ", " + Arrays.toString(f));
		
		if (detectedColorId == lastRegisteredColorId
				|| (lastRegisteredColorId != BACKGROUND_COLOR_ID && detectedColorId != BACKGROUND_COLOR_ID)) {	
			return null;
		}
		
		if (detectedColorId != lastDetectedColorId) {
			lastDetectedColorId = detectedColorId;
			readings = 1;
			return null;
		}
		
//		if (colorCount.containsKey(detectedColorID)) {
//			colorCount.put(detectedColorID, colorCount.get(detectedColorID)+1);			
//		}
//		else {
//			colorCount.put(detectedColorID, 1);
//		}
		
		
		readings++;
		if (detectedColorId < HIGHEST_SIGNAL_COLOR_ID || readings == CONSECUTIVE_READINGS_REQUIREMENT) {
			logger.info(SleeperColor.convertFromLejosColor(detectedColorId).toString());
////			if (findHighestCount() > CONSECUTIVE_COLOR_REQUIREMENT) {
////				int colorWithHighestCount = findColorWithHighestCount();
//				if (detectedColorID != lastRegisteredColorID 
//						&& (lastRegisteredColorID == BACKGROUND_COLOR_ID || detectedColorID == BACKGROUND_COLOR_ID)) {
					lastRegisteredColorId = detectedColorId;
////					registeredTime = System.currentTimeMillis();
////					log.add(registeredTime + ", " + colorCount);
//					if (detectedColorId != BACKGROUND_COLOR_ID) {
//////						speed = calculateSpeed(registeredTime);
//					}
					return SleeperColor.convertFromLejosColor(detectedColorId);
//				}
////				colorCount.clear();
//			}
		}
		return null;
	}

//	private int findHighestCount() {
//		int maxValue = -1;
//		for (int key : colorCount.keySet()) {
//			if (colorCount.get(key) > maxValue) {
//				maxValue = colorCount.get(key); 
//			}
//		}
//
//		return maxValue;
//	}


//	private int findColorWithHighestCount() {
//		int maxKey = -1;
//		int maxValue = -1;
//		for (int key : colorCount.keySet()) {
//			if (colorCount.get(key) > maxValue) {
//				maxKey = key;
//				maxValue = colorCount.get(key); 
//			}
//		}
//
//		return maxKey;
//	}


//	private static boolean isSleeper(int detectedColorID) {
//		return !(detectedColorID == Color.WHITE);
//	}
//
//	private double calculateSpeed(long regTime) {
//		timeDifference = Double.parseDouble(Long.toString(regTime - lastRegisteredTime));
//		double calculatedSpeed = SLEEPER_DISTANCE / timeDifference;
//		lastRegisteredTime = regTime;
//		return calculatedSpeed;
//	}

	public void init() {
		Button.LEDPattern(2);
		colorSensor = new EV3ColorSensor(s1);
		colorSensor.setFloodlight(Color.WHITE);
//		colorCount = new HashMap<Integer, Integer>();
		log = new ArrayList<String>();
		logger.info("Initialized");
	}

	public SleeperColor getColor(Sleeper sleeper) {
		return sleeper.detectedColor;
	}

	public double getSpeed(Sleeper sleeper) {
		return sleeper.measuredSpeed;
	}
	
	public void writeLogToFile() {
		try {
			PrintWriter writer = new PrintWriter("colorcountlog.txt", "UTF-8");
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
