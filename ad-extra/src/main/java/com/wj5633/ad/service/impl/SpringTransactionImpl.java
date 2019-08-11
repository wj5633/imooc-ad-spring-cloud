package com.wj5633.ad.service.impl;

import com.wj5633.ad.dao.ExtraAdRepository;
import com.wj5633.ad.entity.ExtraAd;
import com.wj5633.ad.exception.CustomException;
import com.wj5633.ad.service.ISpringTransaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created at 2019/8/11 14:55.
 *
 * @author wangjie
 * @version 1.0.0
 */

@Service
public class SpringTransactionImpl implements ISpringTransaction {

    private final ExtraAdRepository extraAdRepository;

    public SpringTransactionImpl(ExtraAdRepository extraAdRepository) {
        this.extraAdRepository = extraAdRepository;
    }

    @Override
    @Transactional
    public void catchExceptionCanNotRollback() {
        try {
            extraAdRepository.save(new ExtraAd("wang"));
        } catch (Exception e) {
            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Override
    @Transactional
    public void notRuntimeExceptionCanNotRollback() throws CustomException {
        try {
            extraAdRepository.save(new ExtraAd("wang"));
            throw new RuntimeException();
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public void runtimeExceptionCanRollback() {
        extraAdRepository.save(new ExtraAd("wang"));
        throw new RuntimeException();
    }

    @Override
    @Transactional(rollbackFor = CustomException.class)
    public void assignExceptionCanRollback() throws CustomException {
        try {
            extraAdRepository.save(new ExtraAd("wang"));
            throw new RuntimeException();
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    @Transactional
    public void oneSaveMethod() {
        extraAdRepository.save(new ExtraAd("wang"));
    }

    @Override
    @Transactional
    public void rollbackOnlyCanRollback() {
        oneSaveMethod();

        try {
            extraAdRepository.save(new ExtraAd());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
