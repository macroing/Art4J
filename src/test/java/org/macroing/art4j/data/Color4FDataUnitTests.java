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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.macroing.art4j.color.Color3D;
import org.macroing.art4j.color.Color3F;
import org.macroing.art4j.color.Color3I;
import org.macroing.art4j.color.Color4D;
import org.macroing.art4j.color.Color4F;
import org.macroing.art4j.color.Color4I;
import org.macroing.art4j.data.Color4FData.PixelChange;
import org.macroing.art4j.data.Color4FData.StateChange;
import org.macroing.art4j.kernel.ConvolutionKernelND;
import org.macroing.art4j.kernel.ConvolutionKernelNF;
import org.macroing.art4j.pixel.PixelTransformer;
import org.macroing.geo4j.common.Point2I;
import org.macroing.geo4j.shape.rectangle.Rectangle2I;

@SuppressWarnings("static-method")
public final class Color4FDataUnitTests {
	public Color4FDataUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testCache() {
//		Make sure a cached instance already exists:
		Color4F.getCached(new Color4F(0.0F, 0.0F, 0.0F));
		
		final
		Color4FData color4FData = new Color4FData(2, 2);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F), 0);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F), 1);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F), 2);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F), 3);
		
		assertEquals(4, color4FData.cache());
		assertEquals(0, color4FData.cache());
	}
	
	@Test
	public void testChangeAdd() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertFalse(color4FData.changeAdd(new PixelChange(Color4F.BLACK, Color4F.WHITE, 1)));
		
		color4FData.setChangeHistoryEnabled(true);
		
		assertFalse(color4FData.changeAdd(new PixelChange(Color4F.BLACK, Color4F.WHITE, 1)));
		
		color4FData.changeBegin();
		
		assertTrue(color4FData.changeAdd(new PixelChange(Color4F.BLACK, Color4F.WHITE, 1)));
		
		assertThrows(NullPointerException.class, () -> color4FData.changeAdd(null));
	}
	
	@Test
	public void testChangeBegin() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertFalse(color4FData.changeBegin());
		
		color4FData.setChangeHistoryEnabled(true);
		
		assertTrue(color4FData.changeBegin());
		
		assertFalse(color4FData.changeBegin());
		
		color4FData.changeEnd();
		
		assertTrue(color4FData.changeBegin());
	}
	
	@Test
	public void testChangeEnd() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertFalse(color4FData.changeEnd());
		
		color4FData.setChangeHistoryEnabled(true);
		
		assertFalse(color4FData.changeEnd());
		
		color4FData.changeBegin();
		
		assertTrue(color4FData.changeEnd());
		
		assertFalse(color4FData.changeEnd());
		
		color4FData.changeBegin();
		
		assertTrue(color4FData.changeEnd());
	}
	
	@Test
	public void testConstructor() {
		final Color4FData color4FData = new Color4FData();
		
		assertEquals(1024, color4FData.getResolutionX());
		assertEquals( 768, color4FData.getResolutionY());
		
		for(int y = 0; y < color4FData.getResolutionY(); y++) {
			for(int x = 0; x < color4FData.getResolutionX(); x++) {
				assertEquals(Color4F.WHITE, color4FData.getColor4F(x, y));
			}
		}
	}
	
	@Test
	public void testConstructorBufferedImage() {
		final Color4FData color4FData = new Color4FData(new BufferedImage(1024, 768, BufferedImage.TYPE_INT_ARGB));
		
		assertEquals(1024, color4FData.getResolutionX());
		assertEquals( 768, color4FData.getResolutionY());
		
		for(int y = 0; y < color4FData.getResolutionY(); y++) {
			for(int x = 0; x < color4FData.getResolutionX(); x++) {
				assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> new Color4FData((BufferedImage)(null)));
	}
	
	@Test
	public void testConstructorColor4FData() {
		final
		Color4FData color4FData = new Color4FData(1024, 768);
		color4FData.setChangeHistoryEnabled(true);
		color4FData.setColor4F(Color4F.BLACK, 0);
		color4FData.undo();
		color4FData.redo();
		
		final Color4FData color4FDataCopyA = new Color4FData(color4FData);
		
		assertEquals(color4FData, color4FDataCopyA);
		
		color4FData.undo();
		color4FData.setChangeHistoryEnabled(false);
		color4FData.setColor4F(Color4F.BLACK, 0);
		
		final Color4FData color4FDataCopyB = new Color4FData(color4FData);
		
		assertEquals(color4FData, color4FDataCopyB);
		
		assertThrows(NullPointerException.class, () -> new Color4FData((Color4FData)(null)));
	}
	
	@Test
	public void testConstructorColor4FDataBoolean() {
		final
		Color4FData color4FData = new Color4FData(1024, 768);
		color4FData.setChangeHistoryEnabled(true);
		color4FData.setColor4F(Color4F.BLACK, 0);
		color4FData.undo();
		color4FData.redo();
		
		final Color4FData color4FDataCopyA = new Color4FData(color4FData, false);
		final Color4FData color4FDataCopyB = new Color4FData(color4FData, true);
		
		assertEquals(color4FData, color4FDataCopyA);
		
		assertNotEquals(color4FData, color4FDataCopyB);
		
		color4FData.undo();
		color4FData.setChangeHistoryEnabled(false);
		color4FData.setColor4F(Color4F.BLACK, 0);
		
		final Color4FData color4FDataCopyC = new Color4FData(color4FData, false);
		final Color4FData color4FDataCopyD = new Color4FData(color4FData, true);
		
		assertEquals(color4FData, color4FDataCopyC);
		assertEquals(color4FData, color4FDataCopyD);
		
		assertThrows(NullPointerException.class, () -> new Color4FData((Color4FData)(null), false));
	}
	
	@Test
	public void testConstructorIntInt() {
		final Color4FData color4FData = new Color4FData(1024, 768);
		
		assertEquals(1024, color4FData.getResolutionX());
		assertEquals( 768, color4FData.getResolutionY());
		
		for(int y = 0; y < color4FData.getResolutionY(); y++) {
			for(int x = 0; x < color4FData.getResolutionX(); x++) {
				assertEquals(Color4F.WHITE, color4FData.getColor4F(x, y));
			}
		}
		
		assertThrows(IllegalArgumentException.class, () -> new Color4FData(1, 0));
		assertThrows(IllegalArgumentException.class, () -> new Color4FData(0, 1));
		assertThrows(IllegalArgumentException.class, () -> new Color4FData(Integer.MAX_VALUE, Integer.MAX_VALUE));
	}
	
	@Test
	public void testConstructorIntIntColor4D() {
		final Color4FData color4FData = new Color4FData(1024, 768, Color4D.RED);
		
		assertEquals(1024, color4FData.getResolutionX());
		assertEquals( 768, color4FData.getResolutionY());
		
		for(int y = 0; y < color4FData.getResolutionY(); y++) {
			for(int x = 0; x < color4FData.getResolutionX(); x++) {
				assertEquals(Color4F.RED, color4FData.getColor4F(x, y));
			}
		}
		
		assertThrows(IllegalArgumentException.class, () -> new Color4FData(1, 0, Color4D.RED));
		assertThrows(IllegalArgumentException.class, () -> new Color4FData(0, 1, Color4D.RED));
		assertThrows(IllegalArgumentException.class, () -> new Color4FData(Integer.MAX_VALUE, Integer.MAX_VALUE, Color4D.RED));
		
		assertThrows(NullPointerException.class, () -> new Color4FData(1, 1, (Color4D)(null)));
	}
	
	@Test
	public void testConstructorIntIntColor4F() {
		final Color4FData color4FData = new Color4FData(1024, 768, Color4F.RED);
		
		assertEquals(1024, color4FData.getResolutionX());
		assertEquals( 768, color4FData.getResolutionY());
		
		for(int y = 0; y < color4FData.getResolutionY(); y++) {
			for(int x = 0; x < color4FData.getResolutionX(); x++) {
				assertEquals(Color4F.RED, color4FData.getColor4F(x, y));
			}
		}
		
		assertThrows(IllegalArgumentException.class, () -> new Color4FData(1, 0, Color4F.RED));
		assertThrows(IllegalArgumentException.class, () -> new Color4FData(0, 1, Color4F.RED));
		assertThrows(IllegalArgumentException.class, () -> new Color4FData(Integer.MAX_VALUE, Integer.MAX_VALUE, Color4F.RED));
		
		assertThrows(NullPointerException.class, () -> new Color4FData(1, 1, (Color4F)(null)));
	}
	
	@Test
	public void testConstructorIntIntInt() {
		final Color4FData color4FData = new Color4FData(1024, 768, Color4I.WHITE_A_R_G_B);
		
		assertEquals(1024, color4FData.getResolutionX());
		assertEquals( 768, color4FData.getResolutionY());
		
		for(int y = 0; y < color4FData.getResolutionY(); y++) {
			for(int x = 0; x < color4FData.getResolutionX(); x++) {
				assertEquals(Color4F.WHITE, color4FData.getColor4F(x, y));
			}
		}
		
		assertThrows(IllegalArgumentException.class, () -> new Color4FData(1, 0, Color4I.WHITE_A_R_G_B));
		assertThrows(IllegalArgumentException.class, () -> new Color4FData(0, 1, Color4I.WHITE_A_R_G_B));
		assertThrows(IllegalArgumentException.class, () -> new Color4FData(Integer.MAX_VALUE, Integer.MAX_VALUE, Color4I.WHITE_A_R_G_B));
	}
	
	@Test
	public void testConvolveConvolutionKernelNDIntArray() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertFalse(color4FData.convolve(ConvolutionKernelND.IDENTITY_3, new int[0]));
		assertFalse(color4FData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		assertFalse(color4FData.undo());
		
		color4FData.setChangeHistoryEnabled(true);
		
		assertFalse(color4FData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		assertFalse(color4FData.undo());
		
		color4FData.changeBegin();
		
		assertFalse(color4FData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		
		color4FData.changeEnd();
		
		assertFalse(color4FData.undo());
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		color4FData.setChangeHistoryEnabled(false);
		
		assertTrue(color4FData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {0}));
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		assertFalse(color4FData.undo());
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		color4FData.setChangeHistoryEnabled(true);
		
		assertTrue(color4FData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {0}));
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		assertTrue(color4FData.undo());
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		color4FData.changeBegin();
		
		assertTrue(color4FData.convolve(ConvolutionKernelND.IDENTITY_3, new int[] {0}));
		
		color4FData.changeEnd();
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		assertTrue(color4FData.undo());
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		assertThrows(NullPointerException.class, () -> color4FData.convolve(ConvolutionKernelND.IDENTITY_3, null));
		assertThrows(NullPointerException.class, () -> color4FData.convolve((ConvolutionKernelND)(null), new int[1]));
	}
	
	@Test
	public void testConvolveConvolutionKernelNFIntArray() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertFalse(color4FData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[0]));
		assertFalse(color4FData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		assertFalse(color4FData.undo());
		
		color4FData.setChangeHistoryEnabled(true);
		
		assertFalse(color4FData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		assertFalse(color4FData.undo());
		
		color4FData.changeBegin();
		
		assertFalse(color4FData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {-1, -1, +2, +2}));
		
		color4FData.changeEnd();
		
		assertFalse(color4FData.undo());
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		color4FData.setChangeHistoryEnabled(false);
		
		assertTrue(color4FData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {0}));
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		assertFalse(color4FData.undo());
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		color4FData.setChangeHistoryEnabled(true);
		
		assertTrue(color4FData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {0}));
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		assertTrue(color4FData.undo());
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		color4FData.changeBegin();
		
		assertTrue(color4FData.convolve(ConvolutionKernelNF.IDENTITY_3, new int[] {0}));
		
		color4FData.changeEnd();
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		assertTrue(color4FData.undo());
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		assertThrows(NullPointerException.class, () -> color4FData.convolve(ConvolutionKernelNF.IDENTITY_3, null));
		assertThrows(NullPointerException.class, () -> color4FData.convolve((ConvolutionKernelNF)(null), new int[1]));
	}
	
	@Test
	public void testCopy() {
		final
		Color4FData color4FData = new Color4FData(1024, 768);
		color4FData.setChangeHistoryEnabled(true);
		color4FData.setColor4F(Color4F.BLACK, 0);
		color4FData.undo();
		color4FData.redo();
		
		final Data dataCopyA = color4FData.copy();
		
		assertTrue(dataCopyA instanceof Color4FData);
		
		final Color4FData color4FDataCopyA = Color4FData.class.cast(dataCopyA);
		
		assertEquals(color4FData, color4FDataCopyA);
		
		color4FData.undo();
		color4FData.setChangeHistoryEnabled(false);
		color4FData.setColor4F(Color4F.BLACK, 0);
		
		final Data dataCopyB = color4FData.copy();
		
		assertTrue(dataCopyB instanceof Color4FData);
		
		final Color4FData color4FDataCopyB = Color4FData.class.cast(dataCopyB);
		
		assertEquals(color4FData, color4FDataCopyB);
	}
	
	@Test
	public void testCopyBoolean() {
		final
		Color4FData color4FData = new Color4FData(1024, 768);
		color4FData.setChangeHistoryEnabled(true);
		color4FData.setColor4F(Color4F.BLACK, 0);
		color4FData.undo();
		color4FData.redo();
		
		final Data dataCopyA = color4FData.copy(false);
		final Data dataCopyB = color4FData.copy(true);
		
		assertTrue(dataCopyA instanceof Color4FData);
		assertTrue(dataCopyB instanceof Color4FData);
		
		final Color4FData color4DDataCopyA = Color4FData.class.cast(dataCopyA);
		final Color4FData color4DDataCopyB = Color4FData.class.cast(dataCopyB);
		
		assertEquals(color4FData, color4DDataCopyA);
		
		assertNotEquals(color4FData, color4DDataCopyB);
		
		color4FData.undo();
		color4FData.setChangeHistoryEnabled(false);
		color4FData.setColor4F(Color4F.BLACK, 0);
		
		final Data dataCopyC = color4FData.copy(false);
		final Data dataCopyD = color4FData.copy(true);
		
		assertTrue(dataCopyC instanceof Color4FData);
		assertTrue(dataCopyD instanceof Color4FData);
		
		final Color4FData color4FDataCopyC = Color4FData.class.cast(dataCopyC);
		final Color4FData color4FDataCopyD = Color4FData.class.cast(dataCopyD);
		
		assertEquals(color4FData, color4FDataCopyC);
		assertEquals(color4FData, color4FDataCopyD);
	}
	
	@Test
	public void testCopyShape2I() {
		final Color4FData color4FData = new Color4FData(10, 10);
		
		assertEquals(10, color4FData.getResolutionX());
		assertEquals(10, color4FData.getResolutionY());
		
		for(int y = 0; y < color4FData.getResolutionY(); y++) {
			for(int x = 0; x < color4FData.getResolutionX(); x++) {
				color4FData.setColor4F(new Color4F(x, y, 0.0F), x, y);
			}
		}
		
		final Data dataCopy = color4FData.copy(new Rectangle2I(new Point2I(1, 1), new Point2I(8, 8)));
		
		assertTrue(dataCopy instanceof Color4FData);
		
		final Color4FData color4FDataCopy = Color4FData.class.cast(dataCopy);
		
		assertEquals(8, color4FDataCopy.getResolutionX());
		assertEquals(8, color4FDataCopy.getResolutionY());
		
		for(int y = 0; y < color4FDataCopy.getResolutionY(); y++) {
			for(int x = 0; x < color4FDataCopy.getResolutionX(); x++) {
				final Color4F color = color4FDataCopy.getColor4F(x, y);
				
				assertEquals(x + 1.0F, color.r);
				assertEquals(y + 1.0F, color.g);
				
				assertEquals(0.0F, color.b);
				assertEquals(1.0F, color.a);
			}
		}
		
		assertThrows(NullPointerException.class, () -> color4FData.copy(null));
	}
	
	@Test
	public void testDrawConsumerGraphics2D() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		color4FData.draw(graphics2D -> {
			graphics2D.setColor(Color.RED);
			graphics2D.fillRect(0, 0, 1, 1);
		});
		
		assertEquals(Color4F.RED, color4FData.getColor4F(0));
		
		assertFalse(color4FData.undo());
		
		assertEquals(Color4F.RED, color4FData.getColor4F(0));
		
		color4FData.setChangeHistoryEnabled(true);
		color4FData.draw(graphics2D -> {
			graphics2D.setColor(Color.GREEN);
			graphics2D.fillRect(0, 0, 1, 1);
		});
		
		assertEquals(Color4F.GREEN, color4FData.getColor4F(0));
		
		assertTrue(color4FData.undo());
		
		assertEquals(Color4F.RED, color4FData.getColor4F(0));
		
		color4FData.changeBegin();
		color4FData.draw(graphics2D -> {
			graphics2D.setColor(Color.BLUE);
			graphics2D.fillRect(0, 0, 1, 1);
		});
		color4FData.changeEnd();
		
		assertEquals(Color4F.BLUE, color4FData.getColor4F(0));
		
		assertTrue(color4FData.undo());
		
		assertEquals(Color4F.RED, color4FData.getColor4F(0));
		
		assertThrows(NullPointerException.class, () -> color4FData.draw(null));
	}
	
	@Test
	public void testEquals() {
		final Data a = new Color4FData(400, 400);
		final Data b = new Color4FData(400, 400);
		final Data c = new Color4FData(400, 200);
		final Data d = new Color4FData(200, 400);
		final Data e = new Color4FData(400, 400, Color4F.GREEN);
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
		
		a.setColor4F(Color4F.BLACK, 0);
		b.setColor4F(Color4F.BLACK, 0);
		
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
		
		a.setColor4F(Color4F.BLACK, 0);
		b.setColor4F(Color4F.BLACK, 0);
		
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
	public void testGetChangeHistory() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		final Optional<ChangeHistory> optionalChangeHistoryA = color4FData.getChangeHistory();
		
		color4FData.setChangeHistoryEnabled(true);
		
		final Optional<ChangeHistory> optionalChangeHistoryB = color4FData.getChangeHistory();
		
		assertFalse(optionalChangeHistoryA.isPresent());
		
		assertTrue(optionalChangeHistoryB.isPresent());
	}
	
	@Test
	public void testGetColor3DDoubleDouble() {
		final
		Color4FData color4FData = new Color4FData(2, 2);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 1.0F), 0);
		color4FData.setColor4F(new Color4F(1.0F, 0.0F, 1.0F, 1.0F), 1);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 1.0F), 2);
		color4FData.setColor4F(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), 3);
		
		assertEquals(new Color3D(0.25D, 0.25D, 0.25D), color4FData.getColor3D(0.5D, 0.5D));
		
		assertEquals(new Color3D(0.0D, 0.0D, 0.0D), color4FData.getColor3D(0.0D, 0.5D));
		assertEquals(new Color3D(0.5D, 0.0D, 0.5D), color4FData.getColor3D(0.5D, 0.0D));
		
		assertEquals(new Color3D(0.0D, 0.0D, 0.0D), color4FData.getColor3D(0.0D, 0.0D));
		assertEquals(new Color3D(1.0D, 0.0D, 1.0D), color4FData.getColor3D(1.0D, 0.0D));
		assertEquals(new Color3D(0.0D, 0.0D, 0.0D), color4FData.getColor3D(0.0D, 1.0D));
		assertEquals(new Color3D(0.0D, 1.0D, 0.0D), color4FData.getColor3D(1.0D, 1.0D));
		
		assertEquals(Color3D.BLACK, color4FData.getColor3D(-0.1D, +0.0D));
		assertEquals(Color3D.BLACK, color4FData.getColor3D(+0.0D, -0.1D));
		assertEquals(Color3D.BLACK, color4FData.getColor3D(+2.0D, +0.0D));
		assertEquals(Color3D.BLACK, color4FData.getColor3D(+0.0D, +2.0D));
	}
	
	@Test
	public void testGetColor3DDoubleDoublePixelTransformer() {
		final
		Color4FData color4FData = new Color4FData(2, 2);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 1.0F), 0);
		color4FData.setColor4F(new Color4F(1.0F, 0.0F, 1.0F, 1.0F), 1);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 1.0F), 2);
		color4FData.setColor4F(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), 3);
		
		assertEquals(new Color3D(0.25D, 0.25D, 0.25D), color4FData.getColor3D(0.5D, 0.5D, PixelTransformer.DEFAULT));
		
		assertEquals(new Color3D(0.0D, 0.0D, 0.0D), color4FData.getColor3D(0.0D, 0.5D, PixelTransformer.DEFAULT));
		assertEquals(new Color3D(0.5D, 0.0D, 0.5D), color4FData.getColor3D(0.5D, 0.0D, PixelTransformer.DEFAULT));
		
		assertEquals(new Color3D(0.0D, 0.0D, 0.0D), color4FData.getColor3D(0.0D, 0.0D, PixelTransformer.DEFAULT));
		assertEquals(new Color3D(1.0D, 0.0D, 1.0D), color4FData.getColor3D(1.0D, 0.0D, PixelTransformer.DEFAULT));
		assertEquals(new Color3D(0.0D, 0.0D, 0.0D), color4FData.getColor3D(0.0D, 1.0D, PixelTransformer.DEFAULT));
		assertEquals(new Color3D(0.0D, 1.0D, 0.0D), color4FData.getColor3D(1.0D, 1.0D, PixelTransformer.DEFAULT));
		
		assertEquals(Color3D.BLACK, color4FData.getColor3D(-0.1D, +0.0D, PixelTransformer.DEFAULT));
		assertEquals(Color3D.BLACK, color4FData.getColor3D(+0.0D, -0.1D, PixelTransformer.DEFAULT));
		assertEquals(Color3D.BLACK, color4FData.getColor3D(+2.0D, +0.0D, PixelTransformer.DEFAULT));
		assertEquals(Color3D.BLACK, color4FData.getColor3D(+0.0D, +2.0D, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> color4FData.getColor3D(0.0D, 0.0D, null));
	}
	
	@Test
	public void testGetColor3DInt() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color3D.BLACK, color4FData.getColor3D(-1));
		assertEquals(Color3D.WHITE, color4FData.getColor3D(+0));
		assertEquals(Color3D.BLACK, color4FData.getColor3D(+1));
	}
	
	@Test
	public void testGetColor3DIntInt() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color3D.BLACK, color4FData.getColor3D(-1, +0));
		assertEquals(Color3D.BLACK, color4FData.getColor3D(+0, -1));
		assertEquals(Color3D.WHITE, color4FData.getColor3D(+0, +0));
		assertEquals(Color3D.BLACK, color4FData.getColor3D(+1, +0));
		assertEquals(Color3D.BLACK, color4FData.getColor3D(+0, +1));
	}
	
	@Test
	public void testGetColor3DIntIntPixelTransformer() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color3D.BLACK, color4FData.getColor3D(-1, +0, PixelTransformer.DEFAULT));
		assertEquals(Color3D.BLACK, color4FData.getColor3D(+0, -1, PixelTransformer.DEFAULT));
		assertEquals(Color3D.WHITE, color4FData.getColor3D(+0, +0, PixelTransformer.DEFAULT));
		assertEquals(Color3D.BLACK, color4FData.getColor3D(+1, +0, PixelTransformer.DEFAULT));
		assertEquals(Color3D.BLACK, color4FData.getColor3D(+0, +1, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> color4FData.getColor3D(0, 0, null));
	}
	
	@Test
	public void testGetColor3DIntPixelTransformer() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color3D.BLACK, color4FData.getColor3D(-1, PixelTransformer.DEFAULT));
		assertEquals(Color3D.WHITE, color4FData.getColor3D(+0, PixelTransformer.DEFAULT));
		assertEquals(Color3D.BLACK, color4FData.getColor3D(+1, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> color4FData.getColor3D(0, null));
	}
	
	@Test
	public void testGetColor3FFloatFloat() {
		final
		Color4FData color4FData = new Color4FData(2, 2);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 1.0F), 0);
		color4FData.setColor4F(new Color4F(1.0F, 0.0F, 1.0F, 1.0F), 1);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 1.0F), 2);
		color4FData.setColor4F(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), 3);
		
		assertEquals(new Color3F(0.25F, 0.25F, 0.25F), color4FData.getColor3F(0.5F, 0.5F));
		
		assertEquals(new Color3F(0.0F, 0.0F, 0.0F), color4FData.getColor3F(0.0F, 0.5F));
		assertEquals(new Color3F(0.5F, 0.0F, 0.5F), color4FData.getColor3F(0.5F, 0.0F));
		
		assertEquals(new Color3F(0.0F, 0.0F, 0.0F), color4FData.getColor3F(0.0F, 0.0F));
		assertEquals(new Color3F(1.0F, 0.0F, 1.0F), color4FData.getColor3F(1.0F, 0.0F));
		assertEquals(new Color3F(0.0F, 0.0F, 0.0F), color4FData.getColor3F(0.0F, 1.0F));
		assertEquals(new Color3F(0.0F, 1.0F, 0.0F), color4FData.getColor3F(1.0F, 1.0F));
		
		assertEquals(Color3F.BLACK, color4FData.getColor3F(-0.1F, +0.0F));
		assertEquals(Color3F.BLACK, color4FData.getColor3F(+0.0F, -0.1F));
		assertEquals(Color3F.BLACK, color4FData.getColor3F(+2.0F, +0.0F));
		assertEquals(Color3F.BLACK, color4FData.getColor3F(+0.0F, +2.0F));
	}
	
	@Test
	public void testGetColor3FFloatFloatPixelTransformer() {
		final
		Color4FData color4FData = new Color4FData(2, 2);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 1.0F), 0);
		color4FData.setColor4F(new Color4F(1.0F, 0.0F, 1.0F, 1.0F), 1);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 1.0F), 2);
		color4FData.setColor4F(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), 3);
		
		assertEquals(new Color3F(0.25F, 0.25F, 0.25F), color4FData.getColor3F(0.5F, 0.5F, PixelTransformer.DEFAULT));
		
		assertEquals(new Color3F(0.0F, 0.0F, 0.0F), color4FData.getColor3F(0.0F, 0.5F, PixelTransformer.DEFAULT));
		assertEquals(new Color3F(0.5F, 0.0F, 0.5F), color4FData.getColor3F(0.5F, 0.0F, PixelTransformer.DEFAULT));
		
		assertEquals(new Color3F(0.0F, 0.0F, 0.0F), color4FData.getColor3F(0.0F, 0.0F, PixelTransformer.DEFAULT));
		assertEquals(new Color3F(1.0F, 0.0F, 1.0F), color4FData.getColor3F(1.0F, 0.0F, PixelTransformer.DEFAULT));
		assertEquals(new Color3F(0.0F, 0.0F, 0.0F), color4FData.getColor3F(0.0F, 1.0F, PixelTransformer.DEFAULT));
		assertEquals(new Color3F(0.0F, 1.0F, 0.0F), color4FData.getColor3F(1.0F, 1.0F, PixelTransformer.DEFAULT));
		
		assertEquals(Color3F.BLACK, color4FData.getColor3F(-0.1F, +0.0F, PixelTransformer.DEFAULT));
		assertEquals(Color3F.BLACK, color4FData.getColor3F(+0.0F, -0.1F, PixelTransformer.DEFAULT));
		assertEquals(Color3F.BLACK, color4FData.getColor3F(+2.0F, +0.0F, PixelTransformer.DEFAULT));
		assertEquals(Color3F.BLACK, color4FData.getColor3F(+0.0F, +2.0F, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> color4FData.getColor3F(0.0F, 0.0F, null));
	}
	
	@Test
	public void testGetColor3FInt() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color3F.BLACK, color4FData.getColor3F(-1));
		assertEquals(Color3F.WHITE, color4FData.getColor3F(+0));
		assertEquals(Color3F.BLACK, color4FData.getColor3F(+1));
	}
	
	@Test
	public void testGetColor3FIntInt() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color3F.BLACK, color4FData.getColor3F(-1, +0));
		assertEquals(Color3F.BLACK, color4FData.getColor3F(+0, -1));
		assertEquals(Color3F.WHITE, color4FData.getColor3F(+0, +0));
		assertEquals(Color3F.BLACK, color4FData.getColor3F(+1, +0));
		assertEquals(Color3F.BLACK, color4FData.getColor3F(+0, +1));
	}
	
	@Test
	public void testGetColor3FIntIntPixelTransformer() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color3F.BLACK, color4FData.getColor3F(-1, +0, PixelTransformer.DEFAULT));
		assertEquals(Color3F.BLACK, color4FData.getColor3F(+0, -1, PixelTransformer.DEFAULT));
		assertEquals(Color3F.WHITE, color4FData.getColor3F(+0, +0, PixelTransformer.DEFAULT));
		assertEquals(Color3F.BLACK, color4FData.getColor3F(+1, +0, PixelTransformer.DEFAULT));
		assertEquals(Color3F.BLACK, color4FData.getColor3F(+0, +1, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> color4FData.getColor3F(0, 0, null));
	}
	
	@Test
	public void testGetColor3FIntPixelTransformer() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color3F.BLACK, color4FData.getColor3F(-1, PixelTransformer.DEFAULT));
		assertEquals(Color3F.WHITE, color4FData.getColor3F(+0, PixelTransformer.DEFAULT));
		assertEquals(Color3F.BLACK, color4FData.getColor3F(+1, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> color4FData.getColor3F(0, null));
	}
	
	@Test
	public void testGetColor3IDoubleDouble() {
		final
		Color4FData color4FData = new Color4FData(2, 2);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 1.0F), 0);
		color4FData.setColor4F(new Color4F(1.0F, 0.0F, 1.0F, 1.0F), 1);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 1.0F), 2);
		color4FData.setColor4F(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), 3);
		
		assertEquals(new Color3I(63, 63, 63), color4FData.getColor3I(0.5D, 0.5D));
		
		assertEquals(new Color3I(  0, 0,   0), color4FData.getColor3I(0.0D, 0.5D));
		assertEquals(new Color3I(127, 0, 127), color4FData.getColor3I(0.5D, 0.0D));
		
		assertEquals(new Color3I(  0,   0,   0), color4FData.getColor3I(0.0D, 0.0D));
		assertEquals(new Color3I(255,   0, 255), color4FData.getColor3I(1.0D, 0.0D));
		assertEquals(new Color3I(  0,   0,   0), color4FData.getColor3I(0.0D, 1.0D));
		assertEquals(new Color3I(  0, 255,   0), color4FData.getColor3I(1.0D, 1.0D));
		
		assertEquals(Color3I.BLACK, color4FData.getColor3I(-0.1D, +0.0D));
		assertEquals(Color3I.BLACK, color4FData.getColor3I(+0.0D, -0.1D));
		assertEquals(Color3I.BLACK, color4FData.getColor3I(+2.0D, +0.0D));
		assertEquals(Color3I.BLACK, color4FData.getColor3I(+0.0D, +2.0D));
	}
	
	@Test
	public void testGetColor3IDoubleDoublePixelTransformer() {
		final
		Color4FData color4FData = new Color4FData(2, 2);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 1.0F), 0);
		color4FData.setColor4F(new Color4F(1.0F, 0.0F, 1.0F, 1.0F), 1);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 1.0F), 2);
		color4FData.setColor4F(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), 3);
		
		assertEquals(new Color3I(63, 63, 63), color4FData.getColor3I(0.5D, 0.5D, PixelTransformer.DEFAULT));
		
		assertEquals(new Color3I(  0, 0,   0), color4FData.getColor3I(0.0D, 0.5D, PixelTransformer.DEFAULT));
		assertEquals(new Color3I(127, 0, 127), color4FData.getColor3I(0.5D, 0.0D, PixelTransformer.DEFAULT));
		
		assertEquals(new Color3I(  0,   0,   0), color4FData.getColor3I(0.0D, 0.0D, PixelTransformer.DEFAULT));
		assertEquals(new Color3I(255,   0, 255), color4FData.getColor3I(1.0D, 0.0D, PixelTransformer.DEFAULT));
		assertEquals(new Color3I(  0,   0,   0), color4FData.getColor3I(0.0D, 1.0D, PixelTransformer.DEFAULT));
		assertEquals(new Color3I(  0, 255,   0), color4FData.getColor3I(1.0D, 1.0D, PixelTransformer.DEFAULT));
		
		assertEquals(Color3I.BLACK, color4FData.getColor3I(-0.1D, +0.0D, PixelTransformer.DEFAULT));
		assertEquals(Color3I.BLACK, color4FData.getColor3I(+0.0D, -0.1D, PixelTransformer.DEFAULT));
		assertEquals(Color3I.BLACK, color4FData.getColor3I(+2.0D, +0.0D, PixelTransformer.DEFAULT));
		assertEquals(Color3I.BLACK, color4FData.getColor3I(+0.0D, +2.0D, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> color4FData.getColor3I(0.0D, 0.0D, null));
	}
	
	@Test
	public void testGetColor3IInt() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color3I.BLACK, color4FData.getColor3I(-1));
		assertEquals(Color3I.WHITE, color4FData.getColor3I(+0));
		assertEquals(Color3I.BLACK, color4FData.getColor3I(+1));
	}
	
	@Test
	public void testGetColor3IIntInt() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color3I.BLACK, color4FData.getColor3I(-1, +0));
		assertEquals(Color3I.BLACK, color4FData.getColor3I(+0, -1));
		assertEquals(Color3I.WHITE, color4FData.getColor3I(+0, +0));
		assertEquals(Color3I.BLACK, color4FData.getColor3I(+1, +0));
		assertEquals(Color3I.BLACK, color4FData.getColor3I(+0, +1));
	}
	
	@Test
	public void testGetColor3IIntIntPixelTransformer() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color3I.BLACK, color4FData.getColor3I(-1, +0, PixelTransformer.DEFAULT));
		assertEquals(Color3I.BLACK, color4FData.getColor3I(+0, -1, PixelTransformer.DEFAULT));
		assertEquals(Color3I.WHITE, color4FData.getColor3I(+0, +0, PixelTransformer.DEFAULT));
		assertEquals(Color3I.BLACK, color4FData.getColor3I(+1, +0, PixelTransformer.DEFAULT));
		assertEquals(Color3I.BLACK, color4FData.getColor3I(+0, +1, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> color4FData.getColor3I(0, 0, null));
	}
	
	@Test
	public void testGetColor3IIntPixelTransformer() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color3I.BLACK, color4FData.getColor3I(-1, PixelTransformer.DEFAULT));
		assertEquals(Color3I.WHITE, color4FData.getColor3I(+0, PixelTransformer.DEFAULT));
		assertEquals(Color3I.BLACK, color4FData.getColor3I(+1, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> color4FData.getColor3I(0, null));
	}
	
	@Test
	public void testGetColor4DDoubleDouble() {
		final
		Color4FData color4FData = new Color4FData(2, 2);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), 0);
		color4FData.setColor4F(new Color4F(1.0F, 0.0F, 1.0F, 0.0F), 1);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), 2);
		color4FData.setColor4F(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), 3);
		
		assertEquals(new Color4D(0.25D, 0.25D, 0.25D, 0.25D), color4FData.getColor4D(0.5D, 0.5D));
		
		assertEquals(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), color4FData.getColor4D(0.0D, 0.5D));
		assertEquals(new Color4D(0.5D, 0.0D, 0.5D, 0.0D), color4FData.getColor4D(0.5D, 0.0D));
		
		assertEquals(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), color4FData.getColor4D(0.0D, 0.0D));
		assertEquals(new Color4D(1.0D, 0.0D, 1.0D, 0.0D), color4FData.getColor4D(1.0D, 0.0D));
		assertEquals(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), color4FData.getColor4D(0.0D, 1.0D));
		assertEquals(new Color4D(0.0D, 1.0D, 0.0D, 1.0D), color4FData.getColor4D(1.0D, 1.0D));
		
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(-0.1D, +0.0D));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(+0.0D, -0.1D));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(+2.0D, +0.0D));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(+0.0D, +2.0D));
	}
	
	@Test
	public void testGetColor4DDoubleDoublePixelTransformer() {
		final
		Color4FData color4FData = new Color4FData(2, 2);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), 0);
		color4FData.setColor4F(new Color4F(1.0F, 0.0F, 1.0F, 0.0F), 1);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), 2);
		color4FData.setColor4F(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), 3);
		
		assertEquals(new Color4D(0.25D, 0.25D, 0.25D, 0.25D), color4FData.getColor4D(0.5D, 0.5D, PixelTransformer.DEFAULT));
		
		assertEquals(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), color4FData.getColor4D(0.0D, 0.5D, PixelTransformer.DEFAULT));
		assertEquals(new Color4D(0.5D, 0.0D, 0.5D, 0.0D), color4FData.getColor4D(0.5D, 0.0D, PixelTransformer.DEFAULT));
		
		assertEquals(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), color4FData.getColor4D(0.0D, 0.0D, PixelTransformer.DEFAULT));
		assertEquals(new Color4D(1.0D, 0.0D, 1.0D, 0.0D), color4FData.getColor4D(1.0D, 0.0D, PixelTransformer.DEFAULT));
		assertEquals(new Color4D(0.0D, 0.0D, 0.0D, 0.0D), color4FData.getColor4D(0.0D, 1.0D, PixelTransformer.DEFAULT));
		assertEquals(new Color4D(0.0D, 1.0D, 0.0D, 1.0D), color4FData.getColor4D(1.0D, 1.0D, PixelTransformer.DEFAULT));
		
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(-0.1D, +0.0D, PixelTransformer.DEFAULT));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(+0.0D, -0.1D, PixelTransformer.DEFAULT));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(+2.0D, +0.0D, PixelTransformer.DEFAULT));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(+0.0D, +2.0D, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> color4FData.getColor4D(0.0D, 0.0D, null));
	}
	
	@Test
	public void testGetColor4DInt() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(-1));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(+0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(+1));
	}
	
	@Test
	public void testGetColor4DIntInt() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(-1, +0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(+0, -1));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(+0, +0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(+1, +0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(+0, +1));
	}
	
	@Test
	public void testGetColor4DIntIntPixelTransformer() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(-1, +0, PixelTransformer.DEFAULT));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(+0, -1, PixelTransformer.DEFAULT));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(+0, +0, PixelTransformer.DEFAULT));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(+1, +0, PixelTransformer.DEFAULT));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(+0, +1, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> color4FData.getColor4D(0, 0, null));
	}
	
	@Test
	public void testGetColor4DIntPixelTransformer() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(-1, PixelTransformer.DEFAULT));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(+0, PixelTransformer.DEFAULT));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(+1, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> color4FData.getColor4D(0, null));
	}
	
	@Test
	public void testGetColor4FFloatFloat() {
		final
		Color4FData color4FData = new Color4FData(2, 2);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), 0);
		color4FData.setColor4F(new Color4F(1.0F, 0.0F, 1.0F, 0.0F), 1);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), 2);
		color4FData.setColor4F(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), 3);
		
		assertEquals(new Color4F(0.25F, 0.25F, 0.25F, 0.25F), color4FData.getColor4F(0.5F, 0.5F));
		
		assertEquals(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), color4FData.getColor4F(0.0F, 0.5F));
		assertEquals(new Color4F(0.5F, 0.0F, 0.5F, 0.0F), color4FData.getColor4F(0.5F, 0.0F));
		
		assertEquals(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), color4FData.getColor4F(0.0F, 0.0F));
		assertEquals(new Color4F(1.0F, 0.0F, 1.0F, 0.0F), color4FData.getColor4F(1.0F, 0.0F));
		assertEquals(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), color4FData.getColor4F(0.0F, 1.0F));
		assertEquals(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), color4FData.getColor4F(1.0F, 1.0F));
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(-0.1F, +0.0F));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(+0.0F, -0.1F));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(+2.0F, +0.0F));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(+0.0F, +2.0F));
	}
	
	@Test
	public void testGetColor4FFloatFloatPixelTransformer() {
		final
		Color4FData color4FData = new Color4FData(2, 2);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), 0);
		color4FData.setColor4F(new Color4F(1.0F, 0.0F, 1.0F, 0.0F), 1);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), 2);
		color4FData.setColor4F(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), 3);
		
		assertEquals(new Color4F(0.25F, 0.25F, 0.25F, 0.25F), color4FData.getColor4F(0.5F, 0.5F, PixelTransformer.DEFAULT));
		
		assertEquals(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), color4FData.getColor4F(0.0F, 0.5F, PixelTransformer.DEFAULT));
		assertEquals(new Color4F(0.5F, 0.0F, 0.5F, 0.0F), color4FData.getColor4F(0.5F, 0.0F, PixelTransformer.DEFAULT));
		
		assertEquals(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), color4FData.getColor4F(0.0F, 0.0F, PixelTransformer.DEFAULT));
		assertEquals(new Color4F(1.0F, 0.0F, 1.0F, 0.0F), color4FData.getColor4F(1.0F, 0.0F, PixelTransformer.DEFAULT));
		assertEquals(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), color4FData.getColor4F(0.0F, 1.0F, PixelTransformer.DEFAULT));
		assertEquals(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), color4FData.getColor4F(1.0F, 1.0F, PixelTransformer.DEFAULT));
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(-0.1F, +0.0F, PixelTransformer.DEFAULT));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(+0.0F, -0.1F, PixelTransformer.DEFAULT));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(+2.0F, +0.0F, PixelTransformer.DEFAULT));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(+0.0F, +2.0F, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> color4FData.getColor4F(0.0F, 0.0F, null));
	}
	
	@Test
	public void testGetColor4FInt() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(-1));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(+0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(+1));
	}
	
	@Test
	public void testGetColor4FIntInt() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(-1, +0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(+0, -1));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(+0, +0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(+1, +0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(+0, +1));
	}
	
	@Test
	public void testGetColor4FIntIntPixelTransformer() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(-1, +0, PixelTransformer.DEFAULT));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(+0, -1, PixelTransformer.DEFAULT));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(+0, +0, PixelTransformer.DEFAULT));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(+1, +0, PixelTransformer.DEFAULT));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(+0, +1, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> color4FData.getColor4F(0, 0, null));
	}
	
	@Test
	public void testGetColor4FIntPixelTransformer() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(-1, PixelTransformer.DEFAULT));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(+0, PixelTransformer.DEFAULT));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(+1, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> color4FData.getColor4F(0, null));
	}
	
	@Test
	public void testGetColor4IDoubleDouble() {
		final
		Color4FData color4FData = new Color4FData(2, 2);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), 0);
		color4FData.setColor4F(new Color4F(1.0F, 0.0F, 1.0F, 0.0F), 1);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), 2);
		color4FData.setColor4F(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), 3);
		
		assertEquals(new Color4I(63, 63, 63, 63), color4FData.getColor4I(0.5D, 0.5D));
		
		assertEquals(new Color4I(  0, 0,   0, 0), color4FData.getColor4I(0.0D, 0.5D));
		assertEquals(new Color4I(127, 0, 127, 0), color4FData.getColor4I(0.5D, 0.0D));
		
		assertEquals(new Color4I(  0,   0,   0,   0), color4FData.getColor4I(0.0D, 0.0D));
		assertEquals(new Color4I(255,   0, 255,   0), color4FData.getColor4I(1.0D, 0.0D));
		assertEquals(new Color4I(  0,   0,   0,   0), color4FData.getColor4I(0.0D, 1.0D));
		assertEquals(new Color4I(  0, 255,   0, 255), color4FData.getColor4I(1.0D, 1.0D));
		
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(-0.1D, +0.0D));
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(+0.0D, -0.1D));
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(+2.0D, +0.0D));
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(+0.0D, +2.0D));
	}
	
	@Test
	public void testGetColor4IDoubleDoublePixelTransformer() {
		final
		Color4FData color4FData = new Color4FData(2, 2);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), 0);
		color4FData.setColor4F(new Color4F(1.0F, 0.0F, 1.0F, 0.0F), 1);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), 2);
		color4FData.setColor4F(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), 3);
		
		assertEquals(new Color4I(63, 63, 63, 63), color4FData.getColor4I(0.5D, 0.5D, PixelTransformer.DEFAULT));
		
		assertEquals(new Color4I(  0, 0,   0, 0), color4FData.getColor4I(0.0D, 0.5D, PixelTransformer.DEFAULT));
		assertEquals(new Color4I(127, 0, 127, 0), color4FData.getColor4I(0.5D, 0.0D, PixelTransformer.DEFAULT));
		
		assertEquals(new Color4I(  0,   0,   0,   0), color4FData.getColor4I(0.0D, 0.0D, PixelTransformer.DEFAULT));
		assertEquals(new Color4I(255,   0, 255,   0), color4FData.getColor4I(1.0D, 0.0D, PixelTransformer.DEFAULT));
		assertEquals(new Color4I(  0,   0,   0,   0), color4FData.getColor4I(0.0D, 1.0D, PixelTransformer.DEFAULT));
		assertEquals(new Color4I(  0, 255,   0, 255), color4FData.getColor4I(1.0D, 1.0D, PixelTransformer.DEFAULT));
		
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(-0.1D, +0.0D, PixelTransformer.DEFAULT));
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(+0.0D, -0.1D, PixelTransformer.DEFAULT));
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(+2.0D, +0.0D, PixelTransformer.DEFAULT));
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(+0.0D, +2.0D, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> color4FData.getColor4I(0.0D, 0.0D, null));
	}
	
	@Test
	public void testGetColor4IInt() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(-1));
		assertEquals(Color4I.WHITE, color4FData.getColor4I(+0));
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(+1));
	}
	
	@Test
	public void testGetColor4IIntInt() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(-1, +0));
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(+0, -1));
		assertEquals(Color4I.WHITE, color4FData.getColor4I(+0, +0));
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(+1, +0));
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(+0, +1));
	}
	
	@Test
	public void testGetColor4IIntIntPixelTransformer() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(-1, +0, PixelTransformer.DEFAULT));
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(+0, -1, PixelTransformer.DEFAULT));
		assertEquals(Color4I.WHITE, color4FData.getColor4I(+0, +0, PixelTransformer.DEFAULT));
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(+1, +0, PixelTransformer.DEFAULT));
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(+0, +1, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> color4FData.getColor4I(0, 0, null));
	}
	
	@Test
	public void testGetColor4IIntPixelTransformer() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(-1, PixelTransformer.DEFAULT));
		assertEquals(Color4I.WHITE, color4FData.getColor4I(+0, PixelTransformer.DEFAULT));
		assertEquals(Color4I.TRANSPARENT, color4FData.getColor4I(+1, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> color4FData.getColor4I(0, null));
	}
	
	@Test
	public void testGetColorARGBDoubleDouble() {
		final
		Color4FData color4FData = new Color4FData(2, 2);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), 0);
		color4FData.setColor4F(new Color4F(1.0F, 0.0F, 1.0F, 0.0F), 1);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), 2);
		color4FData.setColor4F(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), 3);
		
		assertEquals(Color4I.toIntARGB(64, 64, 64, 64), color4FData.getColorARGB(0.5D, 0.5D));
		
		assertEquals(Color4I.toIntARGB(  0, 0,   0, 0), color4FData.getColorARGB(0.0D, 0.5D));
		assertEquals(Color4I.toIntARGB(128, 0, 128, 0), color4FData.getColorARGB(0.5D, 0.0D));
		
		assertEquals(Color4I.toIntARGB(  0,   0,   0,   0), color4FData.getColorARGB(0.0D, 0.0D));
		assertEquals(Color4I.toIntARGB(255,   0, 255,   0), color4FData.getColorARGB(1.0D, 0.0D));
		assertEquals(Color4I.toIntARGB(  0,   0,   0,   0), color4FData.getColorARGB(0.0D, 1.0D));
		assertEquals(Color4I.toIntARGB(  0, 255,   0, 255), color4FData.getColorARGB(1.0D, 1.0D));
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4FData.getColorARGB(-0.1D, +0.0D));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4FData.getColorARGB(+0.0D, -0.1D));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4FData.getColorARGB(+2.0D, +0.0D));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4FData.getColorARGB(+0.0D, +2.0D));
	}
	
	@Test
	public void testGetColorARGBFloatFloat() {
		final
		Color4FData color4FData = new Color4FData(2, 2);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), 0);
		color4FData.setColor4F(new Color4F(1.0F, 0.0F, 1.0F, 0.0F), 1);
		color4FData.setColor4F(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), 2);
		color4FData.setColor4F(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), 3);
		
		assertEquals(Color4I.toIntARGB(64, 64, 64, 64), color4FData.getColorARGB(0.5F, 0.5F));
		
		assertEquals(Color4I.toIntARGB(  0, 0,   0, 0), color4FData.getColorARGB(0.0F, 0.5F));
		assertEquals(Color4I.toIntARGB(128, 0, 128, 0), color4FData.getColorARGB(0.5F, 0.0F));
		
		assertEquals(Color4I.toIntARGB(  0,   0,   0,   0), color4FData.getColorARGB(0.0F, 0.0F));
		assertEquals(Color4I.toIntARGB(255,   0, 255,   0), color4FData.getColorARGB(1.0F, 0.0F));
		assertEquals(Color4I.toIntARGB(  0,   0,   0,   0), color4FData.getColorARGB(0.0F, 1.0F));
		assertEquals(Color4I.toIntARGB(  0, 255,   0, 255), color4FData.getColorARGB(1.0F, 1.0F));
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4FData.getColorARGB(-0.1F, +0.0F));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4FData.getColorARGB(+0.0F, -0.1F));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4FData.getColorARGB(+2.0F, +0.0F));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4FData.getColorARGB(+0.0F, +2.0F));
	}
	
	@Test
	public void testGetColorARGBInt() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4FData.getColorARGB(-1));
		assertEquals(Color4I.WHITE_A_R_G_B, color4FData.getColorARGB(+0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4FData.getColorARGB(+1));
	}
	
	@Test
	public void testGetColorARGBIntInt() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4FData.getColorARGB(-1, +0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4FData.getColorARGB(+0, -1));
		assertEquals(Color4I.WHITE_A_R_G_B, color4FData.getColorARGB(+0, +0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4FData.getColorARGB(+1, +0));
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, color4FData.getColorARGB(+0, +1));
	}
	
	@Test
	public void testGetDataFactory() {
		final Color4FData color4FData = new Color4FData();
		
		final DataFactory dataFactory = color4FData.getDataFactory();
		
		assertTrue(dataFactory instanceof Color4FDataFactory);
	}
	
	@Test
	public void testGetResolution() {
		final Color4FData color4FData = new Color4FData(2, 4);
		
		assertEquals(8, color4FData.getResolution());
	}
	
	@Test
	public void testGetResolutionX() {
		final Color4FData color4FData = new Color4FData(2, 4);
		
		assertEquals(2, color4FData.getResolutionX());
	}
	
	@Test
	public void testGetResolutionY() {
		final Color4FData color4FData = new Color4FData(2, 4);
		
		assertEquals(4, color4FData.getResolutionY());
	}
	
	@Test
	public void testHasChangeBegun() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertFalse(color4FData.hasChangeBegun());
		
		color4FData.setChangeHistoryEnabled(true);
		
		assertFalse(color4FData.hasChangeBegun());
		
		color4FData.changeBegin();
		
		assertTrue(color4FData.hasChangeBegun());
		
		color4FData.changeEnd();
		
		assertFalse(color4FData.hasChangeBegun());
	}
	
	@Test
	public void testHashCode() {
		final Color4FData a = new Color4FData(100, 100);
		final Color4FData b = new Color4FData(100, 100);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
		
		a.setChangeHistoryEnabled(true);
		b.setChangeHistoryEnabled(true);
		
		a.setColor4F(Color4F.BLACK, 0);
		b.setColor4F(Color4F.BLACK, 0);
		
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
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertFalse(color4FData.isChangeHistoryEnabled());
		
		color4FData.setChangeHistoryEnabled(true);
		
		assertTrue(color4FData.isChangeHistoryEnabled());
		
		color4FData.setChangeHistoryEnabled(false);
		
		assertFalse(color4FData.isChangeHistoryEnabled());
	}
	
	@Test
	public void testPixelChangeConstructor() {
		final PixelChange pixelChange = new PixelChange(Color4F.BLACK, Color4F.WHITE, 1);
		
		assertEquals(Color4F.BLACK, pixelChange.getColorRedo());
		assertEquals(Color4F.WHITE, pixelChange.getColorUndo());
		
		assertEquals(1, pixelChange.getIndex());
		
		assertThrows(NullPointerException.class, () -> new PixelChange(Color4F.BLACK, null, 0));
		assertThrows(NullPointerException.class, () -> new PixelChange(null, Color4F.BLACK, 0));
		
		assertThrows(IllegalArgumentException.class, () -> new PixelChange(Color4F.BLACK, Color4F.BLACK, -1));
	}
	
	@Test
	public void testPixelChangeEquals() {
		final PixelChange a = new PixelChange(Color4F.BLACK, Color4F.WHITE, 1);
		final PixelChange b = new PixelChange(Color4F.BLACK, Color4F.WHITE, 1);
		final PixelChange c = new PixelChange(Color4F.BLACK, Color4F.WHITE, 2);
		final PixelChange d = new PixelChange(Color4F.BLACK, Color4F.BLACK, 1);
		final PixelChange e = new PixelChange(Color4F.WHITE, Color4F.WHITE, 1);
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
		final PixelChange a = new PixelChange(Color4F.BLACK, Color4F.WHITE, 1);
		final PixelChange b = new PixelChange(Color4F.BLACK, Color4F.WHITE, 1);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testPixelChangeRedoAndUndo() {
		final Color4DData color4DData = new Color4DData(1, 1);
		final Color4FData color4FData = new Color4FData(1, 1);
		
		final PixelChange pixelChange = new PixelChange(Color4F.BLACK, Color4F.WHITE, 0);
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		pixelChange.redo(color4DData);
		pixelChange.redo(color4FData);
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		assertEquals(Color4F.BLACK, color4FData.getColor4F(0));
		
		pixelChange.undo(color4DData);
		pixelChange.undo(color4FData);
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		assertThrows(NullPointerException.class, () -> pixelChange.redo(null));
		assertThrows(NullPointerException.class, () -> pixelChange.undo(null));
	}
	
	@Test
	public void testRedoAndUndo() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertFalse(color4FData.redo());
		assertFalse(color4FData.undo());
		
		color4FData.setChangeHistoryEnabled(true);
		
		assertFalse(color4FData.redo());
		assertFalse(color4FData.undo());
		
		color4FData.setColor4F(Color4F.BLACK, 0);
		
		assertTrue(color4FData.undo());
		assertTrue(color4FData.redo());
	}
	
	@Test
	public void testRotateDoubleBoolean() {
		final
		Color4FData color4FData = new Color4FData(5, 5, Color4D.WHITE);
		color4FData.setChangeHistoryEnabled(true);
		
		assertFalse(color4FData.rotate(+0.0D, false));
		assertFalse(color4FData.rotate(-0.0D, false));
		
		assertFalse(color4FData.rotate(+360.0D, false));
		assertFalse(color4FData.rotate(-360.0D, false));
		
		assertFalse(color4FData.rotate(Math.toRadians(+360.0D), true));
		assertFalse(color4FData.rotate(Math.toRadians(-360.0D), true));
		
		assertEquals(5, color4FData.getResolutionX());
		assertEquals(5, color4FData.getResolutionY());
		
		assertEquals(Color4D.WHITE, color4FData.getColor4D(0, 0));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(1, 0));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(2, 0));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(3, 0));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(4, 0));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(0, 1));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(1, 1));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(2, 1));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(3, 1));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(4, 1));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(0, 2));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(1, 2));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(2, 2));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(3, 2));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(4, 2));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(0, 3));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(1, 3));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(2, 3));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(3, 3));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(4, 3));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(0, 4));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(1, 4));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(2, 4));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(3, 4));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(4, 4));
		
		assertTrue(color4FData.rotate(45.0D, false));
		
		assertEquals(7, color4FData.getResolutionX());
		assertEquals(7, color4FData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(0, 0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(1, 0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(2, 0));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(4, 0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(5, 0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(6, 0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(0, 1));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(1, 1));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(2, 1));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 1));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(4, 1));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(5, 1));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(6, 1));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(0, 2));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(1, 2));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(2, 2));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 2));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(4, 2));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(5, 2));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(6, 2));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(0, 3));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(1, 3));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(2, 3));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 3));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(4, 3));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(5, 3));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(6, 3));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(0, 4));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(1, 4));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(2, 4));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 4));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(4, 4));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(5, 4));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(6, 4));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(0, 5));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(1, 5));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(2, 5));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 5));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(4, 5));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(5, 5));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(6, 5));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(0, 6));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(1, 6));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(2, 6));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 6));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(4, 6));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(5, 6));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(6, 6));
		
		assertTrue(color4FData.undo());
		
		assertEquals(5, color4FData.getResolutionX());
		assertEquals(5, color4FData.getResolutionY());
		
		assertEquals(Color4D.WHITE, color4FData.getColor4D(0, 0));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(1, 0));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(2, 0));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(3, 0));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(4, 0));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(0, 1));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(1, 1));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(2, 1));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(3, 1));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(4, 1));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(0, 2));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(1, 2));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(2, 2));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(3, 2));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(4, 2));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(0, 3));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(1, 3));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(2, 3));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(3, 3));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(4, 3));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(0, 4));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(1, 4));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(2, 4));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(3, 4));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(4, 4));
		
		color4FData.changeBegin();
		
		assertTrue(color4FData.rotate(45.0D, false));
		
		color4FData.changeEnd();
		
		assertEquals(7, color4FData.getResolutionX());
		assertEquals(7, color4FData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(0, 0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(1, 0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(2, 0));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(4, 0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(5, 0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(6, 0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(0, 1));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(1, 1));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(2, 1));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 1));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(4, 1));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(5, 1));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(6, 1));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(0, 2));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(1, 2));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(2, 2));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 2));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(4, 2));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(5, 2));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(6, 2));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(0, 3));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(1, 3));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(2, 3));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 3));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(4, 3));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(5, 3));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(6, 3));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(0, 4));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(1, 4));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(2, 4));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 4));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(4, 4));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(5, 4));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(6, 4));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(0, 5));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(1, 5));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(2, 5));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 5));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(4, 5));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(5, 5));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(6, 5));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(0, 6));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(1, 6));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(2, 6));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 6));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(4, 6));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(5, 6));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(6, 6));
		
		assertTrue(color4FData.undo());
		
		color4FData.setChangeHistoryEnabled(false);
		
		assertEquals(5, color4FData.getResolutionX());
		assertEquals(5, color4FData.getResolutionY());
		
		assertEquals(Color4D.WHITE, color4FData.getColor4D(0, 0));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(1, 0));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(2, 0));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(3, 0));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(4, 0));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(0, 1));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(1, 1));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(2, 1));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(3, 1));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(4, 1));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(0, 2));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(1, 2));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(2, 2));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(3, 2));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(4, 2));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(0, 3));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(1, 3));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(2, 3));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(3, 3));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(4, 3));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(0, 4));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(1, 4));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(2, 4));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(3, 4));
		assertEquals(Color4D.WHITE, color4FData.getColor4D(4, 4));
		
		assertTrue(color4FData.rotate(45.0D, false));
		
		assertEquals(7, color4FData.getResolutionX());
		assertEquals(7, color4FData.getResolutionY());
		
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(0, 0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(1, 0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(2, 0));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(4, 0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(5, 0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(6, 0));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(0, 1));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(1, 1));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(2, 1));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 1));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(4, 1));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(5, 1));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(6, 1));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(0, 2));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(1, 2));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(2, 2));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 2));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(4, 2));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(5, 2));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(6, 2));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(0, 3));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(1, 3));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(2, 3));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 3));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(4, 3));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(5, 3));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(6, 3));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(0, 4));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(1, 4));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(2, 4));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 4));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(4, 4));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(5, 4));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(6, 4));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(0, 5));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(1, 5));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(2, 5));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 5));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(4, 5));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(5, 5));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(6, 5));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(0, 6));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(1, 6));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(2, 6));
		assertEquals(Color4D.WHITE,       color4FData.getColor4D(3, 6));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(4, 6));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(5, 6));
		assertEquals(Color4D.TRANSPARENT, color4FData.getColor4D(6, 6));
		
		assertFalse(color4FData.undo());
	}
	
	@Test
	public void testRotateFloatBoolean() {
		final
		Color4FData color4FData = new Color4FData(5, 5, Color4F.WHITE);
		color4FData.setChangeHistoryEnabled(true);
		
		assertFalse(color4FData.rotate(+0.0F, false));
		assertFalse(color4FData.rotate(-0.0F, false));
		
		assertFalse(color4FData.rotate(+360.0F, false));
		assertFalse(color4FData.rotate(-360.0F, false));
		
		assertFalse(color4FData.rotate((float)(Math.toRadians(+360.0F)), true));
		assertFalse(color4FData.rotate((float)(Math.toRadians(-360.0F)), true));
		
		assertEquals(5, color4FData.getResolutionX());
		assertEquals(5, color4FData.getResolutionY());
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0, 0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1, 0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(2, 0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(3, 0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(4, 0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0, 1));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1, 1));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(2, 1));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(3, 1));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(4, 1));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0, 2));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1, 2));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(2, 2));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(3, 2));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(4, 2));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0, 3));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1, 3));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(2, 3));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(3, 3));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(4, 3));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0, 4));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1, 4));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(2, 4));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(3, 4));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(4, 4));
		
		assertTrue(color4FData.rotate(45.0F, false));
		
		assertEquals(7, color4FData.getResolutionX());
		assertEquals(7, color4FData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0, 0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1, 0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(2, 0));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(4, 0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(5, 0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(6, 0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0, 1));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1, 1));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(2, 1));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 1));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(4, 1));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(5, 1));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(6, 1));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0, 2));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(1, 2));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(2, 2));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 2));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(4, 2));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(5, 2));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(6, 2));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(0, 3));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(1, 3));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(2, 3));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 3));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(4, 3));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(5, 3));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(6, 3));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0, 4));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(1, 4));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(2, 4));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 4));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(4, 4));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(5, 4));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(6, 4));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0, 5));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1, 5));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(2, 5));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 5));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(4, 5));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(5, 5));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(6, 5));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0, 6));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1, 6));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(2, 6));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 6));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(4, 6));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(5, 6));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(6, 6));
		
		assertTrue(color4FData.undo());
		
		assertEquals(5, color4FData.getResolutionX());
		assertEquals(5, color4FData.getResolutionY());
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0, 0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1, 0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(2, 0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(3, 0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(4, 0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0, 1));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1, 1));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(2, 1));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(3, 1));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(4, 1));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0, 2));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1, 2));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(2, 2));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(3, 2));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(4, 2));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0, 3));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1, 3));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(2, 3));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(3, 3));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(4, 3));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0, 4));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1, 4));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(2, 4));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(3, 4));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(4, 4));
		
		color4FData.changeBegin();
		
		assertTrue(color4FData.rotate(45.0F, false));
		
		color4FData.changeEnd();
		
		assertEquals(7, color4FData.getResolutionX());
		assertEquals(7, color4FData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0, 0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1, 0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(2, 0));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(4, 0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(5, 0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(6, 0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0, 1));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1, 1));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(2, 1));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 1));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(4, 1));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(5, 1));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(6, 1));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0, 2));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(1, 2));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(2, 2));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 2));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(4, 2));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(5, 2));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(6, 2));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(0, 3));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(1, 3));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(2, 3));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 3));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(4, 3));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(5, 3));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(6, 3));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0, 4));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(1, 4));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(2, 4));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 4));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(4, 4));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(5, 4));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(6, 4));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0, 5));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1, 5));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(2, 5));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 5));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(4, 5));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(5, 5));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(6, 5));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0, 6));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1, 6));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(2, 6));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 6));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(4, 6));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(5, 6));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(6, 6));
		
		assertTrue(color4FData.undo());
		
		color4FData.setChangeHistoryEnabled(false);
		
		assertEquals(5, color4FData.getResolutionX());
		assertEquals(5, color4FData.getResolutionY());
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0, 0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1, 0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(2, 0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(3, 0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(4, 0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0, 1));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1, 1));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(2, 1));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(3, 1));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(4, 1));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0, 2));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1, 2));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(2, 2));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(3, 2));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(4, 2));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0, 3));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1, 3));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(2, 3));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(3, 3));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(4, 3));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0, 4));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1, 4));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(2, 4));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(3, 4));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(4, 4));
		
		assertTrue(color4FData.rotate(45.0F, false));
		
		assertEquals(7, color4FData.getResolutionX());
		assertEquals(7, color4FData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0, 0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1, 0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(2, 0));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(4, 0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(5, 0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(6, 0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0, 1));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1, 1));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(2, 1));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 1));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(4, 1));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(5, 1));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(6, 1));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0, 2));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(1, 2));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(2, 2));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 2));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(4, 2));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(5, 2));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(6, 2));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(0, 3));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(1, 3));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(2, 3));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 3));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(4, 3));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(5, 3));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(6, 3));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0, 4));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(1, 4));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(2, 4));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 4));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(4, 4));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(5, 4));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(6, 4));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0, 5));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1, 5));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(2, 5));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 5));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(4, 5));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(5, 5));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(6, 5));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0, 6));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1, 6));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(2, 6));
		assertEquals(Color4F.WHITE,       color4FData.getColor4F(3, 6));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(4, 6));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(5, 6));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(6, 6));
		
		assertFalse(color4FData.undo());
	}
	
	@Test
	public void testSave() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		final File directory = new File(String.format("./generated/%s", Long.toString(System.currentTimeMillis())));
		
		final File fileA = new File(directory, "Color4FData.jpg");
		final File fileB = new File(directory, "Color4FData.png");
		
		final File fileC = new File("Color4FData.txt");
		final File fileD = new File("");
		
		assertTrue(color4FData.save(fileA, "jpg"));
		assertTrue(color4FData.save(fileB, "png"));
		
		assertFalse(color4FData.save(fileC, "txt"));
		assertFalse(color4FData.save(fileD, "png"));
		
		assertThrows(NullPointerException.class, () -> color4FData.save(new File("./generated/Color4FData.png"), null));
		assertThrows(NullPointerException.class, () -> color4FData.save(null, "png"));
		
		fileA.delete();
		fileB.delete();
		
		directory.delete();
	}
	
	@Test
	public void testScaleDoubleDouble() {
		final Color4FData color4FData = new Color4FData(1, 1, Color4F.TRANSPARENT);
		
		assertFalse(color4FData.scale(0.0D, 1.0D));
		assertFalse(color4FData.scale(1.0D, 0.0D));
		assertFalse(color4FData.scale(1.0D, 1.0D));
		
		assertEquals(1, color4FData.getResolutionX());
		assertEquals(1, color4FData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0));
		
		assertTrue(color4FData.scale(1.0D, 2.0D));
		
		assertEquals(1, color4FData.getResolutionX());
		assertEquals(2, color4FData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1));
	}
	
	@Test
	public void testScaleFloatFloat() {
		final Color4FData color4FData = new Color4FData(1, 1, Color4F.TRANSPARENT);
		
		assertFalse(color4FData.scale(0.0F, 1.0F));
		assertFalse(color4FData.scale(1.0F, 0.0F));
		assertFalse(color4FData.scale(1.0F, 1.0F));
		
		assertEquals(1, color4FData.getResolutionX());
		assertEquals(1, color4FData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0));
		
		assertTrue(color4FData.scale(1.0F, 2.0F));
		
		assertEquals(1, color4FData.getResolutionX());
		assertEquals(2, color4FData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1));
	}
	
	@Test
	public void testScaleIntInt() {
		final Color4FData color4FData = new Color4FData(1, 1, Color4F.TRANSPARENT);
		
		assertFalse(color4FData.scale(0, 1));
		assertFalse(color4FData.scale(1, 0));
		assertFalse(color4FData.scale(Integer.MAX_VALUE, 2));
		assertFalse(color4FData.scale(2, Integer.MAX_VALUE));
		assertFalse(color4FData.scale(1, 1));
		
		assertEquals(1, color4FData.getResolutionX());
		assertEquals(1, color4FData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0));
		
		assertTrue(color4FData.scale(1, 2));
		
		assertEquals(1, color4FData.getResolutionX());
		assertEquals(2, color4FData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1));
		
		assertFalse(color4FData.undo());
		
		assertEquals(1, color4FData.getResolutionX());
		assertEquals(2, color4FData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1));
		
		color4FData.setChangeHistoryEnabled(true);
		
		assertTrue(color4FData.scale(2, 1));
		
		assertEquals(2, color4FData.getResolutionX());
		assertEquals(1, color4FData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1));
		
		assertTrue(color4FData.undo());
		
		assertEquals(1, color4FData.getResolutionX());
		assertEquals(2, color4FData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1));
		
		color4FData.changeBegin();
		
		assertTrue(color4FData.scale(2, 2));
		
		color4FData.changeEnd();
		
		assertEquals(2, color4FData.getResolutionX());
		assertEquals(2, color4FData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(2));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(3));
		
		assertTrue(color4FData.undo());
		
		assertEquals(1, color4FData.getResolutionX());
		assertEquals(2, color4FData.getResolutionY());
		
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(0));
		assertEquals(Color4F.TRANSPARENT, color4FData.getColor4F(1));
	}
	
	@Test
	public void testSetChangeHistoryEnabled() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertFalse(color4FData.setChangeHistoryEnabled(false));
		
		assertTrue(color4FData.setChangeHistoryEnabled(true));
		
		assertFalse(color4FData.setChangeHistoryEnabled(true));
		
		assertTrue(color4FData.setChangeHistoryEnabled(false));
	}
	
	@Test
	public void testSetColor3DColor3DInt() {
		final Color4FData color4FData = new Color4FData(1, 1, Color4F.WHITE);
		
		assertTrue(color4FData.setColor3D(Color3D.RED, 0));
		
		assertEquals(Color3D.RED, color4FData.getColor3D(0));
		
		assertThrows(NullPointerException.class, () -> color4FData.setColor3D(null, 0));
	}
	
	@Test
	public void testSetColor3DColor3DIntInt() {
		final Color4FData color4FData = new Color4FData(1, 1, Color4F.WHITE);
		
		assertTrue(color4FData.setColor3D(Color3D.RED, 0, 0));
		
		assertEquals(Color3D.RED, color4FData.getColor3D(0, 0));
		
		assertThrows(NullPointerException.class, () -> color4FData.setColor3D(null, 0, 0));
	}
	
	@Test
	public void testSetColor3FColor3FInt() {
		final Color4FData color4FData = new Color4FData(1, 1, Color4F.WHITE);
		
		assertTrue(color4FData.setColor3F(Color3F.RED, 0));
		
		assertEquals(Color3F.RED, color4FData.getColor3F(0));
		
		assertThrows(NullPointerException.class, () -> color4FData.setColor3F(null, 0));
	}
	
	@Test
	public void testSetColor3FColor3FIntInt() {
		final Color4FData color4FData = new Color4FData(1, 1, Color4F.WHITE);
		
		assertTrue(color4FData.setColor3F(Color3F.RED, 0, 0));
		
		assertEquals(Color3F.RED, color4FData.getColor3F(0, 0));
		
		assertThrows(NullPointerException.class, () -> color4FData.setColor3F(null, 0, 0));
	}
	
	@Test
	public void testSetColor3IColor3IInt() {
		final Color4FData color4FData = new Color4FData(1, 1, Color4F.WHITE);
		
		assertTrue(color4FData.setColor3I(Color3I.RED, 0));
		
		assertEquals(Color3I.RED, color4FData.getColor3I(0));
		
		assertThrows(NullPointerException.class, () -> color4FData.setColor3I(null, 0));
	}
	
	@Test
	public void testSetColor3IColor3IIntInt() {
		final Color4FData color4FData = new Color4FData(1, 1, Color4F.WHITE);
		
		assertTrue(color4FData.setColor3I(Color3I.RED, 0, 0));
		
		assertEquals(Color3I.RED, color4FData.getColor3I(0, 0));
		
		assertThrows(NullPointerException.class, () -> color4FData.setColor3I(null, 0, 0));
	}
	
	@Test
	public void testSetColor4DColor4DInt() {
		final Color4FData color4FData = new Color4FData(1, 1, Color4F.WHITE);
		
		assertTrue(color4FData.setColor4D(Color4D.RED, 0));
		
		assertEquals(Color4D.RED, color4FData.getColor4D(0));
		
		assertThrows(NullPointerException.class, () -> color4FData.setColor4D(null, 0));
	}
	
	@Test
	public void testSetColor4DColor4DIntInt() {
		final Color4FData color4FData = new Color4FData(1, 1, Color4F.WHITE);
		
		assertTrue(color4FData.setColor4D(Color4D.RED, 0, 0));
		
		assertEquals(Color4D.RED, color4FData.getColor4D(0, 0));
		
		assertThrows(NullPointerException.class, () -> color4FData.setColor4D(null, 0, 0));
	}
	
	@Test
	public void testSetColor4FColor4FInt() {
		final Color4FData color4FData = new Color4FData(1, 1, Color4F.WHITE);
		
		assertTrue(color4FData.setColor4F(Color4F.RED, 0));
		assertTrue(color4FData.setColor4F(Color4F.WHITE, 0));
		
		color4FData.setChangeHistoryEnabled(true);
		
		assertFalse(color4FData.setColor4F(Color4F.RED, -1));
		assertFalse(color4FData.setColor4F(Color4F.RED, +1));
		
		assertTrue(color4FData.setColor4F(Color4F.WHITE, 0));
		
		assertFalse(color4FData.undo());
		assertFalse(color4FData.redo());
		
		assertTrue(color4FData.setColor4F(Color4F.RED, 0));
		
		assertEquals(Color4F.RED, color4FData.getColor4F(0));
		
		assertTrue(color4FData.undo());
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		assertTrue(color4FData.redo());
		
		assertEquals(Color4F.RED, color4FData.getColor4F(0));
		
		assertTrue(color4FData.changeBegin());
		assertTrue(color4FData.setColor4F(Color4F.GREEN, 0));
		assertTrue(color4FData.changeEnd());
		
		assertEquals(Color4F.GREEN, color4FData.getColor4F(0));
		
		assertTrue(color4FData.undo());
		
		assertEquals(Color4F.RED, color4FData.getColor4F(0));
		
		assertTrue(color4FData.undo());
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		assertTrue(color4FData.redo());
		
		assertEquals(Color4F.RED, color4FData.getColor4F(0));
		
		assertTrue(color4FData.redo());
		
		assertEquals(Color4F.GREEN, color4FData.getColor4F(0));
		
		assertThrows(NullPointerException.class, () -> color4FData.setColor4F(null, 0));
	}
	
	@Test
	public void testSetColor4FColor4FIntInt() {
		final Color4FData color4FData = new Color4FData(1, 1, Color4F.WHITE);
		
		assertTrue(color4FData.setColor4F(Color4F.RED, 0, 0));
		assertTrue(color4FData.setColor4F(Color4F.WHITE, 0, 0));
		
		color4FData.setChangeHistoryEnabled(true);
		
		assertFalse(color4FData.setColor4F(Color4F.RED, -1, +0));
		assertFalse(color4FData.setColor4F(Color4F.RED, +0, -1));
		assertFalse(color4FData.setColor4F(Color4F.RED, +1, +0));
		assertFalse(color4FData.setColor4F(Color4F.RED, +0, +1));
		
		assertTrue(color4FData.setColor4F(Color4F.WHITE, 0, 0));
		
		assertFalse(color4FData.undo());
		assertFalse(color4FData.redo());
		
		assertTrue(color4FData.setColor4F(Color4F.RED, 0, 0));
		
		assertEquals(Color4F.RED, color4FData.getColor4F(0, 0));
		
		assertTrue(color4FData.undo());
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0, 0));
		
		assertTrue(color4FData.redo());
		
		assertEquals(Color4F.RED, color4FData.getColor4F(0, 0));
		
		assertTrue(color4FData.changeBegin());
		assertTrue(color4FData.setColor4F(Color4F.GREEN, 0, 0));
		assertTrue(color4FData.changeEnd());
		
		assertEquals(Color4F.GREEN, color4FData.getColor4F(0, 0));
		
		assertTrue(color4FData.undo());
		
		assertEquals(Color4F.RED, color4FData.getColor4F(0, 0));
		
		assertTrue(color4FData.undo());
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0, 0));
		
		assertTrue(color4FData.redo());
		
		assertEquals(Color4F.RED, color4FData.getColor4F(0, 0));
		
		assertTrue(color4FData.redo());
		
		assertEquals(Color4F.GREEN, color4FData.getColor4F(0, 0));
		
		assertThrows(NullPointerException.class, () -> color4FData.setColor4F(null, 0, 0));
	}
	
	@Test
	public void testSetColor4IColor4IInt() {
		final Color4FData color4FData = new Color4FData(1, 1, Color4F.WHITE);
		
		assertTrue(color4FData.setColor4I(Color4I.RED, 0));
		
		assertEquals(Color4I.RED, color4FData.getColor4I(0));
		
		assertThrows(NullPointerException.class, () -> color4FData.setColor4I(null, 0));
	}
	
	@Test
	public void testSetColor4IColor4IIntInt() {
		final Color4FData color4FData = new Color4FData(1, 1, Color4F.WHITE);
		
		assertTrue(color4FData.setColor4I(Color4I.RED, 0, 0));
		
		assertEquals(Color4I.RED, color4FData.getColor4I(0, 0));
		
		assertThrows(NullPointerException.class, () -> color4FData.setColor4I(null, 0, 0));
	}
	
	@Test
	public void testSetColorARGBIntInt() {
		final Color4FData color4FData = new Color4FData(1, 1, Color4F.WHITE);
		
		assertTrue(color4FData.setColorARGB(Color4I.RED_A_R_G_B, 0));
		
		assertEquals(Color4I.RED_A_R_G_B, color4FData.getColorARGB(0));
	}
	
	@Test
	public void testSetColorARGBIntIntInt() {
		final Color4FData color4FData = new Color4FData(1, 1, Color4F.WHITE);
		
		assertTrue(color4FData.setColorARGB(Color4I.RED_A_R_G_B, 0, 0));
		
		assertEquals(Color4I.RED_A_R_G_B, color4FData.getColorARGB(0, 0));
	}
	
	@Test
	public void testSetContent() {
		final Color4FData a = new Color4FData(1, 1, Color4F.WHITE);
		final Color4FData b = new Color4FData(1, 1, Color4F.BLACK);
		final Color4FData c = new Color4FData(2, 2, Color4F.BLACK);
		final Color4DData d = new Color4DData(3, 3, Color4D.GREEN);
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4F.WHITE, a.getColor4F(0));
		
		assertTrue(a.setContent(b));
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4F.BLACK, a.getColor4F(0));
		
		assertFalse(a.undo());
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4F.BLACK, a.getColor4F(0));
		
		a.setChangeHistoryEnabled(true);
		
		assertTrue(a.setContent(c));
		
		assertEquals(2, a.getResolutionX());
		assertEquals(2, a.getResolutionY());
		
		assertEquals(Color4F.BLACK, a.getColor4F(0));
		assertEquals(Color4F.BLACK, a.getColor4F(1));
		assertEquals(Color4F.BLACK, a.getColor4F(2));
		assertEquals(Color4F.BLACK, a.getColor4F(3));
		
		assertTrue(a.undo());
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4F.BLACK, a.getColor4F(0));
		
		a.changeBegin();
		
		assertTrue(a.setContent(c));
		
		a.changeEnd();
		
		assertEquals(2, a.getResolutionX());
		assertEquals(2, a.getResolutionY());
		
		assertEquals(Color4F.BLACK, a.getColor4F(0));
		assertEquals(Color4F.BLACK, a.getColor4F(1));
		assertEquals(Color4F.BLACK, a.getColor4F(2));
		assertEquals(Color4F.BLACK, a.getColor4F(3));
		
		assertTrue(a.undo());
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4F.BLACK, a.getColor4F(0));
		
		assertFalse(a.setContent(d));
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4F.BLACK, a.getColor4F(0));
		
		assertFalse(a.undo());
		
		assertEquals(1, a.getResolutionX());
		assertEquals(1, a.getResolutionY());
		
		assertEquals(Color4F.BLACK, a.getColor4F(0));
		
		assertThrows(NullPointerException.class, () -> a.setContent(null));
	}
	
	@Test
	public void testSetResolution() {
		final Color4FData color4FData = new Color4FData(1, 1, Color4F.BLACK);
		
		assertFalse(color4FData.setResolution(0, 1));
		assertFalse(color4FData.setResolution(1, 0));
		assertFalse(color4FData.setResolution(Integer.MAX_VALUE, 2));
		assertFalse(color4FData.setResolution(2, Integer.MAX_VALUE));
		assertFalse(color4FData.setResolution(1, 1));
		
		assertEquals(1, color4FData.getResolutionX());
		assertEquals(1, color4FData.getResolutionY());
		
		assertEquals(Color4F.BLACK, color4FData.getColor4F(0));
		
		assertTrue(color4FData.setResolution(1, 2));
		
		assertEquals(1, color4FData.getResolutionX());
		assertEquals(2, color4FData.getResolutionY());
		
		assertEquals(Color4F.BLACK, color4FData.getColor4F(0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1));
		
		assertFalse(color4FData.undo());
		
		assertEquals(1, color4FData.getResolutionX());
		assertEquals(2, color4FData.getResolutionY());
		
		assertEquals(Color4F.BLACK, color4FData.getColor4F(0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1));
		
		color4FData.setChangeHistoryEnabled(true);
		
		assertTrue(color4FData.setResolution(2, 1));
		
		assertEquals(2, color4FData.getResolutionX());
		assertEquals(1, color4FData.getResolutionY());
		
		assertEquals(Color4F.BLACK, color4FData.getColor4F(0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1));
		
		assertTrue(color4FData.undo());
		
		assertEquals(1, color4FData.getResolutionX());
		assertEquals(2, color4FData.getResolutionY());
		
		assertEquals(Color4F.BLACK, color4FData.getColor4F(0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1));
		
		color4FData.changeBegin();
		
		assertTrue(color4FData.setResolution(2, 2));
		
		color4FData.changeEnd();
		
		assertEquals(2, color4FData.getResolutionX());
		assertEquals(2, color4FData.getResolutionY());
		
		assertEquals(Color4F.BLACK, color4FData.getColor4F(0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(2));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(3));
		
		assertTrue(color4FData.undo());
		
		assertEquals(1, color4FData.getResolutionX());
		assertEquals(2, color4FData.getResolutionY());
		
		assertEquals(Color4F.BLACK, color4FData.getColor4F(0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(1));
	}
	
	@Test
	public void testSetResolutionX() {
		final Color4FData color4FData = new Color4FData(2, 2, Color4F.BLACK);
		
		assertFalse(color4FData.setResolutionX(0));
		assertFalse(color4FData.setResolutionX(Integer.MAX_VALUE));
		assertFalse(color4FData.setResolutionX(2));
		
		assertEquals(2, color4FData.getResolutionX());
		assertEquals(2, color4FData.getResolutionY());
	}
	
	@Test
	public void testSetResolutionY() {
		final Color4FData color4FData = new Color4FData(2, 2, Color4F.BLACK);
		
		assertFalse(color4FData.setResolutionY(0));
		assertFalse(color4FData.setResolutionY(Integer.MAX_VALUE));
		assertFalse(color4FData.setResolutionY(2));
		
		assertEquals(2, color4FData.getResolutionX());
		assertEquals(2, color4FData.getResolutionY());
	}
	
	@Test
	public void testStateChangeConstructor() {
		final StateChange stateChange = new StateChange(new Color4F[] {Color4F.BLACK}, new Color4F[] {Color4F.WHITE}, 1, 1, 1, 1);
		
		assertArrayEquals(new Color4F[] {Color4F.BLACK}, stateChange.getColorsRedo());
		assertArrayEquals(new Color4F[] {Color4F.WHITE}, stateChange.getColorsUndo());
		
		assertEquals(1, stateChange.getResolutionXRedo());
		assertEquals(1, stateChange.getResolutionXUndo());
		assertEquals(1, stateChange.getResolutionYRedo());
		assertEquals(1, stateChange.getResolutionYUndo());
		
		assertThrows(NullPointerException.class, () -> new StateChange(new Color4F[] {null}, new Color4F[] {Color4F.WHITE}, 1, 1, 1, 1));
		assertThrows(NullPointerException.class, () -> new StateChange(null, new Color4F[] {Color4F.WHITE}, 1, 1, 1, 1));
		assertThrows(NullPointerException.class, () -> new StateChange(new Color4F[] {Color4F.BLACK}, new Color4F[] {null}, 1, 1, 1, 1));
		assertThrows(NullPointerException.class, () -> new StateChange(new Color4F[] {Color4F.BLACK}, null, 1, 1, 1, 1));
		
		assertThrows(IllegalArgumentException.class, () -> new StateChange(new Color4F[] {Color4F.BLACK}, new Color4F[] {Color4F.WHITE}, 1, 1, 1, 0));
		assertThrows(IllegalArgumentException.class, () -> new StateChange(new Color4F[] {Color4F.BLACK}, new Color4F[] {Color4F.WHITE}, 1, 1, 0, 1));
		assertThrows(IllegalArgumentException.class, () -> new StateChange(new Color4F[] {Color4F.BLACK}, new Color4F[] {Color4F.WHITE}, 1, 0, 1, 1));
		assertThrows(IllegalArgumentException.class, () -> new StateChange(new Color4F[] {Color4F.BLACK}, new Color4F[] {Color4F.WHITE}, 0, 1, 1, 1));
	}
	
	@Test
	public void testStateChangeEquals() {
		final StateChange a = new StateChange(new Color4F[] {Color4F.BLACK}, new Color4F[] {Color4F.WHITE}, 1, 1, 1, 1);
		final StateChange b = new StateChange(new Color4F[] {Color4F.BLACK}, new Color4F[] {Color4F.WHITE}, 1, 1, 1, 1);
		final StateChange c = new StateChange(new Color4F[] {Color4F.BLACK}, new Color4F[] {Color4F.WHITE}, 1, 1, 1, 2);
		final StateChange d = new StateChange(new Color4F[] {Color4F.BLACK}, new Color4F[] {Color4F.WHITE}, 1, 1, 2, 1);
		final StateChange e = new StateChange(new Color4F[] {Color4F.BLACK}, new Color4F[] {Color4F.WHITE}, 1, 2, 1, 1);
		final StateChange f = new StateChange(new Color4F[] {Color4F.BLACK}, new Color4F[] {Color4F.WHITE}, 2, 1, 1, 1);
		final StateChange g = new StateChange(new Color4F[] {Color4F.BLACK}, new Color4F[] {Color4F.BLACK}, 1, 1, 1, 1);
		final StateChange h = new StateChange(new Color4F[] {Color4F.WHITE}, new Color4F[] {Color4F.WHITE}, 1, 1, 1, 1);
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
		final StateChange a = new StateChange(new Color4F[] {Color4F.BLACK}, new Color4F[] {Color4F.WHITE}, 1, 1, 1, 1);
		final StateChange b = new StateChange(new Color4F[] {Color4F.BLACK}, new Color4F[] {Color4F.WHITE}, 1, 1, 1, 1);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testStateChangeRedoAndUndo() {
		final Color4DData color4DData = new Color4DData(1, 1);
		final Color4FData color4FData = new Color4FData(1, 1);
		
		final StateChange stateChange = new StateChange(new Color4F[] {Color4F.BLACK, Color4F.BLACK, Color4F.BLACK, Color4F.BLACK}, new Color4F[] {Color4F.WHITE}, 2, 1, 2, 1);
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(1, color4DData.getResolutionY());
		
		assertEquals(1, color4FData.getResolutionX());
		assertEquals(1, color4FData.getResolutionY());
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		stateChange.redo(color4DData);
		stateChange.redo(color4FData);
		
		assertEquals(1, color4DData.getResolutionX());
		assertEquals(1, color4DData.getResolutionY());
		
		assertEquals(2, color4FData.getResolutionX());
		assertEquals(2, color4FData.getResolutionY());
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		assertEquals(Color4F.BLACK, color4FData.getColor4F(0));
		assertEquals(Color4F.BLACK, color4FData.getColor4F(1));
		assertEquals(Color4F.BLACK, color4FData.getColor4F(2));
		assertEquals(Color4F.BLACK, color4FData.getColor4F(3));
		
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
		Color4FData color4FData = new Color4FData(2, 1);
		color4FData.setColor4F(Color4F.BLUE, 0);
		color4FData.setColor4F(Color4F.CYAN, 1);
		
		assertFalse(color4FData.swap(-1, +0));
		assertFalse(color4FData.swap(+2, +0));
		assertFalse(color4FData.swap(+0, -1));
		assertFalse(color4FData.swap(+0, +2));
		
		assertTrue(color4FData.swap(0, 1));
		
		assertEquals(Color4F.CYAN, color4FData.getColor4F(0));
		assertEquals(Color4F.BLUE, color4FData.getColor4F(1));
		
		assertFalse(color4FData.undo());
		
		color4FData.setChangeHistoryEnabled(true);
		
		assertTrue(color4FData.swap(0, 1));
		
		assertEquals(Color4F.BLUE, color4FData.getColor4F(0));
		assertEquals(Color4F.CYAN, color4FData.getColor4F(1));
		
		assertTrue(color4FData.undo());
		
		assertEquals(Color4F.CYAN, color4FData.getColor4F(0));
		assertEquals(Color4F.BLUE, color4FData.getColor4F(1));
		
		color4FData.changeBegin();
		
		assertTrue(color4FData.swap(0, 1));
		
		color4FData.changeEnd();
		
		assertEquals(Color4F.BLUE, color4FData.getColor4F(0));
		assertEquals(Color4F.CYAN, color4FData.getColor4F(1));
		
		assertTrue(color4FData.undo());
		
		assertEquals(Color4F.CYAN, color4FData.getColor4F(0));
		assertEquals(Color4F.BLUE, color4FData.getColor4F(1));
	}
	
	@Test
	public void testToBufferedImageBoolean() {
		final BufferedImage bufferedImageARGBExpected = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		final BufferedImage bufferedImageRGBExpected = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		
		bufferedImageARGBExpected.setRGB(0, 0, Color4I.toIntARGB(255, 0, 0, 255));
		bufferedImageRGBExpected.setRGB(0, 0, Color3I.toIntRGB(255, 0, 0));
		
		final Color4FData color4FData = new Color4FData(1, 1, Color4F.RED);
		
		final BufferedImage bufferedImageARGB = color4FData.toBufferedImage(false);
		final BufferedImage bufferedImageRGB = color4FData.toBufferedImage(true);
		
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
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		color4FData.updatePixel(Color4F.BLACK, 0);
		
		assertEquals(Color4F.BLACK, color4FData.getColor4F(0));
		
		assertThrows(NullPointerException.class, () -> color4FData.updatePixel(null, 0));
		
		assertThrows(IllegalArgumentException.class, () -> color4FData.updatePixel(Color4F.BLACK, -1));
		assertThrows(IllegalArgumentException.class, () -> color4FData.updatePixel(Color4F.BLACK, +1));
	}
	
	@Test
	public void testUpdateState() {
		final Color4FData color4FData = new Color4FData(1, 1);
		
		assertEquals(1, color4FData.getResolutionX());
		assertEquals(1, color4FData.getResolutionY());
		
		assertEquals(Color4F.WHITE, color4FData.getColor4F(0));
		
		color4FData.updateState(new Color4F[] {Color4F.BLACK, Color4F.BLACK, Color4F.BLACK, Color4F.BLACK}, 2, 2);
		
		assertEquals(2, color4FData.getResolutionX());
		assertEquals(2, color4FData.getResolutionY());
		
		assertEquals(Color4F.BLACK, color4FData.getColor4F(0));
		assertEquals(Color4F.BLACK, color4FData.getColor4F(1));
		assertEquals(Color4F.BLACK, color4FData.getColor4F(2));
		assertEquals(Color4F.BLACK, color4FData.getColor4F(3));
		
		assertThrows(NullPointerException.class, () -> color4FData.updateState(new Color4F[] {null}, 1, 1));
		assertThrows(NullPointerException.class, () -> color4FData.updateState(null, 1, 1));
		
		assertThrows(IllegalArgumentException.class, () -> color4FData.updateState(new Color4F[] {Color4F.BLACK}, 1, 0));
		assertThrows(IllegalArgumentException.class, () -> color4FData.updateState(new Color4F[] {Color4F.BLACK}, 0, 1));
	}
}