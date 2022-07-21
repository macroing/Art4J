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
public final class Color3FUnitTests {
	public Color3FUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAddColor3FColor3F() {
		final Color3F a = new Color3F(1.0F, 2.0F, 3.0F);
		final Color3F b = new Color3F(2.0F, 3.0F, 4.0F);
		final Color3F c = Color3F.add(a, b);
		
		assertEquals(3.0F, c.r);
		assertEquals(5.0F, c.g);
		assertEquals(7.0F, c.b);
		
		assertThrows(NullPointerException.class, () -> Color3F.add(a, null));
		assertThrows(NullPointerException.class, () -> Color3F.add(null, b));
	}
	
	@Test
	public void testAddColor3FFloat() {
		final Color3F a = new Color3F(1.0F, 2.0F, 3.0F);
		final Color3F b = Color3F.add(a, 2.0F);
		
		assertEquals(3.0F, b.r);
		assertEquals(4.0F, b.g);
		assertEquals(5.0F, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3F.add(null, 2.0F));
	}
	
	@Test
	public void testAverage() {
		final Color3F color = new Color3F(0.0F, 0.5F, 1.0F);
		
		assertEquals(0.5F, color.average());
	}
	
	@Test
	public void testBlendColor3FColor3F() {
		final Color3F a = new Color3F(1.0F, 1.0F, 1.0F);
		final Color3F b = new Color3F(3.0F, 3.0F, 3.0F);
		final Color3F c = Color3F.blend(a, b);
		
		assertEquals(2.0F, c.r);
		assertEquals(2.0F, c.g);
		assertEquals(2.0F, c.b);
		
		assertThrows(NullPointerException.class, () -> Color3F.blend(a, null));
		assertThrows(NullPointerException.class, () -> Color3F.blend(null, b));
	}
	
	@Test
	public void testBlendColor3FColor3FColor3FColor3FFloatFloat() {
		final Color3F a = new Color3F(0.0F, 0.0F, 0.0F);
		final Color3F b = new Color3F(2.0F, 0.0F, 0.0F);
		final Color3F c = new Color3F(0.0F, 0.0F, 0.0F);
		final Color3F d = new Color3F(0.0F, 2.0F, 0.0F);
		final Color3F e = Color3F.blend(a, b, c, d, 0.5F, 0.5F);
		
		assertEquals(0.5F, e.r);
		assertEquals(0.5F, e.g);
		assertEquals(0.0F, e.b);
		
		assertThrows(NullPointerException.class, () -> Color3F.blend(a, b, c, null, 0.5F, 0.5F));
		assertThrows(NullPointerException.class, () -> Color3F.blend(a, b, null, d, 0.5F, 0.5F));
		assertThrows(NullPointerException.class, () -> Color3F.blend(a, null, c, d, 0.5F, 0.5F));
		assertThrows(NullPointerException.class, () -> Color3F.blend(null, b, c, d, 0.5F, 0.5F));
	}
	
	@Test
	public void testBlendColor3FColor3FFloat() {
		final Color3F a = new Color3F(1.0F, 1.0F, 1.0F);
		final Color3F b = new Color3F(5.0F, 5.0F, 5.0F);
		final Color3F c = Color3F.blend(a, b, 0.25F);
		
		assertEquals(2.0F, c.r);
		assertEquals(2.0F, c.g);
		assertEquals(2.0F, c.b);
		
		assertThrows(NullPointerException.class, () -> Color3F.blend(a, null, 0.25F));
		assertThrows(NullPointerException.class, () -> Color3F.blend(null, b, 0.25F));
	}
	
