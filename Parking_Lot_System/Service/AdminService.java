package Service;

import domain.Floor;
import domain.ParkingSlot;
import domain.PricingRule;
import domain.Vehicle;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import repository.FloorRepository;
import repository.PricingRepository;
import repository.SlotRepository;

public class AdminService {
    private FloorRepository floorRepository;
    private SlotRepository slotRepository;
    private PricingRepository pricingRepository;

    public AdminService(FloorRepository floorRepository,SlotRepository slotRepository,PricingRepository pricingRepository){
        this.floorRepository=floorRepository;
        this.slotRepository=slotRepository;
        this.pricingRepository=pricingRepository;
    }

    public void initializeParkingLot(){
        System.out.println("[SERVICE] Initializing parking lot with default configuration");

        for(int i=0;i<3;i++){
            addFloor(i);
        }

        addSlotsToFloor(0,Vehicle.VehicleType.BIKE,20);
        addSlotsToFloor(0, Vehicle.VehicleType.CAR, 30);
        addSlotsToFloor(0, Vehicle.VehicleType.TRUCK, 5);
        
        addSlotsToFloor(1, Vehicle.VehicleType.CAR, 40);
        addSlotsToFloor(1, Vehicle.VehicleType.EV, 10);
        
        addSlotsToFloor(2, Vehicle.VehicleType.CAR, 35);
        addSlotsToFloor(2, Vehicle.VehicleType.EV, 15);

        initializeDefaultPricingRules();

        System.out.println("[SERVICE] Parking lot initialization completed");
    }

    private void addFloor(int floorNumber){
        System.out.println("[SERVICE] Adding floor with number: " + floorNumber);

        if(floorRepository.existsByNumber(floorNumber)){
            System.out.println("[REPOSITORY] Floor number " + floorNumber + " exists: true");
            return;
        }

        Floor floor=new Floor(floorNumber);
        floorRepository.save(floor);
        System.out.println("[SERVICE] Floor added successfully: " + floor.getId());
    }

    private void addSlotsToFloor(int floorNumber,Vehicle.VehicleType vehicleType,int count){
        System.out.println("[SERVICE] Adding " + count + " slots of type " + vehicleType + " to floor " + floorNumber);

        Floor floor=floorRepository.findByNumber(floorNumber).orElseThrow(()->new IllegalStateException("Floor"+floorNumber+" not found"));

        for(int i=0;i<count;i++){
            ParkingSlot slot=new ParkingSlot(vehicleType, floorNumber);
            slotRepository.save(slot);
            floor.addSlot(slot);
        }

        System.out.println("[SERVICE] Added " + count + " slots to floor " + floorNumber);
    }

    public void initializeDefaultPricingRules(){
        System.out.println("[REPOSITORY] Initializing default pricing rules");

        PricingRule bikRule=new PricingRule(Vehicle.VehicleType.BIKE, 10.0, 30.0);
        PricingRule carRule = new PricingRule(Vehicle.VehicleType.CAR, 20.0, 60.0);
        PricingRule truckRule = new PricingRule(Vehicle.VehicleType.TRUCK, 30.0, 90.0);
        PricingRule evRule = new PricingRule(Vehicle.VehicleType.EV, 15.0, 45.0);

        pricingRepository.save(bikRule);
        pricingRepository.save(carRule);
        pricingRepository.save(truckRule);
        pricingRepository.save(evRule);
    }

    public void addFloorPublic(int floorNumber){
        addFloor(floorNumber);
    }

    public void addSlotsToFloorPublic(int floorNumber,Vehicle.VehicleType vehicleType,int count){
        addSlotsToFloor(floorNumber, vehicleType, count);
    }

    public void updatePricingRule(Vehicle.VehicleType vehicleType,double ratePerHour,double flatRate){
        System.out.println("[SERVICE] Updating pricing rule for " + vehicleType);

        PricingRule rule=pricingRepository.findByVehicleType(vehicleType).orElseThrow(()-> new IllegalStateException("Pricing rule not found for " + vehicleType));

        rule.updateRates(ratePerHour, flatRate);
        pricingRepository.update(rule);
        System.out.println("[SERVICE] Pricing rule updated successfully");
    }

    public void updateFlatPricing(Vehicle.VehicleType vehicleType, double flatRate) {
        System.out.println("[SERVICE] Updating flat pricing for " + vehicleType + " to " + flatRate);
        
        PricingRule rule = pricingRepository.findByVehicleType(vehicleType)
            .orElseThrow(() -> new IllegalStateException("Pricing rule not found for " + vehicleType));
        
        rule.updateFlatRate(flatRate);
        pricingRepository.update(rule);
        System.out.println("[SERVICE] Flat pricing updated successfully");
    }
    
    public void updateHourlyPricing(Vehicle.VehicleType vehicleType, double ratePerHour) {
        System.out.println("[SERVICE] Updating hourly pricing for " + vehicleType + " to " + ratePerHour);
        
        PricingRule rule = pricingRepository.findByVehicleType(vehicleType)
            .orElseThrow(() -> new IllegalStateException("Pricing rule not found for " + vehicleType));
        
        rule.updateHourlyRate(ratePerHour);
        pricingRepository.update(rule);
        System.out.println("[SERVICE] Hourly pricing updated successfully");
    }

    public void addPricingRule(PricingRule rule) {
        System.out.println("[SERVICE] Adding new pricing rule for " + rule.getVehicleType());
        pricingRepository.save(rule);
        System.out.println("[SERVICE] Pricing rule added successfully");
    }

    public Map<String, Object> getParkingStatus() {
        System.out.println("[SERVICE] Getting parking lot status");
        
        List<Floor> floors = floorRepository.findAll();
        System.out.println("[SERVICE] Total floors: " + floors.size());
        
        for (Floor floor : floors) {
            System.out.println("[SERVICE] Floor " + floor.getFloorNumber() + " has " + floor.getTotalSlots() + " slots");
        }
        
        Map<Vehicle.VehicleType, Long> slotStats = slotRepository.getSlotStatistics();
        System.out.println("[SERVICE] Slot statistics: " + slotStats);
        
        return Map.of(
            "totalFloors", floors.size(),
            "slotStatistics", slotStats
        );
    }
}
