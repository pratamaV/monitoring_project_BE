package com.askrindo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;



@Entity
@Table(name = "mst_issued")
@EntityListeners(AuditingEntityListener.class)
public class Issued extends Auditable<String> {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String issuedDescription;

    @JsonFormat(timezone = "Asia/Jakarta",pattern = "yyyy-MM-dd")
    private Date issuedDate;
    private String issuedPlan;

    @JsonFormat(timezone = "Asia/Jakarta",pattern = "yyyy-MM-dd")
    private Date estEnddate;
    private String pic;
    private String status;

    @ManyToOne
    @JoinColumn(name = "release_id")
    private Release release;

    public Issued(String id, String issuedDescription, Date issuedDate, String issuedPlan, Date estEnddate, String pic, String status, Release release) {
        this.id = id;
        this.issuedDescription = issuedDescription;
        this.issuedDate = issuedDate;
        this.issuedPlan = issuedPlan;
        this.estEnddate = estEnddate;
        this.pic = pic;
        this.status = status;
        this.release = release;
    }

    public Issued() {
    }

    public Issued(String issuedDescription, Date issuedDate, String issuedPlan, Date estEnddate, String pic, String status, Release release) {
        this.issuedDescription = issuedDescription;
        this.issuedDate = issuedDate;
        this.issuedPlan = issuedPlan;
        this.estEnddate = estEnddate;
        this.pic = pic;
        this.status = status;
        this.release = release;
    }

    public Release getRelease() {
        return release;
    }

    public void setRelease(Release release) {
        this.release = release;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssuedDescription() {
        return issuedDescription;
    }

    public void setIssuedDescription(String issuedDescription) {
        this.issuedDescription = issuedDescription;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getIssuedPlan() {
        return issuedPlan;
    }

    public void setIssuedPlan(String issuedPlan) {
        this.issuedPlan = issuedPlan;
    }

    public Date getEstEnddate() {
        return estEnddate;
    }

    public void setEstEnddate(Date estEnddate) {
        this.estEnddate = estEnddate;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
