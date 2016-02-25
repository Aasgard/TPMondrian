import java.util.Random;

public class RandomInt extends Random{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param min : le minimum du nombre al�atoire
	 * @param max : le maximum du nombre al�atoire
	 * @return Un entier al�atoire entre mon et max (inclus)
	 */
	public static int randInt(int min, int max) {
	    RandomInt rand = new RandomInt();

	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
}
