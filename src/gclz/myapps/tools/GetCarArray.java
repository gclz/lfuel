package gclz.myapps.tools;

import java.io.File;

/**
 * @author goncalo.louzada
 *
 */
public class GetCarArray {

	public String[] getCarNames(String path) {

		if (!new File(path).isDirectory()) {
			new File(path).mkdir();
		}

		try {
			File f = new File(path);
			File file[] = f.listFiles();
			String[] cars = new String[file.length];

			for (int i = 0; i < file.length; i++) {
				cars[i] = getIndividualFile(file[i]);
			}

			return cars;

		} catch (Exception e) {
			System.out.println("EXCEPTION " + e);
			return null;
		}
	}

	private String getIndividualFile(File file) {
		return file.getName().split(".csv")[0];
	}

//	public String getIndividualCar(File file) {
//
//		String filename = file.getName();
//
//		try {
//			CSVReader csvReader = new CSVReader(new FileReader(dir + filename));
//			List<String[]> fuelRecord = csvReader.readAll();
//			csvReader.close();
//
//			return fuelRecord.get(0)[1];
//		} catch (Exception e) {
//			System.out.println("EXCEPTION GETCARNAME: " + e);
//			return null;
//		}
//
//	}

}
