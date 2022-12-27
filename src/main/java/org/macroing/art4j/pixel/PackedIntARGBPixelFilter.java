/**
 * Copyright 2022 - 2023 J&#246;rgen Lundgren
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
package org.macroing.art4j.pixel;

/**
 * Represents a pixel filter that filters a specific pixel.
 * <p>
 * This is a functional interface whose functional method is {@link #isAccepted(int, int, int)}.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
@FunctionalInterface
public interface PackedIntARGBPixelFilter {
	/**
	 * Applies this filter to the given operands.
	 * <p>
	 * Returns {@code true} if, and only if, the pixel is accepted, {@code false} otherwise.
	 * 
	 * @param color an {@code int} that represents a color in the format ARGB and contains the current color of the pixel
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return {@code true} if, and only if, the pixel is accepted, {@code false} otherwise
	 */
	boolean isAccepted(final int color, final int x, final int y);
}