package com.uniovi.sdi.services;


import com.uniovi.sdi.entities.Professor;
import com.uniovi.sdi.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProfessorsService {

    //Ejercicio complementario 2
    @Autowired
    private ProfessorRepository professorRepository;

    /* Ejercicio complementario 1 --> para el 2 no hace falta
    private List<Professor> list = new LinkedList<>();
    @PostConstruct
    public void init() {
        list.add(new Professor(1L, "DNI-1", "Nombre1", "Apellido1", "SDI"));
        list.add(new Professor(2L, "DNI-2", "Nombre2", "Apellido2", "SDI"));
    }
    */

    public List<Professor> getProfessors() {
        List<Professor> list = new ArrayList<Professor>();
        professorRepository.findAll().forEach(list::add);
        return list;
    }

    public Professor getProfessor(Long id) {
       /* ej 1 -->  return list.stream()
                .filter(teacher -> teacher.getId().equals(id)).findFirst().get();*/
        return professorRepository.findById(id).get();
    }
    public void addProfessor(Professor t) {
        // Si en Id es null le asignamos el ultimo + 1 de la lista
        /* Ej 11 -> if (t.getId() == null) {
            t.setId(list.get(list.size() - 1).getId() + 1);
        }
        list.add(t); */
        professorRepository.save(t);
    }

    public void deleteProfessor(Long id) {
        // Ej 1 -->  list.removeIf(t -> t.getId().equals(id));
        professorRepository.deleteById(id);
    }


}
