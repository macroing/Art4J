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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;
import org.macroing.img4j.color.Color4D;
import org.macroing.img4j.color.Color4F;

@SuppressWarnings("static-method")
public final class Color4FDataFactoryUnitTests {
	public Color4FDataFactoryUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testCreate() {
		final Color4FDataFactory color4FDataFactory = new Color4FDataFactory();
		
		final Data data = color4FDataFactory.create();
		
		assertTrue(data instanceof Color4FData);
		
		assertEquals(1024, data.getResolutionX());
		assertEquals( 768, data.getResolutionY());
	}
	
	@Test
	public void testCreateBufferedImage() {
		final Color4FDataFactory color4FDataFactory = new Color4FDataFactory();
		
		final Data data = color4FDataFactory.create(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
		
		assertTrue(data instanceof Color4FData);
		
		assertEquals(1, data.getResolution());
		assertEquals(1, data.getResolutionX());
		assertEquals(1, data.getResolutionY());
		
		assertThrows(NullPointerException.class, () -> color4FDataFactory.create((BufferedImage)(null)));
	}
	
	@Test
	public void testCreateIntInt() {
		final Color4FDataFactory color4FDataFactory = new Color4FDataFactory();
		
		final Data data = color4FDataFactory.create(1024, 768);
		
		assertTrue(data instanceof Color4FData);
		
		assertEquals(1024, data.getResolutionX());
		assertEquals( 768, data.getResolutionY());
		
		assertThrows(IllegalArgumentException.class, () -> color4FDataFactory.create(1, 0));
		assertThrows(IllegalArgumentException.class, () -> color4FDataFactory.create(0, 1));
		assertThrows(IllegalArgumentException.class, () -> color4FDataFactory.create(Integer.MAX_VALUE, Integer.MAX_VALUE));
	}
	
	@Test
	public void testCreateIntIntColor4D() {
		final Color4FDataFactory color4FDataFactory = new Color4FDataFactory();
		
		final Data data = color4FDataFactory.create(1024, 768, Color4D.WHITE);
		
		assertTrue(data instanceof Color4FData);
		
		assertEquals(1024, data.getResolutionX());
		assertEquals( 768, data.getResolutionY());
		
		assertThrows(IllegalArgumentException.class, () -> color4FDataFactory.create(1, 0, Color4D.WHITE));
		assertThrows(IllegalArgumentException.class, () -> color4FDataFactory.create(0, 1, Color4D.WHITE));
		assertThrows(IllegalArgumentException.class, () -> color4FDataFactory.create(Integer.MAX_VALUE, Integer.MAX_VALUE, Color4D.WHITE));
		
		assertThrows(NullPointerException.class, () -> color4FDataFactory.create(1, 1, (Color4D)(null)));
	}
	
	@Test
	public void testCreateIntIntColor4F() {
		final Color4FDataFactory color4FDataFactory = new Color4FDataFactory();
		
		final Data data = color4FDataFactory.create(1024, 768, Color4F.WHITE);
		
		assertTrue(data instanceof Color4FData);
		
		assertEquals(1024, data.getResolutionX());
		assertEquals( 768, data.getResolutionY());
		
		assertThrows(IllegalArgumentException.class, () -> color4FDataFactory.create(1, 0, Color4F.WHITE));
		assertThrows(IllegalArgumentException.class, () -> color4FDataFactory.create(0, 1, Color4F.WHITE));
		assertThrows(IllegalArgumentException.class, () -> color4FDataFactory.create(Integer.MAX_VALUE, Integer.MAX_VALUE, Color4F.WHITE));
		
		assertThrows(NullPointerException.class, () -> color4FDataFactory.create(1, 1, (Color4F)(null)));
	}
	
	@Test
	public void testForColor4F() {
		final DataFactory dataFactory = DataFactory.forColor4F();
		
		assertTrue(dataFactory instanceof Color4FDataFactory);
	}
}