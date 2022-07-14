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
	/**
	 * A {@code Color4D} instance that represents the color black.
	 */
	public static final Color4D BLACK;
	
	/**
	 * A {@code Color4D} instance that represents the color blue.
	 */
	public static final Color4D BLUE;
	
	/**
	 * A {@code Color4D} instance that represents the color cyan.
	 */
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
	public static final Color4D TRANSPARENT;
	
//	TODO: Add Javadocs!
	public static final Color4D WHITE;
	
//	TODO: Add Javadocs!
	public static final Color4D YELLOW;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final Map<Color4D, Color4D> CACHE;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static {
		CACHE = new HashMap<>();
		
		BLACK = getCached(new Color4D(0.0D, 0.0D, 0.0D));
		BLUE = getCached(new Color4D(0.0D, 0.0D, 1.0D));
		CYAN = getCached(new Color4D(0.0D, 1.0D, 1.0D));
		GRAY = getCached(new Color4D(0.5D, 0.5D, 0.5D));
		GREEN = getCached(new Color4D(0.0D, 1.0D, 0.0D));
		MAGENTA = getCached(new Color4D(1.0D, 0.0D, 1.0D));
		RED = getCached(new Color4D(1.0D, 0.0D, 0.0D));
		TRANSPARENT = getCached(new Color4D(0.0D, 0.0D, 0.0D, 0.0D));
		WHITE = getCached(new Color4D(1.0D, 1.0D, 1.0D));
		YELLOW = getCached(new Color4D(1.0D, 1.0D, 0.0D));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The value of the alpha component.
	 */
	public final double a;
	
	/**
	 * The value of the blue component.
	 */
	public final double b;
	
	/**
	 * The value of the green component.
	 */
	public final double g;
	
	/**
	 * The value of the red component.
	 */
	public final double r;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Color4D} instance that represents black.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color4D(0.0D);
	 * }
	 * </pre>
	 */
	public Color4D() {
		this(0.0D);
	}
	
//	TODO: Add Javadocs!
	public Color4D(final double grayscale) {
		this(grayscale, 1.0D);
	}
	
//	TODO: Add Javadocs!
	public Color4D(final double grayscale, final double a) {
		this(grayscale, grayscale, grayscale, a);
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
	
	/**
	 * Returns a {@code String} representation of this {@code Color4D} instance.
	 * 
	 * @return a {@code String} representation of this {@code Color4D} instance
	 */
	@Override
	public String toString() {
		return String.format("new Color4D(%s, %s, %s, %s)", Utilities.toNonScientificNotationJava(this.r), Utilities.toNonScientificNotationJava(this.g), Utilities.toNonScientificNotationJava(this.b), Utilities.toNonScientificNotationJava(this.a));
	}
	
	/**
	 * Compares {@code color} to this {@code Color4D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code color} is equal to this {@code Color4D} instance, {@code false} otherwise.
	 * 
	 * @param color the {@code Color4D} to compare to this {@code Color4D} instance for equality
	 * @return {@code true} if, and only if, {@code color} is equal to this {@code Color4D} instance, {@code false} otherwise
	 */
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
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4D} instance is black, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color4D} instance is black, {@code false} otherwise
	 */
	public boolean isBlack() {
		return Utilities.isZero(this.r) && Utilities.isZero(this.g) && Utilities.isZero(this.b);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4D} instance is blue, {@code false} otherwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.isBlue(1.0D, 1.0D);
	 * }
	 * </pre>
	 * 
	 * @return {@code true} if, and only if, this {@code Color4D} instance is blue, {@code false} otherwise
	 */
	public boolean isBlue() {
		return isBlue(1.0D, 1.0D);
	}
	
//	TODO: Add Javadocs!
	public boolean isBlue(final double deltaR, final double deltaG) {
		return this.b - deltaR >= this.r && this.b - deltaG >= this.g;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4D} instance is cyan, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color4D} instance is cyan, {@code false} otherwise
	 */
	public boolean isCyan() {
		return Utilities.equals(this.g, this.b) && this.r < this.g;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4D} instance is grayscale, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color4D} instance is grayscale, {@code false} otherwise
	 */
	public boolean isGrayscale() {
		return Utilities.equals(this.r, this.g) && Utilities.equals(this.g, this.b);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4D} instance is green, {@code false} otherwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.isGreen(1.0D, 1.0D);
	 * }
	 * </pre>
	 * 
	 * @return {@code true} if, and only if, this {@code Color4D} instance is green, {@code false} otherwise
	 */
	public boolean isGreen() {
		return isGreen(1.0D, 1.0D);
	}
	
