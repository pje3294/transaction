package model.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.common.JNDI;

public class BankDAO {

	// DB의 저장된 은행고객의 모든 정보 보여줌
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
			System.out.println("selectAll실행 오류 "); // 로깅
			e.printStackTrace();
		} finally {
			JNDI.disconnect(pstmt, conn);
		}
		return datas;

	}

	// 계좌이체 메서드 trans() ==> 트랜잭션 , update!!!
	public boolean trans(int money, String sendName, String giveName) {
		Connection conn = JNDI.connect();
		PreparedStatement pstmt = null;

		try {
			conn.setAutoCommit(false); // 오토커밋 끄기
			String sql1 = "update bank set balance= balance-? where name=?";
			pstmt = conn.prepareStatement(sql1); // 돈 주는 사람
			pstmt.setInt(1, money);
			pstmt.setString(2, sendName);
			pstmt.executeUpdate();
			String sql2 = "update bank set balance= balance+? where name=? ";
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, money);
			pstmt.setString(2, giveName); // 돈 받는 사람
			pstmt.executeUpdate();

			String sql3 = "select balance from bank where name =?";
			pstmt = conn.prepareStatement(sql3);
			pstmt.setString(1, sendName);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			if (rs.getInt(1) < 0) { // 이체 후, 잔액이 0미만이면 안되기 때문에 확인 작업 필요
				conn.rollback(); // 이체 후 잔액이 0미만이면 롤백 실행
				return false;
			} else {
				conn.commit(); // 잔고가 있으면 커밋 실행
			}
			conn.setAutoCommit(true); // 오토커밋 다시 켜기

		} catch (Exception e) {
			System.out.println("trans 실패"); // 로깅
			e.printStackTrace();
		} finally {
			JNDI.disconnect(pstmt, conn);

		}
		return true;
	}
}
