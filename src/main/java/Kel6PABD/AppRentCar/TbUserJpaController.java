/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Kel6PABD.AppRentCar;

import Kel6PABD.AppRentCar.exceptions.NonexistentEntityException;
import Kel6PABD.AppRentCar.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Zowie
 */
public class TbUserJpaController implements Serializable {

    public TbUserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Kel6PABD_AppRentCar_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbUserJpaController() {
    }

    
    
    public void create(TbUser tbUser) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tbUser);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTbUser(tbUser.getIdUser()) != null) {
                throw new PreexistingEntityException("TbUser " + tbUser + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbUser tbUser) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tbUser = em.merge(tbUser);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tbUser.getIdUser();
                if (findTbUser(id) == null) {
                    throw new NonexistentEntityException("The tbUser with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbUser tbUser;
            try {
                tbUser = em.getReference(TbUser.class, id);
                tbUser.getIdUser();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbUser with id " + id + " no longer exists.", enfe);
            }
            em.remove(tbUser);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TbUser> findTbUserEntities() {
        return findTbUserEntities(true, -1, -1);
    }

    public List<TbUser> findTbUserEntities(int maxResults, int firstResult) {
        return findTbUserEntities(false, maxResults, firstResult);
    }

    private List<TbUser> findTbUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbUser.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbUser findTbUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbUser.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbUser> rt = cq.from(TbUser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
