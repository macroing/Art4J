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
package org.macroing.art4j.test;

import java.io.File;

import org.macroing.art4j.color.Color3D;
import org.macroing.art4j.image.Image;
import org.macroing.art4j.pixel.Color4DPixelOperator;

public final class HDR {
	private HDR() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
		final Color3D a = new Color3D(1.0D, 1.0D, 1.0D);
		final Color3D b = Color3D.fromIntRGBE(a.toIntRGBE());
		final Color3D c = Color3D.fromIntRGBE(b.toIntRGBE());
		
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		
		final
		Image image = Image.createHDR(new File("./generated/HDR_029_Sky_Cloudy_Ref.hdr"));
		image.fillD(Color4DPixelOperator.toneMap(image.relativeLuminanceMaxAsDouble()));
		image.fillD(Color4DPixelOperator.redoGammaCorrection());
		image.save(new File("./generated/HDR_029_Sky_Cloudy_Ref.png"));
	}
}