package se.lnu.eres.evidence.core;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.lnu.eres.evidence.exceptions.NotElementInDiscourseException;

class TestDiscourse {

	Discourse d;
	Set<String> allElements;
	
	@BeforeAll
	void setUpBeforeClass() throws Exception {
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
		Assertions.assertFalse(d.areCorrectMasses());
		d.addMass(allElements, 0.1);
		Assertions.assertTrue(d.areCorrectMasses());
		
	}
	
	@Test
	void testBelief() throws NotElementInDiscourseException {
		Set<String> pb = new HashSet<String>(Arrays.asList("p","b"));
		d.addMass("p", 0.5);
		d.addMass("b", 0.2);
		d.addMass("c", 0.1);
		d.addMass(pb, 0.1);
		d.addMass(allElements, 0.1);
		Assertions.assertTrue(d.areCorrectMasses());
		
		Assertions.assertEquals(0.8, round(5, d.getBelief(pb)));
		Assertions.assertEquals(0.5, round(5,d.getBelief("p")));
		Assertions.assertEquals(0.2, round(5,d.getBelief("b")));
		Assertions.assertEquals(1.0, round(5,d.getBelief(allElements)));
		
	}
	
	@Test
	void testPausibility() throws NotElementInDiscourseException {
		Set<String> pb = new HashSet<String>(Arrays.asList("p","b"));
		d.addMass("p", 0.5);
		d.addMass("b", 0.2);
		d.addMass("c", 0.1);
		d.addMass(pb, 0.1);
		d.addMass(allElements, 0.1);
		Assertions.assertTrue(d.areCorrectMasses());
		
		Assertions.assertEquals(0.9, round(5, d.getPlausibility(pb)));
		Assertions.assertEquals(0.7, round(5,d.getPlausibility("p")));
		Assertions.assertEquals(0.4, round(5,d.getPlausibility("b")));
		Assertions.assertEquals(0.2, round(5,d.getPlausibility("c")));
		Assertions.assertEquals(1.0, round(5,d.getPlausibility(allElements)));
		
	}

	private Double round(int position, double value) {
		return Math.round(value*Math.pow(10, position))/Math.pow(10, position);
	}

}
