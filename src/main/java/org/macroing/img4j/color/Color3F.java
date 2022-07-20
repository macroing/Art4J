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

import org.macroing.img4j.utility.Floats;
import org.macroing.img4j.utility.Randoms;
import org.macroing.img4j.utility.Strings;

//TODO: Add Javadocs!
public final class Color3F {
	/**
	 * A {@code Color3F} instance that represents the color black.
	 */
	public static final Color3F BLACK;
	
	/**
	 * A {@code Color3F} instance that represents the color blue.
	 */
	public static final Color3F BLUE;
	
	/**
	 * A {@code Color3F} instance that represents the color cyan.
	 */
	public static final Color3F CYAN;
	
	/**
	 * A {@code Color3F} instance that represents the color gray.
	 */
	public static final Color3F GRAY;
	
	/**
	 * A {@code Color3F} instance that represents the color green.
	 */
	public static final Color3F GREEN;
	
	/**
	 * A {@code Color3F} instance that represents the color magenta.
	 */
	public static final Color3F MAGENTA;
	
	/**
	 * A {@code Color3F} instance that represents the color red.
	 */
	public static final Color3F RED;
	
	/**
	 * A {@code Color3F} instance that represents the color white.
	 */
	public static final Color3F WHITE;
	
	/**
	 * A {@code Color3F} instance that represents the color yellow.
	 */
	public static final Color3F YELLOW;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final Map<Color3F, Color3F> CACHE;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static {
		CACHE = new HashMap<>();
		
		BLACK = getCached(new Color3F(0.0F, 0.0F, 0.0F));
		BLUE = getCached(new Color3F(0.0F, 0.0F, 1.0F));
		CYAN = getCached(new Color3F(0.0F, 1.0F, 1.0F));
		GRAY = getCached(new Color3F(0.5F, 0.5F, 0.5F));
		GREEN = getCached(new Color3F(0.0F, 1.0F, 0.0F));
		MAGENTA = getCached(new Color3F(1.0F, 0.0F, 1.0F));
		RED = getCached(new Color3F(1.0F, 0.0F, 0.0F));
		WHITE = getCached(new Color3F(1.0F, 1.0F, 1.0F));
		YELLOW = getCached(new Color3F(1.0F, 1.0F, 0.0F));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The value of the blue component.
	 */
	public final float b;
	
	/**
	 * The value of the green component.
	 */
	public final float g;
	
	/**
	 * The value of the red component.
	 */
	public final float r;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Color3F} instance that represents black.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color3F(0.0F);
	 * }
	 * </pre>
	 */
	public Color3F() {
		this(0.0F);
	}
	
//	TODO: Add Javadocs!
	public Color3F(final Color3D color) {
		this((float)(color.r), (float)(color.g), (float)(color.b));
	}
	
//	TODO: Add Javadocs!
	public Color3F(final Color4D color) {
		this((float)(color.r), (float)(color.g), (float)(color.b));
	}
	
//	TODO: Add Javadocs!
	public Color3F(final Color4F color) {
		this(color.r, color.g, color.b);
	}
	
	/**
	 * Constructs a new {@code Color3F} instance that represents gray.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color3F(grayscale, grayscale, grayscale);
	 * }
	 * </pre>
	 * 
	 * @param grayscale the value of the red, green and blue components
	 */
	public Color3F(final float grayscale) {
		this(grayscale, grayscale, grayscale);
	}
	
	/**
	 * Constructs a new {@code Color3F} instance.
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 */
	public Color3F(final float r, final float g, final float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	/**
	 * Constructs a new {@code Color3F} instance that represents gray.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color3F(grayscale, grayscale, grayscale);
	 * }
	 * </pre>
	 * 
	 * @param grayscale the value of the red, green and blue components
	 */
	public Color3F(final int grayscale) {
		this(grayscale, grayscale, grayscale);
	}
	
	/**
	 * Constructs a new {@code Color3F} instance.
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 */
	public Color3F(final int r, final int g, final int b) {
		this(r / 255.0F, g / 255.0F, b / 255.0F);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code Color3F} instance.
	 * 
	 * @return a {@code String} representation of this {@code Color3F} instance
	 */
	@Override
	public String toString() {
		return String.format("new Color3F(%s, %s, %s)", Strings.toNonScientificNotationJava(this.r), Strings.toNonScientificNotationJava(this.g), Strings.toNonScientificNotationJava(this.b));
	}
	
	/**
	 * Compares {@code color} to this {@code Color3F} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code color} is equal to this {@code Color3F} instance, {@code false} otherwise.
	 * 
	 * @param color the {@code Color3F} to compare to this {@code Color3F} instance for equality
	 * @return {@code true} if, and only if, {@code color} is equal to this {@code Color3F} instance, {@code false} otherwise
	 */
	public boolean equals(final Color3F color) {
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
	 * Compares {@code object} to this {@code Color3F} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Color3F}, and they are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Color3F} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Color3F}, and they are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Color3F)) {
			return false;
		} else {
			return equals(Color3F.class.cast(object));
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3F} instance is black, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color3F} instance is black, {@code false} otherwise
	 */
	public boolean isBlack() {
		return Floats.isZero(this.r) && Floats.isZero(this.g) && Floats.isZero(this.b);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3F} instance is blue, {@code false} otherwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.isBlue(1.0F, 1.0F);
	 * }
	 * </pre>
	 * 
	 * @return {@code true} if, and only if, this {@code Color3F} instance is blue, {@code false} otherwise
	 */
	public boolean isBlue() {
		return isBlue(1.0F, 1.0F);
	}
	
