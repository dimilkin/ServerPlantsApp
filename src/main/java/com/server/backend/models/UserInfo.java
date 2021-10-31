package com.server.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @Column(name = "enabled")
    private boolean enabled;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "role_id")
    UserRole userRole;

    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private Token token;

    @JsonIgnore
    @OneToMany(mappedBy = "userOwner", fetch = FetchType.EAGER)
    private Set<UserPlant> userPlants = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "userAssigned", fetch = FetchType.EAGER)
    private Set<UserPlant> assignedUserPlants = new HashSet<>();

    public UserInfo() {
        if (userPlants == null) {
            setOwnPlants(new HashSet<>());
        }
        if (assignedUserPlants == null) {
            setAssignedPlants(new HashSet<>());
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Set<UserPlant> getOwnPlants() {
        return userPlants;
    }

    public void setOwnPlants(Set<UserPlant> ownPlants) {
        this.userPlants = ownPlants;
    }

    public Set<UserPlant> getAssignedPlants() {
        return assignedUserPlants;
    }

    public void setAssignedPlants(Set<UserPlant> assignedPlants) {
        this.assignedUserPlants = assignedPlants;
    }

    public <E extends Collection<UserPlant>>void addPlantToCollection(UserPlant plant, E collection) {
        collection.add(plant);
    }

    public <E extends Collection<UserPlant>> void removePlantFromCollection(UserPlant plant, E collection) {
        if (collection == null) {
            throw new EntityNotFoundException("No Plants Exist for this user");
        }
        collection.remove(plant);
    }
}
