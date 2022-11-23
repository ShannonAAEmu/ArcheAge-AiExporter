package descriptors.impl.links;

import descriptors.Descriptor;
import entities.Vector;

import java.util.Objects;

public class NodeLinkDescriptor implements Descriptor {

    private int sourceNode;
    private int targetNode;
    private Vector edgeCenter;
    private double maxPassRadius;
    private double exposure;
    private double length;
    private double maxWaterDepth;
    private double minWaterDepth;
    private byte startIndex;
    private byte endIndex;
    private byte isPureTriangularLink;      // Don't use in CryEngine source code
    private byte simplePassabilityCheck;

    public NodeLinkDescriptor() {
    }

    public int getSourceNode() {
        return sourceNode;
    }

    public void setSourceNode(int sourceNode) {
        this.sourceNode = sourceNode;
    }

    public int getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(int targetNode) {
        this.targetNode = targetNode;
    }

    public Vector getEdgeCenter() {
        return edgeCenter;
    }

    public void setEdgeCenter(Vector edgeCenter) {
        this.edgeCenter = edgeCenter;
    }

    public double getMaxPassRadius() {
        return maxPassRadius;
    }

    public void setMaxPassRadius(double maxPassRadius) {
        this.maxPassRadius = maxPassRadius;
    }

    public double getExposure() {
        return exposure;
    }

    public void setExposure(double exposure) {
        this.exposure = exposure;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getMaxWaterDepth() {
        return maxWaterDepth;
    }

    public void setMaxWaterDepth(double maxWaterDepth) {
        this.maxWaterDepth = maxWaterDepth;
    }

    public double getMinWaterDepth() {
        return minWaterDepth;
    }

    public void setMinWaterDepth(double minWaterDepth) {
        this.minWaterDepth = minWaterDepth;
    }

    public byte getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(byte startIndex) {
        this.startIndex = startIndex;
    }

    public byte getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(byte endIndex) {
        this.endIndex = endIndex;
    }

    public byte getIsPureTriangularLink() {
        return isPureTriangularLink;
    }

    public void setIsPureTriangularLink(byte isPureTriangularLink) {
        this.isPureTriangularLink = isPureTriangularLink;
    }

    public byte getSimplePassabilityCheck() {
        return simplePassabilityCheck;
    }

    public void setSimplePassabilityCheck(byte simplePassabilityCheck) {
        this.simplePassabilityCheck = simplePassabilityCheck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeLinkDescriptor that = (NodeLinkDescriptor) o;
        return sourceNode == that.sourceNode && targetNode == that.targetNode && Double.compare(that.maxPassRadius, maxPassRadius) == 0 && Double.compare(that.exposure, exposure) == 0 && Double.compare(that.length, length) == 0 && Double.compare(that.maxWaterDepth, maxWaterDepth) == 0 && Double.compare(that.minWaterDepth, minWaterDepth) == 0 && startIndex == that.startIndex && endIndex == that.endIndex && isPureTriangularLink == that.isPureTriangularLink && simplePassabilityCheck == that.simplePassabilityCheck && Objects.equals(edgeCenter, that.edgeCenter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceNode, targetNode, edgeCenter, maxPassRadius, exposure, length, maxWaterDepth, minWaterDepth, startIndex, endIndex, isPureTriangularLink, simplePassabilityCheck);
    }

    @Override
    public String toString() {
        return "NodeLinkDescriptor{" +
                "sourceNode=" + sourceNode +
                ", targetNode=" + targetNode +
                ", edgeCenter=" + edgeCenter +
                ", maxPassRadius=" + maxPassRadius +
                ", exposure=" + exposure +
                ", length=" + length +
                ", maxWaterDepth=" + maxWaterDepth +
                ", minWaterDepth=" + minWaterDepth +
                ", startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", isPureTriangularLink=" + isPureTriangularLink +
                ", simplePassabilityCheck=" + simplePassabilityCheck +
                '}';
    }
}
