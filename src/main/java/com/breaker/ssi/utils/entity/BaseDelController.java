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
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Breaker-93
 * @since 2020-01-01
 */
public class BaseDelController<T extends ServiceImpl, E extends CommonEntity> extends BaseController<T, E> {
    @Autowired
    T t;

    @Transactional(rollbackFor=Exception.class)
    @DeleteMapping("/{businessId:\\w+}")
    @ApiOperation(value = "删除（默认物理删除）", notes = "logDel传1表示做逻辑删除,传0标识恢复逻辑删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name  =  "businessId",value  ="业务主键", required = true),
        @ApiImplicitParam(name  =  "logDel",value  ="逻辑删除标识")
    })
    public Ret removeById(@PathVariable(value="businessId") String businessId, String logDel) {
        if ( logDel != null) {
            ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class clazz = (Class) parameterizedType.getActualTypeArguments()[1];
            E e = null;
            try {
                e = (E)clazz.newInstance();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
            e.setBusinessId(businessId);
            e.setDeleted();
            if(e instanceof CommonEntity) {
//                e = (CommonEntity) e;
            }
            if(t.updateById(e)) {
                String successInfo = logDel.equals("1") ? "逻辑删除成功" : "逻辑删除的数据已成功恢复";
                return Ret.ok().setData(successInfo);
            }else {
                String failInfo = logDel.equals("1") ? "逻辑删除失败" : "逻辑删除的数据恢复失败";
                return Ret.error().setData(failInfo);
            }
        }
        else {
            return Ret.ok().setData(t.removeById(businessId));
        }
    }

}
