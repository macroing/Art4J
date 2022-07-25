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
package org.macroing.img4j.test;

import java.util.List;

import org.macroing.img4j.geometry.Point2I;
import org.macroing.img4j.geometry.shape.LineSegment2I;
import org.macroing.img4j.geometry.shape.Rectangle2I;
import org.macroing.img4j.utility.Doubles;
import org.macroing.img4j.utility.Ints;

public final class RotationTest3 {
	private RotationTest3() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
//		testLineSegment2I();
		testRectangle2I();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Rectangle2I rotate(final Rectangle2I rectangle, final double angle, final boolean isAngleInRadians) {
		final Point2I a = rectangle.getA();
		final Point2I b = rectangle.getB();
		final Point2I c = rectangle.getC();
		final Point2I d = Point2I.rotateCounterclockwise(b, angle, isAngleInRadians, a);
		final Point2I e = Point2I.rotateCounterclockwise(c, angle, isAngleInRadians, b);
		
		/*
		 * Compute along the first axis:
		 */
		
		final int dABX = b.x - a.x;
		final int dABY = b.y - a.y;
		
		final int dADX = d.x - a.x;
		final int dADY = d.y - a.y;
		
		final int dABXAbs = Ints.abs(dABX);
		final int dABYAbs = Ints.abs(dABY);
		
		final int dADXAbs = Ints.abs(dADX);
		final int dADYAbs = Ints.abs(dADY);
		
		final int newDAX = dADX < 0 ? -1 : dADX > 0 ? 1 : 0;
		final int newDAY = dADY < 0 ? -1 : dADY > 0 ? 1 : 0;
		
		final int newDDX = dADXAbs > dADYAbs ? newDAX : 0;
		final int newDDY = dADXAbs > dADYAbs ? 0 : newDAY;
		
		final int oldAL = dABXAbs > dABYAbs ? dABXAbs : dABYAbs;
		final int newAL = dADXAbs > dADYAbs ? dADXAbs : dADYAbs;
		final int newAS = dADXAbs > dADYAbs ? dADYAbs : dADXAbs;
		
		int nA = newAL >> 1;
		
		int newBX = a.x;
		int newBY = a.y;
		int oldBX = a.x;
		int oldBY = a.y;
		
		for(int i = 0; i <= oldAL; i++) {
			newBX = oldBX;
			newBY = oldBY;
			
			nA += newAS;
			
			if(nA >= newAL) {
				nA -= newAL;
				
				oldBX += newDAX;
				oldBY += newDAY;
			} else {
				oldBX += newDDX;
				oldBY += newDDY;
			}
		}
		
		/*
		 * Compute along the second axis:
		 */
		
		final int dBCX = c.x - b.x;
		final int dBCY = c.y - b.y;
		
		final int dBEX = e.x - b.x;
		final int dBEY = e.y - b.y;
		
		final int dBCXAbs = Ints.abs(dBCX);
		final int dBCYAbs = Ints.abs(dBCY);
		
		final int dBEXAbs = Ints.abs(dBEX);
		final int dBEYAbs = Ints.abs(dBEY);
		
		final int newDBX = dBEX < 0 ? -1 : dBEX > 0 ? 1 : 0;
		final int newDBY = dBEY < 0 ? -1 : dBEY > 0 ? 1 : 0;
		
		final int newDEX = dBEXAbs > dBEYAbs ? newDBX : 0;
		final int newDEY = dBEXAbs > dBEYAbs ? 0 : newDBY;
		
		final int oldBL = dBCXAbs > dBCYAbs ? dBCXAbs : dBCYAbs;
		final int newBL = dBEXAbs > dBEYAbs ? dBEXAbs : dBEYAbs;
		final int newBS = dBEXAbs > dBEYAbs ? dBEYAbs : dBEXAbs;
		
		int nB = newBL >> 1;
		
		int newCX = newBX;
		int newCY = newBY;
		int oldCX = newBX;
		int oldCY = newBY;
		
		for(int i = 0; i <= oldBL; i++) {
			newCX = oldCX;
			newCY = oldCY;
			
			nB += newBS;
			
			if(nB >= newBL) {
				nB -= newBL;
				
				oldCX += newDBX;
				oldCY += newDBY;
			} else {
				oldCX += newDEX;
				oldCY += newDEY;
			}
		}
		
		final Point2I newA = a;
		final Point2I newB = new Point2I(newBX, newBY);
		final Point2I newC = new Point2I(newCX, newCY);
		
		System.out.println(newA + " " + newB + " " + newC);
		
		return new Rectangle2I(newA, newB, newC);
		
		/*
		final List<LineSegment2I> lineSegments = rectangle.getLineSegments();
		
		final LineSegment2I lineSegment00 = lineSegments.get(0);
		final LineSegment2I lineSegment10 = lineSegments.get(1);
		final LineSegment2I lineSegment20 = lineSegments.get(2);
		final LineSegment2I lineSegment30 = lineSegments.get(3);
		
		final LineSegment2I lineSegment01 = LineSegment2I.rotateBCounterclockwise(lineSegment00, angle, isAngleInRadians);
		final LineSegment2I lineSegment11 = LineSegment2I.rotateBCounterclockwise(lineSegment10, angle, isAngleInRadians);
		final LineSegment2I lineSegment21 = LineSegment2I.rotateBCounterclockwise(lineSegment20, angle, isAngleInRadians);
		final LineSegment2I lineSegment31 = LineSegment2I.rotateBCounterclockwise(lineSegment30, angle, isAngleInRadians);
		
		final Point2I a = lineSegment01.getB();
		final Point2I b = lineSegment11.getB();
		final Point2I c = lineSegment21.getB();
		final Point2I d = lineSegment31.getB();
		
		return new Rectangle2I(a, b, c, d);
		*/
	}
	
