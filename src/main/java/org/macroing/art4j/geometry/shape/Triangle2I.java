/**
 * Copyright 2022 J&#246;rgen Lundgren
 * 
 * This file is part of org.macroing.art4j.
 * 
 * org.macroing.art4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * org.macroing.art4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with org.macroing.art4j. If not, see <http://www.gnu.org/licenses/>.
 */
package org.macroing.art4j.geometry.shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.art4j.geometry.Point2I;
import org.macroing.art4j.geometry.Shape2I;

/**
 * A {@code Triangle2I} is an implementation of {@link Shape2I} that represents a triangle.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Triangle2I implements Shape2I {
	private final List<LineSegment2I> lineSegments;
	private final Point2I a;
	private final Point2I b;
	private final Point2I c;
	private final Rectangle2I rectangle;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Triangle2I} instance given three {@link Point2I} instances, {@code a}, {@code b} and {@code c}.
	 * <p>
	 * If either {@code a}, {@code b} or {@code c} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2I} instance
	 * @param b a {@code Point2I} instance
	 * @param c a {@code Point2I} instance
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b} or {@code c} are {@code null}
	 */
	public Triangle2I(final Point2I a, final Point2I b, final Point2I c) {
		this.a = Objects.requireNonNull(a, "a == null");
		this.b = Objects.requireNonNull(b, "b == null");
		this.c = Objects.requireNonNull(c, "c == null");
		this.lineSegments = LineSegment2I.fromPoints(a, b, c);
		this.rectangle = Rectangle2I.fromPoints(a, b, c);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} that contains {@link LineSegment2I} instances that connects all {@link Point2I} instances in this {@code Triangle2I} instance.
	 * 
	 * @return a {@code List} that contains {@code LineSegment2I} instances that connects all {@link Point2I} instances in this {@code Triangle2I} instance
	 */
	public List<LineSegment2I> getLineSegments() {
		return new ArrayList<>(this.lineSegments);
	}
	
	/**
	 * Returns a {@code List} with {@link Point2I} instances contained in this {@code Triangle2I} instance.
	 * 
	 * @param isIncludingBorderOnly {@code true} if, and only if, this method should only include {@code Point2I} instances on the border of this {@code Triangle2I} instance, {@code false} otherwise
	 * @return a {@code List} with {@code Point2I} instances contained in this {@code Triangle2I} instance
	 */
	@Override
	public List<Point2I> findPoints(final boolean isIncludingBorderOnly) {
		return this.rectangle.findPoints().stream().filter(point -> contains(point, isIncludingBorderOnly)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
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
	 * Returns the {@link Point2I} instance denoted by {@code C}.
	 * 
	 * @return the {@code Point2I} instance denoted by {@code C}
	 */
	public Point2I getC() {
		return this.c;
	}
	
	/**
	 * Returns a {@link Point2I} with the largest component values needed to contain this {@code Triangle2I} instance.
	 * 
	 * @return a {@code Point2I} with the largest component values needed to contain this {@code Triangle2I} instance
	 */
	@Override
	public Point2I max() {
		return Point2I.max(this.a, this.b, this.c);
	}
	
	/**
	 * Returns a {@link Point2I} with the smallest component values needed to contain this {@code Triangle2I} instance.
	 * 
	 * @return a {@code Point2I} with the smallest component values needed to contain this {@code Triangle2I} instance
	 */
	@Override
	public Point2I min() {
		return Point2I.min(this.a, this.b, this.c);
	}
	
	/**
	 * Returns the {@link Rectangle2I} instance that contains this {@code Triangle2I} instance.
	 * 
	 * @return the {@code Rectangle2I} instance that contains this {@code Triangle2I} instance
	 */
	public Rectangle2I getRectangle() {
		return this.rectangle;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Triangle2I} instance.
	 * 
	 * @return a {@code String} representation of this {@code Triangle2I} instance
	 */
	@Override
	public String toString() {
		return String.format("new Triangle2I(%s, %s, %s)", this.a, this.b, this.c);
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code point} is contained in this {@code Triangle2I} instance, {@code false} otherwise.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@link Point2I} instance
	 * @param isIncludingBorderOnly {@code true} if, and only if, this method should only include {@code Point2I} instances on the border of this {@code Triangle2I} instance, {@code false} otherwise
	 * @return {@code true} if, and only if, {@code point} is contained in this {@code Triangle2I} instance, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	@Override
	public boolean contains(final Point2I point, final boolean isIncludingBorderOnly) {
		return doContainsOnLineSegments(point) || !isIncludingBorderOnly && doContains(point);
	}
	
	/**
	 * Compares {@code object} to this {@code Triangle2I} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Triangle2I}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Triangle2I} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Triangle2I}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Triangle2I)) {
			return false;
		} else if(!Objects.equals(this.a, Triangle2I.class.cast(object).a)) {
			return false;
		} else if(!Objects.equals(this.b, Triangle2I.class.cast(object).b)) {
			return false;
		} else if(!Objects.equals(this.c, Triangle2I.class.cast(object).c)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Triangle2I} instance.
	 * 
	 * @return a hash code for this {@code Triangle2I} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.a, this.b, this.c);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private boolean doContains(final Point2I point) {
		final int signA = (point.x - this.b.x) * (this.a.y - this.b.y) - (this.a.x - this.b.x) * (point.y - this.b.y);
		final int signB = (point.x - this.c.x) * (this.b.y - this.c.y) - (this.b.x - this.c.x) * (point.y - this.c.y);
		final int signC = (point.x - this.a.x) * (this.c.y - this.a.y) - (this.c.x - this.a.x) * (point.y - this.a.y);
		
//		TODO: Using | instead of || to get full code coverage. Should this be fixed?
		final boolean hasNegativeSign = signA < 0 | signB < 0 | signC < 0;
		final boolean hasPositiveSign = signA > 0 | signB > 0 | signC > 0;
		
//		TODO: Using & instead of && to get full code coverage. Should this be fixed?
		return !(hasNegativeSign & hasPositiveSign);
	}
	
	private boolean doContainsOnLineSegments(final Point2I point) {
		for(final LineSegment2I lineSegment : this.lineSegments) {
			if(lineSegment.contains(point)) {
				return true;
			}
		}
		
		return false;
	}
}