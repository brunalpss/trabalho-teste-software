package alugueldecarros.models;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@ToString
@Table(name = "rents")
public class Rents {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rents_id")
    private Long idRent;

    @OneToOne
    @JoinColumn(name = "creator_id")
    private User idCreator;

    @OneToOne
    @JoinColumn(name = "attendant_id")
    private User idAttendant;

    @OneToOne
    @JoinColumn(name = "vehicles_id")
    private Vehicles idVehicle;

    @Column(name = "status")
    private String status;

    @Column(name = "withdraw_date")
    private Date withdrawDate;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "price")
    private double price;

    @Column(name = "payment_Status")
    private String paymentStatus;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    public Rents() {
    }

    public Rents(Long idRent, User idCreator, User idAttendant, Vehicles idVehicle, String status,
                 Date withdrawDate, Date returnDate, Integer price, String paymentStatus,
                 Date createdAt, Date deletedAt) {
        this.idRent = idRent;
        this.idCreator = idCreator;
        this.idAttendant = idAttendant;
        this.idVehicle = idVehicle;
        this.status = status;
        this.withdrawDate = withdrawDate;
        this.returnDate = returnDate;
        this.price = price;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public Rents(User idCreator, User idAttendant, Vehicles idVehicle, String status,
                 Date withdrawDate, Date returnDate, double price, String paymentStatus,
                 Date createdAt, Date deletedAt) {
        this.idCreator = idCreator;
        this.idAttendant = idAttendant;
        this.idVehicle = idVehicle;
        this.status = status;
        this.withdrawDate = withdrawDate;
        this.returnDate = returnDate;
        this.price = price;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }
}
