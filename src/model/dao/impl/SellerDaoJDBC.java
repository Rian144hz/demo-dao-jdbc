package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private final Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }


    @Override
    public void insert(Seller seller) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO seller\n" +
                            "(Name, Email, BirthDate, BaseSalary, DepartmentId)\n" +
                            "VALUES\n" +
                            "(?, ?, ?, ?, ?)"
            );
            st.setString(1,seller.getName());
            st.setString(2,seller.getEmail());
            st.setDate(3,new java.sql.Date(seller.getBirthDate().getTime()));
            st.setDouble(4,seller.getBaseSalary());
            st.setInt(5,seller.getDepartment().getId());


            st.executeUpdate();
            System.out.println("Done!");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE seller\n " +
                            "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?\n " +
                            "WHERE Id = ? "
            );
            st.setString(1,seller.getName());
            st.setString(2,seller.getEmail());
            st.setDate(3,new java.sql.Date(seller.getBirthDate().getTime()));
            st.setDouble(4,seller.getBaseSalary());
            st.setInt(5,seller.getDepartment().getId());
            st.setInt(6, seller.getId());
            st.executeUpdate();
            System.out.println("Done! uptade birthDate!");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "DELETE FROM seller WHERE Id = ?"
            );
            st.setInt(1,id);
            st.executeUpdate();
            System.out.println("Done! delete sucessfull");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName\n " +
                            "FROM seller INNER JOIN department\n " +
                            "ON seller.DepartmentId = department.Id\n " +
                            "WHERE seller.Id = ? "
            );
            st.setInt(1,id);
            rs = st.executeQuery();
            if (rs.next()){
                Department dp = new Department();
                dp.setId(rs.getInt("DepartmentId"));
                dp.setName(rs.getString("DepName"));
                Seller sel = new Seller();
                sel.setId(rs.getInt("Id"));
                sel.setName(rs.getString("Name"));
                sel.setEmail(rs.getString("Email"));
                sel.setBaseSalary(rs.getDouble("BaseSalary"));
                sel.setBirthDate(rs.getDate("BirthDate"));
                sel.setDepartment(dp);
                return sel;
            }
            return null;
        }catch (SQLException e){
            e.printStackTrace();;
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        return null;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName\n" +
                            "FROM seller INNER JOIN department\n" +
                            "ON seller.DepartmentId = department.Id\n" +
                            "ORDER BY Name"
            );
            rs = st.executeQuery();
            List<Seller> list = new ArrayList<>();
            Department dp = new Department();
            boolean hasDepartmentData = false;
            while (rs.next()){
                if (!hasDepartmentData) {
                    dp.setId(rs.getInt("DepartmentId"));
                    dp.setName(rs.getString("DepName"));
                    hasDepartmentData = true;
                }
                Seller sel = new Seller();
                sel.setId(rs.getInt("Id"));
                sel.setName(rs.getString("Name"));
                sel.setEmail(rs.getString("Email"));
                sel.setBaseSalary(rs.getDouble("BaseSalary"));
                sel.setBirthDate(rs.getDate("BirthDate"));
                sel.setDepartment(dp);

                list.add(sel);

            }
            return list;
        }catch (SQLException e){
            e.printStackTrace();;
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        return null;
    }

    @Override
    public List<Seller> findDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName\n" +
                            "FROM seller INNER JOIN department\n" +
                            "ON seller.DepartmentId = department.Id\n" +
                            "WHERE DepartmentId = ?\n" +
                            "ORDER BY Name"
            );
            st.setInt(1,department.getId());
            rs = st.executeQuery();
            List<Seller> list = new ArrayList<>();
            Department dp = new Department();
            boolean hasDepartmentData = false;
            while (rs.next()){
                if (!hasDepartmentData) {
                    dp.setId(rs.getInt("DepartmentId"));
                    dp.setName(rs.getString("DepName"));
                    hasDepartmentData = true;
                }
                Seller sel = new Seller();
                sel.setId(rs.getInt("Id"));
                sel.setName(rs.getString("Name"));
                sel.setEmail(rs.getString("Email"));
                sel.setBaseSalary(rs.getDouble("BaseSalary"));
                sel.setBirthDate(rs.getDate("BirthDate"));
                sel.setDepartment(dp);

                list.add(sel);

            }
            return list;
        }catch (SQLException e){
            e.printStackTrace();;
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        return null;
    }

    }

