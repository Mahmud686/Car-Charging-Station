import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int numStations = -1;
        int numLocations = -1;

        while (numStations < 1) {
            System.out.print("Enter the number of charging stations: ");
            try {
                numStations = scanner.nextInt();
                if (numStations < 1) {
                    throw new IllegalArgumentException("Exception: Must be a positive integer");
                }
            } catch (InputMismatchException e) {
                System.out.println("Exception: Must be an integer");
                scanner.next();
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            }
        }

        while (numLocations < 1) {
            System.out.print("Enter the number of charging locations at each station: ");
            try {
                numLocations = scanner.nextInt();
                if (numLocations < 1) {
                    throw new IllegalArgumentException("Exception: Must be a positive integer");
                }
            } catch (InputMismatchException e) {
                System.out.println("Exception: Must be an integer");
                scanner.next();
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            }
        }

        Station[] chargingStations = new Station[numStations];

        for (int i = 0; i < numStations; i++) {
            int weatherChoice = -1;

            while (weatherChoice < 0 || weatherChoice > 2) {
                System.out.print("Enter weather condition for station " + (i + 1) + " (0 for SUNNY, 1 for WINDY, 2 for RAINY): ");
                try {
                    weatherChoice = scanner.nextInt();
                    if (weatherChoice < 0 || weatherChoice > 2) {
                        throw new IllegalArgumentException("Invalid weather condition");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Exception: Must be an integer");
                    scanner.next();
                } catch (IllegalArgumentException e) {
                    System.out.println(e);
                }
            }

            if (weatherChoice == 0) {
                System.out.println("Switching to Solarpanel energy source.");
                chargingStations[i] = new Station(numLocations, new SolarPanel());
            } else if (weatherChoice == 1) {
                System.out.println("Switching to WindTurbine energy source.");
                chargingStations[i] = new Station(numLocations, new WindTurbine());
            } else if (weatherChoice == 2) {
                System.out.println("Switching to electricity energy source.");
                chargingStations[i] = new Station(numLocations, new Electricity());
            }
        }

        scanner.close();
    }
}
