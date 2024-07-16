package com.unitalk.common.repository;

import com.unitalk.common.model.entity.CommonCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonCodesRepository extends JpaRepository<CommonCode, String> {

}
