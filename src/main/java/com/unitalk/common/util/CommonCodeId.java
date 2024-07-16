package com.unitalk.common.util;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class CommonCodeId implements Serializable {
    private String division;
    private String code;

    //기본 생성자
    public CommonCodeId() {}

    public CommonCodeId(String division, String code) {
        this.division = division;
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonCodeId that = (CommonCodeId) o;
        return Objects.equals(division, that.division) && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(division, code);
    }
}
