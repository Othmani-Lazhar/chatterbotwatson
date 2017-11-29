package restchatbot.entities;

import javax.persistence.*;
import java.io.Serializable;
@Entity
public class Institut implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "idSeqISI")
    @SequenceGenerator(name = "idSeqISI", sequenceName = "isi_seq", allocationSize = 1, initialValue = 1)
    private Long id;
    private String name;
    private String addr;
    private String contact;
    private String email;
    private String logo;

    public Institut(String name, String addr, String contact, String email, String logo) {
        this.name = name;
        this.addr = addr;
        this.contact = contact;
        this.email = email;
        this.logo = logo;
    }



    public Institut() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
