/**
 * Copyright 2022 J&#246;rgen Lundgren
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

import java.util.concurrent.atomic.AtomicReference;

import org.macroing.geo4j.common.Point2I;
import org.macroing.geo4j.shape.ls.LineSegment2I;
import org.macroing.geo4j.shape.rectangle.Rectangle2I;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public final class RotationApplication extends Application {
	public RotationApplication() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void start(final Stage stage) {
		final AtomicReference<LineSegment2I> lineSegment = new AtomicReference<>(new LineSegment2I(new Point2I(400, 400), new Point2I(600, 400)));
		final AtomicReference<Rectangle2I> rectangleA = new AtomicReference<>(new Rectangle2I(new Point2I(400, 400), new Point2I(600, 400), new Point2I(600, 600), new Point2I(400, 600)));
		final AtomicReference<Rectangle2I> rectangleB = new AtomicReference<>(new Rectangle2I(new Point2I(200, 200), new Point2I(400, 200), new Point2I(400, 400), new Point2I(200, 400)));
		
		final Canvas canvas = new Canvas(1024.0D, 768.0D);
		
		final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		
		final
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(canvas);
		
		final Scene scene = new Scene(borderPane, 1024.0D, 768.0D);
		
		stage.setScene(scene);
		stage.setTitle("Rotation");
		stage.sizeToScene();
		stage.show();
		
		new AnimationTimer() {
			@Override
			public void handle(final long now) {
				final LineSegment2I updatedLineSegment = lineSegment.updateAndGet(currentLineSegment -> LineSegment2I.rotateB(currentLineSegment, -1.0D));
				
				final Rectangle2I updatedRectangleA = rectangleA.updateAndGet(currentRectangle -> Rectangle2I.rotateABCD(currentRectangle, 1.0D, false));
				final Rectangle2I updatedRectangleB = rectangleB.updateAndGet(currentRectangle -> Rectangle2I.rotate(currentRectangle, 1.0D, false, new Point2I(300, 300)));
				
				graphicsContext.clearRect(0.0D, 0.0D, 1024.0D, 768.0D);
				graphicsContext.setFill(Color.RED);
				graphicsContext.beginPath();
				graphicsContext.moveTo(updatedRectangleA.getA().x, updatedRectangleA.getA().y);
				graphicsContext.lineTo(updatedRectangleA.getB().x, updatedRectangleA.getB().y);
				graphicsContext.lineTo(updatedRectangleA.getC().x, updatedRectangleA.getC().y);
				graphicsContext.lineTo(updatedRectangleA.getD().x, updatedRectangleA.getD().y);
				graphicsContext.lineTo(updatedRectangleA.getA().x, updatedRectangleA.getA().y);
				graphicsContext.fill();
				graphicsContext.setFill(Color.GREEN);
				graphicsContext.beginPath();
				graphicsContext.moveTo(updatedRectangleB.getA().x, updatedRectangleB.getA().y);
				graphicsContext.lineTo(updatedRectangleB.getB().x, updatedRectangleB.getB().y);
				graphicsContext.lineTo(updatedRectangleB.getC().x, updatedRectangleB.getC().y);
				graphicsContext.lineTo(updatedRectangleB.getD().x, updatedRectangleB.getD().y);
				graphicsContext.lineTo(updatedRectangleB.getA().x, updatedRectangleB.getA().y);
				graphicsContext.fill();
				graphicsContext.setStroke(Color.BLACK);
				graphicsContext.beginPath();
				graphicsContext.moveTo(updatedLineSegment.getA().x, updatedLineSegment.getA().y);
				graphicsContext.lineTo(updatedLineSegment.getB().x, updatedLineSegment.getB().y);
				graphicsContext.stroke();
			}
		}.start();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
		launch(args);
	}
}