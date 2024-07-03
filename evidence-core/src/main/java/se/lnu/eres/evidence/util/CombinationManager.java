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
		for (int i = 0; i < positionEachComponent.length; i++) {
			positionEachComponent[i] = 0;
		}

	}

	public boolean existsNewCombination() {
		for (int i = 0; i < positionEachComponent.length; i++) {

			if (positionEachComponent[i] < (getElementsIthLength(i) - 1)) {
				return false;
			}

		}
		return true;
	}

	public List<T> getNextCombination() {
		List<T> combination = getCurrentCombination();
		incrementCombination();
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
