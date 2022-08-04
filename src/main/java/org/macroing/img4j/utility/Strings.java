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

import java.text.DecimalFormat;

/**
 * A class that consists exclusively of static methods that returns or performs various operations on {@code String} instances.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Strings {
	private static final DecimalFormat DECIMAL_FORMAT_DOUBLE = doCreateDecimalFormat(16);
	private static final DecimalFormat DECIMAL_FORMAT_FLOAT = doCreateDecimalFormat(8);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Strings() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of {@code value} without scientific notation and as a Java {@code double} literal.
	 * 
	 * @param value a {@code double} value
	 * @return a {@code String} representation of {@code value} without scientific notation and as a Java {@code double} literal
	 */
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
	
	/**
	 * Returns a {@code String} representation of {@code value} without scientific notation and as a Java {@code double[]} literal.
	 * 
	 * @param value a {@code double[]} value
	 * @return a {@code String} representation of {@code value} without scientific notation and as a Java {@code double[]} literal
	 */
	public static String toNonScientificNotationJava(final double[] value) {
		if(value == null) {
			return "null";
		} else if(value.length == 0) {
			return "new double[0]";
		} else {
			final StringBuilder stringBuilder = new StringBuilder("new double[] {");
			
			for(int i = 0; i < value.length; i++) {
				stringBuilder.append(i > 0 ? ", " : "");
				stringBuilder.append(toNonScientificNotationJava(value[i]));
			}
			
			stringBuilder.append("}");
			
			return stringBuilder.toString();
		}
	}
	
	/**
	 * Returns a {@code String} representation of {@code value} without scientific notation and as a Java {@code float} literal.
	 * 
	 * @param value a {@code float} value
	 * @return a {@code String} representation of {@code value} without scientific notation and as a Java {@code float} literal
	 */
	public static String toNonScientificNotationJava(final float value) {
		if(Float.isNaN(value)) {
			return "Float.NaN";
		} else if(value == Float.NEGATIVE_INFINITY) {
			return "Float.NEGATIVE_INFINITY";
		} else if(value == Float.POSITIVE_INFINITY) {
			return "Float.POSITIVE_INFINITY";
		} else {
			return DECIMAL_FORMAT_FLOAT.format(value).replace(',', '.') + "F";
		}
	}
	
	/**
	 * Returns a {@code String} representation of {@code value} without scientific notation and as a Java {@code float[]} literal.
	 * 
	 * @param value a {@code float[]} value
	 * @return a {@code String} representation of {@code value} without scientific notation and as a Java {@code float[]} literal
	 */
	public static String toNonScientificNotationJava(final float[] value) {
		if(value == null) {
			return "null";
		} else if(value.length == 0) {
			return "new float[0]";
		} else {
			final StringBuilder stringBuilder = new StringBuilder("new float[] {");
			
			for(int i = 0; i < value.length; i++) {
				stringBuilder.append(i > 0 ? ", " : "");
				stringBuilder.append(toNonScientificNotationJava(value[i]));
			}
			
			stringBuilder.append("}");
			
			return stringBuilder.toString();
		}
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