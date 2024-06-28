package se.lnu.eres.evidence.use.sensorfusion;

public enum SensorType {
	LIDAR(0), CAMERA(1), RADAR(2);

	private final int value;

	SensorType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
