package com.app.fku.amazonx.model;

import java.util.List;

public class AmazonRequestSub2Model {
    private List<String> terms;
    private List<String> isSearch;

    public List<String> getTerms() {
        return terms;
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
    }

    public List<String> getIsSearch() {
        return isSearch;
    }

    public void setIsSearch(List<String> isSearch) {
        this.isSearch = isSearch;
    }
}