	@Test
	public void testBlendColor3FColor3FFloatFloatFloat() {
		final Color3F a = new Color3F(1.0F, 1.0F, 1.0F);
		final Color3F b = new Color3F(2.0F, 2.0F, 2.0F);
		final Color3F c = Color3F.blend(a, b, 0.0F, 0.5F, 1.0F);
		
		assertEquals(1.0F, c.r);
		assertEquals(1.5F, c.g);
		assertEquals(2.0F, c.b);
		
		assertThrows(NullPointerException.class, () -> Color3F.blend(a, null, 0.0F, 0.5F, 1.0F));
		assertThrows(NullPointerException.class, () -> Color3F.blend(null, b, 0.0F, 0.5F, 1.0F));
	}
	
	@Test
	public void testClearCacheAndGetCacheSizeAndGetCached() {
		Color3F.clearCache();
		
		assertEquals(0, Color3F.getCacheSize());
		
		final Color3F a = new Color3F(0.0F, 0.0F, 0.0F);
		final Color3F b = new Color3F(0.0F, 0.0F, 0.0F);
		final Color3F c = Color3F.getCached(a);
		final Color3F d = Color3F.getCached(b);
		
		assertThrows(NullPointerException.class, () -> Color3F.getCached(null));
		
		assertEquals(1, Color3F.getCacheSize());
		
		Color3F.clearCache();
		
		assertEquals(0, Color3F.getCacheSize());
		
		assertTrue(a != b);
		assertTrue(a == c);
		assertTrue(a == d);
		
		assertTrue(b != a);
		assertTrue(b != c);
		assertTrue(b != d);
	}
	
	@Test
	public void testConstants() {
		assertEquals(new Color3F(0.0F, 0.0F, 0.0F), Color3F.BLACK);
		assertEquals(new Color3F(0.0F, 0.0F, 1.0F), Color3F.BLUE);
		assertEquals(new Color3F(0.0F, 1.0F, 1.0F), Color3F.CYAN);
		assertEquals(new Color3F(0.5F, 0.5F, 0.5F), Color3F.GRAY);
		assertEquals(new Color3F(0.0F, 1.0F, 0.0F), Color3F.GREEN);
		assertEquals(new Color3F(1.0F, 0.0F, 1.0F), Color3F.MAGENTA);
		assertEquals(new Color3F(1.0F, 0.0F, 0.0F), Color3F.RED);
		assertEquals(new Color3F(1.0F, 1.0F, 1.0F), Color3F.WHITE);
		assertEquals(new Color3F(1.0F, 1.0F, 0.0F), Color3F.YELLOW);
	}
	
	@Test
	public void testConstructor() {
		final Color3F color = new Color3F();
		
		assertEquals(0.0F, color.r);
		assertEquals(0.0F, color.g);
		assertEquals(0.0F, color.b);
	}
	
	@Test
	public void testConstructorColor3D() {
		final Color3F color = new Color3F(new Color3D(1.0D, 1.0D, 1.0D));
		
		assertEquals(1.0F, color.r);
		assertEquals(1.0F, color.g);
		assertEquals(1.0F, color.b);
		
		assertThrows(NullPointerException.class, () -> new Color3F((Color3D)(null)));
	}
	
	@Test
	public void testConstructorColor4D() {
		final Color3F color = new Color3F(new Color4D(1.0D, 1.0D, 1.0D));
		
		assertEquals(1.0F, color.r);
		assertEquals(1.0F, color.g);
		assertEquals(1.0F, color.b);
		
		assertThrows(NullPointerException.class, () -> new Color3F((Color4D)(null)));
	}
	
	@Test
	public void testConstructorColor4F() {
		final Color3F color = new Color3F(new Color4F(1.0F, 1.0F, 1.0F));
		
		assertEquals(1.0F, color.r);
		assertEquals(1.0F, color.g);
		assertEquals(1.0F, color.b);
		
		assertThrows(NullPointerException.class, () -> new Color3F((Color4F)(null)));
	}
	
	@Test
	public void testConstructorFloat() {
		final Color3F color = new Color3F(2.0F);
		
		assertEquals(2.0F, color.r);
		assertEquals(2.0F, color.g);
		assertEquals(2.0F, color.b);
	}
	
