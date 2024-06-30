package se.lnu.eres.evidence.use.sensorfusion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import se.lnu.eres.evidence.core.Discourse;
import se.lnu.eres.evidence.datatypes.Pair;
import se.lnu.eres.evidence.util.MathEvidence;

public class Decision {

	/**
	 * This method returns a list with the discourses that will decide for the
	 * "decision" subset (i.e., the belief in that set is the biggest among the
	 * possible decisions, and the possible decisions are a subset with a single
	 * element)
	 * 
	 * @param ds
	 * @param decision
	 */
	public List<Discourse> filterByDecision(Discourse[][][] ds, String decision, Set<String> possibleDecisions) {
		List<Discourse> result = new ArrayList<Discourse>();

		for (Discourse[][] d_lid : ds) {
			for (Discourse[] d_lid_cam : d_lid) {
				for (Discourse d_lid_cam_rad : d_lid_cam) {
					if (isLargestMass(d_lid_cam_rad, decision, possibleDecisions)) {
						result.add(d_lid_cam_rad);
					}
				}
			}
		}

		return result;

	}

	private boolean isLargestMass(Discourse d, String decision, Set<String> possibleDecisions) {
		double maxMassValue = 0.0;
		String elementWithMax = "";
		for (String element : possibleDecisions) {
			if (d.getMassValue(element) > maxMassValue) {
				maxMassValue = d.getMassValue(element);
				elementWithMax = element;
			}
		}
		return decision.equals(elementWithMax);
	}

	private List<Double> getBeliefsOfSubset(List<Discourse> ds, Set<String> subset) {

		List<Double> beliefs = new ArrayList<Double>();

		for (Discourse d : ds) {
			beliefs.add(d.getBelief(subset));
		}

		return beliefs;
	}

	private List<Double> getPlausibilityOfSubset(List<Discourse> ds, Set<String> subset) {

		List<Double> plausibilities = new ArrayList<Double>();

		for (Discourse d : ds) {
			plausibilities.add(d.getPlausibility(subset));
		}

		return plausibilities;
	}

	public Pair<Double, Double> createIntervalBelief(List<Discourse> ds, Set<String> subset) {

		List<Double> beliefs = getBeliefsOfSubset(ds, subset);
		return new Pair<Double, Double>(Collections.min(beliefs), Collections.max(beliefs));

	}

	public Pair<Double, Double> createIntervalBelief(List<Discourse> ds, String subset) {

		return createIntervalBelief(ds, MathEvidence.elementToSet(subset));

	}

	private Pair<Double, Double> createIntervalPlausibility(List<Discourse> ds, Set<String> subset) {

		List<Double> plausibilities = getPlausibilityOfSubset(ds, subset);
		return new Pair<Double, Double>(Collections.min(plausibilities), Collections.max(plausibilities));

	}

	public List<Pair<Double, Double>> createIntervalPlausibility(List<Discourse> ds, List<Set<String>> subsets) {
		List<Pair<Double, Double>> intervals = new ArrayList<Pair<Double, Double>>();
		for (Set<String> subset : subsets) {
			intervals.add(createIntervalPlausibility(ds, subset));
		}

		return intervals;
	}

	public List<Pair<Double, Double>> createIntervalPlausibility(List<Discourse> ds, String[] subsets) {
		List<Set<String>> ssubsets = new ArrayList<Set<String>>();
		for (String s : subsets) {
			ssubsets.add(MathEvidence.elementToSet(s));
		}

		return createIntervalPlausibility(ds, ssubsets);
	}

}
