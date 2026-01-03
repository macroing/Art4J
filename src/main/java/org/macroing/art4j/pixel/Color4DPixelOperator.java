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

import java.util.Objects;

import org.macroing.art4j.color.Color3D;
import org.macroing.art4j.color.Color4D;
import org.macroing.art4j.color.ColorSpaceD;
import org.macroing.art4j.noise.SimplexNoiseD;
import org.macroing.geo4j.common.Point2I;
import org.macroing.geo4j.shape.rectangle.Rectangle2I;

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
	 * Returns a {@code Color4DPixelOperator} that generates a {@link Color4D} instance using a gradient algorithm.
	 * <p>
	 * If either {@code color11}, {@code color12}, {@code color21}, {@code color22} or {@code bounds} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color11 the {@link Color3D} instance on row 1 and column 1
	 * @param color12 the {@code Color3D} instance on row 1 and column 2
	 * @param color21 the {@code Color3D} instance on row 2 and column 1
	 * @param color22 the {@code Color3D} instance on row 2 and column 2
	 * @param bounds a {@link Rectangle2I} instance that contains the bounds
	 * @return a {@code Color4DPixelOperator} that generates a {@link Color4D} instance using a gradient algorithm
	 * @throws NullPointerException thrown if, and only if, either {@code color11}, {@code color12}, {@code color21}, {@code color22} or {@code bounds} are {@code null}
	 */
	static Color4DPixelOperator gradient(final Color3D color11, final Color3D color12, final Color3D color21, final Color3D color22, final Rectangle2I bounds) {
		Objects.requireNonNull(color11, "color11 == null");
		Objects.requireNonNull(color12, "color12 == null");
		Objects.requireNonNull(color21, "color21 == null");
		Objects.requireNonNull(color22, "color22 == null");
		Objects.requireNonNull(bounds, "bounds == null");
		
		final Point2I max = bounds.max();
		final Point2I min = bounds.min();
		
		final double maxX = max.x;
		final double maxY = max.y;
		
		final double minX = min.x;
		final double minY = min.y;
		
		return (color, currentX, currentY) -> {
			Objects.requireNonNull(color, "color == null");
			
			final double tX = (currentX - minX) / (maxX - minX);
			final double tY = (currentY - minY) / (maxY - minY);
			
			return new Color4D(Color3D.blend(color11, color12, color21, color22, tX, tY));
		};
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.a}.
	 * 
	 * @return a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.a}
	 */
	static Color4DPixelOperator grayscaleA() {
		return (color, x, y) -> Color4D.grayscaleA(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.average()}.
	 * 
	 * @return a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.average()}
	 */
	static Color4DPixelOperator grayscaleAverage() {
		return (color, x, y) -> Color4D.grayscaleAverage(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.b}.
	 * 
	 * @return a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.b}
	 */
	static Color4DPixelOperator grayscaleB() {
		return (color, x, y) -> Color4D.grayscaleB(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.g}.
	 * 
	 * @return a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.g}
	 */
	static Color4DPixelOperator grayscaleG() {
		return (color, x, y) -> Color4D.grayscaleG(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.lightness()}.
	 * 
	 * @return a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.lightness()}
	 */
	static Color4DPixelOperator grayscaleLightness() {
		return (color, x, y) -> Color4D.grayscaleLightness(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.max()}.
	 * 
	 * @return a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.max()}
	 */
	static Color4DPixelOperator grayscaleMax() {
		return (color, x, y) -> Color4D.grayscaleMax(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.min()}.
	 * 
	 * @return a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.min()}
	 */
	static Color4DPixelOperator grayscaleMin() {
		return (color, x, y) -> Color4D.grayscaleMin(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.r}.
	 * 
	 * @return a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.r}
	 */
	static Color4DPixelOperator grayscaleR() {
		return (color, x, y) -> Color4D.grayscaleR(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.relativeLuminance()}.
	 * 
	 * @return a {@code Color4DPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.relativeLuminance()}
	 */
	static Color4DPixelOperator grayscaleRelativeLuminance() {
		return (color, x, y) -> Color4D.grayscaleRelativeLuminance(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that inverts the red, green and blue component values of {@code color}.
	 * 
	 * @return a {@code Color4DPixelOperator} that inverts the red, green and blue component values of {@code color}
	 */
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
	static Color4DPixelOperator redoGammaCorrection(final ColorSpaceD colorSpace) {
		Objects.requireNonNull(colorSpace, "colorSpace == null");
		
		return (color, x, y) -> colorSpace.redoGammaCorrection(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that converts {@code color} to its sepia-representation.
	 * 
	 * @return a {@code Color4DPixelOperator} that converts {@code color} to its sepia-representation
	 */
	static Color4DPixelOperator sepia() {
		return (color, x, y) -> Color4D.sepia(color);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that generates a {@link Color4D} instance using a Simplex-based fractional Brownian motion (fBm) algorithm.
	 * <p>
	 * If either {@code baseColor} or {@code bounds} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color4DPixelOperator.simplexFractionalBrownianMotion(baseColor, bounds, 5.0D, 0.5D, 16);
	 * }
	 * </pre>
	 * 
	 * @param baseColor a {@link Color3D} instance that is used as the base color
	 * @param bounds a {@link Rectangle2I} instance that contains the bounds
	 * @return a {@code Color4DPixelOperator} that generates a {@link Color4D} instance using a Simplex-based fractional Brownian motion (fBm) algorithm
	 * @throws NullPointerException thrown if, and only if, either {@code baseColor} or {@code bounds} are {@code null}
	 */
	static Color4DPixelOperator simplexFractionalBrownianMotion(final Color3D baseColor, final Rectangle2I bounds) {
		return simplexFractionalBrownianMotion(baseColor, bounds, 5.0D, 0.5D, 16);
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that generates a {@link Color4D} instance using a Simplex-based fractional Brownian motion (fBm) algorithm.
	 * <p>
	 * If either {@code baseColor} or {@code bounds} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param baseColor a {@link Color3D} instance that is used as the base color
	 * @param bounds a {@link Rectangle2I} instance that contains the bounds
	 * @param frequency the frequency to start at
	 * @param gain the amplitude multiplier
	 * @param octaves the number of iterations to perform
	 * @return a {@code Color4DPixelOperator} that generates a {@link Color4D} instance using a Simplex-based fractional Brownian motion (fBm) algorithm
	 * @throws NullPointerException thrown if, and only if, either {@code baseColor} or {@code bounds} are {@code null}
	 */
	static Color4DPixelOperator simplexFractionalBrownianMotion(final Color3D baseColor, final Rectangle2I bounds, final double frequency, final double gain, final int octaves) {
		Objects.requireNonNull(baseColor, "baseColor == null");
		Objects.requireNonNull(bounds, "bounds == null");
		
		final Point2I max = bounds.max();
		final Point2I min = bounds.min();
		
		final double maxX = max.x;
		final double maxY = max.y;
		
		final double minX = min.x;
		final double minY = min.y;
		
		return (color, currentX, currentY) -> {
			Objects.requireNonNull(color, "color == null");
			
			final double x = (currentX - minX) / (maxX - minX);
			final double y = (currentY - minY) / (maxY - minY);
			
			final double noise = SimplexNoiseD.fractionalBrownianMotionXY(x, y, frequency, gain, 0.0D, 1.0D, octaves);
			
			return new Color4D(Color3D.multiply(baseColor, noise), color.a);
		};
	}
	
	/**
	 * Returns a {@code Color4DPixelOperator} that applies a tone map operator to {@code color}.
	 * 
	 * @param relativeLuminanceMax the maximum relative luminance
	 * @return a {@code Color4DPixelOperator} that applies a tone map operator to {@code color}
	 */
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
	static Color4DPixelOperator undoGammaCorrection(final ColorSpaceD colorSpace) {
		Objects.requireNonNull(colorSpace, "colorSpace == null");
		
		return (color, x, y) -> colorSpace.undoGammaCorrection(color);
	}
}