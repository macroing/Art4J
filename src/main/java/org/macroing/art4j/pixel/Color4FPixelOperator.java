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

import java.util.Objects;

import org.macroing.art4j.color.Color3F;
import org.macroing.art4j.color.Color4F;
import org.macroing.art4j.color.ColorSpaceF;
import org.macroing.art4j.noise.SimplexNoiseF;
import org.macroing.geo4j.common.Point2I;
import org.macroing.geo4j.shape.rectangle.Rectangle2I;

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
	 * Returns a {@code Color4FPixelOperator} that generates a {@link Color4F} instance using a gradient algorithm.
	 * <p>
	 * If either {@code color11}, {@code color12}, {@code color21}, {@code color22} or {@code bounds} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color11 the {@link Color3F} instance on row 1 and column 1
	 * @param color12 the {@code Color3F} instance on row 1 and column 2
	 * @param color21 the {@code Color3F} instance on row 2 and column 1
	 * @param color22 the {@code Color3F} instance on row 2 and column 2
	 * @param bounds a {@link Rectangle2I} instance that contains the bounds
	 * @return a {@code Color4FPixelOperator} that generates a {@link Color4F} instance using a gradient algorithm
	 * @throws NullPointerException thrown if, and only if, either {@code color11}, {@code color12}, {@code color21}, {@code color22} or {@code bounds} are {@code null}
	 */
	static Color4FPixelOperator gradient(final Color3F color11, final Color3F color12, final Color3F color21, final Color3F color22, final Rectangle2I bounds) {
		Objects.requireNonNull(color11, "color11 == null");
		Objects.requireNonNull(color12, "color12 == null");
		Objects.requireNonNull(color21, "color21 == null");
		Objects.requireNonNull(color22, "color22 == null");
		Objects.requireNonNull(bounds, "bounds == null");
		
		final Point2I max = bounds.max();
		final Point2I min = bounds.min();
		
		final float maxX = max.x;
		final float maxY = max.y;
		
		final float minX = min.x;
		final float minY = min.y;
		
		return (color, currentX, currentY) -> {
			Objects.requireNonNull(color, "color == null");
			
			final float tX = (currentX - minX) / (maxX - minX);
			final float tY = (currentY - minY) / (maxY - minY);
			
			return new Color4F(Color3F.blend(color11, color12, color21, color22, tX, tY));
		};
	}
	
	/**
	 * Returns a {@code Color4FPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.a}.
	 * 
	 * @return a {@code Color4FPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.a}
	 */
	static Color4FPixelOperator grayscaleA() {
		return (color, x, y) -> Color4F.grayscaleA(color);
	}
	
	/**
	 * Returns a {@code Color4FPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.average()}.
	 * 
	 * @return a {@code Color4FPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.average()}
	 */
	static Color4FPixelOperator grayscaleAverage() {
		return (color, x, y) -> Color4F.grayscaleAverage(color);
	}
	
	/**
	 * Returns a {@code Color4FPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.b}.
	 * 
	 * @return a {@code Color4FPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.b}
	 */
	static Color4FPixelOperator grayscaleB() {
		return (color, x, y) -> Color4F.grayscaleB(color);
	}
	
	/**
	 * Returns a {@code Color4FPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.g}.
	 * 
	 * @return a {@code Color4FPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.g}
	 */
	static Color4FPixelOperator grayscaleG() {
		return (color, x, y) -> Color4F.grayscaleG(color);
	}
	
	/**
	 * Returns a {@code Color4FPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.lightness()}.
	 * 
	 * @return a {@code Color4FPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.lightness()}
	 */
	static Color4FPixelOperator grayscaleLightness() {
		return (color, x, y) -> Color4F.grayscaleLightness(color);
	}
	
	/**
	 * Returns a {@code Color4FPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.max()}.
	 * 
	 * @return a {@code Color4FPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.max()}
	 */
	static Color4FPixelOperator grayscaleMax() {
		return (color, x, y) -> Color4F.grayscaleMax(color);
	}
	
	/**
	 * Returns a {@code Color4FPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.min()}.
	 * 
	 * @return a {@code Color4FPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.min()}
	 */
	static Color4FPixelOperator grayscaleMin() {
		return (color, x, y) -> Color4F.grayscaleMin(color);
	}
	
	/**
	 * Returns a {@code Color4FPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.r}.
	 * 
	 * @return a {@code Color4FPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.r}
	 */
	static Color4FPixelOperator grayscaleR() {
		return (color, x, y) -> Color4F.grayscaleR(color);
	}
	
	/**
	 * Returns a {@code Color4FPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.relativeLuminance()}.
	 * 
	 * @return a {@code Color4FPixelOperator} that creates a grayscale representation of {@code color} based on {@code color.relativeLuminance()}
	 */
	static Color4FPixelOperator grayscaleRelativeLuminance() {
		return (color, x, y) -> Color4F.grayscaleRelativeLuminance(color);
	}
	
	/**
	 * Returns a {@code Color4FPixelOperator} that inverts the red, green and blue component values of {@code color}.
	 * 
	 * @return a {@code Color4FPixelOperator} that inverts the red, green and blue component values of {@code color}
	 */
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
	static Color4FPixelOperator redoGammaCorrection(final ColorSpaceF colorSpace) {
		Objects.requireNonNull(colorSpace, "colorSpace == null");
		
		return (color, x, y) -> colorSpace.redoGammaCorrection(color);
	}
	
	/**
	 * Returns a {@code Color4FPixelOperator} that converts {@code color} to its sepia-representation.
	 * 
	 * @return a {@code Color4FPixelOperator} that converts {@code color} to its sepia-representation
	 */
	static Color4FPixelOperator sepia() {
		return (color, x, y) -> Color4F.sepia(color);
	}
	
	/**
	 * Returns a {@code Color4FPixelOperator} that generates a {@link Color4F} instance using a Simplex-based fractional Brownian motion (fBm) algorithm.
	 * <p>
	 * If either {@code baseColor} or {@code bounds} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color4FPixelOperator.simplexFractionalBrownianMotion(baseColor, bounds, 5.0F, 0.5F, 16);
	 * }
	 * </pre>
	 * 
	 * @param baseColor a {@link Color3F} instance that is used as the base color
	 * @param bounds a {@link Rectangle2I} instance that contains the bounds
	 * @return a {@code Color4FPixelOperator} that generates a {@link Color4F} instance using a Simplex-based fractional Brownian motion (fBm) algorithm
	 * @throws NullPointerException thrown if, and only if, either {@code baseColor} or {@code bounds} are {@code null}
	 */
	static Color4FPixelOperator simplexFractionalBrownianMotion(final Color3F baseColor, final Rectangle2I bounds) {
		return simplexFractionalBrownianMotion(baseColor, bounds, 5.0F, 0.5F, 16);
	}
	
	/**
	 * Returns a {@code Color4FPixelOperator} that generates a {@link Color4F} instance using a Simplex-based fractional Brownian motion (fBm) algorithm.
	 * <p>
	 * If either {@code baseColor} or {@code bounds} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param baseColor a {@link Color3F} instance that is used as the base color
	 * @param bounds a {@link Rectangle2I} instance that contains the bounds
	 * @param frequency the frequency to start at
	 * @param gain the amplitude multiplier
	 * @param octaves the number of iterations to perform
	 * @return a {@code Color4FPixelOperator} that generates a {@link Color4F} instance using a Simplex-based fractional Brownian motion (fBm) algorithm
	 * @throws NullPointerException thrown if, and only if, either {@code baseColor} or {@code bounds} are {@code null}
	 */
	static Color4FPixelOperator simplexFractionalBrownianMotion(final Color3F baseColor, final Rectangle2I bounds, final float frequency, final float gain, final int octaves) {
		Objects.requireNonNull(baseColor, "baseColor == null");
		Objects.requireNonNull(bounds, "bounds == null");
		
		final Point2I max = bounds.max();
		final Point2I min = bounds.min();
		
		final float maxX = max.x;
		final float maxY = max.y;
		
		final float minX = min.x;
		final float minY = min.y;
		
		return (color, currentX, currentY) -> {
			Objects.requireNonNull(color, "color == null");
			
			final float x = (currentX - minX) / (maxX - minX);
			final float y = (currentY - minY) / (maxY - minY);
			
			final float noise = SimplexNoiseF.fractionalBrownianMotionXY(x, y, frequency, gain, 0.0F, 1.0F, octaves);
			
			return new Color4F(Color3F.multiply(baseColor, noise), color.a);
		};
	}
	
	/**
	 * Returns a {@code Color4FPixelOperator} that applies a tone map operator to {@code color}.
	 * 
	 * @param relativeLuminanceMax the maximum relative luminance
	 * @return a {@code Color4FPixelOperator} that applies a tone map operator to {@code color}
	 */
	static Color4FPixelOperator toneMap(final float relativeLuminanceMax) {
		return (color, x, y) -> {
			final float relativeLuminance = color.relativeLuminance();
			final float scale = (1.0F + relativeLuminance / (relativeLuminanceMax * relativeLuminanceMax)) / (1.0F + relativeLuminance);
			
			return new Color4F(Color3F.multiply(new Color3F(color), scale), color.a);
		};
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
	static Color4FPixelOperator undoGammaCorrection(final ColorSpaceF colorSpace) {
		Objects.requireNonNull(colorSpace, "colorSpace == null");
		
		return (color, x, y) -> colorSpace.undoGammaCorrection(color);
	}
}