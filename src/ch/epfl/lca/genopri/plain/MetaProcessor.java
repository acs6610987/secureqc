package ch.epfl.lca.genopri.plain;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author zhihuang
 *
 */
public abstract class MetaProcessor {
	private Logger logger = Logger.getLogger(MetaProcessor.class.getName());
	
	/** Approximate number of SNPs in a study file. It is just used for initialization of a java list. */
	public static final int APPROX_SIZE = 3000000;
	
	/** A study file should organize their columns in this order */
	public static final String[] expectedHeaders = new String[]{
		"MarkerName",
		"Strand",
		"N",
		"Effect_allele",
		"Other_allele",
		"EAF",
		"Imputation",
		"Information_type",
		"Information",
		"BETA",
		"SE",
		"P",
		"MAC"
	};
	
	/** The study file */
	protected File study;
	
	/** A reader for the current study file */
	private Scanner studyScanner = null;
	
	/** The current row separated into the corresponding columns */
	private String[] lineFields = null;
	
	protected MetaProcessor(File study){
		this.study = study;
		try {
			studyScanner = new Scanner(study);
		} catch (FileNotFoundException e) {
			logger.log(Level.WARNING, "Cannot find the study file '" + study + "'.");
			System.exit(1);
		}
		String[] headers = studyScanner.nextLine().split("\\s+");
		if(!matchHeaders(headers)){
			logger.log(Level.WARNING, "The headers of study '" + study + "' does not match the expected headers.");
			System.exit(1);
		}
	}
	
	
	/**
	 * Check whether the headers match the expected headers in both number and order.
	 * @param headers
	 * @return true if matched, false otherwise.
	 */
	public boolean matchHeaders(String[] headers){
		if(expectedHeaders.length != headers.length) return false;
		for(int i = 0; i < expectedHeaders.length; i++)
			if(!expectedHeaders[i].equals(headers[i]))
				return false;
		return true;
	}
	
	/** 
	 * Read the next valid row. A row is valid if and only if it has expected number of fields.
	 * @return true if there is a valid row to be read.
	 */
	public boolean advanceLine(){
		while(studyScanner.hasNextLine()){
			String line = studyScanner.nextLine();
			lineFields = line.split("\\s+");
			if(lineFields.length == 0) continue;	// SKIP empty lines
			if(lineFields.length != expectedHeaders.length){
				logger.log(Level.WARNING, "The line '" + line + "' does not have expected number of fields.");
				continue;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Close the study
	 */
	public void closeStudy(){
		studyScanner.close();
	}
	
	
	/**
	 * The number of subjects analyzed for the current SNP (the current row)
	 */
	public double getN(){
		return Double.valueOf(lineFields[2]);
	}
	
	/**
	 * The effect allele frequency (range 0-1) for the current SNP (the current row)
	 */
	public double getEAF(){
		return Double.valueOf(lineFields[5]);
	}
	
	/**
	 * Beta estimate (effect size) for the current SNP (the current row)
	 */
	public double getBETA(){
		return Double.valueOf(lineFields[9]);
	}
	
	/**
	 * Estimated standard error on the estimate of the effect size for the current SNP (the current row)
	 */
	public double getSE(){
		return Double.valueOf(lineFields[10]);
	}
	
	/**
	 * Significance of the variant association for the current SNP (the current row)
	 */
	public double getP(){
		return Double.valueOf(lineFields[11]);
	}
	
	/**
	 * Process the study file.
	 */
	public abstract void runProcessor();
	
	public String toString(){
		return "Default: MetaProcessor.";
	}
}
