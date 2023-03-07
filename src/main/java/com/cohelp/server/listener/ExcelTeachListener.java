package com.cohelp.server.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.cohelp.server.model.entity.Teach;
import com.cohelp.server.service.TeachService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jianping5
 * @createDate 3/3/2023 下午 7:21
 */
public class ExcelTeachListener extends AnalysisEventListener<Teach> {
    private List<Teach> datas = new ArrayList<>();
    private static final int BATCH_COUNT = 3000;
    private TeachService TeachService;

    public ExcelTeachListener(TeachService TeachService) {
        this.TeachService = TeachService;
    }

    @Override
    public void invoke(Teach Teach, AnalysisContext analysisContext) {
        //数据存储到datas，供批量处理，或后续自己业务逻辑处理。
        datas.add(Teach);
        //达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if(datas.size() >= BATCH_COUNT){
            saveData();
            // 存储完成清理datas
            datas.clear();
        }
    }

    private void saveData() {
        TeachService.saveBatch(datas);
    }

    public List<Teach> getDatas() {
        return datas;
    }

    public void setDatas(List<Teach> datas) {
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
