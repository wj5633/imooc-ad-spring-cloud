package com.wj5633.ad.service;

import com.wj5633.ad.AdExtraApplicationTests;
import com.wj5633.ad.exception.CustomException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created at 2019/8/11 14:58.
 *
 * @author wangjie
 * @version 1.0.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdExtraApplicationTests.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SpringTransactionTest {
    @Autowired
    private ISpringTransaction springTransaction;

    @Test
    public void testCatchExceptionCanNotRollback() {
        springTransaction.catchExceptionCanNotRollback();
    }

    @Test
    public void testNotRuntimeExceptionCanNotRollback() throws CustomException {
        springTransaction.notRuntimeExceptionCanNotRollback();
    }

    @Test
    public void testRuntimeExceptionCanNotRollback() {
        springTransaction.runtimeExceptionCanRollback();
    }

    @Test
    public void testAssignExceptionCanRollback() throws CustomException {
        springTransaction.assignExceptionCanRollback();
    }

    @Test
    public void testRollbackOnlyCanRollback() throws CustomException {
        springTransaction.rollbackOnlyCanRollback();
    }
}
