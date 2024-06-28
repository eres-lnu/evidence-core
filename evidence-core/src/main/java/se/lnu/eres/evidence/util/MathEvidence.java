package se.lnu.eres.evidence.util;

import java.util.HashSet;
import java.util.Set;

public class MathEvidence {

	public static Double Round(int position, double value) {
		return Math.round(value*Math.pow(10, position))/Math.pow(10, position);
	}
	
	public static Set<String> elementToSet(String element) {
		Set<String> s = (new HashSet<String>());
		s.add(element);
		return s;
	}
	
}
