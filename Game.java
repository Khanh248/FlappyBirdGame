import javax.swing.*;

public class Game {
	public static void main(String [] args ) throws Exception{
		int boardwith = 360;
		int boardheight = 640;
		
		
		JFrame frame = new JFrame("Flappy Bird");
		frame.setVisible(true);
		frame.setSize(boardwith, boardheight);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		FlappyBird flappyBird = new FlappyBird();
		frame.add(flappyBird);
		frame.pack();
		flappyBird.requestFocus();
		frame.setVisible(true);
		
		
	
	}
	

}
