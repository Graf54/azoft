package my.test.azoft.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Role implements GrantedAuthority {
    @Id
    private int id;
    @Column(length = 50)
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private List<User> user;

    @Override
    public String getAuthority() {
        return name;
    }
}
