package lk.ijse.gdse71.finalproject.jotit.service;

import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.*;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

    public static ServiceFactory getInstance(){
        return serviceFactory != null ? serviceFactory : new ServiceFactory();
    }

    public SuperService getService(ServiceType type){
        switch (type){
            case RELATIONSHIP : return new RelationshipServiceImpl();
            case USER : return new UserServiceImpl();
            case JOT : return new JotServiceImpl();
            case TAG : return new TagServiceImpl();
            case MOOD : return new MoodServiceImpl();
            case LOCATION : return new LocationServiceImpl();
            case CATEGORY : return new CategoryServiceImpl();
            case SHAREDJOT : return new SharedJotServiceImpl();
            case TASK : return new TaskServiceImpl();

            default:return null;
        }
    }
}
