package info.novatec.travelagency.services;

import com.sun.mail.smtp.SMTPMessage;
import info.novatec.travelagency.entities.travel.Customer;
import info.novatec.travelagency.entities.travel.Invoice;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.mail.*;
import javax.mail.internet.InternetAddress;

/*
Problem: Die Java Mail API - konkret die statische Methode 'Transport.send()' -  erschwert das Testen
 */
@RequestScoped
public class EmailService {

    @Resource
    Session mailSession;

    public void sendInvoice(Customer customer, Invoice invoice) {
        try {
            Message message = createMessage(customer, invoice);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Message createMessage(Customer customer, Invoice invoice) throws MessagingException {
        Message message = new SMTPMessage(mailSession);
        message.setSubject("Confirmation...");
        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(customer.getEmail()));
        message.setContent(invoice, "text/plain");
        return message;
    }

}
