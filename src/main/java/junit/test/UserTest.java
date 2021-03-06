package junit.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;

import com.fsnc.domain.User;

public class UserTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAddUser() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			User user = new User();
			user.setUserName("admin");
			em.persist(user);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		em.close();
		emf.close();
	}

	@Test
	public void testDeleteUser() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			User user = em.getReference(User.class, 1L);
			em.remove(user);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		em.close();
		emf.close();
	}
	
	@Test
	public void testUpdateUser() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			User user = em.find(User.class, 1L);//如果find不到,返回null  如果getReference查不到数据,会抛出异常
			user.setUserName("Admin");
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		em.close();
		emf.close();
	}
	
	@Test
	public void testUpdateUserByNativeSql() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Query query = em.createNativeQuery("update User u set u.userName=:userName where u.id=2");
			query.setParameter("userName", "Hello World");
			query.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		em.close();
		emf.close();
	}

	@Test
	public void testFindUser() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
		EntityManager em = emf.createEntityManager();
		try {
			User user = em.find(User.class, 1L);
			System.out.println("user: " + user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		em.close();
		emf.close();
	}

	@Test
	public void testGetReferenceUser() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
		EntityManager em = emf.createEntityManager();
		try {
			User user = em.getReference(User.class, 1L);
			System.out.println("user: " + user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		em.close();
		emf.close();
	}

	@Test
	public void testUserList() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
		EntityManager em = emf.createEntityManager();
		try {
			@SuppressWarnings("unchecked")
			List<User> list = em.createQuery("select u from User u").getResultList();
			System.out.println(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		em.close();
		emf.close();
	}

	@Test
	public void testPage() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
		EntityManager em = emf.createEntityManager();
		try {
			@SuppressWarnings("unchecked")
			List<User> list = em.createQuery("select u from User u order by u.id desc").setMaxResults(2)
					.setFirstResult(1).getResultList();
			System.out.println(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		em.close();
		emf.close();
	}

	@Test
	public void testDynamicQuery() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
		EntityManager em = emf.createEntityManager();
		try {
			@SuppressWarnings("unchecked")
			Query query = em.createQuery("select u from User u where u.id=?1");
			query.setParameter(1, 1L);
			System.out.println(query.getResultList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		em.close();
		emf.close();
	}

	@Test
	public void testDynamicQuery2() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
		EntityManager em = emf.createEntityManager();
		try {
			@SuppressWarnings("unchecked")
			Query query = em.createQuery("select u from User u where u.id=:id");
			query.setParameter("id", 2L);
			System.out.println(query.getResultList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		em.close();
		emf.close();
	}

}
