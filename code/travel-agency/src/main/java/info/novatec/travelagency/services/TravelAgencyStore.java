package info.novatec.travelagency.services;

import info.novatec.travelagency.entities.flight.FlightBooking;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TravelAgencyStore {

    @PersistenceContext
    EntityManager em;

    public void saveBooking(FlightBooking booking) {
        em.persist(booking);
    }
}
