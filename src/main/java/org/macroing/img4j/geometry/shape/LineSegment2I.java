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

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.lang.reflect.Field;//TODO: Add Unit Tests!
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.img4j.geometry.Point2I;
import org.macroing.img4j.geometry.Shape2I;
import org.macroing.img4j.geometry.Vector2I;
import org.macroing.img4j.utility.Doubles;
import org.macroing.img4j.utility.ParameterArguments;

/**
 * A {@code LineSegment2I} is an implementation of {@link Shape2I} that represents a line segment.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class LineSegment2I implements Shape2I {
	private final Point2I a;
	private final Point2I b;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code LineSegment2I} instance given two {@link Point2I} instances, {@code a} and {@code b}.
	 * <p>
	 * If either {@code a} or {@code b} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2I} instance
	 * @param b a {@code Point2I} instance
	 * @throws NullPointerException thrown if, and only if, either {@code a} or {@code b} are {@code null}
	 */
	public LineSegment2I(final Point2I a, final Point2I b) {
		this.a = Objects.requireNonNull(a, "a == null");
		this.b = Objects.requireNonNull(b, "b == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with {@link Point2I} instances contained in this {@code LineSegment2I} instance.
	 * 
	 * @param isIncludingBorderOnly {@code true} if, and only if, this method should only include {@code Point2I} instances on the border of this {@code LineSegment2I} instance, {@code false} otherwise
	 * @return a {@code List} with {@code Point2I} instances contained in this {@code LineSegment2I} instance
	 */
	@Override
	public List<Point2I> findPoints(final boolean isIncludingBorderOnly) {
		final int aX = this.a.x;
		final int aY = this.a.y;
		final int bX = this.b.x;
		final int bY = this.b.y;
		
		final int w = bX - aX;
		final int h = bY - aY;
		
		final int wAbs = Math.abs(w);
		final int hAbs = Math.abs(h);
		
		final int dAX = w < 0 ? -1 : w > 0 ? 1 : 0;
		final int dAY = h < 0 ? -1 : h > 0 ? 1 : 0;
		final int dBX = wAbs > hAbs ? dAX : 0;
		final int dBY = wAbs > hAbs ? 0 : dAY;
		
		final int l = wAbs > hAbs ? wAbs : hAbs;
		final int s = wAbs > hAbs ? hAbs : wAbs;
		
		final List<Point2I> points = new ArrayList<>(l + 1);
		
		int n = l >> 1;
		
		int x = aX;
		int y = aY;
		
		for(int i = 0; i <= l; i++) {
			points.add(new Point2I(x, y));
			
			n += s;
			
			if(n >= l) {
				n -= l;
				
				x += dAX;
				y += dAY;
			} else {
				x += dBX;
				y += dBY;
			}
		}
		
		return points;
	}
	
	/**
	 * Returns the {@link Point2I} instance denoted by {@code A}.
	 * 
	 * @return the {@code Point2I} instance denoted by {@code A}
	 */
	public Point2I getA() {
		return this.a;
	}
	
	/**
	 * Returns the {@link Point2I} instance denoted by {@code B}.
	 * 
	 * @return the {@code Point2I} instance denoted by {@code B}
	 */
	public Point2I getB() {
		return this.b;
	}
	
	/**
	 * Returns a {@link Point2I} with the largest component values needed to contain this {@code LineSegment2I} instance.
	 * 
	 * @return a {@code Point2I} with the largest component values needed to contain this {@code LineSegment2I} instance
	 */
	@Override
	public Point2I max() {
		return Point2I.max(this.a, this.b);
	}
	
	/**
	 * Returns a {@link Point2I} with the smallest component values needed to contain this {@code LineSegment2I} instance.
	 * 
	 * @return a {@code Point2I} with the smallest component values needed to contain this {@code LineSegment2I} instance
	 */
	@Override
	public Point2I min() {
		return Point2I.min(this.a, this.b);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code LineSegment2I} instance.
	 * 
	 * @return a {@code String} representation of this {@code LineSegment2I} instance
	 */
	@Override
	public String toString() {
		return String.format("new LineSegment2I(%s, %s)", this.a, this.b);
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code point} is contained in this {@code LineSegment2I} instance, {@code false} otherwise.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@link Point2I} instance
	 * @param isIncludingBorderOnly {@code true} if, and only if, this method should only include {@code Point2I} instances on the border of this {@code LineSegment2I} instance, {@code false} otherwise
	 * @return {@code true} if, and only if, {@code point} is contained in this {@code LineSegment2I} instance, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	@Override
	public boolean contains(final Point2I point, final boolean isIncludingBorderOnly) {
		final int aX = this.a.x;
		final int aY = this.a.y;
		final int bX = this.b.x;
		final int bY = this.b.y;
		final int pX = point.x;
		final int pY = point.y;
		
		final int dAPX = pX - aX;
		final int dAPY = pY - aY;
		final int dABX = bX - aX;
		final int dABY = bY - aY;
		
		final int crossProduct = dAPX * dABY - dAPY * dABX;
		
		if(crossProduct != 0) {
			return false;
		} else if(Math.abs(dABX) >= Math.abs(dABY)) {
			return dABX > 0 ? aX <= pX && pX <= bX : bX <= pX && pX <= aX;
		} else {
			return dABY > 0 ? aY <= pY && pY <= bY : bY <= pY && pY <= aY;
		}
	}
	
	/**
	 * Compares {@code object} to this {@code LineSegment2I} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code LineSegment2I}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code LineSegment2I} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code LineSegment2I}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof LineSegment2I)) {
			return false;
		} else if(!Objects.equals(this.a, LineSegment2I.class.cast(object).a)) {
			return false;
		} else if(!Objects.equals(this.b, LineSegment2I.class.cast(object).b)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code LineSegment2I} instance.
	 * 
	 * @return a hash code for this {@code LineSegment2I} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.a, this.b);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} of {@code LineSegment2I} instances that are connecting the {@link Point2I} instances in {@code points}.
	 * <p>
	 * If either {@code points} or an element in {@code points} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code points.length} is less than {@code 2}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param points a {@code Point2I[]} instance
	 * @return a {@code List} of {@code LineSegment2I} instances that are connecting the {@code Point2I} instances in {@code points}
	 * @throws IllegalArgumentException thrown if, and only if, {@code points.length} is less than {@code 2}
	 * @throws NullPointerException thrown if, and only if, either {@code points} or an element in {@code points} are {@code null}
	 */
	public static List<LineSegment2I> fromPoints(final Point2I... points) {
		ParameterArguments.requireNonNullArray(points, "points");
		ParameterArguments.requireRange(points.length, 2, Integer.MAX_VALUE, "points.length");
		
		final List<LineSegment2I> lineSegments = new ArrayList<>(points.length);
		
		for(int i = 0, j = 1; i < points.length; i++, j = (j + 1) % points.length) {
			final Point2I pointI = points[i];
			final Point2I pointJ = points[j];
			
			final LineSegment2I lineSegment = new LineSegment2I(pointI, pointJ);
			
			lineSegments.add(lineSegment);
		}
		
		return lineSegments;
	}
	
//	TODO: Add Javadocs!
//	TODO: Add Unit Tests!
	public static LineSegment2I rotate(final LineSegment2I lineSegment, final double angle, final boolean isAngleInRadians) {
		final double a = isAngleInRadians ? angle : Doubles.toRadians(angle);
		final double aCos = Doubles.cos(a);
		final double aSin = Doubles.sin(a);
		
		final Point2I pA = lineSegment.getA();
		final Point2I pB = lineSegment.getB();
		
		final double pAX = pA.x;
		final double pAY = pA.y;
		final double pBX = pB.x;
		final double pBY = pB.y;
		final double pCX = pAX + (pBX - pAX) * aCos + (pBY - pAY) * aSin;
		final double pCY = pAY - (pBX - pAX) * aSin + (pBY - pAY) * aCos;
		
		final double vABX = pBX - pAX;
		final double vABY = pBY - pAY;
		final double vABLength = Doubles.sqrt(vABX * vABX + vABY * vABY);
		
		final double vACX = pCX - pAX;
		final double vACY = pCY - pAY;
		final double vACLength = Doubles.sqrt(vACX * vACX + vACY * vACY);
		final double vACNX = vACX / vACLength;
		final double vACNY = vACY / vACLength;
		
		final double pDX = pAX + vACNX * vABLength;
		final double pDY = pAY + vACNY * vABLength;
		
		final Point2I pC = new Point2I((int)(pDX - 1.0D), (int)(pDY - 1.0D));
		final Point2I pD = new Point2I((int)(pDX - 0.0D), (int)(pDY - 1.0D));
		final Point2I pE = new Point2I((int)(pDX + 1.0D), (int)(pDY - 1.0D));
		final Point2I pF = new Point2I((int)(pDX - 1.0D), (int)(pDY + 0.0D));
		final Point2I pG = new Point2I((int)(pDX - 0.0D), (int)(pDY + 0.0D));
		final Point2I pH = new Point2I((int)(pDX + 1.0D), (int)(pDY + 0.0D));
		final Point2I pI = new Point2I((int)(pDX - 1.0D), (int)(pDY + 1.0D));
		final Point2I pJ = new Point2I((int)(pDX - 0.0D), (int)(pDY + 1.0D));
		final Point2I pK = new Point2I((int)(pDX + 1.0D), (int)(pDY + 1.0D));
		
		final Vector2I vAB = Vector2I.direction(pA, pB);
		final Vector2I vAC = Vector2I.direction(pA, pC);
		final Vector2I vAD = Vector2I.direction(pA, pD);
		final Vector2I vAE = Vector2I.direction(pA, pE);
		final Vector2I vAF = Vector2I.direction(pA, pF);
		final Vector2I vAG = Vector2I.direction(pA, pG);
		final Vector2I vAH = Vector2I.direction(pA, pH);
		final Vector2I vAI = Vector2I.direction(pA, pI);
		final Vector2I vAJ = Vector2I.direction(pA, pJ);
		final Vector2I vAK = Vector2I.direction(pA, pK);
		
		final int vABLengthI = vAB.length();
		final int vACLengthI = vAC.length();
		final int vADLengthI = vAD.length();
		final int vAELengthI = vAE.length();
		final int vAFLengthI = vAF.length();
		final int vAGLengthI = vAG.length();
		final int vAHLengthI = vAH.length();
		final int vAILengthI = vAI.length();
		final int vAJLengthI = vAJ.length();
		final int vAKLengthI = vAK.length();
		
		if(vABLengthI == vACLengthI) {
			return new LineSegment2I(pA, pC);
		}
		
		if(vABLengthI == vADLengthI) {
			return new LineSegment2I(pA, pD);
		}
		
		if(vABLengthI == vAELengthI) {
			return new LineSegment2I(pA, pE);
		}
		
		if(vABLengthI == vAFLengthI) {
			return new LineSegment2I(pA, pF);
		}
		
		if(vABLengthI == vAGLengthI) {
			return new LineSegment2I(pA, pG);
		}
		
		if(vABLengthI == vAHLengthI) {
			return new LineSegment2I(pA, pH);
		}
		
		if(vABLengthI == vAILengthI) {
			return new LineSegment2I(pA, pI);
		}
		
		if(vABLengthI == vAJLengthI) {
			return new LineSegment2I(pA, pJ);
		}
		
		if(vABLengthI == vAKLengthI) {
			return new LineSegment2I(pA, pK);
		}
		
		return lineSegment;
	}
	
//	TODO: Add Javadocs!
//	TODO: Add Unit Tests!
	public static List<LineSegment2I> rotateAll(final LineSegment2I lineSegment, final double angle, final boolean isAngleInRadians) {
		final List<LineSegment2I> lineSegments = new ArrayList<>();
		
		final double a = isAngleInRadians ? angle : Doubles.toRadians(angle);
		final double aCos = Doubles.cos(a);
		final double aSin = Doubles.sin(a);
		
		final Point2I pA = lineSegment.getA();
		final Point2I pB = lineSegment.getB();
		
		final double pAX = pA.x;
		final double pAY = pA.y;
		final double pBX = pB.x;
		final double pBY = pB.y;
		final double pCX = pAX + (pBX - pAX) * aCos + (pBY - pAY) * aSin;
		final double pCY = pAY - (pBX - pAX) * aSin + (pBY - pAY) * aCos;
		
		final double vABX = pBX - pAX;
		final double vABY = pBY - pAY;
		final double vABLength = Doubles.sqrt(vABX * vABX + vABY * vABY);
		
		final double vACX = pCX - pAX;
		final double vACY = pCY - pAY;
		final double vACLength = Doubles.sqrt(vACX * vACX + vACY * vACY);
		final double vACNX = vACX / vACLength;
		final double vACNY = vACY / vACLength;
		
		final double pDX = pAX + vACNX * vABLength;
		final double pDY = pAY + vACNY * vABLength;
		
		final Point2I pC = new Point2I((int)(pDX - 1.0D), (int)(pDY - 1.0D));
		final Point2I pD = new Point2I((int)(pDX - 0.0D), (int)(pDY - 1.0D));
		final Point2I pE = new Point2I((int)(pDX + 1.0D), (int)(pDY - 1.0D));
		final Point2I pF = new Point2I((int)(pDX - 1.0D), (int)(pDY + 0.0D));
		final Point2I pG = new Point2I((int)(pDX - 0.0D), (int)(pDY + 0.0D));
		final Point2I pH = new Point2I((int)(pDX + 1.0D), (int)(pDY + 0.0D));
		final Point2I pI = new Point2I((int)(pDX - 1.0D), (int)(pDY + 1.0D));
		final Point2I pJ = new Point2I((int)(pDX - 0.0D), (int)(pDY + 1.0D));
		final Point2I pK = new Point2I((int)(pDX + 1.0D), (int)(pDY + 1.0D));
		
		final Vector2I vAB = Vector2I.direction(pA, pB);
		final Vector2I vAC = Vector2I.direction(pA, pC);
		final Vector2I vAD = Vector2I.direction(pA, pD);
		final Vector2I vAE = Vector2I.direction(pA, pE);
		final Vector2I vAF = Vector2I.direction(pA, pF);
		final Vector2I vAG = Vector2I.direction(pA, pG);
		final Vector2I vAH = Vector2I.direction(pA, pH);
		final Vector2I vAI = Vector2I.direction(pA, pI);
		final Vector2I vAJ = Vector2I.direction(pA, pJ);
		final Vector2I vAK = Vector2I.direction(pA, pK);
		
		final int vABLengthI = vAB.length();
		final int vACLengthI = vAC.length();
		final int vADLengthI = vAD.length();
		final int vAELengthI = vAE.length();
		final int vAFLengthI = vAF.length();
		final int vAGLengthI = vAG.length();
		final int vAHLengthI = vAH.length();
		final int vAILengthI = vAI.length();
		final int vAJLengthI = vAJ.length();
		final int vAKLengthI = vAK.length();
		
		if(vABLengthI == vACLengthI) {
			lineSegments.add(new LineSegment2I(pA, pC));
		}
		
		if(vABLengthI == vADLengthI) {
			lineSegments.add(new LineSegment2I(pA, pD));
		}
		
		if(vABLengthI == vAELengthI) {
			lineSegments.add(new LineSegment2I(pA, pE));
		}
		
		if(vABLengthI == vAFLengthI) {
			lineSegments.add(new LineSegment2I(pA, pF));
		}
		
		if(vABLengthI == vAGLengthI) {
			lineSegments.add(new LineSegment2I(pA, pG));
		}
		
		if(vABLengthI == vAHLengthI) {
			lineSegments.add(new LineSegment2I(pA, pH));
		}
		
		if(vABLengthI == vAILengthI) {
			lineSegments.add(new LineSegment2I(pA, pI));
		}
		
		if(vABLengthI == vAJLengthI) {
			lineSegments.add(new LineSegment2I(pA, pJ));
		}
		
		if(vABLengthI == vAKLengthI) {
			lineSegments.add(new LineSegment2I(pA, pK));
		}
		
		return lineSegments;
	}
}