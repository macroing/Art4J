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

import java.lang.reflect.Field;//TODO: Add Unit Tests!

/**
 * A {@code ConstantSpectralCurveF} is an implementation of {@link SpectralCurveF} that returns a constant value.
 * <p>
 * This class is immutable and therefore suitable for concurrent use without external synchronization.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantSpectralCurveF extends SpectralCurveF {
	private final float amplitude;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantSpectralCurveF} instance.
	 * 
	 * @param amplitude the constant value to use
	 */
//	TODO: Add Unit Tests!
	public ConstantSpectralCurveF(final float amplitude) {
		this.amplitude = amplitude;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a sample based on the wavelength {@code lambda} in nanometers.
	 * <p>
	 * This implementation returns a constant value.
	 * 
	 * @param lambda the wavelength in nanometers
	 * @return a sample based on the wavelength {@code lambda} in nanometers
	 */
//	TODO: Add Unit Tests!
	@Override
	public float sample(final float lambda) {
		return this.amplitude;
	}
}