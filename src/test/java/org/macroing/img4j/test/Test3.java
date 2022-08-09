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
package org.macroing.img4j.test;

import org.macroing.img4j.noise.SimplexNoiseD;
import org.macroing.java.lang.Strings;
import org.macroing.java.util.Randoms;

public final class Test3 {
	private Test3() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
		final double x = Randoms.nextDouble(-1000.0D, 1000.0D);
		
		final double frequency = 1.0D;
		final double gain = 1.0D;
		final double minimum = 0.0D;
		final double maximum = 1.0D;
		
		final int octaves = 16;
		
//		final double noise = SimplexNoiseD.noiseX(x);
		final double noise = SimplexNoiseD.fractionalBrownianMotionX(x, frequency, gain, minimum, maximum, octaves);
		
		System.out.println(Strings.toNonScientificNotationJava(noise));
	}
}