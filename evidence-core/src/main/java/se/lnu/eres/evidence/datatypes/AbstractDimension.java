package se.lnu.eres.evidence.datatypes;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.lnu.eres.evidence.exceptions.NotDimensionNameFound;
import se.lnu.eres.evidence.exceptions.NotValueOfDimensionFound;
import se.lnu.eres.evidence.util.MathEvidence;

public abstract class AbstractDimension {

	protected static final String NL = System.getProperty("line.separator");
	
	protected static final Logger Logger = LogManager.getLogger(AbstractDimension.class.getSimpleName());

	protected double[] dimensionValues;
	protected String dimensionName;

	public AbstractDimension(double[] dimensionValues, String dimensionName) {
		super();
		this.dimensionValues = dimensionValues;
		this.dimensionName = dimensionName;
		Logger.trace("Creating dimension with name {} and values {}", dimensionName, Arrays.toString(dimensionValues));
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

	
	
	
	public String toString(StringBuilder sb, int level) {
		appendLine(sb, level, "[dimensionName="+dimensionName + "]   called to string with level " + level);
		for(int i=0; i<dimensionValues.length; i++) {
			appendLine(sb, level, "Iteration number" + i);
			appendLine(sb, level, dimensionName+"="+dimensionValues[i]);
			dimensionContent(sb, level+1, i);
		}
		
		return sb.toString();
	}

	public String toString(int level) {
		StringBuilder sb = new StringBuilder();
		
		return this.toString(sb, level);
	}

	protected abstract void dimensionContent(StringBuilder sb, int level, int index);

	protected void appendLine(StringBuilder sb, int level, String content) {
		//Add white spaces trailing
		for(int i=0; i<level; i++) {
			sb.append("  ");
		}
		//Add content
		sb.append(content);
		//Add newline 
		sb.append(NL);
		
	}

	protected abstract void addSolutionConcrete(List<Pair<Double, Double>> sol, String[] correspondentNames,
			double[] correspondentParameters, int positionInDimensionValues)
			throws NotDimensionNameFound, NotValueOfDimensionFound;

	protected abstract List<Pair<Double, Double>> getSolutionConcrete(String[] correspondentNames,
			double[] correspondentParameters, int positionInDimensionValues)
			throws NotValueOfDimensionFound, NotDimensionNameFound;

}
