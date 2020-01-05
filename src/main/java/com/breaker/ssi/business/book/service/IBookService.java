package com.breaker.ssi.business.book.service;

import com.breaker.ssi.business.book.dto.BookRequestDto;
import com.breaker.ssi.business.book.dto.BookResponseDto;
import com.breaker.ssi.business.book.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Breaker-93
 * @since 2020-01-01
 */
public interface IBookService extends IService<Book> {
    int logicalDelete(String id);
    List<BookResponseDto> listByDto(BookRequestDto requestDto);
}
