package info.novatec.travelagency.services.mocks;

import info.novatec.travelagency.services.AirlineService;
import org.mockito.Mock;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AirlineServiceProducer {

    @Produces
    @MockInstance
    public static final AirlineService airlineService = mock(AirlineService.class);

}
