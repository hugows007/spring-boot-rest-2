package br.com.hugows.restwithspringboot.data.vo.v2;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
public class PersonVO implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gener;
    private Date birthDay;

}
