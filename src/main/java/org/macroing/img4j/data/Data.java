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
import java.util.Objects;
import java.util.Optional;

import org.macroing.img4j.color.Color4D;

/**
 * A {@code Data} contains data for an image.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class Data {
	private ChangeHistory changeHistory;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Data} instance.
	 */
	protected Data() {
		this.changeHistory = null;
	}
	
	/**
	 * Constructs a new {@code Data} instance from {@code data}.
	 * <p>
	 * If {@code data} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param data the {@code Data} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code data} is {@code null}
	 */
	protected Data(final Data data) {
		this.changeHistory = data.changeHistory != null ? new ChangeHistory(data.changeHistory) : null;
	}
	
	/**
	 * Constructs a new {@code Data} instance from {@code data}.
	 * <p>
	 * If {@code data} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param data the {@code Data} instance to copy
	 * @param isIgnoringChangeHistory {@code true} if, and only if, the change history should be ignored, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code data} is {@code null}
	 */
	protected Data(final Data data, final boolean isIgnoringChangeHistory) {
		this.changeHistory = isIgnoringChangeHistory ? null : data.changeHistory != null ? new ChangeHistory(data.changeHistory) : null;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code BufferedImage} representation of this {@code Data} instance.
	 * 
	 * @param isRGB {@code true} if, and only if, {@code BufferedImage.TYPE_INT_RGB} should be used instead of {@code BufferedImage.TYPE_INT_ARGB}, {@code false} otherwise
	 * @return a {@code BufferedImage} representation of this {@code Data} instance
	 */
	public abstract BufferedImage toBufferedImage(final boolean isRGB);
	
	/**
	 * Returns the {@link Color4D} at {@code x} and {@code y} in this {@code Data} instance.
	 * <p>
	 * If {@code x} is less than {@code 0.0D} or greater than or equal to {@code data.getResolutionX()}, {@code Color4D.TRANSPARENT} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0.0D} or greater than or equal to {@code data.getResolutionY()}, {@code Color4D.TRANSPARENT} will be returned.
	 * <p>
	 * If both {@code x} and {@code y} are equal to mathematical integers, this method is equivalent to {@link #getColor4D(int, int)}. Otherwise, bilinear interpolation will be performed on the closest pixels.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color4D} at {@code x} and {@code y} in this {@code Data} instance
	 */
	public final Color4D getColor4D(final double x, final double y) {
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		if(x < 0.0D || x >= resolutionX) {
			return Color4D.TRANSPARENT;
		}
		
		if(y < 0.0D || y >= resolutionY) {
			return Color4D.TRANSPARENT;
		}
		
		final int minimumX = (int)(Math.floor(x));
		final int maximumX = (int)(Math.ceil(x));
		
		final int minimumY = (int)(Math.floor(y));
		final int maximumY = (int)(Math.ceil(y));
		
		if(minimumX == maximumX && minimumY == maximumY) {
			return getColor4D(minimumX, minimumY);
		}
		
		return Color4D.blend(getColor4D(minimumX, minimumY), getColor4D(maximumX, minimumY), getColor4D(minimumX, maximumY), getColor4D(maximumX, maximumY), x - minimumX, y - minimumY);
	}
	
	/**
	 * Returns the {@link Color4D} at {@code index} in this {@code Data} instance.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code data.getResolution()}, {@code Color4D.TRANSPARENT} will be returned.
	 * 
	 * @param index the index of the pixel
	 * @return the {@code Color4D} at {@code index} in this {@code Data} instance
	 */
	public abstract Color4D getColor4D(final int index);
	
	/**
	 * Returns the {@link Color4D} at {@code x} and {@code y} in this {@code Data} instance.
	 * <p>
	 * If {@code x} is less than {@code 0} or greater than or equal to {@code data.getResolutionX()}, {@code Color4D.TRANSPARENT} will be returned.
	 * <p>
	 * If {@code y} is less than {@code 0} or greater than or equal to {@code data.getResolutionY()}, {@code Color4D.TRANSPARENT} will be returned.
	 * 
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return the {@code Color4D} at {@code x} and {@code y} in this {@code Data} instance
	 */
	public abstract Color4D getColor4D(final int x, final int y);
	
	/**
	 * Returns a copy of this {@code Data} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * data.copy(false);
	 * }
	 * </pre>
	 * 
	 * @return a copy of this {@code Data} instance
	 */
	public final Data copy() {
		return copy(false);
	}
	
	/**
	 * Returns a copy of this {@code Data} instance.
	 * 
	 * @param isIgnoringChangeHistory {@code true} if, and only if, the change history should be ignored, {@code false} otherwise
	 * @return a copy of this {@code Data} instance
	 */
	public abstract Data copy(final boolean isIgnoringChangeHistory);
	
	/**
	 * Returns a {@link DataFactory} instance that is associated with this {@code Data} instance.
	 * 
	 * @return a {@code DataFactory} instance that is associated with this {@code Data} instance
	 */
	public abstract DataFactory getDataFactory();
	
	/**
	 * Returns the optional {@link ChangeHistory} instance.
	 * 
	 * @return the optional {@code ChangeHistory} instance
	 */
	public final Optional<ChangeHistory> getChangeHistory() {
		return Optional.ofNullable(this.changeHistory);
	}
	
	/**
	 * Performs a change add operation.
	 * <p>
	 * Returns {@code true} if, and only if, the change history is enabled, {@code false} otherwise.
	 * <p>
	 * If {@code change} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param change the {@link Change} instance to add
	 * @return {@code true} if, and only if, the change history is enabled, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code change} is {@code null}
	 */
	public final boolean changeAdd(final Change change) {
		Objects.requireNonNull(change, "change == null");
		
		final ChangeHistory changeHistory = this.changeHistory;
		
		if(changeHistory != null) {
			changeHistory.add(change);
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Performs a change begin operation.
	 * <p>
	 * Returns {@code true} if, and only if, the change history is enabled, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, the change history is enabled, {@code false} otherwise
	 */
	public final boolean changeBegin() {
		final ChangeHistory changeHistory = this.changeHistory;
		
		if(changeHistory != null) {
			changeHistory.clear();
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Performs a change end operation.
	 * <p>
	 * Returns {@code true} if, and only if, the change history is enabled, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, the change history is enabled, {@code false} otherwise
	 */
	public final boolean changeEnd() {
		final ChangeHistory changeHistory = this.changeHistory;
		
		if(changeHistory != null) {
			changeHistory.push();
			changeHistory.clear();
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns {@code true} if, and only if, the change history is enabled, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, the change history is enabled, {@code false} otherwise
	 */
	public final boolean isChangeHistoryEnabled() {
		return this.changeHistory != null;
	}
	
	/**
	 * Performs the current redo operation.
	 * <p>
	 * Returns {@code true} if, and only if, the redo operation was performed, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, the redo operation was performed, {@code false} otherwise
	 */
	public final boolean redo() {
		final ChangeHistory changeHistory = this.changeHistory;
		
		if(changeHistory != null) {
			return changeHistory.redo(this);
		}
		
		return false;
	}
	
	/**
	 * Rotates this {@code Data} instance by {@code angle} degrees or radians.
	 * <p>
	 * Returns {@code true} if, and only if, the rotation was performed, {@code false} otherwise.
	 * 
	 * @param angle an angle in degrees or radians
	 * @param isAngleInRadians {@code true} if, and only if, {@code angle} is in radians, {@code false} otherwise
	 * @return {@code true} if, and only if, the rotation was performed, {@code false} otherwise
	 */
	public abstract boolean rotate(final double angle, final boolean isAngleInRadians);
	
	/**
	 * Scales this {@code Data} instance to a new resolution given the scale factors {@code scaleX} and {@code scaleY}.
	 * <p>
	 * Returns {@code true} if, and only if, the resolution is changed as a result of this operation, {@code false} otherwise.
	 * <p>
	 * If either {@code scaleX} or {@code scaleY} are less than or equal to {@code 0.0D}, the resolution will not be changed.
	 * <p>
	 * If both {@code scaleX} and {@code scaleY} are equal to {@code 1.0D}, the resolution will not be changed.
	 * 
	 * @param scaleX the scale factor along the X-axis
	 * @param scaleY the scale factor along the Y-axis
	 * @return {@code true} if, and only if, the resolution is changed as a result of this operation, {@code false} otherwise
	 */
	public final boolean scale(final double scaleX, final double scaleY) {
		final int resolutionX = (int)(Math.ceil(getResolutionX() * scaleX));
		final int resolutionY = (int)(Math.ceil(getResolutionY() * scaleY));
		
		return scale(resolutionX, resolutionY);
	}
	
	/**
	 * Scales this {@code Data} instance to {@code resolutionX} and {@code resolutionY}.
	 * <p>
	 * Returns {@code true} if, and only if, the resolution is changed as a result of this operation, {@code false} otherwise.
	 * <p>
	 * If either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 1}, the resolution will not be changed.
	 * <p>
	 * If {@code resolutionX == data.getResolutionX()} and {@code resolutionY == data.getResolutionY()}, the resolution will not be changed.
	 * 
	 * @param resolutionX the new resolution along the X-axis
	 * @param resolutionY the new resolution along the Y-axis
	 * @return {@code true} if, and only if, the resolution is changed as a result of this operation, {@code false} otherwise
	 */
	public abstract boolean scale(final int resolutionX, final int resolutionY);
	
	/**
	 * Sets the change history enabled state to {@code isChangeHistoryEnabled}.
	 * 
	 * @param isChangeHistoryEnabled the change history enabled state
	 */
	public final boolean setChangeHistoryEnabled(final boolean isChangeHistoryEnabled) {
		if(isChangeHistoryEnabled && this.changeHistory == null) {
			this.changeHistory = new ChangeHistory();
			
			return true;
		} else if(isChangeHistoryEnabled) {
			return false;
		} else if(this.changeHistory != null) {
			this.changeHistory = null;
			
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Sets the color of the pixel at {@code index} in this {@code Data} instance to {@code color}.
	 * <p>
	 * Returns {@code true} if, and only if, the color of the pixel at {@code index} is set, {@code false} otherwise.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code data.getResolution()}, the color of the pixel at {@code index} will not be set.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * data.setColor4D(color, index, false);
	 * }
	 * </pre>
	 * 
	 * @param color the {@link Color4D} instance to set
	 * @param index the index of the pixel
	 * @return {@code true} if, and only if, the color of the pixel at {@code index} is set, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public final boolean setColor4D(final Color4D color, final int index) {
		return setColor4D(color, index, false);
	}
	
	/**
	 * Sets the color of the pixel at {@code index} in this {@code Data} instance to {@code color}.
	 * <p>
	 * Returns {@code true} if, and only if, the color of the pixel at {@code index} is set, {@code false} otherwise.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code data.getResolution()}, the color of the pixel at {@code index} will not be set.
	 * 
	 * @param color the {@link Color4D} instance to set
	 * @param index the index of the pixel
	 * @param hasChangeBegun {@code true} if, and only if, change has already begun, {@code false} otherwise
	 * @return {@code true} if, and only if, the color of the pixel at {@code index} is set, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public abstract boolean setColor4D(final Color4D color, final int index, final boolean hasChangeBegun);
	
	/**
	 * Sets the color of the pixel at {@code x} and {@code y} in this {@code Data} instance to {@code color}.
	 * <p>
	 * Returns {@code true} if, and only if, the color of the pixel at {@code x} and {@code y} is set, {@code false} otherwise.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code x} is less than {@code 0} or greater than or equal to {@code data.getResolutionX()}, the color of the pixel at {@code x} and {@code y} will not be set.
	 * <p>
	 * If {@code y} is less than {@code 0} or greater than or equal to {@code data.getResolutionY()}, the color of the pixel at {@code x} and {@code y} will not be set.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * data.setColor4D(color, x, y, false);
	 * }
	 * </pre>
	 * 
	 * @param color the {@link Color4D} instance to set
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @return {@code true} if, and only if, the color of the pixel at {@code x} and {@code y} is set, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public final boolean setColor4D(final Color4D color, final int x, final int y) {
		return setColor4D(color, x, y, false);
	}
	
	/**
	 * Sets the color of the pixel at {@code x} and {@code y} in this {@code Data} instance to {@code color}.
	 * <p>
	 * Returns {@code true} if, and only if, the color of the pixel at {@code x} and {@code y} is set, {@code false} otherwise.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code x} is less than {@code 0} or greater than or equal to {@code data.getResolutionX()}, the color of the pixel at {@code x} and {@code y} will not be set.
	 * <p>
	 * If {@code y} is less than {@code 0} or greater than or equal to {@code data.getResolutionY()}, the color of the pixel at {@code x} and {@code y} will not be set.
	 * 
	 * @param color the {@link Color4D} instance to set
	 * @param x the X-component of the pixel
	 * @param y the Y-component of the pixel
	 * @param hasChangeBegun {@code true} if, and only if, change has already begun, {@code false} otherwise
	 * @return {@code true} if, and only if, the color of the pixel at {@code x} and {@code y} is set, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public abstract boolean setColor4D(final Color4D color, final int x, final int y, final boolean hasChangeBegun);
	
	/**
	 * Sets the content of this {@code Data} instance to a copy of the content in {@code data}.
	 * <p>
	 * Returns {@code true} if, and only if, the content of this {@code Data} instance is changed as a result of this operation, {@code false} otherwise.
	 * <p>
	 * If {@code data} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If this {@code Data} instance and {@code data} are incompatible, the content of this {@code Data} instance will not be changed.
	 * <p>
	 * Two {@code Data} instances are compatible if they implement the same class. Some {@code Data} implementations may be compatible in other ways.
	 * 
	 * @param data the {@code Data} instance to copy content from
	 * @return {@code true} if, and only if, the content of this {@code Data} instance is changed as a result of this operation, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code data} is {@code null}
	 */
	public abstract boolean setContent(final Data data);
	
	/**
	 * Sets the resolution of this {@code Data} instance to {@code resolutionX} and {@code resolutionY}.
	 * <p>
	 * Returns {@code true} if, and only if, the resolution is changed as a result of this operation, {@code false} otherwise.
	 * <p>
	 * If either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 1}, the resolution will not be changed.
	 * <p>
	 * If {@code resolutionX == data.getResolutionX()} and {@code resolutionY == data.getResolutionY()}, the resolution will not be changed.
	 * 
	 * @param resolutionX the new resolution along the X-axis
	 * @param resolutionY the new resolution along the Y-axis
	 * @return {@code true} if, and only if, the resolution is changed as a result of this operation, {@code false} otherwise
	 */
	public abstract boolean setResolution(final int resolutionX, final int resolutionY);
	
	/**
	 * Sets the X-resolution of this {@code Data} instance to {@code resolutionX}.
	 * <p>
	 * Returns {@code true} if, and only if, the resolution is changed as a result of this operation, {@code false} otherwise.
	 * <p>
	 * If either {@code resolutionX} or {@code resolutionX * data.getResolutionY()} are less than {@code 1}, the resolution will not be changed.
	 * <p>
	 * If {@code resolutionX == data.getResolutionX()}, the resolution will not be changed.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * data.setResolution(resolutionX, data.getResolutionY());
	 * }
	 * </pre>
	 * 
	 * @param resolutionX the new resolution along the X-axis
	 * @return {@code true} if, and only if, the resolution is changed as a result of this operation, {@code false} otherwise
	 */
	public final boolean setResolutionX(final int resolutionX) {
		return setResolution(resolutionX, getResolutionY());
	}
	
	/**
	 * Sets the Y-resolution of this {@code Data} instance to {@code resolutionY}.
	 * <p>
	 * Returns {@code true} if, and only if, the resolution is changed as a result of this operation, {@code false} otherwise.
	 * <p>
	 * If either {@code resolutionY} or {@code data.getResolutionX() * resolutionY} are less than {@code 1}, the resolution will not be changed.
	 * <p>
	 * If {@code resolutionY == data.getResolutionY()}, the resolution will not be changed.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * data.setResolution(data.getResolutionX(), resolutionY);
	 * }
	 * </pre>
	 * 
	 * @param resolutionY the new resolution along the Y-axis
	 * @return {@code true} if, and only if, the resolution is changed as a result of this operation, {@code false} otherwise
	 */
	public final boolean setResolutionY(final int resolutionY) {
		return setResolution(getResolutionX(), resolutionY);
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
	public abstract boolean swap(final int indexA, final int indexB, final boolean hasChangeBegun);
	
	/**
	 * Performs the current undo operation.
	 * <p>
	 * Returns {@code true} if, and only if, the undo operation was performed, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, the undo operation was performed, {@code false} otherwise
	 */
	public final boolean undo() {
		final ChangeHistory changeHistory = this.changeHistory;
		
		if(changeHistory != null) {
			return changeHistory.undo(this);
		}
		
		return false;
	}
	
	/**
	 * Returns the resolution of this {@code Data} instance.
	 * 
	 * @return the resolution of this {@code Data} instance
	 */
	public abstract int getResolution();
	
	/**
	 * Returns the resolution along the X-axis of this {@code Data} instance.
	 * 
	 * @return the resolution along the X-axis of this {@code Data} instance
	 */
	public abstract int getResolutionX();
	
	/**
	 * Returns the resolution along the Y-axis of this {@code Data} instance.
	 * 
	 * @return the resolution along the Y-axis of this {@code Data} instance
	 */
	public abstract int getResolutionY();
}