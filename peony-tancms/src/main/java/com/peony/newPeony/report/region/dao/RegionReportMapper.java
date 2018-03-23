package com.peony.newPeony.report.region.dao;

import com.peony.newPeony.report.region.model.RegionUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */
public interface RegionReportMapper {
    List<RegionUtil> selectRegionReportCount(RegionUtil regionUtil);
}
