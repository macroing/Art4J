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
package org.macroing.img4j.geometry;

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.Objects;

//TODO: Add Javadocs!
public final class Point2I {
//	TODO: Add Javadocs!
	public final int x;
	
//	TODO: Add Javadocs!
	public final int y;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Point2I(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("new Point2I(%s, %s)", Integer.toString(this.x), Integer.toString(this.y));
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Point2I)) {
			return false;
		} else if(this.x != Point2I.class.cast(object).x) {
			return false;
		} else if(this.y != Point2I.class.cast(object).y) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.x), Integer.valueOf(this.y));
	}
}