public class WindTurbine implements SourceofEnergy {

    private boolean isSwitchedOn;

    public WindTurbine() {
        this.isSwitchedOn = false;
    }

    @Override
    public double provideEnergy() {
        if (isSwitchedOn) {
            return 8.0; // Assume 8 kWh of energy produced
        } else {
            return 0.0; // Wind turbine is switched off
        }
    }

    @Override
    public void switchSourceofEnergy(WeatherConditions.Weather weather) {
        if (weather == WeatherConditions.Weather.WINDY) {
            isSwitchedOn = true;
            System.out.println("Switching to wind energy source");
        } else {
            isSwitchedOn = false;
        }
    }
}