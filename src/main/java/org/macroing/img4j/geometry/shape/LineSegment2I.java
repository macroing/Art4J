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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.img4j.geometry.Point2I;
import org.macroing.img4j.geometry.Shape2I;

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
		if(points.length < 2) {
			throw new IllegalArgumentException("points.length < 2");
		}
		
		final List<LineSegment2I> lineSegments = new ArrayList<>(points.length);
		
		for(int i = 0, j = 1; i < points.length; i++, j = (j + 1) % points.length) {
			final Point2I pointI = points[i];
			final Point2I pointJ = points[j];
			
			final LineSegment2I lineSegment = new LineSegment2I(pointI, pointJ);
			
			lineSegments.add(lineSegment);
		}
		
		return lineSegments;
	}
}