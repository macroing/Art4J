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
package org.macroing.img4j.image;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.macroing.img4j.color.Color3D;
import org.macroing.img4j.color.Color3F;
import org.macroing.img4j.color.Color4D;
import org.macroing.img4j.color.Color4F;
import org.macroing.img4j.color.Color4I;
import org.macroing.img4j.data.Data;
import org.macroing.img4j.data.DataFactory;
import org.macroing.img4j.geometry.Point2I;
import org.macroing.img4j.geometry.shape.Rectangle2I;

@SuppressWarnings("static-method")
public final class ImageUnitTests {
	public ImageUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testCache() {
		final Image image = new Image();
		
		assertEquals(0, image.cache());
	}
	
	@Test
	public void testCopy() {
		final Image a = new Image();
		final Image b = a.copy();
		
		assertEquals(a, b);
	}
	
	@Test
	public void testConstructor() {
		final Image image = new Image();
		
		assertEquals(1024, image.getResolutionX());
		assertEquals( 768, image.getResolutionY());
	}
	
	@Test
	public void testConstructorBufferedImage() {
		final BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		
		final Image image = new Image(bufferedImage);
		
		assertEquals(1, image.getResolutionX());
		assertEquals(1, image.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, image.getColorARGB(0));
		
		assertThrows(NullPointerException.class, () -> new Image((BufferedImage)(null)));
	}
	
	@Test
	public void testConstructorBufferedImageDataFactory() {
		final BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		
		final Image image = new Image(bufferedImage, DataFactory.forColorARGB());
		
		assertEquals(1, image.getResolutionX());
		assertEquals(1, image.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, image.getColorARGB(0));
		
		assertThrows(NullPointerException.class, () -> new Image(bufferedImage, null));
		assertThrows(NullPointerException.class, () -> new Image((BufferedImage)(null), DataFactory.forColorARGB()));
	}
	