	@Test
	public void testConstructorFloatFloatFloat() {
		final Color3F color = new Color3F(0.0F, 1.0F, 2.0F);
		
		assertEquals(0.0F, color.r);
		assertEquals(1.0F, color.g);
		assertEquals(2.0F, color.b);
	}
	
	@Test
	public void testConstructorInt() {
		final Color3F color = new Color3F(255);
		
		assertEquals(1.0F, color.r);
		assertEquals(1.0F, color.g);
		assertEquals(1.0F, color.b);
	}
	
	@Test
	public void testConstructorIntIntInt() {
		final Color3F color = new Color3F(0, 255, 300);
		
		assertEquals(0.0F, color.r);
		assertEquals(1.0F, color.g);
		assertEquals(1.0F, color.b);
	}
	
	@Test
	public void testEqualsColor3F() {
		final Color3F a = new Color3F(0.0F, 0.5F, 1.0F);
		final Color3F b = new Color3F(0.0F, 0.5F, 1.0F);
		final Color3F c = new Color3F(0.0F, 0.5F, 2.0F);
		final Color3F d = new Color3F(0.0F, 2.0F, 1.0F);
		final Color3F e = new Color3F(2.0F, 0.5F, 1.0F);
		final Color3F f = null;
		
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
		final Color3F a = new Color3F(0.0F, 0.5F, 1.0F);
		final Color3F b = new Color3F(0.0F, 0.5F, 1.0F);
		final Color3F c = new Color3F(0.0F, 0.5F, 2.0F);
		final Color3F d = new Color3F(0.0F, 2.0F, 1.0F);
		final Color3F e = new Color3F(2.0F, 0.5F, 1.0F);
		final Color3F f = null;
		
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
		
		final Color3F color = Color3F.fromIntARGB(colorARGB);
		
		assertEquals(  0, color.toIntR());
		assertEquals(128, color.toIntG());
		assertEquals(255, color.toIntB());
	}
	
	@Test
	public void testFromIntRGB() {
		final int colorRGB = ((0 & 0xFF) << 16) | ((128 & 0xFF) << 8) | ((255 & 0xFF) << 0);
		
		final Color3F color = Color3F.fromIntRGB(colorRGB);
		
		assertEquals(  0, color.toIntR());
		assertEquals(128, color.toIntG());
		assertEquals(255, color.toIntB());
	}
	
	@Test
	public void testGrayscaleAverage() {
		final Color3F a = new Color3F(0.0F, 0.5F, 1.0F);
		final Color3F b = Color3F.grayscaleAverage(a);
		
		assertEquals(0.5F, b.r);
		assertEquals(0.5F, b.g);
		assertEquals(0.5F, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3F.grayscaleAverage(null));
	}
	
	@Test
	public void testGrayscaleB() {
		final Color3F a = new Color3F(0.0F, 0.5F, 1.0F);
		final Color3F b = Color3F.grayscaleB(a);
		
		assertEquals(1.0F, b.r);
		assertEquals(1.0F, b.g);
		assertEquals(1.0F, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3F.grayscaleB(null));
	}
	
	@Test
	public void testGrayscaleG() {
		final Color3F a = new Color3F(0.0F, 0.5F, 1.0F);
		final Color3F b = Color3F.grayscaleG(a);
		
		assertEquals(0.5F, b.r);
		assertEquals(0.5F, b.g);
		assertEquals(0.5F, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3F.grayscaleG(null));
	}
	
	@Test
	public void testGrayscaleLightness() {
		final Color3F a = new Color3F(1.0F, 2.0F, 3.0F);
		final Color3F b = Color3F.grayscaleLightness(a);
		
		assertEquals(2.0F, b.r);
		assertEquals(2.0F, b.g);
		assertEquals(2.0F, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3F.grayscaleLightness(null));
	}
	
