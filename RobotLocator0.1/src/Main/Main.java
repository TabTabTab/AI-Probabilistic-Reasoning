package Main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Graphics.WorldView;
import Robot.Robot;

public class Main {
	private static WorldView boardView;


	public static void main(String[] args) throws InterruptedException{
		int answer=JOptionPane.showConfirmDialog(null, 
				"Do you want to run the Graphic version? \n Pressing no results in plain statistics",
				"The Choice",
				JOptionPane.YES_NO_OPTION);
		if(answer==0){
			JFrame frame = new JFrame();
			frame.setSize(WorldView.WIDTH+7, WorldView.HEIGHT+30);
			boardView=new WorldView();
			frame.setLocationRelativeTo(null);
			frame.getContentPane().add(boardView);
			frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {
					boardView.stop();
					boardView.destroy();
					System.exit(0);
				}
			});
			frame.setVisible(true);
			frame.setResizable(false);
			boardView.init();
			boardView.start();
			Robot robot = new Robot(boardView);
			robot.run();
		}else{
			Robot robot = new Robot(null);
			robot.statisticsRun(100000);
		}
	}
}
