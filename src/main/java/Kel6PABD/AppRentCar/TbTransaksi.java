/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Kel6PABD.AppRentCar;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Zowie
 */
@Entity
@Table(name = "tb_transaksi")
@NamedQueries({
    @NamedQuery(name = "TbTransaksi.findAll", query = "SELECT t FROM TbTransaksi t"),
    @NamedQuery(name = "TbTransaksi.findByIdTransaksi", query = "SELECT t FROM TbTransaksi t WHERE t.idTransaksi = :idTransaksi"),
    @NamedQuery(name = "TbTransaksi.findByLamaSewa", query = "SELECT t FROM TbTransaksi t WHERE t.lamaSewa = :lamaSewa"),
    @NamedQuery(name = "TbTransaksi.findByTglSewa", query = "SELECT t FROM TbTransaksi t WHERE t.tglSewa = :tglSewa"),
    @NamedQuery(name = "TbTransaksi.findByTotalSewa", query = "SELECT t FROM TbTransaksi t WHERE t.totalSewa = :totalSewa")})
public class TbTransaksi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_transaksi")
    private Integer idTransaksi;
    @Basic(optional = false)
    @Column(name = "lama_sewa")
    private int lamaSewa;
    @Basic(optional = false)
    @Column(name = "tgl_sewa")
    @Temporal(TemporalType.DATE)
    private Date tglSewa;
    @Basic(optional = false)
    @Column(name = "total_sewa")
    private int totalSewa;
    @JoinColumn(name = "id_pelanggan", referencedColumnName = "id_pelanggan")
    @OneToOne(optional = false)
    private TbPelanggan idPelanggan;
    @JoinColumn(name = "id_mobil", referencedColumnName = "id_mobil")
    @OneToOne(optional = false)
    private TbMobil idMobil;

    public TbTransaksi() {
    }

    public TbTransaksi(Integer idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public TbTransaksi(Integer idTransaksi, int lamaSewa, Date tglSewa, int totalSewa) {
        this.idTransaksi = idTransaksi;
        this.lamaSewa = lamaSewa;
        this.tglSewa = tglSewa;
        this.totalSewa = totalSewa;
    }

    public Integer getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(Integer idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public int getLamaSewa() {
        return lamaSewa;
    }

    public void setLamaSewa(int lamaSewa) {
        this.lamaSewa = lamaSewa;
    }

    public Date getTglSewa() {
        return tglSewa;
    }

    public void setTglSewa(Date tglSewa) {
        this.tglSewa = tglSewa;
    }

    public int getTotalSewa() {
        return totalSewa;
    }

    public void setTotalSewa(int totalSewa) {
        this.totalSewa = totalSewa;
    }

    public TbPelanggan getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(TbPelanggan idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public TbMobil getIdMobil() {
        return idMobil;
    }

    public void setIdMobil(TbMobil idMobil) {
        this.idMobil = idMobil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransaksi != null ? idTransaksi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbTransaksi)) {
            return false;
        }
        TbTransaksi other = (TbTransaksi) object;
        if ((this.idTransaksi == null && other.idTransaksi != null) || (this.idTransaksi != null && !this.idTransaksi.equals(other.idTransaksi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Kel6PABD.AppRentCar.TbTransaksi[ idTransaksi=" + idTransaksi + " ]";
    }
    
}