	@Test
	public void testGrayscaleMax() {
		final Color3F a = new Color3F(0.0F, 1.0F, 2.0F);
		final Color3F b = Color3F.grayscaleMax(a);
		
		assertEquals(2.0F, b.r);
		assertEquals(2.0F, b.g);
		assertEquals(2.0F, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3F.grayscaleMax(null));
	}
	
	@Test
	public void testGrayscaleMin() {
		final Color3F a = new Color3F(0.0F, 1.0F, 2.0F);
		final Color3F b = Color3F.grayscaleMin(a);
		
		assertEquals(0.0F, b.r);
		assertEquals(0.0F, b.g);
		assertEquals(0.0F, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3F.grayscaleMin(null));
	}
	
	@Test
	public void testGrayscaleR() {
		final Color3F a = new Color3F(0.0F, 0.5F, 1.0F);
		final Color3F b = Color3F.grayscaleR(a);
		
		assertEquals(0.0F, b.r);
		assertEquals(0.0F, b.g);
		assertEquals(0.0F, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3F.grayscaleR(null));
	}
	
	@Test
	public void testGrayscaleRelativeLuminance() {
		final Color3F a = new Color3F(1.0F / 0.212671F, 1.0F / 0.715160F, 1.0F / 0.072169F);
		final Color3F b = Color3F.grayscaleRelativeLuminance(a);
		
		assertEquals(3.0F, b.r);
		assertEquals(3.0F, b.g);
		assertEquals(3.0F, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3F.grayscaleRelativeLuminance(null));
	}
	
