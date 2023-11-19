public class Electricity implements SourceofEnergy {

    private boolean isSwitchedOn;

    public Electricity() {
        this.isSwitchedOn = false;
    }

    @Override
    public double provideEnergy() {
        if (isSwitchedOn) {
            return 10.0; // Assume 10 kWh of energy produced
        } else {
            return 0.0; // Electricity source is switched off
        }
    }

    @Override
    public void switchSourceofEnergy(WeatherConditions.Weather weather) {
        if (weather == WeatherConditions.Weather.SUNNY || weather == WeatherConditions.Weather.WINDY) {
            isSwitchedOn = true;
            System.out.println("Switching to electricity energy source");
        } else {
            isSwitchedOn = false;
        }
    }
}
