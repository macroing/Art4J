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

import org.macroing.img4j.color.Color3D;
import org.macroing.img4j.color.Color3F;
import org.macroing.img4j.color.Color3I;
import org.macroing.img4j.color.Color4D;
import org.macroing.img4j.color.Color4F;
import org.macroing.img4j.color.Color4I;
import org.macroing.img4j.data.ColorARGBData.PixelChange;
import org.macroing.img4j.data.ColorARGBData.StateChange;
import org.macroing.img4j.kernel.ConvolutionKernelND;
import org.macroing.img4j.kernel.ConvolutionKernelNF;

@SuppressWarnings("static-method")
public final class ColorARGBDataUnitTests {
	public ColorARGBDataUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testCache() {
		final
		ColorARGBData colorARGBData = new ColorARGBData(2, 2);
		colorARGBData.setColorARGB(Color4I.toIntARGB(0, 0, 0, 255), 0);
		colorARGBData.setColorARGB(Color4I.toIntARGB(0, 0, 0, 255), 1);
		colorARGBData.setColorARGB(Color4I.toIntARGB(0, 0, 0, 255), 2);
		colorARGBData.setColorARGB(Color4I.toIntARGB(0, 0, 0, 255), 3);
		
		assertEquals(0, colorARGBData.cache());
	}
	
	@Test
	public void testChangeAdd() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertFalse(colorARGBData.changeAdd(new PixelChange(Color4I.BLACK_A_R_G_B, Color4I.WHITE_A_R_G_B, 1)));
		
		colorARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(colorARGBData.changeAdd(new PixelChange(Color4I.BLACK_A_R_G_B, Color4I.WHITE_A_R_G_B, 1)));
		
		colorARGBData.changeBegin();
		
		assertTrue(colorARGBData.changeAdd(new PixelChange(Color4I.BLACK_A_R_G_B, Color4I.WHITE_A_R_G_B, 1)));
		
