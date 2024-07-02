package se.lnu.eres.evidence.datatypes;

import java.util.Objects;

public class Pair<T,U> {

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
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		return Objects.equals(first, other.first) && Objects.equals(second, other.second);
	}
	
}
