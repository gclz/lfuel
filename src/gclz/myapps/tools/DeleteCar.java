package gclz.myapps.tools;

import java.io.File;

/**
 * @author goncalo.louzada
 *
 */
public class DeleteCar {

	public boolean deleteFile(String carName, String dir) {
		try {
			File file = new File(dir + carName + ".csv");
			if (file.exists()) {
				file.delete();
				return true;
			} else {
				System.out.println("CAR" + carName + "DOESN'T EXIST IN DIRECTORY " + dir);
				return false;
			}

		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("ERROR DELETING CAR" + carName + " IN DIRECTORY " + dir);
			return false;

		}
	}
}
