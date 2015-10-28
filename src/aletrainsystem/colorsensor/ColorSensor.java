package aletrainsystem.colorsensor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import aletrainsystem.enums.SleeperColor;
import aletrainsystem.models.Sleeper;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import no.ntnu.item.arctis.runtime.Block;

public class ColorSensor extends Block {

	public final int CONSECUTIVE_READINGS_REQUIREMENT = 10;
//	public final int CONSECUTIVE_COLOR_REQUIREMENT = 8; 
	public final double SLEEPER_DISTANCE = 32.0;

	private EV3ColorSensor colorSensor;
	private Port s1 = LocalEV3.get().getPort("S1");
	private int lastDetectedColorID;
	private double speed;
	public long registeredTime = System.currentTimeMillis();
	public long lastRegisteredTime = System.currentTimeMillis();
	public double timeDifference;
	public boolean stopping = false;
	public boolean standby = true;
	private int readings;
	private HashMap<Integer, Integer> colorCount;
	private ArrayList<String> log;
	
	public Sleeper readColor() {
		int detectedColorID = colorSensor.getColorID();
		
		readings++;
		if (colorCount.containsKey(detectedColorID)) {
			colorCount.put(detectedColorID, colorCount.get(detectedColorID)+1);			
		}
		else {
			colorCount.put(detectedColorID, 1);
		}
		
		log.add(System.currentTimeMillis() + ", " + detectedColorID);
		if (readings > CONSECUTIVE_READINGS_REQUIREMENT) {
//			if (findHighestCount() > CONSECUTIVE_COLOR_REQUIREMENT) {
				int colorWithHighestCount = findColorWithHighestCount();
				if (colorWithHighestCount != lastDetectedColorID) {
					lastDetectedColorID = colorWithHighestCount;
					registeredTime = System.currentTimeMillis();
//					log.add(registeredTime + ", " + colorCount);
					if (isSleeper(colorWithHighestCount)) {
						speed = calculateSpeed(registeredTime);
						return new Sleeper(SleeperColor.convertFromLejosColor(colorWithHighestCount), speed);
					}
				}
				colorCount.clear();
				readings = 0;
			}
//		}
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


	private int findColorWithHighestCount() {
		int maxKey = -1;
		int maxValue = -1;
		for (int key : colorCount.keySet()) {
			if (colorCount.get(key) > maxValue) {
				maxKey = key;
				maxValue = colorCount.get(key); 
			}
		}

		return maxKey;
	}


	private static boolean isSleeper(int detectedColorID) {
		return !(detectedColorID == Color.WHITE);
	}

	private double calculateSpeed(long regTime) {
		timeDifference = Double.parseDouble(Long.toString(regTime - lastRegisteredTime));
		double calculatedSpeed = SLEEPER_DISTANCE / timeDifference;
		lastRegisteredTime = regTime;
		return calculatedSpeed;
	}

	public void init() {
		Button.LEDPattern(2);
		colorSensor = new EV3ColorSensor(s1);
		colorSensor.setFloodlight(Color.WHITE);
		colorCount = new HashMap<Integer, Integer>();
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
