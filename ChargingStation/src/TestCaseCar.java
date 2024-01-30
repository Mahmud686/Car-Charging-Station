
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class TestCaseCar {

	ReserveBattery reserveBattery = new ReserveBattery(100, 20);
	Station station;
	
	@Before
    public void setUp() {
        station = new Station(0, null);
    }
	
	@BeforeAll
	public static void setupLogger() {
        Logfile.setupLogger(Logfile.systemLogger, Logfile.systemLoggerStr);
    }

    @Test
    public void testSystemFunctionalityStartedLog() {
        Logfile.SystemFunctionalityStartedlog();
        assertTrue(true);
    }

    @Test
    public void testSystemFunctionalityEndLog() {
        Logfile.SystemFunctionalityEndlog();
        assertTrue(true);
    }

    @Test
    public void testChargeAboveMaxLevel() {
        reserveBattery.charge(100);
        assertTrue(reserveBattery.isBatteryFull());
        assertEquals(100, reserveBattery.getChargeLevel());
    }
    
    @Test
    public void testBatteryFull() {
        reserveBattery.charge(50);
        assertFalse(reserveBattery.isBatteryFull());
    }

    @Test
    public void testBatteryEmpty() {
        assertTrue(reserveBattery.isBatteryEmpty());
    }
    @Test
    public void testGetWeather() {
        WeatherConditions.Weather weather = WeatherConditions.getWeather();
        assertTrue(weather == WeatherConditions.Weather.SUNNY ||
                   weather == WeatherConditions.Weather.WINDY ||
                   weather == WeatherConditions.Weather.RAINY);
    }

    @Test
    public void testGetWeatherConsistency() {
        WeatherConditions.Weather weather1 = WeatherConditions.getWeather();
        WeatherConditions.Weather weather2 = WeatherConditions.getWeather();

        assertEquals(weather1, weather2);
    }
    @Test
    public void testChargeCar() {
        try {
            boolean charged = station.chargeCar();
            assertTrue(charged);

        } catch (ChargingException e) {

            assertFalse("Exception occurred: " + e.getMessage(), true);
        }
    }
    @Test
    public void testBookTimeslot() {
        User user = new User("Era", User.UserType.EXTERNAL_USER);
        int timeslot = 3;

        station.bookTimeslot(user, timeslot);

        assertTrue(station.getTimeslots().containsKey(user));
        assertEquals(timeslot, (int) station.getTimeslots().get(user));
    }
    @Test
    public void testInvalidNumStationsInput() {
        String input = "-2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Main.main(null));
        assertTrue(exception.getMessage().contains("Must be a positive integer"));
        System.setIn(System.in); 
    }
    @Test
    public void testInvalidNumLocationsInput() {
        String input = "abc\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Main.main(null));
        assertTrue(exception.getMessage().contains("Must be an integer"));
        System.setIn(System.in); 
    }

}
