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
package org.macroing.img4j.data;

import java.awt.image.BufferedImage;

import org.macroing.img4j.color.Color4D;

final class ColorARGBDataFactory extends DataFactory {
	public ColorARGBDataFactory() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Data create(final BufferedImage bufferedImage) {
		return new ColorARGBData(bufferedImage);
	}
	
	@Override
	public Data create(final int resolutionX, final int resolutionY) {
		return new ColorARGBData(resolutionX, resolutionY);
	}
	
	@Override
	public Data create(final int resolutionX, final int resolutionY, final Color4D color) {
		return new ColorARGBData(resolutionX, resolutionY, color);
	}
}