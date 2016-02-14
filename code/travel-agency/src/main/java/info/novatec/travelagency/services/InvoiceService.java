package info.novatec.travelagency.services;

import info.novatec.travelagency.entities.flight.FlightBooking;
import info.novatec.travelagency.entities.travel.Customer;
import info.novatec.travelagency.entities.travel.Invoice;

import javax.ejb.Stateless;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

@Stateless
public class InvoiceService {

    public Invoice createInvoice(Customer customer, FlightBooking booking) {
        return ClientBuilder.newClient()
                .target("http://someService123")
                .request()
                .post(createRequest(customer, booking))
                .readEntity(Invoice.class);
    }

    private Entity<CreateInvoiceRequest> createRequest(Customer customer, FlightBooking booking) {
        return Entity.entity(new CreateInvoiceRequest(customer, booking), MediaType.TEXT_PLAIN_TYPE);
    }

}
