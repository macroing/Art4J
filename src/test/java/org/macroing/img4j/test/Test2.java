package org.macroing.img4j.test;

import java.util.concurrent.atomic.AtomicInteger;

import org.macroing.img4j.geometry.Point2I;
import org.macroing.img4j.geometry.shape.LineSegment2I;
import org.macroing.img4j.geometry.shape.Rectangle2I;
import org.macroing.img4j.utility.Doubles;
import org.macroing.img4j.utility.Ints;

public final class Test2 {
	private static final AtomicInteger MAXIMUM_DEPTH = new AtomicInteger();
	private static final AtomicInteger MINIMUM_DEPTH = new AtomicInteger(Integer.MAX_VALUE);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Test2() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
//		doRotateLineSegment2I();
		doRotateRectangle2I();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Point2I doComputeD(final Point2I a, final Point2I b, final Point2I c) {
		final double aX = a.x;
		final double aY = a.y;
		final double bX = b.x;
		final double bY = b.y;
		final double cX = c.x;
		final double cY = c.y;
		
		final double dABX = bX - aX;
		final double dABY = bY - aY;
		
		final double distance = Doubles.abs(dABY * cX - dABX * cY + bX * aY - bY * aX) / Doubles.sqrt(dABX * dABX + dABY * dABY);
		
		final double perpendicularX = -dABY;
		final double perpendicularY = +dABX;
		final double perpendicularLength = Doubles.sqrt(perpendicularX * perpendicularX + perpendicularY * perpendicularY);
		final double perpendicularNormalizedX = perpendicularX / perpendicularLength;
		final double perpendicularNormalizedY = perpendicularY / perpendicularLength;
		
		final double dX = aX + perpendicularNormalizedX * distance;
		final double dY = aY + perpendicularNormalizedY * distance;
		
		final int x = (int)(Doubles.rint(dX));
		final int y = (int)(Doubles.rint(dY));
		
		return new Point2I(x, y);
	}
	
