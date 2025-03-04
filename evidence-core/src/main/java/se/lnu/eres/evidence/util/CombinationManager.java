/**
 * Copyright 2025 EReS research Lab - Linnaeus University
 * Contact: https://lnu.se/en/research/research-groups/engineering-resilient-systems-eres/
 * 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Contributors: 
 * 		Diego Perez
 */

package se.lnu.eres.evidence.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CombinationManager<T> {

	private static final Logger Logger = LogManager.getLogger(CombinationManager.class.getSimpleName());
	
	protected int[] positionEachComponent;
	protected T[][] elements;

	public CombinationManager(T[][] elements) {
		super();
		this.elements = elements;
		positionEachComponent = new int[elements.length];

	}

	public CombinationManager(int sizeCombination) {
		positionEachComponent = new int[sizeCombination];
	}

	public void startCombinations() {
		for (int i = 0; i < positionEachComponent.length-1; i++) {
			positionEachComponent[i] = 0;
		}
		positionEachComponent[positionEachComponent.length-1]=-1;

	}

	public boolean existsNewCombination() {
		for (int i = 0; i < positionEachComponent.length; i++) {

			if (positionEachComponent[i] < (getElementsIthLength(i) - 1)) {
				return true;
			}

		}
		return false;
	}

	public List<T> getNextCombination() {
		incrementCombination();
		List<T> combination = getCurrentCombination();
		return combination;
	}

	private void incrementCombination() {
		// This is a binary counter that cycles to 0
		for (int i = positionEachComponent.length - 1; i >= 0; i--) {

			if (positionEachComponent[i] == (getElementsIthLength(i) - 1)) {
				positionEachComponent[i] = 0;
			} else {
				positionEachComponent[i] = positionEachComponent[i] + 1;
				return;
			}

		}
	}

	protected int getElementsIthLength(int i) {
		return elements[i].length;
	}

	private List<T> getCurrentCombination() {
		List<T> combination = new ArrayList<T>();
		for (int i = 0; i < positionEachComponent.length; i++) {
			combination.add(elementInIthPosition(i));

		}
		return combination;
	}


	protected T elementInIthPosition(int i) {
		Logger.warn("This should fail for the our double[][]... Override!!! ");
		return elements[i][positionEachComponent[i]];
	}

}
