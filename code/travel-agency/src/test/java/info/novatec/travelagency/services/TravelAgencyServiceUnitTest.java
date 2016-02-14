package info.novatec.travelagency.services;

import info.novatec.travelagency.entities.flight.Flight;
import info.novatec.travelagency.entities.flight.FlightBooking;
import info.novatec.travelagency.entities.travel.Customer;
import info.novatec.travelagency.entities.travel.Invoice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TravelAgencyServiceUnitTest {

    @Mock
    AirlineService airlineService;

    @Mock
    InvoiceService invoiceService;

    @Mock
    EmailService emailService;

    @Mock
    TravelAgencyStore travelAgencyStore;

    @InjectMocks
    TravelAgencyService travelAgencyService = new TravelAgencyService();

    @Test
    public void bookFlight() throws Exception {
        Customer customer = new Customer();
        Flight flight = new Flight();

        FlightBooking booking = new FlightBooking();
        Invoice invoice = new Invoice();
        when(airlineService.bookFlight(customer, flight)).thenReturn(booking);
        when(invoiceService.createInvoice(customer, booking)).thenReturn(invoice);

        travelAgencyService.bookFlight(customer, flight);

        verify(airlineService).bookFlight(customer, flight);
        verify(travelAgencyStore).saveBooking(booking);
        verify(invoiceService).createInvoice(customer, booking);
        verify(emailService).sendInvoice(customer, invoice);
    }
}