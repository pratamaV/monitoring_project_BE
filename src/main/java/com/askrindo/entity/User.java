package com.askrindo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "mst_user")
public class User {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String username;
    private String userRole;

    @Column(unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division divisiUser;

    private String directorateUser;
    private String password;
    private String statusUser;
    private Float totalWeight;
    private Float totalPerformance;

    @OneToMany
    @JsonIgnore
    private List<Task> taskList = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    private List<Project> projectList = new ArrayList<>();

    public User() {
    }

    public User(String id, String username, String userRole, String email, Division divisiUser, String directorateUser, String password, String statusUser, Float totalWeight, Float totalPerformance, List<Task> taskList, List<Project> projectList) {
        this.id = id;
        this.username = username;
        this.userRole = userRole;
        this.email = email;
        this.divisiUser = divisiUser;
        this.directorateUser = directorateUser;
        this.password = password;
        this.statusUser = statusUser;
        this.totalWeight = totalWeight;
        this.totalPerformance = totalPerformance;
        this.taskList = taskList;
        this.projectList = projectList;
    }

    public User(String id, String username, String userRole, String email, Division divisiUser, String directorateUser, String password, String statusUser, List<Task> taskList, List<Project> projectList) {
        this.id = id;
        this.username = username;
        this.userRole = userRole;
        this.email = email;
        this.divisiUser = divisiUser;
        this.directorateUser = directorateUser;
        this.password = password;
        this.statusUser = statusUser;
        this.taskList = taskList;
        this.projectList = projectList;
    }

    public User(String username, String userRole, String email, Division divisiUser, String directorateUser, String password, String statusUser, List<Task> taskList, List<Project> projectList) {
        this.username = username;
        this.userRole = userRole;
        this.email = email;
        this.divisiUser = divisiUser;
        this.directorateUser = directorateUser;
        this.password = password;
        this.statusUser = statusUser;
        this.taskList = taskList;
        this.projectList = projectList;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Division getDivisiUser() {
        return divisiUser;
    }

    public void setDivisiUser(Division divisiUser) {
        this.divisiUser = divisiUser;
    }

    public String getDirectorateUser() {
        return directorateUser;
    }

    public void setDirectorateUser(String directorateUser) {
        this.directorateUser = directorateUser;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(String statusUser) {
        this.statusUser = statusUser;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public Float getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Float totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Float getTotalPerformance() {
        return totalPerformance;
    }

    public void setTotalPerformance(Float totalPerformance) {
        this.totalPerformance = totalPerformance;
    }
}
