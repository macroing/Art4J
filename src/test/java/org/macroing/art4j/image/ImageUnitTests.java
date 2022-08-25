/**
 * Copyright 2022 J&#246;rgen Lundgren
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
package org.macroing.art4j.image;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.macroing.art4j.color.ArrayComponentOrder;
import org.macroing.art4j.color.Color3D;
import org.macroing.art4j.color.Color3F;
import org.macroing.art4j.color.Color3I;
import org.macroing.art4j.color.Color4D;
import org.macroing.art4j.color.Color4F;
import org.macroing.art4j.color.Color4I;
import org.macroing.art4j.data.Data;
import org.macroing.art4j.data.DataFactory;
import org.macroing.art4j.geometry.Point2I;
import org.macroing.art4j.geometry.shape.Rectangle2I;
import org.macroing.art4j.kernel.ConvolutionKernelND;
import org.macroing.art4j.kernel.ConvolutionKernelNF;
import org.macroing.art4j.pixel.Color4DPixelOperator;
import org.macroing.art4j.pixel.Color4FPixelOperator;
import org.macroing.art4j.pixel.PackedIntARGBPixelOperator;
import org.macroing.art4j.pixel.PixelTransformer;

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
		
		final Image image = new Image(bufferedImage, DataFactory.forPackedIntARGB());
		
		assertEquals(1, image.getResolutionX());
		assertEquals(1, image.getResolutionY());
		
		assertEquals(Color4I.TRANSPARENT_A_R_G_B, image.getColorARGB(0));
		
		assertThrows(NullPointerException.class, () -> new Image(bufferedImage, null));
		assertThrows(NullPointerException.class, () -> new Image((BufferedImage)(null), DataFactory.forPackedIntARGB()));
	}
	
	@Test
	public void testConstructorData() {
		final DataFactory dataFactory = DataFactory.forPackedIntARGB();
		
		final Data data = dataFactory.create();
		
		final Image a = new Image(data);
		final Image b = new Image(data);
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> new Image((Data)(null)));
	}
	
	@Test
	public void testConstructorDataBoolean() {
		final DataFactory dataFactory = DataFactory.forPackedIntARGB();
		
		final Data data = dataFactory.create();
		
		final Image a = new Image(data, true);
		final Image b = new Image(data, true);
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> new Image((Data)(null), false));
	}
	
	@Test
	public void testConstructorFile() {
		final DataFactory dataFactory = DataFactory.forPackedIntARGB();
		
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
		final DataFactory dataFactory = DataFactory.forPackedIntARGB();
		
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
		final Image image = new Image(1, 1, Color4D.WHITE, DataFactory.forPackedIntARGB());
		
		assertEquals(1, image.getResolutionX());
		assertEquals(1, image.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(0));
		
		assertThrows(IllegalArgumentException.class, () -> new Image(1, 0, Color4D.WHITE, DataFactory.forPackedIntARGB()));
		assertThrows(IllegalArgumentException.class, () -> new Image(0, 1, Color4D.WHITE, DataFactory.forPackedIntARGB()));
		assertThrows(IllegalArgumentException.class, () -> new Image(2, Integer.MAX_VALUE, Color4D.WHITE, DataFactory.forPackedIntARGB()));
		
		assertThrows(NullPointerException.class, () -> new Image(1, 1, Color4D.WHITE, null));
		assertThrows(NullPointerException.class, () -> new Image(1, 1, (Color4D)(null), DataFactory.forPackedIntARGB()));
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
		final Image image = new Image(1, 1, Color4F.WHITE, DataFactory.forPackedIntARGB());
		
		assertEquals(1, image.getResolutionX());
		assertEquals(1, image.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(0));
		
		assertThrows(IllegalArgumentException.class, () -> new Image(1, 0, Color4F.WHITE, DataFactory.forPackedIntARGB()));
		assertThrows(IllegalArgumentException.class, () -> new Image(0, 1, Color4F.WHITE, DataFactory.forPackedIntARGB()));
		assertThrows(IllegalArgumentException.class, () -> new Image(2, Integer.MAX_VALUE, Color4F.WHITE, DataFactory.forPackedIntARGB()));
		
		assertThrows(NullPointerException.class, () -> new Image(1, 1, Color4F.WHITE, null));
		assertThrows(NullPointerException.class, () -> new Image(1, 1, (Color4F)(null), DataFactory.forPackedIntARGB()));
	}
	
	@Test
	public void testConstructorIntIntDataFactory() {
		final Image image = new Image(1, 1, DataFactory.forPackedIntARGB());
		
		assertEquals(1, image.getResolutionX());
		assertEquals(1, image.getResolutionY());
		
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(0));
		
		assertThrows(IllegalArgumentException.class, () -> new Image(1, 0, DataFactory.forPackedIntARGB()));
		assertThrows(IllegalArgumentException.class, () -> new Image(0, 1, DataFactory.forPackedIntARGB()));
		assertThrows(IllegalArgumentException.class, () -> new Image(2, Integer.MAX_VALUE, DataFactory.forPackedIntARGB()));
		
		assertThrows(NullPointerException.class, () -> new Image(1, 1, (DataFactory)(null)));
	}
	
	@Test
	public void testConstructorIntIntInt() {
		final Image image = new Image(1, 1, Color4I.RED_A_R_G_B);
		
		assertEquals(1, image.getResolutionX());
		assertEquals(1, image.getResolutionY());
		
		assertEquals(Color4I.RED_A_R_G_B, image.getColorARGB(0));
		
		assertThrows(IllegalArgumentException.class, () -> new Image(1, 0, Color4I.RED_A_R_G_B));
		assertThrows(IllegalArgumentException.class, () -> new Image(0, 1, Color4I.RED_A_R_G_B));
		assertThrows(IllegalArgumentException.class, () -> new Image(2, Integer.MAX_VALUE, Color4I.RED_A_R_G_B));
	}
	
	@Test
	public void testConstructorIntIntIntDataFactory() {
		final Image image = new Image(1, 1, Color4I.RED_A_R_G_B, DataFactory.forPackedIntARGB());
		
		assertEquals(1, image.getResolutionX());
		assertEquals(1, image.getResolutionY());
		
		assertEquals(Color4I.RED_A_R_G_B, image.getColorARGB(0));
		
		assertThrows(IllegalArgumentException.class, () -> new Image(1, 0, Color4I.RED_A_R_G_B, DataFactory.forPackedIntARGB()));
		assertThrows(IllegalArgumentException.class, () -> new Image(0, 1, Color4I.RED_A_R_G_B, DataFactory.forPackedIntARGB()));
		assertThrows(IllegalArgumentException.class, () -> new Image(2, Integer.MAX_VALUE, Color4I.RED_A_R_G_B, DataFactory.forPackedIntARGB()));
		
		assertThrows(NullPointerException.class, () -> new Image(1, 1, Color4I.RED_A_R_G_B, (DataFactory)(null)));
	}
	
	@Test
	public void testConstructorString() {
		final DataFactory dataFactory = DataFactory.forPackedIntARGB();
		
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
		final DataFactory dataFactory = DataFactory.forPackedIntARGB();
		
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
			final DataFactory dataFactory = DataFactory.forPackedIntARGB();
			
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
			final DataFactory dataFactory = DataFactory.forPackedIntARGB();
			
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
	public void testConvolveConvolutionKernelND() {
		final
		Image image = new Image(2, 2, Color4D.GRAY, DataFactory.forColor4D());
		image.convolve(ConvolutionKernelND.GAUSSIAN_BLUR_3);
		
		assertNotEquals(Color4D.GRAY, image.getColor4D(0, 0));
		assertNotEquals(Color4D.GRAY, image.getColor4D(1, 0));
		assertNotEquals(Color4D.GRAY, image.getColor4D(0, 1));
		assertNotEquals(Color4D.GRAY, image.getColor4D(1, 1));
		
		assertThrows(NullPointerException.class, () -> image.convolve((ConvolutionKernelND)(null)));
	}
	
	@Test
	public void testConvolveConvolutionKernelNDColor4DPixelFilter() {
		final
		Image image = new Image(2, 2, Color4D.GRAY, DataFactory.forColor4D());
		image.convolve(ConvolutionKernelND.GAUSSIAN_BLUR_3, (color, x, y) -> y != 0);
		
		assertEquals(Color4D.GRAY, image.getColor4D(0, 0));
		assertEquals(Color4D.GRAY, image.getColor4D(1, 0));
		
		assertNotEquals(Color4D.GRAY, image.getColor4D(0, 1));
		assertNotEquals(Color4D.GRAY, image.getColor4D(1, 1));
		
		image.convolve(ConvolutionKernelND.GAUSSIAN_BLUR_3, (color, x, y) -> true);
		
		assertNotEquals(Color4D.GRAY, image.getColor4D(0, 0));
		assertNotEquals(Color4D.GRAY, image.getColor4D(1, 0));
		assertNotEquals(Color4D.GRAY, image.getColor4D(0, 1));
		assertNotEquals(Color4D.GRAY, image.getColor4D(1, 1));
		
		assertThrows(NullPointerException.class, () -> image.convolve(ConvolutionKernelND.GAUSSIAN_BLUR_3, null));
		assertThrows(NullPointerException.class, () -> image.convolve((ConvolutionKernelND)(null), (color, x, y) -> true));
	}
	
	@Test
	public void testConvolveConvolutionKernelNF() {
		final
		Image image = new Image(2, 2, Color4F.GRAY, DataFactory.forColor4F());
		image.convolve(ConvolutionKernelNF.GAUSSIAN_BLUR_3);
		
		assertNotEquals(Color4F.GRAY, image.getColor4F(0, 0));
		assertNotEquals(Color4F.GRAY, image.getColor4F(1, 0));
		assertNotEquals(Color4F.GRAY, image.getColor4F(0, 1));
		assertNotEquals(Color4F.GRAY, image.getColor4F(1, 1));
		
		assertThrows(NullPointerException.class, () -> image.convolve((ConvolutionKernelNF)(null)));
	}
	
	@Test
	public void testConvolveConvolutionKernelNFColor4FPixelFilter() {
		final
		Image image = new Image(2, 2, Color4F.GRAY, DataFactory.forColor4F());
		image.convolve(ConvolutionKernelNF.GAUSSIAN_BLUR_3, (color, x, y) -> y != 0);
		
		assertEquals(Color4F.GRAY, image.getColor4F(0, 0));
		assertEquals(Color4F.GRAY, image.getColor4F(1, 0));
		
		assertNotEquals(Color4F.GRAY, image.getColor4F(0, 1));
		assertNotEquals(Color4F.GRAY, image.getColor4F(1, 1));
		
		image.convolve(ConvolutionKernelNF.GAUSSIAN_BLUR_3, (color, x, y) -> true);
		
		assertNotEquals(Color4F.GRAY, image.getColor4F(0, 0));
		assertNotEquals(Color4F.GRAY, image.getColor4F(1, 0));
		assertNotEquals(Color4F.GRAY, image.getColor4F(0, 1));
		assertNotEquals(Color4F.GRAY, image.getColor4F(1, 1));
		
		assertThrows(NullPointerException.class, () -> image.convolve(ConvolutionKernelNF.GAUSSIAN_BLUR_3, null));
		assertThrows(NullPointerException.class, () -> image.convolve((ConvolutionKernelNF)(null), (color, x, y) -> true));
	}
	
	@Test
	public void testCopy() {
		final Image a = new Image();
		final Image b = a.copy();
		
		assertEquals(a, b);
	}
	
	@Test
	public void testCopyBoolean() {
		final Image a = new Image();
		final Image b = a.copy(false);
		final Image c = a.copy(true);
		
		assertEquals(a, b);
		assertEquals(a, c);
	}
	
	@Test
	public void testCopyShape2I() {
		final Image image = new Image(10, 10);
		
		assertEquals(10, image.getResolutionX());
		assertEquals(10, image.getResolutionY());
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				image.setColorARGB(Color4I.toIntARGB(x, y, 0, 255), x, y);
			}
		}
		
		final Image imageCopy = image.copy(new Rectangle2I(new Point2I(1, 1), new Point2I(8, 8)));
		
		assertEquals(8, imageCopy.getResolutionX());
		assertEquals(8, imageCopy.getResolutionY());
		
		for(int y = 0; y < imageCopy.getResolutionY(); y++) {
			for(int x = 0; x < imageCopy.getResolutionX(); x++) {
				final int color = imageCopy.getColorARGB(x, y);
				
				assertEquals(x + 1, Color4I.fromIntARGBToIntR(color));
				assertEquals(y + 1, Color4I.fromIntARGBToIntG(color));
				
				assertEquals(  0, Color4I.fromIntARGBToIntB(color));
				assertEquals(255, Color4I.fromIntARGBToIntA(color));
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.copy(null));
	}
	
	@Test
	public void testDrawConsumerGraphics2D() {
		final Image image = new Image(1, 1);
		
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(0));
		
		image.draw(graphics2D -> {
			graphics2D.setColor(Color.RED);
			graphics2D.fillRect(0, 0, 1, 1);
		});
		
		assertEquals(Color4I.RED_A_R_G_B, image.getColorARGB(0));
		
		assertThrows(NullPointerException.class, () -> image.draw(null));
	}
	
	@Test
	public void testDrawShapeShape2IColor4D() {
		final
		Image image = new Image(3, 3, Color4D.WHITE);
		image.setChangeHistoryEnabled(true);
		
		assertEquals(Color4D.WHITE, image.getColor4D(0));
		assertEquals(Color4D.WHITE, image.getColor4D(1));
		assertEquals(Color4D.WHITE, image.getColor4D(2));
		assertEquals(Color4D.WHITE, image.getColor4D(3));
		assertEquals(Color4D.WHITE, image.getColor4D(4));
		assertEquals(Color4D.WHITE, image.getColor4D(5));
		assertEquals(Color4D.WHITE, image.getColor4D(6));
		assertEquals(Color4D.WHITE, image.getColor4D(7));
		assertEquals(Color4D.WHITE, image.getColor4D(8));
		
		image.drawShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), Color4D.BLACK);
		
		assertEquals(Color4D.BLACK, image.getColor4D(0));
		assertEquals(Color4D.BLACK, image.getColor4D(1));
		assertEquals(Color4D.BLACK, image.getColor4D(2));
		assertEquals(Color4D.BLACK, image.getColor4D(3));
		assertEquals(Color4D.WHITE, image.getColor4D(4));
		assertEquals(Color4D.BLACK, image.getColor4D(5));
		assertEquals(Color4D.BLACK, image.getColor4D(6));
		assertEquals(Color4D.BLACK, image.getColor4D(7));
		assertEquals(Color4D.BLACK, image.getColor4D(8));
		
		assertTrue(image.undo());
		
		assertEquals(Color4D.WHITE, image.getColor4D(0));
		assertEquals(Color4D.WHITE, image.getColor4D(1));
		assertEquals(Color4D.WHITE, image.getColor4D(2));
		assertEquals(Color4D.WHITE, image.getColor4D(3));
		assertEquals(Color4D.WHITE, image.getColor4D(4));
		assertEquals(Color4D.WHITE, image.getColor4D(5));
		assertEquals(Color4D.WHITE, image.getColor4D(6));
		assertEquals(Color4D.WHITE, image.getColor4D(7));
		assertEquals(Color4D.WHITE, image.getColor4D(8));
		
		assertThrows(NullPointerException.class, () -> image.drawShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), (Color4D)(null)));
		assertThrows(NullPointerException.class, () -> image.drawShape(null, Color4D.BLACK));
	}
	
	@Test
	public void testDrawShapeShape2IColor4DPixelOperator() {
		final
		Image image = new Image(3, 3, Color4D.WHITE);
		image.setChangeHistoryEnabled(true);
		
		assertEquals(Color4D.WHITE, image.getColor4D(0));
		assertEquals(Color4D.WHITE, image.getColor4D(1));
		assertEquals(Color4D.WHITE, image.getColor4D(2));
		assertEquals(Color4D.WHITE, image.getColor4D(3));
		assertEquals(Color4D.WHITE, image.getColor4D(4));
		assertEquals(Color4D.WHITE, image.getColor4D(5));
		assertEquals(Color4D.WHITE, image.getColor4D(6));
		assertEquals(Color4D.WHITE, image.getColor4D(7));
		assertEquals(Color4D.WHITE, image.getColor4D(8));
		
		image.drawShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), (final Color4D currentColor, final int x, final int y) -> Color4D.BLACK);
		
		assertEquals(Color4D.BLACK, image.getColor4D(0));
		assertEquals(Color4D.BLACK, image.getColor4D(1));
		assertEquals(Color4D.BLACK, image.getColor4D(2));
		assertEquals(Color4D.BLACK, image.getColor4D(3));
		assertEquals(Color4D.WHITE, image.getColor4D(4));
		assertEquals(Color4D.BLACK, image.getColor4D(5));
		assertEquals(Color4D.BLACK, image.getColor4D(6));
		assertEquals(Color4D.BLACK, image.getColor4D(7));
		assertEquals(Color4D.BLACK, image.getColor4D(8));
		
		assertTrue(image.undo());
		
		assertEquals(Color4D.WHITE, image.getColor4D(0));
		assertEquals(Color4D.WHITE, image.getColor4D(1));
		assertEquals(Color4D.WHITE, image.getColor4D(2));
		assertEquals(Color4D.WHITE, image.getColor4D(3));
		assertEquals(Color4D.WHITE, image.getColor4D(4));
		assertEquals(Color4D.WHITE, image.getColor4D(5));
		assertEquals(Color4D.WHITE, image.getColor4D(6));
		assertEquals(Color4D.WHITE, image.getColor4D(7));
		assertEquals(Color4D.WHITE, image.getColor4D(8));
		
		assertThrows(NullPointerException.class, () -> image.drawShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), (Color4DPixelOperator)(null)));
		assertThrows(NullPointerException.class, () -> image.drawShape(null, (final Color4D currentColor, final int x, final int y) -> Color4D.BLACK));
		assertThrows(NullPointerException.class, () -> image.drawShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), (final Color4D currentColor, final int x, final int y) -> null));
	}
	
	@Test
	public void testDrawShapeShape2IColor4F() {
		final
		Image image = new Image(3, 3, Color4F.WHITE);
		image.setChangeHistoryEnabled(true);
		
		assertEquals(Color4F.WHITE, image.getColor4F(0));
		assertEquals(Color4F.WHITE, image.getColor4F(1));
		assertEquals(Color4F.WHITE, image.getColor4F(2));
		assertEquals(Color4F.WHITE, image.getColor4F(3));
		assertEquals(Color4F.WHITE, image.getColor4F(4));
		assertEquals(Color4F.WHITE, image.getColor4F(5));
		assertEquals(Color4F.WHITE, image.getColor4F(6));
		assertEquals(Color4F.WHITE, image.getColor4F(7));
		assertEquals(Color4F.WHITE, image.getColor4F(8));
		
		image.drawShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), Color4F.BLACK);
		
		assertEquals(Color4F.BLACK, image.getColor4F(0));
		assertEquals(Color4F.BLACK, image.getColor4F(1));
		assertEquals(Color4F.BLACK, image.getColor4F(2));
		assertEquals(Color4F.BLACK, image.getColor4F(3));
		assertEquals(Color4F.WHITE, image.getColor4F(4));
		assertEquals(Color4F.BLACK, image.getColor4F(5));
		assertEquals(Color4F.BLACK, image.getColor4F(6));
		assertEquals(Color4F.BLACK, image.getColor4F(7));
		assertEquals(Color4F.BLACK, image.getColor4F(8));
		
		assertTrue(image.undo());
		
		assertEquals(Color4F.WHITE, image.getColor4F(0));
		assertEquals(Color4F.WHITE, image.getColor4F(1));
		assertEquals(Color4F.WHITE, image.getColor4F(2));
		assertEquals(Color4F.WHITE, image.getColor4F(3));
		assertEquals(Color4F.WHITE, image.getColor4F(4));
		assertEquals(Color4F.WHITE, image.getColor4F(5));
		assertEquals(Color4F.WHITE, image.getColor4F(6));
		assertEquals(Color4F.WHITE, image.getColor4F(7));
		assertEquals(Color4F.WHITE, image.getColor4F(8));
		
		assertThrows(NullPointerException.class, () -> image.drawShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), (Color4F)(null)));
		assertThrows(NullPointerException.class, () -> image.drawShape(null, Color4F.BLACK));
	}
	
	@Test
	public void testDrawShapeShape2IColor4FPixelOperator() {
		final
		Image image = new Image(3, 3, Color4F.WHITE);
		image.setChangeHistoryEnabled(true);
		
		assertEquals(Color4F.WHITE, image.getColor4F(0));
		assertEquals(Color4F.WHITE, image.getColor4F(1));
		assertEquals(Color4F.WHITE, image.getColor4F(2));
		assertEquals(Color4F.WHITE, image.getColor4F(3));
		assertEquals(Color4F.WHITE, image.getColor4F(4));
		assertEquals(Color4F.WHITE, image.getColor4F(5));
		assertEquals(Color4F.WHITE, image.getColor4F(6));
		assertEquals(Color4F.WHITE, image.getColor4F(7));
		assertEquals(Color4F.WHITE, image.getColor4F(8));
		
		image.drawShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), (final Color4F currentColor, final int x, final int y) -> Color4F.BLACK);
		
		assertEquals(Color4F.BLACK, image.getColor4F(0));
		assertEquals(Color4F.BLACK, image.getColor4F(1));
		assertEquals(Color4F.BLACK, image.getColor4F(2));
		assertEquals(Color4F.BLACK, image.getColor4F(3));
		assertEquals(Color4F.WHITE, image.getColor4F(4));
		assertEquals(Color4F.BLACK, image.getColor4F(5));
		assertEquals(Color4F.BLACK, image.getColor4F(6));
		assertEquals(Color4F.BLACK, image.getColor4F(7));
		assertEquals(Color4F.BLACK, image.getColor4F(8));
		
		assertTrue(image.undo());
		
		assertEquals(Color4F.WHITE, image.getColor4F(0));
		assertEquals(Color4F.WHITE, image.getColor4F(1));
		assertEquals(Color4F.WHITE, image.getColor4F(2));
		assertEquals(Color4F.WHITE, image.getColor4F(3));
		assertEquals(Color4F.WHITE, image.getColor4F(4));
		assertEquals(Color4F.WHITE, image.getColor4F(5));
		assertEquals(Color4F.WHITE, image.getColor4F(6));
		assertEquals(Color4F.WHITE, image.getColor4F(7));
		assertEquals(Color4F.WHITE, image.getColor4F(8));
		
		assertThrows(NullPointerException.class, () -> image.drawShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), (Color4FPixelOperator)(null)));
		assertThrows(NullPointerException.class, () -> image.drawShape(null, (final Color4F currentColor, final int x, final int y) -> Color4F.BLACK));
		assertThrows(NullPointerException.class, () -> image.drawShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), (final Color4F currentColor, final int x, final int y) -> null));
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
	public void testFillColor4DPixelOperator() {
		final
		Image image = new Image(2, 2, Color4D.WHITE, DataFactory.forColor4D());
		image.setColor4D(Color4D.WHITE, 0);
		image.setColor4D(Color4D.WHITE, 1);
		image.setColor4D(Color4D.WHITE, 2);
		image.setColor4D(Color4D.WHITE, 3);
		
		assertEquals(Color4D.WHITE, image.getColor4D(0));
		assertEquals(Color4D.WHITE, image.getColor4D(1));
		assertEquals(Color4D.WHITE, image.getColor4D(2));
		assertEquals(Color4D.WHITE, image.getColor4D(3));
		
		image.fill((final Color4D color, final int x, final int y) -> Color4D.BLACK);
		
		assertFalse(image.undo());
		
		assertEquals(Color4D.BLACK, image.getColor4D(0));
		assertEquals(Color4D.BLACK, image.getColor4D(1));
		assertEquals(Color4D.BLACK, image.getColor4D(2));
		assertEquals(Color4D.BLACK, image.getColor4D(3));
		
		image.setChangeHistoryEnabled(true);
		image.fill((final Color4D color, final int x, final int y) -> Color4D.WHITE);
		
		assertEquals(Color4D.WHITE, image.getColor4D(0));
		assertEquals(Color4D.WHITE, image.getColor4D(1));
		assertEquals(Color4D.WHITE, image.getColor4D(2));
		assertEquals(Color4D.WHITE, image.getColor4D(3));
		
		assertTrue(image.undo());
		
		assertEquals(Color4D.BLACK, image.getColor4D(0));
		assertEquals(Color4D.BLACK, image.getColor4D(1));
		assertEquals(Color4D.BLACK, image.getColor4D(2));
		assertEquals(Color4D.BLACK, image.getColor4D(3));
		
		assertThrows(NullPointerException.class, () -> image.fill((Color4DPixelOperator)(null)));
		assertThrows(NullPointerException.class, () -> image.fill((final Color4D color, final int x, final int y) -> null));
	}
	
	@Test
	public void testFillColor4DPixelOperatorColor4DPixelFilter() {
		final
		Image image = new Image(2, 2, Color4D.WHITE, DataFactory.forColor4D());
		image.setColor4D(Color4D.WHITE, 0);
		image.setColor4D(Color4D.WHITE, 1);
		image.setColor4D(Color4D.WHITE, 2);
		image.setColor4D(Color4D.WHITE, 3);
		
		assertEquals(Color4D.WHITE, image.getColor4D(0));
		assertEquals(Color4D.WHITE, image.getColor4D(1));
		assertEquals(Color4D.WHITE, image.getColor4D(2));
		assertEquals(Color4D.WHITE, image.getColor4D(3));
		
		image.fill((final Color4D color, final int x, final int y) -> Color4D.BLACK, (final Color4D color, final int x, final int y) -> y == 0);
		
		assertFalse(image.undo());
		
		assertEquals(Color4D.BLACK, image.getColor4D(0));
		assertEquals(Color4D.BLACK, image.getColor4D(1));
		assertEquals(Color4D.WHITE, image.getColor4D(2));
		assertEquals(Color4D.WHITE, image.getColor4D(3));
		
		image.setChangeHistoryEnabled(true);
		image.fill((final Color4D color, final int x, final int y) -> Color4D.BLACK, (final Color4D color, final int x, final int y) -> y == 1);
		
		assertEquals(Color4D.BLACK, image.getColor4D(0));
		assertEquals(Color4D.BLACK, image.getColor4D(1));
		assertEquals(Color4D.BLACK, image.getColor4D(2));
		assertEquals(Color4D.BLACK, image.getColor4D(3));
		
		assertTrue(image.undo());
		
		assertEquals(Color4D.BLACK, image.getColor4D(0));
		assertEquals(Color4D.BLACK, image.getColor4D(1));
		assertEquals(Color4D.WHITE, image.getColor4D(2));
		assertEquals(Color4D.WHITE, image.getColor4D(3));
		
		assertThrows(NullPointerException.class, () -> image.fill((final Color4D color, final int x, final int y) -> Color4D.BLACK, null));
		assertThrows(NullPointerException.class, () -> image.fill(null, (final Color4D color, final int x, final int y) -> true));
		assertThrows(NullPointerException.class, () -> image.fill((final Color4D color, final int x, final int y) -> null, (final Color4D color, final int x, final int y) -> true));
	}
	
	@Test
	public void testFillColor4FPixelOperator() {
		final
		Image image = new Image(2, 2, Color4F.WHITE, DataFactory.forColor4F());
		image.setColor4F(Color4F.WHITE, 0);
		image.setColor4F(Color4F.WHITE, 1);
		image.setColor4F(Color4F.WHITE, 2);
		image.setColor4F(Color4F.WHITE, 3);
		
		assertEquals(Color4F.WHITE, image.getColor4F(0));
		assertEquals(Color4F.WHITE, image.getColor4F(1));
		assertEquals(Color4F.WHITE, image.getColor4F(2));
		assertEquals(Color4F.WHITE, image.getColor4F(3));
		
		image.fill((final Color4F color, final int x, final int y) -> Color4F.BLACK);
		
		assertFalse(image.undo());
		
		assertEquals(Color4F.BLACK, image.getColor4F(0));
		assertEquals(Color4F.BLACK, image.getColor4F(1));
		assertEquals(Color4F.BLACK, image.getColor4F(2));
		assertEquals(Color4F.BLACK, image.getColor4F(3));
		
		image.setChangeHistoryEnabled(true);
		image.fill((final Color4F color, final int x, final int y) -> Color4F.WHITE);
		
		assertEquals(Color4F.WHITE, image.getColor4F(0));
		assertEquals(Color4F.WHITE, image.getColor4F(1));
		assertEquals(Color4F.WHITE, image.getColor4F(2));
		assertEquals(Color4F.WHITE, image.getColor4F(3));
		
		assertTrue(image.undo());
		
		assertEquals(Color4F.BLACK, image.getColor4F(0));
		assertEquals(Color4F.BLACK, image.getColor4F(1));
		assertEquals(Color4F.BLACK, image.getColor4F(2));
		assertEquals(Color4F.BLACK, image.getColor4F(3));
		
		assertThrows(NullPointerException.class, () -> image.fill((Color4FPixelOperator)(null)));
		assertThrows(NullPointerException.class, () -> image.fill((final Color4F color, final int x, final int y) -> null));
	}
	
	@Test
	public void testFillColor4FPixelOperatorColor4FPixelFilter() {
		final
		Image image = new Image(2, 2, Color4F.WHITE, DataFactory.forColor4F());
		image.setColor4F(Color4F.WHITE, 0);
		image.setColor4F(Color4F.WHITE, 1);
		image.setColor4F(Color4F.WHITE, 2);
		image.setColor4F(Color4F.WHITE, 3);
		
		assertEquals(Color4F.WHITE, image.getColor4F(0));
		assertEquals(Color4F.WHITE, image.getColor4F(1));
		assertEquals(Color4F.WHITE, image.getColor4F(2));
		assertEquals(Color4F.WHITE, image.getColor4F(3));
		
		image.fill((final Color4F color, final int x, final int y) -> Color4F.BLACK, (final Color4F color, final int x, final int y) -> y == 0);
		
		assertFalse(image.undo());
		
		assertEquals(Color4F.BLACK, image.getColor4F(0));
		assertEquals(Color4F.BLACK, image.getColor4F(1));
		assertEquals(Color4F.WHITE, image.getColor4F(2));
		assertEquals(Color4F.WHITE, image.getColor4F(3));
		
		image.setChangeHistoryEnabled(true);
		image.fill((final Color4F color, final int x, final int y) -> Color4F.BLACK, (final Color4F color, final int x, final int y) -> y == 1);
		
		assertEquals(Color4F.BLACK, image.getColor4F(0));
		assertEquals(Color4F.BLACK, image.getColor4F(1));
		assertEquals(Color4F.BLACK, image.getColor4F(2));
		assertEquals(Color4F.BLACK, image.getColor4F(3));
		
		assertTrue(image.undo());
		
		assertEquals(Color4F.BLACK, image.getColor4F(0));
		assertEquals(Color4F.BLACK, image.getColor4F(1));
		assertEquals(Color4F.WHITE, image.getColor4F(2));
		assertEquals(Color4F.WHITE, image.getColor4F(3));
		
		assertThrows(NullPointerException.class, () -> image.fill((final Color4F color, final int x, final int y) -> Color4F.BLACK, null));
		assertThrows(NullPointerException.class, () -> image.fill(null, (final Color4F color, final int x, final int y) -> true));
		assertThrows(NullPointerException.class, () -> image.fill((final Color4F color, final int x, final int y) -> null, (final Color4F color, final int x, final int y) -> true));
	}
	
	@Test
	public void testFillColorARGBPixelOperator() {
		final
		Image image = new Image(2, 2);
		image.setColorARGB(Color4I.WHITE_A_R_G_B, 0);
		image.setColorARGB(Color4I.WHITE_A_R_G_B, 1);
		image.setColorARGB(Color4I.WHITE_A_R_G_B, 2);
		image.setColorARGB(Color4I.WHITE_A_R_G_B, 3);
		
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(0));
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(1));
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(2));
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(3));
		
		image.fill((final int color, final int x, final int y) -> Color4I.BLACK_A_R_G_B);
		
		assertFalse(image.undo());
		
		assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(0));
		assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(1));
		assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(2));
		assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(3));
		
		image.setChangeHistoryEnabled(true);
		image.fill((final int colorARGB, final int x, final int y) -> Color4I.WHITE_A_R_G_B);
		
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(0));
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(1));
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(2));
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(3));
		
		assertTrue(image.undo());
		
		assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(0));
		assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(1));
		assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(2));
		assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(3));
		
		assertThrows(NullPointerException.class, () -> image.fill((PackedIntARGBPixelOperator)(null)));
	}
	
	@Test
	public void testFillColorARGBPixelOperatorColorARGBPixelFilter() {
		final
		Image image = new Image(2, 2);
		image.setColorARGB(Color4I.WHITE_A_R_G_B, 0);
		image.setColorARGB(Color4I.WHITE_A_R_G_B, 1);
		image.setColorARGB(Color4I.WHITE_A_R_G_B, 2);
		image.setColorARGB(Color4I.WHITE_A_R_G_B, 3);
		
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(0));
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(1));
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(2));
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(3));
		
		image.fill((final int color, final int x, final int y) -> Color4I.BLACK_A_R_G_B, (final int color, final int x, final int y) -> y == 0);
		
		assertFalse(image.undo());
		
		assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(0));
		assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(1));
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(2));
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(3));
		
		image.setChangeHistoryEnabled(true);
		image.fill((final int color, final int x, final int y) -> Color4I.BLACK_A_R_G_B, (final int color, final int x, final int y) -> y == 1);
		
		assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(0));
		assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(1));
		assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(2));
		assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(3));
		
		assertTrue(image.undo());
		
		assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(0));
		assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(1));
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(2));
		assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(3));
		
		assertThrows(NullPointerException.class, () -> image.fill((final int color, final int x, final int y) -> Color4I.BLACK_A_R_G_B, null));
		assertThrows(NullPointerException.class, () -> image.fill(null, (final int color, final int x, final int y) -> true));
	}
	
	@Test
	public void testFillRegionIntIntColor4DPixelOperator() {
		final Image image = new Image(7, 7, Color4D.WHITE);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4D.WHITE, image.getColor4D(x, y));
			}
		}
		
		image.fillRegion(0, 0, (final Color4D color, final int x, final int y) -> Color4D.RED);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4D.RED, image.getColor4D(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillRegion(0, 0, (final Color4D color, final int x, final int y) -> null));
		assertThrows(NullPointerException.class, () -> image.fillRegion(0, 0, (Color4DPixelOperator)(null)));
	}
	
	@Test
	public void testFillRegionIntIntColor4DPixelOperatorColor4DPixelFilter() {
		final
		Image image = new Image(7, 7, Color4D.WHITE);
		image.setChangeHistoryEnabled(true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4D.WHITE, image.getColor4D(x, y));
			}
		}
		
		image.drawShape(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), Color4D.BLACK);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4D.BLACK, image.getColor4D(x, y));
				} else {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				}
			}
		}
		
		image.fillRegion(0, 0, (final Color4D color, final int x, final int y) -> Color4D.RED, (final Color4D color, final int x, final int y) -> true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4D.RED, image.getColor4D(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4D.BLACK, image.getColor4D(x, y));
				} else {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				}
			}
		}
		
		assertTrue(image.undo());
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4D.BLACK, image.getColor4D(x, y));
				} else {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				}
			}
		}
		
		image.fillRegion(3, 3, (final Color4D color, final int x, final int y) -> Color4D.RED, (final Color4D color, final int x, final int y) -> true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4D.BLACK, image.getColor4D(x, y));
				} else {
					assertEquals(Color4D.RED, image.getColor4D(x, y));
				}
			}
		}
		
		assertTrue(image.undo());
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4D.BLACK, image.getColor4D(x, y));
				} else {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				}
			}
		}
		
		image.fillRegion(3, 3, (final Color4D color, final int x, final int y) -> Color4D.RED, (final Color4D color, final int x, final int y) -> false);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4D.BLACK, image.getColor4D(x, y));
				} else {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				}
			}
		}
		
		image.fillRegion(-1, +0, (final Color4D color, final int x, final int y) -> Color4D.RED, (final Color4D color, final int x, final int y) -> false);
		image.fillRegion(+0, -1, (final Color4D color, final int x, final int y) -> Color4D.RED, (final Color4D color, final int x, final int y) -> false);
		image.fillRegion(+7, +0, (final Color4D color, final int x, final int y) -> Color4D.RED, (final Color4D color, final int x, final int y) -> false);
		image.fillRegion(+0, +7, (final Color4D color, final int x, final int y) -> Color4D.RED, (final Color4D color, final int x, final int y) -> false);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4D.BLACK, image.getColor4D(x, y));
				} else {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				}
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillRegion(0, 0, (final Color4D color, final int x, final int y) -> Color4D.RED, null));
		assertThrows(NullPointerException.class, () -> image.fillRegion(0, 0, (final Color4D color, final int x, final int y) -> null, (final Color4D color, final int x, final int y) -> true));
		assertThrows(NullPointerException.class, () -> image.fillRegion(0, 0, null, (final Color4D color, final int x, final int y) -> true));
	}
	
	@Test
	public void testFillRegionIntIntColor4FPixelOperator() {
		final Image image = new Image(7, 7, Color4F.WHITE);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4F.WHITE, image.getColor4F(x, y));
			}
		}
		
		image.fillRegion(0, 0, (final Color4F color, final int x, final int y) -> Color4F.RED);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4F.RED, image.getColor4F(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillRegion(0, 0, (final Color4F color, final int x, final int y) -> null));
		assertThrows(NullPointerException.class, () -> image.fillRegion(0, 0, (Color4FPixelOperator)(null)));
	}
	
	@Test
	public void testFillRegionIntIntColor4FPixelOperatorColor4FPixelFilter() {
		final
		Image image = new Image(7, 7, Color4F.WHITE);
		image.setChangeHistoryEnabled(true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4F.WHITE, image.getColor4F(x, y));
			}
		}
		
		image.drawShape(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), Color4F.BLACK);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4F.BLACK, image.getColor4F(x, y));
				} else {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				}
			}
		}
		
		image.fillRegion(0, 0, (final Color4F color, final int x, final int y) -> Color4F.RED, (final Color4F color, final int x, final int y) -> true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4F.RED, image.getColor4F(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4F.BLACK, image.getColor4F(x, y));
				} else {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				}
			}
		}
		
		assertTrue(image.undo());
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4F.BLACK, image.getColor4F(x, y));
				} else {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				}
			}
		}
		
		image.fillRegion(3, 3, (final Color4F color, final int x, final int y) -> Color4F.RED, (final Color4F color, final int x, final int y) -> true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4F.BLACK, image.getColor4F(x, y));
				} else {
					assertEquals(Color4F.RED, image.getColor4F(x, y));
				}
			}
		}
		
		assertTrue(image.undo());
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4F.BLACK, image.getColor4F(x, y));
				} else {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				}
			}
		}
		
		image.fillRegion(3, 3, (final Color4F color, final int x, final int y) -> Color4F.RED, (final Color4F color, final int x, final int y) -> false);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4F.BLACK, image.getColor4F(x, y));
				} else {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				}
			}
		}
		
		image.fillRegion(-1, +0, (final Color4F color, final int x, final int y) -> Color4F.RED, (final Color4F color, final int x, final int y) -> false);
		image.fillRegion(+0, -1, (final Color4F color, final int x, final int y) -> Color4F.RED, (final Color4F color, final int x, final int y) -> false);
		image.fillRegion(+7, +0, (final Color4F color, final int x, final int y) -> Color4F.RED, (final Color4F color, final int x, final int y) -> false);
		image.fillRegion(+0, +7, (final Color4F color, final int x, final int y) -> Color4F.RED, (final Color4F color, final int x, final int y) -> false);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4F.BLACK, image.getColor4F(x, y));
				} else {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				}
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillRegion(0, 0, (final Color4F color, final int x, final int y) -> Color4F.RED, null));
		assertThrows(NullPointerException.class, () -> image.fillRegion(0, 0, (final Color4F color, final int x, final int y) -> null, (final Color4F color, final int x, final int y) -> true));
		assertThrows(NullPointerException.class, () -> image.fillRegion(0, 0, null, (final Color4F color, final int x, final int y) -> true));
	}
	
	@Test
	public void testFillRegionIntIntPackedIntARGBPixelOperator() {
		final Image image = new Image(7, 7, Color4I.WHITE_A_R_G_B);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(x, y));
			}
		}
		
		image.fillRegion(0, 0, (final int color, final int x, final int y) -> Color4I.RED_A_R_G_B);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4I.RED_A_R_G_B, image.getColorARGB(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillRegion(0, 0, (PackedIntARGBPixelOperator)(null)));
	}
	
	@Test
	public void testFillRegionIntIntPackedIntARGBPixelOperatorPackedIntARGBPixelFilter() {
		final
		Image image = new Image(7, 7, Color4I.WHITE_A_R_G_B);
		image.setChangeHistoryEnabled(true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(x, y));
			}
		}
		
		image.drawShape(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), Color4D.BLACK);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(x, y));
				} else {
					assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(x, y));
				}
			}
		}
		
		image.fillRegion(0, 0, (final int color, final int x, final int y) -> Color4I.RED_A_R_G_B, (final int color, final int x, final int y) -> true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4I.RED_A_R_G_B, image.getColorARGB(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(x, y));
				} else {
					assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(x, y));
				}
			}
		}
		
		assertTrue(image.undo());
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(x, y));
				} else {
					assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(x, y));
				}
			}
		}
		
		image.fillRegion(3, 3, (final int color, final int x, final int y) -> Color4I.RED_A_R_G_B, (final int color, final int x, final int y) -> true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(x, y));
				} else {
					assertEquals(Color4I.RED_A_R_G_B, image.getColorARGB(x, y));
				}
			}
		}
		
		assertTrue(image.undo());
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(x, y));
				} else {
					assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(x, y));
				}
			}
		}
		
		image.fillRegion(3, 3, (final int color, final int x, final int y) -> Color4I.RED_A_R_G_B, (final int color, final int x, final int y) -> false);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(x, y));
				} else {
					assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(x, y));
				}
			}
		}
		
		image.fillRegion(-1, +0, (final int color, final int x, final int y) -> Color4I.RED_A_R_G_B, (final int color, final int x, final int y) -> false);
		image.fillRegion(+0, -1, (final int color, final int x, final int y) -> Color4I.RED_A_R_G_B, (final int color, final int x, final int y) -> false);
		image.fillRegion(+7, +0, (final int color, final int x, final int y) -> Color4I.RED_A_R_G_B, (final int color, final int x, final int y) -> false);
		image.fillRegion(+0, +7, (final int color, final int x, final int y) -> Color4I.RED_A_R_G_B, (final int color, final int x, final int y) -> false);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4I.BLACK_A_R_G_B, image.getColorARGB(x, y));
				} else {
					assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(x, y));
				}
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillRegion(0, 0, (final int color, final int x, final int y) -> Color4I.RED_A_R_G_B, null));
		assertThrows(NullPointerException.class, () -> image.fillRegion(0, 0, null, (final int color, final int x, final int y) -> true));
	}
	
	@Test
	public void testFillRegionPoint2IColor4DPixelOperator() {
		final Image image = new Image(7, 7, Color4D.WHITE);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4D.WHITE, image.getColor4D(x, y));
			}
		}
		
		image.fillRegion(new Point2I(0, 0), (final Color4D color, final int x, final int y) -> Color4D.RED);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4D.RED, image.getColor4D(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillRegion(new Point2I(0, 0), (final Color4D color, final int x, final int y) -> null));
		assertThrows(NullPointerException.class, () -> image.fillRegion(new Point2I(0, 0), (Color4DPixelOperator)(null)));
		assertThrows(NullPointerException.class, () -> image.fillRegion(null, (final Color4D color, final int x, final int y) -> Color4D.RED));
	}
	
	@Test
	public void testFillRegionPoint2IColor4DPixelOperatorColor4DPixelFilter() {
		final Image image = new Image(7, 7, Color4D.WHITE);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4D.WHITE, image.getColor4D(x, y));
			}
		}
		
		image.fillRegion(new Point2I(0, 0), (final Color4D color, final int x, final int y) -> Color4D.RED, (final Color4D color, final int x, final int y) -> true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4D.RED, image.getColor4D(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillRegion(new Point2I(0, 0), (final Color4D color, final int x, final int y) -> Color4D.RED, null));
		assertThrows(NullPointerException.class, () -> image.fillRegion(new Point2I(0, 0), (final Color4D color, final int x, final int y) -> null, (final Color4D color, final int x, final int y) -> true));
		assertThrows(NullPointerException.class, () -> image.fillRegion(new Point2I(0, 0), null, (final Color4D color, final int x, final int y) -> true));
		assertThrows(NullPointerException.class, () -> image.fillRegion(null, (final Color4D color, final int x, final int y) -> Color4D.RED, (final Color4D color, final int x, final int y) -> true));
	}
	
	@Test
	public void testFillRegionPoint2IColor4FPixelOperator() {
		final Image image = new Image(7, 7, Color4F.WHITE);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4F.WHITE, image.getColor4F(x, y));
			}
		}
		
		image.fillRegion(new Point2I(0, 0), (final Color4F color, final int x, final int y) -> Color4F.RED);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4F.RED, image.getColor4F(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillRegion(new Point2I(0, 0), (final Color4F color, final int x, final int y) -> null));
		assertThrows(NullPointerException.class, () -> image.fillRegion(new Point2I(0, 0), (Color4FPixelOperator)(null)));
		assertThrows(NullPointerException.class, () -> image.fillRegion(null, (final Color4F color, final int x, final int y) -> Color4F.RED));
	}
	
	@Test
	public void testFillRegionPoint2IColor4FPixelOperatorColor4FPixelFilter() {
		final Image image = new Image(7, 7, Color4F.WHITE);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4F.WHITE, image.getColor4F(x, y));
			}
		}
		
		image.fillRegion(new Point2I(0, 0), (final Color4F color, final int x, final int y) -> Color4F.RED, (final Color4F color, final int x, final int y) -> true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4F.RED, image.getColor4F(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillRegion(new Point2I(0, 0), (final Color4F color, final int x, final int y) -> Color4F.RED, null));
		assertThrows(NullPointerException.class, () -> image.fillRegion(new Point2I(0, 0), (final Color4F color, final int x, final int y) -> null, (final Color4F color, final int x, final int y) -> true));
		assertThrows(NullPointerException.class, () -> image.fillRegion(new Point2I(0, 0), null, (final Color4F color, final int x, final int y) -> true));
		assertThrows(NullPointerException.class, () -> image.fillRegion(null, (final Color4F color, final int x, final int y) -> Color4F.RED, (final Color4F color, final int x, final int y) -> true));
	}
	
	@Test
	public void testFillRegionPoint2IPackedIntARGBPixelOperator() {
		final Image image = new Image(7, 7, Color4I.WHITE_A_R_G_B);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(x, y));
			}
		}
		
		image.fillRegion(new Point2I(0, 0), (final int color, final int x, final int y) -> Color4I.RED_A_R_G_B);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4I.RED_A_R_G_B, image.getColorARGB(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillRegion(new Point2I(0, 0), (PackedIntARGBPixelOperator)(null)));
		assertThrows(NullPointerException.class, () -> image.fillRegion(null, (final int color, final int x, final int y) -> Color4I.RED_A_R_G_B));
	}
	
	@Test
	public void testFillRegionPoint2IPackedIntARGBPixelOperatorPackedIntARGBPixelFilter() {
		final Image image = new Image(7, 7, Color4I.WHITE_A_R_G_B);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4I.WHITE_A_R_G_B, image.getColorARGB(x, y));
			}
		}
		
		image.fillRegion(new Point2I(0, 0), (final int color, final int x, final int y) -> Color4I.RED_A_R_G_B, (final int color, final int x, final int y) -> true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4I.RED_A_R_G_B, image.getColorARGB(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillRegion(new Point2I(0, 0), (final int color, final int x, final int y) -> Color4I.RED_A_R_G_B, null));
		assertThrows(NullPointerException.class, () -> image.fillRegion(new Point2I(0, 0), null, (final int color, final int x, final int y) -> true));
		assertThrows(NullPointerException.class, () -> image.fillRegion(null, (final int color, final int x, final int y) -> Color4I.RED_A_R_G_B, (final int color, final int x, final int y) -> true));
	}
	
	@Test
	public void testFillShapeComplementShape2IColor4D() {
		final
		Image image = new Image(7, 7, Color4D.WHITE);
		image.setChangeHistoryEnabled(true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4D.WHITE, image.getColor4D(x, y));
			}
		}
		
		image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), Color4D.RED);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4D.RED, image.getColor4D(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				} else {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				}
			}
		}
		
		assertTrue(image.undo());
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4D.WHITE, image.getColor4D(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), (Color4D)(null)));
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(null, Color4D.RED));
	}
	
	@Test
	public void testFillShapeComplementShape2IColor4DBoolean() {
		final
		Image image = new Image(7, 7, Color4D.WHITE);
		image.setChangeHistoryEnabled(true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4D.WHITE, image.getColor4D(x, y));
			}
		}
		
		image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), Color4D.RED, false);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4D.RED, image.getColor4D(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				} else {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				}
			}
		}
		
		assertTrue(image.undo());
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4D.WHITE, image.getColor4D(x, y));
			}
		}
		
		image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), Color4D.RED, true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4D.RED, image.getColor4D(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				} else {
					assertEquals(Color4D.RED, image.getColor4D(x, y));
				}
			}
		}
		
		assertTrue(image.undo());
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4D.WHITE, image.getColor4D(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), (Color4D)(null), false));
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(null, Color4D.RED, false));
	}
	
	@Test
	public void testFillShapeComplementShape2IColor4DPixelOperator() {
		final
		Image image = new Image(7, 7, Color4D.WHITE);
		image.setChangeHistoryEnabled(true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4D.WHITE, image.getColor4D(x, y));
			}
		}
		
		image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), (final Color4D color, final int x, final int y) -> Color4D.RED);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4D.RED, image.getColor4D(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				} else {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				}
			}
		}
		
		assertTrue(image.undo());
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4D.WHITE, image.getColor4D(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), (final Color4D color, final int x, final int y) -> null));
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), (Color4DPixelOperator)(null)));
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(null, (final Color4D color, final int x, final int y) -> Color4D.RED));
	}
	
	@Test
	public void testFillShapeComplementShape2IColor4DPixelOperatorBoolean() {
		final
		Image image = new Image(7, 7, Color4D.WHITE);
		image.setChangeHistoryEnabled(true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4D.WHITE, image.getColor4D(x, y));
			}
		}
		
		image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), (final Color4D color, final int x, final int y) -> Color4D.RED, false);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4D.RED, image.getColor4D(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				} else {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				}
			}
		}
		
		assertTrue(image.undo());
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4D.WHITE, image.getColor4D(x, y));
			}
		}
		
		image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), (final Color4D color, final int x, final int y) -> Color4D.RED, true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4D.RED, image.getColor4D(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4D.WHITE, image.getColor4D(x, y));
				} else {
					assertEquals(Color4D.RED, image.getColor4D(x, y));
				}
			}
		}
		
		assertTrue(image.undo());
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4D.WHITE, image.getColor4D(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), (final Color4D color, final int x, final int y) -> null, false));
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), (Color4DPixelOperator)(null), false));
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(null, (final Color4D color, final int x, final int y) -> Color4D.RED, false));
	}
	
	@Test
	public void testFillShapeComplementShape2IColor4F() {
		final
		Image image = new Image(7, 7, Color4F.WHITE);
		image.setChangeHistoryEnabled(true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4F.WHITE, image.getColor4F(x, y));
			}
		}
		
		image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), Color4F.RED);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4F.RED, image.getColor4F(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				} else {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				}
			}
		}
		
		assertTrue(image.undo());
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4F.WHITE, image.getColor4F(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), (Color4F)(null)));
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(null, Color4F.RED));
	}
	
	@Test
	public void testFillShapeComplementShape2IColor4FBoolean() {
		final
		Image image = new Image(7, 7, Color4F.WHITE);
		image.setChangeHistoryEnabled(true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4F.WHITE, image.getColor4F(x, y));
			}
		}
		
		image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), Color4F.RED, false);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4F.RED, image.getColor4F(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				} else {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				}
			}
		}
		
		assertTrue(image.undo());
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4F.WHITE, image.getColor4F(x, y));
			}
		}
		
		image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), Color4F.RED, true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4F.RED, image.getColor4F(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				} else {
					assertEquals(Color4F.RED, image.getColor4F(x, y));
				}
			}
		}
		
		assertTrue(image.undo());
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4F.WHITE, image.getColor4F(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), (Color4F)(null), false));
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(null, Color4F.RED, false));
	}
	
	@Test
	public void testFillShapeComplementShape2IColor4FPixelOperator() {
		final
		Image image = new Image(7, 7, Color4F.WHITE);
		image.setChangeHistoryEnabled(true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4F.WHITE, image.getColor4F(x, y));
			}
		}
		
		image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), (final Color4F color, final int x, final int y) -> Color4F.RED);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4F.RED, image.getColor4F(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				} else {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				}
			}
		}
		
		assertTrue(image.undo());
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4F.WHITE, image.getColor4F(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), (final Color4F color, final int x, final int y) -> null));
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), (Color4FPixelOperator)(null)));
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(null, (final Color4F color, final int x, final int y) -> Color4F.RED));
	}
	
	@Test
	public void testFillShapeComplementShape2IColor4FPixelOperatorBoolean() {
		final
		Image image = new Image(7, 7, Color4F.WHITE);
		image.setChangeHistoryEnabled(true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4F.WHITE, image.getColor4F(x, y));
			}
		}
		
		image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), (final Color4F color, final int x, final int y) -> Color4F.RED, false);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4F.RED, image.getColor4F(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				} else {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				}
			}
		}
		
		assertTrue(image.undo());
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4F.WHITE, image.getColor4F(x, y));
			}
		}
		
		image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), (final Color4F color, final int x, final int y) -> Color4F.RED, true);
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				if(x == 0 || x == 6 || y == 0 || y == 6) {
					assertEquals(Color4F.RED, image.getColor4F(x, y));
				} else if((x == 1 || x == 5) && (y >= 1 && y <= 5) || (y == 1 || y == 5) && (x >= 1 && x <= 5)) {
					assertEquals(Color4F.WHITE, image.getColor4F(x, y));
				} else {
					assertEquals(Color4F.RED, image.getColor4F(x, y));
				}
			}
		}
		
		assertTrue(image.undo());
		
		for(int y = 0; y < image.getResolutionY(); y++) {
			for(int x = 0; x < image.getResolutionX(); x++) {
				assertEquals(Color4F.WHITE, image.getColor4F(x, y));
			}
		}
		
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), (final Color4F color, final int x, final int y) -> null, false));
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(new Rectangle2I(new Point2I(1, 1), new Point2I(5, 5)), (Color4FPixelOperator)(null), false));
		assertThrows(NullPointerException.class, () -> image.fillShapeComplement(null, (final Color4F color, final int x, final int y) -> Color4F.RED, false));
	}
	
	@Test
	public void testFillShapeShape2IColor4D() {
		final
		Image image = new Image(3, 3, Color4D.WHITE);
		image.setChangeHistoryEnabled(true);
		
		assertEquals(Color4D.WHITE, image.getColor4D(0));
		assertEquals(Color4D.WHITE, image.getColor4D(1));
		assertEquals(Color4D.WHITE, image.getColor4D(2));
		assertEquals(Color4D.WHITE, image.getColor4D(3));
		assertEquals(Color4D.WHITE, image.getColor4D(4));
		assertEquals(Color4D.WHITE, image.getColor4D(5));
		assertEquals(Color4D.WHITE, image.getColor4D(6));
		assertEquals(Color4D.WHITE, image.getColor4D(7));
		assertEquals(Color4D.WHITE, image.getColor4D(8));
		
		image.fillShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), Color4D.BLACK);
		
		assertEquals(Color4D.BLACK, image.getColor4D(0));
		assertEquals(Color4D.BLACK, image.getColor4D(1));
		assertEquals(Color4D.BLACK, image.getColor4D(2));
		assertEquals(Color4D.BLACK, image.getColor4D(3));
		assertEquals(Color4D.BLACK, image.getColor4D(4));
		assertEquals(Color4D.BLACK, image.getColor4D(5));
		assertEquals(Color4D.BLACK, image.getColor4D(6));
		assertEquals(Color4D.BLACK, image.getColor4D(7));
		assertEquals(Color4D.BLACK, image.getColor4D(8));
		
		assertTrue(image.undo());
		
		assertEquals(Color4D.WHITE, image.getColor4D(0));
		assertEquals(Color4D.WHITE, image.getColor4D(1));
		assertEquals(Color4D.WHITE, image.getColor4D(2));
		assertEquals(Color4D.WHITE, image.getColor4D(3));
		assertEquals(Color4D.WHITE, image.getColor4D(4));
		assertEquals(Color4D.WHITE, image.getColor4D(5));
		assertEquals(Color4D.WHITE, image.getColor4D(6));
		assertEquals(Color4D.WHITE, image.getColor4D(7));
		assertEquals(Color4D.WHITE, image.getColor4D(8));
		
		assertThrows(NullPointerException.class, () -> image.fillShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), (Color4D)(null)));
		assertThrows(NullPointerException.class, () -> image.fillShape(null, Color4D.BLACK));
	}
	
	@Test
	public void testFillShapeShape2IColor4DPixelOperator() {
		final
		Image image = new Image(3, 3, Color4D.WHITE);
		image.setChangeHistoryEnabled(true);
		
		assertEquals(Color4D.WHITE, image.getColor4D(0));
		assertEquals(Color4D.WHITE, image.getColor4D(1));
		assertEquals(Color4D.WHITE, image.getColor4D(2));
		assertEquals(Color4D.WHITE, image.getColor4D(3));
		assertEquals(Color4D.WHITE, image.getColor4D(4));
		assertEquals(Color4D.WHITE, image.getColor4D(5));
		assertEquals(Color4D.WHITE, image.getColor4D(6));
		assertEquals(Color4D.WHITE, image.getColor4D(7));
		assertEquals(Color4D.WHITE, image.getColor4D(8));
		
		image.fillShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), (final Color4D currentColor, final int x, final int y) -> Color4D.BLACK);
		
		assertEquals(Color4D.BLACK, image.getColor4D(0));
		assertEquals(Color4D.BLACK, image.getColor4D(1));
		assertEquals(Color4D.BLACK, image.getColor4D(2));
		assertEquals(Color4D.BLACK, image.getColor4D(3));
		assertEquals(Color4D.BLACK, image.getColor4D(4));
		assertEquals(Color4D.BLACK, image.getColor4D(5));
		assertEquals(Color4D.BLACK, image.getColor4D(6));
		assertEquals(Color4D.BLACK, image.getColor4D(7));
		assertEquals(Color4D.BLACK, image.getColor4D(8));
		
		assertTrue(image.undo());
		
		assertEquals(Color4D.WHITE, image.getColor4D(0));
		assertEquals(Color4D.WHITE, image.getColor4D(1));
		assertEquals(Color4D.WHITE, image.getColor4D(2));
		assertEquals(Color4D.WHITE, image.getColor4D(3));
		assertEquals(Color4D.WHITE, image.getColor4D(4));
		assertEquals(Color4D.WHITE, image.getColor4D(5));
		assertEquals(Color4D.WHITE, image.getColor4D(6));
		assertEquals(Color4D.WHITE, image.getColor4D(7));
		assertEquals(Color4D.WHITE, image.getColor4D(8));
		
		assertThrows(NullPointerException.class, () -> image.fillShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), (Color4DPixelOperator)(null)));
		assertThrows(NullPointerException.class, () -> image.fillShape(null, (final Color4D currentColor, final int x, final int y) -> Color4D.BLACK));
		assertThrows(NullPointerException.class, () -> image.fillShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), (final Color4D currentColor, final int x, final int y) -> null));
	}
	
	@Test
	public void testFillShapeShape2IColor4F() {
		final
		Image image = new Image(3, 3, Color4F.WHITE);
		image.setChangeHistoryEnabled(true);
		
		assertEquals(Color4F.WHITE, image.getColor4F(0));
		assertEquals(Color4F.WHITE, image.getColor4F(1));
		assertEquals(Color4F.WHITE, image.getColor4F(2));
		assertEquals(Color4F.WHITE, image.getColor4F(3));
		assertEquals(Color4F.WHITE, image.getColor4F(4));
		assertEquals(Color4F.WHITE, image.getColor4F(5));
		assertEquals(Color4F.WHITE, image.getColor4F(6));
		assertEquals(Color4F.WHITE, image.getColor4F(7));
		assertEquals(Color4F.WHITE, image.getColor4F(8));
		
		image.fillShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), Color4F.BLACK);
		
		assertEquals(Color4F.BLACK, image.getColor4F(0));
		assertEquals(Color4F.BLACK, image.getColor4F(1));
		assertEquals(Color4F.BLACK, image.getColor4F(2));
		assertEquals(Color4F.BLACK, image.getColor4F(3));
		assertEquals(Color4F.BLACK, image.getColor4F(4));
		assertEquals(Color4F.BLACK, image.getColor4F(5));
		assertEquals(Color4F.BLACK, image.getColor4F(6));
		assertEquals(Color4F.BLACK, image.getColor4F(7));
		assertEquals(Color4F.BLACK, image.getColor4F(8));
		
		assertTrue(image.undo());
		
		assertEquals(Color4F.WHITE, image.getColor4F(0));
		assertEquals(Color4F.WHITE, image.getColor4F(1));
		assertEquals(Color4F.WHITE, image.getColor4F(2));
		assertEquals(Color4F.WHITE, image.getColor4F(3));
		assertEquals(Color4F.WHITE, image.getColor4F(4));
		assertEquals(Color4F.WHITE, image.getColor4F(5));
		assertEquals(Color4F.WHITE, image.getColor4F(6));
		assertEquals(Color4F.WHITE, image.getColor4F(7));
		assertEquals(Color4F.WHITE, image.getColor4F(8));
		
		assertThrows(NullPointerException.class, () -> image.fillShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), (Color4F)(null)));
		assertThrows(NullPointerException.class, () -> image.fillShape(null, Color4F.BLACK));
	}
	
	@Test
	public void testFillShapeShape2IColor4FPixelOperator() {
		final
		Image image = new Image(3, 3, Color4F.WHITE);
		image.setChangeHistoryEnabled(true);
		
		assertEquals(Color4F.WHITE, image.getColor4F(0));
		assertEquals(Color4F.WHITE, image.getColor4F(1));
		assertEquals(Color4F.WHITE, image.getColor4F(2));
		assertEquals(Color4F.WHITE, image.getColor4F(3));
		assertEquals(Color4F.WHITE, image.getColor4F(4));
		assertEquals(Color4F.WHITE, image.getColor4F(5));
		assertEquals(Color4F.WHITE, image.getColor4F(6));
		assertEquals(Color4F.WHITE, image.getColor4F(7));
		assertEquals(Color4F.WHITE, image.getColor4F(8));
		
		image.fillShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), (final Color4F currentColor, final int x, final int y) -> Color4F.BLACK);
		
		assertEquals(Color4F.BLACK, image.getColor4F(0));
		assertEquals(Color4F.BLACK, image.getColor4F(1));
		assertEquals(Color4F.BLACK, image.getColor4F(2));
		assertEquals(Color4F.BLACK, image.getColor4F(3));
		assertEquals(Color4F.BLACK, image.getColor4F(4));
		assertEquals(Color4F.BLACK, image.getColor4F(5));
		assertEquals(Color4F.BLACK, image.getColor4F(6));
		assertEquals(Color4F.BLACK, image.getColor4F(7));
		assertEquals(Color4F.BLACK, image.getColor4F(8));
		
		assertTrue(image.undo());
		
		assertEquals(Color4F.WHITE, image.getColor4F(0));
		assertEquals(Color4F.WHITE, image.getColor4F(1));
		assertEquals(Color4F.WHITE, image.getColor4F(2));
		assertEquals(Color4F.WHITE, image.getColor4F(3));
		assertEquals(Color4F.WHITE, image.getColor4F(4));
		assertEquals(Color4F.WHITE, image.getColor4F(5));
		assertEquals(Color4F.WHITE, image.getColor4F(6));
		assertEquals(Color4F.WHITE, image.getColor4F(7));
		assertEquals(Color4F.WHITE, image.getColor4F(8));
		
		assertThrows(NullPointerException.class, () -> image.fillShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), (Color4FPixelOperator)(null)));
		assertThrows(NullPointerException.class, () -> image.fillShape(null, (final Color4F currentColor, final int x, final int y) -> Color4F.BLACK));
		assertThrows(NullPointerException.class, () -> image.fillShape(new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), (final Color4F currentColor, final int x, final int y) -> null));
	}
	
	@Test
	public void testFindBoundsFor() {
		final Image a = new Image(4, 4);
		final Image b = new Image(2, 2);
		
		/*
		 * [B][B][W][W]
		 * [B][B][W][W]
		 * [B][B][B][B]
		 * [B][B][B][B]
		 */
		
		a.setColorARGB(Color4I.BLACK_A_R_G_B, 0, 0);
		a.setColorARGB(Color4I.BLACK_A_R_G_B, 1, 0);
		a.setColorARGB(Color4I.WHITE_A_R_G_B, 2, 0);
		a.setColorARGB(Color4I.WHITE_A_R_G_B, 3, 0);
		a.setColorARGB(Color4I.BLACK_A_R_G_B, 0, 1);
		a.setColorARGB(Color4I.BLACK_A_R_G_B, 1, 1);
		a.setColorARGB(Color4I.WHITE_A_R_G_B, 2, 1);
		a.setColorARGB(Color4I.WHITE_A_R_G_B, 3, 1);
		a.setColorARGB(Color4I.BLACK_A_R_G_B, 0, 2);
		a.setColorARGB(Color4I.BLACK_A_R_G_B, 1, 2);
		a.setColorARGB(Color4I.BLACK_A_R_G_B, 2, 2);
		a.setColorARGB(Color4I.BLACK_A_R_G_B, 3, 2);
		a.setColorARGB(Color4I.BLACK_A_R_G_B, 0, 3);
		a.setColorARGB(Color4I.BLACK_A_R_G_B, 1, 3);
		a.setColorARGB(Color4I.BLACK_A_R_G_B, 2, 3);
		a.setColorARGB(Color4I.BLACK_A_R_G_B, 3, 3);
		
		b.setColorARGB(Color4I.BLACK_A_R_G_B, 0, 0);
		b.setColorARGB(Color4I.BLACK_A_R_G_B, 1, 0);
		b.setColorARGB(Color4I.BLACK_A_R_G_B, 0, 1);
		b.setColorARGB(Color4I.BLACK_A_R_G_B, 1, 1);
		
		final List<Rectangle2I> rectangles = a.findBoundsFor(b);
		
		assertEquals(5, rectangles.size());
		
		assertEquals(new Rectangle2I(new Point2I(0, 0), new Point2I(1, 1)), rectangles.get(0));
		assertEquals(new Rectangle2I(new Point2I(0, 1), new Point2I(1, 2)), rectangles.get(1));
		assertEquals(new Rectangle2I(new Point2I(0, 2), new Point2I(1, 3)), rectangles.get(2));
		assertEquals(new Rectangle2I(new Point2I(1, 2), new Point2I(2, 3)), rectangles.get(3));
		assertEquals(new Rectangle2I(new Point2I(2, 2), new Point2I(3, 3)), rectangles.get(4));
		
		assertThrows(NullPointerException.class, () -> a.findBoundsFor(null));
	}
	
	@Test
	public void testFlip() {
		final
		Image image = new Image(2, 2);
		image.setColorARGB(Color4I.toIntARGB(10, 0, 0, 255), 0);
		image.setColorARGB(Color4I.toIntARGB(20, 0, 0, 255), 1);
		image.setColorARGB(Color4I.toIntARGB(30, 0, 0, 255), 2);
		image.setColorARGB(Color4I.toIntARGB(40, 0, 0, 255), 3);
		
		assertEquals(Color4I.toIntARGB(10, 0, 0, 255), image.getColorARGB(0));
		assertEquals(Color4I.toIntARGB(20, 0, 0, 255), image.getColorARGB(1));
		assertEquals(Color4I.toIntARGB(30, 0, 0, 255), image.getColorARGB(2));
		assertEquals(Color4I.toIntARGB(40, 0, 0, 255), image.getColorARGB(3));
		
		image.flip();
		
		assertFalse(image.undo());
		
		assertEquals(Color4I.toIntARGB(40, 0, 0, 255), image.getColorARGB(0));
		assertEquals(Color4I.toIntARGB(30, 0, 0, 255), image.getColorARGB(1));
		assertEquals(Color4I.toIntARGB(20, 0, 0, 255), image.getColorARGB(2));
		assertEquals(Color4I.toIntARGB(10, 0, 0, 255), image.getColorARGB(3));
		
		image.flip();
		
		assertFalse(image.undo());
		
		assertEquals(Color4I.toIntARGB(10, 0, 0, 255), image.getColorARGB(0));
		assertEquals(Color4I.toIntARGB(20, 0, 0, 255), image.getColorARGB(1));
		assertEquals(Color4I.toIntARGB(30, 0, 0, 255), image.getColorARGB(2));
		assertEquals(Color4I.toIntARGB(40, 0, 0, 255), image.getColorARGB(3));
		
		image.setChangeHistoryEnabled(true);
		
		image.flip();
		
		assertEquals(Color4I.toIntARGB(40, 0, 0, 255), image.getColorARGB(0));
		assertEquals(Color4I.toIntARGB(30, 0, 0, 255), image.getColorARGB(1));
		assertEquals(Color4I.toIntARGB(20, 0, 0, 255), image.getColorARGB(2));
		assertEquals(Color4I.toIntARGB(10, 0, 0, 255), image.getColorARGB(3));
		
		assertTrue(image.undo());
		
		assertEquals(Color4I.toIntARGB(20, 0, 0, 255), image.getColorARGB(0));
		assertEquals(Color4I.toIntARGB(10, 0, 0, 255), image.getColorARGB(1));
		assertEquals(Color4I.toIntARGB(40, 0, 0, 255), image.getColorARGB(2));
		assertEquals(Color4I.toIntARGB(30, 0, 0, 255), image.getColorARGB(3));
		
		assertTrue(image.undo());
		
		assertEquals(Color4I.toIntARGB(10, 0, 0, 255), image.getColorARGB(0));
		assertEquals(Color4I.toIntARGB(20, 0, 0, 255), image.getColorARGB(1));
		assertEquals(Color4I.toIntARGB(30, 0, 0, 255), image.getColorARGB(2));
		assertEquals(Color4I.toIntARGB(40, 0, 0, 255), image.getColorARGB(3));
	}
	
	@Test
	public void testFlipX() {
		final
		Image image = new Image(2, 2);
		image.setColorARGB(Color4I.toIntARGB(10, 0, 0, 255), 0);
		image.setColorARGB(Color4I.toIntARGB(20, 0, 0, 255), 1);
		image.setColorARGB(Color4I.toIntARGB(30, 0, 0, 255), 2);
		image.setColorARGB(Color4I.toIntARGB(40, 0, 0, 255), 3);
		
		assertEquals(Color4I.toIntARGB(10, 0, 0, 255), image.getColorARGB(0));
		assertEquals(Color4I.toIntARGB(20, 0, 0, 255), image.getColorARGB(1));
		assertEquals(Color4I.toIntARGB(30, 0, 0, 255), image.getColorARGB(2));
		assertEquals(Color4I.toIntARGB(40, 0, 0, 255), image.getColorARGB(3));
		
		image.flipX();
		
		assertFalse(image.undo());
		
		assertEquals(Color4I.toIntARGB(20, 0, 0, 255), image.getColorARGB(0));
		assertEquals(Color4I.toIntARGB(10, 0, 0, 255), image.getColorARGB(1));
		assertEquals(Color4I.toIntARGB(40, 0, 0, 255), image.getColorARGB(2));
		assertEquals(Color4I.toIntARGB(30, 0, 0, 255), image.getColorARGB(3));
		
		image.flipX();
		
		assertFalse(image.undo());
		
		assertEquals(Color4I.toIntARGB(10, 0, 0, 255), image.getColorARGB(0));
		assertEquals(Color4I.toIntARGB(20, 0, 0, 255), image.getColorARGB(1));
		assertEquals(Color4I.toIntARGB(30, 0, 0, 255), image.getColorARGB(2));
		assertEquals(Color4I.toIntARGB(40, 0, 0, 255), image.getColorARGB(3));
		
		image.setChangeHistoryEnabled(true);
		
		image.flipX();
		
		assertEquals(Color4I.toIntARGB(20, 0, 0, 255), image.getColorARGB(0));
		assertEquals(Color4I.toIntARGB(10, 0, 0, 255), image.getColorARGB(1));
		assertEquals(Color4I.toIntARGB(40, 0, 0, 255), image.getColorARGB(2));
		assertEquals(Color4I.toIntARGB(30, 0, 0, 255), image.getColorARGB(3));
		
		assertTrue(image.undo());
		
		assertEquals(Color4I.toIntARGB(10, 0, 0, 255), image.getColorARGB(0));
		assertEquals(Color4I.toIntARGB(20, 0, 0, 255), image.getColorARGB(1));
		assertEquals(Color4I.toIntARGB(30, 0, 0, 255), image.getColorARGB(2));
		assertEquals(Color4I.toIntARGB(40, 0, 0, 255), image.getColorARGB(3));
	}
	
	@Test
	public void testFlipY() {
		final
		Image image = new Image(2, 2);
		image.setColorARGB(Color4I.toIntARGB(10, 0, 0, 255), 0);
		image.setColorARGB(Color4I.toIntARGB(20, 0, 0, 255), 1);
		image.setColorARGB(Color4I.toIntARGB(30, 0, 0, 255), 2);
		image.setColorARGB(Color4I.toIntARGB(40, 0, 0, 255), 3);
		
		assertEquals(Color4I.toIntARGB(10, 0, 0, 255), image.getColorARGB(0));
		assertEquals(Color4I.toIntARGB(20, 0, 0, 255), image.getColorARGB(1));
		assertEquals(Color4I.toIntARGB(30, 0, 0, 255), image.getColorARGB(2));
		assertEquals(Color4I.toIntARGB(40, 0, 0, 255), image.getColorARGB(3));
		
		image.flipY();
		
		assertFalse(image.undo());
		
		assertEquals(Color4I.toIntARGB(30, 0, 0, 255), image.getColorARGB(0));
		assertEquals(Color4I.toIntARGB(40, 0, 0, 255), image.getColorARGB(1));
		assertEquals(Color4I.toIntARGB(10, 0, 0, 255), image.getColorARGB(2));
		assertEquals(Color4I.toIntARGB(20, 0, 0, 255), image.getColorARGB(3));
		
		image.flipY();
		
		assertFalse(image.undo());
		
		assertEquals(Color4I.toIntARGB(10, 0, 0, 255), image.getColorARGB(0));
		assertEquals(Color4I.toIntARGB(20, 0, 0, 255), image.getColorARGB(1));
		assertEquals(Color4I.toIntARGB(30, 0, 0, 255), image.getColorARGB(2));
		assertEquals(Color4I.toIntARGB(40, 0, 0, 255), image.getColorARGB(3));
		
		image.setChangeHistoryEnabled(true);
		
		image.flipY();
		
		assertEquals(Color4I.toIntARGB(30, 0, 0, 255), image.getColorARGB(0));
		assertEquals(Color4I.toIntARGB(40, 0, 0, 255), image.getColorARGB(1));
		assertEquals(Color4I.toIntARGB(10, 0, 0, 255), image.getColorARGB(2));
		assertEquals(Color4I.toIntARGB(20, 0, 0, 255), image.getColorARGB(3));
		
		assertTrue(image.undo());
		
		assertEquals(Color4I.toIntARGB(10, 0, 0, 255), image.getColorARGB(0));
		assertEquals(Color4I.toIntARGB(20, 0, 0, 255), image.getColorARGB(1));
		assertEquals(Color4I.toIntARGB(30, 0, 0, 255), image.getColorARGB(2));
		assertEquals(Color4I.toIntARGB(40, 0, 0, 255), image.getColorARGB(3));
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
	public void testGetColor3DDoubleDoublePixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor3D(Color3D.RED, 0);
		
		assertEquals(Color3D.RED, image.getColor3D(0.0D, 0.0D, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor3D(0.0D, 0.0D, null));
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
	public void testGetColor3DIntIntPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor3D(Color3D.RED, 0);
		
		assertEquals(Color3D.RED, image.getColor3D(0, 0, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor3D(0, 0, null));
	}
	
	@Test
	public void testGetColor3DIntPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor3D(Color3D.RED, 0);
		
		assertEquals(Color3D.RED, image.getColor3D(0, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor3D(0, null));
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
	public void testGetColor3DPoint2IPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor3D(Color3D.RED, 0);
		
		assertEquals(Color3D.RED, image.getColor3D(new Point2I(0, 0), PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor3D(new Point2I(0, 0), null));
		assertThrows(NullPointerException.class, () -> image.getColor3D(null, PixelTransformer.DEFAULT));
	}
	
	@Test
	public void testGetColor3FFloatFloat() {
		final
		Image image = new Image(1, 1);
		image.setColor3F(Color3F.RED, 0);
		
		assertEquals(Color3F.RED, image.getColor3F(0.0F, 0.0F));
	}
	
	@Test
	public void testGetColor3FFloatFloatPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor3F(Color3F.RED, 0);
		
		assertEquals(Color3F.RED, image.getColor3F(0.0F, 0.0F, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor3F(0.0F, 0.0F, null));
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
	public void testGetColor3FIntIntPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor3F(Color3F.RED, 0);
		
		assertEquals(Color3F.RED, image.getColor3F(0, 0, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor3F(0, 0, null));
	}
	
	@Test
	public void testGetColor3FIntPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor3F(Color3F.RED, 0);
		
		assertEquals(Color3F.RED, image.getColor3F(0, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor3F(0, null));
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
	public void testGetColor3FPoint2IPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor3F(Color3F.RED, 0);
		
		assertEquals(Color3F.RED, image.getColor3F(new Point2I(0, 0), PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor3F(new Point2I(0, 0), null));
		assertThrows(NullPointerException.class, () -> image.getColor3F(null, PixelTransformer.DEFAULT));
	}
	
	@Test
	public void testGetColor3IDoubleDouble() {
		final
		Image image = new Image(1, 1);
		image.setColor3I(Color3I.RED, 0);
		
		assertEquals(Color3I.RED, image.getColor3I(0.0D, 0.0D));
	}
	
	@Test
	public void testGetColor3IDoubleDoublePixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor3I(Color3I.RED, 0);
		
		assertEquals(Color3I.RED, image.getColor3I(0.0D, 0.0D, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor3I(0.0D, 0.0D, null));
	}
	
	@Test
	public void testGetColor3IInt() {
		final
		Image image = new Image(1, 1);
		image.setColor3I(Color3I.RED, 0);
		
		assertEquals(Color3I.RED, image.getColor3I(0));
	}
	
	@Test
	public void testGetColor3IIntInt() {
		final
		Image image = new Image(1, 1);
		image.setColor3I(Color3I.RED, 0);
		
		assertEquals(Color3I.RED, image.getColor3I(0, 0));
	}
	
	@Test
	public void testGetColor3IIntIntPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor3I(Color3I.RED, 0);
		
		assertEquals(Color3I.RED, image.getColor3I(0, 0, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor3I(0, 0, null));
	}
	
	@Test
	public void testGetColor3IIntPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor3I(Color3I.RED, 0);
		
		assertEquals(Color3I.RED, image.getColor3I(0, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor3I(0, null));
	}
	
	@Test
	public void testGetColor3IPoint2I() {
		final
		Image image = new Image(1, 1);
		image.setColor3I(Color3I.RED, 0);
		
		assertEquals(Color3I.RED, image.getColor3I(new Point2I(0, 0)));
		
		assertThrows(NullPointerException.class, () -> image.getColor3I(null));
	}
	
	@Test
	public void testGetColor3IPoint2IPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor3I(Color3I.RED, 0);
		
		assertEquals(Color3I.RED, image.getColor3I(new Point2I(0, 0), PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor3I(new Point2I(0, 0), null));
		assertThrows(NullPointerException.class, () -> image.getColor3I(null, PixelTransformer.DEFAULT));
	}
	
	@Test
	public void testGetColor4DDoubleDouble() {
		final
		Image image = new Image(1, 1);
		image.setColor4D(Color4D.RED, 0);
		
		assertEquals(Color4D.RED, image.getColor4D(0.0D, 0.0D));
	}
	
	@Test
	public void testGetColor4DDoubleDoublePixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor4D(Color4D.RED, 0);
		
		assertEquals(Color4D.RED, image.getColor4D(0.0D, 0.0D, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor4D(0.0D, 0.0D, null));
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
	public void testGetColor4DIntIntPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor4D(Color4D.RED, 0);
		
		assertEquals(Color4D.RED, image.getColor4D(0, 0, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor4D(0, 0, null));
	}
	
	@Test
	public void testGetColor4DIntPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor4D(Color4D.RED, 0);
		
		assertEquals(Color4D.RED, image.getColor4D(0, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor4D(0, null));
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
	public void testGetColor4DPoint2IPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor4D(Color4D.RED, 0);
		
		assertEquals(Color4D.RED, image.getColor4D(new Point2I(0, 0), PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor4D(new Point2I(0, 0), null));
		assertThrows(NullPointerException.class, () -> image.getColor4D(null, PixelTransformer.DEFAULT));
	}
	
	@Test
	public void testGetColor4FFloatFloat() {
		final
		Image image = new Image(1, 1);
		image.setColor4F(Color4F.RED, 0);
		
		assertEquals(Color4F.RED, image.getColor4F(0.0F, 0.0F));
	}
	
	@Test
	public void testGetColor4FFloatFloatPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor4F(Color4F.RED, 0);
		
		assertEquals(Color4F.RED, image.getColor4F(0.0F, 0.0F, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor4F(0.0F, 0.0F, null));
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
	public void testGetColor4FIntIntPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor4F(Color4F.RED, 0);
		
		assertEquals(Color4F.RED, image.getColor4F(0, 0, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor4F(0, 0, null));
	}
	
	@Test
	public void testGetColor4FIntPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor4F(Color4F.RED, 0);
		
		assertEquals(Color4F.RED, image.getColor4F(0, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor4F(0, null));
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
	public void testGetColor4FPoint2IPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor4F(Color4F.RED, 0);
		
		assertEquals(Color4F.RED, image.getColor4F(new Point2I(0, 0), PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor4F(new Point2I(0, 0), null));
		assertThrows(NullPointerException.class, () -> image.getColor4F(null, PixelTransformer.DEFAULT));
	}
	
	@Test
	public void testGetColor4IDoubleDouble() {
		final
		Image image = new Image(1, 1);
		image.setColor4I(Color4I.RED, 0);
		
		assertEquals(Color4I.RED, image.getColor4I(0.0D, 0.0D));
	}
	
	@Test
	public void testGetColor4IDoubleDoublePixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor4I(Color4I.RED, 0);
		
		assertEquals(Color4I.RED, image.getColor4I(0.0D, 0.0D, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor4I(0.0D, 0.0D, null));
	}
	
	@Test
	public void testGetColor4IInt() {
		final
		Image image = new Image(1, 1);
		image.setColor4I(Color4I.RED, 0);
		
		assertEquals(Color4I.RED, image.getColor4I(0));
	}
	
	@Test
	public void testGetColor4IIntInt() {
		final
		Image image = new Image(1, 1);
		image.setColor4I(Color4I.RED, 0);
		
		assertEquals(Color4I.RED, image.getColor4I(0, 0));
	}
	
	@Test
	public void testGetColor4IIntIntPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor4I(Color4I.RED, 0);
		
		assertEquals(Color4I.RED, image.getColor4I(0, 0, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor4I(0, 0, null));
	}
	
	@Test
	public void testGetColor4IIntPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor4I(Color4I.RED, 0);
		
		assertEquals(Color4I.RED, image.getColor4I(0, PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor4I(0, null));
	}
	
	@Test
	public void testGetColor4IPoint2I() {
		final
		Image image = new Image(1, 1);
		image.setColor4I(Color4I.RED, 0);
		
		assertEquals(Color4I.RED, image.getColor4I(new Point2I(0, 0)));
		
		assertThrows(NullPointerException.class, () -> image.getColor4I(null));
	}
	
	@Test
	public void testGetColor4IPoint2IPixelTransformer() {
		final
		Image image = new Image(1, 1);
		image.setColor4I(Color4I.RED, 0);
		
		assertEquals(Color4I.RED, image.getColor4I(new Point2I(0, 0), PixelTransformer.DEFAULT));
		
		assertThrows(NullPointerException.class, () -> image.getColor4I(new Point2I(0, 0), null));
		assertThrows(NullPointerException.class, () -> image.getColor4I(null, PixelTransformer.DEFAULT));
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
	public void testRelativeLuminanceMaxAsDouble() {
		final
		Image image = new Image(2, 2, DataFactory.forColor4D());
		image.setColor4D(new Color4D(1.0D, 1.0D, 1.0D), 0, 0);
		image.setColor4D(new Color4D(2.0D, 2.0D, 2.0D), 1, 0);
		image.setColor4D(new Color4D(3.0D, 3.0D, 3.0D), 0, 1);
		image.setColor4D(new Color4D(4.0D, 4.0D, 4.0D), 1, 1);
		
		assertEquals(new Color4D(4.0D, 4.0D, 4.0D).relativeLuminance(), image.relativeLuminanceMaxAsDouble());
	}
	
	@Test
	public void testRelativeLuminanceMaxAsFloat() {
		final
		Image image = new Image(2, 2, DataFactory.forColor4F());
		image.setColor4F(new Color4F(1.0F, 1.0F, 1.0F), 0, 0);
		image.setColor4F(new Color4F(2.0F, 2.0F, 2.0F), 1, 0);
		image.setColor4F(new Color4F(3.0F, 3.0F, 3.0F), 0, 1);
		image.setColor4F(new Color4F(4.0F, 4.0F, 4.0F), 1, 1);
		
		assertEquals(new Color4F(4.0F, 4.0F, 4.0F).relativeLuminance(), image.relativeLuminanceMaxAsFloat());
	}
	
	@Test
	public void testRelativeLuminanceMinAsDouble() {
		final
		Image image = new Image(2, 2, DataFactory.forColor4D());
		image.setColor4D(new Color4D(1.0D, 1.0D, 1.0D), 0, 0);
		image.setColor4D(new Color4D(2.0D, 2.0D, 2.0D), 1, 0);
		image.setColor4D(new Color4D(3.0D, 3.0D, 3.0D), 0, 1);
		image.setColor4D(new Color4D(4.0D, 4.0D, 4.0D), 1, 1);
		
		assertEquals(new Color4D(1.0D, 1.0D, 1.0D).relativeLuminance(), image.relativeLuminanceMinAsDouble());
	}
	
	@Test
	public void testRelativeLuminanceMinAsFloat() {
		final
		Image image = new Image(2, 2, DataFactory.forColor4F());
		image.setColor4F(new Color4F(1.0F, 1.0F, 1.0F), 0, 0);
		image.setColor4F(new Color4F(2.0F, 2.0F, 2.0F), 1, 0);
		image.setColor4F(new Color4F(3.0F, 3.0F, 3.0F), 0, 1);
		image.setColor4F(new Color4F(4.0F, 4.0F, 4.0F), 1, 1);
		
		assertEquals(new Color4F(1.0F, 1.0F, 1.0F).relativeLuminance(), image.relativeLuminanceMinAsFloat());
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
	public void testSetColor3IColor3IInt() {
		final
		Image image = new Image(1, 1);
		image.setColor3I(Color3I.RED, 0);
		
		assertEquals(Color3I.RED, image.getColor3I(0));
		
		assertThrows(NullPointerException.class, () -> image.setColor3I(null, 0));
	}
	
	@Test
	public void testSetColor3IColor3IIntInt() {
		final
		Image image = new Image(1, 1);
		image.setColor3I(Color3I.RED, 0, 0);
		
		assertEquals(Color3I.RED, image.getColor3I(0, 0));
		
		assertThrows(NullPointerException.class, () -> image.setColor3I(null, 0, 0));
	}
	
	@Test
	public void testSetColor3IColor3IPoint2I() {
		final
		Image image = new Image(1, 1);
		image.setColor3I(Color3I.RED, new Point2I(0, 0));
		
		assertEquals(Color3I.RED, image.getColor3I(new Point2I(0, 0)));
		
		assertThrows(NullPointerException.class, () -> image.setColor3I(Color3I.RED, null));
		assertThrows(NullPointerException.class, () -> image.setColor3I(null, new Point2I(0, 0)));
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
	public void testSetColor4IColor4IInt() {
		final
		Image image = new Image(1, 1);
		image.setColor4I(Color4I.RED, 0);
		
		assertEquals(Color4I.RED, image.getColor4I(0));
		
		assertThrows(NullPointerException.class, () -> image.setColor4I(null, 0));
	}
	
	@Test
	public void testSetColor4IColor4IIntInt() {
		final
		Image image = new Image(1, 1);
		image.setColor4I(Color4I.RED, 0, 0);
		
		assertEquals(Color4I.RED, image.getColor4I(0, 0));
		
		assertThrows(NullPointerException.class, () -> image.setColor4I(null, 0, 0));
	}
	
	@Test
	public void testSetColor4IColor4IPoint2I() {
		final
		Image image = new Image(1, 1);
		image.setColor4I(Color4I.RED, new Point2I(0, 0));
		
		assertEquals(Color4I.RED, image.getColor4I(new Point2I(0, 0)));
		
		assertThrows(NullPointerException.class, () -> image.setColor4I(Color4I.RED, null));
		assertThrows(NullPointerException.class, () -> image.setColor4I(null, new Point2I(0, 0)));
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
	public void testToDoubleArray() {
		final
		Image image = new Image(2, 2, DataFactory.forColor4D());
		image.setColor4D(new Color4D(10.0D, 11.0D, 12.0D, 13.0D), 0, 0);
		image.setColor4D(new Color4D(20.0D, 21.0D, 22.0D, 23.0D), 1, 0);
		image.setColor4D(new Color4D(30.0D, 31.0D, 32.0D, 33.0D), 0, 1);
		image.setColor4D(new Color4D(40.0D, 41.0D, 42.0D, 43.0D), 1, 1);
		
		final double[] arrayRGBA = image.toDoubleArray();
		
		assertEquals(16, arrayRGBA.length);
		
		assertEquals(10.0D, arrayRGBA[ 0]);
		assertEquals(11.0D, arrayRGBA[ 1]);
		assertEquals(12.0D, arrayRGBA[ 2]);
		assertEquals(13.0D, arrayRGBA[ 3]);
		assertEquals(20.0D, arrayRGBA[ 4]);
		assertEquals(21.0D, arrayRGBA[ 5]);
		assertEquals(22.0D, arrayRGBA[ 6]);
		assertEquals(23.0D, arrayRGBA[ 7]);
		assertEquals(30.0D, arrayRGBA[ 8]);
		assertEquals(31.0D, arrayRGBA[ 9]);
		assertEquals(32.0D, arrayRGBA[10]);
		assertEquals(33.0D, arrayRGBA[11]);
		assertEquals(40.0D, arrayRGBA[12]);
		assertEquals(41.0D, arrayRGBA[13]);
		assertEquals(42.0D, arrayRGBA[14]);
		assertEquals(43.0D, arrayRGBA[15]);
	}
	
	@Test
	public void testToDoubleArrayArrayComponentOrder() {
		final
		Image image = new Image(2, 2, DataFactory.forColor4D());
		image.setColor4D(new Color4D(10.0D, 11.0D, 12.0D, 13.0D), 0, 0);
		image.setColor4D(new Color4D(20.0D, 21.0D, 22.0D, 23.0D), 1, 0);
		image.setColor4D(new Color4D(30.0D, 31.0D, 32.0D, 33.0D), 0, 1);
		image.setColor4D(new Color4D(40.0D, 41.0D, 42.0D, 43.0D), 1, 1);
		
		final double[] arrayRGB = image.toDoubleArray(ArrayComponentOrder.RGB);
		final double[] arrayRGBA = image.toDoubleArray(ArrayComponentOrder.RGBA);
		
		assertEquals(12, arrayRGB.length);
		assertEquals(16, arrayRGBA.length);
		
		assertEquals(10.0D, arrayRGB[ 0]);
		assertEquals(11.0D, arrayRGB[ 1]);
		assertEquals(12.0D, arrayRGB[ 2]);
		assertEquals(20.0D, arrayRGB[ 3]);
		assertEquals(21.0D, arrayRGB[ 4]);
		assertEquals(22.0D, arrayRGB[ 5]);
		assertEquals(30.0D, arrayRGB[ 6]);
		assertEquals(31.0D, arrayRGB[ 7]);
		assertEquals(32.0D, arrayRGB[ 8]);
		assertEquals(40.0D, arrayRGB[ 9]);
		assertEquals(41.0D, arrayRGB[10]);
		assertEquals(42.0D, arrayRGB[11]);
		
		assertEquals(10.0D, arrayRGBA[ 0]);
		assertEquals(11.0D, arrayRGBA[ 1]);
		assertEquals(12.0D, arrayRGBA[ 2]);
		assertEquals(13.0D, arrayRGBA[ 3]);
		assertEquals(20.0D, arrayRGBA[ 4]);
		assertEquals(21.0D, arrayRGBA[ 5]);
		assertEquals(22.0D, arrayRGBA[ 6]);
		assertEquals(23.0D, arrayRGBA[ 7]);
		assertEquals(30.0D, arrayRGBA[ 8]);
		assertEquals(31.0D, arrayRGBA[ 9]);
		assertEquals(32.0D, arrayRGBA[10]);
		assertEquals(33.0D, arrayRGBA[11]);
		assertEquals(40.0D, arrayRGBA[12]);
		assertEquals(41.0D, arrayRGBA[13]);
		assertEquals(42.0D, arrayRGBA[14]);
		assertEquals(43.0D, arrayRGBA[15]);
		
		assertThrows(NullPointerException.class, () -> image.toDoubleArray(null));
	}
	
	@Test
	public void testToFloatArray() {
		final
		Image image = new Image(2, 2, DataFactory.forColor4F());
		image.setColor4F(new Color4F(10.0F, 11.0F, 12.0F, 13.0F), 0, 0);
		image.setColor4F(new Color4F(20.0F, 21.0F, 22.0F, 23.0F), 1, 0);
		image.setColor4F(new Color4F(30.0F, 31.0F, 32.0F, 33.0F), 0, 1);
		image.setColor4F(new Color4F(40.0F, 41.0F, 42.0F, 43.0F), 1, 1);
		
		final float[] arrayRGBA = image.toFloatArray();
		
		assertEquals(16, arrayRGBA.length);
		
		assertEquals(10.0F, arrayRGBA[ 0]);
		assertEquals(11.0F, arrayRGBA[ 1]);
		assertEquals(12.0F, arrayRGBA[ 2]);
		assertEquals(13.0F, arrayRGBA[ 3]);
		assertEquals(20.0F, arrayRGBA[ 4]);
		assertEquals(21.0F, arrayRGBA[ 5]);
		assertEquals(22.0F, arrayRGBA[ 6]);
		assertEquals(23.0F, arrayRGBA[ 7]);
		assertEquals(30.0F, arrayRGBA[ 8]);
		assertEquals(31.0F, arrayRGBA[ 9]);
		assertEquals(32.0F, arrayRGBA[10]);
		assertEquals(33.0F, arrayRGBA[11]);
		assertEquals(40.0F, arrayRGBA[12]);
		assertEquals(41.0F, arrayRGBA[13]);
		assertEquals(42.0F, arrayRGBA[14]);
		assertEquals(43.0F, arrayRGBA[15]);
	}
	
	@Test
	public void testToFloatArrayArrayComponentOrder() {
		final
		Image image = new Image(2, 2, DataFactory.forColor4F());
		image.setColor4F(new Color4F(10.0F, 11.0F, 12.0F, 13.0F), 0, 0);
		image.setColor4F(new Color4F(20.0F, 21.0F, 22.0F, 23.0F), 1, 0);
		image.setColor4F(new Color4F(30.0F, 31.0F, 32.0F, 33.0F), 0, 1);
		image.setColor4F(new Color4F(40.0F, 41.0F, 42.0F, 43.0F), 1, 1);
		
		final float[] arrayRGB = image.toFloatArray(ArrayComponentOrder.RGB);
		final float[] arrayRGBA = image.toFloatArray(ArrayComponentOrder.RGBA);
		
		assertEquals(12, arrayRGB.length);
		assertEquals(16, arrayRGBA.length);
		
		assertEquals(10.0F, arrayRGB[ 0]);
		assertEquals(11.0F, arrayRGB[ 1]);
		assertEquals(12.0F, arrayRGB[ 2]);
		assertEquals(20.0F, arrayRGB[ 3]);
		assertEquals(21.0F, arrayRGB[ 4]);
		assertEquals(22.0F, arrayRGB[ 5]);
		assertEquals(30.0F, arrayRGB[ 6]);
		assertEquals(31.0F, arrayRGB[ 7]);
		assertEquals(32.0F, arrayRGB[ 8]);
		assertEquals(40.0F, arrayRGB[ 9]);
		assertEquals(41.0F, arrayRGB[10]);
		assertEquals(42.0F, arrayRGB[11]);
		
		assertEquals(10.0F, arrayRGBA[ 0]);
		assertEquals(11.0F, arrayRGBA[ 1]);
		assertEquals(12.0F, arrayRGBA[ 2]);
		assertEquals(13.0F, arrayRGBA[ 3]);
		assertEquals(20.0F, arrayRGBA[ 4]);
		assertEquals(21.0F, arrayRGBA[ 5]);
		assertEquals(22.0F, arrayRGBA[ 6]);
		assertEquals(23.0F, arrayRGBA[ 7]);
		assertEquals(30.0F, arrayRGBA[ 8]);
		assertEquals(31.0F, arrayRGBA[ 9]);
		assertEquals(32.0F, arrayRGBA[10]);
		assertEquals(33.0F, arrayRGBA[11]);
		assertEquals(40.0F, arrayRGBA[12]);
		assertEquals(41.0F, arrayRGBA[13]);
		assertEquals(42.0F, arrayRGBA[14]);
		assertEquals(43.0F, arrayRGBA[15]);
		
		assertThrows(NullPointerException.class, () -> image.toFloatArray(null));
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