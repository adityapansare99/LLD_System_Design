package controller;

import Service.SlotService;
import Service.TicketService;
import domain.Ticket;
import domain.Vehicle;
import java.util.Optional;
import java.util.UUID;

public class EntryController {

    private TicketService ticketService;
    private SlotService slotService;

    public EntryController(TicketService ticketService, SlotService slotService) {
        this.ticketService = ticketService;
        this.slotService = slotService;
    }

    public EntryResult enterVehicle(String licensePlate, Vehicle.VehicleType vehicleType) {
        System.out.println("[CONTROLLER] Vehicle entry request - License: " + licensePlate + ", Type: " + vehicleType);

        try {
            Vehicle vehicle = new Vehicle(licensePlate, vehicleType);
            System.out.println("[CONTROLLER] Vehicle created: " + vehicle.getId());

            Optional<UUID> slotId = slotService.allocateSlot(vehicleType).map(slot -> slot.getId);

            if(slotId.isEmpty()){
                return new EntryResult(false,null,null,"No available slots for vehicle type: " + vehicleType);
            }

            Ticket ticket=ticketService.generateTicket(vehicle,slotId.get());

            System.out.println("[CONTROLLER] Vehicle entry successful - Ticket: " + ticket.getId() + ", Slot: " + slotId.get());
            return new EntryResult(true,ticket.getId(),slotId.get(),"Entry successful");

        } catch (Exception e) {
            System.out.println("[CONTROLLER] Vehicle entry failed: " + e.getMessage());
            return new EntryResult(false, null, null, e.getMessage());
        }
    }

    public static class EntryResult{
        private final boolean success;
        private final UUID ticketId;
        private final UUID slotId;
        private final String message;

        public EntryResult(boolean success,UUID ticketId,UUID slotId,String message){
            this.message=message;
            this.success=success;
            this.slotId=slotId;
            this.ticketId=ticketId;
        }

        public boolean isSuccess(){
            return success;
        }

        public UUID getTicketId(){
            return ticketId;
        }

        public UUID getSlotId(){
            return slotId;
        }

        public String getMessage(){
            return message;
        }
    }
}
