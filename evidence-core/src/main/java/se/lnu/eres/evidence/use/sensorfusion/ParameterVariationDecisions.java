package se.lnu.eres.evidence.use.sensorfusion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import com.google.common.primitives.Doubles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.lnu.eres.evidence.core.Discourse;
import se.lnu.eres.evidence.datatypes.Pair;
import se.lnu.eres.evidence.datatypes.SolutionVariations;
import se.lnu.eres.evidence.exceptions.NotDimensionNameFound;
import se.lnu.eres.evidence.exceptions.NotElementInDiscourseException;
import se.lnu.eres.evidence.exceptions.NotValueOfDimensionFound;
import se.lnu.eres.evidence.exceptions.NullDiscourseException;
import se.lnu.eres.evidence.util.CombinationManager;
import se.lnu.eres.evidence.util.CombinationManagerDouble;

public class ParameterVariationDecisions {

	private static final Logger Logger = LogManager.getLogger(ParameterVariationDecisions.class.getSimpleName());

	/**
	 * In each Pair there is an interval <min,max>, in a list of 4 elements (1 for
	 * the belief and 3 plausibilities), in a list for each of the values of a
	 * parameter change, in a list for all the parameters changed
	 * 
	 * @param parametersFilename
	 * @param variatingParameters The names of the parameters to change
	 * @param values              The values for each of the parameters to change
	 * @return the beliefs and plausibilities for each of the values of each of the
	 *         parameters to variate
	 * @throws FileNotFoundException
	 * @throws NotElementInDiscourseException
	 * @throws IOException
	 * @throws NullDiscourseException
	 * @throws NotValueOfDimensionFound
	 * @throws NotDimensionNameFound
	 */
	public SolutionVariations variateParameters(String parametersFilename, String[] variatingParameters,
			double[][] values) throws FileNotFoundException, NotElementInDiscourseException, IOException,
			NullDiscourseException, NotDimensionNameFound, NotValueOfDimensionFound {

		SolutionVariations solution = new SolutionVariations(variatingParameters, values);
		Fusion f = new Fusion();
		Discourse[][][] discourses = f.createProblem(parametersFilename);
		Parameters p = f.getParameters();
		Decision decision = new Decision();

		if (variatingParameters.length != values.length) {
			Logger.error(
					"The size of parameters to change must correspond to the size of the values provided, but it is {} parameters to change and {} lists of values to change",
					variatingParameters.length, values.length);
		}

		// For each combination of parameters to change:
		// update the parameters p with the corresponiding values,
		// solve the problem,
		// filter the pedestrians
		// add the solution to the structure of solutions.

		CombinationManager<Double> c = new CombinationManagerDouble(values);
		c.startCombinations();
		Logger.trace("Entering the loop for the exists new combination");
		while (c.existsNewCombination()) {
			// Values combination are int he same order as variatingParameters
			List<Double> valuesCombination = c.getNextCombination();
			Logger.debug("solving the parameter combination of: {}", valuesCombination.toString());
			updateAllValuesInParameters(p, variatingParameters, valuesCombination);
			Logger.trace("Values of parameters after update is {}", p.toString());
			discourses = f.createProblem(p);

			solution.addSolution(filterPedestrianResults(decision, discourses, f), variatingParameters,
					Doubles.toArray(valuesCombination));
			// ArrayUtils.toPrimitive( (Double) valuesCombination.toArray()));

		}

		return solution;

	}

	private List<Pair<Double, Double>> filterPedestrianResults(Decision decision, Discourse[][][] discourses,
			Fusion fusion) {
		List<Discourse> pedestrianDecisions = decision.filterByDecision(discourses, "p", fusion.getAllElements());
		Pair<Double, Double> beliefInterval = decision.createIntervalBelief(pedestrianDecisions, "p");

		String[] alternatives = new String[] { "b", "c", "t" };
		List<Pair<Double, Double>> plausibilityIntervals = decision.createIntervalPlausibility(pedestrianDecisions,
				alternatives);

		plausibilityIntervals.addFirst(beliefInterval);
		return plausibilityIntervals;
	}

	private void updateAllValuesInParameters(Parameters p, String[] variatingParameters,
			List<Double> valuesCombination) {
		for (int i = 0; i < variatingParameters.length; i++) {
			p.updatePropertyValue(variatingParameters[i], valuesCombination.get(i).toString());
		}
		p.reloadValuesFromProperties();

	}

}
