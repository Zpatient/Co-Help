package com.cohelp.server.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.cohelp.server.model.entity.Selection;
import com.cohelp.server.service.SelectionService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jianping5
 * @createDate 3/3/2023 下午 7:21
 */
public class ExcelSelectionListener extends AnalysisEventListener<Selection> {
    private List<Selection> datas = new ArrayList<>();
    private static final int BATCH_COUNT = 3000;
    private SelectionService SelectionService;

    public ExcelSelectionListener(SelectionService SelectionService) {
        this.SelectionService = SelectionService;
    }

    @Override
    public void invoke(Selection Selection, AnalysisContext analysisContext) {
        //数据存储到datas，供批量处理，或后续自己业务逻辑处理。
        datas.add(Selection);
        //达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if(datas.size() >= BATCH_COUNT){
            saveData();
            // 存储完成清理datas
            datas.clear();
        }
    }

    private void saveData() {
        SelectionService.saveBatch(datas);
    }

    public List<Selection> getDatas() {
        return datas;
    }

    public void setDatas(List<Selection> datas) {
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
