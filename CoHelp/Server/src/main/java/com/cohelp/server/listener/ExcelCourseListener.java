package com.cohelp.server.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.cohelp.server.model.entity.Course;
import com.cohelp.server.service.CourseService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jianping5
 * @createDate 3/3/2023 下午 7:21
 */
public class ExcelCourseListener extends AnalysisEventListener<Course> {
    private List<Course> datas = new ArrayList<>();
    private static final int BATCH_COUNT = 3000;
    private CourseService courseService;

    public ExcelCourseListener(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public void invoke(Course course, AnalysisContext analysisContext) {
        //数据存储到datas，供批量处理，或后续自己业务逻辑处理。
        datas.add(course);
        //达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if(datas.size() >= BATCH_COUNT){
            saveData();
            // 存储完成清理datas
            datas.clear();
        }
    }

    private void saveData() {
        courseService.saveBatch(datas);
    }

    public List<Course> getDatas() {
        return datas;
    }

    public void setDatas(List<Course> datas) {
        this.datas = datas;
    }

    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();//确保所有数据都能入库
    }
}
