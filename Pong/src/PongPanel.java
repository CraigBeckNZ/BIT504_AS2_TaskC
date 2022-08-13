import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;

public class PongPanel extends JPanel implements ActionListener, KeyListener {
	
	// Constants
	private final static Color BACKGROUND_COLOR = Color.BLACK;
	private final static int TIMER_DELAY = 5;
	private final static int BALL_MOVEMENT_SPEED = 4;
	private final static int PADDLE_MOVEMENT_SPEED = 3;
	private final static int POINTS_TO_WIN = 11;
	private final static int SCORE_X_PADDING = 100;
	private final static int SCORE_Y_PADDING = 100;
	private final static int SCORE_FONT_SIZE = 50;
	private final static String SCORE_FONT_FAMILY = "Serif";
	
	
	// Class variables
	private Ball ball;
	private GameState gameState = GameState.INITIALISING;
	private Paddle paddle1, paddle2;
	int player1Score = 0, player2Score = 0;
	Player gameWinner;
	
	public PongPanel() {
		setBackground(BACKGROUND_COLOR);
		Timer timer = new Timer(TIMER_DELAY, this);
		timer.start();
		addKeyListener(this);
		setFocusable(true);
	}
	
	public void createObjects() {
		ball = new Ball(getWidth(), getHeight());
		paddle1 = new Paddle(Player.ONE, getWidth(), getHeight());
		paddle2 = new Paddle(Player.TWO, getWidth(), getHeight());
	}

	public void update() {
		
		switch(gameState) {
		case INITIALISING: {
			createObjects();
			ball.setXVelocity(BALL_MOVEMENT_SPEED);
			ball.setYVelocity(BALL_MOVEMENT_SPEED);
			gameState = GameState.PLAYING;
			break;
		}
		case PLAYING: {
			moveObject(paddle1);
			moveObject(paddle2);
			moveObject(ball);
			checkWallBounce();
			checkPaddleBounce();
			checkWin();
			break;
		}
		case GAMEOVER: {
			break;
		}
		}
		
	}
	
	public void addScore(Player player) {
		switch(player) {
		case ONE: {
			player1Score++;
			break;
		}
		case TWO: {
			player2Score++;
			break;
		}
		}
		
	}
	
	public void checkWin() {
		if (player1Score >= POINTS_TO_WIN) {
			//Player 1 has won the game
			gameWinner = Player.ONE;
			gameState = GameState.GAMEOVER;
		}
		else if (player2Score >= POINTS_TO_WIN) {
			// Player 2 has won the game
			gameWinner = Player.TWO;
			gameState = GameState.GAMEOVER;
		}
	}
	
	public void moveObject(Sprite s){
		s.setXPosition(s.getXPosition() + s.getXVelocity(), getWidth());
		s.setYPosition(s.getYPosition() + s.getYVelocity(), getHeight());
	}
	
	public void checkWallBounce() {
		// Checks side walls collision
		if (ball.getXPosition() <= 0) {
			// Ball has hit the left side
			addScore(Player.TWO);
			ball.setXVelocity(-ball.getXVelocity());			
			resetBall();			
		}
		
		else if (ball.getXPosition() >= getWidth() - ball.getWidth()) {
			// Ball has hit the right side
			addScore(Player.ONE);
			ball.setXVelocity(-ball.getXVelocity());	
			resetBall();
		}
		
		// Checks top/bottom walls collision
		if (ball.getYPosition() <= 0 || ball.getYPosition() >= getHeight() - ball.getHeight()) {
			ball.setYVelocity(-ball.getYVelocity());			
		}
	}
	
	public void checkPaddleBounce(){
		// Check paddle 1 collision
		if (ball.getXVelocity() < 0 && ball.getRectangle().intersects(paddle1.getRectangle())) {
			ball.setXVelocity(-ball.getXVelocity());
		}
		else if (ball.getXVelocity() > 0 && ball.getRectangle().intersects(paddle2.getRectangle())) {
			ball.setXVelocity(-ball.getXVelocity());
		}
	}
	
	public void resetBall() {
		ball.resetToInitialPosition();
	}
	
	public void paintSprite(Graphics g, Sprite sprite) {
		g.setColor(sprite.getColour());
		g.fillRect(sprite.getXPosition(),sprite.getYPosition(), sprite.getWidth(), sprite.getHeight());
	}
	
	public void paintScores(Graphics g){
		Font scoreFont = new Font(SCORE_FONT_FAMILY, Font.BOLD, SCORE_FONT_SIZE);
		String leftScore = Integer.toString(player1Score);
		String rightScore = Integer.toString(player2Score);
		g.setFont(scoreFont);
		g.drawString(leftScore, SCORE_X_PADDING, SCORE_Y_PADDING);
		g.drawString(rightScore, getWidth() - SCORE_X_PADDING, SCORE_Y_PADDING);
	}
	
	public void paintWinner(Graphics g) {
		Font winnerFont = new Font(SCORE_FONT_FAMILY, Font.BOLD, SCORE_FONT_SIZE);
		g.setFont(winnerFont);
		switch (gameWinner) {
		case ONE: {
			g.drawString("Winner!" , getWidth() / 6, getHeight() / 2 - SCORE_FONT_SIZE / 2);
			break;
		}
		case TWO: {
			g.drawString("Winner!" , (getWidth() / 6) * 4, getHeight() / 2 - SCORE_FONT_SIZE / 2);
		}
		}
	}
	
	private void paintDottedLine(Graphics g) {
		Graphics2D g2d = (Graphics2D)g.create();
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {9}, 0);
		g2d.setStroke(dashed);
		g2d.setPaint(Color.white);
		g2d.drawLine(getWidth() / 2, 0,  getWidth() / 2, getHeight());
		g2d.dispose();
	}
		
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintDottedLine(g);
		if (gameState != GameState.INITIALISING) {
			paintSprite(g, ball);
			paintSprite(g, paddle1);
			paintSprite(g, paddle2);
			paintScores(g);
			if (gameState == GameState.GAMEOVER) {
				paintWinner(g);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent event) {
		
		// Detects key press for player 2
		if (event.getKeyCode() == KeyEvent.VK_UP) {
			paddle2.setYVelocity(-PADDLE_MOVEMENT_SPEED);
		}
		else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
			paddle2.setYVelocity(PADDLE_MOVEMENT_SPEED);
		}
		
		// Detects key press for player 1
		if (event.getKeyCode() == KeyEvent.VK_W) {
			paddle1.setYVelocity(-PADDLE_MOVEMENT_SPEED);
		}
		else if (event.getKeyCode() == KeyEvent.VK_S) {
			paddle1.setYVelocity(PADDLE_MOVEMENT_SPEED);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		// Detects key release for player 2
		if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN) {
			paddle2.setYVelocity(0);
		}
		
		// Detects key release for player 1
		if (event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_S) {
			paddle1.setYVelocity(0);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		update();		
		repaint();
	}	

}
