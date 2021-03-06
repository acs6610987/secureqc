package ch.epfl.lca.genopri.secure.utils;

public class UtilsExample {
	public static void main(String[] args){
		int a = Base64Utils.fromBase64ToInt("2nD1Cw==");
		int b = Base64Utils.fromBase64ToInt("CK4VJA==");
		int c = a ^ b;
		System.out.println(c * 1.0 / (1<<30));
		
		a = Base64Utils.fromBase64ToInt("1KgYzA==");
		b = Base64Utils.fromBase64ToInt("XUfkMw==");
		c = a ^ b;
		System.out.println(c * 1.0 / (1 << 24));
		
		a = Base64Utils.fromBase64ToInt("GFizBw==");
		b = Base64Utils.fromBase64ToInt("nm2mBw==");
		c = a ^ b;
		System.out.println(c * 1.0 / (1 << 24));
		
		long al = Base64Utils.fromBase64ToLong("28uooaxzb7Y=");
		long bl = Base64Utils.fromBase64ToLong("2z3t4/e9yo4=");
		long cl = al ^ bl;
		System.out.println(cl * 1.0 / (1L << 62));
	}
}