//	TODO: Add Javadocs!
	public boolean isBlue(final float deltaR, final float deltaG) {
		return this.b - deltaR >= this.r && this.b - deltaG >= this.g;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3F} instance is cyan, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color3F} instance is cyan, {@code false} otherwise
	 */
	public boolean isCyan() {
		return Floats.equals(this.g, this.b) && this.r < this.g;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3F} instance is grayscale, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color3F} instance is grayscale, {@code false} otherwise
	 */
	public boolean isGrayscale() {
		return Floats.equals(this.r, this.g) && Floats.equals(this.g, this.b);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3F} instance is green, {@code false} otherwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.isGreen(1.0F, 1.0F);
	 * }
	 * </pre>
	 * 
	 * @return {@code true} if, and only if, this {@code Color3F} instance is green, {@code false} otherwise
	 */
	public boolean isGreen() {
		return isGreen(1.0F, 1.0F);
	}
	
//	TODO: Add Javadocs!
	public boolean isGreen(final float deltaR, final float deltaB) {
		return this.g - deltaR >= this.r && this.g - deltaB >= this.b;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3F} instance is magenta, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color3F} instance is magenta, {@code false} otherwise
	 */
	public boolean isMagenta() {
		return Floats.equals(this.r, this.b) && this.g < this.b;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3F} instance is red, {@code false} otherwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.isRed(1.0F, 1.0F);
	 * }
	 * </pre>
	 * 
	 * @return {@code true} if, and only if, this {@code Color3F} instance is red, {@code false} otherwise
	 */
	public boolean isRed() {
		return isRed(1.0F, 1.0F);
	}
	
