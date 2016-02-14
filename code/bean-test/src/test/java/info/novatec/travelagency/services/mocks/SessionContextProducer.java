package info.novatec.travelagency.services.mocks;

import javax.ejb.SessionContext;
import javax.enterprise.inject.Produces;

import static org.mockito.Mockito.mock;

public class SessionContextProducer {

    @Produces
    @MockInstance
    public static final SessionContext sessionContext = mock(SessionContext.class);
}
