package com.horstmann.violet.application.gui.util.yangjie;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;



public class HibernateUtils {

	private  SessionFactory sf;
	public HibernateUtils()
	{
		sf = new Configuration().configure().buildSessionFactory();
	}

	// ����Session����
	public  Session getSession() {
		return sf.openSession();
	}

	public  void saveTCDetail(TCDetail detail) {
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