	@Test
	public void testHashCode() {
		final Color3F a = new Color3F(0.0F, 0.5F, 1.0F);
		final Color3F b = new Color3F(0.0F, 0.5F, 1.0F);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testInvert() {
		final Color3F a = new Color3F(0.75F, 0.75F, 0.75F);
		final Color3F b = Color3F.invert(a);
		
		assertEquals(0.25F, b.r);
		assertEquals(0.25F, b.g);
		assertEquals(0.25F, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3F.invert(null));
	}
	
	@Test
	public void testIsBlack() {
		final Color3F a = new Color3F(0.0F, 0.0F, 0.0F);
		final Color3F b = new Color3F(0.0F, 0.0F, 1.0F);
		final Color3F c = new Color3F(0.0F, 1.0F, 0.0F);
		final Color3F d = new Color3F(1.0F, 0.0F, 0.0F);
		final Color3F e = new Color3F(0.0F, 1.0F, 1.0F);
		final Color3F f = new Color3F(1.0F, 1.0F, 0.0F);
		final Color3F g = new Color3F(1.0F, 1.0F, 1.0F);
		
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
		final Color3F a = new Color3F(0.0F, 0.0F, 1.0F);
		final Color3F b = new Color3F(0.5F, 0.5F, 1.0F);
		
		assertTrue(a.isBlue());
		
		assertFalse(b.isBlue());
	}
	
	@Test
	public void testIsBlueFloatFloat() {
		final Color3F a = new Color3F(0.0F, 0.0F, 1.0F);
		final Color3F b = new Color3F(0.5F, 0.5F, 1.0F);
		final Color3F c = new Color3F(1.0F, 1.0F, 1.0F);
		
		assertTrue(a.isBlue(0.5F, 0.5F));
		assertTrue(b.isBlue(0.5F, 0.5F));
		
		assertFalse(b.isBlue(0.5F, 1.0F));
		assertFalse(b.isBlue(1.0F, 0.5F));
		assertFalse(c.isBlue(0.5F, 0.5F));
	}
	
	@Test
	public void testIsCyan() {
		final Color3F a = new Color3F(0.0F, 1.0F, 1.0F);
		final Color3F b = new Color3F(0.0F, 0.5F, 0.5F);
		final Color3F c = new Color3F(1.0F, 1.0F, 1.0F);
		final Color3F d = new Color3F(0.0F, 0.5F, 1.0F);
		
		assertTrue(a.isCyan());
		assertTrue(b.isCyan());
		
		assertFalse(c.isCyan());
		assertFalse(d.isCyan());
	}
	
	@Test
	public void testIsGrayscale() {
		final Color3F a = new Color3F(0.0F, 0.0F, 0.0F);
		final Color3F b = new Color3F(0.5F, 0.5F, 0.5F);
		final Color3F c = new Color3F(1.0F, 1.0F, 1.0F);
		final Color3F d = new Color3F(0.0F, 0.0F, 0.5F);
		final Color3F e = new Color3F(0.0F, 0.5F, 0.5F);
		final Color3F f = new Color3F(0.0F, 0.5F, 0.0F);
		final Color3F g = new Color3F(0.0F, 0.5F, 1.0F);
		
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
		final Color3F a = new Color3F(0.0F, 1.0F, 0.0F);
		final Color3F b = new Color3F(0.5F, 1.0F, 0.5F);
		
		assertTrue(a.isGreen());
		
		assertFalse(b.isGreen());
	}
	
	@Test
	public void testIsGreenFloatFloat() {
		final Color3F a = new Color3F(0.0F, 1.0F, 0.0F);
		final Color3F b = new Color3F(0.5F, 1.0F, 0.5F);
		final Color3F c = new Color3F(1.0F, 1.0F, 1.0F);
		
		assertTrue(a.isGreen(0.5F, 0.5F));
		assertTrue(b.isGreen(0.5F, 0.5F));
		
		assertFalse(b.isGreen(0.5F, 1.0F));
		assertFalse(b.isGreen(1.0F, 0.5F));
		assertFalse(c.isGreen(0.5F, 0.5F));
	}
	
	@Test
	public void testIsMagenta() {
		final Color3F a = new Color3F(1.0F, 0.0F, 1.0F);
		final Color3F b = new Color3F(0.5F, 0.0F, 0.5F);
		final Color3F c = new Color3F(1.0F, 1.0F, 1.0F);
		final Color3F d = new Color3F(0.0F, 0.5F, 1.0F);
		
		assertTrue(a.isMagenta());
		assertTrue(b.isMagenta());
		
		assertFalse(c.isMagenta());
		assertFalse(d.isMagenta());
	}
	
	@Test
	public void testIsRed() {
		final Color3F a = new Color3F(1.0F, 0.0F, 0.0F);
		final Color3F b = new Color3F(1.0F, 0.5F, 0.5F);
		
		assertTrue(a.isRed());
		
		assertFalse(b.isRed());
	}
	
	@Test
	public void testIsRedFloatFloat() {
		final Color3F a = new Color3F(1.0F, 0.0F, 0.0F);
		final Color3F b = new Color3F(1.0F, 0.5F, 0.5F);
		final Color3F c = new Color3F(1.0F, 1.0F, 1.0F);
		
		assertTrue(a.isRed(0.5F, 0.5F));
		assertTrue(b.isRed(0.5F, 0.5F));
		
		assertFalse(b.isRed(0.5F, 1.0F));
		assertFalse(b.isRed(1.0F, 0.5F));
		assertFalse(c.isRed(0.5F, 0.5F));
	}
	
	@Test
	public void testIsWhite() {
		final Color3F a = new Color3F(1.0F, 1.0F, 1.0F);
		final Color3F b = new Color3F(1.0F, 1.0F, 0.0F);
		final Color3F c = new Color3F(1.0F, 0.0F, 0.0F);
		final Color3F d = new Color3F(0.0F, 0.0F, 0.0F);
		
		assertTrue(a.isWhite());
		
		assertFalse(b.isWhite());
		assertFalse(c.isWhite());
		assertFalse(d.isWhite());
	}
	
	@Test
	public void testIsYellow() {
		final Color3F a = new Color3F(1.0F, 1.0F, 0.0F);
		final Color3F b = new Color3F(0.5F, 0.5F, 0.0F);
		final Color3F c = new Color3F(1.0F, 1.0F, 1.0F);
		final Color3F d = new Color3F(0.0F, 0.5F, 1.0F);
		
		assertTrue(a.isYellow());
		assertTrue(b.isYellow());
		
		assertFalse(c.isYellow());
		assertFalse(d.isYellow());
	}
	
	@Test
	public void testLightness() {
		final Color3F color = new Color3F(1.0F, 2.0F, 3.0F);
		
		assertEquals(2.0F, color.lightness());
	}
	
	@Test
	public void testMax() {
		final Color3F color = new Color3F(0.0F, 1.0F, 2.0F);
		
		assertEquals(2.0F, color.max());
	}
	
	@Test
	public void testMin() {
		final Color3F color = new Color3F(0.0F, 1.0F, 2.0F);
		
		assertEquals(0.0F, color.min());
	}
	
	@Test
	public void testMultiplyColor3FFloat() {
		final Color3F a = new Color3F(1.0F, 2.0F, 3.0F);
		final Color3F b = Color3F.multiply(a, 2.0F);
		
		assertEquals(2.0F, b.r);
		assertEquals(4.0F, b.g);
		assertEquals(6.0F, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3F.multiply(null, 2.0F));
	}
	
	@Test
	public void testRandom() {
		for(int i = 0; i < 1000; i++) {
			final Color3F color = Color3F.random();
			
			assertTrue(color.r >= 0.0F && color.r <= 1.0F);
			assertTrue(color.g >= 0.0F && color.g <= 1.0F);
			assertTrue(color.b >= 0.0F && color.b <= 1.0F);
		}
	}
	
	@Test
	public void testRandomBlue() {
		for(int i = 0; i < 1000; i++) {
			final Color3F color = Color3F.randomBlue();
			
			assertTrue(color.r >= 0.0F && color.r <= 0.0F);
			assertTrue(color.g >= 0.0F && color.g <= 0.0F);
			assertTrue(color.b >  0.0F && color.b <= 1.0F);
			
			assertTrue(color.b > color.r);
			assertTrue(color.b > color.g);
		}
	}
	
	@Test
	public void testRandomBlueFloatFloat() {
		for(int i = 0; i < 1000; i++) {
			final Color3F color = Color3F.randomBlue(0.25F, 0.50F);
			
			assertTrue(color.r >= 0.0F && color.r <= 0.25F);
			assertTrue(color.g >= 0.0F && color.g <= 0.50F);
			assertTrue(color.b >  0.0F && color.b <= 1.00F);
			
			assertTrue(color.b > color.r);
			assertTrue(color.b > color.g);
		}
	}
	
	@Test
	public void testRandomCyan() {
		for(int i = 0; i < 1000; i++) {
			final Color3F color = Color3F.randomCyan();
			
			assertTrue(color.r >= 0.0F && color.r <= 0.0F);
			assertTrue(color.g >  0.0F && color.g <= 1.0F);
			assertTrue(color.b >  0.0F && color.b <= 1.0F);
			
			assertTrue(color.g > color.r);
			assertTrue(color.b > color.r);
			
			assertEquals(color.g, color.b);
		}
	}
	
	@Test
	public void testRandomCyanFloatFloat() {
		for(int i = 0; i < 1000; i++) {
			final Color3F color = Color3F.randomCyan(0.50F, 0.25F);
			
			assertTrue(color.r >= 0.0F && color.r <= 0.25F);
			assertTrue(color.g >= 0.5F && color.g <= 1.00F);
			assertTrue(color.b >= 0.5F && color.b <= 1.00F);
			
			assertTrue(color.g > color.r);
			assertTrue(color.b > color.r);
			
			assertEquals(color.g, color.b);
		}
	}
	
	@Test
	public void testRandomGrayscale() {
		for(int i = 0; i < 1000; i++) {
			final Color3F color = Color3F.randomGrayscale();
			
			assertTrue(color.r >= 0.0F && color.r <= 1.0F);
			assertTrue(color.g >= 0.0F && color.g <= 1.0F);
			assertTrue(color.b >= 0.0F && color.b <= 1.0F);
			
			assertEquals(color.r, color.g);
			assertEquals(color.g, color.b);
		}
	}
	
	@Test
	public void testRandomGreen() {
		for(int i = 0; i < 1000; i++) {
			final Color3F color = Color3F.randomGreen();
			
			assertTrue(color.r >= 0.0F && color.r <= 0.0F);
			assertTrue(color.g >  0.0F && color.g <= 1.0F);
			assertTrue(color.b >= 0.0F && color.b <= 0.0F);
			
			assertTrue(color.g > color.r);
			assertTrue(color.g > color.b);
		}
	}
	
	@Test
	public void testRandomGreenFloatFloat() {
		for(int i = 0; i < 1000; i++) {
			final Color3F color = Color3F.randomGreen(0.25F, 0.50F);
			
			assertTrue(color.r >= 0.0F && color.r <= 0.25F);
			assertTrue(color.g >  0.0F && color.g <= 1.00F);
			assertTrue(color.b >= 0.0F && color.b <= 0.50F);
			
			assertTrue(color.g > color.r);
			assertTrue(color.g > color.b);
		}
	}
	
	@Test
	public void testRandomMagenta() {
		for(int i = 0; i < 1000; i++) {
			final Color3F color = Color3F.randomMagenta();
			
			assertTrue(color.r >  0.0F && color.r <= 1.0F);
			assertTrue(color.g >= 0.0F && color.g <= 0.0F);
			assertTrue(color.b >  0.0F && color.b <= 1.0F);
			
			assertTrue(color.r > color.g);
			assertTrue(color.b > color.g);
			
			assertEquals(color.r, color.b);
		}
	}
	
	@Test
	public void testRandomMagentaFloatFloat() {
		for(int i = 0; i < 1000; i++) {
			final Color3F color = Color3F.randomMagenta(0.50F, 0.25F);
			
			assertTrue(color.r >= 0.5F && color.r <= 1.00F);
			assertTrue(color.g >= 0.0F && color.g <= 0.25F);
			assertTrue(color.b >= 0.5F && color.b <= 1.00F);
			
			assertTrue(color.r > color.g);
			assertTrue(color.b > color.g);
			
			assertEquals(color.r, color.b);
		}
	}
	
	@Test
	public void testRandomRed() {
		for(int i = 0; i < 1000; i++) {
			final Color3F color = Color3F.randomRed();
			
			assertTrue(color.r >  0.0F && color.r <= 1.0F);
			assertTrue(color.g >= 0.0F && color.g <= 0.0F);
			assertTrue(color.b >= 0.0F && color.b <= 0.0F);
			
			assertTrue(color.r > color.g);
			assertTrue(color.r > color.b);
		}
	}
	
	@Test
	public void testRandomRedFloatFloat() {
		for(int i = 0; i < 1000; i++) {
			final Color3F color = Color3F.randomRed(0.25F, 0.50F);
			
			assertTrue(color.r >  0.0F && color.r <= 1.00F);
			assertTrue(color.g >= 0.0F && color.g <= 0.25F);
			assertTrue(color.b >= 0.0F && color.b <= 0.50F);
			
			assertTrue(color.r > color.g);
			assertTrue(color.r > color.b);
		}
	}
	
	@Test
	public void testRandomYellow() {
		for(int i = 0; i < 1000; i++) {
			final Color3F color = Color3F.randomYellow();
			
			assertTrue(color.r >  0.0F && color.r <= 1.0F);
			assertTrue(color.g >  0.0F && color.g <= 1.0F);
			assertTrue(color.b >= 0.0F && color.b <= 0.0F);
			
			assertTrue(color.r > color.b);
			assertTrue(color.g > color.b);
			
			assertEquals(color.r, color.g);
		}
	}
	
	@Test
	public void testRandomYellowFloatFloat() {
		for(int i = 0; i < 1000; i++) {
			final Color3F color = Color3F.randomYellow(0.50F, 0.25F);
			
			assertTrue(color.r >= 0.5F && color.r <= 1.00F);
			assertTrue(color.g >= 0.5F && color.g <= 1.00F);
			assertTrue(color.b >= 0.0F && color.b <= 0.25F);
			
			assertTrue(color.r > color.b);
			assertTrue(color.g > color.b);
			
			assertEquals(color.r, color.g);
		}
	}
	
	@Test
	public void testRelativeLuminance() {
		final Color3F color = new Color3F(1.0F / 0.212671F, 1.0F / 0.715160F, 1.0F / 0.072169F);
		
		assertEquals(3.0F, color.relativeLuminance());
	}
	
	@Test
	public void testSepia() {
		final Color3F a = new Color3F(1.0F, 1.0F, 1.0F);
		final Color3F b = Color3F.sepia(a);
		
		assertEquals(1.351F, b.r);
		assertEquals(1.203F, b.g);
		assertEquals(0.937F, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3F.sepia(null));
	}
	
	@Test
	public void testToIntARGB() {
		final int expectedIntARGB = ((255 & 0xFF) << 24) | ((0 & 0xFF) << 16) | ((128 & 0xFF) << 8) | ((255 & 0xFF) << 0);
		
		final Color3F color = new Color3F(0.0F, 0.5F, 1.0F);
		
		assertEquals(expectedIntARGB, color.toIntARGB());
	}
	
	@Test
	public void testToIntB() {
		final Color3F a = new Color3F(0.0F, 0.0F, 0.0F);
		final Color3F b = new Color3F(0.0F, 0.0F, 0.5F);
		final Color3F c = new Color3F(0.0F, 0.0F, 1.0F);
		final Color3F d = new Color3F(0.0F, 0.0F, 2.0F);
		
		assertEquals(  0, a.toIntB());
		assertEquals(128, b.toIntB());
		assertEquals(255, c.toIntB());
		assertEquals(255, d.toIntB());
	}
	
	@Test
	public void testToIntG() {
		final Color3F a = new Color3F(0.0F, 0.0F, 0.0F);
		final Color3F b = new Color3F(0.0F, 0.5F, 0.0F);
		final Color3F c = new Color3F(0.0F, 1.0F, 0.0F);
		final Color3F d = new Color3F(0.0F, 2.0F, 0.0F);
		
		assertEquals(  0, a.toIntG());
		assertEquals(128, b.toIntG());
		assertEquals(255, c.toIntG());
		assertEquals(255, d.toIntG());
	}
	
	@Test
	public void testToIntR() {
		final Color3F a = new Color3F(0.0F, 0.0F, 0.0F);
		final Color3F b = new Color3F(0.5F, 0.0F, 0.0F);
		final Color3F c = new Color3F(1.0F, 0.0F, 0.0F);
		final Color3F d = new Color3F(2.0F, 0.0F, 0.0F);
		
		assertEquals(  0, a.toIntR());
		assertEquals(128, b.toIntR());
		assertEquals(255, c.toIntR());
		assertEquals(255, d.toIntR());
	}
	
	@Test
	public void testToIntRGB() {
		final int expectedIntRGB = ((0 & 0xFF) << 16) | ((128 & 0xFF) << 8) | ((255 & 0xFF) << 0);
		
		final Color3F color = new Color3F(0.0F, 0.5F, 1.0F);
		
		assertEquals(expectedIntRGB, color.toIntRGB());
	}
	
	@Test
	public void testToString() {
		final Color3F color = new Color3F(0.0F, 0.5F, 1.0F);
		
		assertEquals("new Color3F(0.0F, 0.5F, 1.0F)", color.toString());
	}
}