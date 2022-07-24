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

import java.util.ArrayList;
import java.util.List;

import org.macroing.img4j.geometry.Point2I;
import org.macroing.img4j.geometry.Vector2I;
import org.macroing.img4j.geometry.shape.LineSegment2I;
import org.macroing.img4j.geometry.shape.Rectangle2I;
import org.macroing.img4j.utility.Doubles;
import org.macroing.img4j.utility.Ints;

public final class RotationTest2 {
	private RotationTest2() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
//		final LineSegment2I a = new LineSegment2I(new Point2I(0, 0), new Point2I(0, 100));
//		final LineSegment2I b = rotate(a, 45.0D, false);
//		final LineSegment2I c = rotate(b, 45.0D, false);
//		final LineSegment2I d = rotate(c, 45.0D, false);
//		final LineSegment2I e = rotate(d, 45.0D, false);
//		final LineSegment2I f = rotate(e, 45.0D, false);
//		final LineSegment2I g = rotate(f, 45.0D, false);
//		final LineSegment2I h = rotate(g, 45.0D, false);
//		final LineSegment2I i = rotate(h, 45.0D, false);
		
//		System.out.println(a);
//		System.out.println(b);
//		System.out.println(c);
//		System.out.println(d);
//		System.out.println(e);
//		System.out.println(f);
//		System.out.println(g);
//		System.out.println(h);
//		System.out.println(i);
		
//		for(int j = 0; j < 10000; j++) {
//			rotate(a, Math.random() * 360.0D, false);
//		}
		
		final Rectangle2I a = new Rectangle2I(new Point2I(0, 0), new Point2I(0, 100), new Point2I(100, 100), new Point2I(100, 0));
		final Rectangle2I b = rotate(a, 45.0D, false);
		final Rectangle2I c = rotate(b, 45.0D, false);
		final Rectangle2I d = rotate(c, 45.0D, false);
		final Rectangle2I e = rotate(d, 45.0D, false);
		final Rectangle2I f = rotate(e, 45.0D, false);
		final Rectangle2I g = rotate(f, 45.0D, false);
		final Rectangle2I h = rotate(g, 45.0D, false);
		final Rectangle2I i = rotate(h, 45.0D, false);
		
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(d);
		System.out.println(e);
		System.out.println(f);
		System.out.println(g);
		System.out.println(h);
		System.out.println(i);
		
