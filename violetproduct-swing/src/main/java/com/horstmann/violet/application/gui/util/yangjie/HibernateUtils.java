package com.horstmann.violet.application.gui.util.yangjie;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;


public class HibernateUtils {

	private static SessionFactory sf;
	static {
		// �����������ļ�, ������Session�Ĺ���
		sf = new Configuration().configure().buildSessionFactory();
		// sf = new Configuration().configure(new
		// File("hibernate.cfg.xml")).buildSessionFactory();
	}

	// ����Session����
	public static Session getSession() {
		return sf.openSession();
	}

	public static void saveTCDetail(TCDetail detail) {
		// ����

		// ����session�Ĺ���������session����
		Session session = sf.openSession();
		// ��������
		Transaction tx = session.beginTransaction();
		// -----ִ�в���-----
		session.save(detail);

		// �ύ����/ �ر�
		tx.commit();
		session.close();
	}
	public static void saveTCDetails(List<TCDetail> details) {
		// ����

		// ����session�Ĺ���������session����
		Session session = sf.openSession();
		// ��������
		Transaction tx = session.beginTransaction();
		// -----ִ�в���-----
		for(int i = 0;i < details.size();i++)
		{
			session.save(details.get(i));
			if(i % 100 == 0 )
			{
				session.flush();
				session.clear(); 
			}
		}
		// �ύ����/ �ر�
		tx.commit();
		session.close();
	}

	@Test
	public void save() throws Exception {
		// ����
		TCDetail detail = TCDetail.getInstance();
		detail.setStimulateSequence("stimulate");
		detail.setTestCase("testcase");
		detail.setTestSequence("testSequence");
		// ����session�Ĺ���������session����
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		// ��������
		Transaction tx = session.beginTransaction();
		// -----ִ�в���-----
		session.save(detail);

		// �ύ����/ �ر�
		tx.commit();
		session.close();
	}
}
