package info.novatec.travelagency.services;

import info.novatec.travelagency.entities.flight.Flight;
import info.novatec.travelagency.entities.flight.FlightBooking;
import info.novatec.travelagency.entities.travel.Customer;
import info.novatec.travelagency.entities.travel.Invoice;
import info.novatec.travelagency.utils.Secured;
import info.novatec.travelagency.utils.UnacceptedPaymentException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import static info.novatec.travelagency.services.Role.ADMIN;
import static info.novatec.travelagency.services.Role.TRAVEL_AGENT;

/*
Als TravelAgent möchte ich einen Flug für einen Kunden buchen, damit dieser an sein Urlaubsziel kommt.
*/
@Stateless
public class TravelAgencyService {

    @Inject
    AirlineService airlineService;

    @Inject
    TravelAgencyStore agencyStore;

    @Inject
    InvoiceService invoiceService;

    @Inject
    EmailService emailService;

    @Secured({TRAVEL_AGENT, ADMIN})
    @Transactional(rollbackOn = UnacceptedPaymentException.class)
    public FlightBooking bookFlight(Customer customer, Flight flight)
            throws UnacceptedPaymentException {

        FlightBooking booking = airlineService.bookFlight(customer, flight);

        agencyStore.saveBooking(booking);

        Invoice invoice = invoiceService.createInvoice(customer, booking);

        emailService.sendInvoice(customer, invoice);

        return booking;
    }
}
