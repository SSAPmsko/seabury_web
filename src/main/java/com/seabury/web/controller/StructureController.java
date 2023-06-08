package com.seabury.web.controller;

import com.seabury.web.entity.StructureEntity;
import com.seabury.web.service.StructureService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StructureController {

    @Autowired
    StructureService StructureService;
    @RequestMapping(value={"/Structure"}, method = RequestMethod.GET)
    public ModelAndView example(ModelAndView mav){

        // Sample Database CRUD
        StructureEntity sample = new StructureEntity();
        sample.setName("Sample");
        if (StructureService.insertStructure(sample) == 1) {
            sample.setDescription("Modify");
            StructureService.updateStructure(sample);

            var qq = StructureService.getStructureList(null);

            StructureService.deleteStructure(sample);
        }

        mav.setViewName("sub/Structure");
        return mav;
    }
}
