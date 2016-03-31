package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;
import org.joda.time.DateTime;

/**
 * User: Jack Wang
 * Date: 14-4-29
 * Time: 下午3:13
 */
public class ClientUpdateHistory extends EntityBase {

    private String username;

    private String productModel;

    private String guJianVersion;

    private String guJianVersionAfter;

    private int year;

    private int month;

    private int day;

    private int hour;

    private String success;

    public ClientUpdateHistory() {
    }

    public ClientUpdateHistory(String username, String productModel, String guJianVersion, String gujianVersionAfter, String success) {
        this.username = username;
        this.productModel = productModel;
        this.guJianVersion = guJianVersion;
        this.guJianVersionAfter = gujianVersionAfter;
        DateTime time = new DateTime();
        this.year = time.getYear();
        this.month = time.getMonthOfYear();
        this.day = time.getDayOfMonth();
        this.hour = time.getHourOfDay();
        this.success = success;

    }

    /********************************************GET/SET***********************************************/

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getGuJianVersion() {
        return guJianVersion;
    }

    public void setGuJianVersion(String guJianVersion) {
        this.guJianVersion = guJianVersion;
    }

    public String getGuJianVersionAfter() {
        return guJianVersionAfter;
    }

    public void setGuJianVersionAfter(String guJianVersionAfter) {
        this.guJianVersionAfter = guJianVersionAfter;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