//	TODO: Add Javadocs!
	public boolean isRed(final float deltaG, final float deltaB) {
		return this.r - deltaG >= this.g && this.r - deltaB >= this.b;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3F} instance is white, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color3F} instance is white, {@code false} otherwise
	 */
	public boolean isWhite() {
		return Floats.equals(this.r, 1.0F) && Floats.equals(this.g, 1.0F) && Floats.equals(this.b, 1.0F);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3F} instance is yellow, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color3F} instance is yellow, {@code false} otherwise
	 */
	public boolean isYellow() {
		return Floats.equals(this.r, this.g) && this.b < this.r;
	}
	
	/**
	 * Returns the average component value of {@link #r}, {@link #g} and {@link #b}.
	 * 
	 * @return the average component value of {@code  r}, {@code  g} and {@code  b}
	 */
	public float average() {
		return (this.r + this.g + this.b) / 3.0F; 
	}
	
	/**
	 * Returns the lightness for this {@code Color3F} instance.
	 * 
	 * @return the lightness for this {@code Color3F} instance
	 */
	public float lightness() {
		return (max() + min()) / 2.0F;
	}
	
	/**
	 * Returns the largest component value of {@link #r}, {@link #g} and {@link #b}.
	 * 
	 * @return the largest component value of {@code  r}, {@code  g} and {@code  b}
	 */
	public float max() {
		return Floats.max(this.r, this.g, this.b);
	}
	
	/**
	 * Returns the smallest component value of {@link #r}, {@link #g} and {@link #b}.
	 * 
	 * @return the smallest component value of {@code  r}, {@code  g} and {@code  b}
	 */
	public float min() {
		return Floats.min(this.r, this.g, this.b);
	}
	
	/**
	 * Returns the relative luminance for this {@code Color3F} instance.
	 * 
	 * @return the relative luminance for this {@code Color3F} instance
	 */
	public float relativeLuminance() {
		return this.r * 0.212671F + this.g * 0.715160F + this.b * 0.072169F;
	}
	
	/**
	 * Returns a hash code for this {@code Color3F} instance.
	 * 
	 * @return a hash code for this {@code Color3F} instance
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
	public static Color3F addRGB(final Color3F colorLHS, final Color3F colorRHS) {
		final float r = colorLHS.r + colorRHS.r;
		final float g = colorLHS.g + colorRHS.g;
		final float b = colorLHS.b + colorRHS.b;
		
		return new Color3F(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color3F addRGB(final Color3F color, final float s) {
		final float r = color.r + s;
		final float g = color.g + s;
		final float b = color.b + s;
		
		return new Color3F(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color3F blend(final Color3F colorLHS, final Color3F colorRHS) {
		return blend(colorLHS, colorRHS, 0.5F);
	}
	
//	TODO: Add Javadocs!
	public static Color3F blend(final Color3F color11, final Color3F color12, final Color3F color21, final Color3F color22, final float tX, final float tY) {
		return blend(blend(color11, color12, tX), blend(color21, color22, tX), tY);
	}
	
//	TODO: Add Javadocs!
	public static Color3F blend(final Color3F colorLHS, final Color3F colorRHS, final float t) {
		return blend(colorLHS, colorRHS, t, t, t);
	}
	
//	TODO: Add Javadocs!
	public static Color3F blend(final Color3F colorLHS, final Color3F colorRHS, final float tR, final float tG, final float tB) {
		final float r = Floats.lerp(colorLHS.r, colorRHS.r, tR);
		final float g = Floats.lerp(colorLHS.g, colorRHS.g, tG);
		final float b = Floats.lerp(colorLHS.b, colorRHS.b, tB);
		
		return new Color3F(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color3F fromIntARGB(final int colorARGB) {
		final int r = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_R) & 0xFF;
		final int g = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_G) & 0xFF;
		final int b = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_B) & 0xFF;
		
		return new Color3F(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color3F fromIntRGB(final int colorRGB) {
		final int r = (colorRGB >> Utilities.COLOR_A_R_G_B_SHIFT_R) & 0xFF;
		final int g = (colorRGB >> Utilities.COLOR_A_R_G_B_SHIFT_G) & 0xFF;
		final int b = (colorRGB >> Utilities.COLOR_A_R_G_B_SHIFT_B) & 0xFF;
		
		return new Color3F(r, g, b);
	}
	
	/**
	 * Returns a cached {@code Color3F} instance that is equal to {@code color}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color3F} instance
	 * @return a cached {@code Color3F} instance that is equal to {@code color}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color3F getCached(final Color3F color) {
		return CACHE.computeIfAbsent(Objects.requireNonNull(color, "color == null"), key -> color);
	}
	
//	TODO: Add Javadocs!
	public static Color3F grayscaleAverage(final Color3F color) {
		return new Color3F(color.average());
	}
	
//	TODO: Add Javadocs!
	public static Color3F grayscaleB(final Color3F color) {
		return new Color3F(color.b);
	}
	
//	TODO: Add Javadocs!
	public static Color3F grayscaleG(final Color3F color) {
		return new Color3F(color.g);
	}
	
//	TODO: Add Javadocs!
	public static Color3F grayscaleLightness(final Color3F color) {
		return new Color3F(color.lightness());
	}
	
//	TODO: Add Javadocs!
	public static Color3F grayscaleMax(final Color3F color) {
		return new Color3F(color.max());
	}
	
//	TODO: Add Javadocs!
	public static Color3F grayscaleMin(final Color3F color) {
		return new Color3F(color.min());
	}
	
//	TODO: Add Javadocs!
	public static Color3F grayscaleR(final Color3F color) {
		return new Color3F(color.r);
	}
	
//	TODO: Add Javadocs!
	public static Color3F grayscaleRelativeLuminance(final Color3F color) {
		return new Color3F(color.relativeLuminance());
	}
	
//	TODO: Add Javadocs!
	public static Color3F invert(final Color3F color) {
		return new Color3F(1.0F - color.r, 1.0F - color.g, 1.0F - color.b);
	}
	
//	TODO: Add Javadocs!
	public static Color3F multiplyRGB(final Color3F color, final float s) {
		final float r = color.r * s;
		final float g = color.g * s;
		final float b = color.b * s;
		
		return new Color3F(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color3F random() {
		return new Color3F(Randoms.nextFloat(), Randoms.nextFloat(), Randoms.nextFloat());
	}
	
//	TODO: Add Javadocs!
	public static Color3F randomBlue() {
		return randomBlue(0.0F, 0.0F);
	}
	
//	TODO: Add Javadocs!
	public static Color3F randomBlue(final float maxR, final float maxG) {
		final float b = Randoms.nextFloat(Math.nextUp(0.0F), Math.nextUp(1.0F));
		final float r = Randoms.nextFloat(0.0F, Math.min(Math.nextUp(Math.max(maxR, 0.0F)), b));
		final float g = Randoms.nextFloat(0.0F, Math.min(Math.nextUp(Math.max(maxG, 0.0F)), b));
		
		return new Color3F(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color3F randomCyan() {
		return randomCyan(0.0F, 0.0F);
	}
	
//	TODO: Add Javadocs!
	public static Color3F randomCyan(final float minGB, final float maxR) {
		final float x = Randoms.nextFloat(Math.max(Math.min(minGB, 1.0F), Math.nextUp(0.0F)), Math.nextUp(1.0F));
		final float y = Randoms.nextFloat(0.0F, Math.min(Math.nextUp(Math.max(maxR, 0.0F)), x));
		
		return new Color3F(y, x, x);
	}
	
//	TODO: Add Javadocs!
	public static Color3F randomGrayscale() {
		return new Color3F(Randoms.nextFloat(0.0F, Math.nextUp(1.0F)));
	}
	
//	TODO: Add Javadocs!
	public static Color3F randomGreen() {
		return randomGreen(0.0F, 0.0F);
	}
	
//	TODO: Add Javadocs!
	public static Color3F randomGreen(final float maxR, final float maxB) {
		final float g = Randoms.nextFloat(Math.nextUp(0.0F), Math.nextUp(1.0F));
		final float r = Randoms.nextFloat(0.0F, Math.min(Math.nextUp(Math.max(maxR, 0.0F)), g));
		final float b = Randoms.nextFloat(0.0F, Math.min(Math.nextUp(Math.max(maxB, 0.0F)), g));
		
		return new Color3F(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color3F randomMagenta() {
		return randomMagenta(0.0F, 0.0F);
	}
	
//	TODO: Add Javadocs!
	public static Color3F randomMagenta(final float minRB, final float maxG) {
		final float x = Randoms.nextFloat(Math.max(Math.min(minRB, 1.0F), Math.nextUp(0.0F)), Math.nextUp(1.0F));
		final float y = Randoms.nextFloat(0.0F, Math.min(Math.nextUp(Math.max(maxG, 0.0F)), x));
		
		return new Color3F(x, y, x);
	}
	
//	TODO: Add Javadocs!
	public static Color3F randomRed() {
		return randomRed(0.0F, 0.0F);
	}
	
//	TODO: Add Javadocs!
	public static Color3F randomRed(final float maxG, final float maxB) {
		final float r = Randoms.nextFloat(Math.nextUp(0.0F), Math.nextUp(1.0F));
		final float g = Randoms.nextFloat(0.0F, Math.min(Math.nextUp(Math.max(maxG, 0.0F)), r));
		final float b = Randoms.nextFloat(0.0F, Math.min(Math.nextUp(Math.max(maxB, 0.0F)), r));
		
		return new Color3F(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color3F randomYellow() {
		return randomYellow(0.0F, 0.0F);
	}
	
//	TODO: Add Javadocs!
	public static Color3F randomYellow(final float minRG, final float maxB) {
		final float x = Randoms.nextFloat(Math.max(Math.min(minRG, 1.0F), Math.nextUp(0.0F)), Math.nextUp(1.0F));
		final float y = Randoms.nextFloat(0.0F, Math.min(Math.nextUp(Math.max(maxB, 0.0F)), x));
		
		return new Color3F(x, x, y);
	}
	
//	TODO: Add Javadocs!
	public static Color3F redoGammaCorrection(final Color3F color) {
		return new Color3F(Utilities.redoGammaCorrection(color.r), Utilities.redoGammaCorrection(color.g), Utilities.redoGammaCorrection(color.b));
	}
	
//	TODO: Add Javadocs!
	public static Color3F sepia(final Color3F color) {
		final float r = color.r * 0.393F + color.g * 0.769F + color.b * 0.189F;
		final float g = color.r * 0.349F + color.g * 0.686F + color.b * 0.168F;
		final float b = color.r * 0.272F + color.g * 0.534F + color.b * 0.131F;
		
		return new Color3F(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color3F undoGammaCorrection(final Color3F color) {
		return new Color3F(Utilities.undoGammaCorrection(color.r), Utilities.undoGammaCorrection(color.g), Utilities.undoGammaCorrection(color.b));
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