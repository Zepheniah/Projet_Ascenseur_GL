package control.algorithm;

import java.util.Comparator;
import control.Command.FloorRequest;

public class Sort implements Comparator{

	public sortRequest(FloorRequest floor1, FloorRequest floor2){
		return (floor1 > floor2)? floor1 : floor2;
	}
}
