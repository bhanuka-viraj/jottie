package lk.ijse.gdse71.finalproject.jotit.dao;

import lk.ijse.gdse71.finalproject.jotit.dao.custom.impl.*;

public class DaoFactory {
    private static DaoFactory daoFactory;
    public static DaoFactory getInstance(){
        return daoFactory == null ? daoFactory = new DaoFactory() : daoFactory;
    }

    public SuperDao getDao (DaoType type){
        switch (type){
            case JOT : return new JotDAOImpl();
            case TAG: return new TagDAOImpl();
            case MOOD: return new MoodDAOImpl();
            case TASK: return new TaskDAOImpl();
            case USER: return new UserDAOImpl();
            case QUERY: return new QueryDaoImpl();
            case CATEGORY: return new CategoryDAOImpl();
            case LOCATION:return new LocationDAOImpl();
            case SHAREDJOT: return new SharedJotDAOImpl();
            case RELATIONSHIP: return new RelationshipDAOImpl();

            default: return null;
        }
    }
}
