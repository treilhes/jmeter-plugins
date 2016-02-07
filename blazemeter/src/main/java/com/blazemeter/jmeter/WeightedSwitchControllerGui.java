package com.blazemeter.jmeter;

import org.apache.jmeter.control.Controller;
import org.apache.jmeter.control.gui.AbstractControllerGui;
import org.apache.jmeter.gui.GuiPackage;
import org.apache.jmeter.gui.tree.JMeterTreeModel;
import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import java.util.LinkedList;

public class WeightedSwitchControllerGui extends AbstractControllerGui {
    private static final Logger log = LoggingManager.getLoggerForClass();

    @Override
    public String getLabelResource() {
        return getClass().getCanonicalName();
    }

    @Override
    public String getStaticLabel() {
        return "Weighted Switch Controller";
    }

    @Override
    public TestElement createTestElement() {
        WeightedSwitchController te = new WeightedSwitchController();
        modifyTestElement(te);
        return te;
    }

    @Override
    public void modifyTestElement(TestElement element) {
        super.configureTestElement(element);
        // from GUI to model
    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);
        // from model to GUI

        GuiPackage gp = GuiPackage.getInstance();

        if (gp != null) {
            JMeterTreeModel treeModel = GuiPackage.getInstance().getTreeModel();
            JMeterTreeNode root = (JMeterTreeNode) treeModel.getRoot();
            LinkedList<JMeterTreeNode> childItems = getChildItems(root, element);
            log.info("Children found: " + childItems.size());
            for (JMeterTreeNode node : childItems) {
                log.warn("Child: " + node.getTestElement());
                // TODO
            }
        }
    }

    private LinkedList<JMeterTreeNode> getChildItems(JMeterTreeNode root, TestElement element) {
        LinkedList<JMeterTreeNode> result = new LinkedList<>();
        for (int i = 0; i < root.getChildCount(); i++) {
            JMeterTreeNode child = (JMeterTreeNode) root.getChildAt(i);

            TestElement te = child.getTestElement();
            if (element != root.getTestElement()) {
                result.addAll(getChildItems(child, element));
            } else {
                if (te instanceof Sampler || te instanceof Controller) {
                    result.add(child);
                }
            }
        }
        return result;
    }

    @Override
    public void clearGui() {
        super.clearGui();
    }
}
