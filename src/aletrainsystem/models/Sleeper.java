package aletrainsystem.models;

import java.text.DecimalFormat;

import aletrainsystem.enums.SleeperColor;

public class Sleeper {
	public SleeperColor detectedColor;
	public double measuredSpeed;
	public DecimalFormat numberFormat = new DecimalFormat("#.00");

	public Sleeper(SleeperColor color, double speed) {
		detectedColor = color;
		measuredSpeed = speed;
	}

	public String toString() {
		return String.valueOf(detectedColor) + "," + numberFormat.format(measuredSpeed);
	}
}