package se.lnu.eres.evidence.datatypes;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.lnu.eres.evidence.exceptions.NotDimensionNameFound;
import se.lnu.eres.evidence.exceptions.NotValueOfDimensionFound;
import se.lnu.eres.evidence.util.MathEvidence;

public abstract class AbstractDimension {

	protected static final Logger Logger = LogManager.getLogger(AbstractDimension.class.getSimpleName());

	protected double[] dimensionValues;
	protected String dimensionName;

	public AbstractDimension(double[] dimensionValues, String dimensionName) {
		super();
		this.dimensionValues = dimensionValues;
		this.dimensionName = dimensionName;
	}

	public void addSolution(List<Pair<Double, Double>> sol, String[] correspondentNames,
			double[] correspondentParameters) throws NotDimensionNameFound, NotValueOfDimensionFound {

		int positionInDimensionValues = calculateIndexInList(dimensionName, correspondentNames, correspondentParameters);
		Logger.trace(
				"Adding solution. I will add at level of dimension {} a solution for the value in position {} , which is {}", dimensionName, positionInDimensionValues, dimensionValues[positionInDimensionValues]);
		addSolutionConcrete(sol, correspondentNames, correspondentParameters, positionInDimensionValues);

	}

	public List<Pair<Double, Double>> getSolution(String[] correspondentNames, double[] correspondentParameters)
			throws NotValueOfDimensionFound, NotDimensionNameFound {
		int positionInDimensionValues = calculateIndexInList(dimensionName, correspondentNames, correspondentParameters);
		Logger.trace(
				"Searching for {}-{} at level of dimension {} and the position to continue the search is {} with value {}",
				Arrays.toString(correspondentNames), Arrays.toString(correspondentParameters), dimensionName,
				positionInDimensionValues, dimensionValues[positionInDimensionValues]);
		return getSolutionConcrete(correspondentNames, correspondentParameters, positionInDimensionValues);
	}

	private int calculateIndexInList(String dimensionName2, String[] correspondentNames, double[] correspondentParameters)
			throws NotValueOfDimensionFound, NotDimensionNameFound {
		int dimensionPositionInCorrespondentNames = findPositionInCorrespondentNames(dimensionName, correspondentNames);
		// position is found or an Exception was thrown.
		double inputValueOfDimension = correspondentParameters[dimensionPositionInCorrespondentNames];

		return findPositionInDimensionValues(inputValueOfDimension, dimensionValues);
	}

	private int findPositionInDimensionValues(double v, double[] values) throws NotValueOfDimensionFound {
		for (int i = 0; i < values.length; i++) {
			Logger.trace("Comparing the value to search {} with {} and I will return {}", v, values[i],
					MathEvidence.equalDouble(v, values[i]));
			if (MathEvidence.equalDouble(v, values[i])) {
				return i;
			}
		}

		throw new NotValueOfDimensionFound(
				"Looking for the dimension value " + v + " in the array of names [" + Arrays.toString(values) + "]");
	}

	private int findPositionInCorrespondentNames(String n, String[] names) throws NotDimensionNameFound {
		for (int i = 0; i < names.length; i++) {
			Logger.trace("Comparing my dimension name {} with {} and I will return {}", n, names[i],
					n.equals(names[i]));
			if (n.equals(names[i])) {
				return i;
			}
		}
		throw new NotDimensionNameFound(
				"Looking for the dimension name " + n + " in the array of names [" + Arrays.toString(names) + "]");
	}

	protected abstract void addSolutionConcrete(List<Pair<Double, Double>> sol, String[] correspondentNames,
			double[] correspondentParameters, int positionInDimensionValues)
			throws NotDimensionNameFound, NotValueOfDimensionFound;

	protected abstract List<Pair<Double, Double>> getSolutionConcrete(String[] correspondentNames,
			double[] correspondentParameters, int positionInDimensionValues)
			throws NotValueOfDimensionFound, NotDimensionNameFound;

}
