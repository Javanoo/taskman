package org.craft.Service;

import java.util.logging.Handler;
import java.util.logging.Logger;

public class TaskManLog {
    Logger log = Logger.getAnonymousLogger();

    public TaskManLog(){}

    public void inform(String message){
        log.info(message);
    }

    public void warn(String warningMessage){
        log.warning(warningMessage);
    }

    public void error(String errorMessage){
        log.severe(errorMessage);
    }

    private void changeHandler(Handler handler){
        try{
            log.addHandler(handler);
        }catch (Exception exception){
            error("Failed to change handler : " + exception.getMessage());
        }
    }
}
