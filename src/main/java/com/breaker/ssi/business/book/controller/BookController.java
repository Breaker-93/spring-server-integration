package com.breaker.ssi.business.book.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breaker.ssi.business.book.dto.BookRequestDto;
import com.breaker.ssi.business.book.entity.Book;
import com.breaker.ssi.business.book.service.IBookService;
import com.breaker.ssi.utils.DozerUtils;
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
@RequestMapping("/book")
public class BookController {
    @Autowired
    IBookService bookServiceImpl;

    @Transactional(rollbackFor=Exception.class)
    @PostMapping
    @ApiOperation(value = "添加书籍", notes = "添加书籍")
    @ApiImplicitParams({
            @ApiImplicitParam(name  =  "name",value  ="书籍名称"),
            @ApiImplicitParam(name  =  "price",value  ="书籍价格")
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
    @ApiOperation(value = "修改书籍", notes = "修改书籍")
    public Ret updateById(@PathVariable(value="businessId") String businessId, BookRequestDto bookRequestDto) {
        Book book = DozerUtils.map(bookRequestDto, Book.class);
        //判断id 是否为空
        if (!businessId.isEmpty()) {
            book.setBusinessId(businessId);
            if (bookServiceImpl.updateById(book)) {
                return Ret.ok().setData("修改成功").setData(book);
            } else {
                return Ret.error().setData("修改失败");
            }
        } else {
            return Ret.error().setData("businessId不能为空");
        }
    }



//    @Transactional(rollbackFor=Exception.class)
//    @DeleteMapping("/{businessId:\\w+}")
//    @ApiOperation(value = "物理删除书籍", notes = "")
//    public Ret removeById(@PathVariable(value="businessId") String businessId) {
//        if (!businessId.isEmpty()) {
//            return Ret.ok().setData(bookServiceImpl.de(businessId));
//        }else {
//            return Ret.error().setMsg("businessId不能为空");
//        }
//    }
//
//    @Transactional(rollbackFor=Exception.class)
//    @PutMapping("/logDel/{businessId:\\w+}")
//    @ApiOperation(value = "逻辑删除数据字典", notes = "")
//    public Ret logicalDeleteById(@PathVariable(value="businessId") String businessId) {
//        if (StrUtil.isNotBlank(businessId) && dictionaryServiceImpl.logicalDelete(businessId) > 0) {
//            return Ret.ok().setMsg("操作成功");
//        }else {
//            return Ret.error().setMsg("businessId不能为空");
//        }
//    }


//    @Transactional(propagation= Propagation.NOT_SUPPORTED )
//    @GetMapping
//    @ApiOperation(value = "查询所有数据字典列表(不带分页)", notes = "查询数据字典列表(传id即查询编辑页信息)")
//    public Ret<List<BookRequestDto>> selectList(BookRequestDto bookRequestDto) {
//
//        return Ret.ok().setData(dictionaryServiceImpl.getDictionaryList(dictionary));
//    }
//
//    @Transactional(propagation=Propagation.NOT_SUPPORTED )
//    @GetMapping("/page")
//    @ApiOperation(value = "分页查询数据字典", notes = "")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "page", value = "当前页", required = true),
//            @ApiImplicitParam(name = "size", value = "页大小", required = true)})
//    public Ret<IPage<ResultSDictionaryDTO>> selectListPage(QuerySDictionaryDTO sDictionary, Integer page, Integer size) {
//        if (page == null && size == null) {
//            return Ret.error().setMsg("当前页和页大小不能为空");
//        }
//        return Ret.ok().setData(dictionaryServiceImpl.getDictionaryListPage(new Page<>(page,size),sDictionary));
//    }
}
