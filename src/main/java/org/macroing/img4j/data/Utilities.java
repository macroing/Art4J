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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;

final class Utilities {
	private Utilities() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static BufferedImage getCompatibleBufferedImage(final BufferedImage bufferedImage) {
		return getCompatibleBufferedImage(bufferedImage, BufferedImage.TYPE_INT_ARGB);
	}
	
	public static BufferedImage getCompatibleBufferedImage(final BufferedImage bufferedImage, final int type) {
		if(bufferedImage.getType() == type) {
			return bufferedImage;
		}
		
		final BufferedImage compatibleBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), type);
		
		final
		Graphics2D graphics2D = compatibleBufferedImage.createGraphics();
		graphics2D.drawImage(bufferedImage, 0, 0, null);
		
		return compatibleBufferedImage;
	}
	
	public static <T> List<T> requireNonNullList(final List<T> list, final String name) {
		Objects.requireNonNull(name, "name == null");
		Objects.requireNonNull(list, String.format("%s == null", name));
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i) == null) {
				throw new NullPointerException(String.format("%s.get(%d) == null", name, Integer.valueOf(i)));
			}
		}
		
		return list;
	}
	
	public static int requireRange(final int value, final int rangeEndA, final int rangeEndB, final String name) {
		Objects.requireNonNull(name, "name == null");
		
		final int minimum = Math.min(rangeEndA, rangeEndB);
		final int maximum = Math.max(rangeEndA, rangeEndB);
		
		if(value < minimum) {
			throw new IllegalArgumentException(String.format("%s < %d: %s == %d", name, Integer.valueOf(minimum), name, Integer.valueOf(value)));
		} else if(value > maximum) {
			throw new IllegalArgumentException(String.format("%s > %d: %s == %d", name, Integer.valueOf(maximum), name, Integer.valueOf(value)));
		} else {
			return value;
		}
	}
}