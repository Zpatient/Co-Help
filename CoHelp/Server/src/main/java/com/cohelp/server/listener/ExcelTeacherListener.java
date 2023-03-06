package com.cohelp.server.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.service.UserService;
import org.ehcache.shadow.org.terracotta.offheapstore.paging.UpfrontAllocatingPageSource;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jianping5
 * @createDate 3/3/2023 下午 7:21
 */
public class ExcelTeacherListener extends AnalysisEventListener<User> {
    private List<User> datas = new ArrayList<>();
    private static final int BATCH_COUNT = 3000;
    private UserService UserService;
    public static final String SALT = "CoHelp";

    public ExcelTeacherListener(UserService UserService) {
        this.UserService = UserService;
    }

    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + user.getUserPassword()).getBytes());
        user.setUserPassword(encryptedPassword);
        //数据存储到datas，供批量处理，或后续自己业务逻辑处理。
        datas.add(user);
        //达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if(datas.size() >= BATCH_COUNT){
            saveData();
            // 存储完成清理datas
            datas.clear();
        }
    }

    private void saveData() {
        UserService.saveBatch(datas);
    }

    public List<User> getDatas() {
        return datas;
    }

    public void setDatas(List<User> datas) {
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
