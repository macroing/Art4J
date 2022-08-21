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
import org.macroing.art4j.geometry.Point2I;
import org.macroing.art4j.geometry.Shape2I;
import org.macroing.art4j.geometry.shape.Rectangle2I;
import org.macroing.art4j.kernel.ConvolutionKernelND;
import org.macroing.art4j.kernel.ConvolutionKernelNF;
import org.macroing.java.awt.image.BufferedImages;
import org.macroing.java.lang.Doubles;
import org.macroing.java.lang.Floats;
import org.macroing.java.lang.Ints;

final class Color4DData extends Data {
	private Color4D[] colors;
	private int resolutionX;
	private int resolutionY;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Color4DData() {
		this(1024, 768);
	}
	
	public Color4DData(final BufferedImage bufferedImage) {
		this.colors = Arrays.stream(DataBufferInt.class.cast(BufferedImages.getCompatibleBufferedImage(bufferedImage).getRaster().getDataBuffer()).getData()).mapToObj(colorARGB -> Color4D.fromIntARGB(colorARGB)).toArray(Color4D[]::new);
		this.resolutionX = bufferedImage.getWidth();
		this.resolutionY = bufferedImage.getHeight();
	}
	
	public Color4DData(final Color4DData color4DData) {
		super(color4DData);
		
		this.colors = color4DData.colors.clone();
		this.resolutionX = color4DData.resolutionX;
		this.resolutionY = color4DData.resolutionY;
	}
	
	public Color4DData(final Color4DData color4DData, final boolean isIgnoringChangeHistory) {
		super(color4DData, isIgnoringChangeHistory);
		
		this.colors = color4DData.colors.clone();
		this.resolutionX = color4DData.resolutionX;
		this.resolutionY = color4DData.resolutionY;
	}
	
	public Color4DData(final int resolutionX, final int resolutionY) {
		this(resolutionX, resolutionY, Color4D.WHITE);
	}
	
	public Color4DData(final int resolutionX, final int resolutionY, final Color4D color) {
		this.resolutionX = Ints.requireRange(resolutionX, 1, Integer.MAX_VALUE, "resolutionX");
		this.resolutionY = Ints.requireRange(resolutionY, 1, Integer.MAX_VALUE, "resolutionY");
		this.colors = new Color4D[Ints.requireRangeMultiplyExact(resolutionX, resolutionY, 1, Integer.MAX_VALUE, "resolutionX", "resolutionY")];
		
		Arrays.fill(this.colors, Objects.requireNonNull(color, "color == null"));
	}
	
	public Color4DData(final int resolutionX, final int resolutionY, final Color4F color) {
		this(resolutionX, resolutionY, new Color4D(color));
	}
	
