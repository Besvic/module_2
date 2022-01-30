package com.epam.esm;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.List;

public class _main {
    // TODO: 27.01.2022 del
    public static void main(String[] args) {
        BigDecimal cost = BigDecimal.ZERO;
        cost = cost.add(new BigDecimal(10));
        System.out.println(cost);
    }

}
