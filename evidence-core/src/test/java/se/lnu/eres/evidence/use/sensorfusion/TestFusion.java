package se.lnu.eres.evidence.use.sensorfusion;


import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.lnu.eres.evidence.core.Discourse;
import se.lnu.eres.evidence.exceptions.NotElementInDiscourseException;
import se.lnu.eres.evidence.exceptions.NullDiscourseException;
import se.lnu.eres.evidence.util.MathEvidence;

class TestFusion {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCreateProblem() throws FileNotFoundException, NotElementInDiscourseException, IOException, NullDiscourseException {
		Fusion f = new Fusion();
		Parameters c = new Parameters("Constants.properties");
		f.createProblem("Constants.properties");
		Discourse d = f.getInitialDiscourse(SensorType.CAMERA, 1);
		Assertions.assertNotNull(d);
		Assertions.assertEquals(MathEvidence.Round(5, c.alphab * c.cc), MathEvidence.Round(5, d.getMassValue("b")));
	}

}
