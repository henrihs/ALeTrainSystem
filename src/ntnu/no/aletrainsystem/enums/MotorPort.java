package ntnu.no.aletrainsystem.enums;

import lejos.hardware.motor.NXTRegulatedMotor;

public enum MotorPort {
	A("MOTOR_PORT_A", lejos.hardware.motor.Motor.A), 
	B("MOTOR_PORT_B", lejos.hardware.motor.Motor.B), 
	C("MOTOR_PORT_C", lejos.hardware.motor.Motor.C), 
	D("MOTOR_PORT_D", lejos.hardware.motor.Motor.D);
	
	private String propertyName;
	private NXTRegulatedMotor motor;
	private MotorPort(String propertyName, NXTRegulatedMotor motor){
		this.propertyName = propertyName;
		this.motor = motor;
	}
	
	public String getPropertyName(){
		return this.propertyName;
	}
	
	public NXTRegulatedMotor getMotor(){
		return this.motor;
	}
}
