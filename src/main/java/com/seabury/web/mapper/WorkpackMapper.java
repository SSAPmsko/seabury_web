package com.seabury.web.mapper;

import com.seabury.web.entity.WorkpackEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
mybatis 최신 버전에서는 @Mapper 사용해야함
기존에는 @Repository
 */

@Mapper
public interface WorkpackMapper {
    List<WorkpackEntity> getWorkpackList(WorkpackEntity workpackEntity);

    int insertWorkpack(WorkpackEntity workpackEntity);
    int updateWorkpack(WorkpackEntity workpackEntity);
    int deleteWorkpack(WorkpackEntity workpackEntity);
}
