package se.lnu.eres.evidence.util;

public class CombinationManagerDouble extends CombinationManager<Double> {

	//This class should dissapear and: 
	// - use the combinationManager using Lists instead of arrays 
	// OR
	// - Use Double instead of double
	// the reason for this class is the arrays with generics. 
	double[][] elements;
	
	public CombinationManagerDouble(double[][] elements) {
		super(elements.length);
		this.elements=elements;
	}

}
