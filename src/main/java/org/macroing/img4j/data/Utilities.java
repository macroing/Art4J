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
	
	public static boolean equals(final double a, final double b) {
		return Double.compare(a, b) == 0;
	}
	
	public static boolean equals(final float a, final float b) {
		return Float.compare(a, b) == 0;
	}
	
	public static boolean isZero(final double value) {
		return Double.compare(value, +0.0D) == 0 || Double.compare(value, -0.0D) == 0;
	}
	
	public static boolean isZero(final float value) {
		return Float.compare(value, +0.0F) == 0 || Float.compare(value, -0.0F) == 0;
	}
	
	public static double abs(final double value) {
		return Math.abs(value);
	}
	
	public static double ceil(final double value) {
		return Math.ceil(value);
	}
	
	public static double cos(final double angle) {
		return Math.cos(angle);
	}
	
	public static double floor(final double value) {
		return Math.floor(value);
	}
	
	public static double max(final double a, final double b, final double c, final double d) {
		return Math.max(Math.max(a, b), Math.max(c, d));
	}
	
	public static double min(final double a, final double b) {
		return Math.min(a, b);
	}
	
	public static double min(final double a, final double b, final double c, final double d) {
		return Math.min(Math.min(a, b), Math.min(c, d));
	}
	
	public static double sin(final double angle) {
		return Math.sin(angle);
	}
	
	public static double toDegrees(final double angle) {
		return Math.toDegrees(angle);
	}
	
	public static double toRadians(final double angle) {
		return Math.toRadians(angle);
	}
	
	public static float abs(final float value) {
		return Math.abs(value);
	}
	
	public static float ceil(final float value) {
		return (float)(Math.ceil(value));
	}
	
	public static float cos(final float angle) {
		return (float)(Math.cos(angle));
	}
	
	public static float floor(final float value) {
		return (float)(Math.floor(value));
	}
	
	public static float max(final float a, final float b, final float c, final float d) {
		return Math.max(Math.max(a, b), Math.max(c, d));
	}
	
	public static float min(final float a, final float b) {
		return Math.min(a, b);
	}
	
	public static float min(final float a, final float b, final float c, final float d) {
		return Math.min(Math.min(a, b), Math.min(c, d));
	}
	
	public static float sin(final float angle) {
		return (float)(Math.sin(angle));
	}
	
	public static float toDegrees(final float angle) {
		return (float)(Math.toDegrees(angle));
	}
	
	public static float toRadians(final float angle) {
		return (float)(Math.toRadians(angle));
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