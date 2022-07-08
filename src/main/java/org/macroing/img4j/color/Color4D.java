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

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//TODO: Add Javadocs!
public final class Color4D {
//	TODO: Add Javadocs!
	public static final Color4D BLACK;
	
//	TODO: Add Javadocs!
	public static final Color4D BLUE;
	
//	TODO: Add Javadocs!
	public static final Color4D CYAN;
	
//	TODO: Add Javadocs!
	public static final Color4D GRAY;
	
//	TODO: Add Javadocs!
	public static final Color4D GREEN;
	
//	TODO: Add Javadocs!
	public static final Color4D MAGENTA;
	
//	TODO: Add Javadocs!
	public static final Color4D RED;
	
//	TODO: Add Javadocs!
	public static final Color4D WHITE;
	
//	TODO: Add Javadocs!
	public static final Color4D YELLOW;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final Map<Color4D, Color4D> CACHE;
	private static final int COLOR_A_R_G_B_SHIFT_A;
	private static final int COLOR_A_R_G_B_SHIFT_B;
	private static final int COLOR_A_R_G_B_SHIFT_G;
	private static final int COLOR_A_R_G_B_SHIFT_R;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static {
		CACHE = new HashMap<>();
		
		COLOR_A_R_G_B_SHIFT_A = 24;
		COLOR_A_R_G_B_SHIFT_B =  0;
		COLOR_A_R_G_B_SHIFT_G =  8;
		COLOR_A_R_G_B_SHIFT_R = 16;
		
		BLACK = getCached(new Color4D(0.0D, 0.0D, 0.0D));
		BLUE = getCached(new Color4D(0.0D, 0.0D, 1.0D));
		CYAN = getCached(new Color4D(0.0D, 1.0D, 1.0D));
		GRAY = getCached(new Color4D(0.5D, 0.5D, 0.5D));
		GREEN = getCached(new Color4D(0.0D, 1.0D, 0.0D));
		MAGENTA = getCached(new Color4D(1.0D, 0.0D, 1.0D));
		RED = getCached(new Color4D(1.0D, 0.0D, 0.0D));
		WHITE = getCached(new Color4D(1.0D, 1.0D, 1.0D));
		YELLOW = getCached(new Color4D(1.0D, 1.0D, 0.0D));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public final double a;
	
//	TODO: Add Javadocs!
	public final double b;
	
//	TODO: Add Javadocs!
	public final double g;
	
//	TODO: Add Javadocs!
	public final double r;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Color4D() {
		this(0.0D);
	}
	
//	TODO: Add Javadocs!
	public Color4D(final double grayscale) {
		this(grayscale, grayscale, grayscale);
	}
	
//	TODO: Add Javadocs!
	public Color4D(final double r, final double g, final double b) {
		this(r, g, b, 1.0D);
	}
	
//	TODO: Add Javadocs!
	public Color4D(final double r, final double g, final double b, final double a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
//	TODO: Add Javadocs!
	public Color4D(final int grayscale) {
		this(grayscale, grayscale, grayscale);
	}
	
//	TODO: Add Javadocs!
	public Color4D(final int r, final int g, final int b) {
		this(r, g, b, 255);
	}
	
//	TODO: Add Javadocs!
	public Color4D(final int r, final int g, final int b, final int a) {
		this(r / 255.0D, g / 255.0D, b / 255.0D, a / 255.0D);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public boolean equals(final Color4D color) {
		if(color == this) {
			return true;
		} else if(color == null) {
			return false;
		} else if(Double.compare(this.a, color.a) != 0) {
			return false;
		} else if(Double.compare(this.b, color.b) != 0) {
			return false;
		} else if(Double.compare(this.g, color.g) != 0) {
			return false;
		} else if(Double.compare(this.r, color.r) != 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Compares {@code object} to this {@code Color4D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Color4D}, and they are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Color4D} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Color4D}, and they are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Color4D)) {
			return false;
		} else {
			return equals(Color4D.class.cast(object));
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(Double.valueOf(this.a), Double.valueOf(this.b), Double.valueOf(this.g), Double.valueOf(this.r));
	}
	
//	TODO: Add Javadocs!
	public int toIntA() {
		return doConvertComponentFromDoubleToInt(this.a);
	}
	
//	TODO: Add Javadocs!
	public int toIntARGB() {
		final int a = ((toIntA() & 0xFF) << COLOR_A_R_G_B_SHIFT_A);
		final int r = ((toIntR() & 0xFF) << COLOR_A_R_G_B_SHIFT_R);
		final int g = ((toIntG() & 0xFF) << COLOR_A_R_G_B_SHIFT_G);
		final int b = ((toIntB() & 0xFF) << COLOR_A_R_G_B_SHIFT_B);
		
		return a | r | g | b;
	}
	
//	TODO: Add Javadocs!
	public int toIntB() {
		return doConvertComponentFromDoubleToInt(this.b);
	}
	
//	TODO: Add Javadocs!
	public int toIntG() {
		return doConvertComponentFromDoubleToInt(this.g);
	}
	
//	TODO: Add Javadocs!
	public int toIntR() {
		return doConvertComponentFromDoubleToInt(this.r);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static Color4D blend(final Color4D colorLHS, final Color4D colorRHS) {
		return blend(colorLHS, colorRHS, 0.5D);
	}
	
//	TODO: Add Javadocs!
	public static Color4D blend(final Color4D colorLHS, final Color4D colorRHS, final double t) {
		return blend(colorLHS, colorRHS, t, t, t, t);
	}
	
//	TODO: Add Javadocs!
	public static Color4D blend(final Color4D colorLHS, final Color4D colorRHS, final double tR, final double tG, final double tB, final double tA) {
		final double r = Utilities.lerp(colorLHS.r, colorRHS.r, tR);
		final double g = Utilities.lerp(colorLHS.g, colorRHS.g, tG);
		final double b = Utilities.lerp(colorLHS.b, colorRHS.b, tB);
		final double a = Utilities.lerp(colorLHS.a, colorRHS.a, tA);
		
		return new Color4D(r, g, b, a);
	}
	
//	TODO: Add Javadocs!
	public static Color4D fromIntARGB(final int colorARGB) {
		final int a = (colorARGB >> COLOR_A_R_G_B_SHIFT_A) & 0xFF;
		final int r = (colorARGB >> COLOR_A_R_G_B_SHIFT_R) & 0xFF;
		final int g = (colorARGB >> COLOR_A_R_G_B_SHIFT_G) & 0xFF;
		final int b = (colorARGB >> COLOR_A_R_G_B_SHIFT_B) & 0xFF;
		
		return new Color4D(r, g, b, a);
	}
	
//	TODO: Add Javadocs!
	public static Color4D getCached(final Color4D color) {
		return CACHE.computeIfAbsent(Objects.requireNonNull(color, "color == null"), key -> color);
	}
	
//	TODO: Add Javadocs!
	public static Color4D sepia(final Color4D color) {
		final double r = color.r * 0.393D + color.g * 0.769D + color.b * 0.189D;
		final double g = color.r * 0.349D + color.g * 0.686D + color.b * 0.168D;
		final double b = color.r * 0.272D + color.g * 0.534D + color.b * 0.131D;
		final double a = color.a;
		
		return new Color4D(r, g, b, a);
	}
	
//	TODO: Add Javadocs!
	public static int getCacheSize() {
		return CACHE.size();
	}
	
//	TODO: Add Javadocs!
	public static void clearCache() {
		CACHE.clear();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static int doConvertComponentFromDoubleToInt(final double component) {
		return (int)(Utilities.saturate(component) * 255.0D + 0.5D);
	}
}