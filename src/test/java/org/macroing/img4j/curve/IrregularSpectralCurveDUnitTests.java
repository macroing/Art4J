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
public final class IrregularSpectralCurveDUnitTests {
	public IrregularSpectralCurveDUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructor() {
		final IrregularSpectralCurveD irregularSpectralCurveD = new IrregularSpectralCurveD(new double[] {1.0D, 2.0D, 3.0D}, new double[] {4.0D, 5.0D, 6.0D});
		
		assertArrayEquals(new double[] {1.0D, 2.0D, 3.0D}, irregularSpectralCurveD.getAmplitudes());
		assertArrayEquals(new double[] {4.0D, 5.0D, 6.0D}, irregularSpectralCurveD.getWavelengths());
		
		assertThrows(NullPointerException.class, () -> new IrregularSpectralCurveD(new double[0], null));
		assertThrows(NullPointerException.class, () -> new IrregularSpectralCurveD(null, new double[0]));
		
		assertThrows(IllegalArgumentException.class, () -> new IrregularSpectralCurveD(new double[0], new double[1]));
	}
	
	@Test
	public void testGetAmplitudes() {
		final double[] amplitudes = new double[] {1.0D, 2.0D, 3.0D};
		final double[] wavelengths = new double[] {4.0D, 5.0D, 6.0D};
		
		final IrregularSpectralCurveD irregularSpectralCurveD = new IrregularSpectralCurveD(amplitudes, wavelengths);
		
		assertArrayEquals(amplitudes, irregularSpectralCurveD.getAmplitudes());
		
		assertTrue(irregularSpectralCurveD.getAmplitudes() != amplitudes);
	}
	
	@Test
	public void testGetWavelengths() {
		final double[] amplitudes = new double[] {1.0D, 2.0D, 3.0D};
		final double[] wavelengths = new double[] {4.0D, 5.0D, 6.0D};
		
		final IrregularSpectralCurveD irregularSpectralCurveD = new IrregularSpectralCurveD(amplitudes, wavelengths);
		
		assertArrayEquals(wavelengths, irregularSpectralCurveD.getWavelengths());
		
		assertTrue(irregularSpectralCurveD.getWavelengths() != wavelengths);
	}
	
	@Test
	public void testSample() {
		assertEquals(0.0D, new IrregularSpectralCurveD(new double[] {}, new double[] {}).sample(0.0D));
		assertEquals(1.0D, new IrregularSpectralCurveD(new double[] {1.0D}, new double[] {1.0D}).sample(0.0D));
		assertEquals(1.0D, new IrregularSpectralCurveD(new double[] {1.0D, 2.0D}, new double[] {1.0D, 2.0D}).sample(0.0D));
		assertEquals(2.0D, new IrregularSpectralCurveD(new double[] {1.0D, 2.0D}, new double[] {1.0D, 2.0D}).sample(3.0D));
		assertEquals(3.0D, new IrregularSpectralCurveD(new double[] {1.0D, 2.0D, 3.0D, 4.0D, 5.0D}, new double[] {1.0D, 2.0D, 3.0D, 4.0D, 5.0D}).sample(3.0D));
		assertEquals(5.0D, new IrregularSpectralCurveD(new double[] {1.0D, 2.0D, 3.0D, 4.0D, 5.0D}, new double[] {1.0D, 2.0D, 3.0D, 4.0D, 5.0D}).sample(5.0D));
	}
}