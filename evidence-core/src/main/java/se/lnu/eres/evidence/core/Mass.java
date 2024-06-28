package se.lnu.eres.evidence.core;

import java.util.HashSet;
import java.util.Set;

public class Mass {

	private Set<String> elements;
	
	private double value;

	public Mass(Set<String> elements, double value) {
		super();
		this.elements = elements;
		this.value = value;
	}
	
	public Mass(String element, double value) {
		this(elementToSet(element), value);
	}

	protected static Set<String> elementToSet(String element) {
		Set<String> s = (new HashSet<String>());
		s.add(element);
		return s;
	}

	public Set<String> getElements() {
		return elements;
	}

	public void setElements(Set<String> elements) {
		this.elements = elements;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mass other = (Mass) obj;
		return equalElements(elements, other.elements);
			//	&& Double.doubleToLongBits(value) == Double.doubleToLongBits(other.value);
	}

	private boolean equalElements(Set<String> s1, Set<String> s2) {
		if (s1==null) {return false;}
		if (s2==null) {return false;}
		if (s1.size()!=s2.size()) {return false;}
		
		for(String v1 : s1) {
			if(!s2.contains(v1)) {return false;}
		}
		
		return true;
	}
	
	public boolean equalElements(Set<String> s1) {
		return equalElements(elements, s1);
	}

	public Set<String> intersect(Mass m2) {
		Set<String> result = new HashSet<String>(elements);
		result.retainAll(m2.getElements());
		return result;
	}

	private static final String NL = System.getProperty("line.separator");
	@Override
	public String toString() {
		return "Mass [elements=" + elements + NL + ", value=" + value + "]" + NL + NL;
	}

	

	
	
}
