package edu.upenn.cis350;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
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
	
	public RankingGameView(Context c) {
		super(c);
		setUpFoodItems();
		setUpStaticDisplay();
	}
	public RankingGameView(Context c, AttributeSet a) {
		super(c, a);
		setUpFoodItems();
		setUpStaticDisplay();
	}
	
	private void setUpFoodItems() {
		FoodGenerator foods = new FoodGenerator(this.getResources());
		correctOrder = new ArrayList<FoodItem>();
		displayOrder = new ArrayList<FoodItem>();
		FoodItem nextFood = foods.nextFood();
		displayOrder.add(nextFood);
		correctOrder.add(nextFood);
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
		Rect food0Loc = new Rect(2*dispWidth/26, dispHeight/26, 10*dispWidth/26, 6*dispHeight/26);
		Rect food1Loc = new Rect(2*dispWidth/26, 7*dispHeight/26, 10*dispWidth/26, 12*dispHeight/26);
		Rect food2Loc = new Rect(2*dispWidth/26, 13*dispHeight/26, 10*dispWidth/26, 18*dispHeight/26);
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
		canvas.drawText(displayOrder.get(0).getName(), 2*dispWidth/26, 13*dispHeight/52, paint);
		canvas.drawText(displayOrder.get(1).getName(), 2*dispWidth/26, 25*dispHeight/52, paint);
		canvas.drawText(displayOrder.get(2).getName(), 2*dispWidth/26, 37*dispHeight/52, paint);
	}
	
}
