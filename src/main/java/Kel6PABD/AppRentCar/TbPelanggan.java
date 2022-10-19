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
@Table(name = "tb_pelanggan")
@NamedQueries({
    @NamedQuery(name = "TbPelanggan.findAll", query = "SELECT t FROM TbPelanggan t"),
    @NamedQuery(name = "TbPelanggan.findByIdPelanggan", query = "SELECT t FROM TbPelanggan t WHERE t.idPelanggan = :idPelanggan"),
    @NamedQuery(name = "TbPelanggan.findByNmPelanggan", query = "SELECT t FROM TbPelanggan t WHERE t.nmPelanggan = :nmPelanggan"),
    @NamedQuery(name = "TbPelanggan.findByAlamat", query = "SELECT t FROM TbPelanggan t WHERE t.alamat = :alamat"),
    @NamedQuery(name = "TbPelanggan.findByNoHp", query = "SELECT t FROM TbPelanggan t WHERE t.noHp = :noHp")})
public class TbPelanggan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_pelanggan")
    private Integer idPelanggan;
    @Basic(optional = false)
    @Column(name = "nm_pelanggan")
    private int nmPelanggan;
    @Basic(optional = false)
    @Column(name = "alamat")
    private String alamat;
    @Basic(optional = false)
    @Column(name = "no_hp")
    private int noHp;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idPelanggan")
    private TbTransaksi tbTransaksi;

    public TbPelanggan() {
    }

    public TbPelanggan(Integer idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public TbPelanggan(Integer idPelanggan, int nmPelanggan, String alamat, int noHp) {
        this.idPelanggan = idPelanggan;
        this.nmPelanggan = nmPelanggan;
        this.alamat = alamat;
        this.noHp = noHp;
    }

    public Integer getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(Integer idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public int getNmPelanggan() {
        return nmPelanggan;
    }

    public void setNmPelanggan(int nmPelanggan) {
        this.nmPelanggan = nmPelanggan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getNoHp() {
        return noHp;
    }

    public void setNoHp(int noHp) {
        this.noHp = noHp;
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
        hash += (idPelanggan != null ? idPelanggan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbPelanggan)) {
            return false;
        }
        TbPelanggan other = (TbPelanggan) object;
        if ((this.idPelanggan == null && other.idPelanggan != null) || (this.idPelanggan != null && !this.idPelanggan.equals(other.idPelanggan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Kel6PABD.AppRentCar.TbPelanggan[ idPelanggan=" + idPelanggan + " ]";
    }
    
}
