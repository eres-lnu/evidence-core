package se.lnu.eres.evidence.datatypes;

import java.util.List;

import se.lnu.eres.evidence.exceptions.NotDimensionNameFound;
import se.lnu.eres.evidence.exceptions.NotValueOfDimensionFound;

public class SolutionVariations {

	// Based on: List<List<List<Pair<Double, Double>>>>

	/*
	 * * In each Pair there is an interval <min,max>, in a structure of N elements (1 for
	 * the belief and N-1 plausibilities), in a list for each of the values of a
	 * parameter change, in a list for all the parameters changed
	 */
	
	AbstractDimension solution;
	
	public SolutionVariations(String[] parameterNames, double[][] values) {
		if(parameterNames.length==1) {
			//Single parameter, create the Dimension Value directly
			solution= new DimensionValue(parameterNames[0], values[0]);
		}
		else {solution= new Dimension(0,parameterNames, values);}
		
	}
	
	public void addSolution(List<Pair<Double,Double>> sol, String[] correspondentNames, double[] correspondentParameters) throws NotDimensionNameFound, NotValueOfDimensionFound {
	
			solution.addSolution(sol, correspondentNames, correspondentParameters);
		
	}

	public List<Pair<Double,Double>> getSolution(String[] correspondentNames, double[] correspondentParameters) throws NotValueOfDimensionFound, NotDimensionNameFound {
		return solution.getSolution(correspondentNames, correspondentParameters);
	}


}
