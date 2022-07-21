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
package org.macroing.img4j.color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public final class Color3DUnitTests {
	public Color3DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAddColor3DColor3D() {
		final Color3D a = new Color3D(1.0D, 2.0D, 3.0D);
		final Color3D b = new Color3D(2.0D, 3.0D, 4.0D);
		final Color3D c = Color3D.add(a, b);
		
		assertEquals(3.0D, c.r);
		assertEquals(5.0D, c.g);
		assertEquals(7.0D, c.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.add(a, null));
		assertThrows(NullPointerException.class, () -> Color3D.add(null, b));
	}
	
	@Test
	public void testAddColor3DDouble() {
		final Color3D a = new Color3D(1.0D, 2.0D, 3.0D);
		final Color3D b = Color3D.add(a, 2.0D);
		
		assertEquals(3.0D, b.r);
		assertEquals(4.0D, b.g);
		assertEquals(5.0D, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.add(null, 2.0D));
	}
	
	@Test
	public void testAverage() {
		final Color3D color = new Color3D(0.0D, 0.5D, 1.0D);
		
		assertEquals(0.5D, color.average());
	}
	
	@Test
	public void testBlendColor3DColor3D() {
		final Color3D a = new Color3D(1.0D, 1.0D, 1.0D);
		final Color3D b = new Color3D(3.0D, 3.0D, 3.0D);
		final Color3D c = Color3D.blend(a, b);
		
		assertEquals(2.0D, c.r);
		assertEquals(2.0D, c.g);
		assertEquals(2.0D, c.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.blend(a, null));
		assertThrows(NullPointerException.class, () -> Color3D.blend(null, b));
	}
	
	@Test
	public void testBlendColor3DColor3DColor3DColor3DDoubleDouble() {
		final Color3D a = new Color3D(0.0D, 0.0D, 0.0D);
		final Color3D b = new Color3D(2.0D, 0.0D, 0.0D);
		final Color3D c = new Color3D(0.0D, 0.0D, 0.0D);
		final Color3D d = new Color3D(0.0D, 2.0D, 0.0D);
		final Color3D e = Color3D.blend(a, b, c, d, 0.5D, 0.5D);
		
		assertEquals(0.5D, e.r);
		assertEquals(0.5D, e.g);
		assertEquals(0.0D, e.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.blend(a, b, c, null, 0.5D, 0.5D));
		assertThrows(NullPointerException.class, () -> Color3D.blend(a, b, null, d, 0.5D, 0.5D));
		assertThrows(NullPointerException.class, () -> Color3D.blend(a, null, c, d, 0.5D, 0.5D));
		assertThrows(NullPointerException.class, () -> Color3D.blend(null, b, c, d, 0.5D, 0.5D));
	}
	
	@Test
	public void testBlendColor3DColor3DDouble() {
		final Color3D a = new Color3D(1.0D, 1.0D, 1.0D);
		final Color3D b = new Color3D(5.0D, 5.0D, 5.0D);
		final Color3D c = Color3D.blend(a, b, 0.25D);
		
		assertEquals(2.0D, c.r);
		assertEquals(2.0D, c.g);
		assertEquals(2.0D, c.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.blend(a, null, 0.25D));
		assertThrows(NullPointerException.class, () -> Color3D.blend(null, b, 0.25D));
	}
	
	@Test
	public void testBlendColor3DColor3DDoubleDoubleDouble() {
		final Color3D a = new Color3D(1.0D, 1.0D, 1.0D);
		final Color3D b = new Color3D(2.0D, 2.0D, 2.0D);
		final Color3D c = Color3D.blend(a, b, 0.0D, 0.5D, 1.0D);
		
		assertEquals(1.0D, c.r);
		assertEquals(1.5D, c.g);
		assertEquals(2.0D, c.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.blend(a, null, 0.0D, 0.5D, 1.0D));
		assertThrows(NullPointerException.class, () -> Color3D.blend(null, b, 0.0D, 0.5D, 1.0D));
	}
	
	@Test
	public void testClearCacheAndGetCacheSizeAndGetCached() {
		Color3D.clearCache();
		
		assertEquals(0, Color3D.getCacheSize());
		
		final Color3D a = new Color3D(0.0D, 0.0D, 0.0D);
		final Color3D b = new Color3D(0.0D, 0.0D, 0.0D);
		final Color3D c = Color3D.getCached(a);
		final Color3D d = Color3D.getCached(b);
		
		assertThrows(NullPointerException.class, () -> Color3D.getCached(null));
		
		assertEquals(1, Color3D.getCacheSize());
		
		Color3D.clearCache();
		
		assertEquals(0, Color3D.getCacheSize());
		
		assertTrue(a != b);
		assertTrue(a == c);
		assertTrue(a == d);
		
		assertTrue(b != a);
		assertTrue(b != c);
		assertTrue(b != d);
	}
	
	@Test
	public void testConstants() {
		assertEquals(new Color3D(0.0D, 0.0D, 0.0D), Color3D.BLACK);
		assertEquals(new Color3D(0.0D, 0.0D, 1.0D), Color3D.BLUE);
		assertEquals(new Color3D(0.0D, 1.0D, 1.0D), Color3D.CYAN);
		assertEquals(new Color3D(0.5D, 0.5D, 0.5D), Color3D.GRAY);
		assertEquals(new Color3D(0.0D, 1.0D, 0.0D), Color3D.GREEN);
		assertEquals(new Color3D(1.0D, 0.0D, 1.0D), Color3D.MAGENTA);
		assertEquals(new Color3D(1.0D, 0.0D, 0.0D), Color3D.RED);
		assertEquals(new Color3D(1.0D, 1.0D, 1.0D), Color3D.WHITE);
		assertEquals(new Color3D(1.0D, 1.0D, 0.0D), Color3D.YELLOW);
	}
	
	@Test
	public void testConstructor() {
		final Color3D color = new Color3D();
		
		assertEquals(0.0D, color.r);
		assertEquals(0.0D, color.g);
		assertEquals(0.0D, color.b);
	}
	
	@Test
	public void testConstructorColor3F() {
		final Color3D color = new Color3D(new Color3F(1.0F, 1.0F, 1.0F));
		
		assertEquals(1.0D, color.r);
		assertEquals(1.0D, color.g);
		assertEquals(1.0D, color.b);
		
		assertThrows(NullPointerException.class, () -> new Color3D((Color3F)(null)));
	}
	
	@Test
	public void testConstructorColor4D() {
		final Color3D color = new Color3D(new Color4D(1.0D, 1.0D, 1.0D));
		
		assertEquals(1.0D, color.r);
		assertEquals(1.0D, color.g);
		assertEquals(1.0D, color.b);
		
		assertThrows(NullPointerException.class, () -> new Color3D((Color4D)(null)));
	}
	
	@Test
	public void testConstructorColor4F() {
		final Color3D color = new Color3D(new Color4F(1.0F, 1.0F, 1.0F));
		
		assertEquals(1.0D, color.r);
		assertEquals(1.0D, color.g);
		assertEquals(1.0D, color.b);
		
		assertThrows(NullPointerException.class, () -> new Color3D((Color4F)(null)));
	}
	
	@Test
	public void testConstructorDouble() {
		final Color3D color = new Color3D(2.0D);
		
		assertEquals(2.0D, color.r);
		assertEquals(2.0D, color.g);
		assertEquals(2.0D, color.b);
	}
	
	@Test
	public void testConstructorDoubleDoubleDouble() {
		final Color3D color = new Color3D(0.0D, 1.0D, 2.0D);
		
		assertEquals(0.0D, color.r);
		assertEquals(1.0D, color.g);
		assertEquals(2.0D, color.b);
	}
	
	@Test
	public void testConstructorInt() {
		final Color3D color = new Color3D(255);
		
		assertEquals(1.0D, color.r);
		assertEquals(1.0D, color.g);
		assertEquals(1.0D, color.b);
	}
	
	@Test
	public void testConstructorIntIntInt() {
		final Color3D color = new Color3D(0, 255, 300);
		
		assertEquals(0.0D, color.r);
		assertEquals(1.0D, color.g);
		assertEquals(1.0D, color.b);
	}
	
	@Test
	public void testEqualsColor3D() {
		final Color3D a = new Color3D(0.0D, 0.5D, 1.0D);
		final Color3D b = new Color3D(0.0D, 0.5D, 1.0D);
		final Color3D c = new Color3D(0.0D, 0.5D, 2.0D);
		final Color3D d = new Color3D(0.0D, 2.0D, 1.0D);
		final Color3D e = new Color3D(2.0D, 0.5D, 1.0D);
		final Color3D f = null;
		
		assertTrue(a.equals(a));
		assertTrue(a.equals(b));
		assertTrue(b.equals(a));
		
		assertFalse(a.equals(c));
		assertFalse(c.equals(a));
		assertFalse(a.equals(d));
		assertFalse(d.equals(a));
		assertFalse(a.equals(e));
		assertFalse(e.equals(a));
		assertFalse(a.equals(f));
	}
	
	@Test
	public void testEqualsObject() {
		final Color3D a = new Color3D(0.0D, 0.5D, 1.0D);
		final Color3D b = new Color3D(0.0D, 0.5D, 1.0D);
		final Color3D c = new Color3D(0.0D, 0.5D, 2.0D);
		final Color3D d = new Color3D(0.0D, 2.0D, 1.0D);
		final Color3D e = new Color3D(2.0D, 0.5D, 1.0D);
		final Color3D f = null;
		
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
	public void testFromIntARGB() {
		final int colorARGB = ((255 & 0xFF) << 24) | ((0 & 0xFF) << 16) | ((128 & 0xFF) << 8) | ((255 & 0xFF) << 0);
		
		final Color3D color = Color3D.fromIntARGB(colorARGB);
		
		assertEquals(  0, color.toIntR());
		assertEquals(128, color.toIntG());
		assertEquals(255, color.toIntB());
	}
	
	@Test
	public void testFromIntRGB() {
		final int colorRGB = ((0 & 0xFF) << 16) | ((128 & 0xFF) << 8) | ((255 & 0xFF) << 0);
		
		final Color3D color = Color3D.fromIntRGB(colorRGB);
		
		assertEquals(  0, color.toIntR());
		assertEquals(128, color.toIntG());
		assertEquals(255, color.toIntB());
	}
	
	@Test
	public void testGrayscaleAverage() {
		final Color3D a = new Color3D(0.0D, 0.5D, 1.0D);
		final Color3D b = Color3D.grayscaleAverage(a);
		
		assertEquals(0.5D, b.r);
		assertEquals(0.5D, b.g);
		assertEquals(0.5D, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.grayscaleAverage(null));
	}
	
	@Test
	public void testGrayscaleB() {
		final Color3D a = new Color3D(0.0D, 0.5D, 1.0D);
		final Color3D b = Color3D.grayscaleB(a);
		
		assertEquals(1.0D, b.r);
		assertEquals(1.0D, b.g);
		assertEquals(1.0D, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.grayscaleB(null));
	}
	
	@Test
	public void testGrayscaleG() {
		final Color3D a = new Color3D(0.0D, 0.5D, 1.0D);
		final Color3D b = Color3D.grayscaleG(a);
		
		assertEquals(0.5D, b.r);
		assertEquals(0.5D, b.g);
		assertEquals(0.5D, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.grayscaleG(null));
	}
	
	@Test
	public void testGrayscaleLightness() {
		final Color3D a = new Color3D(1.0D, 2.0D, 3.0D);
		final Color3D b = Color3D.grayscaleLightness(a);
		
		assertEquals(2.0D, b.r);
		assertEquals(2.0D, b.g);
		assertEquals(2.0D, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.grayscaleLightness(null));
	}
	
	@Test
	public void testGrayscaleMax() {
		final Color3D a = new Color3D(0.0D, 1.0D, 2.0D);
		final Color3D b = Color3D.grayscaleMax(a);
		
		assertEquals(2.0D, b.r);
		assertEquals(2.0D, b.g);
		assertEquals(2.0D, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.grayscaleMax(null));
	}
	
	@Test
	public void testGrayscaleMin() {
		final Color3D a = new Color3D(0.0D, 1.0D, 2.0D);
		final Color3D b = Color3D.grayscaleMin(a);
		
		assertEquals(0.0D, b.r);
		assertEquals(0.0D, b.g);
		assertEquals(0.0D, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.grayscaleMin(null));
	}
	
	@Test
	public void testGrayscaleR() {
		final Color3D a = new Color3D(0.0D, 0.5D, 1.0D);
		final Color3D b = Color3D.grayscaleR(a);
		
		assertEquals(0.0D, b.r);
		assertEquals(0.0D, b.g);
		assertEquals(0.0D, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.grayscaleR(null));
	}
	
	@Test
	public void testGrayscaleRelativeLuminance() {
		final Color3D a = new Color3D(1.0D / 0.212671D, 1.0D / 0.715160D, 1.0D / 0.072169D);
		final Color3D b = Color3D.grayscaleRelativeLuminance(a);
		
		assertEquals(3.0D, b.r);
		assertEquals(3.0D, b.g);
		assertEquals(3.0D, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.grayscaleRelativeLuminance(null));
	}
	
	@Test
	public void testHashCode() {
		final Color3D a = new Color3D(0.0D, 0.5D, 1.0D);
		final Color3D b = new Color3D(0.0D, 0.5D, 1.0D);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testInvert() {
		final Color3D a = new Color3D(0.75D, 0.75D, 0.75D);
		final Color3D b = Color3D.invert(a);
		
		assertEquals(0.25D, b.r);
		assertEquals(0.25D, b.g);
		assertEquals(0.25D, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.invert(null));
	}
	
	@Test
	public void testIsBlack() {
		final Color3D a = new Color3D(0.0D, 0.0D, 0.0D);
		final Color3D b = new Color3D(0.0D, 0.0D, 1.0D);
		final Color3D c = new Color3D(0.0D, 1.0D, 0.0D);
		final Color3D d = new Color3D(1.0D, 0.0D, 0.0D);
		final Color3D e = new Color3D(0.0D, 1.0D, 1.0D);
		final Color3D f = new Color3D(1.0D, 1.0D, 0.0D);
		final Color3D g = new Color3D(1.0D, 1.0D, 1.0D);
		
		assertTrue(a.isBlack());
		
		assertFalse(b.isBlack());
		assertFalse(c.isBlack());
		assertFalse(d.isBlack());
		assertFalse(e.isBlack());
		assertFalse(f.isBlack());
		assertFalse(g.isBlack());
	}
	
	@Test
	public void testIsBlue() {
		final Color3D a = new Color3D(0.0D, 0.0D, 1.0D);
		final Color3D b = new Color3D(0.5D, 0.5D, 1.0D);
		
		assertTrue(a.isBlue());
		
		assertFalse(b.isBlue());
	}
	
	@Test
	public void testIsBlueDoubleDouble() {
		final Color3D a = new Color3D(0.0D, 0.0D, 1.0D);
		final Color3D b = new Color3D(0.5D, 0.5D, 1.0D);
		final Color3D c = new Color3D(1.0D, 1.0D, 1.0D);
		
		assertTrue(a.isBlue(0.5D, 0.5D));
		assertTrue(b.isBlue(0.5D, 0.5D));
		
		assertFalse(b.isBlue(0.5D, 1.0D));
		assertFalse(b.isBlue(1.0D, 0.5D));
		assertFalse(c.isBlue(0.5D, 0.5D));
	}
	
	@Test
	public void testIsCyan() {
		final Color3D a = new Color3D(0.0D, 1.0D, 1.0D);
		final Color3D b = new Color3D(0.0D, 0.5D, 0.5D);
		final Color3D c = new Color3D(1.0D, 1.0D, 1.0D);
		final Color3D d = new Color3D(0.0D, 0.5D, 1.0D);
		
		assertTrue(a.isCyan());
		assertTrue(b.isCyan());
		
		assertFalse(c.isCyan());
		assertFalse(d.isCyan());
	}
	
	@Test
	public void testIsGrayscale() {
		final Color3D a = new Color3D(0.0D, 0.0D, 0.0D);
		final Color3D b = new Color3D(0.5D, 0.5D, 0.5D);
		final Color3D c = new Color3D(1.0D, 1.0D, 1.0D);
		final Color3D d = new Color3D(0.0D, 0.0D, 0.5D);
		final Color3D e = new Color3D(0.0D, 0.5D, 0.5D);
		final Color3D f = new Color3D(0.0D, 0.5D, 0.0D);
		final Color3D g = new Color3D(0.0D, 0.5D, 1.0D);
		
		assertTrue(a.isGrayscale());
		assertTrue(b.isGrayscale());
		assertTrue(c.isGrayscale());
		
		assertFalse(d.isGrayscale());
		assertFalse(e.isGrayscale());
		assertFalse(f.isGrayscale());
		assertFalse(g.isGrayscale());
	}
	
	@Test
	public void testIsGreen() {
		final Color3D a = new Color3D(0.0D, 1.0D, 0.0D);
		final Color3D b = new Color3D(0.5D, 1.0D, 0.5D);
		
		assertTrue(a.isGreen());
		
		assertFalse(b.isGreen());
	}
	
	@Test
	public void testIsGreenDoubleDouble() {
		final Color3D a = new Color3D(0.0D, 1.0D, 0.0D);
		final Color3D b = new Color3D(0.5D, 1.0D, 0.5D);
		final Color3D c = new Color3D(1.0D, 1.0D, 1.0D);
		
		assertTrue(a.isGreen(0.5D, 0.5D));
		assertTrue(b.isGreen(0.5D, 0.5D));
		
		assertFalse(b.isGreen(0.5D, 1.0D));
		assertFalse(b.isGreen(1.0D, 0.5D));
		assertFalse(c.isGreen(0.5D, 0.5D));
	}
	
	@Test
	public void testIsMagenta() {
		final Color3D a = new Color3D(1.0D, 0.0D, 1.0D);
		final Color3D b = new Color3D(0.5D, 0.0D, 0.5D);
		final Color3D c = new Color3D(1.0D, 1.0D, 1.0D);
		final Color3D d = new Color3D(0.0D, 0.5D, 1.0D);
		
		assertTrue(a.isMagenta());
		assertTrue(b.isMagenta());
		
		assertFalse(c.isMagenta());
		assertFalse(d.isMagenta());
	}
	
	@Test
	public void testIsRed() {
		final Color3D a = new Color3D(1.0D, 0.0D, 0.0D);
		final Color3D b = new Color3D(1.0D, 0.5D, 0.5D);
		
		assertTrue(a.isRed());
		
		assertFalse(b.isRed());
	}
	
	@Test
	public void testIsRedDoubleDouble() {
		final Color3D a = new Color3D(1.0D, 0.0D, 0.0D);
		final Color3D b = new Color3D(1.0D, 0.5D, 0.5D);
		final Color3D c = new Color3D(1.0D, 1.0D, 1.0D);
		
		assertTrue(a.isRed(0.5D, 0.5D));
		assertTrue(b.isRed(0.5D, 0.5D));
		
		assertFalse(b.isRed(0.5D, 1.0D));
		assertFalse(b.isRed(1.0D, 0.5D));
		assertFalse(c.isRed(0.5D, 0.5D));
	}
	
	@Test
	public void testIsWhite() {
		final Color3D a = new Color3D(1.0D, 1.0D, 1.0D);
		final Color3D b = new Color3D(1.0D, 1.0D, 0.0D);
		final Color3D c = new Color3D(1.0D, 0.0D, 0.0D);
		final Color3D d = new Color3D(0.0D, 0.0D, 0.0D);
		
		assertTrue(a.isWhite());
		
		assertFalse(b.isWhite());
		assertFalse(c.isWhite());
		assertFalse(d.isWhite());
	}
	
	@Test
	public void testIsYellow() {
		final Color3D a = new Color3D(1.0D, 1.0D, 0.0D);
		final Color3D b = new Color3D(0.5D, 0.5D, 0.0D);
		final Color3D c = new Color3D(1.0D, 1.0D, 1.0D);
		final Color3D d = new Color3D(0.0D, 0.5D, 1.0D);
		
		assertTrue(a.isYellow());
		assertTrue(b.isYellow());
		
		assertFalse(c.isYellow());
		assertFalse(d.isYellow());
	}
	
	@Test
	public void testLightness() {
		final Color3D color = new Color3D(1.0D, 2.0D, 3.0D);
		
		assertEquals(2.0D, color.lightness());
	}
	
	@Test
	public void testMax() {
		final Color3D color = new Color3D(0.0D, 1.0D, 2.0D);
		
		assertEquals(2.0D, color.max());
	}
	
	@Test
	public void testMin() {
		final Color3D color = new Color3D(0.0D, 1.0D, 2.0D);
		
		assertEquals(0.0D, color.min());
	}
	
	@Test
	public void testMultiplyColor3DDouble() {
		final Color3D a = new Color3D(1.0D, 2.0D, 3.0D);
		final Color3D b = Color3D.multiply(a, 2.0D);
		
		assertEquals(2.0D, b.r);
		assertEquals(4.0D, b.g);
		assertEquals(6.0D, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.multiply(null, 2.0D));
	}
	
	@Test
	public void testRandom() {
		for(int i = 0; i < 1000; i++) {
			final Color3D color = Color3D.random();
			
			assertTrue(color.r >= 0.0D && color.r <= 1.0D);
			assertTrue(color.g >= 0.0D && color.g <= 1.0D);
			assertTrue(color.b >= 0.0D && color.b <= 1.0D);
		}
	}
	
	@Test
	public void testRandomBlue() {
		for(int i = 0; i < 1000; i++) {
			final Color3D color = Color3D.randomBlue();
			
			assertTrue(color.r >= 0.0D && color.r <= 0.0D);
			assertTrue(color.g >= 0.0D && color.g <= 0.0D);
			assertTrue(color.b >  0.0D && color.b <= 1.0D);
			
			assertTrue(color.b > color.r);
			assertTrue(color.b > color.g);
		}
	}
	
	@Test
	public void testRandomBlueDoubleDouble() {
		for(int i = 0; i < 1000; i++) {
			final Color3D color = Color3D.randomBlue(0.25D, 0.50D);
			
			assertTrue(color.r >= 0.0D && color.r <= 0.25D);
			assertTrue(color.g >= 0.0D && color.g <= 0.50D);
			assertTrue(color.b >  0.0D && color.b <= 1.00D);
			
			assertTrue(color.b > color.r);
			assertTrue(color.b > color.g);
		}
	}
	
	@Test
	public void testRandomCyan() {
		for(int i = 0; i < 1000; i++) {
			final Color3D color = Color3D.randomCyan();
			
			assertTrue(color.r >= 0.0D && color.r <= 0.0D);
			assertTrue(color.g >  0.0D && color.g <= 1.0D);
			assertTrue(color.b >  0.0D && color.b <= 1.0D);
			
			assertTrue(color.g > color.r);
			assertTrue(color.b > color.r);
			
			assertEquals(color.g, color.b);
		}
	}
	
	@Test
	public void testRandomCyanDoubleDouble() {
		for(int i = 0; i < 1000; i++) {
			final Color3D color = Color3D.randomCyan(0.50D, 0.25D);
			
			assertTrue(color.r >= 0.0D && color.r <= 0.25D);
			assertTrue(color.g >= 0.5D && color.g <= 1.00D);
			assertTrue(color.b >= 0.5D && color.b <= 1.00D);
			
			assertTrue(color.g > color.r);
			assertTrue(color.b > color.r);
			
			assertEquals(color.g, color.b);
		}
	}
	
	@Test
	public void testRandomGrayscale() {
		for(int i = 0; i < 1000; i++) {
			final Color3D color = Color3D.randomGrayscale();
			
			assertTrue(color.r >= 0.0D && color.r <= 1.0D);
			assertTrue(color.g >= 0.0D && color.g <= 1.0D);
			assertTrue(color.b >= 0.0D && color.b <= 1.0D);
			
			assertEquals(color.r, color.g);
			assertEquals(color.g, color.b);
		}
	}
	
	@Test
	public void testRandomGreen() {
		for(int i = 0; i < 1000; i++) {
			final Color3D color = Color3D.randomGreen();
			
			assertTrue(color.r >= 0.0D && color.r <= 0.0D);
			assertTrue(color.g >  0.0D && color.g <= 1.0D);
			assertTrue(color.b >= 0.0D && color.b <= 0.0D);
			
			assertTrue(color.g > color.r);
			assertTrue(color.g > color.b);
		}
	}
	
	@Test
	public void testRandomGreenDoubleDouble() {
		for(int i = 0; i < 1000; i++) {
			final Color3D color = Color3D.randomGreen(0.25D, 0.50D);
			
			assertTrue(color.r >= 0.0D && color.r <= 0.25D);
			assertTrue(color.g >  0.0D && color.g <= 1.00D);
			assertTrue(color.b >= 0.0D && color.b <= 0.50D);
			
			assertTrue(color.g > color.r);
			assertTrue(color.g > color.b);
		}
	}
	
	@Test
	public void testRandomMagenta() {
		for(int i = 0; i < 1000; i++) {
			final Color3D color = Color3D.randomMagenta();
			
			assertTrue(color.r >  0.0D && color.r <= 1.0D);
			assertTrue(color.g >= 0.0D && color.g <= 0.0D);
			assertTrue(color.b >  0.0D && color.b <= 1.0D);
			
			assertTrue(color.r > color.g);
			assertTrue(color.b > color.g);
			
			assertEquals(color.r, color.b);
		}
	}
	
	@Test
	public void testRandomMagentaDoubleDouble() {
		for(int i = 0; i < 1000; i++) {
			final Color3D color = Color3D.randomMagenta(0.50D, 0.25D);
			
			assertTrue(color.r >= 0.5D && color.r <= 1.00D);
			assertTrue(color.g >= 0.0D && color.g <= 0.25D);
			assertTrue(color.b >= 0.5D && color.b <= 1.00D);
			
			assertTrue(color.r > color.g);
			assertTrue(color.b > color.g);
			
			assertEquals(color.r, color.b);
		}
	}
	
	@Test
	public void testRandomRed() {
		for(int i = 0; i < 1000; i++) {
			final Color3D color = Color3D.randomRed();
			
			assertTrue(color.r >  0.0D && color.r <= 1.0D);
			assertTrue(color.g >= 0.0D && color.g <= 0.0D);
			assertTrue(color.b >= 0.0D && color.b <= 0.0D);
			
			assertTrue(color.r > color.g);
			assertTrue(color.r > color.b);
		}
	}
	
	@Test
	public void testRandomRedDoubleDouble() {
		for(int i = 0; i < 1000; i++) {
			final Color3D color = Color3D.randomRed(0.25D, 0.50D);
			
			assertTrue(color.r >  0.0D && color.r <= 1.00D);
			assertTrue(color.g >= 0.0D && color.g <= 0.25D);
			assertTrue(color.b >= 0.0D && color.b <= 0.50D);
			
			assertTrue(color.r > color.g);
			assertTrue(color.r > color.b);
		}
	}
	
	@Test
	public void testRandomYellow() {
		for(int i = 0; i < 1000; i++) {
			final Color3D color = Color3D.randomYellow();
			
			assertTrue(color.r >  0.0D && color.r <= 1.0D);
			assertTrue(color.g >  0.0D && color.g <= 1.0D);
			assertTrue(color.b >= 0.0D && color.b <= 0.0D);
			
			assertTrue(color.r > color.b);
			assertTrue(color.g > color.b);
			
			assertEquals(color.r, color.g);
		}
	}
	
	@Test
	public void testRandomYellowDoubleDouble() {
		for(int i = 0; i < 1000; i++) {
			final Color3D color = Color3D.randomYellow(0.50D, 0.25D);
			
			assertTrue(color.r >= 0.5D && color.r <= 1.00D);
			assertTrue(color.g >= 0.5D && color.g <= 1.00D);
			assertTrue(color.b >= 0.0D && color.b <= 0.25D);
			
			assertTrue(color.r > color.b);
			assertTrue(color.g > color.b);
			
			assertEquals(color.r, color.g);
		}
	}
	
	@Test
	public void testRelativeLuminance() {
		final Color3D color = new Color3D(1.0D / 0.212671D, 1.0D / 0.715160D, 1.0D / 0.072169D);
		
		assertEquals(3.0D, color.relativeLuminance());
	}
	
	@Test
	public void testSepia() {
		final Color3D a = new Color3D(1.0D, 1.0D, 1.0D);
		final Color3D b = Color3D.sepia(a);
		
		assertEquals(1.351D, b.r);
		assertEquals(1.203D, b.g);
		assertEquals(0.937D, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.sepia(null));
	}
	
	@Test
	public void testToIntARGB() {
		final int expectedIntARGB = ((255 & 0xFF) << 24) | ((0 & 0xFF) << 16) | ((128 & 0xFF) << 8) | ((255 & 0xFF) << 0);
		
		final Color3D color = new Color3D(0.0D, 0.5D, 1.0D);
		
		assertEquals(expectedIntARGB, color.toIntARGB());
	}
	
	@Test
	public void testToIntB() {
		final Color3D a = new Color3D(0.0D, 0.0D, 0.0D);
		final Color3D b = new Color3D(0.0D, 0.0D, 0.5D);
		final Color3D c = new Color3D(0.0D, 0.0D, 1.0D);
		final Color3D d = new Color3D(0.0D, 0.0D, 2.0D);
		
		assertEquals(  0, a.toIntB());
		assertEquals(128, b.toIntB());
		assertEquals(255, c.toIntB());
		assertEquals(255, d.toIntB());
	}
	
	@Test
	public void testToIntG() {
		final Color3D a = new Color3D(0.0D, 0.0D, 0.0D);
		final Color3D b = new Color3D(0.0D, 0.5D, 0.0D);
		final Color3D c = new Color3D(0.0D, 1.0D, 0.0D);
		final Color3D d = new Color3D(0.0D, 2.0D, 0.0D);
		
		assertEquals(  0, a.toIntG());
		assertEquals(128, b.toIntG());
		assertEquals(255, c.toIntG());
		assertEquals(255, d.toIntG());
	}
	
	@Test
	public void testToIntR() {
		final Color3D a = new Color3D(0.0D, 0.0D, 0.0D);
		final Color3D b = new Color3D(0.5D, 0.0D, 0.0D);
		final Color3D c = new Color3D(1.0D, 0.0D, 0.0D);
		final Color3D d = new Color3D(2.0D, 0.0D, 0.0D);
		
		assertEquals(  0, a.toIntR());
		assertEquals(128, b.toIntR());
		assertEquals(255, c.toIntR());
		assertEquals(255, d.toIntR());
	}
	
	@Test
	public void testToIntRGB() {
		final int expectedIntRGB = ((0 & 0xFF) << 16) | ((128 & 0xFF) << 8) | ((255 & 0xFF) << 0);
		
		final Color3D color = new Color3D(0.0D, 0.5D, 1.0D);
		
		assertEquals(expectedIntRGB, color.toIntRGB());
	}
	
	@Test
	public void testToString() {
		final Color3D color = new Color3D(0.0D, 0.5D, 1.0D);
		
		assertEquals("new Color3D(0.0D, 0.5D, 1.0D)", color.toString());
	}
}