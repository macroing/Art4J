/**
 * Copyright 2014 - 2022 J&#246;rgen Lundgren
 * 
 * This file is part of Dayflower.
 * 
 * Dayflower is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Dayflower is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Dayflower. If not, see <http://www.gnu.org/licenses/>.
 */
package org.macroing.img4j.data;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

import org.macroing.img4j.color.Color3D;
import org.macroing.img4j.color.Color3F;
import org.macroing.img4j.color.Color4D;
import org.macroing.img4j.color.Color4F;
import org.macroing.img4j.kernel.ConvolutionKernelND;
import org.macroing.img4j.kernel.ConvolutionKernelNF;
import org.macroing.img4j.utility.BufferedImages;

final class Color4FData extends Data {
	private Color4F[] colors;
	private int resolutionX;
	private int resolutionY;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Color4FData() {
		this(1024, 768);
	}
	
	public Color4FData(final BufferedImage bufferedImage) {
		this.colors = Arrays.stream(DataBufferInt.class.cast(BufferedImages.getCompatibleBufferedImage(bufferedImage).getRaster().getDataBuffer()).getData()).mapToObj(colorARGB -> Color4F.fromIntARGB(colorARGB)).toArray(Color4F[]::new);
		this.resolutionX = bufferedImage.getWidth();
		this.resolutionY = bufferedImage.getHeight();
	}
	
	public Color4FData(final Color4FData color4FData) {
		super(color4FData);
		
		this.colors = color4FData.colors.clone();
		this.resolutionX = color4FData.resolutionX;
		this.resolutionY = color4FData.resolutionY;
	}
	
	public Color4FData(final Color4FData color4FData, final boolean isIgnoringChangeHistory) {
		super(color4FData, isIgnoringChangeHistory);
		
		this.colors = color4FData.colors.clone();
		this.resolutionX = color4FData.resolutionX;
		this.resolutionY = color4FData.resolutionY;
	}
	
	public Color4FData(final int resolutionX, final int resolutionY) {
		this(resolutionX, resolutionY, Color4F.WHITE);
	}
	
	public Color4FData(final int resolutionX, final int resolutionY, final Color4D color) {
		this(resolutionX, resolutionY, new Color4F(color));
	}
	
