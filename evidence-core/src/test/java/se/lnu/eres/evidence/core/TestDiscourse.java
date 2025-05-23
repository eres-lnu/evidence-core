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

package se.lnu.eres.evidence.core;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.lnu.eres.evidence.exceptions.NotElementInDiscourseException;
import se.lnu.eres.evidence.util.MathEvidence;

class TestDiscourse {

	
	Discourse d;
	static Set<String> allElements;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		allElements=new HashSet<String>(Arrays.asList("p","b","c","t"));		
	}

	@BeforeEach
	void setUp() throws Exception {
		
		d = Discourse.createDiscourse(allElements);

	}

	@Test
	void testAddMass() throws NotElementInDiscourseException {
		d.addMass("p", 0.6);
		d.addMass("b", 0.3);
		Assertions.assertFalse(d.isCorrectMasses());
		d.addMass(allElements, 0.1);
		Assertions.assertTrue(d.isCorrectMasses());
		
	}
	
	@Test
	void testBelief() throws NotElementInDiscourseException {
		Set<String> pb = new HashSet<String>(Arrays.asList("p","b"));
		d.addMass("p", 0.5);
		d.addMass("b", 0.2);
		d.addMass("c", 0.1);
		d.addMass(pb, 0.1);
		d.addMass(allElements, 0.1);
		Assertions.assertTrue(d.isCorrectMasses());
		
		Assertions.assertEquals(0.8, MathEvidence.Round(5, d.getBelief(pb)));
		Assertions.assertEquals(0.5, MathEvidence.Round(5,d.getBelief("p")));
		Assertions.assertEquals(0.2, MathEvidence.Round(5,d.getBelief("b")));
		Assertions.assertEquals(1.0, MathEvidence.Round(5,d.getBelief(allElements)));
		
	}
	
	@Test
	void testPausibility() throws NotElementInDiscourseException {
		Set<String> pb = new HashSet<String>(Arrays.asList("p","b"));
		d.addMass("p", 0.5);
		d.addMass("b", 0.2);
		d.addMass("c", 0.1);
		d.addMass(pb, 0.1);
		d.addMass(allElements, 0.1);
		Assertions.assertTrue(d.isCorrectMasses());
		
		Assertions.assertEquals(0.9, MathEvidence.Round(5, d.getPlausibility(pb)));
		Assertions.assertEquals(0.7, MathEvidence.Round(5,d.getPlausibility("p")));
		Assertions.assertEquals(0.4, MathEvidence.Round(5,d.getPlausibility("b")));
		Assertions.assertEquals(0.2, MathEvidence.Round(5,d.getPlausibility("c")));
		Assertions.assertEquals(1.0, MathEvidence.Round(5,d.getPlausibility(allElements)));
		
	}



}
