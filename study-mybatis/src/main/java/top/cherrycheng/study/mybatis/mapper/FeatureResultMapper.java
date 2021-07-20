package top.cherrycheng.study.mybatis.mapper;

import top.cherrycheng.study.mybatis.model.FeatureResult;

import java.util.List;

/**
 * @author ChengRu
 * @date 2020/3/30 8:27
 * @Desc
 */
public interface FeatureResultMapper {
    /**
     * 用默认值插入
     */
    void insertByDefault(FeatureResult featureResult);

    void insert(FeatureResult featureResult);

    void update(FeatureResult featureResult);

    List<Long> getAllIds();
}
