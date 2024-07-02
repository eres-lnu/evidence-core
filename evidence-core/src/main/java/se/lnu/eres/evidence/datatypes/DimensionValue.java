package se.lnu.eres.evidence.datatypes;

import java.util.ArrayList;
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
		for(double v : varyingValues) {
			intervals.add(new ArrayList<Pair<Double, Double>>());
		}
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
	
}
