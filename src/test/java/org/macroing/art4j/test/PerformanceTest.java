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

import org.macroing.art4j.color.Color4D;
import org.macroing.art4j.color.Color4F;
import org.macroing.art4j.color.Color4I;
import org.macroing.art4j.data.DataFactory;
import org.macroing.art4j.image.Image;
import org.macroing.art4j.kernel.ConvolutionKernelND;
import org.macroing.art4j.kernel.ConvolutionKernelNF;

public final class PerformanceTest {
	private PerformanceTest() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
		doTestConvolveColor4D();
		doTestConvolveColor4F();
		doTestDrawConsumer();
		doTestFillColor4D();
		doTestFillColor4F();
		doTestFillColorARGB();
		doTestFillRegionColor4D();
		doTestFillRegionColor4F();
		doTestFillRegionColorARGB();
		doTestFlip();
		doTestFlipX();
		doTestFlipY();
		doTestRotateDouble();
		doTestRotateFloat();
		
		/*
		 * Method:             Color4D: Color4F: ColorARGB: Number 1: Number 2: Number 3:
		 * 
		 * convolveColor4D      68      111       80        Color4D   ColorARGB Color4F
		 * convolveColor4F      60       47       66        Color4F   Color4D   ColorARGB
		 * draw                 39       59        0        ColorARGB Color4D   Color4F
		 * fillColor4D          23       36       38        Color4D   Color4F   ColorARGB
		 * fillColor4F          43       25       30        Color4F   ColorARGB Color4D
		 * fillColorARGB        52       46       23        ColorARGB Color4F   Color4D
		 * fillRegionColor4D    66      188      126        Color4D   ColorARGB Color4F
		 * fillRegionColor4F   197       63      110        Color4F   ColorARGB Color4D
		 * fillRegionColorARGB 187      167       46        ColorARGB Color4F   Color4D
		 * 
		 * flip                  4        4        3        ColorARGB Color4D   Color4F
		 * flipX                 2        2        1        ColorARGB Color4D   Color4F
		 * flipY                 2        2        1        ColorARGB Color4D   Color4F
		 * rotate(double)       14       14       13        ColorARGB Color4D   Color4F
		 * rotate(float)        13       13       12        ColorARGB Color4D   Color4F
		 */
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static void doTestConvolveColor4D() {
		/*
		 * Color4D:    68 | ( 79) (118)
		 * Color4F:   111 | (---) (---)
		 * ColorARGB:  80 | (109) (233)
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColor4F().create());
		final Image imageC = new Image(DataFactory.forPackedIntARGB().create());
		
		int iterations = 10;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		long elapsedTimeC = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.convolve(ConvolutionKernelND.EMBOSS_3);
			
			final long timeB = System.currentTimeMillis();
			
			imageB.convolve(ConvolutionKernelND.EMBOSS_3);
			
			final long timeC = System.currentTimeMillis();
			
			imageC.convolve(ConvolutionKernelND.EMBOSS_3);
			
			final long timeD = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
			elapsedTimeC += timeD - timeC;
		}
		
		System.out.println("convolveColor4D");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - Color4F:   " + elapsedTimeB / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeC / iterations);
	}
	
	private static void doTestConvolveColor4F() {
		/*
		 * Color4D:   60 (---)
		 * Color4F:   47 (---)
		 * ColorARGB: 66 (118)
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColor4F().create());
		final Image imageC = new Image(DataFactory.forPackedIntARGB().create());
		
		int iterations = 10;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		long elapsedTimeC = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.convolve(ConvolutionKernelNF.EMBOSS_3);
			
			final long timeB = System.currentTimeMillis();
			
			imageB.convolve(ConvolutionKernelNF.EMBOSS_3);
			
			final long timeC = System.currentTimeMillis();
			
			imageC.convolve(ConvolutionKernelNF.EMBOSS_3);
			
			final long timeD = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
			elapsedTimeC += timeD - timeC;
		}
		
		System.out.println("convolveColor4F");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - Color4F:   " + elapsedTimeB / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeC / iterations);
	}
	
	private static void doTestDrawConsumer() {
		/*
		 * Color4D:   39 | (46)
		 * Color4F:   59 | (--)
		 * ColorARGB:  0 | ( 0)
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColor4F().create());
		final Image imageC = new Image(DataFactory.forPackedIntARGB().create());
		
		int iterations = 10;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		long elapsedTimeC = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.draw(g -> g.drawString("Hello, World!", 100, 100));
			
			final long timeB = System.currentTimeMillis();
			
			imageB.draw(g -> g.drawString("Hello, World!", 100, 100));
			
			final long timeC = System.currentTimeMillis();
			
			imageC.draw(g -> g.drawString("Hello, World!", 100, 100));
			
			final long timeD = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
			elapsedTimeC += timeD - timeC;
		}
		
		System.out.println("draw(Consumer)");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - Color4F:   " + elapsedTimeB / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeC / iterations);
	}
	
	private static void doTestFillColor4D() {
		/*
		 * Color4D:   23 | (23)
		 * Color4F:   36 | (--)
		 * ColorARGB: 38 | (45)
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColor4F().create());
		final Image imageC = new Image(DataFactory.forPackedIntARGB().create());
		
		int iterations = 100;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		long elapsedTimeC = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.fillD((color, x, y) -> Color4D.sepia(color));
			
			final long timeB = System.currentTimeMillis();
			
			imageB.fillD((color, x, y) -> Color4D.sepia(color));
			
			final long timeC = System.currentTimeMillis();
			
			imageC.fillD((color, x, y) -> Color4D.sepia(color));
			
			final long timeD = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
			elapsedTimeC += timeD - timeC;
		}
		
		System.out.println("fillColor4D");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - Color4F:   " + elapsedTimeB / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeC / iterations);
	}
	
	private static void doTestFillColor4F() {
		/*
		 * Color4D:   43
		 * Color4F:   25
		 * ColorARGB: 30
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColor4F().create());
		final Image imageC = new Image(DataFactory.forPackedIntARGB().create());
		
		int iterations = 100;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		long elapsedTimeC = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.fillF((color, x, y) -> Color4F.sepia(color));
			
			final long timeB = System.currentTimeMillis();
			
			imageB.fillF((color, x, y) -> Color4F.sepia(color));
			
			final long timeC = System.currentTimeMillis();
			
			imageC.fillF((color, x, y) -> Color4F.sepia(color));
			
			final long timeD = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
			elapsedTimeC += timeD - timeC;
		}
		
		System.out.println("fillColor4F");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - Color4F:   " + elapsedTimeB / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeC / iterations);
	}
	
	private static void doTestFillColorARGB() {
		/*
		 * Color4D:   52 | (61)
		 * Color4F:   46 | (--)
		 * ColorARGB: 23 | (28)
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColor4F().create());
		final Image imageC = new Image(DataFactory.forPackedIntARGB().create());
		
		int iterations = 100;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		long elapsedTimeC = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.fillI((color, x, y) -> Color4I.sepiaARGB(color));
			
			final long timeB = System.currentTimeMillis();
			
			imageB.fillI((color, x, y) -> Color4I.sepiaARGB(color));
			
			final long timeC = System.currentTimeMillis();
			
			imageC.fillI((color, x, y) -> Color4I.sepiaARGB(color));
			
			final long timeD = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
			elapsedTimeC += timeD - timeC;
		}
		
		System.out.println("fillColorARGB");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - Color4F:   " + elapsedTimeB / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeC / iterations);
	}
	
	private static void doTestFillRegionColor4D() {
		/*
		 * Color4D:    66 ( 82) | (72)
		 * Color4F:   188 (172) | (--)
		 * ColorARGB: 126 ( 92) | (99)
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColor4F().create());
		final Image imageC = new Image(DataFactory.forPackedIntARGB().create());
		
		int iterations = 100;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		long elapsedTimeC = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.fillRegionD(100, 100, (color, x, y) -> Color4D.RED);
			
			final long timeB = System.currentTimeMillis();
			
			imageB.fillRegionD(100, 100, (color, x, y) -> Color4D.RED);
			
			final long timeC = System.currentTimeMillis();
			
			imageC.fillRegionD(100, 100, (color, x, y) -> Color4D.RED);
			
			final long timeD = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
			elapsedTimeC += timeD - timeC;
		}
		
		System.out.println("fillRegionColor4D");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - Color4F:   " + elapsedTimeB / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeC / iterations);
	}
	
	private static void doTestFillRegionColor4F() {
		/*
		 * Color4D:   197 (201)
		 * Color4F:    63 ( 93)
		 * ColorARGB: 110 ( 96)
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColor4F().create());
		final Image imageC = new Image(DataFactory.forPackedIntARGB().create());
		
		int iterations = 100;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		long elapsedTimeC = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.fillRegionF(100, 100, (color, x, y) -> Color4F.RED);
			
			final long timeB = System.currentTimeMillis();
			
			imageB.fillRegionF(100, 100, (color, x, y) -> Color4F.RED);
			
			final long timeC = System.currentTimeMillis();
			
			imageC.fillRegionF(100, 100, (color, x, y) -> Color4F.RED);
			
			final long timeD = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
			elapsedTimeC += timeD - timeC;
		}
		
		System.out.println("fillRegionColor4F");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - Color4F:   " + elapsedTimeB / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeC / iterations);
	}
	
	private static void doTestFillRegionColorARGB() {
		/*
		 * Color4D:   187 | (192)
		 * Color4F:   167 | (---)
		 * ColorARGB:  46 | ( 38)
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColor4F().create());
		final Image imageC = new Image(DataFactory.forPackedIntARGB().create());
		
		int iterations = 100;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		long elapsedTimeC = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.fillRegionI(100, 100, (color, x, y) -> Color4I.RED_A_R_G_B);
			
			final long timeB = System.currentTimeMillis();
			
			imageB.fillRegionI(100, 100, (color, x, y) -> Color4I.RED_A_R_G_B);
			
			final long timeC = System.currentTimeMillis();
			
			imageC.fillRegionI(100, 100, (color, x, y) -> Color4I.RED_A_R_G_B);
			
			final long timeD = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
			elapsedTimeC += timeD - timeC;
		}
		
		System.out.println("fillRegionColorARGB");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - Color4F:   " + elapsedTimeB / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeC / iterations);
	}
	
	private static void doTestFlip() {
		/*
		 * Color4D:   4 | (1) (6)
		 * Color4F:   4 | (-) (-)
		 * ColorARGB: 3 | (1) (5)
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColor4F().create());
		final Image imageC = new Image(DataFactory.forPackedIntARGB().create());
		
		int iterations = 100;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		long elapsedTimeC = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.flip();
			
			final long timeB = System.currentTimeMillis();
			
			imageB.flip();
			
			final long timeC = System.currentTimeMillis();
			
			imageC.flip();
			
			final long timeD = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
			elapsedTimeC += timeD - timeC;
		}
		
		System.out.println("flip");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - Color4F:   " + elapsedTimeB / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeC / iterations);
	}
	
	private static void doTestFlipX() {
		/*
		 * Color4D:   2 | (1) (6)
		 * Color4F:   2 | (-) (-)
		 * ColorARGB: 1 | (0) (5)
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColor4F().create());
		final Image imageC = new Image(DataFactory.forPackedIntARGB().create());
		
		int iterations = 100;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		long elapsedTimeC = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.flipX();
			
			final long timeB = System.currentTimeMillis();
			
			imageB.flipX();
			
			final long timeC = System.currentTimeMillis();
			
			imageC.flipX();
			
			final long timeD = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
			elapsedTimeC += timeD - timeC;
		}
		
		System.out.println("flipX");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - Color4F:   " + elapsedTimeB / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeC / iterations);
	}
	
	private static void doTestFlipY() {
		/*
		 * Color4D:   2 | (0)
		 * Color4F:   2 | (-)
		 * ColorARGB: 1 | (0)
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColor4F().create());
		final Image imageC = new Image(DataFactory.forPackedIntARGB().create());
		
		int iterations = 100;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		long elapsedTimeC = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.flipY();
			
			final long timeB = System.currentTimeMillis();
			
			imageB.flipY();
			
			final long timeC = System.currentTimeMillis();
			
			imageC.flipY();
			
			final long timeD = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
			elapsedTimeC += timeD - timeC;
		}
		
		System.out.println("flipY");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - Color4F:   " + elapsedTimeB / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeC / iterations);
	}
	
	private static void doTestRotateDouble() {
		/*
		 * Color4D:   14 | (15) ( 46)
		 * Color4F:   14 | (--) (---)
		 * ColorARGB: 13 | (15) (151)
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColor4F().create());
		final Image imageC = new Image(DataFactory.forPackedIntARGB().create());
		
		int iterations = 10;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		long elapsedTimeC = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.copy().rotate(45.0D);
			
			final long timeB = System.currentTimeMillis();
			
			imageB.copy().rotate(45.0D);
			
			final long timeC = System.currentTimeMillis();
			
			imageC.copy().rotate(45.0D);
			
			final long timeD = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
			elapsedTimeC += timeD - timeC;
		}
		
		System.out.println("rotate(double)");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - Color4F:   " + elapsedTimeB / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeC / iterations);
	}
	
	private static void doTestRotateFloat() {
		/*
		 * Color4D:   13
		 * Color4F:   13
		 * ColorARGB: 12
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColor4F().create());
		final Image imageC = new Image(DataFactory.forPackedIntARGB().create());
		
		int iterations = 10;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		long elapsedTimeC = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.copy().rotate(45.0F);
			
			final long timeB = System.currentTimeMillis();
			
			imageB.copy().rotate(45.0F);
			
			final long timeC = System.currentTimeMillis();
			
			imageC.copy().rotate(45.0F);
			
			final long timeD = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
			elapsedTimeC += timeD - timeC;
		}
		
		System.out.println("rotate(float)");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - Color4F:   " + elapsedTimeB / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeC / iterations);
	}
}