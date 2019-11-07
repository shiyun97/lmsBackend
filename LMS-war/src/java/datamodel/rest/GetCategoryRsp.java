/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Category;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yaosh
 */
public class GetCategoryRsp {
    
    private List<Category> categories;

    public GetCategoryRsp() {
        this.categories = new ArrayList<>();
    }

    public GetCategoryRsp(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
    
}
