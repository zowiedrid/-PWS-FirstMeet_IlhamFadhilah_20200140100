/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Kel6PABD.AppRentCar;

import Kel6PABD.AppRentCar.exceptions.IllegalOrphanException;
import Kel6PABD.AppRentCar.exceptions.NonexistentEntityException;
import Kel6PABD.AppRentCar.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Zowie
 */
public class TbMobilJpaController implements Serializable {

    public TbMobilJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Kel6PABD_AppRentCar_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbMobilJpaController() {
    }

    
    
    public void create(TbMobil tbMobil) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbTransaksi tbTransaksi = tbMobil.getTbTransaksi();
            if (tbTransaksi != null) {
                tbTransaksi = em.getReference(tbTransaksi.getClass(), tbTransaksi.getIdTransaksi());
                tbMobil.setTbTransaksi(tbTransaksi);
            }
            em.persist(tbMobil);
            if (tbTransaksi != null) {
                TbMobil oldIdMobilOfTbTransaksi = tbTransaksi.getIdMobil();
                if (oldIdMobilOfTbTransaksi != null) {
                    oldIdMobilOfTbTransaksi.setTbTransaksi(null);
                    oldIdMobilOfTbTransaksi = em.merge(oldIdMobilOfTbTransaksi);
                }
                tbTransaksi.setIdMobil(tbMobil);
                tbTransaksi = em.merge(tbTransaksi);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTbMobil(tbMobil.getIdMobil()) != null) {
                throw new PreexistingEntityException("TbMobil " + tbMobil + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbMobil tbMobil) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbMobil persistentTbMobil = em.find(TbMobil.class, tbMobil.getIdMobil());
            TbTransaksi tbTransaksiOld = persistentTbMobil.getTbTransaksi();
            TbTransaksi tbTransaksiNew = tbMobil.getTbTransaksi();
            List<String> illegalOrphanMessages = null;
            if (tbTransaksiOld != null && !tbTransaksiOld.equals(tbTransaksiNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain TbTransaksi " + tbTransaksiOld + " since its idMobil field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbTransaksiNew != null) {
                tbTransaksiNew = em.getReference(tbTransaksiNew.getClass(), tbTransaksiNew.getIdTransaksi());
                tbMobil.setTbTransaksi(tbTransaksiNew);
            }
            tbMobil = em.merge(tbMobil);
            if (tbTransaksiNew != null && !tbTransaksiNew.equals(tbTransaksiOld)) {
                TbMobil oldIdMobilOfTbTransaksi = tbTransaksiNew.getIdMobil();
                if (oldIdMobilOfTbTransaksi != null) {
                    oldIdMobilOfTbTransaksi.setTbTransaksi(null);
                    oldIdMobilOfTbTransaksi = em.merge(oldIdMobilOfTbTransaksi);
                }
                tbTransaksiNew.setIdMobil(tbMobil);
                tbTransaksiNew = em.merge(tbTransaksiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tbMobil.getIdMobil();
                if (findTbMobil(id) == null) {
                    throw new NonexistentEntityException("The tbMobil with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbMobil tbMobil;
            try {
                tbMobil = em.getReference(TbMobil.class, id);
                tbMobil.getIdMobil();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbMobil with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            TbTransaksi tbTransaksiOrphanCheck = tbMobil.getTbTransaksi();
            if (tbTransaksiOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbMobil (" + tbMobil + ") cannot be destroyed since the TbTransaksi " + tbTransaksiOrphanCheck + " in its tbTransaksi field has a non-nullable idMobil field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tbMobil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TbMobil> findTbMobilEntities() {
        return findTbMobilEntities(true, -1, -1);
    }

    public List<TbMobil> findTbMobilEntities(int maxResults, int firstResult) {
        return findTbMobilEntities(false, maxResults, firstResult);
    }

    private List<TbMobil> findTbMobilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbMobil.class));
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

    public TbMobil findTbMobil(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbMobil.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbMobilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbMobil> rt = cq.from(TbMobil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
