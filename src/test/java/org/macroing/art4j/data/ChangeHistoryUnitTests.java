/**
 * Copyright 2022 - 2023 J&#246;rgen Lundgren
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import org.macroing.art4j.color.Color4D;
import org.macroing.art4j.data.Color4DData.PixelChange;

@SuppressWarnings("static-method")
public final class ChangeHistoryUnitTests {
	public ChangeHistoryUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAdd() {
		final ChangeHistory changeHistory = new ChangeHistory();
		
		assertFalse(changeHistory.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0)));
		
		changeHistory.begin();
		
		assertTrue(changeHistory.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0)));
		
		assertThrows(NullPointerException.class, () -> changeHistory.add(null));
	}
	
	@Test
	public void testBegin() {
		final ChangeHistory changeHistory = new ChangeHistory();
		
		assertTrue(changeHistory.begin());
		
		assertFalse(changeHistory.begin());
	}
	
	@Test
	public void testConstructor() {
		final ChangeHistory changeHistory = new ChangeHistory();
		
		assertFalse(changeHistory.hasBegun());
	}
	
	@Test
	public void testConstructorChangeHistory() {
		final
		ChangeHistory changeHistory = new ChangeHistory();
		changeHistory.begin();
		changeHistory.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0));
		changeHistory.end();
		changeHistory.begin();
		changeHistory.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0));
		
		final ChangeHistory changeHistoryCopy = new ChangeHistory(changeHistory);
		
		assertEquals(changeHistory, changeHistoryCopy);
		
		assertThrows(NullPointerException.class, () -> new ChangeHistory(null));
	}
	
	@Test
	public void testEnd() {
		final ChangeHistory changeHistory = new ChangeHistory();
		
		assertFalse(changeHistory.end());
		
		changeHistory.begin();
		
		assertTrue(changeHistory.end());
		
		assertFalse(changeHistory.end());
		
		changeHistory.begin();
		
		changeHistory.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0));
		
		assertTrue(changeHistory.end());
		
		assertFalse(changeHistory.end());
		
		changeHistory.begin();
		
		changeHistory.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0));
		changeHistory.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0));
		
		assertTrue(changeHistory.end());
	}
	
	@Test
	public void testEquals() {
		final ChangeHistory a = new ChangeHistory();
		final ChangeHistory b = new ChangeHistory();
		final ChangeHistory c = new ChangeHistory();
		final ChangeHistory d = new ChangeHistory();
		final ChangeHistory e = new ChangeHistory();
		final ChangeHistory f = new ChangeHistory();
		final ChangeHistory g = null;
		
		final Color4DData color4DData = new Color4DData(1, 1);
		
		a.begin();
		a.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0));
		a.end();
		
		b.begin();
		b.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0));
		b.end();
		
		c.begin();
		
		d.begin();
		d.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0));
		d.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0));
		d.end();
		d.undo(color4DData);
		
		e.begin();
		e.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0));
		e.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0));
		e.end();
		
		f.begin();
		f.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0));
		f.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0));
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(a, d);
		assertNotEquals(d, a);
		assertNotEquals(a, e);
		assertNotEquals(e, a);
		
		assertNotEquals(c, f);
		assertNotEquals(f, c);
		assertNotEquals(c, g);
		assertNotEquals(g, c);
	}
	
	@Test
	public void testHasBegun() {
		final ChangeHistory changeHistory = new ChangeHistory();
		
		assertFalse(changeHistory.hasBegun());
		
		changeHistory.begin();
		
		assertTrue(changeHistory.hasBegun());
		
		changeHistory.end();
		
		assertFalse(changeHistory.hasBegun());
	}
	
	@Test
	public void testHashCode() {
		final ChangeHistory a = new ChangeHistory();
		final ChangeHistory b = new ChangeHistory();
		
		a.begin();
		a.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0));
		a.end();
		
		b.begin();
		b.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0));
		b.end();
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testRedoAndUndo() {
		final ChangeHistory changeHistory = new ChangeHistory();
		
		final Color4DData color4DData = new Color4DData(1, 1);
		
		assertFalse(changeHistory.redo(color4DData));
		assertFalse(changeHistory.undo(color4DData));
		
		changeHistory.begin();
		changeHistory.add(new PixelChange(Color4D.BLACK, Color4D.WHITE, 0));
		changeHistory.end();
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		color4DData.setColor4D(Color4D.BLACK, 0);
		
		assertFalse(changeHistory.redo(color4DData));
		
		assertEquals(Color4D.BLACK, color4DData.getColor4D(0));
		
		assertTrue(changeHistory.undo(color4DData));
		
		assertFalse(changeHistory.undo(color4DData));
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		assertTrue(changeHistory.redo(color4DData));
		
		assertFalse(changeHistory.redo(color4DData));
		
		assertEquals(Color4D.BLACK, color4DData.getColor4D(0));
		
		assertTrue(changeHistory.undo(color4DData));
		
		assertFalse(changeHistory.undo(color4DData));
		
		assertEquals(Color4D.WHITE, color4DData.getColor4D(0));
		
		assertThrows(NullPointerException.class, () -> changeHistory.redo(null));
		assertThrows(NullPointerException.class, () -> changeHistory.undo(null));
	}
}