	private static Point2I[] doRotate(final double angle, final boolean isAngleInRadians, final Point2I center, final boolean isPreservingLength, final Point2I oldA, final Point2I oldB, final Point2I oldC) {
		/*
		 * Rotate the old A-point around the center point:
		 */
		
		final Point2I newA = Point2I.rotate(oldA, angle, isAngleInRadians, center);
		
		final int newAX = newA.x;
		final int newAY = newA.y;
		
		/*
		 * Rotate the old B-point around the old A-point and update it with regards to the new A-point:
		 */
		
		final Point2I rotatedB = Point2I.rotate(oldB, angle, isAngleInRadians, oldA);
		
		final int oldDeltaABX = oldB.x - oldA.x;
		final int oldDeltaABXAbs = Ints.abs(oldDeltaABX);
		final int oldDeltaABY = oldB.y - oldA.y;
		final int oldDeltaABYAbs = Ints.abs(oldDeltaABY);
		
		final int newDeltaABX = rotatedB.x - oldA.x;
		final int newDeltaABXAbs = Ints.abs(newDeltaABX);
		final int newDeltaABY = rotatedB.y - oldA.y;
		final int newDeltaABYAbs = Ints.abs(newDeltaABY);
		
		final int newBIncrement0X = newDeltaABX < 0 ? -1 : newDeltaABX > 0 ? 1 : 0;
		final int newBIncrement0Y = newDeltaABY < 0 ? -1 : newDeltaABY > 0 ? 1 : 0;
		
		final int newBIncrement1X = newDeltaABXAbs > newDeltaABYAbs ? newBIncrement0X : 0;
		final int newBIncrement1Y = newDeltaABXAbs > newDeltaABYAbs ? 0 : newBIncrement0Y;
		
		final int oldBL = oldDeltaABXAbs > oldDeltaABYAbs ? oldDeltaABXAbs : oldDeltaABYAbs;
		
		final int newBL = newDeltaABXAbs > newDeltaABYAbs ? newDeltaABXAbs : newDeltaABYAbs;
		final int newBS = newDeltaABXAbs > newDeltaABYAbs ? newDeltaABYAbs : newDeltaABXAbs;
		
		int nB = newBL >> 1;
		
		int newBX = newAX;
		int newBY = newAY;
		
		int oldBX = newAX;
		int oldBY = newAY;
		
		if(isPreservingLength) {
			final int oldDistance = Point2I.distance(oldA, oldB);
			
			int currentDistance = 0;
			
			while(currentDistance < oldDistance) {
				newBX = oldBX;
				newBY = oldBY;
				
				currentDistance = Point2I.distance(newA, new Point2I(newBX, newBY));
				
				nB += newBS;
				
				if(nB >= newBL) {
					nB -= newBL;
					
					oldBX += newBIncrement0X;
					oldBY += newBIncrement0Y;
				} else {
					oldBX += newBIncrement1X;
					oldBY += newBIncrement1Y;
				}
			}
		} else {
			for(int i = 0; i <= oldBL; i++) {
				newBX = oldBX;
				newBY = oldBY;
				
				nB += newBS;
				
				if(nB >= newBL) {
					nB -= newBL;
					
					oldBX += newBIncrement0X;
					oldBY += newBIncrement0Y;
				} else {
					oldBX += newBIncrement1X;
					oldBY += newBIncrement1Y;
				}
			}
		}
		
		final Point2I newB = new Point2I(newBX, newBY);
		
		/*
		 * Rotate the old C-point around the old B-point and update it with regards to the new B-point:
		 */
		
		final Point2I rotatedC = Point2I.rotate(oldC, angle, isAngleInRadians, oldB);
		
		final int oldDeltaBCX = oldC.x - oldB.x;
		final int oldDeltaBCXAbs = Ints.abs(oldDeltaBCX);
		final int oldDeltaBCY = oldC.y - oldB.y;
		final int oldDeltaBCYAbs = Ints.abs(oldDeltaBCY);
		
		final int newDeltaBCX = rotatedC.x - oldB.x;
		final int newDeltaBCXAbs = Ints.abs(newDeltaBCX);
		final int newDeltaBCY = rotatedC.y - oldB.y;
		final int newDeltaBCYAbs = Ints.abs(newDeltaBCY);
		
		final int newCIncrement0X = newDeltaBCX < 0 ? -1 : newDeltaBCX > 0 ? 1 : 0;
		final int newCIncrement0Y = newDeltaBCY < 0 ? -1 : newDeltaBCY > 0 ? 1 : 0;
		
		final int newCIncrement1X = newDeltaBCXAbs > newDeltaBCYAbs ? newCIncrement0X : 0;
		final int newCIncrement1Y = newDeltaBCXAbs > newDeltaBCYAbs ? 0 : newCIncrement0Y;
		
		final int oldCL = oldDeltaBCXAbs > oldDeltaBCYAbs ? oldDeltaBCXAbs : oldDeltaBCYAbs;
		
		final int newCL = newDeltaBCXAbs > newDeltaBCYAbs ? newDeltaBCXAbs : newDeltaBCYAbs;
		final int newCS = newDeltaBCXAbs > newDeltaBCYAbs ? newDeltaBCYAbs : newDeltaBCXAbs;
		
		int nC = newCL >> 1;
		
		int newCX = newBX;
		int newCY = newBY;
		
		int oldCX = newBX;
		int oldCY = newBY;
		
		if(isPreservingLength) {
			final int oldDistance = Point2I.distance(oldB, oldC);
			
			int currentDistance = 0;
			
			while(currentDistance < oldDistance) {
				newCX = oldCX;
				newCY = oldCY;
				
				currentDistance = Point2I.distance(newB, new Point2I(newCX, newCY));
				
				nC += newCS;
				
				if(nC >= newCL) {
					nC -= newCL;
					
					oldCX += newCIncrement0X;
					oldCY += newCIncrement0Y;
				} else {
					oldCX += newCIncrement1X;
					oldCY += newCIncrement1Y;
				}
			}
		} else {
			for(int i = 0; i <= oldCL; i++) {
				newCX = oldCX;
				newCY = oldCY;
				
				nC += newCS;
				
				if(nC >= newCL) {
					nC -= newCL;
					
					oldCX += newCIncrement0X;
					oldCY += newCIncrement0Y;
				} else {
					oldCX += newCIncrement1X;
					oldCY += newCIncrement1Y;
				}
			}
		}
		
		final Point2I newC = new Point2I(newCX, newCY);
		
		/*
		 * Construct and return a new Point2I[]:
		 */
		
		final Point2I a = newA;
		final Point2I b = newB;
		final Point2I c = newC;
		final Point2I d = doComputeD(a, b, c);
		
		return new Point2I[] {a, b, c, d};
	}
	
	public static Rectangle2I rotate(final Rectangle2I rectangle, final double angle, final boolean isAngleInRadians, final Point2I center, final boolean isPreservingLength) {
		return rotate(rectangle, angle, isAngleInRadians, center, isPreservingLength, 0);
	}
	
