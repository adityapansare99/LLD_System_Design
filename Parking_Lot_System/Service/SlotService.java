package Service;

import java.util.Optional;

import domain.ParkingSlot;
import domain.Vehicle;
import java.util.UUID;
import repository.SlotRepository;

public class SlotService {
    private SlotRepository slotRepository;

    public SlotService(SlotRepository slotRepository){
        this.slotRepository=slotRepository;
    }

    public Optional<ParkingSlot> allocateSlot(Vehicle.VehicleType vehicleType){
        System.out.println("[SERVICE] Allocating slot for vehicle type: " + vehicleType);
        Optional<ParkingSlot> slot=slotRepository.allocateSlot(vehicleType);

        if (slot.isPresent()) {
            System.out.println("[SERVICE] Slot allocated successfully: " + slot.get().getId());
        } else {
            System.out.println("[SERVICE] No available slots for vehicle type: " + vehicleType);
        }

        return slot;
    }

    public void releaseSlot(UUID slotId){
        System.out.println("[SERVICE] Releasing slot: " + slotId);
        slotRepository.releaseSlot(slotId);
        System.out.println("[SERVICE] Slot released successfully: " + slotId);
    }

    public ParkingSlot createSlot(Vehicle.VehicleType vehicleType,int floorNumber){
        ParkingSlot slot=new ParkingSlot(vehicleType, floorNumber);
        slotRepository.save(slot);
        return slot;
    }

    public long getAvailablesSlotCount(Vehicle.VehicleType vehicleType){
        return slotRepository.findAvailableSlots(vehicleType).size();
    }
}
