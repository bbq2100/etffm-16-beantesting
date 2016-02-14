package info.novatec.travelagency.entities.flight;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FlightBooking {
    @Id
    private long id;

    public long getId() {
        return id;
    }
}
