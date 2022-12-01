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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.macroing.art4j.color.ArrayComponentOrder;
import org.macroing.art4j.color.Color3D;
import org.macroing.art4j.color.Color3F;
import org.macroing.art4j.color.Color3I;
import org.macroing.art4j.color.Color4D;
import org.macroing.art4j.color.Color4F;
import org.macroing.art4j.color.Color4I;
import org.macroing.art4j.color.ColorSpaceD;
import org.macroing.art4j.color.ColorSpaceF;
import org.macroing.art4j.color.PackedIntComponentOrder;
import org.macroing.art4j.data.Data;
import org.macroing.art4j.data.DataFactory;
import org.macroing.art4j.filter.Filter2D;
import org.macroing.art4j.filter.Filter2F;
import org.macroing.art4j.kernel.ConvolutionKernelND;
import org.macroing.art4j.kernel.ConvolutionKernelNF;
import org.macroing.art4j.pixel.Color4DBiPixelOperator;
import org.macroing.art4j.pixel.Color4DPixelFilter;
import org.macroing.art4j.pixel.Color4DPixelOperator;
import org.macroing.art4j.pixel.Color4FBiPixelOperator;
import org.macroing.art4j.pixel.Color4FPixelFilter;
import org.macroing.art4j.pixel.Color4FPixelOperator;
import org.macroing.art4j.pixel.PackedIntARGBPixelFilter;
import org.macroing.art4j.pixel.PackedIntARGBPixelOperator;
import org.macroing.art4j.pixel.PixelTransformer;
import org.macroing.geo4j.common.Point2I;
import org.macroing.geo4j.shape.Shape2I;
import org.macroing.geo4j.shape.rectangle.Rectangle2I;
import org.macroing.java.awt.image.BufferedImages;
import org.macroing.java.lang.Doubles;
import org.macroing.java.lang.Floats;
import org.macroing.java.lang.Ints;
import org.macroing.java.util.Arrays;

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
	 * new Image(bufferedImage, DataFactory.forPackedIntARGB());
	 * }
	 * </pre>
	 * 
	 * @param bufferedImage a {@code BufferedImage} instance
	 * @throws NullPointerException thrown if, and only if, {@code bufferedImage} is {@code null}
	 */
	public Image(final BufferedImage bufferedImage) {
		this(bufferedImage, DataFactory.forPackedIntARGB());
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
	 * Constructs a new {@code Image} instance from {@code data}.
	 * <p>
	 * If {@code data} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param data the {@link Data} instance to copy
	 * @param isIgnoringChangeHistory {@code true} if, and only if, the change history should be ignored, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code data} is {@code null}
	 */
	public Image(final Data data, final boolean isIgnoringChangeHistory) {
		this.data = data.copy(isIgnoringChangeHistory);
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
	 * new Image(file, DataFactory.forPackedIntARGB());
	 * }
	 * </pre>
	 * 
	 * @param file a {@code File} that represents the file to read from
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public Image(final File file) {
		this(file, DataFactory.forPackedIntARGB());
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
	 * new Image(pathname, DataFactory.forPackedIntARGB());
	 * }
	 * </pre>
	 * 
	 * @param pathname a {@code String} that represents the pathname of the file to read from
	 * @throws NullPointerException thrown if, and only if, {@code pathname} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public Image(final String pathname) {
		this(pathname, DataFactory.forPackedIntARGB());
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
	 * new Image(uRL, DataFactory.forPackedIntARGB());
	 * }
	 * </pre>
	 * 
	 * @param uRL a {@code URL} that represents the URL to read from
	 * @throws NullPointerException thrown if, and only if, {@code uRL} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public Image(final URL uRL) {
		this(uRL, DataFactory.forPackedIntARGB());
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
	 * Constructs a new {@code Image} instance with a resolution of {@code resolutionX} and {@code resolutionY}.
	 * <p>
	 * If either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Image(resolutionX, resolutionY, DataFactory.forPackedIntARGB());
	 * }
	 * </pre>
	 * 
	 * @param resolutionX the resolution along the X-axis
	 * @param resolutionY the resolution along the Y-axis
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows
	 */
	public Image(final int resolutionX, final int resolutionY) {
		this(resolutionX, resolutionY, DataFactory.forPackedIntARGB());
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
	 * new Image(resolutionX, resolutionY, color, DataFactory.forColor4D());
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
		this(resolutionX, resolutionY, color, DataFactory.forColor4D());
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
	 * new Image(resolutionX, resolutionY, color, DataFactory.forColor4F());
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
		this(resolutionX, resolutionY, color, DataFactory.forColor4F());
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
	
	/**
	 * Constructs a new {@code Image} instance with a resolution of {@code resolutionX} and {@code resolutionY}.
	 * <p>
	 * If either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code dataFactory} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param resolutionX the resolution along the X-axis
	 * @param resolutionY the resolution along the Y-axis
	 * @param dataFactory a {@link DataFactory} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows
	 * @throws NullPointerException thrown if, and only if, {@code dataFactory} is {@code null}
	 */
	public Image(final int resolutionX, final int resolutionY, final DataFactory dataFactory) {
		this.data = dataFactory.create(resolutionX, resolutionY);
	}
	
	/**
	 * Constructs a new {@code Image} instance filled with {@code color}.
	 * <p>
	 * If either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Image(resolutionX, resolutionY, color, DataFactory.forPackedIntARGB());
	 * }
	 * </pre>
	 * 
	 * @param resolutionX the resolution along the X-axis
	 * @param resolutionY the resolution along the Y-axis
	 * @param color the color in the format ARGB to fill with
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows
	 */
	public Image(final int resolutionX, final int resolutionY, final int color) {
		this(resolutionX, resolutionY, color, DataFactory.forPackedIntARGB());
	}
	
	/**
	 * Constructs a new {@code Image} instance filled with {@code color} using {@code dataFactory}.
	 * <p>
	 * If either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code dataFactory} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param resolutionX the resolution along the X-axis
	 * @param resolutionY the resolution along the Y-axis
	 * @param color the color in the format ARGB to fill with
	 * @param dataFactory a {@link DataFactory} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows
	 * @throws NullPointerException thrown if, and only if, {@code dataFactory} is {@code null}
	 */
	public Image(final int resolutionX, final int resolutionY, final int color, final DataFactory dataFactory) {
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
	 * image.getColor3D(point, PixelTransformer.DEFAULT);
	 * }
	 * </pre>
	 * 
	 * @param point a {@link Point2I} instance that contains the X- and Y-components of the pixel
	 * @return the {@code Color3D} at {@code point} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Color3D getColor3D(final Point2I point) {
		return getColor3D(point, PixelTransformer.DEFAULT);
	}
	
	/**
	 * Returns the {@link Color3D} at {@code point} in this {@code Image} instance.
	 * <p>
	 * If either {@code point} or {@code pixelTransformer} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code point.x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color3D.BLACK} will be returned.
	 * <p>
	 * If the transformed representation of {@code point.y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color3D.BLACK} will be returned.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor3D(point.x, point.y, pixelTransformer);
	 * }
	 * </pre>
	 * 
	 * @param point a {@link Point2I} instance that contains the X- and Y-components of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code point.x} and {@code point.y}
	 * @return the {@code Color3D} at {@code point} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code point} or {@code pixelTransformer} are {@code null}
	 */
	public Color3D getColor3D(final Point2I point, final PixelTransformer pixelTransformer) {
		return getColor3D(point.x, point.y, pixelTransformer);
	}
	
	/**
	 * Returns the {@link Color3D} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionX()}, {@code Color3D.BLACK} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionY()}, {@code Color3D.BLACK} will be returned.
	 * <p>
	 * If both {@code x} and {@code y} are equal to mathematical integers, this method is equivalent to {@link #getColor3D(int, int)}. Otherwise, bilinear interpolation will be performed on the closest pixels.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor3D(x, y, PixelTransformer.DEFAULT);
	 * }
	 * </pre>
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color3D} at {@code x} and {@code y} in this {@code Image} instance
	 */
	public Color3D getColor3D(final double x, final double y) {
		return this.data.getColor3D(x, y);
	}
	
	/**
	 * Returns the {@link Color3D} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code pixelTransformer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code x} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionX()}, {@code Color3D.BLACK} will be returned.
	 * <p>
	 * If the transformed representation of {@code y} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionY()}, {@code Color3D.BLACK} will be returned.
	 * <p>
	 * If the transformed representations of both {@code x} and {@code y} are equal to mathematical integers, this method is equivalent to {@link #getColor3D(int, int, PixelTransformer)}. Otherwise, bilinear interpolation will be performed on the closest pixels.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code x} and {@code y}
	 * @return the {@code Color3D} at {@code x} and {@code y} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelTransformer} is {@code null}
	 */
	public Color3D getColor3D(final double x, final double y, final PixelTransformer pixelTransformer) {
		return this.data.getColor3D(x, y, pixelTransformer);
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
	 * Returns the {@link Color3D} at {@code index} in this {@code Image} instance.
	 * <p>
	 * If {@code pixelTransformer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code index} is less than {@code 0} or greater than or equal to {@code image.getResolution()}, {@code Color3D.BLACK} will be returned.
	 * 
	 * @param index the index of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code index}
	 * @return the {@code Color3D} at {@code index} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelTransformer} is {@code null}
	 */
	public Color3D getColor3D(final int index, final PixelTransformer pixelTransformer) {
		return this.data.getColor3D(index, pixelTransformer);
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
	 * Returns the {@link Color3D} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code pixelTransformer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color3D.BLACK} will be returned.
	 * <p>
	 * If the transformed representation of {@code y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color3D.BLACK} will be returned.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code x} and {@code y}
	 * @return the {@code Color3D} at {@code x} and {@code y} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelTransformer} is {@code null}
	 */
	public Color3D getColor3D(final int x, final int y, final PixelTransformer pixelTransformer) {
		return this.data.getColor3D(x, y, pixelTransformer);
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
	 * image.getColor3F(point, PixelTransformer.DEFAULT);
	 * }
	 * </pre>
	 * 
	 * @param point a {@link Point2I} instance that contains the X- and Y-components of the pixel
	 * @return the {@code Color3F} at {@code point} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Color3F getColor3F(final Point2I point) {
		return getColor3F(point, PixelTransformer.DEFAULT);
	}
	
	/**
	 * Returns the {@link Color3F} at {@code point} in this {@code Image} instance.
	 * <p>
	 * If either {@code point} or {@code pixelTransformer} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code point.x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color3F.BLACK} will be returned.
	 * <p>
	 * If the transformed representation of {@code point.y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color3F.BLACK} will be returned.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor3F(point.x, point.y, pixelTransformer);
	 * }
	 * </pre>
	 * 
	 * @param point a {@link Point2I} instance that contains the X- and Y-components of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code point.x} and {@code point.y}
	 * @return the {@code Color3F} at {@code point} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code point} or {@code pixelTransformer} are {@code null}
	 */
	public Color3F getColor3F(final Point2I point, final PixelTransformer pixelTransformer) {
		return getColor3F(point.x, point.y, pixelTransformer);
	}
	
	/**
	 * Returns the {@link Color3F} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0.0F} or greater than or equal to {@code image.getResolutionX()}, {@code Color3F.BLACK} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0.0F} or greater than or equal to {@code image.getResolutionY()}, {@code Color3F.BLACK} will be returned.
	 * <p>
	 * If both {@code x} and {@code y} are equal to mathematical integers, this method is equivalent to {@link #getColor3F(int, int)}. Otherwise, bilinear interpolation will be performed on the closest pixels.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor3F(x, y, PixelTransformer.DEFAULT);
	 * }
	 * </pre>
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color3F} at {@code x} and {@code y} in this {@code Image} instance
	 */
	public Color3F getColor3F(final float x, final float y) {
		return this.data.getColor3F(x, y);
	}
	
	/**
	 * Returns the {@link Color3F} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code pixelTransformer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code x} is less than {@code 0.0F} or greater than or equal to {@code image.getResolutionX()}, {@code Color3F.BLACK} will be returned.
	 * <p>
	 * If the transformed representation of {@code y} is less than {@code 0.0F} or greater than or equal to {@code image.getResolutionY()}, {@code Color3F.BLACK} will be returned.
	 * <p>
	 * If the transformed representations of both {@code x} and {@code y} are equal to mathematical integers, this method is equivalent to {@link #getColor3F(int, int, PixelTransformer)}. Otherwise, bilinear interpolation will be performed on the closest pixels.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code x} and {@code y}
	 * @return the {@code Color3F} at {@code x} and {@code y} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelTransformer} is {@code null}
	 */
	public Color3F getColor3F(final float x, final float y, final PixelTransformer pixelTransformer) {
		return this.data.getColor3F(x, y, pixelTransformer);
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
	 * Returns the {@link Color3F} at {@code index} in this {@code Image} instance.
	 * <p>
	 * If {@code pixelTransformer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code index} is less than {@code 0} or greater than or equal to {@code image.getResolution()}, {@code Color3F.BLACK} will be returned.
	 * 
	 * @param index the index of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code index}
	 * @return the {@code Color3F} at {@code index} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelTransformer} is {@code null}
	 */
	public Color3F getColor3F(final int index, final PixelTransformer pixelTransformer) {
		return this.data.getColor3F(index, pixelTransformer);
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
	 * Returns the {@link Color3F} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code pixelTransformer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color3F.BLACK} will be returned.
	 * <p>
	 * If the transformed representation of {@code y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color3F.BLACK} will be returned.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code x} and {@code y}
	 * @return the {@code Color3F} at {@code x} and {@code y} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelTransformer} is {@code null}
	 */
	public Color3F getColor3F(final int x, final int y, final PixelTransformer pixelTransformer) {
		return this.data.getColor3F(x, y, pixelTransformer);
	}
	
	/**
	 * Returns the {@link Color3I} at {@code point} in this {@code Image} instance.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code point.x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color3I.BLACK} will be returned.
	 * <p>
	 * If {@code point.y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color3I.BLACK} will be returned.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor3I(point, PixelTransformer.DEFAULT);
	 * }
	 * </pre>
	 * 
	 * @param point a {@link Point2I} instance that contains the X- and Y-components of the pixel
	 * @return the {@code Color3I} at {@code point} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Color3I getColor3I(final Point2I point) {
		return getColor3I(point, PixelTransformer.DEFAULT);
	}
	
	/**
	 * Returns the {@link Color3I} at {@code point} in this {@code Image} instance.
	 * <p>
	 * If either {@code point} or {@code pixelTransformer} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code point.x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color3I.BLACK} will be returned.
	 * <p>
	 * If the transformed representation of {@code point.y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color3I.BLACK} will be returned.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor3I(point.x, point.y, pixelTransformer);
	 * }
	 * </pre>
	 * 
	 * @param point a {@link Point2I} instance that contains the X- and Y-components of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code point.x} and {@code point.y}
	 * @return the {@code Color3I} at {@code point} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code point} or {@code pixelTransformer} are {@code null}
	 */
	public Color3I getColor3I(final Point2I point, final PixelTransformer pixelTransformer) {
		return getColor3I(point.x, point.y, pixelTransformer);
	}
	
	/**
	 * Returns the {@link Color3I} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionX()}, {@code Color3I.BLACK} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionY()}, {@code Color3I.BLACK} will be returned.
	 * <p>
	 * If both {@code x} and {@code y} are equal to mathematical integers, this method is equivalent to {@link #getColor3I(int, int)}. Otherwise, bilinear interpolation will be performed on the closest pixels.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor3I(x, y, PixelTransformer.DEFAULT);
	 * }
	 * </pre>
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color3I} at {@code x} and {@code y} in this {@code Image} instance
	 */
	public Color3I getColor3I(final double x, final double y) {
		return this.data.getColor3I(x, y);
	}
	
	/**
	 * Returns the {@link Color3I} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code pixelTransformer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code x} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionX()}, {@code Color3I.BLACK} will be returned.
	 * <p>
	 * If the transformed representation of {@code y} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionY()}, {@code Color3I.BLACK} will be returned.
	 * <p>
	 * If the transformed representations of both {@code x} and {@code y} are equal to mathematical integers, this method is equivalent to {@link #getColor3I(int, int, PixelTransformer)}. Otherwise, bilinear interpolation will be performed on the closest pixels.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code x} and {@code y}
	 * @return the {@code Color3I} at {@code x} and {@code y} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelTransformer} is {@code null}
	 */
	public Color3I getColor3I(final double x, final double y, final PixelTransformer pixelTransformer) {
		return this.data.getColor3I(x, y, pixelTransformer);
	}
	
	/**
	 * Returns the {@link Color3I} at {@code index} in this {@code Image} instance.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code image.getResolution()}, {@code Color3I.BLACK} will be returned.
	 * 
	 * @param index the index of the pixel
	 * @return the {@code Color3I} at {@code index} in this {@code Image} instance
	 */
	public Color3I getColor3I(final int index) {
		return this.data.getColor3I(index);
	}
	
	/**
	 * Returns the {@link Color3I} at {@code index} in this {@code Image} instance.
	 * <p>
	 * If {@code pixelTransformer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code index} is less than {@code 0} or greater than or equal to {@code image.getResolution()}, {@code Color3I.BLACK} will be returned.
	 * 
	 * @param index the index of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code index}
	 * @return the {@code Color3I} at {@code index} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelTransformer} is {@code null}
	 */
	public Color3I getColor3I(final int index, final PixelTransformer pixelTransformer) {
		return this.data.getColor3I(index, pixelTransformer);
	}
	
	/**
	 * Returns the {@link Color3I} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color3I.BLACK} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color3I.BLACK} will be returned.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color3I} at {@code x} and {@code y} in this {@code Image} instance
	 */
	public Color3I getColor3I(final int x, final int y) {
		return this.data.getColor3I(x, y);
	}
	
	/**
	 * Returns the {@link Color3I} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code pixelTransformer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color3I.BLACK} will be returned.
	 * <p>
	 * If the transformed representation of {@code y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color3I.BLACK} will be returned.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code x} and {@code y}
	 * @return the {@code Color3I} at {@code x} and {@code y} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelTransformer} is {@code null}
	 */
	public Color3I getColor3I(final int x, final int y, final PixelTransformer pixelTransformer) {
		return this.data.getColor3I(x, y, pixelTransformer);
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
	 * image.getColor4D(point, PixelTransformer.DEFAULT);
	 * }
	 * </pre>
	 * 
	 * @param point a {@link Point2I} instance that contains the X- and Y-components of the pixel
	 * @return the {@code Color4D} at {@code point} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Color4D getColor4D(final Point2I point) {
		return getColor4D(point, PixelTransformer.DEFAULT);
	}
	
	/**
	 * Returns the {@link Color4D} at {@code point} in this {@code Image} instance.
	 * <p>
	 * If either {@code point} or {@code pixelTransformer} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code point.x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color4D.TRANSPARENT} will be returned.
	 * <p>
	 * If the transformed representation of {@code point.y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color4D.TRANSPARENT} will be returned.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor4D(point.x, point.y, pixelTransformer);
	 * }
	 * </pre>
	 * 
	 * @param point a {@link Point2I} instance that contains the X- and Y-components of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code point.x} and {@code point.y}
	 * @return the {@code Color4D} at {@code point} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code point} or {@code pixelTransformer} are {@code null}
	 */
	public Color4D getColor4D(final Point2I point, final PixelTransformer pixelTransformer) {
		return getColor4D(point.x, point.y, pixelTransformer);
	}
	
	/**
	 * Returns the {@link Color4D} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionX()}, {@code Color4D.TRANSPARENT} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionY()}, {@code Color4D.TRANSPARENT} will be returned.
	 * <p>
	 * If both {@code x} and {@code y} are equal to mathematical integers, this method is equivalent to {@link #getColor4D(int, int)}. Otherwise, bilinear interpolation will be performed on the closest pixels.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor4D(x, y, PixelTransformer.DEFAULT);
	 * }
	 * </pre>
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color4D} at {@code x} and {@code y} in this {@code Image} instance
	 */
	public Color4D getColor4D(final double x, final double y) {
		return this.data.getColor4D(x, y);
	}
	
	/**
	 * Returns the {@link Color4D} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code pixelTransformer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code x} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionX()}, {@code Color4D.TRANSPARENT} will be returned.
	 * <p>
	 * If the transformed representation of {@code y} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionY()}, {@code Color4D.TRANSPARENT} will be returned.
	 * <p>
	 * If the transformed representations of both {@code x} and {@code y} are equal to mathematical integers, this method is equivalent to {@link #getColor4D(int, int, PixelTransformer)}. Otherwise, bilinear interpolation will be performed on the closest pixels.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code x} and {@code y}
	 * @return the {@code Color4D} at {@code x} and {@code y} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelTransformer} is {@code null}
	 */
	public Color4D getColor4D(final double x, final double y, final PixelTransformer pixelTransformer) {
		return this.data.getColor4D(x, y, pixelTransformer);
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
	 * Returns the {@link Color4D} at {@code index} in this {@code Image} instance.
	 * <p>
	 * If {@code pixelTransformer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code index} is less than {@code 0} or greater than or equal to {@code image.getResolution()}, {@code Color4D.TRANSPARENT} will be returned.
	 * 
	 * @param index the index of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code index}
	 * @return the {@code Color4D} at {@code index} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelTransformer} is {@code null}
	 */
	public Color4D getColor4D(final int index, final PixelTransformer pixelTransformer) {
		return this.data.getColor4D(index, pixelTransformer);
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
	 * Returns the {@link Color4D} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code pixelTransformer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color4D.TRANSPARENT} will be returned.
	 * <p>
	 * If the transformed representation of {@code y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color4D.TRANSPARENT} will be returned.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code x} and {@code y}
	 * @return the {@code Color4D} at {@code x} and {@code y} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelTransformer} is {@code null}
	 */
	public Color4D getColor4D(final int x, final int y, final PixelTransformer pixelTransformer) {
		return this.data.getColor4D(x, y, pixelTransformer);
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
	 * image.getColor4F(point, PixelTransformer.DEFAULT);
	 * }
	 * </pre>
	 * 
	 * @param point a {@link Point2I} instance that contains the X- and Y-components of the pixel
	 * @return the {@code Color4F} at {@code point} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Color4F getColor4F(final Point2I point) {
		return getColor4F(point, PixelTransformer.DEFAULT);
	}
	
	/**
	 * Returns the {@link Color4F} at {@code point} in this {@code Image} instance.
	 * <p>
	 * If either {@code point} or {@code pixelTransformer} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code point.x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color4F.TRANSPARENT} will be returned.
	 * <p>
	 * If the transformed representation of {@code point.y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color4F.TRANSPARENT} will be returned.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor4F(point.x, point.y, pixelTransformer);
	 * }
	 * </pre>
	 * 
	 * @param point a {@link Point2I} instance that contains the X- and Y-components of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code point.x} and {@code point.y}
	 * @return the {@code Color4F} at {@code point} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code point} or {@code pixelTransformer} are {@code null}
	 */
	public Color4F getColor4F(final Point2I point, final PixelTransformer pixelTransformer) {
		return getColor4F(point.x, point.y, pixelTransformer);
	}
	
	/**
	 * Returns the {@link Color4F} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0.0F} or greater than or equal to {@code image.getResolutionX()}, {@code Color4F.TRANSPARENT} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0.0F} or greater than or equal to {@code image.getResolutionY()}, {@code Color4F.TRANSPARENT} will be returned.
	 * <p>
	 * If both {@code x} and {@code y} are equal to mathematical integers, this method is equivalent to {@link #getColor4F(int, int)}. Otherwise, bilinear interpolation will be performed on the closest pixels.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor4F(x, y, PixelTransformer.DEFAULT);
	 * }
	 * </pre>
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color4F} at {@code x} and {@code y} in this {@code Image} instance
	 */
	public Color4F getColor4F(final float x, final float y) {
		return this.data.getColor4F(x, y);
	}
	
	/**
	 * Returns the {@link Color4F} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code pixelTransformer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code x} is less than {@code 0.0F} or greater than or equal to {@code image.getResolutionX()}, {@code Color4F.TRANSPARENT} will be returned.
	 * <p>
	 * If the transformed representation of {@code y} is less than {@code 0.0F} or greater than or equal to {@code image.getResolutionY()}, {@code Color4F.TRANSPARENT} will be returned.
	 * <p>
	 * If the transformed representations of both {@code x} and {@code y} are equal to mathematical integers, this method is equivalent to {@link #getColor4F(int, int, PixelTransformer)}. Otherwise, bilinear interpolation will be performed on the closest pixels.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code x} and {@code y}
	 * @return the {@code Color4F} at {@code x} and {@code y} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelTransformer} is {@code null}
	 */
	public Color4F getColor4F(final float x, final float y, final PixelTransformer pixelTransformer) {
		return this.data.getColor4F(x, y, pixelTransformer);
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
	 * Returns the {@link Color4F} at {@code index} in this {@code Image} instance.
	 * <p>
	 * If {@code pixelTransformer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code index} is less than {@code 0} or greater than or equal to {@code image.getResolution()}, {@code Color4F.TRANSPARENT} will be returned.
	 * 
	 * @param index the index of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code index}
	 * @return the {@code Color4F} at {@code index} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelTransformer} is {@code null}
	 */
	public Color4F getColor4F(final int index, final PixelTransformer pixelTransformer) {
		return this.data.getColor4F(index, pixelTransformer);
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
	 * Returns the {@link Color4F} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code pixelTransformer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color4F.TRANSPARENT} will be returned.
	 * <p>
	 * If the transformed representation of {@code y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color4F.TRANSPARENT} will be returned.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code x} and {@code y}
	 * @return the {@code Color4F} at {@code x} and {@code y} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelTransformer} is {@code null}
	 */
	public Color4F getColor4F(final int x, final int y, final PixelTransformer pixelTransformer) {
		return this.data.getColor4F(x, y, pixelTransformer);
	}
	
	/**
	 * Returns the {@link Color4I} at {@code point} in this {@code Image} instance.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code point.x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color4I.TRANSPARENT} will be returned.
	 * <p>
	 * If {@code point.y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color4I.TRANSPARENT} will be returned.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor4I(point, PixelTransformer.DEFAULT);
	 * }
	 * </pre>
	 * 
	 * @param point a {@link Point2I} instance that contains the X- and Y-components of the pixel
	 * @return the {@code Color4I} at {@code point} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Color4I getColor4I(final Point2I point) {
		return getColor4I(point, PixelTransformer.DEFAULT);
	}
	
	/**
	 * Returns the {@link Color4I} at {@code point} in this {@code Image} instance.
	 * <p>
	 * If either {@code point} or {@code pixelTransformer} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code point.x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color4I.TRANSPARENT} will be returned.
	 * <p>
	 * If the transformed representation of {@code point.y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color4I.TRANSPARENT} will be returned.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor4I(point.x, point.y, pixelTransformer);
	 * }
	 * </pre>
	 * 
	 * @param point a {@link Point2I} instance that contains the X- and Y-components of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code point.x} and {@code point.y}
	 * @return the {@code Color4I} at {@code point} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code point} or {@code pixelTransformer} are {@code null}
	 */
	public Color4I getColor4I(final Point2I point, final PixelTransformer pixelTransformer) {
		return getColor4I(point.x, point.y, pixelTransformer);
	}
	
	/**
	 * Returns the {@link Color4I} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionX()}, {@code Color4I.TRANSPARENT} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionY()}, {@code Color4I.TRANSPARENT} will be returned.
	 * <p>
	 * If both {@code x} and {@code y} are equal to mathematical integers, this method is equivalent to {@link #getColor4I(int, int)}. Otherwise, bilinear interpolation will be performed on the closest pixels.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor4I(x, y, PixelTransformer.DEFAULT);
	 * }
	 * </pre>
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color4I} at {@code x} and {@code y} in this {@code Image} instance
	 */
	public Color4I getColor4I(final double x, final double y) {
		return this.data.getColor4I(x, y);
	}
	
	/**
	 * Returns the {@link Color4I} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code pixelTransformer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code x} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionX()}, {@code Color4I.TRANSPARENT} will be returned.
	 * <p>
	 * If the transformed representation of {@code y} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionY()}, {@code Color4I.TRANSPARENT} will be returned.
	 * <p>
	 * If the transformed representations of both {@code x} and {@code y} are equal to mathematical integers, this method is equivalent to {@link #getColor4I(int, int, PixelTransformer)}. Otherwise, bilinear interpolation will be performed on the closest pixels.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code x} and {@code y}
	 * @return the {@code Color4I} at {@code x} and {@code y} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelTransformer} is {@code null}
	 */
	public Color4I getColor4I(final double x, final double y, final PixelTransformer pixelTransformer) {
		return this.data.getColor4I(x, y, pixelTransformer);
	}
	
	/**
	 * Returns the {@link Color4I} at {@code index} in this {@code Image} instance.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code image.getResolution()}, {@code Color4I.TRANSPARENT} will be returned.
	 * 
	 * @param index the index of the pixel
	 * @return the {@code Color4I} at {@code index} in this {@code Image} instance
	 */
	public Color4I getColor4I(final int index) {
		return this.data.getColor4I(index);
	}
	
	/**
	 * Returns the {@link Color4I} at {@code index} in this {@code Image} instance.
	 * <p>
	 * If {@code pixelTransformer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code index} is less than {@code 0} or greater than or equal to {@code image.getResolution()}, {@code Color4I.TRANSPARENT} will be returned.
	 * 
	 * @param index the index of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code index}
	 * @return the {@code Color4I} at {@code index} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelTransformer} is {@code null}
	 */
	public Color4I getColor4I(final int index, final PixelTransformer pixelTransformer) {
		return this.data.getColor4I(index, pixelTransformer);
	}
	
	/**
	 * Returns the {@link Color4I} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color4I.TRANSPARENT} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color4I.TRANSPARENT} will be returned.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color4I} at {@code x} and {@code y} in this {@code Image} instance
	 */
	public Color4I getColor4I(final int x, final int y) {
		return this.data.getColor4I(x, y);
	}
	
	/**
	 * Returns the {@link Color4I} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code pixelTransformer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the transformed representation of {@code x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color4I.TRANSPARENT} will be returned.
	 * <p>
	 * If the transformed representation of {@code y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color4I.TRANSPARENT} will be returned.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @param pixelTransformer a {@link PixelTransformer} instance that transforms {@code x} and {@code y}
	 * @return the {@code Color4I} at {@code x} and {@code y} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelTransformer} is {@code null}
	 */
	public Color4I getColor4I(final int x, final int y, final PixelTransformer pixelTransformer) {
		return this.data.getColor4I(x, y, pixelTransformer);
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
		
		final int[] indices = doFilterD(pixelFilter);
		
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
		
		final int[] indices = doFilterF(pixelFilter);
		
		this.data.convolve(convolutionKernel, indices);
		
		return this;
	}
	
	/**
	 * Returns a copy of this {@code Image} instance.
	 * 
	 * @return a copy of this {@code Image} instance
	 */
	public Image copy() {
		return new Image(this.data);
	}
	
	/**
	 * Returns a copy of this {@code Image} instance.
	 * <p>
	 * If {@code shape} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method will only copy the data in this {@code Image} instance that is contained by {@code shape}.
	 * <p>
	 * The copied {@code Image} instance will have an X-resolution of {@code shape.max().x - shape.min().x + 1} and a Y-resolution of {@code shape.max().y - shape.min().y + 1}.
	 * 
	 * @param shape a {@link Shape2I} instance
	 * @return a copy of this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code shape} is {@code null}
	 */
	public Image copy(final Shape2I shape) {
		return new Image(this.data.copy(Objects.requireNonNull(shape, "shape == null")));
	}
	
	/**
	 * Returns a copy of this {@code Image} instance.
	 * 
	 * @param isIgnoringChangeHistory {@code true} if, and only if, the change history should be ignored, {@code false} otherwise
	 * @return a copy of this {@code Image} instance
	 */
	public Image copy(final boolean isIgnoringChangeHistory) {
		return new Image(this.data, isIgnoringChangeHistory);
	}
	
	/**
	 * Copies the individual component values of the colors in this {@code Image} instance to the {@code byte[]} {@code array}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code array} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code array.length != image.getResolution() * ArrayComponentOrder.BGRA.getComponentCount()}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.copyTo(array, ArrayComponentOrder.RGBA);
	 * }
	 * </pre>
	 * 
	 * @param array the {@code byte[]} to copy the individual component values of the colors in this {@code Image} instance to
	 * @return this {@code Image} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code array.length != image.getResolution() * ArrayComponentOrder.BGRA.getComponentCount()}
	 * @throws NullPointerException thrown if, and only if, {@code array} is {@code null}
	 */
	public Image copyTo(final byte[] array) {
		return copyTo(array, ArrayComponentOrder.RGBA);
	}
	
	/**
	 * Copies the individual component values of the colors in this {@code Image} instance to the {@code byte[]} {@code array}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code array} or {@code arrayComponentOrder} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code array.length != image.getResolution() * arrayComponentOrder.getComponentCount()}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param array the {@code byte[]} to copy the individual component values of the colors in this {@code Image} instance to
	 * @param arrayComponentOrder an {@link ArrayComponentOrder} to copy the components to {@code array} in the correct order
	 * @return this {@code Image} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code array.length != image.getResolution() * arrayComponentOrder.getComponentCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code array} or {@code arrayComponentOrder} are {@code null}
	 */
	public Image copyTo(final byte[] array, final ArrayComponentOrder arrayComponentOrder) {
		Objects.requireNonNull(array, "array == null");
		Objects.requireNonNull(arrayComponentOrder, "arrayComponentOrder == null");
		
		if(array.length != getResolution() * arrayComponentOrder.getComponentCount()) {
			throw new IllegalArgumentException(String.format("array.length != image.getResolution() * arrayComponentOrder.getComponentCount(): array.length = %d, image.getResolution() = %d, arrayComponentOrder.getComponentCount() = %d", Integer.valueOf(array.length), Integer.valueOf(getResolution()), Integer.valueOf(arrayComponentOrder.getComponentCount())));
		}
		
		final byte[] sourceArray = toByteArray(arrayComponentOrder);
		final byte[] targetArray = array;
		
		System.arraycopy(sourceArray, 0, targetArray, 0, targetArray.length);
		
		return this;
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
	 * Draws {@code shape} to this {@code Image} instance with {@code color} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code color} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is essentially equivalent to the following:
	 * <pre>
	 * {@code
	 * image.drawShapeD(shape, (currentColor, x, y) -> color);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} to draw
	 * @param color the {@link Color4D} to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code color} are {@code null}
	 */
	public Image drawShapeD(final Shape2I shape, final Color4D color) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(color, "color == null");
		
		return drawShapeD(shape, (currentColor, x, y) -> color);
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
	public Image drawShapeD(final Shape2I shape, final Color4DPixelOperator pixelOperator) {
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
	 * image.drawShapeF(shape, (currentColor, x, y) -> color);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} to draw
	 * @param color the {@link Color4F} to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code color} are {@code null}
	 */
	public Image drawShapeF(final Shape2I shape, final Color4F color) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(color, "color == null");
		
		return drawShapeF(shape, (currentColor, x, y) -> color);
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
	public Image drawShapeF(final Shape2I shape, final Color4FPixelOperator pixelOperator) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(pixelOperator, "pixelOperator == null");
		
		this.data.changeBegin();
		
		shape.findPointsOfIntersection(getBounds(), true).forEach(point -> setColor4F(pixelOperator.apply(getColor4F(point), point.x, point.y), point));
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills all pixels in this {@code Image} instance with {@code color}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the {@link Color4D} instance to fill with
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image fillD(final Color4D color) {
		Objects.requireNonNull(color, "color == null");
		
		return fillD((currentColor, x, y) -> color);
	}
	
	/**
	 * Fills all pixels in this {@code Image} instance in the colors provided by {@code pixelOperator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code pixelOperator} is {@code null} or returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillD(pixelOperator, (color, x, y) -> true);
	 * }
	 * </pre>
	 * 
	 * @param pixelOperator a {@link Color4DPixelOperator} instance that returns a {@code Color4D} instance for each pixel affected
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelOperator} is {@code null} or returns {@code null}
	 */
	public Image fillD(final Color4DPixelOperator pixelOperator) {
		return fillD(pixelOperator, (color, x, y) -> true);
	}
	
	/**
	 * Fills all pixels in this {@code Image} instance that are accepted by {@code pixelFilter} in the colors provided by {@code pixelOperator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code pixelOperator} or {@code pixelFilter} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param pixelOperator a {@link Color4DPixelOperator} instance that returns a {@code Color4D} instance for each pixel affected
	 * @param pixelFilter a {@link Color4DPixelFilter} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code pixelOperator} or {@code pixelFilter} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillD(final Color4DPixelOperator pixelOperator, final Color4DPixelFilter pixelFilter) {
		Objects.requireNonNull(pixelOperator, "pixelOperator == null");
		Objects.requireNonNull(pixelFilter, "pixelFilter == null");
		
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		this.data.changeBegin();
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++) {
				final Color4D oldColor = getColor4D(x, y);
				
				if(pixelFilter.isAccepted(oldColor, x, y)) {
					final Color4D newColor = Objects.requireNonNull(pixelOperator.apply(oldColor, x, y));
					
					this.data.setColor4D(newColor, x, y);
				}
			}
		}
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills all pixels in this {@code Image} instance with {@code color}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the {@link Color4F} instance to fill with
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image fillF(final Color4F color) {
		Objects.requireNonNull(color, "color == null");
		
		return fillF((currentColor, x, y) -> color);
	}
	
	/**
	 * Fills all pixels in this {@code Image} instance in the colors provided by {@code pixelOperator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code pixelOperator} is {@code null} or returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillF(operator, (color, x, y) -> true);
	 * }
	 * </pre>
	 * 
	 * @param pixelOperator a {@link Color4FPixelOperator} instance that returns a {@code Color4F} instance for each pixel affected
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelOperator} is {@code null} or returns {@code null}
	 */
	public Image fillF(final Color4FPixelOperator pixelOperator) {
		return fillF(pixelOperator, (color, x, y) -> true);
	}
	
	/**
	 * Fills all pixels in this {@code Image} instance that are accepted by {@code pixelFilter} in the colors provided by {@code pixelOperator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code pixelOperator} or {@code pixelFilter} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param pixelOperator a {@link Color4FPixelOperator} instance that returns a {@code Color4F} instance for each pixel affected
	 * @param pixelFilter a {@link Color4FPixelFilter} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code pixelOperator} or {@code pixelFilter} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillF(final Color4FPixelOperator pixelOperator, final Color4FPixelFilter pixelFilter) {
		Objects.requireNonNull(pixelOperator, "pixelOperator == null");
		Objects.requireNonNull(pixelFilter, "pixelFilter == null");
		
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		this.data.changeBegin();
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++) {
				final Color4F oldColor = getColor4F(x, y);
				
				if(pixelFilter.isAccepted(oldColor, x, y)) {
					final Color4F newColor = Objects.requireNonNull(pixelOperator.apply(oldColor, x, y));
					
					this.data.setColor4F(newColor, x, y);
				}
			}
		}
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills all pixels in this {@code Image} instance in the colors provided by {@code pixelOperator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code pixelOperator} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillI(operator, (color, x, y) -> true);
	 * }
	 * </pre>
	 * 
	 * @param pixelOperator a {@link PackedIntARGBPixelOperator} instance that returns a color for each pixel affected
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelOperator} is {@code null}
	 */
	public Image fillI(final PackedIntARGBPixelOperator pixelOperator) {
		return fillI(pixelOperator, (color, x, y) -> true);
	}
	
	/**
	 * Fills all pixels in this {@code Image} instance that are accepted by {@code pixelFilter} in the colors provided by {@code pixelOperator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code pixelOperator} or {@code pixelFilter} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param pixelOperator a {@link PackedIntARGBPixelOperator} instance that returns a color for each pixel affected
	 * @param pixelFilter a {@link PackedIntARGBPixelFilter} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code pixelOperator} or {@code pixelFilter} are {@code null}
	 */
	public Image fillI(final PackedIntARGBPixelOperator pixelOperator, final PackedIntARGBPixelFilter pixelFilter) {
		Objects.requireNonNull(pixelOperator, "pixelOperator == null");
		Objects.requireNonNull(pixelFilter, "pixelFilter == null");
		
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		this.data.changeBegin();
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++) {
				final int oldColorARGB = getColorARGB(x, y);
				
				if(pixelFilter.isAccepted(oldColorARGB, x, y)) {
					final int newColorARGB = pixelOperator.apply(oldColorARGB, x, y);
					
					this.data.setColorARGB(newColorARGB, x, y);
				}
			}
		}
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills all pixels in this {@code Image} instance with {@code color}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * 
	 * @param color the color to fill with
	 * @return this {@code Image} instance
	 */
	public Image fillI(final int color) {
		return fillI((currentColor, x, y) -> color);
	}
	
	/**
	 * Fills {@code sourceImage} in this {@code Image} instance with {@link Color4D} instances returned by {@code pixelOperator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code pixelOperator} or {@code sourceImage} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillImageD(pixelOperator, sourceImage, sourceImage.getBounds());
	 * }
	 * </pre>
	 * 
	 * @param pixelOperator a {@link Color4DBiPixelOperator} that returns {@code Color4D} instances to use as its color
	 * @param sourceImage the {@code Image} to fill
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code pixelOperator} or {@code sourceImage} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillImageD(final Color4DBiPixelOperator pixelOperator, final Image sourceImage) {
		return fillImageD(pixelOperator, sourceImage, sourceImage.getBounds());
	}
	
	/**
	 * Fills {@code sourceImage} in this {@code Image} instance with {@link Color4D} instances returned by {@code pixelOperator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code pixelOperator}, {@code sourceImage} or {@code targetPosition} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param pixelOperator a {@link Color4DBiPixelOperator} that returns {@code Color4D} instances to use as its color
	 * @param sourceImage the {@code Image} to fill
	 * @param targetPosition a {@link Point2I} that represents the position in this {@code Image} instance to start filling {@code sourceImage}
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code pixelOperator}, {@code sourceImage} or {@code targetPosition} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillImageD(final Color4DBiPixelOperator pixelOperator, final Image sourceImage, final Point2I targetPosition) {
		final Rectangle2I sourceBounds = sourceImage.getBounds();
		
		final Point2I sourceMinimum = sourceBounds.min();
		final Point2I sourceMaximum = sourceBounds.max();
		
		final Rectangle2I targetBounds = new Rectangle2I(targetPosition, new Point2I(targetPosition.x + (sourceMaximum.x - sourceMinimum.x), targetPosition.y + (sourceMaximum.y - sourceMinimum.y)));
		
		return fillImageD(pixelOperator, sourceImage, sourceBounds, targetBounds);
	}
	
	/**
	 * Fills {@code sourceImage} in this {@code Image} instance with {@link Color4D} instances returned by {@code pixelOperator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code pixelOperator}, {@code sourceImage} or {@code sourceBounds} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillImageD(pixelOperator, sourceImage, sourceBounds, image.getBounds());
	 * }
	 * </pre>
	 * 
	 * @param pixelOperator a {@link Color4DBiPixelOperator} that returns {@code Color4D} instances to use as its color
	 * @param sourceImage the {@code Image} to fill
	 * @param sourceBounds a {@link Rectangle2I} that represents the bounds of the region in {@code sourceImage} to use
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code pixelOperator}, {@code sourceImage} or {@code sourceBounds} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillImageD(final Color4DBiPixelOperator pixelOperator, final Image sourceImage, final Rectangle2I sourceBounds) {
		return fillImageD(pixelOperator, sourceImage, sourceBounds, getBounds());
	}
	
	/**
	 * Fills {@code sourceImage} in this {@code Image} instance with {@link Color4D} instances returned by {@code pixelOperator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code pixelOperator}, {@code sourceImage}, {@code sourceBounds} or {@code targetBounds} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param pixelOperator a {@link Color4DBiPixelOperator} that returns {@code Color4D} instances to use as its color
	 * @param sourceImage the {@code Image} to fill
	 * @param sourceBounds a {@link Rectangle2I} that represents the bounds of the region in {@code sourceImage} to use
	 * @param targetBounds a {@code Rectangle2I} that represents the bounds of the region in this {@code Image} instance to use
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code pixelOperator}, {@code sourceImage}, {@code sourceBounds} or {@code targetBounds} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillImageD(final Color4DBiPixelOperator pixelOperator, final Image sourceImage, final Rectangle2I sourceBounds, final Rectangle2I targetBounds) {
		Objects.requireNonNull(pixelOperator, "pixelOperator == null");
		Objects.requireNonNull(sourceImage, "sourceImage == null");
		Objects.requireNonNull(sourceBounds, "sourceBounds == null");
		Objects.requireNonNull(targetBounds, "targetBounds == null");
		
		this.data.changeBegin();
		
		final Image targetImage = this;
		
		final Point2I sourceMinimum = sourceBounds.min();
		final Point2I sourceMaximum = sourceBounds.max();
		final Point2I targetMinimum = targetBounds.min();
		final Point2I targetMaximum = targetBounds.max();
		
		final int sourceMinimumX = sourceMinimum.x;
		final int sourceMinimumY = sourceMinimum.y;
		final int sourceMaximumX = sourceMaximum.x;
		final int sourceMaximumY = sourceMaximum.y;
		final int targetMinimumX = targetMinimum.x;
		final int targetMinimumY = targetMinimum.y;
		final int targetMaximumX = targetMaximum.x;
		final int targetMaximumY = targetMaximum.y;
		
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		for(int sourceY = sourceMinimumY, targetY = targetMinimumY; sourceY <= sourceMaximumY & targetY <= targetMaximumY; sourceY++, targetY++) {
			for(int sourceX = sourceMinimumX, targetX = targetMinimumX; sourceX <= sourceMaximumX & targetX <= targetMaximumX; sourceX++, targetX++) {
				if(targetX >= 0 && targetX < resolutionX && targetY >= 0 && targetY < resolutionY) {
					final Color4D sourceColor = sourceImage.getColor4D(sourceX, sourceY);
					final Color4D targetColor = targetImage.getColor4D(targetX, targetY);
					
					final Color4D color = Objects.requireNonNull(pixelOperator.apply(targetColor, sourceColor, targetX, targetY));
					
					targetImage.setColor4D(color, targetX, targetY);
				}
			}
		}
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills {@code sourceImage} in this {@code Image} instance with {@link Color4F} instances returned by {@code pixelOperator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code pixelOperator} or {@code sourceImage} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillImageF(pixelOperator, sourceImage, sourceImage.getBounds());
	 * }
	 * </pre>
	 * 
	 * @param pixelOperator a {@link Color4FBiPixelOperator} that returns {@code Color4F} instances to use as its color
	 * @param sourceImage the {@code Image} to fill
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code pixelOperator} or {@code sourceImage} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillImageF(final Color4FBiPixelOperator pixelOperator, final Image sourceImage) {
		return fillImageF(pixelOperator, sourceImage, sourceImage.getBounds());
	}
	
	/**
	 * Fills {@code sourceImage} in this {@code Image} instance with {@link Color4F} instances returned by {@code pixelOperator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code pixelOperator}, {@code sourceImage} or {@code targetPosition} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param pixelOperator a {@link Color4FBiPixelOperator} that returns {@code Color4F} instances to use as its color
	 * @param sourceImage the {@code Image} to fill
	 * @param targetPosition a {@link Point2I} that represents the position in this {@code Image} instance to start filling {@code sourceImage}
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code pixelOperator}, {@code sourceImage} or {@code targetPosition} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillImageF(final Color4FBiPixelOperator pixelOperator, final Image sourceImage, final Point2I targetPosition) {
		final Rectangle2I sourceBounds = sourceImage.getBounds();
		
		final Point2I sourceMinimum = sourceBounds.min();
		final Point2I sourceMaximum = sourceBounds.max();
		
		final Rectangle2I targetBounds = new Rectangle2I(targetPosition, new Point2I(targetPosition.x + (sourceMaximum.x - sourceMinimum.x), targetPosition.y + (sourceMaximum.y - sourceMinimum.y)));
		
		return fillImageF(pixelOperator, sourceImage, sourceBounds, targetBounds);
	}
	
	/**
	 * Fills {@code sourceImage} in this {@code Image} instance with {@link Color4F} instances returned by {@code pixelOperator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code pixelOperator}, {@code sourceImage} or {@code sourceBounds} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillImageF(pixelOperator, sourceImage, sourceBounds, image.getBounds());
	 * }
	 * </pre>
	 * 
	 * @param pixelOperator a {@link Color4FBiPixelOperator} that returns {@code Color4F} instances to use as its color
	 * @param sourceImage the {@code Image} to fill
	 * @param sourceBounds a {@link Rectangle2I} that represents the bounds of the region in {@code sourceImage} to use
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code pixelOperator}, {@code sourceImage} or {@code sourceBounds} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillImageF(final Color4FBiPixelOperator pixelOperator, final Image sourceImage, final Rectangle2I sourceBounds) {
		return fillImageF(pixelOperator, sourceImage, sourceBounds, getBounds());
	}
	
	/**
	 * Fills {@code sourceImage} in this {@code Image} instance with {@link Color4F} instances returned by {@code pixelOperator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code pixelOperator}, {@code sourceImage}, {@code sourceBounds} or {@code targetBounds} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param pixelOperator a {@link Color4FBiPixelOperator} that returns {@code Color4F} instances to use as its color
	 * @param sourceImage the {@code Image} to fill
	 * @param sourceBounds a {@link Rectangle2I} that represents the bounds of the region in {@code sourceImage} to use
	 * @param targetBounds a {@code Rectangle2I} that represents the bounds of the region in this {@code Image} instance to use
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code pixelOperator}, {@code sourceImage}, {@code sourceBounds} or {@code targetBounds} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillImageF(final Color4FBiPixelOperator pixelOperator, final Image sourceImage, final Rectangle2I sourceBounds, final Rectangle2I targetBounds) {
		Objects.requireNonNull(pixelOperator, "pixelOperator == null");
		Objects.requireNonNull(sourceImage, "sourceImage == null");
		Objects.requireNonNull(sourceBounds, "sourceBounds == null");
		Objects.requireNonNull(targetBounds, "targetBounds == null");
		
		this.data.changeBegin();
		
		final Image targetImage = this;
		
		final Point2I sourceMinimum = sourceBounds.min();
		final Point2I sourceMaximum = sourceBounds.max();
		final Point2I targetMinimum = targetBounds.min();
		final Point2I targetMaximum = targetBounds.max();
		
		final int sourceMinimumX = sourceMinimum.x;
		final int sourceMinimumY = sourceMinimum.y;
		final int sourceMaximumX = sourceMaximum.x;
		final int sourceMaximumY = sourceMaximum.y;
		final int targetMinimumX = targetMinimum.x;
		final int targetMinimumY = targetMinimum.y;
		final int targetMaximumX = targetMaximum.x;
		final int targetMaximumY = targetMaximum.y;
		
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		for(int sourceY = sourceMinimumY, targetY = targetMinimumY; sourceY <= sourceMaximumY & targetY <= targetMaximumY; sourceY++, targetY++) {
			for(int sourceX = sourceMinimumX, targetX = targetMinimumX; sourceX <= sourceMaximumX & targetX <= targetMaximumX; sourceX++, targetX++) {
				if(targetX >= 0 && targetX < resolutionX && targetY >= 0 && targetY < resolutionY) {
					final Color4F sourceColor = sourceImage.getColor4F(sourceX, sourceY);
					final Color4F targetColor = targetImage.getColor4F(targetX, targetY);
					
					final Color4F color = Objects.requireNonNull(pixelOperator.apply(targetColor, sourceColor, targetX, targetY));
					
					targetImage.setColor4F(color, targetX, targetY);
				}
			}
		}
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code point} in the colors provided by {@code pixelOperator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code point} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRegionD(point, pixelOperator, (color, x, y) -> true);
	 * }
	 * </pre>
	 * 
	 * @param point a {@code Point2I} instance that contains the X- and Y-components of the pixel to start at
	 * @param pixelOperator a {@link Color4DPixelOperator} instance that returns a {@code Color4D} instance for each pixel affected
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code point} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillRegionD(final Point2I point, final Color4DPixelOperator pixelOperator) {
		return fillRegionD(point, pixelOperator, (color, x, y) -> true);
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code point} and accepted by {@code pixelFilter} in the colors provided by {@code pixelOperator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code point}, {@code pixelOperator} or {@code pixelFilter} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRegionD(point.x, point.y, pixelOperator, pixelFilter);
	 * }
	 * </pre>
	 * 
	 * @param point a {@code Point2I} instance that contains the X- and Y-components of the pixel to start at
	 * @param pixelOperator a {@link Color4DPixelOperator} instance that returns a {@code Color4D} instance for each pixel affected
	 * @param pixelFilter a {@link Color4DPixelFilter} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code point}, {@code pixelOperator} or {@code pixelFilter} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillRegionD(final Point2I point, final Color4DPixelOperator pixelOperator, final Color4DPixelFilter pixelFilter) {
		return fillRegionD(point.x, point.y, pixelOperator, pixelFilter);
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code x} and {@code y} in the colors provided by {@code pixelOperator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code pixelOperator} is {@code null} or returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRegionD(x, y, pixelOperator, (color, currentX, currentY) -> true);
	 * }
	 * </pre>
	 * 
	 * @param x the X-component of the pixel to start at
	 * @param y the Y-component of the pixel to start at
	 * @param pixelOperator a {@link Color4DPixelOperator} instance that returns a {@code Color4D} instance for each pixel affected
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelOperator} is {@code null} or returns {@code null}
	 */
	public Image fillRegionD(final int x, final int y, final Color4DPixelOperator pixelOperator) {
		return fillRegionD(x, y, pixelOperator, (color, currentX, currentY) -> true);
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code x} and {@code y} and accepted by {@code pixelFilter} in the colors provided by {@code pixelOperator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code pixelOperator} or {@code pixelFilter} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * 
	 * @param x the X-component of the pixel to start at
	 * @param y the Y-component of the pixel to start at
	 * @param pixelOperator a {@link Color4DPixelOperator} instance that returns a {@code Color4D} instance for each pixel affected
	 * @param pixelFilter a {@link Color4DPixelFilter} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code pixelOperator} or {@code pixelFilter} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillRegionD(final int x, final int y, final Color4DPixelOperator pixelOperator, final Color4DPixelFilter pixelFilter) {
		this.data.changeBegin();
		
		doFillRegionD(x, y, pixelOperator, pixelFilter, getColor4D(x, y));
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code point} in the colors provided by {@code pixelOperator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code point} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRegionF(point, pixelOperator, (color, x, y) -> true);
	 * }
	 * </pre>
	 * 
	 * @param point a {@code Point2I} instance that contains the X- and Y-components of the pixel to start at
	 * @param pixelOperator a {@link Color4FPixelOperator} instance that returns a {@code Color4F} instance for each pixel affected
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code point} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillRegionF(final Point2I point, final Color4FPixelOperator pixelOperator) {
		return fillRegionF(point, pixelOperator, (color, x, y) -> true);
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code point} and accepted by {@code pixelFilter} in the colors provided by {@code pixelOperator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code point}, {@code pixelOperator} or {@code pixelFilter} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRegionF(point.x, point.y, pixelOperator, pixelFilter);
	 * }
	 * </pre>
	 * 
	 * @param point a {@code Point2I} instance that contains the X- and Y-components of the pixel to start at
	 * @param pixelOperator a {@link Color4FPixelOperator} instance that returns a {@code Color4F} instance for each pixel affected
	 * @param pixelFilter a {@link Color4FPixelFilter} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code point}, {@code pixelOperator} or {@code pixelFilter} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillRegionF(final Point2I point, final Color4FPixelOperator pixelOperator, final Color4FPixelFilter pixelFilter) {
		return fillRegionF(point.x, point.y, pixelOperator, pixelFilter);
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code x} and {@code y} in the colors provided by {@code pixelOperator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code pixelOperator} is {@code null} or returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRegionF(x, y, pixelOperator, (color, currentX, currentY) -> true);
	 * }
	 * </pre>
	 * 
	 * @param x the X-component of the pixel to start at
	 * @param y the Y-component of the pixel to start at
	 * @param pixelOperator a {@link Color4FPixelOperator} instance that returns a {@code Color4F} instance for each pixel affected
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelOperator} is {@code null} or returns {@code null}
	 */
	public Image fillRegionF(final int x, final int y, final Color4FPixelOperator pixelOperator) {
		return fillRegionF(x, y, pixelOperator, (color, currentX, currentY) -> true);
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code x} and {@code y} and accepted by {@code pixelFilter} in the colors provided by {@code pixelOperator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code pixelOperator} or {@code pixelFilter} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * 
	 * @param x the X-component of the pixel to start at
	 * @param y the Y-component of the pixel to start at
	 * @param pixelOperator a {@link Color4FPixelOperator} instance that returns a {@code Color4F} instance for each pixel affected
	 * @param pixelFilter a {@link Color4FPixelFilter} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code pixelOperator} or {@code pixelFilter} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillRegionF(final int x, final int y, final Color4FPixelOperator pixelOperator, final Color4FPixelFilter pixelFilter) {
		this.data.changeBegin();
		
		doFillRegionF(x, y, pixelOperator, pixelFilter, getColor4F(x, y));
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code point} in the colors provided by {@code pixelOperator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code point} or {@code pixelOperator} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRegionI(point, pixelOperator, (color, x, y) -> true);
	 * }
	 * </pre>
	 * 
	 * @param point a {@code Point2I} instance that contains the X- and Y-components of the pixel to start at
	 * @param pixelOperator a {@link PackedIntARGBPixelOperator} instance that returns a color for each pixel affected
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code point} or {@code pixelOperator} are {@code null}
	 */
	public Image fillRegionI(final Point2I point, final PackedIntARGBPixelOperator pixelOperator) {
		return fillRegionI(point, pixelOperator, (color, x, y) -> true);
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code point} and accepted by {@code pixelFilter} in the colors provided by {@code pixelOperator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code point}, {@code pixelOperator} or {@code pixelFilter} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRegionI(point.x, point.y, pixelOperator, pixelFilter);
	 * }
	 * </pre>
	 * 
	 * @param point a {@code Point2I} instance that contains the X- and Y-components of the pixel to start at
	 * @param pixelOperator a {@link PackedIntARGBPixelOperator} instance that returns a color for each pixel affected
	 * @param pixelFilter a {@link PackedIntARGBPixelFilter} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code point}, {@code pixelOperator} or {@code pixelFilter} are {@code null}
	 */
	public Image fillRegionI(final Point2I point, final PackedIntARGBPixelOperator pixelOperator, final PackedIntARGBPixelFilter pixelFilter) {
		return fillRegionI(point.x, point.y, pixelOperator, pixelFilter);
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code x} and {@code y} in the colors provided by {@code pixelOperator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code pixelOperator} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRegionI(x, y, pixelOperator, (color, currentX, currentY) -> true);
	 * }
	 * </pre>
	 * 
	 * @param x the X-component of the pixel to start at
	 * @param y the Y-component of the pixel to start at
	 * @param pixelOperator a {@link PackedIntARGBPixelOperator} instance that returns a color for each pixel affected
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelOperator} is {@code null}
	 */
	public Image fillRegionI(final int x, final int y, final PackedIntARGBPixelOperator pixelOperator) {
		return fillRegionI(x, y, pixelOperator, (color, currentX, currentY) -> true);
	}
	
	/**
	 * Fills the region of pixels that are color-connected to the pixel at {@code x} and {@code y} and accepted by {@code pixelFilter} in the colors provided by {@code pixelOperator}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code pixelOperator} or {@code pixelFilter} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This operation works in a similar way to the Bucket Fill tool in Microsoft Paint.
	 * 
	 * @param x the X-component of the pixel to start at
	 * @param y the Y-component of the pixel to start at
	 * @param pixelOperator a {@link PackedIntARGBPixelOperator} instance that returns a color for each pixel affected
	 * @param pixelFilter a {@link PackedIntARGBPixelFilter} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code pixelOperator} or {@code pixelFilter} are {@code null}
	 */
	public Image fillRegionI(final int x, final int y, final PackedIntARGBPixelOperator pixelOperator, final PackedIntARGBPixelFilter pixelFilter) {
		this.data.changeBegin();
		
		doFillRegionI(x, y, pixelOperator, pixelFilter, getColorARGB(x, y));
		
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
	 * image.fillShapeD(shape, (currentColor, x, y) -> color);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} to fill
	 * @param color the {@link Color4D} to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code color} are {@code null}
	 */
	public Image fillShapeD(final Shape2I shape, final Color4D color) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(color, "color == null");
		
		return fillShapeD(shape, (currentColor, x, y) -> color);
	}
	
	/**
	 * Fills {@code shape} in this {@code Image} instance with {@link Color4D} instances returned by {@code pixelOperator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param shape the {@link Shape2I} to fill
	 * @param pixelOperator a {@link Color4DPixelOperator} that returns {@code Color4D} instances to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillShapeD(final Shape2I shape, final Color4DPixelOperator pixelOperator) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(pixelOperator, "pixelOperator == null");
		
		this.data.changeBegin();
		
		shape.findPointsOfIntersection(getBounds()).forEach(point -> setColor4D(pixelOperator.apply(getColor4D(point), point.x, point.y), point));
		
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
	 * image.fillShapeF(shape, (currentColor, x, y) -> color);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} to fill
	 * @param color the {@link Color4F} to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code color} are {@code null}
	 */
	public Image fillShapeF(final Shape2I shape, final Color4F color) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(color, "color == null");
		
		return fillShapeF(shape, (currentColor, x, y) -> color);
	}
	
	/**
	 * Fills {@code shape} in this {@code Image} instance with {@link Color4F} instances returned by {@code pixelOperator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param shape the {@link Shape2I} to fill
	 * @param pixelOperator a {@link Color4FPixelOperator} that returns {@code Color4F} instances to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillShapeF(final Shape2I shape, final Color4FPixelOperator pixelOperator) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(pixelOperator, "pixelOperator == null");
		
		this.data.changeBegin();
		
		shape.findPointsOfIntersection(getBounds()).forEach(point -> setColor4F(pixelOperator.apply(getColor4F(point), point.x, point.y), point));
		
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
	 * image.fillShapeComplementD(shape, color, false);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} not to fill
	 * @param color the {@link Color4D} to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code color} are {@code null}
	 */
	public Image fillShapeComplementD(final Shape2I shape, final Color4D color) {
		return fillShapeComplementD(shape, color, false);
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
	 * image.fillShapeComplementD(shape, (currentColor, x, y) -> color, isExcludingBorderOnly);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} not to fill
	 * @param color the {@link Color4D} to use as its color
	 * @param isExcludingBorderOnly {@code true} if, and only if, this method should only exclude {@link Point2I} instances on the border of {@code shape}, {@code false} otherwise
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code color} are {@code null}
	 */
	public Image fillShapeComplementD(final Shape2I shape, final Color4D color, final boolean isExcludingBorderOnly) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(color, "color == null");
		
		return fillShapeComplementD(shape, (currentColor, x, y) -> color, isExcludingBorderOnly);
	}
	
	/**
	 * Fills everything except for {@code shape} in this {@code Image} instance with {@link Color4D} instances returned by {@code pixelOperator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is essentially equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillShapeComplementD(shape, pixelOperator, false);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} not to fill
	 * @param pixelOperator a {@link Color4DPixelOperator} that returns {@code Color4D} instances to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillShapeComplementD(final Shape2I shape, final Color4DPixelOperator pixelOperator) {
		return fillShapeComplementD(shape, pixelOperator, false);
	}
	
	/**
	 * Fills everything except for {@code shape} in this {@code Image} instance with {@link Color4D} instances returned by {@code pixelOperator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param shape the {@link Shape2I} not to fill
	 * @param pixelOperator a {@link Color4DPixelOperator} that returns {@code Color4D} instances to use as its color
	 * @param isExcludingBorderOnly {@code true} if, and only if, this method should only exclude {@link Point2I} instances on the border of {@code shape}, {@code false} otherwise
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillShapeComplementD(final Shape2I shape, final Color4DPixelOperator pixelOperator, final boolean isExcludingBorderOnly) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(pixelOperator, "pixelOperator == null");
		
		this.data.changeBegin();
		
		shape.findPointsOfComplement(getBounds(), isExcludingBorderOnly).forEach(point -> setColor4D(pixelOperator.apply(getColor4D(point), point.x, point.y), point));
		
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
	 * image.fillShapeComplementF(shape, color, false);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} not to fill
	 * @param color the {@link Color4F} to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code color} are {@code null}
	 */
	public Image fillShapeComplementF(final Shape2I shape, final Color4F color) {
		return fillShapeComplementF(shape, color, false);
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
	 * image.fillShapeComplementF(shape, (currentColor, x, y) -> color, isExcludingBorderOnly);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} not to fill
	 * @param color the {@link Color4F} to use as its color
	 * @param isExcludingBorderOnly {@code true} if, and only if, this method should only exclude {@link Point2I} instances on the border of {@code shape}, {@code false} otherwise
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code color} are {@code null}
	 */
	public Image fillShapeComplementF(final Shape2I shape, final Color4F color, final boolean isExcludingBorderOnly) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(color, "color == null");
		
		return fillShapeComplementF(shape, (currentColor, x, y) -> color, isExcludingBorderOnly);
	}
	
	/**
	 * Fills everything except for {@code shape} in this {@code Image} instance with {@link Color4F} instances returned by {@code pixelOperator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is essentially equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillShapeComplementF(shape, pixelOperator, false);
	 * }
	 * </pre>
	 * 
	 * @param shape the {@link Shape2I} not to fill
	 * @param pixelOperator a {@link Color4FPixelOperator} that returns {@code Color4F} instances to use as its color
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillShapeComplementF(final Shape2I shape, final Color4FPixelOperator pixelOperator) {
		return fillShapeComplementF(shape, pixelOperator, false);
	}
	
	/**
	 * Fills everything except for {@code shape} in this {@code Image} instance with {@link Color4F} instances returned by {@code pixelOperator} as its color.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code shape} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param shape the {@link Shape2I} not to fill
	 * @param pixelOperator a {@link Color4FPixelOperator} that returns {@code Color4F} instances to use as its color
	 * @param isExcludingBorderOnly {@code true} if, and only if, this method should only exclude {@link Point2I} instances on the border of {@code shape}, {@code false} otherwise
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code shape} or {@code pixelOperator} are {@code null} or {@code pixelOperator} returns {@code null}
	 */
	public Image fillShapeComplementF(final Shape2I shape, final Color4FPixelOperator pixelOperator, final boolean isExcludingBorderOnly) {
		Objects.requireNonNull(shape, "shape == null");
		Objects.requireNonNull(pixelOperator, "pixelOperator == null");
		
		this.data.changeBegin();
		
		shape.findPointsOfComplement(getBounds(), isExcludingBorderOnly).forEach(point -> setColor4F(pixelOperator.apply(getColor4F(point), point.x, point.y), point));
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills all pixels in this {@code Image} instance that are accepted by {@code pixelFilter} with {@link Color4D} instances that are generated using a Sobel operator.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code pixelFilter} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param pixelFilter a {@link Color4DPixelFilter} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelFilter} is {@code null}
	 */
	public Image fillSobelD(final Color4DPixelFilter pixelFilter) {
		Objects.requireNonNull(pixelFilter, "pixelFilter == null");
		
		this.data.changeBegin();
		
		final Image image = copy();
		
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		final double[][] kernel = {
			{-1.0D, 0.0D, 1.0D},
			{-2.0D, 0.0D, 2.0D},
			{-1.0D, 0.0D, 1.0D}
		};
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++) {
				final Color4D color = image.getColor4D(x, y);
				
				if(pixelFilter.isAccepted(color, x, y)) {
					double magnitudeX = 0.0D;
					double magnitudeY = 0.0D;
					
					for(int kernelY = 0; kernelY < 3; kernelY++) {
						for(int kernelX = 0; kernelX < 3; kernelX++) {
							final int currentX = x + kernelX - 1;
							final int currentY = y + kernelY - 1;
							
							if(currentX >= 0 && currentX < resolutionX && currentY >= 0 && currentY < resolutionY) {
								final int index = currentY * resolutionX + currentX;
								
								final double intensity = image.getColor3D(index).average();
								
								magnitudeX += intensity * kernel[kernelY][kernelX];
								magnitudeY += intensity * kernel[kernelX][kernelY];
							}
						}
					}
					
					final double grayscale = Doubles.sqrt(magnitudeX * magnitudeX + magnitudeY * magnitudeY);
					final double a = color.a;
					
					setColor4D(new Color4D(grayscale, a), x, y);
				}
			}
		}
		
		this.data.changeEnd();
		
		return this;
	}
	
	/**
	 * Fills all pixels in this {@code Image} instance that are accepted by {@code pixelFilter} with {@link Color4F} instances that are generated using a Sobel operator.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code pixelFilter} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param pixelFilter a {@link Color4FPixelFilter} instance that accepts or rejects pixels
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pixelFilter} is {@code null}
	 */
	public Image fillSobelF(final Color4FPixelFilter pixelFilter) {
		Objects.requireNonNull(pixelFilter, "pixelFilter == null");
		
		this.data.changeBegin();
		
		final Image image = copy();
		
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		final float[][] kernel = {
			{-1.0F, 0.0F, 1.0F},
			{-2.0F, 0.0F, 2.0F},
			{-1.0F, 0.0F, 1.0F}
		};
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++) {
				final Color4F color = image.getColor4F(x, y);
				
				if(pixelFilter.isAccepted(color, x, y)) {
					float magnitudeX = 0.0F;
					float magnitudeY = 0.0F;
					
					for(int kernelY = 0; kernelY < 3; kernelY++) {
						for(int kernelX = 0; kernelX < 3; kernelX++) {
							final int currentX = x + kernelX - 1;
							final int currentY = y + kernelY - 1;
							
							if(currentX >= 0 && currentX < resolutionX && currentY >= 0 && currentY < resolutionY) {
								final int index = currentY * resolutionX + currentX;
								
								final float intensity = image.getColor3F(index).average();
								
								magnitudeX += intensity * kernel[kernelY][kernelX];
								magnitudeY += intensity * kernel[kernelX][kernelY];
							}
						}
					}
					
					final float grayscale = Floats.sqrt(magnitudeX * magnitudeX + magnitudeY * magnitudeY);
					final float a = color.a;
					
					setColor4F(new Color4F(grayscale, a), x, y);
				}
			}
		}
		
		this.data.changeEnd();
		
		return this;
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
	 * image.setColor3I(color, point.x, point.y);
	 * }
	 * </pre>
	 * 
	 * @param color the {@link Color3I} to set
	 * @param point the {@link Point2I} that contains the X- and Y-components of the pixel
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code color} or {@code point} are {@code null}
	 */
	public Image setColor3I(final Color3I color, final Point2I point) {
		return setColor3I(color, point.x, point.y);
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
	 * @param color the {@link Color3I} to set
	 * @param index the index of the pixel
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image setColor3I(final Color3I color, final int index) {
		this.data.setColor3I(Objects.requireNonNull(color, "color == null"), index);
		
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
	 * @param color the {@link Color3I} to set
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image setColor3I(final Color3I color, final int x, final int y) {
		this.data.setColor3I(Objects.requireNonNull(color, "color == null"), x, y);
		
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
	 * image.setColor4I(color, point.x, point.y);
	 * }
	 * </pre>
	 * 
	 * @param color the {@link Color4I} to set
	 * @param point the {@link Point2I} that contains the X- and Y-components of the pixel
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code color} or {@code point} are {@code null}
	 */
	public Image setColor4I(final Color4I color, final Point2I point) {
		return setColor4I(color, point.x, point.y);
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
	 * @param color the {@link Color4I} to set
	 * @param index the index of the pixel
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image setColor4I(final Color4I color, final int index) {
		this.data.setColor4I(Objects.requireNonNull(color, "color == null"), index);
		
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
	 * @param color the {@link Color4I} to set
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image setColor4I(final Color4I color, final int x, final int y) {
		this.data.setColor4I(Objects.requireNonNull(color, "color == null"), x, y);
		
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
	 * Finds the bounds for {@code image} in this {@code Image} instance.
	 * <p>
	 * Returns a {@code List} with all {@link Rectangle2I} bounds found for {@code image} in this {@code Image} instance.
	 * <p>
	 * If {@code image} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param image an {@code Image} instance
	 * @return a {@code List} with all {@code Rectangle2I} bounds found for {@code image} in this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code image} is {@code null}
	 */
	public List<Rectangle2I> findBoundsFor(final Image image) {
		Objects.requireNonNull(image, "image == null");
		
		final List<Rectangle2I> rectangles = new ArrayList<>();
		
		for(int y = 0; y < getResolutionY(); y++) {
			for(int x = 0; x < getResolutionX(); x++) {
				labelImage:
				if(getColorARGB(x, y) == image.getColorARGB(0, 0)) {
					for(int imageY = 0; imageY < image.getResolutionY(); imageY++) {
						for(int imageX = 0; imageX < image.getResolutionX(); imageX++) {
							if(getColorARGB(x + imageX, y + imageY) != image.getColorARGB(imageX, imageY)) {
								break labelImage;
							}
						}
					}
					
					rectangles.add(new Rectangle2I(new Point2I(x, y), new Point2I(x + image.getResolutionX() - 1, y + image.getResolutionY() - 1)));
				}
			}
		}
		
		return rectangles;
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
	 * Returns a {@code byte[]} representation of this {@code Image} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.toByteArray(ArrayComponentOrder.RGBA);
	 * }
	 * </pre>
	 * 
	 * @return a {@code byte[]} representation of this {@code Image} instance
	 */
	public byte[] toByteArray() {
		return toByteArray(ArrayComponentOrder.RGBA);
	}
	
	/**
	 * Returns a {@code byte[]} representation of this {@code Image} instance.
	 * <p>
	 * If {@code arrayComponentOrder} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param arrayComponentOrder an {@link ArrayComponentOrder}
	 * @return a {@code byte[]} representation of this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code arrayComponentOrder} is {@code null}
	 */
	public byte[] toByteArray(final ArrayComponentOrder arrayComponentOrder) {
		return Arrays.toByteArray(toIntArray(Objects.requireNonNull(arrayComponentOrder, "arrayComponentOrder == null")));
	}
	
	/**
	 * Returns the maximum relative luminance in this {@code Image} instance as a {@code double}.
	 * 
	 * @return the maximum relative luminance in this {@code Image} instance as a {@code double}
	 */
	public double relativeLuminanceMaxAsDouble() {
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		double relativeLuminanceMax = -Double.MAX_VALUE;
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++) {
				relativeLuminanceMax = Doubles.max(relativeLuminanceMax, getColor3D(x, y).relativeLuminance());
			}
		}
		
		return relativeLuminanceMax;
	}
	
	/**
	 * Returns the minimum relative luminance in this {@code Image} instance as a {@code double}.
	 * 
	 * @return the minimum relative luminance in this {@code Image} instance as a {@code double}
	 */
	public double relativeLuminanceMinAsDouble() {
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		double relativeLuminanceMin = Double.MAX_VALUE;
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++) {
				relativeLuminanceMin = Doubles.min(relativeLuminanceMin, getColor3D(x, y).relativeLuminance());
			}
		}
		
		return relativeLuminanceMin;
	}
	
	/**
	 * Returns a {@code double[]} representation of this {@code Image} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.toDoubleArray(ArrayComponentOrder.RGBA);
	 * }
	 * </pre>
	 * 
	 * @return a {@code double[]} representation of this {@code Image} instance
	 */
	public double[] toDoubleArray() {
		return toDoubleArray(ArrayComponentOrder.RGBA);
	}
	
	/**
	 * Returns a {@code double[]} representation of this {@code Image} instance.
	 * <p>
	 * If {@code arrayComponentOrder} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param arrayComponentOrder an {@link ArrayComponentOrder}
	 * @return a {@code double[]} representation of this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code arrayComponentOrder} is {@code null}
	 */
	public double[] toDoubleArray(final ArrayComponentOrder arrayComponentOrder) {
		Objects.requireNonNull(arrayComponentOrder, "arrayComponentOrder == null");
		
		final int componentCount = arrayComponentOrder.getComponentCount();
		
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		final double[] array = new double[resolutionX * resolutionY * componentCount];
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0, index = (y * resolutionX + x) * componentCount; x < resolutionX; x++, index += componentCount) {
				final Color4D color = getColor4D(x, y);
				
//				This is currently always true:
//				if(arrayComponentOrder.hasOffsetR()) {
					array[index + arrayComponentOrder.getOffsetR()] = color.r;
//				}
				
//				This is currently always true:
//				if(arrayComponentOrder.hasOffsetG()) {
					array[index + arrayComponentOrder.getOffsetG()] = color.g;
//				}
				
//				This is currently always true:
//				if(arrayComponentOrder.hasOffsetB()) {
					array[index + arrayComponentOrder.getOffsetB()] = color.b;
//				}
				
				if(arrayComponentOrder.hasOffsetA()) {
					array[index + arrayComponentOrder.getOffsetA()] = color.a;
				}
			}
		}
		
		return array;
	}
	
	/**
	 * Returns the maximum relative luminance in this {@code Image} instance as a {@code float}.
	 * 
	 * @return the maximum relative luminance in this {@code Image} instance as a {@code float}
	 */
	public float relativeLuminanceMaxAsFloat() {
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		float relativeLuminanceMax = -Float.MAX_VALUE;
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++) {
				relativeLuminanceMax = Floats.max(relativeLuminanceMax, getColor3F(x, y).relativeLuminance());
			}
		}
		
		return relativeLuminanceMax;
	}
	
	/**
	 * Returns the minimum relative luminance in this {@code Image} instance as a {@code float}.
	 * 
	 * @return the minimum relative luminance in this {@code Image} instance as a {@code float}
	 */
	public float relativeLuminanceMinAsFloat() {
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		float relativeLuminanceMin = Float.MAX_VALUE;
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++) {
				relativeLuminanceMin = Floats.min(relativeLuminanceMin, getColor3F(x, y).relativeLuminance());
			}
		}
		
		return relativeLuminanceMin;
	}
	
	/**
	 * Returns a {@code float[]} representation of this {@code Image} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.toFloatArray(ArrayComponentOrder.RGBA);
	 * }
	 * </pre>
	 * 
	 * @return a {@code float[]} representation of this {@code Image} instance
	 */
	public float[] toFloatArray() {
		return toFloatArray(ArrayComponentOrder.RGBA);
	}
	
	/**
	 * Returns a {@code float[]} representation of this {@code Image} instance.
	 * <p>
	 * If {@code arrayComponentOrder} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param arrayComponentOrder an {@link ArrayComponentOrder}
	 * @return a {@code float[]} representation of this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code arrayComponentOrder} is {@code null}
	 */
	public float[] toFloatArray(final ArrayComponentOrder arrayComponentOrder) {
		Objects.requireNonNull(arrayComponentOrder, "arrayComponentOrder == null");
		
		final int componentCount = arrayComponentOrder.getComponentCount();
		
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		final float[] array = new float[resolutionX * resolutionY * componentCount];
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0, index = (y * resolutionX + x) * componentCount; x < resolutionX; x++, index += componentCount) {
				final Color4F color = getColor4F(x, y);
				
//				This is currently always true:
//				if(arrayComponentOrder.hasOffsetR()) {
					array[index + arrayComponentOrder.getOffsetR()] = color.r;
//				}
				
//				This is currently always true:
//				if(arrayComponentOrder.hasOffsetG()) {
					array[index + arrayComponentOrder.getOffsetG()] = color.g;
//				}
				
//				This is currently always true:
//				if(arrayComponentOrder.hasOffsetB()) {
					array[index + arrayComponentOrder.getOffsetB()] = color.b;
//				}
				
				if(arrayComponentOrder.hasOffsetA()) {
					array[index + arrayComponentOrder.getOffsetA()] = color.a;
				}
			}
		}
		
		return array;
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
	
	/**
	 * Returns an {@code int[]} representation of this {@code Image} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.toIntArray(ArrayComponentOrder.RGBA);
	 * }
	 * </pre>
	 * 
	 * @return an {@code int[]} representation of this {@code Image} instance
	 */
	public int[] toIntArray() {
		return toIntArray(ArrayComponentOrder.RGBA);
	}
	
	/**
	 * Returns an {@code int[]} representation of this {@code Image} instance.
	 * <p>
	 * If {@code arrayComponentOrder} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param arrayComponentOrder an {@link ArrayComponentOrder}
	 * @return an {@code int[]} representation of this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code arrayComponentOrder} is {@code null}
	 */
	public int[] toIntArray(final ArrayComponentOrder arrayComponentOrder) {
		Objects.requireNonNull(arrayComponentOrder, "arrayComponentOrder == null");
		
		final int componentCount = arrayComponentOrder.getComponentCount();
		
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		final int[] array = new int[resolutionX * resolutionY * componentCount];
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0, index = (y * resolutionX + x) * componentCount; x < resolutionX; x++, index += componentCount) {
				final int color = getColorARGB(x, y);
				
//				This is currently always true:
//				if(arrayComponentOrder.hasOffsetR()) {
					array[index + arrayComponentOrder.getOffsetR()] = Color4I.fromIntARGBToIntR(color);
//				}
				
//				This is currently always true:
//				if(arrayComponentOrder.hasOffsetG()) {
					array[index + arrayComponentOrder.getOffsetG()] = Color4I.fromIntARGBToIntG(color);
//				}
				
//				This is currently always true:
//				if(arrayComponentOrder.hasOffsetB()) {
					array[index + arrayComponentOrder.getOffsetB()] = Color4I.fromIntARGBToIntB(color);
//				}
				
				if(arrayComponentOrder.hasOffsetA()) {
					array[index + arrayComponentOrder.getOffsetA()] = Color4I.fromIntARGBToIntA(color);
				}
			}
		}
		
		return array;
	}
	
	/**
	 * Returns an {@code int[]} representation of this {@code Image} instance in a packed form.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.toIntArrayPackedForm(PackedIntComponentOrder.ARGB);
	 * }
	 * </pre>
	 * 
	 * @return an {@code int[]} representation of this {@code Image} instance in a packed form
	 */
	public int[] toIntArrayPackedForm() {
		return toIntArrayPackedForm(PackedIntComponentOrder.ARGB);
	}
	
	/**
	 * Returns an {@code int[]} representation of this {@code Image} instance in a packed form.
	 * <p>
	 * If {@code packedIntComponentOrder} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param packedIntComponentOrder a {@link PackedIntComponentOrder}
	 * @return an {@code int[]} representation of this {@code Image} instance in a packed form
	 * @throws NullPointerException thrown if, and only if, {@code packedIntComponentOrder} is {@code null}
	 */
	public int[] toIntArrayPackedForm(final PackedIntComponentOrder packedIntComponentOrder) {
		return packedIntComponentOrder.pack(ArrayComponentOrder.RGBA, toIntArray());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Creates an {@code Image} instance by capturing the contents of the screen, without the mouse cursor.
	 * <p>
	 * Returns a new {@code Image} instance.
	 * <p>
	 * If {@code rectangle} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the permission {@code readDisplayPixels} is not granted, a {@code SecurityException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Image.createScreenCapture(rectangle, DataFactory.forPackedIntARGB());
	 * }
	 * </pre>
	 * 
	 * @param rectangle a {@link Rectangle2I} that contains the bounds
	 * @return a new {@code PixelImageD} instance
	 * @throws NullPointerException thrown if, and only if, {@code rectangle} is {@code null}
	 * @throws SecurityException thrown if, and only if, the permission {@code readDisplayPixels} is not granted
	 */
	public static Image createScreenCapture(final Rectangle2I rectangle) {
		return createScreenCapture(rectangle, DataFactory.forPackedIntARGB());
	}
	
	/**
	 * Creates an {@code Image} instance by capturing the contents of the screen, without the mouse cursor.
	 * <p>
	 * Returns a new {@code Image} instance.
	 * <p>
	 * If either {@code rectangle} or {@code dataFactory} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the permission {@code readDisplayPixels} is not granted, a {@code SecurityException} will be thrown.
	 * 
	 * @param rectangle a {@link Rectangle2I} that contains the bounds
	 * @param dataFactory a {@link DataFactory} instance
	 * @return a new {@code PixelImageD} instance
	 * @throws NullPointerException thrown if, and only if, either {@code rectangle} or {@code dataFactory} are {@code null}
	 * @throws SecurityException thrown if, and only if, the permission {@code readDisplayPixels} is not granted
	 */
	public static Image createScreenCapture(final Rectangle2I rectangle, final DataFactory dataFactory) {
		Objects.requireNonNull(rectangle, "rectangle == null");
		Objects.requireNonNull(dataFactory, "dataFactory == null");
		
		final Point2I min = rectangle.min();
		final Point2I max = rectangle.max();
		
		final int x = min.x;
		final int y = min.y;
		
		final int width = max.x - min.x + 1;
		final int height = max.y - min.y + 1;
		
		final BufferedImage bufferedImage = BufferedImages.createScreenCapture(x, y, width, height);
		
		return new Image(bufferedImage, dataFactory);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int[] doFilterD(final Color4DPixelFilter pixelFilter) {
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
		
		return java.util.Arrays.stream(indices).filter(index -> index != -1).toArray();
	}
	
	private int[] doFilterF(final Color4FPixelFilter pixelFilter) {
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
		
		return java.util.Arrays.stream(indices).filter(index -> index != -1).toArray();
	}
	
	private void doFillRegionD(final int x, final int y, final Color4DPixelOperator pixelOperator, final Color4DPixelFilter pixelFilter, final Color4D oldColor) {
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
				
				if(pixelFilter.isAccepted(color, currentX, currentY)) {
					this.data.setColor4D(pixelOperator.apply(color, currentX, currentY), currentX, currentY);
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
	
	private void doFillRegionF(final int x, final int y, final Color4FPixelOperator pixelOperator, final Color4FPixelFilter pixelFilter, final Color4F oldColor) {
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
				
				if(pixelFilter.isAccepted(color, currentX, currentY)) {
					this.data.setColor4F(pixelOperator.apply(color, currentX, currentY), currentX, currentY);
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
	
	private void doFillRegionI(final int x, final int y, final PackedIntARGBPixelOperator pixelOperator, final PackedIntARGBPixelFilter pixelFilter, final int oldColorARGB) {
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
				
				if(pixelFilter.isAccepted(colorARGB, currentX, currentY)) {
					this.data.setColorARGB(pixelOperator.apply(colorARGB, currentX, currentY), currentX, currentY);
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