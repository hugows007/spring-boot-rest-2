package br.com.hugows.restwithspringboot.vo;

import java.io.Serializable;
import java.util.Objects;

public class PersonVO implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gener;

    public PersonVO() {
    }

    public PersonVO(Long id, String firstName, String lastName, String address, String gener) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.gener = gener;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGener() {
        return gener;
    }

    public void setGener(String gener) {
        this.gener = gener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonVO personVO = (PersonVO) o;
        return Objects.equals(id, personVO.id) &&
                Objects.equals(firstName, personVO.firstName) &&
                Objects.equals(lastName, personVO.lastName) &&
                Objects.equals(address, personVO.address) &&
                Objects.equals(gener, personVO.gener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, address, gener);
    }
}
