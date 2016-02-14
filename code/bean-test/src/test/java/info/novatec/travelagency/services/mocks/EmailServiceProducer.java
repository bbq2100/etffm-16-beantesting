package info.novatec.travelagency.services.mocks;

import info.novatec.travelagency.services.EmailService;

import javax.enterprise.inject.Produces;

import static org.mockito.Mockito.mock;

public class EmailServiceProducer {

    @Produces
    @MockInstance
    public static final EmailService emailService = mock(EmailService.class);
}
