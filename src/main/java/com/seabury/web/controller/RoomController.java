package com.seabury.web.controller;

import com.seabury.web.entity.dose.PlantEntity;
import com.seabury.web.entity.dose.RoomEntity;
import com.seabury.web.entity.dose.StructureEntity;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.RoomService;
import com.seabury.web.service.StructureService;
import com.seabury.web.vo.dose.project.ReturnParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RoomController {
    @Autowired
    CommonService commonService;

    @Autowired
    RoomService roomService;

    @Autowired
    StructureService structureService;

    @RequestMapping(value = {"/getRoomDetailList"}, method = RequestMethod.POST)
    public @ResponseBody List<RoomEntity> getRoomList(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) Map<String, Object> message) {
            RoomEntity whereRoom = new RoomEntity();
        List<RoomEntity> qq = roomService.getRoomList(whereRoom);
        return qq;
    }

    @RequestMapping(value = {"/roomDetail"}, method = RequestMethod.GET)
    public ModelAndView roomDetail(ModelAndView mav, @RequestParam(value = "id", required = false) Integer id) {

        RoomEntity whereRoom = new RoomEntity();
        whereRoom.setID(id);
        List<RoomEntity> Platn1list = roomService.getRoomList(whereRoom);

        StructureEntity whereStructure = new StructureEntity();
        whereStructure.setObjectID(id);
        whereStructure.setType("Room");
        List<StructureEntity> Structurelist = structureService.getStructureList(whereStructure);

        /*if (id == null){
            mav.addObject("editMode", false);
            mav.addObject("constructionBegan", LocalDate.now());
            mav.addObject("commissionDate", LocalDate.now());
            mav.addObject("decommissionDate", LocalDate.now());
            mav.addObject("thermalCapacity", 0.0);


            mav.setViewName("view/sub/room/roomDetail");
        } else {*/
        mav.addObject("editMode", true);

        mav.addObject("Room1list", Platn1list);
        mav.setViewName("view/sub/room/roomDetail");
        /* }*/
        mav.addObject("Structurelist", Structurelist);
        return mav;
    }

    @RequestMapping(value = {"/roomDetailProperties"}, method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> roomDetailProperties(@RequestParam(value = "id", required = false) Integer id) {
        RoomEntity whereRoom = new RoomEntity();
        whereRoom.setID(id);
        List<RoomEntity> Room1list = roomService.getRoomList(whereRoom);


        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.put("result", Room1list.get(0));
        rp.setSuccess("");

        return rp;
    }

    @RequestMapping(value = {"/getRoomList"}, method = RequestMethod.GET)
    public @ResponseBody Integer getindexroomList(@RequestParam(value = "id", required = false) Integer id) {
        RoomEntity whereRoom = new RoomEntity();

        List<RoomEntity> qq = roomService.getRoomList(whereRoom);
        whereRoom = qq.get(qq.size() - 1);

        return whereRoom.getID();
    }


    @RequestMapping(value = {"/roomList"}, method = RequestMethod.GET)
    public ModelAndView roomList(ModelAndView mav) {

        mav.setViewName("view/sub/room/roomList");

        return mav;
    }

    @RequestMapping(value = {"/roomInsert"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> roomInsert(HttpServletRequest request, HttpServletResponse response, @RequestBody RoomEntity message) {
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", roomService.insertRoom(message));

        return rp;
    }

    @RequestMapping(value = {"/roomUpdate"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> roomUpdate(HttpServletRequest request, HttpServletResponse response, @RequestBody RoomEntity message) {
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", roomService.updateRoom(message));

        return rp;
    }

    @RequestMapping(value = {"/roomDelete"}, method = RequestMethod.DELETE)
    public @ResponseBody Map<String, Object> roomDelete(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) RoomEntity message) {
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", roomService.deleteRoom(message));


        return rp;
    }
}
