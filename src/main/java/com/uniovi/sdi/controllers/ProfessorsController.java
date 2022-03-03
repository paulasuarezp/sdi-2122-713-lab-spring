package com.uniovi.sdi.controllers;


import com.uniovi.sdi.entities.Professor;
import com.uniovi.sdi.services.ProfessorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfessorsController {

    @Autowired //Inyectar el servicio
    private ProfessorsService professorsService;

    @RequestMapping("/professor/list")
    public String getList(Model model) {
        model.addAttribute("professorsList", professorsService.getProfessors());
        return "/professor/list";
    }

    @RequestMapping(value="/professor/add")
    public String getProfessor(Model model){
        model.addAttribute("professor", new Professor());
        model.addAttribute("professorsList", professorsService.getProfessors());
        return "professor/add";
    }

    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setProfessor(@ModelAttribute Professor teacher) {
        professorsService.addProfessor(teacher);
        return "redirect:/user/list";
    }

    @RequestMapping("/professor/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("professor", professorsService.getProfessor(id));
        return "professor/details";
    }

    @RequestMapping(value = "/professor/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("professor", professorsService.getProfessor(id));
        model.addAttribute("professorsList", professorsService.getProfessors());
        return "professor/edit";
    }

    @RequestMapping(value = "/professor/edit/{id}", method = RequestMethod.POST)
    public String editProfessor(@ModelAttribute Professor professor, @PathVariable Long id) {
        Professor p = professorsService.getProfessor(id);
        p.setDni(professor.getDni());
        p.setNombre(professor.getNombre());
        p.setApellidos(professor.getApellidos());
        p.setCategoria(professor.getCategoria());
        professorsService.addProfessor(professor);
        return "redirect:/professor/details/"+id;
    }

    @RequestMapping("/professor/delete/{id}")
    public String deleteProfessor(@PathVariable Long id) {
        professorsService.deleteProfessor(id);
        return "redirect:/professor/list";
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

