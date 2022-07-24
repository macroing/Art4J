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
import org.macroing.img4j.utility.Doubles;

/**
 * A {@code Rectangle2I} is an implementation of {@link Shape2I} that represents a rectangle.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Rectangle2I implements Shape2I {
	private final List<LineSegment2I> lineSegments;
	private final Point2I a;
	private final Point2I b;
	private final Point2I c;
	private final Point2I d;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Rectangle2I} instance that contains {@code circle}.
	 * <p>
	 * If {@code circle} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param circle a {@link Circle2I} instance
	 * @throws NullPointerException thrown if, and only if, {@code circle} is {@code null}
	 */
	public Rectangle2I(final Circle2I circle) {
		this(new Point2I(circle.getCenter().x - circle.getRadius(), circle.getCenter().y - circle.getRadius()), new Point2I(circle.getCenter().x + circle.getRadius(), circle.getCenter().y + circle.getRadius()));
	}
	
	/**
	 * Constructs a new {@code Rectangle2I} instance based on {@code x} and {@code y}.
	 * <p>
	 * If either {@code x} or {@code y} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param x a {@link Point2I} instance
	 * @param y a {@code Point2I} instance
	 * @throws NullPointerException thrown if, and only if, either {@code x} or {@code y} are {@code null}
	 */
	public Rectangle2I(final Point2I x, final Point2I y) {
		this.a = new Point2I(Math.min(x.x, y.x), Math.min(x.y, y.y));
		this.b = new Point2I(Math.max(x.x, y.x), Math.min(x.y, y.y));
		this.c = new Point2I(Math.max(x.x, y.x), Math.max(x.y, y.y));
		this.d = new Point2I(Math.min(x.x, y.x), Math.max(x.y, y.y));
		this.lineSegments = LineSegment2I.fromPoints(this.a, this.b, this.c, this.d);
	}
	
	/**
	 * Constructs a new {@code Rectangle2I} instance based on {@code a}, {@code b} and {@code c}.
	 * <p>
	 * If either {@code a}, {@code b} or {@code c} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code a}, {@code b} and {@code c} cannot form a rectangle, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * It is currently not possible to specify {@code a}, {@code b} and {@code c} in counterclockwise order. This will likely be fixed in the future.
	 * 
	 * @param a a {@link Point2I} instance
	 * @param b a {@code Point2I} instance
	 * @param c a {@code Point2I} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code a}, {@code b} and {@code c} cannot form a rectangle
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b} or {@code c} are {@code null}
	 */
	public Rectangle2I(final Point2I a, final Point2I b, final Point2I c) {
		this.a = Objects.requireNonNull(a, "a == null");
		this.b = Objects.requireNonNull(b, "b == null");
		this.c = Objects.requireNonNull(c, "c == null");
		this.d = doComputeD(a, b, c);
		this.lineSegments = LineSegment2I.fromPoints(this.a, this.b, this.c, this.d);
		
		doCheckPointValidity(this.a, this.b, this.c, this.d);
	}
	
	/**
	 * Constructs a new {@code Rectangle2I} instance based on {@code a}, {@code b}, {@code c} and {@code d}.
	 * <p>
	 * If either {@code a}, {@code b}, {@code c} or {@code d} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code a}, {@code b}, {@code c} and {@code d} does not form a rectangle, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param a a {@link Point2I} instance
	 * @param b a {@code Point2I} instance
	 * @param c a {@code Point2I} instance
	 * @param d a {@code Point2I} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code a}, {@code b}, {@code c} and {@code d} does not form a rectangle
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b}, {@code c} or {@code d} are {@code null}
	 */
	public Rectangle2I(final Point2I a, final Point2I b, final Point2I c, final Point2I d) {
		doCheckPointValidity(a, b, c, d);
		
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.lineSegments = LineSegment2I.fromPoints(this.a, this.b, this.c, this.d);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} that contains {@link LineSegment2I} instances that connects all {@link Point2I} instances in this {@code Rectangle2I} instance.
	 * 
	 * @return a {@code List} that contains {@code LineSegment2I} instances that connects all {@link Point2I} instances in this {@code Rectangle2I} instance
	 */
	public List<LineSegment2I> getLineSegments() {
		return new ArrayList<>(this.lineSegments);
	}
	
	/**
	 * Returns a {@code List} with {@link Point2I} instances contained in this {@code Rectangle2I} instance.
	 * 
	 * @param isIncludingBorderOnly {@code true} if, and only if, this method should only include {@code Point2I} instances on the border of this {@code Rectangle2I} instance, {@code false} otherwise
	 * @return a {@code List} with {@code Point2I} instances contained in this {@code Rectangle2I} instance
	 */
	@Override
	public List<Point2I> findPoints(final boolean isIncludingBorderOnly) {
		final List<Point2I> points = new ArrayList<>();
		
		final Point2I max = max();
		final Point2I min = min();
		
		final int maxX = max.x;
		final int minX = min.x;
		final int maxY = max.y;
		final int minY = min.y;
		
		for(int y = minY; y <= maxY; y++) {
			for(int x = minX; x <= maxX; x++) {
				final Point2I point = new Point2I(x, y);
				
				if(contains(point, isIncludingBorderOnly)) {
					points.add(point);
				}
			}
		}
		
		return points;
	}
	
	/**
	 * Returns the {@link Point2I} instance denoted by A.
	 * <p>
	 * This {@code Point2I} instance usually contains the minimum X and minimum Y component values.
	 * 
	 * @return the {@code Point2I} instance denoted by A
	 */
	public Point2I getA() {
		return this.a;
	}
	
	/**
	 * Returns the {@link Point2I} instance denoted by B.
	 * <p>
	 * This {@code Point2I} instance usually contains the maximum X and minimum Y component values.
	 * 
	 * @return the {@code Point2I} instance denoted by B
	 */
	public Point2I getB() {
		return this.b;
	}
	
	/**
	 * Returns the {@link Point2I} instance denoted by C.
	 * <p>
	 * This {@code Point2I} instance usually contains the maximum X and maximum Y component values.
	 * 
	 * @return the {@code Point2I} instance denoted by C
	 */
	public Point2I getC() {
		return this.c;
	}
	
	/**
	 * Returns the {@link Point2I} instance denoted by D.
	 * <p>
	 * This {@code Point2I} instance usually contains the minimum X and maximum Y component values.
	 * 
	 * @return the {@code Point2I} instance denoted by D
	 */
	public Point2I getD() {
		return this.d;
	}
	
	/**
	 * Returns a {@link Point2I} with the largest component values needed to contain this {@code Rectangle2I} instance.
	 * 
	 * @return a {@code Point2I} with the largest component values needed to contain this {@code Rectangle2I} instance
	 */
	@Override
	public Point2I max() {
		return Point2I.max(this.a, this.b, this.c, this.d);
	}
	
	/**
	 * Returns a {@link Point2I} with the smallest component values needed to contain this {@code Rectangle2I} instance.
	 * 
	 * @return a {@code Point2I} with the smallest component values needed to contain this {@code Rectangle2I} instance
	 */
	@Override
	public Point2I min() {
		return Point2I.min(this.a, this.b, this.c, this.d);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Rectangle2I} instance.
	 * 
	 * @return a {@code String} representation of this {@code Rectangle2I} instance
	 */
	@Override
	public String toString() {
		return String.format("new Rectangle2I(%s, %s, %s, %s)", this.a, this.b, this.c, this.d);
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code point} is contained in this {@code Rectangle2I} instance, {@code false} otherwise.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@link Point2I} instance
	 * @param isIncludingBorderOnly {@code true} if, and only if, this method should only include {@code Point2I} instances on the border of this {@code Rectangle2I} instance, {@code false} otherwise
	 * @return {@code true} if, and only if, {@code point} is contained in this {@code Rectangle2I} instance, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	@Override
	public boolean contains(final Point2I point, final boolean isIncludingBorderOnly) {
		return isIncludingBorderOnly ? doContainsOnLineSegments(point) : doContains(point) || doContainsOnLineSegments(point);
	}
	
	/**
	 * Compares {@code object} to this {@code Rectangle2I} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Rectangle2I}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Rectangle2I} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Rectangle2I}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Rectangle2I)) {
			return false;
		} else if(!Objects.equals(this.a, Rectangle2I.class.cast(object).a)) {
			return false;
		} else if(!Objects.equals(this.b, Rectangle2I.class.cast(object).b)) {
			return false;
		} else if(!Objects.equals(this.c, Rectangle2I.class.cast(object).c)) {
			return false;
		} else {
			return Objects.equals(this.d, Rectangle2I.class.cast(object).d);
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Rectangle2I} instance is axis-aligned, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Rectangle2I} instance is axis-aligned, {@code false} otherwise
	 */
	public boolean isAxisAligned() {
		final boolean isAxisAlignedAB = this.a.y == this.b.y;
		final boolean isAxisAlignedBC = this.b.x == this.c.x;
		final boolean isAxisAlignedCD = this.c.y == this.d.y;
		final boolean isAxisAlignedDA = this.d.x == this.a.x;
		final boolean isAxisAligned = isAxisAlignedAB & isAxisAlignedBC & isAxisAlignedCD & isAxisAlignedDA;//TODO: Using & instead of && to get full code coverage. Should this be fixed?
		
		return isAxisAligned;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Rectangle2I} instance is rotated, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Rectangle2I} instance is rotated, {@code false} otherwise
	 */
	public boolean isRotated() {
		return !isAxisAligned();
	}
	
	/**
	 * Returns a hash code for this {@code Rectangle2I} instance.
	 * 
	 * @return a hash code for this {@code Rectangle2I} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.a, this.b, this.c, this.d);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code Rectangle2I} instance that contains all {@link Point2I} instances in {@code points}.
	 * <p>
	 * If either {@code points} or an element in {@code points} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code points.length} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param points a {@code Point2I[]} instance
	 * @return a {@code Rectangle2I} instance that contains all {@code Point2I} instances in {@code points}
	 * @throws IllegalArgumentException thrown if, and only if, {@code points.length} is less than {@code 1}
	 * @throws NullPointerException thrown if, and only if, either {@code points} or an element in {@code points} are {@code null}
	 */
	public static Rectangle2I fromPoints(final Point2I... points) {
		if(points.length < 1) {
			throw new IllegalArgumentException("points.length < 1");
		}
		
		Point2I max = Point2I.MIN;
		Point2I min = Point2I.MAX;
		
		for(final Point2I point : points) {
			max = Point2I.max(max, point);
			min = Point2I.min(min, point);
		}
		
		return new Rectangle2I(max, min);
	}
	
	/**
	 * Returns a {@code Rectangle2I} instance that is the union of {@code a} and {@code b}.
	 * <p>
	 * If either {@code a} or {@code b} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Rectangle2I} instance
	 * @param b a {@code Rectangle2I} instance
	 * @return a {@code Rectangle2I} instance that is the union of {@code a} and {@code b}
	 * @throws NullPointerException thrown if, and only if, either {@code a} or {@code b} are {@code null}
	 */
	public static Rectangle2I union(final Rectangle2I a, final Rectangle2I b) {
		final Point2I min = Point2I.min(a.min(), b.min());
		final Point2I max = Point2I.max(a.max(), b.max());
		
		return new Rectangle2I(min, max);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private boolean doContains(final Point2I point) {
		boolean isInside = false;
		
		final int pX = point.x;
		final int pY = point.y;
		
		final Point2I[] points = {this.a, this.b, this.c, this.d};
		
		for(int i = 0, j = points.length - 1; i < points.length; j = i++) {
			final Point2I pointI = points[i];
			final Point2I pointJ = points[j];
			
			final int iX = pointI.x;
			final int iY = pointI.y;
			final int jX = pointJ.x;
			final int jY = pointJ.y;
			
			if((iY > pY) != (jY > pY) && pX < (jX - iX) * (pY - iY) / (jY - iY) + iX) {
				isInside = !isInside;
			}
		}
		
		return isInside;
	}
	
	private boolean doContainsOnLineSegments(final Point2I point) {
		for(final LineSegment2I lineSegment : this.lineSegments) {
			if(lineSegment.contains(point)) {
				return true;
			}
		}
		
		return false;
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
		
		final int x = (int)(dX);
		final int y = (int)(dY);
		
		return new Point2I(x, y);
	}
	
	private static void doCheckPointValidity(final Point2I a, final Point2I b, final Point2I c, final Point2I d) {
		Objects.requireNonNull(a, "a == null");
		Objects.requireNonNull(b, "b == null");
		Objects.requireNonNull(c, "c == null");
		Objects.requireNonNull(d, "d == null");
		
		final int distanceAB = Point2I.distance(a, b);
		final int distanceBC = Point2I.distance(b, c);
		final int distanceCD = Point2I.distance(c, d);
		final int distanceDA = Point2I.distance(d, a);
		
		final int deltaABCD = Math.abs(distanceAB - distanceCD);
		final int deltaBCDA = Math.abs(distanceBC - distanceDA);
		
		final boolean isValidABCD = deltaABCD == 0;
		final boolean isValidBCDA = deltaBCDA == 0;
		final boolean isValid = isValidABCD & isValidBCDA;//TODO: Using & instead of && to get full code coverage. Should this be fixed?
		
		if(!isValid) {
			throw new IllegalArgumentException();
		}
	}
}