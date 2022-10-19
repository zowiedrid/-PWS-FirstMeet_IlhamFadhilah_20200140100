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
public class TbTransaksiJpaController implements Serializable {

    public TbTransaksiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Kel6PABD_AppRentCar_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbTransaksiJpaController() {
    }

    
    
    public void create(TbTransaksi tbTransaksi) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        TbPelanggan idPelangganOrphanCheck = tbTransaksi.getIdPelanggan();
        if (idPelangganOrphanCheck != null) {
            TbTransaksi oldTbTransaksiOfIdPelanggan = idPelangganOrphanCheck.getTbTransaksi();
            if (oldTbTransaksiOfIdPelanggan != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The TbPelanggan " + idPelangganOrphanCheck + " already has an item of type TbTransaksi whose idPelanggan column cannot be null. Please make another selection for the idPelanggan field.");
            }
        }
        TbMobil idMobilOrphanCheck = tbTransaksi.getIdMobil();
        if (idMobilOrphanCheck != null) {
            TbTransaksi oldTbTransaksiOfIdMobil = idMobilOrphanCheck.getTbTransaksi();
            if (oldTbTransaksiOfIdMobil != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The TbMobil " + idMobilOrphanCheck + " already has an item of type TbTransaksi whose idMobil column cannot be null. Please make another selection for the idMobil field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbPelanggan idPelanggan = tbTransaksi.getIdPelanggan();
            if (idPelanggan != null) {
                idPelanggan = em.getReference(idPelanggan.getClass(), idPelanggan.getIdPelanggan());
                tbTransaksi.setIdPelanggan(idPelanggan);
            }
            TbMobil idMobil = tbTransaksi.getIdMobil();
            if (idMobil != null) {
                idMobil = em.getReference(idMobil.getClass(), idMobil.getIdMobil());
                tbTransaksi.setIdMobil(idMobil);
            }
            em.persist(tbTransaksi);
            if (idPelanggan != null) {
                idPelanggan.setTbTransaksi(tbTransaksi);
                idPelanggan = em.merge(idPelanggan);
            }
            if (idMobil != null) {
                idMobil.setTbTransaksi(tbTransaksi);
                idMobil = em.merge(idMobil);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTbTransaksi(tbTransaksi.getIdTransaksi()) != null) {
                throw new PreexistingEntityException("TbTransaksi " + tbTransaksi + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbTransaksi tbTransaksi) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbTransaksi persistentTbTransaksi = em.find(TbTransaksi.class, tbTransaksi.getIdTransaksi());
            TbPelanggan idPelangganOld = persistentTbTransaksi.getIdPelanggan();
            TbPelanggan idPelangganNew = tbTransaksi.getIdPelanggan();
            TbMobil idMobilOld = persistentTbTransaksi.getIdMobil();
            TbMobil idMobilNew = tbTransaksi.getIdMobil();
            List<String> illegalOrphanMessages = null;
            if (idPelangganNew != null && !idPelangganNew.equals(idPelangganOld)) {
                TbTransaksi oldTbTransaksiOfIdPelanggan = idPelangganNew.getTbTransaksi();
                if (oldTbTransaksiOfIdPelanggan != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The TbPelanggan " + idPelangganNew + " already has an item of type TbTransaksi whose idPelanggan column cannot be null. Please make another selection for the idPelanggan field.");
                }
            }
            if (idMobilNew != null && !idMobilNew.equals(idMobilOld)) {
                TbTransaksi oldTbTransaksiOfIdMobil = idMobilNew.getTbTransaksi();
                if (oldTbTransaksiOfIdMobil != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The TbMobil " + idMobilNew + " already has an item of type TbTransaksi whose idMobil column cannot be null. Please make another selection for the idMobil field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idPelangganNew != null) {
                idPelangganNew = em.getReference(idPelangganNew.getClass(), idPelangganNew.getIdPelanggan());
                tbTransaksi.setIdPelanggan(idPelangganNew);
            }
            if (idMobilNew != null) {
                idMobilNew = em.getReference(idMobilNew.getClass(), idMobilNew.getIdMobil());
                tbTransaksi.setIdMobil(idMobilNew);
            }
            tbTransaksi = em.merge(tbTransaksi);
            if (idPelangganOld != null && !idPelangganOld.equals(idPelangganNew)) {
                idPelangganOld.setTbTransaksi(null);
                idPelangganOld = em.merge(idPelangganOld);
            }
            if (idPelangganNew != null && !idPelangganNew.equals(idPelangganOld)) {
                idPelangganNew.setTbTransaksi(tbTransaksi);
                idPelangganNew = em.merge(idPelangganNew);
            }
            if (idMobilOld != null && !idMobilOld.equals(idMobilNew)) {
                idMobilOld.setTbTransaksi(null);
                idMobilOld = em.merge(idMobilOld);
            }
            if (idMobilNew != null && !idMobilNew.equals(idMobilOld)) {
                idMobilNew.setTbTransaksi(tbTransaksi);
                idMobilNew = em.merge(idMobilNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tbTransaksi.getIdTransaksi();
                if (findTbTransaksi(id) == null) {
                    throw new NonexistentEntityException("The tbTransaksi with id " + id + " no longer exists.");
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
            TbTransaksi tbTransaksi;
            try {
                tbTransaksi = em.getReference(TbTransaksi.class, id);
                tbTransaksi.getIdTransaksi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbTransaksi with id " + id + " no longer exists.", enfe);
            }
            TbPelanggan idPelanggan = tbTransaksi.getIdPelanggan();
            if (idPelanggan != null) {
                idPelanggan.setTbTransaksi(null);
                idPelanggan = em.merge(idPelanggan);
            }
            TbMobil idMobil = tbTransaksi.getIdMobil();
            if (idMobil != null) {
                idMobil.setTbTransaksi(null);
                idMobil = em.merge(idMobil);
            }
            em.remove(tbTransaksi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TbTransaksi> findTbTransaksiEntities() {
        return findTbTransaksiEntities(true, -1, -1);
    }

    public List<TbTransaksi> findTbTransaksiEntities(int maxResults, int firstResult) {
        return findTbTransaksiEntities(false, maxResults, firstResult);
    }

    private List<TbTransaksi> findTbTransaksiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbTransaksi.class));
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

    public TbTransaksi findTbTransaksi(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbTransaksi.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbTransaksiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbTransaksi> rt = cq.from(TbTransaksi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
