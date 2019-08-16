package blog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Mannager.
 */
@Entity
@Table(name = "mannager")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mannager extends AbstractAuditingEntity2 implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spent_money")
    private String spentMoney;

    @Column(name = "spent_content")
    private String spentContent;

    @Column(name = "status")
    private Integer status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpentMoney() {
        return spentMoney;
    }

    public Mannager spentMoney(String spentMoney) {
        this.spentMoney = spentMoney;
        return this;
    }

    public void setSpentMoney(String spentMoney) {
        this.spentMoney = spentMoney;
    }

    public String getSpentContent() {
        return spentContent;
    }

    public Mannager spentContent(String spentContent) {
        this.spentContent = spentContent;
        return this;
    }

    public void setSpentContent(String spentContent) {
        this.spentContent = spentContent;
    }


    public Integer getStatus() {
        return status;
    }

    public Mannager status(Integer status) {
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
        Mannager mannager = (Mannager) o;
        if (mannager.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mannager.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mannager{" +
            "id=" + getId() +
            ", spentMoney='" + getSpentMoney() + "'" +
            ", spentContent='" + getSpentContent() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
