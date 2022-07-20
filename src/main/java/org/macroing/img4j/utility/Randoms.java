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
import java.lang.reflect.Field;//TODO: Add Unit Tests!
import java.util.concurrent.ThreadLocalRandom;

//TODO: Add Javadocs!
//TODO: Add Unit Tests!
public final class Randoms {
	private Randoms() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
//	TODO: Add Unit Tests!
	public static boolean nextBoolean() {
		return ThreadLocalRandom.current().nextBoolean();
	}
	
//	TODO: Add Javadocs!
//	TODO: Add Unit Tests!
	public static double nextDouble() {
		return ThreadLocalRandom.current().nextDouble();
	}
	
//	TODO: Add Javadocs!
//	TODO: Add Unit Tests!
	public static double nextDouble(final double bound) {
		return ThreadLocalRandom.current().nextDouble(bound);
	}
	
//	TODO: Add Javadocs!
//	TODO: Add Unit Tests!
	public static double nextDouble(final double origin, final double bound) {
		return ThreadLocalRandom.current().nextDouble(origin, bound);
	}
	
//	TODO: Add Javadocs!
//	TODO: Add Unit Tests!
	public static float nextFloat() {
		return ThreadLocalRandom.current().nextFloat();
	}
	
//	TODO: Add Javadocs!
//	TODO: Add Unit Tests!
	public static float nextFloat(final float bound) {
		if(bound <= 0.0F) {
			throw new IllegalArgumentException("bound must be positive");
		}
		
		final float result = ThreadLocalRandom.current().nextFloat() * bound;
		
		return (result < bound) ? result : Float.intBitsToFloat(Float.floatToIntBits(bound) - 1);
	}
	
//	TODO: Add Javadocs!
//	TODO: Add Unit Tests!
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
	
//	TODO: Add Javadocs!
//	TODO: Add Unit Tests!
	public static int nextInt() {
		return ThreadLocalRandom.current().nextInt();
	}
	
//	TODO: Add Javadocs!
//	TODO: Add Unit Tests!
	public static int nextInt(final int bound) {
		return ThreadLocalRandom.current().nextInt(bound);
	}
	
//	TODO: Add Javadocs!
//	TODO: Add Unit Tests!
	public static int nextInt(final int origin, final int bound) {
		return ThreadLocalRandom.current().nextInt(origin, bound);
	}
}