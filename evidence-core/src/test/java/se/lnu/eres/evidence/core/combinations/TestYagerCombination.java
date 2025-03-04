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

package se.lnu.eres.evidence.core.combinations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.lnu.eres.evidence.core.Discourse;
import se.lnu.eres.evidence.exceptions.NotElementInDiscourseException;
import se.lnu.eres.evidence.exceptions.NullDiscourseException;
import se.lnu.eres.evidence.util.MathEvidence;

class TestYagerCombination {

	private static final Logger Logger = LogManager.getLogger(TestYagerCombination.class.getSimpleName());
	private double[] massResultsPaper = { 0.364, 0.144, 0.0, 0.0, 0.36, 0.132 };
	private String[][] setMassResultsPaper = { { "p" }, { "b" }, { "c" }, { "t" }, { "p", "b", "c", "t" },
			{ "p", "b" } };
	private double[] plausibResultsPaper = { 0.856, 0.636, 0.36, 0.36, 1.0 };
	private String[][] setPlausibResultsPaper = { { "p" }, { "b" }, { "c" }, { "t" }, { "p", "b", "c", "t" } };

	Discourse d1, d2, d3;
	static Set<String> allElements;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// System.setProperty("java.util.logging.config.file",
		// ClassLoader.getSystemResource("testingLogging.properties.log4j2.xml").getPath());
		allElements = new HashSet<String>(Arrays.asList("p", "b", "c", "t"));
		// System.setProperty("log4j.configurationFile",
		// ClassLoader.getSystemResource("testingLogging.properties.log4j2.xml").getPath());
	}

	@BeforeEach
	void setUp() throws Exception {

		d1 = Discourse.createDiscourse(allElements);
		d2 = Discourse.createDiscourse(allElements);
		d3 = Discourse.createDiscourse(allElements);
	}

	@Test
	void testYagerCombine() throws NotElementInDiscourseException, NullDiscourseException {
		d1.addMass("p", 0.6);
		d1.addMass("b", 0.3);
		Assertions.assertFalse(d1.isCorrectMasses());
		d1.addMass(allElements, 0.1);
		Assertions.assertTrue(d1.isCorrectMasses());

		d2.addMass("p", 0.3);
		d2.addMass("b", 0.4);
		Assertions.assertFalse(d2.isCorrectMasses());
		d2.addMass("c", 0.1);
		d2.addMass(allElements, 0.2);
		Assertions.assertTrue(d2.isCorrectMasses());

		YagerCombination c = new YagerCombination();
		Discourse dr = c.combine(d1, d2);
		Logger.info("Test yagger with two inputs {}", dr.toString());

	}

	@Test
	void testYagerCombineRecursive() throws NotElementInDiscourseException, NullDiscourseException {
		d1.addMass("p", 0.7);
		Assertions.assertFalse(d1.isCorrectMasses());
		d1.addMass(allElements, 0.3);
		Assertions.assertTrue(d1.isCorrectMasses());

		Set<String> pb = new HashSet<String>(Arrays.asList("p", "b"));
		d2.addMass("b", 0.48);
		d2.addMass(pb, 0.12);
		Assertions.assertFalse(d2.isCorrectMasses());
		d2.addMass(allElements, 0.4);
		Assertions.assertTrue(d2.isCorrectMasses());

		d3.addMass(pb, 0.8);
		d3.addMass(allElements, 0.2);
		Assertions.assertTrue(d3.isCorrectMasses());

		YagerCombination c = new YagerCombination();
		Discourse[] ds = new Discourse[3];
		ds[0] = d1;
		ds[1] = d2;
		ds[2] = d3;
		Discourse dr = c.combine(ds);

		// Assert mass results compared with calculations for paper
		for (int i = 0; i < massResultsPaper.length; i++) {
			Assertions.assertEquals(massResultsPaper[i],
					MathEvidence.Round(5, dr.getMassValue(new HashSet<String>(Arrays.asList(setMassResultsPaper[i])))));
		}
		
		// Assert Plausibility results compared with calculations for paper
		for (int i = 0; i < plausibResultsPaper.length; i++) {
			Assertions.assertEquals(plausibResultsPaper[i],
					MathEvidence.Round(5, dr.getPlausibility(new HashSet<String>(Arrays.asList(setPlausibResultsPaper[i])))));
		}
		

		Logger.info("Test yagger recursive with three inputs {} ", dr.toString());

	}

}
