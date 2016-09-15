import java.awt.image.SampleModel;
import java.util.Arrays;


public class Main {

	private static final int HIGH = 10;

	private static final int LOW = 0;

	static int[] data = new int[]{0, 1, 6, 4, 1, 5, 4, 2, 4, 8, 3, 1, 7, 3, 2, 1, 9, 6, 1, 0, 7, 4, 1, 3, 5, 3, 2, 6, 3, 2, 6, 9, 2, 1, 8, 2, 1, 1, 9, 5, 1, 2, 6, 3, 1, 5, 4, 2, 4, 8, 3, 1, 8, 9, 2, 1, 9, 2, 1, 1, 7, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 10, 7, 1, 0, 0, 0, 0, 4, 5, 3, 4, 0, 0, 0, 0, 8, 2, 1, 10, 8, 0, 0, 0, 0, 1, 3, 6, 4, 0, 0, 0, 0, 6, 10, 1, 2, 0, 0, 0, 0, 11, 7, 1, 1, 0, 0, 0, 0, 5, 3, 5, 9, 0, 0, 0, 0, 3, 1, 12, 8, 0, 0, 0, 0, 0, 4, 7, 4, 3, 0, 0, 0, 0, 11, 3, 2, 11, 0, 0, 0, 0, 7, 1, 2, 8, 0, 0, 0, 0, 3, 6, 11, 4, 3, 0, 0, 0, 0, 13, 9, 8, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 3, 2, 12, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 12, 4, 2, 0, 0, 0, 0, 14, 9, 2, 1, 0, 0, 0, 0, 0, 4, 4, 10, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 9, 5, 3, 0, 0, 0, 0, 13, 4, 6, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 6, 12, 5, 0, 0, 0, 0, 10, 14, 11, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 4, 2, 13, 0, 0, 0, 0, 9, 2, 2, 10, 0, 0, 0, 0, 4, 7, 12, 5, 3, 0, 0, 0, 0, 15, 11, 2, 1, 0, 0, 0, 0, 7, 6, 4, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 10, 7, 1, 0, 0, 0, 2, 14, 5, 3, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 6, 12, 6, 0, 0, 0, 0, 3, 16, 14, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, 5, 3, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 7, 14, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 11, 8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	
	static int[] model = new int[data.length];
	
	static boolean threeZero(int[] data, int i) {
		return data[i] == 0 && data[i+1] == 0 && data[i+2] == 0;
	}
	
	// http://www.sbprojects.com/knowledge/ir/nec.php
	public static void main(String[] args) {
		// find end of the initial burst, which is 9 ms, but we don't know
		// if we got the start
		int i = 0, j;
		double usecPerSample = 140.0;
		while (! threeZero(data, i)) {
			++i;
		}
		Arrays.fill(model, 0, i, HIGH);
		
		double fullone = 2250.0, fullzero = 1125.0;
		double biton = 562.5, zero = 562.5;
		
		double t = 0.0;
		// now at the beginning of the first zero period, which is 4.5 ms
		int i0 = i;
		int prev = i;
		t += 4500.0;
		i = (int)(i0 + (t / usecPerSample));
		Arrays.fill(model, prev, i, LOW);
		
		boolean[] bit = new boolean [32];
		
		for (int ibit = 0; ibit < 32; ++i) {
			prev = i;
			double test = t + biton;
			i = (int)(i0 + (test / usecPerSample));
			Arrays.fill(model, prev, i, HIGH);
			test += zero;
			int itest = (int)(i0 + (test / usecPerSample));
			prev = i;
			if (itest + 3 >= data.length)
				break;
			if (threeZero(data, itest)) {
				bit[ibit] = true;
				t += fullone;
			}
			else {
				bit[ibit] = false;
				t += fullzero;
			}
			i = (int)(i0 + (t / usecPerSample));
			if (i >= data.length)
				break;
			Arrays.fill(model, prev, i, LOW);
		}
		
		int n = 0, l = bit.length;
		for (j = 0; j < l; ++j) {
		    n = (n << 1) + (bit[j] ? 1 : 0);
		}
		
		System.out.println("Raw code: " + Integer.toHexString(n));
		for (j = 0; j < data.length; ++j) {
			System.out.print(Integer.toString(model[j]));
			System.out.print(" ");
		}
		System.out.println();
	}

}
