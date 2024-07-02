package se.lnu.eres.evidence.util;

import java.util.ArrayList;
import java.util.List;

public class CombinationManager<T> {

	protected int[] positionEachComponent;
	protected T[][] elements;
	public CombinationManager(T[][] elements) {
		super();
		this.elements=elements;
		positionEachComponent=new int[elements.length];
		
	}
	
	public CombinationManager(int sizeCombination) {
		positionEachComponent= new int[sizeCombination];
	}

	public void startCombinations() {
		for (int i=0; i< positionEachComponent.length; i++) {
			positionEachComponent[i]=0;
		}
		
		
	}

	public boolean existsNewCombination() {
		for (int i=0; i< positionEachComponent.length; i++) {
			
			if(positionEachComponent[i]<(elements[i].length-1)) {
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
		//This is a binary counter that cycles to 0
		for (int i= positionEachComponent.length -1 ; i>=0; i--) {
			
			if(positionEachComponent[i]==(elements[i].length-1)) {
				positionEachComponent[i]=0;
			}
			else {positionEachComponent[i]=positionEachComponent[i]+1;}
			
		}
	}

	private List<T> getCurrentCombination() {
		System.err.println("This should fail for the our double[][]... Override!!! ");
		List<T> combination = new ArrayList<T>();
		for (int i=0; i< positionEachComponent.length; i++) {
			combination.add(elements[i][positionEachComponent[i]]);
		}
		return combination;
	}
	

	
	

	
	
}
