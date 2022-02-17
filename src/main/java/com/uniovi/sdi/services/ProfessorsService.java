package com.uniovi.sdi.services;


import com.uniovi.sdi.entities.Professor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProfessorsService {

   private List<Professor> list = new LinkedList<>();

    @PostConstruct
    public void init() {
        list.add(new Professor(1L, "DNI-1", "Nombre1", "Apellido1", "SDI"));
        list.add(new Professor(2L, "DNI-2", "Nombre2", "Apellido2", "SDI"));
    }


    public List<Professor> getProfessors() {
       return list;
    }

    public Professor getProfessor(Long id) {
         return list.stream()
                .filter(teacher -> teacher.getId().equals(id)).findFirst().get();
    }

    public void addProfessor(Professor t) {
        // Si en Id es null le asignamos el ultimo + 1 de la lista
        if (t.getId() == null) {
            t.setId(list.get(list.size() - 1).getId() + 1);
        }
        list.add(t);
    }

    public void deleteProfessor(Long id) {
       list.removeIf(t -> t.getId().equals(id));
    }



}
