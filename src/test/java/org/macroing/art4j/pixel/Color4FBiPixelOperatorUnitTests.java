/**
 * Copyright 2022 - 2024 J&#246;rgen Lundgren
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
public final class Color4FBiPixelOperatorUnitTests {
	public Color4FBiPixelOperatorUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testBlendOver() {
		final Color4F a = new Color4F(0.5F, 0.5F, 0.5F, 0.5F);
		final Color4F b = new Color4F(0.0F, 0.0F, 0.0F, 1.0F);
		final Color4F c = Color4FBiPixelOperator.blendOver().apply(a, b, 0, 0);
		
		assertEquals(0.25F, c.r);
		assertEquals(0.25F, c.g);
		assertEquals(0.25F, c.b);
		assertEquals(1.00F, c.a);
		
		assertThrows(NullPointerException.class, () -> Color4FBiPixelOperator.blendOver().apply(a, null, 0, 0));
		assertThrows(NullPointerException.class, () -> Color4FBiPixelOperator.blendOver().apply(null, b, 0, 0));
	}
}