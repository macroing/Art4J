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
package org.macroing.img4j.data;

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

/**
 * A {@code ChangeHistory} represents a history of changes.
 * <p>
 * All changes in this {@code ChangeHistory} class are represented by {@link Change} instances.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ChangeHistory {
	private final Deque<Change> changesToRedo;
	private final Deque<Change> changesToUndo;
	private final List<Change> changes;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ChangeHistory} instance.
	 */
	public ChangeHistory() {
		this.changesToRedo = new ArrayDeque<>();
		this.changesToUndo = new ArrayDeque<>();
		this.changes = new ArrayList<>();
	}
	
	/**
	 * Constructs a new {@code ChangeHistory} instance from {@code changeHistory}.
	 * <p>
	 * If {@code changeHistory} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param changeHistory the {@code ChangeHistory} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code changeHistory} is {@code null}
	 */
	public ChangeHistory(final ChangeHistory changeHistory) {
		this.changesToRedo = new ArrayDeque<>(changeHistory.changesToRedo);
		this.changesToUndo = new ArrayDeque<>(changeHistory.changesToUndo);
		this.changes = new ArrayList<>(changeHistory.changes);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Change toChange() {
		return this.changes.size() == 1 ? this.changes.get(0) : new CombinedChange(getChanges());
	}
	
//	TODO: Add Javadocs!
	public List<Change> getChanges() {
		return new ArrayList<>(this.changes);
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ChangeHistory)) {
			return false;
		} else if(!Objects.equals(this.changesToRedo, ChangeHistory.class.cast(object).changesToRedo)) {
			return false;
		} else if(!Objects.equals(this.changesToUndo, ChangeHistory.class.cast(object).changesToUndo)) {
			return false;
		} else if(!Objects.equals(this.changes, ChangeHistory.class.cast(object).changes)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	public boolean redo(final Data data) {
		if(!this.changesToRedo.isEmpty()) {
			final
			Change change = this.changesToRedo.pop();
			change.redo(data);
			
			this.changesToUndo.push(change);
			
			return true;
		}
		
		return false;
	}
	
//	TODO: Add Javadocs!
	public boolean undo(final Data data) {
		if(!this.changesToUndo.isEmpty()) {
			final
			Change change = this.changesToUndo.pop();
			change.undo(data);
			
			this.changesToRedo.push(change);
			
			return true;
		}
		
		return false;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.changesToRedo, this.changesToUndo, this.changes);
	}
	
//	TODO: Add Javadocs!
	public void add(final Change change) {
		this.changes.add(Objects.requireNonNull(change, "change == null"));
	}
	
//	TODO: Add Javadocs!
	public void clear() {
		this.changes.clear();
	}
	
//	TODO: Add Javadocs!
	public void push() {
		push(toChange());
	}
	
//	TODO: Add Javadocs!
	public void push(final Change change) {
		Objects.requireNonNull(change, "change == null");
		
		this.changesToRedo.clear();
		this.changesToUndo.push(change);
	}
}