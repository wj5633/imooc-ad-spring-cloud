package com.wj5633.ad.service;

import com.wj5633.ad.exception.CustomException;

/**
 * Created at 2019/8/11 14:53.
 *
 * @author wangjie
 * @version 1.0.0
 */

public interface ISpringTransaction {

    void catchExceptionCanNotRollback();

    void notRuntimeExceptionCanNotRollback() throws CustomException;

    void runtimeExceptionCanRollback();

    void assignExceptionCanRollback() throws CustomException;

    void rollbackOnlyCanRollback() throws CustomException;
}
