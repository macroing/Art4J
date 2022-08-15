/**
 * Copyright 2022 J&#246;rgen Lundgren
 * 
 * This file is part of org.macroing.art4j.
 * 
 * org.macroing.art4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * org.macroing.art4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with org.macroing.art4j. If not, see <http://www.gnu.org/licenses/>.
 */
package org.macroing.art4j.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;//TODO: Add Unit Tests!
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import org.macroing.art4j.color.Color3D;
import org.macroing.art4j.color.Color3F;
import org.macroing.art4j.color.Color4D;
import org.macroing.art4j.color.Color4F;
import org.macroing.art4j.color.ColorSpaceD;
import org.macroing.art4j.color.ColorSpaceF;
import org.macroing.art4j.data.Data;
import org.macroing.art4j.data.DataFactory;
import org.macroing.art4j.filter.Filter2D;
import org.macroing.art4j.filter.Filter2F;
import org.macroing.art4j.geometry.Point2I;
import org.macroing.art4j.geometry.Shape2I;
import org.macroing.art4j.geometry.shape.Rectangle2I;
import org.macroing.art4j.kernel.ConvolutionKernelND;
import org.macroing.art4j.kernel.ConvolutionKernelNF;
import org.macroing.art4j.pixel.Color4DPixelFilter;
import org.macroing.art4j.pixel.Color4DPixelOperator;
import org.macroing.art4j.pixel.Color4FPixelFilter;
import org.macroing.art4j.pixel.Color4FPixelOperator;
import org.macroing.java.lang.Doubles;
import org.macroing.java.lang.Floats;
import org.macroing.java.lang.Ints;
import org.macroing.java.util.function.IntTernaryOperator;
import org.macroing.java.util.function.IntTriPredicate;
import org.macroing.java.util.function.TriFunction;

