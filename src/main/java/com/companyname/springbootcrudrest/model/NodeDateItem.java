//package com.companyname.springbootcrudrest.model;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EntityListeners;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedBy;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//
//@Entity
//@Table(name = "node_data")
//@EntityListeners(AuditingEntityListener.class)
//public class NodeDateItem  {
//
//    private long id;
//    @NotNull
//    @Size(min = 2, message = "First Name should have atleast 2 characters")
//    private String firstName;
//    @NotNull
//    @Size(min = 2, message = "First Name should have atleast 2 characters")
//    private String lastName;
//    @Email
//    @NotBlank
//    private String emailId;
//    private Date createdAt;
//    private String createdBy;
//    private Date updatedAt;
//    private String updatedby;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    public long getId() {
//        return id;
//    }
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    @Column(name = "first_name", nullable = false)
//    public String getName() {
//        return firstName;
//    }
//    public void setName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    @Column(name = "last_name", nullable = false)
//    public String getLastName() {
//        return lastName;
//    }
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    @Column(name = "email_address", nullable = false)
//    public String getEmailId() {
//        return emailId;
//    }
//    public void setEmailId(String emailId) {
//        this.emailId = emailId;
//    }
//
//    @Column(name = "created_at", nullable = false)
//    @CreatedDate
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    @Column(name = "created_by", nullable = false)
//    @CreatedBy
//    public String getCreatedBy() {
//        return createdBy;
//    }
//    public void setCreatedBy(String createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    @Column(name = "updated_at", nullable = false)
//    @LastModifiedDate
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    @Column(name = "updated_by", nullable = false)
//    @LastModifiedBy
//    public String getUpdatedby() {
//        return updatedby;
//    }
//    public void setUpdatedby(String updatedby) {
//        this.updatedby = updatedby;
//    }
//}