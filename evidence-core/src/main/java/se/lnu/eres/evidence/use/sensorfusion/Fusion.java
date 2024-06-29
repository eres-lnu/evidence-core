package se.lnu.eres.evidence.use.sensorfusion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import se.lnu.eres.evidence.core.Discourse;
import se.lnu.eres.evidence.core.combinations.YagerCombination;
import se.lnu.eres.evidence.exceptions.NotElementInDiscourseException;
import se.lnu.eres.evidence.exceptions.NullDiscourseException;

public class Fusion {

	private Discourse[][] ds = new Discourse[3][];

	private Set<String> allElements = new HashSet<String>(Arrays.asList("p", "b", "c", "t"));

	public Set<String> getAllElements() {
		return allElements;
	}

	private Parameters c;

	public Parameters getParameters() {
		return c;
	}

	public Discourse[][][] createProblem(Parameters c)
			throws NotElementInDiscourseException, FileNotFoundException, IOException, NullDiscourseException {
		this.c = c;
		return createProblemWithLocalParameters();
	}

	public Discourse[][][] createProblem(String propertiesFilename)
			throws NotElementInDiscourseException, FileNotFoundException, IOException, NullDiscourseException {
		c = new Parameters(propertiesFilename);
		return createProblemWithLocalParameters();

	}

	private Discourse[][][] createProblemWithLocalParameters()
			throws NullDiscourseException, NotElementInDiscourseException {
		// Create the 10 discourses; 4 lidar, 4 camera, 2 radar

		ds[0] = createLidarDiscourses();
		ds[1] = createCameraDiscourses();
		ds[2] = createRadarDiscourses();

		Discourse[][][] resultFusionDiscourses = new Discourse[4][4][2];
		YagerCombination combiner = new YagerCombination();

		for (int lidar = 0; lidar < 4; lidar++) {
			for (int camera = 0; camera < 4; camera++) {
				for (int radar = 0; radar < 2; radar++) {
					resultFusionDiscourses[lidar][camera][radar] = combiner
							.combine(new Discourse[] { ds[SensorType.LIDAR.getValue()][lidar],
									ds[SensorType.CAMERA.getValue()][camera], ds[SensorType.RADAR.getValue()][radar] });
				}
			}

		}

		return resultFusionDiscourses;

	}

	public Discourse getInitialDiscourse(SensorType t, int index) {
		if ((t == SensorType.LIDAR) || (t == SensorType.CAMERA)) {
			if (index > 3) {
				throw new ArrayIndexOutOfBoundsException("Lidar and Camera values should be between 0-3, but type is "
						+ t.name() + "  and the value is " + index);
			}
		}
		if (t == SensorType.RADAR) {
			if (index > 1) {
				throw new ArrayIndexOutOfBoundsException("Radar value should be between 0-1, but it is " + index);
			}
		}

		return ds[t.getValue()][index];

	}

	private Discourse[] createRadarDiscourses() throws NotElementInDiscourseException {
		Discourse[] dr = new Discourse[2];

		Discourse d = Discourse.createDiscourse(allElements, "radar-pb");
		d.addMass(new HashSet<String>(Arrays.asList("p", "b")), c.alpha);
		d.addMass(allElements, 1.0 - c.alpha);
		dr[0] = d;

		d = Discourse.createDiscourse(allElements, "radar-ct");
		d.addMass(new HashSet<String>(Arrays.asList("c", "t")), c.beta);
		d.addMass(allElements, 1.0 - c.beta);
		dr[1] = d;

		return dr;

	}

	private Discourse[] createCameraDiscourses() throws NotElementInDiscourseException {
		Discourse[] dc = new Discourse[4];

		Discourse d = Discourse.createDiscourse(allElements, "camera-p");
		d.addMass("p", c.alphap * c.cc);
		d.addMass(new HashSet<String>(Arrays.asList("p", "b")), c.alphap * (1.0 - c.cc));
		d.addMass(allElements, 1.0 - c.alphap);
		dc[0] = d;

		d = Discourse.createDiscourse(allElements, "camera-b");
		d.addMass("b", c.alphab * c.cc);
		d.addMass(new HashSet<String>(Arrays.asList("p", "b")), c.alphab * (1.0 - c.cc));
		d.addMass(allElements, 1.0 - c.alphab);
		dc[1] = d;

		d = Discourse.createDiscourse(allElements, "camera-c");
		d.addMass("c", c.alphac * c.cc);
		d.addMass(new HashSet<String>(Arrays.asList("c", "t")), c.alphac * (1.0 - c.cc));
		d.addMass(allElements, 1.0 - c.alphac);
		dc[2] = d;

		d = Discourse.createDiscourse(allElements, "camera-t");
		d.addMass("t", c.alphat * c.cc);
		d.addMass(new HashSet<String>(Arrays.asList("c", "t")), c.alphat * (1.0 - c.cc));
		d.addMass(allElements, 1.0 - c.alphat);
		dc[3] = d;

		return dc;

	}

	private Discourse[] createLidarDiscourses() throws NotElementInDiscourseException {
		Discourse[] dl = new Discourse[4];

		Discourse d = Discourse.createDiscourse(allElements, "lidar-p");
		d.addMass("p", c.alphap);
		d.addMass(allElements, 1.0 - c.alphap);
		dl[0] = d;

		d = Discourse.createDiscourse(allElements, "lidar-b");
		d.addMass("b", c.gammab * c.alphab);
		d.addMass(new HashSet<String>(Arrays.asList("b", "c", "t")), c.gammab * (1.0 - c.alphab));
		d.addMass(allElements, 1.0 - c.gammab);
		dl[1] = d;

		d = Discourse.createDiscourse(allElements, "lidar-c");
		d.addMass("c", c.gammac * c.alphac);
		d.addMass(new HashSet<String>(Arrays.asList("c", "t")), c.gammac * (1.0 - c.alphac));
		d.addMass(allElements, 1.0 - c.gammac);
		dl[2] = d;

		d = Discourse.createDiscourse(allElements, "lidar-t");
		d.addMass("t", c.alphat);
		d.addMass(allElements, 1.0 - c.alphat);
		dl[3] = d;

		return dl;
	}

}
