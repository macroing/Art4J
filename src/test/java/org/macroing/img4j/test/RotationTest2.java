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

import org.macroing.img4j.geometry.Point2I;
import org.macroing.img4j.geometry.shape.Rectangle2I;
import org.macroing.img4j.utility.Doubles;

public final class RotationTest2 {
	private RotationTest2() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
		final double angle = 45.0D;
		
		final int oldResolutionX = 100;
		final int oldResolutionY = 100;
		
		final char[][] a = new char[oldResolutionY][oldResolutionX];
		
		for(int y = 0; y < oldResolutionY; y++) {
			for(int x = 0; x < oldResolutionX; x++) {
				a[y][x] = (char)(65 + y % 10);
			}
		}
		
		final char[][] b = rotate2(a, angle);
		final char[][] c = rotate2(b, angle);
		
		/*
		 * [0,0] -> [0,2]
		 * [1,0] -> [0,1]
		 * [2,0] -> [0,0]
		 * 
		 * [CBA]    [AAA]
		 * [CBA]    [BBB]
		 * [CBA]    [CCC]
		 */
		
		print(a);
		print(b);
		print(c);
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
	
	public static char[][] rotate(final char[][] oldColors, final double angle) {
		final int oldResolutionX = oldColors[0].length;
		final int oldResolutionY = oldColors.length;
		
		final Rectangle2I rotationBounds = new Rectangle2I(new Point2I(0, 0), new Point2I(oldResolutionX - 1, 0), new Point2I(oldResolutionX - 1, oldResolutionY - 1), new Point2I(0, oldResolutionY - 1));
		final Rectangle2I rotationBoundsTranslated = translate(rotationBounds, -oldResolutionX / 2, -oldResolutionY / 2);
		final Rectangle2I rotationBoundsRotated = Rectangle2I.rotate(rotationBoundsTranslated, angle, false, new Point2I());
		
		final Point2I min = rotationBoundsRotated.min();
		final Point2I max = rotationBoundsRotated.max();
		
		final int newResolutionX = max.x - min.x + 1;
		final int newResolutionY = max.y - min.y + 1;
		
		final char[][] newColors = new char[newResolutionY][newResolutionX];
		
//		final Rectangle2I rotationBoundsRotatedTranslated = translate(rotationBoundsRotated, newResolutionX / 2, newResolutionY / 2);
		
//		System.out.println(rotationBounds);
//		System.out.println(rotationBoundsTranslated);
//		System.out.println(rotationBoundsRotated);
//		System.out.println(rotationBoundsRotatedTranslated);
//		System.out.println(min);
//		System.out.println(max);
//		System.out.println(oldResolutionX + " -> " + newResolutionX);
//		System.out.println(oldResolutionY + " -> " + newResolutionY);
		
		for(int y = 0; y < newResolutionY; y++) {
			for(int x = 0; x < newResolutionX; x++) {
				final Point2I a = new Point2I(x, y);
				final Point2I b = translate(a, -newResolutionX / 2, -newResolutionY / 2);
				final Point2I c = Point2I.rotate(b, -angle, false, new Point2I());
				final Point2I d = translate(c, oldResolutionX / 2, oldResolutionY / 2 - (oldResolutionY % 2 == 0 ? 1 : 0));
				
				if(d.x >= 0 && d.x < oldResolutionX && d.y >= 0 && d.y < oldResolutionY) {
					newColors[y][x] = oldColors[d.y][d.x];
				} else {
					newColors[y][x] = ' ';
				}
				
//				System.out.println(a);
//				System.out.println(b);
//				System.out.println(c);
//				System.out.println(d);
			}
		}
		
		return newColors;
	}
	
	public static char[][] rotate2(final char[][] oldColors, final double angle) {
		final int oldResolutionX = oldColors[0].length;
		final int oldResolutionY = oldColors.length;
		
		final double angleRadians = Doubles.toRadians(angle);
		final double angleRadiansCos = Doubles.cos(angleRadians);
		final double angleRadiansSin = Doubles.sin(angleRadians);
		
		final double directionAX = -oldResolutionX * 0.5D;
		final double directionAY = -oldResolutionY * 0.5D;
		
		final double rectangleAAX = directionAX;
		final double rectangleAAY = directionAY;
		final double rectangleABX = directionAX;
		final double rectangleABY = directionAY + oldResolutionY;
		final double rectangleACX = directionAX + oldResolutionX;
		final double rectangleACY = directionAY + oldResolutionY;
		final double rectangleADX = directionAX + oldResolutionX;
		final double rectangleADY = directionAY;
		
		final double rectangleBAX = rectangleAAX * angleRadiansCos - rectangleAAY * angleRadiansSin;
		final double rectangleBAY = rectangleAAY * angleRadiansCos + rectangleAAX * angleRadiansSin;
		final double rectangleBBX = rectangleABX * angleRadiansCos - rectangleABY * angleRadiansSin;
		final double rectangleBBY = rectangleABY * angleRadiansCos + rectangleABX * angleRadiansSin;
		final double rectangleBCX = rectangleACX * angleRadiansCos - rectangleACY * angleRadiansSin;
		final double rectangleBCY = rectangleACY * angleRadiansCos + rectangleACX * angleRadiansSin;
		final double rectangleBDX = rectangleADX * angleRadiansCos - rectangleADY * angleRadiansSin;
		final double rectangleBDY = rectangleADY * angleRadiansCos + rectangleADX * angleRadiansSin;
		
		final double minimumX = Doubles.min(rectangleBAX, rectangleBBX, rectangleBCX, rectangleBDX);
		final double minimumY = Doubles.min(rectangleBAY, rectangleBBY, rectangleBCY, rectangleBDY);
		final double maximumX = Doubles.max(rectangleBAX, rectangleBBX, rectangleBCX, rectangleBDX);
		final double maximumY = Doubles.max(rectangleBAY, rectangleBBY, rectangleBCY, rectangleBDY);
		
		final int newResolutionX = (int)(maximumX - minimumX);
		final int newResolutionY = (int)(maximumY - minimumY);
		
		final char[][] newColors = new char[newResolutionY][newResolutionX];
		
		final double directionBX = Doubles.abs(Doubles.min(minimumX, 0.0D));
		final double directionBY = Doubles.abs(Doubles.min(minimumY, 0.0D));
		
		for(int y = 0; y < newResolutionY; y++) {
			for(int x = 0; x < newResolutionX; x++) {
				final double aX = x - directionBX;
				final double aY = y - directionBY;
				
				final double bX = aX * angleRadiansCos - aY * -angleRadiansSin;
				final double bY = aY * angleRadiansCos + aX * -angleRadiansSin;
				
				final int cX = (int)(bX - directionAX - 0.5D);
				final int cY = (int)(bY - directionAY - 0.5D);
				
				if(cX >= 0 && cX < oldResolutionX && cY >= 0 && cY < oldResolutionY) {
					newColors[y][x] = oldColors[cY][cX];
				} else {
					newColors[y][x] = ' ';
				}
			}
		}
		
		return newColors;
	}
	
	public static void print(final char[][] colors) {
		System.out.println();
		
		for(int y = 0; y < colors.length; y++) {
			for(int x = 0; x < colors[y].length; x++) {
				System.out.print(colors[y][x]);
			}
			
			System.out.println();
		}
	}
}