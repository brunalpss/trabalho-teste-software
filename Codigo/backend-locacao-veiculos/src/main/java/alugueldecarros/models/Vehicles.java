package alugueldecarros.models;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@ToString
@Table(name = "vehicles")
public class Vehicles {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicles_id")
    private Long idVehicle;

    @Column(name = "name")
    private String name;

    @Column(name = "chassi")
    private String chassi;

    @Column(name = "license_plate")
    private String licensePlate;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "manufactured_year")
    private String manufacturedYear;

    @Column(name = "legal_document")
    private String legalDocument;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "deleted_at")
    private Date deletedAt;


    public Vehicles() {
    }

    public Vehicles(Long idVehicle, String name, String chassi,
                    String licensePlate, String manufacturer, String manufacturedYear,
                    String legalDocument, Date createdAt, Date deletedAt) {
        this.idVehicle = idVehicle;
        this.name = name;
        this.chassi = chassi;
        this.licensePlate = licensePlate;
        this.manufacturer = manufacturer;
        this.manufacturedYear = manufacturedYear;
        this.legalDocument = legalDocument;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public Vehicles(String name, String chassi, String licensePlate, String manufacturer,
                    String manufacturedYear, String legalDocument, Date createdAt,
                    Date deletedAt) {
        this.name = name;
        this.chassi = chassi;
        this.licensePlate = licensePlate;
        this.manufacturer = manufacturer;
        this.manufacturedYear = manufacturedYear;
        this.legalDocument = legalDocument;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }
}
