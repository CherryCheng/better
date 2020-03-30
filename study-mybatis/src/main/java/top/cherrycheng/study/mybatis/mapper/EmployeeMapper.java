package top.cherrycheng.study.mybatis.mapper;

import top.cherrycheng.study.mybatis.model.Employee;

/**
 * @author ChengRu
 * @date 2020/3/30 8:27
 * @Desc
 */
public interface EmployeeMapper {
    Employee selectById(Long id);
}
