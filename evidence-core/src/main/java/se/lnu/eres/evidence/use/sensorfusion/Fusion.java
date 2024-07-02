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
		d.addMass(new HashSet<String>(Arrays.asList("p", "b")), c.getAlpha());
		d.addMass(allElements, 1.0 - c.getAlpha());
		dr[0] = d;

		d = Discourse.createDiscourse(allElements, "radar-ct");
		d.addMass(new HashSet<String>(Arrays.asList("c", "t")), c.getBeta());
		d.addMass(allElements, 1.0 - c.getBeta());
		dr[1] = d;

		return dr;

	}

	private Discourse[] createCameraDiscourses() throws NotElementInDiscourseException {
		Discourse[] dc = new Discourse[4];

		Discourse d = Discourse.createDiscourse(allElements, "camera-p");
		d.addMass("p", c.getAlphap() * c.getCc());
		d.addMass(new HashSet<String>(Arrays.asList("p", "b")), c.getAlphap() * (1.0 - c.getCc()));
		d.addMass(allElements, 1.0 - c.getAlphap());
		dc[0] = d;

		d = Discourse.createDiscourse(allElements, "camera-b");
		d.addMass("b", c.getAlphab() * c.getCc());
		d.addMass(new HashSet<String>(Arrays.asList("p", "b")), c.getAlphab() * (1.0 - c.getCc()));
		d.addMass(allElements, 1.0 - c.getAlphab());
		dc[1] = d;

		d = Discourse.createDiscourse(allElements, "camera-c");
		d.addMass("c", c.getAlphac() * c.getCc());
		d.addMass(new HashSet<String>(Arrays.asList("c", "t")), c.getAlphac() * (1.0 - c.getCc()));
		d.addMass(allElements, 1.0 - c.getAlphac());
		dc[2] = d;

		d = Discourse.createDiscourse(allElements, "camera-t");
		d.addMass("t", c.getAlphat() * c.getCc());
		d.addMass(new HashSet<String>(Arrays.asList("c", "t")), c.getAlphat() * (1.0 - c.getCc()));
		d.addMass(allElements, 1.0 - c.getAlphat());
		dc[3] = d;

		return dc;

	}

	private Discourse[] createLidarDiscourses() throws NotElementInDiscourseException {
		Discourse[] dl = new Discourse[4];

		Discourse d = Discourse.createDiscourse(allElements, "lidar-p");
		d.addMass("p", c.getAlphap());
		d.addMass(allElements, 1.0 - c.getAlphap());
		dl[0] = d;

		d = Discourse.createDiscourse(allElements, "lidar-b");
		d.addMass("b", c.getGammab() * c.getAlphab());
		d.addMass(new HashSet<String>(Arrays.asList("b", "c", "t")), c.getGammab() * (1.0 - c.getAlphab()));
		d.addMass(allElements, 1.0 - c.getGammab());
		dl[1] = d;

		d = Discourse.createDiscourse(allElements, "lidar-c");
		d.addMass("c", c.getGammac() * c.getAlphac());
		d.addMass(new HashSet<String>(Arrays.asList("c", "t")), c.getGammac() * (1.0 - c.getAlphac()));
		d.addMass(allElements, 1.0 - c.getGammac());
		dl[2] = d;

		d = Discourse.createDiscourse(allElements, "lidar-t");
		d.addMass("t", c.getAlphat());
		d.addMass(allElements, 1.0 - c.getAlphat());
		dl[3] = d;

		return dl;
	}

}
