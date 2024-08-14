package com.sb.ims.repository;

import com.sb.ims.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long>
{

}
