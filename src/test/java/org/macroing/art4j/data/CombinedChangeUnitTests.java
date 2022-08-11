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
package org.macroing.art4j.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.macroing.art4j.color.Color4D;
import org.macroing.art4j.data.Color4DData.PixelChange;

@SuppressWarnings("static-method")
public final class CombinedChangeUnitTests {
	public CombinedChangeUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructor() {
		final List<Change> changes = new ArrayList<>();
		
		changes.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0));
		changes.add(new PixelChange(Color4D.GREEN, Color4D.BLACK, 0));
		
		final CombinedChange combinedChange = new CombinedChange(changes);
		
		assertEquals(changes, combinedChange.getChanges());
		
		assertThrows(NullPointerException.class, () -> new CombinedChange(null));
		assertThrows(NullPointerException.class, () -> new CombinedChange(Arrays.asList(new Change[] {null})));
	}
	
	@Test
	public void testEquals() {
		final CombinedChange a = new CombinedChange(Arrays.asList(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0)));
		final CombinedChange b = new CombinedChange(Arrays.asList(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0)));
		final CombinedChange c = new CombinedChange(Arrays.asList(new PixelChange(Color4D.GREEN, Color4D.BLACK, 0)));
		final CombinedChange d = null;
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(a, d);
		assertNotEquals(d, a);
	}
	
	@Test
	public void testHashCode() {
		final CombinedChange a = new CombinedChange(Arrays.asList(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0)));
		final CombinedChange b = new CombinedChange(Arrays.asList(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0)));
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testRedoAndUndo() {
		final Color4DData color4DData = new Color4DData(1, 1);
		
		final List<Change> changes = new ArrayList<>();
		
		changes.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0));
		changes.add(new PixelChange(Color4D.GREEN, Color4D.BLACK, 0));
		
		final CombinedChange combinedChange = new CombinedChange(changes);
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		combinedChange.redo(color4DData);
		
		assertEquals(Color4D.GREEN, color4DData.getColor4D(0));
		
		combinedChange.undo(color4DData);
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		assertThrows(NullPointerException.class, () -> combinedChange.redo(null));
		assertThrows(NullPointerException.class, () -> combinedChange.undo(null));
	}
}