/**
 * Copyright 2022 - 2026 J&#246;rgen Lundgren
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

/**
 * A {@code ChangeHistoryObserver} observes changes to a {@link ChangeHistory} instance.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface ChangeHistoryObserver {
	/**
	 * Called by {@code changeHistory} when a do operation is performed.
	 * 
	 * @param changeHistory a {@link ChangeHistory} instance
	 */
	void onDo(final ChangeHistory changeHistory);
	
	/**
	 * Called by {@code changeHistory} when a redo operation is performed on {@code data}.
	 * 
	 * @param changeHistory a {@link ChangeHistory} instance
	 * @param data a {@link Data} instance
	 */
	void onRedo(final ChangeHistory changeHistory, final Data data);
	
	/**
	 * Called by {@code changeHistory} when an undo operation is performed on {@code data}.
	 * 
	 * @param changeHistory a {@link ChangeHistory} instance
	 * @param data a {@link Data} instance
	 */
	void onUndo(final ChangeHistory changeHistory, final Data data);
}