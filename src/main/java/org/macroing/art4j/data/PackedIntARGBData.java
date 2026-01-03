/**
 * Copyright 2022 - 2026 J&#246;rgen Lundgren
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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.macroing.art4j.color.Color3D;
import org.macroing.art4j.color.Color3F;
import org.macroing.art4j.color.Color3I;
import org.macroing.art4j.color.Color4D;
import org.macroing.art4j.color.Color4F;
import org.macroing.art4j.color.Color4I;
import org.macroing.art4j.kernel.ConvolutionKernelND;
import org.macroing.art4j.kernel.ConvolutionKernelNF;
import org.macroing.geo4j.common.Point2I;
import org.macroing.geo4j.shape.Shape2I;
import org.macroing.geo4j.shape.rectangle.Rectangle2I;
import org.macroing.java.awt.image.BufferedImages;
import org.macroing.java.lang.Doubles;
import org.macroing.java.lang.Floats;
import org.macroing.java.lang.Ints;

final class PackedIntARGBData extends Data {
	private int resolutionX;
	private int resolutionY;
	private int[] colors;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public PackedIntARGBData() {
		this(1024, 768);
	}
	
	public PackedIntARGBData(final BufferedImage bufferedImage) {
		this.resolutionX = bufferedImage.getWidth();
		this.resolutionY = bufferedImage.getHeight();
		this.colors = DataBufferInt.class.cast(BufferedImages.getCompatibleBufferedImage(bufferedImage).getRaster().getDataBuffer()).getData().clone();
	}
	
	public PackedIntARGBData(final PackedIntARGBData colorARGBData) {
		super(colorARGBData);
		
		this.resolutionX = colorARGBData.resolutionX;
		this.resolutionY = colorARGBData.resolutionY;
		this.colors = colorARGBData.colors.clone();
	}
	
	public PackedIntARGBData(final PackedIntARGBData colorARGBData, final boolean isIgnoringChangeHistory) {
		super(colorARGBData, isIgnoringChangeHistory);
		
		this.resolutionX = colorARGBData.resolutionX;
		this.resolutionY = colorARGBData.resolutionY;
		this.colors = colorARGBData.colors.clone();
	}
	
	public PackedIntARGBData(final int resolutionX, final int resolutionY) {
		this(resolutionX, resolutionY, Color4D.WHITE);
	}
	
	public PackedIntARGBData(final int resolutionX, final int resolutionY, final Color4D color) {
		this(resolutionX, resolutionY, color.toIntARGB());
	}
	
	public PackedIntARGBData(final int resolutionX, final int resolutionY, final Color4F color) {
		this(resolutionX, resolutionY, color.toIntARGB());
	}
	
	public PackedIntARGBData(final int resolutionX, final int resolutionY, final int color) {
		this.resolutionX = Ints.requireRange(resolutionX, 1, Integer.MAX_VALUE, "resolutionX");
		this.resolutionY = Ints.requireRange(resolutionY, 1, Integer.MAX_VALUE, "resolutionY");
		this.colors = new int[Ints.requireRangeMultiplyExact(resolutionX, resolutionY, 1, Integer.MAX_VALUE, "resolutionX", "resolutionY")];
		
		Arrays.fill(this.colors, color);
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
	public Color3I getColor3I(final int index) {
		return Color3I.fromIntARGB(getColorARGB(index));
	}
	
	@Override
	public Color3I getColor3I(final int x, final int y) {
		return Color3I.fromIntARGB(getColorARGB(x, y));
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
	public Color4I getColor4I(final int index) {
		return Color4I.fromIntARGB(getColorARGB(index));
	}
	
	@Override
	public Color4I getColor4I(final int x, final int y) {
		return Color4I.fromIntARGB(getColorARGB(x, y));
	}
	
	@Override
	public Data copy(final Shape2I shape) {
		final Point2I max = shape.max();
		final Point2I min = shape.min();
		
		final int resolutionX = max.x - min.x + 1;
		final int resolutionY = max.y - min.y + 1;
		
		final Data data = getDataFactory().create(resolutionX, resolutionY);
		
		final List<Point2I> points = shape.findPoints();
		
		for(final Point2I point : points) {
			data.setColorARGB(getColorARGB(point.x, point.y), point.x - min.x, point.y - min.y);
		}
		
		return data;
	}
	
	@Override
	public Data copy(final boolean isIgnoringChangeHistory) {
		return new PackedIntARGBData(this, isIgnoringChangeHistory);
	}
	
	@Override
	public Data draw(final Consumer<Graphics2D> graphics2DConsumer) {
		Objects.requireNonNull(graphics2DConsumer, "graphics2DConsumer == null");
		
		final BufferedImage bufferedImage = toBufferedImage(false);
		
		final Graphics2D graphics2D = bufferedImage.createGraphics();
		
		graphics2DConsumer.accept(graphics2D);
		
		final int[] colors = DataBufferInt.class.cast(bufferedImage.getRaster().getDataBuffer()).getData();
		
		final boolean hasChangeBegun = hasChangeBegun();
		final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
		
		if(hasChangeBegun || hasChangeBegunNow) {
			changeAdd(new StateChange(this.resolutionX, this.resolutionX, this.resolutionY, this.resolutionY, colors, this.colors));
			
			if(hasChangeBegunNow) {
				changeEnd();
			}
		}
		
		this.colors = colors;
		
		return this;
	}
	
	@Override
	public DataFactory getDataFactory() {
		return new PackedIntARGBDataFactory();
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
				double colorA = Color4D.fromIntARGBToDoubleA(oldColors[index]);
				
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
				
				newColors[index] = Color4D.toIntARGB(colorR, colorG, colorB, colorA);
				
				count++;
			}
		}
		
		final boolean hasChangeBegun = hasChangeBegun();
		final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
		
		if(hasChangeBegun || hasChangeBegunNow) {
			if(count > 0) {
				changeAdd(new StateChange(resolutionX, resolutionX, resolutionY, resolutionY, newColors, oldColors));
			}
			
			if(hasChangeBegunNow) {
				changeEnd();
			}
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
				float colorA = Color4F.fromIntARGBToFloatA(oldColors[index]);
				
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
				
				newColors[index] = Color4F.toIntARGB(colorR, colorG, colorB, colorA);
				
				count++;
			}
		}
		
		final boolean hasChangeBegun = hasChangeBegun();
		final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
		
		if(hasChangeBegun || hasChangeBegunNow) {
			if(count > 0) {
				changeAdd(new StateChange(resolutionX, resolutionX, resolutionY, resolutionY, newColors, oldColors));
			}
			
			if(hasChangeBegunNow) {
				changeEnd();
			}
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
		} else if(!(object instanceof PackedIntARGBData)) {
			return false;
		} else if(this.resolutionX != PackedIntARGBData.class.cast(object).resolutionX) {
			return false;
		} else if(this.resolutionY != PackedIntARGBData.class.cast(object).resolutionY) {
			return false;
		} else if(!Arrays.equals(this.colors, PackedIntARGBData.class.cast(object).colors)) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public boolean rotate(final double angle, final boolean isAngleInRadians) {
		if(Doubles.isZero(angle)) {
			return false;
		}
		
		final double angleDegrees = isAngleInRadians ? Doubles.toDegrees(angle) : angle;
		final double angleRadians = isAngleInRadians ? angle : Doubles.toRadians(angle);
		final double angleCos = Doubles.cos(angleRadians);
		final double angleSin = Doubles.sin(angleRadians);
		
		if(Doubles.equals(angleDegrees, +360.0D) || Doubles.equals(angleDegrees, -360.0D)) {
			return false;
		}
		
		final int oldResolutionX = this.resolutionX;
		final int oldResolutionY = this.resolutionY;
		
		final Rectangle2I rotationBounds = new Rectangle2I(new Point2I(0, 0), new Point2I(oldResolutionX - 1, 0), new Point2I(oldResolutionX - 1, oldResolutionY - 1), new Point2I(0, oldResolutionY - 1));
		
		final Point2I rotationBoundsMin = rotationBounds.min();
		final Point2I rotationBoundsMax = rotationBounds.max();
		final Point2I rotationBoundsMid = Point2I.midpoint(rotationBoundsMin, rotationBoundsMax);
		
		final Rectangle2I rotationBoundsRotated = Rectangle2I.rotate(rotationBounds, angleRadians, true, rotationBoundsMid);
		final Rectangle2I rotationBoundsTranslated = Rectangle2I.translateToOrigin(rotationBoundsRotated);
		
		final Point2I rotationBoundsRotatedMin = rotationBoundsRotated.min();
		final Point2I rotationBoundsRotatedMax = rotationBoundsRotated.max();
		final Point2I rotationBoundsRotatedMid = Point2I.midpoint(rotationBoundsRotatedMin, rotationBoundsRotatedMax);
		
		final Point2I rotationBoundsTranslatedMax = rotationBoundsTranslated.max();
		
		final int newResolutionX = rotationBoundsTranslatedMax.x + 1;
		final int newResolutionY = rotationBoundsTranslatedMax.y + 1;
		
		final int[] newColors = new int[newResolutionX * newResolutionY];
		final int[] oldColors = this.colors;
		
		for(int y = 0; y < newResolutionY; y++) {
			for(int x = 0; x < newResolutionX; x++) {
				final Point2I pointTranslated = new Point2I(x + rotationBoundsRotatedMin.x, y + rotationBoundsRotatedMin.y);
				final Point2I pointRotated = Point2I.rotate(pointTranslated, angleCos, -angleSin, rotationBoundsRotatedMid);
				
				newColors[y * newResolutionX + x] = getColorARGB(pointRotated.x, pointRotated.y);
			}
		}
		
		final boolean hasChangeBegun = hasChangeBegun();
		final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
		
		if(hasChangeBegun || hasChangeBegunNow) {
			changeAdd(new StateChange(newResolutionX, oldResolutionX, newResolutionY, oldResolutionY, newColors, oldColors));
			
			if(hasChangeBegunNow) {
				changeEnd();
			}
		}
		
		this.colors = newColors;
		this.resolutionX = newResolutionX;
		this.resolutionY = newResolutionY;
		
		return true;
	}
	
	@Override
	public boolean rotate(final float angle, final boolean isAngleInRadians) {
		if(Floats.isZero(angle)) {
			return false;
		}
		
		final float angleDegrees = isAngleInRadians ? Floats.toDegrees(angle) : angle;
		final float angleRadians = isAngleInRadians ? angle : Floats.toRadians(angle);
		final float angleCos = Floats.cos(angleRadians);
		final float angleSin = Floats.sin(angleRadians);
		
		if(Floats.equals(angleDegrees, +360.0F) || Floats.equals(angleDegrees, -360.0F)) {
			return false;
		}
		
		final int oldResolutionX = this.resolutionX;
		final int oldResolutionY = this.resolutionY;
		
		final Rectangle2I rotationBounds = new Rectangle2I(new Point2I(0, 0), new Point2I(oldResolutionX - 1, 0), new Point2I(oldResolutionX - 1, oldResolutionY - 1), new Point2I(0, oldResolutionY - 1));
		
		final Point2I rotationBoundsMin = rotationBounds.min();
		final Point2I rotationBoundsMax = rotationBounds.max();
		final Point2I rotationBoundsMid = Point2I.midpoint(rotationBoundsMin, rotationBoundsMax);
		
		final Rectangle2I rotationBoundsRotated = Rectangle2I.rotate(rotationBounds, angleRadians, true, rotationBoundsMid);
		final Rectangle2I rotationBoundsTranslated = Rectangle2I.translateToOrigin(rotationBoundsRotated);
		
		final Point2I rotationBoundsRotatedMin = rotationBoundsRotated.min();
		final Point2I rotationBoundsRotatedMax = rotationBoundsRotated.max();
		final Point2I rotationBoundsRotatedMid = Point2I.midpoint(rotationBoundsRotatedMin, rotationBoundsRotatedMax);
		
		final Point2I rotationBoundsTranslatedMax = rotationBoundsTranslated.max();
		
		final int newResolutionX = rotationBoundsTranslatedMax.x + 1;
		final int newResolutionY = rotationBoundsTranslatedMax.y + 1;
		
		final int[] newColors = new int[newResolutionX * newResolutionY];
		final int[] oldColors = this.colors;
		
		for(int y = 0; y < newResolutionY; y++) {
			for(int x = 0; x < newResolutionX; x++) {
				final Point2I pointTranslated = new Point2I(x + rotationBoundsRotatedMin.x, y + rotationBoundsRotatedMin.y);
				final Point2I pointRotated = Point2I.rotate(pointTranslated, angleCos, -angleSin, rotationBoundsRotatedMid);
				
				newColors[y * newResolutionX + x] = getColorARGB(pointRotated.x, pointRotated.y);
			}
		}
		
		final boolean hasChangeBegun = hasChangeBegun();
		final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
		
		if(hasChangeBegun || hasChangeBegunNow) {
			changeAdd(new StateChange(newResolutionX, oldResolutionX, newResolutionY, oldResolutionY, newColors, oldColors));
			
			if(hasChangeBegunNow) {
				changeEnd();
			}
		}
		
		this.colors = newColors;
		this.resolutionX = newResolutionX;
		this.resolutionY = newResolutionY;
		
		return true;
	}
	
	@Override
	public boolean scale(final int resolutionX, final int resolutionY) {
		if(resolutionX < 1 || resolutionY < 1 || !Ints.canMultiplyExact(resolutionX, resolutionY)) {
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
		
		final boolean hasChangeBegun = hasChangeBegun();
		final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
		
		if(hasChangeBegun || hasChangeBegunNow) {
			changeAdd(new StateChange(newResolutionX, oldResolutionX, newResolutionY, oldResolutionY, newColors, oldColors));
			
			if(hasChangeBegunNow) {
				changeEnd();
			}
		}
		
		this.colors = newColors;
		this.resolutionX = newResolutionX;
		this.resolutionY = newResolutionY;
		
		return true;
	}
	
	@Override
	public boolean setColor3D(final Color3D color, final int index) {
		return setColorARGB(Objects.requireNonNull(color, "color == null").toIntARGB(), index);
	}
	
	@Override
	public boolean setColor3D(final Color3D color, final int x, final int y) {
		return setColorARGB(Objects.requireNonNull(color, "color == null").toIntARGB(), x, y);
	}
	
	@Override
	public boolean setColor3F(final Color3F color, final int index) {
		return setColorARGB(Objects.requireNonNull(color, "color == null").toIntARGB(), index);
	}
	
	@Override
	public boolean setColor3F(final Color3F color, final int x, final int y) {
		return setColorARGB(Objects.requireNonNull(color, "color == null").toIntARGB(), x, y);
	}
	
	@Override
	public boolean setColor3I(final Color3I color, final int index) {
		return setColorARGB(Objects.requireNonNull(color, "color == null").toIntARGB(), index);
	}
	
	@Override
	public boolean setColor3I(final Color3I color, final int x, final int y) {
		return setColorARGB(Objects.requireNonNull(color, "color == null").toIntARGB(), x, y);
	}
	
	@Override
	public boolean setColor4D(final Color4D color, final int index) {
		return setColorARGB(Objects.requireNonNull(color, "color == null").toIntARGB(), index);
	}
	
	@Override
	public boolean setColor4D(final Color4D color, final int x, final int y) {
		return setColorARGB(Objects.requireNonNull(color, "color == null").toIntARGB(), x, y);
	}
	
	@Override
	public boolean setColor4F(final Color4F color, final int index) {
		return setColorARGB(Objects.requireNonNull(color, "color == null").toIntARGB(), index);
	}
	
	@Override
	public boolean setColor4F(final Color4F color, final int x, final int y) {
		return setColorARGB(Objects.requireNonNull(color, "color == null").toIntARGB(), x, y);
	}
	
	@Override
	public boolean setColor4I(final Color4I color, final int index) {
		return setColorARGB(Objects.requireNonNull(color, "color == null").toIntARGB(), index);
	}
	
	@Override
	public boolean setColor4I(final Color4I color, final int x, final int y) {
		return setColorARGB(Objects.requireNonNull(color, "color == null").toIntARGB(), x, y);
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
		
		if(data instanceof PackedIntARGBData) {
			final PackedIntARGBData colorARGBData = PackedIntARGBData.class.cast(data);
			
			final int[] newColors = colorARGBData.colors.clone();
			final int[] oldColors = this.colors;
			
			final int newResolutionX = colorARGBData.resolutionX;
			final int newResolutionY = colorARGBData.resolutionY;
			
			final int oldResolutionX = this.resolutionX;
			final int oldResolutionY = this.resolutionY;
			
			final boolean hasChangeBegun = hasChangeBegun();
			final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
			
			if(hasChangeBegun || hasChangeBegunNow) {
				changeAdd(new StateChange(newResolutionX, oldResolutionX, newResolutionY, oldResolutionY, newColors, oldColors));
				
				if(hasChangeBegunNow) {
					changeEnd();
				}
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
		if(resolutionX < 1 || resolutionY < 1 || !Ints.canMultiplyExact(resolutionX, resolutionY)) {
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
					newColors[newIndex] = Color4I.WHITE_A_R_G_B;
				}
			}
		}
		
		final boolean hasChangeBegun = hasChangeBegun();
		final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
		
		if(hasChangeBegun || hasChangeBegunNow) {
			changeAdd(new StateChange(newResolutionX, oldResolutionX, newResolutionY, oldResolutionY, newColors, oldColors));
			
			if(hasChangeBegunNow) {
				changeEnd();
			}
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
		return index >= 0 && index < this.colors.length ? this.colors[index] : Color4I.TRANSPARENT_A_R_G_B;
	}
	
	@Override
	public int getColorARGB(final int x, final int y) {
		return x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY ? this.colors[y * this.resolutionX + x] : Color4I.TRANSPARENT_A_R_G_B;
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
		Ints.requireRange(index, 0, this.colors.length - 1, "index");
		
		this.colors[index] = color;
	}
	
	void updateState(final int resolutionX, final int resolutionY, final int[] colors) {
		Ints.requireRange(resolutionX, 1, Integer.MAX_VALUE, "resolutionX");
		Ints.requireRange(resolutionY, 1, Integer.MAX_VALUE, "resolutionY");
		
		Objects.requireNonNull(colors, "colors");
		
		this.resolutionX = resolutionX;
		this.resolutionY = resolutionY;
		this.colors = colors.clone();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static final class PixelChange implements Change {
		private final int colorRedo;
		private final int colorUndo;
		private final int index;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public PixelChange(final int colorRedo, final int colorUndo, final int index) {
			this.colorRedo = colorRedo;
			this.colorUndo = colorUndo;
			this.index = Ints.requireRange(index, 0, Integer.MAX_VALUE, "index");
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
		
		public int getColorRedo() {
			return this.colorRedo;
		}
		
		public int getColorUndo() {
			return this.colorUndo;
		}
		
		public int getIndex() {
			return this.index;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(Integer.valueOf(this.colorRedo), Integer.valueOf(this.colorUndo), Integer.valueOf(this.index));
		}
		
		@Override
		public void redo(final Data data) {
			Objects.requireNonNull(data, "data == null");
			
			if(data instanceof PackedIntARGBData) {
				final
				PackedIntARGBData colorARGBData = PackedIntARGBData.class.cast(data);
				colorARGBData.updatePixel(this.colorRedo, this.index);
			}
		}
		
		@Override
		public void undo(final Data data) {
			Objects.requireNonNull(data, "data == null");
			
			if(data instanceof PackedIntARGBData) {
				final
				PackedIntARGBData colorARGBData = PackedIntARGBData.class.cast(data);
				colorARGBData.updatePixel(this.colorUndo, this.index);
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static final class StateChange implements Change {
		private final int resolutionXRedo;
		private final int resolutionXUndo;
		private final int resolutionYRedo;
		private final int resolutionYUndo;
		private final int[] colorsRedo;
		private final int[] colorsUndo;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public StateChange(final int resolutionXRedo, final int resolutionXUndo, final int resolutionYRedo, final int resolutionYUndo, final int[] colorsRedo, final int[] colorsUndo) {
			this.resolutionXRedo = Ints.requireRange(resolutionXRedo, 1, Integer.MAX_VALUE, "resolutionXRedo");
			this.resolutionXUndo = Ints.requireRange(resolutionXUndo, 1, Integer.MAX_VALUE, "resolutionXUndo");
			this.resolutionYRedo = Ints.requireRange(resolutionYRedo, 1, Integer.MAX_VALUE, "resolutionYRedo");
			this.resolutionYUndo = Ints.requireRange(resolutionYUndo, 1, Integer.MAX_VALUE, "resolutionYUndo");
			this.colorsRedo = Objects.requireNonNull(colorsRedo, "colorsRedo == null").clone();
			this.colorsUndo = Objects.requireNonNull(colorsUndo, "colorsUndo == null").clone();
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
		
		public int getResolutionXRedo() {
			return this.resolutionXRedo;
		}
		
		public int getResolutionXUndo() {
			return this.resolutionXUndo;
		}
		
		public int getResolutionYRedo() {
			return this.resolutionYRedo;
		}
		
		public int getResolutionYUndo() {
			return this.resolutionYUndo;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(Integer.valueOf(this.resolutionXRedo), Integer.valueOf(this.resolutionXUndo), Integer.valueOf(this.resolutionYRedo), Integer.valueOf(this.resolutionYUndo), Integer.valueOf(Arrays.hashCode(this.colorsRedo)), Integer.valueOf(Arrays.hashCode(this.colorsUndo)));
		}
		
		public int[] getColorsRedo() {
			return this.colorsRedo.clone();
		}
		
		public int[] getColorsUndo() {
			return this.colorsUndo.clone();
		}
		
		@Override
		public void redo(final Data data) {
			Objects.requireNonNull(data, "data == null");
			
			if(data instanceof PackedIntARGBData) {
				final
				PackedIntARGBData colorARGBData = PackedIntARGBData.class.cast(data);
				colorARGBData.updateState(this.resolutionXRedo, this.resolutionYRedo, this.colorsRedo);
			}
		}
		
		@Override
		public void undo(final Data data) {
			Objects.requireNonNull(data, "data == null");
			
			if(data instanceof PackedIntARGBData) {
				final
				PackedIntARGBData colorARGBData = PackedIntARGBData.class.cast(data);
				colorARGBData.updateState(this.resolutionXUndo, this.resolutionYUndo, this.colorsUndo);
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private double[] doUnpackColorsAsDoubleArrayRGB() {
		final double[] colors = new double[this.colors.length * 3];
		
		for(int i = 0, j = 0; i < this.colors.length; i++, j = i * 3) {
			colors[j + 0] = Color4D.fromIntARGBToDoubleR(this.colors[i]);
			colors[j + 1] = Color4D.fromIntARGBToDoubleG(this.colors[i]);
			colors[j + 2] = Color4D.fromIntARGBToDoubleB(this.colors[i]);
		}
		
		return colors;
	}
	
	private float[] doUnpackColorsAsFloatArrayRGB() {
		final float[] colors = new float[this.colors.length * 3];
		
		for(int i = 0, j = 0; i < this.colors.length; i++, j = i * 3) {
			colors[j + 0] = Color4F.fromIntARGBToFloatR(this.colors[i]);
			colors[j + 1] = Color4F.fromIntARGBToFloatG(this.colors[i]);
			colors[j + 2] = Color4F.fromIntARGBToFloatB(this.colors[i]);
		}
		
		return colors;
	}
}