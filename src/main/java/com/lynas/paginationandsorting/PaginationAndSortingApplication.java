package com.lynas.paginationandsorting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;


@SpringBootApplication
public class PaginationAndSortingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaginationAndSortingApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(StudentRepository repository) {
        return args -> {
            repository.save(new Student(null, "sazzad", "one", 1995));
            repository.save(new Student(null, "Rony", "one", 1993));
            repository.save(new Student(null, "Naim", "one", 1996));
            repository.save(new Student(null, "Dania", "one", 1995));
            repository.save(new Student(null, "Mamun", "one", 1993));
            repository.save(new Student(null, "Rimi", "one", 1995));
            repository.save(new Student(null, "Habib", "two", 1991));
            repository.save(new Student(null, "Shail", "two", 1999));
            repository.save(new Student(null, "Pranjol", "two", 1998));
            repository.save(new Student(null, "Shohag", "two", 1992));
            repository.save(new Student(null, "Ramjan", "two", 1993));
            repository.save(new Student(null, "Ashik", "two", 1996));
            repository.save(new Student(null, "Kibria", "two", 1995));
            repository.save(new Student(null, "Aurik", "three", 1997));
            repository.save(new Student(null, "Tanvir", "three", 1998));
            repository.save(new Student(null, "Nazmul", "three", 1995));
            repository.save(new Student(null, "Mizan", "three", 1996));
            repository.save(new Student(null, "Anik", "three", 1998));
            repository.save(new Student(null, "Mehedi", "three", 1997));
            repository.save(new Student(null, "Shahadat", "four", 1999));
            repository.save(new Student(null, "Dipak", "four", 1995));
            repository.save(new Student(null, "Mukta", "four", 1997));
            repository.save(new Student(null, "Rabaet", "four", 1997));
            repository.save(new Student(null, "Lopa", "four", 1995));
            repository.save(new Student(null, "Markes", "five", 1997));
            repository.save(new Student(null, "Valentino", "five", 1996));
            repository.save(new Student(null, "Fotik", "five", 1991));
            repository.save(new Student(null, "Lubna", "five", 1992));
        };
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
class Student {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String course;
    private int admissionYear;
}

interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select s from Student s where name like %?1%")
    List<Student> findByName(String name);
}

@RestController
@RequiredArgsConstructor
class StudentController {
    private final StudentRepository repository;

    @GetMapping("/students")
    public List<Student> findAll(@RequestParam String name) {
        // name mandatory
        // requires full name
        return repository.findByName(name);
    }
}