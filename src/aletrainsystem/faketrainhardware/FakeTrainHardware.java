package aletrainsystem.faketrainhardware;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import aletrainsystem.enums.SleeperColor;
import aletrainsystem.enums.SpeedLevel;
import no.ntnu.item.arctis.runtime.Block;

public class FakeTrainHardware extends Block {
	public SpeedLevel speedLevel;
	
	public void initialize() {
		JFrame f = new JFrame("Fake a sleeper!");
		JPanel p = new JPanel();
		GridLayout grid = new GridLayout(1, 8);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for (SleeperColor sleeper : SleeperColor.values()) {
			JButton b = new JButton();
			b.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					sendToBlock("SLEEPER", sleeper);
				}
			});
			grid.addLayoutComponent(sleeper.toString(), b);
		}
		
		p.setLayout(grid);
		f.add(p);
		f.setVisible(true);
	}
	
	public boolean isMoving() {
		return speedLevel != SpeedLevel.STOPPED;
	}

	public void setSpeedLevel() {
	}
	
	
}
