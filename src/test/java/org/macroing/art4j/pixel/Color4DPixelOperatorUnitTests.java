/**
 * Copyright 2022 J&#246;rgen Lundgren
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import org.macroing.art4j.color.Color4D;

@SuppressWarnings("static-method")
public final class Color4DPixelOperatorUnitTests {
	public Color4DPixelOperatorUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testGrayscaleA() {
		final Color4D a = new Color4D(1.0D, 2.0D, 3.0D, 4.0D);
		final Color4D b = Color4DPixelOperator.grayscaleA().apply(a, 0, 0);
		
		assertEquals(4.0D, b.r);
		assertEquals(4.0D, b.g);
		assertEquals(4.0D, b.b);
		assertEquals(4.0D, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4DPixelOperator.grayscaleA().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleAverage() {
		final Color4D a = new Color4D(1.0D, 2.0D, 3.0D, 4.0D);
		final Color4D b = Color4DPixelOperator.grayscaleAverage().apply(a, 0, 0);
		
		assertEquals(2.0D, b.r);
		assertEquals(2.0D, b.g);
		assertEquals(2.0D, b.b);
		assertEquals(4.0D, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4DPixelOperator.grayscaleAverage().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleB() {
		final Color4D a = new Color4D(1.0D, 2.0D, 3.0D, 4.0D);
		final Color4D b = Color4DPixelOperator.grayscaleB().apply(a, 0, 0);
		
		assertEquals(3.0D, b.r);
		assertEquals(3.0D, b.g);
		assertEquals(3.0D, b.b);
		assertEquals(4.0D, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4DPixelOperator.grayscaleB().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleG() {
		final Color4D a = new Color4D(1.0D, 2.0D, 3.0D, 4.0D);
		final Color4D b = Color4DPixelOperator.grayscaleG().apply(a, 0, 0);
		
		assertEquals(2.0D, b.r);
		assertEquals(2.0D, b.g);
		assertEquals(2.0D, b.b);
		assertEquals(4.0D, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4DPixelOperator.grayscaleG().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleLightness() {
		final Color4D a = new Color4D(1.0D, 2.0D, 3.0D, 4.0D);
		final Color4D b = Color4DPixelOperator.grayscaleLightness().apply(a, 0, 0);
		
		assertEquals(2.0D, b.r);
		assertEquals(2.0D, b.g);
		assertEquals(2.0D, b.b);
		assertEquals(4.0D, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4DPixelOperator.grayscaleLightness().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleMax() {
		final Color4D a = new Color4D(1.0D, 2.0D, 3.0D, 4.0D);
		final Color4D b = Color4DPixelOperator.grayscaleMax().apply(a, 0, 0);
		
		assertEquals(3.0D, b.r);
		assertEquals(3.0D, b.g);
		assertEquals(3.0D, b.b);
		assertEquals(4.0D, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4DPixelOperator.grayscaleMax().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleMin() {
		final Color4D a = new Color4D(1.0D, 2.0D, 3.0D, 4.0D);
		final Color4D b = Color4DPixelOperator.grayscaleMin().apply(a, 0, 0);
		
		assertEquals(1.0D, b.r);
		assertEquals(1.0D, b.g);
		assertEquals(1.0D, b.b);
		assertEquals(4.0D, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4DPixelOperator.grayscaleMin().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleR() {
		final Color4D a = new Color4D(1.0D, 2.0D, 3.0D, 4.0D);
		final Color4D b = Color4DPixelOperator.grayscaleR().apply(a, 0, 0);
		
		assertEquals(1.0D, b.r);
		assertEquals(1.0D, b.g);
		assertEquals(1.0D, b.b);
		assertEquals(4.0D, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4DPixelOperator.grayscaleR().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleRelativeLuminance() {
		final Color4D a = new Color4D(1.0D, 2.0D, 3.0D, 4.0D);
		final Color4D b = Color4DPixelOperator.grayscaleRelativeLuminance().apply(a, 0, 0);
		
		assertEquals(1.0D * 0.212671D + 2.0D * 0.715160D + 3.0D * 0.072169D, b.r);
		assertEquals(1.0D * 0.212671D + 2.0D * 0.715160D + 3.0D * 0.072169D, b.g);
		assertEquals(1.0D * 0.212671D + 2.0D * 0.715160D + 3.0D * 0.072169D, b.b);
		assertEquals(4.0D, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4DPixelOperator.grayscaleRelativeLuminance().apply(null, 0, 0));
	}
	
	@Test
	public void testInvert() {
		final Color4D a = new Color4D(1.0D, 1.0D, 1.0D, 1.0D);
		final Color4D b = Color4DPixelOperator.invert().apply(a, 0, 0);
		
		assertEquals(0.0D, b.r);
		assertEquals(0.0D, b.g);
		assertEquals(0.0D, b.b);
		assertEquals(1.0D, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4DPixelOperator.invert().apply(null, 0, 0));
	}
	
	@Test
	public void testSepia() {
		final Color4D a = new Color4D(1.0D, 2.0D, 3.0D, 4.0D);
		final Color4D b = Color4DPixelOperator.sepia().apply(a, 0, 0);
		
		assertEquals(1.0D * 0.393D + 2.0D * 0.769D + 3.0D * 0.189D, b.r);
		assertEquals(1.0D * 0.349D + 2.0D * 0.686D + 3.0D * 0.168D, b.g);
		assertEquals(1.0D * 0.272D + 2.0D * 0.534D + 3.0D * 0.131D, b.b);
		assertEquals(4.0D, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4DPixelOperator.sepia().apply(null, 0, 0));
	}
}