package Main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import Graphics.WorldView;

public class Main {
	private static WorldView boardView;


	public static void main(String[] args) throws InterruptedException{

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
		HMMGame game = new HMMGame(boardView);
		game.run();
	}
}
