package my.test.azoft.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Role implements GrantedAuthority {
    @Id
    private int id;
    @Column(length = 50)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
