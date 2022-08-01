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

import org.junit.jupiter.api.Test;

import org.macroing.img4j.color.Color3D;
import org.macroing.img4j.color.Color3F;
import org.macroing.img4j.color.Color3I;
import org.macroing.img4j.color.Color4D;
import org.macroing.img4j.color.Color4F;
import org.macroing.img4j.color.Color4I;
import org.macroing.img4j.data.Color4DData.PixelChange;
import org.macroing.img4j.data.Color4DData.StateChange;
import org.macroing.img4j.kernel.ConvolutionKernelND;
import org.macroing.img4j.kernel.ConvolutionKernelNF;

@SuppressWarnings("static-method")
public final class Color4DDataUnitTests {
	public Color4DDataUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testCache() {
//		Make sure a cached instance already exists:
		Color4D.getCached(new Color4D(0.0D, 0.0D, 0.0D));
		
		final
		Color4DData color4DData = new Color4DData(2, 2);
		color4DData.setColor4D(new Color4D(0.0D, 0.0D, 0.0D), 0);
		color4DData.setColor4D(new Color4D(0.0D, 0.0D, 0.0D), 1);
		color4DData.setColor4D(new Color4D(0.0D, 0.0D, 0.0D), 2);
		color4DData.setColor4D(new Color4D(0.0D, 0.0D, 0.0D), 3);
		
		assertEquals(4, color4DData.cache());
		assertEquals(0, color4DData.cache());
	}
	
	@Test
	public void testChangeAdd() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertFalse(color4DData.changeAdd(new PixelChange(Color4D.BLACK, Color4D.WHITE, 1)));
		
		color4DData.setChangeHistoryEnabled(true);
		
		assertFalse(color4DData.changeAdd(new PixelChange(Color4D.BLACK, Color4D.WHITE, 1)));
		
		color4DData.changeBegin();
		
		assertTrue(color4DData.changeAdd(new PixelChange(Color4D.BLACK, Color4D.WHITE, 1)));
		
