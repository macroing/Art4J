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
import org.macroing.img4j.image.Image;

public final class ImageRotator {
	private ImageRotator() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
//		final Image image = new Image("./generated/Image-Original.jpg");
		
//		rotate(image, 45.0D, new Rectangle2I(new Point2I(200, 400), new Point2I(400, 400), new Point2I(400, 600), new Point2I(200, 600)));
//		rotate(image, 45.0D);
		
//		image.save("./generated/Image-Rotated.png");
		
		final Image image = new Image(5, 5);
		
		rotate(image, 45.0D);
		
		/*
		 * [T][T][T][W][T][T][T]
		 * [T][T][W][W][W][T][T]
		 * [T][W][W][W][W][W][T]
		 * [W][W][W][W][W][W][W]
		 * [T][W][W][W][W][W][T]
		 * [T][T][W][W][W][T][T]
		 * [T][T][T][W][T][T][T]
		 */
		
		System.out.print(image.getColor4D(0, 0));
		System.out.print(image.getColor4D(1, 0));
		System.out.print(image.getColor4D(2, 0));
		System.out.print(image.getColor4D(3, 0));
		System.out.print(image.getColor4D(4, 0));
		System.out.print(image.getColor4D(5, 0));
		System.out.print(image.getColor4D(6, 0));
		System.out.println();
		System.out.print(image.getColor4D(0, 1));
		System.out.print(image.getColor4D(1, 1));
		System.out.print(image.getColor4D(2, 1));
		System.out.print(image.getColor4D(3, 1));
		System.out.print(image.getColor4D(4, 1));
		System.out.print(image.getColor4D(5, 1));
		System.out.print(image.getColor4D(6, 1));
		System.out.println();
		System.out.print(image.getColor4D(0, 2));
		System.out.print(image.getColor4D(1, 2));
		System.out.print(image.getColor4D(2, 2));
		System.out.print(image.getColor4D(3, 2));
		System.out.print(image.getColor4D(4, 2));
		System.out.print(image.getColor4D(5, 2));
		System.out.print(image.getColor4D(6, 2));
		System.out.println();
		System.out.print(image.getColor4D(0, 3));
		System.out.print(image.getColor4D(1, 3));
		System.out.print(image.getColor4D(2, 3));
		System.out.print(image.getColor4D(3, 3));
		System.out.print(image.getColor4D(4, 3));
		System.out.print(image.getColor4D(5, 3));
		System.out.print(image.getColor4D(6, 3));
		System.out.println();
		System.out.print(image.getColor4D(0, 4));
		System.out.print(image.getColor4D(1, 4));
		System.out.print(image.getColor4D(2, 4));
		System.out.print(image.getColor4D(3, 4));
		System.out.print(image.getColor4D(4, 4));
		System.out.print(image.getColor4D(5, 4));
		System.out.print(image.getColor4D(6, 4));
		System.out.println();
		System.out.print(image.getColor4D(0, 5));
		System.out.print(image.getColor4D(1, 5));
		System.out.print(image.getColor4D(2, 5));
		System.out.print(image.getColor4D(3, 5));
		System.out.print(image.getColor4D(4, 5));
		System.out.print(image.getColor4D(5, 5));
		System.out.print(image.getColor4D(6, 5));
		System.out.println();
		System.out.print(image.getColor4D(0, 6));
		System.out.print(image.getColor4D(1, 6));
		System.out.print(image.getColor4D(2, 6));
		System.out.print(image.getColor4D(3, 6));
		System.out.print(image.getColor4D(4, 6));
		System.out.print(image.getColor4D(5, 6));
		System.out.print(image.getColor4D(6, 6));
		System.out.println();
		System.out.println(image.getResolutionX());
		System.out.println(image.getResolutionY());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static void rotate(final Image image, final double angle) {
		rotate(image, angle, new Rectangle2I(new Point2I(0, 0), new Point2I(image.getResolutionX() - 1, 0), new Point2I(image.getResolutionX() - 1, image.getResolutionY() - 1), new Point2I(0, image.getResolutionY() - 1)));
	}
	
	private static void rotate(final Image image, final double angle, final Rectangle2I rotationBounds) {
		final Image oldImage = image.copy();
		final Image newImage = image;
		
		final Point2I rotationBoundsMin = rotationBounds.min();
		final Point2I rotationBoundsMax = rotationBounds.max();
		final Point2I rotationBoundsMid = Point2I.midpoint(rotationBoundsMin, rotationBoundsMax);
		
		if(rotationBounds.isAxisAligned() && rotationBoundsMin.x == 0 && rotationBoundsMin.y == 0 && rotationBoundsMax.x + 1 == oldImage.getResolutionX() && rotationBoundsMax.y + 1 == oldImage.getResolutionY()) {
			final Rectangle2I rotationBoundsRotated = Rectangle2I.rotate(rotationBounds, angle, false, rotationBoundsMid);
			final Rectangle2I rotationBoundsTranslated = Rectangle2I.translateToOrigin(rotationBoundsRotated);
			
			final Point2I rotationBoundsRotatedMin = rotationBoundsRotated.min();
			final Point2I rotationBoundsRotatedMax = rotationBoundsRotated.max();
			final Point2I rotationBoundsRotatedMid = Point2I.midpoint(rotationBoundsRotatedMin, rotationBoundsRotatedMax);
			
			final Point2I rotationBoundsTranslatedMax = rotationBoundsTranslated.max();
			
			final int newResolutionX = rotationBoundsTranslatedMax.x + 1;
			final int newResolutionY = rotationBoundsTranslatedMax.y + 1;
			
			newImage.setResolution(newResolutionX, newResolutionY);
			
			for(int y = 0; y < newResolutionY; y++) {
				for(int x = 0; x < newResolutionX; x++) {
					final Point2I pointTranslated = new Point2I(x + rotationBoundsRotatedMin.x, y + rotationBoundsRotatedMin.y);
					final Point2I pointRotated = Point2I.rotate(pointTranslated, -angle, false, rotationBoundsRotatedMid);
					
					newImage.setColor4D(oldImage.getColor4D(pointRotated), x, y);
				}
			}
		} else {
			rotationBounds.findPoints().forEach(point -> {
				newImage.setColor4D(oldImage.getColor4D(Point2I.rotate(point, angle, false, rotationBoundsMid)), point);
			});
		}
	}
}