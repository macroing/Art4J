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
package org.macroing.img4j.kernel;

final class Utilities {
	private Utilities() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static boolean equals(final double a, final double b) {
		return Double.compare(a, b) == 0;
	}
	
	public static boolean isZero(final double value) {
		return Double.compare(value, +0.0D) == 0 || Double.compare(value, -0.0D) == 0;
	}
	
	public static double rint(final double value) {
		return Math.rint(value);
	}
	
	public static double sqrt(final double value) {
		return Math.sqrt(value);
	}
	
	public static float rint(final float value) {
		return (float)(Math.rint(value));
	}
	
	public static float sqrt(final float value) {
		return (float)(Math.sqrt(value));
	}
}