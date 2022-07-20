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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

import org.macroing.img4j.color.Color;
import org.macroing.img4j.color.Color3D;
import org.macroing.img4j.color.Color3F;
import org.macroing.img4j.color.Color4D;
import org.macroing.img4j.color.Color4F;
import org.macroing.img4j.kernel.ConvolutionKernelND;
import org.macroing.img4j.kernel.ConvolutionKernelNF;
import org.macroing.img4j.utility.BufferedImages;

final class ColorARGBData extends Data {
	private int resolutionX;
	private int resolutionY;
	private int[] colors;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ColorARGBData() {
		this(1024, 768);
	}
	
	public ColorARGBData(final BufferedImage bufferedImage) {
		this.resolutionX = bufferedImage.getWidth();
		this.resolutionY = bufferedImage.getHeight();
		this.colors = DataBufferInt.class.cast(BufferedImages.getCompatibleBufferedImage(bufferedImage).getRaster().getDataBuffer()).getData().clone();
	}
	
	public ColorARGBData(final ColorARGBData colorARGBData) {
		super(colorARGBData);
		
		this.resolutionX = colorARGBData.resolutionX;
		this.resolutionY = colorARGBData.resolutionY;
		this.colors = colorARGBData.colors.clone();
	}
	
	public ColorARGBData(final ColorARGBData colorARGBData, final boolean isIgnoringChangeHistory) {
		super(colorARGBData, isIgnoringChangeHistory);
		
		this.resolutionX = colorARGBData.resolutionX;
		this.resolutionY = colorARGBData.resolutionY;
		this.colors = colorARGBData.colors.clone();
	}
	
	public ColorARGBData(final int resolutionX, final int resolutionY) {
		this(resolutionX, resolutionY, Color4D.WHITE);
	}
	
	public ColorARGBData(final int resolutionX, final int resolutionY, final Color4D color) {
		this.resolutionX = Utilities.requireRange(resolutionX, 1, Integer.MAX_VALUE, "resolutionX");
		this.resolutionY = Utilities.requireRange(resolutionY, 1, Integer.MAX_VALUE, "resolutionY");
		this.colors = new int[Utilities.requireRange(resolutionX * resolutionY, 1, Integer.MAX_VALUE, "resolutionX * resolutionY")];
		
		Arrays.fill(this.colors, color.toIntARGB());
	}
	
