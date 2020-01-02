package com.breaker.ssi.business.book.service.impl;

import com.breaker.ssi.business.book.entity.Book;
import com.breaker.ssi.business.book.mapper.BookMapper;
import com.breaker.ssi.business.book.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
