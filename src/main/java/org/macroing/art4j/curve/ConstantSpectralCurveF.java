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
package org.macroing.art4j.curve;

import java.util.Objects;

import org.macroing.java.lang.Floats;
import org.macroing.java.lang.Strings;

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
	public ConstantSpectralCurveF(final float amplitude) {
		this.amplitude = amplitude;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantSpectralCurveF} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantSpectralCurveF} instance
	 */
	@Override
	public String toString() {
		return String.format("new ConstantSpectralCurveF(%s)", Strings.toNonScientificNotationJava(this.amplitude));
	}
	
	/**
	 * Compares {@code object} to this {@code ConstantSpectralCurveF} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantSpectralCurveF}, and they are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ConstantSpectralCurveF} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantSpectralCurveF}, and they are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantSpectralCurveF)) {
			return false;
		} else if(!Floats.equals(this.amplitude, ConstantSpectralCurveF.class.cast(object).amplitude)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the amplitude associated with this {@code ConstantSpectralCurveF} instance.
	 * 
	 * @return the amplitude associated with this {@code ConstantSpectralCurveF} instance
	 */
	public float getAmplitude() {
		return this.amplitude;
	}
	
	/**
	 * Returns a sample based on the wavelength {@code lambda} in nanometers.
	 * <p>
	 * This implementation returns a constant value.
	 * 
	 * @param lambda the wavelength in nanometers
	 * @return a sample based on the wavelength {@code lambda} in nanometers
	 */
	@Override
	public float sample(final float lambda) {
		return this.amplitude;
	}
	
	/**
	 * Returns a hash code for this {@code ConstantSpectralCurveF} instance.
	 * 
	 * @return a hash code for this {@code ConstantSpectralCurveF} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Float.valueOf(this.amplitude));
	}
}