	public Color4DData(final int resolutionX, final int resolutionY, final int color) {
		this(resolutionX, resolutionY, Color4D.fromIntARGB(color));
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
	public Color3I getColor3I(final int index) {
		return index >= 0 && index < this.colors.length ? new Color3I(this.colors[index]) : Color3I.BLACK;
	}
	
	@Override
	public Color3I getColor3I(final int x, final int y) {
		return x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY ? new Color3I(this.colors[y * this.resolutionX + x]) : Color3I.BLACK;
	}
	
	@Override
	public Color4D getColor4D(final int index) {
		return index >= 0 && index < this.colors.length ? this.colors[index] : Color4D.TRANSPARENT;
	}
	
	@Override
	public Color4D getColor4D(final int x, final int y) {
		return x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY ? this.colors[y * this.resolutionX + x] : Color4D.TRANSPARENT;
	}
	
	@Override
	public Color4F getColor4F(final int index) {
		return index >= 0 && index < this.colors.length ? new Color4F(this.colors[index]) : Color4F.TRANSPARENT;
	}
	
	@Override
	public Color4F getColor4F(final int x, final int y) {
		return x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY ? new Color4F(this.colors[y * this.resolutionX + x]) : Color4F.TRANSPARENT;
	}
	
	@Override
	public Color4I getColor4I(final int index) {
		return index >= 0 && index < this.colors.length ? new Color4I(this.colors[index]) : Color4I.TRANSPARENT;
	}
	
	@Override
	public Color4I getColor4I(final int x, final int y) {
		return x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY ? new Color4I(this.colors[y * this.resolutionX + x]) : Color4I.TRANSPARENT;
	}
	
	@Override
	public Data copy(final Shape2I shape) {
		final Point2I max = shape.max();
		final Point2I min = shape.min();
		
		final int resolutionX = max.x - min.x + 1;
		final int resolutionY = max.y - min.y + 1;
		
		final Data data = getDataFactory().create(resolutionX, resolutionY, Color4D.TRANSPARENT);
		
		final List<Point2I> points = shape.findPoints();
		
		for(final Point2I point : points) {
			data.setColor4D(getColor4D(point.x, point.y), point.x - min.x, point.y - min.y);
		}
		
		return data;
	}
	
	@Override
	public Data copy(final boolean isIgnoringChangeHistory) {
		return new Color4DData(this, isIgnoringChangeHistory);
	}
	
	@Override
	public Data draw(final Consumer<Graphics2D> graphics2DConsumer) {
		Objects.requireNonNull(graphics2DConsumer, "graphics2DConsumer == null");
		
		final BufferedImage bufferedImage = toBufferedImage(false);
		
		final Graphics2D graphics2D = bufferedImage.createGraphics();
		
		graphics2DConsumer.accept(graphics2D);
		
		final Color4D[] colors = Arrays.stream(DataBufferInt.class.cast(bufferedImage.getRaster().getDataBuffer()).getData()).mapToObj(colorARGB -> Color4D.fromIntARGB(colorARGB)).toArray(Color4D[]::new);
		
		final boolean hasChangeBegun = hasChangeBegun();
		final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
		
		if(hasChangeBegun || hasChangeBegunNow) {
			changeAdd(new StateChange(colors, this.colors, this.resolutionX, this.resolutionX, this.resolutionY, this.resolutionY));
			
			if(hasChangeBegunNow) {
				changeEnd();
			}
		}
		
		this.colors = colors;
		
		return this;
	}
	
	@Override
	public DataFactory getDataFactory() {
		return new Color4DDataFactory();
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
		
		final Color4D[] oldColors = this.colors;
		final Color4D[] newColors = this.colors.clone();
		
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
				double colorA = oldColors[index].a;
				
				for(int kernelY = 0; kernelY < kernelResolution; kernelY++) {
					final int imageY = yOffset + kernelY;
					final int imageRow = imageY * resolutionX;
					
					final int kernelRow = kernelY * kernelResolution;
					
					for(int kernelX = 0; kernelX < kernelResolution; kernelX++) {
						final int imageX = xOffset + kernelX;
						
						if(imageX >= 0 && imageX < resolutionX && imageY >= 0 && imageY < resolutionY) {
							final Color4D color = oldColors[imageRow + imageX];
							
							final double element = elements[kernelRow + kernelX];
							
							colorR += color.r * element;
							colorG += color.g * element;
							colorB += color.b * element;
						}
					}
				}
				
				colorR = colorR * factor + bias;
				colorG = colorG * factor + bias;
				colorB = colorB * factor + bias;
				
				newColors[index] = new Color4D(colorR, colorG, colorB, colorA);
				
				count++;
			}
		}
		
		final boolean hasChangeBegun = hasChangeBegun();
		final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
		
		if(hasChangeBegun || hasChangeBegunNow) {
			if(count > 0) {
				changeAdd(new StateChange(newColors, oldColors, resolutionX, resolutionX, resolutionY, resolutionY));
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
		
		final int resolution = getResolution();
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		final Color4D[] oldColors = this.colors;
		final Color4D[] newColors = this.colors.clone();
		
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
				double colorA = oldColors[index].a;
				
				for(int kernelY = 0; kernelY < kernelResolution; kernelY++) {
					final int imageY = yOffset + kernelY;
					final int imageRow = imageY * resolutionX;
					
					final int kernelRow = kernelY * kernelResolution;
					
					for(int kernelX = 0; kernelX < kernelResolution; kernelX++) {
						final int imageX = xOffset + kernelX;
						
						if(imageX >= 0 && imageX < resolutionX && imageY >= 0 && imageY < resolutionY) {
							final Color4D color = oldColors[imageRow + imageX];
							
							final double element = elements[kernelRow + kernelX];
							
							colorR += color.r * element;
							colorG += color.g * element;
							colorB += color.b * element;
						}
					}
				}
				
				colorR = colorR * factor + bias;
				colorG = colorG * factor + bias;
				colorB = colorB * factor + bias;
				
				newColors[index] = new Color4D(colorR, colorG, colorB, colorA);
				
				count++;
			}
		}
		
