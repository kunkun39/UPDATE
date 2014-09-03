package com.changhong.update.web.facade.dto;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 下午3:43
 */
public class CategoryDTO {

    private int id;

    private String name;

    private String fullPath;

    private int parentId;

    public CategoryDTO() {
    }

    public CategoryDTO(int parentId) {
        this.parentId = parentId;
    }

    public CategoryDTO(int id, String name, int parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public CategoryDTO(int id, String name, String fullPath, int parentId) {
        this.id = id;
        this.name = name;
        this.fullPath = fullPath;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
