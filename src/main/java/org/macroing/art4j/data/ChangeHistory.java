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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A {@code ChangeHistory} represents a change history.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ChangeHistory {
	private final AtomicBoolean hasBegun;
	private final List<Change> changes;
	private final List<Change> changesToRedo;
	private final List<Change> changesToUndo;
	private final List<ChangeHistoryObserver> changeHistoryObservers;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ChangeHistory} instance.
	 */
	public ChangeHistory() {
		this.hasBegun = new AtomicBoolean();
		this.changes = new ArrayList<>();
		this.changesToRedo = new ArrayList<>();
		this.changesToUndo = new ArrayList<>();
		this.changeHistoryObservers = new ArrayList<>();
	}
	
	/**
	 * Constructs a new {@code ChangeHistory} instance from {@code changeHistory}.
	 * <p>
	 * If {@code changeHistory} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param changeHistory a {@code ChangeHistory} instance
	 * @throws NullPointerException thrown if, and only if, {@code changeHistory} is {@code null}
	 */
	public ChangeHistory(final ChangeHistory changeHistory) {
		this.hasBegun = new AtomicBoolean(changeHistory.hasBegun.get());
		this.changes = new ArrayList<>(changeHistory.changes);
		this.changesToRedo = new ArrayList<>(changeHistory.changesToRedo);
		this.changesToUndo = new ArrayList<>(changeHistory.changesToUndo);
		this.changeHistoryObservers = new ArrayList<>(changeHistory.changeHistoryObservers);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@link ChangeHistoryObserver} instances currently added to this {@code ChangeHistory} instance.
	 * 
	 * @return a {@code List} with all {@code ChangeHistoryObserver} instances currently added to this {@code ChangeHistory} instance
	 */
	public List<ChangeHistoryObserver> getChangeHistoryObservers() {
		return new ArrayList<>(this.changeHistoryObservers);
	}
	
	/**
	 * Adds {@code change} to this {@code ChangeHistory} instance.
	 * <p>
	 * Returns {@code true} if, and only if, {@code change} was added, {@code false} otherwise.
	 * <p>
	 * If {@code change} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * For {@code change} to be added, {@code begin()} has to be called first. If {@code hasBegun()} returns {@code true}, {@code begin()} has been called.
	 * 
	 * @param change the {@link Change} instance to add
	 * @return {@code true} if, and only if, {@code change} was added, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code change} is {@code null}
	 */
	public boolean add(final Change change) {
		Objects.requireNonNull(change, "change == null");
		
		if(hasBegun()) {
			this.changes.add(change);
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Adds {@code changeHistoryObserver} to this {@code ChangeHistory} instance.
	 * <p>
	 * Returns {@code true} if, and only if, {@code changeHistoryObserver} was added, {@code false} otherwise.
	 * <p>
	 * If {@code changeHistoryObserver} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param changeHistoryObserver the {@link ChangeHistoryObserver} instance to add
	 * @return {@code true} if, and only if, {@code changeHistoryObserver} was added, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code changeHistoryObserver} is {@code null}
	 */
	public boolean addChangeHistoryObserver(final ChangeHistoryObserver changeHistoryObserver) {
		return this.changeHistoryObservers.add(Objects.requireNonNull(changeHistoryObserver, "changeHistoryObserver == null"));
	}
	
	/**
	 * Performs a begin operation.
	 * <p>
	 * Returns {@code true} if, and only if, a change has not already begun, {@code false} otherwise.
	 * <p>
	 * If {@code begin()} has already been called and {@code end()} has not, {@code false} will be returned.
	 * 
	 * @return {@code true} if, and only if, a change has not already begun, {@code false} otherwise
	 */
	public boolean begin() {
		if(this.hasBegun.compareAndSet(false, true)) {
			this.changes.clear();
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns {@code true} if, and only if, a redo operation can be performed, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, a redo operation can be performed, {@code false} otherwise
	 */
	public boolean canRedo() {
		return !this.changesToRedo.isEmpty();
	}
	
	/**
	 * Returns {@code true} if, and only if, an undo operation can be performed, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, an undo operation can be performed, {@code false} otherwise
	 */
	public boolean canUndo() {
		return !this.changesToUndo.isEmpty();
	}
	
	/**
	 * Performs an end operation.
	 * <p>
	 * Returns {@code true} if, and only if, a change has already begun, {@code false} otherwise.
	 * <p>
	 * If {@code begin()} has not been called, {@code false} will be returned.
	 * 
	 * @return {@code true} if, and only if, a change has already begun, {@code false} otherwise
	 */
	public boolean end() {
		if(this.hasBegun.compareAndSet(true, false)) {
			if(this.changes.size() > 0) {
				final Change change = this.changes.size() == 1 ? this.changes.get(0) : new CombinedChange(this.changes);
				
				this.changesToRedo.clear();
				this.changesToUndo.add(change);
				this.changes.clear();
				
				for(final ChangeHistoryObserver changeHistoryObserver : this.changeHistoryObservers) {
					changeHistoryObserver.onDo(this);
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Compares {@code object} to this {@code ChangeHistory} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ChangeHistory}, and they are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ChangeHistory} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ChangeHistory}, and they are equal, {@code false} otherwise
	 */
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
	
	/**
	 * Returns {@code true} if, and only if, a change has already begun, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, a change has already begun, {@code false} otherwise
	 */
	public boolean hasBegun() {
		return this.hasBegun.get();
	}
	
	/**
	 * Performs the current redo operation.
	 * <p>
	 * Returns {@code true} if, and only if, the redo operation was performed, {@code false} otherwise.
	 * <p>
	 * If {@code data} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param data the {@link Data} to perform the redo operation against
	 * @return {@code true} if, and only if, the redo operation was performed, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code data} is {@code null}
	 */
	public boolean redo(final Data data) {
		Objects.requireNonNull(data, "data == null");
		
		if(!this.changesToRedo.isEmpty()) {
			final
			Change change = this.changesToRedo.remove(this.changesToRedo.size() - 1);
			change.redo(data);
			
			this.changesToUndo.add(change);
			
			for(final ChangeHistoryObserver changeHistoryObserver : this.changeHistoryObservers) {
				changeHistoryObserver.onRedo(this, data);
			}
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Removes {@code changeHistoryObserver} from this {@code ChangeHistory} instance.
	 * <p>
	 * Returns {@code true} if, and only if, {@code changeHistoryObserver} was removed, {@code false} otherwise.
	 * <p>
	 * If {@code changeHistoryObserver} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param changeHistoryObserver the {@link ChangeHistoryObserver} instance to remove
	 * @return {@code true} if, and only if, {@code changeHistoryObserver} was removed, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code changeHistoryObserver} is {@code null}
	 */
	public boolean removeChangeHistoryObserver(final ChangeHistoryObserver changeHistoryObserver) {
		return this.changeHistoryObservers.remove(Objects.requireNonNull(changeHistoryObserver, "changeHistoryObserver == null"));
	}
	
	/**
	 * Performs the current undo operation.
	 * <p>
	 * Returns {@code true} if, and only if, the undo operation was performed, {@code false} otherwise.
	 * <p>
	 * If {@code data} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param data the {@link Data} to perform the undo operation against
	 * @return {@code true} if, and only if, the undo operation was performed, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code data} is {@code null}
	 */
	public boolean undo(final Data data) {
		Objects.requireNonNull(data, "data == null");
		
		if(!this.changesToUndo.isEmpty()) {
			final
			Change change = this.changesToUndo.remove(this.changesToUndo.size() - 1);
			change.undo(data);
			
			this.changesToRedo.add(change);
			
			for(final ChangeHistoryObserver changeHistoryObserver : this.changeHistoryObservers) {
				changeHistoryObserver.onUndo(this, data);
			}
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns a hash code for this {@code ChangeHistory} instance.
	 * 
	 * @return a hash code for this {@code ChangeHistory} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Boolean.valueOf(this.hasBegun.get()), this.changesToRedo, this.changesToUndo, this.changes);
	}
}