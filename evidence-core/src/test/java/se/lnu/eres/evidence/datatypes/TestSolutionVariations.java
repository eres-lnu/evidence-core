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
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.lnu.eres.evidence.exceptions.NotDimensionNameFound;
import se.lnu.eres.evidence.exceptions.NotValueOfDimensionFound;

class TestSolutionVariations {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCreationFillRead() throws NotDimensionNameFound, NotValueOfDimensionFound {
		String[] names = new String[] {"x","y","z"};
		double[][] values = new double[][] { 	{1.0, 2.0, 3.0, 4.0}, 
												{5.0, 6.0, 7.0, 8.0},
												{9.0, 10.0, 11.0, 12.0}};
		
		SolutionVariations<Pair<Double,Double>> sols =new SolutionVariations<Pair<Double,Double>>(names, values);
		
		List<Pair<Double,Double>> sol = new ArrayList<Pair<Double,Double>>();
		sol.add(new Pair<Double,Double>(1.0,1.0));
		sols.addSolution(sol, new String[] {"z","x","y"}, new double[] {10.0, 3.0, 7.0});
		
		sol = new ArrayList<Pair<Double,Double>>();
		sol.add(new Pair<Double,Double>(2.0,2.0));
		sols.addSolution(sol, new String[] {"x","y", "z"}, new double[] {1.0, 5.0, 11.0});

		
		Assertions.assertEquals(new Pair<Double,Double>(1.0,1.0), sols.getSolution(new String[] {"x","y", "z"}, new double[] {3.0, 7.0, 10.0}).getFirst());
		Assertions.assertEquals(new Pair<Double,Double>(2.0,2.0), sols.getSolution(new String[] {"z","x","y"}, new double[] {11.0, 1.0, 5.0}).getFirst());
		
	}

}
