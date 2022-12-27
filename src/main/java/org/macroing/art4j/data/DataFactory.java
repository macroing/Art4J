/**
 * Copyright 2022 - 2023 J&#246;rgen Lundgren
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
package org.macroing.art4j.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.macroing.art4j.color.Color4D;
import org.macroing.art4j.color.Color4F;

/**
 * A {@code DataFactory} creates instances of the {@link Data} class.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class DataFactory {
	/**
	 * Constructs a new {@code DataFactory} instance.
	 */
	protected DataFactory() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code Data} instance with a resolution of {@code 1024} and {@code 768}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * dataFactory.create(1024, 768);
	 * }
	 * </pre>
	 * 
	 * @return a {@code Data} instance with a resolution of {@code 1024} and {@code 768}
	 */
	public final Data create() {
		return create(1024, 768);
	}
	
	/**
	 * Returns a {@code Data} instance from {@code bufferedImage}.
	 * <p>
	 * If {@code bufferedImage} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param bufferedImage a {@code BufferedImage} instance
	 * @return a {@code Data} instance from {@code bufferedImage}
	 * @throws NullPointerException thrown if, and only if, {@code bufferedImage} is {@code null}
	 */
	public abstract Data create(final BufferedImage bufferedImage);
	
	/**
	 * Creates a {@code Data} instance by reading the content of the file represented by {@code file}.
	 * <p>
	 * Returns a new {@code Data} instance.
	 * <p>
	 * If {@code file} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param file a {@code File} that represents the file to read from
	 * @return a new {@code Data} instance
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public final Data create(final File file) {
		try {
			return create(ImageIO.read(Objects.requireNonNull(file, "file == null")));
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Creates a {@code Data} instance by reading the content of the file represented by the pathname {@code pathname}.
	 * <p>
	 * Returns a new {@code Data} instance.
	 * <p>
	 * If {@code pathname} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * dataFactory.create(new File(pathname));
	 * }
	 * </pre>
	 * 
	 * @param pathname a {@code String} that represents the pathname of the file to read from
	 * @return a new {@code Data} instance
	 * @throws NullPointerException thrown if, and only if, {@code pathname} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public final Data create(final String pathname) {
		return create(new File(pathname));
	}
	
	/**
	 * Creates a {@code Data} instance by reading the content of the URL represented by {@code uRL}.
	 * <p>
	 * Returns a new {@code Data} instance.
	 * <p>
	 * If {@code uRL} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param uRL a {@code URL} that represents the URL to read from
	 * @return a new {@code Data} instance
	 * @throws NullPointerException thrown if, and only if, {@code uRL} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public final Data create(final URL uRL) {
		try {
			return create(ImageIO.read(Objects.requireNonNull(uRL, "uRL == null")));
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Returns a {@code Data} instance with a resolution of {@code resolutionX} and {@code resolutionY}.
	 * <p>
	 * If either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param resolutionX the resolution along the X-axis
	 * @param resolutionY the resolution along the Y-axis
	 * @return a {@code Data} instance with a resolution of {@code resolutionX} and {@code resolutionY}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows
	 */
	public abstract Data create(final int resolutionX, final int resolutionY);
	
	/**
	 * Returns a {@code Data} instance with a resolution of {@code resolutionX} and {@code resolutionY} and is filled with a single color represented by {@code color}.
	 * <p>
	 * If either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param resolutionX the resolution along the X-axis
	 * @param resolutionY the resolution along the Y-axis
	 * @param color the {@link Color4D} to fill with
	 * @return a {@code Data} instance with a resolution of {@code resolutionX} and {@code resolutionY} and is filled with a single color represented by {@code color}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public abstract Data create(final int resolutionX, final int resolutionY, final Color4D color);
	
	/**
	 * Returns a {@code Data} instance with a resolution of {@code resolutionX} and {@code resolutionY} and is filled with a single color represented by {@code color}.
	 * <p>
	 * If either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param resolutionX the resolution along the X-axis
	 * @param resolutionY the resolution along the Y-axis
	 * @param color the {@link Color4F} to fill with
	 * @return a {@code Data} instance with a resolution of {@code resolutionX} and {@code resolutionY} and is filled with a single color represented by {@code color}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public abstract Data create(final int resolutionX, final int resolutionY, final Color4F color);
	
	/**
	 * Returns a {@code Data} instance with a resolution of {@code resolutionX} and {@code resolutionY} and is filled with a single color represented by {@code color}.
	 * <p>
	 * If either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param resolutionX the resolution along the X-axis
	 * @param resolutionY the resolution along the Y-axis
	 * @param color the color in the format ARGB to fill with
	 * @return a {@code Data} instance with a resolution of {@code resolutionX} and {@code resolutionY} and is filled with a single color represented by {@code color}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} or {@code resolutionY} are less than {@code 1} or {@code resolutionX * resolutionY} overflows
	 */
	public abstract Data create(final int resolutionX, final int resolutionY, final int color);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code DataFactory} instance that creates {@link Data} instances that stores pixels as {@link Color4D} instances.
	 * 
	 * @return a {@code DataFactory} instance that creates {@code Data} instances that stores pixels as {@code Color4D} instances
	 */
	public static DataFactory forColor4D() {
		return new Color4DDataFactory();
	}
	
	/**
	 * Returns a {@code DataFactory} instance that creates {@link Data} instances that stores pixels as {@link Color4F} instances.
	 * 
	 * @return a {@code DataFactory} instance that creates {@code Data} instances that stores pixels as {@code Color4F} instances
	 */
	public static DataFactory forColor4F() {
		return new Color4FDataFactory();
	}
	
	/**
	 * Returns a {@code DataFactory} instance that creates {@link Data} instances that stores pixels as packed {@code int} values with the format ARGB.
	 * 
	 * @return a {@code DataFactory} instance that creates {@code Data} instances that stores pixels as packed {@code int} values with the format ARGB
	 */
	public static DataFactory forPackedIntARGB() {
		return new PackedIntARGBDataFactory();
	}
}