package info.novatec.travelagency.services;

import info.novatec.beantest.api.BaseBeanTest;
import info.novatec.travelagency.entities.flight.Flight;
import info.novatec.travelagency.entities.flight.FlightBooking;
import info.novatec.travelagency.entities.travel.Customer;
import info.novatec.travelagency.entities.travel.Invoice;
import info.novatec.travelagency.services.mocks.DBTestUtil;
import info.novatec.travelagency.utils.UnacceptedPaymentException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static info.novatec.travelagency.services.Role.*;
import static info.novatec.travelagency.services.mocks.AirlineServiceProducer.airlineService;
import static info.novatec.travelagency.services.mocks.EmailServiceProducer.emailService;
import static info.novatec.travelagency.services.mocks.InvoiceServiceProducer.invoiceService;
import static info.novatec.travelagency.services.mocks.SessionContextProducer.sessionContext;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TravelAgencyServiceBeanTest extends BaseBeanTest {

    DBTestUtil dbTestUtil;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        dbTestUtil = getBean(DBTestUtil.class);

        when(sessionContext.isCallerInRole(TRAVEL_AGENT.name())).thenReturn(true);
        when(sessionContext.isCallerInRole(ADMIN.name())).thenReturn(true);
    }

    @Test
    public void bookFlight() throws Exception {
        Customer customer = new Customer();
        Flight flight = new Flight();
        FlightBooking expectedFlightBooking = new FlightBooking();
        Invoice invoice = new Invoice();

        TravelAgencyService travelAgencyService = getBean(TravelAgencyService.class);

        when(airlineService.bookFlight(customer, flight))
                .thenReturn(expectedFlightBooking);

        when(invoiceService.createInvoice(customer, expectedFlightBooking))
                .thenReturn(invoice);

        FlightBooking actualFlightBooking = travelAgencyService.bookFlight(customer, flight);

        FlightBooking savedBooking = dbTestUtil.findFlightBookingByID(actualFlightBooking.getId());
        assertThat(savedBooking, is(notNullValue()));

        verify(emailService).sendInvoice(customer, invoice);
    }

    @Test
    public void should_throw_exception_when_payment_is_unaccepted() throws Exception {
        expectedException.expect(UnacceptedPaymentException.class);

        Customer customer = new Customer();
        Flight flight = new Flight();

        when(airlineService.bookFlight(customer, flight))
                .thenThrow(new UnacceptedPaymentException());

        TravelAgencyService travelAgencyService = getBean(TravelAgencyService.class);
        travelAgencyService.bookFlight(customer, flight);
    }

    @Test
    public void should_throw_exception_when_unauthorized_user_calls_service() throws Exception {
        expectedException.expect(SecurityException.class);
        expectedException.expectMessage(
                "The caller role(s) [GUEST] do not match the required role(s) : [TRAVEL_AGENT, ADMIN]");

        when(sessionContext.isCallerInRole(GUEST.name())).thenReturn(true);
        when(sessionContext.isCallerInRole(TRAVEL_AGENT.name())).thenReturn(false);
        when(sessionContext.isCallerInRole(ADMIN.name())).thenReturn(false);

        TravelAgencyService travelAgencyService = getBean(TravelAgencyService.class);
        travelAgencyService.bookFlight(mock(Customer.class), mock(Flight.class));
    }

}