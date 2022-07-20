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
package org.macroing.img4j.utility;

import java.lang.reflect.Field;//TODO: Add Javadocs!

//TODO: Add Javadocs!
public final class Floats {
	private Floats() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static boolean equals(final float a, final float b) {
		return Float.compare(a, b) == 0;
	}
	
//	TODO: Add Javadocs!
	public static boolean isZero(final float value) {
		return Float.compare(value, +0.0F) == 0 || Float.compare(value, -0.0F) == 0;
	}
	
//	TODO: Add Javadocs!
	public static float abs(final float value) {
		return Math.abs(value);
	}
	
//	TODO: Add Javadocs!
	public static float ceil(final float value) {
		return (float)(Math.ceil(value));
	}
	
//	TODO: Add Javadocs!
	public static float cos(final float angle) {
		return (float)(Math.cos(angle));
	}
	
//	TODO: Add Javadocs!
	public static float floor(final float value) {
		return (float)(Math.floor(value));
	}
	
//	TODO: Add Javadocs!
	public static float lerp(final float a, final float b, final float t) {
		return (1.0F - t) * a + t * b;
	}
	
//	TODO: Add Javadocs!
	public static float max(final float a, final float b, final float c) {
		return Math.max(Math.max(a, b), c);
	}
	
//	TODO: Add Javadocs!
	public static float max(final float a, final float b, final float c, final float d) {
		return Math.max(Math.max(a, b), Math.max(c, d));
	}
	
//	TODO: Add Javadocs!
	public static float min(final float a, final float b) {
		return Math.min(a, b);
	}
	
//	TODO: Add Javadocs!
	public static float min(final float a, final float b, final float c) {
		return Math.min(Math.min(a, b), c);
	}
	
//	TODO: Add Javadocs!
	public static float min(final float a, final float b, final float c, final float d) {
		return Math.min(Math.min(a, b), Math.min(c, d));
	}
	
//	TODO: Add Javadocs!
	public static float saturate(final float value) {
		return saturate(value, 0.0F, 1.0F);
	}
	
//	TODO: Add Javadocs!
	public static float saturate(final float value, final float valueMinimum, final float valueMaximum) {
		return Math.max(Math.min(value, valueMaximum), valueMinimum);
	}
	
//	TODO: Add Javadocs!
	public static float sin(final float angle) {
		return (float)(Math.sin(angle));
	}
	
//	TODO: Add Javadocs!
	public static float toDegrees(final float angle) {
		return (float)(Math.toDegrees(angle));
	}
	
//	TODO: Add Javadocs!
	public static float toRadians(final float angle) {
		return (float)(Math.toRadians(angle));
	}
}