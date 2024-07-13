package se.lnu.eres.evidence.use.sensorfusion;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.primitives.Doubles;

import se.lnu.eres.evidence.datatypes.SolutionVariations;
import se.lnu.eres.evidence.exceptions.NotDimensionNameFound;
import se.lnu.eres.evidence.exceptions.NotElementInDiscourseException;
import se.lnu.eres.evidence.exceptions.NotValueOfDimensionFound;
import se.lnu.eres.evidence.exceptions.NullDiscourseException;
import se.lnu.eres.evidence.util.MathEvidence;

class ParameterVariationDecisionsTest {

	private static final Logger Logger = LogManager.getLogger(ParameterVariationDecisionsTest.class.getSimpleName());
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {

		setParametersAndValues();
	}

	private void setParametersAndValues() {

		variatingParameters = new String[] { "alphap", "cc" };
		values = new double[2][];

		List<Double> valuesAlpha = new ArrayList<Double>();
		List<Double> valuescc = new ArrayList<Double>();

		for (double v = 0.5; v <= 0.6; v = v + INCREMENT) {
			valuesAlpha.add(MathEvidence.Round(5, v));
			valuescc.add(MathEvidence.Round(5, v));
		}
		values[0] = Doubles.toArray(valuesAlpha);
		values[1] = Doubles.toArray(valuescc);

	}

	private static final String filename = "Constants.properties";
	private static final double INCREMENT = 0.1;

	String[] variatingParameters;
	double[][] values;

	@Test
	void testVariate() throws FileNotFoundException, NotElementInDiscourseException, IOException, NullDiscourseException, NotDimensionNameFound, NotValueOfDimensionFound {
		
		
		ParameterVariationDecisions variations = new ParameterVariationDecisions();
		
		SolutionVariations solutions = variations.variateParameters(filename, variatingParameters, values);
		Logger.info("Result solution is: {}", solutions.toString());
	}

}
