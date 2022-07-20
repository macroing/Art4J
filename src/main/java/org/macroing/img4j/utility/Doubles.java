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
public final class Doubles {
	private Doubles() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static boolean equals(final double a, final double b) {
		return Double.compare(a, b) == 0;
	}
	
//	TODO: Add Javadocs!
	public static boolean isZero(final double value) {
		return Double.compare(value, +0.0D) == 0 || Double.compare(value, -0.0D) == 0;
	}
	
//	TODO: Add Javadocs!
	public static double abs(final double value) {
		return Math.abs(value);
	}
	
//	TODO: Add Javadocs!
	public static double ceil(final double value) {
		return Math.ceil(value);
	}
	
//	TODO: Add Javadocs!
	public static double cos(final double angle) {
		return Math.cos(angle);
	}
	
//	TODO: Add Javadocs!
	public static double floor(final double value) {
		return Math.floor(value);
	}
	
//	TODO: Add Javadocs!
	public static double lerp(final double a, final double b, final double t) {
		return (1.0D - t) * a + t * b;
	}
	
//	TODO: Add Javadocs!
	public static double max(final double a, final double b, final double c) {
		return Math.max(Math.max(a, b), c);
	}
	
//	TODO: Add Javadocs!
	public static double max(final double a, final double b, final double c, final double d) {
		return Math.max(Math.max(a, b), Math.max(c, d));
	}
	
//	TODO: Add Javadocs!
	public static double min(final double a, final double b) {
		return Math.min(a, b);
	}
	
//	TODO: Add Javadocs!
	public static double min(final double a, final double b, final double c) {
		return Math.min(Math.min(a, b), c);
	}
	
//	TODO: Add Javadocs!
	public static double min(final double a, final double b, final double c, final double d) {
		return Math.min(Math.min(a, b), Math.min(c, d));
	}
	
//	TODO: Add Javadocs!
	public static double saturate(final double value) {
		return saturate(value, 0.0D, 1.0D);
	}
	
//	TODO: Add Javadocs!
	public static double saturate(final double value, final double valueMinimum, final double valueMaximum) {
		return Math.max(Math.min(value, valueMaximum), valueMinimum);
	}
	
//	TODO: Add Javadocs!
	public static double sin(final double angle) {
		return Math.sin(angle);
	}
	
//	TODO: Add Javadocs!
	public static double toDegrees(final double angle) {
		return Math.toDegrees(angle);
	}
	
//	TODO: Add Javadocs!
	public static double toRadians(final double angle) {
		return Math.toRadians(angle);
	}
}