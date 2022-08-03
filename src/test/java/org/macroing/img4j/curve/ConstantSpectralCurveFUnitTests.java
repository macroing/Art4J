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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public final class ConstantSpectralCurveFUnitTests {
	public ConstantSpectralCurveFUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructor() {
		final ConstantSpectralCurveF constantSpectralCurveF = new ConstantSpectralCurveF(1.0F);
		
		assertEquals(1.0F, constantSpectralCurveF.getAmplitude());
	}
	
	@Test
	public void testGetAmplitude() {
		final ConstantSpectralCurveF constantSpectralCurveF = new ConstantSpectralCurveF(1.0F);
		
		assertEquals(1.0F, constantSpectralCurveF.getAmplitude());
	}
	
	@Test
	public void testSample() {
		final ConstantSpectralCurveF constantSpectralCurveF = new ConstantSpectralCurveF(1.0F);
		
		assertEquals(1.0F, constantSpectralCurveF.sample(0.5F));
	}
}