		assertThrows(NullPointerException.class, () -> colorARGBData.changeAdd(null));
	}
	
	@Test
	public void testChangeBegin() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertFalse(colorARGBData.changeBegin());
		
		colorARGBData.setChangeHistoryEnabled(true);
		
		assertTrue(colorARGBData.changeBegin());
		
		assertFalse(colorARGBData.changeBegin());
		
		colorARGBData.changeEnd();
		
		assertTrue(colorARGBData.changeBegin());
	}
	
	@Test
	public void testChangeEnd() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertFalse(colorARGBData.changeEnd());
		
		colorARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(colorARGBData.changeEnd());
		
		colorARGBData.changeBegin();
		
		assertTrue(colorARGBData.changeEnd());
		
		assertFalse(colorARGBData.changeEnd());
		
		colorARGBData.changeBegin();
		
		assertTrue(colorARGBData.changeEnd());
	}
	
	@Test
	public void testConstructor() {
		final ColorARGBData colorARGBData = new ColorARGBData();
		
		assertEquals(1024, colorARGBData.getResolutionX());
		assertEquals( 768, colorARGBData.getResolutionY());
		
		for(int y = 0; y < colorARGBData.getResolutionY(); y++) {
			for(int x = 0; x < colorARGBData.getResolutionX(); x++) {
				assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(x, y));
			}
		}
	}
	
	@Test
	public void testConstructorBufferedImage() {
		final ColorARGBData colorARGBData = new ColorARGBData(new BufferedImage(1024, 768, BufferedImage.TYPE_INT_ARGB));
		
		assertEquals(1024, colorARGBData.getResolutionX());
		assertEquals( 768, colorARGBData.getResolutionY());
		
		for(int y = 0; y < colorARGBData.getResolutionY(); y++) {
			for(int x = 0; x < colorARGBData.getResolutionX(); x++) {
				assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> new ColorARGBData((BufferedImage)(null)));
	}
	
	@Test
	public void testConstructorColorARGBData() {
		final
		ColorARGBData colorARGBData = new ColorARGBData(1024, 768);
		colorARGBData.setChangeHistoryEnabled(true);
		colorARGBData.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		colorARGBData.undo();
		colorARGBData.redo();
		
		final ColorARGBData colorARGBDataCopyA = new ColorARGBData(colorARGBData);
		
		assertEquals(colorARGBData, colorARGBDataCopyA);
		
		colorARGBData.undo();
		colorARGBData.setChangeHistoryEnabled(false);
		colorARGBData.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		
		final ColorARGBData colorARGBDataCopyB = new ColorARGBData(colorARGBData);
		
		assertEquals(colorARGBData, colorARGBDataCopyB);
		
		assertThrows(NullPointerException.class, () -> new ColorARGBData((ColorARGBData)(null)));
	}
	
	@Test
	public void testConstructorColorARGBDataBoolean() {
		final
		ColorARGBData colorARGBData = new ColorARGBData(1024, 768);
		colorARGBData.setChangeHistoryEnabled(true);
		colorARGBData.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		colorARGBData.undo();
		colorARGBData.redo();
		
		final ColorARGBData colorARGBDataCopyA = new ColorARGBData(colorARGBData, false);
		final ColorARGBData colorARGBDataCopyB = new ColorARGBData(colorARGBData, true);
		
		assertEquals(colorARGBData, colorARGBDataCopyA);
		
		assertNotEquals(colorARGBData, colorARGBDataCopyB);
		
		colorARGBData.undo();
		colorARGBData.setChangeHistoryEnabled(false);
		colorARGBData.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		
		final ColorARGBData colorARGBDataCopyC = new ColorARGBData(colorARGBData, false);
		final ColorARGBData colorARGBDataCopyD = new ColorARGBData(colorARGBData, true);
		
		assertEquals(colorARGBData, colorARGBDataCopyC);
		assertEquals(colorARGBData, colorARGBDataCopyD);
		
		assertThrows(NullPointerException.class, () -> new ColorARGBData((ColorARGBData)(null), false));
	}
	
	@Test
	public void testConstructorIntInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1024, 768);
		
		assertEquals(1024, colorARGBData.getResolutionX());
		assertEquals( 768, colorARGBData.getResolutionY());
		
		for(int y = 0; y < colorARGBData.getResolutionY(); y++) {
			for(int x = 0; x < colorARGBData.getResolutionX(); x++) {
				assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(x, y));
			}
		}
		
		assertThrows(IllegalArgumentException.class, () -> new ColorARGBData(1, 0));
		assertThrows(IllegalArgumentException.class, () -> new ColorARGBData(0, 1));
		assertThrows(IllegalArgumentException.class, () -> new ColorARGBData(Integer.MAX_VALUE, Integer.MAX_VALUE));
	}
	
	@Test
	public void testConstructorIntIntColor4D() {
		final ColorARGBData colorARGBData = new ColorARGBData(1024, 768, Color4D.RED);
		
		assertEquals(1024, colorARGBData.getResolutionX());
		assertEquals( 768, colorARGBData.getResolutionY());
		
		for(int y = 0; y < colorARGBData.getResolutionY(); y++) {
			for(int x = 0; x < colorARGBData.getResolutionX(); x++) {
				assertEquals(Color4I.RED_A_R_G_B, colorARGBData.getColorARGB(x, y));
			}
		}
		
		assertThrows(IllegalArgumentException.class, () -> new ColorARGBData(1, 0, Color4D.RED));
		assertThrows(IllegalArgumentException.class, () -> new ColorARGBData(0, 1, Color4D.RED));
		assertThrows(IllegalArgumentException.class, () -> new ColorARGBData(Integer.MAX_VALUE, Integer.MAX_VALUE, Color4D.RED));
		
		assertThrows(NullPointerException.class, () -> new ColorARGBData(1, 1, (Color4D)(null)));
	}
	
	@Test
	public void testConstructorIntIntColor4F() {
		final ColorARGBData colorARGBData = new ColorARGBData(1024, 768, Color4F.RED);
		
		assertEquals(1024, colorARGBData.getResolutionX());
		assertEquals( 768, colorARGBData.getResolutionY());
		
		for(int y = 0; y < colorARGBData.getResolutionY(); y++) {
			for(int x = 0; x < colorARGBData.getResolutionX(); x++) {
				assertEquals(Color4I.RED_A_R_G_B, colorARGBData.getColorARGB(x, y));
			}
		}
		
		assertThrows(IllegalArgumentException.class, () -> new ColorARGBData(1, 0, Color4F.RED));
		assertThrows(IllegalArgumentException.class, () -> new ColorARGBData(0, 1, Color4F.RED));
		assertThrows(IllegalArgumentException.class, () -> new ColorARGBData(Integer.MAX_VALUE, Integer.MAX_VALUE, Color4F.RED));
		
		assertThrows(NullPointerException.class, () -> new ColorARGBData(1, 1, (Color4F)(null)));
	}
	
	@Test
	public void testConvolveConvolutionKernelNDIntArray() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertFalse(colorARGBData.convolve(ConvolutionKernelND.IDENTITY_3, new int[0]));
		assertFalse(colorARGBData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		assertFalse(colorARGBData.undo());
		
		colorARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(colorARGBData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		assertFalse(colorARGBData.undo());
		
		colorARGBData.changeBegin();
		
		assertFalse(colorARGBData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		
		colorARGBData.changeEnd();
		
		assertFalse(colorARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		colorARGBData.setChangeHistoryEnabled(false);
		
		assertTrue(colorARGBData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {0}));
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertFalse(colorARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		colorARGBData.setChangeHistoryEnabled(true);
		
		assertTrue(colorARGBData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {0}));
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		colorARGBData.changeBegin();
		
		assertTrue(colorARGBData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {0}));
		
		colorARGBData.changeEnd();
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertThrows(NullPointerException.class, () -> colorARGBData.convolve(ConvolutionKernelND.IDENTITY_3, null));
		assertThrows(NullPointerException.class, () -> colorARGBData.convolve((ConvolutionKernelND)(null), new int[1]));
	}
	
	@Test
	public void testConvolveConvolutionKernelNFIntArray() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertFalse(colorARGBData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[0]));
		assertFalse(colorARGBData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		assertFalse(colorARGBData.undo());
		
		colorARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(colorARGBData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		assertFalse(colorARGBData.undo());
		
		colorARGBData.changeBegin();
		
		assertFalse(colorARGBData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		
		colorARGBData.changeEnd();
		
		assertFalse(colorARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		colorARGBData.setChangeHistoryEnabled(false);
		
		assertTrue(colorARGBData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {0}));
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertFalse(colorARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		colorARGBData.setChangeHistoryEnabled(true);
		
		assertTrue(colorARGBData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {0}));
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		colorARGBData.changeBegin();
		
		assertTrue(colorARGBData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {0}));
		
		colorARGBData.changeEnd();
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertThrows(NullPointerException.class, () -> colorARGBData.convolve(ConvolutionKernelNF.IDENTITY_3, null));
		assertThrows(NullPointerException.class, () -> colorARGBData.convolve((ConvolutionKernelNF)(null), new int[1]));
	}
	
	@Test
	public void testCopy() {
		final
		ColorARGBData colorARGBData = new ColorARGBData(1024, 768);
		colorARGBData.setChangeHistoryEnabled(true);
		colorARGBData.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		colorARGBData.undo();
		colorARGBData.redo();
		
		final Data dataCopyA = colorARGBData.copy();
		
		assertTrue(dataCopyA instanceof ColorARGBData);
		
		final ColorARGBData colorARGBDataCopyA = ColorARGBData.class.cast(dataCopyA);
		
		assertEquals(colorARGBData, colorARGBDataCopyA);
		
		colorARGBData.undo();
		colorARGBData.setChangeHistoryEnabled(false);
		colorARGBData.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		
		final Data dataCopyB = colorARGBData.copy();
		
		assertTrue(dataCopyB instanceof ColorARGBData);
		
		final ColorARGBData colorARGBDataCopyB = ColorARGBData.class.cast(dataCopyB);
		
		assertEquals(colorARGBData, colorARGBDataCopyB);
	}
	
	@Test
	public void testCopyBoolean() {
		final
		ColorARGBData colorARGBData = new ColorARGBData(1024, 768);
		colorARGBData.setChangeHistoryEnabled(true);
		colorARGBData.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		colorARGBData.undo();
		colorARGBData.redo();
		
		final Data dataCopyA = colorARGBData.copy(false);
		final Data dataCopyB = colorARGBData.copy(true);
		
		assertTrue(dataCopyA instanceof ColorARGBData);
		assertTrue(dataCopyB instanceof ColorARGBData);
		
		final ColorARGBData colorARGBDataCopyA = ColorARGBData.class.cast(dataCopyA);
		final ColorARGBData colorARGBDataCopyB = ColorARGBData.class.cast(dataCopyB);
		
		assertEquals(colorARGBData, colorARGBDataCopyA);
		
		assertNotEquals(colorARGBData, colorARGBDataCopyB);
		
		colorARGBData.undo();
		colorARGBData.setChangeHistoryEnabled(false);
		colorARGBData.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		
		final Data dataCopyC = colorARGBData.copy(false);
		final Data dataCopyD = colorARGBData.copy(true);
		
		assertTrue(dataCopyC instanceof ColorARGBData);
		assertTrue(dataCopyD instanceof ColorARGBData);
		
		final ColorARGBData colorARGBDataCopyC = ColorARGBData.class.cast(dataCopyC);
		final ColorARGBData colorARGBDataCopyD = ColorARGBData.class.cast(dataCopyD);
		
		assertEquals(colorARGBData, colorARGBDataCopyC);
		assertEquals(colorARGBData, colorARGBDataCopyD);
	}
	
	@Test
	public void testDrawConsumerGraphics2D() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		colorARGBData.draw(graphics2D -> {
			graphics2D.setColor(Color.RED);
			graphics2D.fillRect(0, 0, 1, 1);
		});
		
		assertEquals(Color4I.RED_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertFalse(colorARGBData.undo());
		
		assertEquals(Color4I.RED_A_R_G_B, colorARGBData.getColorARGB(0));
		
		colorARGBData.setChangeHistoryEnabled(true);
		colorARGBData.draw(graphics2D -> {
			graphics2D.setColor(Color.GREEN);
			graphics2D.fillRect(0, 0, 1, 1);
		});
		
		assertEquals(Color4I.GREEN_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(Color4I.RED_A_R_G_B, colorARGBData.getColorARGB(0));
		
		colorARGBData.changeBegin();
		colorARGBData.draw(graphics2D -> {
			graphics2D.setColor(Color.BLUE);
			graphics2D.fillRect(0, 0, 1, 1);
		});
		colorARGBData.changeEnd();
		
		assertEquals(Color4I.BLUE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(Color4I.RED_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertThrows(NullPointerException.class, () -> colorARGBData.draw(null));
	}
	
	@Test
	public void testEquals() {
		final Data a = new ColorARGBData(400, 400);
		final Data b = new ColorARGBData(400, 400);
		final Data c = new ColorARGBData(400, 200);
		final Data d = new ColorARGBData(200, 400);
		final Data e = new ColorARGBData(400, 400, Color4D.GREEN);
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
		ColorARGBData colorARGBData = new ColorARGBData(2, 2);
		colorARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0, 255), 0);
		colorARGBData.setColorARGB(Color4I.toIntARGB(255,   0, 255, 255), 1);
		colorARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0, 255), 2);
		colorARGBData.setColorARGB(Color4I.toIntARGB(  0, 255,   0, 255), 3);
		
		assertEquals(new Color3D(0.25D, 0.25D, 0.25D), colorARGBData.getColor3D(0.5D, 0.5D));
		
		assertEquals(new Color3D(0.0D, 0.0D, 0.0D), colorARGBData.getColor3D(0.0D, 0.5D));
		assertEquals(new Color3D(0.5D, 0.0D, 0.5D), colorARGBData.getColor3D(0.5D, 0.0D));
		
		assertEquals(new Color3D(0.0D, 0.0D, 0.0D), colorARGBData.getColor3D(0.0D, 0.0D));
		assertEquals(new Color3D(1.0D, 0.0D, 1.0D), colorARGBData.getColor3D(1.0D, 0.0D));
		assertEquals(new Color3D(0.0D, 0.0D, 0.0D), colorARGBData.getColor3D(0.0D, 1.0D));
		assertEquals(new Color3D(0.0D, 1.0D, 0.0D), colorARGBData.getColor3D(1.0D, 1.0D));
		
		assertEquals(Color3D.BLACK, colorARGBData.getColor3D(-0.1D, +0.0D));
		assertEquals(Color3D.BLACK, colorARGBData.getColor3D(+0.0D, -0.1D));
		assertEquals(Color3D.BLACK, colorARGBData.getColor3D(+2.0D, +0.0D));
		assertEquals(Color3D.BLACK, colorARGBData.getColor3D(+0.0D, +2.0D));
	}
	
	@Test
	public void testGetColor3DInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertEquals(Color3D.BLACK, colorARGBData.getColor3D(-1));
		assertEquals(Color3D.WHITE, colorARGBData.getColor3D(+0));
		assertEquals(Color3D.BLACK, colorARGBData.getColor3D(+1));
	}
	
	@Test
	public void testGetColor3DIntInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertEquals(Color3D.BLACK, colorARGBData.getColor3D(-1, +0));
		assertEquals(Color3D.BLACK, colorARGBData.getColor3D(+0, -1));
		assertEquals(Color3D.WHITE, colorARGBData.getColor3D(+0, +0));
		assertEquals(Color3D.BLACK, colorARGBData.getColor3D(+1, +0));
		assertEquals(Color3D.BLACK, colorARGBData.getColor3D(+0, +1));
	}
	
	@Test
	public void testGetColor3FFloatFloat() {
		final
		ColorARGBData colorARGBData = new ColorARGBData(2, 2);
		colorARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0, 255), 0);
		colorARGBData.setColorARGB(Color4I.toIntARGB(255,   0, 255, 255), 1);
		colorARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0, 255), 2);
		colorARGBData.setColorARGB(Color4I.toIntARGB(  0, 255,   0, 255), 3);
		
		assertEquals(new Color3F(0.25F, 0.25F, 0.25F), colorARGBData.getColor3F(0.5F, 0.5F));
		
		assertEquals(new Color3F(0.0F, 0.0F, 0.0F), colorARGBData.getColor3F(0.0F, 0.5F));
		assertEquals(new Color3F(0.5F, 0.0F, 0.5F), colorARGBData.getColor3F(0.5F, 0.0F));
		
		assertEquals(new Color3F(0.0F, 0.0F, 0.0F), colorARGBData.getColor3F(0.0F, 0.0F));
		assertEquals(new Color3F(1.0F, 0.0F, 1.0F), colorARGBData.getColor3F(1.0F, 0.0F));
		assertEquals(new Color3F(0.0F, 0.0F, 0.0F), colorARGBData.getColor3F(0.0F, 1.0F));
		assertEquals(new Color3F(0.0F, 1.0F, 0.0F), colorARGBData.getColor3F(1.0F, 1.0F));
		
		assertEquals(Color3F.BLACK, colorARGBData.getColor3F(-0.1F, +0.0F));
		assertEquals(Color3F.BLACK, colorARGBData.getColor3F(+0.0F, -0.1F));
		assertEquals(Color3F.BLACK, colorARGBData.getColor3F(+2.0F, +0.0F));
		assertEquals(Color3F.BLACK, colorARGBData.getColor3F(+0.0F, +2.0F));
	}
	
	@Test
	public void testGetColor3FInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertEquals(Color3F.BLACK, colorARGBData.getColor3F(-1));
		assertEquals(Color3F.WHITE, colorARGBData.getColor3F(+0));
		assertEquals(Color3F.BLACK, colorARGBData.getColor3F(+1));
	}
	
	@Test
	public void testGetColor3FIntInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertEquals(Color3F.BLACK, colorARGBData.getColor3F(-1, +0));
		assertEquals(Color3F.BLACK, colorARGBData.getColor3F(+0, -1));
		assertEquals(Color3F.WHITE, colorARGBData.getColor3F(+0, +0));
		assertEquals(Color3F.BLACK, colorARGBData.getColor3F(+1, +0));
		assertEquals(Color3F.BLACK, colorARGBData.getColor3F(+0, +1));
	}
	
	@Test
	public void testGetColor4DDoubleDouble() {
		final
		ColorARGBData colorARGBData = new ColorARGBData(2, 2);
		colorARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0,   0), 0);
		colorARGBData.setColorARGB(Color4I.toIntARGB(255,   0, 255,   0), 1);
		colorARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0,   0), 2);
		colorARGBData.setColorARGB(Color4I.toIntARGB(  0, 255,   0, 255), 3);
		
		assertEquals(new Color4D(0.25D, 0.25D, 0.25D, 0.25D), colorARGBData.getColor4D(0.5D, 0.5D));
		
		assertEquals(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), colorARGBData.getColor4D(0.0D, 0.5D));
		assertEquals(new Color4D(0.5D, 0.0D, 0.5D, 0.0D), colorARGBData.getColor4D(0.5D, 0.0D));
		
		assertEquals(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), colorARGBData.getColor4D(0.0D, 0.0D));
		assertEquals(new Color4D(1.0D, 0.0D, 1.0D, 0.0D), colorARGBData.getColor4D(1.0D, 0.0D));
		assertEquals(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), colorARGBData.getColor4D(0.0D, 1.0D));
		assertEquals(new Color4D(0.0D, 1.0D, 0.0D, 1.0D), colorARGBData.getColor4D(1.0D, 1.0D));
		
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(-0.1D, +0.0D));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(+0.0D, -0.1D));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(+2.0D, +0.0D));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(+0.0D, +2.0D));
	}
	
	@Test
	public void testGetColor4DInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(-1));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(+0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(+1));
	}
	
	@Test
	public void testGetColor4DIntInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(-1, +0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(+0, -1));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(+0, +0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(+1, +0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(+0, +1));
	}
	
	@Test
	public void testGetColor4FFloatFloat() {
		final
		ColorARGBData colorARGBData = new ColorARGBData(2, 2);
		colorARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0,   0), 0);
		colorARGBData.setColorARGB(Color4I.toIntARGB(255,   0, 255,   0), 1);
		colorARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0,   0), 2);
		colorARGBData.setColorARGB(Color4I.toIntARGB(  0, 255,   0, 255), 3);
		
		assertEquals(new Color4F(0.25F, 0.25F, 0.25F, 0.25F), colorARGBData.getColor4F(0.5F, 0.5F));
		
		assertEquals(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), colorARGBData.getColor4F(0.0F, 0.5F));
		assertEquals(new Color4F(0.5F, 0.0F, 0.5F, 0.0F), colorARGBData.getColor4F(0.5F, 0.0F));
		
		assertEquals(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), colorARGBData.getColor4F(0.0F, 0.0F));
		assertEquals(new Color4F(1.0F, 0.0F, 1.0F, 0.0F), colorARGBData.getColor4F(1.0F, 0.0F));
		assertEquals(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), colorARGBData.getColor4F(0.0F, 1.0F));
		assertEquals(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), colorARGBData.getColor4F(1.0F, 1.0F));
		
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(-0.1F, +0.0F));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(+0.0F, -0.1F));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(+2.0F, +0.0F));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(+0.0F, +2.0F));
	}
	
	@Test
	public void testGetColor4FInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(-1));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(+0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(+1));
	}
	
	@Test
	public void testGetColor4FIntInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(-1, +0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(+0, -1));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(+0, +0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(+1, +0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(+0, +1));
	}
	
	@Test
	public void testGetColorARGBDoubleDouble() {
		final
		ColorARGBData colorARGBData = new ColorARGBData(2, 2);
		colorARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0,   0), 0);
		colorARGBData.setColorARGB(Color4I.toIntARGB(255,   0, 255,   0), 1);
		colorARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0,   0), 2);
		colorARGBData.setColorARGB(Color4I.toIntARGB(  0, 255,   0, 255), 3);
		
		assertEquals(Color4I.toIntARGB(64, 64, 64, 64), colorARGBData.getColorARGB(0.5D, 0.5D));
		
		assertEquals(Color4I.toIntARGB(  0, 0,   0, 0), colorARGBData.getColorARGB(0.0D, 0.5D));
		assertEquals(Color4I.toIntARGB(128, 0, 128, 0), colorARGBData.getColorARGB(0.5D, 0.0D));
		
		assertEquals(Color4I.toIntARGB(  0,   0,   0,   0), colorARGBData.getColorARGB(0.0D, 0.0D));
		assertEquals(Color4I.toIntARGB(255,   0, 255,   0), colorARGBData.getColorARGB(1.0D, 0.0D));
		assertEquals(Color4I.toIntARGB(  0,   0,   0,   0), colorARGBData.getColorARGB(0.0D, 1.0D));
		assertEquals(Color4I.toIntARGB(  0, 255,   0, 255), colorARGBData.getColorARGB(1.0D, 1.0D));
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(-0.1D, +0.0D));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(+0.0D, -0.1D));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(+2.0D, +0.0D));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(+0.0D, +2.0D));
	}
	
	@Test
	public void testGetColorARGBFloatFloat() {
		final
		ColorARGBData colorARGBData = new ColorARGBData(2, 2);
		colorARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0,   0), 0);
		colorARGBData.setColorARGB(Color4I.toIntARGB(255,   0, 255,   0), 1);
		colorARGBData.setColorARGB(Color4I.toIntARGB(  0,   0,   0,   0), 2);
		colorARGBData.setColorARGB(Color4I.toIntARGB(  0, 255,   0, 255), 3);
		
		assertEquals(Color4I.toIntARGB(64, 64, 64, 64), colorARGBData.getColorARGB(0.5F, 0.5F));
		
		assertEquals(Color4I.toIntARGB(  0, 0,   0, 0), colorARGBData.getColorARGB(0.0F, 0.5F));
		assertEquals(Color4I.toIntARGB(128, 0, 128, 0), colorARGBData.getColorARGB(0.5F, 0.0F));
		
		assertEquals(Color4I.toIntARGB(  0,   0,   0,   0), colorARGBData.getColorARGB(0.0F, 0.0F));
		assertEquals(Color4I.toIntARGB(255,   0, 255,   0), colorARGBData.getColorARGB(1.0F, 0.0F));
		assertEquals(Color4I.toIntARGB(  0,   0,   0,   0), colorARGBData.getColorARGB(0.0F, 1.0F));
		assertEquals(Color4I.toIntARGB(  0, 255,   0, 255), colorARGBData.getColorARGB(1.0F, 1.0F));
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(-0.1F, +0.0F));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(+0.0F, -0.1F));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(+2.0F, +0.0F));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(+0.0F, +2.0F));
	}
	
	@Test
	public void testGetColorARGBInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(-1));
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(+0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(+1));
	}
	
	@Test
	public void testGetColorARGBIntInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(-1, +0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(+0, -1));
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(+0, +0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(+1, +0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(+0, +1));
	}
	
	@Test
	public void testGetDataFactory() {
		final ColorARGBData colorARGBData = new ColorARGBData();
		
		final DataFactory dataFactory = colorARGBData.getDataFactory();
		
		assertTrue(dataFactory instanceof ColorARGBDataFactory);
	}
	
	@Test
	public void testGetResolution() {
		final ColorARGBData colorARGBData = new ColorARGBData(2, 4);
		
		assertEquals(8, colorARGBData.getResolution());
	}
	
	@Test
	public void testGetResolutionX() {
		final ColorARGBData colorARGBData = new ColorARGBData(2, 4);
		
		assertEquals(2, colorARGBData.getResolutionX());
	}
	
	@Test
	public void testGetResolutionY() {
		final ColorARGBData colorARGBData = new ColorARGBData(2, 4);
		
		assertEquals(4, colorARGBData.getResolutionY());
	}
	
	@Test
	public void testHasChangeBegun() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertFalse(colorARGBData.hasChangeBegun());
		
		colorARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(colorARGBData.hasChangeBegun());
		
		colorARGBData.changeBegin();
		
		assertTrue(colorARGBData.hasChangeBegun());
		
		colorARGBData.changeEnd();
		
		assertFalse(colorARGBData.hasChangeBegun());
	}
	
	@Test
	public void testHashCode() {
		final ColorARGBData a = new ColorARGBData(100, 100);
		final ColorARGBData b = new ColorARGBData(100, 100);
		
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
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertFalse(colorARGBData.isChangeHistoryEnabled());
		
		colorARGBData.setChangeHistoryEnabled(true);
		
		assertTrue(colorARGBData.isChangeHistoryEnabled());
		
		colorARGBData.setChangeHistoryEnabled(false);
		
		assertFalse(colorARGBData.isChangeHistoryEnabled());
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
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		final Color4DData color4DData = new Color4DData(1, 1);
		
		final PixelChange pixelChange = new PixelChange(Color4I.BLACK_A_R_G_B, Color4I.WHITE_A_R_G_B, 0);
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		pixelChange.redo(colorARGBData);
		pixelChange.redo(color4DData);
		
		assertEquals(Color4I.BLACK_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		pixelChange.undo(colorARGBData);
		pixelChange.undo(color4DData);
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		assertThrows(NullPointerException.class, () -> pixelChange.redo(null));
		assertThrows(NullPointerException.class, () -> pixelChange.undo(null));
	}
	
	@Test
	public void testRedoAndUndo() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertFalse(colorARGBData.redo());
		assertFalse(colorARGBData.undo());
		
		colorARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(colorARGBData.redo());
		assertFalse(colorARGBData.undo());
		
		colorARGBData.setColorARGB(Color4I.BLACK_A_R_G_B, 0);
		
		assertTrue(colorARGBData.undo());
		assertTrue(colorARGBData.redo());
	}
	
	@Test
	public void testRotateDoubleBoolean() {
		final
		ColorARGBData colorARGBData = new ColorARGBData(5, 5, Color4D.WHITE);
		colorARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(colorARGBData.rotate(+0.0D, false));
		assertFalse(colorARGBData.rotate(-0.0D, false));
		
		assertFalse(colorARGBData.rotate(+360.0D, false));
		assertFalse(colorARGBData.rotate(-360.0D, false));
		
		assertFalse(colorARGBData.rotate(Math.toRadians(+360.0D), true));
		assertFalse(colorARGBData.rotate(Math.toRadians(-360.0D), true));
		
		assertEquals(5, colorARGBData.getResolutionX());
		assertEquals(5, colorARGBData.getResolutionY());
		
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(0, 0));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(1, 0));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(2, 0));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(3, 0));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(4, 0));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(0, 1));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(1, 1));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(2, 1));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(3, 1));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(4, 1));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(0, 2));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(1, 2));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(2, 2));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(3, 2));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(4, 2));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(0, 3));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(1, 3));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(2, 3));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(3, 3));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(4, 3));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(0, 4));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(1, 4));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(2, 4));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(3, 4));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(4, 4));
		
		assertTrue(colorARGBData.rotate(45.0D, false));
		
		assertEquals(7, colorARGBData.getResolutionX());
		assertEquals(7, colorARGBData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(0, 0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(1, 0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(2, 0));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(4, 0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(5, 0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(6, 0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(0, 1));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(1, 1));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(2, 1));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 1));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(4, 1));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(5, 1));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(6, 1));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(0, 2));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(1, 2));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(2, 2));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 2));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(4, 2));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(5, 2));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(6, 2));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(0, 3));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(1, 3));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(2, 3));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 3));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(4, 3));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(5, 3));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(6, 3));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(0, 4));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(1, 4));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(2, 4));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 4));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(4, 4));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(5, 4));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(6, 4));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(0, 5));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(1, 5));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(2, 5));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 5));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(4, 5));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(5, 5));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(6, 5));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(0, 6));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(1, 6));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(2, 6));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 6));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(4, 6));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(5, 6));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(6, 6));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(5, colorARGBData.getResolutionX());
		assertEquals(5, colorARGBData.getResolutionY());
		
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(0, 0));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(1, 0));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(2, 0));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(3, 0));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(4, 0));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(0, 1));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(1, 1));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(2, 1));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(3, 1));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(4, 1));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(0, 2));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(1, 2));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(2, 2));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(3, 2));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(4, 2));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(0, 3));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(1, 3));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(2, 3));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(3, 3));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(4, 3));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(0, 4));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(1, 4));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(2, 4));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(3, 4));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(4, 4));
		
		colorARGBData.changeBegin();
		
		assertTrue(colorARGBData.rotate(45.0D, false));
		
		colorARGBData.changeEnd();
		
		assertEquals(7, colorARGBData.getResolutionX());
		assertEquals(7, colorARGBData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(0, 0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(1, 0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(2, 0));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(4, 0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(5, 0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(6, 0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(0, 1));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(1, 1));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(2, 1));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 1));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(4, 1));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(5, 1));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(6, 1));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(0, 2));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(1, 2));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(2, 2));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 2));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(4, 2));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(5, 2));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(6, 2));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(0, 3));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(1, 3));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(2, 3));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 3));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(4, 3));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(5, 3));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(6, 3));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(0, 4));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(1, 4));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(2, 4));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 4));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(4, 4));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(5, 4));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(6, 4));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(0, 5));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(1, 5));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(2, 5));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 5));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(4, 5));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(5, 5));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(6, 5));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(0, 6));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(1, 6));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(2, 6));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 6));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(4, 6));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(5, 6));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(6, 6));
		
		assertTrue(colorARGBData.undo());
		
		colorARGBData.setChangeHistoryEnabled(false);
		
		assertEquals(5, colorARGBData.getResolutionX());
		assertEquals(5, colorARGBData.getResolutionY());
		
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(0, 0));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(1, 0));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(2, 0));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(3, 0));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(4, 0));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(0, 1));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(1, 1));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(2, 1));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(3, 1));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(4, 1));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(0, 2));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(1, 2));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(2, 2));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(3, 2));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(4, 2));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(0, 3));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(1, 3));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(2, 3));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(3, 3));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(4, 3));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(0, 4));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(1, 4));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(2, 4));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(3, 4));
		assertEquals(Color4D.WHITE, colorARGBData.getColor4D(4, 4));
		
		assertTrue(colorARGBData.rotate(45.0D, false));
		
		assertEquals(7, colorARGBData.getResolutionX());
		assertEquals(7, colorARGBData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(0, 0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(1, 0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(2, 0));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(4, 0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(5, 0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(6, 0));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(0, 1));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(1, 1));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(2, 1));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 1));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(4, 1));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(5, 1));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(6, 1));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(0, 2));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(1, 2));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(2, 2));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 2));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(4, 2));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(5, 2));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(6, 2));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(0, 3));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(1, 3));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(2, 3));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 3));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(4, 3));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(5, 3));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(6, 3));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(0, 4));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(1, 4));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(2, 4));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 4));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(4, 4));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(5, 4));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(6, 4));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(0, 5));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(1, 5));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(2, 5));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 5));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(4, 5));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(5, 5));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(6, 5));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(0, 6));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(1, 6));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(2, 6));
		assertEquals(Color4D.WHITE,       colorARGBData.getColor4D(3, 6));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(4, 6));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(5, 6));
		assertEquals(Color4D.TRANSPARENT, colorARGBData.getColor4D(6, 6));
		
		assertFalse(colorARGBData.undo());
	}
	
	@Test
	public void testRotateFloatBoolean() {
		final
		ColorARGBData colorARGBData = new ColorARGBData(5, 5, Color4F.WHITE);
		colorARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(colorARGBData.rotate(+0.0F, false));
		assertFalse(colorARGBData.rotate(-0.0F, false));
		
		assertFalse(colorARGBData.rotate(+360.0F, false));
		assertFalse(colorARGBData.rotate(-360.0F, false));
		
		assertFalse(colorARGBData.rotate((float)(Math.toRadians(+360.0F)), true));
		assertFalse(colorARGBData.rotate((float)(Math.toRadians(-360.0F)), true));
		
		assertEquals(5, colorARGBData.getResolutionX());
		assertEquals(5, colorARGBData.getResolutionY());
		
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(0, 0));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(1, 0));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(2, 0));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(3, 0));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(4, 0));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(0, 1));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(1, 1));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(2, 1));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(3, 1));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(4, 1));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(0, 2));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(1, 2));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(2, 2));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(3, 2));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(4, 2));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(0, 3));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(1, 3));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(2, 3));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(3, 3));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(4, 3));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(0, 4));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(1, 4));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(2, 4));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(3, 4));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(4, 4));
		
		assertTrue(colorARGBData.rotate(45.0F, false));
		
		assertEquals(7, colorARGBData.getResolutionX());
		assertEquals(7, colorARGBData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(0, 0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(1, 0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(2, 0));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(4, 0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(5, 0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(6, 0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(0, 1));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(1, 1));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(2, 1));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 1));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(4, 1));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(5, 1));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(6, 1));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(0, 2));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(1, 2));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(2, 2));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 2));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(4, 2));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(5, 2));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(6, 2));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(0, 3));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(1, 3));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(2, 3));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 3));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(4, 3));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(5, 3));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(6, 3));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(0, 4));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(1, 4));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(2, 4));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 4));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(4, 4));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(5, 4));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(6, 4));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(0, 5));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(1, 5));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(2, 5));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 5));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(4, 5));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(5, 5));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(6, 5));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(0, 6));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(1, 6));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(2, 6));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 6));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(4, 6));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(5, 6));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(6, 6));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(5, colorARGBData.getResolutionX());
		assertEquals(5, colorARGBData.getResolutionY());
		
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(0, 0));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(1, 0));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(2, 0));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(3, 0));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(4, 0));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(0, 1));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(1, 1));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(2, 1));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(3, 1));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(4, 1));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(0, 2));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(1, 2));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(2, 2));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(3, 2));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(4, 2));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(0, 3));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(1, 3));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(2, 3));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(3, 3));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(4, 3));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(0, 4));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(1, 4));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(2, 4));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(3, 4));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(4, 4));
		
		colorARGBData.changeBegin();
		
		assertTrue(colorARGBData.rotate(45.0F, false));
		
		colorARGBData.changeEnd();
		
		assertEquals(7, colorARGBData.getResolutionX());
		assertEquals(7, colorARGBData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(0, 0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(1, 0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(2, 0));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(4, 0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(5, 0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(6, 0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(0, 1));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(1, 1));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(2, 1));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 1));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(4, 1));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(5, 1));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(6, 1));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(0, 2));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(1, 2));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(2, 2));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 2));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(4, 2));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(5, 2));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(6, 2));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(0, 3));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(1, 3));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(2, 3));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 3));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(4, 3));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(5, 3));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(6, 3));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(0, 4));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(1, 4));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(2, 4));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 4));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(4, 4));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(5, 4));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(6, 4));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(0, 5));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(1, 5));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(2, 5));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 5));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(4, 5));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(5, 5));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(6, 5));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(0, 6));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(1, 6));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(2, 6));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 6));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(4, 6));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(5, 6));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(6, 6));
		
		assertTrue(colorARGBData.undo());
		
		colorARGBData.setChangeHistoryEnabled(false);
		
		assertEquals(5, colorARGBData.getResolutionX());
		assertEquals(5, colorARGBData.getResolutionY());
		
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(0, 0));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(1, 0));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(2, 0));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(3, 0));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(4, 0));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(0, 1));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(1, 1));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(2, 1));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(3, 1));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(4, 1));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(0, 2));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(1, 2));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(2, 2));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(3, 2));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(4, 2));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(0, 3));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(1, 3));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(2, 3));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(3, 3));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(4, 3));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(0, 4));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(1, 4));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(2, 4));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(3, 4));
		assertEquals(Color4F.WHITE, colorARGBData.getColor4F(4, 4));
		
		assertTrue(colorARGBData.rotate(45.0F, false));
		
		assertEquals(7, colorARGBData.getResolutionX());
		assertEquals(7, colorARGBData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(0, 0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(1, 0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(2, 0));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(4, 0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(5, 0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(6, 0));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(0, 1));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(1, 1));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(2, 1));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 1));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(4, 1));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(5, 1));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(6, 1));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(0, 2));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(1, 2));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(2, 2));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 2));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(4, 2));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(5, 2));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(6, 2));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(0, 3));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(1, 3));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(2, 3));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 3));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(4, 3));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(5, 3));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(6, 3));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(0, 4));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(1, 4));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(2, 4));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 4));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(4, 4));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(5, 4));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(6, 4));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(0, 5));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(1, 5));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(2, 5));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 5));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(4, 5));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(5, 5));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(6, 5));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(0, 6));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(1, 6));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(2, 6));
		assertEquals(Color4F.WHITE,       colorARGBData.getColor4F(3, 6));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(4, 6));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(5, 6));
		assertEquals(Color4F.TRANSPARENT, colorARGBData.getColor4F(6, 6));
		
		assertFalse(colorARGBData.undo());
	}
	
	@Test
	public void testSave() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		final File directory = new File(String.format("./generated/%s", Long.toString(System.currentTimeMillis())));
		
		final File fileA = new File(directory, "ColorARGBData.jpg");
		final File fileB = new File(directory, "ColorARGBData.png");
		
		final File fileC = new File("ColorARGBData.txt");
		final File fileD = new File("");
		
		assertTrue(colorARGBData.save(fileA, "jpg"));
		assertTrue(colorARGBData.save(fileB, "png"));
		
		assertFalse(colorARGBData.save(fileC, "txt"));
		assertFalse(colorARGBData.save(fileD, "png"));
		
		assertThrows(NullPointerException.class, () -> colorARGBData.save(new File("./generated/ColorARGBData.png"), null));
		assertThrows(NullPointerException.class, () -> colorARGBData.save(null, "png"));
		
		fileA.delete();
		fileB.delete();
		
		directory.delete();
	}
	
	@Test
	public void testScaleDoubleDouble() {
		final
		ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		colorARGBData.setColorARGB(Color4I.TRANSPARENT_A_R_G_B, 0);
		
		assertFalse(colorARGBData.scale(0.0D, 1.0D));
		assertFalse(colorARGBData.scale(1.0D, 0.0D));
		assertFalse(colorARGBData.scale(1.0D, 1.0D));
		
		assertEquals(1, colorARGBData.getResolutionX());
		assertEquals(1, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertTrue(colorARGBData.scale(1.0D, 2.0D));
		
		assertEquals(1, colorARGBData.getResolutionX());
		assertEquals(2, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(1));
	}
	
	@Test
	public void testScaleFloatFloat() {
		final
		ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		colorARGBData.setColorARGB(Color4I.TRANSPARENT_A_R_G_B, 0);
		
		assertFalse(colorARGBData.scale(0.0F, 1.0F));
		assertFalse(colorARGBData.scale(1.0F, 0.0F));
		assertFalse(colorARGBData.scale(1.0F, 1.0F));
		
		assertEquals(1, colorARGBData.getResolutionX());
		assertEquals(1, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertTrue(colorARGBData.scale(1.0F, 2.0F));
		
		assertEquals(1, colorARGBData.getResolutionX());
		assertEquals(2, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(1));
	}
	
	@Test
	public void testScaleIntInt() {
		final
		ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		colorARGBData.setColorARGB(Color4I.TRANSPARENT_A_R_G_B, 0);
		
		assertFalse(colorARGBData.scale(0, 1));
		assertFalse(colorARGBData.scale(1, 0));
		assertFalse(colorARGBData.scale(Integer.MAX_VALUE, 2));
		assertFalse(colorARGBData.scale(2, Integer.MAX_VALUE));
		assertFalse(colorARGBData.scale(1, 1));
		
		assertEquals(1, colorARGBData.getResolutionX());
		assertEquals(1, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertTrue(colorARGBData.scale(1, 2));
		
		assertEquals(1, colorARGBData.getResolutionX());
		assertEquals(2, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(1));
		
		assertFalse(colorARGBData.undo());
		
		assertEquals(1, colorARGBData.getResolutionX());
		assertEquals(2, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(1));
		
		colorARGBData.setChangeHistoryEnabled(true);
		
		assertTrue(colorARGBData.scale(2, 1));
		
		assertEquals(2, colorARGBData.getResolutionX());
		assertEquals(1, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(1));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(1, colorARGBData.getResolutionX());
		assertEquals(2, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(1));
		
		colorARGBData.changeBegin();
		
		assertTrue(colorARGBData.scale(2, 2));
		
		colorARGBData.changeEnd();
		
		assertEquals(2, colorARGBData.getResolutionX());
		assertEquals(2, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(1));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(2));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(3));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(1, colorARGBData.getResolutionX());
		assertEquals(2, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, colorARGBData.getColorARGB(1));
	}
	
	@Test
	public void testSetChangeHistoryEnabled() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertFalse(colorARGBData.setChangeHistoryEnabled(false));
		
		assertTrue(colorARGBData.setChangeHistoryEnabled(true));
		
		assertFalse(colorARGBData.setChangeHistoryEnabled(true));
		
		assertTrue(colorARGBData.setChangeHistoryEnabled(false));
	}
	
	@Test
	public void testSetColor3DColor3DInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertTrue(colorARGBData.setColor3D(Color3D.RED, 0));
		
		assertEquals(Color3D.RED, colorARGBData.getColor3D(0));
		
		assertThrows(NullPointerException.class, () -> colorARGBData.setColor3D(null, 0));
	}
	
	@Test
	public void testSetColor3DColor3DIntInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertTrue(colorARGBData.setColor3D(Color3D.RED, 0, 0));
		
		assertEquals(Color3D.RED, colorARGBData.getColor3D(0, 0));
		
		assertThrows(NullPointerException.class, () -> colorARGBData.setColor3D(null, 0, 0));
	}
	
	@Test
	public void testSetColor3FColor3FInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertTrue(colorARGBData.setColor3F(Color3F.RED, 0));
		
		assertEquals(Color3F.RED, colorARGBData.getColor3F(0));
		
		assertThrows(NullPointerException.class, () -> colorARGBData.setColor3F(null, 0));
	}
	
	@Test
	public void testSetColor3FColor3FIntInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertTrue(colorARGBData.setColor3F(Color3F.RED, 0, 0));
		
		assertEquals(Color3F.RED, colorARGBData.getColor3F(0, 0));
		
		assertThrows(NullPointerException.class, () -> colorARGBData.setColor3F(null, 0, 0));
	}
	
	@Test
	public void testSetColor4DColor4DInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertTrue(colorARGBData.setColor4D(Color4D.RED, 0));
		
		assertEquals(Color4D.RED, colorARGBData.getColor4D(0));
		
		assertThrows(NullPointerException.class, () -> colorARGBData.setColor4D(null, 0));
	}
	
	@Test
	public void testSetColor4DColor4DIntInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertTrue(colorARGBData.setColor4D(Color4D.RED, 0, 0));
		
		assertEquals(Color4D.RED, colorARGBData.getColor4D(0, 0));
		
		assertThrows(NullPointerException.class, () -> colorARGBData.setColor4D(null, 0, 0));
	}
	
	@Test
	public void testSetColor4FColor4FInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertTrue(colorARGBData.setColor4F(Color4F.RED, 0));
		
		assertEquals(Color4F.RED, colorARGBData.getColor4F(0));
		
		assertThrows(NullPointerException.class, () -> colorARGBData.setColor4F(null, 0));
	}
	
	@Test
	public void testSetColor4FColor4FIntInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertTrue(colorARGBData.setColor4F(Color4F.RED, 0, 0));
		
		assertEquals(Color4F.RED, colorARGBData.getColor4F(0, 0));
		
		assertThrows(NullPointerException.class, () -> colorARGBData.setColor4F(null, 0, 0));
	}
	
	@Test
	public void testSetColorARGBIntInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertTrue(colorARGBData.setColorARGB(Color4I.RED_A_R_G_B, 0));
		assertTrue(colorARGBData.setColorARGB(Color4I.WHITE_A_R_G_B, 0));
		
		colorARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(colorARGBData.setColorARGB(Color4I.RED_A_R_G_B, -1));
		assertFalse(colorARGBData.setColorARGB(Color4I.RED_A_R_G_B, +1));
		
		assertTrue(colorARGBData.setColorARGB(Color4I.WHITE_A_R_G_B, 0));
		
		assertFalse(colorARGBData.undo());
		assertFalse(colorARGBData.redo());
		
		assertTrue(colorARGBData.setColorARGB(Color4I.RED_A_R_G_B, 0));
		
		assertEquals(Color4I.RED_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertTrue(colorARGBData.redo());
		
		assertEquals(Color4I.RED_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertTrue(colorARGBData.changeBegin());
		assertTrue(colorARGBData.setColorARGB(Color4I.GREEN_A_R_G_B, 0));
		assertTrue(colorARGBData.changeEnd());
		
		assertEquals(Color4I.GREEN_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(Color4I.RED_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertTrue(colorARGBData.redo());
		
		assertEquals(Color4I.RED_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertTrue(colorARGBData.redo());
		
		assertEquals(Color4I.GREEN_A_R_G_B, colorARGBData.getColorARGB(0));
	}
	
	@Test
	public void testSetColorARGBIntIntInt() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertTrue(colorARGBData.setColorARGB(Color4I.RED_A_R_G_B, 0, 0));
		assertTrue(colorARGBData.setColorARGB(Color4I.WHITE_A_R_G_B, 0, 0));
		
		colorARGBData.setChangeHistoryEnabled(true);
		
		assertFalse(colorARGBData.setColorARGB(Color4I.RED_A_R_G_B, -1, +0));
		assertFalse(colorARGBData.setColorARGB(Color4I.RED_A_R_G_B, +0, -1));
		assertFalse(colorARGBData.setColorARGB(Color4I.RED_A_R_G_B, +1, +0));
		assertFalse(colorARGBData.setColorARGB(Color4I.RED_A_R_G_B, +0, +1));
		
		assertTrue(colorARGBData.setColorARGB(Color4I.WHITE_A_R_G_B, 0, 0));
		
		assertFalse(colorARGBData.undo());
		assertFalse(colorARGBData.redo());
		
		assertTrue(colorARGBData.setColorARGB(Color4I.RED_A_R_G_B, 0, 0));
		
		assertEquals(Color4I.RED_A_R_G_B, colorARGBData.getColorARGB(0, 0));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0, 0));
		
		assertTrue(colorARGBData.redo());
		
		assertEquals(Color4I.RED_A_R_G_B, colorARGBData.getColorARGB(0, 0));
		
		assertTrue(colorARGBData.changeBegin());
		assertTrue(colorARGBData.setColorARGB(Color4I.GREEN_A_R_G_B, 0, 0));
		assertTrue(colorARGBData.changeEnd());
		
		assertEquals(Color4I.GREEN_A_R_G_B, colorARGBData.getColorARGB(0, 0));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(Color4I.RED_A_R_G_B, colorARGBData.getColorARGB(0, 0));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0, 0));
		
		assertTrue(colorARGBData.redo());
		
		assertEquals(Color4I.RED_A_R_G_B, colorARGBData.getColorARGB(0, 0));
		
		assertTrue(colorARGBData.redo());
		
		assertEquals(Color4I.GREEN_A_R_G_B, colorARGBData.getColorARGB(0, 0));
	}
	
	@Test
	public void testSetContent() {
		final ColorARGBData a = new ColorARGBData(1, 1);
		final ColorARGBData b = new ColorARGBData(1, 1);
		final ColorARGBData c = new ColorARGBData(2, 2);
		
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
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertFalse(colorARGBData.setResolution(0, 1));
		assertFalse(colorARGBData.setResolution(1, 0));
		assertFalse(colorARGBData.setResolution(Integer.MAX_VALUE, 2));
		assertFalse(colorARGBData.setResolution(2, Integer.MAX_VALUE));
		assertFalse(colorARGBData.setResolution(1, 1));
		
		assertEquals(1, colorARGBData.getResolutionX());
		assertEquals(1, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertTrue(colorARGBData.setResolution(1, 2));
		
		assertEquals(1, colorARGBData.getResolutionX());
		assertEquals(2, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(1));
		
		assertFalse(colorARGBData.undo());
		
		assertEquals(1, colorARGBData.getResolutionX());
		assertEquals(2, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(1));
		
		colorARGBData.setChangeHistoryEnabled(true);
		
		assertTrue(colorARGBData.setResolution(2, 1));
		
		assertEquals(2, colorARGBData.getResolutionX());
		assertEquals(1, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(1));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(1, colorARGBData.getResolutionX());
		assertEquals(2, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(1));
		
		colorARGBData.changeBegin();
		
		assertTrue(colorARGBData.setResolution(2, 2));
		
		colorARGBData.changeEnd();
		
		assertEquals(2, colorARGBData.getResolutionX());
		assertEquals(2, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(1));
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(2));
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(3));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(1, colorARGBData.getResolutionX());
		assertEquals(2, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(1));
	}
	
	@Test
	public void testSetResolutionX() {
		final ColorARGBData colorARGBData = new ColorARGBData(2, 2);
		
		assertFalse(colorARGBData.setResolutionX(0));
		assertFalse(colorARGBData.setResolutionX(Integer.MAX_VALUE));
		assertFalse(colorARGBData.setResolutionX(2));
		
		assertEquals(2, colorARGBData.getResolutionX());
		assertEquals(2, colorARGBData.getResolutionY());
	}
	
	@Test
	public void testSetResolutionY() {
		final ColorARGBData colorARGBData = new ColorARGBData(2, 2);
		
		assertFalse(colorARGBData.setResolutionY(0));
		assertFalse(colorARGBData.setResolutionY(Integer.MAX_VALUE));
		assertFalse(colorARGBData.setResolutionY(2));
		
		assertEquals(2, colorARGBData.getResolutionX());
		assertEquals(2, colorARGBData.getResolutionY());
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
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		final Color4DData color4DData = new Color4DData(1, 1);
		
		final StateChange stateChange = new StateChange(2, 1, 2, 1, new int[] {Color4I.BLACK_A_R_G_B, Color4I.BLACK_A_R_G_B, Color4I.BLACK_A_R_G_B, Color4I.BLACK_A_R_G_B}, new int[] {Color4I.WHITE_A_R_G_B});
		
		assertEquals(1, colorARGBData.getResolutionX());
		assertEquals(1, colorARGBData.getResolutionY());
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(1, color4DData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, color4DData.getColorARGB(0));
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		stateChange.redo(colorARGBData);
		stateChange.redo(color4DData);
		
		assertEquals(2, colorARGBData.getResolutionX());
		assertEquals(2, colorARGBData.getResolutionY());
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(1, color4DData.getResolutionY());
		
		assertEquals(Color4I.BLACK_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.BLACK_A_R_G_B, colorARGBData.getColorARGB(1));
		assertEquals(Color4I.BLACK_A_R_G_B, colorARGBData.getColorARGB(2));
		assertEquals(Color4I.BLACK_A_R_G_B, colorARGBData.getColorARGB(3));
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		stateChange.undo(colorARGBData);
		stateChange.undo(color4DData);
		
		assertEquals(1, colorARGBData.getResolutionX());
		assertEquals(1, colorARGBData.getResolutionY());
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(1, color4DData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		assertThrows(NullPointerException.class, () -> stateChange.redo(null));
		assertThrows(NullPointerException.class, () -> stateChange.undo(null));
	}
	
	@Test
	public void testSwap() {
		final
		ColorARGBData colorARGBData = new ColorARGBData(2, 1);
		colorARGBData.setColorARGB(Color4I.BLUE_A_R_G_B, 0);
		colorARGBData.setColorARGB(Color4I.CYAN_A_R_G_B, 1);
		
		assertFalse(colorARGBData.swap(-1, +0));
		assertFalse(colorARGBData.swap(+2, +0));
		assertFalse(colorARGBData.swap(+0, -1));
		assertFalse(colorARGBData.swap(+0, +2));
		
		assertTrue(colorARGBData.swap(0, 1));
		
		assertEquals(Color4I.CYAN_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.BLUE_A_R_G_B, colorARGBData.getColorARGB(1));
		
		assertFalse(colorARGBData.undo());
		
		colorARGBData.setChangeHistoryEnabled(true);
		
		assertTrue(colorARGBData.swap(0, 1));
		
		assertEquals(Color4I.BLUE_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.CYAN_A_R_G_B, colorARGBData.getColorARGB(1));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(Color4I.CYAN_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.BLUE_A_R_G_B, colorARGBData.getColorARGB(1));
		
		colorARGBData.changeBegin();
		
		assertTrue(colorARGBData.swap(0, 1));
		
		colorARGBData.changeEnd();
		
		assertEquals(Color4I.BLUE_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.CYAN_A_R_G_B, colorARGBData.getColorARGB(1));
		
		assertTrue(colorARGBData.undo());
		
		assertEquals(Color4I.CYAN_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.BLUE_A_R_G_B, colorARGBData.getColorARGB(1));
	}
	
	@Test
	public void testToBufferedImageBoolean() {
		final BufferedImage bufferedImageARGBExpected = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		final BufferedImage bufferedImageRGBExpected = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		
		bufferedImageARGBExpected.setRGB(0, 0, Color4I.toIntARGB(255, 255, 255, 255));
		bufferedImageRGBExpected.setRGB(0, 0, Color3I.toIntRGB(255, 255, 255));
		
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		final BufferedImage bufferedImageARGB = colorARGBData.toBufferedImage(false);
		final BufferedImage bufferedImageRGB = colorARGBData.toBufferedImage(true);
		
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
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		colorARGBData.updatePixel(Color4I.BLACK_A_R_G_B, 0);
		
		assertEquals(Color4I.BLACK_A_R_G_B, colorARGBData.getColorARGB(0));
		
		assertThrows(IllegalArgumentException.class, () -> colorARGBData.updatePixel(Color4I.BLACK_A_R_G_B, -1));
		assertThrows(IllegalArgumentException.class, () -> colorARGBData.updatePixel(Color4I.BLACK_A_R_G_B, +1));
	}
	
	@Test
	public void testUpdateState() {
		final ColorARGBData colorARGBData = new ColorARGBData(1, 1);
		
		assertEquals(1, colorARGBData.getResolutionX());
		assertEquals(1, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, colorARGBData.getColorARGB(0));
		
		colorARGBData.updateState(2, 2, new int[] {Color4I.BLACK_A_R_G_B, Color4I.BLACK_A_R_G_B, Color4I.BLACK_A_R_G_B, Color4I.BLACK_A_R_G_B});
		
		assertEquals(2, colorARGBData.getResolutionX());
		assertEquals(2, colorARGBData.getResolutionY());
		
		assertEquals(Color4I.BLACK_A_R_G_B, colorARGBData.getColorARGB(0));
		assertEquals(Color4I.BLACK_A_R_G_B, colorARGBData.getColorARGB(1));
		assertEquals(Color4I.BLACK_A_R_G_B, colorARGBData.getColorARGB(2));
		assertEquals(Color4I.BLACK_A_R_G_B, colorARGBData.getColorARGB(3));
		
		assertThrows(NullPointerException.class, () -> colorARGBData.updateState(1, 1, null));
		
		assertThrows(IllegalArgumentException.class, () -> colorARGBData.updateState(1, 0, new int[] {Color4I.BLACK_A_R_G_B}));
		assertThrows(IllegalArgumentException.class, () -> colorARGBData.updateState(0, 1, new int[] {Color4I.BLACK_A_R_G_B}));
	}
}