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
}