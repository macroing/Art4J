/**
 * Copyright 2022 - 2024 J&#246;rgen Lundgren
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;

import org.macroing.art4j.color.Color4D;
import org.macroing.art4j.color.Color4F;
import org.macroing.art4j.color.Color4I;

@SuppressWarnings("static-method")
public final class Color4DDataFactoryUnitTests {
	public Color4DDataFactoryUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testCreate() {
		final Color4DDataFactory color4DDataFactory = new Color4DDataFactory();
		
		final Data data = color4DDataFactory.create();
		
		assertTrue(data instanceof Color4DData);
		
		assertEquals(1024, data.getResolutionX());
		assertEquals( 768, data.getResolutionY());
	}
	
	@Test
	public void testCreateBufferedImage() {
		final Color4DDataFactory color4DDataFactory = new Color4DDataFactory();
		
		final Data data = color4DDataFactory.create(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
		
		assertTrue(data instanceof Color4DData);
		
		assertEquals(1, data.getResolution());
		assertEquals(1, data.getResolutionX());
		assertEquals(1, data.getResolutionY());
		
		assertThrows(NullPointerException.class, () -> color4DDataFactory.create((BufferedImage)(null)));
	}
	
	@Test
	public void testCreateFile() {
		final Color4DDataFactory color4DDataFactory = new Color4DDataFactory();
		
		final Data dataA = color4DDataFactory.create(1, 1);
		
		final File directory = new File(String.format("./generated/%s", Long.toString(System.currentTimeMillis())));
		
		final File file = new File(directory, "Color4DData.png");
		
		dataA.save(file, "png");
		
		final Data dataB = color4DDataFactory.create(file);
		
		assertTrue(dataB instanceof Color4DData);
		
		assertEquals(1, dataB.getResolution());
		assertEquals(1, dataB.getResolutionX());
		assertEquals(1, dataB.getResolutionY());
		
		assertThrows(NullPointerException.class, () -> color4DDataFactory.create((File)(null)));
		
		assertThrows(UncheckedIOException.class, () -> color4DDataFactory.create(new File(directory, "Color4DData.jpg")));
		
		file.delete();
		
		directory.delete();
	}
	
	@Test
	public void testCreateIntInt() {
		final Color4DDataFactory color4DDataFactory = new Color4DDataFactory();
		
		final Data data = color4DDataFactory.create(1024, 768);
		
		assertTrue(data instanceof Color4DData);
		
		assertEquals(1024, data.getResolutionX());
		assertEquals( 768, data.getResolutionY());
		
		assertThrows(IllegalArgumentException.class, () -> color4DDataFactory.create(1, 0));
		assertThrows(IllegalArgumentException.class, () -> color4DDataFactory.create(0, 1));
		assertThrows(IllegalArgumentException.class, () -> color4DDataFactory.create(Integer.MAX_VALUE, Integer.MAX_VALUE));
	}
	
	@Test
	public void testCreateIntIntColor4D() {
		final Color4DDataFactory color4DDataFactory = new Color4DDataFactory();
		
		final Data data = color4DDataFactory.create(1024, 768, Color4D.WHITE);
		
		assertTrue(data instanceof Color4DData);
		
		assertEquals(1024, data.getResolutionX());
		assertEquals( 768, data.getResolutionY());
		
		assertThrows(IllegalArgumentException.class, () -> color4DDataFactory.create(1, 0, Color4D.WHITE));
		assertThrows(IllegalArgumentException.class, () -> color4DDataFactory.create(0, 1, Color4D.WHITE));
		assertThrows(IllegalArgumentException.class, () -> color4DDataFactory.create(Integer.MAX_VALUE, Integer.MAX_VALUE, Color4D.WHITE));
		
		assertThrows(NullPointerException.class, () -> color4DDataFactory.create(1, 1, (Color4D)(null)));
	}
	
	@Test
	public void testCreateIntIntColor4F() {
		final Color4DDataFactory color4DDataFactory = new Color4DDataFactory();
		
		final Data data = color4DDataFactory.create(1024, 768, Color4F.WHITE);
		
		assertTrue(data instanceof Color4DData);
		
		assertEquals(1024, data.getResolutionX());
		assertEquals( 768, data.getResolutionY());
		
		assertThrows(IllegalArgumentException.class, () -> color4DDataFactory.create(1, 0, Color4F.WHITE));
		assertThrows(IllegalArgumentException.class, () -> color4DDataFactory.create(0, 1, Color4F.WHITE));
		assertThrows(IllegalArgumentException.class, () -> color4DDataFactory.create(Integer.MAX_VALUE, Integer.MAX_VALUE, Color4F.WHITE));
		
		assertThrows(NullPointerException.class, () -> color4DDataFactory.create(1, 1, (Color4F)(null)));
	}
	
	@Test
	public void testCreateIntIntInt() {
		final Color4DDataFactory color4DDataFactory = new Color4DDataFactory();
		
		final Data data = color4DDataFactory.create(1024, 768, Color4I.WHITE_A_R_G_B);
		
		assertTrue(data instanceof Color4DData);
		
		assertEquals(1024, data.getResolutionX());
		assertEquals( 768, data.getResolutionY());
		
		for(int y = 0; y < data.getResolutionY(); y++) {
			for(int x = 0; x < data.getResolutionX(); x++) {
				assertEquals(Color4D.WHITE, data.getColor4D(x, y));
			}
		}
		
		assertThrows(IllegalArgumentException.class, () -> color4DDataFactory.create(1, 0, Color4I.WHITE_A_R_G_B));
		assertThrows(IllegalArgumentException.class, () -> color4DDataFactory.create(0, 1, Color4I.WHITE_A_R_G_B));
		assertThrows(IllegalArgumentException.class, () -> color4DDataFactory.create(Integer.MAX_VALUE, Integer.MAX_VALUE, Color4I.WHITE_A_R_G_B));
	}
	
	@Test
	public void testCreateString() {
		final Color4DDataFactory color4DDataFactory = new Color4DDataFactory();
		
		final Data dataA = color4DDataFactory.create(1, 1);
		
		final File directory = new File(String.format("./generated/%s", Long.toString(System.currentTimeMillis())));
		
		final File file = new File(directory, "Color4DData.png");
		
		dataA.save(file, "png");
		
		final Data dataB = color4DDataFactory.create(file.getAbsolutePath());
		
		assertTrue(dataB instanceof Color4DData);
		
		assertEquals(1, dataB.getResolution());
		assertEquals(1, dataB.getResolutionX());
		assertEquals(1, dataB.getResolutionY());
		
		assertThrows(NullPointerException.class, () -> color4DDataFactory.create((String)(null)));
		
		assertThrows(UncheckedIOException.class, () -> color4DDataFactory.create(file.getAbsolutePath() + ".jpg"));
		
		file.delete();
		
		directory.delete();
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testCreateURL() {
		try {
			final Color4DDataFactory color4DDataFactory = new Color4DDataFactory();
			
			final Data dataA = color4DDataFactory.create(1, 1);
			
			final File directory = new File(String.format("./generated/%s", Long.toString(System.currentTimeMillis())));
			
			final File file = new File(directory, "Color4DData.png");
			
			final URL uRL = file.toURI().toURL();
			
			dataA.save(file, "png");
			
			final Data dataB = color4DDataFactory.create(uRL);
			
			assertTrue(dataB instanceof Color4DData);
			
			assertEquals(1, dataB.getResolution());
			assertEquals(1, dataB.getResolutionX());
			assertEquals(1, dataB.getResolutionY());
			
			assertThrows(NullPointerException.class, () -> color4DDataFactory.create((URL)(null)));
			
			assertThrows(UncheckedIOException.class, () -> color4DDataFactory.create(new File(directory, "Color4DData.jpg").toURI().toURL()));
			
			file.delete();
			
			directory.delete();
		} catch(final MalformedURLException e) {
//			Do nothing.
		}
	}
	
	@Test
	public void testForColor4D() {
		final DataFactory dataFactory = DataFactory.forColor4D();
		
		assertTrue(dataFactory instanceof Color4DDataFactory);
	}
}