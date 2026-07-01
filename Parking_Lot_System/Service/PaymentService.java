package Service;

import adapter.PaymentGatewayAdapter;
import adapter.RazorpayAdapter;
import adapter.StripeAdapter;
import domain.Payment;
import java.util.UUID;
import repository.PaymentRepository;

public class PaymentService {
    private final PaymentRepository paymentRepository;
    private PaymentGatewayAdapter defualtGateway;

    public PaymentService(PaymentRepository paymentRepository){
        this.paymentRepository=paymentRepository;
        this.defualtGateway=new RazorpayAdapter();
        System.out.println("[SERVICE] PaymentService initialized with default gateway: Razorpay");
    }

    public boolean processPayment(UUID ticketId,double amount){
        System.out.println("[SERVICE] Processing payment for ticket: " + ticketId + " amount: " + amount);

        Payment payment=new Payment(ticketId, amount, Payment.PaymentGateway.RAZORPAY);
        paymentRepository.save(payment);

        boolean success=defualtGateway.pay(ticketId,amount);

        if(success){
            payment.markAsSuccess();
        }

        else{
            payment.markAsFailed();
        }

        paymentRepository.update(payment);
        System.out.println("[SERVICE] Payment processed with status: " + (success ? "SUCCESS" : "FAILED"));

        return success;
    }

    public boolean processPaymentWithRetry(UUID ticketId,double amount,int maxTry){
        System.out.println("[SERVICE] Processing payment with retry for ticket: " + ticketId);

        for(int attempt=1;attempt<=maxTry;attempt++){
            System.out.println("[SERVICE] Payment attempt " + attempt + " of " + maxTry);

            boolean success=processPayment(ticketId, amount);

            if(success){
                System.out.println("[SERVICE] Payment successful on attempt " + attempt);
                return true;
            }

            if(attempt>1){
                defualtGateway=new StripeAdapter();
                System.out.println("[SERVICE] Switching to Stripe gateway for retry");
            }
        }

        System.out.println("[SERVICE] Payment failed after " + maxTry + " attempts");
        return false;
    }

    public void setDefaultGateway(PaymentGatewayAdapter gateway){
        this.defualtGateway=gateway;
    }
}
