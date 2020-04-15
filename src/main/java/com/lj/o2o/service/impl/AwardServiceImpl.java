package com.lj.o2o.service.impl;

import com.lj.o2o.dao.AwardDao;
import com.lj.o2o.dto.AwardExecution;
import com.lj.o2o.dto.ImageHolder;
import com.lj.o2o.entity.Award;
import com.lj.o2o.enums.AwardStateEnum;
import com.lj.o2o.exception.AwardOperationException;
import com.lj.o2o.service.AwardService;
import com.lj.o2o.util.ImageUtil;
import com.lj.o2o.util.PageCalculator;
import com.lj.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AwardServiceImpl implements AwardService {

    @Autowired
    private AwardDao awardDao;

    @Override
    public AwardExecution getAwardList(Award awardCondition, Integer pageIndex, Integer pageSize) {
        //页转行
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        if (awardCondition != null && rowIndex > -1 && pageSize > -1) {
            List<Award> awardList = awardDao.queryAwardList(awardCondition, rowIndex, pageSize);
            int count = awardDao.queryAwardCount(awardCondition);
            AwardExecution ae = new AwardExecution();
            ae.setAwardList(awardList);
            ae.setCount(count);
            return ae;
        } else {
            return null;
        }
    }

    /**
     * 修改奖品信息
     * @param award
     * @param thumbnail
     * @return
     */
    @Override
    @Transactional
    // 1.处理缩略图，获取缩略图相对路径并赋值给award
    // 2.往tb_award写入奖品信息
    public AwardExecution modifyAward(Award award, ImageHolder thumbnail) {
        // 空值判断
        if (award != null && award.getAwardId() != null) {
            award.setLastEditTime(new Date());
            if (thumbnail != null) {
                // 通过awardId取出对应的实体类信息
                Award tempAward = awardDao.queryAwardByAwardId(award.getAwardId());
                // 如果传输过程中存在图片流，则删除原有图片
                if (tempAward.getAwardImg() != null) {
                    ImageUtil.deleteFileOrPath(tempAward.getAwardImg());
                }
                // 存储图片流，获取相对路径
                addThumbnail(award, thumbnail);
            }
            try {
                // 根据传入的实体类修改相应的信息
                int effectedNum = awardDao.updateAward(award);
                if (effectedNum <= 0) {
                    throw new AwardOperationException("更新商品信息失败");
                }
                return new AwardExecution(AwardStateEnum.SUCCESS, award);
            } catch (Exception e) {
                throw new AwardOperationException("更新商品信息失败:" + e.toString());
            }
        } else {
            return new AwardExecution(AwardStateEnum.EMPTY);
        }
    }

    private void addThumbnail(Award award, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(award.getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        award.setAwardImg(thumbnailAddr);
    }
}
