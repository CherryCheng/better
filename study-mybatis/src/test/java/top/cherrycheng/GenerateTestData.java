package top.cherrycheng;

import cn.binarywang.tools.generator.ChineseIDCardNumberGenerator;
import cn.binarywang.tools.generator.ChineseMobileNumberGenerator;
import cn.binarywang.tools.generator.ChineseNameGenerator;
import com.google.common.collect.Lists;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import top.cherrycheng.study.mybatis.mapper.FeatureResultMapper;
import top.cherrycheng.study.mybatis.model.FeatureResult;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @author ChengRu
 * @date 2021/7/20 19:40
 * @Desc
 */
public class GenerateTestData {

    /**
     * 测试内部方法
     */
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
//            System.out.println(getRandomDate("2020-10-10","2021-07-20"));
//            System.out.println(getRandomMac());
//            System.out.println(getRandomWorkCompany());
            System.out.println(getRandomDateAfterNow());
        }
    }

    /**
     * 批量生成数据
     */
    @Test
    public void batchInsert() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        ChineseMobileNumberGenerator instance = ChineseMobileNumberGenerator.getInstance();
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        List<FeatureResult> list = Lists.newArrayList();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            final FeatureResultMapper mapper = session.getMapper(FeatureResultMapper.class);
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    final FeatureResult result = new FeatureResult();
                    setFeatureResult(instance, result, null);
                    list.add(result);
                }
                mapper.batchInsert(list);
                session.commit();
                list.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量更新
     */
    @Test
    public void batchUpdateInfo() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        List<FeatureResult> list = Lists.newArrayList();
        final ChineseMobileNumberGenerator instance = ChineseMobileNumberGenerator.getInstance();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            final FeatureResultMapper mapper = session.getMapper(FeatureResultMapper.class);
