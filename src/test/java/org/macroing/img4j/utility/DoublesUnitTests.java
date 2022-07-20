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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public final class DoublesUnitTests {
	public DoublesUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAbs() {
		assertEquals(1.0D, Doubles.abs(+1.0D));
		assertEquals(1.0D, Doubles.abs(-1.0D));
	}
	
	@Test
	public void testCeil() {
		assertEquals(Math.ceil(0.5D), Doubles.ceil(0.5D));
	}
	
	@Test
	public void testCos() {
		assertEquals(Math.cos(0.5D), Doubles.cos(0.5D));
	}
	
	@Test
	public void testEqualsDoubleDouble() {
		assertTrue(Doubles.equals(1.0D, 1.0D));
		
		assertFalse(Doubles.equals(1.0D, 2.0D));
	}
	
	@Test
	public void testFloor() {
		assertEquals(Math.floor(0.5D), Doubles.floor(0.5D));
	}
	
	@Test
	public void testIsZero() {
		assertTrue(Doubles.isZero(+0.0D));
		assertTrue(Doubles.isZero(-0.0D));
		
		assertFalse(Doubles.isZero(Double.NaN));
		assertFalse(Doubles.isZero(1.0D));
	}
	
	@Test
	public void testLerp() {
//		Interpolation:
		assertEquals(1.0D, Doubles.lerp(1.0D, 2.0D, +0.0D));
		assertEquals(1.5D, Doubles.lerp(1.0D, 2.0D, +0.5D));
		assertEquals(2.0D, Doubles.lerp(1.0D, 2.0D, +1.0D));
		
//		Extrapolation:
		assertEquals(0.0D, Doubles.lerp(1.0D, 2.0D, -1.0D));
		assertEquals(3.0D, Doubles.lerp(1.0D, 2.0D, +2.0D));
	}
	
	@Test
	public void testMaxDoubleDouble() {
		assertEquals(2.0D, Doubles.max(1.0D, 2.0D));
	}
	
	@Test
	public void testMaxDoubleDoubleDouble() {
		assertEquals(3.0D, Doubles.max(1.0D, 2.0D, 3.0D));
	}
	
	@Test
	public void testMaxDoubleDoubleDoubleDouble() {
		assertEquals(4.0D, Doubles.max(1.0D, 2.0D, 3.0D, 4.0D));
	}
	
	@Test
	public void testMinDoubleDouble() {
		assertEquals(1.0D, Doubles.min(1.0D, 2.0D));
	}
	
	@Test
	public void testMinDoubleDoubleDouble() {
		assertEquals(1.0D, Doubles.min(1.0D, 2.0D, 3.0D));
	}
	
	@Test
	public void testMinDoubleDoubleDoubleDouble() {
		assertEquals(1.0D, Doubles.min(1.0D, 2.0D, 3.0D, 4.0D));
	}
	
	@Test
	public void testNextDown() {
		assertEquals(Math.nextDown(0.5D), Doubles.nextDown(0.5D));
	}
	
	@Test
	public void testNextUp() {
		assertEquals(Math.nextUp(0.5D), Doubles.nextUp(0.5D));
	}
	
	@Test
	public void testRint() {
		assertEquals(0.0D, Doubles.rint(0.00D));
		assertEquals(0.0D, Doubles.rint(0.25D));
		assertEquals(0.0D, Doubles.rint(0.50D));
		assertEquals(1.0D, Doubles.rint(0.75D));
		assertEquals(1.0D, Doubles.rint(1.00D));
	}
	
	@Test
	public void testSaturateDouble() {
		assertEquals(0.0D, Doubles.saturate(-1.0D));
		assertEquals(0.5D, Doubles.saturate(+0.5D));
		assertEquals(1.0D, Doubles.saturate(+2.0D));
	}
	
	@Test
	public void testSaturateDoubleDoubleDouble() {
		assertEquals(2.0D, Doubles.saturate(1.0D, 2.0D, 3.0D));
		assertEquals(2.0D, Doubles.saturate(1.0D, 3.0D, 2.0D));
		
		assertEquals(2.5D, Doubles.saturate(2.5D, 2.0D, 3.0D));
		assertEquals(2.5D, Doubles.saturate(2.5D, 3.0D, 2.0D));
		
		assertEquals(3.0D, Doubles.saturate(4.0D, 2.0D, 3.0D));
		assertEquals(3.0D, Doubles.saturate(4.0D, 3.0D, 2.0D));
	}
	
	@Test
	public void testSin() {
		assertEquals(Math.sin(0.5D), Doubles.sin(0.5D));
	}
	
	@Test
	public void testSqrt() {
		assertEquals(Math.sqrt(0.5D), Doubles.sqrt(0.5D));
	}
	
	@Test
	public void testToDegrees() {
		assertEquals(Math.toDegrees(0.5D), Doubles.toDegrees(0.5D));
	}
	
	@Test
	public void testToRadians() {
		assertEquals(Math.toRadians(0.5D), Doubles.toRadians(0.5D));
	}
}