		assertThrows(NullPointerException.class, () -> color4DData.changeAdd(null));
	}
	
	@Test
	public void testChangeBegin() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertFalse(color4DData.changeBegin());
		
		color4DData.setChangeHistoryEnabled(true);
		
		assertTrue(color4DData.changeBegin());
		
		assertFalse(color4DData.changeBegin());
		
		color4DData.changeEnd();
		
		assertTrue(color4DData.changeBegin());
	}
	
	@Test
	public void testChangeEnd() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertFalse(color4DData.changeEnd());
		
		color4DData.setChangeHistoryEnabled(true);
		
		assertFalse(color4DData.changeEnd());
		
		color4DData.changeBegin();
		
		assertTrue(color4DData.changeEnd());
		
		assertFalse(color4DData.changeEnd());
		
		color4DData.changeBegin();
		
		assertTrue(color4DData.changeEnd());
	}
	
	@Test
	public void testConstructor() {
		final Color4DData color4DData = new Color4DData();
		
		assertEquals(1024, color4DData.getResolutionX());
		assertEquals( 768, color4DData.getResolutionY());
		
		for(int y = 0; y < color4DData.getResolutionY(); y++) {
			for(int x = 0; x < color4DData.getResolutionX(); x++) {
				assertEquals(Color4D.WHITE, color4DData.getColor4D(x, y));
			}
		}
	}
	
	@Test
	public void testConstructorBufferedImage() {
		final Color4DData color4DData = new Color4DData(new BufferedImage(1024, 768, BufferedImage.TYPE_INT_ARGB));
		
		assertEquals(1024, color4DData.getResolutionX());
		assertEquals( 768, color4DData.getResolutionY());
		
		for(int y = 0; y < color4DData.getResolutionY(); y++) {
			for(int x = 0; x < color4DData.getResolutionX(); x++) {
				assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> new Color4DData((BufferedImage)(null)));
	}
	
	@Test
	public void testConstructorColor4DData() {
		final
		Color4DData color4DData = new Color4DData(1024, 768);
		color4DData.setChangeHistoryEnabled(true);
		color4DData.setColor4D(Color4D.BLACK, 0);
		color4DData.undo();
		color4DData.redo();
		
		final Color4DData color4DDataCopyA = new Color4DData(color4DData);
		
		assertEquals(color4DData, color4DDataCopyA);
		
		color4DData.undo();
		color4DData.setChangeHistoryEnabled(false);
		color4DData.setColor4D(Color4D.BLACK, 0);
		
		final Color4DData color4DDataCopyB = new Color4DData(color4DData);
		
		assertEquals(color4DData, color4DDataCopyB);
		
		assertThrows(NullPointerException.class, () -> new Color4DData((Color4DData)(null)));
	}
	
	@Test
	public void testConstructorColor4DDataBoolean() {
		final
		Color4DData color4DData = new Color4DData(1024, 768);
		color4DData.setChangeHistoryEnabled(true);
		color4DData.setColor4D(Color4D.BLACK, 0);
		color4DData.undo();
		color4DData.redo();
		
		final Color4DData color4DDataCopyA = new Color4DData(color4DData, false);
		final Color4DData color4DDataCopyB = new Color4DData(color4DData, true);
		
		assertEquals(color4DData, color4DDataCopyA);
		
		assertNotEquals(color4DData, color4DDataCopyB);
		
		color4DData.undo();
		color4DData.setChangeHistoryEnabled(false);
		color4DData.setColor4D(Color4D.BLACK, 0);
		
		final Color4DData color4DDataCopyC = new Color4DData(color4DData, false);
		final Color4DData color4DDataCopyD = new Color4DData(color4DData, true);
		
		assertEquals(color4DData, color4DDataCopyC);
		assertEquals(color4DData, color4DDataCopyD);
		
		assertThrows(NullPointerException.class, () -> new Color4DData((Color4DData)(null), false));
	}
	
	@Test
	public void testConstructorIntInt() {
		final Color4DData color4DData = new Color4DData(1024, 768);
		
		assertEquals(1024, color4DData.getResolutionX());
		assertEquals( 768, color4DData.getResolutionY());
		
		for(int y = 0; y < color4DData.getResolutionY(); y++) {
			for(int x = 0; x < color4DData.getResolutionX(); x++) {
				assertEquals(Color4D.WHITE, color4DData.getColor4D(x, y));
			}
		}
		
		assertThrows(IllegalArgumentException.class, () -> new Color4DData(1, 0));
		assertThrows(IllegalArgumentException.class, () -> new Color4DData(0, 1));
		assertThrows(IllegalArgumentException.class, () -> new Color4DData(Integer.MAX_VALUE, Integer.MAX_VALUE));
	}
	
	@Test
	public void testConstructorIntIntColor4D() {
		final Color4DData color4DData = new Color4DData(1024, 768, Color4D.RED);
		
		assertEquals(1024, color4DData.getResolutionX());
		assertEquals( 768, color4DData.getResolutionY());
		
		for(int y = 0; y < color4DData.getResolutionY(); y++) {
			for(int x = 0; x < color4DData.getResolutionX(); x++) {
				assertEquals(Color4D.RED, color4DData.getColor4D(x, y));
			}
		}
		
		assertThrows(IllegalArgumentException.class, () -> new Color4DData(1, 0, Color4D.RED));
		assertThrows(IllegalArgumentException.class, () -> new Color4DData(0, 1, Color4D.RED));
		assertThrows(IllegalArgumentException.class, () -> new Color4DData(Integer.MAX_VALUE, Integer.MAX_VALUE, Color4D.RED));
		
		assertThrows(NullPointerException.class, () -> new Color4DData(1, 1, (Color4D)(null)));
	}
	
	@Test
	public void testConstructorIntIntColor4F() {
		final Color4DData color4DData = new Color4DData(1024, 768, Color4F.RED);
		
		assertEquals(1024, color4DData.getResolutionX());
		assertEquals( 768, color4DData.getResolutionY());
		
		for(int y = 0; y < color4DData.getResolutionY(); y++) {
			for(int x = 0; x < color4DData.getResolutionX(); x++) {
				assertEquals(Color4D.RED, color4DData.getColor4D(x, y));
			}
		}
		
		assertThrows(IllegalArgumentException.class, () -> new Color4DData(1, 0, Color4F.RED));
		assertThrows(IllegalArgumentException.class, () -> new Color4DData(0, 1, Color4F.RED));
		assertThrows(IllegalArgumentException.class, () -> new Color4DData(Integer.MAX_VALUE, Integer.MAX_VALUE, Color4F.RED));
		
		assertThrows(NullPointerException.class, () -> new Color4DData(1, 1, (Color4F)(null)));
	}
	
	@Test
	public void testConvolveConvolutionKernelNDIntArray() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertFalse(color4DData.convolve(ConvolutionKernelND.IDENTITY_3, new int[0]));
		assertFalse(color4DData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		assertFalse(color4DData.undo());
		
		color4DData.setChangeHistoryEnabled(true);
		
		assertFalse(color4DData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		assertFalse(color4DData.undo());
		
		color4DData.changeBegin();
		
		assertFalse(color4DData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		
		color4DData.changeEnd();
		
		assertFalse(color4DData.undo());
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		color4DData.setChangeHistoryEnabled(false);
		
		assertTrue(color4DData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {0}));
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		assertFalse(color4DData.undo());
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		color4DData.setChangeHistoryEnabled(true);
		
		assertTrue(color4DData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {0}));
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		assertTrue(color4DData.undo());
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		color4DData.changeBegin();
		
		assertTrue(color4DData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {0}));
		
		color4DData.changeEnd();
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		assertTrue(color4DData.undo());
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		assertThrows(NullPointerException.class, () -> color4DData.convolve(ConvolutionKernelND.IDENTITY_3, null));
		assertThrows(NullPointerException.class, () -> color4DData.convolve((ConvolutionKernelND)(null), new int[1]));
	}
	
	@Test
	public void testConvolveConvolutionKernelNFIntArray() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertFalse(color4DData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[0]));
		assertFalse(color4DData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		assertFalse(color4DData.undo());
		
		color4DData.setChangeHistoryEnabled(true);
		
		assertFalse(color4DData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		assertFalse(color4DData.undo());
		
		color4DData.changeBegin();
		
		assertFalse(color4DData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		
		color4DData.changeEnd();
		
		assertFalse(color4DData.undo());
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		color4DData.setChangeHistoryEnabled(false);
		
		assertTrue(color4DData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {0}));
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		assertFalse(color4DData.undo());
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		color4DData.setChangeHistoryEnabled(true);
		
		assertTrue(color4DData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {0}));
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		assertTrue(color4DData.undo());
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		color4DData.changeBegin();
		
		assertTrue(color4DData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {0}));
		
		color4DData.changeEnd();
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		assertTrue(color4DData.undo());
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		assertThrows(NullPointerException.class, () -> color4DData.convolve(ConvolutionKernelNF.IDENTITY_3, null));
		assertThrows(NullPointerException.class, () -> color4DData.convolve((ConvolutionKernelNF)(null), new int[1]));
	}
	
	@Test
	public void testCopy() {
		final
		Color4DData color4DData = new Color4DData(1024, 768);
		color4DData.setChangeHistoryEnabled(true);
		color4DData.setColor4D(Color4D.BLACK, 0);
		color4DData.undo();
		color4DData.redo();
		
		final Data dataCopyA = color4DData.copy();
		
		assertTrue(dataCopyA instanceof Color4DData);
		
		final Color4DData color4DDataCopyA = Color4DData.class.cast(dataCopyA);
		
		assertEquals(color4DData, color4DDataCopyA);
		
		color4DData.undo();
		color4DData.setChangeHistoryEnabled(false);
		color4DData.setColor4D(Color4D.BLACK, 0);
		
		final Data dataCopyB = color4DData.copy();
		
		assertTrue(dataCopyB instanceof Color4DData);
		
		final Color4DData color4DDataCopyB = Color4DData.class.cast(dataCopyB);
		
		assertEquals(color4DData, color4DDataCopyB);
	}
	
	@Test
	public void testCopyBoolean() {
		final
		Color4DData color4DData = new Color4DData(1024, 768);
		color4DData.setChangeHistoryEnabled(true);
		color4DData.setColor4D(Color4D.BLACK, 0);
		color4DData.undo();
		color4DData.redo();
		
		final Data dataCopyA = color4DData.copy(false);
		final Data dataCopyB = color4DData.copy(true);
		
		assertTrue(dataCopyA instanceof Color4DData);
		assertTrue(dataCopyB instanceof Color4DData);
		
		final Color4DData color4DDataCopyA = Color4DData.class.cast(dataCopyA);
		final Color4DData color4DDataCopyB = Color4DData.class.cast(dataCopyB);
		
		assertEquals(color4DData, color4DDataCopyA);
		
		assertNotEquals(color4DData, color4DDataCopyB);
		
		color4DData.undo();
		color4DData.setChangeHistoryEnabled(false);
		color4DData.setColor4D(Color4D.BLACK, 0);
		
		final Data dataCopyC = color4DData.copy(false);
		final Data dataCopyD = color4DData.copy(true);
		
		assertTrue(dataCopyC instanceof Color4DData);
		assertTrue(dataCopyD instanceof Color4DData);
		
		final Color4DData color4DDataCopyC = Color4DData.class.cast(dataCopyC);
		final Color4DData color4DDataCopyD = Color4DData.class.cast(dataCopyD);
		
		assertEquals(color4DData, color4DDataCopyC);
		assertEquals(color4DData, color4DDataCopyD);
	}
	
	@Test
	public void testDrawConsumerGraphics2D() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		color4DData.draw(graphics2D -> {
			graphics2D.setColor(Color.RED);
			graphics2D.fillRect(0, 0, 1, 1);
		});
		
		assertEquals(Color4D.RED, color4DData.getColor4D(0));
		
		assertFalse(color4DData.undo());
		
		assertEquals(Color4D.RED, color4DData.getColor4D(0));
		
		color4DData.setChangeHistoryEnabled(true);
		color4DData.draw(graphics2D -> {
			graphics2D.setColor(Color.GREEN);
			graphics2D.fillRect(0, 0, 1, 1);
		});
		
		assertEquals(Color4D.GREEN, color4DData.getColor4D(0));
		
		assertTrue(color4DData.undo());
		
		assertEquals(Color4D.RED, color4DData.getColor4D(0));
		
		color4DData.changeBegin();
		color4DData.draw(graphics2D -> {
			graphics2D.setColor(Color.BLUE);
			graphics2D.fillRect(0, 0, 1, 1);
		});
		color4DData.changeEnd();
		
		assertEquals(Color4D.BLUE, color4DData.getColor4D(0));
		
		assertTrue(color4DData.undo());
		
		assertEquals(Color4D.RED, color4DData.getColor4D(0));
		
		assertThrows(NullPointerException.class, () -> color4DData.draw(null));
	}
	
	@Test
	public void testEquals() {
		final Data a = new Color4DData(400, 400);
		final Data b = new Color4DData(400, 400);
		final Data c = new Color4DData(400, 200);
		final Data d = new Color4DData(200, 400);
		final Data e = new Color4DData(400, 400, Color4D.GREEN);
		final Data f = new Color4FData(400, 400);
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
		
		a.setColor4D(Color4D.BLACK, 0);
		b.setColor4D(Color4D.BLACK, 0);
		
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
		
		a.setColor4D(Color4D.BLACK, 0);
		b.setColor4D(Color4D.BLACK, 0);
		
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
		Color4DData color4DData = new Color4DData(2, 2);
		color4DData.setColor4D(new Color4D(0.0D, 0.0D, 0.0D, 1.0D), 0);
		color4DData.setColor4D(new Color4D(1.0D, 0.0D, 1.0D, 1.0D), 1);
		color4DData.setColor4D(new Color4D(0.0D, 0.0D, 0.0D, 1.0D), 2);
		color4DData.setColor4D(new Color4D(0.0D, 1.0D, 0.0D, 1.0D), 3);
		
		assertEquals(new Color3D(0.25D, 0.25D, 0.25D), color4DData.getColor3D(0.5D, 0.5D));
		
		assertEquals(new Color3D(0.0D, 0.0D, 0.0D), color4DData.getColor3D(0.0D, 0.5D));
		assertEquals(new Color3D(0.5D, 0.0D, 0.5D), color4DData.getColor3D(0.5D, 0.0D));
		
		assertEquals(new Color3D(0.0D, 0.0D, 0.0D), color4DData.getColor3D(0.0D, 0.0D));
		assertEquals(new Color3D(1.0D, 0.0D, 1.0D), color4DData.getColor3D(1.0D, 0.0D));
		assertEquals(new Color3D(0.0D, 0.0D, 0.0D), color4DData.getColor3D(0.0D, 1.0D));
		assertEquals(new Color3D(0.0D, 1.0D, 0.0D), color4DData.getColor3D(1.0D, 1.0D));
		
		assertEquals(Color3D.BLACK, color4DData.getColor3D(-0.1D, +0.0D));
		assertEquals(Color3D.BLACK, color4DData.getColor3D(+0.0D, -0.1D));
		assertEquals(Color3D.BLACK, color4DData.getColor3D(+2.0D, +0.0D));
		assertEquals(Color3D.BLACK, color4DData.getColor3D(+0.0D, +2.0D));
	}
	
	@Test
	public void testGetColor3DInt() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertEquals(Color3D.BLACK, color4DData.getColor3D(-1));
		assertEquals(Color3D.WHITE, color4DData.getColor3D(+0));
		assertEquals(Color3D.BLACK, color4DData.getColor3D(+1));
	}
	
	@Test
	public void testGetColor3DIntInt() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertEquals(Color3D.BLACK, color4DData.getColor3D(-1, +0));
		assertEquals(Color3D.BLACK, color4DData.getColor3D(+0, -1));
		assertEquals(Color3D.WHITE, color4DData.getColor3D(+0, +0));
		assertEquals(Color3D.BLACK, color4DData.getColor3D(+1, +0));
		assertEquals(Color3D.BLACK, color4DData.getColor3D(+0, +1));
	}
	
	@Test
	public void testGetColor3FFloatFloat() {
		final
		Color4DData color4DData = new Color4DData(2, 2);
		color4DData.setColor4D(new Color4D(0.0D, 0.0D, 0.0D, 1.0D), 0);
		color4DData.setColor4D(new Color4D(1.0D, 0.0D, 1.0D, 1.0D), 1);
		color4DData.setColor4D(new Color4D(0.0D, 0.0D, 0.0D, 1.0D), 2);
		color4DData.setColor4D(new Color4D(0.0D, 1.0D, 0.0D, 1.0D), 3);
		
		assertEquals(new Color3F(0.25F, 0.25F, 0.25F), color4DData.getColor3F(0.5F, 0.5F));
		
		assertEquals(new Color3F(0.0F, 0.0F, 0.0F), color4DData.getColor3F(0.0F, 0.5F));
		assertEquals(new Color3F(0.5F, 0.0F, 0.5F), color4DData.getColor3F(0.5F, 0.0F));
		
		assertEquals(new Color3F(0.0F, 0.0F, 0.0F), color4DData.getColor3F(0.0F, 0.0F));
		assertEquals(new Color3F(1.0F, 0.0F, 1.0F), color4DData.getColor3F(1.0F, 0.0F));
		assertEquals(new Color3F(0.0F, 0.0F, 0.0F), color4DData.getColor3F(0.0F, 1.0F));
		assertEquals(new Color3F(0.0F, 1.0F, 0.0F), color4DData.getColor3F(1.0F, 1.0F));
		
		assertEquals(Color3F.BLACK, color4DData.getColor3F(-0.1F, +0.0F));
		assertEquals(Color3F.BLACK, color4DData.getColor3F(+0.0F, -0.1F));
		assertEquals(Color3F.BLACK, color4DData.getColor3F(+2.0F, +0.0F));
		assertEquals(Color3F.BLACK, color4DData.getColor3F(+0.0F, +2.0F));
	}
	
	@Test
	public void testGetColor3FInt() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertEquals(Color3F.BLACK, color4DData.getColor3F(-1));
		assertEquals(Color3F.WHITE, color4DData.getColor3F(+0));
		assertEquals(Color3F.BLACK, color4DData.getColor3F(+1));
	}
	
	@Test
	public void testGetColor3FIntInt() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertEquals(Color3F.BLACK, color4DData.getColor3F(-1, +0));
		assertEquals(Color3F.BLACK, color4DData.getColor3F(+0, -1));
		assertEquals(Color3F.WHITE, color4DData.getColor3F(+0, +0));
		assertEquals(Color3F.BLACK, color4DData.getColor3F(+1, +0));
		assertEquals(Color3F.BLACK, color4DData.getColor3F(+0, +1));
	}
	
	@Test
	public void testGetColor4DDoubleDouble() {
		final
		Color4DData color4DData = new Color4DData(2, 2);
		color4DData.setColor4D(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), 0);
		color4DData.setColor4D(new Color4D(1.0D, 0.0D, 1.0D, 0.0D), 1);
		color4DData.setColor4D(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), 2);
		color4DData.setColor4D(new Color4D(0.0D, 1.0D, 0.0D, 1.0D), 3);
		
		assertEquals(new Color4D(0.25D, 0.25D, 0.25D, 0.25D), color4DData.getColor4D(0.5D, 0.5D));
		
		assertEquals(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), color4DData.getColor4D(0.0D, 0.5D));
		assertEquals(new Color4D(0.5D, 0.0D, 0.5D, 0.0D), color4DData.getColor4D(0.5D, 0.0D));
		
		assertEquals(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), color4DData.getColor4D(0.0D, 0.0D));
		assertEquals(new Color4D(1.0D, 0.0D, 1.0D, 0.0D), color4DData.getColor4D(1.0D, 0.0D));
		assertEquals(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), color4DData.getColor4D(0.0D, 1.0D));
		assertEquals(new Color4D(0.0D, 1.0D, 0.0D, 1.0D), color4DData.getColor4D(1.0D, 1.0D));
		
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(-0.1D, +0.0D));
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(+0.0D, -0.1D));
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(+2.0D, +0.0D));
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(+0.0D, +2.0D));
	}
	
	@Test
	public void testGetColor4DInt() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(-1));
		assertEquals(Color4D.WHITE, color4DData.getColor4D(+0));
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(+1));
	}
	
	@Test
	public void testGetColor4DIntInt() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(-1, +0));
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(+0, -1));
		assertEquals(Color4D.WHITE, color4DData.getColor4D(+0, +0));
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(+1, +0));
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(+0, +1));
	}
	
	@Test
	public void testGetColor4FFloatFloat() {
		final
		Color4DData color4DData = new Color4DData(2, 2);
		color4DData.setColor4D(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), 0);
		color4DData.setColor4D(new Color4D(1.0D, 0.0D, 1.0D, 0.0D), 1);
		color4DData.setColor4D(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), 2);
		color4DData.setColor4D(new Color4D(0.0D, 1.0D, 0.0D, 1.0D), 3);
		
		assertEquals(new Color4F(0.25F, 0.25F, 0.25F, 0.25F), color4DData.getColor4F(0.5F, 0.5F));
		
		assertEquals(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), color4DData.getColor4F(0.0F, 0.5F));
		assertEquals(new Color4F(0.5F, 0.0F, 0.5F, 0.0F), color4DData.getColor4F(0.5F, 0.0F));
		
		assertEquals(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), color4DData.getColor4F(0.0F, 0.0F));
		assertEquals(new Color4F(1.0F, 0.0F, 1.0F, 0.0F), color4DData.getColor4F(1.0F, 0.0F));
		assertEquals(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), color4DData.getColor4F(0.0F, 1.0F));
		assertEquals(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), color4DData.getColor4F(1.0F, 1.0F));
		
		assertEquals(Color4F.TRANSPARENT, color4DData.getColor4F(-0.1F, +0.0F));
		assertEquals(Color4F.TRANSPARENT, color4DData.getColor4F(+0.0F, -0.1F));
		assertEquals(Color4F.TRANSPARENT, color4DData.getColor4F(+2.0F, +0.0F));
		assertEquals(Color4F.TRANSPARENT, color4DData.getColor4F(+0.0F, +2.0F));
	}
	
	@Test
	public void testGetColor4FInt() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertEquals(Color4F.TRANSPARENT, color4DData.getColor4F(-1));
		assertEquals(Color4F.WHITE, color4DData.getColor4F(+0));
		assertEquals(Color4F.TRANSPARENT, color4DData.getColor4F(+1));
	}
	
	@Test
	public void testGetColor4FIntInt() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertEquals(Color4F.TRANSPARENT, color4DData.getColor4F(-1, +0));
		assertEquals(Color4F.TRANSPARENT, color4DData.getColor4F(+0, -1));
		assertEquals(Color4F.WHITE, color4DData.getColor4F(+0, +0));
		assertEquals(Color4F.TRANSPARENT, color4DData.getColor4F(+1, +0));
		assertEquals(Color4F.TRANSPARENT, color4DData.getColor4F(+0, +1));
	}
	
	@Test
	public void testGetColorARGBDoubleDouble() {
		final
		Color4DData color4DData = new Color4DData(2, 2);
		color4DData.setColor4D(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), 0);
		color4DData.setColor4D(new Color4D(1.0D, 0.0D, 1.0D, 0.0D), 1);
		color4DData.setColor4D(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), 2);
		color4DData.setColor4D(new Color4D(0.0D, 1.0D, 0.0D, 1.0D), 3);
		
		assertEquals(Color4I.toIntARGB(64, 64, 64, 64), color4DData.getColorARGB(0.5D, 0.5D));
		
		assertEquals(Color4I.toIntARGB(  0, 0,   0, 0), color4DData.getColorARGB(0.0D, 0.5D));
		assertEquals(Color4I.toIntARGB(128, 0, 128, 0), color4DData.getColorARGB(0.5D, 0.0D));
		
		assertEquals(Color4I.toIntARGB(  0,   0,   0,   0), color4DData.getColorARGB(0.0D, 0.0D));
		assertEquals(Color4I.toIntARGB(255,   0, 255,   0), color4DData.getColorARGB(1.0D, 0.0D));
		assertEquals(Color4I.toIntARGB(  0,   0,   0,   0), color4DData.getColorARGB(0.0D, 1.0D));
		assertEquals(Color4I.toIntARGB(  0, 255,   0, 255), color4DData.getColorARGB(1.0D, 1.0D));
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4DData.getColorARGB(-0.1D, +0.0D));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4DData.getColorARGB(+0.0D, -0.1D));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4DData.getColorARGB(+2.0D, +0.0D));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4DData.getColorARGB(+0.0D, +2.0D));
	}
	
	@Test
	public void testGetColorARGBFloatFloat() {
		final
		Color4DData color4DData = new Color4DData(2, 2);
		color4DData.setColor4D(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), 0);
		color4DData.setColor4D(new Color4D(1.0D, 0.0D, 1.0D, 0.0D), 1);
		color4DData.setColor4D(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), 2);
		color4DData.setColor4D(new Color4D(0.0D, 1.0D, 0.0D, 1.0D), 3);
		
		assertEquals(Color4I.toIntARGB(64, 64, 64, 64), color4DData.getColorARGB(0.5F, 0.5F));
		
		assertEquals(Color4I.toIntARGB(  0, 0,   0, 0), color4DData.getColorARGB(0.0F, 0.5F));
		assertEquals(Color4I.toIntARGB(128, 0, 128, 0), color4DData.getColorARGB(0.5F, 0.0F));
		
		assertEquals(Color4I.toIntARGB(  0,   0,   0,   0), color4DData.getColorARGB(0.0F, 0.0F));
		assertEquals(Color4I.toIntARGB(255,   0, 255,   0), color4DData.getColorARGB(1.0F, 0.0F));
		assertEquals(Color4I.toIntARGB(  0,   0,   0,   0), color4DData.getColorARGB(0.0F, 1.0F));
		assertEquals(Color4I.toIntARGB(  0, 255,   0, 255), color4DData.getColorARGB(1.0F, 1.0F));
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4DData.getColorARGB(-0.1F, +0.0F));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4DData.getColorARGB(+0.0F, -0.1F));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4DData.getColorARGB(+2.0F, +0.0F));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4DData.getColorARGB(+0.0F, +2.0F));
	}
	
	@Test
	public void testGetColorARGBInt() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4DData.getColorARGB(-1));
		assertEquals(Color4I.WHITE_A_R_G_B, color4DData.getColorARGB(+0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4DData.getColorARGB(+1));
	}
	
	@Test
	public void testGetColorARGBIntInt() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4DData.getColorARGB(-1, +0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4DData.getColorARGB(+0, -1));
		assertEquals(Color4I.WHITE_A_R_G_B, color4DData.getColorARGB(+0, +0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4DData.getColorARGB(+1, +0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4DData.getColorARGB(+0, +1));
	}
	
	@Test
	public void testGetDataFactory() {
		final Color4DData color4DData = new Color4DData();
		
		final DataFactory dataFactory = color4DData.getDataFactory();
		
		assertTrue(dataFactory instanceof Color4DDataFactory);
	}
	
	@Test
	public void testGetResolution() {
		final Color4DData color4DData = new Color4DData(2, 4);
		
		assertEquals(8, color4DData.getResolution());
	}
	
	@Test
	public void testGetResolutionX() {
		final Color4DData color4DData = new Color4DData(2, 4);
		
		assertEquals(2, color4DData.getResolutionX());
	}
	
	@Test
	public void testGetResolutionY() {
		final Color4DData color4DData = new Color4DData(2, 4);
		
		assertEquals(4, color4DData.getResolutionY());
	}
	
	@Test
	public void testHasChangeBegun() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertFalse(color4DData.hasChangeBegun());
		
		color4DData.setChangeHistoryEnabled(true);
		
		assertFalse(color4DData.hasChangeBegun());
		
		color4DData.changeBegin();
		
		assertTrue(color4DData.hasChangeBegun());
		
		color4DData.changeEnd();
		
		assertFalse(color4DData.hasChangeBegun());
	}
	
	@Test
	public void testHashCode() {
		final Color4DData a = new Color4DData(100, 100);
		final Color4DData b = new Color4DData(100, 100);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
		
		a.setChangeHistoryEnabled(true);
		b.setChangeHistoryEnabled(true);
		
		a.setColor4D(Color4D.BLACK, 0);
		b.setColor4D(Color4D.BLACK, 0);
		
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
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertFalse(color4DData.isChangeHistoryEnabled());
		
		color4DData.setChangeHistoryEnabled(true);
		
		assertTrue(color4DData.isChangeHistoryEnabled());
		
		color4DData.setChangeHistoryEnabled(false);
		
		assertFalse(color4DData.isChangeHistoryEnabled());
	}
	
	@Test
	public void testPixelChangeConstructor() {
		final PixelChange pixelChange = new PixelChange(Color4D.BLACK, Color4D.WHITE, 1);
		
		assertEquals(Color4D.BLACK, pixelChange.getColorRedo());
		assertEquals(Color4D.WHITE, pixelChange.getColorUndo());
		
		assertEquals(1, pixelChange.getIndex());
		
		assertThrows(NullPointerException.class, () -> new PixelChange(Color4D.BLACK, null, 0));
		assertThrows(NullPointerException.class, () -> new PixelChange(null, Color4D.BLACK, 0));
		
		assertThrows(IllegalArgumentException.class, () -> new PixelChange(Color4D.BLACK, Color4D.BLACK, -1));
	}
	
	@Test
	public void testPixelChangeEquals() {
		final PixelChange a = new PixelChange(Color4D.BLACK, Color4D.WHITE, 1);
		final PixelChange b = new PixelChange(Color4D.BLACK, Color4D.WHITE, 1);
		final PixelChange c = new PixelChange(Color4D.BLACK, Color4D.WHITE, 2);
		final PixelChange d = new PixelChange(Color4D.BLACK, Color4D.BLACK, 1);
		final PixelChange e = new PixelChange(Color4D.WHITE, Color4D.WHITE, 1);
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
		final PixelChange a = new PixelChange(Color4D.BLACK, Color4D.WHITE, 1);
		final PixelChange b = new PixelChange(Color4D.BLACK, Color4D.WHITE, 1);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testPixelChangeRedoAndUndo() {
		final Color4DData color4DData = new Color4DData(1, 1);
		final Color4FData color4FData = new Color4FData(1, 1);
		
		final PixelChange pixelChange = new PixelChange(Color4D.BLACK, Color4D.WHITE, 0);
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		pixelChange.redo(color4DData);
		pixelChange.redo(color4FData);
		
		assertEquals(Color4D.BLACK, color4DData.getColor4D(0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		pixelChange.undo(color4DData);
		pixelChange.undo(color4FData);
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		assertThrows(NullPointerException.class, () -> pixelChange.redo(null));
		assertThrows(NullPointerException.class, () -> pixelChange.undo(null));
	}
	
	@Test
	public void testRedoAndUndo() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertFalse(color4DData.redo());
		assertFalse(color4DData.undo());
		
		color4DData.setChangeHistoryEnabled(true);
		
		assertFalse(color4DData.redo());
		assertFalse(color4DData.undo());
		
		color4DData.setColor4D(Color4D.BLACK, 0);
		
		assertTrue(color4DData.undo());
		assertTrue(color4DData.redo());
	}
	
	@Test
	public void testScaleDoubleDouble() {
		final Color4DData color4DData = new Color4DData(1, 1, Color4D.TRANSPARENT);
		
		assertFalse(color4DData.scale(0.0D, 1.0D));
		assertFalse(color4DData.scale(1.0D, 0.0D));
		assertFalse(color4DData.scale(1.0D, 1.0D));
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(1, color4DData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(0));
		
		assertTrue(color4DData.scale(1.0D, 2.0D));
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(2, color4DData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(0));
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(1));
	}
	
	@Test
	public void testScaleFloatFloat() {
		final Color4DData color4DData = new Color4DData(1, 1, Color4D.TRANSPARENT);
		
		assertFalse(color4DData.scale(0.0F, 1.0F));
		assertFalse(color4DData.scale(1.0F, 0.0F));
		assertFalse(color4DData.scale(1.0F, 1.0F));
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(1, color4DData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(0));
		
		assertTrue(color4DData.scale(1.0F, 2.0F));
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(2, color4DData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(0));
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(1));
	}
	
	@Test
	public void testScaleIntInt() {
		final Color4DData color4DData = new Color4DData(1, 1, Color4D.TRANSPARENT);
		
		assertFalse(color4DData.scale(0, 1));
		assertFalse(color4DData.scale(1, 0));
		assertFalse(color4DData.scale(Integer.MAX_VALUE, 2));
		assertFalse(color4DData.scale(2, Integer.MAX_VALUE));
		assertFalse(color4DData.scale(1, 1));
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(1, color4DData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(0));
		
		assertTrue(color4DData.scale(1, 2));
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(2, color4DData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(0));
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(1));
		
		assertFalse(color4DData.undo());
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(2, color4DData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(0));
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(1));
		
		color4DData.setChangeHistoryEnabled(true);
		
		assertTrue(color4DData.scale(2, 1));
		
		assertEquals(2, color4DData.getResolutionX());
		assertEquals(1, color4DData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(0));
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(1));
		
		assertTrue(color4DData.undo());
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(2, color4DData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(0));
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(1));
		
		color4DData.changeBegin();
		
		assertTrue(color4DData.scale(2, 2));
		
		color4DData.changeEnd();
		
		assertEquals(2, color4DData.getResolutionX());
		assertEquals(2, color4DData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(0));
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(1));
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(2));
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(3));
		
		assertTrue(color4DData.undo());
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(2, color4DData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(0));
		assertEquals(Color4D.TRANSPARENT, color4DData.getColor4D(1));
	}
	
	@Test
	public void testSetChangeHistoryEnabled() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertFalse(color4DData.setChangeHistoryEnabled(false));
		
		assertTrue(color4DData.setChangeHistoryEnabled(true));
		
		assertFalse(color4DData.setChangeHistoryEnabled(true));
		
		assertTrue(color4DData.setChangeHistoryEnabled(false));
	}
	
	@Test
	public void testSetColor3DColor3DInt() {
		final Color4DData color4DData = new Color4DData(1, 1, Color4D.WHITE);
		
		assertTrue(color4DData.setColor3D(Color3D.RED, 0));
		
		assertEquals(Color3D.RED, color4DData.getColor3D(0));
		
		assertThrows(NullPointerException.class, () -> color4DData.setColor3D(null, 0));
	}
	
	@Test
	public void testSetColor3DColor3DIntInt() {
		final Color4DData color4DData = new Color4DData(1, 1, Color4D.WHITE);
		
		assertTrue(color4DData.setColor3D(Color3D.RED, 0, 0));
		
		assertEquals(Color3D.RED, color4DData.getColor3D(0, 0));
		
		assertThrows(NullPointerException.class, () -> color4DData.setColor3D(null, 0, 0));
	}
	
	@Test
	public void testSetColor3FColor3FInt() {
		final Color4DData color4DData = new Color4DData(1, 1, Color4D.WHITE);
		
		assertTrue(color4DData.setColor3F(Color3F.RED, 0));
		
		assertEquals(Color3F.RED, color4DData.getColor3F(0));
		
		assertThrows(NullPointerException.class, () -> color4DData.setColor3F(null, 0));
	}
	
	@Test
	public void testSetColor3FColor3FIntInt() {
		final Color4DData color4DData = new Color4DData(1, 1, Color4D.WHITE);
		
		assertTrue(color4DData.setColor3F(Color3F.RED, 0, 0));
		
		assertEquals(Color3F.RED, color4DData.getColor3F(0, 0));
		
		assertThrows(NullPointerException.class, () -> color4DData.setColor3F(null, 0, 0));
	}
	
	@Test
	public void testSetColor4DColor4DInt() {
		final Color4DData color4DData = new Color4DData(1, 1, Color4D.WHITE);
		
		assertTrue(color4DData.setColor4D(Color4D.RED, 0));
		assertTrue(color4DData.setColor4D(Color4D.WHITE, 0));
		
		color4DData.setChangeHistoryEnabled(true);
		
		assertFalse(color4DData.setColor4D(Color4D.RED, -1));
		assertFalse(color4DData.setColor4D(Color4D.RED, +1));
		
		assertTrue(color4DData.setColor4D(Color4D.WHITE, 0));
		
		assertFalse(color4DData.undo());
		assertFalse(color4DData.redo());
		
		assertTrue(color4DData.setColor4D(Color4D.RED, 0));
		
		assertEquals(Color4D.RED, color4DData.getColor4D(0));
		
		assertTrue(color4DData.undo());
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		assertTrue(color4DData.redo());
		
		assertEquals(Color4D.RED, color4DData.getColor4D(0));
		
		assertTrue(color4DData.changeBegin());
		assertTrue(color4DData.setColor4D(Color4D.GREEN, 0));
		assertTrue(color4DData.changeEnd());
		
		assertEquals(Color4D.GREEN, color4DData.getColor4D(0));
		
		assertTrue(color4DData.undo());
		
		assertEquals(Color4D.RED, color4DData.getColor4D(0));
		
		assertTrue(color4DData.undo());
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		assertTrue(color4DData.redo());
		
		assertEquals(Color4D.RED, color4DData.getColor4D(0));
		
		assertTrue(color4DData.redo());
		
		assertEquals(Color4D.GREEN, color4DData.getColor4D(0));
		
		assertThrows(NullPointerException.class, () -> color4DData.setColor4D(null, 0));
	}
	
	@Test
	public void testSetColor4DColor4DIntInt() {
		final Color4DData color4DData = new Color4DData(1, 1, Color4D.WHITE);
		
		assertTrue(color4DData.setColor4D(Color4D.RED, 0, 0));
		assertTrue(color4DData.setColor4D(Color4D.WHITE, 0, 0));
		
		color4DData.setChangeHistoryEnabled(true);
		
		assertFalse(color4DData.setColor4D(Color4D.RED, -1, +0));
		assertFalse(color4DData.setColor4D(Color4D.RED, +0, -1));
		assertFalse(color4DData.setColor4D(Color4D.RED, +1, +0));
		assertFalse(color4DData.setColor4D(Color4D.RED, +0, +1));
		
		assertTrue(color4DData.setColor4D(Color4D.WHITE, 0, 0));
		
		assertFalse(color4DData.undo());
		assertFalse(color4DData.redo());
		
		assertTrue(color4DData.setColor4D(Color4D.RED, 0, 0));
		
		assertEquals(Color4D.RED, color4DData.getColor4D(0, 0));
		
		assertTrue(color4DData.undo());
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0, 0));
		
		assertTrue(color4DData.redo());
		
		assertEquals(Color4D.RED, color4DData.getColor4D(0, 0));
		
		assertTrue(color4DData.changeBegin());
		assertTrue(color4DData.setColor4D(Color4D.GREEN, 0, 0));
		assertTrue(color4DData.changeEnd());
		
		assertEquals(Color4D.GREEN, color4DData.getColor4D(0, 0));
		
		assertTrue(color4DData.undo());
		
		assertEquals(Color4D.RED, color4DData.getColor4D(0, 0));
		
		assertTrue(color4DData.undo());
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0, 0));
		
		assertTrue(color4DData.redo());
		
		assertEquals(Color4D.RED, color4DData.getColor4D(0, 0));
		
		assertTrue(color4DData.redo());
		
		assertEquals(Color4D.GREEN, color4DData.getColor4D(0, 0));
		
		assertThrows(NullPointerException.class, () -> color4DData.setColor4D(null, 0, 0));
	}
	
	@Test
	public void testSetColor4FColor4FInt() {
		final Color4DData color4DData = new Color4DData(1, 1, Color4D.WHITE);
		
		assertTrue(color4DData.setColor4F(Color4F.RED, 0));
		
		assertEquals(Color4F.RED, color4DData.getColor4F(0));
		
		assertThrows(NullPointerException.class, () -> color4DData.setColor4F(null, 0));
	}
	
	@Test
	public void testSetColor4FColor4FIntInt() {
		final Color4DData color4DData = new Color4DData(1, 1, Color4D.WHITE);
		
		assertTrue(color4DData.setColor4F(Color4F.RED, 0, 0));
		
		assertEquals(Color4F.RED, color4DData.getColor4F(0, 0));
		
		assertThrows(NullPointerException.class, () -> color4DData.setColor4F(null, 0, 0));
	}
	
	@Test
	public void testSetColorARGBIntInt() {
		final Color4DData color4DData = new Color4DData(1, 1, Color4D.WHITE);
		
		assertTrue(color4DData.setColorARGB(Color4I.RED_A_R_G_B, 0));
		
		assertEquals(Color4I.RED_A_R_G_B, color4DData.getColorARGB(0));
	}
	
	@Test
	public void testSetColorARGBIntIntInt() {
		final Color4DData color4DData = new Color4DData(1, 1, Color4D.WHITE);
		
		assertTrue(color4DData.setColorARGB(Color4I.RED_A_R_G_B, 0, 0));
		
		assertEquals(Color4I.RED_A_R_G_B, color4DData.getColorARGB(0, 0));
	}
	
	@Test
	public void testSetContent() {
		final Color4DData a = new Color4DData(1, 1, Color4D.WHITE);
		final Color4DData b = new Color4DData(1, 1, Color4D.BLACK);
		final Color4DData c = new Color4DData(2, 2, Color4D.BLACK);
		final Color4FData d = new Color4FData(3, 3, Color4F.GREEN);
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4D.WHITE, a.getColor4D(0));
		
		assertTrue(a.setContent(b));
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4D.BLACK, a.getColor4D(0));
		
		assertFalse(a.undo());
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4D.BLACK, a.getColor4D(0));
		
		a.setChangeHistoryEnabled(true);
		
		assertTrue(a.setContent(c));
		
		assertEquals(2, a.getResolutionX());
		assertEquals(2, a.getResolutionY());
		
		assertEquals(Color4D.BLACK, a.getColor4D(0));
		assertEquals(Color4D.BLACK, a.getColor4D(1));
		assertEquals(Color4D.BLACK, a.getColor4D(2));
		assertEquals(Color4D.BLACK, a.getColor4D(3));
		
		assertTrue(a.undo());
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4D.BLACK, a.getColor4D(0));
		
		a.changeBegin();
		
		assertTrue(a.setContent(c));
		
		a.changeEnd();
		
		assertEquals(2, a.getResolutionX());
		assertEquals(2, a.getResolutionY());
		
		assertEquals(Color4D.BLACK, a.getColor4D(0));
		assertEquals(Color4D.BLACK, a.getColor4D(1));
		assertEquals(Color4D.BLACK, a.getColor4D(2));
		assertEquals(Color4D.BLACK, a.getColor4D(3));
		
		assertTrue(a.undo());
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4D.BLACK, a.getColor4D(0));
		
		assertFalse(a.setContent(d));
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4D.BLACK, a.getColor4D(0));
		
		assertFalse(a.undo());
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4D.BLACK, a.getColor4D(0));
		
		assertThrows(NullPointerException.class, () -> a.setContent(null));
	}
	
	@Test
	public void testSetResolution() {
		final Color4DData color4DData = new Color4DData(1, 1, Color4D.BLACK);
		
		assertFalse(color4DData.setResolution(0, 1));
		assertFalse(color4DData.setResolution(1, 0));
		assertFalse(color4DData.setResolution(Integer.MAX_VALUE, 2));
		assertFalse(color4DData.setResolution(2, Integer.MAX_VALUE));
		assertFalse(color4DData.setResolution(1, 1));
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(1, color4DData.getResolutionY());
		
		assertEquals(Color4D.BLACK, color4DData.getColor4D(0));
		
		assertTrue(color4DData.setResolution(1, 2));
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(2, color4DData.getResolutionY());
		
		assertEquals(Color4D.BLACK, color4DData.getColor4D(0));
		assertEquals(Color4D.WHITE, color4DData.getColor4D(1));
		
		assertFalse(color4DData.undo());
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(2, color4DData.getResolutionY());
		
		assertEquals(Color4D.BLACK, color4DData.getColor4D(0));
		assertEquals(Color4D.WHITE, color4DData.getColor4D(1));
		
		color4DData.setChangeHistoryEnabled(true);
		
		assertTrue(color4DData.setResolution(2, 1));
		
		assertEquals(2, color4DData.getResolutionX());
		assertEquals(1, color4DData.getResolutionY());
		
		assertEquals(Color4D.BLACK, color4DData.getColor4D(0));
		assertEquals(Color4D.WHITE, color4DData.getColor4D(1));
		
		assertTrue(color4DData.undo());
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(2, color4DData.getResolutionY());
		
		assertEquals(Color4D.BLACK, color4DData.getColor4D(0));
		assertEquals(Color4D.WHITE, color4DData.getColor4D(1));
		
		color4DData.changeBegin();
		
		assertTrue(color4DData.setResolution(2, 2));
		
		color4DData.changeEnd();
		
		assertEquals(2, color4DData.getResolutionX());
		assertEquals(2, color4DData.getResolutionY());
		
		assertEquals(Color4D.BLACK, color4DData.getColor4D(0));
		assertEquals(Color4D.WHITE, color4DData.getColor4D(1));
		assertEquals(Color4D.WHITE, color4DData.getColor4D(2));
		assertEquals(Color4D.WHITE, color4DData.getColor4D(3));
		
		assertTrue(color4DData.undo());
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(2, color4DData.getResolutionY());
		
		assertEquals(Color4D.BLACK, color4DData.getColor4D(0));
		assertEquals(Color4D.WHITE, color4DData.getColor4D(1));
	}
	
	@Test
	public void testSetResolutionX() {
		final Color4DData color4DData = new Color4DData(2, 2, Color4D.BLACK);
		
		assertFalse(color4DData.setResolutionX(0));
		assertFalse(color4DData.setResolutionX(Integer.MAX_VALUE));
		assertFalse(color4DData.setResolutionX(2));
		
		assertEquals(2, color4DData.getResolutionX());
		assertEquals(2, color4DData.getResolutionY());
	}
	
	@Test
	public void testSetResolutionY() {
		final Color4DData color4DData = new Color4DData(2, 2, Color4D.BLACK);
		
		assertFalse(color4DData.setResolutionY(0));
		assertFalse(color4DData.setResolutionY(Integer.MAX_VALUE));
		assertFalse(color4DData.setResolutionY(2));
		
		assertEquals(2, color4DData.getResolutionX());
		assertEquals(2, color4DData.getResolutionY());
	}
	
	@Test
	public void testStateChangeConstructor() {
		final StateChange stateChange = new StateChange(new Color4D[] {Color4D.BLACK}, new Color4D[] {Color4D.WHITE}, 1, 1, 1, 1);
		
		assertArrayEquals(new Color4D[] {Color4D.BLACK}, stateChange.getColorsRedo());
		assertArrayEquals(new Color4D[] {Color4D.WHITE}, stateChange.getColorsUndo());
		
		assertEquals(1, stateChange.getResolutionXRedo());
		assertEquals(1, stateChange.getResolutionXUndo());
		assertEquals(1, stateChange.getResolutionYRedo());
		assertEquals(1, stateChange.getResolutionYUndo());
		
		assertThrows(NullPointerException.class, () -> new StateChange(new Color4D[] {null}, new Color4D[] {Color4D.WHITE}, 1, 1, 1, 1));
		assertThrows(NullPointerException.class, () -> new StateChange(null, new Color4D[] {Color4D.WHITE}, 1, 1, 1, 1));
		assertThrows(NullPointerException.class, () -> new StateChange(new Color4D[] {Color4D.BLACK}, new Color4D[] {null}, 1, 1, 1, 1));
		assertThrows(NullPointerException.class, () -> new StateChange(new Color4D[] {Color4D.BLACK}, null, 1, 1, 1, 1));
		
		assertThrows(IllegalArgumentException.class, () -> new StateChange(new Color4D[] {Color4D.BLACK}, new Color4D[] {Color4D.WHITE}, 1, 1, 1, 0));
		assertThrows(IllegalArgumentException.class, () -> new StateChange(new Color4D[] {Color4D.BLACK}, new Color4D[] {Color4D.WHITE}, 1, 1, 0, 1));
		assertThrows(IllegalArgumentException.class, () -> new StateChange(new Color4D[] {Color4D.BLACK}, new Color4D[] {Color4D.WHITE}, 1, 0, 1, 1));
		assertThrows(IllegalArgumentException.class, () -> new StateChange(new Color4D[] {Color4D.BLACK}, new Color4D[] {Color4D.WHITE}, 0, 1, 1, 1));
	}
	
	@Test
	public void testStateChangeEquals() {
		final StateChange a = new StateChange(new Color4D[] {Color4D.BLACK}, new Color4D[] {Color4D.WHITE}, 1, 1, 1, 1);
		final StateChange b = new StateChange(new Color4D[] {Color4D.BLACK}, new Color4D[] {Color4D.WHITE}, 1, 1, 1, 1);
		final StateChange c = new StateChange(new Color4D[] {Color4D.BLACK}, new Color4D[] {Color4D.WHITE}, 1, 1, 1, 2);
		final StateChange d = new StateChange(new Color4D[] {Color4D.BLACK}, new Color4D[] {Color4D.WHITE}, 1, 1, 2, 1);
		final StateChange e = new StateChange(new Color4D[] {Color4D.BLACK}, new Color4D[] {Color4D.WHITE}, 1, 2, 1, 1);
		final StateChange f = new StateChange(new Color4D[] {Color4D.BLACK}, new Color4D[] {Color4D.WHITE}, 2, 1, 1, 1);
		final StateChange g = new StateChange(new Color4D[] {Color4D.BLACK}, new Color4D[] {Color4D.BLACK}, 1, 1, 1, 1);
		final StateChange h = new StateChange(new Color4D[] {Color4D.WHITE}, new Color4D[] {Color4D.WHITE}, 1, 1, 1, 1);
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
		final StateChange a = new StateChange(new Color4D[] {Color4D.BLACK}, new Color4D[] {Color4D.WHITE}, 1, 1, 1, 1);
		final StateChange b = new StateChange(new Color4D[] {Color4D.BLACK}, new Color4D[] {Color4D.WHITE}, 1, 1, 1, 1);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testStateChangeRedoAndUndo() {
		final Color4DData color4DData = new Color4DData(1, 1);
		final Color4FData color4FData = new Color4FData(1, 1);
		
		final StateChange stateChange = new StateChange(new Color4D[] {Color4D.BLACK, Color4D.BLACK, Color4D.BLACK, Color4D.BLACK}, new Color4D[] {Color4D.WHITE}, 2, 1, 2, 1);
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(1, color4DData.getResolutionY());
		
		assertEquals(1, color4FData.getResolutionX());
		assertEquals(1, color4FData.getResolutionY());
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		stateChange.redo(color4DData);
		stateChange.redo(color4FData);
		
		assertEquals(2, color4DData.getResolutionX());
		assertEquals(2, color4DData.getResolutionY());
		
		assertEquals(1, color4FData.getResolutionX());
		assertEquals(1, color4FData.getResolutionY());
		
		assertEquals(Color4D.BLACK, color4DData.getColor4D(0));
		assertEquals(Color4D.BLACK, color4DData.getColor4D(1));
		assertEquals(Color4D.BLACK, color4DData.getColor4D(2));
		assertEquals(Color4D.BLACK, color4DData.getColor4D(3));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		stateChange.undo(color4DData);
		stateChange.undo(color4FData);
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(1, color4DData.getResolutionY());
		
		assertEquals(1, color4FData.getResolutionX());
		assertEquals(1, color4FData.getResolutionY());
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		assertThrows(NullPointerException.class, () -> stateChange.redo(null));
		assertThrows(NullPointerException.class, () -> stateChange.undo(null));
	}
	
	@Test
	public void testSwap() {
		final
		Color4DData color4DData = new Color4DData(2, 1);
		color4DData.setColor4D(Color4D.BLUE, 0);
		color4DData.setColor4D(Color4D.CYAN, 1);
		
		assertFalse(color4DData.swap(-1, +0));
		assertFalse(color4DData.swap(+2, +0));
		assertFalse(color4DData.swap(+0, -1));
		assertFalse(color4DData.swap(+0, +2));
		
		assertTrue(color4DData.swap(0, 1));
		
		assertEquals(Color4D.CYAN, color4DData.getColor4D(0));
		assertEquals(Color4D.BLUE, color4DData.getColor4D(1));
		
		assertFalse(color4DData.undo());
		
		color4DData.setChangeHistoryEnabled(true);
		
		assertTrue(color4DData.swap(0, 1));
		
		assertEquals(Color4D.BLUE, color4DData.getColor4D(0));
		assertEquals(Color4D.CYAN, color4DData.getColor4D(1));
		
		assertTrue(color4DData.undo());
		
		assertEquals(Color4D.CYAN, color4DData.getColor4D(0));
		assertEquals(Color4D.BLUE, color4DData.getColor4D(1));
		
		color4DData.changeBegin();
		
		assertTrue(color4DData.swap(0, 1));
		
		color4DData.changeEnd();
		
		assertEquals(Color4D.BLUE, color4DData.getColor4D(0));
		assertEquals(Color4D.CYAN, color4DData.getColor4D(1));
		
		assertTrue(color4DData.undo());
		
		assertEquals(Color4D.CYAN, color4DData.getColor4D(0));
		assertEquals(Color4D.BLUE, color4DData.getColor4D(1));
	}
	
	@Test
	public void testToBufferedImageBoolean() {
		final BufferedImage bufferedImageARGBExpected = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		final BufferedImage bufferedImageRGBExpected = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		
		bufferedImageARGBExpected.setRGB(0, 0, Color4I.toIntARGB(255, 0, 0, 255));
		bufferedImageRGBExpected.setRGB(0, 0, Color3I.toIntRGB(255, 0, 0));
		
		final Color4DData color4DData = new Color4DData(1, 1, Color4D.RED);
		
		final BufferedImage bufferedImageARGB = color4DData.toBufferedImage(false);
		final BufferedImage bufferedImageRGB = color4DData.toBufferedImage(true);
		
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
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		color4DData.updatePixel(Color4D.BLACK, 0);
		
		assertEquals(Color4D.BLACK, color4DData.getColor4D(0));
		
		assertThrows(NullPointerException.class, () -> color4DData.updatePixel(null, 0));
		
		assertThrows(IllegalArgumentException.class, () -> color4DData.updatePixel(Color4D.BLACK, -1));
		assertThrows(IllegalArgumentException.class, () -> color4DData.updatePixel(Color4D.BLACK, +1));
	}
	
	@Test
	public void testUpdateState() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(1, color4DData.getResolutionY());
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		color4DData.updateState(new Color4D[] {Color4D.BLACK, Color4D.BLACK, Color4D.BLACK, Color4D.BLACK}, 2, 2);
		
		assertEquals(2, color4DData.getResolutionX());
		assertEquals(2, color4DData.getResolutionY());
		
		assertEquals(Color4D.BLACK, color4DData.getColor4D(0));
		assertEquals(Color4D.BLACK, color4DData.getColor4D(1));
		assertEquals(Color4D.BLACK, color4DData.getColor4D(2));
		assertEquals(Color4D.BLACK, color4DData.getColor4D(3));
		
		assertThrows(NullPointerException.class, () -> color4DData.updateState(new Color4D[] {null}, 1, 1));
		assertThrows(NullPointerException.class, () -> color4DData.updateState(null, 1, 1));
		
		assertThrows(IllegalArgumentException.class, () -> color4DData.updateState(new Color4D[] {Color4D.BLACK}, 1, 0));
		assertThrows(IllegalArgumentException.class, () -> color4DData.updateState(new Color4D[] {Color4D.BLACK}, 0, 1));
	}
}