	public Color4FData(final int resolutionX, final int resolutionY, final Color4F color) {
		this.resolutionX = Utilities.requireRange(resolutionX, 1, Integer.MAX_VALUE, "resolutionX");
		this.resolutionY = Utilities.requireRange(resolutionY, 1, Integer.MAX_VALUE, "resolutionY");
		this.colors = new Color4F[Utilities.requireRange(resolutionX * resolutionY, 1, Integer.MAX_VALUE, "resolutionX * resolutionY")];
		
		Arrays.fill(this.colors, Objects.requireNonNull(color, "color == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public BufferedImage toBufferedImage(final boolean isRGB) {
		final BufferedImage bufferedImage = new BufferedImage(this.resolutionX, this.resolutionY, isRGB ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
		
		final int[] dataSource = Arrays.stream(this.colors).mapToInt(color -> color.toIntARGB()).toArray();
		final int[] dataTarget = DataBufferInt.class.cast(bufferedImage.getRaster().getDataBuffer()).getData();
		
		System.arraycopy(dataSource, 0, dataTarget, 0, dataSource.length);
		
		return bufferedImage;
	}
	
	@Override
	public Color3D getColor3D(final int index) {
		return index >= 0 && index < this.colors.length ? new Color3D(this.colors[index]) : Color3D.BLACK;
	}
	
	@Override
	public Color3D getColor3D(final int x, final int y) {
		return x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY ? new Color3D(this.colors[y * this.resolutionX + x]) : Color3D.BLACK;
	}
	
	@Override
	public Color3F getColor3F(final int index) {
		return index >= 0 && index < this.colors.length ? new Color3F(this.colors[index]) : Color3F.BLACK;
	}
	
	@Override
	public Color3F getColor3F(final int x, final int y) {
		return x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY ? new Color3F(this.colors[y * this.resolutionX + x]) : Color3F.BLACK;
	}
	
	@Override
	public Color4D getColor4D(final int index) {
		return index >= 0 && index < this.colors.length ? new Color4D(this.colors[index]) : Color4D.TRANSPARENT;
	}
	
	@Override
	public Color4D getColor4D(final int x, final int y) {
		return x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY ? new Color4D(this.colors[y * this.resolutionX + x]) : Color4D.TRANSPARENT;
	}
	
	@Override
	public Color4F getColor4F(final int index) {
		return index >= 0 && index < this.colors.length ? this.colors[index] : Color4F.TRANSPARENT;
	}
	
	@Override
	public Color4F getColor4F(final int x, final int y) {
		return x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY ? this.colors[y * this.resolutionX + x] : Color4F.TRANSPARENT;
	}
	
	@Override
	public Data copy(final boolean isIgnoringChangeHistory) {
		return new Color4FData(this, isIgnoringChangeHistory);
	}
	
	@Override
	public Data draw(final Consumer<Graphics2D> graphics2DConsumer) {
		Objects.requireNonNull(graphics2DConsumer, "graphics2DConsumer == null");
		
		final BufferedImage bufferedImage = toBufferedImage(false);
		
		final Graphics2D graphics2D = bufferedImage.createGraphics();
		
		graphics2DConsumer.accept(graphics2D);
		
		final Color4F[] colors = Arrays.stream(DataBufferInt.class.cast(bufferedImage.getRaster().getDataBuffer()).getData()).mapToObj(colorARGB -> Color4F.fromIntARGB(colorARGB)).toArray(Color4F[]::new);
		
		if(changeBegin()) {
			changeAdd(new StateChange(colors, this.colors, this.resolutionX, this.resolutionX, this.resolutionY, this.resolutionY));
			changeEnd();
		}
		
		this.colors = colors;
		
		return this;
	}
	
	@Override
	public DataFactory getDataFactory() {
		return new Color4FDataFactory();
	}
	
	@Override
	public boolean convolve(final ConvolutionKernelND convolutionKernel, final int[] indices) {
		Objects.requireNonNull(convolutionKernel, "convolutionKernel == null");
		Objects.requireNonNull(indices, "indices == null");
		
		if(indices.length == 0) {
			return false;
		}
		
		final double bias = convolutionKernel.getBias();
		final double factor = convolutionKernel.getFactor();
		
		final double[] elements = convolutionKernel.getElements();
		
		final int kernelResolution = convolutionKernel.getResolution();
		final int kernelOffset = (kernelResolution - 1) / 2;
		
		final int resolution = getResolution();
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		final Color4F[] oldColors = this.colors;
		final Color4F[] newColors = this.colors.clone();
		
		final boolean hasChangeBegun = changeBegin();
		
		int count = 0;
		
		for(final int index : indices) {
			if(index >= 0 && index < resolution) {
				final int x = index % resolutionX;
				final int y = index / resolutionX;
				
				final int xOffset = x - kernelOffset;
				final int yOffset = y - kernelOffset;
				
				float colorR = 0.0F;
				float colorG = 0.0F;
				float colorB = 0.0F;
				float colorA = oldColors[index].a;
				
				for(int kernelY = 0; kernelY < kernelResolution; kernelY++) {
					final int imageY = yOffset + kernelY;
					final int imageRow = imageY * resolutionX;
					
					final int kernelRow = kernelY * kernelResolution;
					
					for(int kernelX = 0; kernelX < kernelResolution; kernelX++) {
						final int imageX = xOffset + kernelX;
						
						if(imageX >= 0 && imageX < resolutionX && imageY >= 0 && imageY < resolutionY) {
							final Color4F color = oldColors[imageRow + imageX];
							
							final double element = elements[kernelRow + kernelX];
							
							colorR += color.r * element;
							colorG += color.g * element;
							colorB += color.b * element;
						}
					}
				}
				
				colorR = (float)(colorR * factor + bias);
				colorG = (float)(colorG * factor + bias);
				colorB = (float)(colorB * factor + bias);
				
				newColors[index] = new Color4F(colorR, colorG, colorB, colorA);
				
				count++;
			}
		}
		
		if(hasChangeBegun) {
			if(count > 0) {
				changeAdd(new StateChange(newColors, oldColors, resolutionX, resolutionX, resolutionY, resolutionY));
			}
			
			changeEnd();
		}
		
		if(count > 0) {
			this.colors = newColors;
		}
		
		return count > 0;
	}
	
	@Override
	public boolean convolve(final ConvolutionKernelNF convolutionKernel, final int[] indices) {
		Objects.requireNonNull(convolutionKernel, "convolutionKernel == null");
		Objects.requireNonNull(indices, "indices == null");
		
		if(indices.length == 0) {
			return false;
		}
		
		final float bias = convolutionKernel.getBias();
		final float factor = convolutionKernel.getFactor();
		
		final float[] elements = convolutionKernel.getElements();
		
		final int kernelResolution = convolutionKernel.getResolution();
		final int kernelOffset = (kernelResolution - 1) / 2;
		
		final int resolution = getResolution();
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		final Color4F[] oldColors = this.colors;
		final Color4F[] newColors = this.colors.clone();
		
		final boolean hasChangeBegun = changeBegin();
		
		int count = 0;
		
		for(final int index : indices) {
			if(index >= 0 && index < resolution) {
				final int x = index % resolutionX;
				final int y = index / resolutionX;
				
				final int xOffset = x - kernelOffset;
				final int yOffset = y - kernelOffset;
				
				float colorR = 0.0F;
				float colorG = 0.0F;
				float colorB = 0.0F;
				float colorA = oldColors[index].a;
				
				for(int kernelY = 0; kernelY < kernelResolution; kernelY++) {
					final int imageY = yOffset + kernelY;
					final int imageRow = imageY * resolutionX;
					
					final int kernelRow = kernelY * kernelResolution;
					
					for(int kernelX = 0; kernelX < kernelResolution; kernelX++) {
						final int imageX = xOffset + kernelX;
						
						if(imageX >= 0 && imageX < resolutionX && imageY >= 0 && imageY < resolutionY) {
							final Color4F color = oldColors[imageRow + imageX];
							
							final float element = elements[kernelRow + kernelX];
							
							colorR += color.r * element;
							colorG += color.g * element;
							colorB += color.b * element;
						}
					}
				}
				
				colorR = colorR * factor + bias;
				colorG = colorG * factor + bias;
				colorB = colorB * factor + bias;
				
				newColors[index] = new Color4F(colorR, colorG, colorB, colorA);
				
				count++;
			}
		}
		
		if(hasChangeBegun) {
			if(count > 0) {
				changeAdd(new StateChange(newColors, oldColors, resolutionX, resolutionX, resolutionY, resolutionY));
			}
			
			changeEnd();
		}
		
		if(count > 0) {
			this.colors = newColors;
		}
		
		return count > 0;
	}
	
	@Override
	public boolean equals(final Object object) {
		if(!super.equals(object)) {
			return false;
		} else if(!(object instanceof Color4FData)) {
			return false;
		} else if(!Arrays.equals(this.colors, Color4FData.class.cast(object).colors)) {
			return false;
		} else if(this.resolutionX != Color4FData.class.cast(object).resolutionX) {
			return false;
		} else if(this.resolutionY != Color4FData.class.cast(object).resolutionY) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public boolean rotate(final double angle, final boolean isAngleInRadians) {
		if(Utilities.isZero(angle)) {
			return false;
		}
		
		final double angleDegrees = isAngleInRadians ? Utilities.toDegrees(angle) : angle;
		
		if(Utilities.equals(angleDegrees, +360.0D) || Utilities.equals(angleDegrees, -360.0D)) {
			return false;
		}
		
		final double angleRadians = isAngleInRadians ? angle : Utilities.toRadians(angle);
		final double angleRadiansCos = Utilities.cos(angleRadians);
		final double angleRadiansSin = Utilities.sin(angleRadians);
		
		final double directionAX = -this.resolutionX * 0.5D;
		final double directionAY = -this.resolutionY * 0.5D;
		
		final double rectangleAAX = directionAX;
		final double rectangleAAY = directionAY;
		final double rectangleABX = directionAX;
		final double rectangleABY = directionAY + this.resolutionY;
		final double rectangleACX = directionAX + this.resolutionX;
		final double rectangleACY = directionAY + this.resolutionY;
		final double rectangleADX = directionAX + this.resolutionX;
		final double rectangleADY = directionAY;
		
		final double rectangleBAX = rectangleAAX * angleRadiansCos - rectangleAAY * angleRadiansSin;
		final double rectangleBAY = rectangleAAY * angleRadiansCos + rectangleAAX * angleRadiansSin;
		final double rectangleBBX = rectangleABX * angleRadiansCos - rectangleABY * angleRadiansSin;
		final double rectangleBBY = rectangleABY * angleRadiansCos + rectangleABX * angleRadiansSin;
		final double rectangleBCX = rectangleACX * angleRadiansCos - rectangleACY * angleRadiansSin;
		final double rectangleBCY = rectangleACY * angleRadiansCos + rectangleACX * angleRadiansSin;
		final double rectangleBDX = rectangleADX * angleRadiansCos - rectangleADY * angleRadiansSin;
		final double rectangleBDY = rectangleADY * angleRadiansCos + rectangleADX * angleRadiansSin;
		
		final double minimumX = Utilities.min(rectangleBAX, rectangleBBX, rectangleBCX, rectangleBDX);
		final double minimumY = Utilities.min(rectangleBAY, rectangleBBY, rectangleBCY, rectangleBDY);
		final double maximumX = Utilities.max(rectangleBAX, rectangleBBX, rectangleBCX, rectangleBDX);
		final double maximumY = Utilities.max(rectangleBAY, rectangleBBY, rectangleBCY, rectangleBDY);
		
		final int newResolutionX = (int)(maximumX - minimumX);
		final int newResolutionY = (int)(maximumY - minimumY);
		
		final int oldResolutionX = this.resolutionX;
		final int oldResolutionY = this.resolutionY;
		
		final Color4F[] newColors = new Color4F[newResolutionX * newResolutionY];
		final Color4F[] oldColors = this.colors;
		
		final double directionBX = Utilities.abs(Utilities.min(minimumX, 0.0D));
		final double directionBY = Utilities.abs(Utilities.min(minimumY, 0.0D));
		
		for(int y = 0; y < newResolutionY; y++) {
			for(int x = 0; x < newResolutionX; x++) {
				final double aX = x - directionBX;
				final double aY = y - directionBY;
				
				final double bX = aX * angleRadiansCos - aY * -angleRadiansSin;
				final double bY = aY * angleRadiansCos + aX * -angleRadiansSin;
				
				final int cX = (int)(bX - directionAX - 0.5D);
				final int cY = (int)(bY - directionAY - 0.5D);
				
				newColors[y * newResolutionX + x] = getColor4F(cX, cY);
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
	
	@Override
	public boolean rotate(final float angle, final boolean isAngleInRadians) {
		if(Utilities.isZero(angle)) {
			return false;
		}
		
		final float angleDegrees = isAngleInRadians ? Utilities.toDegrees(angle) : angle;
		
		if(Utilities.equals(angleDegrees, +360.0F) || Utilities.equals(angleDegrees, -360.0F)) {
			return false;
		}
		
		final float angleRadians = isAngleInRadians ? angle : Utilities.toRadians(angle);
		final float angleRadiansCos = Utilities.cos(angleRadians);
		final float angleRadiansSin = Utilities.sin(angleRadians);
		
		final float directionAX = -this.resolutionX * 0.5F;
		final float directionAY = -this.resolutionY * 0.5F;
		
		final float rectangleAAX = directionAX;
		final float rectangleAAY = directionAY;
		final float rectangleABX = directionAX;
		final float rectangleABY = directionAY + this.resolutionY;
		final float rectangleACX = directionAX + this.resolutionX;
		final float rectangleACY = directionAY + this.resolutionY;
		final float rectangleADX = directionAX + this.resolutionX;
		final float rectangleADY = directionAY;
		
		final float rectangleBAX = rectangleAAX * angleRadiansCos - rectangleAAY * angleRadiansSin;
		final float rectangleBAY = rectangleAAY * angleRadiansCos + rectangleAAX * angleRadiansSin;
		final float rectangleBBX = rectangleABX * angleRadiansCos - rectangleABY * angleRadiansSin;
		final float rectangleBBY = rectangleABY * angleRadiansCos + rectangleABX * angleRadiansSin;
		final float rectangleBCX = rectangleACX * angleRadiansCos - rectangleACY * angleRadiansSin;
		final float rectangleBCY = rectangleACY * angleRadiansCos + rectangleACX * angleRadiansSin;
		final float rectangleBDX = rectangleADX * angleRadiansCos - rectangleADY * angleRadiansSin;
		final float rectangleBDY = rectangleADY * angleRadiansCos + rectangleADX * angleRadiansSin;
		
		final float minimumX = Utilities.min(rectangleBAX, rectangleBBX, rectangleBCX, rectangleBDX);
		final float minimumY = Utilities.min(rectangleBAY, rectangleBBY, rectangleBCY, rectangleBDY);
		final float maximumX = Utilities.max(rectangleBAX, rectangleBBX, rectangleBCX, rectangleBDX);
		final float maximumY = Utilities.max(rectangleBAY, rectangleBBY, rectangleBCY, rectangleBDY);
		
		final int newResolutionX = (int)(maximumX - minimumX);
		final int newResolutionY = (int)(maximumY - minimumY);
		
		final int oldResolutionX = this.resolutionX;
		final int oldResolutionY = this.resolutionY;
		
		final Color4F[] newColors = new Color4F[newResolutionX * newResolutionY];
		final Color4F[] oldColors = this.colors;
		
		final float directionBX = Utilities.abs(Utilities.min(minimumX, 0.0F));
		final float directionBY = Utilities.abs(Utilities.min(minimumY, 0.0F));
		
		for(int y = 0; y < newResolutionY; y++) {
			for(int x = 0; x < newResolutionX; x++) {
				final float aX = x - directionBX;
				final float aY = y - directionBY;
				
				final float bX = aX * angleRadiansCos - aY * -angleRadiansSin;
				final float bY = aY * angleRadiansCos + aX * -angleRadiansSin;
				
				final int cX = (int)(bX - directionAX - 0.5F);
				final int cY = (int)(bY - directionAY - 0.5F);
				
				newColors[y * newResolutionX + x] = getColor4F(cX, cY);
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
		
		final Color4F[] newColors = new Color4F[newResolutionX * newResolutionY];
		final Color4F[] oldColors = this.colors;
		
		final float scaleX = (float)(oldResolutionX) / (float)(newResolutionX);
		final float scaleY = (float)(oldResolutionY) / (float)(newResolutionY);
		
		for(int y = 0; y < newResolutionY; y++) {
			for(int x = 0; x < newResolutionX; x++) {
				newColors[y * newResolutionX + x] = getColor4F(x * scaleX, y * scaleY);
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
	
	@Override
	public boolean setColor3D(final Color3D color, final int index) {
		return setColor4D(new Color4D(Objects.requireNonNull(color, "color == null")), index);
	}
	
	@Override
	public boolean setColor3D(final Color3D color, final int x, final int y) {
		return setColor4D(new Color4D(Objects.requireNonNull(color, "color == null")), x, y);
	}
	
	@Override
	public boolean setColor3F(final Color3F color, final int index) {
		return setColor4D(new Color4D(Objects.requireNonNull(color, "color == null")), index);
	}
	
	@Override
	public boolean setColor3F(final Color3F color, final int x, final int y) {
		return setColor4D(new Color4D(Objects.requireNonNull(color, "color == null")), x, y);
	}
	
	@Override
	public boolean setColor4D(final Color4D color, final int index) {
		return setColor4F(new Color4F(Objects.requireNonNull(color, "color == null")), index);
	}
	
	@Override
	public boolean setColor4D(final Color4D color, final int x, final int y) {
		return setColor4F(new Color4F(Objects.requireNonNull(color, "color == null")), x, y);
	}
	
	@Override
	public boolean setColor4F(final Color4F color, final int index) {
		Objects.requireNonNull(color, "color == null");
		
		if(index >= 0 && index < this.colors.length) {
			final Color4F newColor = color;
			final Color4F oldColor = this.colors[index];
			
			if(!newColor.equals(oldColor)) {
				final boolean hasChangeBegun = hasChangeBegun();
				final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
				
				if(hasChangeBegun || hasChangeBegunNow) {
					changeAdd(new PixelChange(newColor, oldColor, index));
					
					if(hasChangeBegunNow) {
						changeEnd();
					}
				}
				
				this.colors[index] = newColor;
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean setColor4F(final Color4F color, final int x, final int y) {
		Objects.requireNonNull(color, "color == null");
		
		if(x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY) {
			final int index = y * this.resolutionX + x;
			
			final Color4F newColor = color;
			final Color4F oldColor = this.colors[index];
			
			if(!newColor.equals(oldColor)) {
				final boolean hasChangeBegun = hasChangeBegun();
				final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
				
				if(hasChangeBegun || hasChangeBegunNow) {
					changeAdd(new PixelChange(newColor, oldColor, index));
					
					if(hasChangeBegunNow) {
						changeEnd();
					}
				}
				
				this.colors[index] = newColor;
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean setColorARGB(final int colorARGB, final int index) {
		if(index >= 0 && index < this.colors.length) {
			final Color4F newColor = Color4F.fromIntARGB(colorARGB);
			final Color4F oldColor = this.colors[index];
			
			if(!newColor.equals(oldColor)) {
				final boolean hasChangeBegun = hasChangeBegun();
				final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
				
				if(hasChangeBegun || hasChangeBegunNow) {
					changeAdd(new PixelChange(newColor, oldColor, index));
					
					if(hasChangeBegunNow) {
						changeEnd();
					}
				}
				
				this.colors[index] = newColor;
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean setColorARGB(final int colorARGB, final int x, final int y) {
		if(x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY) {
			final int index = y * this.resolutionX + x;
			
			final Color4F newColor = Color4F.fromIntARGB(colorARGB);
			final Color4F oldColor = this.colors[index];
			
			if(!newColor.equals(oldColor)) {
				final boolean hasChangeBegun = hasChangeBegun();
				final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
				
				if(hasChangeBegun || hasChangeBegunNow) {
					changeAdd(new PixelChange(newColor, oldColor, index));
					
					if(hasChangeBegunNow) {
						changeEnd();
					}
				}
				
				this.colors[index] = newColor;
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean setContent(final Data data) {
		Objects.requireNonNull(data, "data == null");
		
		if(data instanceof Color4FData) {
			final Color4FData color4FData = Color4FData.class.cast(data);
			
			final Color4F[] newColors = color4FData.colors.clone();
			final Color4F[] oldColors = this.colors;
			
			final int newResolutionX = color4FData.resolutionX;
			final int newResolutionY = color4FData.resolutionY;
			
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
		
		final Color4F[] newColors = new Color4F[newResolutionX * newResolutionY];
		final Color4F[] oldColors = this.colors;
		
		for(int y = 0; y < newResolutionY; y++) {
			for(int x = 0; x < newResolutionX; x++) {
				final int newIndex = y * newResolutionX + x;
				
				if(x < oldResolutionX && y < oldResolutionY) {
					final int oldIndex = y * oldResolutionX + x;
					
					newColors[newIndex] = oldColors[oldIndex];
				} else {
					newColors[newIndex] = Color4F.WHITE;
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
	
	@Override
	public boolean swap(final int indexA, final int indexB) {
		if(indexA < 0 || indexA >= this.colors.length) {
			return false;
		}
		
		if(indexB < 0 || indexB >= this.colors.length) {
			return false;
		}
		
		final Color4F colorA = this.colors[indexA];
		final Color4F colorB = this.colors[indexB];
		
		final boolean hasChangeBegun = hasChangeBegun();
		final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
		
		if(hasChangeBegun || hasChangeBegunNow) {
			changeAdd(new PixelChange(colorB, colorA, indexA));
			changeAdd(new PixelChange(colorA, colorB, indexB));
			
			if(hasChangeBegunNow) {
				changeEnd();
			}
		}
		
		this.colors[indexA] = colorB;
		this.colors[indexB] = colorA;
		
		return true;
	}
	
	@Override
	public int cache() {
		int cached = 0;
		
		for(int i = 0; i < this.colors.length; i++) {
			final Color4F oldColor = this.colors[i];
			final Color4F newColor = Color4F.getCached(oldColor);
			
			if(oldColor != newColor) {
				cached++;
				
				this.colors[i] = newColor;
			}
		}
		
		return cached;
	}
	
	@Override
	public int getColorARGB(final int index) {
		return getColor4F(index).toIntARGB();
	}
	
	@Override
	public int getColorARGB(final int x, final int y) {
		return getColor4F(x, y).toIntARGB();
	}
	
	@Override
	public int getResolution() {
		return this.colors.length;
	}
	
	@Override
	public int getResolutionX() {
		return this.resolutionX;
	}
	
	@Override
	public int getResolutionY() {
		return this.resolutionY;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(super.hashCode()), Integer.valueOf(Arrays.hashCode(this.colors)), Integer.valueOf(this.resolutionX), Integer.valueOf(this.resolutionY));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	void updatePixel(final Color4F color, final int index) {
		this.colors[index] = color;
	}
	
	void updateState(final Color4F[] colors, final int resolutionX, final int resolutionY) {
		this.colors = colors.clone();
		this.resolutionX = resolutionX;
		this.resolutionY = resolutionY;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class PixelChange implements Change {
		private final Color4F colorRedo;
		private final Color4F colorUndo;
		private final int index;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public PixelChange(final Color4F colorRedo, final Color4F colorUndo, final int index) {
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
			if(data instanceof Color4FData) {
				final
				Color4FData color4FData = Color4FData.class.cast(data);
				color4FData.updatePixel(this.colorRedo, this.index);
			}
		}
		
		@Override
		public void undo(final Data data) {
			if(data instanceof Color4FData) {
				final
				Color4FData color4FData = Color4FData.class.cast(data);
				color4FData.updatePixel(this.colorUndo, this.index);
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class StateChange implements Change {
		private final Color4F[] colorsRedo;
		private final Color4F[] colorsUndo;
		private final int resolutionXRedo;
		private final int resolutionXUndo;
		private final int resolutionYRedo;
		private final int resolutionYUndo;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public StateChange(final Color4F[] colorsRedo, final Color4F[] colorsUndo, final int resolutionXRedo, final int resolutionXUndo, final int resolutionYRedo, final int resolutionYUndo) {
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
			if(data instanceof Color4FData) {
				final
				Color4FData color4FData = Color4FData.class.cast(data);
				color4FData.updateState(this.colorsRedo, this.resolutionXRedo, this.resolutionYRedo);
			}
		}
		
		@Override
		public void undo(final Data data) {
			if(data instanceof Color4FData) {
				final
				Color4FData color4FData = Color4FData.class.cast(data);
				color4FData.updateState(this.colorsUndo, this.resolutionXUndo, this.resolutionYUndo);
			}
		}
	}
}