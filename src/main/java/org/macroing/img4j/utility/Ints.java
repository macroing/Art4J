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
package org.macroing.img4j.utility;

/**
 * The class {@code Ints} contains methods for performing basic numeric operations.
 * <p>
 * You can think of this class as an extension of {@code Math}. It does what {@code Math} does for the {@code double}, {@code float} and {@code int} types, but for the {@code int} type only. In addition to this it also adds new methods.
 * <p>
 * This class does not contain all methods from {@code Math}. The methods in {@code Math} that deals with the {@code double} type can be found in the class {@link Doubles}. The methods in {@code Math} that deals with the {@code float} type can be found in the class {@link Floats}.
 * <p>
 * The documentation in this class should be comprehensive. But some of the details covered in the documentation of the {@code Math} class may be missing. To get the full documentation for a particular method, you may want to look at the documentation of the corresponding method in the {@code Math} class. This is, of course, only true if the method you're looking at exists in the {@code Math} class.
 * <p>
 * Not all methods in the {@code Math} class that should be added to this class, may have been added yet.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Ints {
	private Ints() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns {@code true} if, and only if, {@code valueLHS * valueRHS} does not overflow, {@code false} otherwise.
	 * 
	 * @param valueLHS the value on the left-hand side of the multiplication
	 * @param valueRHS the value on the right-hand side of the multiplication
	 * @return {@code true} if, and only if, {@code valueLHS * valueRHS} does not overflow, {@code false} otherwise
	 */
	public static boolean canMultiplyExact(final int valueLHS, final int valueRHS) {
		final long valueLong = (long)(valueLHS) * (long)(valueRHS);
		
		final int value = (int)(valueLong);
		
		return value == valueLong;
	}
	
	/**
	 * Returns the absolute version of {@code value}.
	 * <p>
	 * If the argument is not negative, the argument is returned. If the argument is negative, the negation of the argument is returned.
	 * <p>
	 * Note that if the argument is equal to the value of {@code Integer.MIN_VALUE}, the most negative representable {@code int} value, the result is that same value, which is negative.
	 * 
	 * @param value an {@code int} value
	 * @return the absolute version of {@code value}
	 * @see Math#abs(int)
	 */
	public static int abs(final int value) {
		return Math.abs(value);
	}
	
	/**
	 * Returns the floor modulus of the {@code int} arguments. 
	 * 
	 * @param x the dividend
	 * @param y the divisor
	 * @return the floor modulus of the {@code int} arguments
	 */
	public static int floorMod(final int x, final int y) {
		return Math.floorMod(x, y);
	}
	
	/**
	 * Performs a linear interpolation operation on the supplied values.
	 * <p>
	 * Returns the result of the linear interpolation operation.
	 * 
	 * @param a an {@code int} value
	 * @param b an {@code int} value
	 * @param t the factor
	 * @return the result of the linear interpolation operation
	 */
	public static int lerp(final int a, final int b, final double t) {
		return (int)((1.0D - t) * a + t * b);
	}
	
	/**
	 * Performs a linear interpolation operation on the supplied values.
	 * <p>
	 * Returns the result of the linear interpolation operation.
	 * 
	 * @param a an {@code int} value
	 * @param b an {@code int} value
	 * @param t the factor
	 * @return the result of the linear interpolation operation
	 */
	public static int lerp(final int a, final int b, final float t) {
		return (int)((1.0F - t) * a + t * b);
	}
	
	/**
	 * Returns the greater value of {@code a} and {@code b}.
	 * <p>
	 * The result is the argument closer to the value of {@code Integer.MAX_VALUE}.
	 * <p>
	 * If the arguments have the same value, the result is that same value.
	 * 
	 * @param a a value
	 * @param b a value
	 * @return the greater value of {@code a} and {@code b}
	 * @see Math#max(int, int)
	 */
	public static int max(final int a, final int b) {
		return Math.max(a, b);
	}
	
	/**
	 * Returns the greater value of {@code a}, {@code b} and {@code c}.
	 * <p>
	 * The result is the argument closer to the value of {@code Integer.MAX_VALUE}.
	 * <p>
	 * If the arguments have the same value, the result is that same value.
	 * 
	 * @param a a value
	 * @param b a value
	 * @param c a value
	 * @return the greater value of {@code a}, {@code b} and {@code c}
	 */
	public static int max(final int a, final int b, final int c) {
		return max(max(a, b), c);
	}
	
	/**
	 * Returns the greater value of {@code a}, {@code b}, {@code c} and {@code d}.
	 * <p>
	 * The result is the argument closer to the value of {@code Integer.MAX_VALUE}.
	 * <p>
	 * If the arguments have the same value, the result is that same value.
	 * 
	 * @param a a value
	 * @param b a value
	 * @param c a value
	 * @param d a value
	 * @return the greater value of {@code a}, {@code b}, {@code c} and {@code d}
	 */
	public static int max(final int a, final int b, final int c, final int d) {
		return max(max(a, b), max(c, d));
	}
	
	/**
	 * Returns the smaller value of {@code a} and {@code b}.
	 * <p>
	 * The result the argument closer to the value of {@code Integer.MIN_VALUE}.
	 * <p>
	 * If the arguments have the same value, the result is that same value.
	 * 
	 * @param a a value
	 * @param b a value
	 * @return the smaller value of {@code a} and {@code b}
	 * @see Math#min(int, int)
	 */
	public static int min(final int a, final int b) {
		return Math.min(a, b);
	}
	
	/**
	 * Returns the smaller value of {@code a}, {@code b} and {@code c}.
	 * <p>
	 * The result the argument closer to the value of {@code Integer.MIN_VALUE}.
	 * <p>
	 * If the arguments have the same value, the result is that same value.
	 * 
	 * @param a a value
	 * @param b a value
	 * @param c a value
	 * @return the smaller value of {@code a}, {@code b} and {@code c}
	 * @see Math#min(int, int)
	 */
	public static int min(final int a, final int b, final int c) {
		return min(min(a, b), c);
	}
	
	/**
	 * Returns the smaller value of {@code a}, {@code b}, {@code c} and {@code d}.
	 * <p>
	 * The result the argument closer to the value of {@code Integer.MIN_VALUE}.
	 * <p>
	 * If the arguments have the same value, the result is that same value.
	 * 
	 * @param a a value
	 * @param b a value
	 * @param c a value
	 * @param d a value
	 * @return the smaller value of {@code a}, {@code b}, {@code c} and {@code d}
	 * @see Math#min(int, int)
	 */
	public static int min(final int a, final int b, final int c, final int d) {
		return min(min(a, b), min(c, d));
	}
	
	/**
	 * Returns {@code value} or a saturated (or clamped) representation of it.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Ints.saturate(value, 0, 255);
	 * }
	 * </pre>
	 * 
	 * @param value the value to saturate (or clamp)
	 * @return {@code value} or a saturated (or clamped) representation of it
	 */
	public static int saturate(final int value) {
		return saturate(value, 0, 255);
	}
	
	/**
	 * Returns {@code value} or a saturated (or clamped) representation of it.
	 * <p>
	 * If {@code value} is less than {@code Ints.min(valueMinMax, valueMaxMin)}, {@code Ints.min(valueMinMax, valueMaxMin)} will be returned. If {@code value} is greater than {@code Ints.max(valueMinMax, valueMaxMin)}, {@code Ints.max(valueMinMax, valueMaxMin)} will be returned. Otherwise {@code value} will be returned.
	 * 
	 * @param value the value to saturate (or clamp)
	 * @param valueMinMax the minimum or maximum value
	 * @param valueMaxMin the maximum or minimum value
	 * @return {@code value} or a saturated (or clamped) representation of it
	 */
	public static int saturate(final int value, final int valueMinMax, final int valueMaxMin) {
		final int valueMin = min(valueMinMax, valueMaxMin);
		final int valueMax = max(valueMinMax, valueMaxMin);
		
		return max(min(value, valueMax), valueMin);
	}
	
	/**
	 * Returns an {@code int} representation of {@code Doubles.saturate(value) * 255.0D + 0.5D}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Ints.saturateAndScaleToInt(value, 255.0D);
	 * }
	 * </pre>
	 * 
	 * @param value the {@code double} value to saturate and scale to an {@code int}
	 * @return an {@code int} representation of {@code Doubles.saturate(value) * 255.0D + 0.5D}
	 */
	public static int saturateAndScaleToInt(final double value) {
		return saturateAndScaleToInt(value, 255.0D);
	}
	
	/**
	 * Returns an {@code int} representation of {@code Doubles.saturate(value) * scale + 0.5D}.
	 * 
	 * @param value the {@code double} value to saturate and scale to an {@code int}
	 * @param scale the {@code double} value to scale with
	 * @return an {@code int} representation of {@code Doubles.saturate(value) * scale + 0.5D}
	 */
	public static int saturateAndScaleToInt(final double value, final double scale) {
		return (int)(Doubles.saturate(value) * scale + 0.5D);
	}
	
	/**
	 * Returns an {@code int} representation of {@code Floats.saturate(value) * 255.0F + 0.5F}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Ints.saturateAndScaleToInt(value, 255.0F);
	 * }
	 * </pre>
	 * 
	 * @param value the {@code float} value to saturate and scale to an {@code int}
	 * @return an {@code int} representation of {@code Floats.saturate(value) * 255.0F + 0.5F}
	 */
	public static int saturateAndScaleToInt(final float value) {
		return saturateAndScaleToInt(value, 255.0F);
	}
	
	/**
	 * Returns an {@code int} representation of {@code Floats.saturate(value) * scale + 0.5F}.
	 * 
	 * @param value the {@code float} value to saturate and scale to an {@code int}
	 * @param scale the {@code float} value to scale with
	 * @return an {@code int} representation of {@code Floats.saturate(value) * scale + 0.5F}
	 */
	public static int saturateAndScaleToInt(final float value, final float scale) {
		return (int)(Floats.saturate(value) * scale + 0.5F);
	}
}