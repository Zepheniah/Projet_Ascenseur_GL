package control.algorithm;
/**
 * @class Comparateur entre 2 étage
 */

import java.util.Comparator;
import control.command.FloorRequest;

public class Sort implements Comparator<FloorRequest>{
	/**
	 * Soustraction entre 2 entier correspondant à des étages
	 * @param floor1
	 * @param floor2
	 * @return une valeur positive si floor1 est supérieur,négative si floor2 est supérieur et 0 si égale
	 */
	public int compare(FloorRequest floor1, FloorRequest floor2){
		return floor1.getFloor() - floor2.getFloor();
	}
}
