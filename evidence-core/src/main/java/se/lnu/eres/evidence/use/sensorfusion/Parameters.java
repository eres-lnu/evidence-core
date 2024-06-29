package se.lnu.eres.evidence.use.sensorfusion;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.FileNotFoundException;
import java.io.IOException;

public class Parameters {

	private  double alphap;
	private  double gammab;
	private  double alphab;
	private  double gammac;
	private  double alphac;
	private  double alphat;
	private  double cc;
	private  double alpha;
	private  double beta;
	
	public double getAlphap() {
		return alphap;
	}

	public double getGammab() {
		return gammab;
	}

	public double getAlphab() {
		return alphab;
	}

	public double getGammac() {
		return gammac;
	}

	public double getAlphac() {
		return alphac;
	}

	public double getAlphat() {
		return alphat;
	}

	public double getCc() {
		return cc;
	}

	public double getAlpha() {
		return alpha;
	}

	public double getBeta() {
		return beta;
	}


	private Properties p;
	
	
	private static final Logger Logger = LogManager.getLogger(Parameters.class.getSimpleName());
	
	public Parameters(String filename) throws FileNotFoundException, IOException {
		p = new Properties();
		p.load(getClass().getClassLoader().getResourceAsStream(filename));
		reloadValuesFromProperties();
	}
	
	public void reloadValuesFromProperties() {
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
	
	public void updatePropertyValue(String propertyName, String newValue) {
		Logger.info("Updating the value of property {} to from {} to {}", propertyName, p.getProperty(propertyName), newValue);
		p.setProperty(propertyName, newValue);
	}
}
