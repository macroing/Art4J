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
package org.macroing.img4j.test;

import org.macroing.img4j.color.Color3D;
import org.macroing.img4j.color.Color4D;
import org.macroing.img4j.color.ColorSpaceD;
import org.macroing.img4j.data.DataFactory;
import org.macroing.img4j.filter.Filter2D;
import org.macroing.img4j.filter.GaussianFilter2D;
import org.macroing.img4j.image.Image;
import org.macroing.java.util.Randoms;

public final class FilterTest {
	private FilterTest() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
		final long millisA = System.currentTimeMillis();
		
		final Image imageOriginal = new Image("./generated/Image-Original.jpg");
		final Image imageRGB = new Image(imageOriginal.getResolutionX(), imageOriginal.getResolutionY(), Color4D.TRANSPARENT, DataFactory.forColor4D());
		final Image imageXYZ = new Image(imageOriginal.getResolutionX(), imageOriginal.getResolutionY(), Color4D.TRANSPARENT, DataFactory.forColor4D());
		
		final ColorSpaceD colorSpace = ColorSpaceD.getDefault();
		
//		final Filter2D filter = new BoxFilter2D();
		final Filter2D filter = new GaussianFilter2D();
		
		final int resolutionX = imageOriginal.getResolutionX();
		final int resolutionY = imageOriginal.getResolutionY();
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++) {
				final Color3D colorRGB = colorSpace.undoGammaCorrection(imageOriginal.getColor3D(x, y));
				final Color3D colorXYZ = colorSpace.convertRGBToXYZ(colorRGB);
				
				final double rX = x + Randoms.nextDouble() - 0.5D;
				final double rY = y + Randoms.nextDouble() - 0.5D;
				
				imageXYZ.sampleColorXYZ(colorXYZ, rX, rY, filter);
			}
		}
		
		imageRGB.sampleRenderColor3D(imageXYZ);
		
		final long millisB = System.currentTimeMillis();
		final long millisC = millisB - millisA;
		
		imageRGB.save("./generated/FilterTest.png");
		
		System.out.println("It took " + millisC + " milliseconds.");
	}
}