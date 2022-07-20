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
	public static final int COLOR_A_R_G_B_SHIFT_A;
	public static final int COLOR_A_R_G_B_SHIFT_B;
	public static final int COLOR_A_R_G_B_SHIFT_G;
	public static final int COLOR_A_R_G_B_SHIFT_R;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final DecimalFormat DECIMAL_FORMAT_DOUBLE;
	private static final double COLOR_SPACE_D_BREAK_POINT;
	private static final double COLOR_SPACE_D_GAMMA;
	private static final double COLOR_SPACE_D_SEGMENT_OFFSET;
	private static final double COLOR_SPACE_D_SLOPE;
	private static final double COLOR_SPACE_D_SLOPE_MATCH;
	private static final float COLOR_SPACE_F_BREAK_POINT;
	private static final float COLOR_SPACE_F_GAMMA;
	private static final float COLOR_SPACE_F_SEGMENT_OFFSET;
	private static final float COLOR_SPACE_F_SLOPE;
	private static final float COLOR_SPACE_F_SLOPE_MATCH;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static {
		COLOR_A_R_G_B_SHIFT_A = 24;
		COLOR_A_R_G_B_SHIFT_B =  0;
		COLOR_A_R_G_B_SHIFT_G =  8;
		COLOR_A_R_G_B_SHIFT_R = 16;
		
		DECIMAL_FORMAT_DOUBLE = doCreateDecimalFormat(16);
		
		COLOR_SPACE_D_BREAK_POINT = 0.00304D;
		COLOR_SPACE_D_GAMMA = 2.4D;
		COLOR_SPACE_D_SLOPE = 1.0D / (COLOR_SPACE_D_GAMMA / Math.pow(COLOR_SPACE_D_BREAK_POINT, 1.0D / COLOR_SPACE_D_GAMMA - 1.0D) - COLOR_SPACE_D_GAMMA * COLOR_SPACE_D_BREAK_POINT + COLOR_SPACE_D_BREAK_POINT);
		COLOR_SPACE_D_SLOPE_MATCH = COLOR_SPACE_D_GAMMA * COLOR_SPACE_D_SLOPE / Math.pow(COLOR_SPACE_D_BREAK_POINT, 1.0D / COLOR_SPACE_D_GAMMA - 1.0D);
		COLOR_SPACE_D_SEGMENT_OFFSET = COLOR_SPACE_D_SLOPE_MATCH * Math.pow(COLOR_SPACE_D_BREAK_POINT, 1.0D / COLOR_SPACE_D_GAMMA) - COLOR_SPACE_D_SLOPE * COLOR_SPACE_D_BREAK_POINT;
		
		COLOR_SPACE_F_BREAK_POINT = 0.00304F;
		COLOR_SPACE_F_GAMMA = 2.4F;
		COLOR_SPACE_F_SLOPE = 1.0F / (COLOR_SPACE_F_GAMMA / (float)(Math.pow(COLOR_SPACE_F_BREAK_POINT, 1.0F / COLOR_SPACE_F_GAMMA - 1.0F)) - COLOR_SPACE_F_GAMMA * COLOR_SPACE_F_BREAK_POINT + COLOR_SPACE_F_BREAK_POINT);
		COLOR_SPACE_F_SLOPE_MATCH = COLOR_SPACE_F_GAMMA * COLOR_SPACE_F_SLOPE / (float)(Math.pow(COLOR_SPACE_F_BREAK_POINT, 1.0F / COLOR_SPACE_F_GAMMA - 1.0F));
		COLOR_SPACE_F_SEGMENT_OFFSET = COLOR_SPACE_F_SLOPE_MATCH * (float)(Math.pow(COLOR_SPACE_F_BREAK_POINT, 1.0F / COLOR_SPACE_F_GAMMA)) - COLOR_SPACE_F_SLOPE * COLOR_SPACE_F_BREAK_POINT;
	}
	
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
	
	public static double redoGammaCorrection(final double value) {
		return value <= COLOR_SPACE_D_BREAK_POINT ? value * COLOR_SPACE_D_SLOPE : COLOR_SPACE_D_SLOPE_MATCH * Math.pow(value, 1.0D / COLOR_SPACE_D_GAMMA) - COLOR_SPACE_D_SEGMENT_OFFSET;
	}
	
	public static double saturate(final double value) {
		return saturate(value, 0.0D, 1.0D);
	}
	
	public static double saturate(final double value, final double valueMinimum, final double valueMaximum) {
		return Math.max(Math.min(value, valueMaximum), valueMinimum);
	}
	
	public static double undoGammaCorrection(final double value) {
		return value <= COLOR_SPACE_D_BREAK_POINT * COLOR_SPACE_D_SLOPE ? value / COLOR_SPACE_D_SLOPE : Math.pow((value + COLOR_SPACE_D_SEGMENT_OFFSET) / COLOR_SPACE_D_SLOPE_MATCH, COLOR_SPACE_D_GAMMA);
	}
	
	public static float lerp(final float a, final float b, final float t) {
		return (1.0F - t) * a + t * b;
	}
	
	public static float max(final float a, final float b, final float c) {
		return Math.max(Math.max(a, b), c);
	}
	
	public static float min(final float a, final float b, final float c) {
		return Math.min(Math.min(a, b), c);
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
	
	public static float redoGammaCorrection(final float value) {
		return value <= COLOR_SPACE_F_BREAK_POINT ? value * COLOR_SPACE_F_SLOPE : COLOR_SPACE_F_SLOPE_MATCH * (float)(Math.pow(value, 1.0F / COLOR_SPACE_F_GAMMA)) - COLOR_SPACE_F_SEGMENT_OFFSET;
	}
	
	public static float saturate(final float value) {
		return saturate(value, 0.0F, 1.0F);
	}
	
	public static float saturate(final float value, final float valueMinimum, final float valueMaximum) {
		return Math.max(Math.min(value, valueMaximum), valueMinimum);
	}
	
	public static float undoGammaCorrection(final float value) {
		return value <= COLOR_SPACE_F_BREAK_POINT * COLOR_SPACE_F_SLOPE ? value / COLOR_SPACE_F_SLOPE : (float)(Math.pow((value + COLOR_SPACE_F_SEGMENT_OFFSET) / COLOR_SPACE_F_SLOPE_MATCH, COLOR_SPACE_F_GAMMA));
	}
	
	public static int convertComponentFromDoubleToInt(final double component) {
		return (int)(saturate(component) * 255.0D + 0.5D);
	}
	
	public static int convertComponentFromFloatToInt(final float component) {
		return (int)(saturate(component) * 255.0F + 0.5F);
	}
	
	public static int nextInt() {
		return nextInt(0, 256);
	}
	
	public static int nextInt(final int origin, final int bound) {
		return ThreadLocalRandom.current().nextInt(origin, bound);
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