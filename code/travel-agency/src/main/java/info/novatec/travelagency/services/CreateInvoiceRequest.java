package info.novatec.travelagency.services;

import info.novatec.travelagency.entities.flight.FlightBooking;
import info.novatec.travelagency.entities.travel.Customer;

public class CreateInvoiceRequest {
    private final Customer customer;
    private final FlightBooking booking;

    public CreateInvoiceRequest(Customer customer, FlightBooking booking) {
        this.customer = customer;
        this.booking = booking;
    }
}
