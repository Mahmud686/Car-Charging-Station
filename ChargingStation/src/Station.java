import java.util.*;

public class Station {
    private List<Location> locations;
    private SourceofEnergy sourceOfEnergy;
    private Queue<User> priorityQueue;
    private Map<User, Integer> timeslots;

    public Station(int numLocations, SourceofEnergy sourceOfEnergy) {
        this.sourceOfEnergy = sourceOfEnergy;
        this.locations = new ArrayList<>(numLocations);
        for (int i = 0; i < numLocations; i++) {
            locations.add(new Location());
        }
        this.priorityQueue = new LinkedList<>();
        this.timeslots = new HashMap<>();
    }
    

    
    public boolean chargeCar() throws ChargingException {
        boolean charged = false;
        for (Location location : locations) {
            if (!location.isOccupied()) {
                location.occupy();
                double energyProvided = sourceOfEnergy.provideEnergy();
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
        for (int i = 0; i < locations.size(); i++) {
            int status = -1;
            while (status < 0 || status > 1) {
                System.out.print("Enter status for charging location " + (i + 1) + " at station " + stationNumber + " (1 for Occupied, 0 for Empty): ");
                try {
                    status = scanner.nextInt();
                    if (status < 0 || status > 1) {
                        System.out.println("Invalid input. Please enter 0 for Empty or 1 for Occupied.");
                        scanner.nextLine();
                    } else {
                        if (status == 1) {
                            locations.get(i).occupy();
                        }
                        String displayStatus = (status == 1) ? "Occupied" : "Empty";
                        System.out.println("Charging location " + (i + 1) + " at station " + stationNumber + " is: " + displayStatus);
                    }
                } catch (InputMismatchException e) {
                    throw new ChargingException("Error while taking input for charging location status", e);
                } catch (Exception e) {
                    throw new ChargingException("Unknown error occurred while taking input for charging location status", e.getCause());
                }
            }
        }
    }

    public void bookTimeslot(User user, int timeslot) {
        if (!timeslots.containsKey(user)) {
            timeslots.put(user, timeslot);
            System.out.println(user.getUsername() + " has booked timeslot " + timeslot);
        } else {
            System.out.println("Timeslot already booked by " + user.getUsername());
        }
    }

    public void addToPriorityQueue(User user) {
        priorityQueue.add(user);
        System.out.println(user.getUsername() + " has been added to the priority queue");
    }
    
    public void manageAccess(User user) throws ChargingException {
        if (user.getUserType() == User.UserType.ADMINISTRATOR) {
            System.out.println("Administrator " + user.getUsername() + " has unrestricted access.");
            // Implement logic for allowing administrators to perform actions without restrictions
        } else if (user.getUserType() == User.UserType.EXTERNAL_USER) {
            if (!priorityQueue.isEmpty() && priorityQueue.peek() == user) {
                System.out.println("User " + user.getUsername() + " has been granted access.");
                chargeCar(); // Assuming access grants the user the ability to charge a car
                priorityQueue.poll(); // Remove the user from the front of the queue after granting access
            } else {
                throw new ChargingException("Access denied. User " + user.getUsername() + " is not at the front of the queue.");
            }
        }
        // Add more conditions for different user types or access control rules as needed
    }
    
    public void clearAllLocations() {
        for (Location location : locations) {
            location.release(); // Release all occupied locations
        }
        System.out.println("All locations cleared.");
    }


    public List<Location> getLocations() {
        return this.locations;
    }
}
