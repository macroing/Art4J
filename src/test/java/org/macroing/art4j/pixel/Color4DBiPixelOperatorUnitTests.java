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
public final class Color4DBiPixelOperatorUnitTests {
	public Color4DBiPixelOperatorUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testBlendOver() {
		final Color4D a = new Color4D(0.5D, 0.5D, 0.5D, 0.5D);
		final Color4D b = new Color4D(0.0D, 0.0D, 0.0D, 1.0D);
		final Color4D c = Color4DBiPixelOperator.blendOver().apply(a, b, 0, 0);
		
		assertEquals(0.25D, c.r);
		assertEquals(0.25D, c.g);
		assertEquals(0.25D, c.b);
		assertEquals(1.00D, c.a);
		
		assertThrows(NullPointerException.class, () -> Color4DBiPixelOperator.blendOver().apply(a, null, 0, 0));
		assertThrows(NullPointerException.class, () -> Color4DBiPixelOperator.blendOver().apply(null, b, 0, 0));
	}
}