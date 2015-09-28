package ntnu.no.aletrainsystem.ev3.component;

import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import no.ntnu.item.arctis.runtime.Block;
import ntnu.no.aletrainsystem.enums.SwitchState;
import ntnu.no.aletrainsystem.models.PointSwitchId;
import ntnu.no.aletrainsystem.pointswitch.PointSwitchOrder;

public class Component extends Block {
	
	public RightKey rightButtonListener;
	public LefttKey leftButtonListener;
	public DownKey downButtonListener;
	private int i, j;
	public lejos.hardware.Button lol;
	
	public Component(){
		i = 0; j = 0;
		rightButtonListener = new RightKey();
		leftButtonListener = new LefttKey();
		downButtonListener = new DownKey();
		lejos.hardware.Button.RIGHT.addKeyListener(rightButtonListener);
		lejos.hardware.Button.LEFT.addKeyListener(leftButtonListener);
		lejos.hardware.Button.DOWN.addKeyListener(downButtonListener);
	}

	public PointSwitchOrder newOrderLeft() {
		return new PointSwitchOrder(new PointSwitchId(i++%4+1), SwitchState.DIVERT);
	}
	
	public PointSwitchOrder newOrderRight() {
		return new PointSwitchOrder(new PointSwitchId(j++%4+1), SwitchState.THROUGH);
	}

	public void printDone(PointSwitchOrder order) {
	}
	
	
	public class RightKey implements KeyListener{

		@Override
		public void keyPressed(Key k) {
			sendToBlock("RIGHTBUTTONDOWN");			
		}

		@Override
		public void keyReleased(Key k) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class LefttKey implements KeyListener{

		@Override
		public void keyPressed(Key k) {
			sendToBlock("LEFTBUTTONDOWN");			
		}

		@Override
		public void keyReleased(Key k) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class DownKey implements KeyListener{

		@Override
		public void keyPressed(Key k) {
			sendToBlock("DOWNBUTTONDOWN");			
		}

		@Override
		public void keyReleased(Key k) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
