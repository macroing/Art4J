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
package org.macroing.img4j.data;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.Objects;

import org.macroing.img4j.color.Color4D;

/**
 * A {@code Color4DData} is a {@link Data} implementation that contains data for an image in the form of an array of {@link Color4D} instances.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Color4DData extends Data {
	private Color4D[] colors;
	private int resolutionX;
	private int resolutionY;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Color4DData} instance with a resolution of {@code 1024} and {@code 768} that is filled with {@code Color4D.WHITE}.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color4DData(1024, 768);
	 * }
	 * </pre>
	 */
	public Color4DData() {
		this(1024, 768);
	}
	
	/**
	 * Constructs a new {@code Color4DData} instance from {@code bufferedImage}.
	 * <p>
	 * If {@code bufferedImage} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param bufferedImage a {@code BufferedImage} instance
	 * @throws NullPointerException thrown if, and only if, {@code bufferedImage} is {@code null}
	 */
	public Color4DData(final BufferedImage bufferedImage) {
		this.colors = Arrays.stream(DataBufferInt.class.cast(Utilities.getCompatibleBufferedImage(bufferedImage).getRaster().getDataBuffer()).getData()).mapToObj(colorARGB -> Color4D.getCached(Color4D.fromIntARGB(colorARGB))).toArray(Color4D[]::new);
		this.resolutionX = bufferedImage.getWidth();
		this.resolutionY = bufferedImage.getHeight();
	}
	
	/**
	 * Constructs a new {@code Color4DData} instance from {@code color4DData}.
	 * <p>
	 * If {@code color4DData} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color4DData the {@code Color4DData} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code color4DData} is {@code null}
	 */
	public Color4DData(final Color4DData color4DData) {
		super(color4DData);
		
		this.colors = color4DData.colors.clone();
		this.resolutionX = color4DData.resolutionX;
		this.resolutionY = color4DData.resolutionY;
	}
	
	/**
	 * Constructs a new {@code Color4DData} instance from {@code color4DData}.
	 * <p>
	 * If {@code color4DData} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color4DData the {@code Color4DData} instance to copy
	 * @param isIgnoringChangeHistory {@code true} if, and only if, the change history should be ignored, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code color4DData} is {@code null}
	 */
	public Color4DData(final Color4DData color4DData, final boolean isIgnoringChangeHistory) {
		super(color4DData, isIgnoringChangeHistory);
		
		this.colors = color4DData.colors.clone();
		this.resolutionX = color4DData.resolutionX;
		this.resolutionY = color4DData.resolutionY;
	}
	
	/**
	 * Constructs a new {@code Color4DData} instance with a resolution of {@code resolutionX} and {@code resolutionY} that is filled with {@code Color4D.WHITE}.
	 * <p>
	 * If either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color4DData(resolutionX, resolutionY, Color4D.WHITE);
	 * }
	 * </pre>
	 * 
	 * @param resolutionX the resolution along the X-axis
	 * @param resolutionY the resolution along the Y-axis
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 1}
	 */
	public Color4DData(final int resolutionX, final int resolutionY) {
		this(resolutionX, resolutionY, Color4D.WHITE);
	}
	
	/**
	 * Constructs a new {@code Color4DData} instance with a resolution of {@code resolutionX} and {@code resolutionY} that is filled with {@code color}.
	 * <p>
	 * If either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param resolutionX the resolution along the X-axis
	 * @param resolutionY the resolution along the Y-axis
	 * @param color the {@link Color4D} instance to fill with
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 1}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Color4DData(final int resolutionX, final int resolutionY, final Color4D color) {
		this.resolutionX = Utilities.requireRange(resolutionX, 1, Integer.MAX_VALUE, "resolutionX");
		this.resolutionY = Utilities.requireRange(resolutionY, 1, Integer.MAX_VALUE, "resolutionY");
		this.colors = new Color4D[Utilities.requireRange(resolutionX * resolutionY, 1, Integer.MAX_VALUE, "resolutionX * resolutionY")];
		
		Arrays.fill(this.colors, Objects.requireNonNull(color, "color == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code BufferedImage} representation of this {@code Color4DData} instance.
	 * 
	 * @param isRGB {@code true} if, and only if, {@code BufferedImage.TYPE_INT_RGB} should be used instead of {@code BufferedImage.TYPE_INT_ARGB}, {@code false} otherwise
	 * @return a {@code BufferedImage} representation of this {@code Color4DData} instance
	 */
	@Override
	public BufferedImage toBufferedImage(final boolean isRGB) {
		final BufferedImage bufferedImage = new BufferedImage(this.resolutionX, this.resolutionY, isRGB ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
		
		final int[] dataSource = Arrays.stream(this.colors).mapToInt(color -> color.toIntARGB()).toArray();
		final int[] dataTarget = DataBufferInt.class.cast(bufferedImage.getRaster().getDataBuffer()).getData();
		
		System.arraycopy(dataSource, 0, dataTarget, 0, dataSource.length);
		
		return bufferedImage;
	}
	
	/**
	 * Returns the {@link Color4D} at {@code index} in this {@code Color4DData} instance.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code color4DData.getResolution()}, {@code Color4D.BLACK} will be returned.
	 * 
	 * @param index the index of the pixel
	 * @return the {@code Color4D} at {@code index} in this {@code Color4DData} instance
	 */
	@Override
	public Color4D getColor4D(final int index) {
		return index >= 0 && index < this.colors.length ? this.colors[index] : Color4D.BLACK;
	}
	
	/**
	 * Returns the {@link Color4D} at {@code x} and {@code y} in this {@code Color4DData} instance.
	 * <p>
	 * If {@code x} is less than {@code 0} or greater than or equal to {@code color4DData.getResolutionX()}, {@code Color4D.BLACK} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0} or greater than or equal to {@code color4DData.getResolutionY()}, {@code Color4D.BLACK} will be returned.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color4D} at {@code x} and {@code y} in this {@code Color4DData} instance
	 */
	@Override
	public Color4D getColor4D(final int x, final int y) {
		return x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY ? this.colors[y * this.resolutionX + x] : Color4D.BLACK;
	}
	
	/**
	 * Returns a copy of this {@code Color4DData} instance.
	 * 
	 * @param isIgnoringChangeHistory {@code true} if, and only if, the change history should be ignored, {@code false} otherwise
	 * @return a copy of this {@code Color4DData} instance
	 */
	@Override
	public Data copy(final boolean isIgnoringChangeHistory) {
		return new Color4DData(this, isIgnoringChangeHistory);
	}
	
	/**
	 * Compares {@code object} to this {@code Color4DData} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Color4DData}, and they are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Color4DData} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Color4DData}, and they are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Color4DData)) {
			return false;
		} else if(!Objects.equals(getChangeHistory(), Color4DData.class.cast(object).getChangeHistory())) {
			return false;
		} else if(!Arrays.equals(this.colors, Color4DData.class.cast(object).colors)) {
			return false;
		} else if(this.resolutionX != Color4DData.class.cast(object).resolutionX) {
			return false;
		} else if(this.resolutionY != Color4DData.class.cast(object).resolutionY) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Scales this {@code Color4DData} instance to {@code resolutionX} and {@code resolutionY}.
	 * <p>
	 * Returns {@code true} if, and only if, the resolution is changed as a result of this operation, {@code false} otherwise.
	 * <p>
	 * If either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 1}, the resolution will not be changed.
	 * <p>
	 * If {@code resolutionX == color4DData.getResolutionX()} and {@code resolutionY == color4DData.getResolutionY()}, the resolution will not be changed.
	 * 
	 * @param resolutionX the new resolution along the X-axis
	 * @param resolutionY the new resolution along the Y-axis
	 * @return {@code true} if, and only if, the resolution is changed as a result of this operation, {@code false} otherwise
	 */
	@Override
	public boolean scale(final int resolutionX, final int resolutionY) {
		if(resolutionX < 1 || resolutionY < 1 || resolutionX * resolutionY < 1) {
			return false;
		}
		
		if(resolutionX == this.resolutionX && resolutionY == this.resolutionY) {
			return false;
		}
		
		final int newResolutionX = resolutionX;
		final int newResolutionY = resolutionY;
		
		final int oldResolutionX = this.resolutionX;
		final int oldResolutionY = this.resolutionY;
		
		final Color4D[] newColors = new Color4D[newResolutionX * newResolutionY];
		final Color4D[] oldColors = this.colors;
		
		final double scaleX = (double)(oldResolutionX) / (double)(newResolutionX);
		final double scaleY = (double)(oldResolutionY) / (double)(newResolutionY);
		
		for(int y = 0; y < newResolutionY; y++) {
			for(int x = 0; x < newResolutionX; x++) {
				newColors[y * newResolutionX + x] = getColor4D(x * scaleX, y * scaleY);
			}
		}
		
		if(changeBegin()) {
			changeAdd(new StateChange(newColors, oldColors, newResolutionX, oldResolutionX, newResolutionY, oldResolutionY));
			changeEnd();
		}
		
		this.colors = newColors;
		this.resolutionX = newResolutionX;
		this.resolutionY = newResolutionY;
		
		return true;
	}
	
	/**
	 * Sets the color of the pixel at {@code index} in this {@code Color4DData} instance to {@code color}.
	 * <p>
	 * Returns {@code true} if, and only if, the color of the pixel at {@code index} is set, {@code false} otherwise.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code color4DData.getResolution()}, the color of the pixel at {@code index} will not be set.
	 * 
	 * @param color the {@link Color4D} instance to set
	 * @param index the index of the pixel
	 * @return {@code true} if, and only if, the color of the pixel at {@code index} is set, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	@Override
	public boolean setColor4D(final Color4D color, final int index) {
		Objects.requireNonNull(color, "color == null");
		
		if(index >= 0 && index < this.colors.length) {
			final Color4D newColor = color;
			final Color4D oldColor = this.colors[index];
			
			if(changeBegin()) {
				changeAdd(new PixelChange(newColor, oldColor, index));
				changeEnd();
			}
			
			this.colors[index] = newColor;
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Sets the color of the pixel at {@code x} and {@code y} in this {@code Color4DData} instance to {@code color}.
	 * <p>
	 * Returns {@code true} if, and only if, the color of the pixel at {@code x} and {@code y} is set, {@code false} otherwise.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code x} is less than {@code 0} or greater than or equal to {@code color4DData.getResolutionX()}, the color of the pixel at {@code x} and {@code y} will not be set.
	 * <p>
	 * If {@code y} is less than {@code 0} or greater than or equal to {@code color4DData.getResolutionY()}, the color of the pixel at {@code x} and {@code y} will not be set.
	 * 
	 * @param color the {@link Color4D} instance to set
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return {@code true} if, and only if, the color of the pixel at {@code x} and {@code y} is set, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	@Override
	public boolean setColor4D(final Color4D color, final int x, final int y) {
		Objects.requireNonNull(color, "color == null");
		
		if(x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY) {
			final int index = y * this.resolutionX + x;
			
			final Color4D newColor = color;
			final Color4D oldColor = this.colors[index];
			
			if(changeBegin()) {
				changeAdd(new PixelChange(newColor, oldColor, index));
				changeEnd();
			}
			
			this.colors[index] = newColor;
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Sets the content of this {@code Color4DData} instance to a copy of the content in {@code data}.
	 * <p>
	 * Returns {@code true} if, and only if, the content of this {@code Color4DData} instance is changed as a result of this operation, {@code false} otherwise.
	 * <p>
	 * If {@code data} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If this {@code Color4DData} instance and {@code data} are incompatible, the content of this {@code Color4DData} instance will not be changed.
	 * <p>
	 * A {@code Color4DData} instance is only compatible with other {@code Color4DData} instances.
	 * 
	 * @param data the {@code Data} instance to copy content from
	 * @return {@code true} if, and only if, the content of this {@code Color4DData} instance is changed as a result of this operation, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code data} is {@code null}
	 */
	@Override
	public boolean setContent(final Data data) {
		Objects.requireNonNull(data, "data == null");
		
		if(data instanceof Color4DData) {
			final Color4DData color4DData = Color4DData.class.cast(data);
			
			final Color4D[] newColors = color4DData.colors;
			final Color4D[] oldColors = this.colors;
			
			final int newResolutionX = color4DData.resolutionX;
			final int newResolutionY = color4DData.resolutionY;
			
			final int oldResolutionX = this.resolutionX;
			final int oldResolutionY = this.resolutionY;
			
			if(changeBegin()) {
				changeAdd(new StateChange(newColors, oldColors, newResolutionX, oldResolutionX, newResolutionY, oldResolutionY));
				changeEnd();
			}
			
			this.colors = newColors;
			this.resolutionX = newResolutionX;
			this.resolutionY = newResolutionY;
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Sets the resolution of this {@code Color4DData} instance to {@code resolutionX} and {@code resolutionY}.
	 * <p>
	 * Returns {@code true} if, and only if, the resolution is changed as a result of this operation, {@code false} otherwise.
	 * <p>
	 * If either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 1}, the resolution will not be changed.
	 * <p>
	 * If {@code resolutionX == color4DData.getResolutionX()} and {@code resolutionY == color4DData.getResolutionY()}, the resolution will not be changed.
	 * 
	 * @param resolutionX the new resolution along the X-axis
	 * @param resolutionY the new resolution along the Y-axis
	 * @return {@code true} if, and only if, the resolution is changed as a result of this operation, {@code false} otherwise
	 */
	@Override
	public boolean setResolution(final int resolutionX, final int resolutionY) {
		if(resolutionX < 1 || resolutionY < 1 || resolutionX * resolutionY < 1) {
			return false;
		}
		
		if(resolutionX == this.resolutionX && resolutionY == this.resolutionY) {
			return false;
		}
		
		final int newResolutionX = resolutionX;
		final int newResolutionY = resolutionY;
		
		final int oldResolutionX = this.resolutionX;
		final int oldResolutionY = this.resolutionY;
		
		final Color4D[] newColors = new Color4D[newResolutionX * newResolutionY];
		final Color4D[] oldColors = this.colors;
		
		for(int y = 0; y < newResolutionY; y++) {
			for(int x = 0; x < newResolutionX; x++) {
				final int newIndex = y * newResolutionX + x;
				
				if(x < oldResolutionX && y < oldResolutionY) {
					final int oldIndex = y * oldResolutionX + x;
					
					newColors[newIndex] = oldColors[oldIndex];
				} else {
					newColors[newIndex] = Color4D.WHITE;
				}
			}
		}
		
		if(changeBegin()) {
			changeAdd(new StateChange(newColors, oldColors, newResolutionX, oldResolutionX, newResolutionY, oldResolutionY));
			changeEnd();
		}
		
		this.colors = newColors;
		this.resolutionX = newResolutionX;
		this.resolutionY = newResolutionY;
		
		return true;
	}
	
	/**
	 * Swaps the pixel at index {@code indexA} with the pixel at index {@code indexB}.
	 * <p>
	 * Returns {@code true} if, and only if, the swap occurred, {@code false} otherwise.
	 * <p>
	 * If either {@code indexA} or {@code indexB} are less than {@code 0} or greater than or equal to {@code data.getResolution()}, the swap will not occur.
	 * 
	 * @param indexA the index of one of the pixels to swap
	 * @param indexB the index of one of the pixels to swap
	 * @param hasChangeBegun {@code true} if, and only if, change has already begun, {@code false} otherwise
	 * @return {@code true} if, and only if, the swap occurred, {@code false} otherwise
	 */
	@Override
	public boolean swap(final int indexA, final int indexB, final boolean hasChangeBegun) {
		if(indexA < 0 || indexA >= this.colors.length) {
			return false;
		}
		
		if(indexB < 0 || indexB >= this.colors.length) {
			return false;
		}
		
		final Color4D colorA = this.colors[indexA];
		final Color4D colorB = this.colors[indexB];
		
		if(hasChangeBegun || changeBegin()) {
			changeAdd(new PixelChange(colorB, colorA, indexA));
			changeAdd(new PixelChange(colorA, colorB, indexB));
			
			if(!hasChangeBegun) {
				changeEnd();
			}
		}
		
		this.colors[indexA] = colorB;
		this.colors[indexB] = colorA;
		
		return true;
	}
	
	/**
	 * Returns the resolution of this {@code Color4DData} instance.
	 * 
	 * @return the resolution of this {@code Color4DData} instance
	 */
	@Override
	public int getResolution() {
		return this.colors.length;
	}
	
	/**
	 * Returns the resolution along the X-axis of this {@code Color4DData} instance.
	 * 
	 * @return the resolution along the X-axis of this {@code Color4DData} instance
	 */
	@Override
	public int getResolutionX() {
		return this.resolutionX;
	}
	
	/**
	 * Returns the resolution along the Y-axis of this {@code Color4DData} instance.
	 * 
	 * @return the resolution along the Y-axis of this {@code Color4DData} instance
	 */
	@Override
	public int getResolutionY() {
		return this.resolutionY;
	}
	
	/**
	 * Returns a hash code for this {@code Color4DData} instance.
	 * 
	 * @return a hash code for this {@code Color4DData} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getChangeHistory(), Integer.valueOf(Arrays.hashCode(this.colors)), Integer.valueOf(this.resolutionX), Integer.valueOf(this.resolutionY));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	void updatePixel(final Color4D color, final int index) {
		this.colors[index] = color;
	}
	
	void updateState(final Color4D[] colors, final int resolutionX, final int resolutionY) {
		this.colors = colors.clone();
		this.resolutionX = resolutionX;
		this.resolutionY = resolutionY;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class PixelChange implements Change {
		private final Color4D colorRedo;
		private final Color4D colorUndo;
		private final int index;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public PixelChange(final Color4D colorRedo, final Color4D colorUndo, final int index) {
			this.colorRedo = colorRedo;
			this.colorUndo = colorUndo;
			this.index = index;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public boolean equals(final Object object) {
			if(object == this) {
				return true;
			} else if(!(object instanceof PixelChange)) {
				return false;
			} else if(!Objects.equals(this.colorRedo, PixelChange.class.cast(object).colorRedo)) {
				return false;
			} else if(!Objects.equals(this.colorUndo, PixelChange.class.cast(object).colorUndo)) {
				return false;
			} else if(this.index != PixelChange.class.cast(object).index) {
				return false;
			} else {
				return true;
			}
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(this.colorRedo, this.colorUndo, Integer.valueOf(this.index));
		}
		
		@Override
		public void redo(final Data data) {
			if(data instanceof Color4DData) {
				final
				Color4DData color4DData = Color4DData.class.cast(data);
				color4DData.updatePixel(this.colorRedo, this.index);
			}
		}
		
		@Override
		public void undo(final Data data) {
			if(data instanceof Color4DData) {
				final
				Color4DData color4DData = Color4DData.class.cast(data);
				color4DData.updatePixel(this.colorUndo, this.index);
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class StateChange implements Change {
		private final Color4D[] colorsRedo;
		private final Color4D[] colorsUndo;
		private final int resolutionXRedo;
		private final int resolutionXUndo;
		private final int resolutionYRedo;
		private final int resolutionYUndo;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public StateChange(final Color4D[] colorsRedo, final Color4D[] colorsUndo, final int resolutionXRedo, final int resolutionXUndo, final int resolutionYRedo, final int resolutionYUndo) {
			this.colorsRedo = colorsRedo.clone();
			this.colorsUndo = colorsUndo.clone();
			this.resolutionXRedo = resolutionXRedo;
			this.resolutionXUndo = resolutionXUndo;
			this.resolutionYRedo = resolutionYRedo;
			this.resolutionYUndo = resolutionYUndo;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public boolean equals(final Object object) {
			if(object == this) {
				return true;
			} else if(!(object instanceof StateChange)) {
				return false;
			} else if(!Arrays.equals(this.colorsRedo, StateChange.class.cast(object).colorsRedo)) {
				return false;
			} else if(!Arrays.equals(this.colorsUndo, StateChange.class.cast(object).colorsUndo)) {
				return false;
			} else if(this.resolutionXRedo != StateChange.class.cast(object).resolutionXRedo) {
				return false;
			} else if(this.resolutionXUndo != StateChange.class.cast(object).resolutionXUndo) {
				return false;
			} else if(this.resolutionYRedo != StateChange.class.cast(object).resolutionYRedo) {
				return false;
			} else if(this.resolutionYUndo != StateChange.class.cast(object).resolutionYUndo) {
				return false;
			} else {
				return true;
			}
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(Integer.valueOf(Arrays.hashCode(this.colorsRedo)), Integer.valueOf(Arrays.hashCode(this.colorsUndo)), Integer.valueOf(this.resolutionXRedo), Integer.valueOf(this.resolutionXUndo), Integer.valueOf(this.resolutionYRedo), Integer.valueOf(this.resolutionYUndo));
		}
		
		@Override
		public void redo(final Data data) {
			if(data instanceof Color4DData) {
				final
				Color4DData color4DData = Color4DData.class.cast(data);
				color4DData.updateState(this.colorsRedo, this.resolutionXRedo, this.resolutionYRedo);
			}
		}
		
		@Override
		public void undo(final Data data) {
			if(data instanceof Color4DData) {
				final
				Color4DData color4DData = Color4DData.class.cast(data);
				color4DData.updateState(this.colorsUndo, this.resolutionXUndo, this.resolutionYUndo);
			}
		}
	}
}