		final boolean hasChangeBegun = hasChangeBegun();
		final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
		
		if(hasChangeBegun || hasChangeBegunNow) {
			if(count > 0) {
				changeAdd(new StateChange(newColors, oldColors, resolutionX, resolutionX, resolutionY, resolutionY));
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
		} else if(!(object instanceof Color4DData)) {
			return false;
		} else if(this.resolutionX != Color4DData.class.cast(object).resolutionX) {
			return false;
		} else if(this.resolutionY != Color4DData.class.cast(object).resolutionY) {
			return false;
		} else if(!Arrays.equals(this.colors, Color4DData.class.cast(object).colors)) {
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
		
		final Color4D[] newColors = new Color4D[newResolutionX * newResolutionY];
		final Color4D[] oldColors = this.colors;
		
		for(int y = 0; y < newResolutionY; y++) {
			for(int x = 0; x < newResolutionX; x++) {
				final Point2I pointTranslated = new Point2I(x + rotationBoundsRotatedMin.x, y + rotationBoundsRotatedMin.y);
				final Point2I pointRotated = Point2I.rotate(pointTranslated, angleCos, -angleSin, rotationBoundsRotatedMid);
				
				newColors[y * newResolutionX + x] = getColor4D(pointRotated.x, pointRotated.y);
			}
		}
		
		final boolean hasChangeBegun = hasChangeBegun();
		final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
		
		if(hasChangeBegun || hasChangeBegunNow) {
			changeAdd(new StateChange(newColors, oldColors, newResolutionX, oldResolutionX, newResolutionY, oldResolutionY));
			
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
		
		final Color4D[] newColors = new Color4D[newResolutionX * newResolutionY];
		final Color4D[] oldColors = this.colors;
		
		for(int y = 0; y < newResolutionY; y++) {
			for(int x = 0; x < newResolutionX; x++) {
				final Point2I pointTranslated = new Point2I(x + rotationBoundsRotatedMin.x, y + rotationBoundsRotatedMin.y);
				final Point2I pointRotated = Point2I.rotate(pointTranslated, angleCos, -angleSin, rotationBoundsRotatedMid);
				
				newColors[y * newResolutionX + x] = getColor4D(pointRotated.x, pointRotated.y);
			}
		}
		
		final boolean hasChangeBegun = hasChangeBegun();
		final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
		
		if(hasChangeBegun || hasChangeBegunNow) {
			changeAdd(new StateChange(newColors, oldColors, newResolutionX, oldResolutionX, newResolutionY, oldResolutionY));
			
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
		
		final Color4D[] newColors = new Color4D[newResolutionX * newResolutionY];
		final Color4D[] oldColors = this.colors;
		
		final double scaleX = (double)(oldResolutionX) / (double)(newResolutionX);
		final double scaleY = (double)(oldResolutionY) / (double)(newResolutionY);
		
		for(int y = 0; y < newResolutionY; y++) {
			for(int x = 0; x < newResolutionX; x++) {
				newColors[y * newResolutionX + x] = getColor4D(x * scaleX, y * scaleY);
			}
		}
		
		final boolean hasChangeBegun = hasChangeBegun();
		final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
		
		if(hasChangeBegun || hasChangeBegunNow) {
			changeAdd(new StateChange(newColors, oldColors, newResolutionX, oldResolutionX, newResolutionY, oldResolutionY));
			
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
	public boolean setColor3I(final Color3I color, final int index) {
		return setColor4D(new Color4D(Objects.requireNonNull(color, "color == null")), index);
	}
	
	@Override
	public boolean setColor3I(final Color3I color, final int x, final int y) {
		return setColor4D(new Color4D(Objects.requireNonNull(color, "color == null")), x, y);
	}
	
	@Override
	public boolean setColor4D(final Color4D color, final int index) {
		Objects.requireNonNull(color, "color == null");
		
		if(index >= 0 && index < this.colors.length) {
			final Color4D newColor = color;
			final Color4D oldColor = this.colors[index];
			
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
	public boolean setColor4D(final Color4D color, final int x, final int y) {
		Objects.requireNonNull(color, "color == null");
		
		if(x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY) {
			final int index = y * this.resolutionX + x;
			
			final Color4D newColor = color;
			final Color4D oldColor = this.colors[index];
			
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
	public boolean setColor4F(final Color4F color, final int index) {
		return setColor4D(new Color4D(Objects.requireNonNull(color, "color == null")), index);
	}
	
	@Override
	public boolean setColor4F(final Color4F color, final int x, final int y) {
		return setColor4D(new Color4D(Objects.requireNonNull(color, "color == null")), x, y);
	}
	
	@Override
	public boolean setColor4I(final Color4I color, final int index) {
		return setColor4D(new Color4D(Objects.requireNonNull(color, "color == null")), index);
	}
	
	@Override
	public boolean setColor4I(final Color4I color, final int x, final int y) {
		return setColor4D(new Color4D(Objects.requireNonNull(color, "color == null")), x, y);
	}
	
	@Override
	public boolean setColorARGB(final int colorARGB, final int index) {
		return setColor4D(Color4D.fromIntARGB(colorARGB), index);
	}
	
	@Override
	public boolean setColorARGB(final int colorARGB, final int x, final int y) {
		return setColor4D(Color4D.fromIntARGB(colorARGB), x, y);
	}
	
	@Override
	public boolean setContent(final Data data) {
		Objects.requireNonNull(data, "data == null");
		
		if(data instanceof Color4DData) {
			final Color4DData color4DData = Color4DData.class.cast(data);
			
			final Color4D[] newColors = color4DData.colors.clone();
			final Color4D[] oldColors = this.colors;
			
			final int newResolutionX = color4DData.resolutionX;
			final int newResolutionY = color4DData.resolutionY;
			
			final int oldResolutionX = this.resolutionX;
			final int oldResolutionY = this.resolutionY;
			
			final boolean hasChangeBegun = hasChangeBegun();
			final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
			
			if(hasChangeBegun || hasChangeBegunNow) {
				changeAdd(new StateChange(newColors, oldColors, newResolutionX, oldResolutionX, newResolutionY, oldResolutionY));
				
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
		
		final boolean hasChangeBegun = hasChangeBegun();
		final boolean hasChangeBegunNow = !hasChangeBegun && changeBegin();
		
		if(hasChangeBegun || hasChangeBegunNow) {
			changeAdd(new StateChange(newColors, oldColors, newResolutionX, oldResolutionX, newResolutionY, oldResolutionY));
			
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
		
		final Color4D colorA = this.colors[indexA];
		final Color4D colorB = this.colors[indexB];
		
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
			final Color4D oldColor = this.colors[i];
			final Color4D newColor = Color4D.getCached(oldColor);
			
			if(oldColor != newColor) {
				cached++;
				
				this.colors[i] = newColor;
			}
		}
		
		return cached;
	}
	
	@Override
	public int getColorARGB(final int index) {
		return getColor4D(index).toIntARGB();
	}
	
	@Override
	public int getColorARGB(final int x, final int y) {
		return getColor4D(x, y).toIntARGB();
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
	
	void updatePixel(final Color4D color, final int index) {
		Objects.requireNonNull(color, "color == null");
		
		Ints.requireRange(index, 0, this.colors.length - 1, "index");
		
		this.colors[index] = color;
	}
	
	void updateState(final Color4D[] colors, final int resolutionX, final int resolutionY) {
		org.macroing.java.util.Arrays.requireNonNull(colors, "colors");
		
		Ints.requireRange(resolutionX, 1, Integer.MAX_VALUE, "resolutionX");
		Ints.requireRange(resolutionY, 1, Integer.MAX_VALUE, "resolutionY");
		
		this.colors = colors.clone();
		this.resolutionX = resolutionX;
		this.resolutionY = resolutionY;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static final class PixelChange implements Change {
		private final Color4D colorRedo;
		private final Color4D colorUndo;
		private final int index;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public PixelChange(final Color4D colorRedo, final Color4D colorUndo, final int index) {
			this.colorRedo = Objects.requireNonNull(colorRedo, "colorRedo == null");
			this.colorUndo = Objects.requireNonNull(colorUndo, "colorUndo == null");
			this.index = Ints.requireRange(index, 0, Integer.MAX_VALUE, "index");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Color4D getColorRedo() {
			return this.colorRedo;
		}
		
		public Color4D getColorUndo() {
			return this.colorUndo;
		}
		
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
		
		public int getIndex() {
			return this.index;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(this.colorRedo, this.colorUndo, Integer.valueOf(this.index));
		}
		
		@Override
		public void redo(final Data data) {
			Objects.requireNonNull(data, "data == null");
			
			if(data instanceof Color4DData) {
				final
				Color4DData color4DData = Color4DData.class.cast(data);
				color4DData.updatePixel(this.colorRedo, this.index);
			}
		}
		
		@Override
		public void undo(final Data data) {
			Objects.requireNonNull(data, "data == null");
			
			if(data instanceof Color4DData) {
				final
				Color4DData color4DData = Color4DData.class.cast(data);
				color4DData.updatePixel(this.colorUndo, this.index);
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static final class StateChange implements Change {
		private final Color4D[] colorsRedo;
		private final Color4D[] colorsUndo;
		private final int resolutionXRedo;
		private final int resolutionXUndo;
		private final int resolutionYRedo;
		private final int resolutionYUndo;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public StateChange(final Color4D[] colorsRedo, final Color4D[] colorsUndo, final int resolutionXRedo, final int resolutionXUndo, final int resolutionYRedo, final int resolutionYUndo) {
			this.colorsRedo = org.macroing.java.util.Arrays.requireNonNull(colorsRedo, "colorsRedo").clone();
			this.colorsUndo = org.macroing.java.util.Arrays.requireNonNull(colorsUndo, "colorsUndo").clone();
			this.resolutionXRedo = Ints.requireRange(resolutionXRedo, 1, Integer.MAX_VALUE, "resolutionXRedo");
			this.resolutionXUndo = Ints.requireRange(resolutionXUndo, 1, Integer.MAX_VALUE, "resolutionXUndo");
			this.resolutionYRedo = Ints.requireRange(resolutionYRedo, 1, Integer.MAX_VALUE, "resolutionYRedo");
			this.resolutionYUndo = Ints.requireRange(resolutionYUndo, 1, Integer.MAX_VALUE, "resolutionYUndo");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Color4D[] getColorsRedo() {
			return this.colorsRedo.clone();
		}
		
		public Color4D[] getColorsUndo() {
			return this.colorsUndo.clone();
		}
		
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
			return Objects.hash(Integer.valueOf(Arrays.hashCode(this.colorsRedo)), Integer.valueOf(Arrays.hashCode(this.colorsUndo)), Integer.valueOf(this.resolutionXRedo), Integer.valueOf(this.resolutionXUndo), Integer.valueOf(this.resolutionYRedo), Integer.valueOf(this.resolutionYUndo));
		}
		
		@Override
		public void redo(final Data data) {
			Objects.requireNonNull(data, "data == null");
			
			if(data instanceof Color4DData) {
				final
				Color4DData color4DData = Color4DData.class.cast(data);
				color4DData.updateState(this.colorsRedo, this.resolutionXRedo, this.resolutionYRedo);
			}
		}
		
		@Override
		public void undo(final Data data) {
			Objects.requireNonNull(data, "data == null");
			
			if(data instanceof Color4DData) {
				final
				Color4DData color4DData = Color4DData.class.cast(data);
				color4DData.updateState(this.colorsUndo, this.resolutionXUndo, this.resolutionYUndo);
			}
		}
	}
}