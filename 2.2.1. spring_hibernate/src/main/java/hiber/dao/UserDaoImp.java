package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUser(String model, int series) {
        String hql = "from User where id = " + "(select id from Car where model = :model and series = :series)";
        User result = (User) sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("model", model)
                .setParameter("series", series)
                .getSingleResult();
        return result;
    }


}
