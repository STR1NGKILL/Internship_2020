package org.reallume.service;

import org.reallume.domain.employee.Action;
import org.reallume.domain.employee.ActionOfRights;
import org.reallume.domain.employee.Rights;
import org.reallume.repository.employee.ActionOfRightsRepository;
import org.reallume.repository.employee.ActionRepository;
import org.reallume.repository.employee.RightsRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ActionOfRightsService implements InitializingBean {

    @Autowired
    private ActionOfRightsRepository actionOfRightsRepository;

    @Autowired
    private RightsRepository rightsRepository;

    @Autowired
    private ActionRepository actionRepository;

    private Logger log = Logger.getLogger(ActionOfRightsService.class.getName());

    @Override
    public void afterPropertiesSet(){
        ActionOfRightsUpdater();
        log.info("Action of rights updater completed");
    }

    private void ActionOfRightsUpdater(){

        if(!rightsRepository.findAll().isEmpty() && !actionRepository.findAll().isEmpty())
            for (Rights rights: rightsRepository.findAll()) {

                for (Action action: actionRepository.findAll()) {
                    if(actionOfRightsRepository.findByRights_IdAndAction_Id(rights.getId(), action.getId()).isEmpty()) {
                        ActionOfRights actionOfRights = new ActionOfRights(action, rights, true);
                        actionOfRightsRepository.save(actionOfRights);
                    }
                }
            }
    }
}
