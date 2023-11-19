public interface SourceofEnergy {
	
	double provideEnergy();

	void switchSourceofEnergy(WeatherConditions.Weather weather);
	
}
