package se.lnu.eres.evidence.use.sensorfusion;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.lnu.eres.evidence.core.Discourse;
import se.lnu.eres.evidence.exceptions.NotElementInDiscourseException;
import se.lnu.eres.evidence.exceptions.NullDiscourseException;

class TestDecision {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testFilterByDecision() throws FileNotFoundException, NotElementInDiscourseException, IOException, NullDiscourseException {
		Fusion f = new Fusion();
		Discourse[][][] ds = f.createProblem("Constants.properties");
		
		Decision d = new Decision();
		List<Discourse> pedestrianDecisions = d.filterByDecision(ds, "p", f.getAllElements());
		Assertions.assertNotNull(pedestrianDecisions);
		Assertions.assertEquals(8, pedestrianDecisions.size());
		
		Pair<Double,Double> beliefInterval = d.createIntervalBelief(pedestrianDecisions, "p");
		
		String[] alternatives = new String[] {"b","c","t"};
		List<Pair<Double,Double>> plausibilityIntervals = d.createIntervalPlausibility(pedestrianDecisions, alternatives);
		
		System.out.println("Interval of beliefs is: " + beliefInterval);
		for(int i=0; i<alternatives.length; i++) {
			System.out.println("Plausiblity interval for alternative " + alternatives[i] + " is : " + plausibilityIntervals.get(i));
		}
	}

}
