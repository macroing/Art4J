/**
 * Copyright 2022 - 2023 J&#246;rgen Lundgren
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
package org.macroing.art4j.example;

import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.macroing.art4j.color.Color3D;
import org.macroing.art4j.color.Color4D;
import org.macroing.art4j.data.DataFactory;
import org.macroing.art4j.image.Image;
import org.macroing.art4j.kernel.ConvolutionKernelND;
import org.macroing.art4j.pixel.Color4DBiPixelOperator;
import org.macroing.art4j.pixel.Color4DPixelOperator;
import org.macroing.geo4j.common.Point2I;
import org.macroing.geo4j.shape.circle.Circle2I;
import org.macroing.geo4j.shape.rectangle.Rectangle2I;

public final class Examples {
	private static final Image IMAGE = doCreateImage();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Examples() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
		doBlendOver();
		doConvolve();
		doCopyShape2I();
		doDraw();
		doFillBlend();
		doFillGradient();
		doFillGrayscaleA();
		doFillGrayscaleAverage();
		doFillGrayscaleB();
		doFillGrayscaleG();
		doFillGrayscaleLightness();
		doFillGrayscaleMax();
		doFillGrayscaleMin();
		doFillGrayscaleR();
		doFillGrayscaleRelativeLuminance();
		doFillInvert();
		doFillRedoGammaCorrection();
		doFillSepia();
		doFillShape();
		doFillShapeComplement();
		doFillSimplexFractionalBrownianMotion();
		doFillSobel();
		doFillToneMap();
		doFillUndoGammaCorrection();
		doFlipX();
		doFlipY();
		doRotate();
		doSave();
		doScaleDown();
		doScaleUp();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Image doCreateImage() {
		try {
			return new Image(new URL("https://upload.wikimedia.org/wikipedia/en/7/7d/Lenna_%28test_image%29.png"), DataFactory.forColor4D());
		} catch(@SuppressWarnings("unused") final MalformedURLException | UncheckedIOException e) {
			return new Image(1, 1);
		}
	}
	
	private static void doBlendOver() {
		final
		Image image = IMAGE.copy();
		image.fillD((color, x, y) -> new Color4D(color.r, color.g, color.b, 0.5D));
		image.fillImageD(Color4DBiPixelOperator.blendOver(), new Image(image.getResolutionX(), image.getResolutionY()).fillD(Color4DPixelOperator.gradient(Color3D.BLACK, Color3D.RED, Color3D.GREEN, Color3D.BLUE, image.getBounds())), image.getBounds(), image.getBounds());
		image.save("./generated/example/BlendOver.png");
	}
	
	private static void doConvolve() {
		final
		Image image = IMAGE.copy();
		image.convolve(ConvolutionKernelND.GAUSSIAN_BLUR_5);
		image.save("./generated/example/Convolve.png");
	}
	
	private static void doCopyShape2I() {
		final
		Image image = IMAGE.copy(new Circle2I(new Point2I(IMAGE.getResolutionX() / 2, IMAGE.getResolutionY() / 2), 200));
		image.save("./generated/example/CopyShape2I.png");
	}
	
	private static void doDraw() {
		final
		Image image = IMAGE.copy();
		image.draw(g -> g.drawString("Hello, World!", image.getResolutionX() / 2, image.getResolutionY() / 2));
		image.save("./generated/example/Draw.png");
	}
	
	private static void doFillBlend() {
		final
		Image image = IMAGE.copy();
		image.fillD((color, x, y) -> Color4D.blend(color, new Color4D(0.5D, 0.0D, 0.0D, 1.0D)));
		image.save("./generated/example/FillBlend.png");
	}
	
	private static void doFillGradient() {
		final
		Image image = new Image(800, 800);
		image.fillD(Color4DPixelOperator.gradient(Color3D.BLACK, Color3D.RED, Color3D.GREEN, Color3D.YELLOW, new Rectangle2I(new Point2I(0, 0), new Point2I(799, 799))));
		image.save("./generated/example/FillGradient.png");
	}
	
	private static void doFillGrayscaleA() {
		final
		Image image = IMAGE.copy();
		image.fillD(Color4DPixelOperator.grayscaleA());
		image.save("./generated/example/FillGrayscaleA.png");
	}
	
	private static void doFillGrayscaleAverage() {
		final
		Image image = IMAGE.copy();
		image.fillD(Color4DPixelOperator.grayscaleAverage());
		image.save("./generated/example/FillGrayscaleAverage.png");
	}
	
	private static void doFillGrayscaleB() {
		final
		Image image = IMAGE.copy();
		image.fillD(Color4DPixelOperator.grayscaleB());
		image.save("./generated/example/FillGrayscaleB.png");
	}
	
	private static void doFillGrayscaleG() {
		final
		Image image = IMAGE.copy();
		image.fillD(Color4DPixelOperator.grayscaleG());
		image.save("./generated/example/FillGrayscaleG.png");
	}
	
	private static void doFillGrayscaleLightness() {
		final
		Image image = IMAGE.copy();
		image.fillD(Color4DPixelOperator.grayscaleLightness());
		image.save("./generated/example/FillGrayscaleLightness.png");
	}
	
	private static void doFillGrayscaleMax() {
		final
		Image image = IMAGE.copy();
		image.fillD(Color4DPixelOperator.grayscaleMax());
		image.save("./generated/example/FillGrayscaleMax.png");
	}
	
	private static void doFillGrayscaleMin() {
		final
		Image image = IMAGE.copy();
		image.fillD(Color4DPixelOperator.grayscaleMin());
		image.save("./generated/example/FillGrayscaleMin.png");
	}
	
	private static void doFillGrayscaleR() {
		final
		Image image = IMAGE.copy();
		image.fillD(Color4DPixelOperator.grayscaleR());
		image.save("./generated/example/FillGrayscaleR.png");
	}
	
	private static void doFillGrayscaleRelativeLuminance() {
		final
		Image image = IMAGE.copy();
		image.fillD(Color4DPixelOperator.grayscaleRelativeLuminance());
		image.save("./generated/example/FillGrayscaleRelativeLuminance.png");
	}
	
	private static void doFillInvert() {
		final
		Image image = IMAGE.copy();
		image.fillD(Color4DPixelOperator.invert());
		image.save("./generated/example/FillInvert.png");
	}
	
	private static void doFillRedoGammaCorrection() {
		final
		Image image = IMAGE.copy();
		image.fillD(Color4DPixelOperator.redoGammaCorrection());
		image.save("./generated/example/FillRedoGammaCorrection.png");
	}
	
	private static void doFillSepia() {
		final
		Image image = IMAGE.copy();
		image.fillD(Color4DPixelOperator.sepia());
		image.save("./generated/example/FillSepia.png");
	}
	
	private static void doFillShape() {
		final
		Image image = IMAGE.copy();
		image.fillShapeD(new Rectangle2I(new Point2I(100, 100), new Point2I(300, 300)), Color4DPixelOperator.invert());
		image.save("./generated/example/FillShape.png");
	}
	
	private static void doFillShapeComplement() {
		final
		Image image = new Image(1024, 768);
		image.fillShapeComplementD(new Rectangle2I(new Point2I(100, 100), new Point2I(300, 300)), Color4D.RED, true);
		image.save("./generated/example/FillShapeComplement.png");
	}
	
	private static void doFillSimplexFractionalBrownianMotion() {
		final
		Image image = new Image(1024, 768);
		image.fillD(Color4DPixelOperator.simplexFractionalBrownianMotion(new Color3D(0.75D, 0.5D, 0.75D), new Rectangle2I(new Point2I(0, 0), new Point2I(1023, 767))));
		image.save("./generated/example/FillSimplexFractionalBrownianMotion.png");
	}
	
	private static void doFillSobel() {
		final
		Image image = IMAGE.copy();
		image.fillSobelD((color, x, y) -> true);
		image.save("./generated/example/FillSobel.png");
	}
	
	private static void doFillToneMap() {
		final
		Image image = IMAGE.copy();
		image.fillD(Color4DPixelOperator.toneMap(0.75D));
		image.save("./generated/example/FillToneMap.png");
	}
	
	private static void doFillUndoGammaCorrection() {
		final
		Image image = IMAGE.copy();
		image.fillD(Color4DPixelOperator.undoGammaCorrection());
		image.save("./generated/example/FillUndoGammaCorrection.png");
	}
	
	private static void doFlipX() {
		final
		Image image = IMAGE.copy();
		image.flipX();
		image.save("./generated/example/FlipX.png");
	}
	
	private static void doFlipY() {
		final
		Image image = IMAGE.copy();
		image.flipY();
		image.save("./generated/example/FlipY.png");
	}
	
	private static void doRotate() {
		final
		Image image = IMAGE.copy();
		image.rotate(45.0D);
		image.save("./generated/example/Rotate.png");
	}
	
	private static void doSave() {
		final
		Image image = IMAGE.copy();
		image.save("./generated/example/Original.png");
	}
	
	private static void doScaleDown() {
		final
		Image image = IMAGE.copy();
		image.scale(0.5D, 0.5D);
		image.save("./generated/example/ScaleDown.png");
	}
	
	private static void doScaleUp() {
		final
		Image image = IMAGE.copy();
		image.scale(2.0D, 2.0D);
		image.save("./generated/example/ScaleUp.png");
	}
}