//            ⚠️能过这里来控制改哪些✔️
            for (int i = 0; i < 2; i++) {
                final List<Long> allIds = mapper.getAllIds();
                for (Long id : allIds) {
                    final FeatureResult result = new FeatureResult();
                    setFeatureResult(instance, result, id);
                    mapper.update(result);
                    list.add(result);
                }
                mapper.batchUpdate(list);
                session.commit();
                list.clear();
                System.out.println("第" + (i + 1) + "次,更新的ID为：" + Arrays.toString(allIds.toArray()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成数据：用SQL里定义的值
     */
    @Test
    public void insertInfo() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        ChineseMobileNumberGenerator instance = ChineseMobileNumberGenerator.getInstance();
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            for (int i = 0; i < 1; i++) {
                final FeatureResultMapper mapper = session.getMapper(FeatureResultMapper.class);
                final FeatureResult result = new FeatureResult();
                setFeatureResult(instance, result, null);
                mapper.insert(result);
                session.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 改姓名和身份证号
     */
    @Test
    public void updateInfo() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        final ChineseMobileNumberGenerator instance = ChineseMobileNumberGenerator.getInstance();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            final FeatureResultMapper mapper = session.getMapper(FeatureResultMapper.class);
            final List<Long> allIds = mapper.getAllIds();
            FeatureResult result = new FeatureResult();
            for (Long id : allIds) {
                setFeatureResult(instance, result, id);
                mapper.update(result);
            }
        }
    }

    private void setFeatureResult(ChineseMobileNumberGenerator instance, FeatureResult result, Long id) {
        String IDCardNumber = ChineseIDCardNumberGenerator.getInstance().generate();
        result.setId(id);
        // 成茹: todo  如果是修改，哪些不用改？
//        if (id != null) {
//
//        }
        result.setBk_certnovalidday_i(getRandomDateAfterNow());
        result.setName(ChineseNameGenerator.getInstance().generate());
        result.setCertNo(IDCardNumber);
        result.setAge(getIdCardAge(IDCardNumber));
        result.setMobile(instance.generate());
        result.setApply_time(getRandomDate("2020-10-10", "2021-07-20"));
        result.setMarry_status(getRandomMarryStatus());
        result.setWork_company(getRandomWorkCompany());
        result.setIncome_range(getRandomIncomeRange());
        result.setContact_name1(ChineseNameGenerator.getInstance().generate());
        result.setContact_relation1(getRandomRelation());
        result.setContact_mobile1(instance.generate());
        result.setContact_name2(ChineseNameGenerator.getInstance().generate());
        result.setContact_relation2(getRandomRelation());
        result.setContact_mobile2(instance.generate());
        result.setRequest_ip(getRandomIp());
        result.setDevice_type(getRandomDeviceType());
        result.setDevice_mac(getRandomMac());
        result.setDevice_risk(getRandomDeviceRisk());
    }

    /**
     * 生成数据:用DB默认值
     */
//    @Test
//    public void insertDefaultInfo() throws IOException {
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        try (SqlSession session = sqlSessionFactory.openSession()) {
//            for (int i = 0; i < 10000000; i++) {
//                final FeatureResultMapper mapper = session.getMapper(FeatureResultMapper.class);
//                final FeatureResult result = new FeatureResult();
//                result.setName(ChineseNameGenerator.getInstance().generate());
//                result.setCertNo(ChineseIDCardNumberGenerator.getInstance().generate());
//                mapper.insertByDefault(result);
//                session.commit();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static String getRandomIncomeRange() {
        String[] incomeRange = {"2000及以下", "2001~5000", "5001~8000", "8001~10000", "10001~15000", "15001及以上"};
        return incomeRange[new Random().nextInt(incomeRange.length)];
    }

    public static String getRandomRelation() {
        String[] marryStatus = {"朋友", "父母", "夫妻", "同事"};
        return marryStatus[new Random().nextInt(marryStatus.length)];
    }

    public static String getRandomDeviceRisk() {
        String[] deviceRisk = {"无", "疑似刷机", "关联多账号", "疑似猫池", "新设备"};
        return deviceRisk[new Random().nextInt(deviceRisk.length)];
    }

    public static String getRandomDeviceType() {
        String[] deviceType = {"iPhone 6s", "iPhone 6s Plus", "iPhone SE", "iPhone 7", "iPhone 7 Plus", "iPhone 8", "iPhone 8 Plus", "iPhone X", "iphone Xs", "iPhone XS Max", "iPhone11", "iPhone 11 Pro", "iPhone 11 Pro Max", "iPhone 12", "iPhone 12 mini", "iPhone 12 Pro", "iPhone 12 Pro Max", "华为nova 4e", "华为畅享9s", "华为畅享9e", "华为Mate X", "华为P30", "华为P30 Pro", "华为麦芒8", "华为nova5", "华为nova 5i", "华为nova5 Pro", "HUAWEI Mate 30", "华为Mate Xs", "华为P40", "华为P40 Pro", "华为P40 Pro+", "OPPO R17", "OPPO Find X"};
        return deviceType[new Random().nextInt(deviceType.length)];
    }

    public static String getRandomMarryStatus() {
        String[] marryStatus = {"未知", "已婚", "未婚"};
        return marryStatus[new Random().nextInt(marryStatus.length)];
    }

    public static String getIdCardAge(String idCard) {
        final LocalDate birth = LocalDate.parse(idCard.substring(6, 14), DateTimeFormatter.ofPattern("yyyyMMdd"));
        return ChronoUnit.YEARS.between(birth, LocalDate.now()) + "";
    }

    public static String getRandomIp() {
        Random r = new Random();
        return r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
    }

    private static String SEPARATOR_OF_MAC = ":";

    public static String getRandomMac() {
        Random random = new Random();
        String[] mac = {
                String.format("%02x", 0x52),
                String.format("%02x", 0x54),
                String.format("%02x", 0x00),
                String.format("%02x", random.nextInt(0xff)),
                String.format("%02x", random.nextInt(0xff)),
                String.format("%02x", random.nextInt(0xff))
        };
        return String.join(SEPARATOR_OF_MAC, mac);
    }

    private static Date getRandomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);
            Date end = format.parse(endDate);
//
//            if(start.getTime() >= end.getTime()){
//                return null;
//            }
            long date = random(start.getTime(), end.getTime());
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }

    public static String getRandomWorkCompany() {
        String[] company_suffixes = {"企业管理咨询有限责任公司", "企业管理咨询责任有限公司", "信息科技有限责任公司", "信息科技责任有限公司", "管理咨询责任有限公司", "管理咨询有限责任公司", "企业管理咨询有限公司", "信息技术有限责任公司", "信息技术责任有限公司", "科技股份有限公司", "科技有限责任公司", "科技责任有限公司", "信息科技有限公司", "咨询有限责任公司", "咨询责任有限公司", "管理咨询有限公司", "信息技术有限公司", "技术有限责任公司", "技术责任有限公司", "管理有限责任公司", "管理责任有限公司", "责任有限公司", "有限责任公司", "股份有限公司", "科技有限公司", "信息有限公司", "咨询有限公司", "技术有限公司", "管理有限公司", "有限公司"};

        String[] city = {"北京", "天津", "唐山", "邯郸", "邢台", "保定", "承德", "沧州", "廊坊", "衡水", "太原", "大同", "阳泉", "长治", "晋城", "朔州", "晋中", "运城", "忻州", "临汾", "吕梁", "包头", "乌海", "赤峰", "通辽", "沈阳", "大连", "鞍山", "抚顺", "本溪", "丹东", "锦州", "营口", "阜新", "辽阳", "盘锦", "铁岭", "朝阳", "长春", "吉林", "四平", "辽源", "通化", "白山", "松原", "白城", "鸡西", "鹤岗", "大庆", "伊春", "黑河", "绥化", "上海", "南京", "无锡", "徐州", "常州", "苏州", "南通", "淮安", "盐城", "扬州", "镇江", "泰州", "宿迁", "杭州", "宁波", "温州", "嘉兴", "湖州", "绍兴", "金华", "衢州", "舟山", "台州", "丽水", "合肥", "芜湖", "蚌埠", "淮南", "淮北", "铜陵", "安庆", "黄山", "滁州", "阜阳", "宿州", "六安", "亳州", "池州", "宣城", "福州", "厦门", "莆田", "三明", "泉州", "漳州", "南平", "龙岩", "宁德", "南昌", "萍乡", "九江", "新余", "鹰潭", "赣州", "吉安", "宜春", "抚州", "上饶", "济南", "青岛", "淄博", "枣庄", "东营", "烟台", "潍坊", "济宁", "泰安", "威海", "日照", "临沂", "德州", "聊城", "滨州", "菏泽", "郑州", "开封", "洛阳", "安阳", "鹤壁", "新乡", "焦作", "濮阳", "许昌", "漯河", "南阳", "商丘", "信阳", "周口", "武汉", "黄石", "十堰", "宜昌", "襄阳", "鄂州", "荆门", "孝感", "荆州", "黄冈", "咸宁", "随州", "长沙", "株洲", "湘潭", "衡阳", "邵阳", "岳阳", "常德", "益阳", "郴州", "永州", "怀化", "娄底", "广州", "韶关", "深圳", "珠海", "汕头", "佛山", "江门", "湛江", "茂名", "肇庆", "惠州", "梅州", "汕尾", "河源", "阳江", "清远", "东莞", "中山", "潮州", "揭阳", "云浮", "南宁", "柳州", "桂林", "梧州", "北海", "钦州", "贵港", "玉林", "百色", "贺州", "河池", "来宾", "崇左", "海口", "三亚", "三沙", "儋州", "重庆", "成都", "自贡", "泸州", "德阳", "绵阳", "广元", "遂宁", "内江", "乐山", "南充", "眉山", "宜宾", "广安", "达州", "雅安", "巴中", "资阳", "贵阳", "遵义", "安顺", "毕节", "铜仁", "昆明", "曲靖", "玉溪", "保山", "昭通", "丽江", "普洱", "临沧", "拉萨", "昌都", "林芝", "山南", "那曲", "西安", "铜川", "宝鸡", "咸阳", "渭南", "延安", "汉中", "榆林", "安康", "商洛", "兰州", "金昌", "白银", "天水", "武威", "张掖", "平凉", "酒泉", "庆阳", "定西", "陇南", "西宁", "海东", "银川", "吴忠", "固原", "中卫", "哈密"};
        StringBuilder sb = new StringBuilder();
        sb.append(city[new Random().nextInt(city.length)]);
        sb.append("xxx");
        sb.append(company_suffixes[new Random().nextInt(company_suffixes.length)]);
        return sb.toString();
    }

    private static Date getRandomDateAfterNow() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.YEAR, new Random().nextInt(10));
        c.add(Calendar.MONTH, new Random().nextInt(10));
        c.add(Calendar.DATE, new Random().nextInt(300));
        return c.getTime();
    }
}
