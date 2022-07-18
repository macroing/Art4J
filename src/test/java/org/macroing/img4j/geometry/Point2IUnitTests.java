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
package org.macroing.img4j.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public final class Point2IUnitTests {
	public Point2IUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstants() {
		assertEquals(new Point2I(Integer.MAX_VALUE, Integer.MAX_VALUE), Point2I.MAX);
		assertEquals(new Point2I(Integer.MIN_VALUE, Integer.MIN_VALUE), Point2I.MIN);
	}
	
	@Test
	public void testConstructor() {
		final Point2I point = new Point2I();
		
		assertEquals(0, point.x);
		assertEquals(0, point.y);
	}
	
	@Test
	public void testConstructorIntInt() {
		final Point2I point = new Point2I(1, 2);
		
		assertEquals(1, point.x);
		assertEquals(2, point.y);
	}
	
	@Test
	public void testDistance() {
		final Point2I a = new Point2I(0, 0);
		final Point2I b = new Point2I(9, 0);
		final Point2I c = new Point2I(0, 9);
		
		assertEquals(9, Point2I.distance(a, b));
		assertEquals(9, Point2I.distance(a, c));
		
		assertThrows(NullPointerException.class, () -> Point2I.distance(a, null));
		assertThrows(NullPointerException.class, () -> Point2I.distance(null, b));
	}
	
	@Test
	public void testDistanceSquared() {
		final Point2I a = new Point2I(0, 0);
		final Point2I b = new Point2I(9, 0);
		final Point2I c = new Point2I(0, 9);
		
		assertEquals(81, Point2I.distanceSquared(a, b));
		assertEquals(81, Point2I.distanceSquared(a, c));
		
		assertThrows(NullPointerException.class, () -> Point2I.distanceSquared(a, null));
		assertThrows(NullPointerException.class, () -> Point2I.distanceSquared(null, b));
	}
	
	@Test
	public void testEquals() {
		final Point2I a = new Point2I(0, 1);
		final Point2I b = new Point2I(0, 1);
		final Point2I c = new Point2I(0, 2);
		final Point2I d = new Point2I(2, 1);
		final Point2I e = null;
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(a, d);
		assertNotEquals(d, a);
		assertNotEquals(a, e);
		assertNotEquals(e, a);
	}
	
	@Test
	public void testHashCode() {
		final Point2I a = new Point2I(1, 2);
		final Point2I b = new Point2I(1, 2);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testMax() {
		assertEquals(new Point2I(Integer.MAX_VALUE, Integer.MAX_VALUE), Point2I.max());
	}
	
	@Test
	public void testMaxPoint2IPoint2I() {
		final Point2I a = new Point2I(1, 2);
		final Point2I b = new Point2I(3, 4);
		final Point2I c = Point2I.max(a, b);
		
		assertEquals(3, c.x);
		assertEquals(4, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.max(a, null));
		assertThrows(NullPointerException.class, () -> Point2I.max(null, b));
	}
	
	@Test
	public void testMaxPoint2IPoint2IPoint2I() {
		final Point2I a = new Point2I(1, 2);
		final Point2I b = new Point2I(3, 4);
		final Point2I c = new Point2I(5, 6);
		final Point2I d = Point2I.max(a, b, c);
		
		assertEquals(5, d.x);
		assertEquals(6, d.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.max(a, b, null));
		assertThrows(NullPointerException.class, () -> Point2I.max(a, null, c));
		assertThrows(NullPointerException.class, () -> Point2I.max(null, b, c));
	}
	
	@Test
	public void testMaxPoint2IPoint2IPoint2IPoint2I() {
		final Point2I a = new Point2I(1, 2);
		final Point2I b = new Point2I(3, 4);
		final Point2I c = new Point2I(5, 6);
		final Point2I d = new Point2I(7, 8);
		final Point2I e = Point2I.max(a, b, c, d);
		
		assertEquals(7, e.x);
		assertEquals(8, e.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.max(a, b, c, null));
		assertThrows(NullPointerException.class, () -> Point2I.max(a, b, null, d));
		assertThrows(NullPointerException.class, () -> Point2I.max(a, null, c, d));
		assertThrows(NullPointerException.class, () -> Point2I.max(null, b, c, d));
	}
	
	@Test
	public void testMidpoint() {
		final Point2I a = new Point2I(2, 4);
		final Point2I b = new Point2I(4, 8);
		final Point2I c = Point2I.midpoint(a, b);
		
		assertEquals(3, c.x);
		assertEquals(6, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.midpoint(a, null));
		assertThrows(NullPointerException.class, () -> Point2I.midpoint(null, b));
	}
	
	@Test
	public void testMin() {
		assertEquals(new Point2I(Integer.MIN_VALUE, Integer.MIN_VALUE), Point2I.min());
	}
	
	@Test
	public void testMinPoint2IPoint2I() {
		final Point2I a = new Point2I(1, 2);
		final Point2I b = new Point2I(3, 4);
		final Point2I c = Point2I.min(a, b);
		
		assertEquals(1, c.x);
		assertEquals(2, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.min(a, null));
		assertThrows(NullPointerException.class, () -> Point2I.min(null, b));
	}
	
	@Test
	public void testMinPoint2IPoint2IPoint2I() {
		final Point2I a = new Point2I(1, 2);
		final Point2I b = new Point2I(3, 4);
		final Point2I c = new Point2I(5, 6);
		final Point2I d = Point2I.min(a, b, c);
		
		assertEquals(1, d.x);
		assertEquals(2, d.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.min(a, b, null));
		assertThrows(NullPointerException.class, () -> Point2I.min(a, null, c));
		assertThrows(NullPointerException.class, () -> Point2I.min(null, b, c));
	}
	
	@Test
	public void testMinPoint2IPoint2IPoint2IPoint2I() {
		final Point2I a = new Point2I(1, 2);
		final Point2I b = new Point2I(3, 4);
		final Point2I c = new Point2I(5, 6);
		final Point2I d = new Point2I(7, 8);
		final Point2I e = Point2I.min(a, b, c, d);
		
		assertEquals(1, e.x);
		assertEquals(2, e.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.min(a, b, c, null));
		assertThrows(NullPointerException.class, () -> Point2I.min(a, b, null, d));
		assertThrows(NullPointerException.class, () -> Point2I.min(a, null, c, d));
		assertThrows(NullPointerException.class, () -> Point2I.min(null, b, c, d));
	}
	
	@Test
	public void testToString() {
		final Point2I point = new Point2I(1, 2);
		
		assertEquals("new Point2I(1, 2)", point.toString());
	}
	
	@Test
	public void testToStringPoint2IArray() {
		final Point2I a = new Point2I(1, 1);
		final Point2I b = new Point2I(2, 2);
		final Point2I c = new Point2I(3, 3);
		
		assertEquals("new Point2I[] {new Point2I(1, 1), new Point2I(2, 2), new Point2I(3, 3)}", Point2I.toString(a, b, c));
		
		assertThrows(NullPointerException.class, () -> Point2I.toString((Point2I[])(null)));
		assertThrows(NullPointerException.class, () -> Point2I.toString(a, b, c, null));
		assertThrows(NullPointerException.class, () -> Point2I.toString(new Point2I[] {a, b, c, null}));
	}
}