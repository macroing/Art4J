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

public final class Examples {
	private static final Image IMAGE = doCreateImage();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Examples() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
		doBlendOver();
		doConvolve();
		doDraw();
		doFillBlend();
		doFillInvert();
		doFillSepia();
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
		} catch(final MalformedURLException | UncheckedIOException e) {
			return new Image(1, 1);
		}
	}
	
	private static void doBlendOver() {
		final
		Image image = IMAGE.copy();
		image.fill((final Color4D color, final int x, final int y) -> new Color4D(color.r, color.g, color.b, 0.5D));
		image.fillImage(Color4DBiPixelOperator.blendOver(), new Image(image.getResolutionX(), image.getResolutionY()).fillGradient(Color3D.BLACK, Color3D.RED, Color3D.GREEN, Color3D.BLUE), image.getBounds(), image.getBounds());
		image.save("./generated/example/BlendOver.png");
	}
	
	private static void doConvolve() {
		final
		Image image = IMAGE.copy();
		image.convolve(ConvolutionKernelND.GAUSSIAN_BLUR_5);
		image.save("./generated/example/Convolve.png");
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
		image.fill((final Color4D color, final int x, final int y) -> Color4D.blend(color, new Color4D(0.5D, 0.0D, 0.0D, 1.0D)));
		image.save("./generated/example/FillBlend.png");
	}
	
	private static void doFillInvert() {
		final
		Image image = IMAGE.copy();
		image.fill(Color4DPixelOperator.invert());
		image.save("./generated/example/FillInvert.png");
	}
	
	private static void doFillSepia() {
		final
		Image image = IMAGE.copy();
		image.fill(Color4DPixelOperator.sepia());
		image.save("./generated/example/FillSepia.png");
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