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
package org.macroing.img4j.curve;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public final class IrregularSpectralCurveFUnitTests {
	public IrregularSpectralCurveFUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructor() {
		final IrregularSpectralCurveF irregularSpectralCurveF = new IrregularSpectralCurveF(new float[] {1.0F, 2.0F, 3.0F}, new float[] {4.0F, 5.0F, 6.0F});
		
		assertArrayEquals(new float[] {1.0F, 2.0F, 3.0F}, irregularSpectralCurveF.getAmplitudes());
		assertArrayEquals(new float[] {4.0F, 5.0F, 6.0F}, irregularSpectralCurveF.getWavelengths());
		
		assertThrows(NullPointerException.class, () -> new IrregularSpectralCurveF(new float[0], null));
		assertThrows(NullPointerException.class, () -> new IrregularSpectralCurveF(null, new float[0]));
		
		assertThrows(IllegalArgumentException.class, () -> new IrregularSpectralCurveF(new float[0], new float[1]));
	}
	
	@Test
	public void testGetAmplitudes() {
		final float[] amplitudes = new float[] {1.0F, 2.0F, 3.0F};
		final float[] wavelengths = new float[] {4.0F, 5.0F, 6.0F};
		
		final IrregularSpectralCurveF irregularSpectralCurveF = new IrregularSpectralCurveF(amplitudes, wavelengths);
		
		assertArrayEquals(amplitudes, irregularSpectralCurveF.getAmplitudes());
		
		assertTrue(irregularSpectralCurveF.getAmplitudes() != amplitudes);
	}
	
	@Test
	public void testGetWavelengths() {
		final float[] amplitudes = new float[] {1.0F, 2.0F, 3.0F};
		final float[] wavelengths = new float[] {4.0F, 5.0F, 6.0F};
		
		final IrregularSpectralCurveF irregularSpectralCurveF = new IrregularSpectralCurveF(amplitudes, wavelengths);
		
		assertArrayEquals(wavelengths, irregularSpectralCurveF.getWavelengths());
		
		assertTrue(irregularSpectralCurveF.getWavelengths() != wavelengths);
	}
	
	@Test
	public void testSample() {
		assertEquals(0.0F, new IrregularSpectralCurveF(new float[] {}, new float[] {}).sample(0.0F));
		assertEquals(1.0F, new IrregularSpectralCurveF(new float[] {1.0F}, new float[] {1.0F}).sample(0.0F));
		assertEquals(1.0F, new IrregularSpectralCurveF(new float[] {1.0F, 2.0F}, new float[] {1.0F, 2.0F}).sample(0.0F));
		assertEquals(2.0F, new IrregularSpectralCurveF(new float[] {1.0F, 2.0F}, new float[] {1.0F, 2.0F}).sample(3.0F));
		assertEquals(3.0F, new IrregularSpectralCurveF(new float[] {1.0F, 2.0F, 3.0F, 4.0F, 5.0F}, new float[] {1.0F, 2.0F, 3.0F, 4.0F, 5.0F}).sample(3.0F));
		assertEquals(5.0F, new IrregularSpectralCurveF(new float[] {1.0F, 2.0F, 3.0F, 4.0F, 5.0F}, new float[] {1.0F, 2.0F, 3.0F, 4.0F, 5.0F}).sample(5.0F));
	}
}