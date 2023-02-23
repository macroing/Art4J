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
package org.macroing.art4j.mock;

import org.macroing.art4j.data.ChangeHistory;
import org.macroing.art4j.data.ChangeHistoryObserver;
import org.macroing.art4j.data.Data;

public final class ChangeHistoryObserverMock implements ChangeHistoryObserver {
	public ChangeHistoryObserverMock() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void onRedo(final ChangeHistory changeHistory, final Data data) {
//		Do nothing.
	}
	
	@Override
	public void onUndo(final ChangeHistory changeHistory, final Data data) {
//		Do nothing.
	}
}