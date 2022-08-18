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
package org.macroing.art4j.pixel;

import java.util.Objects;

import org.macroing.art4j.color.Color4F;
import org.macroing.art4j.color.ColorSpaceF;

/**
 * Represents a pixel operation that produces a {@link Color4F} instance for a specific pixel.
 * <p>
 * This is a functional interface whose functional method is {@link #apply(Color4F, int, int)}.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
@FunctionalInterface
public interface Color4FPixelOperator {
	/**
	 * Applies this operator to the given operands.
	 * <p>
	 * Returns a {@link Color4F} instance with the operator result.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color4F} instance that contains the current color of the pixel
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return a {@code Color4F} instance with the operator result
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	Color4F apply(final Color4F color, final int x, final int y);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code Color4FPixelOperator} that inverts the red, green and blue component values of {@code color}.
	 * 
	 * @return a {@code Color4FPixelOperator} that inverts the red, green and blue component values of {@code color}
	 */
//	TODO: Add Unit Tests!
	static Color4FPixelOperator invert() {
		return (color, x, y) -> Color4F.invert(color);
	}
	
	/**
	 * Returns a {@code Color4FPixelOperator} that performs a gamma correction redo operation on {@code color}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color4FPixelOperator.redoGammaCorrection(ColorSpaceF.getDefault());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Color4FPixelOperator} that performs a gamma correction redo operation on {@code color}
	 */
//	TODO: Add Unit Tests!
	static Color4FPixelOperator redoGammaCorrection() {
		return redoGammaCorrection(ColorSpaceF.getDefault());
	}
	
	/**
	 * Returns a {@code Color4FPixelOperator} that performs a gamma correction redo operation on {@code color}.
	 * <p>
	 * If {@code colorSpace} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param colorSpace a {@link ColorSpaceF} instance
	 * @return a {@code Color4FPixelOperator} that performs a gamma correction redo operation on {@code color}
	 * @throws NullPointerException thrown if, and only if, {@code colorSpace} is {@code null}
	 */
//	TODO: Add Unit Tests!
	static Color4FPixelOperator redoGammaCorrection(final ColorSpaceF colorSpace) {
		Objects.requireNonNull(colorSpace, "colorSpace == null");
		
		return (color, x, y) -> colorSpace.redoGammaCorrection(color);
	}
	
	/**
	 * Returns a {@code Color4FPixelOperator} that converts {@code color} to its sepia-representation.
	 * 
	 * @return a {@code Color4FPixelOperator} that converts {@code color} to its sepia-representation
	 */
//	TODO: Add Unit Tests!
	static Color4FPixelOperator sepia() {
		return (color, x, y) -> Color4F.sepia(color);
	}
	
	/**
	 * Returns a {@code Color4FPixelOperator} that performs a gamma correction undo operation on {@code color}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color4FPixelOperator.undoGammaCorrection(ColorSpaceF.getDefault());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Color4FPixelOperator} that performs a gamma correction undo operation on {@code color}
	 */
//	TODO: Add Unit Tests!
	static Color4FPixelOperator undoGammaCorrection() {
		return undoGammaCorrection(ColorSpaceF.getDefault());
	}
	
	/**
	 * Returns a {@code Color4FPixelOperator} that performs a gamma correction undo operation on {@code color}.
	 * <p>
	 * If {@code colorSpace} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param colorSpace a {@link ColorSpaceF} instance
	 * @return a {@code Color4FPixelOperator} that performs a gamma correction undo operation on {@code color}
	 * @throws NullPointerException thrown if, and only if, {@code colorSpace} is {@code null}
	 */
//	TODO: Add Unit Tests!
	static Color4FPixelOperator undoGammaCorrection(final ColorSpaceF colorSpace) {
		Objects.requireNonNull(colorSpace, "colorSpace == null");
		
		return (color, x, y) -> colorSpace.undoGammaCorrection(color);
	}
}