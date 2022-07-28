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

import java.lang.reflect.Field;//TODO: Add Unit Tests!
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

final class ChangeHistory {
	private final AtomicBoolean hasBegun;
	private final List<Change> changes;
	private final List<Change> changesToRedo;
	private final List<Change> changesToUndo;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ChangeHistory() {
		this.hasBegun = new AtomicBoolean();
		this.changes = new ArrayList<>();
		this.changesToRedo = new ArrayList<>();
		this.changesToUndo = new ArrayList<>();
	}
	
	public ChangeHistory(final ChangeHistory changeHistory) {
		this.hasBegun = new AtomicBoolean(changeHistory.hasBegun.get());
		this.changes = new ArrayList<>(changeHistory.changes);
		this.changesToRedo = new ArrayList<>(changeHistory.changesToRedo);
		this.changesToUndo = new ArrayList<>(changeHistory.changesToUndo);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Unit Tests!
	public boolean add(final Change change) {
		Objects.requireNonNull(change, "change == null");
		
		if(hasBegun()) {
			this.changes.add(change);
			
			return true;
		}
		
		return false;
	}
	
//	TODO: Add Unit Tests!
	public boolean begin() {
		if(this.hasBegun.compareAndSet(false, true)) {
			this.changes.clear();
			
			return true;
		}
		
		return false;
	}
	
//	TODO: Add Unit Tests!
	public boolean end() {
		if(this.hasBegun.compareAndSet(true, false)) {
			if(this.changes.size() > 0) {
				final Change change = this.changes.size() == 1 ? this.changes.get(0) : new CombinedChange(this.changes);
				
				this.changesToRedo.clear();
				this.changesToUndo.add(change);
				this.changes.clear();
			}
			
			return true;
		}
		
		return false;
	}
	
//	TODO: Add Unit Tests!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ChangeHistory)) {
			return false;
		} else if(this.hasBegun.get() != ChangeHistory.class.cast(object).hasBegun.get()) {
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
	
	public boolean hasBegun() {
		return this.hasBegun.get();
	}
	
	public boolean redo(final Data data) {
		Objects.requireNonNull(data, "data == null");
		
		if(!this.changesToRedo.isEmpty()) {
			final
			Change change = this.changesToRedo.remove(this.changesToRedo.size() - 1);
			change.redo(data);
			
			this.changesToUndo.add(change);
			
			return true;
		}
		
		return false;
	}
	
	public boolean undo(final Data data) {
		Objects.requireNonNull(data, "data == null");
		
		if(!this.changesToUndo.isEmpty()) {
			final
			Change change = this.changesToUndo.remove(this.changesToUndo.size() - 1);
			change.undo(data);
			
			this.changesToRedo.add(change);
			
			return true;
		}
		
		return false;
	}
	
//	TODO: Add Unit Tests!
	@Override
	public int hashCode() {
		return Objects.hash(Boolean.valueOf(this.hasBegun.get()), this.changesToRedo, this.changesToUndo, this.changes);
	}
}