		for(int j = 0; j < 10000; j++) {
			System.out.println(rotate(a, Math.random() * 360.0D, false));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static LineSegment2I rotate(final LineSegment2I lineSegment, final double angle, final boolean isAngleInRadians) {
		final double a = isAngleInRadians ? angle : Doubles.toRadians(angle);
		final double aCos = Doubles.cos(a);
		final double aSin = Doubles.sin(a);
		
		final Point2I pA = lineSegment.getA();
		final Point2I pB = lineSegment.getB();
		
		final double pAX = pA.x;
		final double pAY = pA.y;
		final double pBX = pB.x;
		final double pBY = pB.y;
		final double pCX = pAX + (pBX - pAX) * aCos + (pBY - pAY) * aSin;
		final double pCY = pAY - (pBX - pAX) * aSin + (pBY - pAY) * aCos;
		
		final double vABX = pBX - pAX;
		final double vABY = pBY - pAY;
		final double vABLength = Doubles.sqrt(vABX * vABX + vABY * vABY);
		
		final double vACX = pCX - pAX;
		final double vACY = pCY - pAY;
		final double vACLength = Doubles.sqrt(vACX * vACX + vACY * vACY);
		final double vACNX = vACX / vACLength;
		final double vACNY = vACY / vACLength;
		
		final double pDX = pAX + vACNX * vABLength;
		final double pDY = pAY + vACNY * vABLength;
		
		final Point2I pC = new Point2I((int)(pDX - 1.0D), (int)(pDY - 1.0D));
		final Point2I pD = new Point2I((int)(pDX - 0.0D), (int)(pDY - 1.0D));
		final Point2I pE = new Point2I((int)(pDX + 1.0D), (int)(pDY - 1.0D));
		final Point2I pF = new Point2I((int)(pDX - 1.0D), (int)(pDY + 0.0D));
		final Point2I pG = new Point2I((int)(pDX - 0.0D), (int)(pDY + 0.0D));
		final Point2I pH = new Point2I((int)(pDX + 1.0D), (int)(pDY + 0.0D));
		final Point2I pI = new Point2I((int)(pDX - 1.0D), (int)(pDY + 1.0D));
		final Point2I pJ = new Point2I((int)(pDX - 0.0D), (int)(pDY + 1.0D));
		final Point2I pK = new Point2I((int)(pDX + 1.0D), (int)(pDY + 1.0D));
		
		final Vector2I vAB = Vector2I.direction(pA, pB);
		final Vector2I vAC = Vector2I.direction(pA, pC);
		final Vector2I vAD = Vector2I.direction(pA, pD);
		final Vector2I vAE = Vector2I.direction(pA, pE);
		final Vector2I vAF = Vector2I.direction(pA, pF);
		final Vector2I vAG = Vector2I.direction(pA, pG);
		final Vector2I vAH = Vector2I.direction(pA, pH);
		final Vector2I vAI = Vector2I.direction(pA, pI);
		final Vector2I vAJ = Vector2I.direction(pA, pJ);
		final Vector2I vAK = Vector2I.direction(pA, pK);
		
		final int vABLengthI = vAB.length();
		final int vACLengthI = vAC.length();
		final int vADLengthI = vAD.length();
		final int vAELengthI = vAE.length();
		final int vAFLengthI = vAF.length();
		final int vAGLengthI = vAG.length();
		final int vAHLengthI = vAH.length();
		final int vAILengthI = vAI.length();
		final int vAJLengthI = vAJ.length();
		final int vAKLengthI = vAK.length();
		
		if(vABLengthI == vACLengthI) {
			return new LineSegment2I(pA, pC);
		}
		
		if(vABLengthI == vADLengthI) {
			return new LineSegment2I(pA, pD);
		}
		
		if(vABLengthI == vAELengthI) {
			return new LineSegment2I(pA, pE);
		}
		
		if(vABLengthI == vAFLengthI) {
			return new LineSegment2I(pA, pF);
		}
		
		if(vABLengthI == vAGLengthI) {
			return new LineSegment2I(pA, pG);
		}
		
		if(vABLengthI == vAHLengthI) {
			return new LineSegment2I(pA, pH);
		}
		
		if(vABLengthI == vAILengthI) {
			return new LineSegment2I(pA, pI);
		}
		
		if(vABLengthI == vAJLengthI) {
			return new LineSegment2I(pA, pJ);
		}
		
		if(vABLengthI == vAKLengthI) {
			return new LineSegment2I(pA, pK);
		}
		
		return lineSegment;
	}
	
	private static List<LineSegment2I> rotateAll(final LineSegment2I lineSegment, final double angle, final boolean isAngleInRadians) {
		final List<LineSegment2I> lineSegments = new ArrayList<>();
		
		final double a = isAngleInRadians ? angle : Doubles.toRadians(angle);
		final double aCos = Doubles.cos(a);
		final double aSin = Doubles.sin(a);
		
		final Point2I pA = lineSegment.getA();
		final Point2I pB = lineSegment.getB();
		
		final double pAX = pA.x;
		final double pAY = pA.y;
		final double pBX = pB.x;
		final double pBY = pB.y;
		final double pCX = pAX + (pBX - pAX) * aCos + (pBY - pAY) * aSin;
		final double pCY = pAY - (pBX - pAX) * aSin + (pBY - pAY) * aCos;
		
		final double vABX = pBX - pAX;
		final double vABY = pBY - pAY;
		final double vABLength = Doubles.sqrt(vABX * vABX + vABY * vABY);
		
		final double vACX = pCX - pAX;
		final double vACY = pCY - pAY;
		final double vACLength = Doubles.sqrt(vACX * vACX + vACY * vACY);
		final double vACNX = vACX / vACLength;
		final double vACNY = vACY / vACLength;
		
		final double pDX = pAX + vACNX * vABLength;
		final double pDY = pAY + vACNY * vABLength;
		
		final Point2I pC = new Point2I((int)(pDX - 1.0D), (int)(pDY - 1.0D));
		final Point2I pD = new Point2I((int)(pDX - 0.0D), (int)(pDY - 1.0D));
		final Point2I pE = new Point2I((int)(pDX + 1.0D), (int)(pDY - 1.0D));
		final Point2I pF = new Point2I((int)(pDX - 1.0D), (int)(pDY + 0.0D));
		final Point2I pG = new Point2I((int)(pDX - 0.0D), (int)(pDY + 0.0D));
		final Point2I pH = new Point2I((int)(pDX + 1.0D), (int)(pDY + 0.0D));
		final Point2I pI = new Point2I((int)(pDX - 1.0D), (int)(pDY + 1.0D));
		final Point2I pJ = new Point2I((int)(pDX - 0.0D), (int)(pDY + 1.0D));
		final Point2I pK = new Point2I((int)(pDX + 1.0D), (int)(pDY + 1.0D));
		
		final Vector2I vAB = Vector2I.direction(pA, pB);
		final Vector2I vAC = Vector2I.direction(pA, pC);
		final Vector2I vAD = Vector2I.direction(pA, pD);
		final Vector2I vAE = Vector2I.direction(pA, pE);
		final Vector2I vAF = Vector2I.direction(pA, pF);
		final Vector2I vAG = Vector2I.direction(pA, pG);
		final Vector2I vAH = Vector2I.direction(pA, pH);
		final Vector2I vAI = Vector2I.direction(pA, pI);
		final Vector2I vAJ = Vector2I.direction(pA, pJ);
		final Vector2I vAK = Vector2I.direction(pA, pK);
		
		final int vABLengthI = vAB.length();
		final int vACLengthI = vAC.length();
		final int vADLengthI = vAD.length();
		final int vAELengthI = vAE.length();
		final int vAFLengthI = vAF.length();
		final int vAGLengthI = vAG.length();
		final int vAHLengthI = vAH.length();
		final int vAILengthI = vAI.length();
		final int vAJLengthI = vAJ.length();
		final int vAKLengthI = vAK.length();
		
		if(vABLengthI == vACLengthI) {
			lineSegments.add(new LineSegment2I(pA, pC));
		}
		
		if(vABLengthI == vADLengthI) {
			lineSegments.add(new LineSegment2I(pA, pD));
		}
		
		if(vABLengthI == vAELengthI) {
			lineSegments.add(new LineSegment2I(pA, pE));
		}
		
		if(vABLengthI == vAFLengthI) {
			lineSegments.add(new LineSegment2I(pA, pF));
		}
		
		if(vABLengthI == vAGLengthI) {
			lineSegments.add(new LineSegment2I(pA, pG));
		}
		
		if(vABLengthI == vAHLengthI) {
			lineSegments.add(new LineSegment2I(pA, pH));
		}
		
		if(vABLengthI == vAILengthI) {
			lineSegments.add(new LineSegment2I(pA, pI));
		}
		
		if(vABLengthI == vAJLengthI) {
			lineSegments.add(new LineSegment2I(pA, pJ));
		}
		
		if(vABLengthI == vAKLengthI) {
			lineSegments.add(new LineSegment2I(pA, pK));
		}
		
		return lineSegments;
	}
	/*
	private static Rectangle2I rotate(final Rectangle2I rectangle, final double angle, final boolean isAngleInRadians) {
		final List<LineSegment2I> lineSegments = rectangle.getLineSegments();
		
		final LineSegment2I lineSegment00 = lineSegments.get(0);
		final LineSegment2I lineSegment10 = lineSegments.get(1);
		final LineSegment2I lineSegment20 = lineSegments.get(2);
		final LineSegment2I lineSegment30 = lineSegments.get(3);
		
		final List<LineSegment2I> lineSegments0 = rotateAll(lineSegment00, angle, isAngleInRadians);
		final List<LineSegment2I> lineSegments1 = rotateAll(lineSegment10, angle, isAngleInRadians);
		final List<LineSegment2I> lineSegments2 = rotateAll(lineSegment20, angle, isAngleInRadians);
		final List<LineSegment2I> lineSegments3 = rotateAll(lineSegment30, angle, isAngleInRadians);
		
		for(int i = 0; i < lineSegments0.size(); i++) {
			final Point2I a = lineSegments0.get(i).getB();
			
			for(int j = 0; j < lineSegments1.size(); j++) {
				final Point2I b = lineSegments1.get(j).getB();
				
				for(int k = 0; k < lineSegments2.size(); k++) {
					final Point2I c = lineSegments2.get(k).getB();
					
					for(int l = 0; l < lineSegments3.size(); l++) {
						final Point2I d = lineSegments3.get(l).getB();
						
						final int distanceAB = Point2I.distance(a, b);
						final int distanceBC = Point2I.distance(b, c);
						final int distanceCD = Point2I.distance(c, d);
						final int distanceDA = Point2I.distance(d, a);
						
						final int deltaABCD = Math.abs(distanceAB - distanceCD);
						final int deltaBCDA = Math.abs(distanceBC - distanceDA);
						
						final boolean isValidABCD = deltaABCD == 0;
						final boolean isValidBCDA = deltaBCDA == 0;
						final boolean isValid = isValidABCD & isValidBCDA;
						
						if(isValid) {
							return new Rectangle2I(a, b, c, d);
						}
					}
				}
			}
		}
		
		return rectangle;
	}
	*/
	
	private static Rectangle2I rotate(final Rectangle2I rectangle, final double angle, final boolean isAngleInRadians) {
		final double a = isAngleInRadians ? angle : Doubles.toRadians(angle);
		final double aCos = Doubles.cos(a);
		final double aSin = Doubles.sin(a);
		
		final Point2I a0 = rectangle.getA();
		final Point2I b0 = rectangle.getB();
		final Point2I c0 = rectangle.getC();
		final Point2I d0 = rectangle.getD();
		final Point2I m0 = Point2I.midpoint(a0, c0);
		
		final int a0X = a0.x;
		final int a0Y = a0.y;
		final int b0X = b0.x;
		final int b0Y = b0.y;
		final int c0X = c0.x;
		final int c0Y = c0.y;
		final int d0X = d0.x;
		final int d0Y = d0.y;
		
		final int dA0B0 = (int)(Doubles.sqrt((b0X - a0X) * (b0X - a0X) + (b0Y - a0Y) * (b0Y - a0Y)));
		final int dB0C0 = (int)(Doubles.sqrt((c0X - b0X) * (c0X - b0X) + (c0Y - b0Y) * (c0Y - b0Y)));
		final int dC0D0 = (int)(Doubles.sqrt((d0X - c0X) * (d0X - c0X) + (d0Y - c0Y) * (d0Y - c0Y)));
		final int dD0A0 = (int)(Doubles.sqrt((a0X - d0X) * (a0X - d0X) + (a0Y - d0Y) * (a0Y - d0Y)));
		
		final int a1X = +(int)((a0.x - m0.x) * aCos) + (int)((a0.y - m0.y) * aSin);
		final int a1Y = -(int)((a0.x - m0.x) * aSin) + (int)((a0.y - m0.y) * aCos);
		final int b1X = +(int)((b0.x - m0.x) * aCos) + (int)((b0.y - m0.y) * aSin);
		final int b1Y = -(int)((b0.x - m0.x) * aSin) + (int)((b0.y - m0.y) * aCos);
		final int c1X = +(int)((c0.x - m0.x) * aCos) + (int)((c0.y - m0.y) * aSin);
		final int c1Y = -(int)((c0.x - m0.x) * aSin) + (int)((c0.y - m0.y) * aCos);
		final int d1X = +(int)((d0.x - m0.x) * aCos) + (int)((d0.y - m0.y) * aSin);
		final int d1Y = -(int)((d0.x - m0.x) * aSin) + (int)((d0.y - m0.y) * aCos);
		final int m1X = Ints.min(a1X, b1X, c1X, d1X);
		final int m1Y = Ints.min(a1Y, b1Y, c1Y, d1Y);
		
		final int a2X = m1X < 0 ? a1X - m1X : a1X;
		final int a2Y = m1Y < 0 ? a1Y - m1Y : a1Y;
		final int b2X = m1X < 0 ? b1X - m1X : b1X;
		final int b2Y = m1Y < 0 ? b1Y - m1Y : b1Y;
		final int c2X = m1X < 0 ? c1X - m1X : c1X;
		final int c2Y = m1Y < 0 ? c1Y - m1Y : c1Y;
		final int d2X = m1X < 0 ? d1X - m1X : d1X;
		final int d2Y = m1Y < 0 ? d1Y - m1Y : d1Y;
		
		final int dA2B2 = (int)(Doubles.sqrt((b2X - a2X) * (b2X - a2X) + (b2Y - a2Y) * (b2Y - a2Y)));
		final int dB2C2 = (int)(Doubles.sqrt((c2X - b2X) * (c2X - b2X) + (c2Y - b2Y) * (c2Y - b2Y)));
		final int dC2D2 = (int)(Doubles.sqrt((d2X - c2X) * (d2X - c2X) + (d2Y - c2Y) * (d2Y - c2Y)));
		final int dD2A2 = (int)(Doubles.sqrt((a2X - d2X) * (a2X - d2X) + (a2Y - d2Y) * (a2Y - d2Y)));
		
		if(dA0B0 == dA2B2 && dB0C0 == dB2C2 && dC0D0 == dC2D2 && dD0A0 == dD2A2) {
			final Point2I a2 = new Point2I(a2X, a2Y);
			final Point2I b2 = new Point2I(b2X, b2Y);
			final Point2I c2 = new Point2I(c2X, c2Y);
			final Point2I d2 = new Point2I(d2X, d2Y);
			
			return new Rectangle2I(a2, b2, c2, d2);
		}
		
		final int a3X = a2X > b2X ? dA2B2 > dA0B0 ? a2X - 1 : dA2B2 < dA0B0 ? a2X + 1 : a2X : a2X;
		final int a3Y = a2Y > b2Y ? dA2B2 > dA0B0 ? a2Y - 1 : dA2B2 < dA0B0 ? a2Y + 1 : a2Y : a2Y;
		final int b3X = b2X > c2X ? dB2C2 > dB0C0 ? b2X - 1 : dB2C2 < dB0C0 ? b2X + 1 : b2X : b2X;
		final int b3Y = b2Y > c2Y ? dB2C2 > dB0C0 ? b2Y - 1 : dB2C2 < dB0C0 ? b2Y + 1 : b2Y : b2Y;
		final int c3X = c2X > d2X ? dC2D2 > dC0D0 ? c2X - 1 : dC2D2 < dC0D0 ? c2X + 1 : c2X : c2X;
		final int c3Y = c2Y > d2Y ? dC2D2 > dC0D0 ? c2Y - 1 : dC2D2 < dC0D0 ? c2Y + 1 : c2Y : c2Y;
		final int d3X = d2X > a2X ? dD2A2 > dD0A0 ? d2X - 1 : dD2A2 < dD0A0 ? d2X + 1 : d2X : d2X;
		final int d3Y = d2Y > a2Y ? dD2A2 > dD0A0 ? d2Y - 1 : dD2A2 < dD0A0 ? d2Y + 1 : d2Y : d2Y;
		
		final int dA3B3 = (int)(Doubles.sqrt((b3X - a3X) * (b3X - a3X) + (b3Y - a3Y) * (b3Y - a3Y)));
		final int dB3C3 = (int)(Doubles.sqrt((c3X - b3X) * (c3X - b3X) + (c3Y - b3Y) * (c3Y - b3Y)));
		final int dC3D3 = (int)(Doubles.sqrt((d3X - c3X) * (d3X - c3X) + (d3Y - c3Y) * (d3Y - c3Y)));
		final int dD3A3 = (int)(Doubles.sqrt((a3X - d3X) * (a3X - d3X) + (a3Y - d3Y) * (a3Y - d3Y)));
		
		if(dA0B0 == dA3B3 && dB0C0 == dB3C3 && dC0D0 == dC3D3 && dD0A0 == dD3A3) {
			final Point2I a3 = new Point2I(a3X, a3Y);
			final Point2I b3 = new Point2I(b3X, b3Y);
			final Point2I c3 = new Point2I(c3X, c3Y);
			final Point2I d3 = new Point2I(d3X, d3Y);
			
			return new Rectangle2I(a3, b3, c3, d3);
		}
		
		int a4X = a3X;
		int a4Y = a3Y;
		int b4X = b3X;
		int b4Y = b3Y;
		int c4X = c3X;
		int c4Y = c3Y;
		int d4X = d3X;
		int d4Y = d3Y;
		
		int a5X = a3X;
		int a5Y = a3Y;
		int b5X = b3X;
		int b5Y = b3Y;
		int c5X = c3X;
		int c5Y = c3Y;
		int d5X = d3X;
		int d5Y = d3Y;
		
		int dA4B4 = (int)(Doubles.sqrt((b3X - a3X) * (b3X - a3X) + (b3Y - a3Y) * (b3Y - a3Y)));
		int dB4C4 = (int)(Doubles.sqrt((c3X - b3X) * (c3X - b3X) + (c3Y - b3Y) * (c3Y - b3Y)));
		int dC4D4 = (int)(Doubles.sqrt((d3X - c3X) * (d3X - c3X) + (d3Y - c3Y) * (d3Y - c3Y)));
		int dD4A4 = (int)(Doubles.sqrt((a3X - d3X) * (a3X - d3X) + (a3Y - d3Y) * (a3Y - d3Y)));
		
		while(dA0B0 != dA4B4 || dB0C0 != dB4C4 || dC0D0 != dC4D4 || dD0A0 != dD4A4) {
			a5X = a4X > b4X ? dA4B4 > dA0B0 ? a4X - 1 : dA4B4 < dA0B0 ? a4X + 1 : a4X : a4X;
			a5Y = a4Y > b4Y ? dA4B4 > dA0B0 ? a4Y - 1 : dA4B4 < dA0B0 ? a4Y + 1 : a4Y : a4Y;
			b5X = b4X > c4X ? dB4C4 > dB0C0 ? b4X - 1 : dB4C4 < dB0C0 ? b4X + 1 : b4X : b4X;
			b5Y = b4Y > c4Y ? dB4C4 > dB0C0 ? b4Y - 1 : dB4C4 < dB0C0 ? b4Y + 1 : b4Y : b4Y;
			c5X = c4X > d4X ? dC4D4 > dC0D0 ? c4X - 1 : dC4D4 < dC0D0 ? c4X + 1 : c4X : c4X;
			c5Y = c4Y > d4Y ? dC4D4 > dC0D0 ? c4Y - 1 : dC4D4 < dC0D0 ? c4Y + 1 : c4Y : c4Y;
			d5X = d4X > a4X ? dD4A4 > dD0A0 ? d4X - 1 : dD4A4 < dD0A0 ? d4X + 1 : d4X : d4X;
			d5Y = d4Y > a4Y ? dD4A4 > dD0A0 ? d4Y - 1 : dD4A4 < dD0A0 ? d4Y + 1 : d4Y : d4Y;
			
			if(a4X == a5X && a4Y == a5Y && b4X == b5X && b4Y == b5Y && c4X == c5X && c4Y == c5Y && d4X == d5X && d4Y == d5Y) {
				return rectangle;
			}
			
			a4X = a5X;
			a4Y = a5Y;
			b4X = b5X;
			b4Y = b5Y;
			c4X = c5X;
			c4Y = c5Y;
			d4X = d5X;
			d4Y = d5Y;
			
			dA4B4 = (int)(Doubles.sqrt((b4X - a4X) * (b4X - a4X) + (b4Y - a4Y) * (b4Y - a4Y)));
			dB4C4 = (int)(Doubles.sqrt((c4X - b4X) * (c4X - b4X) + (c4Y - b4Y) * (c4Y - b4Y)));
			dC4D4 = (int)(Doubles.sqrt((d4X - c4X) * (d4X - c4X) + (d4Y - c4Y) * (d4Y - c4Y)));
			dD4A4 = (int)(Doubles.sqrt((a4X - d4X) * (a4X - d4X) + (a4Y - d4Y) * (a4Y - d4Y)));
			
			if(dA0B0 == dA4B4 && dB0C0 == dB4C4 && dC0D0 == dC4D4 && dD0A0 == dD4A4) {
				final Point2I a4 = new Point2I(a4X, a4Y);
				final Point2I b4 = new Point2I(b4X, b4Y);
				final Point2I c4 = new Point2I(c4X, c4Y);
				final Point2I d4 = new Point2I(d4X, d4Y);
				
				return new Rectangle2I(a4, b4, c4, d4);
			}
		}
		
		final Point2I a4 = new Point2I(a4X, a4Y);
		final Point2I b4 = new Point2I(b4X, b4Y);
		final Point2I c4 = new Point2I(c4X, c4Y);
		final Point2I d4 = new Point2I(d4X, d4Y);
		
		return new Rectangle2I(a4, b4, c4, d4);
	}
}