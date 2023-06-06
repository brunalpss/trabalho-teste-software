package alugueldecarros.models;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@ToString
@Table(name = "user_roles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_roles_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private Date createdAt;
    private Date deletedAt;

    public UserRole() {
    }

    public UserRole(User user, Role role, Date createdAt, Date deletedAt) {
        this.user = user;
        this.role = role;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

}
