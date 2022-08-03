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
import java.io.File;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;

import org.macroing.img4j.color.Color4D;
import org.macroing.img4j.color.Color4F;

@SuppressWarnings("static-method")
public final class ColorARGBDataFactoryUnitTests {
	public ColorARGBDataFactoryUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testCreate() {
		final ColorARGBDataFactory colorARGBDataFactory = new ColorARGBDataFactory();
		
		final Data data = colorARGBDataFactory.create();
		
		assertTrue(data instanceof ColorARGBData);
		
		assertEquals(1024, data.getResolutionX());
		assertEquals( 768, data.getResolutionY());
	}
	
	@Test
	public void testCreateBufferedImage() {
		final ColorARGBDataFactory colorARGBDataFactory = new ColorARGBDataFactory();
		
		final Data data = colorARGBDataFactory.create(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
		
		assertTrue(data instanceof ColorARGBData);
		
		assertEquals(1, data.getResolution());
		assertEquals(1, data.getResolutionX());
		assertEquals(1, data.getResolutionY());
		
		assertThrows(NullPointerException.class, () -> colorARGBDataFactory.create((BufferedImage)(null)));
	}
	
	@Test
	public void testCreateFile() {
		final ColorARGBDataFactory colorARGBDataFactory = new ColorARGBDataFactory();
		
		final Data dataA = colorARGBDataFactory.create(1, 1);
		
		final File directory = new File(String.format("./generated/%s", Long.toString(System.currentTimeMillis())));
		
		final File file = new File(directory, "ColorARGBData.png");
		
		dataA.save(file, "png");
		
		final Data dataB = colorARGBDataFactory.create(file);
		
		assertTrue(dataB instanceof ColorARGBData);
		
		assertEquals(1, dataB.getResolution());
		assertEquals(1, dataB.getResolutionX());
		assertEquals(1, dataB.getResolutionY());
		
		assertThrows(NullPointerException.class, () -> colorARGBDataFactory.create((File)(null)));
		
		assertThrows(UncheckedIOException.class, () -> colorARGBDataFactory.create(new File(directory, "ColorARGBData.jpg")));
		
		file.delete();
		
		directory.delete();
	}
	
	@Test
	public void testCreateIntInt() {
		final ColorARGBDataFactory colorARGBDataFactory = new ColorARGBDataFactory();
		
		final Data data = colorARGBDataFactory.create(1024, 768);
		
		assertTrue(data instanceof ColorARGBData);
		
		assertEquals(1024, data.getResolutionX());
		assertEquals( 768, data.getResolutionY());
		
		assertThrows(IllegalArgumentException.class, () -> colorARGBDataFactory.create(1, 0));
		assertThrows(IllegalArgumentException.class, () -> colorARGBDataFactory.create(0, 1));
		assertThrows(IllegalArgumentException.class, () -> colorARGBDataFactory.create(Integer.MAX_VALUE, Integer.MAX_VALUE));
	}
	
	@Test
	public void testCreateIntIntColor4D() {
		final ColorARGBDataFactory colorARGBDataFactory = new ColorARGBDataFactory();
		
		final Data data = colorARGBDataFactory.create(1024, 768, Color4D.WHITE);
		
		assertTrue(data instanceof ColorARGBData);
		
		assertEquals(1024, data.getResolutionX());
		assertEquals( 768, data.getResolutionY());
		
		assertThrows(IllegalArgumentException.class, () -> colorARGBDataFactory.create(1, 0, Color4D.WHITE));
		assertThrows(IllegalArgumentException.class, () -> colorARGBDataFactory.create(0, 1, Color4D.WHITE));
		assertThrows(IllegalArgumentException.class, () -> colorARGBDataFactory.create(Integer.MAX_VALUE, Integer.MAX_VALUE, Color4D.WHITE));
		
		assertThrows(NullPointerException.class, () -> colorARGBDataFactory.create(1, 1, (Color4D)(null)));
	}
	
	@Test
	public void testCreateIntIntColor4F() {
		final ColorARGBDataFactory colorARGBDataFactory = new ColorARGBDataFactory();
		
		final Data data = colorARGBDataFactory.create(1024, 768, Color4F.WHITE);
		
		assertTrue(data instanceof ColorARGBData);
		
		assertEquals(1024, data.getResolutionX());
		assertEquals( 768, data.getResolutionY());
		
		assertThrows(IllegalArgumentException.class, () -> colorARGBDataFactory.create(1, 0, Color4F.WHITE));
		assertThrows(IllegalArgumentException.class, () -> colorARGBDataFactory.create(0, 1, Color4F.WHITE));
		assertThrows(IllegalArgumentException.class, () -> colorARGBDataFactory.create(Integer.MAX_VALUE, Integer.MAX_VALUE, Color4F.WHITE));
		
		assertThrows(NullPointerException.class, () -> colorARGBDataFactory.create(1, 1, (Color4F)(null)));
	}
	
	@Test
	public void testCreateString() {
		final ColorARGBDataFactory colorARGBDataFactory = new ColorARGBDataFactory();
		
		final Data dataA = colorARGBDataFactory.create(1, 1);
		
		final File directory = new File(String.format("./generated/%s", Long.toString(System.currentTimeMillis())));
		
		final File file = new File(directory, "ColorARGBData.png");
		
		dataA.save(file, "png");
		
		final Data dataB = colorARGBDataFactory.create(file.getAbsolutePath());
		
		assertTrue(dataB instanceof ColorARGBData);
		
		assertEquals(1, dataB.getResolution());
		assertEquals(1, dataB.getResolutionX());
		assertEquals(1, dataB.getResolutionY());
		
		assertThrows(NullPointerException.class, () -> colorARGBDataFactory.create((String)(null)));
		
		assertThrows(UncheckedIOException.class, () -> colorARGBDataFactory.create(file.getAbsolutePath() + ".jpg"));
		
		file.delete();
		
		directory.delete();
	}
	
	@Test
	public void testCreateURL() {
		try {
			final ColorARGBDataFactory colorARGBDataFactory = new ColorARGBDataFactory();
			
			final Data dataA = colorARGBDataFactory.create(1, 1);
			
			final File directory = new File(String.format("./generated/%s", Long.toString(System.currentTimeMillis())));
			
			final File file = new File(directory, "ColorARGBData.png");
			
			final URL uRL = file.toURI().toURL();
			
			dataA.save(file, "png");
			
			final Data dataB = colorARGBDataFactory.create(uRL);
			
			assertTrue(dataB instanceof ColorARGBData);
			
			assertEquals(1, dataB.getResolution());
			assertEquals(1, dataB.getResolutionX());
			assertEquals(1, dataB.getResolutionY());
			
			assertThrows(NullPointerException.class, () -> colorARGBDataFactory.create((URL)(null)));
			
			assertThrows(UncheckedIOException.class, () -> colorARGBDataFactory.create(new File(directory, "ColorARGBData.jpg").toURI().toURL()));
			
			file.delete();
			
			directory.delete();
		} catch(final MalformedURLException e) {
			
		}
	}
	
	@Test
	public void testForColorARGB() {
		final DataFactory dataFactory = DataFactory.forColorARGB();
		
		assertTrue(dataFactory instanceof ColorARGBDataFactory);
	}
}