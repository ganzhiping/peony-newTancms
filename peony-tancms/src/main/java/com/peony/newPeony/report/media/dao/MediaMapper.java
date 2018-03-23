package com.peony.newPeony.report.media.dao;

import com.peony.newPeony.report.media.model.MediaUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */
public interface MediaMapper {
    List<MediaUtil> selectMediaReportCount(MediaUtil mediaUtil);
}
