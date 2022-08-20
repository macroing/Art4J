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

import org.macroing.art4j.color.Color4F;

@SuppressWarnings("static-method")
public final class Color4FPixelOperatorUnitTests {
	public Color4FPixelOperatorUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testGrayscaleA() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.grayscaleA().apply(a, 0, 0);
		
		assertEquals(4.0F, b.r);
		assertEquals(4.0F, b.g);
		assertEquals(4.0F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.grayscaleA().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleAverage() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.grayscaleAverage().apply(a, 0, 0);
		
		assertEquals(2.0F, b.r);
		assertEquals(2.0F, b.g);
		assertEquals(2.0F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.grayscaleAverage().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleB() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.grayscaleB().apply(a, 0, 0);
		
		assertEquals(3.0F, b.r);
		assertEquals(3.0F, b.g);
		assertEquals(3.0F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.grayscaleB().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleG() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.grayscaleG().apply(a, 0, 0);
		
		assertEquals(2.0F, b.r);
		assertEquals(2.0F, b.g);
		assertEquals(2.0F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.grayscaleG().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleLightness() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.grayscaleLightness().apply(a, 0, 0);
		
		assertEquals(2.0F, b.r);
		assertEquals(2.0F, b.g);
		assertEquals(2.0F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.grayscaleLightness().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleMax() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.grayscaleMax().apply(a, 0, 0);
		
		assertEquals(3.0F, b.r);
		assertEquals(3.0F, b.g);
		assertEquals(3.0F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.grayscaleMax().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleMin() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.grayscaleMin().apply(a, 0, 0);
		
		assertEquals(1.0F, b.r);
		assertEquals(1.0F, b.g);
		assertEquals(1.0F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.grayscaleMin().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleR() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.grayscaleR().apply(a, 0, 0);
		
		assertEquals(1.0F, b.r);
		assertEquals(1.0F, b.g);
		assertEquals(1.0F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.grayscaleR().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleRelativeLuminance() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.grayscaleRelativeLuminance().apply(a, 0, 0);
		
		assertEquals(1.0F * 0.212671F + 2.0F * 0.715160F + 3.0F * 0.072169F, b.r);
		assertEquals(1.0F * 0.212671F + 2.0F * 0.715160F + 3.0F * 0.072169F, b.g);
		assertEquals(1.0F * 0.212671F + 2.0F * 0.715160F + 3.0F * 0.072169F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.grayscaleRelativeLuminance().apply(null, 0, 0));
	}
	
	@Test
	public void testInvert() {
		final Color4F a = new Color4F(1.0F, 1.0F, 1.0F, 1.0F);
		final Color4F b = Color4FPixelOperator.invert().apply(a, 0, 0);
		
		assertEquals(0.0F, b.r);
		assertEquals(0.0F, b.g);
		assertEquals(0.0F, b.b);
		assertEquals(1.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.invert().apply(null, 0, 0));
	}
	
	@Test
	public void testSepia() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.sepia().apply(a, 0, 0);
		
		assertEquals(1.0F * 0.393F + 2.0F * 0.769F + 3.0F * 0.189F, b.r);
		assertEquals(1.0F * 0.349F + 2.0F * 0.686F + 3.0F * 0.168F, b.g);
		assertEquals(1.0F * 0.272F + 2.0F * 0.534F + 3.0F * 0.131F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.sepia().apply(null, 0, 0));
	}
}