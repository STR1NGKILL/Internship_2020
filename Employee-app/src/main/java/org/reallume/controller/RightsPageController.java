package org.reallume.controller;

import org.reallume.domain.Action;
import org.reallume.domain.Rights;
import org.reallume.repository.ActionRepository;
import org.reallume.repository.RightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;


@Controller
public class RightsPageController {

    @Autowired
    private RightsRepository rightsRepository;

    @Autowired
    private ActionRepository actionRepository;

    @GetMapping(value = "/rights")
    public String rightsPage(Model model) {

        model.addAttribute("allRights", rightsRepository.findAll());
        model.addAttribute("actions", actionRepository.findAll());

        return "rights-page";
    }

    @PostMapping(value = "/rights/create")
    public String createRights(@RequestParam String rights_name) {

        Rights rights = new Rights(rights_name, actionRepository.findAll());

        rightsRepository.save(rights);

        return "redirect:/rights";
    }

    @PostMapping(value = "/rights/delete")
    public String editActionsStatus(@RequestParam Long selectedRightsIdToDelete) {

        rightsRepository.deleteById(selectedRightsIdToDelete);

        return "redirect:/rights";
    }

    @GetMapping(value = "/rights/actions/view")
    public String viewActions(@RequestParam Long selectedRightsId, Model model) {

        model.addAttribute("currentRights", rightsRepository.findById(selectedRightsId).get());
        model.addAttribute("allRights", rightsRepository.findAll());

        return "rights-page";
    }

    @PostMapping(value = "/rights/actions/status/edit")
    public String editActionsStatus(@ModelAttribute("currentRights") Rights currentRights) {

        rightsRepository.save(currentRights);

        return "redirect:/rights";
    }




}
