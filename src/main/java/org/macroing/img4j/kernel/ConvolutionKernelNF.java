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
package org.macroing.img4j.kernel;

import java.util.Arrays;
import java.util.Objects;

import org.macroing.img4j.utility.Floats;
import org.macroing.img4j.utility.Randoms;

/**
 * A {@code ConvolutionKernelNF} is a convolution kernel with N x N {@code float}-based elements in N rows and N columns.
 * <p>
 * N has to be a positive and odd number. Some valid numbers are 1, 3, 5 and 7.
 * <p>
 * This class is immutable and therefore suitable for concurrent use without external synchronization.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConvolutionKernelNF {
	/**
	 * A {@code ConvolutionKernelNF} instance with 3 rows and 3 columns that performs a box blur effect.
	 */
	public static final ConvolutionKernelNF BOX_BLUR_3 = new ConvolutionKernelNF(0.0F, 1.0F / 9.0F, new float[] {1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F});
	
	/**
	 * A {@code ConvolutionKernelNF} instance with 3 rows and 3 columns that performs an emboss effect.
	 */
	public static final ConvolutionKernelNF EMBOSS_3 = new ConvolutionKernelNF(0.5F, 1.0F, new float[] {-1.0F, -1.0F, 0.0F, -1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F});
	
	/**
	 * A {@code ConvolutionKernelNF} instance with 3 rows and 3 columns that performs a Gaussian blur effect.
	 */
	public static final ConvolutionKernelNF GAUSSIAN_BLUR_3 = new ConvolutionKernelNF(0.0F, 1.0F / 16.0F, new float[] {1.0F, 2.0F, 1.0F, 2.0F, 4.0F, 2.0F, 1.0F, 2.0F, 1.0F});
	
	/**
	 * A {@code ConvolutionKernelNF} instance with 5 rows and 5 columns that performs a Gaussian blur effect.
	 */
	public static final ConvolutionKernelNF GAUSSIAN_BLUR_5 = new ConvolutionKernelNF(0.0F, 1.0F / 256.0F, new float[] {1.0F, 4.0F, 6.0F, 4.0F, 1.0F, 4.0F, 16.0F, 24.0F, 16.0F, 4.0F, 6.0F, 24.0F, 36.0F, 24.0F, 6.0F, 4.0F, 16.0F, 24.0F, 16.0F, 4.0F, 1.0F, 4.0F, 6.0F, 4.0F, 1.0F});
	
	/**
	 * A {@code ConvolutionKernelNF} instance with 3 rows and 3 columns that performs a gradient effect in the horizontal direction.
	 */
	public static final ConvolutionKernelNF GRADIENT_HORIZONTAL_3 = new ConvolutionKernelNF(0.0F, 1.0F, new float[] {-1.0F, -1.0F, -1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F});
	
	/**
	 * A {@code ConvolutionKernelNF} instance with 3 rows and 3 columns that performs a gradient effect in the vertical direction.
	 */
	public static final ConvolutionKernelNF GRADIENT_VERTICAL_3 = new ConvolutionKernelNF(0.0F, 1.0F, new float[] {-1.0F, 0.0F, 1.0F, -1.0F, 0.0F, 1.0F, -1.0F, 0.0F, 1.0F});
	
	/**
	 * A {@code ConvolutionKernelNF} instance with 3 rows and 3 columns that performs no effect.
	 */
	public static final ConvolutionKernelNF IDENTITY_3 = new ConvolutionKernelNF(0.0F, 1.0F, new float[] {0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F});
	
	/**
	 * A {@code ConvolutionKernelNF} instance with 5 rows and 5 columns that performs no effect.
	 */
	public static final ConvolutionKernelNF IDENTITY_5 = new ConvolutionKernelNF(0.0F, 1.0F, new float[] {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F});
	
	/**
	 * A {@code ConvolutionKernelNF} instance with 3 rows and 3 columns that performs a ridge detection effect.
	 */
	public static final ConvolutionKernelNF RIDGE_DETECTION_3 = new ConvolutionKernelNF(0.0F, 1.0F, new float[] {-1.0F, -1.0F, -1.0F, -1.0F, 8.0F, -1.0F, -1.0F, -1.0F, -1.0F});
	
	/**
	 * A {@code ConvolutionKernelNF} instance with 3 rows and 3 columns that performs a sharpen effect.
	 */
	public static final ConvolutionKernelNF SHARPEN_3 = new ConvolutionKernelNF(0.0F, 1.0F, new float[] {-1.0F, -1.0F, -1.0F, -1.0F, 9.0F, -1.0F, -1.0F, -1.0F, -1.0F});
	
	/**
	 * A {@code ConvolutionKernelNF} instance with 5 rows and 5 columns that performs an unsharp masking effect.
	 */
	public static final ConvolutionKernelNF UNSHARP_MASKING_5 = new ConvolutionKernelNF(0.0F, -1.0F / 256.0F, new float[] {1.0F, 4.0F, 6.0F, 4.0F, 1.0F, 4.0F, 16.0F, 24.0F, 16.0F, 4.0F, 6.0F, 24.0F, -476.0F, 24.0F, 6.0F, 4.0F, 16.0F, 24.0F, 16.0F, 4.0F, 1.0F, 4.0F, 6.0F, 4.0F, 1.0F});
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final float bias;
	private final float factor;
	private final float[] elements;
	private final int resolution;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConvolutionKernelNF} instance.
	 * <p>
	 * If {@code elements} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code elements.length} is invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * The value of {@code sqrt(elements.length)} must be a mathematical integer. The mathematical integer must be positive and odd.
	 * <p>
	 * Any modifications made to {@code elements} post construction, will not affect the constructed {@code ConvolutionKernelNF} instance.
	 * 
	 * @param bias the bias to use
	 * @param factor the factor to use
	 * @param elements a {@code float[]} that contains the elements to use
	 * @throws IllegalArgumentException thrown if, and only if, {@code elements.length} is invalid
	 * @throws NullPointerException thrown if, and only if, {@code elements} is {@code null}
	 */
	public ConvolutionKernelNF(final float bias, final float factor, final float[] elements) {
		this.bias = bias;
		this.factor = factor;
		this.elements = doRequireValidElements(elements);
		this.resolution = (int)(Floats.sqrt(this.elements.length));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Compares {@code object} to this {@code ConvolutionKernelNF} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConvolutionKernelNF}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ConvolutionKernelNF} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConvolutionKernelNF}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConvolutionKernelNF)) {
			return false;
		} else if(!Floats.equals(this.bias, ConvolutionKernelNF.class.cast(object).bias)) {
			return false;
		} else if(!Floats.equals(this.factor, ConvolutionKernelNF.class.cast(object).factor)) {
			return false;
		} else if(!Arrays.equals(this.elements, ConvolutionKernelNF.class.cast(object).elements)) {
			return false;
		} else if(this.resolution != ConvolutionKernelNF.class.cast(object).resolution) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the bias associated with this {@code ConvolutionKernelNF} instance.
	 * <p>
	 * This is the same as Offset in Gimp.
	 * 
	 * @return the bias associated with this {@code ConvolutionKernelNF} instance
	 */
	public float getBias() {
		return this.bias;
	}
	
	/**
	 * Returns the factor associated with this {@code ConvolutionKernelNF} instance.
	 * <p>
	 * This is the same as the reciprocal value of the Divisor in Gimp.
	 * 
	 * @return the factor associated with this {@code ConvolutionKernelNF} instance
	 */
	public float getFactor() {
		return this.factor;
	}
	
	/**
	 * Returns a {@code float[]} that contains a copy of the elements associated with this {@code ConvolutionKernelNF} instance.
	 * <p>
	 * Any modifications made to the returned {@code float[]}, will not affect this {@code ConvolutionKernelNF} instance.
	 * 
	 * @return a {@code float[]} that contains a copy of the elements associated with this {@code ConvolutionKernelNF} instance
	 */
	public float[] getElements() {
		return this.elements.clone();
	}
	
	/**
	 * Returns the resolution of this {@code ConvolutionKernelNF} instance.
	 * <p>
	 * This is the same as the number N mentioned elsewhere in the documentation.
	 * 
	 * @return the resolution of this {@code ConvolutionKernelNF} instance
	 */
	public int getResolution() {
		return this.resolution;
	}
	
	/**
	 * Returns a hash code for this {@code ConvolutionKernelNF} instance.
	 * 
	 * @return a hash code for this {@code ConvolutionKernelNF} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Float.valueOf(this.bias), Float.valueOf(this.factor), Integer.valueOf(Arrays.hashCode(this.elements)), Integer.valueOf(this.resolution));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new random {@code ConvolutionKernelNF} instance with a resolution of {@code resolution}.
	 * <p>
	 * If {@code resolution < 1}, {@code resolution % 2 == 0} or {@code resolution * resolution < 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param resolution the resolution of the {@code ConvolutionKernelNF} instance and is the same as the number N mentioned elsewhere in the documentation
	 * @return a new random {@code ConvolutionKernelNF} instance with a resolution of {@code resolution}
	 * @throws IllegalArgumentException thrown if, and only if, {@code resolution < 1}, {@code resolution % 2 == 0} or {@code resolution * resolution < 1}
	 */
	public static ConvolutionKernelNF random(final int resolution) {
		if(resolution < 1) {
			throw new IllegalArgumentException(String.format("The value of resolution, %d, is invalid. It must be greater than or equal to 1.", Integer.valueOf(resolution)));
		}
		
		if(resolution % 2 == 0) {
			throw new IllegalArgumentException(String.format("The value of resolution, %d, is invalid. It cannot be even.", Integer.valueOf(resolution)));
		}
		
		if(resolution * resolution < 1) {
			throw new IllegalArgumentException(String.format("The value of resolution * resolution, %d, is invalid. It must be greater than or equal to 1.", Integer.valueOf(resolution * resolution)));
		}
		
		final float[] elements = new float[resolution * resolution];
		
		final int middle = (resolution - 1) / 2;
		
		for(int y = 0; y < resolution; y++) {
			for(int x = 0; x < resolution; x++) {
				if(x == middle && y == middle) {
					elements[y * resolution + x] = 1.0F;
				} else {
					elements[y * resolution + x] = Randoms.nextFloat(-1.0F, 1.0F);
				}
			}
		}
		
		float elementTotal = 0.0F;
		
		for(final float element : elements) {
			elementTotal += element;
		}
		
		final boolean isBiasBasedOnRandomDouble = Randoms.nextBoolean();
		final boolean isFactorBasedOnElementTotal = Randoms.nextBoolean();
		final boolean isFactorBasedOnRandomDouble = Randoms.nextBoolean();
		
		final float bias = isBiasBasedOnRandomDouble ? Randoms.nextFloat() : 0.0F;
		final float factor = isFactorBasedOnElementTotal ? Floats.isZero(elementTotal) ? 1.0F : 1.0F / elementTotal : isFactorBasedOnRandomDouble ? Randoms.nextFloat() : 1.0F;
		
		return new ConvolutionKernelNF(bias, factor, elements);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static float[] doRequireValidElements(final float[] elements) {
//		Check that 'elements' is not 'null':
		Objects.requireNonNull(elements, "elements == null");
		
//		Check that 'elements.length' is odd:
		if(elements.length % 2 == 0) {
			throw new IllegalArgumentException(String.format("The value of elements.length, %d, is invalid. It cannot be even.", Integer.valueOf(elements.length)));
		}
		
//		Compute the square root of 'elements.length' and the value that is closest to it and represents a mathematical integer:
		final float vA = Floats.sqrt(elements.length);
		final float vB = Floats.rint(vA);
		
//		Check that the square root of 'elements.length' and the value that is closest to it and represents a mathematical integer are equal:
		if(!Floats.equals(vA, vB)) {
			throw new IllegalArgumentException(String.format("The value of elements.length, %d, is invalid. Floats.sqrt(elements.length) must return a value that is a mathematical integer.", Integer.valueOf(elements.length)));
		}
		
//		Compute the resolution by converting the square root of 'elements.length' to an 'int':
		final int kR = (int)(vA);
		
//		Check that the resolution is odd:
		if(kR % 2 == 0) {
			throw new IllegalArgumentException(String.format("The value of elements.length, %d, is invalid. Floats.sqrt(elements.length) must return a value that is odd.", Integer.valueOf(elements.length)));
		}
		
		return elements.clone();
	}
}