package edu.upenn.cis350.test;

import junit.framework.TestCase;
import edu.upenn.cis350.ScreenSquare;

/**
 * Unit tests for ScreenSquare class
 * @author Paul M. Gurniak
 * @version 1.0
 * 
 * Note: there are obviously no unit tests for the drawing functionality.
 */
public class ScreenSquareTest extends TestCase {

	// Test the constructor
	public void testConstructor() {
		ScreenSquare simple = new ScreenSquare(0, 0, 4, 4, 0);
		
		assertEquals(2, simple.getCenterX());
		assertEquals(2, simple.getCenterY());
		assertTrue(simple.containsPoint(1, 1));
		
		simple = new ScreenSquare(simple, 0, null);
		
		// Location-wise this should duplicate the original
		assertEquals(2, simple.getCenterX());
		assertEquals(2, simple.getCenterY());
		assertTrue(simple.containsPoint(1, 1));
		assertFalse(simple.containsPoint(-1, 1));
		assertFalse(simple.containsPoint(-1, -1));
		assertFalse(simple.containsPoint(1, -1));
	}
	
	// Test simple movement
	public void testMovement() {
		ScreenSquare mobile = new ScreenSquare(0, 0, 4, 4, 0);
		assertEquals(2, mobile.getCenterX());
		assertEquals(2, mobile.getCenterY());
		assertTrue(mobile.containsPoint(1, 1));
		
		mobile.moveTo(8, 8);
		assertEquals(10, mobile.getCenterX());
		assertEquals(10, mobile.getCenterY());
		assertTrue(mobile.containsPoint(11, 11));
		assertFalse(mobile.containsPoint(7, 7));
		
		mobile.centerAt(8, 8);
		assertEquals(8, mobile.getCenterX());
		assertEquals(8, mobile.getCenterY());
		assertFalse(mobile.containsPoint(11, 11));
		assertTrue(mobile.containsPoint(7, 7));

		mobile.moveTo(mobile);
		assertEquals(8, mobile.getCenterX());
		assertEquals(8, mobile.getCenterY());
		assertFalse(mobile.containsPoint(11, 11));
		assertTrue(mobile.containsPoint(7, 7));
		
		ScreenSquare start = new ScreenSquare(0, 0, 4, 4, 0);
		mobile.moveTo(start);
		assertEquals(2, mobile.getCenterX());
		assertEquals(2, mobile.getCenterY());
		assertTrue(mobile.containsPoint(1, 1));
		assertFalse(mobile.containsPoint(11, 11));
		assertFalse(mobile.containsPoint(-1, 1));
		assertFalse(mobile.containsPoint(-1, -1));
		assertFalse(mobile.containsPoint(1, -1));
		
	}
	
	// Tests start/reset position methods
	public void testSnapping() {
		ScreenSquare start = new ScreenSquare(0, 0, 4, 4, 0);
		ScreenSquare mobile = new ScreenSquare(start, 0, null);
		
		mobile.moveTo(8, 8);
		assertEquals(10, mobile.getCenterX());
		assertEquals(10, mobile.getCenterY());
		assertTrue(mobile.containsPoint(11, 11));
		assertFalse(mobile.containsPoint(7, 7));
		
		mobile.resetPosition();
		assertEquals(2, mobile.getCenterX());
		assertEquals(2, mobile.getCenterY());
		assertTrue(mobile.containsPoint(1, 1));
		
		mobile.moveTo(8, 8);
		assertEquals(10, mobile.getCenterX());
		assertEquals(10, mobile.getCenterY());
		assertTrue(mobile.containsPoint(11, 11));
		assertFalse(mobile.containsPoint(7, 7));
		
		mobile.setStartPosition();
		mobile.resetPosition();
		assertEquals(10, mobile.getCenterX());
		assertEquals(10, mobile.getCenterY());
		assertTrue(mobile.containsPoint(11, 11));
		assertFalse(mobile.containsPoint(7, 7));
	}
	
	// Tests the overlapping functionality
	public void testOverlapping() {
		// Note: it is assumed that all squares that will have overlap checks will be the SAME size.
		ScreenSquare start = new ScreenSquare(0, 0, 4, 4, 0);
		ScreenSquare other = new ScreenSquare(1, 3, 4, 4, 0);
		
		assertTrue(start.overlapping(other));
		assertTrue(other.overlapping(start));
		
		// This should be inclusive on the boundaries
		other.moveTo(2, 4);
		assertTrue(start.overlapping(other));
		assertTrue(other.overlapping(start));
		other.moveTo(4, 4);
		assertTrue(start.overlapping(other));
		assertTrue(other.overlapping(start));
		other.moveTo(0, 2);
		assertTrue(start.overlapping(other));
		assertTrue(other.overlapping(start));
		other.moveTo(0, -2);
		assertTrue(start.overlapping(other));
		assertTrue(other.overlapping(start));
		
		other.moveTo(-8, -8);
		assertFalse(start.overlapping(other));
		assertFalse(other.overlapping(start));
		
		// Identical squares should pass
		assertTrue(start.overlapping(start));
		other.moveTo(start);
		assertTrue(start.overlapping(other));
		assertTrue(other.overlapping(start));
		
	}
	
	
}
