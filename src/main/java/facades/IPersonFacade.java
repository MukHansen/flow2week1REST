/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Person;
import exceptions.PersonNotFoundException;
import java.util.List;

/**
 *
 * @author Mkhansen;
 */
public interface IPersonFacade {

    public Person addPerson(String fName, String lName, String phone);

    public String deletePerson(int id)throws PersonNotFoundException;

    public Person getPerson(int id)throws PersonNotFoundException;

    public List<Person> getAllPersons();

    public Person editPerson(Person p) throws PersonNotFoundException;

}
