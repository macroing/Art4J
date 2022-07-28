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
	public void testEquals() {
		final Data a = new Color4DData(400, 400);
		final Data b = new Color4DData(400, 400);
		final Data c = new Color4DData(800, 200);
		final Data d = new Color4DData(200, 800);
		final Data e = new Color4FData(400, 400);
		final Data f = null;
		
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
}