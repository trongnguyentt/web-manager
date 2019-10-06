package blog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Games.
 */
@Entity
@Table(name = "games")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Games implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_tai_khoan")
    private String tenTaiKhoan;

    @Column(name = "thoi_gian_tao")
    private Instant thoiGianTao;

    @Column(name = "thoi_gian_truy_cap_cuoi")
    private Instant thoiGianTruyCapCuoi;

    @Column(name = "status")
    private Integer status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public Games tenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
        return this;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public Instant getThoiGianTao() {
        return thoiGianTao;
    }

    public Games thoiGianTao(Instant thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
        return this;
    }

    public void setThoiGianTao(Instant thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public Instant getThoiGianTruyCapCuoi() {
        return thoiGianTruyCapCuoi;
    }

    public Games thoiGianTruyCapCuoi(Instant thoiGianTruyCapCuoi) {
        this.thoiGianTruyCapCuoi = thoiGianTruyCapCuoi;
        return this;
    }

    public void setThoiGianTruyCapCuoi(Instant thoiGianTruyCapCuoi) {
        this.thoiGianTruyCapCuoi = thoiGianTruyCapCuoi;
    }

    public Integer getStatus() {
        return status;
    }

    public Games status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Games games = (Games) o;
        if (games.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), games.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Games{" +
            "id=" + getId() +
            ", tenTaiKhoan='" + getTenTaiKhoan() + "'" +
            ", thoiGianTao='" + getThoiGianTao() + "'" +
            ", thoiGianTruyCapCuoi='" + getThoiGianTruyCapCuoi() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
