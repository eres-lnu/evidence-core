package se.lnu.eres.evidence.datatypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.lnu.eres.evidence.exceptions.NotDimensionNameFound;
import se.lnu.eres.evidence.exceptions.NotValueOfDimensionFound;

public class Dimension extends AbstractDimension {

	 protected static final Logger Logger = LogManager.getLogger(Dimension.class.getSimpleName());
	
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
			
			Arrays.stream(allValues[pos]).forEach(e -> nextDimension.add(new DimensionValue(parameterNames[pos+1], allValues[pos+1])));
//			for (double dummy : allValues[pos]) {
//				nextDimension.add(new DimensionValue(parameterNames[pos+1], allValues[pos+1]));
//			}
		} else {
			Arrays.stream(allValues[pos]).forEach(e -> nextDimension.add(new Dimension(pos + 1, parameterNames, allValues)));
//			for (double dummy : allValues[pos]) {
//				nextDimension.add(new Dimension(pos + 1, parameterNames, allValues));
//			}

		}

	}

	@Override
	protected void addSolutionConcrete(List<Pair<Double, Double>> sol, String[] correspondentNames,
			double[] correspondentParameters, int positionInDimensionValues) throws NotDimensionNameFound, NotValueOfDimensionFound {
		
		nextDimension.get(positionInDimensionValues).addSolution(sol, correspondentNames, correspondentParameters);
		
	}

	@Override
	protected List<Pair<Double, Double>> getSolutionConcrete(String[] correspondentNames,
			double[] correspondentParameters, int positionInDimensionValues) throws NotValueOfDimensionFound, NotDimensionNameFound {
		
		return nextDimension.get(positionInDimensionValues).getSolution(correspondentNames, correspondentParameters);
	}

	@Override
	protected void dimensionContent(StringBuilder sb, int level, int index) {
		nextDimension.get(index).toString(sb,level);
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

	
}
