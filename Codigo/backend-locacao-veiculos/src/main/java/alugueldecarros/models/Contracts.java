package alugueldecarros.models;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@ToString
@Table(name = "rent_contracts")
public class Contracts {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_contracts_id")
    private Long idContracts;

    @OneToOne
    @JoinColumn(name = "vehicles_id")
    private Vehicles idVehicle;

    @OneToOne
    @JoinColumn(name = "rents_id")
    private Rents idRent;

    @Column(name = "contract_type")
    private String contractType;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "contract_number")
    private String contractNumber;

    @Column(name = "contract_description")
    private String contractDescription;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "deleted_at")
    private Date deletedAt;


    public Contracts() {
    }

    public Contracts(Long idContracts, Vehicles idVehicle, Rents idRent,
                     String contractType, String filePath, String contractNumber,
                     String contractDescription, Date createdAt, Date deletedAt) {
        this.idContracts = idContracts;
        this.idVehicle = idVehicle;
        this.idRent = idRent;
        this.contractType = contractType;
        this.filePath = filePath;
        this.contractNumber = contractNumber;
        this.contractDescription = contractDescription;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public Contracts(Vehicles idVehicle, Rents idRent, String contractType, String filePath,
                     String contractNumber, String contractDescription,
                     Date createdAt, Date deletedAt) {
        this.idVehicle = idVehicle;
        this.idRent = idRent;
        this.contractType = contractType;
        this.filePath = filePath;
        this.contractNumber = contractNumber;
        this.contractDescription = contractDescription;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }
}
