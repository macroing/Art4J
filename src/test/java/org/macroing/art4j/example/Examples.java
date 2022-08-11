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

import org.macroing.art4j.color.Color4D;
import org.macroing.art4j.data.DataFactory;
import org.macroing.art4j.image.Image;
import org.macroing.art4j.kernel.ConvolutionKernelND;

public final class Examples {
	private static final Image IMAGE = doCreateImage();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Examples() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
		doConvolve();
		doFillBlend();
		doFillInvert();
		doFillSepia();
		doRotate();
		doSave();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Image doCreateImage() {
		try {
			return new Image(new URL("https://upload.wikimedia.org/wikipedia/en/7/7d/Lenna_%28test_image%29.png"), DataFactory.forColor4D());
		} catch(final MalformedURLException | UncheckedIOException e) {
			return new Image(1, 1);
		}
	}
	
	private static void doConvolve() {
		final
		Image image = IMAGE.copy();
		image.convolveColor4D(ConvolutionKernelND.GAUSSIAN_BLUR_5);
		image.save("./generated/example/Convolve.png");
	}
	
	private static void doFillBlend() {
		final
		Image image = IMAGE.copy();
		image.fillColor4D((color, point) -> Color4D.blend(color, new Color4D(0.5D, 0.0D, 0.0D, 1.0D)));
		image.save("./generated/example/FillBlend.png");
	}
	
	private static void doFillInvert() {
		final
		Image image = IMAGE.copy();
		image.fillColor4D((color, point) -> Color4D.invert(color));
		image.save("./generated/example/FillInvert.png");
	}
	
	private static void doFillSepia() {
		final
		Image image = IMAGE.copy();
		image.fillColor4D((color, point) -> Color4D.sepia(color));
		image.save("./generated/example/FillSepia.png");
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
}