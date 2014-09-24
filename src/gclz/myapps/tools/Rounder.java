package gclz.myapps.tools;

/**
 * @author goncalo.louzada
 *
 */
public class Rounder {
	public double roundDouble (double toRound, int decimals) {
		double rounded = 0;
		if (decimals == 1) {
			rounded = Math.round(toRound*10.0)/10.0;
		}
		
		else if (decimals == 2) {
			rounded = Math.round(toRound*100.0)/100.0;
		}
		
		else if (decimals == 3) {
			rounded = Math.round(toRound*1000.0)/1000.0;
		}
		
		return rounded;
	}
}
