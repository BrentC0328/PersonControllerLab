package zipcode.rocks.PersonControllerLab;


import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {

  @Autowired
    private PersonRepository personRepository;

    @PostMapping("/people")
    public ResponseEntity<Person> createPerson(@RequestBody Person p){
        Person created = personRepository.save(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id){
        Optional<Person> maybePerson = personRepository.findById(id);

        if (maybePerson.isPresent()){
            Person foundPerson = maybePerson.get();
            return ResponseEntity.status(HttpStatus.OK).body(foundPerson);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @GetMapping("/people")
    public ResponseEntity<List<Person>> getPersonList(){

        Iterable<Person> personList = personRepository.findAll();
        List<Person> personList1 = new ArrayList<>((Collection) personList);

        return ResponseEntity.status(HttpStatus.OK).body(personList1);
    }

    @PutMapping("/people/{id}")
    public ResponseEntity<Person> updatePerson(@RequestBody Person p, @PathVariable Long id){
        Optional<Person> personToFind = personRepository.findById(id);


        if(personToFind.isPresent()){
            Person updateThisOne = personToFind.get();
            updateThisOne.setFirstName(p.getFirstName());
            updateThisOne.setLastName(p.getLastName());
            personRepository.save(updateThisOne);
            return ResponseEntity.status(HttpStatus.OK).body(updateThisOne);

        } else {
            return createPerson(p);
        }
    }

    @DeleteMapping("/people/{id}")
    ResponseEntity<Person> deletePerson(Long id){
        Optional<Person> findPerson = personRepository.findById(id);

        if (findPerson.isPresent()){
            Person deletedPerson = findPerson.get();
            personRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(deletedPerson);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
