import harness.x10Test;

/**
 * Check that a float literal can be cast to float.
 */
public class FloatLitDepType_MustFailCompile extends x10Test {
	public boolean run() {
		float(:self==0.001F) f =  0.003F;
		return true;
	}

	public static void main(String[] args) {
		new FloatLitDepType_MustFailCompile().execute();
	}


}

