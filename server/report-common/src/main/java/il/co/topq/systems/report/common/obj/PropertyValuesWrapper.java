package il.co.topq.systems.report.common.obj;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

/**
 * This class is a wrapper for a property; for each property (test/scenario)
 * will hold its key and a list all its values
 */
@XmlRootElement(name = "PropertyValuesWrapper")
@XmlAccessorType(XmlAccessType.FIELD)
public class PropertyValuesWrapper {

    @XmlElement
    String propertyKey;
    @XmlElement
    Set<String> propertyKeyValueList;

    public PropertyValuesWrapper(String propertyKey, Set<String> propertyKeyValueList) {
        this.propertyKey = propertyKey;
        this.propertyKeyValueList = propertyKeyValueList;
    }

    public PropertyValuesWrapper() {

    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public Set<String> getPropertyKeyValueList() {
        return propertyKeyValueList;
    }

    public void setPropertyKeyValueList(Set<String> propertyKeyValueList) {
        this.propertyKeyValueList = propertyKeyValueList;
    }

    @Override
    public String toString() {
        String res = ("property key: " + propertyKey + "\n");

        for (String value : propertyKeyValueList) {
            res += ("property value: " + value + " \n");
        }
        return res;
    }

}
