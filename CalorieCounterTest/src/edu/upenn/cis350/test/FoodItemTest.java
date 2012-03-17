package edu.upenn.cis350.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import junit.framework.TestCase;
import edu.upenn.cis350.FoodItem;

/**
 * Unit tests for the FoodItem class
 * @author Paul M. Gurniak
 * @version 1.0
 *
 */
public class FoodItemTest extends TestCase {
	
	public void testConstructor() {
		FoodItem uut = new FoodItem(null, "Test", 100, 200, 50);
		assertNull(uut.getImage());
		assertEquals("Test", uut.getName());
		assertEquals("Test", uut.toString());
		assertEquals(uut.getCalorieCount(), 150);
	}
	
	public void testGuessing() {
		FoodItem uut = new FoodItem(null, "Test", 100, 200, 50);
		
		assertEquals(FoodItem.AnswerType.WRONGLOW, uut.checkGuess(49));
		assertEquals(FoodItem.AnswerType.WRONGLOW, uut.checkGuess(1));
		
		assertEquals(FoodItem.AnswerType.WRONGHIGH, uut.checkGuess(251));
		assertEquals(FoodItem.AnswerType.WRONGHIGH, uut.checkGuess(16565));
		
		assertEquals(FoodItem.AnswerType.CLOSELOW, uut.checkGuess(50));
		assertEquals(FoodItem.AnswerType.CLOSELOW, uut.checkGuess(75));
		assertEquals(FoodItem.AnswerType.CLOSELOW, uut.checkGuess(99));
		
		assertEquals(FoodItem.AnswerType.CLOSEHIGH, uut.checkGuess(201));
		assertEquals(FoodItem.AnswerType.CLOSEHIGH, uut.checkGuess(225));
		assertEquals(FoodItem.AnswerType.CLOSEHIGH, uut.checkGuess(250));
		
		assertEquals(FoodItem.AnswerType.CORRECT, uut.checkGuess(100));
		assertEquals(FoodItem.AnswerType.CORRECT, uut.checkGuess(150));
		assertEquals(FoodItem.AnswerType.CORRECT, uut.checkGuess(200));
		
		assertEquals(FoodItem.AnswerType.INVALID, uut.checkGuess(-1));
		assertEquals(FoodItem.AnswerType.INVALID, uut.checkGuess(-16565));
		
		assertNotSame(FoodItem.AnswerType.INVALID, uut.checkGuess(0));
	}
	
	public void testHashCodeEquals() {
		FoodItem uut = new FoodItem(null, "Test", 100, 200, 50);
		FoodItem notEqual = new FoodItem(null, "NotTest", 100, 200, 50);
		FoodItem equal = new FoodItem(null, "Test", 5, 10, 5);
		
		assertEquals(uut.hashCode(), equal.hashCode());
		assertFalse(uut.hashCode() == notEqual.hashCode() && "Test".hashCode() != "NotTest".hashCode());
		
		assertTrue(uut.equals(equal));
		assertTrue(equal.equals(uut));
		
		assertFalse(uut.equals(notEqual));
		assertFalse(notEqual.equals(uut));
		
		assertFalse(uut.equals("Test"));
	}
	
	public void testComparable() {
		FoodItem uut = new FoodItem(null, "Test", 100, 200, 50);
		FoodItem bigger = new FoodItem(null, "Test1", 150, 250, 50);
		FoodItem smaller = new FoodItem(null, "Test3", 50, 150, 50);
		FoodItem same = new FoodItem(null, "Test4", 150, 150, 50);
		
		assertTrue(uut.compareTo(bigger) < 0);
		assertTrue(bigger.compareTo(uut) > 0);
		assertTrue(smaller.compareTo(bigger) < 0);
		assertTrue(bigger.compareTo(smaller) > 0);
		assertTrue(uut.compareTo(same) == 0);
		
		ArrayList<FoodItem> bigList = new ArrayList<FoodItem>();
		Random r = new Random();
		for(int i = 0; i < 1024; i++) {
			FoodItem randomItem = new FoodItem(null, "Test" + i, r.nextInt(16565), r.nextInt(16565), 50);
			bigList.add(randomItem);
		}
		Collections.sort(bigList);
		for(int i = 0; i < bigList.size()-1; i++) {
			assertTrue(bigList.get(i).compareTo(bigList.get(i+1)) <= 0);
		}
	}
	
	

}
