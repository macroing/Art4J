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
package org.macroing.art4j.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.macroing.art4j.color.Color3D;
import org.macroing.art4j.data.DataFactory;
import org.macroing.art4j.image.Image;
import org.macroing.art4j.pixel.Color4DPixelOperator;
import org.macroing.java.lang.Doubles;

public final class HDR {
	private static final double[] EXPONENT;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static {
		EXPONENT = new double[256];
		
		EXPONENT[0] = 0.0D;
		
		for(int i = 1; i < 256; i++) {
			double d = 1.0D;
			
			int e = i - (128 + 8);
			
			if(e > 0) {
				for(int j = 0; j < e; j++) {
					d *= 2.0D;
				}
			} else {
				for(int j = 0; j < -e; j++) {
					d *= 0.5D;
				}
			}
			
			EXPONENT[i] = d;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private HDR() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
		final Color3D a = new Color3D(1.0D, 1.0D, 1.0D);
		final Color3D b = doFromRGBE(doToRGBE(a));
		final Color3D c = doFromRGBE(doToRGBE(b));
		
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		
		final
		Image image = doLoadHDR(new File("./generated/HDR_029_Sky_Cloudy_Ref.hdr"));
		image.fillD(Color4DPixelOperator.toneMap(image.relativeLuminanceMaxAsDouble()));
		image.fillD(Color4DPixelOperator.redoGammaCorrection());
		image.save(new File("./generated/HDR_029_Sky_Cloudy_Ref.png"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Color3D doFromRGBE(final int colorRGBE) {
		final double d = EXPONENT[colorRGBE & 0xFF];
		
		final double r = d * ((colorRGBE >>> 24) + 0.5F);
		final double g = d * (((colorRGBE >> 16) & 0xFF) + 0.5F);
		final double b = d * (((colorRGBE >>  8) & 0xFF) + 0.5F);
		
		return new Color3D(r, g, b);
	}
	
	private static Image doLoadHDR(final File file) {
		try(final FileInputStream fileInputStream = new FileInputStream(file)) {
			boolean parseResolutionX = false;
			boolean parseResolutionY = false;
			
			int resolutionX = 0;
			int resolutionY = 0;
			
			int last = 0;
			
			while(resolutionX == 0 || resolutionY == 0 || last != '\n') {
				int n = fileInputStream.read();
				
				switch(n) {
					case 'X':
						parseResolutionX = last == '+';
						parseResolutionY = false;
						
						break;
					case 'Y':
						parseResolutionX = false;
						parseResolutionY = last == '-';
						
						break;
					case ' ':
						parseResolutionX &= resolutionX == 0;
						parseResolutionY &= resolutionY == 0;
						
						break;
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
						if(parseResolutionY) {
							resolutionY = 10 * resolutionY + (n - '0');
						} else if(parseResolutionX) {
							resolutionX = 10 * resolutionX + (n - '0');
						}
						
						break;
					default:
						parseResolutionX = false;
						parseResolutionY = false;
						
						break;
				}
				
				last = n;
			}
			
			final int[] pixels = new int[resolutionX * resolutionY];
			
			if(resolutionX < 8 || resolutionX > 0x7FFF) {
				doReadFlatRGBE(fileInputStream, 0, resolutionX * resolutionY, pixels);
				
				final Image image = new Image(resolutionX, resolutionY, DataFactory.forColor4D());
				
				for(int i = 0; i < pixels.length; i++) {
					image.setColor3D(doFromRGBE(pixels[i]), i);
				}
				
				return image;
			}
			
			int rasterPos = 0;
			int numScanlines = resolutionY;
			
			int[] scanlineBuffer = new int[4 * resolutionX];
			
			while(numScanlines > 0) {
				int r = fileInputStream.read();
				int g = fileInputStream.read();
				int b = fileInputStream.read();
				int e = fileInputStream.read();
				
				if(r != 2 || g != 2 || (b & 0x80) != 0) {
					pixels[rasterPos] = (r << 24) | (g << 16) | (b << 8) | e;
					
					doReadFlatRGBE(fileInputStream, rasterPos + 1, resolutionX * numScanlines - 1, pixels);
					
					final Image image = new Image(resolutionX, resolutionY, DataFactory.forColor4D());
					
					for(int i = 0; i < pixels.length; i++) {
						image.setColor3D(doFromRGBE(pixels[i]), i);
					}
					
					return image;
				}
				
				if(((b << 8) | e) != resolutionX) {
					throw new IOException("Invalid scanline width");
				}
				
				int p = 0;
				
				for(int i = 0; i < 4; i++) {
					if(p % resolutionX != 0) {
						throw new IOException("Unaligned access to scanline data");
					}
					
					final int end = (i + 1) * resolutionX;
					
					while(p < end) {
						final int b0 = fileInputStream.read();
						final int b1 = fileInputStream.read();
						
						if(b0 > 128) {
							int count = b0 - 128;
							
							if(count == 0 || count > end - p) {
								throw new IOException("Bad scanline data - invalid RLE run");
							}
							
							while(count-- > 0) {
								scanlineBuffer[p++] = b1;
							}
						} else {
							int count = b0;
							
							if(count == 0 || count > end - p) {
								throw new IOException("Bad scanline data - invalid count");
							}
							
							scanlineBuffer[p++] = b1;
							
							if(--count > 0) {
								for(int x = 0; x < count; x++) {
									scanlineBuffer[p + x] = fileInputStream.read();
								}
								
								p += count;
							}
						}
					}
				}
				
				for(int i = 0; i < resolutionX; i++) {
					r = scanlineBuffer[i + 0 * resolutionX];
					g = scanlineBuffer[i + 1 * resolutionX];
					b = scanlineBuffer[i + 2 * resolutionX];
					e = scanlineBuffer[i + 3 * resolutionX];
					
					pixels[rasterPos] = (r << 24) | (g << 16) | (b << 8) | e;
					
					rasterPos++;
				}
				
				numScanlines--;
			}
			
			final Image image = new Image(resolutionX, resolutionY, DataFactory.forColor4D());
			
			for(int i = 0; i < pixels.length; i++) {
				image.setColor3D(doFromRGBE(pixels[i]), i);
			}
			
			return image;
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	private static int doToRGBE(final Color3D color) {
		final double r = color.r;
		final double g = color.g;
		final double b = color.b;
		
		double v = Doubles.max(r, g, b);
		
		if(v < 1.0e-32D) {
			return 0;
		}
		
		double m = v;
		
		int e = 0;
		
		if(v > 1.0D) {
			while(m > 1.0D) {
				m *= 0.5D;
				
				e++;
			}
		} else if(v <= 0.5D) {
			while(m <= 0.5D) {
				m *= 2.0D;
				
				e--;
			}
		}
		
		v = (m * 255.0D) / v;
		
		int c = (e + 128);
		
		c |= ((int)(r * v) << 24);
		c |= ((int)(g * v) << 16);
		c |= ((int)(b * v) <<  8);
		
		return c;
	}
	
	private static void doReadFlatRGBE(final FileInputStream fileInputStream, final int rasterPos, final int numPixels, final int[] pixels) throws IOException {
		int currentNumPixels = numPixels;
		int currentRasterPos = rasterPos;
		
		while(currentNumPixels-- > 0) {
			int r = fileInputStream.read();
			int g = fileInputStream.read();
			int b = fileInputStream.read();
			int e = fileInputStream.read();
			
			pixels[currentRasterPos] = (r << 24) | (g << 16) | (b << 8) | e;
			
			currentRasterPos++;
		}
	}
}