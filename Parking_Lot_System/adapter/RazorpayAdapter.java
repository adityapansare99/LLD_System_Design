package adapter;

import java.util.Random;
import java.util.UUID;

public class RazorpayAdapter implements PaymentGatewayAdapter{
    @Override
    public boolean pay(UUID ticketId,double amount){
        System.out.println("[ADAPTER] RazorpayAdapter processing payment for ticket: " + ticketId + " amount: " + amount);

        Random random=new Random();
        boolean success=random.nextDouble()<0.9;

        System.out.println("[ADAPTER] RazorpayAdapter payment result: " + (success ? "SUCCESS" : "FAILED"));
        return success;
    }
}