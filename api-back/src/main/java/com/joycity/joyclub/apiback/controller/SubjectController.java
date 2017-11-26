package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.subject.mapper.SubjectTypeMapper;
import com.joycity.joyclub.subject.modal.generated.SubjectType;
import com.joycity.joyclub.subject.modal.generated.SubjectWithBLOBs;
import com.joycity.joyclub.subject.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

/**
 * @auther fangchen.chai ON 2017/10/25
 */
@RestController
@RequestMapping(URL_API_BACK)
public class SubjectController extends BaseUserSessionController {

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private SubjectTypeMapper subjectTypeMapper;

    @PostMapping("/subject")
    public ResultData createSubject(@RequestBody SubjectWithBLOBs subject, HttpSession session) {
//        checkPlatformOrProjectOrStoreUser(session);
        return subjectService.createSubject(subject);
    }

    @PostMapping("/subject/{id}")
    public ResultData updateSubjectDetail(@PathVariable Long id,@RequestBody SubjectWithBLOBs subject) {
        subject.setId(id);
        return subjectService.updateSubject(subject);
    }

    @GetMapping("/subjects")
    public ResultData getSubjects(@RequestParam(required = false)Long projectId, PageUtil pageUtil, HttpSession session) {
//        checkPlatformOrProjectOrStoreUser(session);
        return subjectService.getSubjects(projectId,pageUtil);
    }

    @GetMapping("/subject/{id}")
    public ResultData getSubjectDetail(@PathVariable Long id) {

        return subjectService.getSubjectDetail(id);
    }


    @GetMapping("/subject/type")
    public ResultData getSubjectTypes(@RequestParam(required = false) Long projectId) {
        return subjectService.getSubjectTypes(projectId);
    }

    @PostMapping("/subject/type")
    public ResultData createSubjectType(@RequestBody SubjectType subjectType){
        subjectTypeMapper.insertSelective(subjectType);
        return new ResultData(new CreateResult(subjectType.getId()));
    }

    @PostMapping("/subject/type/{id}")
    public ResultData updateSubjectType(@PathVariable Long id ,@RequestBody SubjectType subjectType) {
        subjectType.setId(id);
        int num = subjectTypeMapper.updateByPrimaryKeySelective(subjectType);
        return new ResultData(new UpdateResult(num));
    }
}
