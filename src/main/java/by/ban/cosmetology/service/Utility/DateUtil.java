/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.service.Utility;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author alabkovich
 */
public class DateUtil {
    public static LocalDate getLocalDate(Date date) {
        return LocalDate.from(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
    
    public static Date getUtilDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    public static Date getStartOfMonth(Date date) {
        LocalDate localDate = getLocalDate(date);
        
        return getUtilDate(localDate.withDayOfMonth(1));
    }
    
    public static Date getEndOfMonth(Date date) {
        LocalDate localDate = getLocalDate(date);
        
        return getUtilDate(localDate.withDayOfMonth(localDate.lengthOfMonth()));
    }
}
