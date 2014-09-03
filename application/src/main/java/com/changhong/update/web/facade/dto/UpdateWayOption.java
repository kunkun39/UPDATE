package com.changhong.update.web.facade.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-14
 * Time: 上午10:31
 */
public class UpdateWayOption implements Option {

    private String value;

    private String label;

    public static List<UpdateWayOption> getAllOptions() {
        List<UpdateWayOption> options = new ArrayList<UpdateWayOption>();

        options.add(new UpdateWayOption("1", "固件升级"));
        options.add(new UpdateWayOption("2", "差分升级包"));
        options.add(new UpdateWayOption("3", "数字电视应用程序"));
        options.add(new UpdateWayOption("4", "应用程序"));
        options.add(new UpdateWayOption("5", "二进制数据包"));

        return options;
    }

    public UpdateWayOption(String value, String label) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }
}
