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

import java.util.List;

import se.lnu.eres.evidence.exceptions.NotDimensionNameFound;
import se.lnu.eres.evidence.exceptions.NotValueOfDimensionFound;

public class SolutionVariations<T> {

	// Based on: List<List<List<Pair<Double, Double>>>>

	/*
	 * * In each Pair there is an interval <min,max>, in a structure of N elements (1 for
	 * the belief and N-1 plausibilities), in a list for each of the values of a
	 * parameter change, in a list for all the parameters changed
	 */
	
	AbstractDimension<T> solution;
	
	public SolutionVariations(String[] parameterNames, double[][] values) {
		if(parameterNames.length==1) {
			//Single parameter, create the Dimension Value directly
			solution= new DimensionValue<T>(parameterNames[0], values[0]);
		}
		else {solution= new Dimension<T>(0,parameterNames, values);}
		
	}
	
	public void addSolution(List<Pair<T, T>> sol, String[] correspondentNames, double[] correspondentParameters) throws NotDimensionNameFound, NotValueOfDimensionFound {
	
			solution.addSolution(sol, correspondentNames, correspondentParameters);
		
	}

	public List<Pair<T, T>> getSolution(String[] correspondentNames, double[] correspondentParameters) throws NotValueOfDimensionFound, NotDimensionNameFound {
		return solution.getSolution(correspondentNames, correspondentParameters);
	}

	@Override
	public String toString() {
		return "SolutionVariations [" + System.getProperty("line.separator") + solution.toString(0) + "]";
	}
	
	


}
