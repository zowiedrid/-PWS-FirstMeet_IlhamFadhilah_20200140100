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
public class TbPelangganJpaController implements Serializable {

    public TbPelangganJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Kel6PABD_AppRentCar_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbPelangganJpaController() {
    }

    
    
    public void create(TbPelanggan tbPelanggan) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbTransaksi tbTransaksi = tbPelanggan.getTbTransaksi();
            if (tbTransaksi != null) {
                tbTransaksi = em.getReference(tbTransaksi.getClass(), tbTransaksi.getIdTransaksi());
                tbPelanggan.setTbTransaksi(tbTransaksi);
            }
            em.persist(tbPelanggan);
            if (tbTransaksi != null) {
                TbPelanggan oldIdPelangganOfTbTransaksi = tbTransaksi.getIdPelanggan();
                if (oldIdPelangganOfTbTransaksi != null) {
                    oldIdPelangganOfTbTransaksi.setTbTransaksi(null);
                    oldIdPelangganOfTbTransaksi = em.merge(oldIdPelangganOfTbTransaksi);
                }
                tbTransaksi.setIdPelanggan(tbPelanggan);
                tbTransaksi = em.merge(tbTransaksi);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTbPelanggan(tbPelanggan.getIdPelanggan()) != null) {
                throw new PreexistingEntityException("TbPelanggan " + tbPelanggan + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbPelanggan tbPelanggan) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbPelanggan persistentTbPelanggan = em.find(TbPelanggan.class, tbPelanggan.getIdPelanggan());
            TbTransaksi tbTransaksiOld = persistentTbPelanggan.getTbTransaksi();
            TbTransaksi tbTransaksiNew = tbPelanggan.getTbTransaksi();
            List<String> illegalOrphanMessages = null;
            if (tbTransaksiOld != null && !tbTransaksiOld.equals(tbTransaksiNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain TbTransaksi " + tbTransaksiOld + " since its idPelanggan field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbTransaksiNew != null) {
                tbTransaksiNew = em.getReference(tbTransaksiNew.getClass(), tbTransaksiNew.getIdTransaksi());
                tbPelanggan.setTbTransaksi(tbTransaksiNew);
            }
            tbPelanggan = em.merge(tbPelanggan);
            if (tbTransaksiNew != null && !tbTransaksiNew.equals(tbTransaksiOld)) {
                TbPelanggan oldIdPelangganOfTbTransaksi = tbTransaksiNew.getIdPelanggan();
                if (oldIdPelangganOfTbTransaksi != null) {
                    oldIdPelangganOfTbTransaksi.setTbTransaksi(null);
                    oldIdPelangganOfTbTransaksi = em.merge(oldIdPelangganOfTbTransaksi);
                }
                tbTransaksiNew.setIdPelanggan(tbPelanggan);
                tbTransaksiNew = em.merge(tbTransaksiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tbPelanggan.getIdPelanggan();
                if (findTbPelanggan(id) == null) {
                    throw new NonexistentEntityException("The tbPelanggan with id " + id + " no longer exists.");
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
            TbPelanggan tbPelanggan;
            try {
                tbPelanggan = em.getReference(TbPelanggan.class, id);
                tbPelanggan.getIdPelanggan();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbPelanggan with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            TbTransaksi tbTransaksiOrphanCheck = tbPelanggan.getTbTransaksi();
            if (tbTransaksiOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbPelanggan (" + tbPelanggan + ") cannot be destroyed since the TbTransaksi " + tbTransaksiOrphanCheck + " in its tbTransaksi field has a non-nullable idPelanggan field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tbPelanggan);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TbPelanggan> findTbPelangganEntities() {
        return findTbPelangganEntities(true, -1, -1);
    }

    public List<TbPelanggan> findTbPelangganEntities(int maxResults, int firstResult) {
        return findTbPelangganEntities(false, maxResults, firstResult);
    }

    private List<TbPelanggan> findTbPelangganEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbPelanggan.class));
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

    public TbPelanggan findTbPelanggan(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbPelanggan.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbPelangganCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbPelanggan> rt = cq.from(TbPelanggan.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
