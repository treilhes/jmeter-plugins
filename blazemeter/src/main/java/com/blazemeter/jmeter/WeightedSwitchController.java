package com.blazemeter.jmeter;

import kg.apc.jmeter.JMeterPluginsUtils;
import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.gui.util.PowerTableModel;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.JMeterProperty;

import java.io.Serializable;

public class WeightedSwitchController extends GenericController implements Serializable {
    private static final String WEIGHTS = "Weights";

    public void setData(PowerTableModel model) {
        CollectionProperty prop = JMeterPluginsUtils.tableModelRowsToCollectionProperty(model, WEIGHTS);
        setProperty(prop);
    }

    public CollectionProperty getData() {
        JMeterProperty prop = getProperty(WEIGHTS);
        if (prop instanceof CollectionProperty) {
            return (CollectionProperty) prop;
        } else {
            return new CollectionProperty();
        }
    }
}
