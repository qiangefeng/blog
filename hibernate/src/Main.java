import com.fasterxml.classmate.AnnotationConfiguration;
import org.hibernate.*;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;

import java.util.UUID;

public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        try {
            System.out.println("querying all the managed entities...");
            int j= 0;
            while (j++<100){
                Transaction tx = session.beginTransaction();
                int i= 0;
                while (i++<10000){

                    User user = new User();
                    user.setLoginName(UUID.randomUUID().toString());
                    session.save(user);
                }
                tx.commit();
            }

//            System.out.println(show_databases.list());
//            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
//            for (EntityType<?> entityType : metamodel.getEntities()) {
//                final String entityName = entityType.getName();
//                final Query query = session.createQuery("from " + entityName);
//                System.out.println("executing: " + query.getQueryString());
//                for (Object o : query.list()) {
//                    System.out.println("  " + o);
//                }
//            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}