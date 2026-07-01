package Service;

import domain.PricingRule;
import domain.Ticket;
import domain.Vehicle;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import repository.PricingRepository;

public class PricingService {
    private final PricingRepository pricingRepository;

    public PricingService(PricingRepository pricingRepository){
        this.pricingRepository=pricingRepository;
    }

    public double calculateFee(Ticket ticket){
        System.out.println("[SERVICE] Calculating fee for ticket: " + ticket.getId());

        Vehicle.VehicleType vehicleType=Vehicle.VehicleType.CAR;

        Optional<PricingRule> rule=pricingRepository.findByVehicleType(vehicleType);

        if(rule.isEmpty()){
            throw new IllegalStateException("No pricing rule found for vehicle type: " + vehicleType);
        }

        PricingRule pricingRule=rule.get();

        double flatFee=pricingRule.getFlatRate();
        double hourlyFee=calculateHourlyFee(ticket,pricingRule.getRatePerHour());

        double finalFee=Math.min(flatFee,hourlyFee);

        System.out.println("[SERVICE] Flat fee: " + flatFee + ", Hourly fee: " + hourlyFee + ", Final fee: " + finalFee + " for vehicle type: " + vehicleType);
        return finalFee;
    }

    private double calculateHourlyFee(Ticket ticket,double ratePerHour){
        Duration duration=Duration.between(ticket.getEntryTime(), LocalDateTime.now());
        long hr=duration.toHours();

        if(hr<1){
            hr=1;
        }

        return hr*ratePerHour;
    }

    public void addPricingRule(PricingRule rule){
        pricingRepository.save(rule);
    }

    public void updatePricingRule(PricingRule rule){
        pricingRepository.update(rule);
    }
}
