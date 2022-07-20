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

import org.macroing.img4j.utility.Doubles;
import org.macroing.img4j.utility.Randoms;
import org.macroing.img4j.utility.Strings;

//TODO: Add Javadocs!
public final class Color3D {
	/**
	 * A {@code Color3D} instance that represents the color black.
	 */
	public static final Color3D BLACK;
	
	/**
	 * A {@code Color3D} instance that represents the color blue.
	 */
	public static final Color3D BLUE;
	
	/**
	 * A {@code Color3D} instance that represents the color cyan.
	 */
	public static final Color3D CYAN;
	
	/**
	 * A {@code Color3D} instance that represents the color gray.
	 */
	public static final Color3D GRAY;
	
	/**
	 * A {@code Color3D} instance that represents the color green.
	 */
	public static final Color3D GREEN;
	
	/**
	 * A {@code Color3D} instance that represents the color magenta.
	 */
	public static final Color3D MAGENTA;
	
	/**
	 * A {@code Color3D} instance that represents the color red.
	 */
	public static final Color3D RED;
	
	/**
	 * A {@code Color3D} instance that represents the color white.
	 */
	public static final Color3D WHITE;
	
	/**
	 * A {@code Color3D} instance that represents the color yellow.
	 */
	public static final Color3D YELLOW;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final Map<Color3D, Color3D> CACHE;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static {
		CACHE = new HashMap<>();
		
		BLACK = getCached(new Color3D(0.0D, 0.0D, 0.0D));
		BLUE = getCached(new Color3D(0.0D, 0.0D, 1.0D));
		CYAN = getCached(new Color3D(0.0D, 1.0D, 1.0D));
		GRAY = getCached(new Color3D(0.5D, 0.5D, 0.5D));
		GREEN = getCached(new Color3D(0.0D, 1.0D, 0.0D));
		MAGENTA = getCached(new Color3D(1.0D, 0.0D, 1.0D));
		RED = getCached(new Color3D(1.0D, 0.0D, 0.0D));
		WHITE = getCached(new Color3D(1.0D, 1.0D, 1.0D));
		YELLOW = getCached(new Color3D(1.0D, 1.0D, 0.0D));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
	 * Constructs a new {@code Color3D} instance that represents black.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color3D(0.0D);
	 * }
	 * </pre>
	 */
	public Color3D() {
		this(0.0D);
	}
	
//	TODO: Add Javadocs!
	public Color3D(final Color3F color) {
		this(color.r, color.g, color.b);
	}
	
//	TODO: Add Javadocs!
	public Color3D(final Color4D color) {
		this(color.r, color.g, color.b);
	}
	
//	TODO: Add Javadocs!
	public Color3D(final Color4F color) {
		this(color.r, color.g, color.b);
	}
	
	/**
	 * Constructs a new {@code Color3D} instance that represents gray.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color3D(grayscale, grayscale, grayscale);
	 * }
	 * </pre>
	 * 
	 * @param grayscale the value of the red, green and blue components
	 */
	public Color3D(final double grayscale) {
		this(grayscale, grayscale, grayscale);
	}
	
	/**
	 * Constructs a new {@code Color3D} instance.
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 */
	public Color3D(final double r, final double g, final double b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	/**
	 * Constructs a new {@code Color3D} instance that represents gray.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color3D(grayscale, grayscale, grayscale);
	 * }
	 * </pre>
	 * 
	 * @param grayscale the value of the red, green and blue components
	 */
	public Color3D(final int grayscale) {
		this(grayscale, grayscale, grayscale);
	}
	
	/**
	 * Constructs a new {@code Color3D} instance.
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 */
	public Color3D(final int r, final int g, final int b) {
		this(r / 255.0D, g / 255.0D, b / 255.0D);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code Color3D} instance.
	 * 
	 * @return a {@code String} representation of this {@code Color3D} instance
	 */
	@Override
	public String toString() {
		return String.format("new Color3D(%s, %s, %s)", Strings.toNonScientificNotationJava(this.r), Strings.toNonScientificNotationJava(this.g), Strings.toNonScientificNotationJava(this.b));
	}
	
	/**
	 * Compares {@code color} to this {@code Color3D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code color} is equal to this {@code Color3D} instance, {@code false} otherwise.
	 * 
	 * @param color the {@code Color3D} to compare to this {@code Color3D} instance for equality
	 * @return {@code true} if, and only if, {@code color} is equal to this {@code Color3D} instance, {@code false} otherwise
	 */
	public boolean equals(final Color3D color) {
		if(color == this) {
			return true;
		} else if(color == null) {
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
	 * Compares {@code object} to this {@code Color3D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Color3D}, and they are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Color3D} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Color3D}, and they are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Color3D)) {
			return false;
		} else {
			return equals(Color3D.class.cast(object));
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3D} instance is black, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color3D} instance is black, {@code false} otherwise
	 */
	public boolean isBlack() {
		return Doubles.isZero(this.r) && Doubles.isZero(this.g) && Doubles.isZero(this.b);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3D} instance is blue, {@code false} otherwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.isBlue(1.0D, 1.0D);
	 * }
	 * </pre>
	 * 
	 * @return {@code true} if, and only if, this {@code Color3D} instance is blue, {@code false} otherwise
	 */
	public boolean isBlue() {
		return isBlue(1.0D, 1.0D);
	}
	
