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

    public boolean chargeCar() {
        for (Location location : Locations) {
            if (!location.isOccupied()) {
                location.occupy();
                double energyProvided = SourceofEnergy.provideEnergy();
                System.out.println("Charging the car with " + energyProvided + " kWh of energy");
                location.release();
                return true; 
            }
        }
        return false; 
    }

    
    public SourceofEnergy getSourceofEnergy() {
        return SourceofEnergy;
    }
}
