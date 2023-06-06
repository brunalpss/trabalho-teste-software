package alugueldecarros.models;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@ToString
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roles_id")
    private Long id;
    private String name;

    private Date createdAt;
    private Date deletedAt;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

}
