package alugueldecarros.models.RequestEntity;

import alugueldecarros.enums.RolesEnum;
import alugueldecarros.models.Address;
import alugueldecarros.models.Role;
import alugueldecarros.models.User;
import alugueldecarros.models.dto.AddressDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
public class UserRequest {

    @NotNull(message = "Campo name não pode ser nulo")
    @NotEmpty(message = "Campo name não pode ser vazio")
    private String name;
    @NotNull(message = "Campo email não pode ser nulo")
    @NotEmpty(message = "Campo email não pode ser vazio")
    @Email(message = "Digite um email válido")
    private String email;
    @NotNull(message = "Campo password não pode ser nulo")
    @NotEmpty(message = "Campo password não pode ser vazio")
    private String password;

    @NotNull(message = "Campo roles não pode ser nulo")
    private List<String> roles;

    @NotNull(message = "Campo legal_document não pode ser nulo")
    @NotEmpty(message = "Campo legal_document não pode ser vazio")
    private String legalDocument;

    @NotNull(message = "Campo address não pode ser nulo")
    private AddressDto address;

    @NotNull(message = "Campo sex não pode ser nulo")
    @NotEmpty(message = "Campo sex não pode ser vazio")
    private String sex;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthDate;

    @NotNull(message = "Campo phone1 não pode ser nulo")
    @NotEmpty(message = "Campo phone1 não pode ser vazio")
    private String phone1;

    @NotNull(message = "Campo phone2 não pode ser nulo")
    @NotEmpty(message = "Campo phone2 não pode ser vazio")
    private String phone2;

    private Long idUser;

    public UserRequest() {
    }

    public UserRequest(String name, String email, String password, List<String> roles, String legalDocument,
                       AddressDto address, String sex, Date birthDate, String phone1, String phone2) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.legalDocument = legalDocument;
        this.address = address;
        this.sex = sex;
        this.birthDate = birthDate;
        this.phone1 = phone1;
        this.phone2 = phone2;
    }


    public static User toUser(UserRequest user, List<Role> roles, Address address) {
        Date dueDate;
        Calendar c = Calendar.getInstance();


        c.setTime(user.getBirthDate());
        Date birthDate = c.getTime();

        User newUSer;

        if(!roles.stream().anyMatch(f -> f.getName().equals(RolesEnum.CLIENT.getCode()))) {
            newUSer = new User(
                    user.getName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getSex(),
                    user.getLegalDocument(),
                    address,
                    roles,
                    birthDate,
                    user.getPhone1(),
                    user.getPhone2()
                    );

        }else {

            newUSer = new User(
                    user.getName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getSex(),
                    user.getLegalDocument(),
                    address,
                    roles,
                    birthDate,
                    user.getPhone1(),
                    user.getPhone2()
            );
        }
        return newUSer;
    }

}
