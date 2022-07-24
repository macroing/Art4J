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
package org.macroing.img4j.kernel;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public final class ConvolutionKernelNFUnitTests {
	public ConvolutionKernelNFUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructor() {
		final float[] elements = new float[] {3.0F};
		
		final ConvolutionKernelNF convolutionKernel = new ConvolutionKernelNF(1.0F, 2.0F, elements);
		
		assertEquals(1.0F, convolutionKernel.getBias());
		assertEquals(2.0F, convolutionKernel.getFactor());
		assertEquals(1, convolutionKernel.getResolution());
		
		assertArrayEquals(new float[] {3.0F}, convolutionKernel.getElements());
		
		assertTrue(elements != convolutionKernel.getElements());
		
		elements[0] = 4.0F;
		
		assertTrue(Float.compare(elements[0], convolutionKernel.getElements()[0]) != 0);
		
		assertThrows(NullPointerException.class, () -> new ConvolutionKernelNF(1.0F, 2.0F, null));
		
		assertThrows(IllegalArgumentException.class, () -> new ConvolutionKernelNF(1.0F, 2.0F, new float[0]));
		assertThrows(IllegalArgumentException.class, () -> new ConvolutionKernelNF(1.0F, 2.0F, new float[2]));
		assertThrows(IllegalArgumentException.class, () -> new ConvolutionKernelNF(1.0F, 2.0F, new float[3]));
	}
	
	@Test
	public void testEquals() {
		final ConvolutionKernelNF a = new ConvolutionKernelNF(0.0F, 1.0F, new float[] {0.0F});
		final ConvolutionKernelNF b = new ConvolutionKernelNF(0.0F, 1.0F, new float[] {0.0F});
		final ConvolutionKernelNF c = new ConvolutionKernelNF(0.0F, 1.0F, new float[] {1.0F});
		final ConvolutionKernelNF d = new ConvolutionKernelNF(0.0F, 2.0F, new float[] {0.0F});
		final ConvolutionKernelNF e = new ConvolutionKernelNF(1.0F, 1.0F, new float[] {0.0F});
		final ConvolutionKernelNF f = null;
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(a, d);
		assertNotEquals(d, a);
		assertNotEquals(a, e);
		assertNotEquals(e, a);
		assertNotEquals(a, f);
		assertNotEquals(f, a);
	}
	
	@Test
	public void testGetBias() {
		assertEquals(0.0F, ConvolutionKernelNF.BOX_BLUR_3.getBias());
		assertEquals(0.5F, ConvolutionKernelNF.EMBOSS_3.getBias());
		assertEquals(0.0F, ConvolutionKernelNF.GAUSSIAN_BLUR_3.getBias());
		assertEquals(0.0F, ConvolutionKernelNF.GAUSSIAN_BLUR_5.getBias());
		assertEquals(0.0F, ConvolutionKernelNF.GRADIENT_HORIZONTAL_3.getBias());
		assertEquals(0.0F, ConvolutionKernelNF.GRADIENT_VERTICAL_3.getBias());
		assertEquals(0.0F, ConvolutionKernelNF.IDENTITY_3.getBias());
		assertEquals(0.0F, ConvolutionKernelNF.IDENTITY_5.getBias());
		assertEquals(0.0F, ConvolutionKernelNF.RIDGE_DETECTION_3.getBias());
		assertEquals(0.0F, ConvolutionKernelNF.SHARPEN_3.getBias());
		assertEquals(0.0F, ConvolutionKernelNF.UNSHARP_MASKING_5.getBias());
	}
	
	@Test
	public void testGetElements() {
		assertArrayEquals(new float[] {1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F}, ConvolutionKernelNF.BOX_BLUR_3.getElements());
		assertArrayEquals(new float[] {-1.0F, -1.0F, 0.0F, -1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F}, ConvolutionKernelNF.EMBOSS_3.getElements());
		assertArrayEquals(new float[] {1.0F, 2.0F, 1.0F, 2.0F, 4.0F, 2.0F, 1.0F, 2.0F, 1.0F}, ConvolutionKernelNF.GAUSSIAN_BLUR_3.getElements());
		assertArrayEquals(new float[] {1.0F, 4.0F, 6.0F, 4.0F, 1.0F, 4.0F, 16.0F, 24.0F, 16.0F, 4.0F, 6.0F, 24.0F, 36.0F, 24.0F, 6.0F, 4.0F, 16.0F, 24.0F, 16.0F, 4.0F, 1.0F, 4.0F, 6.0F, 4.0F, 1.0F}, ConvolutionKernelNF.GAUSSIAN_BLUR_5.getElements());
		assertArrayEquals(new float[] {-1.0F, -1.0F, -1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F}, ConvolutionKernelNF.GRADIENT_HORIZONTAL_3.getElements());
		assertArrayEquals(new float[] {-1.0F, 0.0F, 1.0F, -1.0F, 0.0F, 1.0F, -1.0F, 0.0F, 1.0F}, ConvolutionKernelNF.GRADIENT_VERTICAL_3.getElements());
		assertArrayEquals(new float[] {0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F}, ConvolutionKernelNF.IDENTITY_3.getElements());
		assertArrayEquals(new float[] {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}, ConvolutionKernelNF.IDENTITY_5.getElements());
		assertArrayEquals(new float[] {-1.0F, -1.0F, -1.0F, -1.0F, 8.0F, -1.0F, -1.0F, -1.0F, -1.0F}, ConvolutionKernelNF.RIDGE_DETECTION_3.getElements());
		assertArrayEquals(new float[] {-1.0F, -1.0F, -1.0F, -1.0F, 9.0F, -1.0F, -1.0F, -1.0F, -1.0F}, ConvolutionKernelNF.SHARPEN_3.getElements());
		assertArrayEquals(new float[] {1.0F, 4.0F, 6.0F, 4.0F, 1.0F, 4.0F, 16.0F, 24.0F, 16.0F, 4.0F, 6.0F, 24.0F, -476.0F, 24.0F, 6.0F, 4.0F, 16.0F, 24.0F, 16.0F, 4.0F, 1.0F, 4.0F, 6.0F, 4.0F, 1.0F}, ConvolutionKernelNF.UNSHARP_MASKING_5.getElements());
	}
	
	@Test
	public void testGetFactor() {
		assertEquals(1.0F / 9.0F, ConvolutionKernelNF.BOX_BLUR_3.getFactor());
		assertEquals(1.0F, ConvolutionKernelNF.EMBOSS_3.getFactor());
		assertEquals(1.0F / 16.0F, ConvolutionKernelNF.GAUSSIAN_BLUR_3.getFactor());
		assertEquals(1.0F / 256.0F, ConvolutionKernelNF.GAUSSIAN_BLUR_5.getFactor());
		assertEquals(1.0F, ConvolutionKernelNF.GRADIENT_HORIZONTAL_3.getFactor());
		assertEquals(1.0F, ConvolutionKernelNF.GRADIENT_VERTICAL_3.getFactor());
		assertEquals(1.0F, ConvolutionKernelNF.IDENTITY_3.getFactor());
		assertEquals(1.0F, ConvolutionKernelNF.IDENTITY_5.getFactor());
		assertEquals(1.0F, ConvolutionKernelNF.RIDGE_DETECTION_3.getFactor());
		assertEquals(1.0F, ConvolutionKernelNF.SHARPEN_3.getFactor());
		assertEquals(-1.0F / 256.0F, ConvolutionKernelNF.UNSHARP_MASKING_5.getFactor());
	}
	
	@Test
	public void testGetResolution() {
		assertEquals(3, ConvolutionKernelNF.BOX_BLUR_3.getResolution());
		assertEquals(3, ConvolutionKernelNF.EMBOSS_3.getResolution());
		assertEquals(3, ConvolutionKernelNF.GAUSSIAN_BLUR_3.getResolution());
		assertEquals(5, ConvolutionKernelNF.GAUSSIAN_BLUR_5.getResolution());
		assertEquals(3, ConvolutionKernelNF.GRADIENT_HORIZONTAL_3.getResolution());
		assertEquals(3, ConvolutionKernelNF.GRADIENT_VERTICAL_3.getResolution());
		assertEquals(3, ConvolutionKernelNF.IDENTITY_3.getResolution());
		assertEquals(5, ConvolutionKernelNF.IDENTITY_5.getResolution());
		assertEquals(3, ConvolutionKernelNF.RIDGE_DETECTION_3.getResolution());
		assertEquals(3, ConvolutionKernelNF.SHARPEN_3.getResolution());
		assertEquals(5, ConvolutionKernelNF.UNSHARP_MASKING_5.getResolution());
	}
	
	@Test
	public void testHashCode() {
		final ConvolutionKernelNF a = new ConvolutionKernelNF(0.0F, 1.0F, new float[] {0.0F});
		final ConvolutionKernelNF b = new ConvolutionKernelNF(0.0F, 1.0F, new float[] {0.0F});
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testRandomInt() {
		final ConvolutionKernelNF convolutionKernel = ConvolutionKernelNF.random(3);
		
		final float[] elements = convolutionKernel.getElements();
		
		assertTrue(convolutionKernel.getBias() >= 0.0F && convolutionKernel.getBias() <= 1.0F);
		assertTrue(convolutionKernel.getFactor() >= -Float.MAX_VALUE && convolutionKernel.getFactor() <= Float.MAX_VALUE);
		
		assertEquals(3, convolutionKernel.getResolution());
		
		for(int i = 0; i < elements.length; i++) {
			assertTrue(elements[i] >= -1.0F && elements[i] <= 1.0F);
		}
		
		assertThrows(IllegalArgumentException.class, () -> ConvolutionKernelNF.random(0));
		assertThrows(IllegalArgumentException.class, () -> ConvolutionKernelNF.random(2));
		assertThrows(IllegalArgumentException.class, () -> ConvolutionKernelNF.random(Integer.MAX_VALUE));
	}
	
	@Test
	public void testRandomIntBooleanBooleanBooleanBoolean() {
		final ConvolutionKernelNF a = ConvolutionKernelNF.random(3, false, false, false, false);
		final ConvolutionKernelNF b = ConvolutionKernelNF.random(3, false, false, false, true);
		final ConvolutionKernelNF c = ConvolutionKernelNF.random(3, false, false, true, false);
		final ConvolutionKernelNF d = ConvolutionKernelNF.random(3, false, true, false, false);
		final ConvolutionKernelNF e = ConvolutionKernelNF.random(3, true, false, false, false);
		final ConvolutionKernelNF f = ConvolutionKernelNF.random(3, false, false, true, true);
		final ConvolutionKernelNF g = ConvolutionKernelNF.random(3, false, true, true, false);
		final ConvolutionKernelNF h = ConvolutionKernelNF.random(3, true, true, false, false);
		final ConvolutionKernelNF i = ConvolutionKernelNF.random(3, true, false, false, true);
		final ConvolutionKernelNF j = ConvolutionKernelNF.random(3, false, true, true, true);
		final ConvolutionKernelNF k = ConvolutionKernelNF.random(3, true, true, true, false);
		final ConvolutionKernelNF l = ConvolutionKernelNF.random(3, true, true, true, true);
		
		assertEquals(0.0F, a.getBias());
		assertEquals(0.0F, b.getBias());
		assertEquals(0.0F, c.getBias());
		assertEquals(0.0F, d.getBias());
		assertEquals(0.0F, f.getBias());
		assertEquals(0.0F, g.getBias());
		assertEquals(0.0F, j.getBias());
		
		assertTrue(e.getBias() >= 0.0F && e.getBias() <= 1.0F);
		assertTrue(h.getBias() >= 0.0F && h.getBias() <= 1.0F);
		assertTrue(i.getBias() >= 0.0F && i.getBias() <= 1.0F);
		assertTrue(k.getBias() >= 0.0F && k.getBias() <= 1.0F);
		assertTrue(l.getBias() >= 0.0F && l.getBias() <= 1.0F);
		
		assertEquals(1.0F, a.getFactor());
		assertEquals(1.0F, d.getFactor());
		assertEquals(1.0F, e.getFactor());
		assertEquals(1.0F, g.getFactor());
		assertEquals(1.0F, h.getFactor());
		assertEquals(1.0F, j.getFactor());
		assertEquals(1.0F, k.getFactor());
		assertEquals(1.0F, l.getFactor());
		
		assertTrue(b.getFactor() >= 0.0F && b.getFactor() <= 1.0F);
		assertTrue(c.getFactor() >= -Float.MAX_VALUE && c.getFactor() <= Float.MAX_VALUE);
		assertTrue(f.getFactor() >= -Float.MAX_VALUE && f.getFactor() <= Float.MAX_VALUE);
		assertTrue(i.getFactor() >= 0.0F && i.getFactor() <= 1.0F);
	}
}