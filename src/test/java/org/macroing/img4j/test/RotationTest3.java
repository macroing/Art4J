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

public final class RotationTest3 {
	private RotationTest3() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
//		testLineSegment2I();
		testRectangle2I();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static LineSegment2I rotate(final LineSegment2I lineSegment, final double angle, final boolean isAngleInRadians) {
		final Point2I pA = lineSegment.getA();
		final Point2I pB = lineSegment.getB();
		final Point2I pC = Point2I.rotateCounterclockwise(pB, angle, isAngleInRadians, pA);
		
		final int aX = pA.x;
		final int aY = pA.y;
		final int bX = pB.x;
		final int bY = pB.y;
		final int cX = pC.x;
		final int cY = pC.y;
		
		final int dABX = bX - aX;
		final int dABY = bY - aY;
		final int dACX = cX - aX;
		final int dACY = cY - aY;
		
		final int dABXAbs = Math.abs(dABX);
		final int dABYAbs = Math.abs(dABY);
		final int dACXAbs = Math.abs(dACX);
		final int dACYAbs = Math.abs(dACY);
		
		final int newDAX = dACX < 0 ? -1 : dACX > 0 ? 1 : 0;
		final int newDAY = dACY < 0 ? -1 : dACY > 0 ? 1 : 0;
		final int newDCX = dACXAbs > dACYAbs ? newDAX : 0;
		final int newDCY = dACXAbs > dACYAbs ? 0 : newDAY;
		
		final int oldL = dABXAbs > dABYAbs ? dABXAbs : dABYAbs;
		
		final int newL = dACXAbs > dACYAbs ? dACXAbs : dACYAbs;
		final int newS = dACXAbs > dACYAbs ? dACYAbs : dACXAbs;
		
		int n = newL >> 1;
		
		int newX = aX;
		int newY = aY;
		int oldX = aX;
		int oldY = aY;
		
		for(int i = 0; i <= oldL; i++) {
			newX = oldX;
			newY = oldY;
			
			n += newS;
			
			if(n >= newL) {
				n -= newL;
				
				oldX += newDAX;
				oldY += newDAY;
			} else {
				oldX += newDCX;
				oldY += newDCY;
			}
		}
		
		final Point2I pD = new Point2I(newX, newY);
		
		return new LineSegment2I(pA, pD);
	}
	
	private static Rectangle2I rotate(final Rectangle2I rectangle, final double angle, final boolean isAngleInRadians) {
		final List<LineSegment2I> lineSegments = rectangle.getLineSegments();
		
		final LineSegment2I lineSegment00 = lineSegments.get(0);
		final LineSegment2I lineSegment10 = lineSegments.get(1);
		final LineSegment2I lineSegment20 = lineSegments.get(2);
		final LineSegment2I lineSegment30 = lineSegments.get(3);
		
		final LineSegment2I lineSegment01 = rotate(lineSegment00, angle, isAngleInRadians);
		final LineSegment2I lineSegment11 = rotate(lineSegment10, angle, isAngleInRadians);
		final LineSegment2I lineSegment21 = rotate(lineSegment20, angle, isAngleInRadians);
		final LineSegment2I lineSegment31 = rotate(lineSegment30, angle, isAngleInRadians);
		
		final Point2I a = lineSegment01.getB();
		final Point2I b = lineSegment11.getB();
		final Point2I c = lineSegment21.getB();
		final Point2I d = lineSegment31.getB();
		
		return new Rectangle2I(a, b, c, d);
	}
	
	private static void testLineSegment2I() {
		final LineSegment2I a = new LineSegment2I(new Point2I(0, 0), new Point2I(0, 100));
		final LineSegment2I b = rotate(a, 45.0D, false);
		final LineSegment2I c = rotate(b, 45.0D, false);
		final LineSegment2I d = rotate(c, 45.0D, false);
		final LineSegment2I e = rotate(d, 45.0D, false);
		final LineSegment2I f = rotate(e, 45.0D, false);
		final LineSegment2I g = rotate(f, 45.0D, false);
		final LineSegment2I h = rotate(g, 45.0D, false);
		final LineSegment2I i = rotate(h, 45.0D, false);
		
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
			if(rotate(a, Math.random() * 360.0D, false).findPoints().size() != 101) {
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
		
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(d);
		System.out.println(e);
		System.out.println(f);
		System.out.println(g);
		System.out.println(h);
		System.out.println(i);
	}
}