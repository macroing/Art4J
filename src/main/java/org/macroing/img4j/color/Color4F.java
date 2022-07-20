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
public final class Color4F {
	/**
	 * A {@code Color4F} instance that represents the color black.
	 */
	public static final Color4F BLACK;
	
	/**
	 * A {@code Color4F} instance that represents the color blue.
	 */
	public static final Color4F BLUE;
	
	/**
	 * A {@code Color4F} instance that represents the color cyan.
	 */
	public static final Color4F CYAN;
	
	/**
	 * A {@code Color4F} instance that represents the color gray.
	 */
	public static final Color4F GRAY;
	
	/**
	 * A {@code Color4F} instance that represents the color green.
	 */
	public static final Color4F GREEN;
	
	/**
	 * A {@code Color4F} instance that represents the color magenta.
	 */
	public static final Color4F MAGENTA;
	
	/**
	 * A {@code Color4F} instance that represents the color red.
	 */
	public static final Color4F RED;
	
	/**
	 * A {@code Color4F} instance that represents the color transparent.
	 */
	public static final Color4F TRANSPARENT;
	
	/**
	 * A {@code Color4F} instance that represents the color white.
	 */
	public static final Color4F WHITE;
	
	/**
	 * A {@code Color4F} instance that represents the color yellow.
	 */
	public static final Color4F YELLOW;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final Map<Color4F, Color4F> CACHE;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static {
		CACHE = new HashMap<>();
		
		BLACK = getCached(new Color4F(0.0F, 0.0F, 0.0F));
		BLUE = getCached(new Color4F(0.0F, 0.0F, 1.0F));
		CYAN = getCached(new Color4F(0.0F, 1.0F, 1.0F));
		GRAY = getCached(new Color4F(0.5F, 0.5F, 0.5F));
		GREEN = getCached(new Color4F(0.0F, 1.0F, 0.0F));
		MAGENTA = getCached(new Color4F(1.0F, 0.0F, 1.0F));
		RED = getCached(new Color4F(1.0F, 0.0F, 0.0F));
		TRANSPARENT = getCached(new Color4F(0.0F, 0.0F, 0.0F, 0.0F));
		WHITE = getCached(new Color4F(1.0F, 1.0F, 1.0F));
		YELLOW = getCached(new Color4F(1.0F, 1.0F, 0.0F));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The value of the alpha component.
	 */
	public final float a;
	
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
	 * Constructs a new {@code Color4F} instance that represents black.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color4F(0.0F);
	 * }
	 * </pre>
	 */
	public Color4F() {
		this(0.0F);
	}
	
//	TODO: Add Javadocs!
	public Color4F(final Color3D color) {
		this((float)(color.r), (float)(color.g), (float)(color.b));
	}
	
//	TODO: Add Javadocs!
	public Color4F(final Color3F color) {
		this(color.r, color.g, color.b);
	}
	
//	TODO: Add Javadocs!
	public Color4F(final Color4D color) {
		this((float)(color.r), (float)(color.g), (float)(color.b), (float)(color.a));
	}
	
	/**
	 * Constructs a new {@code Color4F} instance that represents gray.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color4F(grayscale, 1.0F);
	 * }
	 * </pre>
	 * 
	 * @param grayscale the value of the red, green and blue components
	 */
	public Color4F(final float grayscale) {
		this(grayscale, 1.0F);
	}
	
	/**
	 * Constructs a new {@code Color4F} instance that represents gray.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color4F(grayscale, grayscale, grayscale, a);
	 * }
	 * </pre>
	 * 
	 * @param grayscale the value of the red, green and blue components
	 * @param a the value of the alpha component
	 */
	public Color4F(final float grayscale, final float a) {
		this(grayscale, grayscale, grayscale, a);
	}
	
	/**
	 * Constructs a new {@code Color4F} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color4F(r, g, b, 1.0F);
	 * }
	 * </pre>
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 */
	public Color4F(final float r, final float g, final float b) {
		this(r, g, b, 1.0F);
	}
	