//	TODO: Add Javadocs!
	public boolean isGreen(final double deltaR, final double deltaB) {
		return this.g - deltaR >= this.r && this.g - deltaB >= this.b;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4D} instance is magenta, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color4D} instance is magenta, {@code false} otherwise
	 */
	public boolean isMagenta() {
		return Utilities.equals(this.r, this.b) && this.g < this.b;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4D} instance is red, {@code false} otherwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.isRed(1.0D, 1.0D);
	 * }
	 * </pre>
	 * 
	 * @return {@code true} if, and only if, this {@code Color4D} instance is red, {@code false} otherwise
	 */
	public boolean isRed() {
		return isRed(1.0D, 1.0D);
	}
	
//	TODO: Add Javadocs!
	public boolean isRed(final double deltaG, final double deltaB) {
		return this.r - deltaG >= this.g && this.r - deltaB >= this.b;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4D} instance is white, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color4D} instance is white, {@code false} otherwise
	 */
	public boolean isWhite() {
		return Utilities.equals(this.r, 1.0D) && Utilities.equals(this.g, 1.0D) && Utilities.equals(this.b, 1.0D);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4D} instance is yellow, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color4D} instance is yellow, {@code false} otherwise
	 */
	public boolean isYellow() {
		return Utilities.equals(this.r, this.g) && this.b < this.r;
	}
	
	/**
	 * Returns the average component value of {@link #r}, {@link #g} and {@link #b}.
	 * 
	 * @return the average component value of {@code  r}, {@code  g} and {@code  b}
	 */
	public double average() {
		return (this.r + this.g + this.b) / 3.0D; 
	}
	
	/**
	 * Returns the lightness for this {@code Color4D} instance.
	 * 
	 * @return the lightness for this {@code Color4D} instance
	 */
	public double lightness() {
		return (max() + min()) / 2.0D;
	}
	
	/**
	 * Returns the largest component value of {@link #r}, {@link #g} and {@link #b}.
	 * 
	 * @return the largest component value of {@code  r}, {@code  g} and {@code  b}
	 */
	public double max() {
		return Utilities.max(this.r, this.g, this.b);
	}
	
	/**
	 * Returns the smallest component value of {@link #r}, {@link #g} and {@link #b}.
	 * 
	 * @return the smallest component value of {@code  r}, {@code  g} and {@code  b}
	 */
	public double min() {
		return Utilities.min(this.r, this.g, this.b);
	}
	
	/**
	 * Returns the relative luminance for this {@code Color4D} instance.
	 * 
	 * @return the relative luminance for this {@code Color4D} instance
	 */
	public double relativeLuminance() {
		return this.r * 0.212671D + this.g * 0.715160D + this.b * 0.072169D;
	}
	
	/**
	 * Returns a hash code for this {@code Color4D} instance.
	 * 
	 * @return a hash code for this {@code Color4D} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Double.valueOf(this.a), Double.valueOf(this.b), Double.valueOf(this.g), Double.valueOf(this.r));
	}
	
//	TODO: Add Javadocs!
	public int toIntA() {
		return Utilities.convertComponentFromDoubleToInt(this.a);
	}
	
//	TODO: Add Javadocs!
	public int toIntARGB() {
		final int a = ((toIntA() & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_A);
		final int r = ((toIntR() & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_R);
		final int g = ((toIntG() & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_G);
		final int b = ((toIntB() & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_B);
		
		return a | r | g | b;
	}
	
//	TODO: Add Javadocs!
	public int toIntB() {
		return Utilities.convertComponentFromDoubleToInt(this.b);
	}
	
//	TODO: Add Javadocs!
	public int toIntG() {
		return Utilities.convertComponentFromDoubleToInt(this.g);
	}
	
//	TODO: Add Javadocs!
	public int toIntR() {
		return Utilities.convertComponentFromDoubleToInt(this.r);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static Color4D addRGB(final Color4D colorLHS, final Color4D colorRHS) {
		final double r = colorLHS.r + colorRHS.r;
		final double g = colorLHS.g + colorRHS.g;
		final double b = colorLHS.b + colorRHS.b;
		final double a = colorLHS.a;
		
		return new Color4D(r, g, b, a);
	}
	
//	TODO: Add Javadocs!
	public static Color4D addRGB(final Color4D color, final double s) {
		final double r = color.r + s;
		final double g = color.g + s;
		final double b = color.b + s;
		final double a = color.a;
		
		return new Color4D(r, g, b, a);
	}
	
//	TODO: Add Javadocs!
	public static Color4D blend(final Color4D colorLHS, final Color4D colorRHS) {
		return blend(colorLHS, colorRHS, 0.5D);
	}
	
//	TODO: Add Javadocs!
	public static Color4D blend(final Color4D color11, final Color4D color12, final Color4D color21, final Color4D color22, final double tX, final double tY) {
		return blend(blend(color11, color12, tX), blend(color21, color22, tX), tY);
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
	public static Color4D blendOver(final Color4D colorLHS, final Color4D colorRHS) {
		final double a = colorLHS.a + colorRHS.a * (1.0D - colorLHS.a);
		final double r = (colorLHS.r * colorLHS.a + colorRHS.r * colorRHS.a * (1.0D - colorLHS.a)) / a;
		final double g = (colorLHS.g * colorLHS.a + colorRHS.g * colorRHS.a * (1.0D - colorLHS.a)) / a;
		final double b = (colorLHS.b * colorLHS.a + colorRHS.b * colorRHS.a * (1.0D - colorLHS.a)) / a;
		
		return new Color4D(r, g, b, a);
	}
	
//	TODO: Add Javadocs!
	public static Color4D fromIntARGB(final int colorARGB) {
		final int a = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_A) & 0xFF;
		final int r = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_R) & 0xFF;
		final int g = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_G) & 0xFF;
		final int b = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_B) & 0xFF;
		
		return new Color4D(r, g, b, a);
	}
	
	/**
	 * Returns a cached {@code Color4D} instance that is equal to {@code color}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color4D} instance
	 * @return a cached {@code Color4D} instance that is equal to {@code color}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color4D getCached(final Color4D color) {
		return CACHE.computeIfAbsent(Objects.requireNonNull(color, "color == null"), key -> color);
	}
	
//	TODO: Add Javadocs!
	public static Color4D grayscaleAverage(final Color4D color) {
		return new Color4D(color.average(), color.a);
	}
	
//	TODO: Add Javadocs!
	public static Color4D grayscaleB(final Color4D color) {
		return new Color4D(color.b, color.a);
	}
	
//	TODO: Add Javadocs!
	public static Color4D grayscaleG(final Color4D color) {
		return new Color4D(color.g, color.a);
	}
	
//	TODO: Add Javadocs!
	public static Color4D grayscaleLightness(final Color4D color) {
		return new Color4D(color.lightness(), color.a);
	}
	
//	TODO: Add Javadocs!
	public static Color4D grayscaleMax(final Color4D color) {
		return new Color4D(color.max(), color.a);
	}
	
//	TODO: Add Javadocs!
	public static Color4D grayscaleMin(final Color4D color) {
		return new Color4D(color.min(), color.a);
	}
	
//	TODO: Add Javadocs!
	public static Color4D grayscaleR(final Color4D color) {
		return new Color4D(color.r, color.a);
	}
	
//	TODO: Add Javadocs!
	public static Color4D grayscaleRelativeLuminance(final Color4D color) {
		return new Color4D(color.relativeLuminance(), color.a);
	}
	
//	TODO: Add Javadocs!
	public static Color4D invert(final Color4D color) {
		return new Color4D(1.0D - color.r, 1.0D - color.g, 1.0D - color.b, color.a);
	}
	
//	TODO: Add Javadocs!
	public static Color4D multiplyRGB(final Color4D color, final double s) {
		final double r = color.r * s;
		final double g = color.g * s;
		final double b = color.b * s;
		final double a = color.a;
		
		return new Color4D(r, g, b, a);
	}
	
//	TODO: Add Javadocs!
	public static Color4D random() {
		return new Color4D(Utilities.nextDouble(), Utilities.nextDouble(), Utilities.nextDouble());
	}
	
//	TODO: Add Javadocs!
	public static Color4D randomBlue() {
		return randomBlue(0.0D, 0.0D);
	}
	
//	TODO: Add Javadocs!
	public static Color4D randomBlue(final double maxR, final double maxG) {
		final double b = Utilities.nextDouble(Math.nextUp(0.0D), Math.nextUp(1.0D));
		final double r = Utilities.nextDouble(0.0D, Math.min(Math.nextUp(Math.max(maxR, 0.0D)), b));
		final double g = Utilities.nextDouble(0.0D, Math.min(Math.nextUp(Math.max(maxG, 0.0D)), b));
		
		return new Color4D(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color4D randomCyan() {
		return randomCyan(0.0D, 0.0D);
	}
	
//	TODO: Add Javadocs!
	public static Color4D randomCyan(final double minGB, final double maxR) {
		final double x = Utilities.nextDouble(Math.max(Math.min(minGB, 1.0D), Math.nextUp(0.0D)), Math.nextUp(1.0D));
		final double y = Utilities.nextDouble(0.0D, Math.min(Math.nextUp(Math.max(maxR, 0.0D)), x));
		
		return new Color4D(y, x, x);
	}
	
//	TODO: Add Javadocs!
	public static Color4D randomGrayscale() {
		return new Color4D(Utilities.nextDouble(0.0D, Math.nextUp(1.0D)), 1.0D);
	}
	
//	TODO: Add Javadocs!
	public static Color4D randomGreen() {
		return randomGreen(0.0D, 0.0D);
	}
	
//	TODO: Add Javadocs!
	public static Color4D randomGreen(final double maxR, final double maxB) {
		final double g = Utilities.nextDouble(Math.nextUp(0.0D), Math.nextUp(1.0D));
		final double r = Utilities.nextDouble(0.0D, Math.min(Math.nextUp(Math.max(maxR, 0.0D)), g));
		final double b = Utilities.nextDouble(0.0D, Math.min(Math.nextUp(Math.max(maxB, 0.0D)), g));
		
		return new Color4D(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color4D randomMagenta() {
		return randomMagenta(0.0D, 0.0D);
	}
	
//	TODO: Add Javadocs!
	public static Color4D randomMagenta(final double minRB, final double maxG) {
		final double x = Utilities.nextDouble(Math.max(Math.min(minRB, 1.0D), Math.nextUp(0.0D)), Math.nextUp(1.0D));
		final double y = Utilities.nextDouble(0.0D, Math.min(Math.nextUp(Math.max(maxG, 0.0D)), x));
		
		return new Color4D(x, y, x);
	}
	
//	TODO: Add Javadocs!
	public static Color4D randomRed() {
		return randomRed(0.0D, 0.0D);
	}
	
//	TODO: Add Javadocs!
	public static Color4D randomRed(final double maxG, final double maxB) {
		final double r = Utilities.nextDouble(Math.nextUp(0.0D), Math.nextUp(1.0D));
		final double g = Utilities.nextDouble(0.0D, Math.min(Math.nextUp(Math.max(maxG, 0.0D)), r));
		final double b = Utilities.nextDouble(0.0D, Math.min(Math.nextUp(Math.max(maxB, 0.0D)), r));
		
		return new Color4D(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color4D randomYellow() {
		return randomYellow(0.0D, 0.0D);
	}
	
//	TODO: Add Javadocs!
	public static Color4D randomYellow(final double minRG, final double maxB) {
		final double x = Utilities.nextDouble(Math.max(Math.min(minRG, 1.0D), Math.nextUp(0.0D)), Math.nextUp(1.0D));
		final double y = Utilities.nextDouble(0.0D, Math.min(Math.nextUp(Math.max(maxB, 0.0D)), x));
		
		return new Color4D(x, x, y);
	}
	
//	TODO: Add Javadocs!
	public static Color4D redoGammaCorrection(final Color4D color) {
		return new Color4D(Utilities.redoGammaCorrection(color.r), Utilities.redoGammaCorrection(color.g), Utilities.redoGammaCorrection(color.b), color.a);
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
	public static Color4D undoGammaCorrection(final Color4D color) {
		return new Color4D(Utilities.undoGammaCorrection(color.r), Utilities.undoGammaCorrection(color.g), Utilities.undoGammaCorrection(color.b), color.a);
	}
	
//	TODO: Add Javadocs!
	public static int getCacheSize() {
		return CACHE.size();
	}
	
//	TODO: Add Javadocs!
	public static void clearCache() {
		CACHE.clear();
	}
}