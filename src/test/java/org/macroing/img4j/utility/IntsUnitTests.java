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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public final class IntsUnitTests {
	public IntsUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testLerpIntIntDouble() {
//		Interpolation:
		assertEquals(100, Ints.lerp(100, 200, +0.0D));
		assertEquals(150, Ints.lerp(100, 200, +0.5D));
		assertEquals(200, Ints.lerp(100, 200, +1.0D));
		
//		Extrapolation:
		assertEquals(  0, Ints.lerp(100, 200, -1.0D));
		assertEquals(300, Ints.lerp(100, 200, +2.0D));
	}
	
	@Test
	public void testLerpIntIntFloat() {
//		Interpolation:
		assertEquals(100, Ints.lerp(100, 200, +0.0F));
		assertEquals(150, Ints.lerp(100, 200, +0.5F));
		assertEquals(200, Ints.lerp(100, 200, +1.0F));
		
//		Extrapolation:
		assertEquals(  0, Ints.lerp(100, 200, -1.0F));
		assertEquals(300, Ints.lerp(100, 200, +2.0F));
	}
	
	@Test
	public void testMaxIntInt() {
		assertEquals(2, Ints.max(1, 2));
	}
	
	@Test
	public void testMaxIntIntInt() {
		assertEquals(3, Ints.max(1, 2, 3));
	}
	
	@Test
	public void testMaxIntIntIntInt() {
		assertEquals(4, Ints.max(1, 2, 3, 4));
	}
	
	@Test
	public void testMinIntInt() {
		assertEquals(1, Ints.min(1, 2));
	}
	
	@Test
	public void testMinIntIntInt() {
		assertEquals(1, Ints.min(1, 2, 3));
	}
	
	@Test
	public void testMinIntIntIntInt() {
		assertEquals(1, Ints.min(1, 2, 3, 4));
	}
	
	@Test
	public void testSaturateInt() {
		assertEquals(  0, Ints.saturate(-  1));
		assertEquals(128, Ints.saturate(+128));
		assertEquals(255, Ints.saturate(+256));
	}
	
	@Test
	public void testSaturateIntIntInt() {
		assertEquals(20, Ints.saturate(10, 20, 30));
		assertEquals(20, Ints.saturate(10, 30, 20));
		
		assertEquals(25, Ints.saturate(25, 20, 30));
		assertEquals(25, Ints.saturate(25, 30, 20));
		
		assertEquals(30, Ints.saturate(40, 20, 30));
		assertEquals(30, Ints.saturate(40, 30, 20));
	}
}