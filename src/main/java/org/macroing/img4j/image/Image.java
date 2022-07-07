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
package org.macroing.img4j.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.Objects;

import javax.imageio.ImageIO;

import org.macroing.img4j.color.Color4D;
import org.macroing.img4j.data.Color4DDataFactory;
import org.macroing.img4j.data.Data;
import org.macroing.img4j.data.DataFactory;

//TODO: Add Javadocs!
public final class Image {
	private static final DataFactory DATA_FACTORY = new Color4DDataFactory();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
	 * 
	 * @param bufferedImage a {@code BufferedImage} instance
	 * @throws NullPointerException thrown if, and only if, {@code bufferedImage} is {@code null}
	 */
	public Image(final BufferedImage bufferedImage) {
		this.data = DATA_FACTORY.create(bufferedImage);
	}
	
//	TODO: Add Javadocs!
	public Image(final Data data) {
		this.data = Objects.requireNonNull(data, "data == null");
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
		this.data = image.data.copy();
	}
	
	/**
	 * Constructs a new {@code Image} instance filled with {@code Color4D.WHITE}.
	 * <p>
	 * If either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
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
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 1}
	 */
	public Image(final int resolutionX, final int resolutionY) {
		this(resolutionX, resolutionY, Color4D.WHITE);
	}
	
	/**
	 * Constructs a new {@code Image} instance filled with {@code color}.
	 * <p>
	 * If either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param resolutionX the resolution along the X-axis
	 * @param resolutionY the resolution along the Y-axis
	 * @param color the {@link Color4D} to fill with
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 1}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image(final int resolutionX, final int resolutionY, final Color4D color) {
		this.data = DATA_FACTORY.create(resolutionX, resolutionY, color);
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
	 * Returns the {@link Color4D} at {@code x} and {@code y} in this {@code Image} instance.
	 * <p>
	 * If {@code x} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionX()}, {@code Color4D.BLACK} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0.0D} or greater than or equal to {@code image.getResolutionY()}, {@code Color4D.BLACK} will be returned.
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
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code image.getResolution()}, {@code Color4D.BLACK} will be returned.
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
	 * If {@code x} is less than {@code 0} or greater than or equal to {@code image.getResolutionX()}, {@code Color4D.BLACK} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0} or greater than or equal to {@code image.getResolutionY()}, {@code Color4D.BLACK} will be returned.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color4D} at {@code x} and {@code y} in this {@code Image} instance
	 */
	public Color4D getColor4D(final int x, final int y) {
		return this.data.getColor4D(x, y);
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
		
		final boolean hasChangeBegun = this.data.changeBegin();
		
		for(int xL = 0, xR = resolutionX - 1; xL < xR; xL++, xR--) {
			for(int y = 0; y < resolutionY; y++) {
				final int indexL = y * resolutionX + xL;
				final int indexR = y * resolutionX + xR;
				
				this.data.swap(indexL, indexR, hasChangeBegun);
			}
		}
		
		if(hasChangeBegun) {
			this.data.changeEnd();
		}
		
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
		
		final boolean hasChangeBegun = this.data.changeBegin();
		
		for(int yT = 0, yB = resolutionY - 1; yT < yB; yT++, yB--) {
			for(int x = 0; x < resolutionX; x++) {
				final int indexT = yT * resolutionX + x;
				final int indexB = yB * resolutionX + x;
				
				this.data.swap(indexT, indexB, hasChangeBegun);
			}
		}
		
		if(hasChangeBegun) {
			this.data.changeEnd();
		}
		
		return this;
	}
	
	/**
	 * Saves this {@code Image} instance to the file represented by {@code file} using the informal format name {@code "png"}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code file} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.save(file, "png");
	 * }
	 * </pre>
	 * 
	 * @param file a {@code File} that represents the file to save to
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public Image save(final File file) {
		return save(file, "png");
	}
	
	/**
	 * Saves this {@code Image} instance to the file represented by {@code file} using the informal format name {@code formatName}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code file} or {@code formatName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param file a {@code File} that represents the file to save to
	 * @param formatName the informal format name, such as {@code "png"}
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code file} or {@code formatName} are {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public Image save(final File file, final String formatName) {
		Objects.requireNonNull(file, "file == null");
		Objects.requireNonNull(formatName, "formatName == null");
		
		try {
			final File parentFile = file.getParentFile();
			
			if(parentFile != null && !parentFile.isDirectory()) {
				parentFile.mkdirs();
			}
			
			ImageIO.write(toBufferedImage(doIsJPEG(formatName)), formatName, file);
			
			return this;
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Saves this {@code Image} instance to the file represented by the pathname {@code pathname} using the informal format name {@code "png"}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If {@code pathname} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.save(pathname, "png");
	 * }
	 * </pre>
	 * 
	 * @param pathname a {@code String} that represents the pathname of the file to save to
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code pathname} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public Image save(final String pathname) {
		return save(pathname, "png");
	}
	
	/**
	 * Saves this {@code Image} instance to the file represented by the pathname {@code pathname} using the informal format name {@code formatName}.
	 * <p>
	 * Returns this {@code Image} instance.
	 * <p>
	 * If either {@code pathname} or {@code formatName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
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
	 * @return this {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, either {@code pathname} or {@code formatName} are {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public Image save(final String pathname, final String formatName) {
		return save(new File(pathname), formatName);
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
	 * Sets the change history enabled state to {@code isChangeHistoryEnabled}.
	 * 
	 * @param isChangeHistoryEnabled the change history enabled state
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
	
	private static boolean doIsJPEG(final String formatName) {
		return formatName.matches("^\\.?[Jj][Pp][Ee]?[Gg]$");
	}
}