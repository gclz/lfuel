package gclz.myapps.tools;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author goncalo.louzada
 *
 */
public class Analytics {

	private List<String[]> fuelRecord = new ArrayList<String[]>();

	private String carName;

	private int totalRefills;

	private int oldTotalKm;
	private int originalKm;

	private double oldTotalPrice;
	private double oldAvgPrice;

	private double oldTotalFuel;
	private double tripFuel;

	private double oldConsumption;
	private double tripConsumption;

	private int priceVariation;
	private int consumptionVariation;

	private List<Object[]> refills = new ArrayList<Object[]>();

	public void getValues(String carName, String dir) {

		try {

			CSVReader csvReader = new CSVReader(new FileReader(dir + carName + ".csv"));

			fuelRecord = csvReader.readAll();
			
			this.carName = fuelRecord.get(0)[1];
			this.originalKm = Integer.parseInt(fuelRecord.get(1)[1]);
			this.totalRefills = Integer.parseInt(fuelRecord.get(2)[1]);
			this.oldTotalKm = Integer.parseInt(fuelRecord.get(3)[1]);
			this.oldTotalPrice = Double.parseDouble(fuelRecord.get(4)[1]);
			this.oldAvgPrice = Double.parseDouble(fuelRecord.get(5)[1]);
			this.priceVariation = Integer.parseInt(fuelRecord.get(5)[2]);
			this.oldTotalFuel = Double.parseDouble(fuelRecord.get(6)[1]);
			this.oldConsumption = Double.parseDouble(fuelRecord.get(7)[1]);
			this.consumptionVariation = Integer.parseInt(fuelRecord.get(7)[2]);
			this.tripConsumption = Double.parseDouble(fuelRecord.get(8)[1]);
			this.tripFuel = Double.parseDouble(fuelRecord.get(9)[1]);

			csvReader.close();

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public double getTripConsumption() {
		return tripConsumption;
	}

	public int getOnlyKm(String carName, String dir) {
		try {
			CSVReader csvReader = new CSVReader(new FileReader(dir + carName + ".csv"));

			csvReader.readNext();
			csvReader.readNext();
			csvReader.readNext();
			String km = csvReader.readNext()[1];
			csvReader.close();
     		return Integer.valueOf(km);
			
		} catch (IOException e) {
			System.out.println("IOException" + e);
			return -1;
		}

	}

	public List<String[]> getFuelRecord() {
		return fuelRecord;
	}

	public int getTotalRefills() {
		return totalRefills;
	}

	public int getOldTotalKm() {
		return oldTotalKm;
	}

	public double getOldTotalPrice() {
		return oldTotalPrice;
	}

	public double getOldAvgPrice() {
		return oldAvgPrice;
	}

	public double getOldTotalFuel() {
		return oldTotalFuel;
	}

	public double getOldConsumption() {
		return oldConsumption;
	}

	public int getPriceVariation() {
		return priceVariation;
	}

	public int getConsumptionVariation() {
		return consumptionVariation;
	}

	public List<Object[]> getRefills() {
		return refills;
	}

	public int getOriginalKm() {
		return originalKm;
	}

	public double getTripFuel() {
		return tripFuel;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

}