	public static Rectangle2I rotate(final Rectangle2I rectangle, final double angle, final boolean isAngleInRadians, final Point2I center, final boolean isPreservingLength, final int depth) {
		/*
		final Point2I[] points = doRotate(angle, isAngleInRadians, center, isPreservingLength, rectangle.getA(), rectangle.getB(), rectangle.getC());
		
		final Point2I a = points[0];
		final Point2I b = points[1];
		final Point2I c = points[2];
		final Point2I d = points[3];
		
		return new Rectangle2I(a, b, c, d);
		*/
		
		final Point2I oldA = rectangle.getA();
		final Point2I oldB = rectangle.getB();
		final Point2I oldC = rectangle.getC();
		final Point2I oldD = rectangle.getD();
		
		final int distanceOldAB = Point2I.distance(oldA, oldB);
		final int distanceOldBC = Point2I.distance(oldB, oldC);
		final int distanceOldCD = Point2I.distance(oldC, oldD);
		final int distanceOldDA = Point2I.distance(oldD, oldA);
		
		final Point2I newA = Point2I.rotate(oldA, angle, isAngleInRadians, center);
		final Point2I newB = Point2I.rotate(oldB, angle, isAngleInRadians, center);
		final Point2I newC = Point2I.rotate(oldC, angle, isAngleInRadians, center);
		final Point2I newD = Point2I.rotate(oldD, angle, isAngleInRadians, center);
		
		final int distanceNewAB = Point2I.distance(newA, newB);
		final int distanceNewBC = Point2I.distance(newB, newC);
		final int distanceNewCD = Point2I.distance(newC, newD);
		final int distanceNewDA = Point2I.distance(newD, newA);
		
		if(distanceOldAB == distanceNewAB && distanceOldBC == distanceNewBC && distanceOldCD == distanceNewCD && distanceOldDA == distanceNewDA) {
			final int currentMaximumDepth = MAXIMUM_DEPTH.updateAndGet(maximumDepth -> maximumDepth < depth ? depth : maximumDepth);
			final int currentMinimumDepth = MINIMUM_DEPTH.updateAndGet(minimumDepth -> minimumDepth > depth && depth > 0 ? depth : minimumDepth);
			
			if(currentMaximumDepth == depth || currentMinimumDepth == depth) {
				System.out.println("Depth " + currentMinimumDepth + " " + currentMaximumDepth);
			}
			
			return new Rectangle2I(newA, newB, newC, newD);
		}
		/*
		if(distanceNewAB < distanceOldAB || distanceNewBC < distanceOldBC || distanceNewCD < distanceOldCD || distanceNewDA < distanceOldDA) {
			System.out.println("AB: " + distanceOldAB + " " + distanceNewAB);
			System.out.println("BC: " + distanceOldBC + " " + distanceNewBC);
			System.out.println("CD: " + distanceOldCD + " " + distanceNewCD);
			System.out.println("DA: " + distanceOldDA + " " + distanceNewDA);
		}
		*/
		
//		return rectangle;
		
//		0.48 is the value where the least recursion occurs, at least so far. But a better alternative needs to be found.
		final double newAngleChange = isAngleInRadians ? Doubles.toRadians(0.48D) : 0.48D;
		final double newAngle = angle >= 0.0D ? angle + newAngleChange : angle - newAngleChange;
		
		return rotate(rectangle, newAngle, isAngleInRadians, center, isPreservingLength, depth + 1);
		
//		return new Rectangle2I(updatedA, updatedB, updatedC, updatedD);
	}
	
	private static void doRotateLineSegment2I() {
		final LineSegment2I a = new LineSegment2I(new Point2I(100, 100), new Point2I(200, 100));
		final LineSegment2I b = new LineSegment2I(a.getA(), Point2I.rotate(a.getB(), 45.0D, false, a.getA()));
		final LineSegment2I c = new LineSegment2I(b.getA(), Point2I.rotate(b.getB(), 45.0D, false, b.getA()));
		
		System.out.println(a + " " + a.length() + " " + a.findPoints().size());
		System.out.println(b + " " + b.length() + " " + b.findPoints().size());
		System.out.println(c + " " + c.length() + " " + c.findPoints().size());
	}
	
	private static void doRotateRectangle2I() {
		final Rectangle2I a = new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200));
		final Rectangle2I b = rotate(a, 90.0D, false, a.getA(), true);
		final Rectangle2I c = rotate(b, 90.0D, false, b.getA(), true);
		final Rectangle2I d = rotate(c, 90.0D, false, c.getA(), true);
		final Rectangle2I e = rotate(d, 90.0D, false, d.getA(), true);
		
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(d);
		System.out.println(e);
		
		Rectangle2I r = a;
		
		for(int i = 0; i < 1000; i++) {
			r = rotate(r, 90.0D, false, r.getA(), true);
			
			if(r.equals(a)) {
				System.out.println(i);
			}
		}
		
		System.out.println(r);
	}
}