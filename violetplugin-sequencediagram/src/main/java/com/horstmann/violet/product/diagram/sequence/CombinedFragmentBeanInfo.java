package com.horstmann.violet.product.diagram.sequence;


import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * The bean info for the ClassNode type.
 */
public class CombinedFragmentBeanInfo extends SimpleBeanInfo
{
    /*
     * (non-Javadoc)
     * 
     * @see java.beans.BeanInfo#getPropertyDescriptors()
     */
    public PropertyDescriptor[] getPropertyDescriptors()
    {
        try
        {
        	PropertyDescriptor nameDescriptor = new PropertyDescriptor("name", CombinedFragment.class);
        	nameDescriptor.setValue("priority", new Integer(1));
            PropertyDescriptor typeDescriptor = new PropertyDescriptor("type", CombinedFragment.class);
            typeDescriptor.setValue("priority", new Integer(2));
            PropertyDescriptor conditionDescriptor = new PropertyDescriptor("condition", CombinedFragment.class);
            conditionDescriptor.setValue("priority", new Integer(3));
            PropertyDescriptor widthDescriptor = new PropertyDescriptor("width", CombinedFragment.class);
            widthDescriptor.setValue("priority", new Integer(4));
            PropertyDescriptor heightDescriptor = new PropertyDescriptor("height", CombinedFragment.class);
            heightDescriptor.setValue("priority", new Integer(5));
            return new PropertyDescriptor[]
            {
            		nameDescriptor,
                    typeDescriptor,
                    conditionDescriptor,
//                    widthDescriptor,
//                    heightDescriptor,
                
            };
        }
        catch (IntrospectionException exception)
        {
            return null;
        }
    }
}
