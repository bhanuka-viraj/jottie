package lk.ijse.gdse71.finalproject.jotit.service.custom.impl;

import lk.ijse.gdse71.finalproject.jotit.dao.custom.JotDAO;
import lk.ijse.gdse71.finalproject.jotit.dao.custom.QueryDAO;
import lk.ijse.gdse71.finalproject.jotit.dao.custom.impl.JotDAOImpl;
import lk.ijse.gdse71.finalproject.jotit.dao.custom.impl.QueryDaoImpl;
import lk.ijse.gdse71.finalproject.jotit.dto.*;
import lk.ijse.gdse71.finalproject.jotit.entity.*;
import lk.ijse.gdse71.finalproject.jotit.service.custom.CategoryService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.JotService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.LocationService;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JotServiceImpl implements JotService {
    private final CategoryService categoryService = new CategoryServiceImpl();
    private final LocationService locationService = new LocationServiceImpl();
    private final JotDAO jotDAO = new JotDAOImpl();
    private final QueryDAO queryDAO = new QueryDaoImpl();

    @Override
    public boolean saveJot(JotDto jotDto) throws Exception {
        try {
            JotDto existingJot = getJot(jotDto.getId());
            if (existingJot != null) {
                return updateJot(jotDto);
            }

            CrudUtil.beginTransaction();
            boolean isJotSaved = jotDAO.saveJot(
                    new Jot(
                            jotDto.getId(),
                            jotDto.getUserId(),
                            jotDto.getTitle(),
                            jotDto.getPath(),
                            jotDto.getCategory().getId(),
                            jotDto.getLocation().getId(),
                            jotDto.getMoods().stream().map(mood -> new Mood(mood.getId(), mood.getDescription())).collect(Collectors.toList()),
                            jotDto.getTags().stream().map(tag -> new Tag(tag.getId(), tag.getName(), tag.getCreatedBy())).collect(Collectors.toList()),
                            jotDto.getCreatedAt(),
                            jotDto.getUpdatedAt()
                            )
            );

            if (isJotSaved) {
                boolean isJotTagSaved = false;
                List<TagDto> tags = jotDto.getTags();
                for (TagDto tag : tags) {
                    isJotTagSaved = queryDAO.saveJotTag(jotDto.getId(), tag.getId());
                }

                if (isJotTagSaved) {
                    boolean isMoodSaved = false;
                    List<MoodDto> moods = jotDto.getMoods();
                    System.out.println(moods);
                    for (MoodDto mood : moods) {
                        isMoodSaved = queryDAO.saveJotMood(jotDto.getId(), mood.getId());
                    }

                    if (isMoodSaved) {
                        CrudUtil.commitTransaction();
                    }
                } else {
                    CrudUtil.rollbackTransaction();
                    return false;
                }

            } else {
                CrudUtil.rollbackTransaction();
                return false;
            }
            return true;


        } catch (SQLException e) {
            CrudUtil.rollbackTransaction();
            e.printStackTrace();
            throw e;
        } finally {
            CrudUtil.closeConnection();
        }
    }

    @Override
    public boolean updateJot(JotDto jotDto) throws Exception {
        try {

            CrudUtil.beginTransaction();
            boolean isJotUpdated = jotDAO.updateJot(mapJotDtoToJot(jotDto));

            if (isJotUpdated) {
                boolean isJotTagDeleted = queryDAO.deleteJotTag(jotDto.getId());
                boolean isJotMoodDeleted = queryDAO.deleteJotMood(jotDto.getId());

                if (isJotTagDeleted && isJotMoodDeleted) {
                    boolean isJotTagSaved = false;
                    List<TagDto> tags = jotDto.getTags();
                    for (TagDto tag : tags) {
                        isJotTagSaved = queryDAO.saveJotTag(jotDto.getId(), tag.getId());

                    }

                    if (isJotTagSaved) {
                        boolean isMoodSaved = false;
                        List<MoodDto> moods = jotDto.getMoods();
                        for (MoodDto mood : moods) {
                            isMoodSaved = queryDAO.saveJotMood(jotDto.getId(), mood.getId());
                        }

                        if (isMoodSaved) {
                            CrudUtil.commitTransaction();
                            return true;
                        }
                    }
                }
            }

            CrudUtil.rollbackTransaction();
            return false;
        } catch (Exception e) {
            CrudUtil.rollbackTransaction();
            throw e;
        } finally {
            CrudUtil.closeConnection();
        }
    }



    @Override
    public JotDto getJot(String id) throws Exception {
        Jot jot = jotDAO.getJot(id);

        if (jot!=null) {
            CategoryDto categoryDto = categoryService.getCategory(jot.getCategoryId(), jot.getUserId());
            LocationDto locationDto = locationService.getLocation(jot.getLocationId(), jot.getUserId());
            JotDto jotDto = new JotDto();
            jotDto.setId(jot.getId());
            jotDto.setUserId(jot.getUserId());
            jotDto.setTitle(jot.getTitle());
            jotDto.setPath(jot.getPath());
            jotDto.setLocation(locationDto);
            jotDto.setCategory(categoryDto);

            jotDto.setCreatedAt(jot.getCreatedAt());
            jotDto.setUpdatedAt(jot.getUpdatedAt());

            jotDto.setTags(queryDAO.getTagsByJotId(id, jotDto.getUserId()));

            jotDto.setMoods(queryDAO.getMoodsByJotId(id));

            return jotDto;
        }
        return null;
    }

    @Override
    public List<JotDto> getAllJot(String userId) throws Exception {
        return processJotDtos(jotDAO.getAllJot(userId),userId);
    }

    @Override
    public boolean deleteJot(JotDto jotDto) throws Exception {
        return jotDAO.deleteJot(mapJotDtoToJot(jotDto));
    }

    @Override
    public List<JotDto> findJots(String title, String userID) throws Exception {
        return jotDAO.findJots(title,userID).stream().map(jot -> {
            try {
                return mapJotToJotDto(jot);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public int getJotCountByCategory(String categoryId, String userID) throws Exception {
        return jotDAO.getJotCountByCategory(categoryId,userID);
    }

    @Override
    public List<JotDto> processJotDtos(List<Jot> jots, String userId) throws Exception {
        List<JotDto> processedJotDtos = new ArrayList<>();

        for (Jot jot : jots) {
            CategoryDto category = categoryService.getCategory(jot.getCategoryId(),userId);
            LocationDto location = locationService.getLocation(jot.getLocationId(),userId);
            JotDto processedJotDto = new JotDto();

            processedJotDto.setId(jot.getId());
            processedJotDto.setUserId(jot.getUserId());
            processedJotDto.setTitle(jot.getTitle());
            processedJotDto.setPath(jot.getPath());
            processedJotDto.setCategory(category);
            processedJotDto.setLocation(location);
            processedJotDto.setCreatedAt(jot.getCreatedAt());
            processedJotDto.setUpdatedAt(jot.getUpdatedAt());

            processedJotDto.setTags(queryDAO.getTagsByJotId(jot.getId(), userId));

            processedJotDto.setMoods(queryDAO.getMoodsByJotId(jot.getId()));
            processedJotDtos.add(processedJotDto);
        }
        return processedJotDtos;
    }

    private JotDto mapJotToJotDto(Jot jot) throws Exception {
        CategoryDto categoryDto = categoryService.getCategory(jot.getCategoryId(), jot.getUserId());
        LocationDto locationDto = locationService.getLocation(jot.getLocationId(), jot.getUserId());

        JotDto jotDto = new JotDto();
        jotDto.setId(jot.getId());
        jotDto.setUserId(jot.getUserId());
        jotDto.setTitle(jot.getTitle());
        jotDto.setPath(jot.getPath());
        jotDto.setCategory(categoryDto);
        jotDto.setLocation(locationDto);
        jotDto.setCreatedAt(jot.getCreatedAt());
        jotDto.setUpdatedAt(jot.getUpdatedAt());
        jotDto.setTags(queryDAO.getTagsByJotId(jot.getId(), jot.getUserId()));
        jotDto.setMoods(queryDAO.getMoodsByJotId(jot.getId()));
        return jotDto;
    }

    private Jot mapJotDtoToJot(JotDto jotDto) {
        Jot jot = new Jot();
        jot.setId(jotDto.getId());
        jot.setUserId(jotDto.getUserId());
        jot.setTitle(jotDto.getTitle());
        jot.setPath(jotDto.getPath());
        jot.setCategoryId(jotDto.getCategory().getId());
        jot.setLocationId(jotDto.getLocation().getId());
        jot.setCreatedAt(jotDto.getCreatedAt());
        jot.setUpdatedAt(jotDto.getUpdatedAt());
        jot.setMoods(jotDto.getMoods().stream().map(mood -> new Mood(mood.getId(),mood.getDescription())).collect(Collectors.toList()));
        jot.setTags(jotDto.getTags().stream().map(tag -> new Tag(tag.getId(), tag.getName(), tag.getCreatedBy())).collect(Collectors.toList()));


        //TODO: create factories for daos and services, the relationships are not working
        return jot;
    }
}
