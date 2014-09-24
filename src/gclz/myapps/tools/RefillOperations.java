package gclz.myapps.tools;

import java.io.FileWriter;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author goncalo.louzada
 *
 */
public class RefillOperations {
	
	//FALTA COPIA DE SEGURANCA
	private int totalRefills;
	
	private int newTotalKm;
	private int tripKm;
	private int originalKm;
	
	private double newTotalPrice;
	private double newAvgPrice;

	private double newTotalFuel;
	private double oldTotalFuel;
	private double tripFuel;
	
	private double newConsumption;
	private double tripConsumption;
	
	private int priceVariation;
	private int consumptionVariation;


	
	private Analytics getData;

    public void initRefill (String carName, String dir) {
//    	try {
//    		File carFile = new File(dir + carName + ".csv");
//    		if(!carFile.exists()) {
//    		    
//    		}
//    		else {
////    			System.out.println(carName + " exists!");
//    		}
//    	}
//    	catch (Exception e) {
//    		System.out.println("EXCEPTION CHECKING FILE " + e);
//    	}
    	
    	getData = new Analytics();
		getData.getValues(carName, dir);
		originalKm = getData.getOriginalKm();
		totalRefills = getData.getTotalRefills();
		oldTotalFuel = getData.getOldTotalFuel();
		tripFuel = getData.getTripFuel();
		
	}
	

    private void calculateTotalKm(int km) {
        if (totalRefills != 0) {
            newTotalKm = km;
        }

        else if (totalRefills == 0) {
            newTotalKm = km;
            originalKm = km;
        }
        
    }
    
	private void calculateTripKm(){
		if (totalRefills == 0) {
            tripKm = 0;
        }

        else {
            tripKm = newTotalKm - getData.getOldTotalKm();
        }

	}

	private void calculateFuel(double fuel) {
		newTotalFuel = new Rounder().roundDouble(getData.getOldTotalFuel() + fuel,2);
	}

	private void calculateTotalPrice(double price) {
		newTotalPrice = new Rounder().roundDouble(getData.getOldTotalPrice() + price, 2);
		
	}

	private void calculateAvgPrice() {
        newAvgPrice = new Rounder().roundDouble(newTotalPrice/(getData.getTotalRefills() + 1) , 3);
	}

	private void calculateConsumption() {
		if (totalRefills > 0) {
			newConsumption = new Rounder().roundDouble( ((oldTotalFuel) * 100)/(newTotalKm-originalKm) , 1);
			
		}
		else if (totalRefills == 0)
		{
			newConsumption = 0.0;
		}
//		
//        System.out.println(oldTotalFuel + " XPTO C");
//        System.out.println(newTotalKm + " XPTO C");
//        
//        
//        
//        System.out.println(this.newConsumption + " XPTO C");
	}
	
	private void calculateTripConsumption() {
        if (tripKm != 0) {
            tripConsumption = new Rounder().roundDouble( (tripFuel * 100) / tripKm ,1);
        }

        else {
            tripConsumption = 0;
        }

//        System.out.println(oldTotalFuel + " XPTO TC");
//        System.out.println(newTotalFuel + " XPTO TC");
//        
//        System.out.println(tripKm + " XPTO TC");
//        
//        System.out.println(tripConsumption + " XPTO TC");


	}

	private void setIndicators() {
		if (newConsumption == getData.getOldConsumption()) {
			consumptionVariation = 0;
		}
		if (newConsumption < getData.getOldConsumption()) {
			consumptionVariation = 1;
		}
		if (newConsumption > getData.getOldConsumption()) {
			consumptionVariation = 2;
		}
		if (newAvgPrice == getData.getOldAvgPrice()) {
			priceVariation = 0;
		}
		if (newAvgPrice < getData.getOldAvgPrice()) {
			priceVariation = 1;
		}
		if (newAvgPrice > getData.getOldAvgPrice()) {
			priceVariation = 2;
		}
		
		
	}
	
	private void setTripFuel(double fuel) {
		tripFuel = new Rounder().roundDouble(fuel, 2);
	}
	
	public void calculation (String carName, String dir, double fuel, int km, double price) {
		initRefill(carName, dir);
		calculateTotalPrice(price);
		calculateFuel(fuel);
		calculateAvgPrice();
        calculateTotalKm(km);
        calculateTripKm();
		calculateConsumption();
		calculateTripConsumption();
		setIndicators();
		setTripFuel(fuel);
		storeData(carName, dir);
	}

	private boolean storeData(String carName, String dir) {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(dir + carName + ".csv"));
			writer.writeNext(new String[] {"CarName",getData.getCarName()});
			writer.writeNext(new String[] {"OriginalKm",String.valueOf(originalKm)});
			writer.writeNext(new String[] {"TotalRefills",String.valueOf(getData.getTotalRefills()+1)});
			writer.writeNext(new String[] {"TotalKm",String.valueOf(newTotalKm)});
			writer.writeNext(new String[] {"TotalPrice",String.valueOf(newTotalPrice)});
			writer.writeNext(new String[] {"AvgPrice",String.valueOf(newAvgPrice),String.valueOf(priceVariation)});
			writer.writeNext(new String[] {"TotalFuel",String.valueOf(newTotalFuel)});
			writer.writeNext(new String[] {"Consumption",String.valueOf(newConsumption),String.valueOf(consumptionVariation)});
			writer.writeNext(new String[] {"TripConsumption",String.valueOf(tripConsumption)});
            writer.writeNext(new String[] {"TripFuel",String.valueOf(tripFuel)});
			writer.close();

			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCP " + e);

			return false;
			
		}
		
	}
	

}
