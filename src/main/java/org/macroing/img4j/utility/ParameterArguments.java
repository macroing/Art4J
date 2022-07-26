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

import java.util.List;
import java.util.Objects;

/**
 * A class that consists exclusively of static methods that checks parameter argument validity.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ParameterArguments {
	private ParameterArguments() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Checks that {@code list} and all of its elements are not {@code null}.
	 * <p>
	 * Returns {@code list}.
	 * <p>
	 * If either {@code list}, an element in {@code list} or {@code name} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param <T> the generic type of {@code list}
	 * @param list the {@code List} to check
	 * @param name the name of the parameter argument used for {@code list}, that will be part of the message for the {@code NullPointerException}
	 * @return {@code list}
	 * @throws NullPointerException thrown if, and only if, either {@code list}, an element in {@code list} or {@code name} are {@code null}
	 */
	public static <T> List<T> requireNonNullList(final List<T> list, final String name) {
		Objects.requireNonNull(name, "name == null");
		Objects.requireNonNull(list, String.format("%s == null", name));
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i) == null) {
				throw new NullPointerException(String.format("%s.get(%d) == null", name, Integer.valueOf(i)));
			}
		}
		
		return list;
	}
	
	/**
	 * Checks that {@code objects} and all of its elements are not {@code null}.
	 * <p>
	 * Returns {@code objects}.
	 * <p>
	 * If either {@code objects}, an element in {@code objects} or {@code name} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param <T> the generic type of {@code objects}
	 * @param objects the array to check
	 * @param name the name of the parameter argument used for {@code objects}, that will be part of the message for the {@code NullPointerException}
	 * @return {@code objects}
	 * @throws NullPointerException thrown if, and only if, either {@code objects}, an element in {@code objects} or {@code name} are {@code null}
	 */
	public static <T> T[] requireNonNullArray(final T[] objects, final String name) {
		Objects.requireNonNull(name, "name == null");
		Objects.requireNonNull(objects, String.format("%s == null", name));
		
		for(int i = 0; i < objects.length; i++) {
			Objects.requireNonNull(objects[i], String.format("%s[%s] == null", name, Integer.toString(i)));
		}
		
		return objects;
	}
	
	/**
	 * Checks that {@code value} is in the range {@code [Math.min(rangeEndA, rangeEndB), Math.max(rangeEndA, rangeEndB)]}.
	 * <p>
	 * Returns {@code value}.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code value} is less than {@code Math.min(rangeEndA, rangeEndB)} or greater than {@code Math.max(rangeEndA, rangeEndB)}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param value the value to check
	 * @param rangeEndA the minimum or maximum value allowed
	 * @param rangeEndB the maximum or minimum value allowed
	 * @param name the name of the variable that will be part of the message of the {@code IllegalArgumentException}
	 * @return {@code value}
	 * @throws IllegalArgumentException thrown if, and only if, {@code value} is less than {@code Math.min(rangeEndA, rangeEndB)} or greater than {@code Math.max(rangeEndA, rangeEndB)}
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	public static int requireRange(final int value, final int rangeEndA, final int rangeEndB, final String name) {
		Objects.requireNonNull(name, "name == null");
		
		final int minimum = Math.min(rangeEndA, rangeEndB);
		final int maximum = Math.max(rangeEndA, rangeEndB);
		
		if(value < minimum) {
			throw new IllegalArgumentException(String.format("%s < %d: %s == %d", name, Integer.valueOf(minimum), name, Integer.valueOf(value)));
		} else if(value > maximum) {
			throw new IllegalArgumentException(String.format("%s > %d: %s == %d", name, Integer.valueOf(maximum), name, Integer.valueOf(value)));
		} else {
			return value;
		}
	}
	
	/**
	 * Checks that {@code valueLHS * valueRHS} is in the range {@code [Math.min(rangeEndA, rangeEndB), Math.max(rangeEndA, rangeEndB)]}.
	 * <p>
	 * Returns {@code value}.
	 * <p>
	 * If either {@code nameLHS} or {@code nameRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the result of {@code valueLHS * valueRHS} overflows, is less than {@code Math.min(rangeEndA, rangeEndB)} or greater than {@code Math.max(rangeEndA, rangeEndB)}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param valueLHS the value on the left-hand side of the multiplication
	 * @param valueRHS the value on the right-hand side of the multiplication
	 * @param rangeEndA the minimum or maximum value allowed
	 * @param rangeEndB the maximum or minimum value allowed
	 * @param nameLHS the name of the variable {@code valueLHS} that will be part of the message of the {@code IllegalArgumentException}
	 * @param nameRHS the name of the variable {@code valueRHS} that will be part of the message of the {@code IllegalArgumentException}
	 * @return {@code value}
	 * @throws IllegalArgumentException thrown if, and only if, the result of {@code valueLHS * valueRHS} overflows, is less than {@code Math.min(rangeEndA, rangeEndB)} or greater than {@code Math.max(rangeEndA, rangeEndB)}
	 * @throws NullPointerException thrown if, and only if, either {@code nameLHS} or {@code nameRHS} are {@code null}
	 */
	public static int requireRangeMultiplyExact(final int valueLHS, final int valueRHS, final int rangeEndA, final int rangeEndB, final String nameLHS, final String nameRHS) {
		Objects.requireNonNull(nameLHS, "nameLHS == null");
		Objects.requireNonNull(nameRHS, "nameRHS == null");
		
		final int minimum = Math.min(rangeEndA, rangeEndB);
		final int maximum = Math.max(rangeEndA, rangeEndB);
		
		final long valueLong = (long)(valueLHS) * (long)(valueRHS);
		
		final int value = (int)(valueLong);
		
		if(value != valueLong) {
			throw new IllegalArgumentException(String.format("%s * %s overflows to %d", nameLHS, nameRHS, Integer.valueOf(value)));
		} else if(value < minimum) {
			throw new IllegalArgumentException(String.format("%s * %s < %d: %s * %s == %d", nameLHS, nameRHS, Integer.valueOf(minimum), nameLHS, nameRHS, Integer.valueOf(value)));
		} else if(value > maximum) {
			throw new IllegalArgumentException(String.format("%s * %s > %d: %s * %s == %d", nameLHS, nameRHS, Integer.valueOf(maximum), nameLHS, nameRHS, Integer.valueOf(value)));
		} else {
			return value;
		}
	}
}