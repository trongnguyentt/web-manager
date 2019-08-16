package blog.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Mannager entity.
 */
public class MannagerDTO implements Serializable {

    private Long id;

    private String spentMoney;

    private String spentContent;

    private String createdBy;

    private Instant createdDate;

    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpentMoney() {
        return spentMoney;
    }

    public void setSpentMoney(String spentMoney) {
        this.spentMoney = spentMoney;
    }

    public String getSpentContent() {
        return spentContent;
    }

    public void setSpentContent(String spentContent) {
        this.spentContent = spentContent;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
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

        MannagerDTO mannagerDTO = (MannagerDTO) o;
        if(mannagerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mannagerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MannagerDTO{" +
            "id=" + getId() +
            ", spentMoney='" + getSpentMoney() + "'" +
            ", spentContent='" + getSpentContent() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
