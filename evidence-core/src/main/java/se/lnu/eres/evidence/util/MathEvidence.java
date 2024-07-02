package se.lnu.eres.evidence.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MathEvidence {

	 protected static final Logger Logger = LogManager.getLogger(MathEvidence.class.getSimpleName());
	
	public static Double Round(int position, double value) {
		Logger.trace("I am going to round value {} and the result is {}",value, Math.round(value*Math.pow(10, position))/Math.pow(10, position));
		return Math.round(value*Math.pow(10, position))/Math.pow(10, position);
	}
	
	public static Set<String> elementToSet(String element) {
		Set<String> s = (new HashSet<String>());
		s.add(element);
		return s;
	}

	public static boolean equalDouble(double v1, double v2) {
		Logger.debug("comparing {} and {} and as {}-{} and I will return {}", v1, v2,Round(5,v1),Round(5,v2), Round(5,v1).equals(Round(5,v2)));
		return Round(5,v1).equals(Round(5,v2));
	}
	
}
