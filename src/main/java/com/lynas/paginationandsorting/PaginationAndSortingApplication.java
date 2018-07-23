package com.lynas.paginationandsorting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Optional;

interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select s from Student s")
    Page<Student> findPageable(Pageable pageable);

    @Query("select s from Student s where name like %?1%")
    Page<Student> findByProperty(@Param("name") String name, Pageable pageable);
}

@SpringBootApplication
public class PaginationAndSortingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaginationAndSortingApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(StudentRepository repository) {
        return args -> {
            repository.save(new Student(null, "sazzad", "class1", 1995));
            repository.save(new Student(null, "Rony", "class2", 1993));
            repository.save(new Student(null, "Naim", "class4", 1996));
            repository.save(new Student(null, "Dania", "class5", 1995));
            repository.save(new Student(null, "Mamun", "class6", 1993));
            repository.save(new Student(null, "Rimi", "class3", 1995));
            repository.save(new Student(null, "Habib", "class9", 1991));
            repository.save(new Student(null, "Shail", "class6", 1999));
            repository.save(new Student(null, "Pranjol", "class3", 1998));
            repository.save(new Student(null, "Shohag", "class9", 1992));
            repository.save(new Student(null, "Ramjan", "class7", 1993));
            repository.save(new Student(null, "Ashik", "class4", 1996));
            repository.save(new Student(null, "Kibria", "class6", 1995));
            repository.save(new Student(null, "Aurik", "class3", 1997));
            repository.save(new Student(null, "Tanvir", "class2", 1998));
            repository.save(new Student(null, "Nazmul", "class6", 1995));
            repository.save(new Student(null, "Mizan", "class7", 1996));
            repository.save(new Student(null, "Anik", "class3", 1998));
            repository.save(new Student(null, "Mehedi", "class9", 1997));
            repository.save(new Student(null, "Shahadat", "class8", 1999));
            repository.save(new Student(null, "Dipak", "class9", 1995));
            repository.save(new Student(null, "Mukta", "class4", 1997));
            repository.save(new Student(null, "Rabaet", "class2", 1997));
            repository.save(new Student(null, "Lopa", "class5", 1995));
            repository.save(new Student(null, "Markes", "class2", 1997));
            repository.save(new Student(null, "Valentino", "class1", 1996));
            repository.save(new Student(null, "Fotik", "class2", 1991));
            repository.save(new Student(null, "Lubna", "class4", 1992));
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

@RestController
@RequiredArgsConstructor
class StudentController {
    private final StudentRepository repository;

    @GetMapping("/students")
    public Page<Student> findAll(@RequestParam(value = "name") Optional<String> name,
                                 @RequestParam("page") Optional<Integer> page) {
        System.out.println(name);
        return repository.findByProperty(name.orElse("_"), new PageRequest(page.orElse(0), 5));
    }
}