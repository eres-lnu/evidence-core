package se.lnu.eres.evidence.core.combinations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.lnu.eres.evidence.core.Discourse;
import se.lnu.eres.evidence.exceptions.NotElementInDiscourseException;
import se.lnu.eres.evidence.exceptions.NullDiscourseException;

class TestYagerCombination {

	Discourse d1, d2, d3;
	static Set<String> allElements;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		allElements = new HashSet<String>(Arrays.asList("p", "b", "c", "t"));
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
		System.out.println("Test yagger with two inputs " + dr.toString());

	}

	@Test
	void testYagerCombineRecursive() throws NotElementInDiscourseException, NullDiscourseException {
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

		Set<String> pb = new HashSet<String>(Arrays.asList("p", "b"));
		d3.addMass(pb, 0.6);
		d3.addMass(allElements, 0.4);
		Assertions.assertTrue(d3.isCorrectMasses());

		YagerCombination c = new YagerCombination();
		Discourse[] ds = new Discourse[3];
		ds[0] = d1;
		ds[1] = d2;
		ds[2] = d3;
		Discourse dr = c.combine(ds);
		System.out.println("Test yagger recursive with three inputs " + dr.toString());

	}

	private Double round(int position, double value) {
		return Math.round(value * Math.pow(10, position)) / Math.pow(10, position);
	}

}
