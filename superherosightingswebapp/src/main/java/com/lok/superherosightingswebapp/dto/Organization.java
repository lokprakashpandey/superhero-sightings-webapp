/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-08
 * purpose: entity class for organization table
 */
package com.lok.superherosightingswebapp.dto;

import java.util.List;
import java.util.Objects;

public class Organization {

    private int id;
    private String name;
    private String description;
    private String address;
    private String contactNumber;

    //Since there is a m-to-m relationship between the entities superhero and organization
    private List<Superhero> superheroes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public List<Superhero> getSuperheroes() {
        return superheroes;
    }

    public void setSuperheroes(List<Superhero> superheroes) {
        this.superheroes = superheroes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.id;
        hash = 43 * hash + Objects.hashCode(this.name);
        hash = 43 * hash + Objects.hashCode(this.description);
        hash = 43 * hash + Objects.hashCode(this.address);
        hash = 43 * hash + Objects.hashCode(this.contactNumber);
        hash = 43 * hash + Objects.hashCode(this.superheroes);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Organization other = (Organization) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.contactNumber, other.contactNumber)) {
            return false;
        }
        return Objects.equals(this.superheroes, other.superheroes);
    }

}
