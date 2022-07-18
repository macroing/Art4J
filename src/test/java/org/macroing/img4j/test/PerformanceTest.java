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
package org.macroing.img4j.test;

import org.macroing.img4j.color.Color;
import org.macroing.img4j.color.Color4D;
import org.macroing.img4j.data.DataFactory;
import org.macroing.img4j.image.Image;
import org.macroing.img4j.kernel.ConvolutionKernelND;

public final class PerformanceTest {
	private PerformanceTest() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
		doTestConvolve();
		doTestDrawConsumer();
		doTestFillBiFunction();
		doTestFillPixelOperator();
		doTestFillRegionBiFunction();
		doTestFillRegionPixelOperator();
		doTestFlip();
		doTestFlipX();
		doTestFlipY();
		doTestRotate();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static void doTestConvolve() {
		/*
		 * Color4D:    79 (118)
		 * ColorARGB: 109 (233)
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColorARGB().create());
		
		int iterations = 10;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.convolve(ConvolutionKernelND.EMBOSS_3);
			
			final long timeB = System.currentTimeMillis();
			
			imageB.convolve(ConvolutionKernelND.EMBOSS_3);
			
			final long timeC = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
		}
		
		System.out.println("convolve");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeB / iterations);
	}
	
	private static void doTestDrawConsumer() {
		/*
		 * Color4D:   46
		 * ColorARGB:  0
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColorARGB().create());
		
		int iterations = 10;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.draw(g -> g.drawString("Hello, World!", 100, 100));
			
			final long timeB = System.currentTimeMillis();
			
			imageB.draw(g -> g.drawString("Hello, World!", 100, 100));
			
			final long timeC = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
		}
		
		System.out.println("draw(Consumer)");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeB / iterations);
	}
	
	private static void doTestFillBiFunction() {
		/*
		 * Color4D:   23
		 * ColorARGB: 45
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColorARGB().create());
		
		int iterations = 100;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.fill((color, point) -> Color4D.sepia(color));
			
			final long timeB = System.currentTimeMillis();
			
			imageB.fill((color, point) -> Color4D.sepia(color));
			
			final long timeC = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
		}
		
		System.out.println("fill(BiFunction)");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeB / iterations);
	}
	
	private static void doTestFillPixelOperator() {
		/*
		 * Color4D:   61
		 * ColorARGB: 28
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColorARGB().create());
		
		int iterations = 100;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.fill((colorARGB, x, y) -> Color.sepia(colorARGB));
			
			final long timeB = System.currentTimeMillis();
			
			imageB.fill((colorARGB, x, y) -> Color.sepia(colorARGB));
			
			final long timeC = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
		}
		
		System.out.println("fill(PixelOperator)");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeB / iterations);
	}
	
	private static void doTestFillRegionBiFunction() {
		/*
		 * Color4D:   72
		 * ColorARGB: 99
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColorARGB().create());
		
		int iterations = 100;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.fillRegion(100, 100, (color, point) -> Color4D.RED);
			
			final long timeB = System.currentTimeMillis();
			
			imageB.fillRegion(100, 100, (color, point) -> Color4D.RED);
			
			final long timeC = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
		}
		
		System.out.println("fillRegion(BiFunction)");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeB / iterations);
	}
	
	private static void doTestFillRegionPixelOperator() {
		/*
		 * Color4D:   192
		 * ColorARGB:  38
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColorARGB().create());
		
		int iterations = 100;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.fillRegion(100, 100, (colorARGB, x, y) -> Color.RED);
			
			final long timeB = System.currentTimeMillis();
			
			imageB.fillRegion(100, 100, (colorARGB, x, y) -> Color.RED);
			
			final long timeC = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
		}
		
		System.out.println("fillRegion(PixelOperator)");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeB / iterations);
	}
	
	private static void doTestFlip() {
		/*
		 * Color4D:   1 (6)
		 * ColorARGB: 1 (5)
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColorARGB().create());
		
		int iterations = 100;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.flip();
			
			final long timeB = System.currentTimeMillis();
			
			imageB.flip();
			
			final long timeC = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
		}
		
		System.out.println("flip");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeB / iterations);
	}
	
	private static void doTestFlipX() {
		/*
		 * Color4D:   1 (6)
		 * ColorARGB: 0 (5)
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColorARGB().create());
		
		int iterations = 100;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.flipX();
			
			final long timeB = System.currentTimeMillis();
			
			imageB.flipX();
			
			final long timeC = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
		}
		
		System.out.println("flipX");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeB / iterations);
	}
	
	private static void doTestFlipY() {
		/*
		 * Color4D:   0
		 * ColorARGB: 0
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColorARGB().create());
		
		int iterations = 100;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.flipY();
			
			final long timeB = System.currentTimeMillis();
			
			imageB.flipY();
			
			final long timeC = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
		}
		
		System.out.println("flipY");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeB / iterations);
	}
	
	private static void doTestRotate() {
		/*
		 * Color4D:   15 ( 46)
		 * ColorARGB: 15 (151)
		 */
		
		final Image imageA = new Image(DataFactory.forColor4D().create());
		final Image imageB = new Image(DataFactory.forColorARGB().create());
		
		int iterations = 10;
		
		long elapsedTimeA = 0L;
		long elapsedTimeB = 0L;
		
		for(int i = 0; i < iterations; i++) {
			final long timeA = System.currentTimeMillis();
			
			imageA.copy().rotate(45.0D);
			
			final long timeB = System.currentTimeMillis();
			
			imageB.copy().rotate(45.0D);
			
			final long timeC = System.currentTimeMillis();
			
			elapsedTimeA += timeB - timeA;
			elapsedTimeB += timeC - timeB;
		}
		
		System.out.println("rotate");
		System.out.println(" - Color4D:   " + elapsedTimeA / iterations);
		System.out.println(" - ColorARGB: " + elapsedTimeB / iterations);
	}
}