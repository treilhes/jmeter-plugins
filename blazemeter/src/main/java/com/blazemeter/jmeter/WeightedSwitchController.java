package com.blazemeter.jmeter;

import kg.apc.jmeter.JMeterPluginsUtils;
import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.gui.util.PowerTableModel;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import java.io.Serializable;
import java.util.Arrays;

public class WeightedSwitchController extends GenericController implements Serializable {
    private static final Logger log = LoggingManager.getLoggerForClass();
    public static final String WEIGHTS = "Weights";
    protected long[] sums = null;

    public void setData(PowerTableModel model) {
        CollectionProperty prop = JMeterPluginsUtils.tableModelRowsToCollectionProperty(model, WEIGHTS);
        log.warn("Set prop from model: " + prop, new Throwable());
        setProperty(prop);
    }

    public CollectionProperty getData() {
        JMeterProperty prop = getProperty(WEIGHTS);
        log.info("Weights prop: " + prop, new Throwable());
        if (prop instanceof CollectionProperty) {
            return (CollectionProperty) prop;
        } else {
            log.warn("Returning empty collection");
            return new CollectionProperty();
        }
    }

    @Override
    public Sampler next() {
        incrementSums();
        return super.next();
    }

    private void incrementSums() {
        CollectionProperty data = getData();
        if (sums == null) {
            log.warn("Creating array of size " + data.size());
            sums = new long[data.size()];
        }

        long min = Long.MAX_VALUE;
        for (int n = 0; n < sums.length; n++) {
            CollectionProperty row = (CollectionProperty) data.get(n);
            sums[n] += row.get(1).getLongValue();
            min = Math.min(min, sums[n]);
        }

        if (min != Long.MAX_VALUE && min > 1) {
            for (int n = 0; n < sums.length; n++) {
                sums[n] -= min - 1; // +1 to not mix zero prio with others
            }
        }

        log.info("Weights: " + Arrays.toString(sums));
        long max = Long.MIN_VALUE;
        for (int n = 0; n < sums.length; n++) {
            if (sums[n] > max) {
                CollectionProperty row = (CollectionProperty) data.get(n);
                sums[n] -= row.get(1).getLongValue();
                current = n;
            }
        }
    }

}
