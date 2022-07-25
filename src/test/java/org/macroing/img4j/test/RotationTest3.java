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
	
	private static void testLineSegment2I() {
		final LineSegment2I a = new LineSegment2I(new Point2I(0, 0), new Point2I(0, 100));
		final LineSegment2I b = LineSegment2I.rotateB(a, 45.0D, false);
		final LineSegment2I c = LineSegment2I.rotateB(b, 45.0D, false);
		final LineSegment2I d = LineSegment2I.rotateB(c, 45.0D, false);
		final LineSegment2I e = LineSegment2I.rotateB(d, 45.0D, false);
		final LineSegment2I f = LineSegment2I.rotateB(e, 45.0D, false);
		final LineSegment2I g = LineSegment2I.rotateB(f, 45.0D, false);
		final LineSegment2I h = LineSegment2I.rotateB(g, 45.0D, false);
		final LineSegment2I i = LineSegment2I.rotateB(h, 45.0D, false);
		
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
			if(LineSegment2I.rotateB(a, Math.random() * 360.0D, false).findPoints().size() != 101) {
				System.out.println("Error!");
			}
		}
	}
	
	private static void testRectangle2I() {
		final Rectangle2I a = new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200));
		final Rectangle2I b = Rectangle2I.rotateBCD(a, 90.0D, false);
		final Rectangle2I c = Rectangle2I.rotateBCD(b, 90.0D, false);
		final Rectangle2I d = Rectangle2I.rotateBCD(c, 90.0D, false);
		final Rectangle2I e = Rectangle2I.rotateBCD(d, 90.0D, false);
		final Rectangle2I f = Rectangle2I.rotateBCD(e, 90.0D, false);
		final Rectangle2I g = Rectangle2I.rotateBCD(f, 90.0D, false);
		final Rectangle2I h = Rectangle2I.rotateBCD(g, 90.0D, false);
		final Rectangle2I i = Rectangle2I.rotateBCD(h, 90.0D, false);
		
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
			final Rectangle2I r = Rectangle2I.rotateBCD(a, Doubles.rint(Math.random() * 360.0D), false);
			
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