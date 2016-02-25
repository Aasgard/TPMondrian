import java.util.Random;

public class RandomInt extends Random{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param min : le minimum du nombre aléatoire
	 * @param max : le maximum du nombre aléatoire
	 * @return Un entier aléatoire entre mon et max (inclus)
	 */
	public static int randInt(int min, int max) {
	    RandomInt rand = new RandomInt();

	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
}
