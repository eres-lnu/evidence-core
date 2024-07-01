package se.lnu.eres.evidence.datatypes;

import java.util.ArrayList;
import java.util.List;

import se.lnu.eres.evidence.exceptions.NotDimensionNameFound;
import se.lnu.eres.evidence.exceptions.NotValueOfDimensionFound;

public class Dimension extends AbstractDimension {

	List<AbstractDimension> nextDimension;
	// The list will have as many items as different values will be considered for
	// the current dimension

	public Dimension(int pos, String[] parameterNames, double[][] allValues) {
		super(allValues[pos], parameterNames[pos]);
		// The constructor is recursive and pos indicates the position in the array that
		// is handled in the current recursion
		nextDimension = new ArrayList<AbstractDimension>();

		if (pos == parameterNames.length - 2) {
			// We are in the last iteration. Single parameter, create the Dimension Value
			// directly
			for (double dummy : allValues[pos]) {
				nextDimension.add(new DimensionValue(parameterNames[pos], allValues[pos]));
			}
		} else {
			for (double dummy : allValues[pos]) {
				nextDimension.add(new Dimension(pos + 1, parameterNames, allValues));
			}

		}

	}

	@Override
	protected void addSolutionConcrete(List<Pair<Double, Double>> sol, String[] correspondentNames,
			double[] correspondentParameters, int positionInDimensionValues) throws NotDimensionNameFound, NotValueOfDimensionFound {
		nextDimension.get(positionInDimensionValues).addSolution(sol, correspondentNames, correspondentParameters);
		
	}
}
