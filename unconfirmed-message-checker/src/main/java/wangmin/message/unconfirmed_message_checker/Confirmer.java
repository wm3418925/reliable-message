package wangmin.message.unconfirmed_message_checker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wangmin.demo_initiative_business.core.Constants;
import wangmin.demo_initiative_business.core.remote.DemoInitiativeBusinessServiceInterface;
import wangmin.message.core.entity.Message;

/**
 * Created by wm on 2016/12/21.
 */
@Service
public class Confirmer {
    @Autowired
    private DemoInitiativeBusinessServiceInterface demoInitiativeBusinessService;

    public Boolean confirm(Message msg) {
        switch (msg.source) {
            case Constants.messageSource:
                return demoInitiativeBusinessService.isMessageBusinessExist(msg.id);

            default:
        }

        return null;
    }
}
