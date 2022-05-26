package com.example.batch_20220525;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(Person item) throws Exception {
        return null;
    }


    //private final Person person;


//    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);
//
//    public PersonItemProcessor() {};
//    public PersonItemProcessor(Person person) {
//        this.person = person;
//    }
//
//    @Override
//    public Person process(final Person person) throws Exception {
//        final String firstName = person.getFirstName().toUpperCase();
//        final String lastName = person.getLastName().toUpperCase();
//
//        //final Person transformedPerson = new Person(firstName, lastName);
//
//        //log.info("Converting (" + person + ") into (" + transformedPerson + ")");
//
//        return person;
//    }


}