	@Test
	public void testConstructorData() {
		final DataFactory dataFactory = DataFactory.forColorARGB();
		
		final Data data = dataFactory.create();
		
		final Image a = new Image(data);
		final Image b = new Image(data);
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> new Image((Data)(null)));
	}
	
	@Test
	public void testConstructorFile() {
		final DataFactory dataFactory = DataFactory.forColorARGB();
		
		final Data data = dataFactory.create(1, 1);
		
		final File directory = new File(String.format("./generated/%s", Long.toString(System.currentTimeMillis())));
		
		final File file = new File(directory, "Image.png");
		
		data.save(file, "png");
		
		final Image image = new Image(file);
		
		assertEquals(1, image.getResolution());
		assertEquals(1, image.getResolutionX());
		assertEquals(1, image.getResolutionY());
		
		assertThrows(NullPointerException.class, () -> new Image((File)(null)));
		
		assertThrows(UncheckedIOException.class, () -> new Image(new File(directory, "Image.jpg")));
		
		file.delete();
		
		directory.delete();
	}
	
	@Test
	public void testConstructorFileDataFactory() {
		final DataFactory dataFactory = DataFactory.forColorARGB();
		
		final Data data = dataFactory.create(1, 1);
		
		final File directory = new File(String.format("./generated/%s", Long.toString(System.currentTimeMillis())));
		
		final File file = new File(directory, "Image.png");
		
		data.save(file, "png");
		
		final Image image = new Image(file, dataFactory);
		
		assertEquals(1, image.getResolution());
		assertEquals(1, image.getResolutionX());
		assertEquals(1, image.getResolutionY());
		
		assertThrows(NullPointerException.class, () -> new Image(file, null));
		assertThrows(NullPointerException.class, () -> new Image((File)(null), dataFactory));
		
		assertThrows(UncheckedIOException.class, () -> new Image(new File(directory, "Image.jpg"), dataFactory));
		
		file.delete();
		
		directory.delete();
	}
	
	@Test
	public void testConstructorImage() {
		final Image a = new Image();
		final Image b = new Image(a);
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> new Image((Image)(null)));
	}
	
	@Test
	public void testConstructorIntInt() {
		final Image image = new Image(1, 1);
		
		assertEquals(1, image.getResolutionX());
		assertEquals(1, image.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(0));
		
		assertThrows(IllegalArgumentException.class, () -> new Image(1, 0));
		assertThrows(IllegalArgumentException.class, () -> new Image(0, 1));
		assertThrows(IllegalArgumentException.class, () -> new Image(2, Integer.MAX_VALUE));
	}
	
	@Test
	public void testConstructorIntIntColor4D() {
		final Image image = new Image(1, 1, Color4D.WHITE);
		
		assertEquals(1, image.getResolutionX());
		assertEquals(1, image.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(0));
		
		assertThrows(IllegalArgumentException.class, () -> new Image(1, 0, Color4D.WHITE));
		assertThrows(IllegalArgumentException.class, () -> new Image(0, 1, Color4D.WHITE));
		assertThrows(IllegalArgumentException.class, () -> new Image(2, Integer.MAX_VALUE, Color4D.WHITE));
		
		assertThrows(NullPointerException.class, () -> new Image(1, 1, (Color4D)(null)));
	}
	
	@Test
	public void testConstructorIntIntColor4DDataFactory() {
		final Image image = new Image(1, 1, Color4D.WHITE, DataFactory.forColorARGB());
		
		assertEquals(1, image.getResolutionX());
		assertEquals(1, image.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(0));
		
		assertThrows(IllegalArgumentException.class, () -> new Image(1, 0, Color4D.WHITE, DataFactory.forColorARGB()));
		assertThrows(IllegalArgumentException.class, () -> new Image(0, 1, Color4D.WHITE, DataFactory.forColorARGB()));
		assertThrows(IllegalArgumentException.class, () -> new Image(2, Integer.MAX_VALUE, Color4D.WHITE, DataFactory.forColorARGB()));
		
		assertThrows(NullPointerException.class, () -> new Image(1, 1, Color4D.WHITE, null));
		assertThrows(NullPointerException.class, () -> new Image(1, 1, (Color4D)(null), DataFactory.forColorARGB()));
	}
	
	@Test
	public void testConstructorIntIntColor4F() {
		final Image image = new Image(1, 1, Color4F.WHITE);
		
		assertEquals(1, image.getResolutionX());
		assertEquals(1, image.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(0));
		
		assertThrows(IllegalArgumentException.class, () -> new Image(1, 0, Color4F.WHITE));
		assertThrows(IllegalArgumentException.class, () -> new Image(0, 1, Color4F.WHITE));
		assertThrows(IllegalArgumentException.class, () -> new Image(2, Integer.MAX_VALUE, Color4F.WHITE));
		
		assertThrows(NullPointerException.class, () -> new Image(1, 1, (Color4F)(null)));
	}
	
	@Test
	public void testConstructorIntIntColor4FDataFactory() {
		final Image image = new Image(1, 1, Color4F.WHITE, DataFactory.forColorARGB());
		
		assertEquals(1, image.getResolutionX());
		assertEquals(1, image.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(0));
		
		assertThrows(IllegalArgumentException.class, () -> new Image(1, 0, Color4F.WHITE, DataFactory.forColorARGB()));
		assertThrows(IllegalArgumentException.class, () -> new Image(0, 1, Color4F.WHITE, DataFactory.forColorARGB()));
		assertThrows(IllegalArgumentException.class, () -> new Image(2, Integer.MAX_VALUE, Color4F.WHITE, DataFactory.forColorARGB()));
		
		assertThrows(NullPointerException.class, () -> new Image(1, 1, Color4F.WHITE, null));
		assertThrows(NullPointerException.class, () -> new Image(1, 1, (Color4F)(null), DataFactory.forColorARGB()));
	}
	
	@Test
	public void testConstructorString() {
		final DataFactory dataFactory = DataFactory.forColorARGB();
		
		final Data data = dataFactory.create(1, 1);
		
		final File directory = new File(String.format("./generated/%s", Long.toString(System.currentTimeMillis())));
		
		final File file = new File(directory, "Image.png");
		
		data.save(file, "png");
		
		final Image image = new Image(file.getAbsolutePath());
		
		assertEquals(1, image.getResolution());
		assertEquals(1, image.getResolutionX());
		assertEquals(1, image.getResolutionY());
		
		assertThrows(NullPointerException.class, () -> new Image((String)(null)));
		
		assertThrows(UncheckedIOException.class, () -> new Image(file.getAbsolutePath() + ".jpg"));
		
		file.delete();
		
		directory.delete();
	}
	
	@Test
	public void testConstructorStringDataFactory() {
		final DataFactory dataFactory = DataFactory.forColorARGB();
		
		final Data data = dataFactory.create(1, 1);
		
		final File directory = new File(String.format("./generated/%s", Long.toString(System.currentTimeMillis())));
		
		final File file = new File(directory, "Image.png");
		
		data.save(file, "png");
		
		final Image image = new Image(file.getAbsolutePath(), dataFactory);
		
		assertEquals(1, image.getResolution());
		assertEquals(1, image.getResolutionX());
		assertEquals(1, image.getResolutionY());
		
		assertThrows(NullPointerException.class, () -> new Image(file.getAbsolutePath(), null));
		assertThrows(NullPointerException.class, () -> new Image((String)(null), dataFactory));
		
		assertThrows(UncheckedIOException.class, () -> new Image(file.getAbsolutePath() + ".jpg", dataFactory));
		
		file.delete();
		
		directory.delete();
	}
	
	@Test
	public void testConstructorURL() {
		try {
			final DataFactory dataFactory = DataFactory.forColorARGB();
			
			final Data data = dataFactory.create(1, 1);
			
			final File directory = new File(String.format("./generated/%s", Long.toString(System.currentTimeMillis())));
			
			final File file = new File(directory, "Image.png");
			
			final URL uRL = file.toURI().toURL();
			
			data.save(file, "png");
			
			final Image image = new Image(uRL);
			
			assertEquals(1, image.getResolution());
			assertEquals(1, image.getResolutionX());
			assertEquals(1, image.getResolutionY());
			
			assertThrows(NullPointerException.class, () -> new Image((URL)(null)));
			
			assertThrows(UncheckedIOException.class, () -> new Image(new File(directory, "Image.jpg").toURI().toURL()));
			
			file.delete();
			
			directory.delete();
		} catch(final MalformedURLException e) {
			
		}
	}
	
	@Test
	public void testConstructorURLDataFactory() {
		try {
			final DataFactory dataFactory = DataFactory.forColorARGB();
			
			final Data data = dataFactory.create(1, 1);
			
			final File directory = new File(String.format("./generated/%s", Long.toString(System.currentTimeMillis())));
			
			final File file = new File(directory, "Image.png");
			
			final URL uRL = file.toURI().toURL();
			
			data.save(file, "png");
			
			final Image image = new Image(uRL, dataFactory);
			
			assertEquals(1, image.getResolution());
			assertEquals(1, image.getResolutionX());
			assertEquals(1, image.getResolutionY());
			
			assertThrows(NullPointerException.class, () -> new Image(uRL, null));
			assertThrows(NullPointerException.class, () -> new Image((URL)(null), dataFactory));
			
			assertThrows(UncheckedIOException.class, () -> new Image(new File(directory, "Image.jpg").toURI().toURL(), dataFactory));
			
			file.delete();
			
			directory.delete();
		} catch(final MalformedURLException e) {
			
		}
	}
	
	@Test
	public void testEquals() {
		final Image a = new Image(1, 1);
		final Image b = new Image(1, 1);
		final Image c = new Image(2, 2);
		final Image d = null;
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(a, d);
		assertNotEquals(d, a);
	}
	
	@Test
	public void testGetBounds() {
		final Image a = new Image(1, 1);
		final Image b = new Image(9, 9);
		
		assertEquals(new Rectangle2I(new Point2I(0, 0), new Point2I(0, 0), new Point2I(0, 0), new Point2I(0, 0)), a.getBounds());
		assertEquals(new Rectangle2I(new Point2I(0, 0), new Point2I(8, 0), new Point2I(8, 8), new Point2I(0, 8)), b.getBounds());
	}
	
	@Test
	public void testGetColor3DDoubleDouble() {
		final
		Image image = new Image(1, 1);
		image.setColor3D(Color3D.RED, 0);
		
		assertEquals(Color3D.RED, image.getColor3D(0.0D, 0.0D));
	}
	
	@Test
	public void testGetColor3DInt() {
		final
		Image image = new Image(1, 1);
		image.setColor3D(Color3D.RED, 0);
		
		assertEquals(Color3D.RED, image.getColor3D(0));
	}
	
	@Test
	public void testGetColor3DIntInt() {
		final
		Image image = new Image(1, 1);
		image.setColor3D(Color3D.RED, 0);
		
		assertEquals(Color3D.RED, image.getColor3D(0, 0));
	}
	
	@Test
	public void testGetColor3DPoint2I() {
		final
		Image image = new Image(1, 1);
		image.setColor3D(Color3D.RED, 0);
		
		assertEquals(Color3D.RED, image.getColor3D(new Point2I(0, 0)));
		
		assertThrows(NullPointerException.class, () -> image.getColor3D(null));
	}
	
	@Test
	public void testGetColor3FFloatFloat() {
		final
		Image image = new Image(1, 1);
		image.setColor3F(Color3F.RED, 0);
		
		assertEquals(Color3F.RED, image.getColor3F(0.0F, 0.0F));
	}
	
	@Test
	public void testGetColor3FInt() {
		final
		Image image = new Image(1, 1);
		image.setColor3F(Color3F.RED, 0);
		
		assertEquals(Color3F.RED, image.getColor3F(0));
	}
	
	@Test
	public void testGetColor3FIntInt() {
		final
		Image image = new Image(1, 1);
		image.setColor3F(Color3F.RED, 0);
		
		assertEquals(Color3F.RED, image.getColor3F(0, 0));
	}
	
	@Test
	public void testGetColor3FPoint2I() {
		final
		Image image = new Image(1, 1);
		image.setColor3F(Color3F.RED, 0);
		
		assertEquals(Color3F.RED, image.getColor3F(new Point2I(0, 0)));
		
		assertThrows(NullPointerException.class, () -> image.getColor3F(null));
	}
	
	@Test
	public void testGetColor4DDoubleDouble() {
		final
		Image image = new Image(1, 1);
		image.setColor4D(Color4D.RED, 0);
		
		assertEquals(Color4D.RED, image.getColor4D(0.0D, 0.0D));
	}
	
	@Test
	public void testGetColor4DInt() {
		final
		Image image = new Image(1, 1);
		image.setColor4D(Color4D.RED, 0);
		
		assertEquals(Color4D.RED, image.getColor4D(0));
	}
	
	@Test
	public void testGetColor4DIntInt() {
		final
		Image image = new Image(1, 1);
		image.setColor4D(Color4D.RED, 0);
		
		assertEquals(Color4D.RED, image.getColor4D(0, 0));
	}
	
	@Test
	public void testGetColor4DPoint2I() {
		final
		Image image = new Image(1, 1);
		image.setColor4D(Color4D.RED, 0);
		
		assertEquals(Color4D.RED, image.getColor4D(new Point2I(0, 0)));
		
		assertThrows(NullPointerException.class, () -> image.getColor4D(null));
	}
	
	@Test
	public void testGetColor4FFloatFloat() {
		final
		Image image = new Image(1, 1);
		image.setColor4F(Color4F.RED, 0);
		
		assertEquals(Color4F.RED, image.getColor4F(0.0F, 0.0F));
	}
	
	@Test
	public void testGetColor4FInt() {
		final
		Image image = new Image(1, 1);
		image.setColor4F(Color4F.RED, 0);
		
		assertEquals(Color4F.RED, image.getColor4F(0));
	}
	
	@Test
	public void testGetColor4FIntInt() {
		final
		Image image = new Image(1, 1);
		image.setColor4F(Color4F.RED, 0);
		
		assertEquals(Color4F.RED, image.getColor4F(0, 0));
	}
	
	@Test
	public void testGetColor4FPoint2I() {
		final
		Image image = new Image(1, 1);
		image.setColor4F(Color4F.RED, 0);
		
		assertEquals(Color4F.RED, image.getColor4F(new Point2I(0, 0)));
		
		assertThrows(NullPointerException.class, () -> image.getColor4F(null));
	}
	
	@Test
	public void testGetColorARGBDoubleDouble() {
		final
		Image image = new Image(1, 1);
		image.setColorARGB(Color4I.RED_A_R_G_B, 0);
		
		assertEquals(Color4I.RED_A_R_G_B, image.getColorARGB(0.0D, 0.0D));
	}
	
	@Test
	public void testGetColorARGBFloatFloat() {
		final
		Image image = new Image(1, 1);
		image.setColorARGB(Color4I.RED_A_R_G_B, 0);
		
		assertEquals(Color4I.RED_A_R_G_B, image.getColorARGB(0.0F, 0.0F));
	}
	
	@Test
	public void testGetColorARGBInt() {
		final
		Image image = new Image(1, 1);
		image.setColorARGB(Color4I.RED_A_R_G_B, 0);
		
		assertEquals(Color4I.RED_A_R_G_B, image.getColorARGB(0));
	}
	
	@Test
	public void testGetColorARGBIntInt() {
		final
		Image image = new Image(1, 1);
		image.setColorARGB(Color4I.RED_A_R_G_B, 0);
		
		assertEquals(Color4I.RED_A_R_G_B, image.getColorARGB(0, 0));
	}
	
	@Test
	public void testGetColorARGBPoint2I() {
		final
		Image image = new Image(1, 1);
		image.setColorARGB(Color4I.RED_A_R_G_B, 0);
		
		assertEquals(Color4I.RED_A_R_G_B, image.getColorARGB(new Point2I(0, 0)));
		
		assertThrows(NullPointerException.class, () -> image.getColorARGB(null));
	}
	
	@Test
	public void testGetResolution() {
		final Image image = new Image(2, 3);
		
		assertEquals(6, image.getResolution());
	}
	
	@Test
	public void testGetResolutionX() {
		final Image image = new Image(2, 3);
		
		assertEquals(2, image.getResolutionX());
	}
	
	@Test
	public void testGetResolutionY() {
		final Image image = new Image(2, 3);
		
		assertEquals(3, image.getResolutionY());
	}
	
	@Test
	public void testHashCode() {
		final Image a = new Image();
		final Image b = new Image();
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIsChangeHistoryEnabled() {
		final Image image = new Image(1, 1);
		
		assertFalse(image.isChangeHistoryEnabled());
	}
	
	@Test
	public void testRedo() {
		final Image image = new Image(1, 1);
		
		assertFalse(image.redo());
	}
	
	@Test
	public void testRotateDouble() {
		final
		Image image = new Image(5, 5);
		image.rotate(45.0D);
		
		assertEquals(7, image.getResolutionX());
		assertEquals(7, image.getResolutionY());
	}
	
	@Test
	public void testRotateDoubleBoolean() {
		final
		Image image = new Image(5, 5);
		image.rotate(45.0D, false);
		
		assertEquals(7, image.getResolutionX());
		assertEquals(7, image.getResolutionY());
	}
	
	@Test
	public void testRotateFloat() {
		final
		Image image = new Image(5, 5);
		image.rotate(45.0F);
		
		assertEquals(7, image.getResolutionX());
		assertEquals(7, image.getResolutionY());
	}
	
	@Test
	public void testRotateFloatBoolean() {
		final
		Image image = new Image(5, 5);
		image.rotate(45.0F, false);
		
		assertEquals(7, image.getResolutionX());
		assertEquals(7, image.getResolutionY());
	}
	
	@Test
	public void testSaveFile() {
		final Image image = new Image(1, 1);
		
		final File directory = new File(String.format("./generated/%s", Long.toString(System.currentTimeMillis())));
		
		final File fileA = new File(directory, "Image.png");
		
		final File fileB = new File("");
		
		assertTrue(image.save(fileA));
		
		assertFalse(image.save(fileB));
		
		assertThrows(NullPointerException.class, () -> image.save((File)(null)));
		
		fileA.delete();
		
		directory.delete();
	}
	
	@Test
	public void testSaveFileString() {
		final Image image = new Image(1, 1);
		
		final File directory = new File(String.format("./generated/%s", Long.toString(System.currentTimeMillis())));
		
		final File fileA = new File(directory, "Image.jpg");
		final File fileB = new File(directory, "Image.png");
		
		final File fileC = new File("Image.txt");
		final File fileD = new File("");
		
		assertTrue(image.save(fileA, "jpg"));
		assertTrue(image.save(fileB, "png"));
		
		assertFalse(image.save(fileC, "txt"));
		assertFalse(image.save(fileD, "png"));
		
		assertThrows(NullPointerException.class, () -> image.save(new File("./generated/Image.png"), null));
		assertThrows(NullPointerException.class, () -> image.save((File)(null), "png"));
		
		fileA.delete();
		fileB.delete();
		
		directory.delete();
	}
	
	@Test
	public void testSaveString() {
		final Image image = new Image(1, 1);
		
		final File directory = new File(String.format("./generated/%s", Long.toString(System.currentTimeMillis())));
		
		final File fileA = new File(directory, "Image.png");
		
		final File fileB = new File("");
		
		assertTrue(image.save(fileA.getAbsolutePath()));
		
		assertFalse(image.save(fileB.getAbsolutePath()));
		
		assertThrows(NullPointerException.class, () -> image.save((String)(null)));
		
		fileA.delete();
		
		directory.delete();
	}
	
	@Test
	public void testSaveStringString() {
		final Image image = new Image(1, 1);
		
		final File directory = new File(String.format("./generated/%s", Long.toString(System.currentTimeMillis())));
		
		final File fileA = new File(directory, "Image.jpg");
		final File fileB = new File(directory, "Image.png");
		
		final File fileC = new File("Image.txt");
		final File fileD = new File("");
		
		assertTrue(image.save(fileA.getAbsolutePath(), "jpg"));
		assertTrue(image.save(fileB.getAbsolutePath(), "png"));
		
		assertFalse(image.save(fileC.getAbsolutePath(), "txt"));
		assertFalse(image.save(fileD.getAbsolutePath(), "png"));
		
		assertThrows(NullPointerException.class, () -> image.save(new File("./generated/Image.png").getAbsolutePath(), null));
		assertThrows(NullPointerException.class, () -> image.save((String)(null), "png"));
		
		fileA.delete();
		fileB.delete();
		
		directory.delete();
	}
	
	@Test
	public void testScaleDoubleDouble() {
		final
		Image image = new Image(1, 1);
		image.scale(2.0D, 2.0D);
		
		assertEquals(2, image.getResolutionX());
		assertEquals(2, image.getResolutionY());
	}
	
	@Test
	public void testScaleFloatFloat() {
		final
		Image image = new Image(1, 1);
		image.scale(2.0F, 2.0F);
		
		assertEquals(2, image.getResolutionX());
		assertEquals(2, image.getResolutionY());
	}
	
	@Test
	public void testScaleIntInt() {
		final
		Image image = new Image(1, 1);
		image.scale(2, 2);
		
		assertEquals(2, image.getResolutionX());
		assertEquals(2, image.getResolutionY());
	}
	
	@Test
	public void testSetChangeHistoryEnabled() {
		final Image image = new Image();
		
		assertFalse(image.setChangeHistoryEnabled(false));
		
		assertTrue(image.setChangeHistoryEnabled(true));
		
		assertFalse(image.setChangeHistoryEnabled(true));
		
		assertTrue(image.setChangeHistoryEnabled(false));
		
		assertFalse(image.setChangeHistoryEnabled(false));
	}
	
	@Test
	public void testSetColor3DColor3DInt() {
		final
		Image image = new Image(1, 1);
		image.setColor3D(Color3D.RED, 0);
		
		assertEquals(Color3D.RED, image.getColor3D(0));
		
		assertThrows(NullPointerException.class, () -> image.setColor3D(null, 0));
	}
	
	@Test
	public void testSetColor3DColor3DIntInt() {
		final
		Image image = new Image(1, 1);
		image.setColor3D(Color3D.RED, 0, 0);
		
		assertEquals(Color3D.RED, image.getColor3D(0, 0));
		
		assertThrows(NullPointerException.class, () -> image.setColor3D(null, 0, 0));
	}
	
	@Test
	public void testSetColor3DColor3DPoint2I() {
		final
		Image image = new Image(1, 1);
		image.setColor3D(Color3D.RED, new Point2I(0, 0));
		
		assertEquals(Color3D.RED, image.getColor3D(new Point2I(0, 0)));
		
		assertThrows(NullPointerException.class, () -> image.setColor3D(Color3D.RED, null));
		assertThrows(NullPointerException.class, () -> image.setColor3D(null, new Point2I(0, 0)));
	}
	
	@Test
	public void testSetColor3FColor3FInt() {
		final
		Image image = new Image(1, 1);
		image.setColor3F(Color3F.RED, 0);
		
		assertEquals(Color3F.RED, image.getColor3F(0));
		
		assertThrows(NullPointerException.class, () -> image.setColor3F(null, 0));
	}
	
	@Test
	public void testSetColor3FColor3FIntInt() {
		final
		Image image = new Image(1, 1);
		image.setColor3F(Color3F.RED, 0, 0);
		
		assertEquals(Color3F.RED, image.getColor3F(0, 0));
		
		assertThrows(NullPointerException.class, () -> image.setColor3F(null, 0, 0));
	}
	
	@Test
	public void testSetColor3FColor3FPoint2I() {
		final
		Image image = new Image(1, 1);
		image.setColor3F(Color3F.RED, new Point2I(0, 0));
		
		assertEquals(Color3F.RED, image.getColor3F(new Point2I(0, 0)));
		
		assertThrows(NullPointerException.class, () -> image.setColor3F(Color3F.RED, null));
		assertThrows(NullPointerException.class, () -> image.setColor3F(null, new Point2I(0, 0)));
	}
	
	@Test
	public void testSetColor4DColor4DInt() {
		final
		Image image = new Image(1, 1);
		image.setColor4D(Color4D.RED, 0);
		
		assertEquals(Color4D.RED, image.getColor4D(0));
		
		assertThrows(NullPointerException.class, () -> image.setColor4D(null, 0));
	}
	
	@Test
	public void testSetColor4DColor4DIntInt() {
		final
		Image image = new Image(1, 1);
		image.setColor4D(Color4D.RED, 0, 0);
		
		assertEquals(Color4D.RED, image.getColor4D(0, 0));
		
		assertThrows(NullPointerException.class, () -> image.setColor4D(null, 0, 0));
	}
	
	@Test
	public void testSetColor4DColor4DPoint2I() {
		final
		Image image = new Image(1, 1);
		image.setColor4D(Color4D.RED, new Point2I(0, 0));
		
		assertEquals(Color4D.RED, image.getColor4D(new Point2I(0, 0)));
		
		assertThrows(NullPointerException.class, () -> image.setColor4D(Color4D.RED, null));
		assertThrows(NullPointerException.class, () -> image.setColor4D(null, new Point2I(0, 0)));
	}
	
	@Test
	public void testSetColor4FColor4FInt() {
		final
		Image image = new Image(1, 1);
		image.setColor4F(Color4F.RED, 0);
		
		assertEquals(Color4F.RED, image.getColor4F(0));
		
		assertThrows(NullPointerException.class, () -> image.setColor4F(null, 0));
	}
	
	@Test
	public void testSetColor4FColor4FIntInt() {
		final
		Image image = new Image(1, 1);
		image.setColor4F(Color4F.RED, 0, 0);
		
		assertEquals(Color4F.RED, image.getColor4F(0, 0));
		
		assertThrows(NullPointerException.class, () -> image.setColor4F(null, 0, 0));
	}
	
	@Test
	public void testSetColor4FColor4FPoint2I() {
		final
		Image image = new Image(1, 1);
		image.setColor4F(Color4F.RED, new Point2I(0, 0));
		
		assertEquals(Color4F.RED, image.getColor4F(new Point2I(0, 0)));
		
		assertThrows(NullPointerException.class, () -> image.setColor4F(Color4F.RED, null));
		assertThrows(NullPointerException.class, () -> image.setColor4F(null, new Point2I(0, 0)));
	}
	
	@Test
	public void testSetColorARGBIntInt() {
		final
		Image image = new Image(1, 1);
		image.setColorARGB(Color4I.RED_A_R_G_B, 0);
		
		assertEquals(Color4I.RED_A_R_G_B, image.getColorARGB(0));
	}
	
	@Test
	public void testSetColorARGBIntIntInt() {
		final
		Image image = new Image(1, 1);
		image.setColorARGB(Color4I.RED_A_R_G_B, 0, 0);
		
		assertEquals(Color4I.RED_A_R_G_B, image.getColorARGB(0, 0));
	}
	
	@Test
	public void testSetColorARGBIntPoint2I() {
		final
		Image image = new Image(1, 1);
		image.setColorARGB(Color4I.RED_A_R_G_B, new Point2I(0, 0));
		
		assertEquals(Color4I.RED_A_R_G_B, image.getColorARGB(new Point2I(0, 0)));
		
		assertThrows(NullPointerException.class, () -> image.setColorARGB(Color4I.RED_A_R_G_B, null));
	}
	
	@Test
	public void testSetResolution() {
		final
		Image image = new Image(1, 1);
		image.setResolution(2, 2);
		
		assertEquals(2, image.getResolutionX());
		assertEquals(2, image.getResolutionY());
	}
	
	@Test
	public void testSetResolutionX() {
		final
		Image image = new Image(1, 1);
		image.setResolutionX(2);
		
		assertEquals(2, image.getResolutionX());
		assertEquals(1, image.getResolutionY());
	}
	
	@Test
	public void testSetResolutionY() {
		final
		Image image = new Image(1, 1);
		image.setResolutionY(2);
		
		assertEquals(1, image.getResolutionX());
		assertEquals(2, image.getResolutionY());
	}
	
	@Test
	public void testToBufferedImage() {
		final Image image = new Image();
		
		final BufferedImage bufferedImage = image.toBufferedImage();
		
		assertEquals(image.getResolutionX(), bufferedImage.getWidth());
		assertEquals(image.getResolutionY(), bufferedImage.getHeight());
		
		assertEquals(BufferedImage.TYPE_INT_ARGB, bufferedImage.getType());
	}
	
	@Test
	public void testToBufferedImageBoolean() {
		final Image image = new Image();
		
		final BufferedImage bufferedImage = image.toBufferedImage(true);
		
		assertEquals(image.getResolutionX(), bufferedImage.getWidth());
		assertEquals(image.getResolutionY(), bufferedImage.getHeight());
		
		assertEquals(BufferedImage.TYPE_INT_RGB, bufferedImage.getType());
	}
	
	@Test
	public void testToString() {
		final Image image = new Image(1, 1);
		
		assertEquals("new Image(1, 1)", image.toString());
	}
	
	@Test
	public void testUndo() {
		final Image image = new Image(1, 1);
		
		assertFalse(image.undo());
	}
}