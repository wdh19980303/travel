package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {
    // 通过route的sid查询商家
    public Seller findBySeller(int sid);
}
