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
import org.macroing.img4j.geometry.Vector2I;

/**
 * A {@code Circle2I} is an implementation of {@link Shape2I} that represents a circle.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Circle2I implements Shape2I {
	private final Point2I center;
	private final int radius;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Circle2I} instance with a center of {@code new Point2I()} and a radius of {@code 10}.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Circle2I(new Point2I());
	 * }
	 * </pre>
	 */
	public Circle2I() {
		this(new Point2I());
	}
	
	/**
	 * Constructs a new {@code Circle2I} instance with a center of {@code center} and a radius of {@code 10}.
	 * <p>
	 * If {@code center} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Circle2I(center, 10);
	 * }
	 * </pre>
	 * 
	 * @param center the center of this {@code Circle2I} instance
	 * @throws NullPointerException thrown if, and only if, {@code center} is {@code null}
	 */
	public Circle2I(final Point2I center) {
		this(center, 10);
	}
	
	/**
	 * Constructs a new {@code Circle2I} instance with a center of {@code center} and a radius of {@code radius}.
	 * <p>
	 * If {@code center} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param center the center of this {@code Circle2I} instance
	 * @param radius the radius of this {@code Circle2I} instance
	 * @throws NullPointerException thrown if, and only if, {@code center} is {@code null}
	 */
	public Circle2I(final Point2I center, final int radius) {
		this.center = Objects.requireNonNull(center, "center == null");
		this.radius = radius;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with {@link Point2I} instances contained in this {@code Circle2I} instance.
	 * 
	 * @param isIncludingBorderOnly {@code true} if, and only if, this method should only include {@code Point2I} instances on the border of this {@code Circle2I} instance, {@code false} otherwise
	 * @return a {@code List} with {@code Point2I} instances contained in this {@code Circle2I} instance
	 */
	@Override
	public List<Point2I> findPoints(final boolean isIncludingBorderOnly) {
		final List<Point2I> points = new ArrayList<>();
		
		final Point2I maximum = max();
		final Point2I minimum = min();
		
		final int maximumX = maximum.x;
		final int minimumX = minimum.x;
		final int maximumY = maximum.y;
		final int minimumY = minimum.y;
		
		for(int y = minimumY; y <= maximumY; y++) {
			for(int x = minimumX; x <= maximumX; x++) {
				final Point2I point = new Point2I(x, y);
				
				if(contains(point, isIncludingBorderOnly)) {
					points.add(point);
				}
			}
		}
		
		return points;
	}
	
	/**
	 * Returns the center of this {@code Circle2I} instance.
	 * 
	 * @return the center of this {@code Circle2I} instance
	 */
	public Point2I getCenter() {
		return this.center;
	}
	
	/**
	 * Returns a {@link Point2I} with the largest component values needed to contain this {@code Circle2I} instance.
	 * 
	 * @return a {@code Point2I} with the largest component values needed to contain this {@code Circle2I} instance
	 */
	@Override
	public Point2I max() {
		return new Point2I(this.center.x + this.radius, this.center.y + this.radius);
	}
	
	/**
	 * Returns a {@link Point2I} with the smallest component values needed to contain this {@code Circle2I} instance.
	 * 
	 * @return a {@code Point2I} with the smallest component values needed to contain this {@code Circle2I} instance
	 */
	@Override
	public Point2I min() {
		return new Point2I(this.center.x - this.radius, this.center.y - this.radius);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Circle2I} instance.
	 * 
	 * @return a {@code String} representation of this {@code Circle2I} instance
	 */
	@Override
	public String toString() {
		return String.format("new Circle2I(%s, %d)", this.center, Integer.valueOf(this.radius));
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code point} is contained in this {@code Circle2I} instance, {@code false} otherwise.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@link Point2I} instance
	 * @param isIncludingBorderOnly {@code true} if, and only if, this method should only include {@code Point2I} instances on the border of this {@code Circle2I} instance, {@code false} otherwise
	 * @return {@code true} if, and only if, {@code point} is contained in this {@code Circle2I} instance, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	@Override
	public boolean contains(final Point2I point, final boolean isIncludingBorderOnly) {
		final int lengthSquared = Vector2I.direction(this.center, point).lengthSquared();
		
		final boolean isInsideCircle = lengthSquared <= this.radius * this.radius;
		final boolean isInsideCircleBorder = isInsideCircle && lengthSquared > (this.radius - 1) * (this.radius - 1);
		
		return isIncludingBorderOnly ? isInsideCircleBorder : isInsideCircle;
	}
	
	/**
	 * Compares {@code object} to this {@code Circle2I} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Circle2I}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Circle2I} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Circle2I}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Circle2I)) {
			return false;
		} else if(!Objects.equals(this.center, Circle2I.class.cast(object).center)) {
			return false;
		} else if(this.radius != Circle2I.class.cast(object).radius) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the radius of this {@code Circle2I} instance.
	 * 
	 * @return the radius of this {@code Circle2I} instance
	 */
	public int getRadius() {
		return this.radius;
	}
	
	/**
	 * Returns a hash code for this {@code Circle2I} instance.
	 * 
	 * @return a hash code for this {@code Circle2I} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.center, Integer.valueOf(this.radius));
	}
}