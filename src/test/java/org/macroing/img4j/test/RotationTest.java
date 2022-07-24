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

import java.util.Arrays;
import java.util.Objects;

import org.macroing.img4j.geometry.Point2I;
import org.macroing.img4j.geometry.shape.Rectangle2I;
import org.macroing.img4j.utility.Doubles;
import org.macroing.img4j.utility.Ints;

public final class RotationTest {
	private RotationTest() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
		final
		Image image = new Image(11, 11);
		image.print();
		image.rotate(90.0D, false);
		image.print();
		image.rotate(90.0D, false);
		image.print();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class Image {
		private Rectangle2I rotationBounds;
		private int resolutionX;
		private int resolutionY;
		private int[] colors;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Image(final int resolutionX, final int resolutionY) {
			this.rotationBounds = null;
			this.resolutionX = resolutionX;
			this.resolutionY = resolutionY;
			this.colors = new int[resolutionX * resolutionY];
			
			Arrays.fill(this.colors, 100);
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Rectangle2I getRotationBounds() {
			if(this.rotationBounds != null) {
				return this.rotationBounds;
			}
			
			final int resolutionX = getResolutionX();
			final int resolutionY = getResolutionY();
			
			return new Rectangle2I(new Point2I(0, 0), new Point2I(0, resolutionY), new Point2I(resolutionX, resolutionY), new Point2I(resolutionX, 0));
		}
		
		public boolean rotate(final double angle, final boolean isAngleInRadians) {
//			Check that the angle is not 0.0:
			if(Doubles.isZero(angle)) {
				return false;
			}
			
//			Retrieve the angle in degrees:
			final double angleDegrees = isAngleInRadians ? Doubles.toDegrees(angle) : angle;
			
//			Check that the angle in degrees is not +360.0 or -360.0:
			if(Doubles.equals(angleDegrees, +360.0D) || Doubles.equals(angleDegrees, -360.0D)) {
				return false;
			}
			
//			Retrieve the old resolution:
			final int oldResolutionX = this.resolutionX;
			final int oldResolutionY = this.resolutionY;
			
//			Retrieve the old rotation bounds:
			final Rectangle2I rotationBounds = getRotationBounds();
//			final Rectangle2I rotationBoundsTranslated = translate(rotationBounds, -oldResolutionX / 2, -oldResolutionY / 2);
			final Rectangle2I rotationBoundsRotated = rotate(rotationBounds, angle, isAngleInRadians);
			
			final Point2I min = rotationBoundsRotated.min();
			final Point2I max = rotationBoundsRotated.max();
			
			final int newResolutionX = max.x - min.x;
			final int newResolutionY = max.y - min.y;
			
//			final Rectangle2I rotationBoundsRotatedTranslated = translate(rotationBoundsRotated, newResolutionX / 2, newResolutionY / 2);
			
			final int[] newColors = new int[newResolutionX * newResolutionY];
			
			System.out.println("Old resolution: " + oldResolutionX + "," + oldResolutionY);
			System.out.println("New resolution: " + newResolutionX + "," + newResolutionY);
			
			for(int y = 0; y < newResolutionY; y++) {
				for(int x = 0; x < newResolutionX; x++) {
					final Point2I a = new Point2I(x, y);
//					final Point2I b = translate(a, -newResolutionX / 2, -newResolutionY / 2);
					final Point2I c = rotate(a, rotationBoundsRotated, -angle, isAngleInRadians);
//					final Point2I d = translate(c, oldResolutionX / 2, oldResolutionY / 2);
					
					newColors[y * newResolutionX + x] = getColor(c.x, c.y);
				}
			}
			
			setRotationBounds(rotationBoundsRotated);
			
			this.colors = newColors;
			this.resolutionX = newResolutionX;
			this.resolutionY = newResolutionY;
			
			return true;
		}
		
		public boolean setRotationBounds(final Rectangle2I rotationBounds) {
			final Rectangle2I oldRotationBounds = this.rotationBounds;
			final Rectangle2I newRotationBounds =      rotationBounds;
			
			if(Objects.equals(oldRotationBounds, newRotationBounds)) {
				return false;
			}
			
			this.rotationBounds = newRotationBounds;
			
			return true;
		}
		
		public int getColor(final int x, final int y) {
			return x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY ? this.colors[y * this.resolutionX + x] : 255;
		}
		
		public int getResolutionX() {
			return this.resolutionX;
		}
		
		public int getResolutionY() {
			return this.resolutionY;
		}
		
		public void print() {
			for(int y = 0; y < this.resolutionY; y++) {
				for(int x = 0; x < this.resolutionX; x++) {
					if(this.colors[y * this.resolutionX + x] == 255) {
						System.out.print("  ");
					} else {
						System.out.print("OO");
					}
				}
				
				System.out.println();
			}
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public static Point2I rotate(final Point2I point, final Rectangle2I rectangle, final double angle, final boolean isAngleInRadians) {
			final double a = isAngleInRadians ? angle : Doubles.toRadians(angle);
			final double aCos = Doubles.cos(a);
			final double aSin = Doubles.sin(a);
			
			final Point2I a0 = rectangle.getA();
			final Point2I b0 = rectangle.getB();
			final Point2I c0 = rectangle.getC();
			final Point2I d0 = rectangle.getD();
			final Point2I p0 = point;
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
			final int p1X = +(int)((p0.x - m0.x) * aCos) + (int)((p0.y - m0.y) * aSin);
			final int p1Y = -(int)((p0.x - m0.x) * aSin) + (int)((p0.y - m0.y) * aCos);
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
			final int p2X = m1X < 0 ? p1X - m1X : p1X;
			final int p2Y = m1Y < 0 ? p1Y - m1Y : p1Y;
			
			final int dA2B2 = (int)(Doubles.sqrt((b2X - a2X) * (b2X - a2X) + (b2Y - a2Y) * (b2Y - a2Y)));
			final int dB2C2 = (int)(Doubles.sqrt((c2X - b2X) * (c2X - b2X) + (c2Y - b2Y) * (c2Y - b2Y)));
			final int dC2D2 = (int)(Doubles.sqrt((d2X - c2X) * (d2X - c2X) + (d2Y - c2Y) * (d2Y - c2Y)));
			final int dD2A2 = (int)(Doubles.sqrt((a2X - d2X) * (a2X - d2X) + (a2Y - d2Y) * (a2Y - d2Y)));
			
			if(dA0B0 == dA2B2 && dB0C0 == dB2C2 && dC0D0 == dC2D2 && dD0A0 == dD2A2) {
				return new Point2I(p2X, p2Y);
			}
			
			final int a3X = a2X > b2X ? dA2B2 > dA0B0 ? a2X - 1 : dA2B2 < dA0B0 ? a2X + 1 : a2X : a2X;
			final int a3Y = a2Y > b2Y ? dA2B2 > dA0B0 ? a2Y - 1 : dA2B2 < dA0B0 ? a2Y + 1 : a2Y : a2Y;
			final int b3X = b2X > c2X ? dB2C2 > dB0C0 ? b2X - 1 : dB2C2 < dB0C0 ? b2X + 1 : b2X : b2X;
			final int b3Y = b2Y > c2Y ? dB2C2 > dB0C0 ? b2Y - 1 : dB2C2 < dB0C0 ? b2Y + 1 : b2Y : b2Y;
			final int c3X = c2X > d2X ? dC2D2 > dC0D0 ? c2X - 1 : dC2D2 < dC0D0 ? c2X + 1 : c2X : c2X;
			final int c3Y = c2Y > d2Y ? dC2D2 > dC0D0 ? c2Y - 1 : dC2D2 < dC0D0 ? c2Y + 1 : c2Y : c2Y;
			final int d3X = d2X > a2X ? dD2A2 > dD0A0 ? d2X - 1 : dD2A2 < dD0A0 ? d2X + 1 : d2X : d2X;
			final int d3Y = d2Y > a2Y ? dD2A2 > dD0A0 ? d2Y - 1 : dD2A2 < dD0A0 ? d2Y + 1 : d2Y : d2Y;
			final int p3X = a3X < a2X || b3X < b2X || c3X < c2X || d3X < d2X ? p2X - 1 : a3X > a2X || b3X > b2X || c3X > c2X || d3X > d2X ? p2X + 1 : p2X;
			final int p3Y = a3Y < a2Y || b3Y < b2Y || c3Y < c2Y || d3Y < d2Y ? p2Y - 1 : a3Y > a2Y || b3Y > b2Y || c3Y > c2Y || d3Y > d2Y ? p2Y + 1 : p2Y;
			
			final int dA3B3 = (int)(Doubles.sqrt((b3X - a3X) * (b3X - a3X) + (b3Y - a3Y) * (b3Y - a3Y)));
			final int dB3C3 = (int)(Doubles.sqrt((c3X - b3X) * (c3X - b3X) + (c3Y - b3Y) * (c3Y - b3Y)));
			final int dC3D3 = (int)(Doubles.sqrt((d3X - c3X) * (d3X - c3X) + (d3Y - c3Y) * (d3Y - c3Y)));
			final int dD3A3 = (int)(Doubles.sqrt((a3X - d3X) * (a3X - d3X) + (a3Y - d3Y) * (a3Y - d3Y)));
			
			if(dA0B0 == dA3B3 && dB0C0 == dB3C3 && dC0D0 == dC3D3 && dD0A0 == dD3A3) {
				return new Point2I(p3X, p3Y);
			}
			
			int a4X = a3X;
			int a4Y = a3Y;
			int b4X = b3X;
			int b4Y = b3Y;
			int c4X = c3X;
			int c4Y = c3Y;
			int d4X = d3X;
			int d4Y = d3Y;
			int p4X = d3X;
			int p4Y = d3Y;
			
			int a5X = a3X;
			int a5Y = a3Y;
			int b5X = b3X;
			int b5Y = b3Y;
			int c5X = c3X;
			int c5Y = c3Y;
			int d5X = d3X;
			int d5Y = d3Y;
			int p5X = d3X;
			int p5Y = d3Y;
			
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
				p5X = a5X < a4X || b5X < b4X || c5X < c4X || d5X < d4X ? p4X - 1 : a5X > a4X || b5X > b4X || c5X > c4X || d5X > d4X ? p4X + 1 : p4X;
				p5Y = a5Y < a4Y || b5Y < b4Y || c5Y < c4Y || d5Y < d4Y ? p4Y - 1 : a5Y > a4Y || b5Y > b4Y || c5Y > c4Y || d5Y > d4Y ? p4Y + 1 : p4Y;
				
				if(a4X == a5X && a4Y == a5Y && b4X == b5X && b4Y == b5Y && c4X == c5X && c4Y == c5Y && d4X == d5X && d4Y == d5Y) {
					return point;
				}
				
				a4X = a5X;
				a4Y = a5Y;
				b4X = b5X;
				b4Y = b5Y;
				c4X = c5X;
				c4Y = c5Y;
				d4X = d5X;
				d4Y = d5Y;
				p4X = p5X;
				p4Y = p5Y;
				
				dA4B4 = (int)(Doubles.sqrt((b4X - a4X) * (b4X - a4X) + (b4Y - a4Y) * (b4Y - a4Y)));
				dB4C4 = (int)(Doubles.sqrt((c4X - b4X) * (c4X - b4X) + (c4Y - b4Y) * (c4Y - b4Y)));
				dC4D4 = (int)(Doubles.sqrt((d4X - c4X) * (d4X - c4X) + (d4Y - c4Y) * (d4Y - c4Y)));
				dD4A4 = (int)(Doubles.sqrt((a4X - d4X) * (a4X - d4X) + (a4Y - d4Y) * (a4Y - d4Y)));
				
				if(dA0B0 == dA4B4 && dB0C0 == dB4C4 && dC0D0 == dC4D4 && dD0A0 == dD4A4) {
					return new Point2I(p4X, p4Y);
				}
			}
			
			return new Point2I(p4X, p4Y);
		}
		
		public static Point2I translate(final Point2I point, final int x, final int y) {
			return new Point2I(point.x + x, point.y + y);
		}
		
		public static Rectangle2I rotate(final Rectangle2I rectangle, final double angle, final boolean isAngleInRadians) {
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
		
		public static Rectangle2I translate(final Rectangle2I rectangle, final int x, final int y) {
			final Point2I oldA = rectangle.getA();
			final Point2I oldB = rectangle.getB();
			final Point2I oldC = rectangle.getC();
			final Point2I oldD = rectangle.getD();
			
			final Point2I newA = new Point2I(oldA.x + x, oldA.y + y);
			final Point2I newB = new Point2I(oldB.x + x, oldB.y + y);
			final Point2I newC = new Point2I(oldC.x + x, oldC.y + y);
			final Point2I newD = new Point2I(oldD.x + x, oldD.y + y);
			
			return new Rectangle2I(newA, newB, newC, newD);
		}
	}
}