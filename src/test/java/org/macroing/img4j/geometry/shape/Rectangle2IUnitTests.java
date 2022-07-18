/**
 * Copyright 2022 J&#246;rgen Lundgren
 * 
 * This file is part of org.macroing.img4j.
 * 
 * org.macroing.img4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * org.macroing.img4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with org.macroing.img4j. If not, see <http://www.gnu.org/licenses/>.
 */
package org.macroing.img4j.geometry.shape;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.macroing.img4j.geometry.Point2I;

@SuppressWarnings("static-method")
public final class Rectangle2IUnitTests {
	public Rectangle2IUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructorCircle2I() {
		final Rectangle2I rectangle = new Rectangle2I(new Circle2I(new Point2I(20, 20), 10));
		
		final List<LineSegment2I> lineSegments = rectangle.getLineSegments();
		
		assertEquals(new Point2I(10, 10), rectangle.getA());
		assertEquals(new Point2I(30, 10), rectangle.getB());
		assertEquals(new Point2I(30, 30), rectangle.getC());
		assertEquals(new Point2I(10, 30), rectangle.getD());
		
		assertNotNull(lineSegments);
		
		assertTrue(lineSegments.size() == 4);
		
		assertNotNull(lineSegments.get(0));
		assertNotNull(lineSegments.get(1));
		assertNotNull(lineSegments.get(2));
		assertNotNull(lineSegments.get(3));
		
		assertEquals(new Point2I(10, 10), lineSegments.get(0).getA());
		assertEquals(new Point2I(30, 10), lineSegments.get(0).getB());
		
		assertEquals(new Point2I(30, 10), lineSegments.get(1).getA());
		assertEquals(new Point2I(30, 30), lineSegments.get(1).getB());
		
		assertEquals(new Point2I(30, 30), lineSegments.get(2).getA());
		assertEquals(new Point2I(10, 30), lineSegments.get(2).getB());
		
		assertEquals(new Point2I(10, 30), lineSegments.get(3).getA());
		assertEquals(new Point2I(10, 10), lineSegments.get(3).getB());
		
		assertThrows(NullPointerException.class, () -> new Rectangle2I((Circle2I)(null)));
	}
	
