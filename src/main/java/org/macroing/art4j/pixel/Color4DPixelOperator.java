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

import org.macroing.art4j.color.Color3D;
import org.macroing.art4j.color.Color4D;
import org.macroing.art4j.color.ColorSpaceD;

/**
 * Represents a pixel operation that produces a {@link Color4D} instance for a specific pixel.
 * <p>
 * This is a functional interface whose functional method is {@link #apply(Color4D, int, int)}.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
@FunctionalInterface
public interface Color4DPixelOperator {
	/**
	 * Applies this operator to the given operands.
	 * <p>
	 * Returns a {@link Color4D} instance with the operator result.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color4D} instance that contains the current color of the pixel
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return a {@code Color4D} instance with the operator result
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	Color4D apply(final Color4D color, final int x, final int y);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.a}.
	 * 
	 * @return a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.a}
	 */
//	TODO: Add Unit Tests!
	static Color4DPixelOperator grayscaleA() {
		return (color, x, y) -> Color4D.grayscaleA(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.average()}.
	 * 
	 * @return a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.average()}
	 */
//	TODO: Add Unit Tests!
	static Color4DPixelOperator grayscaleAverage() {
		return (color, x, y) -> Color4D.grayscaleAverage(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.b}.
	 * 
	 * @return a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.b}
	 */
//	TODO: Add Unit Tests!
	static Color4DPixelOperator grayscaleB() {
		return (color, x, y) -> Color4D.grayscaleB(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.g}.
	 * 
	 * @return a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.g}
	 */
//	TODO: Add Unit Tests!
	static Color4DPixelOperator grayscaleG() {
		return (color, x, y) -> Color4D.grayscaleG(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.lightness()}.
	 * 
	 * @return a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.lightness()}
	 */
//	TODO: Add Unit Tests!
	static Color4DPixelOperator grayscaleLightness() {
		return (color, x, y) -> Color4D.grayscaleLightness(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.max()}.
	 * 
	 * @return a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.max()}
	 */
//	TODO: Add Unit Tests!
	static Color4DPixelOperator grayscaleMax() {
		return (color, x, y) -> Color4D.grayscaleMax(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.min()}.
	 * 
	 * @return a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.min()}
	 */
//	TODO: Add Unit Tests!
	static Color4DPixelOperator grayscaleMin() {
		return (color, x, y) -> Color4D.grayscaleMin(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.r}.
	 * 
	 * @return a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.r}
	 */
//	TODO: Add Unit Tests!
	static Color4DPixelOperator grayscaleR() {
		return (color, x, y) -> Color4D.grayscaleR(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.relativeLuminance()}.
	 * 
	 * @return a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.relativeLuminance()}
	 */
//	TODO: Add Unit Tests!
	static Color4DPixelOperator grayscaleRelativeLuminance() {
		return (color, x, y) -> Color4D.grayscaleRelativeLuminance(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that inverts the red, green and blue component values of {@code color}.
	 * 
	 * @return a {@code Color4DPixelOperator} that inverts the red, green and blue component values of {@code color}
	 */
//	TODO: Add Unit Tests!
	static Color4DPixelOperator invert() {
		return (color, x, y) -> Color4D.invert(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that performs a gamma correction redo operation on {@code color}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color4DPixelOperator.redoGammaCorrection(ColorSpaceD.getDefault());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Color4DPixelOperator} that performs a gamma correction redo operation on {@code color}
	 */
//	TODO: Add Unit Tests!
	static Color4DPixelOperator redoGammaCorrection() {
		return redoGammaCorrection(ColorSpaceD.getDefault());
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that performs a gamma correction redo operation on {@code color}.
	 * <p>
	 * If {@code colorSpace} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param colorSpace a {@link ColorSpaceD} instance
	 * @return a {@code Color4DPixelOperator} that performs a gamma correction redo operation on {@code color}
	 * @throws NullPointerException thrown if, and only if, {@code colorSpace} is {@code null}
	 */
//	TODO: Add Unit Tests!
	static Color4DPixelOperator redoGammaCorrection(final ColorSpaceD colorSpace) {
		Objects.requireNonNull(colorSpace, "colorSpace == null");
		
		return (color, x, y) -> colorSpace.redoGammaCorrection(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that converts {@code color} to its sepia-representation.
	 * 
	 * @return a {@code Color4DPixelOperator} that converts {@code color} to its sepia-representation
	 */
//	TODO: Add Unit Tests!
	static Color4DPixelOperator sepia() {
		return (color, x, y) -> Color4D.sepia(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that applies a tone map operator to {@code color}.
	 * 
	 * @param relativeLuminanceMax the maximum relative luminance
	 * @return a {@code Color4DPixelOperator} that applies a tone map operator to {@code color}
	 */
//	TODO: Add Unit Tests!
	static Color4DPixelOperator toneMap(final double relativeLuminanceMax) {
		return (color, x, y) -> {
			final double relativeLuminance = color.relativeLuminance();
			final double scale = (1.0D + relativeLuminance / (relativeLuminanceMax * relativeLuminanceMax)) / (1.0D + relativeLuminance);
			
			return new Color4D(Color3D.multiply(new Color3D(color), scale), color.a);
		};
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that performs a gamma correction undo operation on {@code color}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color4DPixelOperator.undoGammaCorrection(ColorSpaceD.getDefault());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Color4DPixelOperator} that performs a gamma correction undo operation on {@code color}
	 */
//	TODO: Add Unit Tests!
	static Color4DPixelOperator undoGammaCorrection() {
		return undoGammaCorrection(ColorSpaceD.getDefault());
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that performs a gamma correction undo operation on {@code color}.
	 * <p>
	 * If {@code colorSpace} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param colorSpace a {@link ColorSpaceD} instance
	 * @return a {@code Color4DPixelOperator} that performs a gamma correction undo operation on {@code color}
	 * @throws NullPointerException thrown if, and only if, {@code colorSpace} is {@code null}
	 */
//	TODO: Add Unit Tests!
	static Color4DPixelOperator undoGammaCorrection(final ColorSpaceD colorSpace) {
		Objects.requireNonNull(colorSpace, "colorSpace == null");
		
		return (color, x, y) -> colorSpace.undoGammaCorrection(color);
	}
}