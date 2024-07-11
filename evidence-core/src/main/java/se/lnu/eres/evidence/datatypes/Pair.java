package se.lnu.eres.evidence.datatypes;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Pair<T, U> {

	private static final Logger Logger = LogManager.getLogger(Pair.class.getSimpleName());

	T first;
	T second;

	public Pair(T first, T second) {
		super();
		this.first = first;
		this.second = second;
	}

	public T getFirst() {
		return first;
	}

	public T getSecond() {
		return second;
	}

	@Override
	public String toString() {
		return "Interval <" + first + " , " + second + ">";
	}

	@Override
	public int hashCode() {
		return Objects.hash(first, second);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass()) {
			Logger.debug("comparing objects of different classes, this is {} and the parameter of equas is of class {}", getClass().toString(), obj.getClass().toString());
			return false;
		}
		Pair<T, U> other = (Pair<T, U>) obj;

		return Objects.equals(first, other.first) && Objects.equals(second, other.second);
	}

}
