package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParameter;
import com.zerobase.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminCourseController extends BaseController {
    private final CourseService courseService;
    private final CategoryService categoryService;

    @GetMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
    public String add(Model model, HttpServletRequest request
            , CourseInput parameter) {

        model.addAttribute("category", categoryService.list());

        boolean isEditMode = request.getRequestURI().contains("/edit.do");
        CourseDto courseDto = new CourseDto();

        if (isEditMode) {
            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);

            if (existCourse == null) {
                model.addAttribute("message", "강좌 정보가 존재 하지 않습니다.");
                return "common/error";
            }
            model.addAttribute("isEditMode", isEditMode);
            model.addAttribute("courseDto", courseDto);

            return "admin/course/add";
        }

        return "admin/course/add";
    }

    @PostMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
    public String add(Model model,
                      CourseInput parameter,
                      HttpServletRequest request,
                      MultipartFile file
    ) {
        String saveFileName = "";
        String urlFileName = "";

        if (file != null) {
            String originalFileName = file.getOriginalFilename();

            String baseLocalPath = "/Users/yejinkim/Desktop/heyazoo1007/fastlms/files/";
            String baseUrlPath = "/files";

            String[] arrFilename = getNewFileName(baseLocalPath, baseUrlPath, originalFileName);

            saveFileName = arrFilename[0];
            urlFileName = arrFilename[1];;

            try {
                File newFile = new File(baseLocalPath);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (IOException e) {
                log.info("#######################");
                log.info(e.getMessage());
            }
        }

        parameter.setSaveFileName(saveFileName);

        boolean isEditMode = request.getRequestURI().contains("/edit.do");

        if (isEditMode) {
            long id = parameter.getId();
            CourseDto courseDto = courseService.getById(id);
            if (courseDto == null) {
                model.addAttribute("message", "강좌정보가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = courseService.modify(parameter);
        } else {
            courseService.add(parameter);
        }

        return "redirect:/admin/course/list.do";
    }

    @PostMapping("/admin/course/delete.do")
    public String delete(Model model, HttpServletRequest request,
                         CourseInput parameter) {
        courseService.delete(parameter.getIdList());

        return "redirect:/admin/course/list.do";
    }

    @GetMapping("/admin/course/list.do")
    public String list(Model model, CourseParameter parameter) {

        parameter.init();

        List<CourseDto> list = courseService.list(parameter);

        long totalCount = 0;
        if (!CollectionUtils.isEmpty(list)) {
            totalCount = list.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml = super.getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", list);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "admin/course/list";
    }

    private String[] getNewFileName(String baseLocalPath, String baseUrlPath, String originalFilename) {
        LocalDate now = LocalDate.now();

        String[] dirs = {
                String.format("%s/%d/", baseLocalPath, now.getYear()),
                String.format("%s/%d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())};

        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        for (String dir : dirs) {
            File file = new File(dir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }

        String fileExtension = "";
        if (originalFilename != null) {
            int dotPos = originalFilename.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFilename.substring(dotPos + 1);
            }
        }

        String uuid = UUID.randomUUID().toString().replace("-", "");
        String newFileName = String.format("%s%s", dirs[2], uuid);
        String newUrlFileName = String.format("%s%s", urlDir, uuid);

        if (fileExtension.length() > 0) {
            newFileName += "." + fileExtension;
            newUrlFileName += "."+ fileExtension;
        }

        return new String[]{newFileName, newUrlFileName};
    }
}
