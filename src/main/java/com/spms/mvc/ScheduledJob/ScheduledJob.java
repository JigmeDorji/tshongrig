package com.spms.mvc.ScheduledJob;

import com.spms.mvc.library.helper.Kuenga_workshop_db_backup;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.IOException;

/**
 * Created by vcass on 1/28/2018.
 */
public class ScheduledJob extends QuartzJobBean {

    private Kuenga_workshop_db_backup kuenga_workshop_db_backup;

    public void setKuenga_workshop_db_backup(Kuenga_workshop_db_backup kuenga_workshop_db_backup) {
        this.kuenga_workshop_db_backup = kuenga_workshop_db_backup;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            kuenga_workshop_db_backup.backupDataDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
