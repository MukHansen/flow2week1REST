package facades;

import dto.PersonDTO;
import entities.Person;
import exceptions.PersonNotFoundException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //TODO Remove/Change this before use
    public long getPersonCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long personCount = (long) em.createQuery("SELECT COUNT(p) FROM Person p").getSingleResult();
            return personCount;
        } finally {
            em.close();
        }
    }

    @Override
    public Person addPerson(String fName, String lName, String phone) {
        EntityManager em = emf.createEntityManager();
        Person person = new Person(fName, lName, phone);
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
            return person;
        } finally {
            em.close();
        }
    }

    @Override
    public String deletePerson(int id) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, id);
            if (person == null) {
                throw new PersonNotFoundException("Person with provided id does not exist");
            }
            em.remove(person);
            em.getTransaction().commit();
//            Query query = em.createQuery("DELETE FROM Person p WHERE p.id = :id");
//            query.setParameter("id", id);
//            query.executeUpdate();

            return "{\"status\":\"OK, Person Deleted\"}";
        } finally {
            em.close();
        }
    }

    @Override
    public Person getPerson(int id) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            Person person = em.find(Person.class, id);
            if (person == null) {
                throw new PersonNotFoundException("Person with provided id does not exist");
            }
            return person;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Person> getAllPersons() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query
                    = em.createQuery("Select p from Person p", Person.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Person editPerson(Person p) throws PersonNotFoundException {
        Person personNew = p;
        if (p == null) {
            throw new PersonNotFoundException("Person with provided id does not exist");
        }
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(p);

//            Query query = em.createQuery("UPDATE Person p SET p.firstName = :firstName , p.lastName = :lastName , p.phone = :phone "
//                    + "WHERE p.id = :id");
//            query.setParameter("firstName", p.getFirstName());
//            query.setParameter("lastName", p.getLastName());
//            query.setParameter("phone", p.getPhone());
//            query.setParameter("id", p.getId());
//            query.executeUpdate();

            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return personNew;
    }
}
