package info.novatec.travelagency.services.mocks;

import info.novatec.travelagency.entities.flight.FlightBooking;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DBTestUtil {

    @PersistenceContext
    EntityManager em;

    public FlightBooking findFlightBookingByID(long id) {
        return em.find(FlightBooking.class, id);
    }

}

