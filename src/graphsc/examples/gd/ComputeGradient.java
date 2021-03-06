package graphsc.examples.gd;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import flexsc.CompEnv;
import gc.BadLabelException;
import graphsc.parallel.Gadget;
import graphsc.parallel.Machine;

public class ComputeGradient<T> extends Gadget<T> {

	private MFNode<T>[] mfNodes;

	public ComputeGradient(CompEnv<T> env, Machine machine) {
		super(env, machine);
	}

	public ComputeGradient<T> setInputs(MFNode<T>[] mfNodes) {
		this.mfNodes = mfNodes;
		return this;
	}

	@Override
	public Object secureCompute() throws InterruptedException, IOException,
			BadLabelException, InstantiationException,
			IllegalAccessException, NoSuchMethodException,
			IllegalArgumentException, InvocationTargetException {

		for (int i = 0; i < mfNodes.length; i++) {
			mfNodes[i].computeGradient(MatrixFactorization.GAMMA, env);
		}
		return null;
	}

}
