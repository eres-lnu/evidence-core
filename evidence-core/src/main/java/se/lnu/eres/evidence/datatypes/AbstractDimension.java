package se.lnu.eres.evidence.datatypes;

import java.util.List;

import se.lnu.eres.evidence.exceptions.NotDimensionNameFound;
import se.lnu.eres.evidence.exceptions.NotValueOfDimensionFound;
import se.lnu.eres.evidence.util.MathEvidence;

public abstract class AbstractDimension {

	protected double[] dimensionValues;
	protected String dimensionName;

	public AbstractDimension(double[] dimensionValues, String dimensionName) {
		super();
		this.dimensionValues = dimensionValues;
		this.dimensionName = dimensionName;
	}

	public void addSolution(List<Pair<Double, Double>> sol, String[] correspondentNames,
			double[] correspondentParameters) throws NotDimensionNameFound, NotValueOfDimensionFound {
		
		int dimensionPositionInCorrespondentNames = findPositionInCorrespondentNames(dimensionName, correspondentNames);
		//position is found or an Exception was thrown.
		double inputValueOfDimension = dimensionValues[dimensionPositionInCorrespondentNames];
		
		int positionInDimensionValues = findPositionInDimensionValues(inputValueOfDimension, dimensionValues);
		
		addSolutionConcrete(sol, correspondentNames, correspondentParameters, positionInDimensionValues);
		
	}

	protected abstract void addSolutionConcrete(List<Pair<Double, Double>> sol, String[] correspondentNames,
			double[] correspondentParameters, int positionInDimensionValues) throws NotDimensionNameFound, NotValueOfDimensionFound;

	private int findPositionInDimensionValues(double v, double[] values) throws NotValueOfDimensionFound {
		for(int i=0; i<values.length; i++) {
			if(MathEvidence.equalDouble(v, values[i])) {return i;}
		}
		
		throw new NotValueOfDimensionFound("Looking for the dimension value " + v + " in the array of names ["+values.toString()+"]");
	}

	private int findPositionInCorrespondentNames(String n, String[] names) throws NotDimensionNameFound {
		for(int i=0; i<names.length; i++ ) {
			if(n.equals(names[i])) {
				return i;
			}
		}
		throw new NotDimensionNameFound("Looking for the dimension name " + n + " in the array of names ["+names.toString()+"]");
	}

	
	
}
