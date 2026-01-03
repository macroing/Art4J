/**
 * Copyright 2022 - 2026 J&#246;rgen Lundgren
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

import org.macroing.art4j.color.Color4F;

/**
 * Represents a pixel operation that produces a {@link Color4F} instance for a specific pixel in two images.
 * <p>
 * This is a functional interface whose functional method is {@link #apply(Color4F, Color4F, int, int)}.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
@FunctionalInterface
public interface Color4FBiPixelOperator {
	/**
	 * Applies this operator to the given operands.
	 * <p>
	 * Returns a {@link Color4F} instance with the operator result.
	 * <p>
	 * If either {@code colorA} or {@code colorB} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param colorA a {@code Color4F} instance that contains the current color of the pixel in image A
	 * @param colorB a {@code Color4F} instance that contains the current color of the pixel in image B
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return a {@code Color4F} instance with the operator result
	 * @throws NullPointerException thrown if, and only if, either {@code colorA} or {@code colorB} are {@code null}
	 */
	Color4F apply(final Color4F colorA, final Color4F colorB, final int x, final int y);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code Color4FBiPixelOperator} instance that blends the component values of {@code colorA} over the component values of {@code colorB}.
	 * 
	 * @return a {@code Color4FBiPixelOperator} instance that blends the component values of {@code colorA} over the component values of {@code colorB}
	 */
	static Color4FBiPixelOperator blendOver() {
		return (colorA, colorB, x, y) -> Color4F.blendOver(colorA, colorB);
	}
}