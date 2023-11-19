public class SolarPanel implements SourceofEnergy {

    private boolean isSwitchedOn;

    public SolarPanel() {
        this.isSwitchedOn = false;
    }

    @Override
    public double provideEnergy() {
        if (isSwitchedOn) {
            return 5.0; // Assume 5 kWh of energy produced
        } else {
            return 0.0; // Solar panel is switched off
        }
    }

    @Override
    public void switchSourceofEnergy(WeatherConditions.Weather weather) {
        if (weather == WeatherConditions.Weather.SUNNY) {
            isSwitchedOn = true;
            System.out.println("Switching to solar energy source");
        } else {
            isSwitchedOn = false;
        }
    }
}