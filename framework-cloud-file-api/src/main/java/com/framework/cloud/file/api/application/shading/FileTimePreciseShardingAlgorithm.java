package com.framework.cloud.file.api.application.shading;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.framework.cloud.common.enums.GlobalNumber;
import lombok.Getter;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.Properties;

/**
 * 文件标准分片
 *
 * @author wusiwei
 */
public class FileTimePreciseShardingAlgorithm implements StandardShardingAlgorithm<Long> {

    /**
     * 参数 可以自定义参数在props里面
     */
    @Getter
    private Properties properties;

    @Override
    public String doSharding(Collection<String> targetNames, PreciseShardingValue<Long> shardingValue) {
        String yearMonth;
        DateTime date = DateUtil.date(shardingValue.getValue());
        int month = DateUtil.month(date) + 1;
        if (month < GlobalNumber.TEN.getIntValue()) {
            yearMonth = DateUtil.year(date) + StringPool.UNDERSCORE + GlobalNumber.ZERO.getStrValue() + month;
        } else {
            yearMonth = DateUtil.year(date) + StringPool.UNDERSCORE + month;
        }
        return targetNames.stream().filter(name -> name.endsWith(yearMonth)).findAny().orElse(null);
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        return null;
    }

    @Override
    public Properties getProps() {
        return properties;
    }

    @Override
    public void init(Properties props) {
        properties = props;
    }
}
