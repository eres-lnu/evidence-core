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

public class DimensionValue<T> extends AbstractDimension<T> {

	protected static final Logger Logger = LogManager.getLogger(DimensionValue.class.getSimpleName());
	
	List<List<T>> intervals;

	/*
	 * A list, each item handles an input value of the varying parameter. The item
	 * contains a list, one value for the belief and other for plausibilities (maybe this thing of 1+(N-1) may be rethought)
	 */
	public DimensionValue(String dimensionName, double[] varyingValues) {
		super(varyingValues, dimensionName);
		intervals = new ArrayList<List<T>>();
		
		Arrays.stream(varyingValues).forEach(e -> intervals.add(new ArrayList<T>()));
//		for(double v : varyingValues) {
//			intervals.add(new ArrayList<Pair<Double, Double>>());
//		}
	}

	@Override
	protected void addSolutionConcrete(List<T> sol, String[] correspondentNames,
			double[] correspondentParameters, int positionInDimensionValues) {
		intervals.set(positionInDimensionValues, sol);
		Logger.debug("Added solution {} {} in position {}", sol.toString(), NL, positionInDimensionValues);
		
	}

	@Override
	protected List<T> getSolutionConcrete(String[] correspondentNames,
			double[] correspondentParameters, int positionInDimensionValues) {
		return intervals.get(positionInDimensionValues);
	}

	@Override
	protected void dimensionContent(StringBuilder sb, int level, int index) {
		sb.append("       entering concrete dimension value with name " + getDimensionName() + " at index " + index + NL);
		List<T> indexContent = intervals.get(index);
		for(T p : indexContent) {
		appendLine(sb, level, p.toString());
		}
		
	}

	@Override
	protected List<String> getNamesOfDimensions() {
		List<String> name = new ArrayList<String>();
		name.add(getDimensionName());
		return name;
	}

	@Override
	protected List<double[]> getValuesOfDimensions() {
		List<double[]> value = new ArrayList<double[]>();
		value.add(getDimensionValues());
		return value;
	}
	
}
