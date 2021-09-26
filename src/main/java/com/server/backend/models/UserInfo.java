package com.server.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.CascadeType.REFRESH;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = {DETACH, MERGE, PERSIST, REFRESH})
    @JoinTable(
            name = "user_plants",
            joinColumns = @JoinColumn(name = "fk_user_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_plant_id")
    )
    private Set<PlantModel> ownPlants;

    public UserInfo() {
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

    public Set<PlantModel> getOwnPlants() {
        return ownPlants;
    }

    public void setOwnPlants(Set<PlantModel> ownPlants) {
        this.ownPlants = ownPlants;
    }

    public void addPlantToOwnCollection(PlantModel plant) {
        if (ownPlants == null) {
            ownPlants = new HashSet<>(200);
        }
        ownPlants.add(plant);
    }

    public void removePlantFromOwnCollection(PlantModel plant) {
        if (ownPlants == null) {
            throw new EntityNotFoundException("No Plants Exist for this user");
        }
        ownPlants.remove(plant);
    }
}
