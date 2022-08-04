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
package org.macroing.img4j.pixel;

import org.macroing.java.lang.Ints;

/**
 * A {@code PixelTransformer} transforms a pixel when it is outside the boundaries of an image.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public enum PixelTransformer {
	/**
	 * A {@code PixelTransformer} instance that performs no transformation.
	 */
	DEFAULT,
	
	/**
	 * A {@code PixelTransformer} instance that performs a wrap-around transformation.
	 */
	WRAP_AROUND;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private PixelTransformer() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Performs a transformation on {@code pixel} given a resolution of {@code resolution}.
	 * <p>
	 * Returns {@code pixel} or a transformed version of it.
	 * <p>
	 * If {@code resolution} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * The first parameter, {@code pixel}, may represent the index of the pixel, the X-coordinate of the pixel or the Y-coordinate of the pixel. Its value is allowed to be negative or greater than or equal to {@code resolution}. No transformation will occur unless it is.
	 * <p>
	 * The second parameter, {@code resolution}, may represent the resolution of the image, the resolution of the X-axis or the resolution of the Y-axis.
	 * 
	 * @param pixel the index of the pixel, the X-coordinate of the pixel or the Y-coordinate of the pixel
	 * @param resolution the resolution of the image, the resolution of the X-axis or the resolution of the Y-axis
	 * @return {@code pixel} or a transformed version of it
	 * @throws IllegalArgumentException thrown if, and only if, {@code resolution} is less than {@code 1}
	 */
	public int transform(final int pixel, final int resolution) {
		Ints.requireRange(resolution, 1, Integer.MAX_VALUE, "resolution");
		
		if(this == WRAP_AROUND) {
			return Ints.floorMod(pixel, resolution);
		}
		
		return pixel;
	}
}