package alugueldecarros.models.ResponseEntity;

import alugueldecarros.models.Rents;
import alugueldecarros.models.User;
import alugueldecarros.models.Vehicles;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class RentsResponse {


    private Long idRent;
    private User idCreator;
    private User idAttendant;
    private Vehicles idVehicle;
    private String status;
    private Date withdrawDate;
    private Date returnDate;
    private double price;
    private String paymentStatus;
    private Date createdAt;
    private Date deletedAt;


    public RentsResponse(Long idRent, User idCreator, User idAttendant,
                         Vehicles idVehicle, String status, Date withdrawDate, Date returnDate, double price, String paymentStatus,
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


    public static RentsResponse fromRents (Rents rents){
        return new RentsResponse(
              rents.getIdRent(),
              rents.getIdCreator(),
              rents.getIdAttendant(),
              rents.getIdVehicle(),
              rents.getStatus(),
              rents.getWithdrawDate(),
              rents.getReturnDate(),
              rents.getPrice(),
              rents.getPaymentStatus(),
              rents.getCreatedAt(),
              rents.getDeletedAt()

        );
    }
}
