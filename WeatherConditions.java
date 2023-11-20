import java.util.Random;

//WeatherConditions.java
public class WeatherConditions {
    public enum Weather {
        SUNNY, WINDY, RAINY
    }

    public static Weather getWeather() {
        // Simulate getting the current weather condition (randomly generate for simulation)
        Random random = new Random();
        int rand = random.nextInt(3); // Generate a random number between 0 and 2

        switch (rand) {
            case 0:
                return Weather.SUNNY;
            case 1:
                return Weather.WINDY;
            case 2:
                return Weather.RAINY;
            default:
                throw new IllegalStateException("Unexpected value: " + rand);
        }
    }
}