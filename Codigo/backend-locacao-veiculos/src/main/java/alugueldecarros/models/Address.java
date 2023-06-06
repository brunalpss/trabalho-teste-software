package alugueldecarros.models;

import alugueldecarros.models.dto.AddressDto;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    private String street;
    private Integer number;
    private String district;

    @Column(name = "zip_code")
    private String zipCode;

    @OneToOne
    @JoinColumn(name = "city_id")
    private Cities city;

    @OneToOne
    @JoinColumn(name = "state_id")
    private States state;

    public Address(String street, Integer number, String district, Cities city, States state, String zipCode) {
        this.street = street;
        this.number = number;
        this.district = district;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public Address() {

    }

    public static Address fromAddressDTO(AddressDto address, Cities city, States states) {

        return new Address(
                address.getStreet(),
                address.getNumber(),
                address.getDistrict(),
                city,
                states,
                address.getZipCode()
        );
    }
}

