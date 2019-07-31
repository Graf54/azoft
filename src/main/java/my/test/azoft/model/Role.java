package my.test.azoft.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@ToString(exclude = "user")
@EqualsAndHashCode(of = "id")
public class Role implements GrantedAuthority {
    @Id
    @JsonView(Views.Id.class)
    private int id;

    @Column(length = 50)
    @JsonView(Views.IdName.class)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<User> user;

    @Override
    public String getAuthority() {
        return name;
    }
}