/**
 * An {@code Image} represents an image that can be drawn to and saved to disk.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * Many operations are supported by default. But there are limitations, such as drawing text. In order to circumvent these limitations, the method {@link #draw(Consumer)} was added. It allows for interoperability between this API and the Java2D API.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Image {
	private final Data data;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Image} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Image(1024, 768);
	 * }
	 * </pre>
	 */
	public Image() {
		this(1024, 768);
	}
	
	/**
	 * Constructs a new {@code Image} instance from {@code bufferedImage}.
	 * <p>
	 * If {@code bufferedImage} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Image(bufferedImage, DataFactory.forColorARGB());
	 * }
	 * </pre>
	 * 
	 * @param bufferedImage a {@code BufferedImage} instance
	 * @throws NullPointerException thrown if, and only if, {@code bufferedImage} is {@code null}
	 */
	public Image(final BufferedImage bufferedImage) {
		this(bufferedImage, DataFactory.forColorARGB());
	}
	
	/**
	 * Constructs a new {@code Image} instance from {@code bufferedImage} using {@code dataFactory}.
	 * <p>
	 * If either {@code bufferedImage} or {@code dataFactory} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param bufferedImage a {@code BufferedImage} instance
	 * @param dataFactory a {@link DataFactory} instance
	 * @throws NullPointerException thrown if, and only if, either {@code bufferedImage} or {@code dataFactory} are {@code null}
	 */
	public Image(final BufferedImage bufferedImage, final DataFactory dataFactory) {
		this.data = dataFactory.create(bufferedImage);
	}
	
	/**
	 * Constructs a new {@code Image} instance from {@code data}.
	 * <p>
	 * If {@code data} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param data the {@link Data} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code data} is {@code null}
	 */
	public Image(final Data data) {
		this.data = data.copy();
	}
	
	/**
	 * Constructs a new {@code Image} instance by reading the content of the file represented by {@code file}.
	 * <p>
	 * If {@code file} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Image(file, DataFactory.forColorARGB());
	 * }
	 * </pre>
	 * 
	 * @param file a {@code File} that represents the file to read from
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public Image(final File file) {
		this(file, DataFactory.forColorARGB());
	}
	
	/**
	 * Constructs a new {@code Image} instance by reading the content of the file represented by {@code file} using {@code dataFactory}.
	 * <p>
	 * If either {@code file} or {@code dataFactory} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param file a {@code File} that represents the file to read from
	 * @param dataFactory a {@link DataFactory} instance
	 * @throws NullPointerException thrown if, and only if, either {@code file} or {@code dataFactory} are {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public Image(final File file, final DataFactory dataFactory) {
		this.data = dataFactory.create(file);
	}
	
	/**
	 * Constructs a new {@code Image} instance from {@code image}.
	 * <p>
	 * If {@code image} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param image an {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code image} is {@code null}
	 */
	public Image(final Image image) {
		this(image.data);
	}
	
	/**
	 * Constructs a new {@code Image} instance by reading the content of the file represented by the pathname {@code pathname}.
	 * <p>
	 * If {@code pathname} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Image(pathname, DataFactory.forColorARGB());
	 * }
	 * </pre>
	 * 
	 * @param pathname a {@code String} that represents the pathname of the file to read from
	 * @throws NullPointerException thrown if, and only if, {@code pathname} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public Image(final String pathname) {
		this(pathname, DataFactory.forColorARGB());
	}
	
	/**
	 * Constructs a new {@code Image} instance by reading the content of the file represented by the pathname {@code pathname} using {@code dataFactory}.
	 * <p>
	 * If either {@code pathname} or {@code dataFactory} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param pathname a {@code String} that represents the pathname of the file to read from
	 * @param dataFactory a {@link DataFactory} instance
	 * @throws NullPointerException thrown if, and only if, either {@code pathname} or {@code dataFactory} are {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public Image(final String pathname, final DataFactory dataFactory) {
		this.data = dataFactory.create(pathname);
	}
	
	/**
	 * Constructs a new {@code Image} instance by reading the content of the URL represented by {@code uRL}.
	 * <p>
	 * If {@code uRL} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Image(uRL, DataFactory.forColorARGB());
	 * }
	 * </pre>
	 * 
	 * @param uRL a {@code URL} that represents the URL to read from
	 * @throws NullPointerException thrown if, and only if, {@code uRL} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public Image(final URL uRL) {
		this(uRL, DataFactory.forColorARGB());
	}
	
	/**
	 * Constructs a new {@code Image} instance by reading the content of the URL represented by {@code uRL} using {@code dataFactory}.
	 * <p>
	 * If either {@code uRL} or {@code dataFactory} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param uRL a {@code URL} that represents the URL to read from
	 * @param dataFactory a {@link DataFactory} instance
	 * @throws NullPointerException thrown if, and only if, either {@code uRL} or {@code dataFactory} are {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public Image(final URL uRL, final DataFactory dataFactory) {
		this.data = dataFactory.create(uRL);
	}
	
	/**
	 * Constructs a new {@code Image} instance filled with {@code Color4D.WHITE}.
	 * <p>
	 * If either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Image(resolutionX, resolutionY, Color4D.WHITE);
	 * }
	 * </pre>
	 * 
	 * @param resolutionX the resolution along the X-axis
	 * @param resolutionY the resolution along the Y-axis
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows
	 */
	public Image(final int resolutionX, final int resolutionY) {
		this(resolutionX, resolutionY, Color4D.WHITE);
	}
	
	/**
	 * Constructs a new {@code Image} instance filled with {@code color}.
	 * <p>
	 * If either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Image(resolutionX, resolutionY, color, DataFactory.forColorARGB());
	 * }
	 * </pre>
	 * 
	 * @param resolutionX the resolution along the X-axis
	 * @param resolutionY the resolution along the Y-axis
	 * @param color the {@link Color4D} to fill with
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image(final int resolutionX, final int resolutionY, final Color4D color) {
		this(resolutionX, resolutionY, color, DataFactory.forColorARGB());
	}
	
	/**
	 * Constructs a new {@code Image} instance filled with {@code color} using {@code dataFactory}.
	 * <p>
	 * If either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If either {@code color} or {@code dataFactory} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param resolutionX the resolution along the X-axis
	 * @param resolutionY the resolution along the Y-axis
	 * @param color the {@link Color4D} to fill with
	 * @param dataFactory a {@link DataFactory} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows
	 * @throws NullPointerException thrown if, and only if, either {@code color} or {@code dataFactory} are {@code null}
	 */
	public Image(final int resolutionX, final int resolutionY, final Color4D color, final DataFactory dataFactory) {
		this.data = dataFactory.create(resolutionX, resolutionY, color);
	}
	
	/**
	 * Constructs a new {@code Image} instance filled with {@code color}.
	 * <p>
	 * If either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Image(resolutionX, resolutionY, color, DataFactory.forColorARGB());
	 * }
	 * </pre>
	 * 
	 * @param resolutionX the resolution along the X-axis
	 * @param resolutionY the resolution along the Y-axis
	 * @param color the {@link Color4F} to fill with
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image(final int resolutionX, final int resolutionY, final Color4F color) {
		this(resolutionX, resolutionY, color, DataFactory.forColorARGB());
	}
	
	/**
	 * Constructs a new {@code Image} instance filled with {@code color} using {@code dataFactory}.
	 * <p>
	 * If either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If either {@code color} or {@code dataFactory} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param resolutionX the resolution along the X-axis
	 * @param resolutionY the resolution along the Y-axis
	 * @param color the {@link Color4F} to fill with
	 * @param dataFactory a {@link DataFactory} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows
	 * @throws NullPointerException thrown if, and only if, either {@code color} or {@code dataFactory} are {@code null}
	 */
	public Image(final int resolutionX, final int resolutionY, final Color4F color, final DataFactory dataFactory) {
		this.data = dataFactory.create(resolutionX, resolutionY, color);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code BufferedImage} representation of this {@code Image} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.toBufferedImage(false);
	 * }
	 * </pre>
	 * 
	 * @return a {@code BufferedImage} representation of this {@code Image} instance
	 */
	public BufferedImage toBufferedImage() {
		return toBufferedImage(false);
	}
	
	/**
	 * Returns a {@code BufferedImage} representation of this {@code Image} instance.
	 * 
	 * @param isRGB {@code true} if, and only if, {@code BufferedImage.TYPE_INT_RGB} should be used instead of {@code BufferedImage.TYPE_INT_ARGB}, {@code false} otherwise
	 * @return a {@code BufferedImage} representation of this {@code Image} instance
	 */
	public BufferedImage toBufferedImage(final boolean isRGB) {
		return this.data.toBufferedImage(isRGB);
	}
	
	/**
	 * Returns the {@link Color3D} at {@code point} in this {@code Image} instance.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code point.x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color3D.BLACK} will be returned.
	 * <p>
	 * If {@code point.y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color3D.BLACK} will be returned.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor3D(point.x, point.y);
	 * }
	 * </pre>
	 * 
	 * @param point a {@link Point2I} instance that contains the X- and Y-components of the pixel
	 * @return the {@code Color3D} at {@code point} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Color3D getColor3D(final Point2I point) {
		return getColor3D(point.x, point.y);
	}
	
	/**
	 * Returns the {@link Color3D} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionX()}, {@code Color3D.BLACK} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionY()}, {@code Color3D.BLACK} will be returned.
	 * <p>
	 * If both {@code x} and {@code y} are equal to mathematical integers, this method is equivalent to {@link #getColor3D(int, int)}. Otherwise, bilinear interpolation will be performed on the closest pixels.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color3D} at {@code x} and {@code y} in this {@code Image} instance
	 */
	public Color3D getColor3D(final double x, final double y) {
		return this.data.getColor3D(x, y);
	}
	
	/**
	 * Returns the {@link Color3D} at {@code index} in this {@code Image} instance.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code image.getResolution()}, {@code Color3D.BLACK} will be returned.
	 * 
	 * @param index the index of the pixel
	 * @return the {@code Color3D} at {@code index} in this {@code Image} instance
	 */
	public Color3D getColor3D(final int index) {
		return this.data.getColor3D(index);
	}
	
	/**
	 * Returns the {@link Color3D} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color3D.BLACK} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color3D.BLACK} will be returned.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color3D} at {@code x} and {@code y} in this {@code Image} instance
	 */
	public Color3D getColor3D(final int x, final int y) {
		return this.data.getColor3D(x, y);
	}
	
	/**
	 * Returns the {@link Color3F} at {@code point} in this {@code Image} instance.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code point.x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color3F.BLACK} will be returned.
	 * <p>
	 * If {@code point.y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color3F.BLACK} will be returned.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor3F(point.x, point.y);
	 * }
	 * </pre>
	 * 
	 * @param point a {@link Point2I} instance that contains the X- and Y-components of the pixel
	 * @return the {@code Color3F} at {@code point} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Color3F getColor3F(final Point2I point) {
		return getColor3F(point.x, point.y);
	}
	
	/**
	 * Returns the {@link Color3F} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0.0F} or greater than or equal to {@code image.getResolutionX()}, {@code Color3F.BLACK} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0.0F} or greater than or equal to {@code image.getResolutionY()}, {@code Color3F.BLACK} will be returned.
	 * <p>
	 * If both {@code x} and {@code y} are equal to mathematical integers, this method is equivalent to {@link #getColor3F(int, int)}. Otherwise, bilinear interpolation will be performed on the closest pixels.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color3F} at {@code x} and {@code y} in this {@code Image} instance
	 */
	public Color3F getColor3F(final float x, final float y) {
		return this.data.getColor3F(x, y);
	}
	
	/**
	 * Returns the {@link Color3F} at {@code index} in this {@code Image} instance.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code image.getResolution()}, {@code Color3F.BLACK} will be returned.
	 * 
	 * @param index the index of the pixel
	 * @return the {@code Color3F} at {@code index} in this {@code Image} instance
	 */
	public Color3F getColor3F(final int index) {
		return this.data.getColor3F(index);
	}
	
	/**
	 * Returns the {@link Color3F} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color3F.BLACK} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color3F.BLACK} will be returned.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color3F} at {@code x} and {@code y} in this {@code Image} instance
	 */
	public Color3F getColor3F(final int x, final int y) {
		return this.data.getColor3F(x, y);
	}
	
	/**
	 * Returns the {@link Color4D} at {@code point} in this {@code Image} instance.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code point.x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color4D.TRANSPARENT} will be returned.
	 * <p>
	 * If {@code point.y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color4D.TRANSPARENT} will be returned.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor4D(point.x, point.y);
	 * }
	 * </pre>
	 * 
	 * @param point a {@link Point2I} instance that contains the X- and Y-components of the pixel
	 * @return the {@code Color4D} at {@code point} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Color4D getColor4D(final Point2I point) {
		return getColor4D(point.x, point.y);
	}
	
	/**
	 * Returns the {@link Color4D} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionX()}, {@code Color4D.TRANSPARENT} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionY()}, {@code Color4D.TRANSPARENT} will be returned.
	 * <p>
	 * If both {@code x} and {@code y} are equal to mathematical integers, this method is equivalent to {@link #getColor4D(int, int)}. Otherwise, bilinear interpolation will be performed on the closest pixels.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color4D} at {@code x} and {@code y} in this {@code Image} instance
	 */
	public Color4D getColor4D(final double x, final double y) {
		return this.data.getColor4D(x, y);
	}
	
	/**
	 * Returns the {@link Color4D} at {@code index} in this {@code Image} instance.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code image.getResolution()}, {@code Color4D.TRANSPARENT} will be returned.
	 * 
	 * @param index the index of the pixel
	 * @return the {@code Color4D} at {@code index} in this {@code Image} instance
	 */
	public Color4D getColor4D(final int index) {
		return this.data.getColor4D(index);
	}
	
	/**
	 * Returns the {@link Color4D} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color4D.TRANSPARENT} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color4D.TRANSPARENT} will be returned.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color4D} at {@code x} and {@code y} in this {@code Image} instance
	 */
	public Color4D getColor4D(final int x, final int y) {
		return this.data.getColor4D(x, y);
	}
	
	/**
	 * Returns the {@link Color4F} at {@code point} in this {@code Image} instance.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code point.x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color4F.TRANSPARENT} will be returned.
	 * <p>
	 * If {@code point.y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color4F.TRANSPARENT} will be returned.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor4F(point.x, point.y);
	 * }
	 * </pre>
	 * 
	 * @param point a {@link Point2I} instance that contains the X- and Y-components of the pixel
	 * @return the {@code Color4F} at {@code point} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Color4F getColor4F(final Point2I point) {
		return getColor4F(point.x, point.y);
	}
	
	/**
	 * Returns the {@link Color4F} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0.0F} or greater than or equal to {@code image.getResolutionX()}, {@code Color4F.TRANSPARENT} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0.0F} or greater than or equal to {@code image.getResolutionY()}, {@code Color4F.TRANSPARENT} will be returned.
	 * <p>
	 * If both {@code x} and {@code y} are equal to mathematical integers, this method is equivalent to {@link #getColor4F(int, int)}. Otherwise, bilinear interpolation will be performed on the closest pixels.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color4F} at {@code x} and {@code y} in this {@code Image} instance
	 */
	public Color4F getColor4F(final float x, final float y) {
		return this.data.getColor4F(x, y);
	}
	
	/**
	 * Returns the {@link Color4F} at {@code index} in this {@code Image} instance.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code image.getResolution()}, {@code Color4F.TRANSPARENT} will be returned.
	 * 
	 * @param index the index of the pixel
	 * @return the {@code Color4F} at {@code index} in this {@code Image} instance
	 */
	public Color4F getColor4F(final int index) {
		return this.data.getColor4F(index);
	}
	
	/**
	 * Returns the {@link Color4F} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color4F.TRANSPARENT} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color4F.TRANSPARENT} will be returned.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color4F} at {@code x} and {@code y} in this {@code Image} instance
	 */
	public Color4F getColor4F(final int x, final int y) {
		return this.data.getColor4F(x, y);
	}
	
	/**
	 * Applies {@code convolutionKernel} to all pixels in this {@code Image} instance.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code convolutionKernel} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.convolve(convolutionKernel, (color, x, y) -> true);
	 * }
	 * </pre>
	 * 
	 * @param convolutionKernel the {@link ConvolutionKernelND} instance to apply
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code convolutionKernel} is {@code null}
	 */
	public Image convolve(final ConvolutionKernelND convolutionKernel) {
		return convolve(convolutionKernel, (color, x, y) -> true);
	}
	
	/**
	 * Applies {@code convolutionKernel} to all pixels in this {@code Image} instance that are accepted by {@code pixelFilter}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code convolutionKernel} or {@code pixelFilter} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param convolutionKernel the {@link ConvolutionKernelND} instance to apply
	 * @param pixelFilter a {@link Color4DPixelFilter} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code convolutionKernel} or {@code pixelFilter} are {@code null}
	 */
	public Image convolve(final ConvolutionKernelND convolutionKernel, final Color4DPixelFilter pixelFilter) {
		Objects.requireNonNull(convolutionKernel, "convolutionKernel == null");
		Objects.requireNonNull(pixelFilter, "pixelFilter == null");
		
		final int[] indices = doFilter(pixelFilter);
		
		this.data.convolve(convolutionKernel, indices);
		
		return this;
	}
	
	/**
	 * Applies {@code convolutionKernel} to all pixels in this {@code Image} instance.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code convolutionKernel} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.convolve(convolutionKernel, (color, x, y) -> true);
	 * }
	 * </pre>
	 * 
	 * @param convolutionKernel the {@link ConvolutionKernelNF} instance to apply
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code convolutionKernel} is {@code null}
	 */
	public Image convolve(final ConvolutionKernelNF convolutionKernel) {
		return convolve(convolutionKernel, (color, x, y) -> true);
	}
	
	/**
	 * Applies {@code convolutionKernel} to all pixels in this {@code Image} instance that are accepted by {@code pixelFilter}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code convolutionKernel} or {@code pixelFilter} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param convolutionKernel the {@link ConvolutionKernelNF} instance to apply
	 * @param pixelFilter a {@link Color4FPixelFilter} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code convolutionKernel} or {@code pixelFilter} are {@code null}
	 */
	public Image convolve(final ConvolutionKernelNF convolutionKernel, final Color4FPixelFilter pixelFilter) {
		Objects.requireNonNull(convolutionKernel, "convolutionKernel == null");
		Objects.requireNonNull(pixelFilter, "pixelFilter == null");
		
		final int[] indices = doFilter(pixelFilter);
		
		this.data.convolve(convolutionKernel, indices);
		
		return this;
	}
	
	/**
	 * Returns a copy of this {@code Image} instance.
	 * 
	 * @return a copy of this {@code Image} instance
	 */
	public Image copy() {
		return new Image(this);
	}
	
	/**
	 * Draws the contents drawn to the supplied {@code Graphics2D} instance into this {@code Image} instance.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code graphics2DConsumer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param graphics2DConsumer a {@code Consumer} that accepts a {@code Graphics2D} instance
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code graphics2DConsumer} is {@code null}
	 */
	public Image draw(final Consumer<Graphics2D> graphics2DConsumer) {
		Objects.requireNonNull(graphics2DConsumer, "graphics2DConsumer == null");
		
		this.data.draw(graphics2DConsumer);
		
		return this;
	}
	
	/**
	 * Draws {@code shape} to this {@code Image} instance with {@link Color4D} instances returned by {@code pixelOperator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param shape the {@link Shape2I} to draw
	 * @param pixelOperator a {@link Color4DPixelOperator} that returns {@code Color4D} instances to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image drawShape(final Shape2I shape, final Color4DPixelOperator pixelOperator) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(pixelOperator, "pixelOperator == null");
		
		this.data.changeBegin();
		
		shape.findPointsOfIntersection(getBounds(), true).forEach(point -> setColor4D(pixelOperator.apply(getColor4D(point), point.x, point.y), point));
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Draws {@code shape} to this {@code Image} instance with {@code color} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code color} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is essentially equivalent to the following:
	 * <pre>
	 * {@code
	 * image.drawShape(shape, (Color4D currentColor, int x, int y) -> color);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} to draw
	 * @param color the {@link Color4D} to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code color} are {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image drawShape(final Shape2I shape, final Color4D color) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(color, "color == null");
		
		return drawShape(shape, (final Color4D currentColor, final int x, final int y) -> color);
	}
	
	/**
	 * Draws {@code shape} to this {@code Image} instance with {@link Color4F} instances returned by {@code pixelOperator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param shape the {@link Shape2I} to draw
	 * @param pixelOperator a {@link Color4FPixelOperator} that returns {@code Color4F} instances to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image drawShape(final Shape2I shape, final Color4FPixelOperator pixelOperator) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(pixelOperator, "pixelOperator == null");
		
		this.data.changeBegin();
		
		shape.findPointsOfIntersection(getBounds(), true).forEach(point -> setColor4F(pixelOperator.apply(getColor4F(point), point.x, point.y), point));
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Draws {@code shape} to this {@code Image} instance with {@code color} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code color} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is essentially equivalent to the following:
	 * <pre>
	 * {@code
	 * image.drawShape(shape, (Color4F currentColor, int x, int y) -> color);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} to draw
	 * @param color the {@link Color4F} to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code color} are {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image drawShape(final Shape2I shape, final Color4F color) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(color, "color == null");
		
		return drawShape(shape, (final Color4F currentColor, final int x, final int y) -> color);
	}
	
	/**
	 * Draws everything except for {@code shape} in this {@code Image} instance with {@code Color4D.BLACK} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code shape} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.drawShapeComplementColor4D(shape, Color4D.BLACK);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} not to draw
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code shape} is {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image drawShapeComplementColor4D(final Shape2I shape) {
		return drawShapeComplementColor4D(shape, Color4D.BLACK);
	}
	
	/**
	 * Draws everything except for {@code shape} in this {@code Image} instance with {@link Color4D} instances returned by {@code operator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code operator} are {@code null} or {@code operator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param shape the {@link Shape2I} not to draw
	 * @param operator a {@code BiFunction} that returns {@code Color4D} instances to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code operator} are {@code null} or {@code operator} returns {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image drawShapeComplementColor4D(final Shape2I shape, final BiFunction<Color4D, Point2I, Color4D> operator) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(operator, "operator == null");
		
		this.data.changeBegin();
		
		shape.findPointsOfComplement(getBounds(), true).forEach(point -> setColor4D(operator.apply(getColor4D(point), point), point));
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Draws everything except for {@code shape} in this {@code Image} instance with {@code color} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code color} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is essentially equivalent to the following:
	 * <pre>
	 * {@code
	 * image.drawShapeComplementColor4D(shape, (currentColor, currentPoint) -> color);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} not to draw
	 * @param color the {@link Color4D} to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code color} are {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image drawShapeComplementColor4D(final Shape2I shape, final Color4D color) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(color, "color == null");
		
		return drawShapeComplementColor4D(shape, (currentColor, currentPoint) -> color);
	}
	
	/**
	 * Draws everything except for {@code shape} in this {@code Image} instance with {@code Color4F.BLACK} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code shape} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.drawShapeComplementColor4F(shape, Color4F.BLACK);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} not to draw
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code shape} is {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image drawShapeComplementColor4F(final Shape2I shape) {
		return drawShapeComplementColor4F(shape, Color4F.BLACK);
	}
	
	/**
	 * Draws everything except for {@code shape} in this {@code Image} instance with {@link Color4F} instances returned by {@code operator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code operator} are {@code null} or {@code operator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param shape the {@link Shape2I} not to draw
	 * @param operator a {@code BiFunction} that returns {@code Color4F} instances to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code operator} are {@code null} or {@code operator} returns {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image drawShapeComplementColor4F(final Shape2I shape, final BiFunction<Color4F, Point2I, Color4F> operator) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(operator, "operator == null");
		
		this.data.changeBegin();
		
		shape.findPointsOfComplement(getBounds(), true).forEach(point -> setColor4F(operator.apply(getColor4F(point), point), point));
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Draws everything except for {@code shape} in this {@code Image} instance with {@code color} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code color} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is essentially equivalent to the following:
	 * <pre>
	 * {@code
	 * image.drawShapeComplementColor4F(shape, (currentColor, currentPoint) -> color);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} not to draw
	 * @param color the {@link Color4F} to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code color} are {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image drawShapeComplementColor4F(final Shape2I shape, final Color4F color) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(color, "color == null");
		
		return drawShapeComplementColor4F(shape, (currentColor, currentPoint) -> color);
	}
	
	/**
	 * Fills all pixels in this {@code Image} instance in the colors provided by {@code operator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code operator} is {@code null} or returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The {@code BiFunction} instance, {@code operator}, is supplied with a {@link Color4D} instance and a {@link Point2I} instance. The {@code Color4D} instance represents the current color and the {@code Point2I} instance represents the location of the current pixel. It returns a {@code Color4D} instance that represents the filled color.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillColor4D(operator, (color, point) -> true);
	 * }
	 * </pre>
	 * 
	 * @param operator a {@code BiFunction} instance that returns a {@code Color4D} instance for each pixel affected
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code operator} is {@code null} or returns {@code null}
	 */
	public Image fillColor4D(final BiFunction<Color4D, Point2I, Color4D> operator) {
		return fillColor4D(operator, (color, point) -> true);
	}
	
	/**
	 * Fills all pixels in this {@code Image} instance that are accepted by {@code filter} in the colors provided by {@code operator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code operator} or {@code filter} are {@code null} or {@code operator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The {@code BiFunction} instance, {@code operator}, is supplied with a {@link Color4D} instance and a {@link Point2I} instance. The {@code Color4D} instance represents the current color and the {@code Point2I} instance represents the location of the current pixel. It returns a {@code Color4D} instance that represents the filled color.
	 * <p>
	 * The {@code BiPredicate} instance, {@code filter}, is supplied with a {@code Color4D} instance and a {@code Point2I} instance. The {@code Color4D} instance represents the current color and the {@code Point2I} instance represents the location of the current pixel. It returns {@code true} if, and only if, the current pixel should be filled, {@code false} otherwise.
	 * 
	 * @param operator a {@code BiFunction} instance that returns a {@code Color4D} instance for each pixel affected
	 * @param filter a {@code BiPredicate} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code operator} or {@code filter} are {@code null} or {@code operator} returns {@code null}
	 */
	public Image fillColor4D(final BiFunction<Color4D, Point2I, Color4D> operator, final BiPredicate<Color4D, Point2I> filter) {
		Objects.requireNonNull(operator, "operator == null");
		Objects.requireNonNull(filter, "filter == null");
		
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		this.data.changeBegin();
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++) {
				final Point2I point = new Point2I(x, y);
				
				final Color4D oldColor = getColor4D(x, y);
				
				if(filter.test(oldColor, point)) {
					final Color4D newColor = Objects.requireNonNull(operator.apply(oldColor, point));
					
					this.data.setColor4D(newColor, x, y);
				}
			}
		}
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills all pixels in this {@code Image} instance in the colors provided by {@code operator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code operator} is {@code null} or returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The {@code BiFunction} instance, {@code operator}, is supplied with a {@link Color4F} instance and a {@link Point2I} instance. The {@code Color4F} instance represents the current color and the {@code Point2I} instance represents the location of the current pixel. It returns a {@code Color4F} instance that represents the filled color.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillColor4F(operator, (color, point) -> true);
	 * }
	 * </pre>
	 * 
	 * @param operator a {@code BiFunction} instance that returns a {@code Color4F} instance for each pixel affected
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code operator} is {@code null} or returns {@code null}
	 */
	public Image fillColor4F(final BiFunction<Color4F, Point2I, Color4F> operator) {
		return fillColor4F(operator, (color, point) -> true);
	}
	
	/**
	 * Fills all pixels in this {@code Image} instance that are accepted by {@code filter} in the colors provided by {@code operator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code operator} or {@code filter} are {@code null} or {@code operator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The {@code BiFunction} instance, {@code operator}, is supplied with a {@link Color4F} instance and a {@link Point2I} instance. The {@code Color4F} instance represents the current color and the {@code Point2I} instance represents the location of the current pixel. It returns a {@code Color4F} instance that represents the filled color.
	 * <p>
	 * The {@code BiPredicate} instance, {@code filter}, is supplied with a {@code Color4F} instance and a {@code Point2I} instance. The {@code Color4F} instance represents the current color and the {@code Point2I} instance represents the location of the current pixel. It returns {@code true} if, and only if, the current pixel should be filled, {@code false} otherwise.
	 * 
	 * @param operator a {@code BiFunction} instance that returns a {@code Color4F} instance for each pixel affected
	 * @param filter a {@code BiPredicate} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code operator} or {@code filter} are {@code null} or {@code operator} returns {@code null}
	 */
	public Image fillColor4F(final BiFunction<Color4F, Point2I, Color4F> operator, final BiPredicate<Color4F, Point2I> filter) {
		Objects.requireNonNull(operator, "operator == null");
		Objects.requireNonNull(filter, "filter == null");
		
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		this.data.changeBegin();
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++) {
				final Point2I point = new Point2I(x, y);
				
				final Color4F oldColor = getColor4F(x, y);
				
				if(filter.test(oldColor, point)) {
					final Color4F newColor = Objects.requireNonNull(operator.apply(oldColor, point));
					
					this.data.setColor4F(newColor, x, y);
				}
			}
		}
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills all pixels in this {@code Image} instance in the colors provided by {@code operator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code operator} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillColorARGB(operator, (colorARGB, x, y) -> true);
	 * }
	 * </pre>
	 * 
	 * @param operator an {@code IntTernaryOperator} instance that returns a color for each pixel affected
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code operator} is {@code null}
	 */
	public Image fillColorARGB(final IntTernaryOperator operator) {
		return fillColorARGB(operator, (colorARGB, x, y) -> true);
	}
	
	/**
	 * Fills all pixels in this {@code Image} instance that are accepted by {@code filter} in the colors provided by {@code operator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code operator} or {@code filter} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param operator an {@code IntTernaryOperator} instance that returns a color for each pixel affected
	 * @param filter an {@code IntTriPredicate} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code operator} or {@code filter} are {@code null}
	 */
	public Image fillColorARGB(final IntTernaryOperator operator, final IntTriPredicate filter) {
		Objects.requireNonNull(operator, "operator == null");
		Objects.requireNonNull(filter, "filter == null");
		
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		this.data.changeBegin();
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++) {
				final int oldColorARGB = getColorARGB(x, y);
				
				if(filter.test(oldColorARGB, x, y)) {
					final int newColorARGB = operator.applyAsInt(oldColorARGB, x, y);
					
					this.data.setColorARGB(newColorARGB, x, y);
				}
			}
		}
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills a gradient in this {@code Image} instance.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillGradientColor3D(Color3D.BLACK, Color3D.RED, Color3D.GREEN, Color3D.YELLOW);
	 * }
	 * </pre>
	 * 
	 * @return this {@code Image} instance
	 */
