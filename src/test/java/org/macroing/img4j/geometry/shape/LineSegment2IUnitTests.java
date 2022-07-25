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
public final class LineSegment2IUnitTests {
	public LineSegment2IUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructor() {
		final LineSegment2I lineSegment = new LineSegment2I(new Point2I(10, 10), new Point2I(20, 10));
		
		assertEquals(new Point2I(10, 10), lineSegment.getA());
		assertEquals(new Point2I(20, 10), lineSegment.getB());
		
		assertThrows(NullPointerException.class, () -> new LineSegment2I(new Point2I(10, 10), null));
		assertThrows(NullPointerException.class, () -> new LineSegment2I(null, new Point2I(20, 10)));
	}
	
	@Test
	public void testContainsPoint2I() {
		final List<LineSegment2I> lineSegments = LineSegment2I.fromPoints(new Point2I(10, 0), new Point2I(20, 0), new Point2I(30, 10), new Point2I(30, 20), new Point2I(20, 30), new Point2I(10, 30), new Point2I(0, 20), new Point2I(0, 10));
		
		for(final LineSegment2I lineSegment : lineSegments) {
			assertTrue(lineSegment.contains(lineSegment.getA()));
			assertTrue(lineSegment.contains(Point2I.midpoint(lineSegment.getA(), lineSegment.getB())));
			assertTrue(lineSegment.contains(lineSegment.getB()));
			
			assertFalse(lineSegment.contains(new Point2I(lineSegment.getA().x + 100, lineSegment.getA().y)));
			assertFalse(lineSegment.contains(new Point2I(lineSegment.getA().x - 100, lineSegment.getA().y)));
			
			assertFalse(lineSegment.contains(new Point2I(lineSegment.getA().x, lineSegment.getA().y + 100)));
			assertFalse(lineSegment.contains(new Point2I(lineSegment.getA().x, lineSegment.getA().y - 100)));
		}
		
		assertThrows(NullPointerException.class, () -> new LineSegment2I(new Point2I(10, 10), new Point2I(20, 10)).contains(null));
	}
	
	@Test
	public void testContainsPoint2IBoolean() {
		final List<LineSegment2I> lineSegments = LineSegment2I.fromPoints(new Point2I(10, 0), new Point2I(20, 0), new Point2I(30, 10), new Point2I(30, 20), new Point2I(20, 30), new Point2I(10, 30), new Point2I(0, 20), new Point2I(0, 10));
		
		for(final LineSegment2I lineSegment : lineSegments) {
			assertTrue(lineSegment.contains(lineSegment.getA(), false));
			assertTrue(lineSegment.contains(Point2I.midpoint(lineSegment.getA(), lineSegment.getB()), false));
			assertTrue(lineSegment.contains(lineSegment.getB(), false));
			
			assertFalse(lineSegment.contains(new Point2I(lineSegment.getA().x + 100, lineSegment.getA().y), false));
			assertFalse(lineSegment.contains(new Point2I(lineSegment.getA().x - 100, lineSegment.getA().y), false));
			
			assertFalse(lineSegment.contains(new Point2I(lineSegment.getA().x, lineSegment.getA().y + 100), false));
			assertFalse(lineSegment.contains(new Point2I(lineSegment.getA().x, lineSegment.getA().y - 100), false));
		}
		
		assertThrows(NullPointerException.class, () -> new LineSegment2I(new Point2I(10, 10), new Point2I(20, 10)).contains(null, false));
	}
	
