import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="employee_class")
@DiscriminatorValue(value="Sales Person")
public class SalesPerson extends Employee{
    private int incentive;

    public SalesPerson(){

    }

    public SalesPerson(String name,int sal, int incentive) {
        super(name,sal);
        this.incentive = incentive;
    }

    public int getIncentive() {
        return incentive;
    }

    public void setIncentive(int incentive) {
        this.incentive = incentive;
    }
}
