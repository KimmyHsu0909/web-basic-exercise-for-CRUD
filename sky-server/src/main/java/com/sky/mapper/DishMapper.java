package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Constant;
import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 新增菜品
     *
     * @param dish
     * @return
     */
    @AutoFill(value = OperationType.INSERT)
    int insert(Dish dish);

    /**
     * 分页查询
     * @param queryDTO
     * @return
     */
    Page<DishVO> pageQuery(DishPageQueryDTO queryDTO);

    /**
     * 根据id修改菜品
     * @param dish
     * @return
     */
    @AutoFill(value = OperationType.UPDATE)
    int updateById(@Param("et") Dish dish);
}
