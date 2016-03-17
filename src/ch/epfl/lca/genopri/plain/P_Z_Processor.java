package ch.epfl.lca.genopri.plain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.math3.distribution.NormalDistribution;


public class P_Z_Processor extends MetaProcessor{

	private Logger logger = Logger.getLogger(P_Z_Processor.class.getName());
	
	/** The reported P values */
	private List<Double> pValues = null;
	
	/** The computed P values from the Z-statistics based on reported beta estimate and standard error */
	private List<Double> compPValues = null;
	
	public P_Z_Processor(File study){
		super(study);
	}
	
	@Override
	public void runProcessor() {
		logger.log(Level.INFO, "++++++++++ Start processing study file '"
				+ study.getName()
				+ "' ++++++++++");

		pValues = new ArrayList<>(APPROX_SIZE);
		compPValues = new ArrayList<>(APPROX_SIZE);
		NormalDistribution nd = new NormalDistribution();
		while(advanceLine()){
			pValues.add(getP());
			Double BETA = getBETA(), SE = getSE(), Z = BETA / SE;
			compPValues.add(nd.cumulativeProbability(-Math.abs(Z)) * 2);
		}
		closeStudy();
		
		logger.log(Level.INFO, "---------- End ----------");
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 100; i++)
			sb.append(pValues.get(i) + "\t" + compPValues.get(i) + "\n");
		return sb.toString();
	}
	
}
