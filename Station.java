

import java.util.ArrayList;
import java.util.List;
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

    public boolean chargeCar()
            throws EnergyProvidingException, LocationOccupiedException, EnergyProvidingException {
        try{
        for (Location location : Locations) {
            if (!location.isOccupied()) {
                location.occupy();
                location.release();

                double energyProvided = 0;
                try {
                    energyProvided = SourceofEnergy.provideEnergy();
                } catch (EnergyProvidingException e) {
                    throw new RuntimeException(e);
                } catch (EnergyProvidingException e) {
                    throw new RuntimeException(e);
                }
                
                System.out.println("Charging the car with " + energyProvided + " kWh of energy");
                }
        }
        }
                catch (LocationOccupiedException | EnergyProvidingException e) {
                    // Handle or log the exception if needed
                    System.out.println("Error providing energy: " + e.getMessage());
                    throw e; // Rethrow the exception
                } catch (EnergyProvidingException e) {
            throw new RuntimeException(e);
        } catch (EnergyProvidingException e) {
            throw new RuntimeException(e);
        } catch (EnergyProvidingException e) {
            throw new RuntimeException(e);
        } finally {
                    System.out.println("everything is perfact");
                }

                return true;




    }

    public SourceofEnergy getSourceofEnergy() {
        return SourceofEnergy;
    }


    private class EnergyProvidingException extends Exception {
    }
}

