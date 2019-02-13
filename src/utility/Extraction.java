package utility;
import java.util.*;

/**Questa classe permette di estrarre numeri casuali da un range fissato.
 * @author alber
 *
 */
public class Extraction 
{	
	private static Random rand = new Random();	
	
	/**Restituisce un numero intero casuale all'interno di un intervallo 
	 * avente per estremi i due numeri interi passati come parametri.
	 * @param min
	 * @param max
	 * @return
	 */
	public static int estraiIntero(int min, int max)
	{
	 int range = max + 1 - min;
	 int casual = rand.nextInt(range);
	 return casual + min;
	}
	
	/**Restituisce un numero double casuale all'interno di un intervallo 
	 * avente per estremi i due numeri double passati come parametri.
	 * @param min
	 * @param max
	 * @return
	 */
	public static double estraiDouble(double min, double max)
	{
	double casual = min + (max - min) * rand.nextDouble();
	return casual;
	}
	
	
}
