package domain;

import java.util.UUID;

public class ParkingSlot {

    private UUID id;
    private Vehicle.VehicleType slotType;
    private boolean isOccupied;
    private int floorNumber;

    public ParkingSlot(Vehicle.VehicleType slotType, int floorNumber) {
        this.floorNumber = floorNumber;
        this.slotType = slotType;
        this.isOccupied = false;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public Vehicle.VehicleType getSlotType() {
        return slotType;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    @Override
    public String toString() {
        return "ParkingSlot{" + "id=" + id + ", slotType=" + slotType + ", isOccupied=" + isOccupied + ", floorNumber=" + floorNumber + "}";
    }
}