	@Test
	public void testEquals() {
		final LineSegment2I a = new LineSegment2I(new Point2I(10, 10), new Point2I(20, 10));
		final LineSegment2I b = new LineSegment2I(new Point2I(10, 10), new Point2I(20, 10));
		final LineSegment2I c = new LineSegment2I(new Point2I(10, 10), new Point2I(30, 30));
		final LineSegment2I d = new LineSegment2I(new Point2I(30, 30), new Point2I(20, 10));
		final LineSegment2I e = null;
		
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
	public void testFindPointsBoolean() {
		final LineSegment2I lineSegmentA = new LineSegment2I(new Point2I(10, 10), new Point2I(15, 10));
		final LineSegment2I lineSegmentB = new LineSegment2I(new Point2I(10, 10), new Point2I(10, 15));
		final LineSegment2I lineSegmentC = new LineSegment2I(new Point2I(15, 10), new Point2I(10, 10));
		final LineSegment2I lineSegmentD = new LineSegment2I(new Point2I(10, 15), new Point2I(10, 10));
		final LineSegment2I lineSegmentE = new LineSegment2I(new Point2I(10, 10), new Point2I(15, 12));
		
		final List<Point2I> lineSegmentAPoints = lineSegmentA.findPoints(false);
		final List<Point2I> lineSegmentBPoints = lineSegmentB.findPoints(false);
		final List<Point2I> lineSegmentCPoints = lineSegmentC.findPoints(false);
		final List<Point2I> lineSegmentDPoints = lineSegmentD.findPoints(false);
		final List<Point2I> lineSegmentEPoints = lineSegmentE.findPoints(false);
		
		assertNotNull(lineSegmentAPoints);
		assertNotNull(lineSegmentBPoints);
		assertNotNull(lineSegmentCPoints);
		assertNotNull(lineSegmentDPoints);
		assertNotNull(lineSegmentEPoints);
		
		assertEquals(6, lineSegmentAPoints.size());
		assertEquals(6, lineSegmentBPoints.size());
		assertEquals(6, lineSegmentCPoints.size());
		assertEquals(6, lineSegmentDPoints.size());
		assertEquals(6, lineSegmentEPoints.size());
		
		assertEquals(new Point2I(10, 10), lineSegmentAPoints.get(0));
		assertEquals(new Point2I(11, 10), lineSegmentAPoints.get(1));
		assertEquals(new Point2I(12, 10), lineSegmentAPoints.get(2));
		assertEquals(new Point2I(13, 10), lineSegmentAPoints.get(3));
		assertEquals(new Point2I(14, 10), lineSegmentAPoints.get(4));
		assertEquals(new Point2I(15, 10), lineSegmentAPoints.get(5));
		
		assertEquals(new Point2I(10, 10), lineSegmentBPoints.get(0));
		assertEquals(new Point2I(10, 11), lineSegmentBPoints.get(1));
		assertEquals(new Point2I(10, 12), lineSegmentBPoints.get(2));
		assertEquals(new Point2I(10, 13), lineSegmentBPoints.get(3));
		assertEquals(new Point2I(10, 14), lineSegmentBPoints.get(4));
		assertEquals(new Point2I(10, 15), lineSegmentBPoints.get(5));
		
		assertEquals(new Point2I(15, 10), lineSegmentCPoints.get(0));
		assertEquals(new Point2I(14, 10), lineSegmentCPoints.get(1));
		assertEquals(new Point2I(13, 10), lineSegmentCPoints.get(2));
		assertEquals(new Point2I(12, 10), lineSegmentCPoints.get(3));
		assertEquals(new Point2I(11, 10), lineSegmentCPoints.get(4));
		assertEquals(new Point2I(10, 10), lineSegmentCPoints.get(5));
		
		assertEquals(new Point2I(10, 15), lineSegmentDPoints.get(0));
		assertEquals(new Point2I(10, 14), lineSegmentDPoints.get(1));
		assertEquals(new Point2I(10, 13), lineSegmentDPoints.get(2));
		assertEquals(new Point2I(10, 12), lineSegmentDPoints.get(3));
		assertEquals(new Point2I(10, 11), lineSegmentDPoints.get(4));
		assertEquals(new Point2I(10, 10), lineSegmentDPoints.get(5));
		
		assertEquals(new Point2I(10, 10), lineSegmentEPoints.get(0));
		assertEquals(new Point2I(11, 10), lineSegmentEPoints.get(1));
		assertEquals(new Point2I(12, 11), lineSegmentEPoints.get(2));
		assertEquals(new Point2I(13, 11), lineSegmentEPoints.get(3));
		assertEquals(new Point2I(14, 12), lineSegmentEPoints.get(4));
		assertEquals(new Point2I(15, 12), lineSegmentEPoints.get(5));
	}
	
	@Test
	public void testFindPointsOfComplementShape2IBoolean() {
		final LineSegment2I a = new LineSegment2I(new Point2I(10, 10), new Point2I(20, 10));
		final LineSegment2I b = new LineSegment2I(new Point2I(10, 10), new Point2I(21, 10));
		
		final List<Point2I> points = a.findPointsOfComplement(b, false);
		
		assertNotNull(points);
		
		assertEquals(1, points.size());
		
		assertEquals(new Point2I(21, 10), points.get(0));
		
		assertThrows(NullPointerException.class, () -> a.findPointsOfComplement(null, false));
	}
	
	@Test
	public void testFromPoints() {
		final List<LineSegment2I> lineSegments = LineSegment2I.fromPoints(new Point2I(0, 0), new Point2I(10, 0), new Point2I(10, 10), new Point2I(0, 10));
		
		assertNotNull(lineSegments);
		
		assertEquals(4, lineSegments.size());
		
		assertEquals(new LineSegment2I(new Point2I( 0,  0), new Point2I(10,  0)), lineSegments.get(0));
		assertEquals(new LineSegment2I(new Point2I(10,  0), new Point2I(10, 10)), lineSegments.get(1));
		assertEquals(new LineSegment2I(new Point2I(10, 10), new Point2I( 0, 10)), lineSegments.get(2));
		assertEquals(new LineSegment2I(new Point2I( 0, 10), new Point2I( 0,  0)), lineSegments.get(3));
		
		assertThrows(IllegalArgumentException.class, () -> LineSegment2I.fromPoints(new Point2I()));
		assertThrows(NullPointerException.class, () -> LineSegment2I.fromPoints((Point2I[])(null)));
		assertThrows(NullPointerException.class, () -> LineSegment2I.fromPoints(new Point2I[] {new Point2I(), null}));
	}
	
	@Test
	public void testGetA() {
		final LineSegment2I lineSegment = new LineSegment2I(new Point2I(10, 10), new Point2I(20, 10));
		
		assertEquals(new Point2I(10, 10), lineSegment.getA());
	}
	
	@Test
	public void testGetB() {
		final LineSegment2I lineSegment = new LineSegment2I(new Point2I(10, 10), new Point2I(20, 10));
		
		assertEquals(new Point2I(20, 10), lineSegment.getB());
	}
	
	@Test
	public void testHashCode() {
		final LineSegment2I a = new LineSegment2I(new Point2I(10, 10), new Point2I(20, 10));
		final LineSegment2I b = new LineSegment2I(new Point2I(10, 10), new Point2I(20, 10));
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testLength() {
		final LineSegment2I a = new LineSegment2I(new Point2I(0, 0), new Point2I(4, 0));
		final LineSegment2I b = new LineSegment2I(new Point2I(0, 0), new Point2I(0, 4));
		
		assertEquals(4, a.length());
		assertEquals(4, b.length());
	}
	
	@Test
	public void testLengthSquared() {
		final LineSegment2I lineSegment = new LineSegment2I(new Point2I(0, 0), new Point2I(2, 4));
		
		assertEquals(20, lineSegment.lengthSquared());
	}
	
	@Test
	public void testMax() {
		final LineSegment2I lineSegment = new LineSegment2I(new Point2I(10, 10), new Point2I(20, 10));
		
		assertEquals(new Point2I(20, 10), lineSegment.max());
	}
	
	@Test
	public void testMin() {
		final LineSegment2I lineSegment = new LineSegment2I(new Point2I(10, 10), new Point2I(20, 10));
		
		assertEquals(new Point2I(10, 10), lineSegment.min());
	}
	
	@Test
	public void testRotateBCounterclockwiseLineSegment2IDouble() {
		final LineSegment2I a = new LineSegment2I(new Point2I(0, 0), new Point2I(9, 0));
		
		final LineSegment2I b = LineSegment2I.rotateBCounterclockwise(a, +90.0D);
		final LineSegment2I c = LineSegment2I.rotateBCounterclockwise(b, +90.0D);
		final LineSegment2I d = LineSegment2I.rotateBCounterclockwise(c, +90.0D);
		final LineSegment2I e = LineSegment2I.rotateBCounterclockwise(d, +90.0D);
		
		final LineSegment2I f = LineSegment2I.rotateBCounterclockwise(e, -90.0D);
		final LineSegment2I g = LineSegment2I.rotateBCounterclockwise(f, -90.0D);
		final LineSegment2I h = LineSegment2I.rotateBCounterclockwise(g, -90.0D);
		final LineSegment2I i = LineSegment2I.rotateBCounterclockwise(h, -90.0D);
		
		assertEquals(new Point2I(+0, +0), b.getA());
		assertEquals(new Point2I(+0, +9), b.getB());
		assertEquals(new Point2I(+0, +0), c.getA());
		assertEquals(new Point2I(-9, +0), c.getB());
		assertEquals(new Point2I(+0, +0), d.getA());
		assertEquals(new Point2I(-0, -9), d.getB());
		assertEquals(new Point2I(+0, +0), e.getA());
		assertEquals(new Point2I(+9, +0), e.getB());
		
		assertEquals(new Point2I(+0, +0), f.getA());
		assertEquals(new Point2I(-0, -9), f.getB());
		assertEquals(new Point2I(+0, +0), g.getA());
		assertEquals(new Point2I(-9, +0), g.getB());
		assertEquals(new Point2I(+0, +0), h.getA());
		assertEquals(new Point2I(+0, +9), h.getB());
		assertEquals(new Point2I(+0, +0), i.getA());
		assertEquals(new Point2I(+9, +0), i.getB());
		
		assertThrows(NullPointerException.class, () -> LineSegment2I.rotateBCounterclockwise(null, 0.0D));
	}
	
	@Test
	public void testRotateBCounterclockwiseLineSegment2IDoubleBoolean() {
		final LineSegment2I a = new LineSegment2I(new Point2I(0, 0), new Point2I(9, 0));
		
		final LineSegment2I b = LineSegment2I.rotateBCounterclockwise(a, +90.0D, false);
		final LineSegment2I c = LineSegment2I.rotateBCounterclockwise(b, +90.0D, false);
		final LineSegment2I d = LineSegment2I.rotateBCounterclockwise(c, +90.0D, false);
		final LineSegment2I e = LineSegment2I.rotateBCounterclockwise(d, +90.0D, false);
		
		final LineSegment2I f = LineSegment2I.rotateBCounterclockwise(e, Math.toRadians(-90.0D), true);
		final LineSegment2I g = LineSegment2I.rotateBCounterclockwise(f, Math.toRadians(-90.0D), true);
		final LineSegment2I h = LineSegment2I.rotateBCounterclockwise(g, Math.toRadians(-90.0D), true);
		final LineSegment2I i = LineSegment2I.rotateBCounterclockwise(h, Math.toRadians(-90.0D), true);
		
		final LineSegment2I j = new LineSegment2I(new Point2I(1, 1), new Point2I(8, 1));
		
		final LineSegment2I k = LineSegment2I.rotateBCounterclockwise(j, +90.0D, false);
		final LineSegment2I l = LineSegment2I.rotateBCounterclockwise(k, +90.0D, false);
		final LineSegment2I m = LineSegment2I.rotateBCounterclockwise(l, +90.0D, false);
		final LineSegment2I n = LineSegment2I.rotateBCounterclockwise(m, +90.0D, false);
		
		assertEquals(new Point2I(+0, +0), b.getA());
		assertEquals(new Point2I(+0, +9), b.getB());
		assertEquals(new Point2I(+0, +0), c.getA());
		assertEquals(new Point2I(-9, +0), c.getB());
		assertEquals(new Point2I(+0, +0), d.getA());
		assertEquals(new Point2I(-0, -9), d.getB());
		assertEquals(new Point2I(+0, +0), e.getA());
		assertEquals(new Point2I(+9, +0), e.getB());
		
		assertEquals(new Point2I(+0, +0), f.getA());
		assertEquals(new Point2I(-0, -9), f.getB());
		assertEquals(new Point2I(+0, +0), g.getA());
		assertEquals(new Point2I(-9, +0), g.getB());
		assertEquals(new Point2I(+0, +0), h.getA());
		assertEquals(new Point2I(+0, +9), h.getB());
		assertEquals(new Point2I(+0, +0), i.getA());
		assertEquals(new Point2I(+9, +0), i.getB());
		
		assertEquals(new Point2I(+1, +1), k.getA());
		assertEquals(new Point2I(+1, +8), k.getB());
		assertEquals(new Point2I(+1, +1), l.getA());
		assertEquals(new Point2I(-6, +1), l.getB());
		assertEquals(new Point2I(+1, +1), m.getA());
		assertEquals(new Point2I(+1, -6), m.getB());
		assertEquals(new Point2I(+1, +1), n.getA());
		assertEquals(new Point2I(+8, +1), n.getB());
		
		assertThrows(NullPointerException.class, () -> LineSegment2I.rotateBCounterclockwise(null, 0.0D, false));
		
		final List<Point2I> points = j.findPoints();
		
		LineSegment2I o = j;
		LineSegment2I p = j;
		
		for(double degrees = 1.0D; degrees <= 360.0D; degrees += 1.0D) {
			o = LineSegment2I.rotateBCounterclockwise(o, 1.0D, false);
			p = LineSegment2I.rotateBCounterclockwise(j, degrees, false);
			
			assertEquals(points.size(), o.findPoints().size());
			assertEquals(points.size(), p.findPoints().size());
		}
		
		assertEquals(j, o);
		assertEquals(j, p);
	}
	
	@Test
	public void testToString() {
		final LineSegment2I lineSegment = new LineSegment2I(new Point2I(10, 10), new Point2I(20, 10));
		
		assertEquals("new LineSegment2I(new Point2I(10, 10), new Point2I(20, 10))", lineSegment.toString());
	}
}