	@Test
	public void testConstructorPoint2IPoint2I() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(30, 30), new Point2I(10, 10));
		
		final List<LineSegment2I> lineSegments = rectangle.getLineSegments();
		
		assertEquals(new Point2I(10, 10), rectangle.getA());
		assertEquals(new Point2I(30, 10), rectangle.getB());
		assertEquals(new Point2I(30, 30), rectangle.getC());
		assertEquals(new Point2I(10, 30), rectangle.getD());
		
		assertNotNull(lineSegments);
		
		assertTrue(lineSegments.size() == 4);
		
		assertNotNull(lineSegments.get(0));
		assertNotNull(lineSegments.get(1));
		assertNotNull(lineSegments.get(2));
		assertNotNull(lineSegments.get(3));
		
		assertEquals(new Point2I(10, 10), lineSegments.get(0).getA());
		assertEquals(new Point2I(30, 10), lineSegments.get(0).getB());
		
		assertEquals(new Point2I(30, 10), lineSegments.get(1).getA());
		assertEquals(new Point2I(30, 30), lineSegments.get(1).getB());
		
		assertEquals(new Point2I(30, 30), lineSegments.get(2).getA());
		assertEquals(new Point2I(10, 30), lineSegments.get(2).getB());
		
		assertEquals(new Point2I(10, 30), lineSegments.get(3).getA());
		assertEquals(new Point2I(10, 10), lineSegments.get(3).getB());
		
		assertThrows(NullPointerException.class, () -> new Rectangle2I(new Point2I(), null));
		assertThrows(NullPointerException.class, () -> new Rectangle2I(null, new Point2I()));
	}
	
	@Test
	public void testConstructorPoint2IPoint2IPoint2IPoint2I() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(10, 30), new Point2I(30, 30), new Point2I(30, 10));
		
		final List<LineSegment2I> lineSegments = rectangle.getLineSegments();
		
		assertEquals(new Point2I(10, 10), rectangle.getA());
		assertEquals(new Point2I(10, 30), rectangle.getB());
		assertEquals(new Point2I(30, 30), rectangle.getC());
		assertEquals(new Point2I(30, 10), rectangle.getD());
		
		assertNotNull(lineSegments);
		
		assertTrue(lineSegments.size() == 4);
		
		assertNotNull(lineSegments.get(0));
		assertNotNull(lineSegments.get(1));
		assertNotNull(lineSegments.get(2));
		assertNotNull(lineSegments.get(3));
		
		assertEquals(new Point2I(10, 10), lineSegments.get(0).getA());
		assertEquals(new Point2I(10, 30), lineSegments.get(0).getB());
		
		assertEquals(new Point2I(10, 30), lineSegments.get(1).getA());
		assertEquals(new Point2I(30, 30), lineSegments.get(1).getB());
		
		assertEquals(new Point2I(30, 30), lineSegments.get(2).getA());
		assertEquals(new Point2I(30, 10), lineSegments.get(2).getB());
		
		assertEquals(new Point2I(30, 10), lineSegments.get(3).getA());
		assertEquals(new Point2I(10, 10), lineSegments.get(3).getB());
		
		assertThrows(NullPointerException.class, () -> new Rectangle2I(new Point2I(), new Point2I(), new Point2I(), null));
		assertThrows(NullPointerException.class, () -> new Rectangle2I(new Point2I(), new Point2I(), null, new Point2I()));
		assertThrows(NullPointerException.class, () -> new Rectangle2I(new Point2I(), null, new Point2I(), new Point2I()));
		assertThrows(NullPointerException.class, () -> new Rectangle2I(null, new Point2I(), new Point2I(), new Point2I()));
		
		assertThrows(IllegalArgumentException.class, () -> new Rectangle2I(new Point2I(10, 10), new Point2I(10, 20), new Point2I(10, 40), new Point2I(10, 80)));
		assertThrows(IllegalArgumentException.class, () -> new Rectangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(40, 10), new Point2I(80, 10)));
	}
	
	@Test
	public void testContainsPoint2I() {
		final Rectangle2I rectangle2I = new Rectangle2I(new Point2I(20, 20), new Point2I(30, 30));
		
		assertTrue(rectangle2I.contains(new Point2I(20, 20)));
		assertTrue(rectangle2I.contains(new Point2I(25, 20)));
		assertTrue(rectangle2I.contains(new Point2I(30, 20)));
		assertTrue(rectangle2I.contains(new Point2I(30, 25)));
		assertTrue(rectangle2I.contains(new Point2I(30, 30)));
		assertTrue(rectangle2I.contains(new Point2I(25, 30)));
		assertTrue(rectangle2I.contains(new Point2I(20, 30)));
		assertTrue(rectangle2I.contains(new Point2I(20, 25)));
		assertTrue(rectangle2I.contains(new Point2I(25, 25)));
		
		assertFalse(rectangle2I.contains(new Point2I(19, 20)));
		assertFalse(rectangle2I.contains(new Point2I(31, 20)));
		assertFalse(rectangle2I.contains(new Point2I(19, 30)));
		assertFalse(rectangle2I.contains(new Point2I(31, 30)));
		assertFalse(rectangle2I.contains(new Point2I(20, 19)));
		assertFalse(rectangle2I.contains(new Point2I(20, 31)));
		assertFalse(rectangle2I.contains(new Point2I(30, 19)));
		assertFalse(rectangle2I.contains(new Point2I(30, 31)));
		
		assertThrows(NullPointerException.class, () -> rectangle2I.contains(null));
	}
	
	@Test
	public void testContainsPoint2IBoolean() {
		final Rectangle2I rectangle2I = new Rectangle2I(new Point2I(20, 20), new Point2I(30, 30));
		
		assertTrue(rectangle2I.contains(new Point2I(20, 20), false));
		assertTrue(rectangle2I.contains(new Point2I(25, 20), false));
		assertTrue(rectangle2I.contains(new Point2I(30, 20), false));
		assertTrue(rectangle2I.contains(new Point2I(30, 25), false));
		assertTrue(rectangle2I.contains(new Point2I(30, 30), false));
		assertTrue(rectangle2I.contains(new Point2I(25, 30), false));
		assertTrue(rectangle2I.contains(new Point2I(20, 30), false));
		assertTrue(rectangle2I.contains(new Point2I(20, 25), false));
		assertTrue(rectangle2I.contains(new Point2I(25, 25), false));
		
		assertTrue(rectangle2I.contains(new Point2I(20, 20), true));
		assertTrue(rectangle2I.contains(new Point2I(25, 20), true));
		assertTrue(rectangle2I.contains(new Point2I(30, 20), true));
		assertTrue(rectangle2I.contains(new Point2I(30, 25), true));
		assertTrue(rectangle2I.contains(new Point2I(30, 30), true));
		assertTrue(rectangle2I.contains(new Point2I(25, 30), true));
		assertTrue(rectangle2I.contains(new Point2I(20, 30), true));
		assertTrue(rectangle2I.contains(new Point2I(20, 25), true));
		
		assertFalse(rectangle2I.contains(new Point2I(19, 20), false));
		assertFalse(rectangle2I.contains(new Point2I(31, 20), false));
		assertFalse(rectangle2I.contains(new Point2I(19, 30), false));
		assertFalse(rectangle2I.contains(new Point2I(31, 30), false));
		assertFalse(rectangle2I.contains(new Point2I(20, 19), false));
		assertFalse(rectangle2I.contains(new Point2I(20, 31), false));
		assertFalse(rectangle2I.contains(new Point2I(30, 19), false));
		assertFalse(rectangle2I.contains(new Point2I(30, 31), false));
		
		assertFalse(rectangle2I.contains(new Point2I(19, 20), true));
		assertFalse(rectangle2I.contains(new Point2I(31, 20), true));
		assertFalse(rectangle2I.contains(new Point2I(19, 30), true));
		assertFalse(rectangle2I.contains(new Point2I(31, 30), true));
		assertFalse(rectangle2I.contains(new Point2I(20, 19), true));
		assertFalse(rectangle2I.contains(new Point2I(20, 31), true));
		assertFalse(rectangle2I.contains(new Point2I(30, 19), true));
		assertFalse(rectangle2I.contains(new Point2I(30, 31), true));
		
		assertFalse(rectangle2I.contains(new Point2I(25, 25), true));
		
		assertThrows(NullPointerException.class, () -> rectangle2I.contains(null, false));
	}
	
	@Test
	public void testEquals() {
		final Rectangle2I a = new Rectangle2I(new Point2I(20, 20), new Point2I(30, 20), new Point2I(30, 30), new Point2I(20, 30));
		final Rectangle2I b = new Rectangle2I(new Point2I(20, 20), new Point2I(30, 20), new Point2I(30, 30), new Point2I(20, 30));
		final Rectangle2I c = new Rectangle2I(new Point2I(20, 20), new Point2I(40, 20), new Point2I(40, 30), new Point2I(20, 30));
		final Rectangle2I d = new Rectangle2I(new Point2I(10, 20), new Point2I(30, 20), new Point2I(30, 30), new Point2I(10, 30));
		final Rectangle2I e = new Rectangle2I(new Point2I(20, 20), new Point2I(30, 20), new Point2I(30, 40), new Point2I(20, 40));
		final Rectangle2I f = null;
		
		assertEquals(a, a);
		
		assertEquals(a, b);
		assertEquals(b, a);
		
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		
		assertNotEquals(a, d);
		assertNotEquals(d, a);
		
		assertNotEquals(a, e);
		assertNotEquals(e, a);
		
		assertNotEquals(a, f);
		assertNotEquals(f, a);
	}
	
	@Test
	public void testFindPointsBoolean() {
		final Rectangle2I rectangle2I = new Rectangle2I(new Point2I(0, 0), new Point2I(2, 0), new Point2I(2, 2), new Point2I(0, 2));
		
		final List<Point2I> pointsA = rectangle2I.findPoints(false);
		final List<Point2I> pointsB = rectangle2I.findPoints(true);
		
		assertNotNull(pointsA);
		assertNotNull(pointsB);
		
		assertEquals(9, pointsA.size());
		assertEquals(8, pointsB.size());
		
		assertEquals(new Point2I(0, 0), pointsA.get(0));
		assertEquals(new Point2I(1, 0), pointsA.get(1));
		assertEquals(new Point2I(2, 0), pointsA.get(2));
		assertEquals(new Point2I(0, 1), pointsA.get(3));
		assertEquals(new Point2I(1, 1), pointsA.get(4));
		assertEquals(new Point2I(2, 1), pointsA.get(5));
		assertEquals(new Point2I(0, 2), pointsA.get(6));
		assertEquals(new Point2I(1, 2), pointsA.get(7));
		assertEquals(new Point2I(2, 2), pointsA.get(8));
		
		assertEquals(new Point2I(0, 0), pointsB.get(0));
		assertEquals(new Point2I(1, 0), pointsB.get(1));
		assertEquals(new Point2I(2, 0), pointsB.get(2));
		assertEquals(new Point2I(0, 1), pointsB.get(3));
		assertEquals(new Point2I(2, 1), pointsB.get(4));
		assertEquals(new Point2I(0, 2), pointsB.get(5));
		assertEquals(new Point2I(1, 2), pointsB.get(6));
		assertEquals(new Point2I(2, 2), pointsB.get(7));
	}
	
	@Test
	public void testFromPoints() {
		final Rectangle2I a = Rectangle2I.fromPoints(new Point2I(10, 10), new Point2I(15, 15), new Point2I(20, 20), new Point2I(25, 25), new Point2I(30, 30));
		final Rectangle2I b = new Rectangle2I(new Point2I(10, 10), new Point2I(30, 30));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> Rectangle2I.fromPoints((Point2I[])(null)));
		assertThrows(NullPointerException.class, () -> Rectangle2I.fromPoints(new Point2I(), null));
		
		assertThrows(IllegalArgumentException.class, () -> Rectangle2I.fromPoints());
	}
	
	@Test
	public void testGetA() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(10, 30), new Point2I(30, 30), new Point2I(30, 10));
		
		assertEquals(new Point2I(10, 10), rectangle.getA());
	}
	
	@Test
	public void testGetB() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(10, 30), new Point2I(30, 30), new Point2I(30, 10));
		
		assertEquals(new Point2I(10, 30), rectangle.getB());
	}
	
	@Test
	public void testGetC() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(10, 30), new Point2I(30, 30), new Point2I(30, 10));
		
		assertEquals(new Point2I(30, 30), rectangle.getC());
	}
	
	@Test
	public void testGetD() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(10, 30), new Point2I(30, 30), new Point2I(30, 10));
		
		assertEquals(new Point2I(30, 10), rectangle.getD());
	}
	
	@Test
	public void testGetLineSegments() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(10, 30), new Point2I(30, 30), new Point2I(30, 10));
		
		final List<LineSegment2I> lineSegments = rectangle.getLineSegments();
		
		assertNotNull(lineSegments);
		
		assertTrue(lineSegments.size() == 4);
		
		assertNotNull(lineSegments.get(0));
		assertNotNull(lineSegments.get(1));
		assertNotNull(lineSegments.get(2));
		assertNotNull(lineSegments.get(3));
		
		assertEquals(new Point2I(10, 10), lineSegments.get(0).getA());
		assertEquals(new Point2I(10, 30), lineSegments.get(0).getB());
		
		assertEquals(new Point2I(10, 30), lineSegments.get(1).getA());
		assertEquals(new Point2I(30, 30), lineSegments.get(1).getB());
		
		assertEquals(new Point2I(30, 30), lineSegments.get(2).getA());
		assertEquals(new Point2I(30, 10), lineSegments.get(2).getB());
		
		assertEquals(new Point2I(30, 10), lineSegments.get(3).getA());
		assertEquals(new Point2I(10, 10), lineSegments.get(3).getB());
	}
	
	@Test
	public void testHashCode() {
		final Rectangle2I a = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 20));
		final Rectangle2I b = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 20));
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIsAxisAligned() {
		final Rectangle2I a = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		final Rectangle2I b = new Rectangle2I(new Point2I(20, 20), new Point2I(25, 25), new Point2I(20, 30), new Point2I(15, 25));
		
		assertTrue(a.isAxisAligned());
		
		assertFalse(b.isAxisAligned());
	}
	
	@Test
	public void testIsRotated() {
		final Rectangle2I a = new Rectangle2I(new Point2I(20, 20), new Point2I(25, 25), new Point2I(20, 30), new Point2I(15, 25));
		final Rectangle2I b = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		
		assertTrue(a.isRotated());
		
		assertFalse(b.isRotated());
	}
	
	@Test
	public void testMax() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 20));
		
		assertEquals(new Point2I(20, 20), rectangle.max());
	}
	
	@Test
	public void testMin() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 20));
		
		assertEquals(new Point2I(10, 10), rectangle.min());
	}
	
	@Test
	public void testToString() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 20));
		
		assertEquals("new Rectangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20))", rectangle.toString());
	}
	
	@Test
	public void testUnion() {
		final Rectangle2I a = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 20));
		final Rectangle2I b = new Rectangle2I(new Point2I(20, 20), new Point2I(30, 30));
		final Rectangle2I c = new Rectangle2I(new Point2I(10, 10), new Point2I(30, 30));
		final Rectangle2I d = Rectangle2I.union(a, b);
		
		assertEquals(c, d);
		
		assertThrows(NullPointerException.class, () -> Rectangle2I.union(a, null));
		assertThrows(NullPointerException.class, () -> Rectangle2I.union(null, b));
	}
}