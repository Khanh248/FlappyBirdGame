import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.random.*;
import javax.swing.*;



public class FlappyBird extends JPanel implements ActionListener, KeyListener{
	
	int boardwidth = 360;
	int boardheight = 640;
	
	// Images
	Image BackgroundImg;
	Image BirdImg;
	Image topPipeImg;
	Image bottomPipeImg;
	
	//Bird
	
	int birdX = boardwidth/8; // position 1/8 left side on the screen
	int birdY = boardheight/2;// from the top of the screen, the bird is moved down half of the board 
	int birdWidth = 34;
	int birdHeight = 24;

	class Bird {
		int x = birdX;
		int y = birdY;
		int width = birdWidth;
		int height = birdHeight;
		Image img;
		
		Bird(Image img){
			this.img = img;
		}
	}
	
	//Pipes
	int pipeX = boardwidth;
	int pipeY = 0;
	int pipewidth = 64;// scale 1/6
	int pipeheight = 512;
	
	
	class Pipe{
		int x = pipeX;
		int y = pipeY;
		int width = pipewidth;
		int height = pipeheight;
		Image img;
		boolean passed = false;
		
		
		Pipe(Image img){
			this.img = img;
		}
	}
	
	
	//logic playing
	Bird bird;
	int velocityX = -4; //move pipes to the left speed (simulates bird moving right)
	int velocityY =0; // move bird up/down speed
	int gravity = 1;
	
	
	ArrayList<Pipe> pipes;
	Random random = new Random();
	// timer
	Timer gameloop;
	Timer placePtimer;
	boolean gameOver = false;
	double score = 0;
	
	
	 FlappyBird() {
		setPreferredSize(new Dimension(boardwidth,boardheight));
		//setBackground(Color.blue);
		setFocusable(true);
		addKeyListener(this);
		
	// load images
		BackgroundImg =  new ImageIcon(getClass().getResource("./Img Flappy Bird/flappybirdbg.png")).getImage();
		BirdImg = new ImageIcon(getClass().getResource("./Img Flappy Bird/flappybird.png")).getImage();
		topPipeImg = new ImageIcon(getClass().getResource("./Img Flappy Bird/toppipe.png")).getImage();
		bottomPipeImg = new ImageIcon(getClass().getResource("./Img Flappy Bird/bottompipe.png")).getImage();
	
		// bird
  		bird = new Bird(BirdImg);
  		pipes = new ArrayList<Pipe>();
  		
  		// place pipes timer
  		placePtimer = new Timer(1500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				placePipes();	
			}
  		
  		});
  		placePtimer.start();
		
		// game timer
		gameloop = new Timer(1000/60, this);
		gameloop.start();			
		
	}
	public void placePipes() {
		int randomPipeY = (int)(pipeY - pipeheight/4 - Math.random()*(pipeheight/2));
		int openingSpace = boardheight/4;
		Pipe topPipe = new Pipe(topPipeImg);
		topPipe.y = randomPipeY;
		pipes.add(topPipe);
		
		Pipe bottomPipe = new Pipe(bottomPipeImg);
		bottomPipe.y = topPipe.y + pipeheight + openingSpace;
		pipes.add(bottomPipe); 
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		
		System.out.println("draw");
		//background
		g.drawImage(BackgroundImg, 0, 0, boardwidth, boardheight, null);
	    
		// Bird
		g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
	
		// pipes
		for(int i= 0; i< pipes.size(); i++) {
			Pipe pipe = pipes.get(i);
			g.drawImage(pipe.img, pipe.x,pipe.y, pipe.width, pipe.height, null);
		}
		
		//Score
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN,32));
		if (gameOver) {
			g.drawString("Game Over: "+ String.valueOf((int)score), 10,35);
		}
		else {
			g.drawString(String.valueOf((int)score), 10, 35);
		}
	}
	public void move() {
		//bird
		velocityY += gravity;
		bird.y += velocityY;
		bird.y = Math.max(bird.y, 0);
		
		//pipes
		for(int i=0; i< pipes.size(); i++ ) {
			Pipe pipe = pipes.get(i);
			pipe.x += velocityX;
			
			if (!pipe.passed && bird.x > pipe.x + pipe.width) {
				pipe.passed = true;
				score += 0.5;
				
			}
				
		}
	if (bird.y > boardheight) {
		gameOver = true;
	}
	}
	
	public boolean collision(Bird a, Pipe b) {
		return a.x < b.x + b.width && // a's top left corner doesn't reach b's top right corner
				a.x + a.width > b.x && // a's top right corner passes b's top left corner 
				a.y < b.y + b.height && //a's top left corner doesn't reach b's bottom left corner
				a.y + a.height > b.y; // a's bottom left corner passes b's top left corner
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		repaint();
		if (gameOver) {
			placePtimer.stop();
			gameloop.stop();
		}
		
		
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
	    	velocityY = -9;
	        if (gameOver) {
	        	// reset the game
	        	bird.y = birdY;
	        	velocityY = 0;
	        	pipes.clear();
	        	score =0;
	        	gameOver = false;
	        	gameloop.start();
	        	placePtimer.start();
	        		        		
	        }
	    }
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
