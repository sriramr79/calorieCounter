package edu.upenn.cis350;

import java.util.ArrayList;
import java.util.Collections;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;


/**
 * Class to implement the view (containing drag-and-drop functionality) for the "food ranking" game
 * @author Paul M. Gurniak
 * @version 1.0
 */
public class RankingGameView extends View {

	private ArrayList<FoodItem> correctOrder;
	private ArrayList<FoodItem> displayOrder;
	
	private int dispHeight;
	private int dispWidth;
	
	private ScreenSquare order0Square;
	private ScreenSquare order1Square;
	private ScreenSquare order2Square;
	
	private ScreenSquare food0Square;
	private ScreenSquare food1Square;
	private ScreenSquare food2Square;
	private boolean food0Occupied;
	private boolean food1Occupied;
	private boolean food2Occupied;
	
	private ScreenSquare start0Square;
	private ScreenSquare start1Square;
	private ScreenSquare start2Square;
	
	private ScreenSquare submitSquare;
	
	private ScreenSquare touchedSquare;
	
	private static final int START_DIALOG = 1;
	private static final int START2_DIALOG = 2;
	private static final int CORRECT_DIALOG = 3;
	private static final int WRONG_DIALOG = 4;
	
	private Context mContext;
	
	public RankingGameView(Context c) {
		super(c);
		setUpFoodItems();
		setUpStaticDisplay();
		this.mContext = c;
		showDialog(START_DIALOG);
	}
	public RankingGameView(Context c, AttributeSet a) {
		super(c, a);
		setUpFoodItems();
		setUpStaticDisplay();
		this.mContext = c;
		showDialog(START_DIALOG);
	}
	
	private void setUpFoodItems() {
		FoodGenerator foods = new FoodGenerator(this.getResources());
		correctOrder = new ArrayList<FoodItem>();
		displayOrder = new ArrayList<FoodItem>();
		FoodItem nextFood = foods.nextFood();
		displayOrder.add(nextFood);
		correctOrder.add(nextFood);
		nextFood = foods.nextFood();
		displayOrder.add(nextFood);
		correctOrder.add(nextFood);
		nextFood = foods.nextFood();
		displayOrder.add(nextFood);
		correctOrder.add(nextFood);
		Collections.sort(correctOrder);
	}
	
	private void setUpStaticDisplay() {
		WindowManager wm = (WindowManager)this.getContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		this.dispHeight = display.getHeight();
		this.dispWidth = display.getWidth();
		
		Rect food0Loc = new Rect(2*dispWidth/26, dispHeight/26, (2*dispWidth + 8*dispHeight)/26, 6*dispHeight/26);
		Rect food1Loc = new Rect(2*dispWidth/26, 7*dispHeight/26, (2*dispWidth + 8*dispHeight)/26, 12*dispHeight/26);
		Rect food2Loc = new Rect(2*dispWidth/26, 13*dispHeight/26, (2*dispWidth + 8*dispHeight)/26, 18*dispHeight/26);
		
		order0Square = new ScreenSquare(1*dispWidth/26, 20*dispHeight/26, 3*dispWidth/26, 3*dispWidth/26, Color.GREEN, this.getContext().getResources().getString(R.string.rank1Button));
		order1Square = new ScreenSquare(5*dispWidth/26, 20*dispHeight/26, 3*dispWidth/26, 3*dispWidth/26, Color.YELLOW, this.getContext().getResources().getString(R.string.rank2Button));
		order2Square = new ScreenSquare(9*dispWidth/26, 20*dispHeight/26, 3*dispWidth/26, 3*dispWidth/26, Color.RED, this.getContext().getResources().getString(R.string.rank3Button));
		
		start0Square = new ScreenSquare(order0Square, Color.GRAY, this.getContext().getResources().getString(R.string.rankOtherButton));
		start1Square = new ScreenSquare(order1Square, Color.GRAY, this.getContext().getResources().getString(R.string.rankOtherButton));
		start2Square = new ScreenSquare(order2Square, Color.GRAY, this.getContext().getResources().getString(R.string.rankOtherButton));
		
		food0Square = new ScreenSquare(19*dispWidth/26, 3*dispHeight/26, 3*dispWidth/26, 3*dispWidth/26, Color.GRAY, this.getContext().getResources().getString(R.string.rankOtherButton));
		food1Square = new ScreenSquare(19*dispWidth/26, 9*dispHeight/26, 3*dispWidth/26, 3*dispWidth/26, Color.GRAY, this.getContext().getResources().getString(R.string.rankOtherButton));
		food2Square = new ScreenSquare(19*dispWidth/26, 15*dispHeight/26, 3*dispWidth/26, 3*dispWidth/26, Color.GRAY, this.getContext().getResources().getString(R.string.rankOtherButton));
		
		submitSquare = new ScreenSquare(16*dispWidth/26, 20*dispHeight/26, 9*dispWidth/26, 3*dispWidth/26, Color.RED, this.getContext().getResources().getString(R.string.rankSubmitButton), Color.WHITE);
		
		checkOccupancy();
		
		displayOrder.get(0).getImage().setBounds(food0Loc);
		displayOrder.get(1).getImage().setBounds(food1Loc);
		displayOrder.get(2).getImage().setBounds(food2Loc);
	}
	