	/**
	 * Constructs a new {@code Color4F} instance.
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 * @param a the value of the alpha component
	 */
	public Color4F(final float r, final float g, final float b, final float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	/**
	 * Constructs a new {@code Color4F} instance that represents gray.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color4F(grayscale, 255);
	 * }
	 * </pre>
	 * 
	 * @param grayscale the value of the red, green and blue components
	 */
	public Color4F(final int grayscale) {
		this(grayscale, 255);
	}
	
	/**
	 * Constructs a new {@code Color4F} instance that represents gray.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color4F(grayscale, grayscale, grayscale, a);
	 * }
	 * </pre>
	 * 
	 * @param grayscale the value of the red, green and blue components
	 * @param a the value of the alpha component
	 */
	public Color4F(final int grayscale, final int a) {
		this(grayscale, grayscale, grayscale, a);
	}
	
	/**
	 * Constructs a new {@code Color4F} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color4F(r, g, b, 255);
	 * }
	 * </pre>
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 */
	public Color4F(final int r, final int g, final int b) {
		this(r, g, b, 255);
	}
	
	/**
	 * Constructs a new {@code Color4F} instance.
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 * @param a the value of the alpha component
	 */
	public Color4F(final int r, final int g, final int b, final int a) {
		this(r / 255.0F, g / 255.0F, b / 255.0F, a / 255.0F);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code Color4F} instance.
	 * 
	 * @return a {@code String} representation of this {@code Color4F} instance
	 */
	@Override
	public String toString() {
		return String.format("new Color4F(%s, %s, %s, %s)", Utilities.toNonScientificNotationJava(this.r), Utilities.toNonScientificNotationJava(this.g), Utilities.toNonScientificNotationJava(this.b), Utilities.toNonScientificNotationJava(this.a));
	}
	
	/**
	 * Compares {@code color} to this {@code Color4F} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code color} is equal to this {@code Color4F} instance, {@code false} otherwise.
	 * 
	 * @param color the {@code Color4F} to compare to this {@code Color4F} instance for equality
	 * @return {@code true} if, and only if, {@code color} is equal to this {@code Color4F} instance, {@code false} otherwise
	 */
	public boolean equals(final Color4F color) {
		if(color == this) {
			return true;
		} else if(color == null) {
			return false;
		} else if(Float.compare(this.a, color.a) != 0) {
			return false;
		} else if(Float.compare(this.b, color.b) != 0) {
			return false;
		} else if(Float.compare(this.g, color.g) != 0) {
			return false;
		} else if(Float.compare(this.r, color.r) != 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Compares {@code object} to this {@code Color4F} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Color4F}, and they are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Color4F} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Color4F}, and they are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Color4F)) {
			return false;
		} else {
			return equals(Color4F.class.cast(object));
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4F} instance is black, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color4F} instance is black, {@code false} otherwise
	 */
	public boolean isBlack() {
		return Utilities.isZero(this.r) && Utilities.isZero(this.g) && Utilities.isZero(this.b);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4F} instance is blue, {@code false} otherwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.isBlue(1.0F, 1.0F);
	 * }
	 * </pre>
	 * 
	 * @return {@code true} if, and only if, this {@code Color4F} instance is blue, {@code false} otherwise
	 */
	public boolean isBlue() {
		return isBlue(1.0F, 1.0F);
	}
	
//	TODO: Add Javadocs!
	public boolean isBlue(final float deltaR, final float deltaG) {
		return this.b - deltaR >= this.r && this.b - deltaG >= this.g;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4F} instance is cyan, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color4F} instance is cyan, {@code false} otherwise
	 */
	public boolean isCyan() {
		return Utilities.equals(this.g, this.b) && this.r < this.g;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4F} instance is grayscale, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color4F} instance is grayscale, {@code false} otherwise
	 */
	public boolean isGrayscale() {
		return Utilities.equals(this.r, this.g) && Utilities.equals(this.g, this.b);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4F} instance is green, {@code false} otherwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.isGreen(1.0F, 1.0F);
	 * }
	 * </pre>
	 * 
	 * @return {@code true} if, and only if, this {@code Color4F} instance is green, {@code false} otherwise
	 */
	public boolean isGreen() {
		return isGreen(1.0F, 1.0F);
	}
	
//	TODO: Add Javadocs!
	public boolean isGreen(final float deltaR, final float deltaB) {
		return this.g - deltaR >= this.r && this.g - deltaB >= this.b;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4F} instance is magenta, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color4F} instance is magenta, {@code false} otherwise
	 */
	public boolean isMagenta() {
		return Utilities.equals(this.r, this.b) && this.g < this.b;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4F} instance is red, {@code false} otherwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.isRed(1.0F, 1.0F);
	 * }
	 * </pre>
	 * 
	 * @return {@code true} if, and only if, this {@code Color4F} instance is red, {@code false} otherwise
	 */
	public boolean isRed() {
		return isRed(1.0F, 1.0F);
	}
	
