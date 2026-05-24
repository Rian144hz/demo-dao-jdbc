package model.dao.impl;

import db.DB;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private final Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }


    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

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
        return List.of();
    }
}
