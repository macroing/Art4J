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
package org.macroing.art4j.kernel;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public final class ConvolutionKernelNDUnitTests {
	public ConvolutionKernelNDUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructor() {
		final double[] elements = new double[] {3.0D};
		
		final ConvolutionKernelND convolutionKernel = new ConvolutionKernelND(1.0D, 2.0D, elements);
		
		assertEquals(1.0D, convolutionKernel.getBias());
		assertEquals(2.0D, convolutionKernel.getFactor());
		assertEquals(1, convolutionKernel.getResolution());
		
		assertArrayEquals(new double[] {3.0D}, convolutionKernel.getElements());
		
		assertTrue(elements != convolutionKernel.getElements());
		
		elements[0] = 4.0D;
		
		assertTrue(Double.compare(elements[0], convolutionKernel.getElements()[0]) != 0);
		
		assertThrows(NullPointerException.class, () -> new ConvolutionKernelND(1.0D, 2.0D, null));
		
		assertThrows(IllegalArgumentException.class, () -> new ConvolutionKernelND(1.0D, 2.0D, new double[0]));
		assertThrows(IllegalArgumentException.class, () -> new ConvolutionKernelND(1.0D, 2.0D, new double[2]));
		assertThrows(IllegalArgumentException.class, () -> new ConvolutionKernelND(1.0D, 2.0D, new double[3]));
	}
	
	@Test
	public void testEquals() {
		final ConvolutionKernelND a = new ConvolutionKernelND(0.0D, 1.0D, new double[] {0.0D});
		final ConvolutionKernelND b = new ConvolutionKernelND(0.0D, 1.0D, new double[] {0.0D});
		final ConvolutionKernelND c = new ConvolutionKernelND(0.0D, 1.0D, new double[] {1.0D});
		final ConvolutionKernelND d = new ConvolutionKernelND(0.0D, 2.0D, new double[] {0.0D});
		final ConvolutionKernelND e = new ConvolutionKernelND(1.0D, 1.0D, new double[] {0.0D});
		final ConvolutionKernelND f = null;
		
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
		assertEquals(0.0D, ConvolutionKernelND.BOX_BLUR_3.getBias());
		assertEquals(0.5D, ConvolutionKernelND.EMBOSS_3.getBias());
		assertEquals(0.0D, ConvolutionKernelND.GAUSSIAN_BLUR_3.getBias());
		assertEquals(0.0D, ConvolutionKernelND.GAUSSIAN_BLUR_5.getBias());
		assertEquals(0.0D, ConvolutionKernelND.GRADIENT_HORIZONTAL_3.getBias());
		assertEquals(0.0D, ConvolutionKernelND.GRADIENT_VERTICAL_3.getBias());
		assertEquals(0.0D, ConvolutionKernelND.IDENTITY_3.getBias());
		assertEquals(0.0D, ConvolutionKernelND.IDENTITY_5.getBias());
		assertEquals(0.0D, ConvolutionKernelND.RIDGE_DETECTION_3.getBias());
		assertEquals(0.0D, ConvolutionKernelND.SHARPEN_3.getBias());
		assertEquals(0.0D, ConvolutionKernelND.UNSHARP_MASKING_5.getBias());
	}
	
	@Test
	public void testGetElements() {
		assertArrayEquals(new double[] {1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D}, ConvolutionKernelND.BOX_BLUR_3.getElements());
		assertArrayEquals(new double[] {-1.0D, -1.0D, 0.0D, -1.0D, 0.0D, 1.0D, 0.0D, 1.0D, 1.0D}, ConvolutionKernelND.EMBOSS_3.getElements());
		assertArrayEquals(new double[] {1.0D, 2.0D, 1.0D, 2.0D, 4.0D, 2.0D, 1.0D, 2.0D, 1.0D}, ConvolutionKernelND.GAUSSIAN_BLUR_3.getElements());
		assertArrayEquals(new double[] {1.0D, 4.0D, 6.0D, 4.0D, 1.0D, 4.0D, 16.0D, 24.0D, 16.0D, 4.0D, 6.0D, 24.0D, 36.0D, 24.0D, 6.0D, 4.0D, 16.0D, 24.0D, 16.0D, 4.0D, 1.0D, 4.0D, 6.0D, 4.0D, 1.0D}, ConvolutionKernelND.GAUSSIAN_BLUR_5.getElements());
		assertArrayEquals(new double[] {-1.0D, -1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D}, ConvolutionKernelND.GRADIENT_HORIZONTAL_3.getElements());
		assertArrayEquals(new double[] {-1.0D, 0.0D, 1.0D, -1.0D, 0.0D, 1.0D, -1.0D, 0.0D, 1.0D}, ConvolutionKernelND.GRADIENT_VERTICAL_3.getElements());
		assertArrayEquals(new double[] {0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D}, ConvolutionKernelND.IDENTITY_3.getElements());
		assertArrayEquals(new double[] {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, ConvolutionKernelND.IDENTITY_5.getElements());
		assertArrayEquals(new double[] {-1.0D, -1.0D, -1.0D, -1.0D, 8.0D, -1.0D, -1.0D, -1.0D, -1.0D}, ConvolutionKernelND.RIDGE_DETECTION_3.getElements());
		assertArrayEquals(new double[] {-1.0D, -1.0D, -1.0D, -1.0D, 9.0D, -1.0D, -1.0D, -1.0D, -1.0D}, ConvolutionKernelND.SHARPEN_3.getElements());
		assertArrayEquals(new double[] {1.0D, 4.0D, 6.0D, 4.0D, 1.0D, 4.0D, 16.0D, 24.0D, 16.0D, 4.0D, 6.0D, 24.0D, -476.0D, 24.0D, 6.0D, 4.0D, 16.0D, 24.0D, 16.0D, 4.0D, 1.0D, 4.0D, 6.0D, 4.0D, 1.0D}, ConvolutionKernelND.UNSHARP_MASKING_5.getElements());
	}
	
	@Test
	public void testGetFactor() {
		assertEquals(1.0D / 9.0D, ConvolutionKernelND.BOX_BLUR_3.getFactor());
		assertEquals(1.0D, ConvolutionKernelND.EMBOSS_3.getFactor());
		assertEquals(1.0D / 16.0D, ConvolutionKernelND.GAUSSIAN_BLUR_3.getFactor());
		assertEquals(1.0D / 256.0D, ConvolutionKernelND.GAUSSIAN_BLUR_5.getFactor());
		assertEquals(1.0D, ConvolutionKernelND.GRADIENT_HORIZONTAL_3.getFactor());
		assertEquals(1.0D, ConvolutionKernelND.GRADIENT_VERTICAL_3.getFactor());
		assertEquals(1.0D, ConvolutionKernelND.IDENTITY_3.getFactor());
		assertEquals(1.0D, ConvolutionKernelND.IDENTITY_5.getFactor());
		assertEquals(1.0D, ConvolutionKernelND.RIDGE_DETECTION_3.getFactor());
		assertEquals(1.0D, ConvolutionKernelND.SHARPEN_3.getFactor());
		assertEquals(-1.0D / 256.0D, ConvolutionKernelND.UNSHARP_MASKING_5.getFactor());
	}
	
	@Test
	public void testGetResolution() {
		assertEquals(3, ConvolutionKernelND.BOX_BLUR_3.getResolution());
		assertEquals(3, ConvolutionKernelND.EMBOSS_3.getResolution());
		assertEquals(3, ConvolutionKernelND.GAUSSIAN_BLUR_3.getResolution());
		assertEquals(5, ConvolutionKernelND.GAUSSIAN_BLUR_5.getResolution());
		assertEquals(3, ConvolutionKernelND.GRADIENT_HORIZONTAL_3.getResolution());
		assertEquals(3, ConvolutionKernelND.GRADIENT_VERTICAL_3.getResolution());
		assertEquals(3, ConvolutionKernelND.IDENTITY_3.getResolution());
		assertEquals(5, ConvolutionKernelND.IDENTITY_5.getResolution());
		assertEquals(3, ConvolutionKernelND.RIDGE_DETECTION_3.getResolution());
		assertEquals(3, ConvolutionKernelND.SHARPEN_3.getResolution());
		assertEquals(5, ConvolutionKernelND.UNSHARP_MASKING_5.getResolution());
	}
	
	@Test
	public void testHashCode() {
		final ConvolutionKernelND a = new ConvolutionKernelND(0.0D, 1.0D, new double[] {0.0D});
		final ConvolutionKernelND b = new ConvolutionKernelND(0.0D, 1.0D, new double[] {0.0D});
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testRandomInt() {
		final ConvolutionKernelND convolutionKernel = ConvolutionKernelND.random(3);
		
		final double[] elements = convolutionKernel.getElements();
		
		assertTrue(convolutionKernel.getBias() >= 0.0D && convolutionKernel.getBias() <= 1.0D);
		assertTrue(convolutionKernel.getFactor() >= -Double.MAX_VALUE && convolutionKernel.getFactor() <= Double.MAX_VALUE);
		
		assertEquals(3, convolutionKernel.getResolution());
		
		for(int i = 0; i < elements.length; i++) {
			assertTrue(elements[i] >= -1.0D && elements[i] <= 1.0D);
		}
		
		assertThrows(IllegalArgumentException.class, () -> ConvolutionKernelND.random(0));
		assertThrows(IllegalArgumentException.class, () -> ConvolutionKernelND.random(2));
		assertThrows(IllegalArgumentException.class, () -> ConvolutionKernelND.random(Integer.MAX_VALUE));
	}
	
	@Test
	public void testRandomIntBooleanBooleanBooleanBoolean() {
		final ConvolutionKernelND a = ConvolutionKernelND.random(3, false, false, false, false);
		final ConvolutionKernelND b = ConvolutionKernelND.random(3, false, false, false, true);
		final ConvolutionKernelND c = ConvolutionKernelND.random(3, false, false, true, false);
		final ConvolutionKernelND d = ConvolutionKernelND.random(3, false, true, false, false);
		final ConvolutionKernelND e = ConvolutionKernelND.random(3, true, false, false, false);
		final ConvolutionKernelND f = ConvolutionKernelND.random(3, false, false, true, true);
		final ConvolutionKernelND g = ConvolutionKernelND.random(3, false, true, true, false);
		final ConvolutionKernelND h = ConvolutionKernelND.random(3, true, true, false, false);
		final ConvolutionKernelND i = ConvolutionKernelND.random(3, true, false, false, true);
		final ConvolutionKernelND j = ConvolutionKernelND.random(3, false, true, true, true);
		final ConvolutionKernelND k = ConvolutionKernelND.random(3, true, true, true, false);
		final ConvolutionKernelND l = ConvolutionKernelND.random(3, true, true, true, true);
		
		assertEquals(0.0D, a.getBias());
		assertEquals(0.0D, b.getBias());
		assertEquals(0.0D, c.getBias());
		assertEquals(0.0D, d.getBias());
		assertEquals(0.0D, f.getBias());
		assertEquals(0.0D, g.getBias());
		assertEquals(0.0D, j.getBias());
		
		assertTrue(e.getBias() >= 0.0D && e.getBias() <= 1.0D);
		assertTrue(h.getBias() >= 0.0D && h.getBias() <= 1.0D);
		assertTrue(i.getBias() >= 0.0D && i.getBias() <= 1.0D);
		assertTrue(k.getBias() >= 0.0D && k.getBias() <= 1.0D);
		assertTrue(l.getBias() >= 0.0D && l.getBias() <= 1.0D);
		
		assertEquals(1.0D, a.getFactor());
		assertEquals(1.0D, d.getFactor());
		assertEquals(1.0D, e.getFactor());
		assertEquals(1.0D, g.getFactor());
		assertEquals(1.0D, h.getFactor());
		assertEquals(1.0D, j.getFactor());
		assertEquals(1.0D, k.getFactor());
		assertEquals(1.0D, l.getFactor());
		
		assertTrue(b.getFactor() >= 0.0D && b.getFactor() <= 1.0D);
		assertTrue(c.getFactor() >= -Double.MAX_VALUE && c.getFactor() <= Double.MAX_VALUE);
		assertTrue(f.getFactor() >= -Double.MAX_VALUE && f.getFactor() <= Double.MAX_VALUE);
		assertTrue(i.getFactor() >= 0.0D && i.getFactor() <= 1.0D);
	}
}