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

package se.lnu.eres.evidence.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MathEvidence {

	 protected static final Logger Logger = LogManager.getLogger(MathEvidence.class.getSimpleName());
	
	public static Double Round(int position, double value) {
		Logger.trace("I am going to round value {} and the result is {}",value, Math.round(value*Math.pow(10, position))/Math.pow(10, position));
		return Math.round(value*Math.pow(10, position))/Math.pow(10, position);
	}
	
	public static Set<String> elementToSet(String element) {
		Set<String> s = (new HashSet<String>());
		s.add(element);
		return s;
	}

	public static boolean equalDouble(double v1, double v2) {
		Logger.debug("comparing {} and {} and as {}-{} and I will return {}", v1, v2,Round(5,v1),Round(5,v2), Round(5,v1).equals(Round(5,v2)));
		return Round(5,v1).equals(Round(5,v2));
	}
	
}
