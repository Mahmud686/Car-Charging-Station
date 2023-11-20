class LocationOccupiedException extends Exception {
    public LocationOccupiedException(String message) {
        super(message);
    }
}

public class Location {

    private boolean occupied;

    public Location() {
        this.occupied = false;
    }

    public boolean isOccupied() {
        return occupied;
    }


    public void occupy()
        throws LocationOccupiedException {
            try {
                if (occupied) {
                    throw new LocationOccupiedException("Location is already occupied.");
                }
                occupied = true;
            } catch (LocationOccupiedException e) {
                // Additional handling/logic
                System.out.println("Additional handling for location occupation: " + e.getMessage());

                // Rethrow the exception
                throw e;
            }
        }



    public void release() {
        occupied = false;
    }

}