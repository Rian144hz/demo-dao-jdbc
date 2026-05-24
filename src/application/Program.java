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
        Department department = new Department();
        department.setId(2);
        SellerDao sellerDao = DaoFactory.createSellerDao();

        List<Seller> list = sellerDao.findDepartment(department);
        for(Seller sell: list){
            System.out.println(sell);
        }


        Seller seller  = sellerDao.findById(3);

        System.out.println(seller);
    }
}
