package cn.cat.platform.model.VO;

import lombok.Data;

/**
 * @author linlt
 * @createTime 2020/4/23 13:11
 * @description TODO
 */
@Data
public class Header {
    /**
     * 表头的中文名称
     */
    private String label;

    /**
     * 表头的英文名称
     */
    private String prop;

    public Header(String label, String prop) {
        this.label = label;
        this.prop = prop;
    }
}