	// This method is called when the View is displayed
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		displayOrder.get(0).getImage().draw(canvas);
		displayOrder.get(1).getImage().draw(canvas);
		displayOrder.get(2).getImage().draw(canvas);
		paint.setTextSize(dispHeight/40);
		paint.setColor(Color.WHITE);
		canvas.drawText(displayOrder.get(0).getName(), 2*dispWidth/26, 27*dispHeight/104, paint);
		canvas.drawText(displayOrder.get(1).getName(), 2*dispWidth/26, 51*dispHeight/104, paint);
		canvas.drawText(displayOrder.get(2).getName(), 2*dispWidth/26, 75*dispHeight/104, paint);
		submitSquare.setColor(food0Occupied && food1Occupied && food2Occupied ? Color.GREEN : Color.RED);
		submitSquare.draw(canvas, paint);
		start0Square.draw(canvas, paint);
		start1Square.draw(canvas, paint);
		start2Square.draw(canvas, paint);
		food0Square.draw(canvas, paint);
		food1Square.draw(canvas, paint);
		food2Square.draw(canvas, paint);
		order0Square.draw(canvas, paint);
		order1Square.draw(canvas, paint);
		order2Square.draw(canvas, paint);
		// Draw touchedSquare last so that it appears on top of everything else
		if(touchedSquare != null) {
			touchedSquare.draw(canvas, paint);
		}
		
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			int touchX = (int)event.getX();
			int touchY = (int)event.getY();
			
			if(submitSquare.containsPoint(touchX, touchY) && food0Occupied && food1Occupied && food2Occupied) {
				if(checkEnteredOrder()) {
					showDialog(CORRECT_DIALOG);
				} else {
					showDialog(WRONG_DIALOG);
				}
			}

