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
public final class Color4FUnitTests {
	public Color4FUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAddColor4FColor4F() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = new Color4F(2.0F, 3.0F, 4.0F, 5.0F);
		final Color4F c = Color4F.add(a, b);
		
		assertEquals(3.0F, c.r);
		assertEquals(5.0F, c.g);
		assertEquals(7.0F, c.b);
		assertEquals(4.0F, c.a);
		
		assertThrows(NullPointerException.class, () -> Color4F.add(a, null));
		assertThrows(NullPointerException.class, () -> Color4F.add(null, b));
	}
	
	@Test
	public void testAddColor4FFloat() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4F.add(a, 2.0F);
		
		assertEquals(3.0F, b.r);
		assertEquals(4.0F, b.g);
		assertEquals(5.0F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4F.add(null, 2.0F));
	}
	
	@Test
	public void testAverage() {
		final Color4F color = new Color4F(0.0F, 0.5F, 1.0F, 2.0F);
		
		assertEquals(0.5F, color.average());
	}
	
	@Test
	public void testBlendColor4FColor4F() {
		final Color4F a = new Color4F(1.0F, 1.0F, 1.0F, 1.0F);
		final Color4F b = new Color4F(3.0F, 3.0F, 3.0F, 3.0F);
		final Color4F c = Color4F.blend(a, b);
		
		assertEquals(2.0F, c.r);
		assertEquals(2.0F, c.g);
		assertEquals(2.0F, c.b);
		assertEquals(2.0F, c.a);
		
		assertThrows(NullPointerException.class, () -> Color4F.blend(a, null));
		assertThrows(NullPointerException.class, () -> Color4F.blend(null, b));
	}
	
	@Test
	public void testBlendColor4FColor4FColor4FColor4FFloatFloat() {
		final Color4F a = new Color4F(0.0F, 0.0F, 0.0F, 0.0F);
		final Color4F b = new Color4F(2.0F, 0.0F, 2.0F, 0.0F);
		final Color4F c = new Color4F(0.0F, 0.0F, 0.0F, 0.0F);
		final Color4F d = new Color4F(0.0F, 2.0F, 0.0F, 2.0F);
		final Color4F e = Color4F.blend(a, b, c, d, 0.5F, 0.5F);
		
		assertEquals(0.5F, e.r);
		assertEquals(0.5F, e.g);
		assertEquals(0.5F, e.b);
		assertEquals(0.5F, e.a);
		
		assertThrows(NullPointerException.class, () -> Color4F.blend(a, b, c, null, 0.5F, 0.5F));
		assertThrows(NullPointerException.class, () -> Color4F.blend(a, b, null, d, 0.5F, 0.5F));
		assertThrows(NullPointerException.class, () -> Color4F.blend(a, null, c, d, 0.5F, 0.5F));
		assertThrows(NullPointerException.class, () -> Color4F.blend(null, b, c, d, 0.5F, 0.5F));
	}
	
	@Test
	public void testBlendColor4FColor4FFloat() {
		final Color4F a = new Color4F(1.0F, 1.0F, 1.0F, 1.0F);
		final Color4F b = new Color4F(5.0F, 5.0F, 5.0F, 5.0F);
		final Color4F c = Color4F.blend(a, b, 0.25F);
		
		assertEquals(2.0F, c.r);
		assertEquals(2.0F, c.g);
		assertEquals(2.0F, c.b);
		assertEquals(2.0F, c.a);
		
		assertThrows(NullPointerException.class, () -> Color4F.blend(a, null, 0.25F));
		assertThrows(NullPointerException.class, () -> Color4F.blend(null, b, 0.25F));
	}
	
	@Test
	public void testBlendColor4FColor4FFloatFloatFloat() {
		final Color4F a = new Color4F(1.0F, 1.0F, 1.0F, 1.0F);
		final Color4F b = new Color4F(2.0F, 2.0F, 2.0F, 2.0F);
		final Color4F c = Color4F.blend(a, b, 0.0F, 0.5F, 1.0F, 2.0F);
		
		assertEquals(1.0F, c.r);
		assertEquals(1.5F, c.g);
		assertEquals(2.0F, c.b);
		assertEquals(3.0F, c.a);
		
		assertThrows(NullPointerException.class, () -> Color4F.blend(a, null, 0.0F, 0.5F, 1.0F, 2.0F));
		assertThrows(NullPointerException.class, () -> Color4F.blend(null, b, 0.0F, 0.5F, 1.0F, 2.0F));
	}
	
	@Test
	public void testBlendOver() {
		final Color4F a = new Color4F(0.5F, 0.5F, 0.5F, 0.5F);
		final Color4F b = new Color4F(0.0F, 0.0F, 0.0F, 1.0F);
		final Color4F c = Color4F.blendOver(a, b);
		
		assertEquals(0.25F, c.r);
		assertEquals(0.25F, c.g);
		assertEquals(0.25F, c.b);
		assertEquals(1.00F, c.a);
		
		assertThrows(NullPointerException.class, () -> Color4F.blendOver(a, null));
		assertThrows(NullPointerException.class, () -> Color4F.blendOver(null, b));
	}
	
	@Test
	public void testClearCacheAndGetCacheSizeAndGetCached() {
		Color4F.clearCache();
		
		assertEquals(0, Color4F.getCacheSize());
		
		final Color4F a = new Color4F(0.0F, 0.0F, 0.0F, 1.0F);
		final Color4F b = new Color4F(0.0F, 0.0F, 0.0F, 1.0F);
		final Color4F c = Color4F.getCached(a);
		final Color4F d = Color4F.getCached(b);
		
		assertThrows(NullPointerException.class, () -> Color4F.getCached(null));
		
		assertEquals(1, Color4F.getCacheSize());
		
		Color4F.clearCache();
		
		assertEquals(0, Color4F.getCacheSize());
		
		assertTrue(a != b);
		assertTrue(a == c);
		assertTrue(a == d);
		
		assertTrue(b != a);
		assertTrue(b != c);
		assertTrue(b != d);
	}
	
	@Test
	public void testConstants() {
		assertEquals(new Color4F(0.0F, 0.0F, 0.0F, 1.0F), Color4F.BLACK);
		assertEquals(new Color4F(0.0F, 0.0F, 1.0F, 1.0F), Color4F.BLUE);
		assertEquals(new Color4F(0.0F, 1.0F, 1.0F, 1.0F), Color4F.CYAN);
		assertEquals(new Color4F(0.5F, 0.5F, 0.5F, 1.0F), Color4F.GRAY);
		assertEquals(new Color4F(0.0F, 1.0F, 0.0F, 1.0F), Color4F.GREEN);
		assertEquals(new Color4F(1.0F, 0.0F, 1.0F, 1.0F), Color4F.MAGENTA);
		assertEquals(new Color4F(1.0F, 0.0F, 0.0F, 1.0F), Color4F.RED);
		assertEquals(new Color4F(0.0F, 0.0F, 0.0F, 0.0F), Color4F.TRANSPARENT);
		assertEquals(new Color4F(1.0F, 1.0F, 1.0F, 1.0F), Color4F.WHITE);
		assertEquals(new Color4F(1.0F, 1.0F, 0.0F, 1.0F), Color4F.YELLOW);
	}
	
	@Test
	public void testConstructor() {
		final Color4F color = new Color4F();
		
		assertEquals(0.0F, color.r);
		assertEquals(0.0F, color.g);
		assertEquals(0.0F, color.b);
		assertEquals(1.0F, color.a);
	}
	
	@Test
	public void testConstructorColor3D() {
		final Color4F color = new Color4F(new Color3D(1.0D, 1.0D, 1.0D));
		
		assertEquals(1.0F, color.r);
		assertEquals(1.0F, color.g);
		assertEquals(1.0F, color.b);
		assertEquals(1.0F, color.a);
		
		assertThrows(NullPointerException.class, () -> new Color4F((Color3D)(null)));
	}
	
	@Test
	public void testConstructorColor3DFloat() {
		final Color4F color = new Color4F(new Color3D(1.0D, 1.0D, 1.0D), 0.0F);
		
		assertEquals(1.0F, color.r);
		assertEquals(1.0F, color.g);
		assertEquals(1.0F, color.b);
		assertEquals(0.0F, color.a);
		
		assertThrows(NullPointerException.class, () -> new Color4F((Color3D)(null), 0.0F));
	}
	
	@Test
	public void testConstructorColor3F() {
		final Color4F color = new Color4F(new Color3F(1.0F, 1.0F, 1.0F));
		
		assertEquals(1.0F, color.r);
		assertEquals(1.0F, color.g);
		assertEquals(1.0F, color.b);
		assertEquals(1.0F, color.a);
		
		assertThrows(NullPointerException.class, () -> new Color4F((Color3F)(null)));
	}
	
	@Test
	public void testConstructorColor3FFloat() {
		final Color4F color = new Color4F(new Color3F(1.0F, 1.0F, 1.0F), 0.0F);
		
		assertEquals(1.0F, color.r);
		assertEquals(1.0F, color.g);
		assertEquals(1.0F, color.b);
		assertEquals(0.0F, color.a);
		
		assertThrows(NullPointerException.class, () -> new Color4F((Color3F)(null), 0.0F));
	}
	
	@Test
	public void testConstructorColor4D() {
		final Color4F color = new Color4F(new Color4D(1.0D, 1.0D, 1.0D, 0.0D));
		
		assertEquals(1.0F, color.r);
		assertEquals(1.0F, color.g);
		assertEquals(1.0F, color.b);
		assertEquals(0.0F, color.a);
		
		assertThrows(NullPointerException.class, () -> new Color4F((Color4D)(null)));
	}
	
	@Test
	public void testConstructorFloat() {
		final Color4F color = new Color4F(2.0F);
		
		assertEquals(2.0F, color.r);
		assertEquals(2.0F, color.g);
		assertEquals(2.0F, color.b);
		assertEquals(1.0F, color.a);
	}
	
	@Test
	public void testConstructorFloatFloat() {
		final Color4F color = new Color4F(2.0F, 0.0F);
		
		assertEquals(2.0F, color.r);
		assertEquals(2.0F, color.g);
		assertEquals(2.0F, color.b);
		assertEquals(0.0F, color.a);
	}
	
	@Test
	public void testConstructorFloatFloatFloat() {
		final Color4F color = new Color4F(0.0F, 1.0F, 2.0F);
		
		assertEquals(0.0F, color.r);
		assertEquals(1.0F, color.g);
		assertEquals(2.0F, color.b);
		assertEquals(1.0F, color.a);
	}
	
	@Test
	public void testConstructorFloatFloatFloatFloat() {
		final Color4F color = new Color4F(0.0F, 1.0F, 2.0F, 3.0F);
		
		assertEquals(0.0F, color.r);
		assertEquals(1.0F, color.g);
		assertEquals(2.0F, color.b);
		assertEquals(3.0F, color.a);
	}
	
	@Test
	public void testConstructorInt() {
		final Color4F color = new Color4F(255);
		
		assertEquals(1.0F, color.r);
		assertEquals(1.0F, color.g);
		assertEquals(1.0F, color.b);
		assertEquals(1.0F, color.a);
	}
	
	@Test
	public void testConstructorIntInt() {
		final Color4F color = new Color4F(255, 0);
		
		assertEquals(1.0F, color.r);
		assertEquals(1.0F, color.g);
		assertEquals(1.0F, color.b);
		assertEquals(0.0F, color.a);
	}
	
	@Test
	public void testConstructorIntIntInt() {
		final Color4F color = new Color4F(0, 255, 300);
		
		assertEquals(0.0F, color.r);
		assertEquals(1.0F, color.g);
		assertEquals(1.0F, color.b);
		assertEquals(1.0F, color.a);
	}
	
	@Test
	public void testConstructorIntIntIntInt() {
		final Color4F color = new Color4F(0, 255, 300, 400);
		
		assertEquals(0.0F, color.r);
		assertEquals(1.0F, color.g);
		assertEquals(1.0F, color.b);
		assertEquals(1.0F, color.a);
	}
	
	@Test
	public void testEqualsColor4F() {
		final Color4F a = new Color4F(0.0F, 0.5F, 1.0F, 1.5F);
		final Color4F b = new Color4F(0.0F, 0.5F, 1.0F, 1.5F);
		final Color4F c = new Color4F(0.0F, 0.5F, 1.0F, 2.0F);
		final Color4F d = new Color4F(0.0F, 0.5F, 2.0F, 1.5F);
		final Color4F e = new Color4F(0.0F, 2.0F, 1.0F, 1.5F);
		final Color4F f = new Color4F(2.0F, 0.5F, 1.0F, 1.5F);
		final Color4F g = null;
		
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
		assertFalse(f.equals(a));
		assertFalse(a.equals(g));
	}
	
	@Test
	public void testEqualsObject() {
		final Color4F a = new Color4F(0.0F, 0.5F, 1.0F, 1.5F);
		final Color4F b = new Color4F(0.0F, 0.5F, 1.0F, 1.5F);
		final Color4F c = new Color4F(0.0F, 0.5F, 1.0F, 2.0F);
		final Color4F d = new Color4F(0.0F, 0.5F, 2.0F, 1.5F);
		final Color4F e = new Color4F(0.0F, 2.0F, 1.0F, 1.5F);
		final Color4F f = new Color4F(2.0F, 0.5F, 1.0F, 1.5F);
		final Color4F g = null;
		
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
	public void testFromIntARGB() {
		final int colorARGB = ((255 & 0xFF) << 24) | ((0 & 0xFF) << 16) | ((128 & 0xFF) << 8) | ((255 & 0xFF) << 0);
		
		final Color4F color = Color4F.fromIntARGB(colorARGB);
		
		assertEquals(  0, color.toIntR());
		assertEquals(128, color.toIntG());
		assertEquals(255, color.toIntB());
		assertEquals(255, color.toIntA());
	}
	
	@Test
	public void testFromIntRGB() {
		final int colorRGB = ((0 & 0xFF) << 16) | ((128 & 0xFF) << 8) | ((255 & 0xFF) << 0);
		
		final Color4F color = Color4F.fromIntRGB(colorRGB);
		
		assertEquals(  0, color.toIntR());
		assertEquals(128, color.toIntG());
		assertEquals(255, color.toIntB());
		assertEquals(255, color.toIntA());
	}
	
	@Test
	public void testGrayscaleA() {
		final Color4F a = new Color4F(0.0F, 0.0F, 0.0F, 1.0F);
		final Color4F b = Color4F.grayscaleA(a);
		
		assertEquals(1.0F, b.r);
		assertEquals(1.0F, b.g);
		assertEquals(1.0F, b.b);
		assertEquals(1.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4F.grayscaleA(null));
	}
	
	@Test
	public void testGrayscaleAverage() {
		final Color4F a = new Color4F(0.0F, 0.5F, 1.0F, 1.5F);
		final Color4F b = Color4F.grayscaleAverage(a);
		
		assertEquals(0.5F, b.r);
		assertEquals(0.5F, b.g);
		assertEquals(0.5F, b.b);
		assertEquals(1.5F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4F.grayscaleAverage(null));
	}
	
	@Test
	public void testGrayscaleB() {
		final Color4F a = new Color4F(0.0F, 0.5F, 1.0F, 1.5F);
		final Color4F b = Color4F.grayscaleB(a);
		
		assertEquals(1.0F, b.r);
		assertEquals(1.0F, b.g);
		assertEquals(1.0F, b.b);
		assertEquals(1.5F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4F.grayscaleB(null));
	}
	
	@Test
	public void testGrayscaleG() {
		final Color4F a = new Color4F(0.0F, 0.5F, 1.0F, 1.5F);
		final Color4F b = Color4F.grayscaleG(a);
		
		assertEquals(0.5F, b.r);
		assertEquals(0.5F, b.g);
		assertEquals(0.5F, b.b);
		assertEquals(1.5F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4F.grayscaleG(null));
	}
	
	@Test
	public void testGrayscaleLightness() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4F.grayscaleLightness(a);
		
		assertEquals(2.0F, b.r);
		assertEquals(2.0F, b.g);
		assertEquals(2.0F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4F.grayscaleLightness(null));
	}
	
	@Test
	public void testGrayscaleMax() {
		final Color4F a = new Color4F(0.0F, 1.0F, 2.0F, 3.0F);
		final Color4F b = Color4F.grayscaleMax(a);
		
		assertEquals(2.0F, b.r);
		assertEquals(2.0F, b.g);
		assertEquals(2.0F, b.b);
		assertEquals(3.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4F.grayscaleMax(null));
	}
	
	@Test
	public void testGrayscaleMin() {
		final Color4F a = new Color4F(0.0F, 1.0F, 2.0F, 3.0F);
		final Color4F b = Color4F.grayscaleMin(a);
		
		assertEquals(0.0F, b.r);
		assertEquals(0.0F, b.g);
		assertEquals(0.0F, b.b);
		assertEquals(3.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4F.grayscaleMin(null));
	}
	
	@Test
	public void testGrayscaleR() {
		final Color4F a = new Color4F(0.0F, 0.5F, 1.0F, 1.5F);
		final Color4F b = Color4F.grayscaleR(a);
		
		assertEquals(0.0F, b.r);
		assertEquals(0.0F, b.g);
		assertEquals(0.0F, b.b);
		assertEquals(1.5F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4F.grayscaleR(null));
	}
	
	@Test
	public void testGrayscaleRelativeLuminance() {
		final Color4F a = new Color4F(1.0F / 0.212671F, 1.0F / 0.715160F, 1.0F / 0.072169F, 0.0F);
		final Color4F b = Color4F.grayscaleRelativeLuminance(a);
		
		assertEquals(3.0F, b.r);
		assertEquals(3.0F, b.g);
		assertEquals(3.0F, b.b);
		assertEquals(0.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4F.grayscaleRelativeLuminance(null));
	}
	
	@Test
	public void testHashCode() {
		final Color4F a = new Color4F(0.0F, 0.5F, 1.0F, 1.5F);
		final Color4F b = new Color4F(0.0F, 0.5F, 1.0F, 1.5F);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testInvert() {
		final Color4F a = new Color4F(0.75F, 0.75F, 0.75F, 0.75F);
		final Color4F b = Color4F.invert(a);
		
		assertEquals(0.25F, b.r);
		assertEquals(0.25F, b.g);
		assertEquals(0.25F, b.b);
		assertEquals(0.75F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4F.invert(null));
	}
	
	@Test
	public void testIsBlack() {
		final Color4F a = new Color4F(0.0F, 0.0F, 0.0F);
		final Color4F b = new Color4F(0.0F, 0.0F, 1.0F);
		final Color4F c = new Color4F(0.0F, 1.0F, 0.0F);
		final Color4F d = new Color4F(1.0F, 0.0F, 0.0F);
		final Color4F e = new Color4F(0.0F, 1.0F, 1.0F);
		final Color4F f = new Color4F(1.0F, 1.0F, 0.0F);
		final Color4F g = new Color4F(1.0F, 1.0F, 1.0F);
		
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
		final Color4F a = new Color4F(0.0F, 0.0F, 1.0F);
		final Color4F b = new Color4F(0.5F, 0.5F, 1.0F);
		
		assertTrue(a.isBlue());
		
		assertFalse(b.isBlue());
	}
	
	@Test
	public void testIsBlueFloatFloat() {
		final Color4F a = new Color4F(0.0F, 0.0F, 1.0F);
		final Color4F b = new Color4F(0.5F, 0.5F, 1.0F);
		final Color4F c = new Color4F(1.0F, 1.0F, 1.0F);
		
		assertTrue(a.isBlue(0.5F, 0.5F));
		assertTrue(b.isBlue(0.5F, 0.5F));
		
		assertFalse(b.isBlue(0.5F, 1.0F));
		assertFalse(b.isBlue(1.0F, 0.5F));
		assertFalse(c.isBlue(0.5F, 0.5F));
	}
	
	@Test
	public void testIsCyan() {
		final Color4F a = new Color4F(0.0F, 1.0F, 1.0F);
		final Color4F b = new Color4F(0.0F, 0.5F, 0.5F);
		final Color4F c = new Color4F(1.0F, 1.0F, 1.0F);
		final Color4F d = new Color4F(0.0F, 0.5F, 1.0F);
		
		assertTrue(a.isCyan());
		assertTrue(b.isCyan());
		
		assertFalse(c.isCyan());
		assertFalse(d.isCyan());
	}
	
	@Test
	public void testIsGrayscale() {
		final Color4F a = new Color4F(0.0F, 0.0F, 0.0F);
		final Color4F b = new Color4F(0.5F, 0.5F, 0.5F);
		final Color4F c = new Color4F(1.0F, 1.0F, 1.0F);
		final Color4F d = new Color4F(0.0F, 0.0F, 0.5F);
		final Color4F e = new Color4F(0.0F, 0.5F, 0.5F);
		final Color4F f = new Color4F(0.0F, 0.5F, 0.0F);
		final Color4F g = new Color4F(0.0F, 0.5F, 1.0F);
		
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
		final Color4F a = new Color4F(0.0F, 1.0F, 0.0F);
		final Color4F b = new Color4F(0.5F, 1.0F, 0.5F);
		
		assertTrue(a.isGreen());
		
		assertFalse(b.isGreen());
	}
	
	@Test
	public void testIsGreenFloatFloat() {
		final Color4F a = new Color4F(0.0F, 1.0F, 0.0F);
		final Color4F b = new Color4F(0.5F, 1.0F, 0.5F);
		final Color4F c = new Color4F(1.0F, 1.0F, 1.0F);
		
		assertTrue(a.isGreen(0.5F, 0.5F));
		assertTrue(b.isGreen(0.5F, 0.5F));
		
		assertFalse(b.isGreen(0.5F, 1.0F));
		assertFalse(b.isGreen(1.0F, 0.5F));
		assertFalse(c.isGreen(0.5F, 0.5F));
	}
	
	@Test
	public void testIsMagenta() {
		final Color4F a = new Color4F(1.0F, 0.0F, 1.0F);
		final Color4F b = new Color4F(0.5F, 0.0F, 0.5F);
		final Color4F c = new Color4F(1.0F, 1.0F, 1.0F);
		final Color4F d = new Color4F(0.0F, 0.5F, 1.0F);
		
		assertTrue(a.isMagenta());
		assertTrue(b.isMagenta());
		
		assertFalse(c.isMagenta());
		assertFalse(d.isMagenta());
	}
	
	@Test
	public void testIsRed() {
		final Color4F a = new Color4F(1.0F, 0.0F, 0.0F);
		final Color4F b = new Color4F(1.0F, 0.5F, 0.5F);
		
		assertTrue(a.isRed());
		
		assertFalse(b.isRed());
	}
	
	@Test
	public void testIsRedFloatFloat() {
		final Color4F a = new Color4F(1.0F, 0.0F, 0.0F);
		final Color4F b = new Color4F(1.0F, 0.5F, 0.5F);
		final Color4F c = new Color4F(1.0F, 1.0F, 1.0F);
		
		assertTrue(a.isRed(0.5F, 0.5F));
		assertTrue(b.isRed(0.5F, 0.5F));
		
		assertFalse(b.isRed(0.5F, 1.0F));
		assertFalse(b.isRed(1.0F, 0.5F));
		assertFalse(c.isRed(0.5F, 0.5F));
	}
	
	@Test
	public void testIsWhite() {
		final Color4F a = new Color4F(1.0F, 1.0F, 1.0F);
		final Color4F b = new Color4F(1.0F, 1.0F, 0.0F);
		final Color4F c = new Color4F(1.0F, 0.0F, 0.0F);
		final Color4F d = new Color4F(0.0F, 0.0F, 0.0F);
		
		assertTrue(a.isWhite());
		
		assertFalse(b.isWhite());
		assertFalse(c.isWhite());
		assertFalse(d.isWhite());
	}
	
	@Test
	public void testIsYellow() {
		final Color4F a = new Color4F(1.0F, 1.0F, 0.0F);
		final Color4F b = new Color4F(0.5F, 0.5F, 0.0F);
		final Color4F c = new Color4F(1.0F, 1.0F, 1.0F);
		final Color4F d = new Color4F(0.0F, 0.5F, 1.0F);
		
		assertTrue(a.isYellow());
		assertTrue(b.isYellow());
		
		assertFalse(c.isYellow());
		assertFalse(d.isYellow());
	}
	
	@Test
	public void testLightness() {
		final Color4F color = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		
		assertEquals(2.0F, color.lightness());
	}
	
	@Test
	public void testMax() {
		final Color4F color = new Color4F(0.0F, 1.0F, 2.0F, 3.0F);
		
		assertEquals(2.0F, color.max());
	}
	
	@Test
	public void testMin() {
		final Color4F color = new Color4F(0.0F, 1.0F, 2.0F, 3.0F);
		
		assertEquals(0.0F, color.min());
	}
	
	@Test
	public void testMultiplyColor4FFloat() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4F.multiply(a, 2.0F);
		
		assertEquals(2.0F, b.r);
		assertEquals(4.0F, b.g);
		assertEquals(6.0F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4F.multiply(null, 2.0F));
	}
	
	@Test
	public void testRandom() {
		for(int i = 0; i < 1000; i++) {
			final Color4F color = Color4F.random();
			
			assertTrue(color.r >= 0.0F && color.r <= 1.0F);
			assertTrue(color.g >= 0.0F && color.g <= 1.0F);
			assertTrue(color.b >= 0.0F && color.b <= 1.0F);
			
			assertEquals(1.0F, color.a);
		}
	}
	
	@Test
	public void testRandomBlue() {
		for(int i = 0; i < 1000; i++) {
			final Color4F color = Color4F.randomBlue();
			
			assertTrue(color.r >= 0.0F && color.r <= 0.0F);
			assertTrue(color.g >= 0.0F && color.g <= 0.0F);
			assertTrue(color.b >  0.0F && color.b <= 1.0F);
			
			assertTrue(color.b > color.r);
			assertTrue(color.b > color.g);
			
			assertEquals(1.0F, color.a);
		}
	}
	
	@Test
	public void testRandomBlueFloatFloat() {
		for(int i = 0; i < 1000; i++) {
			final Color4F color = Color4F.randomBlue(0.25F, 0.50F);
			
			assertTrue(color.r >= 0.0F && color.r <= 0.25F);
			assertTrue(color.g >= 0.0F && color.g <= 0.50F);
			assertTrue(color.b >  0.0F && color.b <= 1.00F);
			
			assertTrue(color.b > color.r);
			assertTrue(color.b > color.g);
			
			assertEquals(1.0F, color.a);
		}
	}
	
	@Test
	public void testRandomCyan() {
		for(int i = 0; i < 1000; i++) {
			final Color4F color = Color4F.randomCyan();
			
			assertTrue(color.r >= 0.0F && color.r <= 0.0F);
			assertTrue(color.g >  0.0F && color.g <= 1.0F);
			assertTrue(color.b >  0.0F && color.b <= 1.0F);
			
			assertTrue(color.g > color.r);
			assertTrue(color.b > color.r);
			
			assertEquals(color.g, color.b);
			
			assertEquals(1.0F, color.a);
		}
	}
	
	@Test
	public void testRandomCyanFloatFloat() {
		for(int i = 0; i < 1000; i++) {
			final Color4F color = Color4F.randomCyan(0.50F, 0.25F);
			
			assertTrue(color.r >= 0.0F && color.r <= 0.25F);
			assertTrue(color.g >= 0.5F && color.g <= 1.00F);
			assertTrue(color.b >= 0.5F && color.b <= 1.00F);
			
			assertTrue(color.g > color.r);
			assertTrue(color.b > color.r);
			
			assertEquals(color.g, color.b);
			
			assertEquals(1.0F, color.a);
		}
	}
	
	@Test
	public void testRandomGrayscale() {
		for(int i = 0; i < 1000; i++) {
			final Color4F color = Color4F.randomGrayscale();
			
			assertTrue(color.r >= 0.0F && color.r <= 1.0F);
			assertTrue(color.g >= 0.0F && color.g <= 1.0F);
			assertTrue(color.b >= 0.0F && color.b <= 1.0F);
			
			assertEquals(color.r, color.g);
			assertEquals(color.g, color.b);
			
			assertEquals(1.0F, color.a);
		}
	}
	
	@Test
	public void testRandomGreen() {
		for(int i = 0; i < 1000; i++) {
			final Color4F color = Color4F.randomGreen();
			
			assertTrue(color.r >= 0.0F && color.r <= 0.0F);
			assertTrue(color.g >  0.0F && color.g <= 1.0F);
			assertTrue(color.b >= 0.0F && color.b <= 0.0F);
			
			assertTrue(color.g > color.r);
			assertTrue(color.g > color.b);
			
			assertEquals(1.0F, color.a);
		}
	}
	
	@Test
	public void testRandomGreenFloatFloat() {
		for(int i = 0; i < 1000; i++) {
			final Color4F color = Color4F.randomGreen(0.25F, 0.50F);
			
			assertTrue(color.r >= 0.0F && color.r <= 0.25F);
			assertTrue(color.g >  0.0F && color.g <= 1.00F);
			assertTrue(color.b >= 0.0F && color.b <= 0.50F);
			
			assertTrue(color.g > color.r);
			assertTrue(color.g > color.b);
			
			assertEquals(1.0F, color.a);
		}
	}
	
	@Test
	public void testRandomMagenta() {
		for(int i = 0; i < 1000; i++) {
			final Color4F color = Color4F.randomMagenta();
			
			assertTrue(color.r >  0.0F && color.r <= 1.0F);
			assertTrue(color.g >= 0.0F && color.g <= 0.0F);
			assertTrue(color.b >  0.0F && color.b <= 1.0F);
			
			assertTrue(color.r > color.g);
			assertTrue(color.b > color.g);
			
			assertEquals(color.r, color.b);
			
			assertEquals(1.0F, color.a);
		}
	}
	
	@Test
	public void testRandomMagentaFloatFloat() {
		for(int i = 0; i < 1000; i++) {
			final Color4F color = Color4F.randomMagenta(0.50F, 0.25F);
			
			assertTrue(color.r >= 0.5F && color.r <= 1.00F);
			assertTrue(color.g >= 0.0F && color.g <= 0.25F);
			assertTrue(color.b >= 0.5F && color.b <= 1.00F);
			
			assertTrue(color.r > color.g);
			assertTrue(color.b > color.g);
			
			assertEquals(color.r, color.b);
			
			assertEquals(1.0F, color.a);
		}
	}
	
	@Test
	public void testRandomRed() {
		for(int i = 0; i < 1000; i++) {
			final Color4F color = Color4F.randomRed();
			
			assertTrue(color.r >  0.0F && color.r <= 1.0F);
			assertTrue(color.g >= 0.0F && color.g <= 0.0F);
			assertTrue(color.b >= 0.0F && color.b <= 0.0F);
			
			assertTrue(color.r > color.g);
			assertTrue(color.r > color.b);
			
			assertEquals(1.0F, color.a);
		}
	}
	
	@Test
	public void testRandomRedFloatFloat() {
		for(int i = 0; i < 1000; i++) {
			final Color4F color = Color4F.randomRed(0.25F, 0.50F);
			
			assertTrue(color.r >  0.0F && color.r <= 1.00F);
			assertTrue(color.g >= 0.0F && color.g <= 0.25F);
			assertTrue(color.b >= 0.0F && color.b <= 0.50F);
			
			assertTrue(color.r > color.g);
			assertTrue(color.r > color.b);
			
			assertEquals(1.0F, color.a);
		}
	}
	
	@Test
	public void testRandomYellow() {
		for(int i = 0; i < 1000; i++) {
			final Color4F color = Color4F.randomYellow();
			
			assertTrue(color.r >  0.0F && color.r <= 1.0F);
			assertTrue(color.g >  0.0F && color.g <= 1.0F);
			assertTrue(color.b >= 0.0F && color.b <= 0.0F);
			
			assertTrue(color.r > color.b);
			assertTrue(color.g > color.b);
			
			assertEquals(color.r, color.g);
			
			assertEquals(1.0F, color.a);
		}
	}
	
	@Test
	public void testRandomYellowFloatFloat() {
		for(int i = 0; i < 1000; i++) {
			final Color4F color = Color4F.randomYellow(0.50F, 0.25F);
			
			assertTrue(color.r >= 0.5F && color.r <= 1.00F);
			assertTrue(color.g >= 0.5F && color.g <= 1.00F);
			assertTrue(color.b >= 0.0F && color.b <= 0.25F);
			
			assertTrue(color.r > color.b);
			assertTrue(color.g > color.b);
			
			assertEquals(color.r, color.g);
			
			assertEquals(1.0F, color.a);
		}
	}
	
	@Test
	public void testRelativeLuminance() {
		final Color4F color = new Color4F(1.0F / 0.212671F, 1.0F / 0.715160F, 1.0F / 0.072169F, 1.0F);
		
		assertEquals(3.0F, color.relativeLuminance());
	}
	
	@Test
	public void testSepia() {
		final Color4F a = new Color4F(1.0F, 1.0F, 1.0F, 1.0F);
		final Color4F b = Color4F.sepia(a);
		
		assertEquals(1.351F, b.r);
		assertEquals(1.203F, b.g);
		assertEquals(0.937F, b.b);
		assertEquals(1.000F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4F.sepia(null));
	}
	
	@Test
	public void testToIntA() {
		final Color4F a = new Color4F(0.0F, 0.0F, 0.0F, 0.0F);
		final Color4F b = new Color4F(0.0F, 0.0F, 0.0F, 0.5F);
		final Color4F c = new Color4F(0.0F, 0.0F, 0.0F, 1.0F);
		final Color4F d = new Color4F(0.0F, 0.0F, 0.0F, 2.0F);
		
		assertEquals(  0, a.toIntA());
		assertEquals(128, b.toIntA());
		assertEquals(255, c.toIntA());
		assertEquals(255, d.toIntA());
	}
	
	@Test
	public void testToIntARGB() {
		final int expectedIntARGB = ((255 & 0xFF) << 24) | ((0 & 0xFF) << 16) | ((128 & 0xFF) << 8) | ((255 & 0xFF) << 0);
		
		final Color4F color = new Color4F(0.0F, 0.5F, 1.0F, 2.0F);
		
		assertEquals(expectedIntARGB, color.toIntARGB());
	}
	
	@Test
	public void testToIntB() {
		final Color4F a = new Color4F(0.0F, 0.0F, 0.0F, 0.0F);
		final Color4F b = new Color4F(0.0F, 0.0F, 0.5F, 0.0F);
		final Color4F c = new Color4F(0.0F, 0.0F, 1.0F, 0.0F);
		final Color4F d = new Color4F(0.0F, 0.0F, 2.0F, 0.0F);
		
		assertEquals(  0, a.toIntB());
		assertEquals(128, b.toIntB());
		assertEquals(255, c.toIntB());
		assertEquals(255, d.toIntB());
	}
	
	@Test
	public void testToIntG() {
		final Color4F a = new Color4F(0.0F, 0.0F, 0.0F, 0.0F);
		final Color4F b = new Color4F(0.0F, 0.5F, 0.0F, 0.0F);
		final Color4F c = new Color4F(0.0F, 1.0F, 0.0F, 0.0F);
		final Color4F d = new Color4F(0.0F, 2.0F, 0.0F, 0.0F);
		
		assertEquals(  0, a.toIntG());
		assertEquals(128, b.toIntG());
		assertEquals(255, c.toIntG());
		assertEquals(255, d.toIntG());
	}
	
	@Test
	public void testToIntR() {
		final Color4F a = new Color4F(0.0F, 0.0F, 0.0F, 0.0F);
		final Color4F b = new Color4F(0.5F, 0.0F, 0.0F, 0.0F);
		final Color4F c = new Color4F(1.0F, 0.0F, 0.0F, 0.0F);
		final Color4F d = new Color4F(2.0F, 0.0F, 0.0F, 0.0F);
		
		assertEquals(  0, a.toIntR());
		assertEquals(128, b.toIntR());
		assertEquals(255, c.toIntR());
		assertEquals(255, d.toIntR());
	}
	
	@Test
	public void testToIntRGB() {
		final int expectedIntRGB = ((0 & 0xFF) << 16) | ((128 & 0xFF) << 8) | ((255 & 0xFF) << 0);
		
		final Color4F color = new Color4F(0.0F, 0.5F, 1.0F, 1.0F);
		
		assertEquals(expectedIntRGB, color.toIntRGB());
	}
	
	@Test
	public void testToString() {
		final Color4F color = new Color4F(0.0F, 0.25F, 0.75F, 1.0F);
		
		assertEquals("new Color4F(0.0F, 0.25F, 0.75F, 1.0F)", color.toString());
	}
}