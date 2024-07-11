package se.lnu.eres.evidence.core.combinations;


import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.lnu.eres.evidence.core.Discourse;
import se.lnu.eres.evidence.core.Mass;
import se.lnu.eres.evidence.exceptions.NotElementInDiscourseException;
import se.lnu.eres.evidence.exceptions.NullDiscourseException;

public class YagerCombination {

	private static final Logger Logger = LogManager.getLogger(YagerCombination.class.getSimpleName());

	public Discourse combine(Discourse[] ds) throws NullDiscourseException, NotElementInDiscourseException {
		
		String nameResultDiscourse="";
		for (Discourse d : ds) {
			if (d == null) {
				throw new NullDiscourseException("The array of discourse contains some null element");
			}
			//taking advantage of this iteration to build the name of the combined discourse
			nameResultDiscourse+=d.getName()+"|";
		}
		//remove the last "|" in the name 
		nameResultDiscourse=nameResultDiscourse.substring(0, nameResultDiscourse.length()-1);
		

		
		Discourse result = Discourse.createDiscourse(ds[0].getOmegaSet(), nameResultDiscourse);

		result = combineYagerRecursive(0, result, ds, new HashSet<String>(result.getOmegaSet()), 1.0);
		addEmptySetMassesToOmega(result);
		return result;
	}

	private Discourse combineYagerRecursive(int pos, Discourse result, Discourse[] ds, Set<String> elementsIn,
			double partialMassValue) throws NotElementInDiscourseException {
		// If pos is over the ds elelents (ds.lenght), update the masses in result
		// If pos is not the last discourse, Calculate intersection with the discourse
		// in position pos and call recursively for each of its masses.
		if (pos == ds.length) {
			result.incrementOrAddAsNew(elementsIn, partialMassValue);
			Logger.debug("Adding to the result set elements={} , a value partialMAssValue={}",elementsIn.toString(), partialMassValue);

		} else {

			for (Mass m : ds[pos].getMasses()) {
				Set<String> elements = new HashSet<String>(elementsIn);
				elements.retainAll(m.getElements());
				Logger.debug("pos={} , elements={} , partialMassValue={}", pos, elements.toString(), partialMassValue);
				combineYagerRecursive(pos + 1, result, ds, elements, partialMassValue * m.getValue());
			}
		}

		return result;

	}

	public Discourse combine(Discourse d1, Discourse d2) throws NullDiscourseException, NotElementInDiscourseException {
		if (d1 == null || d2 == null) {
			throw new NullDiscourseException();
		}
		Discourse result = Discourse.createDiscourse(d1.getOmegaSet(), d1.getName()+"|"+d2.getName());

		// Calculate the intersections of all sets in the two discourses. The mass of a
		// resulting element is the sum of all intersections that resulted in that
		// element. For each of the resulting intersection, the value to add is the
		// product of the masses of the intersected sets

		for (Mass m1 : d1.getMasses()) {
			for (Mass m2 : d2.getMasses()) {

				Set<String> intersection = m1.intersect(m2);
				double intersectionValue = m1.getValue() * m2.getValue();

				result.incrementOrAddAsNew(intersection, intersectionValue);

			}
		}

		// Fix the mass of omega. At this point it may exist or it may not.
		// It must get its value incremented by the mass of the empty set, and remove
		// the
		// empty set from the list of masses

		addEmptySetMassesToOmega(result);

		return result;
	}

	private void addEmptySetMassesToOmega(Discourse result) throws NotElementInDiscourseException {
		Set<String> emptySet = new HashSet<String>();
		if (result.getMassValue(emptySet) > 0.0) {
			result.incrementOrAddAsNew(result.getOmegaSet(), result.getMassValue(emptySet));
			result.removeMass(emptySet);
		}

	}

}