//	TODO: Add Javadocs!
	public boolean isRed(final float deltaG, final float deltaB) {
		return this.r - deltaG >= this.g && this.r - deltaB >= this.b;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4F} instance is white, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color4F} instance is white, {@code false} otherwise
	 */
	public boolean isWhite() {
		return Utilities.equals(this.r, 1.0F) && Utilities.equals(this.g, 1.0F) && Utilities.equals(this.b, 1.0F);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4F} instance is yellow, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color4F} instance is yellow, {@code false} otherwise
	 */
	public boolean isYellow() {
		return Utilities.equals(this.r, this.g) && this.b < this.r;
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
	 * Returns the lightness for this {@code Color4F} instance.
	 * 
	 * @return the lightness for this {@code Color4F} instance
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
		return Utilities.max(this.r, this.g, this.b);
	}
	
	/**
	 * Returns the smallest component value of {@link #r}, {@link #g} and {@link #b}.
	 * 
	 * @return the smallest component value of {@code  r}, {@code  g} and {@code  b}
	 */
	public float min() {
		return Utilities.min(this.r, this.g, this.b);
	}
	
	/**
	 * Returns the relative luminance for this {@code Color4F} instance.
	 * 
	 * @return the relative luminance for this {@code Color4F} instance
	 */
	public float relativeLuminance() {
		return this.r * 0.212671F + this.g * 0.715160F + this.b * 0.072169F;
	}
	
	/**
	 * Returns a hash code for this {@code Color4F} instance.
	 * 
	 * @return a hash code for this {@code Color4F} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Float.valueOf(this.a), Float.valueOf(this.b), Float.valueOf(this.g), Float.valueOf(this.r));
	}
	
	/**
	 * Returns the alpha component as an {@code int}.
	 * 
	 * @return the alpha component as an {@code int}
	 */
	public int toIntA() {
		return Utilities.convertComponentFromFloatToInt(this.a);
	}
	
