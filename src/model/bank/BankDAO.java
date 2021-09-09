package model.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.common.JNDI;

public class BankDAO {

	// DB�� ����� ������� ��� ���� ������
	public ArrayList<BankVO> selectAll() {
		Connection conn = JNDI.connect();
		ArrayList<BankVO> datas = new ArrayList<BankVO>();
		PreparedStatement pstmt = null;

		try {
			String sql = "select * from bank";

			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				BankVO data = new BankVO();
				data.setBalance(rs.getInt("balance"));
				data.setBid(rs.getInt("bid"));
				data.setName(rs.getString("name"));
				datas.add(data);
			}
			rs.close();
		} catch (Exception e) {
			System.out.println("selectAll���� ���� "); // �α�
			e.printStackTrace();
		} finally {
			JNDI.disconnect(pstmt, conn);
		}
		return datas;

	}

	// ������ü �޼��� trans() ==> Ʈ����� , update!!!
	public boolean trans(int money, String sendName, String giveName) {
		Connection conn = JNDI.connect();
		PreparedStatement pstmt = null;

		try {
			conn.setAutoCommit(false); // ����Ŀ�� ����
			String sql1 = "update bank set balance= balance-? where name=?";
			pstmt = conn.prepareStatement(sql1); // �� �ִ� ���
			pstmt.setInt(1, money);
			pstmt.setString(2, sendName);
			pstmt.executeUpdate();
			String sql2 = "update bank set balance= balance+? where name=? ";
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, money);
			pstmt.setString(2, giveName); // �� �޴� ���
			pstmt.executeUpdate();

			String sql3 = "select balance from bank where name =?";
			pstmt = conn.prepareStatement(sql3);
			pstmt.setString(1, sendName);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			if (rs.getInt(1) < 0) { // ��ü ��, �ܾ��� 0�̸��̸� �ȵǱ� ������ Ȯ�� �۾� �ʿ�
				conn.rollback(); // ��ü �� �ܾ��� 0�̸��̸� �ѹ� ����
				return false;
			} else {
				conn.commit(); // �ܰ� ������ Ŀ�� ����
			}
			conn.setAutoCommit(true); // ����Ŀ�� �ٽ� �ѱ�

		} catch (Exception e) {
			System.out.println("trans ����"); // �α�
			e.printStackTrace();
		} finally {
			JNDI.disconnect(pstmt, conn);

		}
		return true;
	}
}
