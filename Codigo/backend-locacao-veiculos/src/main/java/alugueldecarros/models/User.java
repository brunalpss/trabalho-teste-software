package alugueldecarros.models;

import alugueldecarros.enums.SexEnum;
import alugueldecarros.models.dto.UserDto;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Data
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long idUser;

    @NotNull(message = "Campo name não pode ser nulo")
    @NotEmpty(message = "Campo name não pode ser vazio")
    private String name;

    @NotNull(message = "Campo email não pode ser nulo")
    @NotEmpty(message = "Campo email não pode ser vazio")
    private String email;

    @NotNull(message = "Campo password não pode ser nulo")
    @NotEmpty(message = "Campo password não pode ser vazio")
    private String password;

    @NotNull(message = "Campo password não pode ser nulo")
    @NotEmpty(message = "Campo password não pode ser vazio")
    private String sex;

    @NotNull(message = "Campo legal_document não pode ser nulo")
    @NotEmpty(message = "Campo legal_document não pode ser vazio")
    private String legal_document;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    /*
    @OneToOne
    @JoinColumn(name = "plans_id")
    private Plans plan;
    */

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @Column(name = "birth_date")
    private Date birthDate;

    @NotNull(message = "Campo password não pode ser nulo")
    @NotEmpty(message = "Campo password não pode ser vazio")
    @Column(name = "phone1")
    private String phone1;

    @NotNull(message = "Campo password não pode ser nulo")
    @NotEmpty(message = "Campo password não pode ser vazio")
    @Column(name = "phone2")
    private String phone2;

    private Date createdAt;
    private Date deletedAt;

    public User(){}

    public User(String name,
                String email,
                String password,
                Date birthDate,
                String phone1,
                String phone2,
                String legal_document,
                Address address,
                SexEnum sex,
                List<Role> roles
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.sex = sex.getCode();
        this.legal_document = legal_document;
        this.address = address;
        this.roles = roles;
    }

    public User(String name, String email, String password, String sex, String legal_document, Address address,
                List<Role> roles, Date birthDate, String phone1, String phone2) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.legal_document = legal_document;
        this.address = address;
        this.roles = roles;
        this.birthDate = birthDate;
        this.phone1 = phone1;
        this.phone2 = phone2;

    }



    public static User fromUserResponse(UserDto user){
        return null;
    }

}
