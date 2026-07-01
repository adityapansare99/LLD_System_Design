package domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Receipt {

    private final UUID id;
    private final UUID ticketId;
    private final LocalDateTime exitTime;
    private final double totalFee;
    private PaymentStatus paymentStatus;

    public enum PaymentStatus {
        PENDING, SUCCESS, FAILED
    }

    public Receipt(UUID ticketId, double totalFee) {
        this.id = UUID.randomUUID();
        this.ticketId = ticketId;
        this.exitTime = LocalDateTime.now();
        this.totalFee = totalFee;
        this.paymentStatus = PaymentStatus.PENDING;
    }

    public void markAsPaid() {
        this.paymentStatus = PaymentStatus.SUCCESS;
    }

    public void markAsFailed() {
        this.paymentStatus = PaymentStatus.FAILED;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    @Override
    public String toString() {
        return "Receipt{" + "id=" + id + ", ticketId=" + ticketId + ", exitTime=" + exitTime + ", totalFee=" + totalFee + ", paymentStatus=" + paymentStatus + "}";
    }
}
