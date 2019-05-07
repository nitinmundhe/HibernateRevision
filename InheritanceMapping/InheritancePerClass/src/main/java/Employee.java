import javax.persistence.*;

@Entity
@Table(name = "employee_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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
