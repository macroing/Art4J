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

import java.util.concurrent.ThreadLocalRandom;

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
	
	public static boolean nextBoolean() {
		return ThreadLocalRandom.current().nextBoolean();
	}
	
	public static double nextDouble() {
		return ThreadLocalRandom.current().nextDouble();
	}
	
	public static double nextDouble(final double bound) {
		return ThreadLocalRandom.current().nextDouble(bound);
	}
	
	public static double nextDouble(final double origin, final double bound) {
		return ThreadLocalRandom.current().nextDouble(origin, bound);
	}
	
	public static double rint(final double value) {
		return Math.rint(value);
	}
	
	public static double sqrt(final double value) {
		return Math.sqrt(value);
	}
	
	public static float nextFloat() {
		return ThreadLocalRandom.current().nextFloat();
	}
	
	public static float nextFloat(final float bound) {
		if(bound <= 0.0F) {
			throw new IllegalArgumentException("bound must be positive");
		}
		
		final float result = ThreadLocalRandom.current().nextFloat() * bound;
		
		return (result < bound) ? result : Float.intBitsToFloat(Float.floatToIntBits(bound) - 1);
	}
	
	public static float nextFloat(final float origin, final float bound) {
		if(origin >= bound) {
			throw new IllegalArgumentException("bound must be greater than origin");
		}
		
		float result = (ThreadLocalRandom.current().nextInt() >>> 8) * 0x1.0p-24F;
		
		if(origin < bound) {
			result = result * (bound - origin) + origin;
			
			if(result >= bound) {
				result = Float.intBitsToFloat(Float.floatToIntBits(bound) - 1);
			}
		}
		
		return result;
	}
	
	public static float rint(final float value) {
		return (float)(Math.rint(value));
	}
	
	public static float sqrt(final float value) {
		return (float)(Math.sqrt(value));
	}
}