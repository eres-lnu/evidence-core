package se.lnu.eres.evidence.use.sensorfusion;

import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Constants {

	public final double alphap;
	public final double gammab;
	public final double alphab;
	public final double gammac;
	public final double alphac;
	public final double alphat;
	public final double cc;
	public final double alpha;
	public final double beta;

	public Constants(String filename) throws FileNotFoundException, IOException {
		Properties p = new Properties();
		p.load(getClass().getClassLoader().getResourceAsStream(filename));
		this.alphap=Double.valueOf(p.getProperty("alphap"));
		this.gammab=Double.valueOf(p.getProperty("gammab"));
		this.alphab=Double.valueOf(p.getProperty("alphab"));
		this.gammac=Double.valueOf(p.getProperty("gammac"));
		this.alphac=Double.valueOf(p.getProperty("alphac"));
		this.alphat=Double.valueOf(p.getProperty("alphat"));
		this.cc=Double.valueOf(p.getProperty("cc"));
		this.alpha=Double.valueOf(p.getProperty("alpha"));
		this.beta=Double.valueOf(p.getProperty("beta"));
	}
	
}
