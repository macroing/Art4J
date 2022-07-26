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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public final class ParameterArgumentsUnitTests {
	public ParameterArgumentsUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testRequireNonNullArray() {
		assertThrows(NullPointerException.class, () -> ParameterArguments.requireNonNullArray(null, "array"));
		assertThrows(NullPointerException.class, () -> ParameterArguments.requireNonNullArray(new String[] {}, null));
		assertThrows(NullPointerException.class, () -> ParameterArguments.requireNonNullArray(new String[] {"A", null, "C"}, "array"));
		
		assertArrayEquals(new String[] {"A", "B", "C"}, ParameterArguments.requireNonNullArray(new String[] {"A", "B", "C"}, "array"));
	}
	
	@Test
	public void testRequireNonNullList() {
		assertThrows(NullPointerException.class, () -> ParameterArguments.requireNonNullList(null, "list"));
		assertThrows(NullPointerException.class, () -> ParameterArguments.requireNonNullList(new ArrayList<>(), null));
		assertThrows(NullPointerException.class, () -> ParameterArguments.requireNonNullList(Arrays.asList("A", null, "C"), "list"));
		
		assertEquals(Arrays.asList("A", "B", "C"), ParameterArguments.requireNonNullList(Arrays.asList("A", "B", "C"), "list"));
	}
	
	@Test
	public void testRequireRangeIntIntIntString() {
		assertThrows(NullPointerException.class, () -> ParameterArguments.requireRange(0, 0, 0, null));
		
		assertThrows(IllegalArgumentException.class, () -> ParameterArguments.requireRange(0, 1, 2, "value"));
		assertThrows(IllegalArgumentException.class, () -> ParameterArguments.requireRange(0, 2, 1, "value"));
		assertThrows(IllegalArgumentException.class, () -> ParameterArguments.requireRange(3, 1, 2, "value"));
		assertThrows(IllegalArgumentException.class, () -> ParameterArguments.requireRange(3, 2, 1, "value"));
		
		assertEquals(0, ParameterArguments.requireRange(0, 0, 1, "value"));
	}
	
	@Test
	public void testRequireRangeMultiplyExact() {
		assertThrows(NullPointerException.class, () -> ParameterArguments.requireRangeMultiplyExact(0, 0, 0, 0, "a", null));
		assertThrows(NullPointerException.class, () -> ParameterArguments.requireRangeMultiplyExact(0, 0, 0, 0, null, "b"));
		
		assertThrows(IllegalArgumentException.class, () -> ParameterArguments.requireRangeMultiplyExact(0, 1, 1, 2, "a", "b"));
		assertThrows(IllegalArgumentException.class, () -> ParameterArguments.requireRangeMultiplyExact(0, 1, 2, 1, "a", "b"));
		assertThrows(IllegalArgumentException.class, () -> ParameterArguments.requireRangeMultiplyExact(3, 1, 1, 2, "a", "b"));
		assertThrows(IllegalArgumentException.class, () -> ParameterArguments.requireRangeMultiplyExact(3, 1, 2, 1, "a", "b"));
		assertThrows(IllegalArgumentException.class, () -> ParameterArguments.requireRangeMultiplyExact(Integer.MAX_VALUE, Integer.MAX_VALUE, 2, 1, "a", "b"));
		
		assertEquals(0, ParameterArguments.requireRangeMultiplyExact(0, 1, 0, 1, "a", "b"));
	}
}