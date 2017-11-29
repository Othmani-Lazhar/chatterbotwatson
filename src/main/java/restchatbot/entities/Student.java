package restchatbot.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@Entity
@DiscriminatorValue("INTER")
public class Student extends Person {
    private Long CIN ;
    public Student(String name, String addr, String contact, String email, String photo, Long CIN) {
        super(name, addr, contact, email, photo);
        this.CIN = CIN;
    }

    public Long getCIN() {
        return CIN;
    }

    public void setCIN(Long CIN) {
        this.CIN = CIN;
    }
}
