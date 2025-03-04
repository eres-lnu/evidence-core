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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CombinationManagerDouble extends CombinationManager<Double> {

	private static final Logger Logger = LogManager.getLogger(CombinationManagerDouble.class.getSimpleName());
	
	//This class should dissapear and: 
	// - use the combinationManager using Lists instead of arrays 
	// OR
	// - Use Double instead of double
	// the reason for this class is the arrays with generics. 
	double[][] elements;
	
	public CombinationManagerDouble(double[][] elements) {
		super(elements.length);
		this.elements=elements;
	}

	

//	private List<Double> getCurrentCombination() {
//		System.err.println("OK -> This method is executing the method from CombinationManagerDouble. ");
//		List<Double> combination = new ArrayList<Double>();
//		for (int i=0; i< positionEachComponent.length; i++) {
//			combination.add(elements[i][positionEachComponent[i]]);
//		}
//		return combination;
//	}
	

	@Override
	protected int getElementsIthLength(int i) {
		
		Logger.trace("OK -> getElementsIthLength method is executing the method from CombinationManagerDouble. ");
		return elements[i].length;
	}
	
	@Override
	public Double elementInIthPosition(int i) {
		Logger.trace("OK -> elementInIthPosition method is executing the method from CombinationManagerDouble. ");
		return elements[i][positionEachComponent[i]];
	}
	
	
}
