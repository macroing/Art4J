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

import org.macroing.img4j.color.Color4D;
import org.macroing.img4j.color.Color4I;
import org.macroing.img4j.data.Data;
import org.macroing.img4j.data.DataFactory;
import org.macroing.img4j.geometry.Point2I;
import org.macroing.img4j.geometry.shape.Circle2I;
import org.macroing.img4j.image.Image;
import org.macroing.img4j.kernel.ConvolutionKernelND;

public final class Test {
	private Test() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
		final DataFactory dataFactory = DataFactory.forColor4D();
//		final DataFactory dataFactory = DataFactory.forColorARGB();
		
		final Data data = dataFactory.create("./generated/Image-Original.jpg");
		
		final Image image = new Image(data);
		
		final long a = System.currentTimeMillis();
		
		image.setChangeHistoryEnabled(true);
		image.flipX();
		image.undo();
		image.redo();
		image.draw(g -> g.drawString("Hello, World!", 100, 100));
		image.fillColorARGB((colorARGB, x, y) -> Color4I.sepiaARGB(colorARGB));
		image.fillColor4D((color, point) -> Color4D.sepia(color));
		image.fillShapeColor4D(new Circle2I(new Point2I(100, 100), 50), Color4D.RED);
		image.convolveColor4D(ConvolutionKernelND.EMBOSS_3);
		image.rotate(45.0D);
		image.rotate(45.0D);
		image.save("./generated/Image.png");
		
		final long b = System.currentTimeMillis();
		final long c = b - a;
		
		System.out.println("Image took " + c + " milliseconds.");
	}
}