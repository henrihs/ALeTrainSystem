package aletrainsystem.colorsensor;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import aletrainsystem.enums.SleeperColor;
import aletrainsystem.models.Sleeper;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import no.ntnu.item.arctis.runtime.Block;

public class ColorSensor extends Block implements Runnable {

	public final int CONSECUTIVE_READINGS_REQUIREMENT = 15;
	public final int CONSECUTIVE_COLOR_REQUIREMENT = 8; 
	public final double SLEEPER_DISTANCE = 32.0;

	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);	
	private EV3ColorSensor colorSensor;
	private Port s1 = LocalEV3.get().getPort("S1");
	private int lastDetectedColorID;
	private int lastRegisteredColorID;
	private int consecutiveColorIdCounter;
	private double speed;
	public long registeredTime = System.currentTimeMillis();
	public long lastRegisteredTime = System.currentTimeMillis();
	public double timeDifference;
	public boolean stopping = false;
	public boolean standby = true;
	private int readings;
	private HashMap<Integer, Integer> colorCount;

	@Override
	public synchronized void run() {
		int detectedColorID = colorSensor.getColorID();
		//		if (detectedColorID == lastRegisteredColorID) 
		//			return;

		//		if (detectedColorID == lastDetectedColorID && consecutiveColorIdCounter > 0) {
		//			consecutiveColorIdCounter++;
		//		} 
		//		else {
		//			consecutiveColorIdCounter = 1;
		//			lastDetectedColorID = detectedColorID;
		//			return;
		//		}

		readings++;
		if (colorCount.containsKey(detectedColorID)) {
			colorCount.put(detectedColorID, colorCount.get(detectedColorID)+1);			
		}
		else {
			colorCount.put(detectedColorID, 1);
		}

		//		if (consecutiveColorIdCounter > CONSECUTIVE_COLOR_REQUIREMENT && detectedColorID != lastRegisteredColorID) {
		//			registeredTime = System.currentTimeMillis();
		//			if (isSleeper(detectedColorID)) {
		//				speed = calculateSpeed(registeredTime);
		//				sendToBlock("SLEEPER", new Sleeper(SleeperColor.convertFromLejosColor(detectedColorID), speed));
		//			}
		//			
		//			lastRegisteredColorID = detectedColorID;
		//		}

		if (readings > CONSECUTIVE_READINGS_REQUIREMENT) {
			if (findHighestCount() > CONSECUTIVE_COLOR_REQUIREMENT) {
				int colorWithHighestCount = findColorWithHighestCount();
				if (colorWithHighestCount != lastDetectedColorID) {
					lastDetectedColorID = colorWithHighestCount;
					registeredTime = System.currentTimeMillis();
					if (isSleeper(colorWithHighestCount)) {
						speed = calculateSpeed(registeredTime);
						sendToBlock("SLEEPER", new Sleeper(SleeperColor.convertFromLejosColor(colorWithHighestCount), speed));
						lastRegisteredColorID = colorWithHighestCount;
					}
				}
				colorCount.clear();
				readings = 0;
			}
		}
	}

	private int findHighestCount() {
		int maxValue = -1;
		for (int key : colorCount.keySet()) {
			if (colorCount.get(key) > maxValue) {
				maxValue = colorCount.get(key); 
			}
		}

		return maxValue;
	}


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
		return !(detectedColorID == Color.BROWN);
	}

	private double calculateSpeed(long regTime) {
		timeDifference = Double.parseDouble(Long.toString(regTime - lastRegisteredTime));
		double calculatedSpeed = SLEEPER_DISTANCE / timeDifference;
		lastRegisteredTime = regTime;
		return calculatedSpeed;
	}

	public void start() {
		Button.LEDPattern(2);
		colorSensor = new EV3ColorSensor(s1);
		colorSensor.setFloodlight(Color.WHITE);
		colorCount = new HashMap<Integer, Integer>();
		scheduler.scheduleAtFixedRate(this, 5, 1, TimeUnit.MILLISECONDS);
		logger.info("Initialized");
	}

	public void stop() {
		try {
			scheduler.awaitTermination(500, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public SleeperColor getColor(Sleeper sleeper) {
		return sleeper.detectedColor;
	}

	public double getSpeed(Sleeper sleeper) {
		return sleeper.measuredSpeed;
	}
}
