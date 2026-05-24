package application;

import db.DbException;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) throws ParseException {
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

        System.out.println("==== Test 4 insert ====");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try{
            Department dep = new Department();
            dep.setId(1);


            Seller newSeller = new Seller();
            newSeller.setName("Matheus");
            newSeller.setEmail("matheus@gmail.com");
            newSeller.setBirthDate(sdf.parse("22/04/1985"));
            newSeller.setBaseSalary(3000.0);
            newSeller.setDepartment(dep);
             sellerDao.insert(newSeller);

        }catch (DbException e ){
            e.printStackTrace();
        }

        System.out.println("==== Test 5 update ====");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Seller sell1 = sellerDao.findById(9);

            sell1.setBirthDate(simpleDateFormat.parse("04/02/2006"));

            sellerDao.update(sell1);

        }catch (ParseException e){
            e.printStackTrace();
        }

        System.out.println("==== Test 6 delete ====");
        sellerDao.deleteById(8);

    }
}
