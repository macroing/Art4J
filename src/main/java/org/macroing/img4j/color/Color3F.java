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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.macroing.java.lang.Floats;
import org.macroing.java.lang.Ints;
import org.macroing.java.lang.Strings;
import org.macroing.java.util.Randoms;

/**
 * A {@code Color3F} represents a color with three {@code float}-based components.
 * <p>
 * This class is immutable and therefore suitable for concurrent use without external synchronization.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
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
	
	/**
	 * Constructs a new {@code Color3F} instance from {@code color}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@link Color3D} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Color3F(final Color3D color) {
		this((float)(color.r), (float)(color.g), (float)(color.b));
	}
	
	/**
	 * Constructs a new {@code Color3F} instance from {@code color}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@link Color4D} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Color3F(final Color4D color) {
		this((float)(color.r), (float)(color.g), (float)(color.b));
	}
	
	/**
	 * Constructs a new {@code Color3F} instance from {@code color}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@link Color4F} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
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
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color3F(Ints.saturate(r) / 255.0F, Ints.saturate(g) / 255.0F, Ints.saturate(b) / 255.0F);
	 * }
	 * </pre>
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 */
	public Color3F(final int r, final int g, final int b) {
		this(Ints.saturate(r) / 255.0F, Ints.saturate(g) / 255.0F, Ints.saturate(b) / 255.0F);
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
		} else if(!Floats.equals(this.b, color.b)) {
			return false;
		} else if(!Floats.equals(this.g, color.g)) {
			return false;
		} else if(!Floats.equals(this.r, color.r)) {
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
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3F} instance is blue, {@code false} otherwise.
	 * <p>
	 * The {@code Color3F} instance {@code color} is blue if, and only if, {@code color.b - deltaR >= color.r} and {@code color.b - deltaG >= color.g}.
	 * 
	 * @param deltaR the delta for the R-component
	 * @param deltaG the delta for the G-component
	 * @return {@code true} if, and only if, this {@code Color3F} instance is blue, {@code false} otherwise
	 */
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
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3F} instance is green, {@code false} otherwise.
	 * <p>
	 * The {@code Color3F} instance {@code color} is green if, and only if, {@code color.g - deltaR >= color.r} and {@code color.g - deltaB >= color.b}.
	 * 
	 * @param deltaR the delta for the R-component
	 * @param deltaB the delta for the B-component
	 * @return {@code true} if, and only if, this {@code Color3F} instance is green, {@code false} otherwise
	 */
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
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color3F} instance is red, {@code false} otherwise.
	 * <p>
	 * The {@code Color3F} instance {@code color} is red if, and only if, {@code color.r - deltaG >= color.g} and {@code color.r - deltaB >= color.b}.
	 * 
	 * @param deltaG the delta for the G-component
	 * @param deltaB the delta for the B-component
	 * @return {@code true} if, and only if, this {@code Color3F} instance is red, {@code false} otherwise
	 */
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
		return average(this.r, this.g, this.b);
	}
	
	/**
	 * Returns the lightness for this {@code Color3F} instance.
	 * 
	 * @return the lightness for this {@code Color3F} instance
	 */
	public float lightness() {
		return lightness(this.r, this.g, this.b);
	}
	
	/**
	 * Returns the largest component value of {@link #r}, {@link #g} and {@link #b}.
	 * 
	 * @return the largest component value of {@code  r}, {@code  g} and {@code  b}
	 */
	public float max() {
		return max(this.r, this.g, this.b);
	}
	
	/**
	 * Returns the smallest component value of {@link #r}, {@link #g} and {@link #b}.
	 * 
	 * @return the smallest component value of {@code  r}, {@code  g} and {@code  b}
	 */
	public float min() {
		return min(this.r, this.g, this.b);
	}
	
	/**
	 * Returns the relative luminance for this {@code Color3F} instance.
	 * 
	 * @return the relative luminance for this {@code Color3F} instance
	 */
	public float relativeLuminance() {
		return relativeLuminance(this.r, this.g, this.b);
	}
	
	/**
	 * Returns a hash code for this {@code Color3F} instance.
	 * 
	 * @return a hash code for this {@code Color3F} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Float.valueOf(this.b), Float.valueOf(this.g), Float.valueOf(this.r));
	}
	
	/**
	 * Returns the alpha, red, green and blue components as an {@code int} in the format ARGB.
	 * <p>
	 * The alpha component is treated as if it was {@code 1.0F} or {@code 255}.
	 * 
	 * @return the alpha, red, green and blue components as an {@code int} in the format ARGB
	 */
	public int toIntARGB() {
		return toIntARGB(this.r, this.g, this.b);
	}
	
	/**
	 * Returns the blue component as an {@code int}.
	 * 
	 * @return the blue component as an {@code int}
	 */
	public int toIntB() {
		return toIntB(this.b);
	}
	
	/**
	 * Returns the green component as an {@code int}.
	 * 
	 * @return the green component as an {@code int}
	 */
	public int toIntG() {
		return toIntG(this.g);
	}
	
	/**
	 * Returns the red component as an {@code int}.
	 * 
	 * @return the red component as an {@code int}
	 */
	public int toIntR() {
		return toIntR(this.r);
	}
	
	/**
	 * Returns the red, green and blue components as an {@code int} in the format RGB.
	 * 
	 * @return the red, green and blue components as an {@code int} in the format RGB
	 */
	public int toIntRGB() {
		return toIntRGB(this.r, this.g, this.b);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Adds the component values of {@code colorRHS} to the component values of {@code colorLHS}.
	 * <p>
	 * Returns a new {@code Color3F} instance with the result of the addition.
	 * <p>
	 * If either {@code colorLHS} or {@code colorRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param colorLHS the {@code Color3F} instance on the left-hand side
	 * @param colorRHS the {@code Color3F} instance on the right-hand side
	 * @return a new {@code Color3F} instance with the result of the addition
	 * @throws NullPointerException thrown if, and only if, either {@code colorLHS} or {@code colorRHS} are {@code null}
	 */
	public static Color3F add(final Color3F colorLHS, final Color3F colorRHS) {
		final float r = colorLHS.r + colorRHS.r;
		final float g = colorLHS.g + colorRHS.g;
		final float b = colorLHS.b + colorRHS.b;
		
		return new Color3F(r, g, b);
	}
	
	/**
	 * Adds {@code scalarRHS} to the component values of {@code colorLHS}.
	 * <p>
	 * Returns a new {@code Color3F} instance with the result of the addition.
	 * <p>
	 * If {@code colorLHS} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param colorLHS the {@code Color3F} instance on the left-hand side
	 * @param scalarRHS the scalar value on the right-hand side
	 * @return a new {@code Color3F} instance with the result of the addition
	 * @throws NullPointerException thrown if, and only if, {@code colorLHS} is {@code null}
	 */
	public static Color3F add(final Color3F colorLHS, final float scalarRHS) {
		final float r = colorLHS.r + scalarRHS;
		final float g = colorLHS.g + scalarRHS;
		final float b = colorLHS.b + scalarRHS;
		
		return new Color3F(r, g, b);
	}
	
	/**
	 * Blends the component values of {@code colorLHS} and {@code colorRHS}.
	 * <p>
	 * Returns a new {@code Color3F} instance with the result of the blend.
	 * <p>
	 * If either {@code colorLHS} or {@code colorRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color3F.blend(colorLHS, colorRHS, 0.5F);
	 * }
	 * </pre>
	 * 
	 * @param colorLHS the {@code Color3F} instance on the left-hand side
	 * @param colorRHS the {@code Color3F} instance on the right-hand side
	 * @return a new {@code Color3F} instance with the result of the blend
	 * @throws NullPointerException thrown if, and only if, either {@code colorLHS} or {@code colorRHS} are {@code null}
	 */
	public static Color3F blend(final Color3F colorLHS, final Color3F colorRHS) {
		return blend(colorLHS, colorRHS, 0.5F);
	}
	
	/**
	 * Blends the component values of {@code color11}, {@code color12}, {@code color21} and {@code color22}.
	 * <p>
	 * Returns a new {@code Color3F} instance with the result of the blend.
	 * <p>
	 * If either {@code color11}, {@code color12}, {@code color21} or {@code color22} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color3F.blend(Color3F.blend(color11, color12, tX), Color3F.blend(color21, color22, tX), tY);
	 * }
	 * </pre>
	 * 
	 * @param color11 the {@code Color3F} instance on row 1 and column 1
	 * @param color12 the {@code Color3F} instance on row 1 and column 2
	 * @param color21 the {@code Color3F} instance on row 2 and column 1
	 * @param color22 the {@code Color3F} instance on row 2 and column 2
	 * @param tX the factor to use for all components in the first and second blend operation
	 * @param tY the factor to use for all components in the third blend operation
	 * @return a new {@code Color3F} instance with the result of the blend
	 * @throws NullPointerException thrown if, and only if, either {@code color11}, {@code color12}, {@code color21} or {@code color22} are {@code null}
	 */
	public static Color3F blend(final Color3F color11, final Color3F color12, final Color3F color21, final Color3F color22, final float tX, final float tY) {
		return blend(blend(color11, color12, tX), blend(color21, color22, tX), tY);
	}
	
	/**
	 * Blends the component values of {@code colorLHS} and {@code colorRHS}.
	 * <p>
	 * Returns a new {@code Color3F} instance with the result of the blend.
	 * <p>
	 * If either {@code colorLHS} or {@code colorRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color3F.blend(colorLHS, colorRHS, t, t, t);
	 * }
	 * </pre>
	 * 
	 * @param colorLHS the {@code Color3F} instance on the left-hand side
	 * @param colorRHS the {@code Color3F} instance on the right-hand side
	 * @param t the factor to use for all components in the blending process
	 * @return a new {@code Color3F} instance with the result of the blend
	 * @throws NullPointerException thrown if, and only if, either {@code colorLHS} or {@code colorRHS} are {@code null}
	 */
	public static Color3F blend(final Color3F colorLHS, final Color3F colorRHS, final float t) {
		return blend(colorLHS, colorRHS, t, t, t);
	}
	
	/**
	 * Blends the component values of {@code colorLHS} and {@code colorRHS}.
	 * <p>
	 * Returns a new {@code Color3F} instance with the result of the blend.
	 * <p>
	 * If either {@code colorLHS} or {@code colorRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param colorLHS the {@code Color3F} instance on the left-hand side
	 * @param colorRHS the {@code Color3F} instance on the right-hand side
	 * @param tR the factor to use for the R-component in the blending process
	 * @param tG the factor to use for the G-component in the blending process
	 * @param tB the factor to use for the B-component in the blending process
	 * @return a new {@code Color3F} instance with the result of the blend
	 * @throws NullPointerException thrown if, and only if, either {@code colorLHS} or {@code colorRHS} are {@code null}
	 */
	public static Color3F blend(final Color3F colorLHS, final Color3F colorRHS, final float tR, final float tG, final float tB) {
		final float r = Floats.lerp(colorLHS.r, colorRHS.r, tR);
		final float g = Floats.lerp(colorLHS.g, colorRHS.g, tG);
		final float b = Floats.lerp(colorLHS.b, colorRHS.b, tB);
		
		return new Color3F(r, g, b);
	}
	
	/**
	 * Returns a {@code Color3F} instance from the {@code int} {@code colorARGB}.
	 * 
	 * @param colorARGB an {@code int} that contains the alpha, red, green and blue components
	 * @return a {@code Color3F} instance from the {@code int} {@code colorARGB}
	 */
	public static Color3F fromIntARGB(final int colorARGB) {
		final int r = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_R) & 0xFF;
		final int g = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_G) & 0xFF;
		final int b = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_B) & 0xFF;
		
		return new Color3F(r, g, b);
	}
	
	/**
	 * Returns a {@code Color3F} instance from the {@code int} {@code colorRGB}.
	 * 
	 * @param colorRGB an {@code int} that contains the red, green and blue components
	 * @return a {@code Color3F} instance from the {@code int} {@code colorRGB}
	 */
	public static Color3F fromIntRGB(final int colorRGB) {
		final int r = (colorRGB >> Utilities.COLOR_R_G_B_SHIFT_R) & 0xFF;
		final int g = (colorRGB >> Utilities.COLOR_R_G_B_SHIFT_G) & 0xFF;
		final int b = (colorRGB >> Utilities.COLOR_R_G_B_SHIFT_B) & 0xFF;
		
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
	
	/**
	 * Returns a grayscale {@code Color3F} instance based on {@code color.average()}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color3F} instance
	 * @return a grayscale {@code Color3F} instance based on {@code color.average()}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color3F grayscaleAverage(final Color3F color) {
		return new Color3F(color.average());
	}
	
	/**
	 * Returns a grayscale {@code Color3F} instance based on {@code color.b}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color3F} instance
	 * @return a grayscale {@code Color3F} instance based on {@code color.b}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color3F grayscaleB(final Color3F color) {
		return new Color3F(color.b);
	}
	
	/**
	 * Returns a grayscale {@code Color3F} instance based on {@code color.g}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color3F} instance
	 * @return a grayscale {@code Color3F} instance based on {@code color.g}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color3F grayscaleG(final Color3F color) {
		return new Color3F(color.g);
	}
	
	/**
	 * Returns a grayscale {@code Color3F} instance based on {@code color.lightness()}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color3F} instance
	 * @return a grayscale {@code Color3F} instance based on {@code color.lightness()}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color3F grayscaleLightness(final Color3F color) {
		return new Color3F(color.lightness());
	}
	
	/**
	 * Returns a grayscale {@code Color3F} instance based on {@code color.max()}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color3F} instance
	 * @return a grayscale {@code Color3F} instance based on {@code color.max()}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color3F grayscaleMax(final Color3F color) {
		return new Color3F(color.max());
	}
	
	/**
	 * Returns a grayscale {@code Color3F} instance based on {@code color.min()}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color3F} instance
	 * @return a grayscale {@code Color3F} instance based on {@code color.min()}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color3F grayscaleMin(final Color3F color) {
		return new Color3F(color.min());
	}
	
	/**
	 * Returns a grayscale {@code Color3F} instance based on {@code color.r}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color3F} instance
	 * @return a grayscale {@code Color3F} instance based on {@code color.r}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color3F grayscaleR(final Color3F color) {
		return new Color3F(color.r);
	}
	
	/**
	 * Returns a grayscale {@code Color3F} instance based on {@code color.relativeLuminance()}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color3F} instance
	 * @return a grayscale {@code Color3F} instance based on {@code color.luminance()}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color3F grayscaleRelativeLuminance(final Color3F color) {
		return new Color3F(color.relativeLuminance());
	}
	
	/**
	 * Inverts the component values of {@code color}.
	 * <p>
	 * Returns a new {@code Color3F} instance with the result of the inversion.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color3F} instance
	 * @return a new {@code Color3F} instance with the result of the inversion
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color3F invert(final Color3F color) {
		return new Color3F(1.0F - color.r, 1.0F - color.g, 1.0F - color.b);
	}
	
	/**
	 * Multiplies the component values of {@code colorLHS} with {@code scalarRHS}.
	 * <p>
	 * Returns a new {@code Color3F} instance with the result of the multiplication.
	 * <p>
	 * If {@code colorLHS} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param colorLHS the {@code Color3F} instance on the left-hand side
	 * @param scalarRHS the scalar value on the right-hand side
	 * @return a new {@code Color3F} instance with the result of the multiplication
	 * @throws NullPointerException thrown if, and only if, {@code colorLHS} is {@code null}
	 */
	public static Color3F multiply(final Color3F colorLHS, final float scalarRHS) {
		final float r = colorLHS.r * scalarRHS;
		final float g = colorLHS.g * scalarRHS;
		final float b = colorLHS.b * scalarRHS;
		
		return new Color3F(r, g, b);
	}
	
	/**
	 * Returns a {@code Color3F} instance with random component values.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color3F(Randoms.nextFloat(Floats.nextUp(1.0F)), Randoms.nextFloat(Floats.nextUp(1.0F)), Randoms.nextFloat(Floats.nextUp(1.0F)));
	 * }
	 * </pre>
	 * 
	 * @return a {@code Color3F} instance with random component values
	 */
	public static Color3F random() {
		return new Color3F(Randoms.nextFloat(Floats.nextUp(1.0F)), Randoms.nextFloat(Floats.nextUp(1.0F)), Randoms.nextFloat(Floats.nextUp(1.0F)));
	}
	
	/**
	 * Returns a {@code Color3F} instance with random component values that represents a blue color.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color3F.randomBlue(0.0F, 0.0F);
	 * }
	 * </pre>
	 * 
	 * @return a {@code Color3F} instance with random component values that represents a blue color
	 */
	public static Color3F randomBlue() {
		return randomBlue(0.0F, 0.0F);
	}
	
	/**
	 * Returns a {@code Color3F} instance with random component values that represents a blue color.
	 * 
	 * @param maxR the maximum value to use for the R-component
	 * @param maxG the maximum value to use for the G-component
	 * @return a {@code Color3F} instance with random component values that represents a blue color
	 */
	public static Color3F randomBlue(final float maxR, final float maxG) {
		final float b = Randoms.nextFloat(Floats.nextUp(0.0F), Floats.nextUp(1.0F));
		final float r = Randoms.nextFloat(0.0F, Floats.min(Floats.nextUp(Floats.max(maxR, 0.0F)), b));
		final float g = Randoms.nextFloat(0.0F, Floats.min(Floats.nextUp(Floats.max(maxG, 0.0F)), b));
		
		return new Color3F(r, g, b);
	}
	
	/**
	 * Returns a {@code Color3F} instance with random component values that represents a cyan color.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color3F.randomCyan(0.0F, 0.0F);
	 * }
	 * </pre>
	 * 
	 * @return a {@code Color3F} instance with random component values that represents a cyan color
	 */
	public static Color3F randomCyan() {
		return randomCyan(0.0F, 0.0F);
	}
	
	/**
	 * Returns a {@code Color3F} instance with random component values that represents a cyan color.
	 * 
	 * @param minGB the minimum value to use for the G- and B-components
	 * @param maxR the maximum value to use for the R-component
	 * @return a {@code Color3F} instance with random component values that represents a cyan color
	 */
	public static Color3F randomCyan(final float minGB, final float maxR) {
		final float x = Randoms.nextFloat(Floats.max(Floats.min(minGB, 1.0F), Floats.nextUp(0.0F)), Floats.nextUp(1.0F));
		final float y = Randoms.nextFloat(0.0F, Floats.min(Floats.nextUp(Floats.max(maxR, 0.0F)), x));
		
		return new Color3F(y, x, x);
	}
	
	/**
	 * Returns a {@code Color3F} instance with random component values that represents a grayscale color.
	 * 
	 * @return a {@code Color3F} instance with random component values that represents a grayscale color
	 */
	public static Color3F randomGrayscale() {
		return new Color3F(Randoms.nextFloat(Floats.nextUp(1.0F)));
	}
	
	/**
	 * Returns a {@code Color3F} instance with random component values that represents a green color.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color3F.randomGreen(0.0F, 0.0F);
	 * }
	 * </pre>
	 * 
	 * @return a {@code Color3F} instance with random component values that represents a green color
	 */
	public static Color3F randomGreen() {
		return randomGreen(0.0F, 0.0F);
	}
	
	/**
	 * Returns a {@code Color3F} instance with random component values that represents a green color.
	 * 
	 * @param maxR the maximum value to use for the R-component
	 * @param maxB the maximum value to use for the B-component
	 * @return a {@code Color3F} instance with random component values that represents a green color
	 */
	public static Color3F randomGreen(final float maxR, final float maxB) {
		final float g = Randoms.nextFloat(Floats.nextUp(0.0F), Floats.nextUp(1.0F));
		final float r = Randoms.nextFloat(0.0F, Floats.min(Floats.nextUp(Floats.max(maxR, 0.0F)), g));
		final float b = Randoms.nextFloat(0.0F, Floats.min(Floats.nextUp(Floats.max(maxB, 0.0F)), g));
		
		return new Color3F(r, g, b);
	}
	
	/**
	 * Returns a {@code Color3F} instance with random component values that represents a magenta color.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color3F.randomMagenta(0.0F, 0.0F);
	 * }
	 * </pre>
	 * 
	 * @return a {@code Color3F} instance with random component values that represents a magenta color
	 */
	public static Color3F randomMagenta() {
		return randomMagenta(0.0F, 0.0F);
	}
	
	/**
	 * Returns a {@code Color3F} instance with random component values that represents a magenta color.
	 * 
	 * @param minRB the minimum value to use for the R- and B-components
	 * @param maxG the maximum value to use for the G-component
	 * @return a {@code Color3F} instance with random component values that represents a magenta color
	 */
	public static Color3F randomMagenta(final float minRB, final float maxG) {
		final float x = Randoms.nextFloat(Floats.max(Floats.min(minRB, 1.0F), Floats.nextUp(0.0F)), Floats.nextUp(1.0F));
		final float y = Randoms.nextFloat(0.0F, Floats.min(Floats.nextUp(Floats.max(maxG, 0.0F)), x));
		
		return new Color3F(x, y, x);
	}
	
	/**
	 * Returns a {@code Color3F} instance with random component values that represents a red color.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color3F.randomRed(0.0F, 0.0F);
	 * }
	 * </pre>
	 * 
	 * @return a {@code Color3F} instance with random component values that represents a red color
	 */
	public static Color3F randomRed() {
		return randomRed(0.0F, 0.0F);
	}
	
	/**
	 * Returns a {@code Color3F} instance with random component values that represents a red color.
	 * 
	 * @param maxG the maximum value to use for the G-component
	 * @param maxB the maximum value to use for the B-component
	 * @return a {@code Color3F} instance with random component values that represents a red color
	 */
	public static Color3F randomRed(final float maxG, final float maxB) {
		final float r = Randoms.nextFloat(Floats.nextUp(0.0F), Floats.nextUp(1.0F));
		final float g = Randoms.nextFloat(0.0F, Floats.min(Floats.nextUp(Floats.max(maxG, 0.0F)), r));
		final float b = Randoms.nextFloat(0.0F, Floats.min(Floats.nextUp(Floats.max(maxB, 0.0F)), r));
		
		return new Color3F(r, g, b);
	}
	
	/**
	 * Returns a {@code Color3F} instance with random component values that represents a yellow color.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color3F.randomYellow(0.0F, 0.0F);
	 * }
	 * </pre>
	 * 
	 * @return a {@code Color3F} instance with random component values that represents a yellow color
	 */
	public static Color3F randomYellow() {
		return randomYellow(0.0F, 0.0F);
	}
	
	/**
	 * Returns a {@code Color3F} instance with random component values that represents a yellow color.
	 * 
	 * @param minRG the minimum value to use for the R- and G-components
	 * @param maxB the maximum value to use for the B-component
	 * @return a {@code Color3F} instance with random component values that represents a yellow color
	 */
	public static Color3F randomYellow(final float minRG, final float maxB) {
		final float x = Randoms.nextFloat(Floats.max(Floats.min(minRG, 1.0F), Floats.nextUp(0.0F)), Floats.nextUp(1.0F));
		final float y = Randoms.nextFloat(0.0F, Floats.min(Floats.nextUp(Floats.max(maxB, 0.0F)), x));
		
		return new Color3F(x, x, y);
	}
	
	/**
	 * Converts {@code color} to its sepia-representation.
	 * <p>
	 * Returns a new {@code Color3F} instance with the result of the operation.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color3F} instance
	 * @return a new {@code Color3F} instance with the result of the operation
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color3F sepia(final Color3F color) {
		final float r = color.r * 0.393F + color.g * 0.769F + color.b * 0.189F;
		final float g = color.r * 0.349F + color.g * 0.686F + color.b * 0.168F;
		final float b = color.r * 0.272F + color.g * 0.534F + color.b * 0.131F;
		
		return new Color3F(r, g, b);
	}
	
	/**
	 * Returns the average component value of {@code r}, {@code g} and {@code b}.
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 * @return the average component value of {@code r}, {@code g} and {@code b}
	 */
	public static float average(final float r, final float g, final float b) {
		return (r + g + b) / 3.0F;
	}
	
	/**
	 * Returns the value of the B-component in {@code colorARGB} as a {@code float}.
	 * 
	 * @param colorARGB an {@code int} that contains a color with components in the format ARGB
	 * @return the value of the B-component in {@code colorARGB} as a {@code float}
	 */
	public static float fromIntARGBToFloatB(final int colorARGB) {
		return ((colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_B) & 0xFF) / 255.0F;
	}
	
	/**
	 * Returns the value of the G-component in {@code colorARGB} as a {@code float}.
	 * 
	 * @param colorARGB an {@code int} that contains a color with components in the format ARGB
	 * @return the value of the G-component in {@code colorARGB} as a {@code float}
	 */
	public static float fromIntARGBToFloatG(final int colorARGB) {
		return ((colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_G) & 0xFF) / 255.0F;
	}
	
	/**
	 * Returns the value of the R-component in {@code colorARGB} as a {@code float}.
	 * 
	 * @param colorARGB an {@code int} that contains a color with components in the format ARGB
	 * @return the value of the R-component in {@code colorARGB} as a {@code float}
	 */
	public static float fromIntARGBToFloatR(final int colorARGB) {
		return ((colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_R) & 0xFF) / 255.0F;
	}
	
	/**
	 * Returns the value of the B-component in {@code colorRGB} as a {@code float}.
	 * 
	 * @param colorRGB an {@code int} that contains a color with components in the format RGB
	 * @return the value of the B-component in {@code colorRGB} as a {@code float}
	 */
	public static float fromIntRGBToFloatB(final int colorRGB) {
		return ((colorRGB >> Utilities.COLOR_R_G_B_SHIFT_B) & 0xFF) / 255.0F;
	}
	
	/**
	 * Returns the value of the G-component in {@code colorRGB} as a {@code float}.
	 * 
	 * @param colorRGB an {@code int} that contains a color with components in the format RGB
	 * @return the value of the G-component in {@code colorRGB} as a {@code float}
	 */
	public static float fromIntRGBToFloatG(final int colorRGB) {
		return ((colorRGB >> Utilities.COLOR_R_G_B_SHIFT_G) & 0xFF) / 255.0F;
	}
	
	/**
	 * Returns the value of the R-component in {@code colorRGB} as a {@code float}.
	 * 
	 * @param colorRGB an {@code int} that contains a color with components in the format RGB
	 * @return the value of the R-component in {@code colorRGB} as a {@code float}
	 */
	public static float fromIntRGBToFloatR(final int colorRGB) {
		return ((colorRGB >> Utilities.COLOR_R_G_B_SHIFT_R) & 0xFF) / 255.0F;
	}
	
	/**
	 * Returns the lightness for the color represented by {@code r}, {@code g} and {@code b}.
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 * @return the lightness for the color represented by {@code r}, {@code g} and {@code b}
	 */
	public static float lightness(final float r, final float g, final float b) {
		return (max(r, g, b) + min(r, g, b)) / 2.0F;
	}
	
	/**
	 * Returns the largest component value of {@code r}, {@code g} and {@code b}.
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 * @return the largest component value of {@code r}, {@code g} and {@code b}
	 */
	public static float max(final float r, final float g, final float b) {
		return Floats.max(r, g, b);
	}
	
	/**
	 * Returns the smallest component value of {@code r}, {@code g} and {@code b}.
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 * @return the smallest component value of {@code r}, {@code g} and {@code b}
	 */
	public static float min(final float r, final float g, final float b) {
		return Floats.min(r, g, b);
	}
	
	/**
	 * Returns the relative luminance for the color represented by {@code r}, {@code g} and {@code b}.
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 * @return the relative luminance for the color represented by {@code r}, {@code g} and {@code b}
	 */
	public static float relativeLuminance(final float r, final float g, final float b) {
		return r * 0.212671F + g * 0.715160F + b * 0.072169F;
	}
	
	/**
	 * Returns the size of the cache.
	 * 
	 * @return the size of the cache
	 */
	public static int getCacheSize() {
		return CACHE.size();
	}
	
	/**
	 * Returns the alpha, red, green and blue components as an {@code int} in the format ARGB.
	 * <p>
	 * The alpha component is treated as if it was {@code 1.0F} or {@code 255}.
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 * @return the alpha, red, green and blue components as an {@code int} in the format ARGB
	 */
	public static int toIntARGB(final float r, final float g, final float b) {
		final int colorA = ((255       & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_A);
		final int colorR = ((toIntR(r) & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_R);
		final int colorG = ((toIntG(g) & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_G);
		final int colorB = ((toIntB(b) & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_B);
		
		return colorA | colorR | colorG | colorB;
	}
	
	/**
	 * Returns the blue component as an {@code int}.
	 * 
	 * @param b the value of the blue component
	 * @return the blue component as an {@code int}
	 */
	public static int toIntB(final float b) {
		return Ints.saturateAndScaleToInt(b);
	}
	
	/**
	 * Returns the green component as an {@code int}.
	 * 
	 * @param g the value of the green component
	 * @return the green component as an {@code int}
	 */
	public static int toIntG(final float g) {
		return Ints.saturateAndScaleToInt(g);
	}
	
	/**
	 * Returns the red component as an {@code int}.
	 * 
	 * @param r the value of the red component
	 * @return the red component as an {@code int}
	 */
	public static int toIntR(final float r) {
		return Ints.saturateAndScaleToInt(r);
	}
	
	/**
	 * Returns the red, green and blue components as an {@code int} in the format RGB.
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 * @return the red, green and blue components as an {@code int} in the format RGB
	 */
	public static int toIntRGB(final float r, final float g, final float b) {
		final int colorR = ((toIntR(r) & 0xFF) << Utilities.COLOR_R_G_B_SHIFT_R);
		final int colorG = ((toIntG(g) & 0xFF) << Utilities.COLOR_R_G_B_SHIFT_G);
		final int colorB = ((toIntB(b) & 0xFF) << Utilities.COLOR_R_G_B_SHIFT_B);
		
		return colorR | colorG | colorB;
	}
	
	/**
	 * Clears the cache.
	 */
	public static void clearCache() {
		CACHE.clear();
	}
}