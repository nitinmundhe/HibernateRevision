import javax.persistence.*;

@Entity
@Table(name = "employee_class")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "employee_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "Employee")
public class Employee {
    @Id
    @GeneratedValue
    protected int id;
    protected int sal;
    protected String name;
    public Employee() {
    }
    public Employee(String name,int sal) {
        this.sal = sal;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSal() {
        return sal;
    }

    public void setSal(int sal) {
        this.sal = sal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
