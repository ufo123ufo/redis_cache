package test.com.youguu.cache.dao;

import java.util.List;
import java.util.Map;

import test.com.youguu.cache.pojo.Product;

public interface ITestProductDAO {

	Product get();

	void update();

	Product get(int type);

	int getId();

	Integer getInteger();

	String getName(int uid,String accountId);

	float getFloat();

	Float getFFloat();

	double getDouble();

	Double getDDouble();

	List<Product> findList();

	Map<String,String> findMap();
}
