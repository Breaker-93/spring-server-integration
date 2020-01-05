package com.breaker.ssi.business.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breaker.ssi.business.book.dto.BookRequestDto;
import com.breaker.ssi.business.book.dto.BookResponseDto;
import com.breaker.ssi.business.book.entity.Book;
import com.breaker.ssi.business.book.mapper.BookMapper;
import com.breaker.ssi.business.book.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breaker.ssi.utils.DozerUtils;
import com.breaker.ssi.utils.entity.DelStatus;
import com.breaker.ssi.utils.entity.FlagStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Breaker-93
 * @since 2020-01-01
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {
    @Autowired
    BookMapper bookMapper;

    @Override
    public int logicalDelete(String id) {
        Book book = new Book();
        book.setDeleted();
        book.setBusinessId(id);
//        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.eq("business_id", id);
        return this.baseMapper.updateById(book);
    }

    @Override
    public List<BookResponseDto> listByDto(BookRequestDto requestDto) {
        QueryWrapper queryWrapper = getQueryWrapper(requestDto);
        List<Book> bookList = bookMapper.selectList(queryWrapper);
        List<BookResponseDto> resultList = DozerUtils.mapList(bookList, BookResponseDto.class);
        return resultList;
    }

    private QueryWrapper getQueryWrapper(BookRequestDto requestDto) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("del_flag", DelStatus.NORMAL.getStatus());
        queryWrapper.eq("flag", FlagStatus.START.getStatus());
        return queryWrapper;
    }
}
