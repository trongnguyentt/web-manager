package blog.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Games entity.
 */
public class GamesDTO implements Serializable {

    private Long id;

    private String tenTaiKhoan;

    private Instant thoiGianTao;

    private Instant thoiGianTruyCapCuoi;

    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public Instant getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(Instant thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public Instant getThoiGianTruyCapCuoi() {
        return thoiGianTruyCapCuoi;
    }

    public void setThoiGianTruyCapCuoi(Instant thoiGianTruyCapCuoi) {
        this.thoiGianTruyCapCuoi = thoiGianTruyCapCuoi;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GamesDTO gamesDTO = (GamesDTO) o;
        if(gamesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gamesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GamesDTO{" +
            "id=" + getId() +
            ", tenTaiKhoan='" + getTenTaiKhoan() + "'" +
            ", thoiGianTao='" + getThoiGianTao() + "'" +
            ", thoiGianTruyCapCuoi='" + getThoiGianTruyCapCuoi() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
