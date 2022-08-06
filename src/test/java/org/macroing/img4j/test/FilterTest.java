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
import org.macroing.img4j.filter.BoxFilter2D;
import org.macroing.img4j.filter.Filter2D;
import org.macroing.img4j.image.Image;
import org.macroing.java.lang.Doubles;
import org.macroing.java.lang.Ints;
import org.macroing.java.util.Randoms;

public final class FilterTest {
	private FilterTest() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
		final Image imageOriginal = new Image("./generated/Image-Original.jpg");
		final Image imageRGB = new Image(imageOriginal.getResolutionX(), imageOriginal.getResolutionY(), Color4D.TRANSPARENT, DataFactory.forColor4D());
		final Image imageXYZ = new Image(imageOriginal.getResolutionX(), imageOriginal.getResolutionY(), Color4D.TRANSPARENT, DataFactory.forColor4D());
		
		final ColorSpaceD colorSpace = ColorSpaceD.getDefault();
		
		final Filter2D filter = new BoxFilter2D();
		
		final int resolutionX = imageOriginal.getResolutionX();
		final int resolutionY = imageOriginal.getResolutionY();
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++) {
				final Color3D colorRGB = colorSpace.undoGammaCorrection(imageOriginal.getColor3D(x, y));
				final Color3D colorXYZ = colorSpace.convertRGBToXYZ(colorRGB);
				
				final double rX = x + Randoms.nextDouble() - 0.5D;
				final double rY = y + Randoms.nextDouble() - 0.5D;
				
				doAddColorXYZ(imageXYZ, colorXYZ, filter, 1.0D, rX, rY);
			}
		}
		
		doRender(imageRGB, imageXYZ);
		
		imageRGB.save("./generated/FilterTest.png");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static void doAddColorXYZ(final Image imageXYZ, final Color3D colorXYZ, final double sampleWeight, final double filterWeight, final int index) {
		final Color4D oldColorXYZW = imageXYZ.getColor4D(index);
		final Color4D newColorXYZW = new Color4D(oldColorXYZW.r + colorXYZ.r * sampleWeight * filterWeight, oldColorXYZW.g + colorXYZ.g * sampleWeight * filterWeight, oldColorXYZW.b + colorXYZ.b * sampleWeight * filterWeight, oldColorXYZW.a + filterWeight);
		
		imageXYZ.setColor4D(newColorXYZW, index);
	}
	
	private static void doAddColorXYZ(final Image imageXYZ, final Color3D colorXYZ, final Filter2D filter, final double sampleWeight, final double x, final double y) {
		final double[] filterTable = filter.createFilterTable();
		
		final double filterResolutionX = filter.getResolutionX();
		final double filterResolutionY = filter.getResolutionY();
		final double filterResolutionXReciprocal = filter.getResolutionXReciprocal();
		final double filterResolutionYReciprocal = filter.getResolutionYReciprocal();
		
		final double deltaX = x - 0.5D;
		final double deltaY = y - 0.5D;
		
		final int resolutionX = imageXYZ.getResolutionX();
		final int resolutionY = imageXYZ.getResolutionY();
		
		final int minimumFilterX = (int)(Doubles.max(Doubles.ceil(deltaX - filterResolutionX), 0.0D));
		final int maximumFilterX = (int)(Doubles.min(Doubles.floor(deltaX + filterResolutionX), resolutionX - 1.0D));
		final int minimumFilterY = (int)(Doubles.max(Doubles.ceil(deltaY - filterResolutionY), 0.0D));
		final int maximumFilterY = (int)(Doubles.min(Doubles.floor(deltaY + filterResolutionY), resolutionY - 1.0D));
		final int maximumFilterXMinimumFilterX = maximumFilterX - minimumFilterX;
		final int maximumFilterYMinimumFilterY = maximumFilterY - minimumFilterY;
		
		if(maximumFilterXMinimumFilterX >= 0 && maximumFilterYMinimumFilterY >= 0) {
			final int[] filterOffsetX = new int[maximumFilterXMinimumFilterX + 1];
			final int[] filterOffsetY = new int[maximumFilterYMinimumFilterY + 1];
			
			for(int filterX = minimumFilterX; filterX <= maximumFilterX; filterX++) {
				filterOffsetX[filterX - minimumFilterX] = Ints.min((int)(Doubles.floor(Doubles.abs((filterX - deltaX) * filterResolutionXReciprocal * Filter2D.FILTER_TABLE_SIZE))), Filter2D.FILTER_TABLE_SIZE - 1);
			}
			
			for(int filterY = minimumFilterY; filterY <= maximumFilterY; filterY++) {
				filterOffsetY[filterY - minimumFilterY] = Ints.min((int)(Doubles.floor(Doubles.abs((filterY - deltaY) * filterResolutionYReciprocal * Filter2D.FILTER_TABLE_SIZE))), Filter2D.FILTER_TABLE_SIZE - 1);
			}
			
			for(int filterY = minimumFilterY; filterY <= maximumFilterY; filterY++) {
				final int filterYResolutionX = filterY * resolutionX;
				final int filterOffsetYOffsetFilterTableSize = filterOffsetY[filterY - minimumFilterY] * Filter2D.FILTER_TABLE_SIZE;
				
				for(int filterX = minimumFilterX; filterX <= maximumFilterX; filterX++) {
					doAddColorXYZ(imageXYZ, colorXYZ, sampleWeight, filterTable[filterOffsetYOffsetFilterTableSize + filterOffsetX[filterX - minimumFilterX]], filterYResolutionX + filterX);
				}
			}
		}
	}
	
	private static void doRender(final Image imageRGB, final Image imageXYZ) {
		final ColorSpaceD colorSpace = ColorSpaceD.getDefault();
		
		final int resolutionX = Ints.min(imageRGB.getResolutionX(), imageXYZ.getResolutionX());
		final int resolutionY = Ints.min(imageRGB.getResolutionY(), imageXYZ.getResolutionY());
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++) {
				final Color4D colorXYZW = imageXYZ.getColor4D(x, y);
				final Color3D colorXYZ = new Color3D(colorXYZW);
				
				Color3D colorRGB = colorSpace.convertXYZToRGB(colorXYZ);
				
				if(!Doubles.isZero(colorXYZW.a)) {
					colorRGB = new Color3D(Doubles.max(colorRGB.r / colorXYZW.a, 0.0D), Doubles.max(colorRGB.g / colorXYZW.a, 0.0D), Doubles.max(colorRGB.b / colorXYZW.a, 0.0D));
				}
				
				colorRGB = colorSpace.redoGammaCorrection(colorRGB);
				
				imageRGB.setColor3D(colorRGB, x, y);
			}
		}
	}
}