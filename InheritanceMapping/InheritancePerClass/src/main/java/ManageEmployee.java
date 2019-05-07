import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Iterator;
import java.util.List;

public class ManageEmployee {

    private static SessionFactory factory;

    public static void main(String[] args) {

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Employee E1 = new Employee("Joe",2500);
        Employee E2 = new SalesPerson("Ram",3000,500);
        session.save(E1);
        session.save(E2);
        tx.commit();

        ManageEmployee manageEmployee = new ManageEmployee();
        manageEmployee.listEmployee();
    }

    /* Method to  READ all the employees */
    public void listEmployee() {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM SalesPerson").list();
            for (Iterator iterator = employees.iterator(); iterator.hasNext(); ) {
                SalesPerson employee = (SalesPerson) iterator.next();
                System.out.print("{" + employee.getName());
                System.out.print("," + employee.getSal());
                System.out.println("," + employee.getIncentive() + "}");
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
