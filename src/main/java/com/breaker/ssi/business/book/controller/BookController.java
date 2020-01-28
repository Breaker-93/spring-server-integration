package com.breaker.ssi.business.book.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breaker.ssi.business.book.dto.BookRequestDto;
import com.breaker.ssi.business.book.dto.BookResponseDto;
import com.breaker.ssi.business.book.entity.Book;
import com.breaker.ssi.business.book.service.IBookService;
import com.breaker.ssi.utils.DozerUtils;
import com.breaker.ssi.utils.entity.DelStatus;
import com.breaker.ssi.utils.entity.FlagStatus;
import com.breaker.ssi.utils.result.Ret;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Breaker-93
 * @since 2020-01-01
 */
@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    IBookService bookServiceImpl;

    @Transactional(rollbackFor=Exception.class)
    @PostMapping
    @ApiOperation(value = "添加书籍", notes = "业务主键自动生成")
    @ApiImplicitParams({
            @ApiImplicitParam(name  =  "name",value  ="书籍名称", required = true),
            @ApiImplicitParam(name  =  "price",value  ="书籍价格", required = true)
    })
    public Ret insert(BookRequestDto bookRequestDto) {
        Book book = DozerUtils.map(bookRequestDto, Book.class);
        if (bookServiceImpl.save(book) ) {
            return Ret.ok().setData(book);
        } else {
            return Ret.error();
        }

    }

    @Transactional(rollbackFor=Exception.class)
    @PutMapping("/{businessId:\\w+}")
    @ApiOperation(value = "修改书籍", notes = "修改名称和价格")
    public Ret updateById(@PathVariable(value="businessId") String businessId, BookRequestDto bookRequestDto) {
        Book book = DozerUtils.map(bookRequestDto, Book.class);
        book.setBusinessId(businessId);
        if (bookServiceImpl.updateById(book)) {
            return Ret.ok().setData("修改成功").setData(book);
        } else {
            return Ret.error().setData("修改失败");
        }
    }



    @Transactional(rollbackFor=Exception.class)
    @DeleteMapping("/{businessId:\\w+}")
    @ApiOperation(value = "删除书籍（默认物理删除）", notes = "logDel传1表示做逻辑删除,传0标识恢复逻辑删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name  =  "businessId",value  ="业务主键", required = true),
        @ApiImplicitParam(name  =  "logDel",value  ="逻辑删除标识")
    })
    public Ret removeById(@PathVariable(value="businessId") String businessId, String logDel) {
        if ( logDel != null) {
            if(logDel.equals("1")) {
                if(bookServiceImpl.logicalDelete(businessId) > 0) {
                   return Ret.ok().setData("逻辑删除成功");
                }else {
                    return Ret.error().setData("逻辑删除失败");
                }
            }else if(logDel.equals("0")) {
                if(bookServiceImpl.recoverLogDel(businessId) > 0) {
                    return Ret.ok().setData("逻辑删除恢复成功");
                }else {
                    return Ret.error().setData("逻辑删除恢复失败");
                }
            }else {
                return Ret.error().setData("逻辑删除标识码错误");
            }
        }else {
            return Ret.ok().setData(bookServiceImpl.removeById(businessId));
        }
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED )
    @GetMapping("/{businessId:\\w+}")
    @ApiOperation(value = "查询书籍详情", notes = "根据业务主键businessId")
    @ApiImplicitParams({
            @ApiImplicitParam(name  =  "businessId",value  ="业务主键", required = true)
    })
    public Ret<List<BookResponseDto>> selectById(@PathVariable String businessId) {
        return Ret.ok().setData(bookServiceImpl.getById(businessId));
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED )
    @GetMapping
    @ApiOperation(value = "条件模糊查询所有书籍列表(不带分页)", notes = "复合条件模糊查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name  =  "name",value  ="书籍名称"),
        @ApiImplicitParam(name  =  "price",value  ="书籍价格")
    })
    public Ret<List<BookResponseDto>> selectList(BookRequestDto bookRequestDto) {
        return Ret.ok().setData(bookServiceImpl.listByDto(bookRequestDto));
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED )
    @GetMapping("/page")
    @ApiOperation(value = "条件分页查询书籍", notes = "条件模糊匹配")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "当前页", required = true),
        @ApiImplicitParam(name = "size", value = "每页数量", required = true),
        @ApiImplicitParam(name  =  "name",value  ="书籍名称"),
        @ApiImplicitParam(name  =  "price",value  ="书籍价格")
    })
    public Ret<IPage<BookResponseDto>> selectListPage(BookRequestDto bookRequestDto, Integer page, Integer size) {
        if (page == null && size == null) {
            return Ret.error().setData("当前页和页大小不能为空");
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        if(bookRequestDto.getName() != null) {
            queryWrapper.like("name", bookRequestDto.getName());
        }
        if(bookRequestDto.getPrice() != null) {
            queryWrapper.like("price", bookRequestDto.getPrice());
        }
        queryWrapper.eq("del_flag", DelStatus.NORMAL.getStatus());
        queryWrapper.eq("flag", FlagStatus.START.getStatus());
        return Ret.ok().setData(bookServiceImpl.page(new Page<>(page,size),queryWrapper));
    }
}
