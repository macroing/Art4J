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
package org.macroing.art4j.test;

public final class Algorithms {
	private Algorithms() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	Source: https://en.wikipedia.org/wiki/Color_rendering_index
	public static void main(final String[] args) {
		final double X = 109.2D;
		final double Y = 100.0D;
		final double Z =  38.9D;
		
		final double[] pointXY = doComputePointXY(X, Y, Z);
		final double[] pointUV = doComputePointUV(pointXY);
		
		final boolean DC = doComputeDC(pointUV);
		
		final double CCT = doComputeCCT(pointXY);
		
		System.out.println("x = " + pointXY[0]);
		System.out.println("y = " + pointXY[1]);
		System.out.println("u = " + pointUV[0]);
		System.out.println("v = " + pointUV[1]);
		System.out.println("DC = " + DC);
		System.out.println("CCT = " + CCT);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static boolean doComputeDC(final double[] pointUV) {
		final double planckianLocusX = 0.2528D;
		final double planckianLocusY = 0.3484D;
		
		return Math.sqrt(Math.pow(pointUV[0] - planckianLocusX, 2.0D) + Math.pow(pointUV[1] - planckianLocusY, 2.0D)) < 5.4D * 10.0E-3D;
	}
	
//	Compute CCT using McCamy's approximation algorithm:
	private static double doComputeCCT(final double[] pointXY) {
		final double epicenterX = 0.3320D;
		final double epicenterY = 0.1858D;
		
		final double n = (pointXY[0] - epicenterX) / (pointXY[1] - epicenterY);
		
		return -449.0D * Math.pow(n, 3.0D) + 3525.0D * Math.pow(n, 2.0D) - 6823.3D * n + 5520.33D;
	}
	
//	Compute the tristimulus values (xy chromaticity values) using the 1931 standard observer:
	private static double[] doComputePointXY(final double X, final double Y, final double Z) {
		final double x = X / (X + Y + Z);
		final double y = Y / (X + Y + Z);
		
		return new double[] {x, y};
	}
	
//	Compute the CIE 1960 UCS values from chromaticity values:
	private static double[] doComputePointUV(final double[] pointXY) {
		final double u = 4.0D * pointXY[0] / (-2.0D * pointXY[0] + 12.0D * pointXY[1] + 3.0D);
		final double v = 6.0D * pointXY[1] / (-2.0D * pointXY[0] + 12.0D * pointXY[1] + 3.0D);
		
		return new double[] {u, v};
	}
}