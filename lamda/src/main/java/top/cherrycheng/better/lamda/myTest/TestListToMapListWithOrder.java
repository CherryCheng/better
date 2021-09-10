package top.cherrycheng.better.lamda.myTest;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 把List转成Map<String,List>，并且自定义里面的List排序
 * 因为Map是无序的，为保证顺序，必须用LinkedHashMap
 *
 * @author ChengRu
 * @date 2021/9/5 17:49
 * @Desc
 */
public class TestListToMapListWithOrder {
    public static void main(String[] args) {
        List<Component> list = new ArrayList<Component>() {{
            add(new Component("组件1", "高级风险", true));
            add(new Component("组件2", "高级风险", false));
            add(new Component("组件3", "高级风险", false));
            add(new Component("组件4", "中级风险", true));
            add(new Component("组件5", "低级风险", true));
            add(new Component("组件6", "低级风险", true));

            add(new Component("组件A", "高级风险", true));
            add(new Component("组件B", "高级风险", true));
            add(new Component("组件C", "高级风险", false));
            add(new Component("组件D", "中级风险", false));
            add(new Component("组件E", "低级风险", false));
            add(new Component("组件F", "低级风险", true));
        }};
        LinkedHashMap<String, List<Component>> collect = list.parallelStream()
                .sorted(Comparator.comparing(Component::isHit).reversed().thenComparing(Component::getName))
                .collect(Collectors.groupingBy(Component::getType))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Collator.getInstance(Locale.CHINA)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldVal, newVal) -> oldVal,
                        LinkedHashMap::new));

        collect.entrySet().forEach(x -> System.out.println(x));

        //        --------------------另一种写法-源数据如下：------------
        //        rule_hit |rule_name|rule_type       |
        //        ------|---------|----------------|
        //        true  |行业类型是民营企业|SimpleRuleResult|
        //        true  |未婚       |SimpleRuleResult|
        //        true  |学历本科     |SimpleRuleResult|
        //        true  |手机号以13开头 |SimpleRuleResult|
        //        true  |性别为女     |SimpleRuleResult|
        //        true  |汉族       |SimpleRuleResult|
        //        true  |年龄大于28   |SimpleRuleResult|
        //        true  |月收入大于3w  |SimpleRuleResult|
        //        false |月收入500_5K|SimpleRuleResult|
        //        false |月收入小于500 |SimpleRuleResult|
        //        false |月收入5k_3w |SimpleRuleResult|
        //        ----------- 转换后数据 ---------------
        //        "SimpleRuleResult":{"8":[{"ruleType":"SimpleRuleResult","ruleHit":true,"ruleName":"学历本科"},{"ruleType":"SimpleRuleResult","ruleHit":true,"ruleName":"年龄大于28"},{"ruleType":"SimpleRuleResult","ruleHit":true,"ruleName":"性别为女"},{"ruleType":"SimpleRuleResult","ruleHit":true,"ruleName":"手机号以13开头"},{"ruleType":"SimpleRuleResult","ruleHit":true,"ruleName":"月收入大于3w"},{"ruleType":"SimpleRuleResult","ruleHit":true,"ruleName":"未婚"},{"ruleType":"SimpleRuleResult","ruleHit":true,"ruleName":"汉族"},{"ruleType":"SimpleRuleResult","ruleHit":true,"ruleName":"行业类型是民营企业"},{"ruleType":"SimpleRuleResult","ruleHit":false,"ruleName":"月收入500_5K"},{"ruleType":"SimpleRuleResult","ruleHit":false,"ruleName":"月收入5k_3w"},{"ruleType":"SimpleRuleResult","ruleHit":false,"ruleName":"月收入小于500"}]}}
        //        List<SmpStrategyRequestRuleDO> list = riskNoticeFromDataHouseMapper.getHitInfo(businessId);
        //        LinkedHashMap<String, LinkedHashMap<Integer, List<SmpStrategyRequestRule>>> result = Maps.newLinkedHashMap();
        //        converter.toSmpStrategyRequestRuleList(list).parallelStream()
        //                .collect(Collectors.groupingBy(SmpStrategyRequestRule::getRuleType))
        //                .entrySet().stream()
        //                .sorted(Map.Entry.comparingByKey(Collator.getInstance(Locale.CHINA)))
        //                .forEachOrdered(x->{
        //                    LinkedHashMap<Integer, List<SmpStrategyRequestRule>> map = Maps.newLinkedHashMap();
        //                    List<SmpStrategyRequestRule> orderedList =
        //                            x.getValue().stream().sorted(Comparator.comparing(SmpStrategyRequestRule::isRuleHit).reversed()
        //                                            .thenComparing(SmpStrategyRequestRule::getRuleName))
        //                                    .collect(Collectors.toList());
        //                    map.put(orderedList.stream().filter(rule -> rule.isRuleHit()).mapToInt(obj->1).sum(), orderedList);
        //                    result.put(x.getKey(),map);
        //                });
        //计算出命中总和
        //        result.entrySet().stream().mapToInt(x -> x.getValue().keySet().stream().mapToInt(obj -> obj.intValue()).sum()).sum()
    }

    @Data
    @AllArgsConstructor
    public static class Component {
        /**
         * 组件姓名
         */
        private String name;
        /**
         * 组件类型
         */
        private String type;
        /**
         * 是否命中
         */
        private boolean hit;

        public Component() {
        }
    }
}
