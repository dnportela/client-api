package br.com.platformbuilders.consumer.mapper;

import br.com.platformbuilders.consumer.dto.ContactDto;
import br.com.platformbuilders.consumer.entity.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public Contact toEntity(ContactDto contactDto) {
        return Contact.builder()
                .email(contactDto.getEmail())
                .mobilePhoneNumber(contactDto.getMobilePhoneNumber())
                .residencePhoneNumber(contactDto.getResidencePhoneNumber())
                .build();
    }

    public ContactDto toDto(Contact contact) {
        return ContactDto.builder()
                .email(contact.getEmail())
                .mobilePhoneNumber(contact.getMobilePhoneNumber())
                .residencePhoneNumber(contact.getResidencePhoneNumber())
                .build();
    }

    public void merge(Contact contactDb, Contact updatedContact) {
        contactDb.setResidencePhoneNumber(updatedContact.getResidencePhoneNumber());
        contactDb.setMobilePhoneNumber(updatedContact.getMobilePhoneNumber());
        contactDb.setEmail(updatedContact.getEmail());
    }
}
