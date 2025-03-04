/**
 * Copyright 2025 EReS research Lab - Linnaeus University
 * Contact: https://lnu.se/en/research/research-groups/engineering-resilient-systems-eres/
 * 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Contributors: 
 * 		Diego Perez
 */

package se.lnu.eres.evidence.core;

import java.util.HashSet;
import java.util.Set;

import se.lnu.eres.evidence.util.MathEvidence;

public class Mass {

	private Set<String> elements;
	
	private double value;

	public Mass(Set<String> elements, double value) {
		super();
		this.elements = elements;
		this.value = value;
	}
	
	public Mass(String element, double value) {
		this(MathEvidence.elementToSet(element), value);
	}
	
	public static final Mass DUMMY_MASS = new Mass(new HashSet<String>(),0.0);


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
