public interface SourceofEnergy {

    double provideEnergy() throws EnergyProvidingException;

    void switchSourceofEnergy(WeatherConditions.Weather weather);

}