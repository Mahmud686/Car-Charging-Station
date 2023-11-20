public class EnergyProvidingException extends Exception {
    public EnergyProvidingException(String electricitySourceIsSwitchedOff) {
        try {
            Electricity electricity = new Electricity();
            double energyProvided = electricity.provideEnergy();
            System.out.println("Energy provided: " + energyProvided + " kWh");
        } catch (EnergyProvidingException e) {
            System.out.println("Error providing energy: " + e.getMessage());
            // Handle the exception or log it
        }

    }

    public EnergyProvidingException(String errorSwitchingSourceOfEnergy, Exception e) {

    }
}
