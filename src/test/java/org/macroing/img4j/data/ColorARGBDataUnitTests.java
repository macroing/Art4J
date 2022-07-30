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

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;
import org.macroing.img4j.color.Color3D;
import org.macroing.img4j.color.Color3F;
import org.macroing.img4j.color.Color3I;
import org.macroing.img4j.color.Color4D;
import org.macroing.img4j.color.Color4F;
import org.macroing.img4j.color.Color4I;

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
}