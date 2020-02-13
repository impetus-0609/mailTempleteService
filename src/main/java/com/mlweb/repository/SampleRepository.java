package com.mlweb.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.mlweb.model.SampleModel;

@Repository
@Mapper
public interface SampleRepository {

  /*
   * CREATE SCHEMA mailTemplete CREATE TABLE test (title text, entry_date date) ;
   */
  @Select("select * from mailtemplete.test")
  public List<SampleModel> findAll();

}
