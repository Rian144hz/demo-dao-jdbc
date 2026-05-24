package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("===== Test 1 findById ====");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("===== Test 2 findByDepartment ====");
        Department dp = new Department();
        dp.setId(2);
        List<Seller> list = sellerDao.findDepartment(dp);

        for (Seller sell: list){
            System.out.println(sell);
        }
        System.out.println("===== Test 3 findAll ====");
        List<Seller> list1 = sellerDao.findAll();

        for (Seller sell1: list1){
            System.out.println(sell1);
        }




    }
}
