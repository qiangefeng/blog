import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "loginName")
    private String loginName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
