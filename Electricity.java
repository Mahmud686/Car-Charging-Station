public class Electricity implements SourceofEnergy {

    private boolean isSwitchedOn;
    private Object e;

    public Electricity() {
        this.isSwitchedOn = false;
    }

    public double provideEnergy(Exception e) throws EnergyProvidingException {
        if (isSwitchedOn) {
            return 10.0; // Assume 10 kWh of energy produced
        } else {
            throw new EnergyProvidingException("Electricity source is switched off", e);
        }
    }

    @Override
    public double provideEnergy() throws EnergyProvidingException {
        return 0;
    }

    @Override
    public void switchSourceofEnergy(WeatherConditions.Weather weather) {
        try {
            if (weather == WeatherConditions.Weather.SUNNY || weather == WeatherConditions.Weather.WINDY) {
                isSwitchedOn = true;
                System.out.println("Switching to electricity energy source");
            } else {
                isSwitchedOn = false;
            }
        } catch (Exception e) {
            // Handle or log the exception if needed
            System.out.println("Error switching source of energy: " + e.getMessage());
            // Rethrow the exception if needed
            try {
                throw new EnergyProvidingException("Error switching source of energy");
            } catch (EnergyProvidingException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
