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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public final class StringsUnitTests {
	public StringsUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testToNonScientificNotationJavaDouble() {
		final String a = Strings.toNonScientificNotationJava(100.0D);
		final String b = Strings.toNonScientificNotationJava(-100.0D);
		final String c = Strings.toNonScientificNotationJava(Double.NaN);
		final String d = Strings.toNonScientificNotationJava(Double.NEGATIVE_INFINITY);
		final String e = Strings.toNonScientificNotationJava(Double.POSITIVE_INFINITY);
		
		assertEquals("100.0D", a);
		assertEquals("-100.0D", b);
		assertEquals("Double.NaN", c);
		assertEquals("Double.NEGATIVE_INFINITY", d);
		assertEquals("Double.POSITIVE_INFINITY", e);
	}
	
	@Test
	public void testToNonScientificNotationJavaDoubleArray() {
		assertEquals("null", Strings.toNonScientificNotationJava((double[])(null)));
		assertEquals("new double[0]", Strings.toNonScientificNotationJava(new double[0]));
		assertEquals("new double[] {0.0D, 1.0D, 2.0D}", Strings.toNonScientificNotationJava(new double[] {0.0D, 1.0D, 2.0D}));
	}
	
	@Test
	public void testToNonScientificNotationJavaFloat() {
		final String a = Strings.toNonScientificNotationJava(100.0F);
		final String b = Strings.toNonScientificNotationJava(-100.0F);
		final String c = Strings.toNonScientificNotationJava(Float.NaN);
		final String d = Strings.toNonScientificNotationJava(Float.NEGATIVE_INFINITY);
		final String e = Strings.toNonScientificNotationJava(Float.POSITIVE_INFINITY);
		
		assertEquals("100.0F", a);
		assertEquals("-100.0F", b);
		assertEquals("Float.NaN", c);
		assertEquals("Float.NEGATIVE_INFINITY", d);
		assertEquals("Float.POSITIVE_INFINITY", e);
	}
	
	@Test
	public void testToNonScientificNotationJavaFloatArray() {
		assertEquals("null", Strings.toNonScientificNotationJava((float[])(null)));
		assertEquals("new float[0]", Strings.toNonScientificNotationJava(new float[0]));
		assertEquals("new float[] {0.0F, 1.0F, 2.0F}", Strings.toNonScientificNotationJava(new float[] {0.0F, 1.0F, 2.0F}));
	}
}