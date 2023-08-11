package com.seabury.web.service;

import com.seabury.web.entity.integrated.WorkFlowEntity;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class IntegratedDoseServiceImpl implements IntegratedDoseService{

    @Autowired
    @Qualifier(value = "integratedSqlSession")
    private SqlSession integratedSqlSession;

    final String INTEGRATED_RADIATIONSOURCEMAPPER_PATH = "com.seabury.web.mapper.integrated.WorkFlowMapper";

    @Override
    public List<WorkFlowEntity> getWorkFlowList(WorkFlowEntity workFlowEntity){
        return integratedSqlSession.selectList(INTEGRATED_RADIATIONSOURCEMAPPER_PATH + ".getWorkFlowList", workFlowEntity);
    }
}
