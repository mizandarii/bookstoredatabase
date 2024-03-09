package managers;

import entity.Book;
import entity.Order;
import entity.OrderHistory;
import entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class DatabaseManager {
    private EntityManagerFactory emf;
    private EntityManager em;

    public DatabaseManager() {
        emf = Persistence.createEntityManagerFactory("BookStoreDatabasePU");
        em = emf.createEntityManager();
    }

    public void closeEntityManager() {
        if (em != null && em.isOpen()) {
            em.close();
        }

        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    public void saveBook(Book book) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (book.getId() == null) {
                em.persist(book);
            } else {
                em.merge(book);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            //closeEntityManager();
        }
    }

    public void saveUser(User user) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (user.getId() == null) {
                em.persist(user);
            } else {
                em.merge(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            //closeEntityManager();
        }
    }

    public List<Book> getListBooks() {
        return em.createQuery("SELECT book FROM Book book").getResultList();
    }

    public List<User> getListUsers() {
        return em.createQuery("SELECT user FROM User user").getResultList();
    }
    
    public List<Order> getListOrders() {
        return em.createQuery("SELECT order FROM Order order").getResultList();
    }    
    
    /**
    public List<OrderHistory> getReadingBooks(){
        return  em.createQuery("SELECT history FROM History history WHERE history.returnBook=null")
                .getResultList();
    }**/

    public User authorization(String login, String password) {
        try {
            return (User) em.createQuery("SELECT user FROM User user WHERE user.login = :login AND user.password = :password")
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Book getBook(Long id) {
        try {
            return em.find(Book.class, id);
        } catch (Exception e) {
            return null;
        }
    }
    public User getUser(Long id) {
        try {
            return em.find(User.class,id);
        } catch (Exception e) {
            return null;
        }
    }

    public void saveHistory(OrderHistory history) {
        try {
            em.getTransaction().begin();
            if(history.getId() == null){
                em.persist(history);
            }else{
                em.merge(history);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    OrderHistory getHistory(Long id) {
        try {
            return em.find(OrderHistory.class,id);
        } catch (Exception e) {
            return null;
        }
    }

    List<OrderHistory> getListHistories() {
        return em.createQuery("SELECT h FROM OrderHistory h")
                .getResultList();
    }

    
}