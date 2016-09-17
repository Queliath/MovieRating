package by.epam.movierating.service.impl;

import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.PersonService;
import org.junit.Test;

/**
 * Created by Владислав on 17.09.2016.
 */
public class PersonServiceImplTest {
    @Test(expected = ServiceException.class)
    public void getPersonByIdTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        PersonService personService = serviceFactory.getPersonService();

        personService.getPersonById(-1, "EN");
    }

    @Test(expected = ServiceException.class)
    public void addPersonTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        PersonService personService = serviceFactory.getPersonService();

        personService.addPerson("", "", "", "");
    }

    @Test(expected = ServiceException.class)
    public void editPersonTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        PersonService personService = serviceFactory.getPersonService();

        personService.editPerson(-1, "", "", "", "", "EN");
    }

    @Test(expected = ServiceException.class)
    public void deletePersonTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        PersonService personService = serviceFactory.getPersonService();

        personService.deletePerson(-1);
    }
}
