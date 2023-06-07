package hr.algebra.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Director extends Person {

    public Director(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Director(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }
}
