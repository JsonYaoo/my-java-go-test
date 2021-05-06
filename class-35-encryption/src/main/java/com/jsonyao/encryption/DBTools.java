package com.jsonyao.encryption;

import com.dqiangpay.common.global.Global;
import org.apache.ibatis.session.SqlSession;

import java.util.function.Function;

public class DBTools {
	private ThreadLocal<SqlSession> THREAD_LOCAL = new ThreadLocal<>();
	
	public <T> T runSql(Function<SqlSession, T> function) {
		return runSql(function, false);
	}
	

	public <T> T runSql(Function<SqlSession, T> function, boolean isReadOnly) {
		SqlSession sqlSession = THREAD_LOCAL.get();
		if (sqlSession == null) {
			/*
			 * 表示为此线程的起点，在此处负责事务的提交/回滚/以及关闭sqlSession
			 */
			sqlSession = isReadOnly?Global.DB_RO.openSession():Global.DB.openSession();//by default, auto commit is false
			THREAD_LOCAL.set(sqlSession);
			try {
				T t = function.apply(sqlSession);
				sqlSession.commit();
				return t;
			} catch (Exception e) {
				sqlSession.rollback(true);
				throw new RuntimeException(e);
			} finally {
				THREAD_LOCAL.remove();
				try {
					sqlSession.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			/*
			 * 表示此线程已经创建过sqlSession，此处操作只需要追加即可
			 */
			try {
				return function.apply(sqlSession);
			} catch (Exception e) {
				throw new RuntimeException(e);//或改为扔出checked exception 
			}
		}
	}
}
