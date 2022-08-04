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

import org.macroing.img4j.utility.Doubles;
import org.macroing.img4j.utility.Ints;
import org.macroing.java.lang.Strings;
import org.macroing.java.util.Randoms;

/**
 * A {@code Color4D} represents a color with four {@code double}-based components.
 * <p>
 * This class is immutable and therefore suitable for concurrent use without external synchronization.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
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
	
	/**
	 * A {@code Color4D} instance that represents the color gray.
	 */
	public static final Color4D GRAY;
	
	/**
	 * A {@code Color4D} instance that represents the color green.
	 */
	public static final Color4D GREEN;
	
	/**
	 * A {@code Color4D} instance that represents the color magenta.
	 */
	public static final Color4D MAGENTA;
	
	/**
	 * A {@code Color4D} instance that represents the color red.
	 */
	public static final Color4D RED;
	
	/**
	 * A {@code Color4D} instance that represents the color transparent.
	 */
	public static final Color4D TRANSPARENT;
	
	/**
	 * A {@code Color4D} instance that represents the color white.
	 */
	public static final Color4D WHITE;
	
	/**
	 * A {@code Color4D} instance that represents the color yellow.
	 */
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
	
	/**
	 * Constructs a new {@code Color4D} instance from {@code color}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@link Color3D} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Color4D(final Color3D color) {
		this(color.r, color.g, color.b);
	}
	
	/**
	 * Constructs a new {@code Color4D} instance from {@code color} and {@code a}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@link Color3D} instance
	 * @param a the value of the alpha component
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Color4D(final Color3D color, final double a) {
		this(color.r, color.g, color.b, a);
	}
	
	/**
	 * Constructs a new {@code Color4D} instance from {@code color}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@link Color3F} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Color4D(final Color3F color) {
		this(color.r, color.g, color.b);
	}
	
	/**
	 * Constructs a new {@code Color4D} instance from {@code color} and {@code a}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@link Color3F} instance
	 * @param a the value of the alpha component
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Color4D(final Color3F color, final double a) {
		this(color.r, color.g, color.b, a);
	}
	
	/**
	 * Constructs a new {@code Color4D} instance from {@code color}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@link Color4F} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Color4D(final Color4F color) {
		this(color.r, color.g, color.b, color.a);
	}
	
	/**
	 * Constructs a new {@code Color4D} instance that represents gray.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color4D(grayscale, 1.0D);
	 * }
	 * </pre>
	 * 
	 * @param grayscale the value of the red, green and blue components
	 */
	public Color4D(final double grayscale) {
		this(grayscale, 1.0D);
	}
	
	/**
	 * Constructs a new {@code Color4D} instance that represents gray.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color4D(grayscale, grayscale, grayscale, a);
	 * }
	 * </pre>
	 * 
	 * @param grayscale the value of the red, green and blue components
	 * @param a the value of the alpha component
	 */
	public Color4D(final double grayscale, final double a) {
		this(grayscale, grayscale, grayscale, a);
	}
	
	/**
	 * Constructs a new {@code Color4D} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color4D(r, g, b, 1.0D);
	 * }
	 * </pre>
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 */
	public Color4D(final double r, final double g, final double b) {
		this(r, g, b, 1.0D);
	}
	
	/**
	 * Constructs a new {@code Color4D} instance.
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 * @param a the value of the alpha component
	 */
	public Color4D(final double r, final double g, final double b, final double a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	/**
	 * Constructs a new {@code Color4D} instance that represents gray.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color4D(grayscale, 255);
	 * }
	 * </pre>
	 * 
	 * @param grayscale the value of the red, green and blue components
	 */
	public Color4D(final int grayscale) {
		this(grayscale, 255);
	}
	
	/**
	 * Constructs a new {@code Color4D} instance that represents gray.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color4D(grayscale, grayscale, grayscale, a);
	 * }
	 * </pre>
	 * 
	 * @param grayscale the value of the red, green and blue components
	 * @param a the value of the alpha component
	 */
	public Color4D(final int grayscale, final int a) {
		this(grayscale, grayscale, grayscale, a);
	}
	
	/**
	 * Constructs a new {@code Color4D} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color4D(r, g, b, 255);
	 * }
	 * </pre>
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 */
	public Color4D(final int r, final int g, final int b) {
		this(r, g, b, 255);
	}
	
	/**
	 * Constructs a new {@code Color4D} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color4D(Ints.saturate(r) / 255.0D, Ints.saturate(g) / 255.0D, Ints.saturate(b) / 255.0D, Ints.saturate(a) / 255.0D);
	 * }
	 * </pre>
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 * @param a the value of the alpha component
	 */
	public Color4D(final int r, final int g, final int b, final int a) {
		this(Ints.saturate(r) / 255.0D, Ints.saturate(g) / 255.0D, Ints.saturate(b) / 255.0D, Ints.saturate(a) / 255.0D);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code Color4D} instance.
	 * 
	 * @return a {@code String} representation of this {@code Color4D} instance
	 */
	@Override
	public String toString() {
		return String.format("new Color4D(%s, %s, %s, %s)", Strings.toNonScientificNotationJava(this.r), Strings.toNonScientificNotationJava(this.g), Strings.toNonScientificNotationJava(this.b), Strings.toNonScientificNotationJava(this.a));
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
		} else if(!Doubles.equals(this.a, color.a)) {
			return false;
		} else if(!Doubles.equals(this.b, color.b)) {
			return false;
		} else if(!Doubles.equals(this.g, color.g)) {
			return false;
		} else if(!Doubles.equals(this.r, color.r)) {
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
		return Doubles.isZero(this.r) && Doubles.isZero(this.g) && Doubles.isZero(this.b);
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
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4D} instance is blue, {@code false} otherwise.
	 * <p>
	 * The {@code Color4D} instance {@code color} is blue if, and only if, {@code color.b - deltaR >= color.r} and {@code color.b - deltaG >= color.g}.
	 * 
	 * @param deltaR the delta for the R-component
	 * @param deltaG the delta for the G-component
	 * @return {@code true} if, and only if, this {@code Color4D} instance is blue, {@code false} otherwise
	 */
	public boolean isBlue(final double deltaR, final double deltaG) {
		return this.b - deltaR >= this.r && this.b - deltaG >= this.g;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4D} instance is cyan, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color4D} instance is cyan, {@code false} otherwise
	 */
	public boolean isCyan() {
		return Doubles.equals(this.g, this.b) && this.r < this.g;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4D} instance is grayscale, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color4D} instance is grayscale, {@code false} otherwise
	 */
	public boolean isGrayscale() {
		return Doubles.equals(this.r, this.g) && Doubles.equals(this.g, this.b);
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
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4D} instance is green, {@code false} otherwise.
	 * <p>
	 * The {@code Color4D} instance {@code color} is green if, and only if, {@code color.g - deltaR >= color.r} and {@code color.g - deltaB >= color.b}.
	 * 
	 * @param deltaR the delta for the R-component
	 * @param deltaB the delta for the B-component
	 * @return {@code true} if, and only if, this {@code Color4D} instance is green, {@code false} otherwise
	 */
	public boolean isGreen(final double deltaR, final double deltaB) {
		return this.g - deltaR >= this.r && this.g - deltaB >= this.b;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4D} instance is magenta, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color4D} instance is magenta, {@code false} otherwise
	 */
	public boolean isMagenta() {
		return Doubles.equals(this.r, this.b) && this.g < this.b;
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
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4D} instance is red, {@code false} otherwise.
	 * <p>
	 * The {@code Color4D} instance {@code color} is red if, and only if, {@code color.r - deltaG >= color.g} and {@code color.r - deltaB >= color.b}.
	 * 
	 * @param deltaG the delta for the G-component
	 * @param deltaB the delta for the B-component
	 * @return {@code true} if, and only if, this {@code Color4D} instance is red, {@code false} otherwise
	 */
	public boolean isRed(final double deltaG, final double deltaB) {
		return this.r - deltaG >= this.g && this.r - deltaB >= this.b;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4D} instance is white, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color4D} instance is white, {@code false} otherwise
	 */
	public boolean isWhite() {
		return Doubles.equals(this.r, 1.0D) && Doubles.equals(this.g, 1.0D) && Doubles.equals(this.b, 1.0D);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Color4D} instance is yellow, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Color4D} instance is yellow, {@code false} otherwise
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
	
	/**
	 * Returns the alpha component as an {@code int}.
	 * 
	 * @return the alpha component as an {@code int}
	 */
	public int toIntA() {
		return toIntA(this.a);
	}
	
	/**
	 * Returns the alpha, red, green and blue components as an {@code int} in the format ARGB.
	 * 
	 * @return the alpha, red, green and blue components as an {@code int} in the format ARGB
	 */
	public int toIntARGB() {
		return toIntARGB(this.r, this.g, this.b, this.a);
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
	 * Adds the red, green and blue component values of {@code colorRHS} to the red, green and blue component values of {@code colorLHS}.
	 * <p>
	 * Returns a new {@code Color3D} instance with the result of the addition.
	 * <p>
	 * If either {@code colorLHS} or {@code colorRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The alpha component of {@code colorLHS} will be used.
	 * 
	 * @param colorLHS the {@code Color3D} instance on the left-hand side
	 * @param colorRHS the {@code Color3D} instance on the right-hand side
	 * @return a new {@code Color3D} instance with the result of the addition
	 * @throws NullPointerException thrown if, and only if, either {@code colorLHS} or {@code colorRHS} are {@code null}
	 */
	public static Color4D add(final Color4D colorLHS, final Color4D colorRHS) {
		final double r = colorLHS.r + colorRHS.r;
		final double g = colorLHS.g + colorRHS.g;
		final double b = colorLHS.b + colorRHS.b;
		final double a = colorLHS.a;
		
		return new Color4D(r, g, b, a);
	}
	
	/**
	 * Adds {@code scalarRHS} to the red, green and blue component values of {@code colorLHS}.
	 * <p>
	 * Returns a new {@code Color3D} instance with the result of the addition.
	 * <p>
	 * If {@code colorLHS} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The alpha component of {@code colorLHS} will be used.
	 * 
	 * @param colorLHS the {@code Color3D} instance on the left-hand side
	 * @param scalarRHS the scalar value on the right-hand side
	 * @return a new {@code Color3D} instance with the result of the addition
	 * @throws NullPointerException thrown if, and only if, {@code colorLHS} is {@code null}
	 */
	public static Color4D add(final Color4D colorLHS, final double scalarRHS) {
		final double r = colorLHS.r + scalarRHS;
		final double g = colorLHS.g + scalarRHS;
		final double b = colorLHS.b + scalarRHS;
		final double a = colorLHS.a;
		
		return new Color4D(r, g, b, a);
	}
	
	/**
	 * Blends the component values of {@code colorLHS} and {@code colorRHS}.
	 * <p>
	 * Returns a new {@code Color4D} instance with the result of the blend.
	 * <p>
	 * If either {@code colorLHS} or {@code colorRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color4D.blend(colorLHS, colorRHS, 0.5D);
	 * }
	 * </pre>
	 * 
	 * @param colorLHS the {@code Color4D} instance on the left-hand side
	 * @param colorRHS the {@code Color4D} instance on the right-hand side
	 * @return a new {@code Color4D} instance with the result of the blend
	 * @throws NullPointerException thrown if, and only if, either {@code colorLHS} or {@code colorRHS} are {@code null}
	 */
	public static Color4D blend(final Color4D colorLHS, final Color4D colorRHS) {
		return blend(colorLHS, colorRHS, 0.5D);
	}
	
	/**
	 * Blends the component values of {@code color11}, {@code color12}, {@code color21} and {@code color22}.
	 * <p>
	 * Returns a new {@code Color4D} instance with the result of the blend.
	 * <p>
	 * If either {@code color11}, {@code color12}, {@code color21} or {@code color22} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color4D.blend(Color4D.blend(color11, color12, tX), Color4D.blend(color21, color22, tX), tY);
	 * }
	 * </pre>
	 * 
	 * @param color11 the {@code Color4D} instance on row 1 and column 1
	 * @param color12 the {@code Color4D} instance on row 1 and column 2
	 * @param color21 the {@code Color4D} instance on row 2 and column 1
	 * @param color22 the {@code Color4D} instance on row 2 and column 2
	 * @param tX the factor to use for all components in the first and second blend operation
	 * @param tY the factor to use for all components in the third blend operation
	 * @return a new {@code Color4D} instance with the result of the blend
	 * @throws NullPointerException thrown if, and only if, either {@code color11}, {@code color12}, {@code color21} or {@code color22} are {@code null}
	 */
	public static Color4D blend(final Color4D color11, final Color4D color12, final Color4D color21, final Color4D color22, final double tX, final double tY) {
		return blend(blend(color11, color12, tX), blend(color21, color22, tX), tY);
	}
	
	/**
	 * Blends the component values of {@code colorLHS} and {@code colorRHS}.
	 * <p>
	 * Returns a new {@code Color4D} instance with the result of the blend.
	 * <p>
	 * If either {@code colorLHS} or {@code colorRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color4D.blend(colorLHS, colorRHS, t, t, t, t);
	 * }
	 * </pre>
	 * 
	 * @param colorLHS the {@code Color4D} instance on the left-hand side
	 * @param colorRHS the {@code Color4D} instance on the right-hand side
	 * @param t the factor to use for all components in the blending process
	 * @return a new {@code Color4D} instance with the result of the blend
	 * @throws NullPointerException thrown if, and only if, either {@code colorLHS} or {@code colorRHS} are {@code null}
	 */
	public static Color4D blend(final Color4D colorLHS, final Color4D colorRHS, final double t) {
		return blend(colorLHS, colorRHS, t, t, t, t);
	}
	
	/**
	 * Blends the component values of {@code colorLHS} and {@code colorRHS}.
	 * <p>
	 * Returns a new {@code Color4D} instance with the result of the blend.
	 * <p>
	 * If either {@code colorLHS} or {@code colorRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param colorLHS the {@code Color4D} instance on the left-hand side
	 * @param colorRHS the {@code Color4D} instance on the right-hand side
	 * @param tR the factor to use for the R-component in the blending process
	 * @param tG the factor to use for the G-component in the blending process
	 * @param tB the factor to use for the B-component in the blending process
	 * @param tA the factor to use for the A-component in the blending process
	 * @return a new {@code Color4D} instance with the result of the blend
	 * @throws NullPointerException thrown if, and only if, either {@code colorLHS} or {@code colorRHS} are {@code null}
	 */
	public static Color4D blend(final Color4D colorLHS, final Color4D colorRHS, final double tR, final double tG, final double tB, final double tA) {
		final double r = Doubles.lerp(colorLHS.r, colorRHS.r, tR);
		final double g = Doubles.lerp(colorLHS.g, colorRHS.g, tG);
		final double b = Doubles.lerp(colorLHS.b, colorRHS.b, tB);
		final double a = Doubles.lerp(colorLHS.a, colorRHS.a, tA);
		
		return new Color4D(r, g, b, a);
	}
	
	/**
	 * Blends the component values of {@code colorLHS} over the component values of {@code colorRHS}.
	 * <p>
	 * Returns a new {@code Color4D} instance with the result of the blend.
	 * <p>
	 * If either {@code colorLHS} or {@code colorRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param colorLHS the {@code Color4D} instance on the left-hand side
	 * @param colorRHS the {@code Color4D} instance on the right-hand side
	 * @return a new {@code Color4D} instance with the result of the blend
	 * @throws NullPointerException thrown if, and only if, either {@code colorLHS} or {@code colorRHS} are {@code null}
	 */
	public static Color4D blendOver(final Color4D colorLHS, final Color4D colorRHS) {
		final double a = colorLHS.a + colorRHS.a * (1.0D - colorLHS.a);
		final double r = (colorLHS.r * colorLHS.a + colorRHS.r * colorRHS.a * (1.0D - colorLHS.a)) / a;
		final double g = (colorLHS.g * colorLHS.a + colorRHS.g * colorRHS.a * (1.0D - colorLHS.a)) / a;
		final double b = (colorLHS.b * colorLHS.a + colorRHS.b * colorRHS.a * (1.0D - colorLHS.a)) / a;
		
		return new Color4D(r, g, b, a);
	}
	
	/**
	 * Returns a {@code Color4D} instance from the {@code int} {@code colorARGB}.
	 * 
	 * @param colorARGB an {@code int} that contains the alpha, red, green and blue components
	 * @return a {@code Color4D} instance from the {@code int} {@code colorARGB}
	 */
	public static Color4D fromIntARGB(final int colorARGB) {
		final int a = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_A) & 0xFF;
		final int r = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_R) & 0xFF;
		final int g = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_G) & 0xFF;
		final int b = (colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_B) & 0xFF;
		
		return new Color4D(r, g, b, a);
	}
	
	/**
	 * Returns a {@code Color4D} instance from the {@code int} {@code colorRGB}.
	 * 
	 * @param colorRGB an {@code int} that contains the red, green and blue components
	 * @return a {@code Color4D} instance from the {@code int} {@code colorRGB}
	 */
	public static Color4D fromIntRGB(final int colorRGB) {
		final int r = (colorRGB >> Utilities.COLOR_R_G_B_SHIFT_R) & 0xFF;
		final int g = (colorRGB >> Utilities.COLOR_R_G_B_SHIFT_G) & 0xFF;
		final int b = (colorRGB >> Utilities.COLOR_R_G_B_SHIFT_B) & 0xFF;
		
		return new Color4D(r, g, b);
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
	
	/**
	 * Returns a grayscale {@code Color4D} instance based on {@code color.a}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color4D} instance
	 * @return a grayscale {@code Color4D} instance based on {@code color.a}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color4D grayscaleA(final Color4D color) {
		return new Color4D(color.a, color.a);
	}
	
	/**
	 * Returns a grayscale {@code Color4D} instance based on {@code color.average()}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color4D} instance
	 * @return a grayscale {@code Color4D} instance based on {@code color.average()}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color4D grayscaleAverage(final Color4D color) {
		return new Color4D(color.average(), color.a);
	}
	
	/**
	 * Returns a grayscale {@code Color4D} instance based on {@code color.b}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color4D} instance
	 * @return a grayscale {@code Color4D} instance based on {@code color.b}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color4D grayscaleB(final Color4D color) {
		return new Color4D(color.b, color.a);
	}
	
	/**
	 * Returns a grayscale {@code Color4D} instance based on {@code color.g}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color4D} instance
	 * @return a grayscale {@code Color4D} instance based on {@code color.g}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color4D grayscaleG(final Color4D color) {
		return new Color4D(color.g, color.a);
	}
	
	/**
	 * Returns a grayscale {@code Color4D} instance based on {@code color.lightness()}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color4D} instance
	 * @return a grayscale {@code Color4D} instance based on {@code color.lightness()}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color4D grayscaleLightness(final Color4D color) {
		return new Color4D(color.lightness(), color.a);
	}
	
	/**
	 * Returns a grayscale {@code Color4D} instance based on {@code color.max()}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color4D} instance
	 * @return a grayscale {@code Color4D} instance based on {@code color.max()}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color4D grayscaleMax(final Color4D color) {
		return new Color4D(color.max(), color.a);
	}
	
	/**
	 * Returns a grayscale {@code Color4D} instance based on {@code color.min()}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color4D} instance
	 * @return a grayscale {@code Color4D} instance based on {@code color.min()}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color4D grayscaleMin(final Color4D color) {
		return new Color4D(color.min(), color.a);
	}
	
	/**
	 * Returns a grayscale {@code Color4D} instance based on {@code color.r}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color4D} instance
	 * @return a grayscale {@code Color4D} instance based on {@code color.r}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color4D grayscaleR(final Color4D color) {
		return new Color4D(color.r, color.a);
	}
	
	/**
	 * Returns a grayscale {@code Color4D} instance based on {@code color.relativeLuminance()}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color4D} instance
	 * @return a grayscale {@code Color4D} instance based on {@code color.relativeLuminance()}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color4D grayscaleRelativeLuminance(final Color4D color) {
		return new Color4D(color.relativeLuminance(), color.a);
	}
	
	/**
	 * Inverts the red, green and blue component values of {@code color}.
	 * <p>
	 * Returns a new {@code Color4D} instance with the result of the inversion.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color4D} instance
	 * @return a new {@code Color4D} instance with the result of the inversion
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color4D invert(final Color4D color) {
		return new Color4D(1.0D - color.r, 1.0D - color.g, 1.0D - color.b, color.a);
	}
	
	/**
	 * Multiplies the red, green and blue component values of {@code colorLHS} with {@code scalarRHS}.
	 * <p>
	 * Returns a new {@code Color4D} instance with the result of the multiplication.
	 * <p>
	 * If {@code colorLHS} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param colorLHS the {@code Color4D} instance on the left-hand side
	 * @param scalarRHS the scalar value on the right-hand side
	 * @return a new {@code Color4D} instance with the result of the multiplication
	 * @throws NullPointerException thrown if, and only if, {@code colorLHS} is {@code null}
	 */
	public static Color4D multiply(final Color4D colorLHS, final double scalarRHS) {
		final double r = colorLHS.r * scalarRHS;
		final double g = colorLHS.g * scalarRHS;
		final double b = colorLHS.b * scalarRHS;
		final double a = colorLHS.a;
		
		return new Color4D(r, g, b, a);
	}
	
	/**
	 * Returns a {@code Color4D} instance with random component values.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color4D(Randoms.nextDouble(Doubles.nextUp(1.0D)), Randoms.nextDouble(Doubles.nextUp(1.0D)), Randoms.nextDouble(Doubles.nextUp(1.0D)));
	 * }
	 * </pre>
	 * 
	 * @return a {@code Color4D} instance with random component values
	 */
	public static Color4D random() {
		return new Color4D(Randoms.nextDouble(Doubles.nextUp(1.0D)), Randoms.nextDouble(Doubles.nextUp(1.0D)), Randoms.nextDouble(Doubles.nextUp(1.0D)));
	}
	
	/**
	 * Returns a {@code Color4D} instance with random component values that represents a blue color.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color4D.randomBlue(0.0D, 0.0D);
	 * }
	 * </pre>
	 * 
	 * @return a {@code Color4D} instance with random component values that represents a blue color
	 */
	public static Color4D randomBlue() {
		return randomBlue(0.0D, 0.0D);
	}
	
	/**
	 * Returns a {@code Color4D} instance with random component values that represents a blue color.
	 * 
	 * @param maxR the maximum value to use for the R-component
	 * @param maxG the maximum value to use for the G-component
	 * @return a {@code Color4D} instance with random component values that represents a blue color
	 */
	public static Color4D randomBlue(final double maxR, final double maxG) {
		final double b = Randoms.nextDouble(Doubles.nextUp(0.0D), Doubles.nextUp(1.0D));
		final double r = Randoms.nextDouble(0.0D, Doubles.min(Doubles.nextUp(Doubles.max(maxR, 0.0D)), b));
		final double g = Randoms.nextDouble(0.0D, Doubles.min(Doubles.nextUp(Doubles.max(maxG, 0.0D)), b));
		
		return new Color4D(r, g, b);
	}
	
	/**
	 * Returns a {@code Color4D} instance with random component values that represents a cyan color.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color4D.randomCyan(0.0D, 0.0D);
	 * }
	 * </pre>
	 * 
	 * @return a {@code Color4D} instance with random component values that represents a cyan color
	 */
	public static Color4D randomCyan() {
		return randomCyan(0.0D, 0.0D);
	}
	
	/**
	 * Returns a {@code Color4D} instance with random component values that represents a cyan color.
	 * 
	 * @param minGB the minimum value to use for the G- and B-components
	 * @param maxR the maximum value to use for the R-component
	 * @return a {@code Color4D} instance with random component values that represents a cyan color
	 */
	public static Color4D randomCyan(final double minGB, final double maxR) {
		final double x = Randoms.nextDouble(Doubles.max(Doubles.min(minGB, 1.0D), Doubles.nextUp(0.0D)), Doubles.nextUp(1.0D));
		final double y = Randoms.nextDouble(0.0D, Doubles.min(Doubles.nextUp(Doubles.max(maxR, 0.0D)), x));
		
		return new Color4D(y, x, x);
	}
	
	/**
	 * Returns a {@code Color4D} instance with random component values that represents a grayscale color.
	 * 
	 * @return a {@code Color4D} instance with random component values that represents a grayscale color
	 */
	public static Color4D randomGrayscale() {
		return new Color4D(Randoms.nextDouble(Doubles.nextUp(1.0D)));
	}
	
	/**
	 * Returns a {@code Color4D} instance with random component values that represents a green color.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color4D.randomGreen(0.0D, 0.0D);
	 * }
	 * </pre>
	 * 
	 * @return a {@code Color4D} instance with random component values that represents a green color
	 */
	public static Color4D randomGreen() {
		return randomGreen(0.0D, 0.0D);
	}
	
	/**
	 * Returns a {@code Color4D} instance with random component values that represents a green color.
	 * 
	 * @param maxR the maximum value to use for the R-component
	 * @param maxB the maximum value to use for the B-component
	 * @return a {@code Color4D} instance with random component values that represents a green color
	 */
	public static Color4D randomGreen(final double maxR, final double maxB) {
		final double g = Randoms.nextDouble(Doubles.nextUp(0.0D), Doubles.nextUp(1.0D));
		final double r = Randoms.nextDouble(0.0D, Doubles.min(Doubles.nextUp(Doubles.max(maxR, 0.0D)), g));
		final double b = Randoms.nextDouble(0.0D, Doubles.min(Doubles.nextUp(Doubles.max(maxB, 0.0D)), g));
		
		return new Color4D(r, g, b);
	}
	
	/**
	 * Returns a {@code Color4D} instance with random component values that represents a magenta color.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color4D.randomMagenta(0.0D, 0.0D);
	 * }
	 * </pre>
	 * 
	 * @return a {@code Color4D} instance with random component values that represents a magenta color
	 */
	public static Color4D randomMagenta() {
		return randomMagenta(0.0D, 0.0D);
	}
	
	/**
	 * Returns a {@code Color4D} instance with random component values that represents a magenta color.
	 * 
	 * @param minRB the minimum value to use for the R- and B-components
	 * @param maxG the maximum value to use for the G-component
	 * @return a {@code Color4D} instance with random component values that represents a magenta color
	 */
	public static Color4D randomMagenta(final double minRB, final double maxG) {
		final double x = Randoms.nextDouble(Doubles.max(Doubles.min(minRB, 1.0D), Doubles.nextUp(0.0D)), Doubles.nextUp(1.0D));
		final double y = Randoms.nextDouble(0.0D, Doubles.min(Doubles.nextUp(Doubles.max(maxG, 0.0D)), x));
		
		return new Color4D(x, y, x);
	}
	
	/**
	 * Returns a {@code Color4D} instance with random component values that represents a red color.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color4D.randomRed(0.0D, 0.0D);
	 * }
	 * </pre>
	 * 
	 * @return a {@code Color4D} instance with random component values that represents a red color
	 */
	public static Color4D randomRed() {
		return randomRed(0.0D, 0.0D);
	}
	
	/**
	 * Returns a {@code Color4D} instance with random component values that represents a red color.
	 * 
	 * @param maxG the maximum value to use for the G-component
	 * @param maxB the maximum value to use for the B-component
	 * @return a {@code Color4D} instance with random component values that represents a red color
	 */
	public static Color4D randomRed(final double maxG, final double maxB) {
		final double r = Randoms.nextDouble(Doubles.nextUp(0.0D), Doubles.nextUp(1.0D));
		final double g = Randoms.nextDouble(0.0D, Doubles.min(Doubles.nextUp(Doubles.max(maxG, 0.0D)), r));
		final double b = Randoms.nextDouble(0.0D, Doubles.min(Doubles.nextUp(Doubles.max(maxB, 0.0D)), r));
		
		return new Color4D(r, g, b);
	}
	
	/**
	 * Returns a {@code Color4D} instance with random component values that represents a yellow color.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color4D.randomYellow(0.0D, 0.0D);
	 * }
	 * </pre>
	 * 
	 * @return a {@code Color4D} instance with random component values that represents a yellow color
	 */
	public static Color4D randomYellow() {
		return randomYellow(0.0D, 0.0D);
	}
	
	/**
	 * Returns a {@code Color4D} instance with random component values that represents a yellow color.
	 * 
	 * @param minRG the minimum value to use for the R- and G-components
	 * @param maxB the maximum value to use for the B-component
	 * @return a {@code Color4D} instance with random component values that represents a yellow color
	 */
	public static Color4D randomYellow(final double minRG, final double maxB) {
		final double x = Randoms.nextDouble(Doubles.max(Doubles.min(minRG, 1.0D), Doubles.nextUp(0.0D)), Doubles.nextUp(1.0D));
		final double y = Randoms.nextDouble(0.0D, Doubles.min(Doubles.nextUp(Doubles.max(maxB, 0.0D)), x));
		
		return new Color4D(x, x, y);
	}
	
	/**
	 * Converts {@code color} to its sepia-representation.
	 * <p>
	 * Returns a new {@code Color4D} instance with the result of the operation.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color a {@code Color4D} instance
	 * @return a new {@code Color4D} instance with the result of the operation
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public static Color4D sepia(final Color4D color) {
		final double r = color.r * 0.393D + color.g * 0.769D + color.b * 0.189D;
		final double g = color.r * 0.349D + color.g * 0.686D + color.b * 0.168D;
		final double b = color.r * 0.272D + color.g * 0.534D + color.b * 0.131D;
		final double a = color.a;
		
		return new Color4D(r, g, b, a);
	}
	
	/**
	 * Returns the value of the A-component in {@code colorARGB} as a {@code double}.
	 * 
	 * @param colorARGB an {@code int} that contains a color with components in the format ARGB
	 * @return the value of the A-component in {@code colorARGB} as a {@code double}
	 */
	public static double fromIntARGBToDoubleA(final int colorARGB) {
		return ((colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_A) & 0xFF) / 255.0D;
	}
	
	/**
	 * Returns the value of the B-component in {@code colorARGB} as a {@code double}.
	 * 
	 * @param colorARGB an {@code int} that contains a color with components in the format ARGB
	 * @return the value of the B-component in {@code colorARGB} as a {@code double}
	 */
	public static double fromIntARGBToDoubleB(final int colorARGB) {
		return ((colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_B) & 0xFF) / 255.0D;
	}
	
	/**
	 * Returns the value of the G-component in {@code colorARGB} as a {@code double}.
	 * 
	 * @param colorARGB an {@code int} that contains a color with components in the format ARGB
	 * @return the value of the G-component in {@code colorARGB} as a {@code double}
	 */
	public static double fromIntARGBToDoubleG(final int colorARGB) {
		return ((colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_G) & 0xFF) / 255.0D;
	}
	
	/**
	 * Returns the value of the R-component in {@code colorARGB} as a {@code double}.
	 * 
	 * @param colorARGB an {@code int} that contains a color with components in the format ARGB
	 * @return the value of the R-component in {@code colorARGB} as a {@code double}
	 */
	public static double fromIntARGBToDoubleR(final int colorARGB) {
		return ((colorARGB >> Utilities.COLOR_A_R_G_B_SHIFT_R) & 0xFF) / 255.0D;
	}
	
	/**
	 * Returns the value of the B-component in {@code colorRGB} as a {@code double}.
	 * 
	 * @param colorRGB an {@code int} that contains a color with components in the format RGB
	 * @return the value of the B-component in {@code colorRGB} as a {@code double}
	 */
	public static double fromIntRGBToDoubleB(final int colorRGB) {
		return ((colorRGB >> Utilities.COLOR_R_G_B_SHIFT_B) & 0xFF) / 255.0D;
	}
	
	/**
	 * Returns the value of the G-component in {@code colorRGB} as a {@code double}.
	 * 
	 * @param colorRGB an {@code int} that contains a color with components in the format RGB
	 * @return the value of the G-component in {@code colorRGB} as a {@code double}
	 */
	public static double fromIntRGBToDoubleG(final int colorRGB) {
		return ((colorRGB >> Utilities.COLOR_R_G_B_SHIFT_G) & 0xFF) / 255.0D;
	}
	
	/**
	 * Returns the value of the R-component in {@code colorRGB} as a {@code double}.
	 * 
	 * @param colorRGB an {@code int} that contains a color with components in the format RGB
	 * @return the value of the R-component in {@code colorRGB} as a {@code double}
	 */
	public static double fromIntRGBToDoubleR(final int colorRGB) {
		return ((colorRGB >> Utilities.COLOR_R_G_B_SHIFT_R) & 0xFF) / 255.0D;
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
	 * Returns the alpha component as an {@code int}.
	 * 
	 * @param a the value of the alpha component
	 * @return the alpha component as an {@code int}
	 */
	public static int toIntA(final double a) {
		return Ints.saturateAndScaleToInt(a);
	}
	
	/**
	 * Returns the alpha, red, green and blue components as an {@code int} in the format ARGB.
	 * 
	 * @param r the value of the red component
	 * @param g the value of the green component
	 * @param b the value of the blue component
	 * @param a the value of the alpha component
	 * @return the alpha, red, green and blue components as an {@code int} in the format ARGB
	 */
	public static int toIntARGB(final double r, final double g, final double b, final double a) {
		final int colorA = ((toIntA(a) & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_A);
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
	public static int toIntB(final double b) {
		return Ints.saturateAndScaleToInt(b);
	}
	
	/**
	 * Returns the green component as an {@code int}.
	 * 
	 * @param g the value of the green component
	 * @return the green component as an {@code int}
	 */
	public static int toIntG(final double g) {
		return Ints.saturateAndScaleToInt(g);
	}
	
	/**
	 * Returns the red component as an {@code int}.
	 * 
	 * @param r the value of the red component
	 * @return the red component as an {@code int}
	 */
	public static int toIntR(final double r) {
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
	public static int toIntRGB(final double r, final double g, final double b) {
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