package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.subject.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;

/**
 * @auther fangchen.chai ON 2017/10/26
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class SubjectFrontController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/subject/detail/{id}")
    public ResultData getSubjectDetail(@PathVariable("id") Long id) {

        return subjectService.getSubjectDetail(id);
    }

}
