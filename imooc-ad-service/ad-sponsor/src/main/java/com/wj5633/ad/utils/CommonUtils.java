package com.wj5633.ad.utils;

import com.wj5633.ad.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/1/30 1:33
 * @description
 */
public class CommonUtils {

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };

    public static String md5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    public static Date parseStringToDate(String dateString) throws AdException {
        try {
            return DateUtils.parseDate(dateString, parsePatterns);
        } catch (ParseException e) {
            throw new AdException(e.getMessage());
        }
    }
}
