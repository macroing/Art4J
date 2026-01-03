/**
 * Copyright 2022 - 2026 J&#246;rgen Lundgren
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
package org.macroing.art4j.pixel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.macroing.art4j.color.Color3F;
import org.macroing.art4j.color.Color4F;
import org.macroing.art4j.color.ColorSpaceF;
import org.macroing.geo4j.common.Point2I;
import org.macroing.geo4j.shape.rectangle.Rectangle2I;

@SuppressWarnings("static-method")
public final class Color4FPixelOperatorUnitTests {
	public Color4FPixelOperatorUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testGradient() {
		final Color4F a = new Color4F(0.5F, 0.5F, 0.5F, 1.0F);
		final Color4F b = Color4FPixelOperator.gradient(new Color3F(0.0F, 0.0F, 0.0F), new Color3F(1.0F, 1.0F, 1.0F), new Color3F(0.0F, 0.0F, 0.0F), new Color3F(1.0F, 1.0F, 1.0F), new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2))).apply(a, 1, 1);
		
		assertEquals(0.5F, b.r);
		assertEquals(0.5F, b.g);
		assertEquals(0.5F, b.b);
		assertEquals(1.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.gradient(new Color3F(0.0F, 0.0F, 0.0F), new Color3F(1.0F, 1.0F, 1.0F), new Color3F(0.0F, 0.0F, 0.0F), new Color3F(1.0F, 1.0F, 1.0F), new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2))).apply(null, 1, 1));
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.gradient(new Color3F(0.0F, 0.0F, 0.0F), new Color3F(1.0F, 1.0F, 1.0F), new Color3F(0.0F, 0.0F, 0.0F), new Color3F(1.0F, 1.0F, 1.0F), null));
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.gradient(new Color3F(0.0F, 0.0F, 0.0F), new Color3F(1.0F, 1.0F, 1.0F), new Color3F(0.0F, 0.0F, 0.0F), null, new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2))));
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.gradient(new Color3F(0.0F, 0.0F, 0.0F), new Color3F(1.0F, 1.0F, 1.0F), null, new Color3F(1.0F, 1.0F, 1.0F), new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2))));
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.gradient(new Color3F(0.0F, 0.0F, 0.0F), null, new Color3F(0.0F, 0.0F, 0.0F), new Color3F(1.0F, 1.0F, 1.0F), new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2))));
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.gradient(null, new Color3F(1.0F, 1.0F, 1.0F), new Color3F(0.0F, 0.0F, 0.0F), new Color3F(1.0F, 1.0F, 1.0F), new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2))));
	}
	
	@Test
	public void testGrayscaleA() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.grayscaleA().apply(a, 0, 0);
		
		assertEquals(4.0F, b.r);
		assertEquals(4.0F, b.g);
		assertEquals(4.0F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.grayscaleA().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleAverage() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.grayscaleAverage().apply(a, 0, 0);
		
		assertEquals(2.0F, b.r);
		assertEquals(2.0F, b.g);
		assertEquals(2.0F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.grayscaleAverage().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleB() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.grayscaleB().apply(a, 0, 0);
		
		assertEquals(3.0F, b.r);
		assertEquals(3.0F, b.g);
		assertEquals(3.0F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.grayscaleB().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleG() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.grayscaleG().apply(a, 0, 0);
		
		assertEquals(2.0F, b.r);
		assertEquals(2.0F, b.g);
		assertEquals(2.0F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.grayscaleG().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleLightness() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.grayscaleLightness().apply(a, 0, 0);
		
		assertEquals(2.0F, b.r);
		assertEquals(2.0F, b.g);
		assertEquals(2.0F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.grayscaleLightness().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleMax() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.grayscaleMax().apply(a, 0, 0);
		
		assertEquals(3.0F, b.r);
		assertEquals(3.0F, b.g);
		assertEquals(3.0F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.grayscaleMax().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleMin() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.grayscaleMin().apply(a, 0, 0);
		
		assertEquals(1.0F, b.r);
		assertEquals(1.0F, b.g);
		assertEquals(1.0F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.grayscaleMin().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleR() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.grayscaleR().apply(a, 0, 0);
		
		assertEquals(1.0F, b.r);
		assertEquals(1.0F, b.g);
		assertEquals(1.0F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.grayscaleR().apply(null, 0, 0));
	}
	
	@Test
	public void testGrayscaleRelativeLuminance() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.grayscaleRelativeLuminance().apply(a, 0, 0);
		
		assertEquals(1.0F * 0.212671F + 2.0F * 0.715160F + 3.0F * 0.072169F, b.r);
		assertEquals(1.0F * 0.212671F + 2.0F * 0.715160F + 3.0F * 0.072169F, b.g);
		assertEquals(1.0F * 0.212671F + 2.0F * 0.715160F + 3.0F * 0.072169F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.grayscaleRelativeLuminance().apply(null, 0, 0));
	}
	
	@Test
	public void testInvert() {
		final Color4F a = new Color4F(1.0F, 1.0F, 1.0F, 1.0F);
		final Color4F b = Color4FPixelOperator.invert().apply(a, 0, 0);
		
		assertEquals(0.0F, b.r);
		assertEquals(0.0F, b.g);
		assertEquals(0.0F, b.b);
		assertEquals(1.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.invert().apply(null, 0, 0));
	}
	
	@Test
	public void testRedoGammaCorrection() {
		final Color4F a = new Color4F(1.0F, 1.0F, 1.0F, 1.0F);
		final Color4F b = Color4FPixelOperator.redoGammaCorrection().apply(a, 0, 0);
		
		assertEquals(1.0F, b.r);
		assertEquals(1.0F, b.g);
		assertEquals(1.0F, b.b);
		assertEquals(1.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.redoGammaCorrection().apply(null, 0, 0));
	}
	
	@Test
	public void testRedoGammaCorrectionColorSpaceF() {
		final Color4F a = new Color4F(1.0F, 1.0F, 1.0F, 1.0F);
		final Color4F b = Color4FPixelOperator.redoGammaCorrection(ColorSpaceF.getDefault()).apply(a, 0, 0);
		
		assertEquals(1.0F, b.r);
		assertEquals(1.0F, b.g);
		assertEquals(1.0F, b.b);
		assertEquals(1.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.redoGammaCorrection(ColorSpaceF.getDefault()).apply(null, 0, 0));
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.redoGammaCorrection(null));
	}
	
	@Test
	public void testSepia() {
		final Color4F a = new Color4F(1.0F, 2.0F, 3.0F, 4.0F);
		final Color4F b = Color4FPixelOperator.sepia().apply(a, 0, 0);
		
		assertEquals(1.0F * 0.393F + 2.0F * 0.769F + 3.0F * 0.189F, b.r);
		assertEquals(1.0F * 0.349F + 2.0F * 0.686F + 3.0F * 0.168F, b.g);
		assertEquals(1.0F * 0.272F + 2.0F * 0.534F + 3.0F * 0.131F, b.b);
		assertEquals(4.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.sepia().apply(null, 0, 0));
	}
	
	@Test
	public void testSimplexFractionalBrownianMotionColor3FRectangle2I() {
		final Color4F a = new Color4F(0.5F, 0.5F, 0.5F, 0.5F);
		final Color4F b = Color4FPixelOperator.simplexFractionalBrownianMotion(new Color3F(0.5F, 0.5F, 0.5F), new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2))).apply(a, 1, 1);
		
		assertTrue(b.isGrayscale());
		
		assertTrue(b.r >= 0.0F && b.r <= 1.0F);
		assertTrue(b.g >= 0.0F && b.g <= 1.0F);
		assertTrue(b.b >= 0.0F && b.b <= 1.0F);
		
		assertEquals(0.5F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.simplexFractionalBrownianMotion(new Color3F(0.5F, 0.5F, 0.5F), new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2))).apply(null, 1, 1));
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.simplexFractionalBrownianMotion(new Color3F(0.5F, 0.5F, 0.5F), null));
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.simplexFractionalBrownianMotion(null, new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2))));
	}
	
	@Test
	public void testSimplexFractionalBrownianMotionColor3FRectangle2IFloatFloatInt() {
		final Color4F a = new Color4F(0.5F, 0.5F, 0.5F, 0.5F);
		final Color4F b = Color4FPixelOperator.simplexFractionalBrownianMotion(new Color3F(0.5F, 0.5F, 0.5F), new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), 5.0F, 0.5F, 16).apply(a, 1, 1);
		
		assertTrue(b.isGrayscale());
		
		assertTrue(b.r >= 0.0F && b.r <= 1.0F);
		assertTrue(b.g >= 0.0F && b.g <= 1.0F);
		assertTrue(b.b >= 0.0F && b.b <= 1.0F);
		
		assertEquals(0.5F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.simplexFractionalBrownianMotion(new Color3F(0.5F, 0.5F, 0.5F), new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), 5.0F, 0.5F, 16).apply(null, 1, 1));
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.simplexFractionalBrownianMotion(new Color3F(0.5F, 0.5F, 0.5F), null, 5.0F, 0.5F, 16));
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.simplexFractionalBrownianMotion(null, new Rectangle2I(new Point2I(0, 0), new Point2I(2, 2)), 5.0F, 0.5F, 16));
	}
	
	@Test
	public void testToneMap() {
		final Color4F a = new Color4F(1.0F, 1.0F, 1.0F, 1.0F);
		final Color4F b = Color4FPixelOperator.toneMap(1.0F).apply(a, 0, 0);
		
		assertEquals(1.0F, b.r);
		assertEquals(1.0F, b.g);
		assertEquals(1.0F, b.b);
		assertEquals(1.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.toneMap(1.0F).apply(null, 0, 0));
	}
	
	@Test
	public void testUndoGammaCorrection() {
		final Color4F a = new Color4F(1.0F, 1.0F, 1.0F, 1.0F);
		final Color4F b = Color4FPixelOperator.undoGammaCorrection().apply(a, 0, 0);
		
		assertEquals(1.0F, b.r);
		assertEquals(1.0F, b.g);
		assertEquals(1.0F, b.b);
		assertEquals(1.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.undoGammaCorrection().apply(null, 0, 0));
	}
	
	@Test
	public void testUndoGammaCorrectionColorSpaceF() {
		final Color4F a = new Color4F(1.0F, 1.0F, 1.0F, 1.0F);
		final Color4F b = Color4FPixelOperator.undoGammaCorrection(ColorSpaceF.getDefault()).apply(a, 0, 0);
		
		assertEquals(1.0F, b.r);
		assertEquals(1.0F, b.g);
		assertEquals(1.0F, b.b);
		assertEquals(1.0F, b.a);
		
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.undoGammaCorrection(ColorSpaceF.getDefault()).apply(null, 0, 0));
		assertThrows(NullPointerException.class, () -> Color4FPixelOperator.undoGammaCorrection(null));
	}
}