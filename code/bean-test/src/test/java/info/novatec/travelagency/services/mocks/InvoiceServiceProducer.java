package info.novatec.travelagency.services.mocks;

import info.novatec.travelagency.services.InvoiceService;

import javax.enterprise.inject.Produces;

import static org.mockito.Mockito.mock;

public class InvoiceServiceProducer {

    @Produces
    @MockInstance
    public static final InvoiceService invoiceService = mock(InvoiceService.class);
}
