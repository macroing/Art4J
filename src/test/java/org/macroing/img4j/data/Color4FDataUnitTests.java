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

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;
import org.macroing.img4j.color.Color3D;
import org.macroing.img4j.color.Color3F;
import org.macroing.img4j.color.Color3I;
import org.macroing.img4j.color.Color4D;
import org.macroing.img4j.color.Color4F;
import org.macroing.img4j.color.Color4I;
import org.macroing.img4j.data.Color4FData.PixelChange;
import org.macroing.img4j.data.Color4FData.StateChange;

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