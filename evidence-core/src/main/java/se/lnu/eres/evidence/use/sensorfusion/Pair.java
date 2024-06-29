package se.lnu.eres.evidence.use.sensorfusion;

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
	
}
