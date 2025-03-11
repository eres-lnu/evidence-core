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

package se.lnu.eres.evidence.datatypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.lnu.eres.evidence.exceptions.NotDimensionNameFound;
import se.lnu.eres.evidence.exceptions.NotValueOfDimensionFound;

public class Dimension<T> extends AbstractDimension<T> {

	 protected static final Logger Logger = LogManager.getLogger(Dimension.class.getSimpleName());
	
	List<AbstractDimension<T>> nextDimension;
	// The list will have as many items as different values will be considered for
	// the current dimension

	public Dimension(int pos, String[] parameterNames, double[][] allValues) {
		super(allValues[pos], parameterNames[pos]);
		// The constructor is recursive and pos indicates the position in the array that
		// is handled in the current recursion
		nextDimension = new ArrayList<AbstractDimension<T>>();

		if (pos == parameterNames.length - 2) {
			// We are in the last iteration. Single parameter, create the Dimension Value
			// directly
			
			Arrays.stream(allValues[pos]).forEach(e -> nextDimension.add(new DimensionValue<T>(parameterNames[pos+1], allValues[pos+1])));
//			for (double dummy : allValues[pos]) {
//				nextDimension.add(new DimensionValue(parameterNames[pos+1], allValues[pos+1]));
//			}
		} else {
			Arrays.stream(allValues[pos]).forEach(e -> nextDimension.add(new Dimension<T>(pos + 1, parameterNames, allValues)));
//			for (double dummy : allValues[pos]) {
//				nextDimension.add(new Dimension(pos + 1, parameterNames, allValues));
//			}

		}

	}

	@Override
	protected void addSolutionConcrete(List<T> sol, String[] correspondentNames,
			double[] correspondentParameters, int positionInDimensionValues) throws NotDimensionNameFound, NotValueOfDimensionFound {
		
		nextDimension.get(positionInDimensionValues).addSolution(sol, correspondentNames, correspondentParameters);
		
	}

	@Override
	protected List<T> getSolutionConcrete(String[] correspondentNames,
			double[] correspondentParameters, int positionInDimensionValues) throws NotValueOfDimensionFound, NotDimensionNameFound {
		
		return nextDimension.get(positionInDimensionValues).getSolution(correspondentNames, correspondentParameters);
	}

	@Override
	protected void dimensionContent(StringBuilder sb, int level, int index) {
		nextDimension.get(index).buildString(sb,level);
	}

	@Override
	protected List<String> getNamesOfDimensions() {
		List<String> name = new ArrayList<String>();
		name.add(getDimensionName());
		name.addAll(nextDimension.get(0).getNamesOfDimensions());
		return name;
	}

	@Override
	protected List<double[]> getValuesOfDimensions() {
		List<double[]> values = new ArrayList<double[]>();
		values.add(getDimensionValues());
		values.addAll(nextDimension.get(0).getValuesOfDimensions());
		return values;
	}


	
}
