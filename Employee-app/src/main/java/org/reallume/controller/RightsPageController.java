package org.reallume.controller;

import lombok.Getter;
import lombok.Setter;
import org.reallume.domain.Action;
import org.reallume.domain.ActionOfRights;
import org.reallume.domain.Rights;
import org.reallume.repository.ActionOfRightsRepository;
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
import java.util.ArrayList;
import java.util.List;


@Controller
public class RightsPageController {

    @Autowired
    private RightsRepository rightsRepository;

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private ActionOfRightsRepository actionOfRightsRepository;

    @Setter @Getter
    private static class CurrentRights {
        private String name;

        private List<ActionOfRights> actionOfRights = new ArrayList<>();

        public CurrentRights(){}

        public CurrentRights(List<ActionOfRights> actionOfRights){
            this.actionOfRights.addAll(actionOfRights);
            this.name = actionOfRights.get(0).getRights().getName();
        }

    }

    @GetMapping(value = "/rights")
    public String rightsPage(Model model) {

        model.addAttribute("allRights", rightsRepository.findAll());
        model.addAttribute("commonActions", actionRepository.findAll());

        return "rights-page";
    }

    @PostMapping(value = "/rights/create")
    public String createRights(@RequestParam String rights_name) {

        Rights rights = new Rights(rights_name);
        rightsRepository.save(rights);

        for (Action action:actionRepository.findAll()) {
            ActionOfRights actionRightsLink = new ActionOfRights(action, rights);
            actionOfRightsRepository.save(actionRightsLink);
        }

        return "redirect:/rights";
    }

    @Transactional
    @PostMapping(value = "/rights/delete")
    public String deleteActionsStatus(@RequestParam Long selectedRightsIdToDelete) {

        actionOfRightsRepository.deleteByRights_Id(selectedRightsIdToDelete);
        rightsRepository.deleteById(selectedRightsIdToDelete);

        return "redirect:/rights";
    }

    @GetMapping(value = "/rights/actions/view")
    public String viewActions(@RequestParam Long selectedRightsId, Model model) {


        CurrentRights currentRights = new CurrentRights(new ArrayList<>(actionOfRightsRepository.findActionOfRightsByRights_Id(selectedRightsId)));

        model.addAttribute("commonActions", actionRepository.findAll());
        model.addAttribute("currentRights", currentRights);
        model.addAttribute("allRights", rightsRepository.findAll());

        return "rights-page";
    }

    @PostMapping(value = "/rights/actions/status/edit")
    public String editActionsStatus(@ModelAttribute("currentRights") CurrentRights currentRights) {

        for (ActionOfRights element:currentRights.getActionOfRights()) {
            ActionOfRights actionOfRights = actionOfRightsRepository.findById(element.getId()).get();
            actionOfRights.setStatus(element.getStatus());
            actionOfRightsRepository.save(actionOfRights);
        }

        return "redirect:/rights";
    }

}
