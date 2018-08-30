package micronaut.jpa.ms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "fullname", nullable = false, unique = true)
    private String fullname;

    @NotNull
    @Column(name = "citizennumber", nullable = false)
    private Long citizenNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFullname()
    {
        return fullname;
    }

    public void setFullname(String fullname)
    {
        this.fullname = fullname;
    }

    public Long getCitizenNumber()
    {
        return citizenNumber;
    }

    public void setCitizenNumber(Long citizenNumber)
    {
        this.citizenNumber = citizenNumber;
    }

    public Set<Order> getOrders()
    {
        return orders;
    }

    public void setOrders(Set<Order> orders)
    {
        this.orders = orders;
    }

    @Override
    public String toString()
    {
        return "Customer{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", citizenNumber=" + citizenNumber +
                ", orders=" + orders +
                '}';
    }
}
