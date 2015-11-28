package aletrainsystem.colorsensorthreaded;

import aletrainsystem.enums.SleeperColor;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import no.ntnu.item.arctis.runtime.Block;

public class ColorSensorThreaded extends Block implements Runnable {
	public final int CONSECUTIVE_READINGS_REQUIREMENT = 2;
	public final int SAMPLESIZE = 20;
	public final int BACKGROUND_COLOR_ID = Color.BROWN;
	public final int HIGHEST_SIGNAL_COLOR_ID = 5;
	public EV3ColorSensor colorSensor;
	public Port s1 = LocalEV3.get().getPort("S1");
	public int lastDetectedColorId = -1;
	public int lastRegisteredColorId = BACKGROUND_COLOR_ID;
	public int readings;
	public boolean stopped;
	public java.lang.Thread sensorThread;
	public void init() {
		Button.LEDPattern(2);
		colorSensor = new EV3ColorSensor(s1);
		colorSensor.setFloodlight(Color.WHITE);
		logger.info("Initialized");
		sensorThread = new Thread(this);
		sensorThread.start();
	}
	
	@Override
	public void run() {
		Thread thisThread = Thread.currentThread();
		
		while (sensorThread == thisThread) {
			float[] f = new float[SAMPLESIZE];
			for (int i = 0; i < f.length; i++) {
				colorSensor.fetchSample(f, i);
				if (i > 0 && f[i] != f[i - 1])
					return;
			}
			int detectedColorId = (int) f[0];
			if (detectedColorId == lastRegisteredColorId) {
				return;
			}
			if (detectedColorId != lastDetectedColorId) {
				lastDetectedColorId = detectedColorId;
				readings = 1;
				return;
			}
			readings++;
			if (detectedColorId < HIGHEST_SIGNAL_COLOR_ID || readings == CONSECUTIVE_READINGS_REQUIREMENT) {
				lastRegisteredColorId = detectedColorId;
				if (detectedColorId != BACKGROUND_COLOR_ID) {
					logger.info("Detected sleeper: " + detectedColorId);
					sendToBlock("SLEEPER", SleeperColor.convertFromLejosColor(detectedColorId));
				}
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stop() {
		sensorThread = null;
	}
}
