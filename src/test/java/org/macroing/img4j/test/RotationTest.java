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

import java.util.Objects;

import org.macroing.img4j.geometry.Point2I;
import org.macroing.img4j.geometry.shape.Rectangle2I;
import org.macroing.img4j.utility.Doubles;

public final class RotationTest {
	private RotationTest() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
		final
		Image image = new Image(10, 10);
		image.print();
		image.rotate(45.0D, false);
		image.print();
		image.rotate(45.0D, false);
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
			
			for(int i = 0; i < this.colors.length; i++) {
				this.colors[i] = i % 10;
			}
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Rectangle2I getRotationBounds() {
			if(this.rotationBounds != null) {
				return this.rotationBounds;
			}
			
			final int resolutionX = getResolutionX();
			final int resolutionY = getResolutionY();
			
			return new Rectangle2I(new Point2I(0, 0), new Point2I(resolutionX, 0), new Point2I(resolutionX, resolutionY), new Point2I(0, resolutionY));
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
			final Rectangle2I rotationBoundsTranslated = translate(rotationBounds, -(oldResolutionX / 2), -(oldResolutionY / 2));
			final Rectangle2I rotationBoundsRotated = Rectangle2I.rotateABCD(rotationBoundsTranslated, angle, isAngleInRadians);
			
			final Point2I min = rotationBoundsRotated.min();
			final Point2I max = rotationBoundsRotated.max();
			
			final int newResolutionX = max.x - min.x;
			final int newResolutionY = max.y - min.y;
			
			final Rectangle2I rotationBoundsRotatedTranslated = translate(rotationBoundsRotated, newResolutionX / 2, newResolutionY / 2);
			
			final int[] newColors = new int[newResolutionX * newResolutionY];
			
			System.out.println("Old resolution: " + oldResolutionX + "," + oldResolutionY);
			System.out.println("New resolution: " + newResolutionX + "," + newResolutionY);
			
			for(int y = 0; y < newResolutionY; y++) {
				for(int x = 0; x < newResolutionX; x++) {
					final Point2I a = new Point2I(x, y);
					final Point2I b = translate(a, -(newResolutionX / 2), -(newResolutionY / 2));
					final Point2I c = Point2I.rotate(b, -angle, isAngleInRadians);
					final Point2I d = translate(c, oldResolutionX / 2, oldResolutionY / 2);
					
					newColors[y * newResolutionX + x] = getColor(d.x, d.y);
					
//					if(newColors[y * newResolutionX + x] == 255) {
//						System.out.println("(" + d.x + "," + d.y + ") -> (" + x + "," + y + ")");
//					}
				}
			}
			
			setRotationBounds(rotationBoundsRotatedTranslated);
			
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
						System.out.print(" ");
					} else {
						System.out.print(this.colors[y * this.resolutionX + x]);
					}
				}
				
				System.out.println();
			}
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public static Point2I translate(final Point2I point, final int x, final int y) {
			return new Point2I(point.x + x, point.y + y);
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