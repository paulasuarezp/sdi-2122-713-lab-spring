package com.uniovi.sdi.controllers;


import com.uniovi.sdi.entities.Professor;
import com.uniovi.sdi.services.ProfessorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfessorsController {

    @Autowired //Inyectar el servicio
    private ProfessorsService professorsService;

    @RequestMapping("/professor/list")
    public String getList() {
        return professorsService.getProfessors().toString();
    }
    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String addProfessor(@ModelAttribute Professor teacher) {
        professorsService.addProfessor(teacher);
        return "Adding Teacher";
    }
    @RequestMapping("/professor/details/{id}")
    public String getDetail(@PathVariable Long id) {
        return "Getting Details => " + professorsService.getProfessor(id).toString();
    }

    @RequestMapping(value = "/professor/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("professor", professorsService.getProfessor(id));
        return "En el futuro redirige a --> professor/edit";
    }

    @RequestMapping(value = "/professor/edit/{id}", method = RequestMethod.POST)
    public String editProfessor(@ModelAttribute Professor professor, @PathVariable Long id) {
        professor.setId(id);
        professorsService.addProfessor(professor);
        return "redirect:/professor/details/"+id;
    }

    @RequestMapping("/professor/delete/{id}")
    public String deleteProfessor(@PathVariable Long id) {
        professorsService.deleteProfessor(id);
        return "Deleting Teacher ID: " + id;
    }

    /**
     * Método para comprobar que se edita correctamente un profesor
     * @param professor
     * @return
     */
    @RequestMapping(value = "/professor/testEdit", method = RequestMethod.POST)
    public String testEdit(@ModelAttribute Professor professor) {
        Professor p = professorsService.getProfessor(professor.getId());
        p.setCategoria(professor.getCategoria());
        editProfessor(p, p.getId());
        return "Testing edit professor --> comprobar en el listado que se ha modificado";
    }

}

