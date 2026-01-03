/**
 * Copyright 2022 - 2026 J&#246;rgen Lundgren
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

@SuppressWarnings("static-method")
public final class PixelTransformerUnitTests {
	public PixelTransformerUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testTransform() {
		assertEquals(-1, PixelTransformer.DEFAULT.transform(-1, 9));
		assertEquals(+0, PixelTransformer.DEFAULT.transform(+0, 9));
		assertEquals(+8, PixelTransformer.DEFAULT.transform(+8, 9));
		assertEquals(+9, PixelTransformer.DEFAULT.transform(+9, 9));
		
		assertEquals(+8, PixelTransformer.WRAP_AROUND.transform(-1, 9));
		assertEquals(+0, PixelTransformer.WRAP_AROUND.transform(+0, 9));
		assertEquals(+8, PixelTransformer.WRAP_AROUND.transform(+8, 9));
		assertEquals(+0, PixelTransformer.WRAP_AROUND.transform(+9, 9));
		
		assertThrows(IllegalArgumentException.class, () -> PixelTransformer.DEFAULT.transform(0, 0));
		assertThrows(IllegalArgumentException.class, () -> PixelTransformer.WRAP_AROUND.transform(0, -1));
	}
}