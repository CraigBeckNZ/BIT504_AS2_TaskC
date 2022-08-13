import java.awt.Color;

public class Paddle extends Sprite {
	
	private static final int PADDLE_WIDTH = 10;
	private static final int PADDLE_HEIGHT = 100;
	private static final int PADDLE_INDENT = 40;
	private static final Color PADDLE_COLOUR = Color.white;

	public Paddle(Player playerEnum, int panelWidth, int panelHeight) {
		setWidth(PADDLE_WIDTH);
		setHeight(PADDLE_HEIGHT);
		setColour(PADDLE_COLOUR);
		
		// Detects wether this paddle is for player one or two and sets intial position accordingly
		if (playerEnum == Player.ONE) {
			setInitialPosition(PADDLE_INDENT, panelHeight / 2 - getHeight() / 2);
		}
		else if (playerEnum == Player.TWO) {
			setInitialPosition(panelWidth - PADDLE_INDENT - getWidth(), panelHeight / 2 - getHeight() / 2);
		}
		
		resetToInitialPosition();
	}
}
