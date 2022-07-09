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
package org.macroing.img4j.color;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

final class Utilities {
	private static final DecimalFormat DECIMAL_FORMAT_DOUBLE = doCreateDecimalFormat(16);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Utilities() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String toNonScientificNotationJava(final double value) {
		if(Double.isNaN(value)) {
			return "Double.NaN";
		} else if(value == Double.NEGATIVE_INFINITY) {
			return "Double.NEGATIVE_INFINITY";
		} else if(value == Double.POSITIVE_INFINITY) {
			return "Double.POSITIVE_INFINITY";
		} else {
			return DECIMAL_FORMAT_DOUBLE.format(value).replace(',', '.') + "D";
		}
	}
	
	public static boolean equals(final double a, final double b) {
		return Double.compare(a, b) == 0;
	}
	
	public static boolean isZero(final double value) {
		return Double.compare(value, +0.0D) == 0 || Double.compare(value, -0.0D) == 0;
	}
	
	public static double lerp(final double a, final double b, final double t) {
		return (1.0D - t) * a + t * b;
	}
	
	public static double max(final double a, final double b, final double c) {
		return Math.max(Math.max(a, b), c);
	}
	
	public static double min(final double a, final double b, final double c) {
		return Math.min(Math.min(a, b), c);
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
	
	public static double saturate(final double value) {
		return saturate(value, 0.0D, 1.0D);
	}
	
	public static double saturate(final double value, final double valueMinimum, final double valueMaximum) {
		return Math.max(Math.min(value, valueMaximum), valueMinimum);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static DecimalFormat doCreateDecimalFormat(final int maximumFractionDigits) {
		final
		DecimalFormat decimalFormat = new DecimalFormat("#");
		decimalFormat.setDecimalSeparatorAlwaysShown(true);
		decimalFormat.setMaximumFractionDigits(maximumFractionDigits);
		decimalFormat.setMinimumFractionDigits(1);
		decimalFormat.setMinimumIntegerDigits(1);
		
		return decimalFormat;
	}
}