package com.lionsaid.admin.web.common;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IService<T, ID> {
    void flush();

    <S extends T> S saveAndFlush(S entity);

    <S extends T> List<S> saveAllAndFlush(Iterable<S> entities);

    /**
     * @deprecated
     */
    @Deprecated
    default void deleteInBatch(Iterable<T> entities) {
        this.deleteAllInBatch(entities);
    }

    void deleteAllInBatch(Iterable<T> entities);

    void deleteAllByIdInBatch(Iterable<ID> ids);

    void deleteAllInBatch();

    /**
     * @deprecated
     */
    @Deprecated
    T getOne(ID id);

    /**
     * @deprecated
     */
    @Deprecated
    T getById(ID id);

    T getReferenceById(ID id);

    <S extends T> List<S> findAll(Example<S> example);

    <S extends T> List<S> findAll(Example<S> example, Sort sort);
}