//	TODO: Add Javadocs!
	public int toIntARGB() {
		final int a = ((toIntA() & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_A);
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
		return Utilities.convertComponentFromFloatToInt(this.b);
	}
	
	/**
	 * Returns the green component as an {@code int}.
	 * 
	 * @return the green component as an {@code int}
	 */
	public int toIntG() {
		return Utilities.convertComponentFromFloatToInt(this.g);
	}
	
	/**
	 * Returns the red component as an {@code int}.
	 * 
	 * @return the red component as an {@code int}
	 */
	public int toIntR() {
		return Utilities.convertComponentFromFloatToInt(this.r);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static Color4F addRGB(final Color4F colorLHS, final Color4F colorRHS) {
		final float r = colorLHS.r + colorRHS.r;
		final float g = colorLHS.g + colorRHS.g;
		final float b = colorLHS.b + colorRHS.b;
		final float a = colorLHS.a;
		
		return new Color4F(r, g, b, a);
	}
	
//	TODO: Add Javadocs!
	public static Color4F addRGB(final Color4F color, final float s) {
		final float r = color.r + s;
		final float g = color.g + s;
		final float b = color.b + s;
		final float a = color.a;
		
		return new Color4F(r, g, b, a);
	}
	
//	TODO: Add Javadocs!
	public static Color4F blend(final Color4F colorLHS, final Color4F colorRHS) {
		return blend(colorLHS, colorRHS, 0.5F);
	}
	
//	TODO: Add Javadocs!
	public static Color4F blend(final Color4F color11, final Color4F color12, final Color4F color21, final Color4F color22, final float tX, final float tY) {
		return blend(blend(color11, color12, tX), blend(color21, color22, tX), tY);
	}
	
//	TODO: Add Javadocs!
	public static Color4F blend(final Color4F colorLHS, final Color4F colorRHS, final float t) {
		return blend(colorLHS, colorRHS, t, t, t, t);
	}
	
//	TODO: Add Javadocs!
	public static Color4F blend(final Color4F colorLHS, final Color4F colorRHS, final float tR, final float tG, final float tB, final float tA) {
		final float r = Utilities.lerp(colorLHS.r, colorRHS.r, tR);
		final float g = Utilities.lerp(colorLHS.g, colorRHS.g, tG);
		final float b = Utilities.lerp(colorLHS.b, colorRHS.b, tB);
		final float a = Utilities.lerp(colorLHS.a, colorRHS.a, tA);
		
		return new Color4F(r, g, b, a);
	}
	
//	TODO: Add Javadocs!
	public static Color4F blendOver(final Color4F colorLHS, final Color4F colorRHS) {
		final float a = colorLHS.a + colorRHS.a * (1.0F - colorLHS.a);
		final float r = (colorLHS.r * colorLHS.a + colorRHS.r * colorRHS.a * (1.0F - colorLHS.a)) / a;
		final float g = (colorLHS.g * colorLHS.a + colorRHS.g * colorRHS.a * (1.0F - colorLHS.a)) / a;
		final float b = (colorLHS.b * colorLHS.a + colorRHS.b * colorRHS.a * (1.0F - colorLHS.a)) / a;
		
		return new Color4F(r, g, b, a);
	}
	
//	TODO: Add Javadocs!
	public static Color4F fromIntARGB(final int colorARGB) {
		final int a = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_A) & 0xFF;
		final int r = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_R) & 0xFF;
		final int g = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_G) & 0xFF;
		final int b = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_B) & 0xFF;
		
		return new Color4F(r, g, b, a);
	}
	
	/**
	 * Returns a cached {@code Color4F} instance that is equal to {@code color}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color4F} instance
	 * @return a cached {@code Color4F} instance that is equal to {@code color}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color4F getCached(final Color4F color) {
		return CACHE.computeIfAbsent(Objects.requireNonNull(color, "color == null"), key -> color);
	}
	
//	TODO: Add Javadocs!
	public static Color4F grayscaleAverage(final Color4F color) {
		return new Color4F(color.average(), color.a);
	}
	
//	TODO: Add Javadocs!
	public static Color4F grayscaleB(final Color4F color) {
		return new Color4F(color.b, color.a);
	}
	
//	TODO: Add Javadocs!
	public static Color4F grayscaleG(final Color4F color) {
		return new Color4F(color.g, color.a);
	}
	
//	TODO: Add Javadocs!
	public static Color4F grayscaleLightness(final Color4F color) {
		return new Color4F(color.lightness(), color.a);
	}
	
//	TODO: Add Javadocs!
	public static Color4F grayscaleMax(final Color4F color) {
		return new Color4F(color.max(), color.a);
	}
	
//	TODO: Add Javadocs!
	public static Color4F grayscaleMin(final Color4F color) {
		return new Color4F(color.min(), color.a);
	}
	
//	TODO: Add Javadocs!
	public static Color4F grayscaleR(final Color4F color) {
		return new Color4F(color.r, color.a);
	}
	
//	TODO: Add Javadocs!
	public static Color4F grayscaleRelativeLuminance(final Color4F color) {
		return new Color4F(color.relativeLuminance(), color.a);
	}
	
//	TODO: Add Javadocs!
	public static Color4F invert(final Color4F color) {
		return new Color4F(1.0F - color.r, 1.0F - color.g, 1.0F - color.b, color.a);
	}
	
//	TODO: Add Javadocs!
	public static Color4F multiplyRGB(final Color4F color, final float s) {
		final float r = color.r * s;
		final float g = color.g * s;
		final float b = color.b * s;
		final float a = color.a;
		
		return new Color4F(r, g, b, a);
	}
	
//	TODO: Add Javadocs!
	public static Color4F random() {
		return new Color4F(Utilities.nextFloat(), Utilities.nextFloat(), Utilities.nextFloat());
	}
	
//	TODO: Add Javadocs!
	public static Color4F randomBlue() {
		return randomBlue(0.0F, 0.0F);
	}
	
//	TODO: Add Javadocs!
	public static Color4F randomBlue(final float maxR, final float maxG) {
		final float b = Utilities.nextFloat(Math.nextUp(0.0F), Math.nextUp(1.0F));
		final float r = Utilities.nextFloat(0.0F, Math.min(Math.nextUp(Math.max(maxR, 0.0F)), b));
		final float g = Utilities.nextFloat(0.0F, Math.min(Math.nextUp(Math.max(maxG, 0.0F)), b));
		
		return new Color4F(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color4F randomCyan() {
		return randomCyan(0.0F, 0.0F);
	}
	
//	TODO: Add Javadocs!
	public static Color4F randomCyan(final float minGB, final float maxR) {
		final float x = Utilities.nextFloat(Math.max(Math.min(minGB, 1.0F), Math.nextUp(0.0F)), Math.nextUp(1.0F));
		final float y = Utilities.nextFloat(0.0F, Math.min(Math.nextUp(Math.max(maxR, 0.0F)), x));
		
		return new Color4F(y, x, x);
	}
	
//	TODO: Add Javadocs!
	public static Color4F randomGrayscale() {
		return new Color4F(Utilities.nextFloat(0.0F, Math.nextUp(1.0F)), 1.0F);
	}
	
//	TODO: Add Javadocs!
	public static Color4F randomGreen() {
		return randomGreen(0.0F, 0.0F);
	}
	
//	TODO: Add Javadocs!
	public static Color4F randomGreen(final float maxR, final float maxB) {
		final float g = Utilities.nextFloat(Math.nextUp(0.0F), Math.nextUp(1.0F));
		final float r = Utilities.nextFloat(0.0F, Math.min(Math.nextUp(Math.max(maxR, 0.0F)), g));
		final float b = Utilities.nextFloat(0.0F, Math.min(Math.nextUp(Math.max(maxB, 0.0F)), g));
		
		return new Color4F(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color4F randomMagenta() {
		return randomMagenta(0.0F, 0.0F);
	}
	
//	TODO: Add Javadocs!
	public static Color4F randomMagenta(final float minRB, final float maxG) {
		final float x = Utilities.nextFloat(Math.max(Math.min(minRB, 1.0F), Math.nextUp(0.0F)), Math.nextUp(1.0F));
		final float y = Utilities.nextFloat(0.0F, Math.min(Math.nextUp(Math.max(maxG, 0.0F)), x));
		
		return new Color4F(x, y, x);
	}
	
//	TODO: Add Javadocs!
	public static Color4F randomRed() {
		return randomRed(0.0F, 0.0F);
	}
	
//	TODO: Add Javadocs!
	public static Color4F randomRed(final float maxG, final float maxB) {
		final float r = Utilities.nextFloat(Math.nextUp(0.0F), Math.nextUp(1.0F));
		final float g = Utilities.nextFloat(0.0F, Math.min(Math.nextUp(Math.max(maxG, 0.0F)), r));
		final float b = Utilities.nextFloat(0.0F, Math.min(Math.nextUp(Math.max(maxB, 0.0F)), r));
		
		return new Color4F(r, g, b);
	}
	
//	TODO: Add Javadocs!
	public static Color4F randomYellow() {
		return randomYellow(0.0F, 0.0F);
	}
	
//	TODO: Add Javadocs!
	public static Color4F randomYellow(final float minRG, final float maxB) {
		final float x = Utilities.nextFloat(Math.max(Math.min(minRG, 1.0F), Math.nextUp(0.0F)), Math.nextUp(1.0F));
		final float y = Utilities.nextFloat(0.0F, Math.min(Math.nextUp(Math.max(maxB, 0.0F)), x));
		
		return new Color4F(x, x, y);
	}
	
//	TODO: Add Javadocs!
	public static Color4F redoGammaCorrection(final Color4F color) {
		return new Color4F(Utilities.redoGammaCorrection(color.r), Utilities.redoGammaCorrection(color.g), Utilities.redoGammaCorrection(color.b), color.a);
	}
	
//	TODO: Add Javadocs!
	public static Color4F sepia(final Color4F color) {
		final float r = color.r * 0.393F + color.g * 0.769F + color.b * 0.189F;
		final float g = color.r * 0.349F + color.g * 0.686F + color.b * 0.168F;
		final float b = color.r * 0.272F + color.g * 0.534F + color.b * 0.131F;
		final float a = color.a;
		
		return new Color4F(r, g, b, a);
	}
	
//	TODO: Add Javadocs!
	public static Color4F undoGammaCorrection(final Color4F color) {
		return new Color4F(Utilities.undoGammaCorrection(color.r), Utilities.undoGammaCorrection(color.g), Utilities.undoGammaCorrection(color.b), color.a);
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