	private static void testLineSegment2I() {
		final LineSegment2I a = new LineSegment2I(new Point2I(0, 0), new Point2I(0, 100));
		final LineSegment2I b = LineSegment2I.rotateBCounterclockwise(a, 45.0D, false);
		final LineSegment2I c = LineSegment2I.rotateBCounterclockwise(b, 45.0D, false);
		final LineSegment2I d = LineSegment2I.rotateBCounterclockwise(c, 45.0D, false);
		final LineSegment2I e = LineSegment2I.rotateBCounterclockwise(d, 45.0D, false);
		final LineSegment2I f = LineSegment2I.rotateBCounterclockwise(e, 45.0D, false);
		final LineSegment2I g = LineSegment2I.rotateBCounterclockwise(f, 45.0D, false);
		final LineSegment2I h = LineSegment2I.rotateBCounterclockwise(g, 45.0D, false);
		final LineSegment2I i = LineSegment2I.rotateBCounterclockwise(h, 45.0D, false);
		
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(d);
		System.out.println(e);
		System.out.println(f);
		System.out.println(g);
		System.out.println(h);
		System.out.println(i);
		
		for(int j = 0; j < 10000; j++) {
			if(LineSegment2I.rotateBCounterclockwise(a, Math.random() * 360.0D, false).findPoints().size() != 101) {
				System.out.println("Error!");
			}
		}
	}
	
	private static void testRectangle2I() {
		final Rectangle2I a = new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200));
		final Rectangle2I b = rotate(a, 90.0D, false);
		final Rectangle2I c = rotate(b, 90.0D, false);
		final Rectangle2I d = rotate(c, 90.0D, false);
		final Rectangle2I e = rotate(d, 90.0D, false);
		final Rectangle2I f = rotate(e, 90.0D, false);
		final Rectangle2I g = rotate(f, 90.0D, false);
		final Rectangle2I h = rotate(g, 90.0D, false);
		final Rectangle2I i = rotate(h, 90.0D, false);
		
		System.out.println(a + " " + a.findPoints().size());
		System.out.println(b + " " + b.findPoints().size());
		System.out.println(c + " " + c.findPoints().size());
		System.out.println(d + " " + d.findPoints().size());
		System.out.println(e + " " + e.findPoints().size());
		System.out.println(f + " " + f.findPoints().size());
		System.out.println(g + " " + g.findPoints().size());
		System.out.println(h + " " + h.findPoints().size());
		System.out.println(i + " " + i.findPoints().size());
		
		final LineSegment2I a0 = a.getLineSegments().get(0);
		final LineSegment2I a1 = a.getLineSegments().get(1);
		final LineSegment2I a2 = a.getLineSegments().get(2);
		final LineSegment2I a3 = a.getLineSegments().get(3);
		
//		final List<Point2I> aPoints = a.findPoints();
		
		for(int j = 0; j < 10; j++) {
			final Rectangle2I r = rotate(a, Doubles.rint(Math.random() * 360.0D), false);
			
			final LineSegment2I r0 = r.getLineSegments().get(0);
			final LineSegment2I r1 = r.getLineSegments().get(1);
			final LineSegment2I r2 = r.getLineSegments().get(2);
			final LineSegment2I r3 = r.getLineSegments().get(3);
			
			if(a0.findPoints().size() != r0.findPoints().size() || a1.findPoints().size() != r1.findPoints().size() || a2.findPoints().size() != r2.findPoints().size() || a3.findPoints().size() != r3.findPoints().size()) {
				System.out.println("Error!");
			}
			
//			final List<Point2I> rPoints = r.findPoints(true);
			
//			if(rPoints.size() != aPoints.size()) {
//				System.out.println("Error! " + aPoints.size() + " " + rPoints.size() + " " + (r.getLineSegments().get(2).findPoints().size() * r.getLineSegments().get(3).findPoints().size()));
//			}
		}
		
		System.out.println(a.getLineSegments().get(0).findPoints().size() * a.getLineSegments().get(1).findPoints().size());
	}
}