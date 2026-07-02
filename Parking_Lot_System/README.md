# 🚗 Parking Lot System – Low Level Design (LLD)

## 📖 Overview

The **Parking Lot System** is a Low-Level Design (LLD) project that models a real-world parking management solution using **Object-Oriented Design (OOD)** principles and **layered architecture**.

The system supports:

- Vehicle Entry
- Vehicle Exit
- Parking Slot Allocation
- Ticket Generation
- Payment Processing
- Pricing Management
- Administrative Operations

The project demonstrates how to build a **scalable**, **maintainable**, and **extensible** parking lot system by applying **SOLID principles**, **design patterns**, and **clean architecture**.

---

# 📑 Table of Contents

- [Problem Statement](#-1-problem-statement)
- [Functional Requirements](#-2-functional-requirements)
- [Non-Functional Requirements](#-3-non-functional-requirements)
- [System Workflow](#-4-system-workflow)
- [Core Entities](#-5-core-entities)
- [System Architecture](#-6-system-architecture)
- [Class Responsibilities](#-7-class-responsibilities)
- [Core Use Cases](#-8-core-use-cases)
- [Design Patterns Used](#-9-design-patterns-used)
- [SOLID Principles](#-10-solid-principles)
- [Edge Cases](#-11-edge-cases)
- [Future Enhancements](#-12-future-enhancements)

---

# 📌 1. Problem Statement

Design a **Parking Lot Management System** capable of handling:

- Different vehicle types
- Parking slot allocation
- Ticket generation
- Payment processing
- Administrative operations

The system should be modular enough to allow adding new vehicle types, pricing strategies, or payment gateways without modifying existing code.

---

# 🚘 2. Functional Requirements

The system consists of three primary workflows.

## A. Vehicle Entry

When a vehicle enters the parking lot:

1. Driver arrives at the entry gate.
2. Vehicle information is validated.
3. Search for an available parking slot.
4. Allocate the slot.
5. Generate a parking ticket.
6. Mark the slot as occupied.
7. Return the entry response.

### Sample Output

```text
Vehicle Entered Successfully

Ticket ID : T101
Slot      : A-12
Floor     : 1
Entry Time: 10:30 AM
```

---

## B. Vehicle Exit

When a vehicle exits:

1. Scan the ticket.
2. Retrieve ticket details.
3. Calculate parking duration.
4. Calculate parking fee.
5. Process payment.
6. Generate receipt.
7. Release parking slot.
8. Deactivate ticket.

### Sample Output

```text
Exit Successful

Parking Fee   : ₹120
Payment Status: SUCCESS
Slot Released
```

---

## C. Admin Operations

The administrator can:

- Add Floors
- Delete Floors
- Add Parking Slots
- Delete Parking Slots
- Update Pricing Rules
- View Parking Status
- Monitor Parking Utilization

---

# ⚙️ 3. Non-Functional Requirements

## Scalability

- Multiple parking lots
- Thousands of parking slots
- High daily traffic

## Availability

Entry and exit operations should continue working even if payment services are temporarily unavailable.

## Consistency

Parking slot status must always remain synchronized.

Example:

```text
Occupied Slot → Cannot be allocated

Released Slot → Immediately available
```

## Extensibility

Support easy addition of:

- Vehicle types
- Pricing strategies
- Payment gateways
- Notification services

without modifying existing code.

## Security

Only administrators should perform:

- Floor management
- Slot management
- Pricing updates

## Performance

Important operations should execute quickly:

- Slot Allocation
- Ticket Retrieval
- Exit Processing

---

# 🔄 4. System Workflow

## Vehicle Entry Workflow

```text
Vehicle Arrives
       │
       ▼
Find Suitable Slot
       │
       ▼
Allocate Slot
       │
       ▼
Generate Ticket
       │
       ▼
Mark Slot Occupied
       │
       ▼
Return Entry Response
```

---

## Vehicle Exit Workflow

```text
Scan Ticket
      │
      ▼
Retrieve Ticket
      │
      ▼
Calculate Parking Fee
      │
      ▼
Process Payment
      │
      ▼
Generate Receipt
      │
      ▼
Release Slot
      │
      ▼
Deactivate Ticket
```

---

## Admin Workflow

```text
Login
   │
   ▼
Manage Floors
   │
   ▼
Manage Parking Slots
   │
   ▼
Update Pricing Rules
   │
   ▼
View Parking Status
```

---

# 🏗️ 5. Core Entities

## Vehicle

Represents every vehicle entering the parking lot.

### Attributes

- vehicleId
- licensePlate
- vehicleType

### Responsibility

Stores vehicle information.

---

## ParkingSlot

Represents a parking space.

### Attributes

- slotId
- slotType
- floorNumber
- occupied

### Responsibility

Maintains slot availability.

---

## Floor

Represents a parking floor.

### Attributes

- floorId
- floorNumber
- listOfSlots

### Responsibility

Contains multiple parking slots.

---

## Ticket

Generated when a vehicle enters.

### Attributes

- ticketId
- vehicleId
- slotId
- entryTime
- active

### Responsibility

Stores the parking session.

---

## Receipt

Generated after payment.

### Attributes

- receiptId
- ticketId
- exitTime
- paymentStatus
- totalFee

---

## PricingRule

Defines parking charges.

### Attributes

- vehicleType
- flatRate
- hourlyRate
- pricingType

---

## Payment

Stores payment details.

### Attributes

- amount
- gateway
- paymentStatus

---

# 🏛️ 6. System Architecture

The project follows a **Layered Architecture**.

```text
Presentation Layer
        │
        ▼
Controller Layer
        │
        ▼
Service Layer
        │
        ▼
Repository Layer
        │
        ▼
Domain Layer
```

Each layer has a single responsibility, improving modularity and maintainability.

---

## Client Layer

Handles user interaction.

Examples:

- Entry Operator
- Exit Operator
- Admin Panel

---

## Controller Layer

Receives client requests.

Controllers:

- EntryController
- ExitController
- AdminController

Controllers delegate business logic to services.

---

## Service Layer

Contains core business logic.

### TicketService

- Generate Ticket
- Retrieve Ticket

### SlotService

- Allocate Slot
- Release Slot

### PricingService

- Calculate Parking Fee

### PaymentService

- Process Payments

### ReceiptService

- Generate Receipt

### AdminService

- Manage Parking Lot

---

## Repository Layer

Responsible for data persistence.

Repositories:

- TicketRepository
- SlotRepository
- FloorRepository
- PaymentRepository
- PricingRuleRepository

---

## Domain Layer

Contains business entities.

- Vehicle
- ParkingSlot
- Ticket
- Receipt
- Payment
- PricingRule
- Floor

---

# 📋 7. Class Responsibilities

## EntryController

Handles vehicle entry.

Calls:

- SlotService
- TicketService

---

## ExitController

Handles vehicle exit.

Calls:

- PricingService
- PaymentService
- ReceiptService

---

## AdminController

Handles administrative operations.

Calls:

- AdminService

---

## SlotService

Responsible for:

```text
Find Available Slot
       │
Reserve Slot
       │
Release Slot
```

---

# 🔄 8. Core Use Cases

## Enter Vehicle

```text
Client
   │
EntryController
   │
SlotService
   │
TicketService
   │
TicketRepository
   │
EntryResult
```

---

## Exit Vehicle

```text
Client
   │
ExitController
   │
Retrieve Ticket
   │
PricingService
   │
PaymentService
   │
ReceiptService
   │
Release Slot
   │
ExitResult
```

---

## Admin Operations

```text
Admin
   │
AdminController
   │
AdminService
   │
Repository
```

Operations include:

- Add Floor
- Add Slot
- Update Pricing
- View Parking Status

---

# 🎯 9. Design Patterns Used

## Adapter Pattern

Used for integrating multiple payment gateways.

```text
PaymentGateway
      ▲
      │
 Interface
      ▲
 ────────────────
 RazorpayAdapter
 StripeAdapter
```

Benefits:

- Loose coupling
- Easy gateway integration

---

## Repository Pattern

Separates database logic from business logic.

Benefits:

- Cleaner architecture
- Easier testing
- Database independence

---

## Service Layer Pattern

Moves business logic out of controllers.

Benefits:

- Thin controllers
- Better maintainability

---

# 💡 10. SOLID Principles

## Single Responsibility Principle (SRP)

Every class has only one responsibility.

Example:

```text
PricingService
↓
Only calculates parking fees.
```

---

## Open/Closed Principle (OCP)

Open for extension but closed for modification.

Example:

```text
Add TruckVehicle

No existing code changes required.
```

---

## Interface Segregation Principle (ISP)

Use small, focused interfaces.

```text
PaymentGateway
      │
 ├────────────┐
 │            │
Razorpay   Stripe
```

---

## Dependency Inversion Principle (DIP)

Depend on abstractions rather than implementations.

```text
PaymentService
       │
PaymentGateway
       │
StripeAdapter
```

---

## Encapsulation

Each entity manages its own state.

Example:

```java
ParkingSlot

occupy()

release()

isAvailable()
```

---

# ⚠️ 11. Edge Cases

### Lost Ticket

Admin manually validates the vehicle and processes exit.

---

### Payment Failure

Retry payment through the payment gateway adapter.

If payment still fails:

- Keep the ticket active.
- Allow settlement later.

---

### Wrong Vehicle Type

Reject incompatible slot allocation.

---

### Time Synchronization

Use a centralized clock service.

---

### Slot State Mismatch

Run reconciliation jobs to synchronize actual and stored slot states.

---

# 🚀 12. Future Enhancements

Possible improvements include:

- Online Slot Reservation
- QR Code Ticketing
- Real-Time Parking Dashboard
- Multi Parking Lot Support
- Dynamic Pricing
- Membership Plans
- EV Charging Support
- Automatic Number Plate Recognition (ANPR)
- SMS & Email Notifications
- Mobile Application
- Analytics Dashboard
- Cloud Deployment
- Parking Reminder Notifications

---

# ✅ Conclusion

The **Parking Lot System** demonstrates a complete Low-Level Design solution using **Layered Architecture**, **Object-Oriented Design**, **SOLID Principles**, and **Design Patterns**.

It provides a modular, scalable, and extensible architecture capable of supporting real-world parking operations while serving as an excellent reference for **LLD interviews** and **software design learning**.