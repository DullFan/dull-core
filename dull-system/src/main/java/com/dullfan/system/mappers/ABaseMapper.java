package com.dullfan.system.mappers;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface ABaseMapper<T, P> {
    /**
     * 插入
     */
    Integer insert(@Param("bean") T t);
    /**
     * 批量插入
     */
    Integer insertBatch(@Param("list") List<T> list);
    /**
     * 根据参数查询集合
     */
    List<T> selectList(@Param("query") P p);
    /**
     * 根据集合查询数量
     */
    Integer selectCount(@Param("query") P p);
    /**
     * updateByParams:(多条件更新)
     */
    Integer updateByParam(@Param("bean") T t,@Param("query") P p);
    /**
      * deleteByParam:(多条件删除)
    */
    Integer deleteByParam(@Param("query") P p);
}
