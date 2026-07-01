package main;

import Service.AdminService;
import Service.PaymentService;
import Service.PricingService;
import Service.ReceiptService;
import Service.SlotService;
import Service.TicketService;
import controller.AdminController;
import controller.EntryController;
import controller.ExitController;
import domain.PricingRule;
import domain.Vehicle;
import java.util.List;
import java.util.UUID;
import repository.FloorRepository;
import repository.PaymentRepository;
import repository.PricingRepository;
import repository.SlotRepository;
import repository.TicketRepository;

public class main_method {
    public static void main(String[] args){
        System.out.println("=== PARKING LOT LLD SIMULATION ===");
        System.out.println("This simulation demonstrates the complete parking lot system flow\n");

        FloorRepository floorRepository = new FloorRepository();
        PaymentRepository paymentRepository=new PaymentRepository();
        PricingRepository pricingRepository=new PricingRepository();
        SlotRepository slotRepository=new SlotRepository();
        TicketRepository ticketRepository=new TicketRepository();

        TicketService ticketService=new TicketService(ticketRepository);
        SlotService slotService=new SlotService(slotRepository);
        PricingService pricingService=new PricingService(pricingRepository);
        PaymentService paymentService=new PaymentService(paymentRepository);
        ReceiptService receiptService=new ReceiptService();
        AdminService adminService=new AdminService(floorRepository, slotRepository, pricingRepository);

        EntryController entryController=new EntryController(ticketService, slotService);
        ExitController exitController=new ExitController(ticketService, pricingService, paymentService, receiptService, slotService);
        AdminController adminController=new AdminController(adminService);

        System.out.println("=== INITIALIZATION PHASE ===");
        adminController.initializeParkingLot();
        adminController.getParkingStatus();

        System.out.println("\n=== ENTRY FLOW SIMULATION ===");
        
        simulateVehicleEntry(entryController, "ABC123", Vehicle.VehicleType.CAR);
        simulateVehicleEntry(entryController, "XYZ789", Vehicle.VehicleType.BIKE);
        simulateVehicleEntry(entryController, "DEF456", Vehicle.VehicleType.TRUCK);
        
        System.out.println("\n=== EXIT FLOW SIMULATION ===");

        List<UUID> activeTickets=ticketRepository.findActiveTickets().stream().map(ticket->ticket.getId()).toList();
        
        System.out.println("[REPOSITORY] Found " + activeTickets.size() + " active tickets");
        
        for (UUID ticketId : activeTickets) {
            simulateVehicleExit(exitController, ticketId);
        }

        System.out.println("\n=== ADMIN OPERATIONS SIMULATION ===");
        simulateAdminOperations(adminController);


        System.out.println("\n=== FINAL STATUS ===");
        adminController.getParkingStatus();

        System.out.println("\n=== SIMULATION COMPLETED ===");
    }

    private static void simulateVehicleEntry(EntryController entryController, String licensePlate, Vehicle.VehicleType vehicleType) {
        System.out.println("\n--- Vehicle Entry Simulation ---");
        EntryController.EntryResult result = entryController.enterVehicle(licensePlate, vehicleType);
        
        if (result.isSuccess()) {
            System.out.println("Entry successful - Ticket ID: " + result.getTicketId());
        } else {
            System.out.println("Entry failed: " + result.getMessage());
        }
    }

    private static void simulateVehicleExit(ExitController exitController, UUID ticketId) {
        System.out.println("\n--- Vehicle Exit Simulation ---");
        ExitController.ExitResult result = exitController.exitVehicle(ticketId);
        
        if (result.isSuccess()) {
            System.out.println("Exit successful - Receipt ID: " + result.getReceiptId());
            System.out.println("Total Fee: $" + String.format("%.2f", result.getFee()));
            System.out.println("Payment Status: SUCCESS");
            
            String receiptText = exitController.generateReceiptText(ticketId);
            System.out.println(receiptText);
        } else {
            System.out.println("Exit failed: " + result.getMessage());
        }
    }

    private static void simulateAdminOperations(AdminController adminController) {
        System.out.println("\n--- Admin Operations Simulation ---");
        
        adminController.addFloor(3);
        
        adminController.addSlotsToFloor(3, Vehicle.VehicleType.CAR, 10);
        adminController.addSlotsToFloor(3, Vehicle.VehicleType.EV, 5);
        
        adminController.updatePricingRule(Vehicle.VehicleType.CAR, 25.0, 60.0);
        
        adminController.updateFlatPricing(Vehicle.VehicleType.BIKE, 35.0);
        
        adminController.updateHourlyPricing(Vehicle.VehicleType.TRUCK, 40.0);
        
        PricingRule newEvRule = new PricingRule(Vehicle.VehicleType.EV, 18.0, 50.0);
        adminController.addPricingRule(newEvRule);
        
        System.out.println("Admin operations completed successfully");
    }
}