//	TODO: Add Unit Tests!
	public Image fillGradientColor3D() {
		return fillGradientColor3D(Color3D.BLACK, Color3D.RED, Color3D.GREEN, Color3D.YELLOW);
	}
	
	/**
	 * Fills a gradient in this {@code Image} instance.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code a}, {@code b}, {@code c} or {@code d} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a the {@link Color3D} instance in the top left corner
	 * @param b the {@code Color3D} instance in the top right corner
	 * @param c the {@code Color3D} instance in the bottom left corner
	 * @param d the {@code Color3D} instance in the bottom right corner
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b}, {@code c} or {@code d} are {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillGradientColor3D(final Color3D a, final Color3D b, final Color3D c, final Color3D d) {
		Objects.requireNonNull(a, "a == null");
		Objects.requireNonNull(b, "b == null");
		Objects.requireNonNull(c, "c == null");
		Objects.requireNonNull(d, "d == null");
		
		final float resolutionX = getResolutionX();
		final float resolutionY = getResolutionY();
		
		return fillColor4D((color, point) -> {
			final double tX = 1.0D / resolutionX * point.x;
			final double tY = 1.0D / resolutionY * point.y;
			
			return new Color4D(Color3D.blend(a, b, c, d, tX, tY));
		});
	}
	
	/**
	 * Fills {@code sourceImage} in this {@code Image} instance.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code sourceImage} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillImageColor4D(sourceImage, sourceImage.getBounds());
	 * }
	 * </pre>
	 * 
	 * @param sourceImage the {@code Image} to fill
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code sourceImage} is {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillImageColor4D(final Image sourceImage) {
		return fillImageColor4D(sourceImage, sourceImage.getBounds());
	}
	
	/**
	 * Fills {@code sourceImage} in this {@code Image} instance.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code sourceImage} or {@code targetPosition} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillImageColor4D(sourceImage, targetPosition, (sourceColor, targetColor, targetPoint) -> Color4D.blendOver(sourceColor, targetColor));
	 * }
	 * </pre>
	 * 
	 * @param sourceImage the {@code Image} to fill
	 * @param targetPosition a {@link Point2I} that represents the position in this {@code Image} instance to start filling {@code sourceImage}
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code sourceImage} or {@code targetPosition} are {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillImageColor4D(final Image sourceImage, final Point2I targetPosition) {
		return fillImageColor4D(sourceImage, targetPosition, (sourceColor, targetColor, targetPoint) -> Color4D.blendOver(sourceColor, targetColor));
	}
	
	/**
	 * Fills {@code sourceImage} in this {@code Image} instance.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code sourceImage}, {@code targetPosition} or {@code operator} are {@code null} or {@code operator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param sourceImage the {@code Image} to fill
	 * @param targetPosition a {@link Point2I} that represents the position in this {@code Image} instance to start filling {@code sourceImage}
	 * @param operator a {@code TriFunction} that returns {@code Color4D} instances to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code sourceImage}, {@code targetPosition} or {@code operator} are {@code null} or {@code operator} returns {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillImageColor4D(final Image sourceImage, final Point2I targetPosition, final TriFunction<Color4D, Color4D, Point2I, Color4D> operator) {
		final Rectangle2I sourceBounds = sourceImage.getBounds();
		final Rectangle2I targetBounds = new Rectangle2I(targetPosition, new Point2I(targetPosition.x + (sourceBounds.getC().x - sourceBounds.getA().x), targetPosition.y + (sourceBounds.getC().y - sourceBounds.getA().y)));
		
		return fillImageColor4D(sourceImage, sourceBounds, targetBounds, operator);
	}
	
	/**
	 * Fills {@code sourceImage} in this {@code Image} instance.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code sourceImage} or {@code sourceBounds} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillImageColor4D(sourceImage, sourceBounds, image.getBounds());
	 * }
	 * </pre>
	 * 
	 * @param sourceImage the {@code Image} to fill
	 * @param sourceBounds a {@link Rectangle2I} that represents the bounds of the region in {@code sourceImage} to use
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code sourceImage} or {@code sourceBounds} are {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillImageColor4D(final Image sourceImage, final Rectangle2I sourceBounds) {
		return fillImageColor4D(sourceImage, sourceBounds, getBounds());
	}
	
	/**
	 * Fills {@code sourceImage} in this {@code Image} instance.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code sourceImage}, {@code sourceBounds} or {@code targetBounds} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillImageColor4D(sourceImage, sourceBounds, targetBounds, (sourceColor, targetColor, targetPoint) -> Color4D.blendOver(sourceColor, targetColor));
	 * }
	 * </pre>
	 * 
	 * @param sourceImage the {@code Image} to fill
	 * @param sourceBounds a {@link Rectangle2I} that represents the bounds of the region in {@code sourceImage} to use
	 * @param targetBounds a {@code Rectangle2I} that represents the bounds of the region in this {@code Image} instance to use
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code sourceImage}, {@code sourceBounds} or {@code targetBounds} are {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillImageColor4D(final Image sourceImage, final Rectangle2I sourceBounds, final Rectangle2I targetBounds) {
		return fillImageColor4D(sourceImage, sourceBounds, targetBounds, (sourceColor, targetColor, targetPoint) -> Color4D.blendOver(sourceColor, targetColor));
	}
	
	/**
	 * Fills {@code sourceImage} in this {@code Image} instance with {@link Color4D} instances returned by {@code operator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code sourceImage}, {@code sourceBounds}, {@code targetBounds} or {@code operator} are {@code null} or {@code operator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param sourceImage the {@code Image} to fill
	 * @param sourceBounds a {@link Rectangle2I} that represents the bounds of the region in {@code sourceImage} to use
	 * @param targetBounds a {@code Rectangle2I} that represents the bounds of the region in this {@code Image} instance to use
	 * @param operator a {@code TriFunction} that returns {@code Color4D} instances to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code sourceImage}, {@code sourceBounds}, {@code targetBounds} or {@code operator} are {@code null} or {@code operator} returns {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillImageColor4D(final Image sourceImage, final Rectangle2I sourceBounds, final Rectangle2I targetBounds, final TriFunction<Color4D, Color4D, Point2I, Color4D> operator) {
		Objects.requireNonNull(sourceImage, "sourceImage == null");
		Objects.requireNonNull(sourceBounds, "sourceBounds == null");
		Objects.requireNonNull(targetBounds, "targetBounds == null");
		Objects.requireNonNull(operator, "operator == null");
		
		this.data.changeBegin();
		
		final Image targetImage = this;
		
		final int sourceMinimumX = sourceBounds.getA().x;
		final int sourceMinimumY = sourceBounds.getA().y;
		final int sourceMaximumX = sourceBounds.getC().x;
		final int sourceMaximumY = sourceBounds.getC().y;
		final int targetMinimumX = targetBounds.getA().x;
		final int targetMinimumY = targetBounds.getA().y;
		final int targetMaximumX = targetBounds.getC().x;
		final int targetMaximumY = targetBounds.getC().y;
		
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		for(int sourceY = sourceMinimumY, targetY = targetMinimumY; sourceY < sourceMaximumY && targetY < targetMaximumY; sourceY++, targetY++) {
			for(int sourceX = sourceMinimumX, targetX = targetMinimumX; sourceX < sourceMaximumX && targetX < targetMaximumX; sourceX++, targetX++) {
				if(targetX >= 0 && targetX < resolutionX && targetY >= 0 && targetY < resolutionY) {
					final Color4D sourceColor = sourceImage.getColor4D(sourceX, sourceY);
					final Color4D targetColor = targetImage.getColor4D(targetX, targetY);
					
					final Color4D color = Objects.requireNonNull(operator.apply(sourceColor, targetColor, new Point2I(targetX, targetY)));
					
					targetImage.setColor4D(color, targetX, targetY);
				}
			}
		}
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code point} in the colors provided by {@code operator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code point} or {@code operator} are {@code null} or {@code operator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The {@code BiFunction} instance, {@code operator}, is supplied with a {@link Color4D} instance and a {@link Point2I} instance. The {@code Color4D} instance represents the current color and the {@code Point2I} instance represents the location of the current pixel. It returns a {@code Color4D} instance that represents the filled color.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRegionColor4D(point, operator, (currentColor, currentPoint) -> true);
	 * }
	 * </pre>
	 * 
	 * @param point a {@code Point2I} instance that contains the X- and Y-components of the pixel to start at
	 * @param operator a {@code BiFunction} instance that returns a {@code Color4D} instance for each pixel affected
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code point} or {@code operator} are {@code null} or {@code operator} returns {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillRegionColor4D(final Point2I point, final BiFunction<Color4D, Point2I, Color4D> operator) {
		return fillRegionColor4D(point, operator, (currentColor, currentPoint) -> true);
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code point} and accepted by {@code filter} in the colors provided by {@code operator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code point}, {@code operator} or {@code filter} are {@code null} or {@code operator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The {@code BiFunction} instance, {@code operator}, is supplied with a {@link Color4D} instance and a {@link Point2I} instance. The {@code Color4D} instance represents the current color and the {@code Point2I} instance represents the location of the current pixel. It returns a {@code Color4D} instance that represents the filled color.
	 * <p>
	 * The {@code BiPredicate} instance, {@code filter}, is supplied with a {@code Color4D} instance and a {@code Point2I} instance. The {@code Color4D} instance represents the current color and the {@code Point2I} instance represents the location of the current pixel. It returns {@code true} if, and only if, the current pixel should be filled, {@code false} otherwise.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRegionColor4D(point.x, point.y, operator, filter);
	 * }
	 * </pre>
	 * 
	 * @param point a {@code Point2I} instance that contains the X- and Y-components of the pixel to start at
	 * @param operator a {@code BiFunction} instance that returns a {@code Color4D} instance for each pixel affected
	 * @param filter a {@code BiPredicate} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code point}, {@code operator} or {@code filter} are {@code null} or {@code operator} returns {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillRegionColor4D(final Point2I point, final BiFunction<Color4D, Point2I, Color4D> operator, final BiPredicate<Color4D, Point2I> filter) {
		return fillRegionColor4D(point.x, point.y, operator, filter);
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code x} and {@code y} in the colors provided by {@code operator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code operator} is {@code null} or returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The {@code BiFunction} instance, {@code operator}, is supplied with a {@link Color4D} instance and a {@link Point2I} instance. The {@code Color4D} instance represents the current color and the {@code Point2I} instance represents the location of the current pixel. It returns a {@code Color4D} instance that represents the filled color.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRegionColor4D(x, y, operator, (color, point) -> true);
	 * }
	 * </pre>
	 * 
	 * @param x the X-component of the pixel to start at
	 * @param y the Y-component of the pixel to start at
	 * @param operator a {@code BiFunction} instance that returns a {@code Color4D} instance for each pixel affected
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code operator} is {@code null} or returns {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillRegionColor4D(final int x, final int y, final BiFunction<Color4D, Point2I, Color4D> operator) {
		return fillRegionColor4D(x, y, operator, (color, point) -> true);
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code x} and {@code y} and accepted by {@code filter} in the colors provided by {@code operator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code operator} or {@code filter} are {@code null} or {@code operator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The {@code BiFunction} instance, {@code operator}, is supplied with a {@link Color4D} instance and a {@link Point2I} instance. The {@code Color4D} instance represents the current color and the {@code Point2I} instance represents the location of the current pixel. It returns a {@code Color4D} instance that represents the filled color.
	 * <p>
	 * The {@code BiPredicate} instance, {@code filter}, is supplied with a {@code Color4D} instance and a {@code Point2I} instance. The {@code Color4D} instance represents the current color and the {@code Point2I} instance represents the location of the current pixel. It returns {@code true} if, and only if, the current pixel should be filled, {@code false} otherwise.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * 
	 * @param x the X-component of the pixel to start at
	 * @param y the Y-component of the pixel to start at
	 * @param operator a {@code BiFunction} instance that returns a {@code Color4D} instance for each pixel affected
	 * @param filter a {@code BiPredicate} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code operator} or {@code filter} are {@code null} or {@code operator} returns {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillRegionColor4D(final int x, final int y, final BiFunction<Color4D, Point2I, Color4D> operator, final BiPredicate<Color4D, Point2I> filter) {
		this.data.changeBegin();
		
		doFillRegionColor4D(x, y, operator, filter, getColor4D(x, y));
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code point} in the colors provided by {@code operator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code point} or {@code operator} are {@code null} or {@code operator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The {@code BiFunction} instance, {@code operator}, is supplied with a {@link Color4F} instance and a {@link Point2I} instance. The {@code Color4F} instance represents the current color and the {@code Point2I} instance represents the location of the current pixel. It returns a {@code Color4F} instance that represents the filled color.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRegionColor4F(point, operator, (currentColor, currentPoint) -> true);
	 * }
	 * </pre>
	 * 
	 * @param point a {@code Point2I} instance that contains the X- and Y-components of the pixel to start at
	 * @param operator a {@code BiFunction} instance that returns a {@code Color4F} instance for each pixel affected
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code point} or {@code operator} are {@code null} or {@code operator} returns {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillRegionColor4F(final Point2I point, final BiFunction<Color4F, Point2I, Color4F> operator) {
		return fillRegionColor4F(point, operator, (currentColor, currentPoint) -> true);
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code point} and accepted by {@code filter} in the colors provided by {@code operator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code point}, {@code operator} or {@code filter} are {@code null} or {@code operator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The {@code BiFunction} instance, {@code operator}, is supplied with a {@link Color4F} instance and a {@link Point2I} instance. The {@code Color4F} instance represents the current color and the {@code Point2I} instance represents the location of the current pixel. It returns a {@code Color4F} instance that represents the filled color.
	 * <p>
	 * The {@code BiPredicate} instance, {@code filter}, is supplied with a {@code Color4F} instance and a {@code Point2I} instance. The {@code Color4F} instance represents the current color and the {@code Point2I} instance represents the location of the current pixel. It returns {@code true} if, and only if, the current pixel should be filled, {@code false} otherwise.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRegionColor4F(point.x, point.y, operator, filter);
	 * }
	 * </pre>
	 * 
	 * @param point a {@code Point2I} instance that contains the X- and Y-components of the pixel to start at
	 * @param operator a {@code BiFunction} instance that returns a {@code Color4F} instance for each pixel affected
	 * @param filter a {@code BiPredicate} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code point}, {@code operator} or {@code filter} are {@code null} or {@code operator} returns {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillRegionColor4F(final Point2I point, final BiFunction<Color4F, Point2I, Color4F> operator, final BiPredicate<Color4F, Point2I> filter) {
		return fillRegionColor4F(point.x, point.y, operator, filter);
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code x} and {@code y} in the colors provided by {@code operator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code operator} is {@code null} or returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The {@code BiFunction} instance, {@code operator}, is supplied with a {@link Color4F} instance and a {@link Point2I} instance. The {@code Color4F} instance represents the current color and the {@code Point2I} instance represents the location of the current pixel. It returns a {@code Color4F} instance that represents the filled color.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRegionColor4F(x, y, operator, (color, point) -> true);
	 * }
	 * </pre>
	 * 
	 * @param x the X-component of the pixel to start at
	 * @param y the Y-component of the pixel to start at
	 * @param operator a {@code BiFunction} instance that returns a {@code Color4F} instance for each pixel affected
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code operator} is {@code null} or returns {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillRegionColor4F(final int x, final int y, final BiFunction<Color4F, Point2I, Color4F> operator) {
		return fillRegionColor4F(x, y, operator, (color, point) -> true);
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code x} and {@code y} and accepted by {@code filter} in the colors provided by {@code operator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code operator} or {@code filter} are {@code null} or {@code operator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The {@code BiFunction} instance, {@code operator}, is supplied with a {@link Color4F} instance and a {@link Point2I} instance. The {@code Color4F} instance represents the current color and the {@code Point2I} instance represents the location of the current pixel. It returns a {@code Color4F} instance that represents the filled color.
	 * <p>
	 * The {@code BiPredicate} instance, {@code filter}, is supplied with a {@code Color4F} instance and a {@code Point2I} instance. The {@code Color4F} instance represents the current color and the {@code Point2I} instance represents the location of the current pixel. It returns {@code true} if, and only if, the current pixel should be filled, {@code false} otherwise.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * 
	 * @param x the X-component of the pixel to start at
	 * @param y the Y-component of the pixel to start at
	 * @param operator a {@code BiFunction} instance that returns a {@code Color4F} instance for each pixel affected
	 * @param filter a {@code BiPredicate} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code operator} or {@code filter} are {@code null} or {@code operator} returns {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillRegionColor4F(final int x, final int y, final BiFunction<Color4F, Point2I, Color4F> operator, final BiPredicate<Color4F, Point2I> filter) {
		this.data.changeBegin();
		
		doFillRegionColor4F(x, y, operator, filter, getColor4F(x, y));
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code point} in the colors provided by {@code operator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code point} or {@code operator} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRegionColorARGB(point, operator, (colorARGB, x, y) -> true);
	 * }
	 * </pre>
	 * 
	 * @param point a {@code Point2I} instance that contains the X- and Y-components of the pixel to start at
	 * @param operator an {@code IntTernaryOperator} instance that returns a color for each pixel affected
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code point} or {@code operator} are {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillRegionColorARGB(final Point2I point, final IntTernaryOperator operator) {
		return fillRegionColorARGB(point, operator, (colorARGB, x, y) -> true);
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code point} and accepted by {@code filter} in the colors provided by {@code operator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code point}, {@code operator} or {@code filter} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRegionColorARGB(point.x, point.y, operator, filter);
	 * }
	 * </pre>
	 * 
	 * @param point a {@code Point2I} instance that contains the X- and Y-components of the pixel to start at
	 * @param operator an {@code IntTernaryOperator} instance that returns a color for each pixel affected
	 * @param filter an {@code IntTriPredicate} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code point}, {@code operator} or {@code filter} are {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillRegionColorARGB(final Point2I point, final IntTernaryOperator operator, final IntTriPredicate filter) {
		return fillRegionColorARGB(point.x, point.y, operator, filter);
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code x} and {@code y} in the colors provided by {@code operator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code operator} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRegionColorARGB(x, y, operator, (currentColorARGB, currentX, currentY) -> true);
	 * }
	 * </pre>
	 * 
	 * @param x the X-component of the pixel to start at
	 * @param y the Y-component of the pixel to start at
	 * @param operator an {@code IntTernaryOperator} instance that returns a color for each pixel affected
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code operator} is {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillRegionColorARGB(final int x, final int y, final IntTernaryOperator operator) {
		return fillRegionColorARGB(x, y, operator, (currentColorARGB, currentX, currentY) -> true);
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code x} and {@code y} and accepted by {@code filter} in the colors provided by {@code operator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code operator} or {@code filter} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * 
	 * @param x the X-component of the pixel to start at
	 * @param y the Y-component of the pixel to start at
	 * @param operator an {@code IntTernaryOperator} instance that returns a color for each pixel affected
	 * @param filter an {@code IntTriPredicate} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code operator} or {@code filter} are {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillRegionColorARGB(final int x, final int y, final IntTernaryOperator operator, final IntTriPredicate filter) {
		this.data.changeBegin();
		
		doFillRegionColorARGB(x, y, operator, filter, getColorARGB(x, y));
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills {@code shape} in this {@code Image} instance with {@code Color4D.BLACK} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code shape} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillShapeColor4D(shape, Color4D.BLACK);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} to fill
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code shape} is {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillShapeColor4D(final Shape2I shape) {
		return fillShapeColor4D(shape, Color4D.BLACK);
	}
	
	/**
	 * Fills {@code shape} in this {@code Image} instance with {@link Color4D} instances returned by {@code operator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code operator} are {@code null} or {@code operator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param shape the {@link Shape2I} to fill
	 * @param operator a {@code BiFunction} that returns {@code Color4D} instances to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code operator} are {@code null} or {@code operator} returns {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillShapeColor4D(final Shape2I shape, final BiFunction<Color4D, Point2I, Color4D> operator) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(operator, "operator == null");
		
		this.data.changeBegin();
		
		shape.findPointsOfIntersection(getBounds()).forEach(point -> setColor4D(operator.apply(getColor4D(point), point), point));
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills {@code shape} in this {@code Image} instance with {@code color} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code color} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is essentially equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillShapeColor4D(shape, (currentColor, currentPoint) -> color);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} to fill
	 * @param color the {@link Color4D} to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code color} are {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillShapeColor4D(final Shape2I shape, final Color4D color) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(color, "color == null");
		
		return fillShapeColor4D(shape, (currentColor, currentPoint) -> color);
	}
	
	/**
	 * Fills {@code shape} in this {@code Image} instance with {@code Color4F.BLACK} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code shape} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillShapeColor4F(shape, Color4F.BLACK);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} to fill
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code shape} is {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillShapeColor4F(final Shape2I shape) {
		return fillShapeColor4F(shape, Color4F.BLACK);
	}
	
	/**
	 * Fills {@code shape} in this {@code Image} instance with {@link Color4F} instances returned by {@code operator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code operator} are {@code null} or {@code operator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param shape the {@link Shape2I} to fill
	 * @param operator a {@code BiFunction} that returns {@code Color4F} instances to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code operator} are {@code null} or {@code operator} returns {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillShapeColor4F(final Shape2I shape, final BiFunction<Color4F, Point2I, Color4F> operator) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(operator, "operator == null");
		
		this.data.changeBegin();
		
		shape.findPointsOfIntersection(getBounds()).forEach(point -> setColor4F(operator.apply(getColor4F(point), point), point));
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills {@code shape} in this {@code Image} instance with {@code color} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code color} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is essentially equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillShapeColor4F(shape, (currentColor, currentPoint) -> color);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} to fill
	 * @param color the {@link Color4F} to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code color} are {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillShapeColor4F(final Shape2I shape, final Color4F color) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(color, "color == null");
		
		return fillShapeColor4F(shape, (currentColor, currentPoint) -> color);
	}
	
	/**
	 * Fills everything except for {@code shape} in this {@code Image} instance with {@code Color4D.BLACK} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code shape} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillShapeComplementColor4D(shape, Color4D.BLACK);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} not to fill
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code shape} is {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillShapeComplementColor4D(final Shape2I shape) {
		return fillShapeComplementColor4D(shape, Color4D.BLACK);
	}
	
	/**
	 * Fills everything except for {@code shape} in this {@code Image} instance with {@link Color4D} instances returned by {@code operator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code operator} are {@code null} or {@code operator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param shape the {@link Shape2I} not to fill
	 * @param operator a {@code BiFunction} that returns {@code Color4D} instances to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code operator} are {@code null} or {@code operator} returns {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillShapeComplementColor4D(final Shape2I shape, final BiFunction<Color4D, Point2I, Color4D> operator) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(operator, "operator == null");
		
		this.data.changeBegin();
		
		shape.findPointsOfComplement(getBounds()).forEach(point -> setColor4D(operator.apply(getColor4D(point), point), point));
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills everything except for {@code shape} in this {@code Image} instance with {@code color} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code color} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is essentially equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillShapeComplementColor4D(shape, (currentColor, currentPoint) -> color);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} not to fill
	 * @param color the {@link Color4D} to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code color} are {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillShapeComplementColor4D(final Shape2I shape, final Color4D color) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(color, "color == null");
		
		return fillShapeComplementColor4D(shape, (currentColor, currentPoint) -> color);
	}
	
	/**
	 * Fills everything except for {@code shape} in this {@code Image} instance with {@code Color4F.BLACK} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code shape} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillShapeComplementColor4F(shape, Color4F.BLACK);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} not to fill
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code shape} is {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillShapeComplementColor4F(final Shape2I shape) {
		return fillShapeComplementColor4F(shape, Color4F.BLACK);
	}
	
	/**
	 * Fills everything except for {@code shape} in this {@code Image} instance with {@link Color4F} instances returned by {@code operator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code operator} are {@code null} or {@code operator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param shape the {@link Shape2I} not to fill
	 * @param operator a {@code BiFunction} that returns {@code Color4F} instances to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code operator} are {@code null} or {@code operator} returns {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillShapeComplementColor4F(final Shape2I shape, final BiFunction<Color4F, Point2I, Color4F> operator) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(operator, "operator == null");
		
		this.data.changeBegin();
		
		shape.findPointsOfComplement(getBounds()).forEach(point -> setColor4F(operator.apply(getColor4F(point), point), point));
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills everything except for {@code shape} in this {@code Image} instance with {@code color} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code color} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is essentially equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillShapeComplementColor4F(shape, (currentColor, currentPoint) -> color);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} not to fill
	 * @param color the {@link Color4F} to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code color} are {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image fillShapeComplementColor4F(final Shape2I shape, final Color4F color) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(color, "color == null");
		
		return fillShapeComplementColor4F(shape, (currentColor, currentPoint) -> color);
	}
	
	/**
	 * Flips this {@code Image} instance along the X- and Y-axes.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.flipX().flipY();
	 * }
	 * </pre>
	 * 
	 * @return this {@code Image} instance
	 */
	public Image flip() {
		return flipX().flipY();
	}
	
	/**
	 * Flips this {@code Image} instance along the X-axis.
	 * <p>
	 * Returns this {@code Image} instance.
	 * 
	 * @return this {@code Image} instance
	 */
	public Image flipX() {
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		this.data.changeBegin();
		
		for(int y = 0; y < resolutionY; y++) {
			for(int xL = 0, xR = resolutionX - 1; xL < xR; xL++, xR--) {
				final int indexL = y * resolutionX + xL;
				final int indexR = y * resolutionX + xR;
				
				this.data.swap(indexL, indexR);
			}
		}
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Flips this {@code Image} instance along the Y-axis.
	 * <p>
	 * Returns this {@code Image} instance.
	 * 
	 * @return this {@code Image} instance
	 */
	public Image flipY() {
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		this.data.changeBegin();
		
		for(int yT = 0, yB = resolutionY - 1; yT < yB; yT++, yB--) {
			for(int x = 0; x < resolutionX; x++) {
				final int indexT = yT * resolutionX + x;
				final int indexB = yB * resolutionX + x;
				
				this.data.swap(indexT, indexB);
			}
		}
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Rotates this {@code Image} instance by {@code angle} degrees.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * A rotation is performed on the entire image. It scales the image so the content will fit. Therefore, it is not advised to perform multiple rotations on a given {@code Image} instance.
	 * <p>
	 * If you need to perform multiple rotations, consider using the {@link #copy()} method and apply the rotation to the copy.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.rotate(angle, false);
	 * }
	 * </pre>
	 * 
	 * @param angle an angle in degrees
	 * @return this {@code Image} instance
	 */
	public Image rotate(final double angle) {
		return rotate(angle, false);
	}
	
	/**
	 * Rotates this {@code Image} instance by {@code angle} degrees or radians.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * A rotation is performed on the entire image. It scales the image so the content will fit. Therefore, it is not advised to perform multiple rotations on a given {@code Image} instance.
	 * <p>
	 * If you need to perform multiple rotations, consider using the {@link #copy()} method and apply the rotation to the copy.
	 * 
	 * @param angle an angle in degrees or radians
	 * @param isAngleInRadians {@code true} if, and only if, {@code angle} is in radians, {@code false} otherwise
	 * @return this {@code Image} instance
	 */
	public Image rotate(final double angle, final boolean isAngleInRadians) {
		this.data.rotate(angle, isAngleInRadians);
		
		return this;
	}
	
	/**
	 * Rotates this {@code Image} instance by {@code angle} degrees.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * A rotation is performed on the entire image. It scales the image so the content will fit. Therefore, it is not advised to perform multiple rotations on a given {@code Image} instance.
	 * <p>
	 * If you need to perform multiple rotations, consider using the {@link #copy()} method and apply the rotation to the copy.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.rotate(angle, false);
	 * }
	 * </pre>
	 * 
	 * @param angle an angle in degrees
	 * @return this {@code Image} instance
	 */
	public Image rotate(final float angle) {
		return rotate(angle, false);
	}
	
	/**
	 * Rotates this {@code Image} instance by {@code angle} degrees or radians.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * A rotation is performed on the entire image. It scales the image so the content will fit. Therefore, it is not advised to perform multiple rotations on a given {@code Image} instance.
	 * <p>
	 * If you need to perform multiple rotations, consider using the {@link #copy()} method and apply the rotation to the copy.
	 * 
	 * @param angle an angle in degrees or radians
	 * @param isAngleInRadians {@code true} if, and only if, {@code angle} is in radians, {@code false} otherwise
	 * @return this {@code Image} instance
	 */
	public Image rotate(final float angle, final boolean isAngleInRadians) {
		this.data.rotate(angle, isAngleInRadians);
		
		return this;
	}
	
	/**
	 * Adds a filtered version of the sample {@code colorXYZ} to all pixels surrounding the pixel at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code colorXYZ} or {@code filter} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.sampleColorXYZ(colorXYZ, x, y, filter, 1.0D);
	 * }
	 * </pre>
	 * 
	 * @param colorXYZ a {@link Color3D} instance represented in the XYZ-color space
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @param filter the {@link Filter2D} instance to use when filtering
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code colorXYZ} or {@code filter} are {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image sampleColorXYZ(final Color3D colorXYZ, final double x, final double y, final Filter2D filter) {
		return sampleColorXYZ(colorXYZ, x, y, filter, 1.0D);
	}
	
	/**
	 * Adds a filtered version of the sample {@code colorXYZ} to all pixels surrounding the pixel at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code colorXYZ} or {@code filter} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param colorXYZ a {@link Color3D} instance represented in the XYZ-color space
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @param filter the {@link Filter2D} instance to use when filtering
	 * @param sampleWeight the sample weight for this method call
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code colorXYZ} or {@code filter} are {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image sampleColorXYZ(final Color3D colorXYZ, final double x, final double y, final Filter2D filter, final double sampleWeight) {
		Objects.requireNonNull(colorXYZ, "colorXYZ == null");
		Objects.requireNonNull(filter, "filter == null");
		
		this.data.changeBegin();
		
		final double[] filterTable = filter.getTable();
		
		final double filterResolutionX = filter.getResolutionX();
		final double filterResolutionY = filter.getResolutionY();
		final double filterResolutionXReciprocal = filter.getResolutionXReciprocal();
		final double filterResolutionYReciprocal = filter.getResolutionYReciprocal();
		
		final double deltaX = x - 0.5D;
		final double deltaY = y - 0.5D;
		
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		final int minimumFilterX = (int)(Doubles.max(Doubles.ceil(deltaX - filterResolutionX), 0.0D));
		final int maximumFilterX = (int)(Doubles.min(Doubles.floor(deltaX + filterResolutionX), resolutionX - 1.0D));
		final int minimumFilterY = (int)(Doubles.max(Doubles.ceil(deltaY - filterResolutionY), 0.0D));
		final int maximumFilterY = (int)(Doubles.min(Doubles.floor(deltaY + filterResolutionY), resolutionY - 1.0D));
		final int maximumFilterXMinimumFilterX = maximumFilterX - minimumFilterX;
		final int maximumFilterYMinimumFilterY = maximumFilterY - minimumFilterY;
		
		if(maximumFilterXMinimumFilterX >= 0 && maximumFilterYMinimumFilterY >= 0) {
			final int[] filterOffsetX = new int[maximumFilterXMinimumFilterX + 1];
			final int[] filterOffsetY = new int[maximumFilterYMinimumFilterY + 1];
			
			for(int filterX = minimumFilterX; filterX <= maximumFilterX; filterX++) {
				filterOffsetX[filterX - minimumFilterX] = Ints.min((int)(Doubles.floor(Doubles.abs((filterX - deltaX) * filterResolutionXReciprocal * Filter2D.TABLE_SIZE))), Filter2D.TABLE_SIZE - 1);
			}
			
			for(int filterY = minimumFilterY; filterY <= maximumFilterY; filterY++) {
				filterOffsetY[filterY - minimumFilterY] = Ints.min((int)(Doubles.floor(Doubles.abs((filterY - deltaY) * filterResolutionYReciprocal * Filter2D.TABLE_SIZE))), Filter2D.TABLE_SIZE - 1);
			}
			
			for(int filterY = minimumFilterY; filterY <= maximumFilterY; filterY++) {
				final int filterYResolutionX = filterY * resolutionX;
				final int filterOffsetYOffsetFilterTableSize = filterOffsetY[filterY - minimumFilterY] * Filter2D.TABLE_SIZE;
				
				for(int filterX = minimumFilterX; filterX <= maximumFilterX; filterX++) {
					final double filterWeight = filterTable[filterOffsetYOffsetFilterTableSize + filterOffsetX[filterX - minimumFilterX]];
					
					final int index = filterYResolutionX + filterX;
					
					final Color4D oldColorXYZW = getColor4D(index);
					final Color4D newColorXYZW = new Color4D(oldColorXYZW.r + colorXYZ.r * sampleWeight * filterWeight, oldColorXYZW.g + colorXYZ.g * sampleWeight * filterWeight, oldColorXYZW.b + colorXYZ.b * sampleWeight * filterWeight, oldColorXYZW.a + filterWeight);
					
					setColor4D(newColorXYZW, index);
				}
			}
		}
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Adds a filtered version of the sample {@code colorXYZ} to all pixels surrounding the pixel at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code colorXYZ} or {@code filter} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.sampleColorXYZ(colorXYZ, x, y, filter, 1.0F);
	 * }
	 * </pre>
	 * 
	 * @param colorXYZ a {@link Color3F} instance represented in the XYZ-color space
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @param filter the {@link Filter2F} instance to use when filtering
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code colorXYZ} or {@code filter} are {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image sampleColorXYZ(final Color3F colorXYZ, final float x, final float y, final Filter2F filter) {
		return sampleColorXYZ(colorXYZ, x, y, filter, 1.0F);
	}
	
	/**
	 * Adds a filtered version of the sample {@code colorXYZ} to all pixels surrounding the pixel at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code colorXYZ} or {@code filter} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param colorXYZ a {@link Color3F} instance represented in the XYZ-color space
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @param filter the {@link Filter2F} instance to use when filtering
	 * @param sampleWeight the sample weight for this method call
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code colorXYZ} or {@code filter} are {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image sampleColorXYZ(final Color3F colorXYZ, final float x, final float y, final Filter2F filter, final float sampleWeight) {
		Objects.requireNonNull(colorXYZ, "colorXYZ == null");
		Objects.requireNonNull(filter, "filter == null");
		
		this.data.changeBegin();
		
		final float[] filterTable = filter.getTable();
		
		final float filterResolutionX = filter.getResolutionX();
		final float filterResolutionY = filter.getResolutionY();
		final float filterResolutionXReciprocal = filter.getResolutionXReciprocal();
		final float filterResolutionYReciprocal = filter.getResolutionYReciprocal();
		
		final float deltaX = x - 0.5F;
		final float deltaY = y - 0.5F;
		
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		final int minimumFilterX = (int)(Floats.max(Floats.ceil(deltaX - filterResolutionX), 0.0F));
		final int maximumFilterX = (int)(Floats.min(Floats.floor(deltaX + filterResolutionX), resolutionX - 1.0F));
		final int minimumFilterY = (int)(Floats.max(Floats.ceil(deltaY - filterResolutionY), 0.0F));
		final int maximumFilterY = (int)(Floats.min(Floats.floor(deltaY + filterResolutionY), resolutionY - 1.0F));
		final int maximumFilterXMinimumFilterX = maximumFilterX - minimumFilterX;
		final int maximumFilterYMinimumFilterY = maximumFilterY - minimumFilterY;
		
		if(maximumFilterXMinimumFilterX >= 0 && maximumFilterYMinimumFilterY >= 0) {
			final int[] filterOffsetX = new int[maximumFilterXMinimumFilterX + 1];
			final int[] filterOffsetY = new int[maximumFilterYMinimumFilterY + 1];
			
			for(int filterX = minimumFilterX; filterX <= maximumFilterX; filterX++) {
				filterOffsetX[filterX - minimumFilterX] = Ints.min((int)(Floats.floor(Floats.abs((filterX - deltaX) * filterResolutionXReciprocal * Filter2F.TABLE_SIZE))), Filter2F.TABLE_SIZE - 1);
			}
			
			for(int filterY = minimumFilterY; filterY <= maximumFilterY; filterY++) {
				filterOffsetY[filterY - minimumFilterY] = Ints.min((int)(Floats.floor(Floats.abs((filterY - deltaY) * filterResolutionYReciprocal * Filter2F.TABLE_SIZE))), Filter2F.TABLE_SIZE - 1);
			}
			
			for(int filterY = minimumFilterY; filterY <= maximumFilterY; filterY++) {
				final int filterYResolutionX = filterY * resolutionX;
				final int filterOffsetYOffsetFilterTableSize = filterOffsetY[filterY - minimumFilterY] * Filter2F.TABLE_SIZE;
				
				for(int filterX = minimumFilterX; filterX <= maximumFilterX; filterX++) {
					final float filterWeight = filterTable[filterOffsetYOffsetFilterTableSize + filterOffsetX[filterX - minimumFilterX]];
					
					final int index = filterYResolutionX + filterX;
					
					final Color4F oldColorXYZW = getColor4F(index);
					final Color4F newColorXYZW = new Color4F(oldColorXYZW.r + colorXYZ.r * sampleWeight * filterWeight, oldColorXYZW.g + colorXYZ.g * sampleWeight * filterWeight, oldColorXYZW.b + colorXYZ.b * sampleWeight * filterWeight, oldColorXYZW.a + filterWeight);
					
					setColor4F(newColorXYZW, index);
				}
			}
		}
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Renders {@code imageXYZ} that stores pixel data in XYZ-color space to this {@code Image} instance that stores pixel data in RGB-color space.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code imageXYZ} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param imageXYZ an {@code Image} instance that stores pixel data in XYZ-color space
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code imageXYZ} is {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image sampleRenderColor3D(final Image imageXYZ) {
		Objects.requireNonNull(imageXYZ, "imageXYZ == null");
		
		this.data.changeBegin();
		
		final ColorSpaceD colorSpace = ColorSpaceD.getDefault();
		
		final int resolutionX = Ints.min(getResolutionX(), imageXYZ.getResolutionX());
		final int resolutionY = Ints.min(getResolutionY(), imageXYZ.getResolutionY());
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++) {
				final Color4D colorXYZW = imageXYZ.getColor4D(x, y);
				final Color3D colorXYZ = new Color3D(colorXYZW);
				
				Color3D colorRGB = colorSpace.convertXYZToRGB(colorXYZ);
				
				if(!Doubles.isZero(colorXYZW.a)) {
					colorRGB = new Color3D(Doubles.max(colorRGB.r / colorXYZW.a, 0.0D), Doubles.max(colorRGB.g / colorXYZW.a, 0.0D), Doubles.max(colorRGB.b / colorXYZW.a, 0.0D));
				}
				
				colorRGB = colorSpace.redoGammaCorrection(colorRGB);
				
				setColor3D(colorRGB, x, y);
			}
		}
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Renders {@code imageXYZ} that stores pixel data in XYZ-color space to this {@code Image} instance that stores pixel data in RGB-color space.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code imageXYZ} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param imageXYZ an {@code Image} instance that stores pixel data in XYZ-color space
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code imageXYZ} is {@code null}
	 */
//	TODO: Add Unit Tests!
	public Image sampleRenderColor3F(final Image imageXYZ) {
		Objects.requireNonNull(imageXYZ, "imageXYZ == null");
		
		this.data.changeBegin();
		
		final ColorSpaceF colorSpace = ColorSpaceF.getDefault();
		
		final int resolutionX = Ints.min(getResolutionX(), imageXYZ.getResolutionX());
		final int resolutionY = Ints.min(getResolutionY(), imageXYZ.getResolutionY());
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++) {
				final Color4F colorXYZW = imageXYZ.getColor4F(x, y);
				final Color3F colorXYZ = new Color3F(colorXYZW);
				
				Color3F colorRGB = colorSpace.convertXYZToRGB(colorXYZ);
				
				if(!Floats.isZero(colorXYZW.a)) {
					colorRGB = new Color3F(Floats.max(colorRGB.r / colorXYZW.a, 0.0F), Floats.max(colorRGB.g / colorXYZW.a, 0.0F), Floats.max(colorRGB.b / colorXYZW.a, 0.0F));
				}
				
				colorRGB = colorSpace.redoGammaCorrection(colorRGB);
				
				setColor3F(colorRGB, x, y);
			}
		}
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Scales this {@code Image} instance to a new resolution given the scale factors {@code scaleX} and {@code scaleY}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code scaleX} or {@code scaleY} are less than or equal to {@code 0.0D}, the resolution will not be changed.
	 * <p>
	 * If both {@code scaleX} and {@code scaleY} are equal to {@code 1.0D}, the resolution will not be changed.
	 * 
	 * @param scaleX the scale factor along the X-axis
	 * @param scaleY the scale factor along the Y-axis
	 * @return this {@code Image} instance
	 */
	public Image scale(final double scaleX, final double scaleY) {
		this.data.scale(scaleX, scaleY);
		
		return this;
	}
	
	/**
	 * Scales this {@code Image} instance to a new resolution given the scale factors {@code scaleX} and {@code scaleY}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code scaleX} or {@code scaleY} are less than or equal to {@code 0.0F}, the resolution will not be changed.
	 * <p>
	 * If both {@code scaleX} and {@code scaleY} are equal to {@code 1.0F}, the resolution will not be changed.
	 * 
	 * @param scaleX the scale factor along the X-axis
	 * @param scaleY the scale factor along the Y-axis
	 * @return this {@code Image} instance
	 */
	public Image scale(final float scaleX, final float scaleY) {
		this.data.scale(scaleX, scaleY);
		
		return this;
	}
	
	/**
	 * Scales this {@code Image} instance to {@code resolutionX} and {@code resolutionY}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows, the resolution will not be changed.
	 * <p>
	 * If {@code resolutionX == image.getResolutionX()} and {@code resolutionY == image.getResolutionY()}, the resolution will not be changed.
	 * 
	 * @param resolutionX the new resolution along the X-axis
	 * @param resolutionY the new resolution along the Y-axis
	 * @return this {@code Image} instance
	 */
	public Image scale(final int resolutionX, final int resolutionY) {
		this.data.scale(resolutionX, resolutionY);
		
		return this;
	}
	
	/**
	 * Sets the color of the pixel at {@code point} in this {@code Image} instance to {@code color}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code color} or {@code point} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code point.x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, nothing will happen.
	 * <p>
	 * If {@code point.y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, nothing will happen.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.setColor3D(color, point.x, point.y);
	 * }
	 * </pre>
	 * 
	 * @param color the {@link Color3D} to set
	 * @param point the {@link Point2I} that contains the X- and Y-components of the pixel
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code color} or {@code point} are {@code null}
	 */
	public Image setColor3D(final Color3D color, final Point2I point) {
		return setColor3D(color, point.x, point.y);
	}
	
	/**
	 * Sets the color of the pixel at {@code index} in this {@code Image} instance to {@code color}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code image.getResolution()}, nothing will happen.
	 * 
	 * @param color the {@link Color3D} to set
	 * @param index the index of the pixel
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image setColor3D(final Color3D color, final int index) {
		this.data.setColor3D(Objects.requireNonNull(color, "color == null"), index);
		
		return this;
	}
	
	/**
	 * Sets the color of the pixel at {@code x} and {@code y} in this {@code Image} instance to {@code color}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, nothing will happen.
	 * <p>
	 * If {@code y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, nothing will happen.
	 * 
	 * @param color the {@link Color3D} to set
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image setColor3D(final Color3D color, final int x, final int y) {
		this.data.setColor3D(Objects.requireNonNull(color, "color == null"), x, y);
		
		return this;
	}
	
	/**
	 * Sets the color of the pixel at {@code point} in this {@code Image} instance to {@code color}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code color} or {@code point} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code point.x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, nothing will happen.
	 * <p>
	 * If {@code point.y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, nothing will happen.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.setColor3F(color, point.x, point.y);
	 * }
	 * </pre>
	 * 
	 * @param color the {@link Color3F} to set
	 * @param point the {@link Point2I} that contains the X- and Y-components of the pixel
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code color} or {@code point} are {@code null}
	 */
	public Image setColor3F(final Color3F color, final Point2I point) {
		return setColor3F(color, point.x, point.y);
	}
	
	/**
	 * Sets the color of the pixel at {@code index} in this {@code Image} instance to {@code color}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code image.getResolution()}, nothing will happen.
	 * 
	 * @param color the {@link Color3F} to set
	 * @param index the index of the pixel
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image setColor3F(final Color3F color, final int index) {
		this.data.setColor3F(Objects.requireNonNull(color, "color == null"), index);
		
		return this;
	}
	
	/**
	 * Sets the color of the pixel at {@code x} and {@code y} in this {@code Image} instance to {@code color}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, nothing will happen.
	 * <p>
	 * If {@code y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, nothing will happen.
	 * 
	 * @param color the {@link Color3F} to set
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image setColor3F(final Color3F color, final int x, final int y) {
		this.data.setColor3F(Objects.requireNonNull(color, "color == null"), x, y);
		
		return this;
	}
	
	/**
	 * Sets the color of the pixel at {@code point} in this {@code Image} instance to {@code color}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code color} or {@code point} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code point.x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, nothing will happen.
	 * <p>
	 * If {@code point.y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, nothing will happen.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.setColor4D(color, point.x, point.y);
	 * }
	 * </pre>
	 * 
	 * @param color the {@link Color4D} to set
	 * @param point the {@link Point2I} that contains the X- and Y-components of the pixel
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code color} or {@code point} are {@code null}
	 */
	public Image setColor4D(final Color4D color, final Point2I point) {
		return setColor4D(color, point.x, point.y);
	}
	
	/**
	 * Sets the color of the pixel at {@code index} in this {@code Image} instance to {@code color}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code image.getResolution()}, nothing will happen.
	 * 
	 * @param color the {@link Color4D} to set
	 * @param index the index of the pixel
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image setColor4D(final Color4D color, final int index) {
		this.data.setColor4D(Objects.requireNonNull(color, "color == null"), index);
		
		return this;
	}
	
	/**
	 * Sets the color of the pixel at {@code x} and {@code y} in this {@code Image} instance to {@code color}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, nothing will happen.
	 * <p>
	 * If {@code y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, nothing will happen.
	 * 
	 * @param color the {@link Color4D} to set
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image setColor4D(final Color4D color, final int x, final int y) {
		this.data.setColor4D(Objects.requireNonNull(color, "color == null"), x, y);
		
		return this;
	}
	
	/**
	 * Sets the color of the pixel at {@code point} in this {@code Image} instance to {@code color}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code color} or {@code point} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code point.x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, nothing will happen.
	 * <p>
	 * If {@code point.y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, nothing will happen.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.setColor4F(color, point.x, point.y);
	 * }
	 * </pre>
	 * 
	 * @param color the {@link Color4F} to set
	 * @param point the {@link Point2I} that contains the X- and Y-components of the pixel
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code color} or {@code point} are {@code null}
	 */
	public Image setColor4F(final Color4F color, final Point2I point) {
		return setColor4F(color, point.x, point.y);
	}
	
	/**
	 * Sets the color of the pixel at {@code index} in this {@code Image} instance to {@code color}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code image.getResolution()}, nothing will happen.
	 * 
	 * @param color the {@link Color4F} to set
	 * @param index the index of the pixel
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image setColor4F(final Color4F color, final int index) {
		this.data.setColor4F(Objects.requireNonNull(color, "color == null"), index);
		
		return this;
	}
	
	/**
	 * Sets the color of the pixel at {@code x} and {@code y} in this {@code Image} instance to {@code color}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, nothing will happen.
	 * <p>
	 * If {@code y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, nothing will happen.
	 * 
	 * @param color the {@link Color4F} to set
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image setColor4F(final Color4F color, final int x, final int y) {
		this.data.setColor4F(Objects.requireNonNull(color, "color == null"), x, y);
		
		return this;
	}
	
	/**
	 * Sets the color of the pixel at {@code point} in this {@code Image} instance to {@code colorARGB}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code point.x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, nothing will happen.
	 * <p>
	 * If {@code point.y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, nothing will happen.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.setColorARGB(colorARGB, point.x, point.y);
	 * }
	 * </pre>
	 * 
	 * @param colorARGB the color to set
	 * @param point the {@link Point2I} that contains the X- and Y-components of the pixel
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Image setColorARGB(final int colorARGB, final Point2I point) {
		return setColorARGB(colorARGB, point.x, point.y);
	}
	
	/**
	 * Sets the color of the pixel at {@code index} in this {@code Image} instance to {@code colorARGB}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code image.getResolution()}, nothing will happen.
	 * 
	 * @param colorARGB the color to set
	 * @param index the index of the pixel
	 * @return this {@code Image} instance
	 */
	public Image setColorARGB(final int colorARGB, final int index) {
		this.data.setColorARGB(colorARGB, index);
		
		return this;
	}
	
	/**
	 * Sets the color of the pixel at {@code x} and {@code y} in this {@code Image} instance to {@code colorARGB}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, nothing will happen.
	 * <p>
	 * If {@code y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, nothing will happen.
	 * 
	 * @param colorARGB the color to set
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return this {@code Image} instance
	 */
	public Image setColorARGB(final int colorARGB, final int x, final int y) {
		this.data.setColorARGB(colorARGB, x, y);
		
		return this;
	}
	
	/**
	 * Sets the resolution of this {@code Image} instance to {@code resolutionX} and {@code resolutionY}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows, the resolution will not be changed.
	 * <p>
	 * If {@code resolutionX == image.getResolutionX()} and {@code resolutionY == image.getResolutionY()}, the resolution will not be changed.
	 * 
	 * @param resolutionX the new resolution along the X-axis
	 * @param resolutionY the new resolution along the Y-axis
	 * @return this {@code Image} instance
	 */
	public Image setResolution(final int resolutionX, final int resolutionY) {
		this.data.setResolution(resolutionX, resolutionY);
		
		return this;
	}
	
	/**
	 * Sets the X-resolution of this {@code Image} instance to {@code resolutionX}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code resolutionX} is less than {@code 1} or {@code resolutionX * image.getResolutionY()} overflows, the resolution will not be changed.
	 * <p>
	 * If {@code resolutionX == image.getResolutionX()}, the resolution will not be changed.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.setResolution(resolutionX, image.getResolutionY());
	 * }
	 * </pre>
	 * 
	 * @param resolutionX the new resolution along the X-axis
	 * @return this {@code Image} instance
	 */
	public Image setResolutionX(final int resolutionX) {
		this.data.setResolutionX(resolutionX);
		
		return this;
	}
	
	/**
	 * Sets the Y-resolution of this {@code Image} instance to {@code resolutionY}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code resolutionY} is less than {@code 1} or {@code image.getResolutionX() * resolutionY} overflows, the resolution will not be changed.
	 * <p>
	 * If {@code resolutionY == image.getResolutionY()}, the resolution will not be changed.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.setResolution(image.getResolutionX(), resolutionY);
	 * }
	 * </pre>
	 * 
	 * @param resolutionY the new resolution along the Y-axis
	 * @return this {@code Image} instance
	 */
	public Image setResolutionY(final int resolutionY) {
		this.data.setResolutionY(resolutionY);
		
		return this;
	}
	
	/**
	 * Returns a {@link Rectangle2I} with the bounds of this {@code Image} instance.
	 * 
	 * @return a {@code Rectangle2I} with the bounds of this {@code Image} instance
	 */
	public Rectangle2I getBounds() {
		return new Rectangle2I(new Point2I(), new Point2I(getResolutionX() - 1, getResolutionY() - 1));
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Image} instance.
	 * 
	 * @return a {@code String} representation of this {@code Image} instance
	 */
	@Override
	public String toString() {
		return String.format("new Image(%d, %d)", Integer.valueOf(getResolutionX()), Integer.valueOf(getResolutionY()));
	}
	
	/**
	 * Compares {@code object} to this {@code Image} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Image}, and they are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Image} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Image}, and they are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Image)) {
			return false;
		} else if(!Objects.equals(this.data, Image.class.cast(object).data)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, the change history is enabled, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, the change history is enabled, {@code false} otherwise
	 */
	public boolean isChangeHistoryEnabled() {
		return this.data.isChangeHistoryEnabled();
	}
	
	/**
	 * Performs the current redo operation.
	 * <p>
	 * Returns {@code true} if, and only if, the redo operation was performed, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, the redo operation was performed, {@code false} otherwise
	 */
	public boolean redo() {
		return this.data.redo();
	}
	
	/**
	 * Saves this {@code Image} instance to the file represented by {@code file} using the informal format name {@code "png"}.
	 * <p>
	 * Returns {@code true} if, and only if, this {@code Image} instance was saved to {@code file}, {@code false} otherwise.
	 * <p>
	 * If {@code file} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.save(file, "png");
	 * }
	 * </pre>
	 * 
	 * @param file a {@code File} that represents the file to save to
	 * @return {@code true} if, and only if, this {@code Image} instance was saved to {@code file}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 */
	public boolean save(final File file) {
		return save(file, "png");
	}
	
	/**
	 * Saves this {@code Image} instance to the file represented by {@code file} using the informal format name {@code formatName}.
	 * <p>
	 * Returns {@code true} if, and only if, this {@code Image} instance was saved to {@code file}, {@code false} otherwise.
	 * <p>
	 * If either {@code file} or {@code formatName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param file a {@code File} that represents the file to save to
	 * @param formatName the informal format name, such as {@code "png"}
	 * @return {@code true} if, and only if, this {@code Image} instance was saved to {@code file}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code file} or {@code formatName} are {@code null}
	 */
	public boolean save(final File file, final String formatName) {
		return this.data.save(file, formatName);
	}
	
	/**
	 * Saves this {@code Image} instance to the file represented by the pathname {@code pathname} using the informal format name {@code "png"}.
	 * <p>
	 * Returns {@code true} if, and only if, this {@code Image} instance was saved to the file, {@code false} otherwise.
	 * <p>
	 * If {@code pathname} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.save(pathname, "png");
	 * }
	 * </pre>
	 * 
	 * @param pathname a {@code String} that represents the pathname of the file to save to
	 * @return {@code true} if, and only if, this {@code Image} instance was saved to the file, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code pathname} is {@code null}
	 */
	public boolean save(final String pathname) {
		return save(pathname, "png");
	}
	
	/**
	 * Saves this {@code Image} instance to the file represented by the pathname {@code pathname} using the informal format name {@code formatName}.
	 * <p>
	 * Returns {@code true} if, and only if, this {@code Image} instance was saved to the file, {@code false} otherwise.
	 * <p>
	 * If either {@code pathname} or {@code formatName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.save(new File(pathname), formatName);
	 * }
	 * </pre>
	 * 
	 * @param pathname a {@code String} that represents the pathname of the file to save to
	 * @param formatName the informal format name, such as {@code "png"}
	 * @return {@code true} if, and only if, this {@code Image} instance was saved to the file, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code pathname} or {@code formatName} are {@code null}
	 */
	public boolean save(final String pathname, final String formatName) {
		return save(new File(pathname), formatName);
	}
	
	/**
	 * Sets the change history enabled state to {@code isChangeHistoryEnabled}.
	 * <p>
	 * Returns {@code true} if, and only if, the change history enabled state was changed, {@code false} otherwise.
	 * 
	 * @param isChangeHistoryEnabled the change history enabled state
	 * @return {@code true} if, and only if, the change history enabled state was changed, {@code false} otherwise
	 */
	public boolean setChangeHistoryEnabled(final boolean isChangeHistoryEnabled) {
		return this.data.setChangeHistoryEnabled(isChangeHistoryEnabled);
	}
	
	/**
	 * Performs the current undo operation.
	 * <p>
	 * Returns {@code true} if, and only if, the undo operation was performed, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, the undo operation was performed, {@code false} otherwise
	 */
	public boolean undo() {
		return this.data.undo();
	}
	
	/**
	 * Performs a cache operation to this {@code Image} instance.
	 * <p>
	 * Returns the number of pixels that were cached as a result of this operation.
	 * 
	 * @return the number of pixels that were cached as a result of this operation
	 */
	public int cache() {
		return this.data.cache();
	}
	
	/**
	 * Returns the color at {@code point} in this {@code Image} instance.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code point.x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color4I.TRANSPARENT_A_R_G_B} will be returned.
	 * <p>
	 * If {@code point.y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color4I.TRANSPARENT_A_R_G_B} will be returned.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColorARGB(point.x, point.y);
	 * }
	 * </pre>
	 * 
	 * @param point a {@link Point2I} instance that contains the X- and Y-components of the pixel
	 * @return the color at {@code point} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public int getColorARGB(final Point2I point) {
		return getColorARGB(point.x, point.y);
	}
	
	/**
	 * Returns the color at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionX()}, {@code Color4I.TRANSPARENT_A_R_G_B} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionY()}, {@code Color4I.TRANSPARENT_A_R_G_B} will be returned.
	 * <p>
	 * If both {@code x} and {@code y} are equal to mathematical integers, this method is equivalent to {@link #getColorARGB(int, int)}. Otherwise, bilinear interpolation will be performed on the closest pixels.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the color at {@code x} and {@code y} in this {@code Image} instance
	 */
	public int getColorARGB(final double x, final double y) {
		return this.data.getColorARGB(x, y);
	}
	
	/**
	 * Returns the color at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0.0F} or greater than or equal to {@code image.getResolutionX()}, {@code Color4I.TRANSPARENT_A_R_G_B} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0.0F} or greater than or equal to {@code image.getResolutionY()}, {@code Color4I.TRANSPARENT_A_R_G_B} will be returned.
	 * <p>
	 * If both {@code x} and {@code y} are equal to mathematical integers, this method is equivalent to {@link #getColorARGB(int, int)}. Otherwise, bilinear interpolation will be performed on the closest pixels.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the color at {@code x} and {@code y} in this {@code Image} instance
	 */
	public int getColorARGB(final float x, final float y) {
		return this.data.getColorARGB(x, y);
	}
	
	/**
	 * Returns the color at {@code index} in this {@code Image} instance.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code image.getResolution()}, {@code Color4I.TRANSPARENT_A_R_G_B} will be returned.
	 * 
	 * @param index the index of the pixel
	 * @return the color at {@code index} in this {@code Image} instance
	 */
	public int getColorARGB(final int index) {
		return this.data.getColorARGB(index);
	}
	
	/**
	 * Returns the color at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color4I.TRANSPARENT_A_R_G_B} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color4I.TRANSPARENT_A_R_G_B} will be returned.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the color at {@code x} and {@code y} in this {@code Image} instance
	 */
	public int getColorARGB(final int x, final int y) {
		return this.data.getColorARGB(x, y);
	}
	
	/**
	 * Returns the resolution of this {@code Image} instance.
	 * 
	 * @return the resolution of this {@code Image} instance
	 */
	public int getResolution() {
		return this.data.getResolution();
	}
	
	/**
	 * Returns the resolution along the X-axis of this {@code Image} instance.
	 * 
	 * @return the resolution along the X-axis of this {@code Image} instance
	 */
	public int getResolutionX() {
		return this.data.getResolutionX();
	}
	
	/**
	 * Returns the resolution along the Y-axis of this {@code Image} instance.
	 * 
	 * @return the resolution along the Y-axis of this {@code Image} instance
	 */
	public int getResolutionY() {
		return this.data.getResolutionY();
	}
	
	/**
	 * Returns a hash code for this {@code Image} instance.
	 * 
	 * @return a hash code for this {@code Image} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.data);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int[] doFilter(final Color4DPixelFilter pixelFilter) {
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		final int[] indices = new int[resolutionX * resolutionY];
		
		boolean isFullyCovered = true;
		
		for(int y = 0, index = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++, index++) {
				final Color4D color = getColor4D(x, y);
				
				if(pixelFilter.isAccepted(color, x, y)) {
					indices[index] = index;
				} else {
					indices[index] = -1;
					
					isFullyCovered = false;
				}
			}
		}
		
		if(isFullyCovered) {
			return indices;
		}
		
		return Arrays.stream(indices).filter(index -> index != -1).toArray();
	}
	
	private int[] doFilter(final Color4FPixelFilter pixelFilter) {
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		final int[] indices = new int[resolutionX * resolutionY];
		
		boolean isFullyCovered = true;
		
		for(int y = 0, index = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++, index++) {
				final Color4F color = getColor4F(x, y);
				
				if(pixelFilter.isAccepted(color, x, y)) {
					indices[index] = index;
				} else {
					indices[index] = -1;
					
					isFullyCovered = false;
				}
			}
		}
		
		if(isFullyCovered) {
			return indices;
		}
		
		return Arrays.stream(indices).filter(index -> index != -1).toArray();
	}
	
//	TODO: Add Unit Tests!
	private void doFillRegionColor4D(final int x, final int y, final BiFunction<Color4D, Point2I, Color4D> operator, final BiPredicate<Color4D, Point2I> filter, final Color4D oldColor) {
		final int resolution = getResolution();
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		final int minimumX = 0;
		final int maximumX = resolutionX - 1;
		final int minimumY = 0;
		final int maximumY = resolutionY - 1;
		
		if(x >= minimumX && x <= maximumX && y >= minimumY && y <= maximumY) {
			final boolean[] isFilled = new boolean[resolution];
			
			final int[] stackX = new int[resolution];
			final int[] stackY = new int[resolution];
			
			stackX[0] = x;
			stackY[0] = y;
			
			int stackLength = 1;
			
			while(stackLength > 0) {
				final int currentX = stackX[stackLength - 1];
				final int currentY = stackY[stackLength - 1];
				
				stackLength--;
				
				final Color4D color = getColor4D(currentX, currentY);
				
				final Point2I point = new Point2I(currentX, currentY);
				
				if(filter.test(color, point)) {
					this.data.setColor4D(operator.apply(color, point), currentX, currentY);
				}
				
				isFilled[currentY * resolutionX + currentX] = true;
				
				if(currentX + 1 <= maximumX && !isFilled[currentY * resolutionX + currentX + 1] && getColor4D(currentX + 1, currentY).equals(oldColor)) {
					stackX[stackLength] = currentX + 1;
					stackY[stackLength] = currentY;
					
					stackLength++;
				}
				
				if(currentX - 1 >= minimumX && !isFilled[currentY * resolutionX + currentX - 1] && getColor4D(currentX - 1, currentY).equals(oldColor)) {
					stackX[stackLength] = currentX - 1;
					stackY[stackLength] = currentY;
					
					stackLength++;
				}
				
				if(currentY + 1 <= maximumY && !isFilled[(currentY + 1) * resolutionX + currentX] && getColor4D(currentX, currentY + 1).equals(oldColor)) {
					stackX[stackLength] = currentX;
					stackY[stackLength] = currentY + 1;
					
					stackLength++;
				}
				
				if(currentY - 1 >= minimumY && !isFilled[(currentY - 1) * resolutionX + currentX] && getColor4D(currentX, currentY - 1).equals(oldColor)) {
					stackX[stackLength] = currentX;
					stackY[stackLength] = currentY - 1;
					
					stackLength++;
				}
			}
		}
	}
	
//	TODO: Add Unit Tests!
	private void doFillRegionColor4F(final int x, final int y, final BiFunction<Color4F, Point2I, Color4F> operator, final BiPredicate<Color4F, Point2I> filter, final Color4F oldColor) {
		final int resolution = getResolution();
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		final int minimumX = 0;
		final int maximumX = resolutionX - 1;
		final int minimumY = 0;
		final int maximumY = resolutionY - 1;
		
		if(x >= minimumX && x <= maximumX && y >= minimumY && y <= maximumY) {
			final boolean[] isFilled = new boolean[resolution];
			
			final int[] stackX = new int[resolution];
			final int[] stackY = new int[resolution];
			
			stackX[0] = x;
			stackY[0] = y;
			
			int stackLength = 1;
			
			while(stackLength > 0) {
				final int currentX = stackX[stackLength - 1];
				final int currentY = stackY[stackLength - 1];
				
				stackLength--;
				
				final Color4F color = getColor4F(currentX, currentY);
				
				final Point2I point = new Point2I(currentX, currentY);
				
				if(filter.test(color, point)) {
					this.data.setColor4F(operator.apply(color, point), currentX, currentY);
				}
				
				isFilled[currentY * resolutionX + currentX] = true;
				
				if(currentX + 1 <= maximumX && !isFilled[currentY * resolutionX + currentX + 1] && getColor4F(currentX + 1, currentY).equals(oldColor)) {
					stackX[stackLength] = currentX + 1;
					stackY[stackLength] = currentY;
					
					stackLength++;
				}
				
				if(currentX - 1 >= minimumX && !isFilled[currentY * resolutionX + currentX - 1] && getColor4F(currentX - 1, currentY).equals(oldColor)) {
					stackX[stackLength] = currentX - 1;
					stackY[stackLength] = currentY;
					
					stackLength++;
				}
				
				if(currentY + 1 <= maximumY && !isFilled[(currentY + 1) * resolutionX + currentX] && getColor4F(currentX, currentY + 1).equals(oldColor)) {
					stackX[stackLength] = currentX;
					stackY[stackLength] = currentY + 1;
					
					stackLength++;
				}
				
				if(currentY - 1 >= minimumY && !isFilled[(currentY - 1) * resolutionX + currentX] && getColor4F(currentX, currentY - 1).equals(oldColor)) {
					stackX[stackLength] = currentX;
					stackY[stackLength] = currentY - 1;
					
					stackLength++;
				}
			}
		}
	}
	
//	TODO: Add Unit Tests!
	private void doFillRegionColorARGB(final int x, final int y, final IntTernaryOperator pixelOperator, final IntTriPredicate pixelFilter, final int oldColorARGB) {
		final int resolution = getResolution();
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		final int minimumX = 0;
		final int maximumX = resolutionX - 1;
		final int minimumY = 0;
		final int maximumY = resolutionY - 1;
		
		if(x >= minimumX && x <= maximumX && y >= minimumY && y <= maximumY) {
			final boolean[] isFilled = new boolean[resolution];
			
			final int[] stackX = new int[resolution];
			final int[] stackY = new int[resolution];
			
			stackX[0] = x;
			stackY[0] = y;
			
			int stackLength = 1;
			
			while(stackLength > 0) {
				final int currentX = stackX[stackLength - 1];
				final int currentY = stackY[stackLength - 1];
				
				stackLength--;
				
				final int colorARGB = getColorARGB(currentX, currentY);
				
				if(pixelFilter.test(colorARGB, currentX, currentY)) {
					this.data.setColorARGB(pixelOperator.applyAsInt(colorARGB, currentX, currentY), currentX, currentY);
				}
				
				isFilled[currentY * resolutionX + currentX] = true;
				
				if(currentX + 1 <= maximumX && !isFilled[currentY * resolutionX + currentX + 1] && getColorARGB(currentX + 1, currentY) == oldColorARGB) {
					stackX[stackLength] = currentX + 1;
					stackY[stackLength] = currentY;
					
					stackLength++;
				}
				
				if(currentX - 1 >= minimumX && !isFilled[currentY * resolutionX + currentX - 1] && getColorARGB(currentX - 1, currentY) == oldColorARGB) {
					stackX[stackLength] = currentX - 1;
					stackY[stackLength] = currentY;
					
					stackLength++;
				}
				
				if(currentY + 1 <= maximumY && !isFilled[(currentY + 1) * resolutionX + currentX] && getColorARGB(currentX, currentY + 1) == oldColorARGB) {
					stackX[stackLength] = currentX;
					stackY[stackLength] = currentY + 1;
					
					stackLength++;
				}
				
				if(currentY - 1 >= minimumY && !isFilled[(currentY - 1) * resolutionX + currentX] && getColorARGB(currentX, currentY - 1) == oldColorARGB) {
					stackX[stackLength] = currentX;
					stackY[stackLength] = currentY - 1;
					
					stackLength++;
				}
			}
		}
	}
}