//	TODO: Add Javadocs!
	public boolean isBlue(final double deltaR, final double deltaG) {
		return this.b - deltaR >= this.r && this.b - deltaG >= this.g;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3D} instance is cyan, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color3D} instance is cyan, {@code false} otherwise
	 */
	public boolean isCyan() {
		return Doubles.equals(this.g, this.b) && this.r < this.g;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3D} instance is grayscale, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color3D} instance is grayscale, {@code false} otherwise
	 */
	public boolean isGrayscale() {
		return Doubles.equals(this.r, this.g) && Doubles.equals(this.g, this.b);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3D} instance is green, {@code false} otherwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.isGreen(1.0D, 1.0D);
	 * }
	 * </pre>
	 * 
	 * @return {@code true} if, and only if, this {@code Color3D} instance is green, {@code false} otherwise
	 */
	public boolean isGreen() {
		return isGreen(1.0D, 1.0D);
	}
	
//	TODO: Add Javadocs!
	public boolean isGreen(final double deltaR, final double deltaB) {
		return this.g - deltaR >= this.r && this.g - deltaB >= this.b;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3D} instance is magenta, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color3D} instance is magenta, {@code false} otherwise
	 */
	public boolean isMagenta() {
		return Doubles.equals(this.r, this.b) && this.g < this.b;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3D} instance is red, {@code false} otherwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.isRed(1.0D, 1.0D);
	 * }
	 * </pre>
	 * 
	 * @return {@code true} if, and only if, this {@code Color3D} instance is red, {@code false} otherwise
	 */
	public boolean isRed() {
		return isRed(1.0D, 1.0D);
	}
	
//	TODO: Add Javadocs!
	public boolean isRed(final double deltaG, final double deltaB) {
		return this.r - deltaG >= this.g && this.r - deltaB >= this.b;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3D} instance is white, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color3D} instance is white, {@code false} otherwise
	 */
	public boolean isWhite() {
		return Doubles.equals(this.r, 1.0D) && Doubles.equals(this.g, 1.0D) && Doubles.equals(this.b, 1.0D);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3D} instance is yellow, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color3D} instance is yellow, {@code false} otherwise
	 */
	public boolean isYellow() {
		return Doubles.equals(this.r, this.g) && this.b < this.r;
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
	 * Returns the lightness for this {@code Color3D} instance.
	 * 
	 * @return the lightness for this {@code Color3D} instance
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
		return Doubles.max(this.r, this.g, this.b);
	}
	
	/**
	 * Returns the smallest component value of {@link #r}, {@link #g} and {@link #b}.
	 * 
	 * @return the smallest component value of {@code  r}, {@code  g} and {@code  b}
	 */
	public double min() {
		return Doubles.min(this.r, this.g, this.b);
	}
	
	/**
	 * Returns the relative luminance for this {@code Color3D} instance.
	 * 
	 * @return the relative luminance for this {@code Color3D} instance
	 */
	public double relativeLuminance() {
		return this.r * 0.212671D + this.g * 0.715160D + this.b * 0.072169D;
	}
	
	/**
	 * Returns a hash code for this {@code Color3D} instance.
	 * 
	 * @return a hash code for this {@code Color3D} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Double.valueOf(this.b), Double.valueOf(this.g), Double.valueOf(this.r));
	}
	
//	TODO: Add Javadocs!
	public int toIntARGB() {
		final int a = ((255      & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_A);
		final int r = ((toIntR() & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_R);
		final int g = ((toIntG() & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_G);
		final int b = ((toIntB() & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_B);
		
		return a | r | g | b;
	}
	
	/**
	 * Returns the blue component as an {@code int}.
	 * 
	 * @return the blue component as an {@code int}
	 */
	public int toIntB() {
		return Utilities.convertComponentFromDoubleToInt(this.b);
	}
	
	/**
	 * Returns the green component as an {@code int}.
	 * 
	 * @return the green component as an {@code int}
	 */
	public int toIntG() {
		return Utilities.convertComponentFromDoubleToInt(this.g);
	}
	
	/**
	 * Returns the red component as an {@code int}.
	 * 
	 * @return the red component as an {@code int}
	 */
	public int toIntR() {
		return Utilities.convertComponentFromDoubleToInt(this.r);
	}
	
