package be.mira.slinger.model;


public class Slinger {

    /**
     * Valversnelling g in m/s
     */
    private static final double VALVERSNELLING = 9.81;

	/**
	 * Bereken de lengte van de slinger aan de hand van de gemeten periode.  
	 *
	 * @param periode De experimenteel bepaalde periode in seconden. (=tijd voor één keer heen en weer). 
	 * @return De lengte van de slinger in meter. 
	*/
	public static double berekenLengte(double periode){
		double result = (periode*Math.sqrt(Slinger.VALVERSNELLING))/(2*Math.PI); //T*sqrt(9.81) / (2*pi)
		result = Math.pow(result, 2); 
		return result; 	
	}
	
	/**
	 * Bereken de valversnelling aan de hand van de lengte van de slinger en de experimenteel bepaalde periode. 
	 * 
	 * @param periode De slingertijd in seconden. 
	 * @param lengte De lengte in meter. 
	 * @return De valversnelling g in meter per seconde. 
	 */
	public static double berekenValversnelling(double periode, double lengte){
		return Math.pow((2*Math.PI*Math.sqrt(lengte))/periode, 2);
	}
}
