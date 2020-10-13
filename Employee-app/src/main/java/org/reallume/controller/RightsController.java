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
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Controller
public class RightsController {

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

    //rights main page
    @GetMapping(value = "/rights")
    public String rightsPage(Model model) {

        model.addAttribute("allRights", rightsRepository.findAll());
        model.addAttribute("commonActions", actionRepository.findAll());

        return "rights/rights-page";
    }

    //rights editing page
    @GetMapping(value = "/rights/{rights_id}/edit")
    public String editRightsPage(@PathVariable Long rights_id, Model model) {

        Rights currentRights = rightsRepository.findById(rights_id).get();

        model.addAttribute("currentRights", currentRights);
        model.addAttribute("commonActions", actionRepository.findAll());

        return "rights/edit-page";
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
    @GetMapping(value = "/rights/{rights_id}/delete")
    public String deleteRights(@PathVariable Long rights_id) {

        actionOfRightsRepository.deleteByRights_Id(rights_id);
        rightsRepository.deleteById(rights_id);

        return "redirect:/rights";
    }

    @PostMapping(value = "/rights/{rights_id}/edit")
    public String editRights(@PathVariable Long rights_id,
                                    @ModelAttribute("currentRights") CurrentRights currentRights) {

        for (ActionOfRights element:currentRights.getActionOfRights()) {
            ActionOfRights actionOfRights = actionOfRightsRepository.findById(element.getId()).get();
            actionOfRights.setStatus(element.getStatus());
            actionOfRightsRepository.save(actionOfRights);
        }
        Rights rightsWithNewName = rightsRepository.findById(rights_id).get();
        rightsWithNewName.setName(currentRights.name);

        rightsRepository.save(rightsWithNewName);

        return "redirect:/rights/" + rights_id.toString() + "/edit";
    }

}
