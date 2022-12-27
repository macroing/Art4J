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
package org.macroing.art4j.kernel;

import java.util.Arrays;
import java.util.Objects;

import org.macroing.java.lang.Doubles;
import org.macroing.java.util.Randoms;

/**
 * A {@code ConvolutionKernelND} is a convolution kernel with N x N {@code double}-based elements in N rows and N columns.
 * <p>
 * N has to be a positive and odd number. Some valid numbers are 1, 3, 5 and 7.
 * <p>
 * This class is immutable and therefore suitable for concurrent use without external synchronization.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConvolutionKernelND {
	/**
	 * A {@code ConvolutionKernelND} instance with 3 rows and 3 columns that performs a box blur effect.
	 */
	public static final ConvolutionKernelND BOX_BLUR_3 = new ConvolutionKernelND(0.0D, 1.0D / 9.0D, new double[] {1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D});
	
	/**
	 * A {@code ConvolutionKernelND} instance with 3 rows and 3 columns that performs an emboss effect.
	 */
	public static final ConvolutionKernelND EMBOSS_3 = new ConvolutionKernelND(0.5D, 1.0D, new double[] {-1.0D, -1.0D, 0.0D, -1.0D, 0.0D, 1.0D, 0.0D, 1.0D, 1.0D});
	
	/**
	 * A {@code ConvolutionKernelND} instance with 3 rows and 3 columns that performs a Gaussian blur effect.
	 */
	public static final ConvolutionKernelND GAUSSIAN_BLUR_3 = new ConvolutionKernelND(0.0D, 1.0D / 16.0D, new double[] {1.0D, 2.0D, 1.0D, 2.0D, 4.0D, 2.0D, 1.0D, 2.0D, 1.0D});
	
	/**
	 * A {@code ConvolutionKernelND} instance with 5 rows and 5 columns that performs a Gaussian blur effect.
	 */
	public static final ConvolutionKernelND GAUSSIAN_BLUR_5 = new ConvolutionKernelND(0.0D, 1.0D / 256.0D, new double[] {1.0D, 4.0D, 6.0D, 4.0D, 1.0D, 4.0D, 16.0D, 24.0D, 16.0D, 4.0D, 6.0D, 24.0D, 36.0D, 24.0D, 6.0D, 4.0D, 16.0D, 24.0D, 16.0D, 4.0D, 1.0D, 4.0D, 6.0D, 4.0D, 1.0D});
	
	/**
	 * A {@code ConvolutionKernelND} instance with 3 rows and 3 columns that performs a gradient effect in the horizontal direction.
	 */
	public static final ConvolutionKernelND GRADIENT_HORIZONTAL_3 = new ConvolutionKernelND(0.0D, 1.0D, new double[] {-1.0D, -1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D});
	
	/**
	 * A {@code ConvolutionKernelND} instance with 3 rows and 3 columns that performs a gradient effect in the vertical direction.
	 */
	public static final ConvolutionKernelND GRADIENT_VERTICAL_3 = new ConvolutionKernelND(0.0D, 1.0D, new double[] {-1.0D, 0.0D, 1.0D, -1.0D, 0.0D, 1.0D, -1.0D, 0.0D, 1.0D});
	
	/**
	 * A {@code ConvolutionKernelND} instance with 3 rows and 3 columns that performs no effect.
	 */
	public static final ConvolutionKernelND IDENTITY_3 = new ConvolutionKernelND(0.0D, 1.0D, new double[] {0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D});
	
	/**
	 * A {@code ConvolutionKernelND} instance with 5 rows and 5 columns that performs no effect.
	 */
	public static final ConvolutionKernelND IDENTITY_5 = new ConvolutionKernelND(0.0D, 1.0D, new double[] {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D});
	
	/**
	 * A {@code ConvolutionKernelND} instance with 3 rows and 3 columns that performs a ridge detection effect.
	 */
	public static final ConvolutionKernelND RIDGE_DETECTION_3 = new ConvolutionKernelND(0.0D, 1.0D, new double[] {-1.0D, -1.0D, -1.0D, -1.0D, 8.0D, -1.0D, -1.0D, -1.0D, -1.0D});
	
	/**
	 * A {@code ConvolutionKernelND} instance with 3 rows and 3 columns that performs a sharpen effect.
	 */
	public static final ConvolutionKernelND SHARPEN_3 = new ConvolutionKernelND(0.0D, 1.0D, new double[] {-1.0D, -1.0D, -1.0D, -1.0D, 9.0D, -1.0D, -1.0D, -1.0D, -1.0D});
	
	/**
	 * A {@code ConvolutionKernelND} instance with 5 rows and 5 columns that performs an unsharp masking effect.
	 */
	public static final ConvolutionKernelND UNSHARP_MASKING_5 = new ConvolutionKernelND(0.0D, -1.0D / 256.0D, new double[] {1.0D, 4.0D, 6.0D, 4.0D, 1.0D, 4.0D, 16.0D, 24.0D, 16.0D, 4.0D, 6.0D, 24.0D, -476.0D, 24.0D, 6.0D, 4.0D, 16.0D, 24.0D, 16.0D, 4.0D, 1.0D, 4.0D, 6.0D, 4.0D, 1.0D});
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final int MAX_RESOLUTION = 46339;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final double bias;
	private final double factor;
	private final double[] elements;
	private final int resolution;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConvolutionKernelND} instance.
	 * <p>
	 * If {@code elements} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code elements.length} is invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * The value of {@code sqrt(elements.length)} must be a mathematical integer. The mathematical integer must be positive and odd.
	 * <p>
	 * Any modifications made to {@code elements} post construction, will not affect the constructed {@code ConvolutionKernelND} instance.
	 * 
	 * @param bias the bias to use
	 * @param factor the factor to use
	 * @param elements a {@code double[]} that contains the elements to use
	 * @throws IllegalArgumentException thrown if, and only if, {@code elements.length} is invalid
	 * @throws NullPointerException thrown if, and only if, {@code elements} is {@code null}
	 */
	public ConvolutionKernelND(final double bias, final double factor, final double[] elements) {
		this.bias = bias;
		this.factor = factor;
		this.elements = doRequireValidElements(elements);
		this.resolution = (int)(Doubles.sqrt(this.elements.length));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Compares {@code object} to this {@code ConvolutionKernelND} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConvolutionKernelND}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ConvolutionKernelND} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConvolutionKernelND}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConvolutionKernelND)) {
			return false;
		} else if(!Doubles.equals(this.bias, ConvolutionKernelND.class.cast(object).bias)) {
			return false;
		} else if(!Doubles.equals(this.factor, ConvolutionKernelND.class.cast(object).factor)) {
			return false;
		} else if(!Arrays.equals(this.elements, ConvolutionKernelND.class.cast(object).elements)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the bias associated with this {@code ConvolutionKernelND} instance.
	 * <p>
	 * This is the same as Offset in Gimp.
	 * 
	 * @return the bias associated with this {@code ConvolutionKernelND} instance
	 */
	public double getBias() {
		return this.bias;
	}
	
	/**
	 * Returns the factor associated with this {@code ConvolutionKernelND} instance.
	 * <p>
	 * This is the same as the reciprocal value of the Divisor in Gimp.
	 * 
	 * @return the factor associated with this {@code ConvolutionKernelND} instance
	 */
	public double getFactor() {
		return this.factor;
	}
	
	/**
	 * Returns a {@code double[]} that contains a copy of the elements associated with this {@code ConvolutionKernelND} instance.
	 * <p>
	 * Any modifications made to the returned {@code double[]}, will not affect this {@code ConvolutionKernelND} instance.
	 * 
	 * @return a {@code double[]} that contains a copy of the elements associated with this {@code ConvolutionKernelND} instance
	 */
	public double[] getElements() {
		return this.elements.clone();
	}
	
	/**
	 * Returns the resolution of this {@code ConvolutionKernelND} instance.
	 * <p>
	 * This is the same as the number N mentioned elsewhere in the documentation.
	 * 
	 * @return the resolution of this {@code ConvolutionKernelND} instance
	 */
	public int getResolution() {
		return this.resolution;
	}
	
	/**
	 * Returns a hash code for this {@code ConvolutionKernelND} instance.
	 * 
	 * @return a hash code for this {@code ConvolutionKernelND} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Double.valueOf(this.bias), Double.valueOf(this.factor), Integer.valueOf(Arrays.hashCode(this.elements)));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new random {@code ConvolutionKernelND} instance with a resolution of {@code resolution}.
	 * <p>
	 * If {@code resolution < 1}, {@code resolution % 2 == 0} or {@code resolution > 46339}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param resolution the resolution of the {@code ConvolutionKernelND} instance and is the same as the number N mentioned elsewhere in the documentation
	 * @return a new random {@code ConvolutionKernelND} instance with a resolution of {@code resolution}
	 * @throws IllegalArgumentException thrown if, and only if, {@code resolution < 1}, {@code resolution % 2 == 0} or {@code resolution > 46339}
	 */
	public static ConvolutionKernelND random(final int resolution) {
		return random(resolution, Randoms.nextBoolean(), false, Randoms.nextBoolean(), Randoms.nextBoolean());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static ConvolutionKernelND random(final int resolution, final boolean isBiasBasedOnRandomDouble, final boolean isElementTotalZero, final boolean isFactorBasedOnElementTotal, final boolean isFactorBasedOnRandomDouble) {
		if(resolution < 1) {
			throw new IllegalArgumentException(String.format("The value of resolution, %d, is invalid. It must be greater than or equal to 1.", Integer.valueOf(resolution)));
		}
		
		if(resolution % 2 == 0) {
			throw new IllegalArgumentException(String.format("The value of resolution, %d, is invalid. It cannot be even.", Integer.valueOf(resolution)));
		}
		
		if(resolution > MAX_RESOLUTION) {
			throw new IllegalArgumentException(String.format("The value of resolution, %d, is invalid. It must be less than or equal to 46339.", Integer.valueOf(resolution)));
		}
		
		final double[] elements = new double[resolution * resolution];
		
		final int middle = (resolution - 1) / 2;
		
		for(int y = 0; y < resolution; y++) {
			for(int x = 0; x < resolution; x++) {
				if(x == middle && y == middle) {
					elements[y * resolution + x] = 1.0D;
				} else {
					elements[y * resolution + x] = Randoms.nextDouble(-1.0D, Doubles.nextUp(1.0D));
				}
			}
		}
		
		final double elementTotal = isElementTotalZero ? 0.0D : Arrays.stream(elements).sum();
		
		final double bias = isBiasBasedOnRandomDouble ? Randoms.nextDouble(0.0D, Doubles.nextUp(1.0D)) : 0.0D;
		final double factor = isFactorBasedOnElementTotal ? Doubles.isZero(elementTotal) ? 1.0D : 1.0D / elementTotal : isFactorBasedOnRandomDouble ? Randoms.nextDouble(0.0D, Doubles.nextUp(1.0D)) : 1.0D;
		
		return new ConvolutionKernelND(bias, factor, elements);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static double[] doRequireValidElements(final double[] elements) {
//		Check that 'elements' is not 'null':
		Objects.requireNonNull(elements, "elements == null");
		
//		Check that 'elements.length' is odd:
		if(elements.length % 2 == 0) {
			throw new IllegalArgumentException(String.format("The value of elements.length, %d, is invalid. It cannot be even.", Integer.valueOf(elements.length)));
		}
		
//		Compute the square root of 'elements.length' and the value that is closest to it and represents a mathematical integer:
		final double vA = Doubles.sqrt(elements.length);
		final double vB = Doubles.rint(vA);
		
//		Check that the square root of 'elements.length' and the value that is closest to it and represents a mathematical integer are equal:
		if(!Doubles.equals(vA, vB)) {
			throw new IllegalArgumentException(String.format("The value of elements.length, %d, is invalid. Math.sqrt(elements.length) must return a value that is a mathematical integer.", Integer.valueOf(elements.length)));
		}
		
		return elements.clone();
	}
}