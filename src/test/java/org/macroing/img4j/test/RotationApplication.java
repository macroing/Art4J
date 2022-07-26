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

import java.util.concurrent.atomic.AtomicReference;

import org.macroing.img4j.geometry.Point2I;
import org.macroing.img4j.geometry.shape.LineSegment2I;
import org.macroing.img4j.geometry.shape.Rectangle2I;

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
		final AtomicReference<Rectangle2I> rectangle = new AtomicReference<>(new Rectangle2I(new Point2I(400, 400), new Point2I(600, 400), new Point2I(600, 600), new Point2I(400, 600)));
		
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
				
				final Rectangle2I updatedRectangle = rectangle.updateAndGet(currentRectangle -> Rectangle2I.rotate(currentRectangle, 1.0D, false, new Point2I(500, 500)));
				
				graphicsContext.clearRect(0.0D, 0.0D, 1024.0D, 768.0D);
				graphicsContext.setFill(Color.RED);
				graphicsContext.beginPath();
				graphicsContext.moveTo(updatedRectangle.getA().x, updatedRectangle.getA().y);
				graphicsContext.lineTo(updatedRectangle.getB().x, updatedRectangle.getB().y);
				graphicsContext.lineTo(updatedRectangle.getC().x, updatedRectangle.getC().y);
				graphicsContext.lineTo(updatedRectangle.getD().x, updatedRectangle.getD().y);
				graphicsContext.lineTo(updatedRectangle.getA().x, updatedRectangle.getA().y);
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