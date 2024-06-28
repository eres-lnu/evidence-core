package se.lnu.eres.evidence.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.lnu.eres.evidence.exceptions.NotElementInDiscourseException;

public class Discourse {

	private Set<String> disc = null;

	private List<Mass> masses;

	public void clean() {
		disc = null;
		masses = null;

	}

	private Discourse(Set<String> d) {
		disc = d;
		masses = new ArrayList<Mass>();
	}

	private Discourse() {
		disc = new HashSet<String>();
		masses = new ArrayList<Mass>();
	}

	public static Discourse createDiscourse(Set<String> d) {
		return new Discourse(d);
	}

	public static Discourse createDiscourse() {
		return new Discourse();
	}

	public List<Mass> getMasses() {
		return masses;
	}

	/**
	 * @param elements
	 * @param value
	 * @return adds the value to the mass set of elements. Returns whether a new
	 *         subset of elements has been created. If the mass set of elements
	 *         already existed, it updates its value and returns false. If the mass
	 *         set of elements needs to be created, it creates it and returns true.
	 * @throws NotElementInDiscourseException
	 */
	public boolean addMass(Set<String> elements, double value) throws NotElementInDiscourseException {

		// Check if elemens are in the discourse
		for (String e : elements) {
			if (!disc.contains(e)) {
				throw new NotElementInDiscourseException("Element " + e + " does not belong to the discourse. Discouse containts {"+disc+"}");
			}
		}

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

	public boolean containsMass(Set<String> elements) {

		for (Mass m : masses) {
			if (m.equalElements(elements)) {
				return true;
			}
		}
		return false;

	}

	public void incrementMass(Set<String> elements, double value) {

		for (Mass m : masses) {
			if (m.equalElements(elements)) {
				m.setValue(m.getValue() + value);
			}
		}

	}

	/**
	 * @param elements
	 * @param value
	 * @return adds the value to the mass set of elements. Returns whether a new
	 *         subset of elements has been created. If the mass set of elements
	 *         already existed, it updates its value and returns false. If the mass
	 *         set of elements needs to be created, it creates it and returns true.
	 * @throws NotElementInDiscourseException
	 */
	public boolean addMass(String element, double value) throws NotElementInDiscourseException {
		return addMass(Mass.elementToSet(element), value);
	}

	public boolean isCorrectMasses() {
		double sum = 0.0;
		for (Mass m : masses) {
			sum += m.getValue();
		}

		return (sum > 0.998 && sum < 1.002);
	}

	public double getBelief(Set<String> element) {
		// Add the mass of all the subsets of element

		if (element == null) {
			return 0.0;
		}
		;
		double belief = 0.0;
		for (Mass m : masses) {
			if (element.containsAll(m.getElements())) {
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

	public Mass getMass(Set<String> elements) {
		for (Mass m : masses) {
			if (m.equalElements(elements)) {
				return m;
			}
		}
		return null;
	}

	public void removeMass(Set<String> emptySet) {
		masses.remove(new Mass(emptySet, 0.0)); // Create a Mass object just for satisfying the equals function
	}

	public Set<String> getOmegaSet() {
		return disc;
	}

	public void incrementOrAddAsNew(Set<String> elements, double value) throws NotElementInDiscourseException {
		if (containsMass(elements)) {
			incrementMass(elements, value);
		} else {
			addMass(elements, value);
		}

	}

	private static final String NL = System.getProperty("line.separator");
	@Override
	public String toString() {
		return "Discourse [disc=" + disc + ", masses=" + NL + masses + "]";
	}
	

}
