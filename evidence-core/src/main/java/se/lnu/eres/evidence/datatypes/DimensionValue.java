package se.lnu.eres.evidence.datatypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DimensionValue extends AbstractDimension {

	List<List<Pair<Double, Double>>> intervals;

	/*
	 * A list, each item handles an input value of the varying parameter. The item
	 * contains a list, one value for the belief and other for plausibilities (maybe this thing of 1+(N-1) may be rethought)
	 */
	public DimensionValue(String dimensionName, double[] varyingValues) {
		super(varyingValues, dimensionName);
		intervals = new ArrayList<List<Pair<Double, Double>>>();
		
		Arrays.stream(varyingValues).forEach(e -> intervals.add(new ArrayList<Pair<Double, Double>>()));
//		for(double v : varyingValues) {
//			intervals.add(new ArrayList<Pair<Double, Double>>());
//		}
	}

	@Override
	protected void addSolutionConcrete(List<Pair<Double, Double>> sol, String[] correspondentNames,
			double[] correspondentParameters, int positionInDimensionValues) {
		intervals.set(positionInDimensionValues, sol);
		
	}

	@Override
	protected List<Pair<Double, Double>> getSolutionConcrete(String[] correspondentNames,
			double[] correspondentParameters, int positionInDimensionValues) {
		return intervals.get(positionInDimensionValues);
	}

	@Override
	protected void dimensionContent(StringBuilder sb, int level, int index) {
		sb.append("       entering concrete dimension value with name " + dimensionName + " at index " + index + NL);
		List<Pair<Double, Double>> indexContent = intervals.get(index);
		for(Pair<Double, Double> p : indexContent) {
		appendLine(sb, level, p.toString());
		}
		
	}
	
}
