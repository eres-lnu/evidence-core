package se.lnu.eres.evidence.util;

public class MathEvidence {

	public static Double Round(int position, double value) {
		return Math.round(value*Math.pow(10, position))/Math.pow(10, position);
	}
	
}
