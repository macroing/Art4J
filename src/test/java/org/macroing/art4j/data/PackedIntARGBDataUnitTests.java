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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import org.junit.jupiter.api.Test;

import org.macroing.art4j.color.Color3D;
import org.macroing.art4j.color.Color3F;
import org.macroing.art4j.color.Color3I;
import org.macroing.art4j.color.Color4D;
import org.macroing.art4j.color.Color4F;
import org.macroing.art4j.color.Color4I;
import org.macroing.art4j.data.PackedIntARGBData.PixelChange;
import org.macroing.art4j.data.PackedIntARGBData.StateChange;
import org.macroing.art4j.geometry.Point2I;
import org.macroing.art4j.geometry.shape.Rectangle2I;
import org.macroing.art4j.kernel.ConvolutionKernelND;
import org.macroing.art4j.kernel.ConvolutionKernelNF;

@SuppressWarnings("static-method")
public final class PackedIntARGBDataUnitTests {
	public PackedIntARGBDataUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testCache() {
		final
		PackedIntARGBData packedIntARGBData = new PackedIntARGBData(2, 2);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(0, 0, 0, 255), 0);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(0, 0, 0, 255), 1);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(0, 0, 0, 255), 2);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(0, 0, 0, 255), 3);
		
		assertEquals(0, packedIntARGBData.cache());
	}
	
	@Test
	public void testChangeAdd() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertFalse(packedIntARGBData.changeAdd(new PixelChange(Color4I.BLACK_A_R_G_B, Color4I.WHITE_A_R_G_B, 1)));
		
		packedIntARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(packedIntARGBData.changeAdd(new PixelChange(Color4I.BLACK_A_R_G_B, Color4I.WHITE_A_R_G_B, 1)));
		
		packedIntARGBData.changeBegin();
		
		assertTrue(packedIntARGBData.changeAdd(new PixelChange(Color4I.BLACK_A_R_G_B, Color4I.WHITE_A_R_G_B, 1)));
		
		assertThrows(NullPointerException.class, () -> packedIntARGBData.changeAdd(null));
	}
	
	@Test
	public void testChangeBegin() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertFalse(packedIntARGBData.changeBegin());
		
		packedIntARGBData.setChangeHistoryEnabled(true);
		
		assertTrue(packedIntARGBData.changeBegin());
		
		assertFalse(packedIntARGBData.changeBegin());
		
		packedIntARGBData.changeEnd();
		
		assertTrue(packedIntARGBData.changeBegin());
	}
	
	@Test
	public void testChangeEnd() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertFalse(packedIntARGBData.changeEnd());
		
		packedIntARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(packedIntARGBData.changeEnd());
		
		packedIntARGBData.changeBegin();
		
		assertTrue(packedIntARGBData.changeEnd());
		
		assertFalse(packedIntARGBData.changeEnd());
		
		packedIntARGBData.changeBegin();
		
		assertTrue(packedIntARGBData.changeEnd());
	}
	
	@Test
	public void testConstructor() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData();
		
		assertEquals(1024, packedIntARGBData.getResolutionX());
		assertEquals( 768, packedIntARGBData.getResolutionY());
		
		for(int y = 0; y < packedIntARGBData.getResolutionY(); y++) {
			for(int x = 0; x < packedIntARGBData.getResolutionX(); x++) {
				assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(x, y));
			}
		}
	}
	
	@Test
	public void testConstructorBufferedImage() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(new BufferedImage(1024, 768, BufferedImage.TYPE_INT_ARGB));
		
		assertEquals(1024, packedIntARGBData.getResolutionX());
		assertEquals( 768, packedIntARGBData.getResolutionY());
		
		for(int y = 0; y < packedIntARGBData.getResolutionY(); y++) {
			for(int x = 0; x < packedIntARGBData.getResolutionX(); x++) {
				assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> new PackedIntARGBData((BufferedImage)(null)));
	}
	
	@Test
	public void testConstructorColorARGBData() {
		final
		PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1024, 768);
		packedIntARGBData.setChangeHistoryEnabled(true);
		packedIntARGBData.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		packedIntARGBData.undo();
		packedIntARGBData.redo();
		
		final PackedIntARGBData packedIntARGBDataCopyA = new PackedIntARGBData(packedIntARGBData);
		
		assertEquals(packedIntARGBData, packedIntARGBDataCopyA);
		
		packedIntARGBData.undo();
		packedIntARGBData.setChangeHistoryEnabled(false);
		packedIntARGBData.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		
		final PackedIntARGBData packedIntARGBDataCopyB = new PackedIntARGBData(packedIntARGBData);
		
		assertEquals(packedIntARGBData, packedIntARGBDataCopyB);
		
		assertThrows(NullPointerException.class, () -> new PackedIntARGBData((PackedIntARGBData)(null)));
	}
	
	@Test
	public void testConstructorColorARGBDataBoolean() {
		final
		PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1024, 768);
		packedIntARGBData.setChangeHistoryEnabled(true);
		packedIntARGBData.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		packedIntARGBData.undo();
		packedIntARGBData.redo();
		
		final PackedIntARGBData packedIntARGBDataCopyA = new PackedIntARGBData(packedIntARGBData, false);
		final PackedIntARGBData packedIntARGBDataCopyB = new PackedIntARGBData(packedIntARGBData, true);
		
		assertEquals(packedIntARGBData, packedIntARGBDataCopyA);
		
		assertNotEquals(packedIntARGBData, packedIntARGBDataCopyB);
		
		packedIntARGBData.undo();
		packedIntARGBData.setChangeHistoryEnabled(false);
		packedIntARGBData.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		
		final PackedIntARGBData packedIntARGBDataCopyC = new PackedIntARGBData(packedIntARGBData, false);
		final PackedIntARGBData packedIntARGBDataCopyD = new PackedIntARGBData(packedIntARGBData, true);
		
		assertEquals(packedIntARGBData, packedIntARGBDataCopyC);
		assertEquals(packedIntARGBData, packedIntARGBDataCopyD);
		
		assertThrows(NullPointerException.class, () -> new PackedIntARGBData((PackedIntARGBData)(null), false));
	}
	
	@Test
	public void testConstructorIntInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1024, 768);
		
		assertEquals(1024, packedIntARGBData.getResolutionX());
		assertEquals( 768, packedIntARGBData.getResolutionY());
		
		for(int y = 0; y < packedIntARGBData.getResolutionY(); y++) {
			for(int x = 0; x < packedIntARGBData.getResolutionX(); x++) {
				assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(x, y));
			}
		}
		
		assertThrows(IllegalArgumentException.class, () -> new PackedIntARGBData(1, 0));
		assertThrows(IllegalArgumentException.class, () -> new PackedIntARGBData(0, 1));
		assertThrows(IllegalArgumentException.class, () -> new PackedIntARGBData(Integer.MAX_VALUE, Integer.MAX_VALUE));
	}
	
	@Test
	public void testConstructorIntIntColor4D() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1024, 768, Color4D.RED);
		
		assertEquals(1024, packedIntARGBData.getResolutionX());
		assertEquals( 768, packedIntARGBData.getResolutionY());
		
		for(int y = 0; y < packedIntARGBData.getResolutionY(); y++) {
			for(int x = 0; x < packedIntARGBData.getResolutionX(); x++) {
				assertEquals(Color4I.RED_A_R_G_B, packedIntARGBData.getColorARGB(x, y));
			}
		}
		
		assertThrows(IllegalArgumentException.class, () -> new PackedIntARGBData(1, 0, Color4D.RED));
		assertThrows(IllegalArgumentException.class, () -> new PackedIntARGBData(0, 1, Color4D.RED));
		assertThrows(IllegalArgumentException.class, () -> new PackedIntARGBData(Integer.MAX_VALUE, Integer.MAX_VALUE, Color4D.RED));
		
		assertThrows(NullPointerException.class, () -> new PackedIntARGBData(1, 1, (Color4D)(null)));
	}
	
	@Test
	public void testConstructorIntIntColor4F() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1024, 768, Color4F.RED);
		
		assertEquals(1024, packedIntARGBData.getResolutionX());
		assertEquals( 768, packedIntARGBData.getResolutionY());
		
		for(int y = 0; y < packedIntARGBData.getResolutionY(); y++) {
			for(int x = 0; x < packedIntARGBData.getResolutionX(); x++) {
				assertEquals(Color4I.RED_A_R_G_B, packedIntARGBData.getColorARGB(x, y));
			}
		}
		
		assertThrows(IllegalArgumentException.class, () -> new PackedIntARGBData(1, 0, Color4F.RED));
		assertThrows(IllegalArgumentException.class, () -> new PackedIntARGBData(0, 1, Color4F.RED));
		assertThrows(IllegalArgumentException.class, () -> new PackedIntARGBData(Integer.MAX_VALUE, Integer.MAX_VALUE, Color4F.RED));
		
		assertThrows(NullPointerException.class, () -> new PackedIntARGBData(1, 1, (Color4F)(null)));
	}
	
	@Test
	public void testConstructorIntIntInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1024, 768, Color4I.WHITE_A_R_G_B);
		
		assertEquals(1024, packedIntARGBData.getResolutionX());
		assertEquals( 768, packedIntARGBData.getResolutionY());
		
		for(int y = 0; y < packedIntARGBData.getResolutionY(); y++) {
			for(int x = 0; x < packedIntARGBData.getResolutionX(); x++) {
				assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(x, y));
			}
		}
		
		assertThrows(IllegalArgumentException.class, () -> new PackedIntARGBData(1, 0, Color4I.WHITE_A_R_G_B));
		assertThrows(IllegalArgumentException.class, () -> new PackedIntARGBData(0, 1, Color4I.WHITE_A_R_G_B));
		assertThrows(IllegalArgumentException.class, () -> new PackedIntARGBData(Integer.MAX_VALUE, Integer.MAX_VALUE, Color4I.WHITE_A_R_G_B));
	}
	
	@Test
	public void testConvolveConvolutionKernelNDIntArray() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertFalse(packedIntARGBData.convolve(ConvolutionKernelND.IDENTITY_3, new int[0]));
		assertFalse(packedIntARGBData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		assertFalse(packedIntARGBData.undo());
		
		packedIntARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(packedIntARGBData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		assertFalse(packedIntARGBData.undo());
		
		packedIntARGBData.changeBegin();
		
		assertFalse(packedIntARGBData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		
		packedIntARGBData.changeEnd();
		
		assertFalse(packedIntARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		packedIntARGBData.setChangeHistoryEnabled(false);
		
		assertTrue(packedIntARGBData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {0}));
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertFalse(packedIntARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		packedIntARGBData.setChangeHistoryEnabled(true);
		
		assertTrue(packedIntARGBData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {0}));
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		packedIntARGBData.changeBegin();
		
		assertTrue(packedIntARGBData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {0}));
		
		packedIntARGBData.changeEnd();
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertThrows(NullPointerException.class, () -> packedIntARGBData.convolve(ConvolutionKernelND.IDENTITY_3, null));
		assertThrows(NullPointerException.class, () -> packedIntARGBData.convolve((ConvolutionKernelND)(null), new int[1]));
	}
	
	@Test
	public void testConvolveConvolutionKernelNFIntArray() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertFalse(packedIntARGBData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[0]));
		assertFalse(packedIntARGBData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		assertFalse(packedIntARGBData.undo());
		
		packedIntARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(packedIntARGBData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		assertFalse(packedIntARGBData.undo());
		
		packedIntARGBData.changeBegin();
		
		assertFalse(packedIntARGBData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		
		packedIntARGBData.changeEnd();
		
		assertFalse(packedIntARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		packedIntARGBData.setChangeHistoryEnabled(false);
		
		assertTrue(packedIntARGBData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {0}));
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertFalse(packedIntARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		packedIntARGBData.setChangeHistoryEnabled(true);
		
		assertTrue(packedIntARGBData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {0}));
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		packedIntARGBData.changeBegin();
		
		assertTrue(packedIntARGBData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {0}));
		
		packedIntARGBData.changeEnd();
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertThrows(NullPointerException.class, () -> packedIntARGBData.convolve(ConvolutionKernelNF.IDENTITY_3, null));
		assertThrows(NullPointerException.class, () -> packedIntARGBData.convolve((ConvolutionKernelNF)(null), new int[1]));
	}
	
	@Test
	public void testCopy() {
		final
		PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1024, 768);
		packedIntARGBData.setChangeHistoryEnabled(true);
		packedIntARGBData.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		packedIntARGBData.undo();
		packedIntARGBData.redo();
		
		final Data dataCopyA = packedIntARGBData.copy();
		
		assertTrue(dataCopyA instanceof PackedIntARGBData);
		
		final PackedIntARGBData packedIntARGBDataCopyA = PackedIntARGBData.class.cast(dataCopyA);
		
		assertEquals(packedIntARGBData, packedIntARGBDataCopyA);
		
		packedIntARGBData.undo();
		packedIntARGBData.setChangeHistoryEnabled(false);
		packedIntARGBData.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		
		final Data dataCopyB = packedIntARGBData.copy();
		
		assertTrue(dataCopyB instanceof PackedIntARGBData);
		
		final PackedIntARGBData packedIntARGBDataCopyB = PackedIntARGBData.class.cast(dataCopyB);
		
		assertEquals(packedIntARGBData, packedIntARGBDataCopyB);
	}
	
	@Test
	public void testCopyBoolean() {
		final
		PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1024, 768);
		packedIntARGBData.setChangeHistoryEnabled(true);
		packedIntARGBData.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		packedIntARGBData.undo();
		packedIntARGBData.redo();
		
		final Data dataCopyA = packedIntARGBData.copy(false);
		final Data dataCopyB = packedIntARGBData.copy(true);
		
		assertTrue(dataCopyA instanceof PackedIntARGBData);
		assertTrue(dataCopyB instanceof PackedIntARGBData);
		
		final PackedIntARGBData packedIntARGBDataCopyA = PackedIntARGBData.class.cast(dataCopyA);
		final PackedIntARGBData packedIntARGBDataCopyB = PackedIntARGBData.class.cast(dataCopyB);
		
		assertEquals(packedIntARGBData, packedIntARGBDataCopyA);
		
		assertNotEquals(packedIntARGBData, packedIntARGBDataCopyB);
		
		packedIntARGBData.undo();
		packedIntARGBData.setChangeHistoryEnabled(false);
		packedIntARGBData.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		
		final Data dataCopyC = packedIntARGBData.copy(false);
		final Data dataCopyD = packedIntARGBData.copy(true);
		
		assertTrue(dataCopyC instanceof PackedIntARGBData);
		assertTrue(dataCopyD instanceof PackedIntARGBData);
		
		final PackedIntARGBData packedIntARGBDataCopyC = PackedIntARGBData.class.cast(dataCopyC);
		final PackedIntARGBData packedIntARGBDataCopyD = PackedIntARGBData.class.cast(dataCopyD);
		
		assertEquals(packedIntARGBData, packedIntARGBDataCopyC);
		assertEquals(packedIntARGBData, packedIntARGBDataCopyD);
	}
	
	@Test
	public void testCopyShape2I() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(10, 10);
		
		assertEquals(10, packedIntARGBData.getResolutionX());
		assertEquals(10, packedIntARGBData.getResolutionY());
		
		for(int y = 0; y < packedIntARGBData.getResolutionY(); y++) {
			for(int x = 0; x < packedIntARGBData.getResolutionX(); x++) {
				packedIntARGBData.setColorARGB(Color4I.toIntARGB(x, y, 0, 255), x, y);
			}
		}
		
		final Data dataCopy = packedIntARGBData.copy(new Rectangle2I(new Point2I(1, 1), new Point2I(8, 8)));
		
		assertTrue(dataCopy instanceof PackedIntARGBData);
		
		final PackedIntARGBData packedIntARGBDataCopy = PackedIntARGBData.class.cast(dataCopy);
		
		assertEquals(8, packedIntARGBDataCopy.getResolutionX());
		assertEquals(8, packedIntARGBDataCopy.getResolutionY());
		
		for(int y = 0; y < packedIntARGBDataCopy.getResolutionY(); y++) {
			for(int x = 0; x < packedIntARGBDataCopy.getResolutionX(); x++) {
				final int color = packedIntARGBDataCopy.getColorARGB(x, y);
				
				assertEquals(x + 1, Color4I.fromIntARGBToIntR(color));
				assertEquals(y + 1, Color4I.fromIntARGBToIntG(color));
				
				assertEquals(  0, Color4I.fromIntARGBToIntB(color));
				assertEquals(255, Color4I.fromIntARGBToIntA(color));
			}
		}
		
		assertThrows(NullPointerException.class, () -> packedIntARGBData.copy(null));
	}
	
	@Test
	public void testDrawConsumerGraphics2D() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		packedIntARGBData.draw(graphics2D -> {
			graphics2D.setColor(Color.RED);
			graphics2D.fillRect(0, 0, 1, 1);
		});
		
		assertEquals(Color4I.RED_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertFalse(packedIntARGBData.undo());
		
		assertEquals(Color4I.RED_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		packedIntARGBData.setChangeHistoryEnabled(true);
		packedIntARGBData.draw(graphics2D -> {
			graphics2D.setColor(Color.GREEN);
			graphics2D.fillRect(0, 0, 1, 1);
		});
		
		assertEquals(Color4I.GREEN_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(Color4I.RED_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		packedIntARGBData.changeBegin();
		packedIntARGBData.draw(graphics2D -> {
			graphics2D.setColor(Color.BLUE);
			graphics2D.fillRect(0, 0, 1, 1);
		});
		packedIntARGBData.changeEnd();
		
		assertEquals(Color4I.BLUE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(Color4I.RED_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertThrows(NullPointerException.class, () -> packedIntARGBData.draw(null));
	}
	
	@Test
	public void testEquals() {
		final Data a = new PackedIntARGBData(400, 400);
		final Data b = new PackedIntARGBData(400, 400);
		final Data c = new PackedIntARGBData(400, 200);
		final Data d = new PackedIntARGBData(200, 400);
		final Data e = new PackedIntARGBData(400, 400, Color4D.GREEN);
		final Data f = new Color4DData(400, 400);
		final Data g = null;
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(a, d);
		assertNotEquals(d, a);
		assertNotEquals(a, e);
		assertNotEquals(e, a);
		assertNotEquals(a, f);
		assertNotEquals(f, a);
		assertNotEquals(a, g);
		assertNotEquals(g, a);
		
		a.setChangeHistoryEnabled(true);
		b.setChangeHistoryEnabled(true);
		
		a.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		b.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		
		a.undo();
		b.undo();
		
		a.redo();
		b.redo();
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(a, d);
		assertNotEquals(d, a);
		assertNotEquals(a, e);
		assertNotEquals(e, a);
		assertNotEquals(a, f);
		assertNotEquals(f, a);
		assertNotEquals(a, g);
		assertNotEquals(g, a);
		
		a.undo();
		b.undo();
		
		a.setChangeHistoryEnabled(false);
		b.setChangeHistoryEnabled(false);
		
		a.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		b.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(a, d);
		assertNotEquals(d, a);
		assertNotEquals(a, e);
		assertNotEquals(e, a);
		assertNotEquals(a, f);
		assertNotEquals(f, a);
		assertNotEquals(a, g);
		assertNotEquals(g, a);
		
		a.setChangeHistoryEnabled(true);
		b.setChangeHistoryEnabled(true);
		
		a.setResolution(500, 500);
		b.setResolution(500, 500);
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(a, d);
		assertNotEquals(d, a);
		assertNotEquals(a, e);
		assertNotEquals(e, a);
		assertNotEquals(a, f);
		assertNotEquals(f, a);
		assertNotEquals(a, g);
		assertNotEquals(g, a);
	}
	
	@Test
	public void testGetColor3DDoubleDouble() {
		final
		PackedIntARGBData packedIntARGBData = new PackedIntARGBData(2, 2);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0, 255), 0);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(255,   0, 255, 255), 1);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0, 255), 2);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(  0, 255,   0, 255), 3);
		
		assertEquals(new Color3D(0.25D, 0.25D, 0.25D), packedIntARGBData.getColor3D(0.5D, 0.5D));
		
		assertEquals(new Color3D(0.0D, 0.0D, 0.0D), packedIntARGBData.getColor3D(0.0D, 0.5D));
		assertEquals(new Color3D(0.5D, 0.0D, 0.5D), packedIntARGBData.getColor3D(0.5D, 0.0D));
		
		assertEquals(new Color3D(0.0D, 0.0D, 0.0D), packedIntARGBData.getColor3D(0.0D, 0.0D));
		assertEquals(new Color3D(1.0D, 0.0D, 1.0D), packedIntARGBData.getColor3D(1.0D, 0.0D));
		assertEquals(new Color3D(0.0D, 0.0D, 0.0D), packedIntARGBData.getColor3D(0.0D, 1.0D));
		assertEquals(new Color3D(0.0D, 1.0D, 0.0D), packedIntARGBData.getColor3D(1.0D, 1.0D));
		
		assertEquals(Color3D.BLACK, packedIntARGBData.getColor3D(-0.1D, +0.0D));
		assertEquals(Color3D.BLACK, packedIntARGBData.getColor3D(+0.0D, -0.1D));
		assertEquals(Color3D.BLACK, packedIntARGBData.getColor3D(+2.0D, +0.0D));
		assertEquals(Color3D.BLACK, packedIntARGBData.getColor3D(+0.0D, +2.0D));
	}
	
	@Test
	public void testGetColor3DInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertEquals(Color3D.BLACK, packedIntARGBData.getColor3D(-1));
		assertEquals(Color3D.WHITE, packedIntARGBData.getColor3D(+0));
		assertEquals(Color3D.BLACK, packedIntARGBData.getColor3D(+1));
	}
	
	@Test
	public void testGetColor3DIntInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertEquals(Color3D.BLACK, packedIntARGBData.getColor3D(-1, +0));
		assertEquals(Color3D.BLACK, packedIntARGBData.getColor3D(+0, -1));
		assertEquals(Color3D.WHITE, packedIntARGBData.getColor3D(+0, +0));
		assertEquals(Color3D.BLACK, packedIntARGBData.getColor3D(+1, +0));
		assertEquals(Color3D.BLACK, packedIntARGBData.getColor3D(+0, +1));
	}
	
	@Test
	public void testGetColor3FFloatFloat() {
		final
		PackedIntARGBData packedIntARGBData = new PackedIntARGBData(2, 2);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0, 255), 0);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(255,   0, 255, 255), 1);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0, 255), 2);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(  0, 255,   0, 255), 3);
		
		assertEquals(new Color3F(0.25F, 0.25F, 0.25F), packedIntARGBData.getColor3F(0.5F, 0.5F));
		
		assertEquals(new Color3F(0.0F, 0.0F, 0.0F), packedIntARGBData.getColor3F(0.0F, 0.5F));
		assertEquals(new Color3F(0.5F, 0.0F, 0.5F), packedIntARGBData.getColor3F(0.5F, 0.0F));
		
		assertEquals(new Color3F(0.0F, 0.0F, 0.0F), packedIntARGBData.getColor3F(0.0F, 0.0F));
		assertEquals(new Color3F(1.0F, 0.0F, 1.0F), packedIntARGBData.getColor3F(1.0F, 0.0F));
		assertEquals(new Color3F(0.0F, 0.0F, 0.0F), packedIntARGBData.getColor3F(0.0F, 1.0F));
		assertEquals(new Color3F(0.0F, 1.0F, 0.0F), packedIntARGBData.getColor3F(1.0F, 1.0F));
		
		assertEquals(Color3F.BLACK, packedIntARGBData.getColor3F(-0.1F, +0.0F));
		assertEquals(Color3F.BLACK, packedIntARGBData.getColor3F(+0.0F, -0.1F));
		assertEquals(Color3F.BLACK, packedIntARGBData.getColor3F(+2.0F, +0.0F));
		assertEquals(Color3F.BLACK, packedIntARGBData.getColor3F(+0.0F, +2.0F));
	}
	
	@Test
	public void testGetColor3FInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertEquals(Color3F.BLACK, packedIntARGBData.getColor3F(-1));
		assertEquals(Color3F.WHITE, packedIntARGBData.getColor3F(+0));
		assertEquals(Color3F.BLACK, packedIntARGBData.getColor3F(+1));
	}
	
	@Test
	public void testGetColor3FIntInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertEquals(Color3F.BLACK, packedIntARGBData.getColor3F(-1, +0));
		assertEquals(Color3F.BLACK, packedIntARGBData.getColor3F(+0, -1));
		assertEquals(Color3F.WHITE, packedIntARGBData.getColor3F(+0, +0));
		assertEquals(Color3F.BLACK, packedIntARGBData.getColor3F(+1, +0));
		assertEquals(Color3F.BLACK, packedIntARGBData.getColor3F(+0, +1));
	}
	
	@Test
	public void testGetColor4DDoubleDouble() {
		final
		PackedIntARGBData packedIntARGBData = new PackedIntARGBData(2, 2);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0,   0), 0);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(255,   0, 255,   0), 1);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0,   0), 2);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(  0, 255,   0, 255), 3);
		
		assertEquals(new Color4D(0.25D, 0.25D, 0.25D, 0.25D), packedIntARGBData.getColor4D(0.5D, 0.5D));
		
		assertEquals(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), packedIntARGBData.getColor4D(0.0D, 0.5D));
		assertEquals(new Color4D(0.5D, 0.0D, 0.5D, 0.0D), packedIntARGBData.getColor4D(0.5D, 0.0D));
		
		assertEquals(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), packedIntARGBData.getColor4D(0.0D, 0.0D));
		assertEquals(new Color4D(1.0D, 0.0D, 1.0D, 0.0D), packedIntARGBData.getColor4D(1.0D, 0.0D));
		assertEquals(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), packedIntARGBData.getColor4D(0.0D, 1.0D));
		assertEquals(new Color4D(0.0D, 1.0D, 0.0D, 1.0D), packedIntARGBData.getColor4D(1.0D, 1.0D));
		
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(-0.1D, +0.0D));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(+0.0D, -0.1D));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(+2.0D, +0.0D));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(+0.0D, +2.0D));
	}
	
	@Test
	public void testGetColor4DInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(-1));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(+0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(+1));
	}
	
	@Test
	public void testGetColor4DIntInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(-1, +0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(+0, -1));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(+0, +0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(+1, +0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(+0, +1));
	}
	
	@Test
	public void testGetColor4FFloatFloat() {
		final
		PackedIntARGBData packedIntARGBData = new PackedIntARGBData(2, 2);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0,   0), 0);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(255,   0, 255,   0), 1);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0,   0), 2);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(  0, 255,   0, 255), 3);
		
		assertEquals(new Color4F(0.25F, 0.25F, 0.25F, 0.25F), packedIntARGBData.getColor4F(0.5F, 0.5F));
		
		assertEquals(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), packedIntARGBData.getColor4F(0.0F, 0.5F));
		assertEquals(new Color4F(0.5F, 0.0F, 0.5F, 0.0F), packedIntARGBData.getColor4F(0.5F, 0.0F));
		
		assertEquals(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), packedIntARGBData.getColor4F(0.0F, 0.0F));
		assertEquals(new Color4F(1.0F, 0.0F, 1.0F, 0.0F), packedIntARGBData.getColor4F(1.0F, 0.0F));
		assertEquals(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), packedIntARGBData.getColor4F(0.0F, 1.0F));
		assertEquals(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), packedIntARGBData.getColor4F(1.0F, 1.0F));
		
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(-0.1F, +0.0F));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(+0.0F, -0.1F));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(+2.0F, +0.0F));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(+0.0F, +2.0F));
	}
	
	@Test
	public void testGetColor4FInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(-1));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(+0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(+1));
	}
	
	@Test
	public void testGetColor4FIntInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(-1, +0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(+0, -1));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(+0, +0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(+1, +0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(+0, +1));
	}
	
	@Test
	public void testGetColorARGBDoubleDouble() {
		final
		PackedIntARGBData packedIntARGBData = new PackedIntARGBData(2, 2);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0,   0), 0);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(255,   0, 255,   0), 1);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0,   0), 2);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(  0, 255,   0, 255), 3);
		
		assertEquals(Color4I.toIntARGB(64, 64, 64, 64), packedIntARGBData.getColorARGB(0.5D, 0.5D));
		
		assertEquals(Color4I.toIntARGB(  0, 0,   0, 0), packedIntARGBData.getColorARGB(0.0D, 0.5D));
		assertEquals(Color4I.toIntARGB(128, 0, 128, 0), packedIntARGBData.getColorARGB(0.5D, 0.0D));
		
		assertEquals(Color4I.toIntARGB(  0,   0,   0,   0), packedIntARGBData.getColorARGB(0.0D, 0.0D));
		assertEquals(Color4I.toIntARGB(255,   0, 255,   0), packedIntARGBData.getColorARGB(1.0D, 0.0D));
		assertEquals(Color4I.toIntARGB(  0,   0,   0,   0), packedIntARGBData.getColorARGB(0.0D, 1.0D));
		assertEquals(Color4I.toIntARGB(  0, 255,   0, 255), packedIntARGBData.getColorARGB(1.0D, 1.0D));
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(-0.1D, +0.0D));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(+0.0D, -0.1D));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(+2.0D, +0.0D));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(+0.0D, +2.0D));
	}
	
	@Test
	public void testGetColorARGBFloatFloat() {
		final
		PackedIntARGBData packedIntARGBData = new PackedIntARGBData(2, 2);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0,   0), 0);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(255,   0, 255,   0), 1);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0,   0), 2);
		packedIntARGBData.setColorARGB(Color4I.toIntARGB(  0, 255,   0, 255), 3);
		
		assertEquals(Color4I.toIntARGB(64, 64, 64, 64), packedIntARGBData.getColorARGB(0.5F, 0.5F));
		
		assertEquals(Color4I.toIntARGB(  0, 0,   0, 0), packedIntARGBData.getColorARGB(0.0F, 0.5F));
		assertEquals(Color4I.toIntARGB(128, 0, 128, 0), packedIntARGBData.getColorARGB(0.5F, 0.0F));
		
		assertEquals(Color4I.toIntARGB(  0,   0,   0,   0), packedIntARGBData.getColorARGB(0.0F, 0.0F));
		assertEquals(Color4I.toIntARGB(255,   0, 255,   0), packedIntARGBData.getColorARGB(1.0F, 0.0F));
		assertEquals(Color4I.toIntARGB(  0,   0,   0,   0), packedIntARGBData.getColorARGB(0.0F, 1.0F));
		assertEquals(Color4I.toIntARGB(  0, 255,   0, 255), packedIntARGBData.getColorARGB(1.0F, 1.0F));
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(-0.1F, +0.0F));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(+0.0F, -0.1F));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(+2.0F, +0.0F));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(+0.0F, +2.0F));
	}
	
	@Test
	public void testGetColorARGBInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(-1));
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(+0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(+1));
	}
	
	@Test
	public void testGetColorARGBIntInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(-1, +0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(+0, -1));
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(+0, +0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(+1, +0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(+0, +1));
	}
	
	@Test
	public void testGetDataFactory() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData();
		
		final DataFactory dataFactory = packedIntARGBData.getDataFactory();
		
		assertTrue(dataFactory instanceof PackedIntARGBDataFactory);
	}
	
	@Test
	public void testGetResolution() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(2, 4);
		
		assertEquals(8, packedIntARGBData.getResolution());
	}
	
	@Test
	public void testGetResolutionX() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(2, 4);
		
		assertEquals(2, packedIntARGBData.getResolutionX());
	}
	
	@Test
	public void testGetResolutionY() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(2, 4);
		
		assertEquals(4, packedIntARGBData.getResolutionY());
	}
	
	@Test
	public void testHasChangeBegun() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertFalse(packedIntARGBData.hasChangeBegun());
		
		packedIntARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(packedIntARGBData.hasChangeBegun());
		
		packedIntARGBData.changeBegin();
		
		assertTrue(packedIntARGBData.hasChangeBegun());
		
		packedIntARGBData.changeEnd();
		
		assertFalse(packedIntARGBData.hasChangeBegun());
	}
	
	@Test
	public void testHashCode() {
		final PackedIntARGBData a = new PackedIntARGBData(100, 100);
		final PackedIntARGBData b = new PackedIntARGBData(100, 100);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
		
		a.setChangeHistoryEnabled(true);
		b.setChangeHistoryEnabled(true);
		
		a.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		b.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		
		a.undo();
		b.undo();
		
		a.redo();
		b.redo();
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
		
		a.setResolution(200, 200);
		b.setResolution(200, 200);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIsChangeHistoryEnabled() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertFalse(packedIntARGBData.isChangeHistoryEnabled());
		
		packedIntARGBData.setChangeHistoryEnabled(true);
		
		assertTrue(packedIntARGBData.isChangeHistoryEnabled());
		
		packedIntARGBData.setChangeHistoryEnabled(false);
		
		assertFalse(packedIntARGBData.isChangeHistoryEnabled());
	}
	
	@Test
	public void testPixelChangeConstructor() {
		final PixelChange pixelChange = new PixelChange(Color4I.BLACK_A_R_G_B, Color4I.WHITE_A_R_G_B, 1);
		
		assertEquals(Color4I.BLACK_A_R_G_B, pixelChange.getColorRedo());
		assertEquals(Color4I.WHITE_A_R_G_B, pixelChange.getColorUndo());
		
		assertEquals(1, pixelChange.getIndex());
		
		assertThrows(IllegalArgumentException.class, () -> new PixelChange(Color4I.BLACK_A_R_G_B, Color4I.BLACK_A_R_G_B, -1));
	}
	
	@Test
	public void testPixelChangeEquals() {
		final PixelChange a = new PixelChange(Color4I.BLACK_A_R_G_B, Color4I.WHITE_A_R_G_B, 1);
		final PixelChange b = new PixelChange(Color4I.BLACK_A_R_G_B, Color4I.WHITE_A_R_G_B, 1);
		final PixelChange c = new PixelChange(Color4I.BLACK_A_R_G_B, Color4I.WHITE_A_R_G_B, 2);
		final PixelChange d = new PixelChange(Color4I.BLACK_A_R_G_B, Color4I.BLACK_A_R_G_B, 1);
		final PixelChange e = new PixelChange(Color4I.WHITE_A_R_G_B, Color4I.WHITE_A_R_G_B, 1);
		final PixelChange f = null;
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(a, d);
		assertNotEquals(d, a);
		assertNotEquals(a, e);
		assertNotEquals(e, a);
		assertNotEquals(a, f);
		assertNotEquals(f, a);
	}
	
	@Test
	public void testPixelChangeHashCode() {
		final PixelChange a = new PixelChange(Color4I.BLACK_A_R_G_B, Color4I.WHITE_A_R_G_B, 1);
		final PixelChange b = new PixelChange(Color4I.BLACK_A_R_G_B, Color4I.WHITE_A_R_G_B, 1);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testPixelChangeRedoAndUndo() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		final Color4DData color4DData = new Color4DData(1, 1);
		
		final PixelChange pixelChange = new PixelChange(Color4I.BLACK_A_R_G_B, Color4I.WHITE_A_R_G_B, 0);
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		pixelChange.redo(packedIntARGBData);
		pixelChange.redo(color4DData);
		
		assertEquals(Color4I.BLACK_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		pixelChange.undo(packedIntARGBData);
		pixelChange.undo(color4DData);
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		assertThrows(NullPointerException.class, () -> pixelChange.redo(null));
		assertThrows(NullPointerException.class, () -> pixelChange.undo(null));
	}
	
	@Test
	public void testRedoAndUndo() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertFalse(packedIntARGBData.redo());
		assertFalse(packedIntARGBData.undo());
		
		packedIntARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(packedIntARGBData.redo());
		assertFalse(packedIntARGBData.undo());
		
		packedIntARGBData.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		
		assertTrue(packedIntARGBData.undo());
		assertTrue(packedIntARGBData.redo());
	}
	
	@Test
	public void testRotateDoubleBoolean() {
		final
		PackedIntARGBData packedIntARGBData = new PackedIntARGBData(5, 5, Color4D.WHITE);
		packedIntARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(packedIntARGBData.rotate(+0.0D, false));
		assertFalse(packedIntARGBData.rotate(-0.0D, false));
		
		assertFalse(packedIntARGBData.rotate(+360.0D, false));
		assertFalse(packedIntARGBData.rotate(-360.0D, false));
		
		assertFalse(packedIntARGBData.rotate(Math.toRadians(+360.0D), true));
		assertFalse(packedIntARGBData.rotate(Math.toRadians(-360.0D), true));
		
		assertEquals(5, packedIntARGBData.getResolutionX());
		assertEquals(5, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(0, 0));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(1, 0));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(2, 0));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(3, 0));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(4, 0));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(0, 1));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(1, 1));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(2, 1));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(3, 1));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(4, 1));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(0, 2));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(1, 2));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(2, 2));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(3, 2));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(4, 2));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(0, 3));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(1, 3));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(2, 3));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(3, 3));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(4, 3));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(0, 4));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(1, 4));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(2, 4));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(3, 4));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(4, 4));
		
		assertTrue(packedIntARGBData.rotate(45.0D, false));
		
		assertEquals(7, packedIntARGBData.getResolutionX());
		assertEquals(7, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(0, 0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(1, 0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(2, 0));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(4, 0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(5, 0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(6, 0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(0, 1));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(1, 1));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(2, 1));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 1));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(4, 1));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(5, 1));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(6, 1));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(0, 2));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(1, 2));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(2, 2));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 2));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(4, 2));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(5, 2));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(6, 2));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(0, 3));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(1, 3));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(2, 3));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 3));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(4, 3));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(5, 3));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(6, 3));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(0, 4));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(1, 4));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(2, 4));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 4));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(4, 4));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(5, 4));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(6, 4));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(0, 5));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(1, 5));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(2, 5));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 5));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(4, 5));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(5, 5));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(6, 5));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(0, 6));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(1, 6));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(2, 6));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 6));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(4, 6));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(5, 6));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(6, 6));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(5, packedIntARGBData.getResolutionX());
		assertEquals(5, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(0, 0));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(1, 0));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(2, 0));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(3, 0));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(4, 0));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(0, 1));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(1, 1));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(2, 1));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(3, 1));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(4, 1));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(0, 2));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(1, 2));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(2, 2));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(3, 2));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(4, 2));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(0, 3));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(1, 3));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(2, 3));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(3, 3));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(4, 3));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(0, 4));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(1, 4));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(2, 4));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(3, 4));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(4, 4));
		
		packedIntARGBData.changeBegin();
		
		assertTrue(packedIntARGBData.rotate(45.0D, false));
		
		packedIntARGBData.changeEnd();
		
		assertEquals(7, packedIntARGBData.getResolutionX());
		assertEquals(7, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(0, 0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(1, 0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(2, 0));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(4, 0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(5, 0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(6, 0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(0, 1));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(1, 1));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(2, 1));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 1));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(4, 1));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(5, 1));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(6, 1));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(0, 2));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(1, 2));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(2, 2));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 2));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(4, 2));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(5, 2));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(6, 2));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(0, 3));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(1, 3));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(2, 3));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 3));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(4, 3));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(5, 3));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(6, 3));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(0, 4));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(1, 4));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(2, 4));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 4));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(4, 4));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(5, 4));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(6, 4));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(0, 5));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(1, 5));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(2, 5));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 5));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(4, 5));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(5, 5));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(6, 5));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(0, 6));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(1, 6));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(2, 6));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 6));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(4, 6));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(5, 6));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(6, 6));
		
		assertTrue(packedIntARGBData.undo());
		
		packedIntARGBData.setChangeHistoryEnabled(false);
		
		assertEquals(5, packedIntARGBData.getResolutionX());
		assertEquals(5, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(0, 0));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(1, 0));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(2, 0));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(3, 0));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(4, 0));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(0, 1));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(1, 1));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(2, 1));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(3, 1));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(4, 1));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(0, 2));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(1, 2));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(2, 2));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(3, 2));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(4, 2));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(0, 3));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(1, 3));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(2, 3));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(3, 3));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(4, 3));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(0, 4));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(1, 4));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(2, 4));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(3, 4));
		assertEquals(Color4D.WHITE, packedIntARGBData.getColor4D(4, 4));
		
		assertTrue(packedIntARGBData.rotate(45.0D, false));
		
		assertEquals(7, packedIntARGBData.getResolutionX());
		assertEquals(7, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(0, 0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(1, 0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(2, 0));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(4, 0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(5, 0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(6, 0));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(0, 1));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(1, 1));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(2, 1));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 1));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(4, 1));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(5, 1));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(6, 1));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(0, 2));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(1, 2));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(2, 2));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 2));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(4, 2));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(5, 2));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(6, 2));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(0, 3));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(1, 3));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(2, 3));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 3));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(4, 3));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(5, 3));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(6, 3));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(0, 4));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(1, 4));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(2, 4));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 4));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(4, 4));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(5, 4));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(6, 4));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(0, 5));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(1, 5));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(2, 5));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 5));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(4, 5));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(5, 5));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(6, 5));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(0, 6));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(1, 6));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(2, 6));
		assertEquals(Color4D.WHITE,       packedIntARGBData.getColor4D(3, 6));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(4, 6));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(5, 6));
		assertEquals(Color4D.TRANSPARENT, packedIntARGBData.getColor4D(6, 6));
		
		assertFalse(packedIntARGBData.undo());
	}
	
	@Test
	public void testRotateFloatBoolean() {
		final
		PackedIntARGBData packedIntARGBData = new PackedIntARGBData(5, 5, Color4F.WHITE);
		packedIntARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(packedIntARGBData.rotate(+0.0F, false));
		assertFalse(packedIntARGBData.rotate(-0.0F, false));
		
		assertFalse(packedIntARGBData.rotate(+360.0F, false));
		assertFalse(packedIntARGBData.rotate(-360.0F, false));
		
		assertFalse(packedIntARGBData.rotate((float)(Math.toRadians(+360.0F)), true));
		assertFalse(packedIntARGBData.rotate((float)(Math.toRadians(-360.0F)), true));
		
		assertEquals(5, packedIntARGBData.getResolutionX());
		assertEquals(5, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(0, 0));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(1, 0));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(2, 0));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(3, 0));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(4, 0));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(0, 1));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(1, 1));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(2, 1));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(3, 1));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(4, 1));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(0, 2));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(1, 2));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(2, 2));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(3, 2));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(4, 2));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(0, 3));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(1, 3));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(2, 3));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(3, 3));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(4, 3));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(0, 4));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(1, 4));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(2, 4));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(3, 4));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(4, 4));
		
		assertTrue(packedIntARGBData.rotate(45.0F, false));
		
		assertEquals(7, packedIntARGBData.getResolutionX());
		assertEquals(7, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(0, 0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(1, 0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(2, 0));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(4, 0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(5, 0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(6, 0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(0, 1));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(1, 1));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(2, 1));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 1));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(4, 1));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(5, 1));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(6, 1));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(0, 2));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(1, 2));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(2, 2));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 2));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(4, 2));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(5, 2));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(6, 2));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(0, 3));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(1, 3));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(2, 3));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 3));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(4, 3));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(5, 3));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(6, 3));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(0, 4));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(1, 4));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(2, 4));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 4));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(4, 4));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(5, 4));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(6, 4));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(0, 5));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(1, 5));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(2, 5));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 5));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(4, 5));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(5, 5));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(6, 5));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(0, 6));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(1, 6));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(2, 6));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 6));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(4, 6));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(5, 6));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(6, 6));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(5, packedIntARGBData.getResolutionX());
		assertEquals(5, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(0, 0));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(1, 0));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(2, 0));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(3, 0));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(4, 0));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(0, 1));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(1, 1));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(2, 1));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(3, 1));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(4, 1));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(0, 2));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(1, 2));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(2, 2));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(3, 2));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(4, 2));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(0, 3));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(1, 3));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(2, 3));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(3, 3));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(4, 3));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(0, 4));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(1, 4));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(2, 4));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(3, 4));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(4, 4));
		
		packedIntARGBData.changeBegin();
		
		assertTrue(packedIntARGBData.rotate(45.0F, false));
		
		packedIntARGBData.changeEnd();
		
		assertEquals(7, packedIntARGBData.getResolutionX());
		assertEquals(7, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(0, 0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(1, 0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(2, 0));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(4, 0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(5, 0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(6, 0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(0, 1));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(1, 1));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(2, 1));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 1));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(4, 1));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(5, 1));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(6, 1));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(0, 2));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(1, 2));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(2, 2));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 2));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(4, 2));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(5, 2));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(6, 2));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(0, 3));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(1, 3));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(2, 3));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 3));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(4, 3));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(5, 3));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(6, 3));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(0, 4));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(1, 4));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(2, 4));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 4));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(4, 4));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(5, 4));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(6, 4));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(0, 5));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(1, 5));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(2, 5));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 5));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(4, 5));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(5, 5));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(6, 5));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(0, 6));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(1, 6));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(2, 6));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 6));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(4, 6));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(5, 6));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(6, 6));
		
		assertTrue(packedIntARGBData.undo());
		
		packedIntARGBData.setChangeHistoryEnabled(false);
		
		assertEquals(5, packedIntARGBData.getResolutionX());
		assertEquals(5, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(0, 0));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(1, 0));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(2, 0));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(3, 0));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(4, 0));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(0, 1));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(1, 1));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(2, 1));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(3, 1));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(4, 1));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(0, 2));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(1, 2));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(2, 2));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(3, 2));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(4, 2));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(0, 3));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(1, 3));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(2, 3));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(3, 3));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(4, 3));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(0, 4));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(1, 4));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(2, 4));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(3, 4));
		assertEquals(Color4F.WHITE, packedIntARGBData.getColor4F(4, 4));
		
		assertTrue(packedIntARGBData.rotate(45.0F, false));
		
		assertEquals(7, packedIntARGBData.getResolutionX());
		assertEquals(7, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(0, 0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(1, 0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(2, 0));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(4, 0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(5, 0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(6, 0));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(0, 1));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(1, 1));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(2, 1));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 1));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(4, 1));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(5, 1));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(6, 1));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(0, 2));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(1, 2));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(2, 2));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 2));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(4, 2));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(5, 2));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(6, 2));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(0, 3));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(1, 3));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(2, 3));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 3));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(4, 3));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(5, 3));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(6, 3));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(0, 4));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(1, 4));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(2, 4));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 4));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(4, 4));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(5, 4));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(6, 4));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(0, 5));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(1, 5));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(2, 5));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 5));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(4, 5));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(5, 5));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(6, 5));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(0, 6));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(1, 6));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(2, 6));
		assertEquals(Color4F.WHITE,       packedIntARGBData.getColor4F(3, 6));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(4, 6));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(5, 6));
		assertEquals(Color4F.TRANSPARENT, packedIntARGBData.getColor4F(6, 6));
		
		assertFalse(packedIntARGBData.undo());
	}
	
	@Test
	public void testSave() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		final File directory = new File(String.format("./generated/%s", Long.toString(System.currentTimeMillis())));
		
		final File fileA = new File(directory, "ColorARGBData.jpg");
		final File fileB = new File(directory, "ColorARGBData.png");
		
		final File fileC = new File("ColorARGBData.txt");
		final File fileD = new File("");
		
		assertTrue(packedIntARGBData.save(fileA, "jpg"));
		assertTrue(packedIntARGBData.save(fileB, "png"));
		
		assertFalse(packedIntARGBData.save(fileC, "txt"));
		assertFalse(packedIntARGBData.save(fileD, "png"));
		
		assertThrows(NullPointerException.class, () -> packedIntARGBData.save(new File("./generated/ColorARGBData.png"), null));
		assertThrows(NullPointerException.class, () -> packedIntARGBData.save(null, "png"));
		
		fileA.delete();
		fileB.delete();
		
		directory.delete();
	}
	
	@Test
	public void testScaleDoubleDouble() {
		final
		PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		packedIntARGBData.setColorARGB(Color4I.TRANSPARENT_A_R_G_B, 0);
		
		assertFalse(packedIntARGBData.scale(0.0D, 1.0D));
		assertFalse(packedIntARGBData.scale(1.0D, 0.0D));
		assertFalse(packedIntARGBData.scale(1.0D, 1.0D));
		
		assertEquals(1, packedIntARGBData.getResolutionX());
		assertEquals(1, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertTrue(packedIntARGBData.scale(1.0D, 2.0D));
		
		assertEquals(1, packedIntARGBData.getResolutionX());
		assertEquals(2, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(1));
	}
	
	@Test
	public void testScaleFloatFloat() {
		final
		PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		packedIntARGBData.setColorARGB(Color4I.TRANSPARENT_A_R_G_B, 0);
		
		assertFalse(packedIntARGBData.scale(0.0F, 1.0F));
		assertFalse(packedIntARGBData.scale(1.0F, 0.0F));
		assertFalse(packedIntARGBData.scale(1.0F, 1.0F));
		
		assertEquals(1, packedIntARGBData.getResolutionX());
		assertEquals(1, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertTrue(packedIntARGBData.scale(1.0F, 2.0F));
		
		assertEquals(1, packedIntARGBData.getResolutionX());
		assertEquals(2, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(1));
	}
	
	@Test
	public void testScaleIntInt() {
		final
		PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		packedIntARGBData.setColorARGB(Color4I.TRANSPARENT_A_R_G_B, 0);
		
		assertFalse(packedIntARGBData.scale(0, 1));
		assertFalse(packedIntARGBData.scale(1, 0));
		assertFalse(packedIntARGBData.scale(Integer.MAX_VALUE, 2));
		assertFalse(packedIntARGBData.scale(2, Integer.MAX_VALUE));
		assertFalse(packedIntARGBData.scale(1, 1));
		
		assertEquals(1, packedIntARGBData.getResolutionX());
		assertEquals(1, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertTrue(packedIntARGBData.scale(1, 2));
		
		assertEquals(1, packedIntARGBData.getResolutionX());
		assertEquals(2, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(1));
		
		assertFalse(packedIntARGBData.undo());
		
		assertEquals(1, packedIntARGBData.getResolutionX());
		assertEquals(2, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(1));
		
		packedIntARGBData.setChangeHistoryEnabled(true);
		
		assertTrue(packedIntARGBData.scale(2, 1));
		
		assertEquals(2, packedIntARGBData.getResolutionX());
		assertEquals(1, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(1));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(1, packedIntARGBData.getResolutionX());
		assertEquals(2, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(1));
		
		packedIntARGBData.changeBegin();
		
		assertTrue(packedIntARGBData.scale(2, 2));
		
		packedIntARGBData.changeEnd();
		
		assertEquals(2, packedIntARGBData.getResolutionX());
		assertEquals(2, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(1));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(2));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(3));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(1, packedIntARGBData.getResolutionX());
		assertEquals(2, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, packedIntARGBData.getColorARGB(1));
	}
	
	@Test
	public void testSetChangeHistoryEnabled() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertFalse(packedIntARGBData.setChangeHistoryEnabled(false));
		
		assertTrue(packedIntARGBData.setChangeHistoryEnabled(true));
		
		assertFalse(packedIntARGBData.setChangeHistoryEnabled(true));
		
		assertTrue(packedIntARGBData.setChangeHistoryEnabled(false));
	}
	
	@Test
	public void testSetColor3DColor3DInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertTrue(packedIntARGBData.setColor3D(Color3D.RED, 0));
		
		assertEquals(Color3D.RED, packedIntARGBData.getColor3D(0));
		
		assertThrows(NullPointerException.class, () -> packedIntARGBData.setColor3D(null, 0));
	}
	
	@Test
	public void testSetColor3DColor3DIntInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertTrue(packedIntARGBData.setColor3D(Color3D.RED, 0, 0));
		
		assertEquals(Color3D.RED, packedIntARGBData.getColor3D(0, 0));
		
		assertThrows(NullPointerException.class, () -> packedIntARGBData.setColor3D(null, 0, 0));
	}
	
	@Test
	public void testSetColor3FColor3FInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertTrue(packedIntARGBData.setColor3F(Color3F.RED, 0));
		
		assertEquals(Color3F.RED, packedIntARGBData.getColor3F(0));
		
		assertThrows(NullPointerException.class, () -> packedIntARGBData.setColor3F(null, 0));
	}
	
	@Test
	public void testSetColor3FColor3FIntInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertTrue(packedIntARGBData.setColor3F(Color3F.RED, 0, 0));
		
		assertEquals(Color3F.RED, packedIntARGBData.getColor3F(0, 0));
		
		assertThrows(NullPointerException.class, () -> packedIntARGBData.setColor3F(null, 0, 0));
	}
	
	@Test
	public void testSetColor4DColor4DInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertTrue(packedIntARGBData.setColor4D(Color4D.RED, 0));
		
		assertEquals(Color4D.RED, packedIntARGBData.getColor4D(0));
		
		assertThrows(NullPointerException.class, () -> packedIntARGBData.setColor4D(null, 0));
	}
	
	@Test
	public void testSetColor4DColor4DIntInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertTrue(packedIntARGBData.setColor4D(Color4D.RED, 0, 0));
		
		assertEquals(Color4D.RED, packedIntARGBData.getColor4D(0, 0));
		
		assertThrows(NullPointerException.class, () -> packedIntARGBData.setColor4D(null, 0, 0));
	}
	
	@Test
	public void testSetColor4FColor4FInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertTrue(packedIntARGBData.setColor4F(Color4F.RED, 0));
		
		assertEquals(Color4F.RED, packedIntARGBData.getColor4F(0));
		
		assertThrows(NullPointerException.class, () -> packedIntARGBData.setColor4F(null, 0));
	}
	
	@Test
	public void testSetColor4FColor4FIntInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertTrue(packedIntARGBData.setColor4F(Color4F.RED, 0, 0));
		
		assertEquals(Color4F.RED, packedIntARGBData.getColor4F(0, 0));
		
		assertThrows(NullPointerException.class, () -> packedIntARGBData.setColor4F(null, 0, 0));
	}
	
	@Test
	public void testSetColorARGBIntInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertTrue(packedIntARGBData.setColorARGB(Color4I.RED_A_R_G_B, 0));
		assertTrue(packedIntARGBData.setColorARGB(Color4I.WHITE_A_R_G_B, 0));
		
		packedIntARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(packedIntARGBData.setColorARGB(Color4I.RED_A_R_G_B, -1));
		assertFalse(packedIntARGBData.setColorARGB(Color4I.RED_A_R_G_B, +1));
		
		assertTrue(packedIntARGBData.setColorARGB(Color4I.WHITE_A_R_G_B, 0));
		
		assertFalse(packedIntARGBData.undo());
		assertFalse(packedIntARGBData.redo());
		
		assertTrue(packedIntARGBData.setColorARGB(Color4I.RED_A_R_G_B, 0));
		
		assertEquals(Color4I.RED_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertTrue(packedIntARGBData.redo());
		
		assertEquals(Color4I.RED_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertTrue(packedIntARGBData.changeBegin());
		assertTrue(packedIntARGBData.setColorARGB(Color4I.GREEN_A_R_G_B, 0));
		assertTrue(packedIntARGBData.changeEnd());
		
		assertEquals(Color4I.GREEN_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(Color4I.RED_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertTrue(packedIntARGBData.redo());
		
		assertEquals(Color4I.RED_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertTrue(packedIntARGBData.redo());
		
		assertEquals(Color4I.GREEN_A_R_G_B, packedIntARGBData.getColorARGB(0));
	}
	
	@Test
	public void testSetColorARGBIntIntInt() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertTrue(packedIntARGBData.setColorARGB(Color4I.RED_A_R_G_B, 0, 0));
		assertTrue(packedIntARGBData.setColorARGB(Color4I.WHITE_A_R_G_B, 0, 0));
		
		packedIntARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(packedIntARGBData.setColorARGB(Color4I.RED_A_R_G_B, -1, +0));
		assertFalse(packedIntARGBData.setColorARGB(Color4I.RED_A_R_G_B, +0, -1));
		assertFalse(packedIntARGBData.setColorARGB(Color4I.RED_A_R_G_B, +1, +0));
		assertFalse(packedIntARGBData.setColorARGB(Color4I.RED_A_R_G_B, +0, +1));
		
		assertTrue(packedIntARGBData.setColorARGB(Color4I.WHITE_A_R_G_B, 0, 0));
		
		assertFalse(packedIntARGBData.undo());
		assertFalse(packedIntARGBData.redo());
		
		assertTrue(packedIntARGBData.setColorARGB(Color4I.RED_A_R_G_B, 0, 0));
		
		assertEquals(Color4I.RED_A_R_G_B, packedIntARGBData.getColorARGB(0, 0));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0, 0));
		
		assertTrue(packedIntARGBData.redo());
		
		assertEquals(Color4I.RED_A_R_G_B, packedIntARGBData.getColorARGB(0, 0));
		
		assertTrue(packedIntARGBData.changeBegin());
		assertTrue(packedIntARGBData.setColorARGB(Color4I.GREEN_A_R_G_B, 0, 0));
		assertTrue(packedIntARGBData.changeEnd());
		
		assertEquals(Color4I.GREEN_A_R_G_B, packedIntARGBData.getColorARGB(0, 0));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(Color4I.RED_A_R_G_B, packedIntARGBData.getColorARGB(0, 0));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0, 0));
		
		assertTrue(packedIntARGBData.redo());
		
		assertEquals(Color4I.RED_A_R_G_B, packedIntARGBData.getColorARGB(0, 0));
		
		assertTrue(packedIntARGBData.redo());
		
		assertEquals(Color4I.GREEN_A_R_G_B, packedIntARGBData.getColorARGB(0, 0));
	}
	
	@Test
	public void testSetContent() {
		final PackedIntARGBData a = new PackedIntARGBData(1, 1);
		final PackedIntARGBData b = new PackedIntARGBData(1, 1);
		final PackedIntARGBData c = new PackedIntARGBData(2, 2);
		
		final Color4DData d = new Color4DData(3, 3, Color4D.GREEN);
		
		a.setColorARGB(Color4I.WHITE_A_R_G_B, 0);
		b.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		c.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		c.setColorARGB(Color4I.BLACK_A_R_G_B, 1);
		c.setColorARGB(Color4I.BLACK_A_R_G_B, 2);
		c.setColorARGB(Color4I.BLACK_A_R_G_B, 3);
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, a.getColorARGB(0));
		
		assertTrue(a.setContent(b));
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4I.BLACK_A_R_G_B, a.getColorARGB(0));
		
		assertFalse(a.undo());
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4I.BLACK_A_R_G_B, a.getColorARGB(0));
		
		a.setChangeHistoryEnabled(true);
		
		assertTrue(a.setContent(c));
		
		assertEquals(2, a.getResolutionX());
		assertEquals(2, a.getResolutionY());
		
		assertEquals(Color4I.BLACK_A_R_G_B, a.getColorARGB(0));
		assertEquals(Color4I.BLACK_A_R_G_B, a.getColorARGB(1));
		assertEquals(Color4I.BLACK_A_R_G_B, a.getColorARGB(2));
		assertEquals(Color4I.BLACK_A_R_G_B, a.getColorARGB(3));
		
		assertTrue(a.undo());
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4I.BLACK_A_R_G_B, a.getColorARGB(0));
		
		a.changeBegin();
		
		assertTrue(a.setContent(c));
		
		a.changeEnd();
		
		assertEquals(2, a.getResolutionX());
		assertEquals(2, a.getResolutionY());
		
		assertEquals(Color4I.BLACK_A_R_G_B, a.getColorARGB(0));
		assertEquals(Color4I.BLACK_A_R_G_B, a.getColorARGB(1));
		assertEquals(Color4I.BLACK_A_R_G_B, a.getColorARGB(2));
		assertEquals(Color4I.BLACK_A_R_G_B, a.getColorARGB(3));
		
		assertTrue(a.undo());
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4I.BLACK_A_R_G_B, a.getColorARGB(0));
		
		assertFalse(a.setContent(d));
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4I.BLACK_A_R_G_B, a.getColorARGB(0));
		
		assertFalse(a.undo());
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4I.BLACK_A_R_G_B, a.getColorARGB(0));
		
		assertThrows(NullPointerException.class, () -> a.setContent(null));
	}
	
	@Test
	public void testSetResolution() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertFalse(packedIntARGBData.setResolution(0, 1));
		assertFalse(packedIntARGBData.setResolution(1, 0));
		assertFalse(packedIntARGBData.setResolution(Integer.MAX_VALUE, 2));
		assertFalse(packedIntARGBData.setResolution(2, Integer.MAX_VALUE));
		assertFalse(packedIntARGBData.setResolution(1, 1));
		
		assertEquals(1, packedIntARGBData.getResolutionX());
		assertEquals(1, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertTrue(packedIntARGBData.setResolution(1, 2));
		
		assertEquals(1, packedIntARGBData.getResolutionX());
		assertEquals(2, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(1));
		
		assertFalse(packedIntARGBData.undo());
		
		assertEquals(1, packedIntARGBData.getResolutionX());
		assertEquals(2, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(1));
		
		packedIntARGBData.setChangeHistoryEnabled(true);
		
		assertTrue(packedIntARGBData.setResolution(2, 1));
		
		assertEquals(2, packedIntARGBData.getResolutionX());
		assertEquals(1, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(1));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(1, packedIntARGBData.getResolutionX());
		assertEquals(2, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(1));
		
		packedIntARGBData.changeBegin();
		
		assertTrue(packedIntARGBData.setResolution(2, 2));
		
		packedIntARGBData.changeEnd();
		
		assertEquals(2, packedIntARGBData.getResolutionX());
		assertEquals(2, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(1));
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(2));
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(3));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(1, packedIntARGBData.getResolutionX());
		assertEquals(2, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(1));
	}
	
	@Test
	public void testSetResolutionX() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(2, 2);
		
		assertFalse(packedIntARGBData.setResolutionX(0));
		assertFalse(packedIntARGBData.setResolutionX(Integer.MAX_VALUE));
		assertFalse(packedIntARGBData.setResolutionX(2));
		
		assertEquals(2, packedIntARGBData.getResolutionX());
		assertEquals(2, packedIntARGBData.getResolutionY());
	}
	
	@Test
	public void testSetResolutionY() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(2, 2);
		
		assertFalse(packedIntARGBData.setResolutionY(0));
		assertFalse(packedIntARGBData.setResolutionY(Integer.MAX_VALUE));
		assertFalse(packedIntARGBData.setResolutionY(2));
		
		assertEquals(2, packedIntARGBData.getResolutionX());
		assertEquals(2, packedIntARGBData.getResolutionY());
	}
	
	@Test
	public void testStateChangeConstructor() {
		final StateChange stateChange = new StateChange(1, 1, 1, 1, new int[] {Color4I.BLACK_A_R_G_B}, new int[] {Color4I.WHITE_A_R_G_B});
		
		assertArrayEquals(new int[] {Color4I.BLACK_A_R_G_B}, stateChange.getColorsRedo());
		assertArrayEquals(new int[] {Color4I.WHITE_A_R_G_B}, stateChange.getColorsUndo());
		
		assertEquals(1, stateChange.getResolutionXRedo());
		assertEquals(1, stateChange.getResolutionXUndo());
		assertEquals(1, stateChange.getResolutionYRedo());
		assertEquals(1, stateChange.getResolutionYUndo());
		
		assertThrows(NullPointerException.class, () -> new StateChange(1, 1, 1, 1, new int[] {Color4I.BLACK_A_R_G_B}, null));
		assertThrows(NullPointerException.class, () -> new StateChange(1, 1, 1, 1, null, new int[] {Color4I.WHITE_A_R_G_B}));
		
		assertThrows(IllegalArgumentException.class, () -> new StateChange(1, 1, 1, 0, new int[] {Color4I.BLACK_A_R_G_B}, new int[] {Color4I.WHITE_A_R_G_B}));
		assertThrows(IllegalArgumentException.class, () -> new StateChange(1, 1, 0, 1, new int[] {Color4I.BLACK_A_R_G_B}, new int[] {Color4I.WHITE_A_R_G_B}));
		assertThrows(IllegalArgumentException.class, () -> new StateChange(1, 0, 1, 1, new int[] {Color4I.BLACK_A_R_G_B}, new int[] {Color4I.WHITE_A_R_G_B}));
		assertThrows(IllegalArgumentException.class, () -> new StateChange(0, 1, 1, 1, new int[] {Color4I.BLACK_A_R_G_B}, new int[] {Color4I.WHITE_A_R_G_B}));
	}
	
	@Test
	public void testStateChangeEquals() {
		final StateChange a = new StateChange(1, 1, 1, 1, new int[] {Color4I.BLACK_A_R_G_B}, new int[] {Color4I.WHITE_A_R_G_B});
		final StateChange b = new StateChange(1, 1, 1, 1, new int[] {Color4I.BLACK_A_R_G_B}, new int[] {Color4I.WHITE_A_R_G_B});
		final StateChange c = new StateChange(1, 1, 1, 1, new int[] {Color4I.BLACK_A_R_G_B}, new int[] {Color4I.BLACK_A_R_G_B});
		final StateChange d = new StateChange(1, 1, 1, 1, new int[] {Color4I.WHITE_A_R_G_B}, new int[] {Color4I.WHITE_A_R_G_B});
		final StateChange e = new StateChange(1, 1, 1, 2, new int[] {Color4I.BLACK_A_R_G_B}, new int[] {Color4I.WHITE_A_R_G_B});
		final StateChange f = new StateChange(1, 1, 2, 1, new int[] {Color4I.BLACK_A_R_G_B}, new int[] {Color4I.WHITE_A_R_G_B});
		final StateChange g = new StateChange(1, 2, 1, 1, new int[] {Color4I.BLACK_A_R_G_B}, new int[] {Color4I.WHITE_A_R_G_B});
		final StateChange h = new StateChange(2, 1, 1, 1, new int[] {Color4I.BLACK_A_R_G_B}, new int[] {Color4I.WHITE_A_R_G_B});
		final StateChange i = null;
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(a, d);
		assertNotEquals(d, a);
		assertNotEquals(a, e);
		assertNotEquals(e, a);
		assertNotEquals(a, f);
		assertNotEquals(f, a);
		assertNotEquals(a, g);
		assertNotEquals(g, a);
		assertNotEquals(a, h);
		assertNotEquals(h, a);
		assertNotEquals(a, i);
		assertNotEquals(i, a);
	}
	
	@Test
	public void testStateChangeHashCode() {
		final StateChange a = new StateChange(1, 1, 1, 1, new int[] {Color4I.BLACK_A_R_G_B}, new int[] {Color4I.WHITE_A_R_G_B});
		final StateChange b = new StateChange(1, 1, 1, 1, new int[] {Color4I.BLACK_A_R_G_B}, new int[] {Color4I.WHITE_A_R_G_B});
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testStateChangeRedoAndUndo() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		final Color4DData color4DData = new Color4DData(1, 1);
		
		final StateChange stateChange = new StateChange(2, 1, 2, 1, new int[] {Color4I.BLACK_A_R_G_B, Color4I.BLACK_A_R_G_B, Color4I.BLACK_A_R_G_B, Color4I.BLACK_A_R_G_B}, new int[] {Color4I.WHITE_A_R_G_B});
		
		assertEquals(1, packedIntARGBData.getResolutionX());
		assertEquals(1, packedIntARGBData.getResolutionY());
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(1, color4DData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, color4DData.getColorARGB(0));
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		stateChange.redo(packedIntARGBData);
		stateChange.redo(color4DData);
		
		assertEquals(2, packedIntARGBData.getResolutionX());
		assertEquals(2, packedIntARGBData.getResolutionY());
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(1, color4DData.getResolutionY());
		
		assertEquals(Color4I.BLACK_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.BLACK_A_R_G_B, packedIntARGBData.getColorARGB(1));
		assertEquals(Color4I.BLACK_A_R_G_B, packedIntARGBData.getColorARGB(2));
		assertEquals(Color4I.BLACK_A_R_G_B, packedIntARGBData.getColorARGB(3));
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		stateChange.undo(packedIntARGBData);
		stateChange.undo(color4DData);
		
		assertEquals(1, packedIntARGBData.getResolutionX());
		assertEquals(1, packedIntARGBData.getResolutionY());
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(1, color4DData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		assertThrows(NullPointerException.class, () -> stateChange.redo(null));
		assertThrows(NullPointerException.class, () -> stateChange.undo(null));
	}
	
	@Test
	public void testSwap() {
		final
		PackedIntARGBData packedIntARGBData = new PackedIntARGBData(2, 1);
		packedIntARGBData.setColorARGB(Color4I.BLUE_A_R_G_B, 0);
		packedIntARGBData.setColorARGB(Color4I.CYAN_A_R_G_B, 1);
		
		assertFalse(packedIntARGBData.swap(-1, +0));
		assertFalse(packedIntARGBData.swap(+2, +0));
		assertFalse(packedIntARGBData.swap(+0, -1));
		assertFalse(packedIntARGBData.swap(+0, +2));
		
		assertTrue(packedIntARGBData.swap(0, 1));
		
		assertEquals(Color4I.CYAN_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.BLUE_A_R_G_B, packedIntARGBData.getColorARGB(1));
		
		assertFalse(packedIntARGBData.undo());
		
		packedIntARGBData.setChangeHistoryEnabled(true);
		
		assertTrue(packedIntARGBData.swap(0, 1));
		
		assertEquals(Color4I.BLUE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.CYAN_A_R_G_B, packedIntARGBData.getColorARGB(1));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(Color4I.CYAN_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.BLUE_A_R_G_B, packedIntARGBData.getColorARGB(1));
		
		packedIntARGBData.changeBegin();
		
		assertTrue(packedIntARGBData.swap(0, 1));
		
		packedIntARGBData.changeEnd();
		
		assertEquals(Color4I.BLUE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.CYAN_A_R_G_B, packedIntARGBData.getColorARGB(1));
		
		assertTrue(packedIntARGBData.undo());
		
		assertEquals(Color4I.CYAN_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.BLUE_A_R_G_B, packedIntARGBData.getColorARGB(1));
	}
	
	@Test
	public void testToBufferedImageBoolean() {
		final BufferedImage bufferedImageARGBExpected = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		final BufferedImage bufferedImageRGBExpected = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		
		bufferedImageARGBExpected.setRGB(0, 0, Color4I.toIntARGB(255, 255, 255, 255));
		bufferedImageRGBExpected.setRGB(0, 0, Color3I.toIntRGB(255, 255, 255));
		
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		final BufferedImage bufferedImageARGB = packedIntARGBData.toBufferedImage(false);
		final BufferedImage bufferedImageRGB = packedIntARGBData.toBufferedImage(true);
		
		assertEquals(bufferedImageARGBExpected.getHeight(), bufferedImageARGB.getHeight());
		assertEquals(bufferedImageARGBExpected.getType(), bufferedImageARGB.getType());
		assertEquals(bufferedImageARGBExpected.getWidth(), bufferedImageARGB.getWidth());
		
		assertEquals(bufferedImageRGBExpected.getHeight(), bufferedImageRGB.getHeight());
		assertEquals(bufferedImageRGBExpected.getType(), bufferedImageRGB.getType());
		assertEquals(bufferedImageRGBExpected.getWidth(), bufferedImageRGB.getWidth());
		
		for(int y = 0; y < bufferedImageARGBExpected.getHeight(); y++) {
			for(int x = 0; x < bufferedImageARGBExpected.getWidth(); x++) {
				assertEquals(bufferedImageARGBExpected.getRGB(x, y), bufferedImageARGB.getRGB(x, y));
			}
		}
		
		for(int y = 0; y < bufferedImageRGBExpected.getHeight(); y++) {
			for(int x = 0; x < bufferedImageRGBExpected.getWidth(); x++) {
				assertEquals(bufferedImageRGBExpected.getRGB(x, y), bufferedImageRGB.getRGB(x, y));
			}
		}
	}
	
	@Test
	public void testUpdatePixel() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		packedIntARGBData.updatePixel(Color4I.BLACK_A_R_G_B, 0);
		
		assertEquals(Color4I.BLACK_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		assertThrows(IllegalArgumentException.class, () -> packedIntARGBData.updatePixel(Color4I.BLACK_A_R_G_B, -1));
		assertThrows(IllegalArgumentException.class, () -> packedIntARGBData.updatePixel(Color4I.BLACK_A_R_G_B, +1));
	}
	
	@Test
	public void testUpdateState() {
		final PackedIntARGBData packedIntARGBData = new PackedIntARGBData(1, 1);
		
		assertEquals(1, packedIntARGBData.getResolutionX());
		assertEquals(1, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, packedIntARGBData.getColorARGB(0));
		
		packedIntARGBData.updateState(2, 2, new int[] {Color4I.BLACK_A_R_G_B, Color4I.BLACK_A_R_G_B, Color4I.BLACK_A_R_G_B, Color4I.BLACK_A_R_G_B});
		
		assertEquals(2, packedIntARGBData.getResolutionX());
		assertEquals(2, packedIntARGBData.getResolutionY());
		
		assertEquals(Color4I.BLACK_A_R_G_B, packedIntARGBData.getColorARGB(0));
		assertEquals(Color4I.BLACK_A_R_G_B, packedIntARGBData.getColorARGB(1));
		assertEquals(Color4I.BLACK_A_R_G_B, packedIntARGBData.getColorARGB(2));
		assertEquals(Color4I.BLACK_A_R_G_B, packedIntARGBData.getColorARGB(3));
		
		assertThrows(NullPointerException.class, () -> packedIntARGBData.updateState(1, 1, null));
		
		assertThrows(IllegalArgumentException.class, () -> packedIntARGBData.updateState(1, 0, new int[] {Color4I.BLACK_A_R_G_B}));
		assertThrows(IllegalArgumentException.class, () -> packedIntARGBData.updateState(0, 1, new int[] {Color4I.BLACK_A_R_G_B}));
	}
}