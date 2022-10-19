/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Kel6PABD.AppRentCar;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Zowie
 */
@Entity
@Table(name = "tb_mobil")
@NamedQueries({
    @NamedQuery(name = "TbMobil.findAll", query = "SELECT t FROM TbMobil t"),
    @NamedQuery(name = "TbMobil.findByIdMobil", query = "SELECT t FROM TbMobil t WHERE t.idMobil = :idMobil"),
    @NamedQuery(name = "TbMobil.findByNoMobil", query = "SELECT t FROM TbMobil t WHERE t.noMobil = :noMobil"),
    @NamedQuery(name = "TbMobil.findByMrkMobil", query = "SELECT t FROM TbMobil t WHERE t.mrkMobil = :mrkMobil"),
    @NamedQuery(name = "TbMobil.findByHrgSewa", query = "SELECT t FROM TbMobil t WHERE t.hrgSewa = :hrgSewa")})
public class TbMobil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_mobil")
    private Integer idMobil;
    @Basic(optional = false)
    @Column(name = "no_mobil")
    private String noMobil;
    @Basic(optional = false)
    @Column(name = "mrk_mobil")
    private String mrkMobil;
    @Basic(optional = false)
    @Column(name = "hrg_sewa")
    private int hrgSewa;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idMobil")
    private TbTransaksi tbTransaksi;

    public TbMobil() {
    }

    public TbMobil(Integer idMobil) {
        this.idMobil = idMobil;
    }

    public TbMobil(Integer idMobil, String noMobil, String mrkMobil, int hrgSewa) {
        this.idMobil = idMobil;
        this.noMobil = noMobil;
        this.mrkMobil = mrkMobil;
        this.hrgSewa = hrgSewa;
    }

    public Integer getIdMobil() {
        return idMobil;
    }

    public void setIdMobil(Integer idMobil) {
        this.idMobil = idMobil;
    }

    public String getNoMobil() {
        return noMobil;
    }

    public void setNoMobil(String noMobil) {
        this.noMobil = noMobil;
    }

    public String getMrkMobil() {
        return mrkMobil;
    }

    public void setMrkMobil(String mrkMobil) {
        this.mrkMobil = mrkMobil;
    }

    public int getHrgSewa() {
        return hrgSewa;
    }

    public void setHrgSewa(int hrgSewa) {
        this.hrgSewa = hrgSewa;
    }

    public TbTransaksi getTbTransaksi() {
        return tbTransaksi;
    }

    public void setTbTransaksi(TbTransaksi tbTransaksi) {
        this.tbTransaksi = tbTransaksi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMobil != null ? idMobil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbMobil)) {
            return false;
        }
        TbMobil other = (TbMobil) object;
        if ((this.idMobil == null && other.idMobil != null) || (this.idMobil != null && !this.idMobil.equals(other.idMobil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Kel6PABD.AppRentCar.TbMobil[ idMobil=" + idMobil + " ]";
    }
    
}
