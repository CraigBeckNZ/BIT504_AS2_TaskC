import java.awt.Color;
import java.awt.Rectangle;

public class Sprite {
	
	private int xPosition;
	private int yPosition;
	private int xVelocity;
	private int yVelocity;
	private int width;
	private int height;
	private Color colour;
	private int initialXPosition;
	private int initialYPosition;
	
	
	// Getter methods
	public int getXPosition() {return xPosition;}	
	public int getYPosition() {return yPosition;}	
	public int getXVelocity() {return xVelocity;}
	public int getYVelocity() {return yVelocity;}	
	public int getHeight() {return height;}	
	public int getWidth() {return width;}	
	public Color getColour() {return colour;}
	
	// Setter methods
	public void setXPosition(int x) {xPosition = x;}	
	public void setYPosition(int y) {yPosition = y;}	
	public void setXVelocity(int x) {xVelocity = x;}	
	public void setYVelocity(int y) {yVelocity = y;}	
	public void setWidth(int w) {width = w;}	
	public void setHeight(int h) {height = h;}
	public void setColour(Color c) {colour = c;}
	public void setInitialPosition(int x, int y) {
		initialXPosition = x;
		initialYPosition = y;
	}
	
	// Alternate setter for xposition that checks if sprite will be drawn in the panel
	public void setXPosition(int x, int panelWidth) {
		if (x < 0)
			xPosition = 0;
		else if (x + width > panelWidth)
			xPosition = panelWidth - width;
		else
			xPosition = x;
	}
	
	// Alternate setter for yposition that checks if sprite will be drawn in the panel
	public void setYPosition(int y, int panelHeight) {
		if (y < 0)
			yPosition = 0;
		else if (y + height > panelHeight)
			yPosition = panelHeight - getHeight();
		else
			yPosition = y;
	}
	
	// Resets the sprite to the original position
	public void resetToInitialPosition() {
		setXPosition(initialXPosition);
		setYPosition(initialYPosition);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(getXPosition(), getYPosition(), getWidth(), getHeight());
	}
	
}
