package br.com.platformbuilders.consumer.mapper;

import br.com.platformbuilders.consumer.dto.AddressDto;
import br.com.platformbuilders.consumer.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toEntity(AddressDto addressDto) {
        return Address.builder()
                .city(addressDto.getCity())
                .state(addressDto.getState())
                .number(addressDto.getNumber())
                .postalCode(addressDto.getPostalCode())
                .street(addressDto.getStreet())
                .build();
    }

    public AddressDto toDto(Address address) {
        return AddressDto.builder()
                .city(address.getCity())
                .state(address.getState())
                .number(address.getNumber())
                .postalCode(address.getPostalCode())
                .street(address.getStreet())
                .build();
    }

    public void merge(Address addressDb, Address updatedAddress) {
        addressDb.setState(updatedAddress.getState());
        addressDb.setCity(updatedAddress.getCity());
        addressDb.setStreet(updatedAddress.getStreet());
        addressDb.setNumber(updatedAddress.getNumber());
        addressDb.setPostalCode(updatedAddress.getPostalCode());
    }
}
