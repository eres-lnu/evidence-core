package se.lnu.eres.evidence.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Discourse {

	private Set<String> disc = null;

	private List<Mass> masses;

	private static Discourse singleton = null;

	// singleton
	private Discourse(Set<String> d) {
		singleton.disc = d;
		masses = new ArrayList<Mass>();
	}

	public static Discourse createDiscourse(Set<String> d) {
		if (singleton == null) {
			singleton = new Discourse(d);
		} else {
			throw new ExceptionInInitializerError();
		}
		return singleton;
	}

	/**
	 * @param elements
	 * @param value
	 * @return adds the value to the mass set of elements. Returns whether a new
	 *         subset of elements has been created. If the mass set of elements
	 *         already existed, it updates its value and returns false. If the mass
	 *         set of elements needs to be created, it creates it and returns true.
	 */
	public boolean addMass(Set<String> elements, double value) {

		Mass newValue = new Mass(elements, value);
		// Check if subset already exists in masses
		for (Mass m : masses) {
			if (m.equals(newValue)) {
				// Mass subset already exists, just update value
				m.setValue(m.getValue());
				return false;
			}
		}

		// add to list
		masses.add(newValue);
		return true;

	}

	public boolean areCorrectMasses() {
		double sum = 0.0;
		for (Mass m : masses) {
			sum += m.getValue();
		}

		return (sum > 0.998 && sum < 1.002);
	}

	public double getBelief(Set<String> element) {
		// Add the mass of all the subsets of element

		double belief = 0.0;
		for (Mass m : masses) {
			if (m.getElements().containsAll(element)) {
				belief += m.getValue();
			}
		}
		return belief;
	}

	public double getBelief(String element) {
		return getBelief(Mass.elementToSet(element));
	}

	public double getPlausibility(Set<String> element) {
		// Add the mass of all the subsets of element

		double plausibility = 0.0;
		boolean added;
		for (Mass m : masses) {
			added = false; // to add only once each mass
			for (String s : element) {
				if (m.getElements().contains(s) && !added) {
					plausibility += m.getValue();
					added = true;
				}
			}
		}
		return plausibility;
	}

	public double getPlausibility(String element) {
		return getPlausibility(Mass.elementToSet(element));
	}

}
