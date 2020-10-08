package control.algorithm;

import java.util.Comparator;
import control.command.FloorRequest;

public class Sort implements Comparator<FloorRequest>{

	public int compare(FloorRequest floor1, FloorRequest floor2){
		return floor1.getFloor() - floor2.getFloor();
	}
}
