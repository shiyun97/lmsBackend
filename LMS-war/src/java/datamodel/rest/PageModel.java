/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Question;
import java.util.List;

/**
 *
 * @author Asus
 */
public class PageModel {
    private List<Question> elements;
    private String name;

    public PageModel() {
    }

    public PageModel(List<Question> elements, String name) {
        this.elements = elements;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getElements() {
        return elements;
    }

    public void setElements(List<Question> elements) {
        this.elements = elements;
    }
}
