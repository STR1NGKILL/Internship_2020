package org.reallume.controller;

import lombok.Getter;
import lombok.Setter;
import org.reallume.domain.Action;
import org.reallume.domain.ActionOfRights;
import org.reallume.repository.ActionOfRightsRepository;
import org.reallume.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ActionController {

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    ActionOfRightsRepository actionOfRightsRepository;

    @Setter
    @Getter
    private static class LitAction {
        private Long id;

        private String name;

        private String description;

        public LitAction(){}

        public LitAction(Long id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }
    }

    @GetMapping(value = "/actions")
    public String actionPage(Model model) {

        model.addAttribute("actions", actionRepository.findAll());

        return "action-page";
    }

    @Transactional
    @GetMapping(value = "/actions/{action_id}/delete")
    public String deleteAction(@PathVariable Long action_id){

        actionOfRightsRepository.deleteByAction_Id(action_id);
        actionRepository.deleteById(action_id);

        return "redirect:/actions";
    }

    @PostMapping(value = "/actions/edit")
    public String editAction(@RequestParam Long action_id,
                               @RequestParam String name,
                               @RequestParam String description){

        Action actionToEdit = actionRepository.findById(action_id).get();
        actionToEdit.setName(name);
        actionToEdit.setDescription(description);

        actionRepository.save(actionToEdit);

        return "redirect:/actions";
    }

    //for modal popup to edit an action - action wrapper
    @GetMapping(value = "/actions/{action_id}/find-action")
    @ResponseBody
    public LitAction findActionToEdit(@PathVariable Long action_id)
    {
        Action action = actionRepository.findById(action_id).get();

        return new LitAction(action.getId(), action.getName(), action.getDescription());
    }

}
