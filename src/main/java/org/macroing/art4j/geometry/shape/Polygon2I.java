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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.macroing.art4j.geometry.Point2I;
import org.macroing.art4j.geometry.Shape2I;
import org.macroing.java.lang.Ints;

/**
 * A {@code Polygon2I} is an implementation of {@link Shape2I} that represents a polygon.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Polygon2I implements Shape2I {
	private final List<LineSegment2I> lineSegments;
	private final Point2I[] points;
	private final Rectangle2I rectangle;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Polygon2I} instance.
	 * <p>
	 * If either {@code points} or an element in {@code points} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code points.length} is less than {@code 3}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param points a {@code Point2I[]} instances
	 * @throws IllegalArgumentException thrown if, and only if, {@code points.length} is less than {@code 3}
	 * @throws NullPointerException thrown if, and only if, either {@code points} or an element in {@code points} are {@code null}
	 */
	public Polygon2I(final Point2I... points) {
		this.points = doRequireValidPoints(points);
		this.lineSegments = LineSegment2I.fromPoints(this.points);
		this.rectangle = Rectangle2I.fromPoints(this.points);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} that contains {@link LineSegment2I} instances that connects all {@link Point2I} instances in this {@code Polygon2I} instance.
	 * 
	 * @return a {@code List} that contains {@code LineSegment2I} instances that connects all {@link Point2I} instances in this {@code Polygon2I} instance
	 */
	public List<LineSegment2I> getLineSegments() {
		return new ArrayList<>(this.lineSegments);
	}
	
	/**
	 * Returns a {@code List} with {@link Point2I} instances contained in this {@code Polygon2I} instance.
	 * 
	 * @param isIncludingBorderOnly {@code true} if, and only if, this method should only include {@code Point2I} instances on the border of this {@code Polygon2I} instance, {@code false} otherwise
	 * @return a {@code List} with {@code Point2I} instances contained in this {@code Polygon2I} instance
	 */
	@Override
	public List<Point2I> findPoints(final boolean isIncludingBorderOnly) {
		return this.rectangle.findPoints().stream().filter(point -> contains(point, isIncludingBorderOnly)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Point2I} instances in this {@code Polygon2I} instance.
	 * 
	 * @return a {@code List} that contains all {@code Point2I} instances in this {@code Polygon2I} instance
	 */
	public List<Point2I> getPoints() {
		return new ArrayList<>(Arrays.asList(this.points));
	}
	
	/**
	 * Returns a {@link Point2I} with the largest component values needed to contain this {@code Polygon2I} instance.
	 * 
	 * @return a {@code Point2I} with the largest component values needed to contain this {@code Polygon2I} instance
	 */
	@Override
	public Point2I max() {
		return this.rectangle.max();
	}
	
	/**
	 * Returns a {@link Point2I} with the smallest component values needed to contain this {@code Polygon2I} instance.
	 * 
	 * @return a {@code Point2I} with the smallest component values needed to contain this {@code Polygon2I} instance
	 */
	@Override
	public Point2I min() {
		return this.rectangle.min();
	}
	
	/**
	 * Returns the {@link Rectangle2I} instance that contains this {@code Polygon2I} instance.
	 * 
	 * @return the {@code Rectangle2I} instance that contains this {@code Polygon2I} instance
	 */
	public Rectangle2I getRectangle() {
		return this.rectangle;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Polygon2I} instance.
	 * 
	 * @return a {@code String} representation of this {@code Polygon2I} instance
	 */
	@Override
	public String toString() {
		return String.format("new Polygon2I(%s)", Point2I.toString(this.points));
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code point} is contained in this {@code Polygon2I} instance, {@code false} otherwise.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@link Point2I} instance
	 * @param isIncludingBorderOnly {@code true} if, and only if, this method should only include {@code Point2I} instances on the border of this {@code Polygon2I} instance, {@code false} otherwise
	 * @return {@code true} if, and only if, {@code point} is contained in this {@code Polygon2I} instance, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	@Override
	public boolean contains(final Point2I point, final boolean isIncludingBorderOnly) {
		return isIncludingBorderOnly ? doContainsOnLineSegments(point) : doContains(point) || doContainsOnLineSegments(point);
	}
	
	/**
	 * Compares {@code object} to this {@code Polygon2I} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Polygon2I}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Polygon2I} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Polygon2I}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Polygon2I)) {
			return false;
		} else if(!Arrays.equals(this.points, Polygon2I.class.cast(object).points)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Polygon2I} instance.
	 * 
	 * @return a hash code for this {@code Polygon2I} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(Arrays.hashCode(this.points)));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private boolean doContains(final Point2I point) {
		boolean isInside = false;
		
		final int pX = point.x;
		final int pY = point.y;
		
		for(int i = 0, j = this.points.length - 1; i < this.points.length; j = i++) {
			final Point2I pointI = this.points[i];
			final Point2I pointJ = this.points[j];
			
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
	
	private static Point2I[] doRequireValidPoints(final Point2I[] points) {
		org.macroing.java.util.Arrays.requireNonNull(points, "points");
		
		Ints.requireRange(points.length, 3, Integer.MAX_VALUE, "points.length");
		
		return points.clone();
	}
}