	public ColorARGBData(final int resolutionX, final int resolutionY, final Color4F color) {
		this.resolutionX = Utilities.requireRange(resolutionX, 1, Integer.MAX_VALUE, "resolutionX");
		this.resolutionY = Utilities.requireRange(resolutionY, 1, Integer.MAX_VALUE, "resolutionY");
		this.colors = new int[Utilities.requireRange(resolutionX * resolutionY, 1, Integer.MAX_VALUE, "resolutionX * resolutionY")];
		
		Arrays.fill(this.colors, color.toIntARGB());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public BufferedImage toBufferedImage(final boolean isRGB) {
		final BufferedImage bufferedImage = new BufferedImage(this.resolutionX, this.resolutionY, isRGB ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
		
		final int[] dataSource = this.colors;
		final int[] dataTarget = DataBufferInt.class.cast(bufferedImage.getRaster().getDataBuffer()).getData();
		
		System.arraycopy(dataSource, 0, dataTarget, 0, dataSource.length);
		
		return bufferedImage;
	}
	
	@Override
	public Color3D getColor3D(final int index) {
		return Color3D.fromIntARGB(getColorARGB(index));
	}
	
	@Override
	public Color3D getColor3D(final int x, final int y) {
		return Color3D.fromIntARGB(getColorARGB(x, y));
	}
	
	@Override
	public Color3F getColor3F(final int index) {
		return Color3F.fromIntARGB(getColorARGB(index));
	}
	
	@Override
	public Color3F getColor3F(final int x, final int y) {
		return Color3F.fromIntARGB(getColorARGB(x, y));
	}
	
	@Override
	public Color4D getColor4D(final int index) {
		return Color4D.fromIntARGB(getColorARGB(index));
	}
	
	@Override
	public Color4D getColor4D(final int x, final int y) {
		return Color4D.fromIntARGB(getColorARGB(x, y));
	}
	
	@Override
	public Color4F getColor4F(final int index) {
		return Color4F.fromIntARGB(getColorARGB(index));
	}
	
	@Override
	public Color4F getColor4F(final int x, final int y) {
		return Color4F.fromIntARGB(getColorARGB(x, y));
	}
	
	@Override
	public Data copy(final boolean isIgnoringChangeHistory) {
		return new ColorARGBData(this, isIgnoringChangeHistory);
	}
	
	@Override
	public Data draw(final Consumer<Graphics2D> graphics2DConsumer) {
		Objects.requireNonNull(graphics2DConsumer, "graphics2DConsumer == null");
		
		final BufferedImage bufferedImage = toBufferedImage(false);
		
		final Graphics2D graphics2D = bufferedImage.createGraphics();
		
		graphics2DConsumer.accept(graphics2D);
		
		final int[] colors = DataBufferInt.class.cast(bufferedImage.getRaster().getDataBuffer()).getData();
		
		if(changeBegin()) {
			changeAdd(new StateChange(this.resolutionX, this.resolutionX, this.resolutionY, this.resolutionY, colors, this.colors));
			changeEnd();
		}
		
		this.colors = colors;
		
		return this;
	}
	
	@Override
	public DataFactory getDataFactory() {
		return new ColorARGBDataFactory();
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
		
		final int resolution  = getResolution();
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		final int[] oldColors = this.colors;
		final int[] newColors = this.colors.clone();
		
		final double[] colors = doUnpackColorsAsDoubleArrayRGB();
		
		final boolean hasChangeBegun = changeBegin();
		
		int count = 0;
		
		for(final int index : indices) {
			if(index >= 0 && index < resolution) {
				final int x = index % resolutionX;
				final int y = index / resolutionX;
				
				final int xOffset = x - kernelOffset;
				final int yOffset = y - kernelOffset;
				
				double colorR = 0.0D;
				double colorG = 0.0D;
				double colorB = 0.0D;
				double colorA = Color.unpackAAsDouble(oldColors[index]);
				
				for(int kernelY = 0; kernelY < kernelResolution; kernelY++) {
					final int imageY = yOffset + kernelY;
					final int imageRow = imageY * resolutionX;
					
					final int kernelRow = kernelY * kernelResolution;
					
					for(int kernelX = 0; kernelX < kernelResolution; kernelX++) {
						final int imageX = xOffset + kernelX;
						
						if(imageX >= 0 && imageX < resolutionX && imageY >= 0 && imageY < resolutionY) {
							final int imageIndex = (imageRow + imageX) * 3;
							
							final double element = elements[kernelRow + kernelX];
							
							colorR += colors[imageIndex + 0] * element;
							colorG += colors[imageIndex + 1] * element;
							colorB += colors[imageIndex + 2] * element;
						}
					}
				}
				
				colorR = colorR * factor + bias;
				colorG = colorG * factor + bias;
				colorB = colorB * factor + bias;
				
				newColors[index] = Color.packRGBA(colorR, colorG, colorB, colorA);
				
				count++;
			}
		}
		
		if(hasChangeBegun) {
			if(count > 0) {
				changeAdd(new StateChange(resolutionX, resolutionX, resolutionY, resolutionY, newColors, oldColors));
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
		
		final int resolution  = getResolution();
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		final int[] oldColors = this.colors;
		final int[] newColors = this.colors.clone();
		
		final float[] colors = doUnpackColorsAsFloatArrayRGB();
		
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
				float colorA = Color.unpackAAsFloat(oldColors[index]);
				
				for(int kernelY = 0; kernelY < kernelResolution; kernelY++) {
					final int imageY = yOffset + kernelY;
					final int imageRow = imageY * resolutionX;
					
					final int kernelRow = kernelY * kernelResolution;
					
					for(int kernelX = 0; kernelX < kernelResolution; kernelX++) {
						final int imageX = xOffset + kernelX;
						
						if(imageX >= 0 && imageX < resolutionX && imageY >= 0 && imageY < resolutionY) {
							final int imageIndex = (imageRow + imageX) * 3;
							
							final float element = elements[kernelRow + kernelX];
							
							colorR += colors[imageIndex + 0] * element;
							colorG += colors[imageIndex + 1] * element;
							colorB += colors[imageIndex + 2] * element;
						}
					}
				}
				
				colorR = colorR * factor + bias;
				colorG = colorG * factor + bias;
				colorB = colorB * factor + bias;
				
				newColors[index] = Color.packRGBA(colorR, colorG, colorB, colorA);
				
				count++;
			}
		}
		
		if(hasChangeBegun) {
			if(count > 0) {
				changeAdd(new StateChange(resolutionX, resolutionX, resolutionY, resolutionY, newColors, oldColors));
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
		} else if(!(object instanceof ColorARGBData)) {
			return false;
		} else if(this.resolutionX != ColorARGBData.class.cast(object).resolutionX) {
			return false;
		} else if(this.resolutionY != ColorARGBData.class.cast(object).resolutionY) {
			return false;
		} else if(!Arrays.equals(this.colors, ColorARGBData.class.cast(object).colors)) {
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
		
		final int[] newColors = new int[newResolutionX * newResolutionY];
		final int[] oldColors = this.colors;
		
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
				
				newColors[y * newResolutionX + x] = getColorARGB(cX, cY);
			}
		}
		
		if(changeBegin()) {
			changeAdd(new StateChange(newResolutionX, oldResolutionX, newResolutionY, oldResolutionY, newColors, oldColors));
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
		
		final int[] newColors = new int[newResolutionX * newResolutionY];
		final int[] oldColors = this.colors;
		
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
				
				newColors[y * newResolutionX + x] = getColorARGB(cX, cY);
			}
		}
		
		if(changeBegin()) {
			changeAdd(new StateChange(newResolutionX, oldResolutionX, newResolutionY, oldResolutionY, newColors, oldColors));
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
		
		final int[] newColors = new int[newResolutionX * newResolutionY];
		final int[] oldColors = this.colors;
		
		final double scaleX = (double)(oldResolutionX) / (double)(newResolutionX);
		final double scaleY = (double)(oldResolutionY) / (double)(newResolutionY);
		
		for(int y = 0; y < newResolutionY; y++) {
			for(int x = 0; x < newResolutionX; x++) {
				newColors[y * newResolutionX + x] = getColorARGB(x * scaleX, y * scaleY);
			}
		}
		
		if(changeBegin()) {
			changeAdd(new StateChange(newResolutionX, oldResolutionX, newResolutionY, oldResolutionY, newColors, oldColors));
			changeEnd();
		}
		
		this.colors = newColors;
		this.resolutionX = newResolutionX;
		this.resolutionY = newResolutionY;
		
		return true;
	}
	
	@Override
	public boolean setColor3D(final Color3D color, final int index) {
		Objects.requireNonNull(color, "color == null");
		
		if(index >= 0 && index < this.colors.length) {
			final int newColor = color.toIntARGB();
			final int oldColor = this.colors[index];
			
			if(newColor != oldColor) {
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
	public boolean setColor3D(final Color3D color, final int x, final int y) {
		Objects.requireNonNull(color, "color == null");
		
		if(x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY) {
			final int index = y * this.resolutionX + x;
			
			final int newColor = color.toIntARGB();
			final int oldColor = this.colors[index];
			
			if(newColor != oldColor) {
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
	public boolean setColor3F(final Color3F color, final int index) {
		Objects.requireNonNull(color, "color == null");
		
		if(index >= 0 && index < this.colors.length) {
			final int newColor = color.toIntARGB();
			final int oldColor = this.colors[index];
			
			if(newColor != oldColor) {
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
	public boolean setColor3F(final Color3F color, final int x, final int y) {
		Objects.requireNonNull(color, "color == null");
		
		if(x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY) {
			final int index = y * this.resolutionX + x;
			
			final int newColor = color.toIntARGB();
			final int oldColor = this.colors[index];
			
			if(newColor != oldColor) {
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
	public boolean setColor4D(final Color4D color, final int index) {
		Objects.requireNonNull(color, "color == null");
		
		if(index >= 0 && index < this.colors.length) {
			final int newColor = color.toIntARGB();
			final int oldColor = this.colors[index];
			
			if(newColor != oldColor) {
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
	public boolean setColor4D(final Color4D color, final int x, final int y) {
		Objects.requireNonNull(color, "color == null");
		
		if(x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY) {
			final int index = y * this.resolutionX + x;
			
			final int newColor = color.toIntARGB();
			final int oldColor = this.colors[index];
			
			if(newColor != oldColor) {
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
	public boolean setColor4F(final Color4F color, final int index) {
		Objects.requireNonNull(color, "color == null");
		
		if(index >= 0 && index < this.colors.length) {
			final int newColor = color.toIntARGB();
			final int oldColor = this.colors[index];
			
			if(newColor != oldColor) {
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
			
			final int newColor = color.toIntARGB();
			final int oldColor = this.colors[index];
			
			if(newColor != oldColor) {
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
			final int newColor = colorARGB;
			final int oldColor = this.colors[index];
			
			if(newColor != oldColor) {
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
			
			final int newColor = colorARGB;
			final int oldColor = this.colors[index];
			
			if(newColor != oldColor) {
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
		
		if(data instanceof ColorARGBData) {
			final ColorARGBData colorARGBData = ColorARGBData.class.cast(data);
			
			final int[] newColors = colorARGBData.colors.clone();
			final int[] oldColors = this.colors;
			
			final int newResolutionX = colorARGBData.resolutionX;
			final int newResolutionY = colorARGBData.resolutionY;
			
			final int oldResolutionX = this.resolutionX;
			final int oldResolutionY = this.resolutionY;
			
			if(changeBegin()) {
				changeAdd(new StateChange(newResolutionX, oldResolutionX, newResolutionY, oldResolutionY, newColors, oldColors));
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
		
		final int[] newColors = new int[newResolutionX * newResolutionY];
		final int[] oldColors = this.colors;
		
		for(int y = 0; y < newResolutionY; y++) {
			for(int x = 0; x < newResolutionX; x++) {
				final int newIndex = y * newResolutionX + x;
				
				if(x < oldResolutionX && y < oldResolutionY) {
					final int oldIndex = y * oldResolutionX + x;
					
					newColors[newIndex] = oldColors[oldIndex];
				} else {
					newColors[newIndex] = Color.WHITE;
				}
			}
		}
		
		if(changeBegin()) {
			changeAdd(new StateChange(newResolutionX, oldResolutionX, newResolutionY, oldResolutionY, newColors, oldColors));
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
		
		final int colorA = this.colors[indexA];
		final int colorB = this.colors[indexB];
		
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
		return 0;
	}
	
	@Override
	public int getColorARGB(final int index) {
		return index >= 0 && index < this.colors.length ? this.colors[index] : Color.TRANSPARENT;
	}
	
	@Override
	public int getColorARGB(final int x, final int y) {
		return x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY ? this.colors[y * this.resolutionX + x] : Color.TRANSPARENT;
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
		return Objects.hash(Integer.valueOf(super.hashCode()), Integer.valueOf(this.resolutionX), Integer.valueOf(this.resolutionY), Integer.valueOf(Arrays.hashCode(this.colors)));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	void updatePixel(final int color, final int index) {
		this.colors[index] = color;
	}
	
	void updateState(final int resolutionX, final int resolutionY, final int[] colors) {
		this.resolutionX = resolutionX;
		this.resolutionY = resolutionY;
		this.colors = colors.clone();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class PixelChange implements Change {
		private final int colorRedo;
		private final int colorUndo;
		private final int index;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public PixelChange(final int colorRedo, final int colorUndo, final int index) {
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
			} else if(this.colorRedo != PixelChange.class.cast(object).colorRedo) {
				return false;
			} else if(this.colorUndo != PixelChange.class.cast(object).colorUndo) {
				return false;
			} else if(this.index != PixelChange.class.cast(object).index) {
				return false;
			} else {
				return true;
			}
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(Integer.valueOf(this.colorRedo), Integer.valueOf(this.colorUndo), Integer.valueOf(this.index));
		}
		
		@Override
		public void redo(final Data data) {
			if(data instanceof ColorARGBData) {
				final
				ColorARGBData colorARGBData = ColorARGBData.class.cast(data);
				colorARGBData.updatePixel(this.colorRedo, this.index);
			}
		}
		
		@Override
		public void undo(final Data data) {
			if(data instanceof ColorARGBData) {
				final
				ColorARGBData colorARGBData = ColorARGBData.class.cast(data);
				colorARGBData.updatePixel(this.colorUndo, this.index);
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class StateChange implements Change {
		private final int resolutionXRedo;
		private final int resolutionXUndo;
		private final int resolutionYRedo;
		private final int resolutionYUndo;
		private final int[] colorsRedo;
		private final int[] colorsUndo;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public StateChange(final int resolutionXRedo, final int resolutionXUndo, final int resolutionYRedo, final int resolutionYUndo, final int[] colorsRedo, final int[] colorsUndo) {
			this.resolutionXRedo = resolutionXRedo;
			this.resolutionXUndo = resolutionXUndo;
			this.resolutionYRedo = resolutionYRedo;
			this.resolutionYUndo = resolutionYUndo;
			this.colorsRedo = colorsRedo.clone();
			this.colorsUndo = colorsUndo.clone();
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public boolean equals(final Object object) {
			if(object == this) {
				return true;
			} else if(!(object instanceof StateChange)) {
				return false;
			} else if(this.resolutionXRedo != StateChange.class.cast(object).resolutionXRedo) {
				return false;
			} else if(this.resolutionXUndo != StateChange.class.cast(object).resolutionXUndo) {
				return false;
			} else if(this.resolutionYRedo != StateChange.class.cast(object).resolutionYRedo) {
				return false;
			} else if(this.resolutionYUndo != StateChange.class.cast(object).resolutionYUndo) {
				return false;
			} else if(!Arrays.equals(this.colorsRedo, StateChange.class.cast(object).colorsRedo)) {
				return false;
			} else if(!Arrays.equals(this.colorsUndo, StateChange.class.cast(object).colorsUndo)) {
				return false;
			} else {
				return true;
			}
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(Integer.valueOf(this.resolutionXRedo), Integer.valueOf(this.resolutionXUndo), Integer.valueOf(this.resolutionYRedo), Integer.valueOf(this.resolutionYUndo), Integer.valueOf(Arrays.hashCode(this.colorsRedo)), Integer.valueOf(Arrays.hashCode(this.colorsUndo)));
		}
		
		@Override
		public void redo(final Data data) {
			if(data instanceof ColorARGBData) {
				final
				ColorARGBData colorARGBData = ColorARGBData.class.cast(data);
				colorARGBData.updateState(this.resolutionXRedo, this.resolutionYRedo, this.colorsRedo);
			}
		}
		
		@Override
		public void undo(final Data data) {
			if(data instanceof ColorARGBData) {
				final
				ColorARGBData colorARGBData = ColorARGBData.class.cast(data);
				colorARGBData.updateState(this.resolutionXUndo, this.resolutionYUndo, this.colorsUndo);
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private double[] doUnpackColorsAsDoubleArrayRGB() {
		final double[] colors = new double[this.colors.length * 3];
		
		for(int i = 0, j = 0; i < this.colors.length; i++, j = i * 3) {
			colors[j + 0] = Color.unpackRAsDouble(this.colors[i]);
			colors[j + 1] = Color.unpackGAsDouble(this.colors[i]);
			colors[j + 2] = Color.unpackBAsDouble(this.colors[i]);
		}
		
		return colors;
	}
	
	private float[] doUnpackColorsAsFloatArrayRGB() {
		final float[] colors = new float[this.colors.length * 3];
		
		for(int i = 0, j = 0; i < this.colors.length; i++, j = i * 3) {
			colors[j + 0] = Color.unpackRAsFloat(this.colors[i]);
			colors[j + 1] = Color.unpackGAsFloat(this.colors[i]);
			colors[j + 2] = Color.unpackBAsFloat(this.colors[i]);
		}
		
		return colors;
	}
}