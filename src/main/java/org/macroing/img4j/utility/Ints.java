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
}