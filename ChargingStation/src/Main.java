import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Station {

    private List<Location> Locations;
    private SourceofEnergy SourceofEnergy;

    public Station(int numLocations, SourceofEnergy SourceofEnergy) {
        this.SourceofEnergy = SourceofEnergy;
        this.Locations = new ArrayList<>(numLocations);
        for (int i = 0; i < numLocations; i++) {
            Locations.add(new Location());
        }
    }

    public boolean chargeCar() throws ChargingException {
        boolean charged = false;
        for (Location location : Locations) {
            if (!location.isOccupied()) {
                location.occupy();
                double energyProvided = SourceofEnergy.provideEnergy();
                System.out.println("Charging the car with " + energyProvided + " kWh of energy");
                location.release();
                charged = true;
                break;
            }
        }
        if (!charged) {
            throw new ChargingException("All places occupied, unable to charge");
        }
        return charged;
    }

    public void displayStationStatus(Scanner scanner, int stationNumber) throws ChargingException {
        for (int i = 0; i < Locations.size(); i++) {
            int status = -1;
            while (status < 0 || status > 1) {
                System.out.print("Enter status for charging location " + (i + 1) + " at station " + stationNumber + " (1 for Occupied, 0 for Empty): ");
                try {
                    status = scanner.nextInt();
                    if (status < 0 || status > 1) {
                        System.out.println("Invalid input. Please enter 0 for Empty or 1 for Occupied.");
                        // Clear the scanner buffer to prevent it from reading the invalid input again
                        scanner.nextLine();
                    } else {
                        if (status == 1) {
                            Locations.get(i).occupy();
                        }
                        String displayStatus = (status == 1) ? "Occupied" : "Empty";
                        System.out.println("Charging location " + (i + 1) + " at station " + stationNumber + " is: " + displayStatus);
                    }
                } catch (InputMismatchException e) {
                    throw new ChargingException("Error while taking input for charging location status", e);
                } catch (Exception e) {
                    throw new ChargingException("Unknown error occurred while taking input for charging location status", e.getCause());
                }//finally{
                	//scanner.close();
                //}
            }
        }
    }
}

        scanner.close();
    }
}

