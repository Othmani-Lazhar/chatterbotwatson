package restchatbot.entities;

import javax.persistence.*;
import java.io.Serializable;
    @Entity
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "idSeqPers")
    @SequenceGenerator(name = "idSeqPers", sequenceName = "per_seq", allocationSize = 1, initialValue = 1)
    private Long id;
    private String name;
    private String addr;
    private String contact;
    private String email;
    private String photo;

        public Person() {
        }

        public Person(String name, String addr, String contact, String email, String photo) {
            this.name = name;
            this.addr = addr;
            this.contact = contact;
            this.email = email;
            this.photo = photo;
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

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }
