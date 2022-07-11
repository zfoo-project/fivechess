package com.zfoo.fivechess.common.resource;


import com.zfoo.storage.model.anno.Id;
import com.zfoo.storage.model.anno.Resource;

@Resource
public class FilterWordResource {

    @Id
    private int id;

    private String filter;

    public int getId() {
        return id;
    }

    public String getFilter() {
        return filter;
    }

}