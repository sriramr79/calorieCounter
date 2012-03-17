package edu.upenn.cis350;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Class used to represent a Square that is drawn on the screen, including text, color, movement, and collisions.
 * 
 * See individual method documentation for more information
 * 
 * @author Paul M. Gurniak
 * @version 1.0
 *
 */
public class ScreenSquare {
	
	private int xpos;
	private int ypos;
	private int width;
	private int height;
	private int color;
	private int startx;
	private int starty;
	private int textColor;
	
	private String text;
	
	/**
	 * Constructor for a ScreenSquare with no text display (solid color).
	 * @param xpos Starting x position
	 * @param ypos Staring y position
	 * @param width Width of the square
	 * @param height Height of the square
	 * @param color Color of the square
	 */
	public ScreenSquare(int xpos, int ypos, int width, int height, int color) {
		this.xpos = xpos;
		this.ypos = ypos;
		this.startx = xpos;
		this.starty = ypos;
		this.width = width;
		this.height = height;
		this.color = color;
		this.text = null;
		this.textColor = Color.BLACK;
	}
	
	/**
	 * Default recommended constructor for a square on the screen.  Text color is black.
	 * @param xpos Starting x position
	 * @param ypos Staring y position
	 * @param width Width of the square
	 * @param height Height of the square
	 * @param color Color of the square
	 * @param text String to be drawn at the center of this square, or null if no string should be written.
	 */
	public ScreenSquare(int xpos, int ypos, int width, int height, int color, String text) {
		this.xpos = xpos;
		this.ypos = ypos;
		this.startx = xpos;
		this.starty = ypos;
		this.width = width;
		this.height = height;
		this.color = color;
		this.text = text;
		this.textColor = Color.BLACK;
	}
	
	/**
	 * Same as the default constructor, but allows the setting of a separate text color.
	 * @param xpos Starting x position
	 * @param ypos Staring y position
	 * @param width Width of the square
	 * @param height Height of the square
	 * @param color Color of the square
	 * @param text String to be drawn at the center of this square, or null if no string should be written.
	 * @param textColor Color of the text to be drawn
	 */
	public ScreenSquare(int xpos, int ypos, int width, int height, int color, String text, int textColor) {
		this.xpos = xpos;
		this.ypos = ypos;
		this.startx = xpos;
		this.starty = ypos;
		this.width = width;
		this.height = height;
		this.color = color;
		this.text = text;
		this.textColor = textColor;
	}
	
	/**
	 * Creates a new ScreenSquare at the location of the specified ScreenSquare with the specified color and text
	 * @param other ScreenSquare at the desired location
	 * @param color Color of the new ScreenSquare
	 * @param text Text label for the new ScreenSquare
	 */
	public ScreenSquare(ScreenSquare other, int color, String text) {
		this.xpos = other.xpos;
		this.ypos = other.ypos;
		this.startx = this.xpos;
		this.starty = this.ypos;
		this.height = other.height;
		this.width = other.width;
		this.color = color;
		this.text = text;
	}
	
	/**
	 * Resets the position of the square to its starting position
	 */
	public void resetPosition() {
		this.xpos = this.startx;
		this.ypos = this.starty;
	}
	
	/**
	 * Sets the current position as the starting position for this square
	 */
	public void setStartPosition() {
		this.startx = this.xpos;
		this.starty = this.ypos;
	}
	
	/**
	 * Moves the square to center at the given point
	 * @param x desired x-coordinate
	 * @param y desired y-coordinate
	 */
	public void centerAt(int x, int y) {
		this.xpos = x - this.width/2;
		this.ypos = y - this.height/2;
	}
	
	/**
	 * Gets the x-coordinate of the center of this square.  Used to generate a point that is guaranteed
	 * to be contained within the square
	 * @return The x-coordinate of the center of the square
	 */
	public int getCenterX() {
		return this.xpos + this.width/2;
	}
	
	/**
	 * Gets the y-coordinate of the center of this square.  Used to generate a point that is guaranteed
	 * to be contained within the square
	 * @return The y-coordinate of the center of the square
	 */
	public int getCenterY() {
		return this.ypos + this.height/2;
	}
	
	/**
	 * Moves the square's top left corner to the given point
	 * @param x desired x-coordinate
	 * @param y desired y-coordinate
	 */
	public void moveTo(int x, int y) {
		this.xpos = x;
		this.ypos = y;
	}
	
	/**
	 * Sets this square's color to the specified color
	 * @param color Color to be set to
	 */
	public void setColor(int color) {
		this.color = color;
	}
	
	/**
	 * Moves this square's top left corner to match that of the specified ScreenSquare
	 * @param other ScreenSquare whose position is to be matched
	 */
	public void moveTo(ScreenSquare other) {
		this.xpos = other.xpos;
		this.ypos = other.ypos;
	}
	
	/**
	 * Helper to draw this square in its current color (whichever that is).
	 * 
	 * This method does not modify any settings of the Paint object passed to it.
	 * 
	 * @param canvas Canvas object to draw to
	 * @param paint Paint object to use for the drawing
	 */
	public void draw(Canvas canvas, Paint paint) {
		int oldColor = paint.getColor();
		paint.setColor(this.color);
		canvas.drawRect(this.xpos, this.ypos, this.xpos + this.width, this.ypos + this.height, paint);
		
		if(text != null) {
			Paint.Align oldAlign = paint.getTextAlign();
			float oldSize = paint.getTextSize();
			paint.setTextAlign(Paint.Align.CENTER);
			paint.setColor(textColor);
			paint.setTextSize(0.8f*this.height);
			canvas.drawText(this.text, this.xpos + this.width/2, this.ypos + 4*this.height/5, paint);
			paint.setTextAlign(oldAlign);
			paint.setTextSize(oldSize);
		}
			
		paint.setColor(oldColor);
		
	}

	/**
	 * Method to determine if this square contains the specified point within its boundaries
	 * 
	 * @param x X coordinate of the point
	 * @param y Y coordinate of the point
	 * @return true if the point is contained within the boundaries (inclusive), false otherwise.
	 */
	public boolean containsPoint(int x, int y) {
		if(x - this.xpos >= 0 && y - this.ypos >= 0 && x - this.xpos < this.width && y - this.ypos < this.height) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method to help determine if this square is overlapping another one.
	 * 
	 * Warning: for simplicity, this assumes that both squares are the same size.
	 * 
	 * @param other Other instance of ScreenSquare to compare to
	 * @return Whether the ScreenSquares are overlapping
	 */
	public boolean overlapping(ScreenSquare other) {
		if(other == null) return false;
		return this.containsPoint(other.xpos, other.ypos) ||
				this.containsPoint(other.xpos, other.ypos + other.height) ||
				this.containsPoint(other.xpos + other.width, other.ypos) ||
				this.containsPoint(other.xpos + other.width, other.ypos + other.height) ||
				other.containsPoint(this.xpos, this.ypos) ||
				other.containsPoint(this.xpos, this.ypos + this.height) ||
				other.containsPoint(this.xpos + this.width, this.ypos) ||
				other.containsPoint(this.xpos + this.width, this.ypos + this.height);
	}
	
}
