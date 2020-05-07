package com.breaker.ssi.utils.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breaker.ssi.utils.result.Ret;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Breaker-93
 * @since 2020-01-01
 */
public class BaseController<T extends ServiceImpl, E extends IdEntity> {
    @Autowired
    public T t;

    @Transactional(rollbackFor=Exception.class)
    @PostMapping
    @ApiOperation(value = "添加" , notes = "业务主键自动生成")
    public Ret insert(E e) {
        if (t.save(e) ) {
            return Ret.ok().setData(e);
        } else {
            return Ret.error();
        }

    }

    @Transactional(rollbackFor=Exception.class)
    @PutMapping("/{businessId:\\w+}")
    @ApiOperation(value = "修改更新", notes = "修改")
    public Ret updateById(@PathVariable(value="businessId") String businessId, E e) {
        e.setBusinessId(businessId);
        if (t.updateById(e)) {
            return Ret.ok().setData("修改成功").setData(e);
        } else {
            return Ret.error().setData("修改失败");
        }
    }


    @Transactional(propagation= Propagation.NOT_SUPPORTED )
    @GetMapping("/{businessId:\\w+}")
    @ApiOperation(value = "查询详情", notes = "根据业务主键businessId")
    @ApiImplicitParams({
            @ApiImplicitParam(name  =  "businessId",value  ="业务主键", required = true)
    })
    public Ret<List<E>> selectById(@PathVariable String businessId) {
        return Ret.ok().setData(t.getById(businessId));
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED )
    @GetMapping
    @ApiOperation(value = "多条件复合模糊或多合一模糊查询列表(不带分页)", notes = "复合条件模糊查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "keyword", value = "多合一条件的关键字，不传则为多条件复合")
    })
    public Ret<List<E>> selectList(String keyword, E e) {
        QueryWrapper queryWrapper;
        if(keyword != null && keyword != "") {
            queryWrapper = getWrapperByOr(keyword, e);
        }else {
            queryWrapper = getWrapperByAnd(e);
        }
        return Ret.ok().setData(t.list(queryWrapper));
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED )
    @GetMapping("/page")
    @ApiOperation(value = "多条件复合模糊或多合一模糊查询列表（分页）", notes = "条件模糊匹配")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "当前页", required = true),
        @ApiImplicitParam(name = "size", value = "每页数量", required = true),
        @ApiImplicitParam(name = "keyword", value = "多合一条件的关键字，不传则为多条件复合")
    })
    public Ret<IPage<E>> selectListPage(String keyword, E e, Integer page, Integer size) {
        if (page == null && size == null) {
            return Ret.error().setData("当前页和页大小不能为空");
        }
        QueryWrapper queryWrapper;
        if(keyword != null && keyword != "") {
            queryWrapper = getWrapperByOr(keyword, e);
        }else {
            queryWrapper = getWrapperByAnd(e);
        }
        return Ret.ok().setData(t.page(new Page<>(page,size),queryWrapper));
    }

    public QueryWrapper getWrapperByAnd(E e) {
        QueryWrapper queryWrapper = new QueryWrapper();
        try {
            Class<?> clazz = Class.forName(e.getClass().getName());

            // 获得字段注解
            Field fields[] = clazz.getDeclaredFields();
            for (Field field : fields) {
                // 获取普通属性的@Column注解
                TableField tableField = field.getAnnotation(TableField.class);
                boolean accessFlag = field.isAccessible();
                field.setAccessible(true);
                if(field.get(e) != null && tableField != null) {
                    queryWrapper.like(tableField.value(), field.get(e));
                }
                field.setAccessible(accessFlag);
            }
        }catch (Exception exception) {
            exception.printStackTrace();
        }
        queryWrapper.eq("del_flag", DelStatus.NORMAL.getStatus());
        queryWrapper.orderByDesc("create_time");
        return queryWrapper;
    }

    public QueryWrapper getWrapperByOr(String keyword, E e) {
        QueryWrapper queryWrapper = new QueryWrapper();
        try {
            Class<?> clazz = Class.forName(e.getClass().getName());
            // 获得字段注解
            Field fields[] = clazz.getDeclaredFields();
            queryWrapper.eq("del_flag", DelStatus.NORMAL.getStatus());
            queryWrapper.eq("business_id", "false id");
            for (Field field : fields) {
                // 获取普通属性的@Column注解
                TableField tableField = field.getAnnotation(TableField.class);
                queryWrapper.or(true).like(tableField.value(), keyword);
            }
        }catch (Exception exception) {
            exception.printStackTrace();
        }

        return queryWrapper;
    }
}
