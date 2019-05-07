import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="EMPLOYEE")
public class Employee {


    @Column(name="EMPLOYEE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Id
    @Column(name="key")
    private String key;

    @Column(name="FIRSTNAME")
    private String firstname;

    @Column(name="LASTNAME")
    private String lastname;


    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="EMPLOYEE_COLLEAGUE",
            joinColumns={@JoinColumn(name="EMPLOYEE_Key")},
            inverseJoinColumns={@JoinColumn(name="COLLEAGUE_Key")})
    private Set<Employee> colleagues = new HashSet<Employee>();

    @ManyToMany(mappedBy="colleagues")
    private Set<Employee> teammates = new HashSet<Employee>();

    public Employee() {
    }

    public Employee(String key,String firstname, String lastname) {
        this.key = key;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    // Getter and Setter methods

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Set<Employee> getColleagues() {
        return colleagues;
    }

    public void setColleagues(Set<Employee> colleagues) {
        this.colleagues = colleagues;
    }

    public Set<Employee> getTeammates() {
        return teammates;
    }

    public void setTeammates(Set<Employee> teammates) {
        this.teammates = teammates;
    }
}