//	TODO: Add Javadocs!
	public int toIntRGB() {
		final int r = ((toIntR() & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_R);
		final int g = ((toIntG() & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_G);
		final int b = ((toIntB() & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_B);
		
		return r | g | b;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static Color3D addRGB(final Color3D colorLHS, final Color3D colorRHS) {
		final double r = colorLHS.r + colorRHS.r;
		final double g = colorLHS.g + colorRHS.g;
		final double b = colorLHS.b + colorRHS.b;
		
		return new Color3D(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color3D addRGB(final Color3D color, final double s) {
		final double r = color.r + s;
		final double g = color.g + s;
		final double b = color.b + s;
		
		return new Color3D(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color3D blend(final Color3D colorLHS, final Color3D colorRHS) {
		return blend(colorLHS, colorRHS, 0.5D);
	}
	
//	TODO: Add Javadocs!
	public static Color3D blend(final Color3D color11, final Color3D color12, final Color3D color21, final Color3D color22, final double tX, final double tY) {
		return blend(blend(color11, color12, tX), blend(color21, color22, tX), tY);
	}
	
//	TODO: Add Javadocs!
	public static Color3D blend(final Color3D colorLHS, final Color3D colorRHS, final double t) {
		return blend(colorLHS, colorRHS, t, t, t);
	}
	
//	TODO: Add Javadocs!
	public static Color3D blend(final Color3D colorLHS, final Color3D colorRHS, final double tR, final double tG, final double tB) {
		final double r = Doubles.lerp(colorLHS.r, colorRHS.r, tR);
		final double g = Doubles.lerp(colorLHS.g, colorRHS.g, tG);
		final double b = Doubles.lerp(colorLHS.b, colorRHS.b, tB);
		
		return new Color3D(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color3D fromIntARGB(final int colorARGB) {
		final int r = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_R) & 0xFF;
		final int g = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_G) & 0xFF;
		final int b = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_B) & 0xFF;
		
		return new Color3D(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color3D fromIntRGB(final int colorRGB) {
		final int r = (colorRGB >> Utilities.COLOR_A_R_G_B_SHIFT_R) & 0xFF;
		final int g = (colorRGB >> Utilities.COLOR_A_R_G_B_SHIFT_G) & 0xFF;
		final int b = (colorRGB >> Utilities.COLOR_A_R_G_B_SHIFT_B) & 0xFF;
		
		return new Color3D(r, g, b);
	}
	
	/**
	 * Returns a cached {@code Color3D} instance that is equal to {@code color}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color3D} instance
	 * @return a cached {@code Color3D} instance that is equal to {@code color}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color3D getCached(final Color3D color) {
		return CACHE.computeIfAbsent(Objects.requireNonNull(color, "color == null"), key -> color);
	}
	
//	TODO: Add Javadocs!
	public static Color3D grayscaleAverage(final Color3D color) {
		return new Color3D(color.average());
	}
	
//	TODO: Add Javadocs!
	public static Color3D grayscaleB(final Color3D color) {
		return new Color3D(color.b);
	}
	
//	TODO: Add Javadocs!
	public static Color3D grayscaleG(final Color3D color) {
		return new Color3D(color.g);
	}
	
//	TODO: Add Javadocs!
	public static Color3D grayscaleLightness(final Color3D color) {
		return new Color3D(color.lightness());
	}
	
//	TODO: Add Javadocs!
	public static Color3D grayscaleMax(final Color3D color) {
		return new Color3D(color.max());
	}
	
//	TODO: Add Javadocs!
	public static Color3D grayscaleMin(final Color3D color) {
		return new Color3D(color.min());
	}
	
//	TODO: Add Javadocs!
	public static Color3D grayscaleR(final Color3D color) {
		return new Color3D(color.r);
	}
	
//	TODO: Add Javadocs!
	public static Color3D grayscaleRelativeLuminance(final Color3D color) {
		return new Color3D(color.relativeLuminance());
	}
	
//	TODO: Add Javadocs!
	public static Color3D invert(final Color3D color) {
		return new Color3D(1.0D - color.r, 1.0D - color.g, 1.0D - color.b);
	}
	
//	TODO: Add Javadocs!
	public static Color3D multiplyRGB(final Color3D color, final double s) {
		final double r = color.r * s;
		final double g = color.g * s;
		final double b = color.b * s;
		
		return new Color3D(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color3D random() {
		return new Color3D(Randoms.nextDouble(), Randoms.nextDouble(), Randoms.nextDouble());
	}
	
//	TODO: Add Javadocs!
	public static Color3D randomBlue() {
		return randomBlue(0.0D, 0.0D);
	}
	
//	TODO: Add Javadocs!
	public static Color3D randomBlue(final double maxR, final double maxG) {
		final double b = Randoms.nextDouble(Math.nextUp(0.0D), Math.nextUp(1.0D));
		final double r = Randoms.nextDouble(0.0D, Math.min(Math.nextUp(Math.max(maxR, 0.0D)), b));
		final double g = Randoms.nextDouble(0.0D, Math.min(Math.nextUp(Math.max(maxG, 0.0D)), b));
		
		return new Color3D(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color3D randomCyan() {
		return randomCyan(0.0D, 0.0D);
	}
	
//	TODO: Add Javadocs!
	public static Color3D randomCyan(final double minGB, final double maxR) {
		final double x = Randoms.nextDouble(Math.max(Math.min(minGB, 1.0D), Math.nextUp(0.0D)), Math.nextUp(1.0D));
		final double y = Randoms.nextDouble(0.0D, Math.min(Math.nextUp(Math.max(maxR, 0.0D)), x));
		
		return new Color3D(y, x, x);
	}
	
//	TODO: Add Javadocs!
	public static Color3D randomGrayscale() {
		return new Color3D(Randoms.nextDouble(0.0D, Math.nextUp(1.0D)));
	}
	
//	TODO: Add Javadocs!
	public static Color3D randomGreen() {
		return randomGreen(0.0D, 0.0D);
	}
	
//	TODO: Add Javadocs!
	public static Color3D randomGreen(final double maxR, final double maxB) {
		final double g = Randoms.nextDouble(Math.nextUp(0.0D), Math.nextUp(1.0D));
		final double r = Randoms.nextDouble(0.0D, Math.min(Math.nextUp(Math.max(maxR, 0.0D)), g));
		final double b = Randoms.nextDouble(0.0D, Math.min(Math.nextUp(Math.max(maxB, 0.0D)), g));
		
		return new Color3D(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color3D randomMagenta() {
		return randomMagenta(0.0D, 0.0D);
	}
	
//	TODO: Add Javadocs!
	public static Color3D randomMagenta(final double minRB, final double maxG) {
		final double x = Randoms.nextDouble(Math.max(Math.min(minRB, 1.0D), Math.nextUp(0.0D)), Math.nextUp(1.0D));
		final double y = Randoms.nextDouble(0.0D, Math.min(Math.nextUp(Math.max(maxG, 0.0D)), x));
		
		return new Color3D(x, y, x);
	}
	
//	TODO: Add Javadocs!
	public static Color3D randomRed() {
		return randomRed(0.0D, 0.0D);
	}
	
//	TODO: Add Javadocs!
	public static Color3D randomRed(final double maxG, final double maxB) {
		final double r = Randoms.nextDouble(Math.nextUp(0.0D), Math.nextUp(1.0D));
		final double g = Randoms.nextDouble(0.0D, Math.min(Math.nextUp(Math.max(maxG, 0.0D)), r));
		final double b = Randoms.nextDouble(0.0D, Math.min(Math.nextUp(Math.max(maxB, 0.0D)), r));
		
		return new Color3D(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color3D randomYellow() {
		return randomYellow(0.0D, 0.0D);
	}
	
//	TODO: Add Javadocs!
	public static Color3D randomYellow(final double minRG, final double maxB) {
		final double x = Randoms.nextDouble(Math.max(Math.min(minRG, 1.0D), Math.nextUp(0.0D)), Math.nextUp(1.0D));
		final double y = Randoms.nextDouble(0.0D, Math.min(Math.nextUp(Math.max(maxB, 0.0D)), x));
		
		return new Color3D(x, x, y);
	}
	
//	TODO: Add Javadocs!
	public static Color3D redoGammaCorrection(final Color3D color) {
		return new Color3D(Utilities.redoGammaCorrection(color.r), Utilities.redoGammaCorrection(color.g), Utilities.redoGammaCorrection(color.b));
	}
	
//	TODO: Add Javadocs!
	public static Color3D sepia(final Color3D color) {
		final double r = color.r * 0.393D + color.g * 0.769D + color.b * 0.189D;
		final double g = color.r * 0.349D + color.g * 0.686D + color.b * 0.168D;
		final double b = color.r * 0.272D + color.g * 0.534D + color.b * 0.131D;
		
		return new Color3D(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color3D undoGammaCorrection(final Color3D color) {
		return new Color3D(Utilities.undoGammaCorrection(color.r), Utilities.undoGammaCorrection(color.g), Utilities.undoGammaCorrection(color.b));
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