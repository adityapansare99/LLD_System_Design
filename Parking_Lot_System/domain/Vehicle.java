package domain;

import java.util.UUID;

public class Vehicle {
    private final UUID id;
    private final String licensePlate;
    private final VehicleType vehicleType;

    public enum VehicleType{
        BIKE,CAR,TRUCK,EV
    }

    public Vehicle(String licensePlate, VehicleType vehicleType) {
        this.id = UUID.randomUUID();
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
    }

    public UUID getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    @Override
    public String toString() {
        return "Vehicle{" + "id=" + id + ", licensePlate='" + licensePlate + '\'' + ", vehicleType=" + vehicleType + '}';
    }
}
