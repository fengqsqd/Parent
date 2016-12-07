package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom1;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DoubleConverter;
import com.thoughtworks.xstream.converters.basic.FloatConverter;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.converters.basic.LongConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * xml����������
 * 
 * @author FireAnt��http://my.oschina.net/LittleDY��
 * @version ����ʱ�䣺2014��9��27�� ����2:04:19
 * 
 */

public class XStreamTransformUtil {

    private final static String TAG = XStreamTransformUtil.class.getSimpleName();

    /**
     * ��һ��xml��ת��Ϊbeanʵ����
     * 
     * @param type
     * @param is
     * @return
     * @throws //AppException
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(Class<T> type, InputStream is) { 
//       json�� JettisonMappedXmlDriver()  || xml�� DomDriver()
        XStream xmStream = new XStream(new DomDriver("UTF-8"));
        // ���ÿɺ���Ϊ��javabean���ж���Ľ�������
      //   xmStream.ignoreUnknownElements();
        xmStream.registerConverter(new MyIntCoverter());
        xmStream.registerConverter(new MyLongCoverter());
        xmStream.registerConverter(new MyFloatCoverter());
        xmStream.registerConverter(new MyDoubleCoverter());
        xmStream.processAnnotations(type);
        T obj = null;
        try {
            obj = (T) xmStream.fromXML(is);
        } catch (Exception e) {
            System.out.println("ת���쳣");
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                  
                }
            }
        }
        return obj;
    }
    
    
    public static <T> T toBean(Class<T> type, byte[] bytes) { //ֱ�Ӵ���byte
        if (bytes == null) return null;
        return toBean(type, new ByteArrayInputStream(bytes));
    }

    private static class MyIntCoverter extends IntConverter {

        @Override
        public Object fromString(String str) {
            int value;
            try {
                value = (Integer) super.fromString(str);
            } catch (Exception e) {
                value = 0;
            }
            return value;
        }

        @Override
        public String toString(Object obj) {
            return super.toString(obj);
        }
    }

    private static class MyLongCoverter extends LongConverter {
        @Override
        public Object fromString(String str) {
            long value;
            try {
                value = (Long) super.fromString(str);
            } catch (Exception e) {
                value = 0;
            }
            return value;
        }

        @Override
        public String toString(Object obj) {
            return super.toString(obj);
        }
    }

    private static class MyFloatCoverter extends FloatConverter {
        @Override
        public Object fromString(String str) {
            float value;
            try {
                value = (Float) super.fromString(str);
            } catch (Exception e) {
                value = 0;
            }
            return value;
        }

        @Override
        public String toString(Object obj) {
            return super.toString(obj);
        }
    }

    private static class MyDoubleCoverter extends DoubleConverter {
        @Override
        public Object fromString(String str) {
            double value;
            try {
                value = (Double) super.fromString(str);
            } catch (Exception e) {
                value = 0;
            }
            return value;
        }

        @Override
        public String toString(Object obj) {
            return super.toString(obj);
        }
    }
}