			checkOccupancy();
			touchedSquare = order0Square.containsPoint(touchX, touchY) ? order0Square :
							order1Square.containsPoint(touchX, touchY) ? order1Square : 
							order2Square.containsPoint(touchX, touchY) ? order2Square : null; 		
			// If no square is touched, ignore event
			if(touchedSquare == null) {
				return false;
			}
			touchedSquare.centerAt(touchX, touchY);
			unmarkOverlapping(touchedSquare);
			invalidate();
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE) {
			// If no square was touched on the down action, ignore event
			if(touchedSquare != null) {
				touchedSquare.centerAt((int)event.getX(), (int)event.getY());
				invalidate();
				return true;
			}
			return false;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP) {
			// If no square was touched on the down action, ignore event
			if(touchedSquare != null) {
				// If we are currently overlapping a "destination" square, reset "starting" position
				ScreenSquare overlapping = 	touchedSquare.overlapping(food0Square) && !food0Occupied ? food0Square :
										  	touchedSquare.overlapping(food1Square) && !food1Occupied ? food1Square :
										  	touchedSquare.overlapping(food2Square) && !food2Occupied ? food2Square :
											null;
				
				if(overlapping == null) {
					touchedSquare.resetPosition();
				} else {
					touchedSquare.moveTo(overlapping);
					checkOccupancy();
					//touchedSquare.setStartPosition();
				}
				touchedSquare = null;
				invalidate();
				return true;
			}
			return false;
		}
		return false;
	}
	
	private void showDialog(int id) {
		createDialog(id).show();
	}

    private Dialog createDialog(int id) {
    	if(id == START_DIALOG) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
    		builder.setMessage(getResources().getString(R.string.rankWelcomeMessage));
    		builder.setPositiveButton(R.string.rankNextButton,
    				new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {
    				dialog.cancel();
    				showDialog(START2_DIALOG);
    			}
    		});
    		return builder.create();
    	}
    	else if(id == START2_DIALOG) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
    		builder.setMessage(getResources().getString(R.string.rankWelcomeMessage2));
    		builder.setPositiveButton(R.string.rankStartButton,
    				new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {
    				dialog.cancel();
    			}
    		});
    		return builder.create();
    	}
    	else if(id == CORRECT_DIALOG) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
    		builder.setMessage(getResources().getString(R.string.rankCorrectMessage));
    		builder.setPositiveButton(R.string.rankExitButton,
    				new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {
    				dialog.cancel();
    			}
    		});
    		return builder.create();
    	}
    	else if(id == WRONG_DIALOG) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
    		builder.setMessage(getResources().getString(R.string.rankWrongMessage));
    		builder.setPositiveButton(R.string.rankRetryButton,
    				new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {
    				dialog.cancel();
    			}
    		});
    		return builder.create();
    	}
    	return null;
    }
	
	/**
	 * Updates all occupancy variables depending on the position of the movable squares
	 */
	private void checkOccupancy() {
		food0Occupied = false;
		food1Occupied = false;
		food2Occupied = false;
		markOverlapping(order0Square);
		markOverlapping(order1Square);
		markOverlapping(order2Square);
	}
	
	/**
	 * Sets the occupancy variable for the position of the square that was just released
	 * @param square The square that was just released
	 */
	private void markOverlapping(ScreenSquare square) {
		if(square.overlapping(food0Square)) food0Occupied = true;
		if(square.overlapping(food1Square)) food1Occupied = true;
		if(square.overlapping(food2Square)) food2Occupied = true;
	}
	
	/**
	 * Clears the occupancy variable for the position of the square that was just picked up
	 * @param square The square that was just touched
	 */
	private void unmarkOverlapping(ScreenSquare square) {
		if(square.overlapping(food0Square)) food0Occupied = false;
		if(square.overlapping(food1Square)) food1Occupied = false;
		if(square.overlapping(food2Square)) food2Occupied = false;
	}

	/**
	 * Checks to see whether the order that the foods were ranked is correct
	 * @return
	 */
	private boolean checkEnteredOrder() {
		for(int i = 0; i < 3; i++) {
			int position = displayOrder.indexOf(correctOrder.get(i));
			ScreenSquare checkPosition = position == 0 ? food0Square : position == 1 ? food1Square : food2Square;
			ScreenSquare expectedRank = i == 0 ? order0Square : i == 1 ? order1Square : order2Square;
			if(!checkPosition.overlapping(expectedRank)) {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Inner class used to represent the position/state of each of the squares on the screen
	 */
	private class ScreenSquare {
		
		private int xpos;
		private int ypos;
		private int width;
		private int height;
		private int color;
		private int startx;
		private int starty;
		private int textColor;
		
		private String text;
		
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
		 * Resets the position of the square to the starting position
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
		 * Transparently stores and resets the Paint object to its original color
		 * @param canvas Canvas object to draw to
		 * @param paint Paint object to use for the drawing
		 */
		public void draw(Canvas canvas, Paint paint) {
			int oldColor = paint.getColor();
			paint.setColor(this.color);
			canvas.drawRect(this.xpos, this.ypos, this.xpos + this.width, this.ypos + this.height, paint);
			
			Paint.Align oldAlign = paint.getTextAlign();
			float oldSize = paint.getTextSize();
			paint.setTextAlign(Paint.Align.CENTER);
			paint.setColor(textColor);
			paint.setTextSize(0.8f*this.height);
			canvas.drawText(this.text, this.xpos + this.width/2, this.ypos + 4*this.height/5, paint);
			
			paint.setTextAlign(oldAlign);
			paint.setTextSize(oldSize);
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
					this.containsPoint(other.xpos + other.width, other.ypos + other.height);
		}
		
	}
	
}
