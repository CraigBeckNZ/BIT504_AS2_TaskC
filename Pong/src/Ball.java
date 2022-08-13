import java.awt.Color;

public class Ball extends Sprite {
	private final static Color BALL_COLOUR = Color.white;
	private final static int BALL_WIDTH = 25;
	private final static int BALL_HEIGHT = 25;

	public Ball(int panelWidth, int panelHeight) {
		setColour(BALL_COLOUR);
		setWidth(BALL_WIDTH);
		setHeight(BALL_HEIGHT);
		setInitialPosition(panelWidth / 2 - BALL_WIDTH /2, panelHeight / 2 - BALL_HEIGHT / 2);
		resetToInitialPosition();
	}
}
