package maktab.team.library.service;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import maktab.team.library.model.Person;
import maktab.team.library.repository.PersonRepository;
import maktab.team.library.utils.exceptions.InputMismatchExecption;
import maktab.team.library.utils.exceptions.InvalidElement;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@UtilityClass
public class PersonService {

    @Getter
    private final ArrayList<String> validationOptions = new ArrayList<>(
            Arrays.asList("username", "password"));

    @SneakyThrows
    public boolean addUser(Person person) {
        if (!validatePerson(person)) return false;

        PersonRepository.addPerson(person);
        return true;
    }

    @SneakyThrows
    public <T> boolean validatePersonData(String option, T dataType) {
        if (!getValidationOptions().contains(option))
            throw new InputMismatchExecption("In Option Dar Database Vojod Nadarad!");

        return PersonRepository.validatePersonData(option, dataType);
    }

    @SneakyThrows
    public boolean validatePerson(Person person) {
        if (person == null)
            throw new InvalidElement("In User Article Nist!");

        return !PersonService.optionalUser(person.getUsername(), "username").isPresent();
    }

    @SneakyThrows
    public boolean checkCredentials(Person person) {
        if (validatePerson(person)) return false;

        return PersonRepository.validatePerson(person);
    }

    @SneakyThrows
    public <T> Person getUser(T datatype, String option) {
        if (!getValidationOptions().contains(option))
            throw new InputMismatchExecption("In Option Dar Database Vojod Nadarad!");

        final Optional<Person> optionalWriter = PersonService.optionalUser(datatype, option);
        if (!optionalWriter.isPresent())
            throw new InvalidElement("In User Mojod Nist!");

        return optionalWriter.get();
    }

    @SneakyThrows
    public Person getUser(Person person) {
        return getUser(person.getUsername(), "username");
    }

    @SneakyThrows
    public <T> Optional<Person> optionalUser(T datatype, String option) {
        if (!getValidationOptions().contains(option))
            throw new InputMismatchExecption("In Option Dar Database Vojod Nadarad!");

        return PersonService.handleResult(PersonRepository.getPerson(datatype, option));
    }

    @SneakyThrows
    private Optional<Person> handleResult(ResultSet resultSet) {
        return resultSet.next() ?
                Optional.of(new Person(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getBoolean("admin"),
                        resultSet.getBoolean("verify"),
                        resultSet.getInt("fine"))) :
                Optional.empty();
    }
}
