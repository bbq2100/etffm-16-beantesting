package info.novatec.travelagency.services;

import info.novatec.travelagency.entities.travel.Customer;
import info.novatec.travelagency.entities.flight.FlightBooking;
import info.novatec.travelagency.entities.flight.*;
import info.novatec.travelagency.utils.UnacceptedPaymentException;

import javax.ejb.Remote;

/*
Problem: Eine konkrete Implementierung existiert nicht. Es muss daher eine @Alternative
 Bean angegeben werden.
 */
@Remote
public interface AirlineService {
    FlightBooking bookFlight(Customer customer, Flight flight) throws UnacceptedPaymentException;
}
