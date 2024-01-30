
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

class ReserveBattery extends Logfile{
    public static final Logger energyManagementLogger = Logger.getLogger("EnergyManagementFunctionality");
    private int chargeLevel;

	private final int maxChargeLevel;
    private final int minChargeLevelForWarning; // Added minimum charge level for warning

    public ReserveBattery(int maxChargeLevel, int minChargeLevelForWarning) {
        this.chargeLevel = 0;
        this.maxChargeLevel = maxChargeLevel;
        this.minChargeLevelForWarning = minChargeLevelForWarning;
    }

    public synchronized boolean isBatteryEmpty() {
        return chargeLevel == 0;
    }

    public int getChargeLevel() {
		return chargeLevel;
	}

	public void setChargeLevel(int chargeLevel) {
		this.chargeLevel = chargeLevel;
	}
	
    public synchronized boolean isBatteryFull() {
        return chargeLevel == maxChargeLevel;
    }

    public synchronized void charge(int energy) {
        while (isBatteryFull()) {
            try {
                wait(); // Wait if the battery is full
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        this.chargeLevel += energy;
        if (this.chargeLevel > maxChargeLevel) {
            this.chargeLevel = maxChargeLevel;
        }

        if (this.chargeLevel < minChargeLevelForWarning) {
            printMessage("Warning: Reserve Battery is below the minimum charge level.");
        }

        // System.out.println(Thread.currentThread().getName() + " charging. Current charge level: " + this.chargeLevel);

        notifyAll(); // Notify waiting threads that the battery has been charged
    }

    public synchronized void printMessage(String message) {
        System.out.println(message);
    }
}

class EnergySource implements Runnable {
    private String sourceName;
    private ReserveBattery reserveBattery;
    private int energyGeneratedPerCharge;
    private Random random;

    public EnergySource(String sourceName, ReserveBattery reserveBattery, int energyGeneratedPerCharge) {
        this.sourceName = sourceName;
        this.reserveBattery = reserveBattery;
        this.energyGeneratedPerCharge = energyGeneratedPerCharge;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            int cycles = 0;
            while (cycles < 1) { // Limit the simulation to 2 cycles
                Thread.sleep(10000); // Simulate time between energy generation

                if (randomlyGeneratesEnergy()) {

//                    Logfile.setupLogger(Logfile.energyManagementLogger, "energy_management.log");
                    reserveBattery.printMessage("Reserve Battery approaches " + sourceName + " for charging.");
                    Logfile.energyManagementLogger.info("Reserve Battery approaches " + sourceName + " for charging.");
                    Logfile.energyManagementLoggersimulation("Reserve Battery approaches " + sourceName + " for charging.");
                    reserveBattery.charge(energyGeneratedPerCharge);
                    cycles++;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean randomlyGeneratesEnergy() {
        return random.nextBoolean();
    }
}


