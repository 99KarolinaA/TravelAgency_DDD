package mk.ukim.finki.emt.offermanagement.domain.models;

import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObject;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="clients")
public class Client extends AbstractEntity<ClientId> {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Integer age;

    public Client() {
    }
}
